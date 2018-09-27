package com.ht.temp.task.temp;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class MyUtils {

	public static String getUUID32() {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		return uuid;
		// return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	/***
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/***
	 * 删除文件夹
	 * 
	 * @param folderPath文件夹完整绝对路径
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createFile(String str) {

		String path = "E:\\___1111\\测试";// 所创建文件的路径

		File f = new File(path);

		if (!f.exists()) {

			f.mkdirs();// 创建目录
		}

		// String fileName = "abc.txt";//文件名及类型
		String fileName = str + ".exe";// 文件名及类型

		File file = new File(path, fileName);

		if (!file.exists()) {

			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {
		while (true) {

			String str = getUUID32();
			createFile(str);
			
			delAllFile("E:\\___1111\\测试");
		}
	}
}
