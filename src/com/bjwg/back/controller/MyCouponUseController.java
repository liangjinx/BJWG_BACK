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
import com.bjwg.back.model.MyCouponUse;
import com.bjwg.back.service.DictDetailService;
import com.bjwg.back.service.MyCouponService;
import com.bjwg.back.service.MyCouponUseService;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class MyCouponUseController extends BaseAction{
	
	@Autowired
	private MyCouponUseService myCouponUseService;
	@Autowired
	private DictDetailService dictDetailService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/mycoupon/use/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_MYCOUPON_2.getStatus(), request);
        Pages<MyCouponUse> page = new Pages<MyCouponUse>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
        //使用类型列表
        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_MY_COUPON_USE_USE_TYPE);
        request.setAttribute("typelist", list);
        
        
        String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		SearchVo vo = new SearchVo();
		vo.setStartTime(startTime);
		vo.setEndTime(endTime);
        
		//分页查询菜单信息
		myCouponUseService.queryPage(page,vo);
        List<MyCouponUse> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
        return "mycouponuse/list";
	}
}
