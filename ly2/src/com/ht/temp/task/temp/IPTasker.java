package com.ht.temp.task.temp;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class IPTasker {
	private static Logger logger = Logger.getLogger(IPTasker.class);

	public Document getIP() throws IOException {
		String url = "http://www.xicidaili.com/nn";

		// String[] r = ip.split(":");
		Connection con = Jsoup.connect(url);
		// 请求头设置，特别是cookie设置
		con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		con.header("Content-Type", "application/x-www-form-urlencoded");
		con.header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
		// con.header("Cookie", cookie);
		// 解析请求结果
		Document doc = con.get();
		
		
		
		// 获取标题
		//logger.debug(doc);
		// 返回内容
		return doc;
	}

}
