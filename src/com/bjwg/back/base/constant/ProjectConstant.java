package com.bjwg.back.base.constant;
/**
 * 项目常量
 * @author Kim
 * @version 创建时间：2015-6-10 下午04:00:24
 * @Modified By:Administrator
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class ProjectConstant {

	
	
	/**
	 * 项目状态 - 未开始
	 */
	public static byte PROJECT_STATUS_0 = 0;
	/**
	 * 项目状态 - 进行中
	 */
	public static byte PROJECT_STATUS_1 = 1;
	/**
	 * 项目状态 - 已结束
	 */
	public static byte PROJECT_STATUS_2 = 2;
	
	/**
	 * 回报方式 - 1:委托寄卖
	 */
	public static byte REWARDS_1 = 1;
	/**
	 * 回报方式 - 2:屠宰配送
	 */
	public static byte REWARDS_2 = 2;
	/**
	 * 回报方式 - 3:领取活猪
	 */
	public static byte REWARDS_3 = 3;
	/**
	 * 回报方式 - 4:领取2300元／头的猪肉券
	 */
	public static byte REWARDS_4 = 4;
	
	/**
	 * 处理状态 - 0:未处理
	 */
	public static byte DEALS_TATUS_0 = 0;
	/**
	 * 处理状态 - 1:处理完成
	 */
	public static byte DEALS_TATUS_1 = 1;
	
	/**
	 * 关联类型- 1:抢购购买
	 */
	public static byte WALLET_CHANGE_RELATION_TYPE_1 = 1;
	/**
	 * 关联类型- 2:委托寄卖
	 */
	public static byte WALLET_CHANGE_RELATION_TYPE_2 = 2;
	/**
	 * 关联类型- 3:提现
	 */
	public static byte WALLET_CHANGE_RELATION_TYPE_3 = 3;
	/**
	 * 关联类型- 4:收益
	 */
	public static byte WALLET_CHANGE_RELATION_TYPE_4 = 4;
	/**
	 * 关联类型- 5:逾期扣款
	 */
	public static byte WALLET_CHANGE_RELATION_TYPE_5 = 5;
	
	/**
	 * 变更类型- 1:消费
	 */
	public static byte WALLET_CHANGE_CHANGE_TYPE_1 = 1;
	/**
	 * 变更类型- 2:收入
	 */
	public static byte WALLET_CHANGE_CHANGE_TYPE_2 = 2;
	
	/**
	 * 我的券-关联类型- 1:选择猪肉券
	 */
	public static byte MY_COUPON_RELATION_TYPE_1 = 1;
	/**
	 * 我的券-变更类型- 2:赠送
	 */
	public static byte MY_COUPON_RELATION_TYPE_2 = 2;
	
	/**
	 * 我的券-状态- 1:未使用
	 */
	public static byte MY_COUPON_STATUS_0 = 0;
	
	/**
	 * 我的券-状态- 1:使用部分
	 */
	public static byte MY_COUPON_STATUS_1 = 1;
	
	/**
	 * 我的券-状态- 1:使用完成
	 */
	public static byte MY_COUPON_STATUS_2 = 2;
	
}
