package com.ht.http.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

//https://m.stock.pingan.com/html/h5security/quote/zxg.html ---平安证券
//https://blog.csdn.net/shaochong047/article/details/79636142  ---添加请求头
//https://www.jianshu.com/p/99c627c6aa9b
//https://www.cnblogs.com/hugo-zhangzhen/p/6858013.html
@Component
public class PingAnHttp {
	private static Logger logger = Logger.getLogger(PingAnHttp.class);
	
	
	public String httpGet(String url) {
		
		 RequestConfig requestConfig = RequestConfig.custom()
	                .setConnectTimeout(5000)   //设置连接超时时间
	                .setConnectionRequestTimeout(5000) // 设置请求超时时间
	                .setSocketTimeout(5000)
	                .setRedirectsEnabled(true)//默认允许自动重定向
	                .build();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String srtResult = null;
		try {
			
			
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(requestConfig);
			httpget.setHeader("accept", "*/*");
			httpget.addHeader("Accept-Charset", "utf-8");
			httpget.setHeader("Connection", "keep-alive"); 
			httpget.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B329 MicroMessenger/5.0.1");
			
			httpget.addHeader("Accept-Encoding", "gzip");
			httpget.addHeader("Cache-Control", "max-age=0");
			httpget.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
	        // 传输的类型
			httpget.addHeader("Content-Type", "application/x-www-form-urlencoded");
			
			
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
			  /*	System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: " + entity.getContentLength());
					// 打印响应内容
					System.out.println("Response content: " + EntityUtils.toString(entity,"UTF-8"));
				}*/
				
				 if(response.getStatusLine().getStatusCode() == 200){
		                srtResult = EntityUtils.toString(response.getEntity());//获得返回的结果
		               // System.out.println(srtResult);
		            }else if(response.getStatusLine().getStatusCode() == 400){
		                //..........
		            }else if(response.getStatusLine().getStatusCode() == 500){
		                //.............
		            }
				
			} finally {
				if (null != response) response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return srtResult;
	}

}
