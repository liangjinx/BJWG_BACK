package com.bjwg.back.util;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import com.bjwg.back.base.constant.StatusConstant;

public class MapUtil {
	/**
	 * 获取百度地图的经纬度
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public synchronized static String getBaiduMapResult(String address){
		HttpClient client = null;
		GetMethod getMethod = null;
		try{
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(6*1000);
			client.getHttpConnectionManager().getParams().setSoTimeout(6*1000);  
	    	String url = ToolKit.getInstance().getSingleConfig("baidu_map_interface") + "?address="+java.net.URLEncoder.encode(address, "utf-8")+"&output=json&ak="+ToolKit.getInstance().getSingleConfig("baidu_map_ak");
	    	getMethod = new GetMethod(url);
			int statusCode = client.executeMethod(getMethod);
			if (statusCode == HttpStatus.SC_OK) {
				String sms = getMethod.getResponseBodyAsString();
				return sms;
			}
		}catch(ConnectTimeoutException  e){
			return StatusConstant.Status.SHOP_ADDRESS_CHECK_FAIL.getMsg();
		}catch(SocketTimeoutException  e){
			return StatusConstant.Status.SHOP_ADDRESS_CHECK_FAIL.getMsg();
		}catch(IOException  e){
			e.printStackTrace();
		}finally {  
			getMethod.releaseConnection(); //释放链接  
        }
		return null;
	}
	/**
	 * 解析百度地图返回的经纬度数据
	 * @param sms
	 * @return
	 * @throws Exception
	 */
	public synchronized static Double[] parseJson(String sms) throws Exception{
		
		net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(sms);
		
		if(object.getString("status").equals("0")){
			
			object = object.getJSONObject("result").getJSONObject("location");
			
			Double[] d = new Double[]{object.getDouble("lng"),object.getDouble("lat")}; 
			
			return d;
		}
		
		return null;
	}
	public static void main(String[] args) {
		System.out.println(getBaiduMapResult("重庆市紫荆路169号(市公安局南门旁)"));
	}

}
