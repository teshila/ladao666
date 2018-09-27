package com.ht.http.utils.bak;

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
import org.springframework.stereotype.Component;

//https://www.jianshu.com/p/99c627c6aa9b
//https://www.cnblogs.com/hugo-zhangzhen/p/6858013.html
@Component
public class CopyOfPingAnHttp2 {

	public void httpGet() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet("http://www.iqiyi.com/");
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: " + entity.getContentLength());
					// 打印响应内容
					System.out.println("Response content: " + EntityUtils.toString(entity,"UTF-8"));
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
	}

	public static String httpPost(String host, int port, byte[] buf) throws IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		BufferedReader reader = null;
		StringBuffer response = new StringBuffer();
		try {

			String url = "http://" + host + ":" + port;
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();// 设置请求和传输超时时间
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("User-Agent", "Mozilla/5.0");

			ByteArrayEntity entity = new ByteArrayEntity(buf);
			httpPost.setEntity(entity);

			httpResponse = httpClient.execute(httpPost);

			reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

			String inputLine;

			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}

		} catch (Exception var) {
			var.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (httpResponse != null) {
				httpResponse.close();
			}
			httpClient.close();
		}
		return response.toString();

	}

}
