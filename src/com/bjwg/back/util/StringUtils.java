package com.bjwg.back.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bjwg.back.util.MyUtils;


/**
 * 字符串处理工具类
 * @author Administrator
 *
 */
public class StringUtils {

	
	/**
	 * 去空值
	 * @param s
	 * @reutrn string
	 */
	public static String getString(String s)
	{	
		return isNotEmpty(s) ? s : "";
	}
	
	/**
	 * 去空值
	 * @param s
	 * @return
	 */
	public static String getString(Object s)
	{	
		if(s == null)
			return "";
		
		return isNotEmpty(s.toString()) ? s.toString() : "";
	}
	
	/**
	 * 是否为空
	 * @param s
	 * @return  null false
	 */
	public static boolean isNotEmpty(String s){
		
		return s != null && !"".equals(s);
	}
	
	/**
	 * 所有的字符串是否为空
	 * 有一个为空，则返回false
	 * @param s
	 * @return
	 */
	public static boolean isAllNotEmpty(String... s){
		
		if(s != null){
			
			for(String v : s){
				
				if(!isNotEmpty(v)){
					
					return false;
				}
				
			}
			
		}
		
		return true;
	}
	
	/**
	 * 将字符串首字符转成大写
	 * 首字符不是字母则原样返回
	 * @param s
	 * @return String
	 * 如：abc -> Abc  测试->测试
	 */
	public static String firstToUpperCase(String s){
		
		if(isNotEmpty(s)){
			
			char f = s.charAt(0);
			
			if( (f <= 'z' && f >= 'a') ){
				
				s = (f+"").toUpperCase() + s.substring(1);
				
			}
			
		}
		
		return s;
	}
	
	/**
	 * 截取字符串
	 * @param s 字符串
	 * @param len 截取的长度
	 * @param startIndex 开始截取的下标
	 * @param replaceChar 截取下的字符串要替换的字符
	 * @return String
	 * 如："深圳市龙岗区布吉" , 3 , 0 ,"..." -> "深圳市"
	 * 	   "深圳市龙岗区布吉" , 6 , 0 ,"..." -> "深圳市龙岗..."
	 */
	public static String sub(String s,int len,int startIndex,String replaceChar){
		
		if(isNotEmpty(s)){
			
			if(startIndex < s.length()){
				
				if(len < s.length()){
					
					if(len + startIndex <= s.length()){
						
						s = s.substring(startIndex,startIndex + len) + replaceChar; 
						
					}
					
				}else{
					
					s = s.substring(startIndex);
				}
				
			}
			
		}
		
		return s;
	}
	
	/**
	 * 按照字符串字母排序然后拼接
	 * @param results
	 * @return
	 */
	public static String appendStringSort(Map<String, String[]>  results,String filterStr){
		
		//TreeSet 自动排序了
		Set<String> keyset = new TreeSet<String>(results.keySet());
		
		Iterator<String> it = keyset.iterator();  
		
		StringBuffer str = new StringBuffer();
		
		String key = null;
		
		String[] s = {};
		while (it.hasNext()) {  
			
			key = it.next();  
		  
			s = results.get(key);
			
			if(!StringUtils.isNotEmpty(filterStr) || !filterStr.equals(key)){
				
				str.append(key).append("=").append(s[0]).append("&");
			}
			
		}  
		
		return str.substring(0,str.length() - 1);
	}
	
	
	/**
	 * 过滤特殊字符串
	 * @param value
	 * @return
	 */
	public static String filterDangerString(String value) {
		
        if (value == null) {
        	
            return null;
        }
        value = value.replaceAll("\\|", "");

        value = value.replaceAll("&", "");

        value = value.replaceAll(";", "");

//      value = value.replaceAll("@", "");

       /* value = value.replaceAll("\"", "“");*/

        value = value.replaceAll("\\'", "‘");

        value = value.replaceAll("\\(", "（");

//        value = value.replaceAll("\\)", "）");

        value = value.replaceAll("\\+", "");

        value = value.replaceAll("\r", " ");

//        value = value.replaceAll("\n", " ");

        value = value.replaceAll("script", "");

        value = value.replaceAll("'", "‘");
        
//        value = value.replaceAll(">", "");
        
//        value = value.replaceAll("<", "");
        
//        value = value.replaceAll(" ", "");
        
//        value = value.replaceAll("\t", "");
        
        return value;
    }
	
	
	/**
	 * 判断字符串的长度范围
	 * @param str
	 * 
	 */
	public static boolean isLengthCom(String str){
		 boolean rs = false;  
		int strLenth = getStrLength(str);  
       if (strLenth < 5 || strLenth > 45) {  
           rs = false;  
       }else{
       	rs = true;
       } 
       return rs;
	}
	
	 /** 
    * 获取字符串的长度，对双字符（包括汉字）按两位计数 
    *  
    * @param value 
    * @return 
    */  
   public static int getStrLength(String value) {  
       int valueLength = 0;  
       String chinese = "[\u0391-\uFFE5]";  
       for (int i = 0; i < value.length(); i++) {  
           String temp = value.substring(i, i + 1);  
           if (temp.matches(chinese)) {  
               valueLength += 2;  
           } else {  
               valueLength += 1;  
           }  
       }  
       return valueLength;  
   }
   
   
   /**
	 * 判断字符串是否为空
	 * @param str
	 * @return  null true
	 */
	public static boolean isEmpty(String str){
		if( str == null || str.length() <= 0){
			return true;
		}
		return false;
	}
	
	
	/***
	 * 屏蔽用户手动输入的代码或特殊字符处理
	 * @param str
	 * @return
	 */
	public static String htmlEncode(String str) {
	    if (str == null || str.length() == 0) {
	     return str;
	    }
	    Pattern p = Pattern.compile("[\\s]|[\t]|[\r]|[\n]|[?]|[^\u4E00-\u9FA5\u0000-\u007f]"); 
	    Matcher m = p.matcher(str); 
	    str = m.replaceAll("");
	    //  
	    StringBuffer sBuf = new StringBuffer(str.length());
	    for (int i = 0; i < str.length(); i++) {
	     char content = str.charAt(i);
	     switch (content) {
	     case ('<'):
	      sBuf.append("&lt;");
	      break;
	     case ('>'):
	      sBuf.append("&gt;");
	      break;
	     case ('\"'):
	      sBuf.append("&#34;");
	      break;
	     case ('\''):
	      sBuf.append("&#39;");
	      break;
	     case ('('):
	      sBuf.append("&#40;");
	      break;
	     case (')'):
	      sBuf.append("&#41;");
	      break;
	     case ('%'):
	      sBuf.append("&#37;");
	      break;
	     case ('&'):
	      sBuf.append("&#38;");
	      break;
	     case ('+'):
	      sBuf.append("&#43;");
	      break;
	     case (';'):
	      sBuf.append("&#59;");
	      break;
	     default:
	      sBuf.append(content);
	      break;
	     }
	    }
	    return sBuf.toString();
	   }

	/**
	 * unicode编码转换
	 * @param utfString
	 * @return
	 */
	public String convert(String utfString){  
	    StringBuilder sb = new StringBuilder();  
	    int i = -1;  
	    int pos = 0;  
	      
	    while((i=utfString.indexOf("\\u", pos)) != -1){  
	        sb.append(utfString.substring(pos, i));  
	        if(i+5 < utfString.length()){  
	            pos = i+6;  
	            sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));  
	        }  
	    }  
	    return sb.toString();  
	}  
	
	public static String toString(Object object)
	{
		return object == null ? null : object.toString();
	}
	
	/**
	 * 字符串转换
	 * 表字段名称转model标准名称
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String columnToProperty(String str) throws Exception{
		
		String t1 = null;
		
		String[] s = null;
		
		//str[i].charAt(0) - 32)转成大写   思路
		str = str.toLowerCase();
		
		s = str.split("_");
		
		for (int j = 0; j < s.length; j++) {
			
			if(j == 0){
				
				t1 = s[0];
			}else{
				
				t1 += StringUtils.firstToUpperCase(s[j]);
			}
		}
		
		return t1;
	}
	//将string数组转化为sql的in条件,例如select * from tableName id in (字符串)
	public static String stringArray2StrinNot (String[] strs,String split){
		if(strs==null||strs.length==0){
			return "";
		}
		StringBuffer idsStr = new StringBuffer(); 
		for (int i = 0; i < strs.length; i++) { 
		if (i > 0) { 
			idsStr.append(split); 
		} 
//		idsStr.append("'").append(strs[i]).append("'");
		idsStr.append(strs[i]); 
		}
		return idsStr.toString();
	}
	//将string的list集合转化为sql的in条件,例如select * from tableName id in (字符串)
	public static String stringList2StrinNot (List<Long> strs,String split){
		if(MyUtils.isListEmpty(strs)){
			return "";
		}
		StringBuffer idsStr = new StringBuffer(); 
		for (int i = 0; i < strs.size(); i++) { 
		if (i > 0) { 
			idsStr.append(split); 
		} 
//			idsStr.append("'").append(strs.get(i)).append("'"); 
			idsStr.append(strs.get(i)); 
		}
		return idsStr.toString();
	}
	// 判断一个字符串是否为数字加一个字符
	public static boolean isDigit(String strNum) {
		String dpattern = "\\d*";
		return strNum.matches(dpattern);
	}
	public static boolean isNumeric(String str){
	  for (int i = 0; i < str.length(); i++){
		  System.out.println(str.charAt(i));
		  if (!Character.isDigit(str.charAt(i))){
			return false;
		  }
	  }
	  return true;
	}
}
