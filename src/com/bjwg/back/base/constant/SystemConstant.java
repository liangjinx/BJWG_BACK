package com.bjwg.back.base.constant;

/**
 * 常用的常量
 * @author Kim
 * @version 创建时间：2015-4-7 上午09:42:44
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class SystemConstant
{
    /**
     * 系统基本设置
     */
    public static final String SYS_MANAGE_SITE = "SYS_MANAGE_SITE";
    /**
     * 在线客服设置
     */
    public static final String SYS_MANAGE_CUSTOMER_SERVICE = "SYS_MANAGE_CUSTOMER_SERVICE";
	
	/**
	 * 二维码url地址
	 */
	public final static String QR_CODE_URL = "USER_CONFIG.QR_CODE_URL";
	/**
	 * 二维码本地存放路径
	 */
	public final static String QR_UPLOAD_CODE_IMG_PATH = "USER_CONFIG.QR_UPLOAD_CODE_IMG_PATH";
	
	/**
	 * 项目访问地址 
	 */
	public final static String PROJECT_ACCESS_URL = "SYS_CONFIG.PROJECT_ACCESS_URL";
	/**
	 * 图片项目的访问地址
	 */
	public final static String PROJECT_IMG_ACCESS_URL = "SYS_CONFIG.PROJECT_IMG_ACCESS_URL";
	/**
	 * 用户上传图片的根目录
	 */
	public final static String PROJECT_IMG_UPLOAD_ROOT_PATH = "SYS_CONFIG.PROJECT_IMG_UPLOAD_ROOT_PATH";
	/**
	 * 上传文件限制类型
	 */
	public final static String UPLOAD_FILE_LIMIT_TYPE = "SYS_CONFIG.UPLOAD_FILE_LIMIT_TYPE";
	/**
	 * 上传文件限制大小
	 */
	public final static String UPLOAD_FILE_MAX_SIZE = "SYS_CONFIG.UPLOAD_FILE_MAX_SIZE";
	/**
	 * 导出文件条数限制
	 */
	public final static String EXPORT_FILE_MAX_COUNT = "SYS_CONFIG.EXPORT_FILE_MAX_COUNT";
	
	/**
	 * 提前n天最终确认回报方式
	 */
	public final static String ORDER_CONFIG_CONFIRM_REWARDS_BEFORE_N_DAYS = "ORDER_CONFIG.CONFIRM_REWARDS_BEFORE_N_DAYS";
	
	/**
	 * 领取活猪提前n天确认收货
	 */
	public final static String ORDER_CONFIG_CONFIRM_REWARDS_BEFORE_N_DAYS_FOR_PIGS = "ORDER_CONFIG.CONFIRM_REWARDS_BEFORE_N_DAYS_FOR_PIGS";
	
	/**
	 * 领取活猪逾期天数
	 */
	public final static String ORDER_CONFIG_OVERDUE_DAYS_FOR_PIGS = "ORDER_CONFIG.OVERDUE_DAYS_FOR_PIGS";
	
	/**
	 * 领取活猪逾期未领取扣除的综合费用(元/每头)
	 */
	public final static String ORDER_CONFIG_OVERDUE_FEE_FOR_PIGS = "ORDER_CONFIG.OVERDUE_FEE_FOR_PIGS";
	
	/**
	 * 订单支付超时时间
	 */
	public final static String ORDER_PAY_OVER_LIMIT_HOURS = "PAY_CONFIG.ORDER_PAY_OVER_LIMIT_HOURS";
	
	/**
	 * 猪肉券额度
	 */
	public final static String SYS_CONFIG_PIG_COUPON_MONEY = "SYS_CONFIG.PIG_COUPON_MONEY";
	
	/**
	 * 未选择回报方式,短信提醒时间设置
	 */
	public final static String SYS_CONFIG_DEALTYPE_SMS_REMIND_DAY = "SYS_CONFIG.DEALTYPE_SMS_REMIND_DAY";
	/**
	 * 猪肉券有效期(单位:天)
	 */
	public final static String SYS_CONFIG_PIG_COUPON_VALIDITY = "SYS_CONFIG.PIG_COUPON_VALIDITY";
}
