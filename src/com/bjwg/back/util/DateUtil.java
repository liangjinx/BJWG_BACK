package com.bjwg.back.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Kim
 * @version 创建时间：2015-4-1 下午08:01:45
 * Version: 1.0
 * jdk : 1.6
 * 类说明：日期的工具类
 */

public class DateUtil {
	
	 private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	
    /** 
     * 将时间字符串转换为Date类型 
     * @param dateStr 
     * @return Date 
     */  
    public static Date to24Date(String dateStr) {
    	if(!StringUtils.isNotEmpty(dateStr)){
    		return null;
    	}
        Date date = null;  
        try {  
            date = longSdf.parse(dateStr);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return date;  
    }
    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(Date beginTime,Date endTime)
    {
    	if(beginTime==null || endTime==null){
    		return -1;
    	}
        if(compareDate(beginTime,endTime)){
        	return -1;
        }
        long day = 0;
    	String beginDateStr = getStringDateShort(beginTime);
    	String endDateStr = getStringDateShort(endTime);
        java.util.Date beginDate;
        java.util.Date endDate;
        try{
            beginDate = sdf.parse(beginDateStr);
            endDate= sdf.parse(endDateStr);    
            day = (endDate.getTime() - beginDate.getTime())/(24*60*60*1000);    
            //System.out.println("相隔的天数="+day);   
        }catch (Exception e){
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }   
        return day;
    }
	
    /** 
     * 比较两个日期之间的大小 
     *  
     * @param d1 
     * @param d2 
     * @return 前者大于后者返回true 反之false 
     */  
    public static boolean compareDate(Date d1, Date d2) {  
        Calendar c1 = Calendar.getInstance();  
        Calendar c2 = Calendar.getInstance();  
        c1.setTime(d1);  
        c2.setTime(d2);  
      
        int result = c1.compareTo(c2);  
        if (result > 0)  
            return true;  
        else  
            return false;  
    }  
    /** 
     * 比较两个日期之间的大小 
     *  
     * @param d1 
     * @param d2 
     * @return 两个日期是否相等,相等返回true,不相等返回false
     */  
    public static boolean compareDateEquals(Date d1, Date d2) {  
        Calendar c1 = Calendar.getInstance();  
        Calendar c2 = Calendar.getInstance();  
        c1.setTime(d1);  
        c2.setTime(d2);  
      
        int result = c1.compareTo(c2);  
        if (result == 0)  
            return true;  
        else  
            return false;  
    } 
    
    /**
     * 判断第一个到第二个日期 是否 在第三个日期到第四个日期内
     * 是 返加true 不是 返回false
     * @param d1
     * @param d2
     * @param d3
     * @param d4
     * @return
     */
    public static boolean isInDates(Date d1,Date d2,Date d3,Date d4){
    	
    	if(compareDateEquals(d1,d3)){
    		return true;
    	}else if(compareDateEquals(d1,d4)){
    		return true;
    	}else if(compareDateEquals(d2,d3)){
    		return true;
    	}else if(compareDateEquals(d2,d4)){
    		return true;
    	}else if(d1.after(d3) && d1.before(d4)){
    		return true;
    	}else if(d2.after(d3) && d2.before(d4)){
    		return true;
    	}
    	return false;
    }
    /**
     * 判断当前时间是否 在第一个日期到第二个日期内
     * 是 返回true 不是 返回false
     * @param d1
     * @param d2
     * @return
     */
    public static boolean nowInDateEquals(Date now,Date d1, Date d2) {  
        if(compareDateEquals(d1,now)){
    		return true;
    	}else if(compareDateEquals(d2,now)){
    		return true;
    	}else if(now.after(d1) && now.before(d2)){
    		return true;
    	}
        return false;
    } 
    /**
     * 获取传入时间 向前 推day天，不包含时分秒
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {      
        Calendar now = Calendar.getInstance();      
        now.setTime(d);      
        now.set(Calendar.HOUR_OF_DAY, 0);
    	now.set(Calendar.MINUTE, 0);
    	now.set(Calendar.SECOND, 0);
    	now.set(Calendar.MILLISECOND, 0);   
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);      
        return now.getTime();      
    }  
    /**
     * 获取传入时间 向后 推day天，不包含时分秒
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {      
    	Calendar now = Calendar.getInstance();      
    	now.setTime(d);      
    	now.set(Calendar.HOUR_OF_DAY, 0);
    	now.set(Calendar.MINUTE, 0);
    	now.set(Calendar.SECOND, 0);
    	now.set(Calendar.MILLISECOND, 0);   
    	now.set(Calendar.DATE, now.get(Calendar.DATE) + day);      
    	return now.getTime();      
    }  
    /**
     * 获取传入时间 向前 推min分钟
     * @param d
     * @param min
     * @return
     */
    public static Date getMinBefore(Date d, int min) {      
        Calendar now = Calendar.getInstance();      
        now.setTime(d);      
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - min);      
        return now.getTime();      
    }  
    /**
     * 获取传入时间 向后 推min分钟
     * @param d
     * @param min
     * @return
     */
    public static Date getMinAfter(Date d, int min) {      
        Calendar now = Calendar.getInstance();      
        now.setTime(d);      
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + min);      
        return now.getTime();      
    }   
    // 获得当天0点时间
	public static Date getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获得当天24点时间
	public static Date getTimesnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return  cal.getTime();
	}

	// 获得本周一0点时间
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return  cal.getTime();
	}

	// 获得本周日24点时间
	public  static Date getTimesWeeknight() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 7);
		return cal.getTime();
	}

	// 获得本月第一天0点时间
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return  cal.getTime();
	}

	// 获得本月最后一天24点时间
	public static Date getTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTime();
	}

	/**
	   * @return 返回短时间字符串格式yyyy-MM-dd
	   */
	public static String getStringDateShort(Date date) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   String dateString = formatter.format(date);
	   return dateString;
	}
}
