package com.bjwg.back.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;




/**
 * 参数验证工具类
 * @author :Kim  
 * @CreateDate : 2015-8-6 下午03:27:22 
 * @lastModified : 2015-8-6 下午03:27:22 
 * @version : 1.0
 * @jdk：1.6
 */
public class ValidateUtil {

	/**
	 * 验证字符串
	 * @param s
	 * @param isCanNull 是否可为空
	 * @param minSize 最小长度
	 * @param MaxSize 最大长度
	 * @param obj 
	 * @param str 
	 * @param l 状态码
	 * @return
	 */
	public static boolean validateString(String s,boolean isCanNull,Integer minSize,Integer MaxSize) throws Exception{
		
		int status = 0;
		
		//字符为空
		if(!StringUtils.isNotEmpty(s)){
			
			//允许为空
			if(isCanNull){
				
				status = 1;
			}
		}else{
			
			status = 1;
			
			//小于
			if(MyUtils.isIntegerGtZero(minSize)){
				
				if(s.length() < minSize){
					
					status = 0;
				}
			}
			if(MyUtils.isIntegerGtZero(MaxSize)){
				
				if(s.length() > MaxSize){
					
					status = 0;
				}
			}
			
		}
		
		return status == 1;
	}
	/**
	 * 验证字符串
	 * @param s
	 * @param isCanNull 是否可为空
	 * @param minSize 最小长度
	 * @param MaxSize 最大长度
	 * @param obj 
	 * @param str 
	 * @param l 状态码
	 * @return
	 */
	public static boolean validateString(String s,boolean isCanNull,Integer minSize,Integer MaxSize,String[] str) throws Exception{
		
		int status = 0;
		
		String msg = null;
		
		String data = null;
		
		//字符为空
		if(!StringUtils.isNotEmpty(s)){
			
			//允许为空
			if(isCanNull){
				
				status = 1;
			}else{
				
				status = Integer.valueOf(str[0]);
				
				msg = str[1];
				
				data = str[2];
			}
		}else{
			
			status = 1;
			
			//小于
			if(MyUtils.isIntegerGtZero(minSize)){
				
				if(s.length() < minSize){
					
					status = Integer.valueOf(str[0]);
					
					msg = str[1];
					
					data = str[2];
				}
			}
			if(MyUtils.isIntegerGtZero(MaxSize)){
				
				if(s.length() > MaxSize){
					
					status = Integer.valueOf(str[0]);
					
					msg = str[1];
					
					data = str[2];
				}
			}
			
		}
		
		
		return status == 1;
	}
	/**
	 * 验证手机号
	 * @param s
	 * @param isCanNull 是否可为空
	 * @param minSize 最小长度
	 * @param MaxSize 最大长度
	 * @param obj 
	 * @param str 
	 * @param l 状态码
	 * @return
	 */
	public static boolean validateMoblieNumber(String s,boolean isCanNull,String[] str) throws Exception{
		
		int status = 0;
		
		String msg = null;
		
		String data = null;
		
		//为空
		if(!StringUtils.isNotEmpty(s)){
			
			if(isCanNull){
				
				status = 1;
			}else{
				
				status = Integer.valueOf(str[0]);
				
				msg = str[1];
				
				data = str[2];
			}
			
		}else if(!isNumeric(s) || s.length() > 13){
			
			status = Integer.valueOf(str[0]);
			
			msg = str[1];
			
			data = str[2];
		}else{
			
			status = 1;
		}
		
		
		return status == 1;
	}
	
	/**
	 * 验证座机
	 * @param s
	 * @param isCanNull 是否可为空
	 * @param minSize 最小长度
	 * @param MaxSize 最大长度
	 * @param obj 
	 * @param str 
	 * @param l 状态码
	 * @return
	 
	public static boolean validatePhoneNumber(String s,boolean isCanNull,JSONObject obj,String[] str) throws Exception{
		
		int status = 0;
		
		String msg = null;
		
		String data = null;
		
		//为空
		if(!StringUtils.isNotEmpty(s)){
			
			if(isCanNull){
				
				status = 1;
			}else{
				
				status = Integer.valueOf(str[0]);
				
				msg = str[1];
				
				data = str[2];
			}
			
		}else if(!isPhoneNumber(s)){
			
			status = Integer.valueOf(str[0]);
			
			msg = str[1];
			
			data = str[2];
		}else{
			
			status = 1;
		}
		
		obj.put("status", status);
		
		obj.put("msg", msg);
		
		obj.put("data", data);
		
		return status == 1;
	}*/
	
	/**
	 * 验证integer
	 * “必须有效且大于0小于2147483648”
	 * @param s
	 * @param isCanNull 是否可为空
	 * @param minSize 最小值
	 * @param MaxSize 最大值
	 * @return
	 */
	public static boolean validateInteger(String s,boolean isCanNull,Integer minSize,Integer MaxSize,String[] str) throws Exception{
		
		int status = 1;
		
		String msg = null;
		
		String data = null;
		
		//为空
		if(!StringUtils.isNotEmpty(s)){
			
			//允许为空
			if(!isCanNull){
				
				status = Integer.valueOf(str[0]);
				
				msg = str[1];
				
				data = str[2];
			}
			
		}else{
			
			
			//是数字
			if(MyUtils.isNumeric(s)){
				
				long i = Long.valueOf(s);
				
				if(MyUtils.isIntegerGtZero(minSize)){
					
					if(i < minSize){
						
						status = Integer.valueOf(str[0]);
						
						msg = str[1];
						
						data = str[2];
					}
				}
				if(MyUtils.isIntegerGtZero(MaxSize)){
					
					if(i > MaxSize){
						
						status = Integer.valueOf(str[0]);
						
						msg = str[1];
						
						data = str[2];
					}
				}else if(i <= 2147483647){
					
					status = 1;
					
				}
				
			}else{
				
				status = Integer.valueOf(str[0]);
				
				msg = str[1];
				
				data = str[2];
			}
			
		}
			
		
		return status == 1;
	}
	/**
	 * 验证integer
	 * “必须有效且大于0小于2147483648”
	 * @param s
	 * @param isCanNull 是否可为空
	 * @param minSize 最小值
	 * @param MaxSize 最大值
	 * @return
	 */
	public static boolean validateInteger(String s,boolean isCanNull,Integer minSize,Integer MaxSize) throws Exception{
		
		int status = 1;
		//为空
		if(!StringUtils.isNotEmpty(s)){
			//允许为空
			if(!isCanNull){
				status = 0;
			}
		}else{
			//是数字
			if(MyUtils.isNumeric(s)){
				int i = Integer.valueOf(s);
				if(MyUtils.isIntegerGtZero(minSize)){
					if(i < minSize.intValue()){
						status = 0;
					}
				}
				if(MyUtils.isIntegerGtZero(MaxSize)){
					if(i > MaxSize.intValue()){
						status = 0;
					}
				}
			}else{
				status = 0;
			}
		}
		return status == 1;
	}
	public static boolean validateLong(String s,boolean isCanNull,Long minSize,Long MaxSize) throws Exception{
		
		int status = 1;
		//为空
		if(!StringUtils.isNotEmpty(s)){
			//允许为空
			if(!isCanNull){
				status = 0;
			}
		}else{
			//是数字
			if(MyUtils.isNumeric(s)){
				int i = Integer.valueOf(s);
					if(i < minSize.longValue()){
						status = 0;
					}
					if(i > MaxSize.longValue()){
						status = 0;
					}
			}else{
				status = 0;
			}
		}
		return status == 1;
	}
	/**
	 * 验证double
	 * @param s
	 * @param isCanNull 是否可为空
	 * @param minSize 最小值
	 * @param MaxSize 最大值
	 * @return
	 */
	public static boolean validateDoublue(String s,boolean isCanNull,Float minSize,Float MaxSize) throws Exception{
		int status = 1;
		//为空
		if(!StringUtils.isNotEmpty(s)){
			//不允许为空
			if(!isCanNull){
				status = 0;
			}
		}else{
			//是整数或小数
			if(isDecimal2(s)){
				double i = Double.valueOf(s);
				if(minSize != null){
					if(i < minSize){
						status = 0;
					}
				}
				if(MaxSize != null){
					if(i > MaxSize){
						status = 0;
					}
				}
			}else{
				status = 0;
			}
		}
		return status == 1;
	}
	
	

	 /** 
    * 电话号码验证 
    *  
    * @param  str 
    * @return 验证通过返回true 
    */  
   public static boolean isPhone(String str) {   
       Pattern p1 = null,p2 = null;  
       Matcher m = null;  
       boolean b = false;    
       p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
       p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
       if(str.length() >9)  
       {   m = p1.matcher(str);  
           b = m.matches();    
       }else{  
           m = p2.matcher(str);  
           b = m.matches();   
       }    
       return b;  
   }  
	
	
	
   
   /** 
    * 验证用户名，支持中英文（包括全角字符）、数字、下划线和减号 （全角及汉字算两位）,长度为4-20位,中文按二位计数 
    *  
    * @param userName 
    * @return 
    */  
   public static boolean validateUserName(String userName) {  
       String validateStr = "^[\\w\\-－＿[０-９]\u4e00-\u9fa5\uFF21-\uFF3A\uFF41-\uFF5A]+$";  
       boolean rs = false;  
       rs = match(validateStr, userName);  
       if (rs) {  
           int strLenth = StringUtils.getStrLength(userName);  
           if (strLenth < 6 || strLenth > 45) {  
               rs = false;  
           }  
       }  
       return rs;  
   }
   
   
	
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	/**
	 * 判断是否为手机号	
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher m = p.matcher(mobiles);  
		return m.matches();  
	}  
	
	/**
	 * 判断字符串是否为座机
	 * @param str
	 * @return
	 */
	public static boolean isPhoneNumber(String orginal){  
		//"^[0][1-9]{2,3}-[0-9]{5,10}$"
		return match("^1\\d{10}$|^(0\\d{2,3}-?|\\(0\\d{2,3}\\))?[1-9]\\d{4,7}(-\\d{1,8})?$", orginal);  
	} 

	/**
	 * 验证邮箱格式
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * 验证IP地址
	 * @param ip
	 * @return
	 */
	public static boolean isIp(String ip){
		Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher matcher = pattern.matcher(ip); //以验证127.400.600.2为例
		return  matcher.matches();
	}
	/**
	 * 验证日期时间
	 * @param date
	 * @return
	 */
	public static boolean isDate(String date){
		Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher matcher = pattern.matcher(date);
		return  matcher.matches();
	}
	
	
	/**
	* 验证邮箱
	* 
	* @param 待验证的字符串
	* @return 如果是符合的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean isEmail2(String str) {
		String regex = "^([\\w-\\.]+)@((http://www.cnblogs.com/yaojian/admin/file://[[0-9]%7b1,3%7d//.[0-9]%7B1,3%7D//.[0-9]%7B1,3%7D//.)%7C(([//w-]+//.)+))([a-zA-Z]%7B2,4%7D%7C[0-9]%7B1,3%7D)(//]?)$";
		return match(regex, str);
	}

	/**
	* 验证IP地址
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean isIP(String str) {
		String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
		String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
		return match(regex, str);
	}
	/**
	* 验证网址Url
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsUrl(String str) {
		String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
		return match(regex, str);
	}
	/**
	* 验证电话号码
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsTelephone(String str) {
		String regex = "^(http://www.cnblogs.com/yaojian/admin/file://d%7b3,4%7d-)/?\\d{6,8}$";
		return match(regex, str);
	}
	/**
	* 验证输入密码条件(字符与数据同时出现)
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsPassword(String str) {
		String regex = "[A-Za-z]+[0-9]";
		return match(regex, str);
	}
	/**
	* 验证输入密码长度 (6-18位)
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsPasswLength(String str) {
		String regex = "^\\d{6,18}$";
		return match(regex, str);
	}
	/**
	* 验证输入邮政编号
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsPostalcode(String str) {
		String regex = "^\\d{6}$";
		return match(regex, str);
	}

	/**
	* 验证输入手机号码
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsHandset(String str) {
		String regex = "^[1]+[3,5]+\\d{9}$";
		return match(regex, str);
	}

	/**
	* 验证输入身份证号
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsIDcard(String str) {
		String regex = "(^\\d{18}$)|(^\\d{15}$)";
		return match(regex, str);
	}

	/**
	* 验证输入两位小数
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsDecimal(String str) {
		String regex = "^[0-9]+(.[0-9]{2})?$";
		return match(regex, str);
	}

	/**
	* 验证输入一年的12个月
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsMonth(String str) {
		String regex = "^(0?[[1-9]|1[0-2])$";
		return match(regex, str);
	}

	/**
	* 验证输入一个月的31天
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsDay(String str) {
		String regex = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
		return match(regex, str);
	}

	/**
	* 验证日期时间
	* 
	* @param 待验证的字符串
	* @return 如果是符合网址格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean isDate2(String str) {
	// 严格验证时间格式的(匹配[2002-01-31], [1997-04-30],
	// [2004-01-01])不匹配([2002-01-32], [2003-02-29], [04-01-01])
	// String regex =
	// "^((((19|20)(([02468][048])|([13579][26]))-02-29))|((20[0-9][0-9])|(19[0-9][0-9]))-((((0[1-9])|(1[0-2]))-((0[1-9])|(1\\d)|(2[0-8])))|((((0[13578])|(1[02]))-31)|(((01,3-9])|(1[0-2]))-(29|30)))))$";
	// 没加时间验证的YYYY-MM-DD
	// String regex =
	// "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
	// 加了时间验证的YYYY-MM-DD 00:00:00
		String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
		return match(regex, str);
	}

	/**
	* 验证数字输入
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsNumber(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	/**
	* 验证非零的正整数
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsIntNumber(String str) {
		String regex = "^\\+?[1-9][0-9]*$";
		return match(regex, str);
	}

	/**
	* 验证大写字母
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsUpChar(String str) {
		String regex = "^[A-Z]+$";
		return match(regex, str);
	}

	/**
	* 验证小写字母
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsLowChar(String str) {
		String regex = "^[a-z]+$";
		return match(regex, str);
	}

	/**
	* 验证输入字母
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsLetter(String str) {
		String regex = "^[A-Za-z]+$";
		return match(regex, str);
	}

	/**
	* 验证输入汉字
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsChinese(String str) {
		String regex = "^[\u4e00-\u9fa5],{0,}$";
		return match(regex, str);
	}

	/**
	* 验证输入字符串
	* 
	* @param 待验证的字符串
	* @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	*/
	public static boolean IsLength(String str) {
		String regex = "^.{8,}$";
		return match(regex, str);
	}

	/**
	* @param regex
	*            正则表达式字符串
	* @param str
	*            要匹配的字符串
	* @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	*/
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	
	
	/**
	 * 
	 * @param str
	 * @return  true 是小数或整数  
	 */
	public static boolean isDecimal(String str){  
		return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str).matches();  
	}  
	
	/**
	 * 判断字符串是否为整数或小数
	 * @param str
	 * @return
	 */
	public static boolean isDecimal2(String orginal){  
		
		return match("[0-9]\\d*\\.{0,1}\\d*", orginal);  
	}  
	
	
	
	
//	public static List<String[]> 
	
	/** 
	 * @param args 
	 */ 
	public static void main(String[] args) throws Exception{
		
		String[] s = new String[3];
		s[0] = "-1";
		s[1] = "1_msg";
		s[2] = "一";
		
//		System.out.println(validateString("400", true, null, null,new JSONObject(),s));
//		
//		System.out.println(validateString("12", false, 2, 3,new JSONObject(),s));
//		
//		System.out.println(validateInteger("2147sss47", true, null, null,new JSONObject(),s));
		
		
		String[][] m = new String[2][2];
		
		m[0][0] = "1";
		m[0][1] = "11";
		m[1][0] = "2";
		m[1][1] = "22";
		
		System.out.println(m.toString());
		
		System.out.println(Integer.valueOf("2147483647"));
		
		
		 System.out.println("123:"+isDecimal("123"));  
		  System.out.println("0.123:"+isDecimal("0.123"));  
		  System.out.println(".123:"+isDecimal(".123"));  
		  System.out.println("1.23:"+isDecimal("1.23"));  
		  System.out.println("123.:"+isDecimal("123."));  
		  System.out.println("00.123:"+isDecimal("00.123"));  
		  System.out.println("123.0:"+isDecimal("123.0"));  
		  System.out.println("123.00:"+isDecimal("123.00"));  
		  System.out.println("0123:"+isDecimal("0123"));  
		
		System.out.println(StringUtils.htmlEncode("<script>sdfsfs???</script>"));
		
//		System.out.println(isNumeric("dd"));  
//		
//		System.out.println(isLengthCom("admin3"));
//		
//		System.out.println(validateUserName("3w"));
//		
//		System.out.println(IsLength("飒飒是大大大滴"));
		
		System.out.println("sdfsdfsdfs-------"+StringUtils.isNotEmpty(null));
		
		int i = 0;
		System.out.println(++i);
		
		String str = "周一/17:00";
		System.out.println(str.split("/")[0]);
		
		
	}
	
}
