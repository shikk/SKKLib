/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */
package com.lonever.skklib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.os.Environment;
import android.os.StatFs;

/**
 * 文件处理工具类
 * 
 * @author Gary Fu
 */
public class FileUtils {

	/**
	 * 将文件大小转为友好的格式，比如 122K / 22M
	 * 
	 * @param fileSize
	 *            要转换的文件大小
	 * @return 按格式转好的字符串
	 */
	public static String getFileSizeReadable(long fileSize) {
		if (fileSize < 1024L) {
			return "" + fileSize;
		} else if (fileSize >= 1024L && fileSize < 1024L * 1024L) {
			return ((long) ((double) fileSize / (double) 1024)) + "K";
		} else if (fileSize >= 1024L * 1024L
				&& fileSize < 1024L * 1024L * 1024L) {
			double r = (double) fileSize / (double) (1024 * 1024);
			double temp1 = r - (long) r;
			long temp2 = (long) (temp1 * 100);
			String temp3 = ((long) r + temp2 * 0.01) + "";
			String result = temp3;
			if (temp3.length() - temp3.lastIndexOf(".") > 3)
				result = temp3.substring(0, temp3.lastIndexOf(".") + 3);
			return result + "M";
		} else if (fileSize >= 1024L * 1024L * 1024L
				&& fileSize < 1024L * 1024L * 1024L * 1024L) {
			double r = (double) fileSize / (double) (1024 * 1024 * 1024);
			double temp1 = r - (long) r;
			long temp2 = (long) (temp1 * 100);
			String temp3 = ((long) r + temp2 * 0.01) + "";
			String result = temp3;
			if (temp3.length() - temp3.lastIndexOf(".") > 3)
				result = temp3.substring(0, temp3.lastIndexOf(".") + 3);
			return result + "G";
		} else {
			return ((long) ((double) fileSize / (double) (1024 * 1024 * 1024 * 1024)))
					+ "T";
		}
	}

	/**
	 * 从字符串文件名中获得文件后缀名
	 * 
	 * @param fileName
	 *            文件名
	 * @return 后缀名，不含"."
	 */
	public static String getFileSuffix(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return "";
		}

		int index = fileName.lastIndexOf(".");
		if (index >= 0) {
			return fileName.substring(index + 1);
		} else {
			return "";
		}
	}

	/**
	 * 判断是否已加载SD卡
	 * 
	 * @return true 有SD卡；false 没有SD卡
	 */
	public static boolean hasSd() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 计算手机剩余空间
	 * 
	 * @return 手机剩余空间
	 */
	public static long phoneStorageFree() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		return (long) stat.getAvailableBlocks() * stat.getBlockSize();
	}

	/**
	 * 计算手机总空间
	 * 
	 * @return 手机总空间
	 */
	public static long phoneStorageTotal() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		return (long) stat.getBlockCount() * stat.getBlockSize();
	}

	/**
	 * 计算手机已使用空间
	 * 
	 * @return 手机已使用空间
	 */
	public static long phoneStorageUsed() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		return (long) (stat.getBlockCount() - stat.getAvailableBlocks())
				* stat.getBlockSize();
	}

	/**
	 * 计算SD卡剩余空间
	 * 
	 * @return SD卡剩余空间
	 */
	public static long sdCardFree() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		return (long) stat.getAvailableBlocks() * stat.getBlockSize();
	}

	/**
	 * 计算SD卡总空间
	 * 
	 * @return SD卡总空间
	 */
	public static long sdCardTotal() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		return (long) stat.getBlockCount() * stat.getBlockSize();
	}

	/**
	 * 计算SD卡已使用空间
	 * 
	 * @return SD卡已使用空间
	 */
	public static long sdCardUsed() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		return (long) (stat.getBlockCount() - stat.getAvailableBlocks())
				* stat.getBlockSize();
	}

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean
	 */
	public static void newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();

		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹

		} catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();

		}

	}

	
	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path){
		delAllFile(path,new String[]{});
	}
	
	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path,String... exceptFileNames) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		ArrayList<String> exceptFileNameList = new ArrayList<String>();
		for (String string : exceptFileNames) {
			exceptFileNameList.add(string);
		}
		
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (exceptFileNameList.contains(temp.getName())) {
				continue;
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static boolean copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
			return true;
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static boolean copyFile(InputStream ios, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			if (ios != null) { // 文件存在时
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = ios.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				ios.close();
			}
			return true; 
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
		
		return false;

	}

	/**
	 * 复制整个文件夹内的文件以及文件夹  
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath){
		copyFolder(oldPath, newPath, new String[]{});
	}
	
	
	
	
	/**
	 * 复制整个文件夹内的文件以及文件夹
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @param exceptFileName
	 * 			  除外的文件名称
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath,String... exceptFileNames) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			
			ArrayList<String> exceptFileNameList = new ArrayList<String>();
			for (String string : exceptFileNames) {
				exceptFileNameList.add(string);
			}
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (exceptFileNameList.contains(temp.getName())) {
					continue;
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);

	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);

	}
}
