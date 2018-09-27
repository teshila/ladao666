package com.ht.util.temp.fileUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;


public class Snippet {
	public boolean createCardImgZip(String sourcePath, String zipName) {
			// TODO Auto-generated method stub
			boolean result = false;
			String zipPath = "D:/test/";
			File sourceFile = new File(sourcePath);
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			FileOutputStream fos = null;
			ZipOutputStream zos = null;
	 
			if (sourceFile.exists() == false) {
				System.out.println("File catalog:" + sourcePath + "not exist!");
			} else {
				try {
					if(!new File(zipPath).exists()){
						new File(zipPath).mkdirs();
					}
					File zipFile = new File(zipPath + "/" + zipName + ".zip");
					if (zipFile.exists()) {
						System.out.println(zipPath + "Catalog File: " + zipName + ".zip" + "pack file.");
					} else {
						File[] sourceFiles = sourceFile.listFiles();
						if (null == sourceFiles || sourceFiles.length < 1) {
							System.out.println("File Catalog:" + sourcePath + "nothing in there,don't hava to compress!");
						} else {
							fos = new FileOutputStream(zipFile);
							zos = new ZipOutputStream(new BufferedOutputStream(fos));
							byte[] bufs = new byte[1024 * 10];
							for (int i = 0; i < sourceFiles.length; i++) {
								// create .zip and put pictures in
								ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
								zos.putNextEntry(zipEntry);
								// read documents and put them in the zip
								fis = new FileInputStream(sourceFiles[i]);
								bis = new BufferedInputStream(fis, 1024 * 10);
								int read = 0;
								while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
									zos.write(bufs, 0, read);
								}
							}
							result = true;
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				} finally {
					try {
						if (null != bis)
							bis.close();
						if (null != zos)
							zos.close();
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
			}
			return result;
		}
	
}

