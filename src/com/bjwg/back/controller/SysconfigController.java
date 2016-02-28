package com.bjwg.back.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.base.constant.SystemConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.SysconfigService;
import com.bjwg.back.util.MyUtils;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class SysconfigController extends BaseAction{
	
	@Autowired
	private SysconfigService sysconfigService;
	@Autowired
	private OperationLogService operationLogService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/config/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_1.getStatus(), request);
        Pages<Sysconfig> page = new Pages<Sysconfig>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
      //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
		//分页查询菜单信息
        sysconfigService.queryPage(page);
        List<Sysconfig> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
        
        return "sysconfig/list";
	}
	
    /**
     * 跳转到配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/config/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long id,HttpServletRequest request) throws Exception{
    	Sysconfig sysconfig = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_3.getStatus(), request);
        	sysconfig = sysconfigService.getById(id);
        	request.setAttribute("currentPage", currentPage);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_2.getStatus(), request);
        	sysconfig = new Sysconfig();
        }
        request.setAttribute("sysconfig", sysconfig);
        return "sysconfig/edit";
    } 
    
    /**
     * 系统配置
     * @param sysconfig
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/config/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(Sysconfig sysconfig,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(sysconfig.getId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	int status = sysconfigService.saveOrupdate(sysconfig);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(sysconfig.getId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(sysconfig.getId(),sysconfig.getName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SYSCONFIG,"");
        }
        return list(request);
    }

    /**
     * 资源删除
     */
    @RequestMapping(value="/v/config/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String rescDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = sysconfigService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					Sysconfig resc = sysconfigService.getById(temp);
					String opeObject = "";
					if(resc!=null){ opeObject = resc.getName();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SYSCONFIG,"");
				}
			}
		}
        return list(request);
    } 
    
    
    /**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/user/config/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String userList(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_4.getStatus(), request);
        Pages<Sysconfig> page = new Pages<Sysconfig>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
      //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
		//分页查询菜单信息
        sysconfigService.queryPage(page);
        List<Sysconfig> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
        return "sysconfig/userlist";
	}
	/**
     * 跳转到用户的配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/user/config/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String userEditInit(Long id,HttpServletRequest request) throws Exception{
    	Sysconfig sysconfig = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_5.getStatus(), request);
        	sysconfig = sysconfigService.getById(id);
            //新增
        }
        request.setAttribute("sysconfig", sysconfig);
        return "sysconfig/useredit";
    } 
    
    /**
     * 用户的系统配置
     * @param sysconfig
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/user/config/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String userEdit(Sysconfig sysconfig,HttpServletRequest request) throws Exception{
    	int status = sysconfigService.userUpdate(sysconfig);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return userEditInit(sysconfig.getId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(sysconfig.getId(),sysconfig.getName(),OperationLogConstant.OPE_MODULE_TYPE_UPDATE,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SYSCONFIG,"");
        }
        return userList(request);
    }
    
    /**
	 * 网站查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/site/config/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String siteList(HttpServletRequest request) throws Exception{
		this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_6.getStatus(), request);
        List<Sysconfig> logList = sysconfigService.getListByParentCode(SystemConstant.SYS_MANAGE_SITE);
        request.setAttribute("list", logList);
        return "sysconfig/sitelist";
	}
	/**
     * 跳转网站基本信息配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/site/config/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String siteEditInit(Long id,HttpServletRequest request) throws Exception{
    	Sysconfig sysconfig = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_8.getStatus(), request);
        	sysconfig = sysconfigService.getById(id);
        }
        request.setAttribute("sysconfig", sysconfig);
        return "sysconfig/siteedit";
    } 
    
    /**
     * 网站基本信息配置
     * @param sysconfig
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/site/config/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String siteEdit(Sysconfig sysconfig,HttpServletRequest request) throws Exception{
    	int status = sysconfigService.userUpdate(sysconfig);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return siteEditInit(sysconfig.getId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(sysconfig.getId(),sysconfig.getName(),OperationLogConstant.OPE_MODULE_TYPE_UPDATE,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SYSCONFIG,"");
        }
        return siteList(request);
    }
    
    /**
	 * 在线客服设置
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/kefu/config/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String kefuList(HttpServletRequest request) throws Exception{
		this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_7.getStatus(), request);
        List<Sysconfig> logList = sysconfigService.getListByParentCode(SystemConstant.SYS_MANAGE_CUSTOMER_SERVICE);
        request.setAttribute("list", logList);
        return "sysconfig/kefulist";
	}
	/**
     * 跳转在线客服配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/kefu/config/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String kefuEditInit(Long id,HttpServletRequest request) throws Exception{
    	Sysconfig sysconfig = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_9.getStatus(), request);
        	sysconfig = sysconfigService.getById(id);
        }
        request.setAttribute("sysconfig", sysconfig);
        return "sysconfig/kefuedit";
    } 
    
    /**
     * 在线客服信息配置
     * @param sysconfig
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/kefu/config/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String kefuEdit(Sysconfig sysconfig,HttpServletRequest request) throws Exception{
    	int status = sysconfigService.userUpdate(sysconfig);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return kefuEditInit(sysconfig.getId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(sysconfig.getId(),sysconfig.getName(),OperationLogConstant.OPE_MODULE_TYPE_UPDATE,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SYSCONFIG,"");
        }
        return kefuList(request);
    }
    
}
