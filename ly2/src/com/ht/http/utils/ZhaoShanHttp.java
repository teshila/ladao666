package com.ht.http.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ht.temp.HttpClientService;

//https://blog.csdn.net/YouCanYouUp_/article/details/80769572 配置请求头部
//https://hq.cmschina.com/web/hq/views/hq/index.html
//https://hq.cmschina.com/market/json?funcno=20003&version=1&stock_list=SZ%253A000983&timeStamp=1534218100379
@Component
public class ZhaoShanHttp {
	public static Logger logger = LogManager.getLogger(HttpClientService.class);

	public void get() {
		CloseableHttpClient httpCilent2 = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) // 设置连接超时时间
				.setConnectionRequestTimeout(5000) // 设置请求超时时间
				.setSocketTimeout(5000).setRedirectsEnabled(true)// 默认允许自动重定向
				.build();
				 //.setProxy(new HttpHost("114.234.80.26", 9000)).build(); //配置代理

		HttpGet httpGet2 = new HttpGet("http://www.citicbank.com/");
		httpGet2.setConfig(requestConfig);
		
		
		
		httpGet2.addHeader("Accept", "text/html");
		httpGet2.addHeader("Accept-Charset", "utf-8");
		httpGet2.addHeader("Accept-Encoding", "gzip");
		httpGet2.addHeader("Cache-Control", "max-age=0");
		httpGet2.addHeader("Connection", "keep-alive");
		httpGet2.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpGet2.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");
        // 传输的类型
		httpGet2.addHeader("Content-Type", "application/x-www-form-urlencoded");
		
		
		
		//conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		/*conn.header("Accept-Encoding", "gzip, deflate, br");
		conn.header("Accept-Language", "zh-CN,zh;q=0.9");
		conn.header("Cache-Control", "max-age=0");
		conn.header("Connection", "keep-alive");
		conn.header("Host", "blog.maxleap.cn");
		conn.header("Upgrade-Insecure-Requests", "1");
		conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36");*/
		
		
		
		String srtResult = "";
		try {
			HttpResponse httpResponse = httpCilent2.execute(httpGet2);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				srtResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果，https://www.cnblogs.com/wbp0818/p/5458631.html可以配置请求响应编码
				System.out.println(srtResult);
			} else if (httpResponse.getStatusLine().getStatusCode() == 400) {
				// ..........
			} else if (httpResponse.getStatusLine().getStatusCode() == 500) {
				// .............
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpCilent2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
public void post(){
		
		//获取可关闭的 httpCilent
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
         
        HttpPost httpPost = new HttpPost("http://consentprt.dtac.co.th/webaoc/123SubscriberProcess");
        //设置超时时间
        httpPost.setConfig(requestConfig);
        //装配post请求参数
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(); 
        list.add(new BasicNameValuePair("age", "20"));  //请求参数
        list.add(new BasicNameValuePair("name", "zhangsan")); //请求参数
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8"); 
            //设置post求情参数
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String strResult = "";
            if(httpResponse != null){ 
                System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                   // strResult = "Error Response: " + response.getStatusLine().toString();
                } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                   // strResult = "Error Response: " + response.getStatusLine().toString();
                } else {
                  //  strResult = "Error Response: " + response.getStatusLine().toString();
                } 
            }else{
                 
            }
            System.out.println(strResult);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(httpClient != null){
                    httpClient.close(); //释放资源
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}
