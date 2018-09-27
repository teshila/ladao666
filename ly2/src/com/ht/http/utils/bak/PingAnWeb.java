package com.ht.http.utils.bak;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.pojo.Stock;
//单个  https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.6314712335455435&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&stockCode=300193&codeType=4621&type=shsz
//批量  https://m.stock.pingan.com/h5quote/quote/getRealTimeDatas?random=0.9797709232149874&thirdAccount=&rdm=&timestamp=&tokenId=&signature=&key=&chnl=&requestId=&codes=000983%2C002552&codeTypes=4609%2C4614
import com.ht.temp.YePingAnHttpUtil;

//@Component
public class PingAnWeb {
	
	
	@Autowired
	private YePingAnHttpUtil pingAnHttpUtil;
	
	
	@SuppressWarnings({ "rawtypes"})
	public Map getStockInfo(Stock stock) throws JsonParseException, JsonMappingException, IOException{
		 DecimalFormat df = new DecimalFormat( "0.00000000000000000");
         BigDecimal decimal=new BigDecimal(Math.random());
        // System.out.println(df.format(decimal));
         String random = df.format(decimal);
		String marketType = "";
		//上海603848   4353
		//深圳 000983  4609
		//String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.08151615015075153&stockCode=603848&codeType=4353&type=shsz";
		//url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random=0.08151615015075153&stockCode=000983&codeType=4609&type=shsz";
     	if(stock.getCode().indexOf("6")==0){
     		marketType = "4353";
		}else{
			marketType = "4609";
		}
		String url = "https://m.stock.pingan.com/h5quote/quote/getRealTimeData?random="+random+"&stockCode="+stock.getCode()+"&codeType="+marketType+"&type=shsz";
		String result = null;
		result = pingAnHttpUtil.httpGet(url);
		ObjectMapper mapper = new ObjectMapper();  
		Map map1 =mapper.readValue(result, Map.class);
		Map map2 = (Map) map1.get("results");
		return map2;
	}
	
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getDaPanZhiShu() throws JsonParseException, JsonMappingException, IOException{
		 DecimalFormat df = new DecimalFormat( "0.00000000000000000");
         BigDecimal decimal=new BigDecimal(Math.random());
         String random = df.format(decimal);
     	//https://m.stock.pingan.com/h5quote/quote/getIndexData?random=0.2779566904218944&marketType=shsz
		String url = "https://m.stock.pingan.com/h5quote/quote/getIndexData?random="+random+"&marketType=shsz";
		String result = null;
		result = pingAnHttpUtil.httpGet(url);
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
