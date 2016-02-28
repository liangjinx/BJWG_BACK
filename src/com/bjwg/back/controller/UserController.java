package com.bjwg.back.controller;

import java.util.ArrayList;
import java.util.List;


import java.util.Date;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;





import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.base.constant.StatusConstant.Status;
import com.bjwg.back.model.BankCard;
import com.bjwg.back.model.BankCardExample;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.User;
import com.bjwg.back.model.UserExt;
import com.bjwg.back.model.UserPreorder;
import com.bjwg.back.model.epUser;
import com.bjwg.back.service.BankCardService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.UserService;
import com.bjwg.back.service.WithdrawService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.vo.HomeView;
import com.bjwg.back.vo.SearchVo;

/**
 * 用户管理controller
 * @author Kim
 * @version 创建时间：2015-8-6 上午10:32:54
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
@Controller
@Scope("prototype")
public class UserController extends BaseAction
{

    @Autowired
    private UserService userService;
	@Autowired
	private OperationLogService operationLogService;
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private WithdrawService withdrawService;
	protected JSONObject jsonResponseResult;
    /**
     * 用户列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/user/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String userList(HttpServletRequest request){
    	this.navIndex(PageConstant.PageMsg.NAV_USER_1.getStatus(), request);
        Pages<User> page = new Pages<User>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		SearchVo vo = new SearchVo();
		vo.setStartTime(startTime);
		vo.setEndTime(endTime);
		page.setWhereSql(whereSql);
		String userType = request.getParameter("userType");//1、八戒平台用户;2、微信用户
		
		
		//分页查询菜单信息
		try {
			userService.queryPage(page,vo);
			logger.debug("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询用户列表失败!",e);
		}
        List<User> userList = page.getRoot();
        
       
        request.setAttribute("list", userList);
        request.setAttribute("page", page);
        request.setAttribute("userType", userType);
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        return "user/list";
    }  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //查看用户预抢
    
    
	@RequestMapping(value = "/v/user/myPreorder",method = {RequestMethod.POST, RequestMethod.GET})
	public String myPigFarm(Long userId,HttpServletRequest request){
		try {
if(userId==null){
	userId=(Long) request.getSession().getAttribute("uid");
}
		request.getSession().setAttribute("uid",userId);
		
			//Map<String, Object> dataMap = myEarningsService.selectMyFarmData(userId, earningsId);
			//获取本地时间（小时）
			//request.setAttribute("hours", MyUtils.dateFormat8(new Date()));
			
		
			UserExt userExt = userService.selectUserExtByPrimaryKey(userId);
	
			request.setAttribute("userExt", userExt);
			
			List<UserPreorder> list = userService.selectLoadNameByUserId(userId);
			//System.out.println("size="+list.size());
			request.setAttribute("preOrderList", list);
			
			//request.setAttribute("totalEr", userService.selectUserCenterData(userId).getEarnings());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("进入农场错误", e);
		}
		return "user/myPreorder";
	}
	
	
	
	/**
	 * 抢标设置保存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/v/user/preOrderSave", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String preOrderSave() throws Exception{
		try {
			
			
			Long userId=(Long) request.getSession().getAttribute("uid");
			
			String settingType = request.getParameter("settingType");
			
			String projectId = request.getParameter("projectId");
			
			String num = request.getParameter("num");
			
			String cancel = request.getParameter("cancel");
			
			//不是取消一定要传“false”到后台
			boolean isCancel = !(StringUtils.isNotEmpty(cancel) && "false".equals(cancel));
			
			Status status = userService.updateUserPreorder(userId,projectId,num,settingType,isCancel);
			
			System.out.println("status"+status);
			this.putJsonStatus(status);
			//System.out.println("json= "+this.jsonResponseResult);
		} catch (Exception e) {
			e.printStackTrace();
	
			this.putJsonStatus(Status.serverError);
			throw e;
		}
		
		return this.outputJson();
	}
	
    
    
    
    
    
    
    /**
     * 用户管理编辑初始化
     * @param userId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/user/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String userEditInit(Long userId,HttpServletRequest request) throws Exception{
        
        User user = null;
        //修改
        if(MyUtils.isLongGtZero(userId)){
        	try {
				user = userService.getById(userId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("查询用户信息失败!",e);
			}
			this.navIndex(PageConstant.PageMsg.NAV_USER_3.getStatus(), request);
        	request.setAttribute("currentPage", currentPage);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_USER_2.getStatus(), request);
        	user = new User();
        }
        if(user.getFlag()==2){
        	request.setAttribute("fg", "2");
        	System.out.println("id="+userId);
        	epUser eu=userService.getepUser(userId);
            user.setAddress(eu.getAddress());
            user.setLegalperson(eu.getLegalperson());
            user.setBusinessLicense(eu.getBusinessLicense());
        }
        
        
        request.setAttribute("user",user);
        setPhotoDomain(request);
        return "user/save";
    } 
    
    /**
     * 用户查看
     * @param userId 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/user/view", method = {RequestMethod.POST, RequestMethod.GET})
    public String userView(Long userId,HttpServletRequest request){
        
    	this.navIndex(PageConstant.PageMsg.NAV_USER_3.getStatus(), request);
        User user = null;
        String type = request.getParameter("type");
        //修改
        if(MyUtils.isLongGtZero(userId)){
        	try {
				user = userService.getById(userId);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("查询用户信息失败!",e);
			}
        	if(user!=null){
	        	request.setAttribute("user",user);
	        	setPhotoDomain(request);
        	}
        }
        if(type!=null && type.equals("dialog")){
        	return "user/view_dialog";
        }
        return "user/view";
    } 
    /**
     * 用户管理编辑
     */
    /**
     * @param user
     * @param bindingResult
     * @param nopath
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/user/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String userEdit(User user,HttpServletRequest request){
    	try{
    		System.out.println("user获取"+user.getPhone());
	        int status = userService.saveOrupdate(user, request);
	        noticeMsg(StatusConstant.getMsg(status),request);
			if(status == 1){
				//记录日志
				Manager login = getLoginUser(request);
				operationLogService.oplog(user.getUserId(),user.getUsername(),OperationLogConstant.OPE_MODULE_TYPE_UPDATE,login.getManagerId(),login.getUsername(),OperationLogConstant.OPE_MODULE_USER,"");
				return userList(request);
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return "user/save"; 
    } 
    
	/**
	 * 冻结用户
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/v/user/freeze",method = {RequestMethod.POST, RequestMethod.GET})
	public String freeze(Long userId, HttpServletRequest request){
		try {
			int status = userService.batchCommitFreeze(userId);
			if(status==1){
				//记录日志
				Manager manager = getLoginUser(request);
				User user = userService.getById(userId);
				String opeObject = "";
				if(user!=null){ opeObject = user.getUsername();}
				operationLogService.oplog(userId,opeObject,OperationLogConstant.OPE_MODULE_TYPE_FREEZE,manager.getManagerId(),manager.getUsername(),OperationLogConstant.OPE_MODULE_USER,"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList(request);
	}
    
    /**
     * 用户删除
     * @param userId
     * @param idList
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/user/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String managerDelete(Long userId,String idList,HttpServletRequest request){
        try{
	        List<Long> ids = null;
	        if(!StringUtils.isNotEmpty(idList)){
	            ids = new ArrayList<Long>();
	            ids.add(userId);
	        }else{
	            ids = MyUtils.convertToLongList(idList, ",");
	        }
	        int status = 0;
			try {
				status = userService.delete(ids);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息失败!",e);
			}
			
		
			//删除企业用户表数据
			userService.deleteEpusers(ids);
			
			
	        noticeMsg(StatusConstant.getMsg(status),request);
			for (Long id : ids){
				if(MyUtils.isLongGtZero(id)){
					//记录日志
					Manager manger = getLoginUser(request);
					User user = null;
					try {
						user = userService.getById(id);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("查询用户信息失败!",e);
					}
					String opeObject = "";
					if(user!=null){ opeObject = user.getUsername();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,manger.getManagerId(),manger.getUsername(),OperationLogConstant.OPE_MODULE_USER,"");
				}
			}
        }catch(Exception e){
        	e.printStackTrace();
        }
        return userList(request);
    } 
    
    /**
	 *  验证导出页面
	 * @param request
	 * @param response
	 * @return
	 * @user Kim
	 */
	@RequestMapping(value = "/v/user/checkExport", method = { RequestMethod.POST,RequestMethod.GET })
	public void checkExportList(HttpServletRequest request,HttpServletResponse response) {
		try {
			//查询条件
	        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
	        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
	        String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			SearchVo vo = new SearchVo();
			vo.setStartTime(startTime);
			vo.setEndTime(endTime);
			String userType = request.getParameter("userType");//1、八戒平台用户;2、微信用户
			vo.setUserType(userType);
			int status = userService.checkExportList(whereSql,vo);
			
		
			if(status < 0){
				jsonOut(response,StatusConstant.getMsg(status));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导出页面
	 * @param request
	 * @param response
	 * @return
	 * @user Kim
	 */
	@RequestMapping(value = "/v/user/export", method = { RequestMethod.POST,RequestMethod.GET })
	public void exportListShop(HttpServletRequest request,HttpServletResponse response) {
		try {
	        
			int status = userService.exportList(request,response);
			if(status==1){
				//记录日志
				Manager user = getLoginUser(request);
				operationLogService.oplog(null,"",OperationLogConstant.OPE_MODULE_TYPE_EXPORT,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_ORDER,"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	
	/**
     * 银行卡列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/bank/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String bankList(HttpServletRequest request){
    	this.navIndex(PageConstant.PageMsg.NAV_USER_15.getStatus(), request);
        Pages<BankCard> page = new Pages<BankCard>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
		page.setWhereSql(whereSql);
		//分页查询菜单信息
		try {
			BankCardExample example = new BankCardExample();
			
			BankCardExample.Criteria criteria = example.createCriteria();
			
			if(StringUtils.isNotEmpty(whereSql)){
				
				whereSql = "1=1 "+whereSql;
				criteria.addCriterion(whereSql);
			}
			
			bankCardService.queryPage(page,example);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询用户列表失败!",e);
		}
        List<BankCard> list = page.getRoot();
        request.setAttribute("list", list);
        request.setAttribute("page", page);
        return "bank/list";
    }  
    /**
	 * 用户管理
	 * @throws Exception
	 */
	@RequestMapping(value = "/nv/user/manager" , method = {RequestMethod.POST ,RequestMethod.GET})
	public String userManager(HttpServletRequest request) throws Exception{
		//后台首页导航
		this.navIndex(PageConstant.PageMsg.NAV_USER_0.getStatus(), request);
		HomeView withdraw = withdrawService.getWithdrawCount((byte)1);
		HomeView user = userService.getUserCount((byte)1);
		HomeView homeView = new HomeView();
		homeView.setWithdrawCount(withdraw.getWithdrawCount());
		homeView.setWithdrawMoney(withdraw.getWithdrawMoney());
		homeView.setUserCount(user.getUserCount());
		request.setAttribute("homeView",homeView);
	  return "user/manager";
	}
}
