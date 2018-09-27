package com.ht.temp.task.temp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ht.dao.StockDao;
import com.ht.dao.StockDayDetailDao;
import com.ht.http.utils.bak.PingAnWeb;
import com.ht.temp.jsoup.GetIPProxyJsoup;


public class Task {

	private static Logger logger = Logger.getLogger(Task.class);

	@Autowired
	private StockDao stockDao;
	
	
	@Autowired
	private PingAnWeb pingan;
	
	
	@Autowired
	private StockDayDetailDao stockDetailDao;
	
	
	@Autowired
	private GetIPProxyJsoup getIPProxyJsoup;
	
	@Autowired
	private IPTasker ipTasker;
	

/*	@Scheduled(cron = "0/1 * * * * ?")
	public void task01() {
		// System.out.println("111");
	}

	@Scheduled(fixedRate = 100)
	public void task002() {
		List list = stockDao.selectAllStock();
		//logger.debug("This is debug message.");

		Stock st = new Stock();
		stockDao.save(st);
		//System.out.println("111");
		
		
		UUID uuid = UUID.randomUUID();
    	//ForFile.createFile(uuid+"myfile", "我的梦说别停留等待,就让光芒折射泪湿的瞳孔,映出心中最想拥有的彩虹,带我奔向那片有你的天空,因为你是我的梦 我的梦"+uuid);
    	//MyUtils.delFolder("E:\\_1111\\");
    	
	}
	
	
	
	@Scheduled(fixedRate = 100)
	public void task003() {
		List list = stockDao.selectAllStock();
		logger.debug("This is debug message.");
		logger.debug("中文.");
		StockDetail st = new StockDetail();
		stockDetailDao.save(st);
	}*/
	
	/*
	@Scheduled(cron="0/2 * * * * ?")
	public void task201801() throws JsonParseException, JsonMappingException, IOException{
		Stock stock = new Stock();
		stock.setCode("000983");
		Map map = pingan.getStockInfo(stock);
		System.out.println(map);
		
		//getIPProxyJsoup.httpGet("http://www.xicidaili.com",null);
		String str = getIPProxyJsoup.httpGet("http://www.iqiyi.com/",null);
		
		File txt=new File("c:/t.html");
		if(!txt.exists()){
		txt.createNewFile();
		}
		byte bytes[]=new byte[512];
		bytes=str.getBytes(); //新加的
		int b=str.length(); //改
		FileOutputStream fos=new FileOutputStream(txt);
		fos.write(bytes,0,b);
		fos.close();
	}*/
	
	
	//@Scheduled(cron = "0 0/30 9-17 * * ?")
	/*@Scheduled(cron = "0/15 * * * * ?")
	public void task01() throws IOException {
		Document doc = ipTasker.getIP();
		Element element = doc.getElementById("#ip_list");
		Elements es = element.select("tr");
		for (Element tdelement : es) {
			Elements tdes = tdelement.select("td");
			for (int i = 0; i < tdes.size(); i++) {
				System.out.println(tdes.get(i).text());
				// System.out.println(tdes.get(i).text());
			}
		}

	}*/
}
