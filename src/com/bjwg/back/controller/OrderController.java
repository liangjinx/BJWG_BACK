package com.bjwg.back.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.CommConstant;
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.OrderConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.base.constant.SysConstant;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.MyEarnings;
import com.bjwg.back.model.MyEarningsExample;
import com.bjwg.back.model.Order;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.service.DictDetailService;
import com.bjwg.back.service.MyEarningsService;
import com.bjwg.back.service.MyProjectService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.OrderService;
import com.bjwg.back.service.SysconfigService;
import com.bjwg.back.service.UserService;
import com.bjwg.back.vo.SearchVo;
import com.bjwg.back.model.User;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.ConsoleUtil;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class OrderController extends BaseAction{
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private DictDetailService dictDetailService;
	@Autowired
	private MyProjectService myProjectService;
	@Autowired
	private OperationLogService operationLogService;
	
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/order/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request){
		try{
			//用于 商户列表 查看订单 显示调用
			setUserName(request);
			 //系统管理导航
			this.navIndex(PageConstant.PageMsg.NAV_ORDER_1.getStatus(), request);
	        Pages<Order> page = new Pages<Order>();
	        page.setCurrentPage(currentPage);
	        page.setPerRows(10);
	        
	        //查询条件
	        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
	        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
	        page.setWhereSql(whereSql);
	        //下单时间
	        String startTime = request.getParameter("cStartTime");
			String endTime = request.getParameter("cEndTime");
			//付款时间
			String startTime2 = request.getParameter("pStartTime");
			String endTime2 = request.getParameter("pEndTime");
			
	        SearchVo vo = new SearchVo();
	        vo.setStartTime(startTime);
	        vo.setEndTime(endTime);
	        vo.setStartTime2(startTime2);
	        vo.setEndTime2(endTime2);
			//分页查询菜单信息
	        orderService.queryPage(page,vo);
	        List<Order> logList = page.getRoot();
	        request.setAttribute("list", logList);
	        request.setAttribute("page", page);
	        //订单类型
	        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_ORDER_TYPE);
	        request.setAttribute("typelist", list);
	        //订单状态
	        List<DictDetail> statuslist = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_ORDER_STATUS);
	        request.setAttribute("statuslist", statuslist);
	        //支付方式
	        List<DictDetail> paytypelist = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_ORDER_PAY_TYPE);
	        request.setAttribute("payTypes", paytypelist);
	        
			request.setAttribute("cStartTime", startTime);
			request.setAttribute("cEndTime", endTime);
			request.setAttribute("pStartTime", startTime2);
			request.setAttribute("pEndTime", endTime2);
			request.setAttribute("ORDER_STATUS_4", OrderConstant.ORDER_STATUS_4);
			request.setAttribute("ORDER_TYPE_3", OrderConstant.ORDER_TYPE_3);
		}catch(Exception e){
			e.printStackTrace();
		}
        return "order/list";
	}
	/**
	 * 跳转到订单查看页面
	 * @param id 订单Id
	 * @param request
	 * @return 
	 */
	@RequestMapping(value="/v/order/view",method = {RequestMethod.POST, RequestMethod.GET})
	public String toView(@RequestParam("orderId") Long orderId,HttpServletRequest request){
		try {
			 //系统管理导航
	        this.navIndex(PageConstant.PageMsg.NAV_ORDER_2.getStatus(), request);
	        Order order = orderService.getById(orderId);
	        if(order==null){
	        	int status = StatusConstant.Status.ORDER_DEL_NULL.getStatus();
	        	noticeMsg(StatusConstant.getMsg(status),request);
	        	return list(request);
	        }
	        if(order.getUserId()!=null){
	        	User user = userService.getById(order.getUserId());
	        	order.setUsername(user==null?"":user.getUsername());
	        }
	        //订单类型
	        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_ORDER_TYPE);
	        request.setAttribute("typelist", list);
	        //订单状态
	        List<DictDetail> statuslist = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_ORDER_STATUS);
	        request.setAttribute("statuslist", statuslist);
	        //付款方式
	        List<DictDetail> paytypelist = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_ORDER_PAY_TYPE);
	        request.setAttribute("paytypelist", paytypelist);
	        request.setAttribute("order", order);
	        setPhotoDomain(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "order/view";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 确认支付
	 * @param orderId
	 * @param request
	 * @return
	 */
	
	
	@RequestMapping(value="/v/order/confirmPay",method = {RequestMethod.POST, RequestMethod.GET})
	public String confirmPay(@RequestParam("orderId") Long orderId,HttpServletRequest request){
		try {
			String userIdStr = request.getParameter("filter_EQI_t.USER_ID");
			if(StringUtils.isNotEmpty(userIdStr) && MyUtils.isLongGtZero(Long.parseLong(userIdStr))){
	        	Long userId = Long.parseLong(userIdStr);
	        	
	        
	        	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 			
	 			 Order order = orderService.getById(orderId);
	 			String payTime=sdf.format(new Date());
	 	order.setPayTime(sdf.parse(payTime));
	 	order.setStatus((byte)3);
	 	order.setPayType((byte)5);
	 			orderService.confirmOrder(order);
	 			list(request);
			}
			
			
			
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list(request);
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 确认收货
	 * @param id 订单Id
	 * @param request
	 * @return 
	 */
	@RequestMapping(value="/v/order/confirm",method = {RequestMethod.POST, RequestMethod.GET})
	public String confirm(@RequestParam("orderId") Long orderId,HttpServletRequest request){
		try {
			 //系统管理导航
	        this.navIndex(PageConstant.PageMsg.NAV_ORDER_2.getStatus(), request);
	        Order order = orderService.getById(orderId);
	        
	        if(order==null){
	        	int status = StatusConstant.Status.ORDER_DEL_NULL.getStatus();
	        	noticeMsg(StatusConstant.getMsg(status),request);
	        	return list(request);
	        }
	        Order ha = new Order();
	        ha.setOrderId(order.getOrderId());
	        ha.setConfirmTime(new Date());
	        ha.setStatus(OrderConstant.ORDER_STATUS_5);
	        orderService.confirmOrder(ha);
	        //修改我的项目里面 已处理
	        myProjectService.updateDealStatusByUidAndPid(order.getUserId(),order.getRelationId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list(request);
	}
	/**
	 *  验证导出页面
	 * @param request
	 * @param response
	 * @return
	 * @user Kim
	 */
	@RequestMapping(value = "/v/order/checkExport", method = { RequestMethod.POST,RequestMethod.GET })
	public void checkExportList(HttpServletRequest request,HttpServletResponse response) {
		try {
	        //查询条件
	        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
	        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
	        //下单时间
	        String startTime = request.getParameter("cStartTime");
			String endTime = request.getParameter("cEndTime");
			//付款时间
			String startTime2 = request.getParameter("pStartTime");
			String endTime2 = request.getParameter("pEndTime");
			
	        SearchVo vo = new SearchVo();
	        vo.setStartTime(startTime);
	        vo.setEndTime(endTime);
	        vo.setStartTime2(startTime2);
	        vo.setEndTime2(endTime2);
			int status = orderService.checkExportList(whereSql,vo);
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
	@RequestMapping(value = "/v/order/export", method = { RequestMethod.POST,RequestMethod.GET })
	public void exportListShop(HttpServletRequest request,HttpServletResponse response) {
		try {
	        
			int status = orderService.exportList(request,response);
			if(status==1){
				//记录日志
				Manager user = getLoginUser(request);
				operationLogService.oplog(null,"",OperationLogConstant.OPE_MODULE_TYPE_EXPORT,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_ORDER,"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void setUserName(HttpServletRequest request){
		try{
			String userIdStr = request.getParameter("filter_EQI_t.USER_ID");
			if(StringUtils.isNotEmpty(userIdStr) && MyUtils.isLongGtZero(Long.parseLong(userIdStr))){
	        	Long userId = Long.parseLong(userIdStr);
	        	User user = userService.getById(userId);
	        	if(user!=null){
	        		request.setAttribute("username",user.getUsername());
	        	}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
