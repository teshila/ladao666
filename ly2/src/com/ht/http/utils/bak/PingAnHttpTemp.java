package com.ht.http.utils.bak;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ht.temp.HttpClientService;

//https://blog.csdn.net/YouCanYouUp_/article/details/80769572 配置请求头部
//https://m.stock.pingan.com/html/h5security/quote/main.html
//https://hq.cmschina.com/web/hq/views/hq/index.html
//https://www.cnblogs.com/hugo-zhangzhen/p/6858013.html
//https://www.2cto.com/kf/201710/690275.html
public class PingAnHttpTemp {
	public static Logger logger = LogManager.getLogger(HttpClientService.class);

	
	public String doPost(String url, String params) {
	    String result = "";
	    CloseableHttpClient httpclient = null;
	    CloseableHttpResponse response = null;
	    if (httpclient == null) {
	        httpclient = HttpClients.createDefault();
	    }
	    try {
	        HttpPost httppost = new HttpPost(url);
	        httppost.setHeader("accept", "*/*");
	        httppost.setHeader("connection", "Keep-Alive");
	        httppost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
	 
	        if (params != null && !"".equals(params)) {
	            MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
	            reqEntity.setCharset(Charset.forName("utf-8"));
	            String[] nameAndValue = params.split("&");
	            for (String s : nameAndValue) {
	                String[] keyAndvalue = s.split("=",2);
	                //String[] keyAndvalue = s.split("=");
	                StringBody value = new StringBody("", ContentType.TEXT_PLAIN);
	                if(keyAndvalue.length == 2) {
	                    value = new StringBody(keyAndvalue[1], Charset.forName("utf-8"));
	                    //value = new StringBody(keyAndvalue[1],  ContentType.TEXT_PLAIN);
	                }
	                reqEntity.addPart(keyAndvalue[0], value);
	            }
	            HttpEntity httpEntity = reqEntity.build();
	            httppost.setEntity(httpEntity);
	        }
	        response = httpclient.execute(httppost);
	        HttpEntity resEntity = response.getEntity();
	        if(resEntity != null) {
	            result = EntityUtils.toString(resEntity, "utf-8");
	        }
	        EntityUtils.consume(resEntity);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (response != null) {
	                response.close();
	            }
	            // httpclient.close();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
	    }
	 
	    return result;
	}
	
	
	public  String httpGet(String url){
		String str = null;
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpClient httpclient = HttpClients.custom().build();  //为了给头部添加信息
		try{
			//创建httpget
			HttpGet httpget = new HttpGet(url);
			httpget.setHeader("accept", "*/*");
			httpget.setHeader("Connection", "keep-alive");  
			//httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");  
			//httpget.setHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"); 
			httpget.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B329 MicroMessenger/5.0.1"); 
			
			//httpget.setURI(new URI(httpget.getURI().toString())); //设置请求参数
			
			//执行get请求
			CloseableHttpResponse response = httpclient.execute(httpget);
			try{
				//获取响应实体
				HttpEntity entity = response.getEntity();
				//响应状态
				//System.out.println(response.getStatusLine());
				//log.log(Level.INFO,"HTTP请求状态 ===>>>>"+ response.getStatusLine()+",请求的地址 ====> " + url);
				if(entity != null){
					//内容长度
					//System.out.println("Response content length: " + entity.getContentLength());
					//响应内容
					//System.out.println("Response content: " + EntityUtils.toString(entity));
					str = EntityUtils.toString(entity);
					//System.out.println(str);
				}
			}finally{
				response.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				httpclient.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return str;
	}
	
	
	
	
	
	
	
	
	
	public void get() {
		CloseableHttpClient httpCilent2 = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000) // 设置连接超时时间
				.setConnectionRequestTimeout(1000) // 设置请求超时时间
				.setSocketTimeout(1000).setRedirectsEnabled(true)// 默认允许自动重定向
				.build();
				 //.setProxy(new HttpHost("114.234.80.26", 9000)).build(); //配置代理

		HttpGet httpGet2 = new HttpGet("https://www.baidu.com/");
		httpGet2.setConfig(requestConfig);
		
		
		
		httpGet2.addHeader("Accept", "text/html");
		//httpGet2.addHeader("Accept-Charset", "utf-8");
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
		
		
		CloseableHttpResponse  httpResponse = null;
		String srtResult = "";
		try {
			 httpResponse = httpCilent2.execute(httpGet2);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				srtResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果，https://www.cnblogs.com/wbp0818/p/5458631.html可以配置请求响应编码
				logger.debug(srtResult);
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
				httpResponse.close();
				httpGet2.releaseConnection();
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
