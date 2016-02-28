package com.bjwg.back.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.constant.CommConstant;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Resc;
import com.bjwg.back.model.Role;
import com.bjwg.back.service.LoginService;
import com.bjwg.back.service.ManagerService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.OrderService;
import com.bjwg.back.service.RoleService;
import com.bjwg.back.service.UserService;
import com.bjwg.back.service.WithdrawService;
import com.bjwg.back.util.MD5;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.vo.HomeView;

/**
 * 后台登录action
 * @author Kim
 * @version 创建时间：2015-8-5 下午04:53:58
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 * 备注：nv是not validate缩写，表示该路径不需要进行登录验证
 *      v表示需要进行验证
 */
@Controller
@Scope("prototype")
public class LoginController extends BaseAction{
	
	@Autowired
    private LoginService loginService;
	@Autowired
	private ManagerService managerService;
    @Autowired
    private RoleService roleService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private WithdrawService withdrawService;
	@Autowired
	private UserService userService;
	@Autowired
	private OperationLogService operationLogService;
	
	/**
	 * 后台登录 
	 * @throws Exception
	 */
	@RequestMapping(value = "/nv/login.do" , method = {RequestMethod.POST ,RequestMethod.GET})
	public String login(String username,String password,HttpServletRequest request) throws Exception{
	    
	    
	    ModelAndView view = new ModelAndView();
	    
	    view.setViewName("login");
	    
	    //用户名和密码都不能为空
	    if(StringUtils.isAllNotEmpty(username,password)){
	        
	        //根据用户名查询
	        Manager m = managerService.queryByName(username);
	        String pwd = MD5.GetMD5Code(password);
	        //查询管理员资料
	        String loginIp = MyUtils.getIpAddr(request);
	        int status = loginService.updateLogin(m,pwd,loginIp);
	        
	        boolean isSuccess = StatusConstant.Status.SUCCESS.getStatus() == status;
	        
	        noticeMsg(StatusConstant.getMsg(status),request);
	        
	        //登录成功
	        if(isSuccess){
	        	
	            HttpSession session = request.getSession();
	            
	            session.setAttribute(CommConstant.SESSION_MANAGER_LOGIN_FLAG, CommConstant.SUCCESS);
	            
	            m.setPassword(null);
	            
	            session.setAttribute(CommConstant.SESSION_MANAGER, m);
	            
	            //查询当前用户对应的权限
	            session.setAttribute(CommConstant.SESSION_MANAGER_AUTHORITY_LINK, loginService.obtainCurrentManagerAuthority(m.getManagerId().longValue()));
	            //是否可以查看所有数据
	            session.setAttribute(CommConstant.SESSION_ISVIEW_ALLDATA, loginService.isViewAllData(m.getManagerId().longValue()));
	            //是否有操作子账号权限
	            session.setAttribute(CommConstant.SESSION_ISCREATE_CHILD, loginService.isCreateChild(m.getManagerId().longValue()));
	            //是否有查看子账号数据的权限
	            session.setAttribute(CommConstant.SESSION_ISVIEW_CHILD, loginService.isViewChild(m.getManagerId().longValue()));
	            
	           //刷新一下菜单
	           // menuService.refreshMenu();
	            
	            //展示有权限的菜单
	           // this.listMenu(request);
	            
	            //后台首页导航
	            this.navIndex(PageConstant.PageMsg.NAV_HOME.getStatus(), request);
				//记录日志
				Manager user = getLoginUser(request);
				operationLogService.oplog(null,"",OperationLogConstant.OPE_MODULE_TYPE_LOGIN,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SYSCONFIG,"登入IP:"+loginIp);
	            return start(request);
	            
	        }
	        
	    }
	    
	    return "login";
	}
	
	/**
	 * 点击开始按钮 
	 * @throws Exception
	 */
	@RequestMapping(value = "/" , method = {RequestMethod.POST ,RequestMethod.GET})
	public String welcome(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession(false);
		if(session == null){
			return "login";
		}
		String loginFlag = (String)session.getAttribute(CommConstant.SESSION_MANAGER_LOGIN_FLAG);
		if(!StringUtils.isNotEmpty(loginFlag) || !CommConstant.SUCCESS.equals(loginFlag)){
			return "login";
		}
	  //后台首页导航
	  this.navIndex(PageConstant.PageMsg.NAV_HOME.getStatus(), request);
	  return "home";
	}
	
	/**
	 * 点击开始按钮 
	 * @throws Exception
	 */
	@RequestMapping(value = "/v/home.do" , method = {RequestMethod.POST ,RequestMethod.GET})
	public String start(HttpServletRequest request) throws Exception{
		
		//后台首页导航
		this.navIndex(PageConstant.PageMsg.NAV_HOME.getStatus(), request);
		Long managerId = getLoginUser(request).getManagerId();
		List<Role> roles = roleService.getRolesByUserId(managerId);
		List<Resc> rescs = loginService.obtainCurrentManagerAuthority(managerId.longValue());
		request.setAttribute("roles",roles);
		request.setAttribute("rescs",rescs);
		HomeView order = orderService.getOrderCount((byte)1);
		HomeView withdraw = withdrawService.getWithdrawCount((byte)1);
		HomeView user = userService.getUserCount((byte)1);
		HomeView homeView = new HomeView();
		homeView.setOrderCount(order.getOrderCount());
		homeView.setOrderTotalMoney(order.getOrderTotalMoney());
		homeView.setWaitOrderCount(order.getWaitOrderCount());
		homeView.setWithdrawCount(withdraw.getWithdrawCount());
		homeView.setWithdrawMoney(withdraw.getWithdrawMoney());
		homeView.setUserCount(user.getUserCount());
		request.setAttribute("homeView",homeView);
		
	  return "home";
	}
	
	/**
	 * 后台注销
	 * @throws Exception
	 */
	@RequestMapping(value = "/nv/logout.do" , method = {RequestMethod.POST ,RequestMethod.GET})
	public ModelAndView logout(HttpServletRequest request) throws Exception{
		//记录日志
		Manager user = getLoginUser(request);
		if(user!=null){
			operationLogService.oplog(null,"",OperationLogConstant.OPE_MODULE_TYPE_LOGOUT,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SYSCONFIG,"");
		}
	    HttpSession session = request.getSession();
	    
	    session.removeAttribute(CommConstant.SESSION_MANAGER_LOGIN_FLAG);
	    
	    session.removeAttribute(CommConstant.SESSION_MANAGER);
	    
	    ModelAndView view = new ModelAndView("login");
	    return view;
	}
	/**
	 * 得到用户的统计信息
	 * @param type 1、当日;2、本周;3、本月
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/nv/index/user/count", method = RequestMethod.POST)
	@ResponseBody
	public void getUserCount(byte type, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			HomeView order = userService.getUserCount(type);
			
			JSONObject obj = JSONObject.fromObject(order);
			
			PrintWriter out = response.getWriter();
			System.out.println("json0=="+obj.toString());
			out.write(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到订单的统计信息
	 * @param type 1、当日;2、本周;3、本月
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/nv/index/order/count", method = RequestMethod.POST)
	@ResponseBody
	public void getOrderCount(byte type, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			HomeView order = orderService.getOrderCount(type);
			
			JSONObject obj = JSONObject.fromObject(order);
			
			PrintWriter out = response.getWriter();
			System.out.println("json1=="+obj.toString());
			out.write(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 得到提现的统计信息
	 * @param type 1、当日;2、本周;3、本月
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/nv/index/withdraw/count", method = RequestMethod.POST)
	@ResponseBody
	public void getWithdrawCount(byte type, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			HomeView order = withdrawService.getWithdrawCount(type);
			
			JSONObject obj = JSONObject.fromObject(order);
			
			PrintWriter out = response.getWriter();
			System.out.println("json2=="+obj.toString());
			out.write(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
