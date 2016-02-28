package com.bjwg.back.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.Pay;
import com.bjwg.back.service.DictDetailService;
import com.bjwg.back.service.PayService;
import com.bjwg.back.vo.SearchVo;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class PayController extends BaseAction{
	
	@Autowired
	private PayService payService;
	@Autowired
	private DictDetailService dictDetailService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/pay/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_USER_12.getStatus(), request);
        Pages<Pay> page = new Pages<Pay>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
        String startTime = request.getParameter("cStartTime");
		String endTime = request.getParameter("cEndTime");
		String startTime2 = request.getParameter("pStartTime");
		String endTime2 = request.getParameter("pEndTime");
		
        SearchVo vo = new SearchVo();
        vo.setStartTime(startTime);
        vo.setEndTime(endTime);
        vo.setStartTime2(startTime2);
        vo.setEndTime2(endTime2);
		//分页查询菜单信息
        payService.queryPage(page,vo);
        List<Pay> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
        
        //支付方式
        List<DictDetail> paytypelist = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_ORDER_PAY_TYPE);
        request.setAttribute("payTypes", paytypelist);
		request.setAttribute("cStartTime", startTime);
		request.setAttribute("cEndTime", endTime);
		request.setAttribute("pStartTime", startTime2);
		request.setAttribute("pEndTime", endTime2);
        
        return "pay/list";
	}
}
