package com.ht.util.temp.fileUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class Down {
	public String downLoadZip() {
		String fileName = "test.zip";
		String path = "D:\\test\\test.zip";
		try {
			// HttpServletResponse response =
			// ServletActionContext.getResponse();
			HttpServletResponse response = null;
			File file = new File(path);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String(fileName.getBytes("ISO8859-1"), "UTF-8"));
			response.setContentLength((int) file.length());
			response.setContentType("application/zip");// 定义输出类型
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);
			byte[] b = new byte[1024];// 相当于我们的缓存
			long k = 0;// 该值用于计算当前实际下载了多少字节
			OutputStream myout = response.getOutputStream();// 从response对象中得到输出流,准备下载
			// 开始循环下载
			while (k < file.length()) {
				int j = buff.read(b, 0, 1024);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
			buff.close();
			file.delete();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
