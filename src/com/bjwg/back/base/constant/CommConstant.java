package com.bjwg.back.base.constant;

/**
 * 常用的常量
 * @author Kim
 * @version 创建时间：2015-4-7 上午09:42:44
 * @Modified By:Allen
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class CommConstant
{
    
	
	   /**
     * 处理状态 0 -未处理
     */
    public static byte DEAL_STATUS_0 = 0;
	
	
    /**
     * 通用成功标识
     */
    public static String SUCCESS = "success";
    /**
     * 用户登录成功标识
     */
    public static String SESSION_MANAGER_LOGIN_FLAG = "session_manager_login";
    /**
     * 用户当前拥有的权限
     */
    public static String SESSION_MANAGER_AUTHORITY_LINK = "session_manager_authority";
    /**
     * session中保存的用户资料信息
     */
    public static String SESSION_MANAGER = "session_manager";
    /**
     * session中保存的当前用户授权的菜单
     */
    public static String SESSION_MANAGER_AUTHORITY_MENU = "session_manager_authority_menu";
    /**
     * session中保存的是否可以查看所有数据
     */
    public static String SESSION_ISVIEW_ALLDATA = "session_isview_alldata";
    /**
     * session中保存的是否可以创建子账号权限
     */
    public static String SESSION_ISCREATE_CHILD = "session_iscreate_child";
    /**
     * session中保存的是否可以查看子账号数据权限
     */
    public static String SESSION_ISVIEW_CHILD = "session_isview_child";
    
 	//查看他人的数据 资源代码
 	public static final String VIEW_ALL_DATA_CODE = "others_data_view";
 	//创建子账号 资源代码
 	//public static final String CREATE_CHILD_USER_CODE = "create_child_user";
 	//查看子账号数据 资源代码
 	public static final String VIEW_CHILD_USER_DATA_CODE = "child_data_view";
 	
 	public static final String PASSWORD_SECRET_TEXT = "6d6630c5a8d0384aba30133b10375cec";
 	
    /**
     * 邀请码前缀
     */
    public static String INVITECODE_PREFIX = "rm";
    
}
