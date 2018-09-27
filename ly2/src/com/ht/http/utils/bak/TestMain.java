package com.ht.http.utils.bak;

import java.util.HashMap;
import java.util.Map;

public class TestMain {
	// 发送get请求,拼接参数
	public static void testPost01() {
		String url = "https://192.168.1.101/login?username=xxx&password=xxxx";
		String result = HttpClientUtils.getContentFromUrl(url);
		System.out.println("get登录结果:" + result);
	}

	// 发送post请求,form表单参数
	public static void testPost02() {
		String url = "http://192.168.1.101/login";
		String charset = "utf-8";
		Map<String, String> loginMap = new HashMap<String, String>();
		loginMap.put("username", "*****");
		loginMap.put("password", "*****");
		String result = HttpClientUtils.doPost(url, loginMap, charset);
		System.out.println("post登录结果:" + result);
	}

	public static void main(String[] args) {
		testPost01();
		testPost02();
	}
}