package com.ht.util.temp.fileUtil;

import java.util.UUID;

public class UUIDUtils {
	public static String getUUID32() {
		String uuid = UUID.randomUUID().toString().replace("-", "")
				.toLowerCase();
		return uuid;
		// return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
	
	
	public static void main(String[] args) {
		System.out.println(UUIDUtils.getUUID32());
	}
}
