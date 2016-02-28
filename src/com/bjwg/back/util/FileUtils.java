package com.bjwg.back.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 文件操作工具类
 * @author :Kim
 * @CreateDate : 2015-3-12 下午04:57:08 
 * @lastModified : 2015-3-12 下午04:57:08 
 * @version : 1.0
 * @jdk：1.6
 */
public class FileUtils {

	//文件最大大小限制
	public static final Integer FILE_MAX_SIZE = 1024 * 1024 * 10;
	//限制文件上传的后缀名
	public static final List<String> suffixList = Arrays.asList(".jpg",".png",".docx",".rar",".zip");
	
	
	/**
	 * 创建文件/文件夹
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static boolean createFile(File file) throws IOException {  
		
		if(!file.exists()) {  
        	
            makeDir(file.getParentFile());  
        }  
        
        if(file.getPath().contains(".")){
        	
        	return file.createNewFile();  
        }else{
        	
        	return file.mkdir();
        }
    }  
	
	/**
	 * 创建目录的上级文件夹
	 * @param dir
	 */
	public static void makeDir(File dir) {  
		
        if(!dir.getParentFile().exists()) {  
        	
            makeDir(dir.getParentFile());  
        }  
        dir.mkdir();  
    }  
	 
	 /** 
     * 生成随机文件名：当前年月日时分秒+五位随机数 
     *  
     * @return 
     */  
    public static String getRandomFileName() {  
  
        SimpleDateFormat simpleDateFormat;  
  
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");  
  
        Date date = new Date();  
  
        String str = simpleDateFormat.format(date);  
  
        Random random = new Random();  
  
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
  
        return  str + rannum ;// 当前时间  
    }  
	
	
	
	/**
	 * 删除文件，可以是文件或文件夹
	 * 
	 * @param fileName
	 *            要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("删除文件失败:" + fileName + "不存在！");
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = FileUtils.deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = FileUtils.deleteDirectory(files[i]
						.getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}
	/**
	  * 生成二维码文件
	  * @param url 访问地址
	  * @param imgPath 图片路径
	  * @param imgName 图片命名 含文件类型。如"1.png"、"1.jpg"
	  * @param suffix 后缀名，不含. 如"png"、"jpg"
	  */
	public static void createQRCodeFile(String url,String imgPath,String imgName,String suffix){
		try {
           
		     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		     
		     Map hints = new HashMap();
		     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		     BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE, 400, 400,hints);
		     File file1 = new File(imgPath,imgName);
		     FileUtils.createFile(file1);
		     MatrixToImageWriter.writeToFile(bitMatrix, suffix, file1);
		     
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
	}
}
