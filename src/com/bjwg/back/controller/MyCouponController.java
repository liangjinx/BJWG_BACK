package com.bjwg.back.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.vo.SearchVo;
import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.MyCoupon;
import com.bjwg.back.service.DictDetailService;
import com.bjwg.back.service.MyCouponService;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class MyCouponController extends BaseAction{
	
	@Autowired
	private MyCouponService mycouponService;
	@Autowired
	private DictDetailService dictDetailService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/mycoupon/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_MYCOUPON_1.getStatus(), request);
        Pages<MyCoupon> page = new Pages<MyCoupon>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
        //状态列表
        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_MY_COUPON_STATUS);
        request.setAttribute("statuslist", list);
        
        //类型列表
        List<DictDetail> typelist = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_MY_COUPON_RELATION_TYPE);
        request.setAttribute("typelist", typelist);
        
        String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		SearchVo vo = new SearchVo();
		vo.setStartTime(startTime);
		vo.setEndTime(endTime);
		
		//分页查询菜单信息
        mycouponService.queryPage(page,vo);
        List<MyCoupon> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
        return "mycoupon/list";
	}
}
