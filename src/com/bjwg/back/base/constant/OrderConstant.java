package com.bjwg.back.base.constant;
/**
 * 订单常量
 * @author Kim
 * @version 创建时间：2015-6-10 下午04:00:24
 * @Modified By:Administrator
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class OrderConstant {

	
	
	/**
	 * 订单状态 - 取消
	 */
	public static byte ORDER_STATUS_0 = -1;
	/**
	 * 订单状态 - 新增，未付款
	 */
	public static byte ORDER_STATUS_1 = 1;
	/**
	 * 订单状态 - 付款中
	 */
	public static byte ORDER_STATUS_2 = 2;
	/**
	 * 订单状态 - 已付款
	 */
	public static byte ORDER_STATUS_3 = 3;
	/**
	 * 订单状态 - 已选择回报方式，待收货
	 */
	public static byte ORDER_STATUS_4 = 4;
	/**
	 * 订单状态 - 已确认收货
	 */
	public static byte ORDER_STATUS_5 = 5;
	
	
	/**
	 * 订单 是否前台显示 - Y - 1
	 */
	public static byte IS_FRONT_SHOW_Y = 1;
	/**
	 * 订单 是否前台显示 - N - 0
	 */
	public static byte IS_FRONT_SHOW_N = 0;
	
	
	/**
	 * 订单类型 1 - 抢购
	 */
	public static byte ORDER_TYPE_1 = 1;
	/**
	 * 订单类型 2 - 屠宰配送
	 */
	public static byte ORDER_TYPE_2 = 2;
	/**
	 * 订单类型 3 - 领活猪
	 */
	public static byte ORDER_TYPE_3 = 3;
	/**
	 * 订单处理状态 - 0 未处理
	 */
	public static byte ORDER_DEAL_STATUS_0 = 0;
	/**
	 * 订单处理状态 - 1 已处理
	 */
	public static byte ORDER_DEAL_STATUS_1 = 1;
	
	/**
	 * 0:未选择
	 */
	public static byte ORDER_PRE_TYPE_0 = 0;
	
	/**
	 * 1:每期固定抢标
	 */
	public static byte ORDER_PRE_TYPE_1 = 1;
	
	/**
	 * 2:定制哪期抢标
	 */
	public static byte ORDER_PRE_TYPE_2 = 2;
	
}
