package com.ht.http.web;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.JobTask;
import com.ht.http.utils.PingAnHttp;
import com.ht.pojo.Stock;

//https://m.stock.pingan.com/html/h5security/quote/zxg.html
//单个  https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.6314712335455435&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=300193&codeType=4621&type=shsz
//批量  https://m.stock.pingan.com/h5quote/quote/getRealTimeDatas?random=0.9797709232149874&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&codes=000983%2C002552&codeTypes=4609%2C4614

@Component
public class PingAn {
	private static Logger logger = Logger.getLogger(PingAn.class);
	@Autowired
	private PingAnHttp pingAnHttp;
	
	
	
	/*
	1、深圳创业板股票的代码是：300XXX 的股票    4621
	2、而深圳中小板股票的代码是：002XXX 开头的股票  4614
	3、上海主板的股票代码是：60XXXX 开头的股票  4353
	4、深圳主板的股票代码是：000XXX 开头的股票  4609
	600开头的股票是上证A股，其中6006开头的股票是最早上市的股票，6016开头的股票为大盘蓝筹股；
	000开头的股票是上证A股，001、002开头的股票也都属于深证A股，其中002开头的股票是深证A股中小企业股票；
	200开头的股票是深证B股；
	300开头的股票是创业板股票；
	400开头的股票是三板市场股票。
	*/

/*update stock set marketType='4621' where `code` like '300%';
update stock set marketType='4614' where `code` like '002%';
update stock set marketType='4353' where `code` like '60%';
update stock set marketType='4609' where `code` like '000%';*/
	
	//单个
	public Map getInfo(Stock stock) throws JsonParseException, JsonMappingException, IOException{
		 DecimalFormat df = new DecimalFormat( "0.00000000000000000");
         BigDecimal decimal=new BigDecimal(Math.random());
         String random = df.format(decimal);
         /*String marketType = "";
 		if(stock.getCode().indexOf("6")==0){
     		marketType = "4353";
		}else{
			marketType = "4609";
		}
 		
 		String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random="+random+"&stockCode="+stock.getCode()+"&codeType="+marketType+"&type=shsz";*/
        String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random="+random+"&stockCode="+stock.getCode()+"&codeType="+stock.getMarketType()+"&type=shsz";
 		String result = pingAnHttp.httpGet(url);
		ObjectMapper mapper = new ObjectMapper();  
		Map map1 =mapper.readValue(result, Map.class);
		Map map2 = (Map) map1.get("results");
		pingAnHttp.httpGet(url);
		return null;
	}
	
	//自选多只
	@SuppressWarnings({ "rawtypes"})
	public List getStockInfoByBatch(List<Stock> stocks) throws JsonParseException, JsonMappingException, IOException{
		 DecimalFormat df = new DecimalFormat( "0.00000000000000000");
         BigDecimal decimal=new BigDecimal(Math.random());
        // System.out.println(df.format(decimal));
         String random = df.format(decimal);
		String marketType = "";
		//上海603848   4353
		//深圳 000983  4609
		//1、深圳创业板股票的代码是：300XXX 的股票    4621
		//2、而深圳中小板股票的代码是：002XXX 开头的股票  4614
		//3、上海主板的股票代码是：60XXXX 开头的股票  4353
		//4、深圳主板的股票代码是：000XXX 开头的股票  4609
		//001开头的股票一共有3只：宗申动力（001696）、豫能控股（001896）、招商蛇口（001979） 001开头出现的原因是因为新旧码的问题 ===> 归类于 4609
		
		
		//String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.08151615015075153&stockCode=603848&codeType=4353&type=shsz";
		//url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.08151615015075153&stockCode=000983&codeType=4609&type=shsz";
     /*	if(stock.getCode().indexOf("6")==0){
     		marketType = "4353";
		}else{
			marketType = "4609";
		}*/
		//String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random="+random+"&stockCode="+stock.getCode()+"&codeType="+marketType+"&type=shsz";
     	//https://m.stock.pingan.com/h5quote/quote/getRealTimeDatas?random=0.08988796250354825&codes=000983%2C002558%2C002550%2C603991&codeTypes=4609%2C4614%2C4614%2C4353
     	//https://m.stock.pingan.com/h5quote/quote/getRealTimeDatas?random=0.6720446696426674&tokenId=&chnl=&requestId=&codes=000983%2C002558%2C002550%2C603991%2C300587&codeTypes=4609%2C4614%2C4614%2C4353%2C4621
     	StringBuffer stockCodes  = new StringBuffer();
     	StringBuffer stockMarketType  = new StringBuffer();
     	for (int i = 0; i < stocks.size(); i++) {
     		if(i<stocks.size()-1){
     			stockCodes.append(stocks.get(i).getCode()+",");
     		}else{
     			stockCodes.append(stocks.get(i).getCode());
     		}
		}
     	
    	for (int i = 0; i < stocks.size(); i++) {
     		if(i<stocks.size()-1){
     			stockMarketType.append(stocks.get(i).getMarketType()+",");
     		}else{
     			stockMarketType.append(stocks.get(i).getMarketType());
     		}
		}
     	
     	//String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeDatas?random="+random+"&&codes=000983%2C002552&codeTypes=4609%2C4614";
     	String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeDatas?random="+random+"&&codes="+ URLEncoder.encode(stockCodes.toString(),"UTF-8")+"&codeTypes="+URLEncoder.encode(stockMarketType.toString(),"UTF-8");
		String result = null;
		logger.info("\n请求的URL开始");
		logger.info("URL===>" +url);
		logger.info("\n请求的URL结束");
		result = pingAnHttp.httpGet(url);
		ObjectMapper mapper = new ObjectMapper();  
		Map map1 =mapper.readValue(result, Map.class);
		List list = (List) map1.get("results");
		return list;
	}
	
	//大盘指数
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getDaPanZhiShu() throws JsonParseException, JsonMappingException, IOException{
		 DecimalFormat df = new DecimalFormat( "0.00000000000000000");
         BigDecimal decimal=new BigDecimal(Math.random());
         String random = df.format(decimal);
     	//https://m.stock.pingan.com/h5quote/quote/getIndexData?random=0.2779566904218944&marketType=shsz
		String url = "https://m.stock.pingan.com/h5quote/quote/getIndexData?random="+random+"&marketType=shsz";
		String result = null;
		result = pingAnHttp.httpGet(url);
		ObjectMapper mapper = new ObjectMapper();  
		Map map1 = mapper.readValue(result, Map.class);
		ArrayList array = (ArrayList) map1.get("results");
		
		/*JSONArray json  =  JSON.parseArray(jsonObj.getString("results"));
		String stockName = json.getJSONObject(0).getString("stockName");
		String rise = json.getJSONObject(0).getString("rise");
		String newPrice = json.getJSONObject(0).getString("newPrice");
		System.out.println(stockName + "===>  "   + rise + "  ==> "  +newPrice);
		
		String stockName1 = json.getJSONObject(1).getString("stockName");
		String rise1 = json.getJSONObject(1).getString("rise");
		String newPrice1 = json.getJSONObject(1).getString("newPrice");
		System.out.println(stockName1 + ""   + rise1 + "  ==> "  +newPrice1);
		
		String stockName2 = json.getJSONObject(2).getString("stockName");
		String rise2 = json.getJSONObject(2).getString("rise");
		String newPrice2 = json.getJSONObject(2).getString("newPrice");
		System.out.println(stockName2 + ""   + rise2 + "  ==> "  +newPrice2);
		
		System.out.println(json.get(0));
		System.out.println(json.get(1));
		System.out.println(json.get(2));
		Map map = new HashMap();*/
		return array;
	}
}
