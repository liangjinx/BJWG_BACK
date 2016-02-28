package com.bjwg.back.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.OperationLog;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.vo.SearchVo;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class LogController extends BaseAction{
	
	@Autowired
	private OperationLogService operationLogService;

	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/log/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_HOME_LOG_0.getStatus(), request);
    	
        Pages<OperationLog> page = new Pages<OperationLog>();
        
        page.setCurrentPage(currentPage);
        
        page.setPerRows(10);
        
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		page.setWhereSql(whereSql);
		
        SearchVo vo = new SearchVo();
        vo.setStartTime(startTime);
        vo.setEndTime(endTime);
        
        // 获取登入user的id
        Manager ma = getLoginUser(request);
		Long managerId = ma.getManagerId();
        
		//分页查询菜单信息
		operationLogService.queryPage(page,vo,isViewOtherData(request),isViewChildData(request),managerId);
        List<OperationLog> logList = page.getRoot();
        
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
        return "log/list";
	}
	
	@ModelAttribute("opemodules")
	public List<String> getOpeModules() {
		List<String> m = new ArrayList<String>();
		m.add(OperationLogConstant.OPE_MODULE_RESC);
		m.add(OperationLogConstant.OPE_MODULE_MANAGER);
		m.add(OperationLogConstant.OPE_MODULE_ROLE);
		m.add(OperationLogConstant.OPE_MODULE_USER);
		m.add(OperationLogConstant.OPE_MODULE_SYSCONFIG);
		m.add(OperationLogConstant.OPE_MODULE_DICT);
		m.add(OperationLogConstant.OPE_MODULE_DICT_DETAIL);
		m.add(OperationLogConstant.OPE_MODULE_BULLETIN);
		m.add(OperationLogConstant.OPE_MODULE_INFO);
		m.add(OperationLogConstant.OPE_MODULE_INFO_SORT);
		m.add(OperationLogConstant.OPE_MODULE_PROJECT);
		m.add(OperationLogConstant.OPE_MODULE_MY_PROJECT);
		m.add(OperationLogConstant.OPE_MODULE_WALLET);
		m.add(OperationLogConstant.OPE_MODULE_WITHDRAW);
		m.add(OperationLogConstant.OPE_MODULE_PRESENT);
		m.add(OperationLogConstant.OPE_MODULE_SHOP);
		m.add(OperationLogConstant.OPE_MODULE_SLIDE);
		m.add(OperationLogConstant.OPE_MODULE_ORDER);
		return m;
	}
	@ModelAttribute("opetypes")
	public List<String> getOpeTypes() {
		List<String> m = new ArrayList<String>();
		m.add(OperationLogConstant.OPE_MODULE_TYPE_ADD);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_UPDATE);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_EXAMINE);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_SHOW);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_HIDE);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_FREEZE);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_DEL);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_FOREIGN_USER);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_FOREIGN_RESC);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_EXPORT);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_IMPORT);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_ADDED);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_SHELVES);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_LOGIN);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_LOGOUT);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_ASSIGN_ROLE);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_ASSIGN_CHILD);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_SEND_AUTH);
		m.add(OperationLogConstant.OPE_MODULE_TYPE_REMOVE_USER);
		return m;
	}

}
