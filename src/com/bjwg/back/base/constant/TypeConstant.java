package com.bjwg.back.base.constant;
/**
 * 类型常量 
 * @author Allen
 * @version 创建时间：2015-7-13 上午10:37:02
 * @Modified By:Administrator
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class TypeConstant {

	
	/**
	 * 用户类型 - qq登录 - 1
	 */
	public static String USER_LOGIN_TYPE_QQ = "1";
	/**
	 * 用户类型 - 微信登录 - 2
	 */
	public static String USER_LOGIN_TYPE_WX = "2";
	/**
	 * 用户类型 - 手机号登录 - 3
	 */
	public static String USER_LOGIN_TYPE_PHONE = "3";
	/**
	 * 服务协议类型 - 抢购 - 1
	 */
	public static byte SP_TYPE_PANICUTBUY = 1;
	/**
	 * 赠送类型 - 送猪仔 - 1
	 */
	public static byte SEND_TYPE_PIG = 1;
	/**
	 * 赠送类型 - 送猪肉券 - 2
	 */
	public static byte SEND_TYPE_PIG_COUPON = 2;
	/**
	 * 券使用类型 - 1:使用
	 */
	public static byte COUPON_USE_TYPE_1 = 1;
	/**
	 * 券使用类型 - 2:赠送
	 */
	public static byte COUPON_USE_TYPE_2 = 2;
	/**
	 * 分割方式 1:粗分
	 */
	public static byte DIVISION_TYPE_1 = 1;
	/**
	 * 分割方式 2:细分
	 */
	public static byte DIVISION_TYPE_2 = 2;
	/**
	 * 用户设置项 - 0:默认
	 */
	public static byte USER_SETTING_TYPE_0 = 0;
	/**
	 * 用户设置项 - 1:每期固定抢标
	 */
	public static byte USER_SETTING_TYPE_1 = 1;
	/**
	 * 用户设置项 - 2:定制哪期抢标
	 */
	public static byte USER_SETTING_TYPE_2 = 2;
	/**
	 * 添加好友类型 1:查找添加
	 */
	public static byte FREINDS_ADD_TYPE_1 = 1;
	/**
	 * 添加好友类型 2:邀请添加（默认自动同意status:1，agree_time为用户注册时间）
	 */
	public static byte FREINDS_ADD_TYPE_2 = 2;
	
	
}
