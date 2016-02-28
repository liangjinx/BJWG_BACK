package com.bjwg.back.base.constant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面需要用到的常量
 * @author Kim
 * @version 创建时间：2015-4-10 下午04:11:39
 * @Modified By:Kim
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class PageConstant {
   
	/**
	 * common.jsp页面加载时显示菜单
	 * @author Administrator
	 *
	 */
	public enum PageView{
		
		
		NAV_1("首页","1000,1100,","/v/home.do","","icon-home"),
		NAV_1_1("首页 ","1000,","/v/home.do","",""),
		NAV_1_2("操作日志列表","1100,","/v/log/list","log_list",""),
		
		NAV_3("系统管理","3001,3002,3003,3004,3005,3006,3007,3008,3009,3010,3011,3012,3013,3014,3015,3016,3017,3018,3019,3020,","/v/site/config/list","site_config_list_OR_user_config_list_OR_kefu_config_list_OR_slide_list_OR_pact_config_list_OR_sysconfig_list_OR_dict_list_OR_dict_detail_list","icon-cog"),
		NAV_3_5("系统基本设置","3012,3014,","/v/site/config/list","site_config_list",""),
		NAV_3_4("设置列表","3010,3011,","/v/user/config/list","user_config_list",""),
		NAV_3_6("在线客服设置","3013,3015,","/v/kefu/config/list","kefu_config_list",""),
		NAV_3_7("首页轮播图","3016,3017,3018,","/v/slide/list","slide_list",""),
		NAV_3_8("服务协议设置","3019,3020,","/v/pact/config/list","pact_config_list",""),
		NAV_3_1("设置列表(管理员)","3001,3002,3003,","/v/config/list","sysconfig_list",""),
		NAV_3_2("字典设置","3004,3005,3006,","/v/dict/list","dict_list",""),
		NAV_3_3("字典项明细设置","3007,3008,3009,","/v/dictDetail/list","dict_detail_list",""),
		
		NAV_2("权限管理","2000,2100,2200,2210,2220,2230,2240,2250,2300,2310,2320,2100,2110,2120,2130,2140,","/v/managerList.do","manager_list_OR_role_list_OR_resc_list","icon-cog"),
		NAV_2_1("管理员管理","2100,2110,2120,2130,2140,","/v/managerList.do","manager_list",""),
		NAV_2_2("角色管理","2200,2210,2220,2230,2240,2250,","/v/roleList.do","role_list",""),
		NAV_2_3("资源管理","2300,2310,2320,","/v/rescList.do","resc_list",""),
		
		NAV_4("用户管理","4000,4001,4002,4003,4004,4005,4006,4007,4008,4009,4010,4011,4012,4013,4014,4015,","/nv/user/manager","user_list_OR_user_edit_init_OR_wallet_list_OR_withdraw_list_OR_walletchange_list_OR_pay_list_OR_message_list_OR_myproject_list_OR_bank_list","icon-user-md"),
		NAV_4_1("注册用户","4002,","/v/user/editInit","user_edit_init",""),
		NAV_4_2("用户列表","4001,4003,4004,","/v/user/list","user_list",""),
		NAV_4_3("用户钱包","4005,4006,4007,4008,","/v/wallet/list","wallet_list",""),
		NAV_4_4("用户提现","4009,4010,","/v/withdraw/list","withdraw_list",""),
		NAV_4_5("用户余额变更记录","4011,","/v/walletchange/list","walletchange_list",""),
		NAV_4_6("用户支付信息","4012,","/v/pay/list","pay_list",""),
		NAV_4_7("用户消息列表","4013,","/v/message/list","message_list",""),
		NAV_4_8("用户收益列表","4014,","/v/myproject/list","myproject_list",""),
		NAV_4_9("用户银行卡列表","4015,","/v/bank/list","bank_list",""),
		
		NAV_6("项目管理","6001,6002,6003,6004,6005,","/v/project/list","project_list_OR_project_edit_init","icon-tasks"),
		NAV_6_1("发布抢购项目","6002,","/v/project/editInit","project_edit_init",""),
		NAV_6_2("项目列表","6001,6003,6004,","/v/project/list","project_list",""),
		
		
		NAV_8("订单","8001,8002,","/v/order/list","order_list","icon-inbox"),
		NAV_8_1("订单列表 ","8001,8002,","/v/order/list","order_list",""),
		
		NAV_9("猪肉券","9001,9002,9003,9004,9005,","/v/mycoupon/list","mycoupon_list_OR_mycoupon_use_list_OR_present_coupon_list_OR_present_pig_list","icon-inbox"),
		NAV_9_1("用户猪肉券","9001,","/v/mycoupon/list","mycoupon_list",""),
		NAV_9_2("猪肉券使用记录","9002,","/v/mycoupon/use/list","mycoupon_use_list",""),
		NAV_9_3("猪肉券赠送记录","9003,","/v/present/coupon/list","present_coupon_list",""),
		NAV_9_4("猪仔赠送记录","9004,","/v/present/pig/list","present_pig_list",""),
		//NAV_9_5("赠送猪肉券/猪仔","9005,","/v/present/editInit","present_editInit",""),
		
		NAV_5("资讯管理","5001,5002,5003,5004,5005,5006,5007,5008,5009,5010,5011,","/v/bulletin/list","bulletin_edit_init_OR_bulletin_list_OR_info_edit_init_OR_info_list_OR_info_sort_list","icon-th-list"),
		NAV_5_1("发布公告","5002,","/v/bulletin/editInit","bulletin_edit_init",""),
		NAV_5_2("公告列表","5001,5003,5004,","/v/bulletin/list","bulletin_list",""),
		NAV_5_3("发布资讯","5006,","/v/info/editInit","info_edit_init",""),
		NAV_5_4("资讯列表","5005,5007,5008,","/v/info/list","info_list",""),
		NAV_5_5("资讯分类管理","5009,","/v/info/sort/list","info_sort_list",""),
		
		NAV_7("线下门店","7001,7002,7003,7004,","/v/shop/list","shop_edit_init_OR_shop_list","icon-shopping-cart"),
		NAV_7_1("新增线下门店","7002,","/v/shop/editInit","shop_edit_init",""),
		NAV_7_2("线下门店 ","7001,7003,7004,","/v/shop/list","shop_list","");
		
		private String name;//显示名称
		private String status;//状态码
		private String link;//跳转链接
		private String code;//权限代码
		private String icon;//商标图片
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		private PageView(String name, String status, String link, String code,String icon) {
			this.name = name; 
			this.status = status;
			this.link = link;
			this.code = code;
			this.icon = icon;
		}
	}
	
	public static Map<PageView, List<PageView>> getViewMap(){
		LinkedHashMap<PageView, List<PageView>> map = new LinkedHashMap<PageView, List<PageView>>(); 
		List<PageConstant.PageView> result = null;
		for(PageView view : PageView.values()){
			if(view.getName().equals(PageConstant.PageView.NAV_1.getName())){
				result = new ArrayList<PageView>();
				result.add(PageView.NAV_1_1);
				result.add(PageView.NAV_1_2);
				map.put(view,result);
			}else if(view.getName().equals(PageConstant.PageView.NAV_2.getName())){
				result = new ArrayList<PageView>();
				result.add(PageView.NAV_2_1);
				result.add(PageView.NAV_2_2);
				result.add(PageView.NAV_2_3);
				map.put(view,result);
			}else if(view.getName().equals(PageConstant.PageView.NAV_3.getName())){
				result = new ArrayList<PageView>();
				result.add(PageView.NAV_3_5);
				result.add(PageView.NAV_3_6);
				result.add(PageView.NAV_3_4);
				result.add(PageView.NAV_3_7);
				result.add(PageView.NAV_3_8);
				result.add(PageView.NAV_3_1);
				result.add(PageView.NAV_3_2);
				result.add(PageView.NAV_3_3);
				map.put(view,result);
			}else if(view.getName().equals(PageConstant.PageView.NAV_4.getName())){
				result = new ArrayList<PageView>();
				result.add(PageView.NAV_4_1);
				result.add(PageView.NAV_4_2);
				result.add(PageView.NAV_4_3);
				result.add(PageView.NAV_4_4);
				result.add(PageView.NAV_4_5);
				result.add(PageView.NAV_4_6);
				result.add(PageView.NAV_4_7);
				result.add(PageView.NAV_4_8);
				result.add(PageView.NAV_4_9);
				map.put(view,result);
			}else if(view.getName().equals(PageConstant.PageView.NAV_5.getName())){
				result = new ArrayList<PageView>();
				result.add(PageView.NAV_5_1);
				result.add(PageView.NAV_5_2);
				result.add(PageView.NAV_5_3);
				result.add(PageView.NAV_5_4);
				result.add(PageView.NAV_5_5);
				map.put(view,result);
			}else if(view.getName().equals(PageConstant.PageView.NAV_6.getName())){
				result = new ArrayList<PageView>();
				result.add(PageView.NAV_6_1);
				result.add(PageView.NAV_6_2);
				map.put(view,result);
			}else if(view.getName().equals(PageConstant.PageView.NAV_8.getName())){
				result = new ArrayList<PageView>();
				result.add(PageView.NAV_8_1);
				map.put(view,result);
			}else if(view.getName().equals(PageConstant.PageView.NAV_7.getName())){
				result = new ArrayList<PageView>();
				result.add(PageView.NAV_7_1);
				result.add(PageView.NAV_7_2);
				map.put(view,result);
			}else if(view.getName().equals(PageConstant.PageView.NAV_9.getName())){
				result = new ArrayList<PageView>();
				result.add(PageView.NAV_9_1);
				result.add(PageView.NAV_9_2);
				result.add(PageView.NAV_9_3);
				result.add(PageView.NAV_9_4);
//				result.add(PageView.NAV_9_5);
				map.put(view,result);
			}
		}
		return map;
	}
	
	/**
	 * common.jsp页面加载
	 * @author Administrator
	 *
	 */
	public enum PageMsg{
	
		
	    NAV_HOME_LOG_0(1100,"后台首页-操作日志列表"),
		NAV_MENU_0(2000,"权限管理 -系统设置"),
		NAV_ROLE_1(2200,"权限管理 -角色管理"),
		NAV_ROLE_2(2210,"权限管理 -角色管理-添加角色"),
		NAV_ROLE_3(2220,"权限管理 -角色管理-编辑角色"),
		NAV_ROLE_4(2230,"权限管理 -角色管理-用户分配"),
		NAV_ROLE_5(2240,"权限管理 -角色管理-权限分配"),
		NAV_ROLE_6(2250,"权限管理 -角色管理-可选角色分配"),
		NAV_RESC_1(2300,"权限管理 -资源管理"),
		NAV_RESC_2(2310,"权限管理 -资源管理-添加资源"),
		NAV_RESC_3(2320,"权限管理 -资源管理-编辑资源"),
		NAV_MANAGER_1(2100,"权限管理 -管理员管理"),
		NAV_MANAGER_2(2110,"权限管理 -管理员管理-添加管理员"),
		NAV_MANAGER_3(2120,"权限管理 -管理员管理-编辑管理员"),
		NAV_MANAGER_4(2130,"权限管理 -管理员管理-查看管理员"),
		NAV_MANAGER_5(2140,"权限管理 -管理员管理-管理员用户分配子账号"),
		NAV_SYSCONFIG_1(3001,"系统管理-系统配置列表"),
		NAV_SYSCONFIG_2(3002,"系统管理-增加系统配置"),
		NAV_SYSCONFIG_3(3003,"系统管理-编辑系统配置"),
		NAV_SYSCONFIG_4(3010,"系统管理-系统配置列表"),
		NAV_SYSCONFIG_5(3011,"系统管理-编辑系统配置"),
		NAV_SYSCONFIG_6(3012,"系统管理-系统基本设置"),
		NAV_SYSCONFIG_7(3013,"系统管理-在线客服设置"),
		NAV_SYSCONFIG_8(3014,"系统管理-编辑系统基本设置"),
		NAV_SYSCONFIG_9(3015,"系统管理-编辑在线客服设置"),
		NAV_SYSCONFIG_10(3016,"系统管理-首页幻灯"),
		NAV_SYSCONFIG_11(3017,"系统管理-增加首页幻灯"),
		NAV_SYSCONFIG_12(3018,"系统管理-编辑首页幻灯"),
		NAV_SYSCONFIG_13(3019,"系统管理-服务协议设置"),
		NAV_SYSCONFIG_14(3020,"系统管理-编辑服务协议"),
		
		NAV_DICT_1(3004,"系统配置-字典配置-字典配置列表"),
		NAV_DICT_2(3005,"系统配置-字典配置-增加字典配置"),
		NAV_DICT_3(3006,"系统配置-字典配置-编辑字典配置"),
		NAV_DICT_4(3007,"系统配置-字典配置-字典项明细列表"),
		NAV_DICT_5(3008,"系统配置-字典配置-增加字典项明细"),
		NAV_DICT_6(3009,"系统配置-字典配置-编辑字典项明细"),
		

		NAV_INFO_1(5001,"公告资讯-公告列表"),
		NAV_INFO_2(5002,"公告资讯-发布公告"),
		NAV_INFO_3(5003,"公告资讯-编辑公告"),
		NAV_INFO_4(5004,"公告资讯-查看公告"),
		NAV_INFO_5(5005,"公告资讯-资讯列表"),
		NAV_INFO_6(5006,"公告资讯-发布资讯"),
		NAV_INFO_7(5007,"公告资讯-编辑资讯"),
		NAV_INFO_8(5008,"公告资讯-查看资讯"),
		NAV_INFO_9(5009,"公告资讯-资讯分类列表"),
		NAV_INFO_10(5010,"公告资讯-新增资讯分类"),
		NAV_INFO_11(5011,"公告资讯-编辑资讯分类"),
		
		NAV_PROJECT_1(6001,"项目-项目列表"),
		NAV_PROJECT_2(6002,"项目-发布项目"),
		NAV_PROJECT_3(6003,"项目-编辑项目"),
		NAV_PROJECT_4(6004,"项目-查看项目"),
		
		NAV_SHOP_1(7001,"线下门店-线下门店列表"),
		NAV_SHOP_2(7002,"线下门店-新增线下门店"),
		NAV_SHOP_3(7003,"线下门店-编辑线下门店"),
		NAV_SHOP_4(7004,"线下门店-查看线下门店"),
		
		NAV_USER_0(4000,"用户-用户管理"),
		NAV_USER_1(4001,"用户-用户列表"),
		NAV_USER_2(4002,"用户-用户注册"),
		NAV_USER_3(4003,"用户-用户编辑"),
		NAV_USER_4(4004,"用户-用户查看"),
		NAV_USER_5(4005,"用户-用户钱包"),
		NAV_USER_6(4006,"用户-新增用户钱包"),
		NAV_USER_7(4007,"用户-编辑用户钱包"),
		NAV_USER_8(4008,"用户-查看用户钱包"),
		NAV_USER_9(4009,"用户-提现记录"),
		NAV_USER_10(4010,"用户-提现记录查看"),
		NAV_USER_11(4011,"用户-余额变更记录"),
		NAV_USER_12(4012,"用户-用户支付信息"),
		NAV_USER_13(4013,"用户-消息列表"),
		NAV_USER_14(4014,"用户-用户收益列表"),
		NAV_USER_15(4015,"用户-用户银行卡列表"),
		
		NAV_MYCOUPON_1(9001,"猪肉券-用户猪肉券"),
		NAV_MYCOUPON_2(9002,"猪肉券-猪肉券使用记录"),
		NAV_MYCOUPON_3(9003,"猪肉券-猪肉券赠送记录"),
		NAV_MYCOUPON_4(9004,"猪肉券-猪仔赠送记录"),
		NAV_MYCOUPON_5(9005,"猪肉券-赠送猪肉券/猪仔"),
		
		
//		NAV_9("猪肉券","9001,9002,","/v/order/list","order_list","icon-inbox"),
//		NAV_4_9("用户猪肉券","4017,","/v/mycoupon/list","mycoupon_list",""),
//		NAV_4_10("猪肉券使用记录","4018,","/v/mycoupon/use/list","mycoupon_use_list",""),
//		NAV_4_8("猪肉券/猪仔赠送记录","4014,4015,4016,","/v/present/list","present_list",""),
		
		NAV_ORDER_1(8001,"订单-订单列表"),
		NAV_ORDER_2(8002,"订单-订单查看"),
		
	    NAV_HOME(1000,"后台首页");
	    
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
	
		PageMsg(int status,String msg)
		{
			this.status=status;
			this.msg=msg;
		}
	}
	
	public static String getMsg(int status){
		String msg = "";
        for(PageMsg s :PageMsg.values()){
        	if(s.status==status){
        		msg = s.msg;
        		break;
        	}
        }
        return msg;
    }
	
	
	private String name;
	private String status;
	private String link;
	private String code;
	private List<PageConstant> childlist;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<PageConstant> getChildlist() {
		return childlist;
	}

	public void setChildlist(List<PageConstant> childlist) {
		this.childlist = childlist;
	}
	
}
