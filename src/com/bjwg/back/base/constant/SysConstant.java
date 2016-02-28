package com.bjwg.back.base.constant;


/**
 * 系统常量
 * @author Allen
 *
 */
public class SysConstant {

	/**
	 * 生长天数
	 */
	public final static String GROW_UP_DAYS = "GROUP_CONFIG.GROW_UP_DAYS";
	/**
	 * 成长阶段
	 */
	public final static String GROW_UP_LEVEL = "GROUP_CONFIG.GROW_UP_LEVEL";
	/**
	 * 年化率
	 */
	public final static String YEAR_RETURN_RATE = "SYS_CONFIG.YEAR_RETURN_RATE";
	/**
	 * 猪肉券额度
	 */
	public final static String PIG_COUPON_MONEY = "SYS_CONFIG.PIG_COUPON_MONEY";
	/**
	 * 订单支付超时期限，单位分钟
	 */
	public final static String ORDER_PAY_OVER_LIMIT_HOURS = "PAY_CONFIG.ORDER_PAY_OVER_LIMIT_HOURS";
	/**
	 * 粗分割费
	 */
	public final static String DIVISION_THICK_FEE = "PAY_CONFIG.DIVISION_THICK_FEE";
	/**
	 * 细分割费
	 */
	public final static String DIVISION_THIN_FEE = "PAY_CONFIG.DIVISION_THIN_FEE";
	/**
	 * 屠宰运送费
	 */
	public final static String SLAUGHTER_EXPRESS_FEE = "PAY_CONFIG.SLAUGHTER_EXPRESS_FEE";
	/**
	 * 真空包装费
	 */
	public final static String PACKAGE_FEE = "PAY_CONFIG.PACKAGE_FEE";
	/**
	 * 真空包装规格
	 */
	public final static String PACKAGE_SPECS = "PAY_CONFIG.PACKAGE_SPECS";
	/**
	 * 单只重量
	 */
	public final static String PER_WEIGHT = "PAY_CONFIG.PER_WEIGHT";
	/**
	 * 用户邀请码二维码访问地址
	 */
	public final static String QR_CODE_URL = "USER_CONFIG.QR_CODE_URL";
	/**
	 * 图片存储的根路径
	 */
	public final static String PROJECT_IMG_UPLOAD_ROOT_PATH = "SYS_CONFIG.PROJECT_IMG_UPLOAD_ROOT_PATH";
	/**
	 * 列举新的好友的过滤时间
	 */
	public final static String NEW_FREINDS_FILTER_TIME = "USER_CONFIG.NEW_FREINDS_FILTER_TIME";
	/**
	 * 提前n天最终确认回报方式
	 */
	public final static String CONFIRM_REWARDS_BEFORE_N_DAYS = "ORDER_CONFIG.CONFIRM_REWARDS_BEFORE_N_DAYS";
	/**
	 * 项目访问地址
	 */
	public final static String PROJECT_ACCESS_URL = "SYS_CONFIG.PROJECT_ACCESS_URL";
	/**
	 * 项目访问地址PC
	 */
	public final static String PROJECT_ACCESS_URL_PC = "SYS_CONFIG.PROJECT_ACCESS_URL_PC";
	/**
	 * 图片项目的访问地址
	 */
	public final static String PROJECT_IMG_ACCESS_URL = "SYS_CONFIG.PROJECT_IMG_ACCESS_URL";
	/**
	 * 上传文件限制类型
	 */
	public final static String UPLOAD_FILE_LIMIT_TYPE = "SYS_CONFIG.UPLOAD_FILE_LIMIT_TYPE";
	/**
	 * 上传文件限制大小
	 */
	public final static String UPLOAD_FILE_MAX_SIZE = "SYS_CONFIG.UPLOAD_FILE_MAX_SIZE";
	/**
	 * 支付密码输入错误次数限制
	 */
	public final static String PAY_PASSWORD_LIMIT_ERROR_COUNT = "PAY_CONFIG.PAY_PASSWORD_LIMIT_ERROR_COUNT";
	
	/**
	 * 统计报表展示n周的收益
	 */
	public final static String STAT_N_WEEK_REWARDS = "SYS_CONFIG.STAT_N_WEEK_REWARDS";
	/**
	 * 猪肉券可使用城市
	 */
	public final static String PIG_COUPON_CAN_USE_CITY = "PAY_CONFIG.PIG_COUPON_CAN_USE_CITY";
	/**
	 * 猪肉券到期天数
	 */
	public final static String PIG_COUPON_END_DAYS = "SYS_CONFIG.PIG_COUPON_END_DAYS";
	/**
	 * 回报方式
	 */
	public final static String REPAY_TYPE = "PAY_CONFIG.REPAY_TYPE";
	/**
	 * 回报方式1 - 委托寄卖
	 */
	public final static String REPAY_TYPE_1 = "PAY_CONFIG.REPAY_TYPE_1";
	/**
	 * 回报方式2 - 屠宰配送
	 */
	public final static String REPAY_TYPE_2 = "PAY_CONFIG.REPAY_TYPE_2";
	/**
	 * 回报方式3 - 领活猪
	 */
	public final static String REPAY_TYPE_3 = "PAY_CONFIG.REPAY_TYPE_3";
	/**
	 * 回报方式4 - 兑换券
	 */
	public final static String REPAY_TYPE_4 = "PAY_CONFIG.REPAY_TYPE_4";
	/**
	 * 费用详情说明
	 */
	public final static String FEE_DETAIL_DESCRIPTION = "PAY_CONFIG.FEE_DETAIL_DESCRIPTION";
	/**
	 * 下期预告描述
	 */
	public final static String NEXT_PROJECT_DESCRIPTION = "PAY_CONFIG.NEXT_PROJECT_DESCRIPTION";
	/**
	 * 客服热线
	 */
	public final static String SYS_MANAGE_CUSTOMER_SERVICE_PHONE = "SYS_MANAGE_CUSTOMER_SERVICE.PHONE";
	/**
	 * qq客服
	 */
	public final static String SYS_MANAGE_CUSTOMER_SERVICE_QQ = "SYS_MANAGE_CUSTOMER_SERVICE.QQ";
	/**
	 * qq群
	 */
	public final static String SYS_MANAGE_CUSTOMER_SERVICE_QQ_GROUP = "SYS_MANAGE_CUSTOMER_SERVICE.QQ_GROUP";
	
	/**
	 * 短信接口url
	 */
	public final static String SMS_URL = "SYS_CONFIG.SMS_URL";
	/**
	 * 短信接口登录用户id
	 */
	public final static String SMS_UID = "SYS_CONFIG.SMS_UID";
	/**
	 * 短信接口登录用户id
	 */
	public final static String SMS_AUTH = "SYS_CONFIG.SMS_AUTH";
	
	/**
	 * 微信appid
	 */
	public final static String WX_APP_ID = "SYS_CONFIG.WX_APP_ID";
	/**
	 * 微信AppSecret
	 */
	public final static String WX_APP_SECRET = "SYS_CONFIG.WX_APP_SECRET";
	/**
	 * 微信公众号重定向地址
	 */
	public final static String WX_APP_REDIRECT = "SYS_CONFIG.WX_APP_REDIRECT";
	/**
	 * 微信应用授权作用域
	 */
	public final static String WX_APP_SCOPE = "SYS_CONFIG.WX_APP_SCOPE";
	/**
	 * 微信应用partner
	 */
	public final static String WX_APP_PARTNER = "SYS_CONFIG.WX_APP_PARTNER";
	/**
	 * 微信应用partnerKey
	 */
	public final static String WX_APP_PARTNER_KEY = "SYS_CONFIG.WX_APP_PARTNER_KEY";
	/**
	 * QQAppid。
	 */
	public final static String QQ_APP_ID = "SYS_CONFIG.QQ_APP_ID";
	/**
	 * QQAppKey。
	 */
	public final static String QQ_APP_KEY = "SYS_CONFIG.QQ_APP_KEY";
	/**
	 * 支付宝合作身份者ID（partner 以2088开头由16位纯数字组成的字符串，例如：2088465151123433） 
	 */
	public final static String ALIPAY_PARTNER = "SYS_CONFIG.ALIPAY_PARTNER";
	/**
	 * 支付宝收款帐户（seller_email 如：858922@qq.com）
	 */
	public final static String ALIPAY_SELLER = "SYS_CONFIG.ALIPAY_SELLER";
	/**
	 * 支付宝交易安全检验码（key，由数字和字母组成的32位字符串，例如：n8wzlo12d5yu2vaosiwf7fe8s2s26x9wx）
	 */
	public final static String ALIPAY_PARTNER_KEY = "SYS_CONFIG.ALIPAY_PARTNER_KEY";
	
	/**
	 * 银联商户号
	 */
	public final static String UNIONPAY_PARTNER_KEY = "SYS_CONFIG.UNIONPAY_PARTNER_KEY";
	
	/**
	 * 默认头像
	 */
	public final static String DEFAULT_HEADIMG = "SYS_CONFIG.DEFAULT_HEADIMG";
	
}
