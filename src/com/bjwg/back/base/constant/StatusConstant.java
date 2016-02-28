package com.bjwg.back.base.constant;



/**
 * 状态常量类
 * @author Kim
 * @version 创建时间：2015-8-10 下午05:22:36
 * @Modified By:Kim
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class StatusConstant
{
	
	public enum Status{
		SUCCESS(1,"请求成功"),
		
		
		FAIL_UNKOWN(-1000,"未知错误!"),
		MANAGER_USERNAME_OR_PSW_IS_WRONG(-1,"用户名或密码不正确!"),
		MANAGER_IS_FORBID(-2,"用户被禁止!"),
		MANAGER_IS_FREEZE(-3,"用户被冻结!"),
		ROLE_NAME_EXISTED(-4,"角色名重复!"),
		RESC_EXISTED(-5,"资源名称或编号或链接重复!"),
		USER_NAME_EXISTED(-6,"用户名重复!"),
		USER_DEL_FORBID(-7,"管理员不能被删除!"),
		FOREIGN_USER_NULL(-8,"关联用户不能为空!"),
		PHONE_EXIST(-9,"该电话号码已存在!"),
		HAS_NO_EXPORT_COUNT(-10,"没有可以导出的数据!"),
		OPE_LOG_OBJECT_NULL(-11,"操作日志记录失败,操作对象不能为空!"),
		OPE_LOG_OBJECT_ID_NULL(-12,"操作日志记录失败,操作对象ID不能为空!"),
		OPE_LOG_TIME_NULL(-13,"操作日志记录失败,操作时间不能为空!"),
		OPE_LOG_TYPE_NULL(-14,"操作日志记录失败,操作类型不能为空!"),
		OPE_LOG_OPERATOR_NULL(-15,"操作日志记录失败,操作人ID不能为空!"),
		OPE_LOG_DEL_FAIL(-16,"删除操作日志失败,无此操作日志!"),
		SEND_MESSAGE_TIME_OUT(-17,"发送消息超时!"),
		SEND_MESSAGE_FAIL(-18,"发送消息失败,请检查手机号码是否正确!"),
		SEND_MESSAGE_PHONE_NULL(-19,"电话号码不能为空!"),
		PARENT_USER_NOT_EXIST(-20,"分配子账号主错,无此主账号!"),
		USER_SESSION_EXPIRED(-21,"用户会话过期!"),
		SYSCONFIG_DEL_FAIL(-22,"删除系统配置失败,无此系统配置!"),
		SYSCONFIG_NAME_NULL(-23,"系统配置名称为空!"),
		SYSCONFIG_CODE_NULL(-24,"系统配置编号为空!"),
		SYSCONFIG_PARENT_CODE_NULL(-25,"系统配置父级编号为空!"),
		DICT_NAME_NULL(-26,"字典配置名称为空!"),
		DICT_CODE_NULL(-27,"字典配置编号为空!"),
		DICT_DEL_FAIL(-28,"操作字典配置失败,无此字典配置项!"),
		DICT_DETAIL_NAME_NULL(-29,"字典明细配置名称为空!"),
		DICT_DETAIL_CODE_NULL(-30,"字典明细配置编号为空!"),
		DICT_DETAIL_DEL_FAIL(-31,"操作字典明细失败,无此字典明细!"),
		USER_DEL_FAIL(-32,"删除用户失败,无此用户!"),
		USER_USERNAME_NULL(-33,"用户名称不能为空!"),
		USER_USERNAME_LENGTH_OVER(-34,"用户名称超出最大长度!"),
		USER_PASSWORD_NULL(-35,"用户密码不能为空!"),
		USER_PASSWORD_OVER_LEN(-36,"用户密码超出最大长度!"),
		DICT_NAME_OVER_LEN(-37,"字典配置名称超出最大长度!"),
		DICT_CODE_OVER_LEN(-38,"字典配置编号超出最大长度!"),
		DICT_DETAIL_NAME_OVER_LEN(-39,"字典明细配置名称超出最大长度!"),
		DICT_DETAIL_CODE_OVER_LEN(-40,"字典明细配置编号超出最大长度!"),
		SYSCONFIG_NAME_OVER_LEN(-41,"系统配置名称超出最大长度!"),
		SYSCONFIG_CODE_OVER_LEN(-42,"系统配置编号超出最大长度!"),
		SYSCONFIG_PARENT_CODE_OVER_LEN(-43,"系统配置父级编号超出最大长度!"),
		BULLETIN_TITLE_NULL(-44,"公告标题为空!"),
		BULLETIN_TITLE_OVER_LEN(-45,"公告标题超出最大长度!"),
		BULLETIN_CONTENT_NULL(-46,"公告内容为空!"),
		BULLETIN_CONTENT_OVER_LEN(-47,"公告内容超出最大长度!"),
		BULLETIN_DEL_FAIL(-48,"删除公告失败,无此公告!"),
		INFO_TITLE_NULL(-49,"资讯标题为空!"),
		INFO_TITLE_OVER_LEN(-50,"资讯标题超出最大长度!"),
		INFO_DETAIL_NULL(-51,"资讯内容为空!"),
		INFO_DETAIL_OVER_LEN(-52,"公告内容超出最大长度!"),
		INFO_DEL_FAIL(-53,"删除资讯失败,无此资讯!"),
		PROJECT_NAME_NULL(-54,"抢购项目名称为空!"),
		PROJECT_NAME_OVER_LEN(-55,"抢购项目名称超出最大长度!"),
		PROJECT_PRICE_NULL(-56,"抢购项目单价为空!"),
		PROJECT_PRICE_OVER_LEN(-57,"抢购项目单价超出最大长度(整数位7位,小数位2位)!"),
		PROJECT_TOTAL_MONEY_NULL(-58,"抢购项目单只总价为空!"),
		PROJECT_TOTAL_MONEY_OVER_LEN(-59,"抢购项目单只总价超出最大长度!"),
		PROJECT_BEGIN_TIME_NULL(-60,"抢购项目开始抢购时间为空!"),
		PROJECT_END_TIME_NULL(-61,"抢购项目结束抢购时间为空!"),
		PROJECT_DEL_FAIL(-62,"删除抢购项目失败,无此抢购项目!"),
		WALLET_USER_ID_NULL(-63,"用户为空!"),
		WALLET_MONEY_NULL(-64,"用户余额为空!"),
		WALLET_MONEY_OVER_LEN(-65,"用户余额超出最大长度(整数位7位,小数位2位)!"),
		WALLET_DEL_FAIL(-66,"删除用户钱包失败,无此用户!"),
		
		WITHDRAW_FAIL_FORMAT_ERROR(-67,"提现信息审核出错,参数错误!"),
		
		PRESENT_DEL_FAIL(-68,"操作赠送好友物品记录失败,无此记录!"),
		
		PRESENT_PRESENT_USER_NULL(-69,"赠送人昵称为空!"),
		PRESENT_PRESENTED_USER_NULL(-70,"被赠送人昵称!"),
		PRESENT_PRESENT_NUM_NULL(-71,"数量为空!"),
		PRESENT_PRICE_NULL(-72,"单价为空!"),
		PRESENT_PRICE_OVER_LEN(-73,"单价超出最大长度!"),
		PRESENT_TOTAL_MONEY_NULL(-74,"总额为空!"),
		PRESENT_TOTAL_MONEY_OVER_LEN(-75,"总额超出最大长度!"),
		SYSCONFIG_VALUE_NULL(-76,"系统配置的值不能为空!"),
		SYSCONFIG_VALUE_OVER_LEN(-77,"系统配置的值超出最大长度!"),
		
		DICT_DETAIL_REMARK_OVER_LEN(-78,"字典明细备注超出最大长度!"),
		
		SHOP_NAME_NULL(-79,"店铺店名为空!"),
		SHOP_PROVINCE_NULL(-80,"店铺省份为空!"),
		SHOP_CITY_NULL(-81,"店铺城市为空!"),
		SHOP_ADDRESS_NULL(-82,"店铺具体地址为空!"),
		SHOP_LOGO_NULL(-83,"店铺的logo为空!"),
		SHOP_DEL_FAIL(-84,"操作店铺失败,无此店铺!"),
		SHOP_NAME_OVER_LEN(-85,"店铺店名超出最大长度!"),
		SHOP_ADDRESS_OVER_LEN(-86,"店铺具体地址超出最大长度!"),
		SHOP_REMARK_OVER_LEN(-87,"店铺备注超出最大长度!"),
		
		SHOP_ADDRESS_CHECK_FAIL(-88,"校验地址失败,请求百度接口超时!"),
		SHOP_ADDRESS_VALID(-89,"店铺地址无效"),
		
		ORDER_DEL_NULL(-90,"操作订单失败,无此订单!"),
		
		SERVICE_PROTOCOL_CONTENT_NULL(-91,"协议内容不能为空!"),
		SERVICE_PROTOCOL_CONTENT_OVER_LEN(-92,"协议内容超出最大长度!"),
		SERVICE_PROTOCOL_EXIST(-93,"存在当前协议!"),
		
		SLIDE_PATH_NULL(-94,"轮播图图片不能为空!"),
		SLIDE_NAME_NULL(-95,"轮播图图片名称不能为空!"),
		SLIDE_NAME_OVER_LEN(-96,"轮播图图片名称超出最大长度!"),
		SLIDE_LINK_OVER_LEN(-97,"轮播图图片链接超出最大长度!"),
		SLIDE_DEL_NULL(-98,"轮播图删除失败,无此轮播图!"),
		OVER_MAX_EXPORT_COUNT(-99,"超出了导出的数据最大量!"),
		
		PROJECT_NUM_NULL(-100,"抢购项目数量不能为空!"),
		PROJECT_NUM_OVER_LEN(-101,"抢购项目数量超出最大长度5位!"),
		FILE_UPLOAD_CONFIG_NOEXIST(-102,"上传图片的配置信息不存在!"),
		INFO_PATH_NULL(-103,"资讯首页图片为空!"),
		PROJECT_DEALS_DATE_LIMIT(-104,"进行中的抢购项目只能有一个!"),
		PROJECT_LEFT_NUM_NULLITY(-105,"抢购项目剩余数量无法计算!"),
		INFO_SORT_OVER_LENGTH(-106,"资讯排序超出最大长度!"),
		
		UPLOAD_NOFILE(-500,"未包含文件上传域!!"),
		UPLOAD_TYPE(-501,"不允许的文件格式!"),
		UPLOAD_SIZE(-502,"文件大小超出限制!"),
		UPLOAD_ENTYPE(-503,"请求类型ENTYPE错误!"),
		UPLOAD_REQUEST(-504,"上传请求异常!"),
		UPLOAD_IO(-505,"IO异常!"),
		UPLOAD_DIR(-506,"目录创建失败!"),
	UPLOAD_UNKNOWN(-507,"未知错误!"),
	
	

	/**
	 * 用户id不能为空且只长度只能是10位
	 */
	useridNullity(-601,"用户id不能为空且只长度只能是10位"), 
	/**
	 * 设置类型无效
	 */
	settingTypeNullity(-602,"设置类型无效"),
	/**
	 * 抢购id不能为空且只长度只能是10位
	 */
	projectidNullity(-603,"抢购id不能为空且只长度只能是10位"),
	/**
	 * 数量无效
	 */
	numNullity(-604,"数量无效"),
	/**
	 * 服务器错误
	 */
	serverError(-605,"服务器错误"),
	;
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//状态码
		private int status;
		//状态中文提示
		private String msg;
		
		public int getStatus()
		{
			return status;
		}

		public String getMsg()
		{
			return msg;
		}

		Status(int status,String msg)
		{
			this.status=status;
			this.msg=msg;
		}
	}
	
	
	public static String getMsg(int status){
		String msg = "";
        for(Status s :Status.values()){
        	if(s.status==status){
        		msg = s.msg;
        		break;
        	}
        }
        return msg;
    } 
	
	
	
}
