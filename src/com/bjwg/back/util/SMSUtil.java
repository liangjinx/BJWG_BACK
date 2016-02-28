package com.bjwg.back.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.bjwg.back.base.constant.StatusConstant;

/** 
 * @ClassName: SMSUtil 
 * @Description: TODO短信接口工具类
 * @author Kim
 * @date 2015-4-14 下午04:11:32  
 */ 
public class SMSUtil
{
	/**
	 * @param mobilePhone
	 * @param str
	 * @throws HttpException
	 * @throws IOException
	 */
	public static int sendOne(String mobilePhone, String str){
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(6*1000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(6*1000);  
        String content = "";
		
        try {
			content = java.net.URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		PostMethod postMethod = new PostMethod(ToolKit.getInstance().getSingleConfig("sms_url"));
		
		NameValuePair[] data = {
					new NameValuePair("uid", ToolKit.getInstance().getSingleConfig("sms_uid")),
					new NameValuePair("auth", ToolKit.getInstance().getSingleConfig("sms_auth")),
					new NameValuePair("mobile", mobilePhone.toString()),
					new NameValuePair("expid", "0"),
					new NameValuePair("msg",content ),
					new NameValuePair("encode","utf-8")};
		postMethod.setRequestBody(data);
		try{
			int statusCode = httpClient.executeMethod(postMethod);
				
			if (statusCode == HttpStatus.SC_OK) {
				String sms = postMethod.getResponseBodyAsString();
				System.out.println("result:" + sms);
				if(sms.indexOf(",")<0){
					Integer ret = 0;
					try{
						ret = Integer.parseInt(sms.trim());
					}catch(Exception e){
						e.printStackTrace();
					}
					if(ret.intValue()<0){
						return StatusConstant.Status.SEND_MESSAGE_FAIL.getStatus();
					}
				}
			}
			System.out.println("statusCode="+statusCode);	
		}catch(ConnectTimeoutException  e){
			return StatusConstant.Status.SEND_MESSAGE_TIME_OUT.getStatus();
		}catch(SocketTimeoutException  e){
			return StatusConstant.Status.SEND_MESSAGE_TIME_OUT.getStatus();
		}catch(IOException  e){
			e.printStackTrace();
			return StatusConstant.Status.FAIL_UNKOWN.getStatus();
		}finally {  
			postMethod.releaseConnection(); //释放链接  
        }
		return StatusConstant.Status.SUCCESS.getStatus();
	}
	
//	public static void main(String[] args) throws Exception {
//		SMSUtil.sendOne("18611523272", "http://dwz.cn/1DXgwP");//result:0,873366
//	}
} 