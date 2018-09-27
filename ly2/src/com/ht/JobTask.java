package com.ht;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ht.dao.EmailDao;
import com.ht.dao.HolidayDao;
import com.ht.dao.StockDao;
import com.ht.dao.StockDayDetailDao;
import com.ht.dao.StockDealDao;
import com.ht.email.EmailServiceImpl;
import com.ht.http.web.PingAn;
import com.ht.pinyin.PinyinUtilsPro;
import com.ht.pojo.Email;
import com.ht.pojo.Holiday;
import com.ht.pojo.Stock;
import com.ht.pojo.StockDayDealsRecord;
import com.ht.pojo.StockDayDetail;


/*
 
 mysql 触发器
#https://www.cnblogs.com/wudage/p/7940960.html
#https://www.linuxidc.com/Linux/2017-05/144015.htm
#https://blog.csdn.net/u014134828/article/details/77221559
#https://blog.csdn.net/santta/article/details/52588950
#https://www.cnblogs.com/phpper/p/7587031.html  OLD和New的说明 https://www.cnblogs.com/joyco773/p/5787088.html
#https://blog.csdn.net/u010034987/article/details/46399827


--修改
DROP TRIGGER IF EXISTS upd_check02;
CREATE TRIGGER upd_check02 BEFORE update ON stock
 FOR EACH ROW
BEGIN
if new.`code` LIKE '000%' THEN
set new.marketType ='4609';
elseif new.`code` LIKE '002%' THEN
set new.marketType ='4614';
elseif new.`code` LIKE '300%' THEN
set new.marketType ='4621';
elseif new.`code` LIKE '60%' THEN
set new.marketType ='4353';
end if;
end



-----新增
DROP TRIGGER IF EXISTS insert_check;
CREATE TRIGGER insert_check BEFORE insert ON stock
 FOR EACH ROW
BEGIN
if new.`code` LIKE '000%' THEN
set new.marketType ='4609';
elseif new.`code` LIKE '002%' THEN
set new.marketType ='4614';
elseif new.`code` LIKE '300%' THEN
set new.marketType ='4621';
elseif new.`code` LIKE '60%' THEN
set new.marketType ='4353';
end if;
end



/*update stock set marketType='4609' where `code` like '000%';
update stock set marketType='4614' where `code` like '002%';
update stock set marketType='4621' where `code` like '300%';
update stock set marketType='4353' where `code` like '60%';*/





@Component
public class JobTask {
	private static Logger logger = Logger.getLogger(JobTask.class);

	@Autowired
	private PingAn pingan;
	@Autowired
	private StockDao stockDao;
	
	@Autowired
	private StockDealDao stockDealDao;
	
	@Autowired
	private StockDayDetailDao stockDayDetailDao;

	@Autowired
	private HolidayDao holidayDao;
	
	@Autowired
	private EmailServiceImpl emailImp;
	
	@Autowired
	private EmailDao emailDao;
	
	///公用的休市表
	
	public boolean getIsBegin() {
		boolean flag = false;

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY); // http://blog.csdn.net/jiangeeq/article/details/53103069
		int minute = c.get(Calendar.MINUTE);
		flag = hour == 11 && minute >= 30 || hour == 9 && minute <=29||hour==14&&minute>=57;
		return flag;
	}
	
	// 计算两个日期相差天数
	// https://www.cnblogs.com/mingforyou/p/3545174.html
	public static final int daysBetween(Date early, Date late) {

		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}
		
	
	public boolean getIsHoliday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		//System.out.println("当前日期=============>" + df.format(new Date()));
		Holiday h = holidayDao.getIsHoliday();
		if (h != null) {
			//log.log(Level.OFF, "当前节日" + h.getHolidayName() + ",系统不监控 ");
			return true;
		} else {
			return false;
		}
	}
	
	//根据当前系统时间，每月15号的10点15分更新一次假日表的节日信息
	//@Scheduled(cron="0/10 * *  * * ? ")
	//https://blog.csdn.net/qq_33556185/article/details/51852537
	@Scheduled(cron= "0 15 10 15 * ?")
	public void updateHolidayByCurrentSystem() throws Exception{
		Calendar rightNow = Calendar.getInstance(); 
		rightNow.setTime(new Date());  
		int year  = rightNow.get(Calendar.YEAR);
		DateFormat df = DateFormat.getDateInstance();  
		Holiday holiday = new Holiday();
		holidayDao.delete();
		//http://www.sse.com.cn/disclosure/dealinstruc/closed/
		/*String str = httpClientService.doGet("http://www.sse.com.cn/disclosure/dealinstruc/closed/");
		System.out.println(str);*/
		Document doc = Jsoup.connect("http://www.sse.com.cn/disclosure/dealinstruc/closed/").get();
		/*doc = Jsoup.connect("http://blog.csdn.net/roy_70")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                .get();*/
		
	/*	Document doc = Jsoup.connect("http://blog.csdn.net/roy_70")
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .post();*/
		
		Elements trs = doc.getElementsByClass("table").select("tr");  ;
		 for (int i = 0; i < trs.size(); ++i) {  
		        // 获取一个tr  
		        Element tr = trs.get(i);  
		        // 获取该行的所有td节点  
		        Elements tds = tr.select("td");  
		        // 选择某一个td节点  
		        for (int j = 0; j < tds.size(); ++j) {  
		            Element td = tds.get(j);  
		           if(td.text().contains("节")||td.text().contains("旦")){
		        	// System.out.println(td.text().substring(0, td.text().length()-1));
		        	   String holidayName = td.text().substring(0, td.text().length()-1);
		        	   holiday.setHolidayName(holidayName);
		        	  // System.out.println(holidayName);
		           }else{
		        	   if(td.text().contains("至")){
		        		  String from =  td.text().split("至")[0].substring(0,td.text().split("至")[0].indexOf("日")+1);
		        		  String end =  td.text().split("至")[1].substring(0, td.text().split("至")[1].indexOf("休市"));
		        		  
		        		  String begin_month = from.substring(0,from.indexOf("月"));
		        		  String begin_day = from.substring(from.indexOf("月")+1,from.indexOf("日"));
		        		  
		        		  String end_month = end.substring(0,end.indexOf("月"));
		        		  String end_day = end.substring(end.indexOf("月")+1,end.indexOf("日"));
		        		  
		        		  if(begin_month.length()<2){
		        			  begin_month ="0"+begin_month;
		        		  }
		        		  if(begin_day.length()<2){
		        			  begin_day ="0"+begin_day;
		        		  }
		        		  
		        		  if(end_month.length()<2){
		        			  end_month ="0"+end_month;
		        		  }
		        		  if(end_day.length()<2){
		        			  end_day ="0"+end_day;
		        		  }
		        		  
		        		  String earyDateStr = year+"-"+begin_month+"-"+begin_day;
		        		  String lateDateStr  = year+"-"+end_month+"-"+end_day;
		        		 
		        		  Date  earlydate = df.parse(earyDateStr);   
		        		  Date  latedate = df.parse(lateDateStr);   
		        		  int getDays = daysBetween(earlydate,latedate)+1;
		        		  //System.out.println(getDays + "       " + earlydate +"  "+ latedate);
		        		  for (int k = 0; k < getDays; k++) {
		        			  //将开始日期加上日期相差之后的时间,用于获取相差日期天数之后的日期
		        			  Date date3 = new Date(earlydate.getTime() + k * 24 * 60 * 60 * 1000);
		        			  holiday.setHoliday(date3);
			        		  holidayDao.saveHoliday(holiday);
						  }
		        		 
		        	   }else{
		        		  // System.out.println(td.text().substring(0,4));
		        		   String dataStr = td.text().substring(0,4);
		        		   String begin_month = dataStr.substring(0,dataStr.indexOf("月"));
			        	   String begin_day = dataStr.substring(dataStr.indexOf("月")+1,dataStr.indexOf("日"));
			        		  
			        		  if(begin_month.length()<2){
			        			  begin_month ="0"+begin_month;
			        		  }
			        		  if(begin_day.length()<2){
			        			  begin_day ="0"+begin_day;
			        		  }
			        		  String dateStr = year+"-"+begin_month+"-"+begin_day;
			        		  //System.out.println(dateStr);
			        		  Date  date = df.parse(dateStr);  
			        		  holiday.setHoliday(date);
			        		  holidayDao.saveHoliday(holiday);
		        	   }
		           }
		           
		        }  
		 }
	}
	
	
	
	
	
	
	
	
	@SuppressWarnings({ "unchecked","rawtypes" })
	//@Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0/20 * 9,10,11,13,14 ? * MON-FRI")
	public void job01() throws JsonParseException, JsonMappingException, IOException {
		//Map param = new HashMap();
		/*
		 * param.put("code", "000983"); param.put("pstart", 0);
		 * param.put("psize", 10);
		 */
		//param.put("marketType", null);

		boolean flag = this.getIsBegin();
		boolean isHoliday = this.getIsHoliday();
		if (!isHoliday) {
			if (!flag) {

				List<Stock> list = stockDao.selectStockByParam(null);
				List<Map<String, String>> list2 = pingan.getStockInfoByBatch(list);
				for (Map map : list2) {
					//logger.info(map);
					Stock stock = new Stock();
					stock.setName((String) map.get("stockName"));
					stock.setCode((String) map.get("code"));
					stock.setCurrent_price((String) map.get("newPrice"));
					stock.setMarketType((String) map.get("codeType"));
					stock.setMaxprice((String) map.get("maxPrice"));
					stock.setMinprice((String) map.get("minPrice"));
					stock.setPrevclose((String) map.get("prevClose"));
					stock.setOpen_price((String) map.get("open"));
					stock.setRisePrice((String) map.get("risePrice"));
					
					//dr xd 除权除红的没有加入
					
					if(stock.getName().contains("Ａ")){
						PinyinUtilsPro pro = new PinyinUtilsPro();
						pro.convertChineseToPinyin(stock.getName().substring(0,stock.getName().indexOf("Ａ")));
						String headP = pro.getHeadPinyin();
						stock.setStockPinYin(headP);
					}else if(!stock.getName().contains("Ａ")&&stock.getName().contains("ST")){
						PinyinUtilsPro pro = new PinyinUtilsPro();
						pro.convertChineseToPinyin(stock.getName().substring(stock.getName().indexOf("ST")+1, stock.getName().length()));
						String headP = pro.getHeadPinyin();
						stock.setStockPinYin(headP);
					}else{
						//System.out.println("====" +sts.getName());
						PinyinUtilsPro pro = new PinyinUtilsPro();
						pro.convertChineseToPinyin(stock.getName());
						String headP = pro.getHeadPinyin();
						stock.setStockPinYin(headP);
					}
					
					stockDao.save(stock);

					// 保存每日成交明细
					StockDayDealsRecord recorde = new StockDayDealsRecord();
					recorde.setName((String) map.get("stockName"));
					recorde.setCode((String) map.get("code"));
					recorde.setPrice((String) map.get("newPrice"));
					recorde.setMarketType((String) map.get("codeType"));
					if(recorde.getName().contains("Ａ")){
						PinyinUtilsPro pro = new PinyinUtilsPro();
						pro.convertChineseToPinyin(recorde.getName().substring(0,recorde.getName().indexOf("Ａ")));
						String headP = pro.getHeadPinyin();
						recorde.setStockPinYin(headP);
					}else if(!recorde.getName().contains("Ａ")&&recorde.getName().contains("ST")){
						PinyinUtilsPro pro = new PinyinUtilsPro();
						pro.convertChineseToPinyin(recorde.getName().substring(recorde.getName().indexOf("ST")+1, recorde.getName().length()));
						String headP = pro.getHeadPinyin();
						recorde.setStockPinYin(headP);
					}else{
						//System.out.println("====" +sts.getName());
						PinyinUtilsPro pro = new PinyinUtilsPro();
						pro.convertChineseToPinyin(recorde.getName());
						String headP = pro.getHeadPinyin();
						recorde.setStockPinYin(headP);
					}
					
					stockDealDao.save(recorde);
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Scheduled(cron = "0 30 20 ? * MON-FRI")
	public void job02() throws JsonParseException, JsonMappingException, IOException {
		Map param = new HashMap();
		param.put("marketType", null);
		boolean isHoliday = this.getIsHoliday();
		if (!isHoliday) {
			List<Stock> list = stockDao.selectStockByParam(null);
			List<Map<String, String>> list2 = pingan.getStockInfoByBatch(list);
			for (Map map : list2) {
				logger.info(map);
				StockDayDetail stockDayDetail = new StockDayDetail();
				stockDayDetail.setCode((String) map.get("code"));
				stockDayDetail.setName((String) map.get("stockName"));
				stockDayDetail.setCloseprice((String) map.get("newPrice"));
				stockDayDetail.setMarketType((String) map.get("codeType"));
				stockDayDetail.setMaxprice((String) map.get("maxPrice"));
				stockDayDetail.setMinprice((String) map.get("minPrice"));
				stockDayDetail.setPrevclose((String) map.get("prevClose"));
				stockDayDetail.setOpen_price((String) map.get("open"));
				stockDayDetail.setRisePrice((String) map.get("risePrice"));
				if(stockDayDetail.getName().contains("Ａ")){
					PinyinUtilsPro pro = new PinyinUtilsPro();
					pro.convertChineseToPinyin(stockDayDetail.getName().substring(0,stockDayDetail.getName().indexOf("Ａ")));
					String headP = pro.getHeadPinyin();
					stockDayDetail.setStockPinYin(headP);
				}else if(!stockDayDetail.getName().contains("Ａ")&&stockDayDetail.getName().contains("ST")){
					PinyinUtilsPro pro = new PinyinUtilsPro();
					pro.convertChineseToPinyin(stockDayDetail.getName().substring(stockDayDetail.getName().indexOf("ST")+1, stockDayDetail.getName().length()));
					String headP = pro.getHeadPinyin();
					stockDayDetail.setStockPinYin(headP);
				}else{
					//System.out.println("====" +sts.getName());
					PinyinUtilsPro pro = new PinyinUtilsPro();
					pro.convertChineseToPinyin(stockDayDetail.getName());
					String headP = pro.getHeadPinyin();
					stockDayDetail.setStockPinYin(headP);
				}
				stockDayDetailDao.save(stockDayDetail);
			}
		}
	}
	
	
	
	//https://blog.csdn.net/qq_28268507/article/details/74316065 原生JDK方式，以下是spring 方式
	@Scheduled(fixedDelay=6*1000*2)
	public void job03(){
		
		Map emailMap = new HashMap();
		emailMap.put("isSender", 1);
		List<Email> elist = emailDao.selectAll(emailMap);
		StringBuffer emailBf = new StringBuffer();
		for (int i = 0; i < elist.size(); i++) {
     		if(i<elist.size()-1){
     			emailBf.append(elist.get(i).getEmailAddress()+";");
     		}else{
     			emailBf.append(elist.get(i).getEmailAddress());
     		}
		}
		
		String toEmail = emailBf.toString();
		
		StringBuffer bf = new StringBuffer();
		bf.append("<table style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'><tbody>");
		bf.append("<tr><td  style='background:url(https://rescdn.qqmail.com/zh_CN/htmledition/images/xinzhi/bg/a_03.jpg) no-repeat #fbf7f4; min-height:550px; padding: 150px 80px 200px;'>");
		StringBuffer contents = new StringBuffer();
	
		
		Map map = new HashMap();
		map.put("orderType", 1);
		List<Stock> list = stockDao.getStockInfo(map);
		
		map.put("orderType", 0);
		List<Stock> list1 = stockDao.getStockInfo(map);
		map.put("orderType", -1);
		List<Stock> list2 = stockDao.getStockInfo(map);
		
		contents.append("<table class='tbl1' style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		contents.append("<tr><td colspan='6' style='text-align:center;;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#C1D9F3'>监控宝</td></tr>");
		contents.append("<tr><td colspan='6' style='text-align:center;;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#F2F2F2'>自选当天涨</td></tr>");
		contents.append("<tr><td style='text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>序号</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>代码</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>名称</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>当前价</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>昨收</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>涨跌幅</td></tr>");
		
		for (int i = 0; i < list.size(); i++) {
			if(i%2==0){
				contents.append("<tr><td style='text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+i+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+list.get(i).getCode()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+list.get(i).getName()+"</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+list.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+list.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+list.get(i).getRisePrice()+"</td></tr>");
			}else{
				contents.append("<tr><td style='text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+i+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+list.get(i).getCode()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+list.get(i).getName()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+list.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+list.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+list.get(i).getRisePrice()+"</td></tr>");
			}
		}
		contents.append("</table>");
		
		contents.append("<table class='tbl2' style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		contents.append("<tr><td colspan='6' style='text-align:center;;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#F2F2F2;color:#ccc'>自选当天稳定</td></tr>");
		contents.append("<tr><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>序号</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>代码</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>名称</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>当前价</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>昨收</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>涨跌幅</td></tr>");
		for (int i = 0; i < list1.size(); i++) {
			if(i%2==0){
				contents.append("<tr><td style='text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;'>"+i+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;'>"+list1.get(i).getCode()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;'>"+list1.get(i).getName()+"</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;'>"+list1.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;'>"+list1.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>"+list1.get(i).getRisePrice()+"</td></tr>");
			}else{
				contents.append("<tr><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;'>"+i+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;'>"+list1.get(i).getCode()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;'>"+list1.get(i).getName()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;'>"+list1.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;'>"+list1.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>"+list1.get(i).getRisePrice()+"</td></tr>");
			}
		}
		contents.append("</table>");
		
		contents.append("<table class='tbl3' style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		contents.append("<tr><td colspan='6' style='text-align:center;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#F2F2F2;color:green'>自选当天跌</td></tr>");
		contents.append("<tr><td style='text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>序号</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>代码</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>名称</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>当前价</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>昨收</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>涨跌幅</td></tr>");
		for (int i = 0; i < list2.size(); i++) {
			if(i%2==0){
				contents.append("<tr><td style='text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+i+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getCode()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getName()+"</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getRisePrice()+"</td></tr>");
			}else{
				contents.append("<tr><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+i+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getCode()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getName()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getRisePrice()+"</td></tr>");
			}
		}
		contents.append("</table>");
		
		
		bf.append("<div>"+contents.toString()+"</div>");//内容div
		bf.append("</td></tr></tbody></table>");
		logger.debug( bf.toString());
		emailImp.sendingEmail(toEmail,"2分钟提醒  【 "+new Date().toLocaleString()+" 】", bf.toString());
	}
	
	
	
	
	
	@Scheduled(cron = "0 30 20 ? * MON-FRI")
	public void jobDay(){
		
		Map emailMap = new HashMap();
		emailMap.put("isSender", 1);
		List<Email> elist = emailDao.selectAll(emailMap);
		StringBuffer emailBf = new StringBuffer();
		for (int i = 0; i < elist.size(); i++) {
     		if(i<elist.size()-1){
     			emailBf.append(elist.get(i).getEmailAddress()+";");
     		}else{
     			emailBf.append(elist.get(i).getEmailAddress());
     		}
		}
		
	
		String toEmail = emailBf.toString();
		
		
		StringBuffer bf = new StringBuffer();
		bf.append("<table style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'><tbody>");
		bf.append("<tr><td  style='background:url(https://rescdn.qqmail.com/zh_CN/htmledition/images/xinzhi/bg/a_03.jpg) no-repeat #fbf7f4; min-height:550px; padding: 150px 80px 200px;'>");
		StringBuffer contents = new StringBuffer();
	
		
		Map map = new HashMap();
		map.put("orderType", 1);
		List<Stock> list = stockDao.getStockInfo(map);
		
		map.put("orderType", 0);
		List<Stock> list1 = stockDao.getStockInfo(map);
		map.put("orderType", -1);
		List<Stock> list2 = stockDao.getStockInfo(map);
		
		contents.append("<table class='tbl1' style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		contents.append("<tr><td colspan='6' style='text-align:center;;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#C1D9F3'>监控宝</td></tr>");
		contents.append("<tr><td colspan='6' style='text-align:center;;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#F2F2F2'>自选当天涨</td></tr>");
		contents.append("<tr><td style='text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>序号</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>代码</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>名称</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>当前价</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>昨收</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>涨跌幅</td></tr>");
		
		for (int i = 0; i < list.size(); i++) {
			if(i%2==0){
				contents.append("<tr><td style='text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+i+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+list.get(i).getCode()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+list.get(i).getName()+"</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+list.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+list.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:red'>"+list.get(i).getRisePrice()+"</td></tr>");
			}else{
				contents.append("<tr><td style='text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+i+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+list.get(i).getCode()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+list.get(i).getName()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+list.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+list.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:red'>"+list.get(i).getRisePrice()+"</td></tr>");
			}
		}
		contents.append("</table>");
		
		contents.append("<table class='tbl2' style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		contents.append("<tr><td colspan='6' style='text-align:center;;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#F2F2F2;color:#ccc'>自选当天稳定</td></tr>");
		contents.append("<tr><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>序号</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>代码</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>名称</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>当前价</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>昨收</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>涨跌幅</td></tr>");
		for (int i = 0; i < list1.size(); i++) {
			if(i%2==0){
				contents.append("<tr><td style='text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;'>"+i+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;'>"+list1.get(i).getCode()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;'>"+list1.get(i).getName()+"</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;'>"+list1.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;'>"+list1.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>"+list1.get(i).getRisePrice()+"</td></tr>");
			}else{
				contents.append("<tr><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;'>"+i+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;'>"+list1.get(i).getCode()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;'>"+list1.get(i).getName()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;'>"+list1.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;'>"+list1.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>"+list1.get(i).getRisePrice()+"</td></tr>");
			}
		}
		contents.append("</table>");
		
		contents.append("<table class='tbl3' style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		contents.append("<tr><td colspan='6' style='text-align:center;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#F2F2F2;color:green'>自选当天跌</td></tr>");
		contents.append("<tr><td style='text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>序号</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>代码</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>名称</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>当前价</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>昨收</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>涨跌幅</td></tr>");
		for (int i = 0; i < list2.size(); i++) {
			if(i%2==0){
				contents.append("<tr><td style='text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+i+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getCode()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getName()+"</td><td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getRisePrice()+"</td></tr>");
			}else{
				contents.append("<tr><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+i+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getCode()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getName()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getCurrent_price()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getPrevclose()+"</td><td style=';text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:green'>"+list2.get(i).getRisePrice()+"</td></tr>");
			}
		}
		contents.append("</table>");
		
		
		bf.append("<div>"+contents.toString()+"</div>");//内容div
		bf.append("</td></tr></tbody></table>");
		emailImp.sendingEmail(toEmail,"日报提醒", bf.toString());
	}
	
	
	
	//急压和急拉的
	//@Scheduled(fixedRate = 3000)
	public void job04() {
		boolean flag = this.getIsBegin();
		boolean isHoliday = this.getIsHoliday();
		if (!isHoliday) {
			if (!flag) {
				List<Stock> list = stockDao.selectStockByParam(null);
				// System.out.println(list);
				for (int i = 0; i < list.size(); i++) {
					Stock sto = list.get(i);
					Double cprice = Double.valueOf(sto.getCurrent_price());
					Double prevPrice = Double.valueOf(sto.getPrevclose());
					if(cprice - prevPrice>0){
						
					}
				}
			}
		}
	}
	
}
