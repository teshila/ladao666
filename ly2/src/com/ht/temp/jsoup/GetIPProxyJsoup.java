package com.ht.temp.jsoup;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

//https://www.kuaidaili.com/free/intr/
//https://www.cnblogs.com/Lxiaojiang/p/6236913.html
//输出html===> https://blog.csdn.net/u012325167/article/details/50969782/

//http://dacoolbaby.iteye.com/blog/2036955
//jsoup 换IP  https://blog.csdn.net/tianjia1872954551/article/details/52196607?locationNum=8&fps=1

//https://www.cnblogs.com/airsen/p/6135238.html
//https://www.cnblogs.com/beijingstruggle/p/4895626.html
//https://blog.csdn.net/eff666/article/details/53912466
//https://blog.csdn.net/roy_70/article/details/72453362   用法

public class GetIPProxyJsoup {
	
	
	private static Logger logger = Logger.getLogger(GetIPProxyJsoup.class);

	/*public void getIP() throws IOException {

//		Document doc = Jsoup.connect("http://www.xicidaili.com").data("query", "Java") // 请求参数
//				.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)") // 设置 User-Agent
//				.cookie("auth", "token") // 设置 cookie
//				.timeout(3000) // 设置连接超时时间
//				.post(); // 使用 POST 方法访问 URL
		
		 Document doc = Jsoup.connect("http://www.xicidaili.com").get();
		System.out.println(doc);
	}*/
	
	
/*	public void way_2() throws Exception{
	    File dest = new File("c://tem/t.html");
	    InputStream is;//接收字节输入流
	    FileOutputStream fos = new FileOutputStream(dest);//字节输出流

	    URL wangyi = new URL("http://www.163.com/");
	    is = wangyi.openStream();

	    BufferedInputStream bis = new BufferedInputStream(is);//为字节输入流加缓冲
	    BufferedOutputStream bos = new BufferedOutputStream(fos);//为字节输出流加缓冲

	    int length;

	    byte[] bytes = new byte[1024*20];
	    while((length = bis.read(bytes, 0, bytes.length)) != -1){
	        fos.write(bytes, 0, length);
	    }

	    bos.close();
	    fos.close();
	    bis.close();
	    is.close();
	}*/
	
	
	public  String httpGet(String url,String cookie) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect(url);
        //请求头设置，特别是cookie设置
        con.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"); 
        con.header("Content-Type", "application/x-www-form-urlencoded");
        con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36"); 
       // con.header("Cookie", cookie);
        //解析请求结果
        Document doc=con.get(); 
        //获取标题
        System.out.println(doc);
        logger.debug(doc);
        //返回内容
        return doc.toString();
    }
}
