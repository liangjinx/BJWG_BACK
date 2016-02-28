package com.bjwg.back.util;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


/**
 * 工具类
 * @author Administrator
 *
 */
public class MyUtils {

	
	/**
	 * 判断list是否为空
	 * @param list
	 * @return
	 */
	public static boolean isListEmpty(List<?> list){
		
		
		return list == null || list.size() == 0;
	}
	/**
	 * 判断map是否为空
	 * @param list
	 * @return
	 */
	public static boolean isMapEmpty(Map<?,?> map){
		
		
		return map == null || map.size() == 0;
	}
	/**
	 * 判断integer是否大于0
	 * @param i
	 * @return
	 */
	public static boolean isIntegerGtZero(Integer i){
		
		return i != null && i.intValue() > 0;
	}
	/**
	 * 判断Long是否大于0
	 * @param i
	 * @return
	 */
	public static boolean isLongGtZero(Long i){
		
		return i != null && i.longValue() > 0 && !(i.equals(""));
	}
	
	/**
	 * 获取当前时间，未格式化
	 * @return
	 */
	public static Date getCurrentDate(){
		
		return new Date();
	}
	
	/**
	 * 时间格式化
	 * @return
	 * @throws ParseException 
	 */
	public static Date dateFormat(String dateStr) throws ParseException{
		DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy",java.util.Locale.UK);
		return format.parse(dateStr);
	}
	
	/**
	 * 格式化时间
	 * @param mode 格式
	 * @return
	 */
	public static Date dateFormat(String data ,int mode) throws Exception{
		
		String format = "yyyy-MM-dd HH:mm:ss";
		switch (mode) {
		case 1:
			
			format = "yyyy/MM/dd HH:mm:ss";
			break;
		case 2:
			
			format = "yyyy.MM.dd HH:mm:ss";
			break;
		case 3:
			
			format = "yyyy年MM月dd日 HH:mm:ss";
			break;
			
		default:
			
			break;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.parse(data);
	}
	/**
	 * 格式化时间
	 * @param mode 格式
	 * @return
	 */
	public static String dateFormat2(Date data ,int mode) throws Exception{
		
		String format = "yyyy-MM-dd HH:mm:ss";
		switch (mode) {
		case 1:
			
			format = "yyyy/MM/dd HH:mm:ss";
			break;
		case 2:
			
			format = "yyyy.MM.dd HH:mm:ss";
			break;
		case 3:
			
			format = "yyyy年MM月dd日 HH:mm:ss";
			break;
			
		case 4:
			format = "yyyy-MM-dd";
			
		default:
			
			break;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.format(data);
	}
	
	/**
	 * 格式化时间 yyyy-MM
	 * 
	 * @param mode
	 *            格式
	 * @return
	 */
	public static Date dateFormat5(String dataStr, int mode) throws Exception {

		String format = "yyyy-MM";
		switch (mode) {
		case 1:

			format = "yyyy/MM";
			break;
		case 2:

			format = "yyyy.MM";
			break;
		case 3:

			format = "yyyy年MM月";
			break;

		default:

			break;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.parse(dataStr);
	}
	
	public static String dateFormat3(Date data, int mode) throws Exception {

		String format = "yyyy-MM-dd HH:mm";
		switch (mode) {
		case 1:

			format = "yyyy/MM/dd HH:mm";
			break;
		case 2:

			format = "yyyy.MM.dd HH:mm";
			break;
		case 3:

			format = "yyyy年MM月dd日 HH:mm";
			break;

		default:

			break;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(data);
	}
	
	public static String dateFormat6(Date data, int mode) throws Exception {

		String format = "HH:mm";
		switch (mode) {
		case 1:

			format = "HH/mm";
			break;
		case 2:

			format = "HH.mm";
			break;
		case 3:

			format = "HH时mm分";
			break;

		default:

			break;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(data);
	}
	/**
	 * 格式化时间 格式：昨天 HH:mm、前天 HH:mm、日期(yyyy-MM-dd)
	 * 
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public static Date dateFormat4(String dateStr, int mode) throws Exception {

		String format = "yyyy-MM-dd";
		switch (mode) {
		case 1:

			format = "yyyy/MM/dd";
			break;
		case 2:

			format = "yyyy.MM.dd";
			break;
		case 3:

			format = "yyyy年MM月dd日";
			break;

		default:

			break;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.parse(dateStr);
	}

	
	
	
	
	
	
	
	
	/**
	 * 格式化时间 yyyy-MM
	 * 
	 * @param mode
	 *            格式
	 * @return
	 */
	public static String dateFormat5(Date data, int mode) throws Exception {

		String format = "yyyy-MM";
		switch (mode) {
		case 1:

			format = "yyyy/MM";
			break;
		case 2:

			format = "yyyy.MM";
			break;
		case 3:

			format = "yyyy年MM月";
			break;

		default:

			break;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(data);
	}
	
	
	/**
	 * 格式化时间    格式：n（分钟/小时/天）前
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String dateFormat3(Date date) throws Exception
	{
		Date now = new Date();
		long minute = 60;
		long hour = 3600;
		long day = 3600*24;
		long x = (now.getTime()-date.getTime())/1000;
		if(x<60)
		{
			return "1分钟前";
		}
		else if(x>minute && x<hour)
		{
			return (int)x/minute+"分钟前";
		}else if(x>hour && x<day)
		{
			return x/hour+"小时前";
		}else if(x>day && x<(day*10))
		{
			return x/day+"天前";
		}else
		{
			return dateFormat2(date, 4);
		}
	}
	
	/**
	 * 获取当前时间yyyy-MM-dd HH:mm:ss
	 * @param mode 格式
	 * @return
	 */
	public static String getYYYYMMDDHHmmss(int mode){
		
		String format = "yyyy-MM-dd HH:mm:ss";
		switch (mode) {
		case 1:
			
			format = "yyyy/MM/dd HH:mm:ss";
			break;
		case 2:
			
			format = "yyyy.MM.dd HH:mm:ss";
			break;
		case 3:
			
			format = "yyyy年MM月dd日 HH:mm:ss";
			break;

		default:
			
			break;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.format(new Date());
	}
	
	/**
	 * 获取当前时间yyyy-MM-dd
	 * @param mode 格式
	 * @return
	 */
	public static String getYYYYMMDD(int mode){
		
		String format = "yyyy-MM-dd";
		switch (mode) {
		case 1:
			
			format = "yyyy/MM/dd";
			break;
		case 2:
			
			format = "yyyy.MM.dd";
			break;
		case 3:
			
			format = "yyyy年MM月dd日";
			break;
		case 4:
			
			format = "yyyyMMdd";
			break;
			
		default:
			
			break;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		return dateFormat.format(new Date());
	}
	
	/**
	 * 获取当前时间yyyy-MM
	 * 
	 * @param mode
	 *            格式
	 * @return
	 */
	public static String getYYYYMM(int mode) {

		String format = "yyyy-MM";
		switch (mode) {
		case 1:

			format = "yyyy/MM";
			break;
		case 2:

			format = "yyyy.MM";
			break;
		case 3:

			format = "yyyy年MM月";
			break;
		case 4:

			format = "yyyyMM";
			break;

		default:

			break;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(new Date());
	}
	
	
	/**
	 * 时间相加
	 * @param date
	 * @param hours 小时
	 * @return
	 */
	public static Date addDate(Date date ,int hours){
		
		return new Date(date.getTime() + hours * 60 * 60 * 1000);
		
	}
	/**
	 * 时间相加
	 * @param date
	 * @param minitues 分钟
	 * @return
	 */
	public static Date addDate2(Date date ,int minitues){
		
		return new Date(date.getTime() + minitues  * 60 * 1000);
		
	}
	/**
	 * 时间相加
	 * @param date
	 * @param day 天
	 * @return
	 * @throws ParseException 
	 */
	public static Date addDate3(Date date ,int day) throws ParseException{
		
		long l = 24 * 60 * 60 * 1000;
		
		l = l * day;
		
		return new Date(date.getTime() + l);
		
	}
	
	/**
	 * 获取随机数
	 * @param len 最大十位数
	 * @return 
	 */
	public static int random(int len){
		
		Random rdm = new Random(len);
        int intRd = Math.abs(rdm.nextInt());
        return intRd;
	}
	
	/**
	 * 将字符串封装成List<String>
	 * @param s 字符串是以^符号进行连接的
	 * @return 
	 */
	public static List<String> convertToList(String s){
		
		String[] s1 = s.split("\\^");
		
		List<String> list = new ArrayList<String>();
		
		for(int i = 0 ; i < s1.length ; i+=3){
			
			list.add(s1[i]);
		}
		
		return list;
	}
	
	
	/**
	 * 将字符串封装成List<String[]>
	 * @param s 字符串是以^符号进行连接的
	 * @return 
	 */
	public static List<String[]> convertToList2(String s){
		
		String[] s1 = s.split("\\^");
		
		List<String[]> list = new ArrayList<String[]>();
		
		String[] s2 = null;
		
		for(int i = 0 ; i < s1.length ; i+=3){
			
			s2 = new String[3];
			
			s2[0] = s1[i];
			s2[1] = s1[i+1];
			s2[2] = s1[i+2];
			
			list.add(s2);
		}
		
		return list;
	}
	
	public static boolean isNumeric(String str) {
        int begin = 0;
        boolean once = true;
        if (str == null || str.trim().equals("")) {
            return false;
        }
        str = str.trim();
        if (str.startsWith("+") || str.startsWith("-")) {
            if (str.length() == 1) {
                // "+" "-"
                return false;
            }
            begin = 1;
        }
        for (int i = begin; i < str.length(); i++) {
	        if (!Character.isDigit(str.charAt(i))) {
	            if (str.charAt(i) == '.' && once) {
	                // '.' can only once
	                once = false;
	            }
	            else {
	                return false;
	            }
	        }
        }
        if (str.length() == (begin + 1) && !once) {
            // "." "+." "-."
            return false;
        }
        return true;
    }

	
	
	
	/**
	 * 正则表达式匹配
	 * @param regex
	 * @param orginal
	 * @return
	 */
	public static boolean isMatch1(String regex, String orginal){ 
		
        if (orginal == null || orginal.trim().equals("")) {  
        	
            return false;  
        }  
        
        Pattern pattern = Pattern.compile(regex);  
        
        Matcher isNum = pattern.matcher(orginal);  
        
        return isNum.matches();  
    }  
	
	/**  
     * ip地址转成整数.  
     * @param ip  
     * @return  
     */  
    public static long ip2long(String ip) {   
        String[] ips = ip.split("[.]");   
        long num =  16777216L*Long.parseLong(ips[0]) + 65536L*Long.parseLong(ips[1]) + 256*Long.parseLong(ips[2]) + Long.parseLong(ips[3]);   
        return num;   
    }   
       
    /**  
     * 整数转成ip地址.  
     * @param ipLong  
     * @return  
     */  
    public static String long2ip(long ipLong) {   
        //long ipLong = 1037591503;   
        long mask[] = {0x000000FF,0x0000FF00,0x00FF0000,0xFF000000};   
        long num = 0;   
        StringBuffer ipInfo = new StringBuffer();   
        for(int i=0;i<4;i++){   
            num = (ipLong & mask[i])>>(i*8);   
            if(i>0) ipInfo.insert(0,".");   
            ipInfo.insert(0,Long.toString(num,10));   
        }   
        return ipInfo.toString();   
    }  
    
    /**
     * 计算分钟
     * @param s
     * @return
     */
    public static Integer calcMinute(String s){
        
        String[] t = s.split(":");
        
        return Integer.valueOf(t[0]) * 60 + Integer.valueOf(t[1]);
    }
    /**
     * 计算分钟
     * @param s 分钟数
     * @return
     */
    public static String calcMinute(Integer s){
        
        int t = s % 60;
        
        return s/60 + ":" + ((t < 10) ? "0" + t : t);
    }
    /**
     * 计算分钟
     * @param s 分钟数
     * @return
     */
    public static String calcMinute2(Integer s){
    	
    	int t = s % 60;
    	
    	int n = s/60;
    	
    	String str = "上午";
    	
    	if(n == 12){
    		
    		str = "中午";
    		
    	}else if(n > 12){
    		
    		str = "下午";
    	}else if(n < 3){
    		
    		str = "凌晨";
    	}
    	
    	str = "";
    	
    	return str + n + ":" + ((t < 10) ? "0" + t : t);
    }
    /** 
	 * 计算地球上任意两点(经纬度)距离 
	 *  
	 * @param long1 
	 *            第一点经度 
	 * @param lat1 
	 *            第一点纬度 
	 * @param long2 
	 *            第二点经度 
	 * @param lat2 
	 *            第二点纬度 
	 * @return 返回距离 单位：米 
	 */  
	public static double Distance(double long1, double lat1, double long2,  
	        double lat2) {  
	    double a, b, R;  
	    R = 6378137; // 地球半径  
	    lat1 = lat1 * Math.PI / 180.0;  
	    lat2 = lat2 * Math.PI / 180.0;  
	    a = lat1 - lat2;  
	    b = (long1 - long2) * Math.PI / 180.0;  
	    double d;  
	    double sa2, sb2;  
	    sa2 = Math.sin(a / 2.0);  
	    sb2 = Math.sin(b / 2.0);  
	    d = 2* R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
	                    * Math.cos(lat2) * sb2 * sb2));  
	    return d;  
	}  
	
	
	/**
	 * 获取百度地图的经纬度
	 * @param address
	 * @return
	 * @throws Exception
	 */
/*	public static String getBaiduMapResult(String address) throws Exception{
		
		HttpClient client = new HttpClient();
    	
    	client.setConnectionTimeout(6*1000);

    	String url = ToolKit.getInstance().getSingleConfig("baidu_map_interface") + "?address="+URLEncoder.encode(address)+"&output=json&ak="+ToolKit.getInstance().getSingleConfig("baidu_map_ak");
    	//"http://api.map.baidu.com/geocoder/v2/?address="+URLEncoder.encode(address)+"&output=json&ak=DqTGazNxpb6tujm41W2ULrtb";
    	GetMethod getMethod = new GetMethod(url);
    	
		int statusCode = client.executeMethod(getMethod);
			
		if (statusCode == HttpStatus.SC_OK) {
			
			String sms = getMethod.getResponseBodyAsString();
			
			System.out.println("result:" + sms);
			
			return sms;
		}
		
		return null;
	}*/
	
	/**
	 * 解析百度地图返回的经纬度数据
	 * @param sms
	 * @return
	 * @throws Exception
	 */
/*	public static Double[] parseJson(String sms) throws Exception{
		
		net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(sms);
		
		if(object.getString("status").equals("0")){
			
			object = object.getJSONObject("result").getJSONObject("location");
			
			System.out.println(object.getDouble("lng") + "-->" + object.getDouble("lat"));
			
			Double[] d = new Double[]{object.getDouble("lng"),object.getDouble("lat")}; 
			
			return d;
		}
		
		return null;
	}*/
	
	
	/**
     * 格式化距离显示
     * @param d
     * @return
     */
    public static String formatDistance(Double d){
        
    	int k = d.intValue() / 1000;
    	
    	int l = d.intValue() % 1000;
    	
    	if(k < 1){
    		
    		k = d.intValue();
    		
    		if(k<300)
    			return "<300m";
    		
    		return k + "m";
    	}else{
    		
    		l = l / 100;
    		
    		return k + "." + l +"km";
    	}
    	
    }

    /**
     * 返回图片地址，没有则返回默认图片地址
     * @param d
     * @return
     */
    public static String getLocalHttpUrl(String s,String webroot,Integer categoryId,boolean flag){
    	
    	if(StringUtils.isNotEmpty(s)){
    		
    		if(s.startsWith("http://") || s.startsWith("https://")){
    			
    			return s;
    		}else{
    			
    			return webroot + s;
    		}
    		
    	}else if(flag){
    		
    		return webroot + "resources/default-img/index"+categoryId+".png";
    	}
    	
    	return "";
    }
    /**
     * 返回图片地址，没有则返回默认图片地址
     * @param d
     * @return
     */
    public static String getLocalHttpUrl(String s,String webroot,String webroot2,Integer source,Integer categoryId,boolean flag){
    	
    	String path = "";

    	
    	if(source != null && source < 3){
    		
    		path = webroot2;
    	}else{
    		
    		path = webroot;

    	}
    	
    	if(StringUtils.isNotEmpty(s)){
    		
    		if(s.startsWith("http://") || s.startsWith("https://")){
    			
    			return s;
    		}else{
    			
    			return path + s;
    		}
    		
    	}else if(flag){
    		
    		return webroot + "resources/default-img/index"+categoryId+".png";
    	}
    	
    	return "";
    }
    
    /**
     * 返回图片http访问地址
     * @param s
     * @return
     */
    public static String getImageHttpUrl(String s, String param)
    {
    	if(StringUtils.isEmpty(s))
    		return "";
    	
    	if(s.startsWith("http://") || s.startsWith("https://"))
    		return s;
    	
    	return ToolKit.getInstance().getSingleConfig("imgDomain") + s + (param == null ? "" : param);
    }
    
    /**
     * 判断店家logo是否存在,没有返回默认logo
     * @param url
     * @param webroot
     * @param categoryId
     * @return
     */
    /*public static String getDefaultShopLogo(String url,String webroot, Integer categoryId)
    {
    	if(StringUtils.isEmptyNo(url))
    	{
    		return webroot + "resources/default-img/index" + categoryId + ".png";
    	}
    	else {
    		//压缩logo
			url += ImageCutConstant.SHOP_LOGO;
			//店铺中的是相对路径，此处拼装绝对路径
			url = ToolKit.getInstance().getSingleConfig("imgDomain") + url;
		}
    	return url;
    }*/
    
	
    /**
     * 比较两个时间之间相差多少天
     * @param date1 较小的时间(null则默认为当前时间)
     * @param date2 较大的时间(null则默认为当前时间)
     * @return
     * @throws ParseException
     */
    public static int compareDate(Date date1,Date date2) throws ParseException
    {  
    	date1 = date1 == null ? new Date() : date1;
    	date2 = date2 == null ? new Date() : date2;
    	
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date1 = sdf.parse(sdf.format(date1));
        date2 = sdf.parse(sdf.format(date2));
        
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date1);
        long time1 = calendar.getTimeInMillis();
        calendar.setTime(date2);
        long time2 = calendar.getTimeInMillis();
        
        return Integer.parseInt(String.valueOf((time2-time1)/(1000*3600*24)));

    }
    
    
    /**
     * 转换服务范围
     * @param s 
     * @return
     */
    public static Integer convertArea(String s){
        
        if("500M".equals(s.toUpperCase()) || "500M以内".equals(s.toUpperCase())){
            
            return 1;
        }else if("1KM".equals(s.toUpperCase()) || "1KM以内".equals(s.toUpperCase())){
            
            return 2;
        }else if("3KM".equals(s) || "3KM以内".equals(s.toUpperCase())){
            
            return 3;
        }else if("5KM".equals(s) || "5KM以内".equals(s.toUpperCase())){
            
            return 4;
        }else if("全城".equals(s) || "全程以内".equals(s.toUpperCase())){
            
            return 5;
        }else if("不限".equals(s) || "不限以内".equals(s.toUpperCase())){
            
            return 6;
        }
        return 0;
    }
    /**
     * 转换服务范围
     * @param s 
     * @return
     */
    public static String convertArea(Integer i){
        
        switch (i)
        {
            case 1:
                
                return "500M";
            case 2:
                
                return "1KM";
            case 3:
                
                return "3KM";
            case 4:
                
                return "5KM";
            case 5:
                
                return "全城";
            case 6:
                
                return "不限";

            default:
            break;
        }
        
        return "";
    }
	
    public static Integer[] toIntegers(String[] array)
    {
    	Integer[] integers = new Integer[array.length];
    	for(int i=0; i<array.length; i++)
    	{
    		integers[i] = Integer.valueOf(array[i]);
    	}
    	return integers;
    }
    
	public static List<Long> splitToLongList(String s, String splitChar){
	    
	    String[] s1 = s.split(splitChar);
	    
	    List<Long> list = new ArrayList<Long>();
	    
	    for(int i = 0 ; i < s1.length ; i++){
	        
	        list.add(Long.valueOf(s1[i]));
	    }
	    
	    return list;
	}
    
	/**
	 * 将字符串封装成List<Long>
	 * @param s 
	 * @param splitChar 连接符
	 * @return 
	 */
	public static List<Long> convertToLongList(String s, String splitChar){
	    
	    String[] s1 = s.split(splitChar);
	    
	    List<Long> list = new ArrayList<Long>();
	    
	    for(int i = 0 ; i < s1.length ; i++){
	        
	        list.add(Long.valueOf(s1[i]));
	    }
	    return list;
	}
	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {   
        String ipAddress = null;   
        //ipAddress = this.getRequest().getRemoteAddr();   
        ipAddress = request.getHeader("x-forwarded-for");   
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
         ipAddress = request.getHeader("Proxy-Client-IP");   
        }   
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
            ipAddress = request.getHeader("WL-Proxy-Client-IP");   
        }   
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
         ipAddress = request.getRemoteAddr();   
         if(ipAddress.equals("127.0.0.1")){   
          //根据网卡取本机配置的IP   
          InetAddress inet=null;   
       try {   
        inet = InetAddress.getLocalHost();   
       } catch (Exception e) {   
        e.printStackTrace();   
       }   
       ipAddress= inet.getHostAddress();   
         }   
      }   
     
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
            if(ipAddress.indexOf(",")>0){   
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
            }   
        }   
        return ipAddress;    
     }   
	
	
	
	
	
	
	
	
	
	
	
	public static long calcDays(Date date1, Date date2) {

		// 当前时间处理
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		// 给定时间处理
		Calendar setCal = Calendar.getInstance();
		setCal.setTime(date2);
		setCal.set(Calendar.HOUR_OF_DAY, 0);
		setCal.set(Calendar.MINUTE, 0);
		setCal.set(Calendar.SECOND, 0);
		setCal.set(Calendar.MILLISECOND, 0);

		long dayDiff = (setCal.getTimeInMillis() - cal.getTimeInMillis())
				/ (1000 * 60 * 60 * 24);
//		ConsoleUtil.println(dayDiff);
		return dayDiff;
	}
	public static String addDateL(Date date, int day) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

}














