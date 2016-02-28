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
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.model.Message;
import com.bjwg.back.service.MessageService;
import com.bjwg.back.vo.SearchVo;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class MessageController extends BaseAction{
	
	@Autowired
	private MessageService messageService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/message/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_USER_13.getStatus(), request);
        Pages<Message> page = new Pages<Message>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
        String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
        SearchVo vo = new SearchVo();
        vo.setStartTime(startTime);
        vo.setEndTime(endTime);
		//分页查询菜单信息
        messageService.queryPage(page,vo);
        List<Message> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
        return "message/list";
	}
}
