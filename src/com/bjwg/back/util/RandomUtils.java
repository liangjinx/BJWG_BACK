package com.bjwg.back.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Carter
 * @version 创建时间：2015-4-15 上午09:20:47
 * @Modified By:Administrator
 * Version: 1.0
 * jdk : 1.6
 * 类说明：随机数生成util
 */

public class RandomUtils
{
	private static String[] words = {
			"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y",
			"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y",
			"1","2","3","4","5","6","7","8","9","0"};
	
	/**
	 * 生成指定长度的随机数
	 * @param length
	 * @return
	 */
	public static String getNum(int length)
	{
		Random rm = new Random();
		int pross = (int)(rm.nextDouble() * Math.pow(10, length));
		return String.format("%0"+length+"d", pross);
	}
	
	public static String getNumAndWord(int length)
	{
		String result = "";
		for (int i = 0; i < length; i++)
		{
			Random rm = new Random();
			result += words[rm.nextInt(60)];
		}
		
		return result;
	}
	
	 /** 
     * 生成随机文件名：当前年月日+五位随机数 
     *  
     * @return 
     */  
    public static String getRandomFileName() {  
  
        SimpleDateFormat simpleDateFormat;  
  
        simpleDateFormat = new SimpleDateFormat("yyMMdd");  
  
        Date date = new Date();  
  
        String str = simpleDateFormat.format(date);  
  
        Random random = new Random();  
  
        int rannum = (int) (random.nextDouble() * (999999 - 100000 + 1)) + 100000;// 获取6位随机数  
  
        return  str + rannum ;// 当前时间  
    }  
    /** 
     * 生成编号：当前年月日分秒+五位随机数 
     *  
     * @return 
     */  
    public static String getOrderCode() {  
    	
    	SimpleDateFormat simpleDateFormat;  
    	
    	simpleDateFormat = new SimpleDateFormat("yyMMddmmss");  
    	
    	Date date = new Date();  
    	
    	String str = simpleDateFormat.format(date);  
    	
    	Random random = new Random();  
    	
    	int rannum = (int) (random.nextDouble() * (9999 - 1000 + 1)) + 1000;// 获取4位随机数  
    	
    	return  str + rannum ;// 当前时间  
    }  
    /** 
     * 生成编号：当前年月日分秒+五位随机数 
     *  
     * @return 
     */  
    public static String getCouponCode() {  
    	
    	SimpleDateFormat simpleDateFormat;  
    	
    	simpleDateFormat = new SimpleDateFormat("mmssSS");  
    	
    	Date date = new Date();  
    	
    	String str = simpleDateFormat.format(date);  
    	
    	str = getLetter(5) + str;
    	
    	return str;
    }  
    
    /**
     * 返回len位字母
     * @param len
     * @return
     */
    public static String getLetter(int len){
    	
    	//65 ~ 90(A ~ Z) || 97 ~ 122(a ~ z)
    	int r = 65;
    	
    	StringBuffer letter = new StringBuffer();
    	
    	for (int i = 0; i < len; i++) {
			
    		r = randomAgain();
    		
    		letter.append((char)r);
		}
    	
    	return letter.toString();
    }
    
    private static int randomAgain(){
    	
    	int r = (int)(58 * Math.random() + 65);
		
		if(r > 90 && r < 97){
			
			return randomAgain();
		}
		
		return r;
    }
	
    
	
	public static void main(String[] args)
	{
		System.err.println(getOrderCode());
		for (int i = 0; i < 100; i++) {
			System.err.println(RandomUtils.getCouponCode());
		}
	}
}
