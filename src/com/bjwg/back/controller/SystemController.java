package com.bjwg.back.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
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
import com.bjwg.back.service.RescService;
import com.bjwg.back.service.RoleService;
import com.bjwg.back.util.MD5;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.SortBean;
import com.bjwg.back.util.StringUtils;

/**
 * 系统管理controller
 * @author Kim
 * @version 创建时间：2015-8-6 上午10:32:54
 * @Modified By:Kim
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
@Controller
@Scope("prototype")
public class SystemController extends BaseAction
{
    @Autowired
    private RescService rescService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private LoginService loginService;
	@Autowired
	private OperationLogService operationLogService;
    
    /**
     * 资源管理列表
     */
    @RequestMapping(value="/v/rescList.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String rescList(HttpServletRequest request) throws Exception{
        
    	this.navIndex(PageConstant.PageMsg.NAV_RESC_1.getStatus(), request);
    	
        Pages<Resc> page = new Pages<Resc>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
      //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
		page.setWhereSql(whereSql);
        
        //分页查询菜单信息
        rescService.queryPage(page);
        
        request.setAttribute("list", page.getRoot());
        request.setAttribute("page", page);
        
        return "system/resc_list";
    } 
    
    /**
     * 资源管理编辑初始化
     */
    @RequestMapping(value="/v/rescEditInit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String rescEditInit(Long rescId,HttpServletRequest request) throws Exception{
    	
        Resc resc = null;
        
        //修改
        if(MyUtils.isLongGtZero(rescId)){
            
            resc = rescService.getById(rescId);
            this.navIndex(PageConstant.PageMsg.NAV_RESC_3.getStatus(), request);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_RESC_2.getStatus(), request);
            resc = new Resc();
        }
        
        request.setAttribute("resc", resc);
        
        return "system/resc_edit";
    } 
    
    /**
     * 资源管理编辑
     */
    @RequestMapping(value="/nv/rescEdit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String rescEdit(Resc resc,HttpServletRequest request) throws Exception{
        String text = "";
    	if(resc==null){
        	text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
        }else{
        	text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
        }
        int status = rescService.updateResc(resc);
        
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status!=1){
        	return rescEditInit(resc.getRescId(),request);
        }else{
			//记录日志
        	Manager user = getLoginUser(request);
			operationLogService.oplog(resc.getRescId(),resc.getRescName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_RESC,"");
        }
        
        return rescList(request);
    } 
    
    /**
     * 资源删除
     */
    @RequestMapping(value="/v/rescDelete.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String rescDelete(Long rescId,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(rescId);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = rescService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long id : ids){
				if(MyUtils.isLongGtZero(id)){
					Resc resc = rescService.getById(id);
					String opeObject = "";
					if(resc!=null){ opeObject = resc.getRescName();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_RESC,"");
				}
			}
		}
        return rescList(request);
    } 
    
    /**
     * 用户管理列表
     */
    @RequestMapping(value="/v/managerList.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String managerList(HttpServletRequest request) throws Exception{
        
    	this.navIndex(PageConstant.PageMsg.NAV_MANAGER_1.getStatus(), request);
    	
        Pages<Manager> page = new Pages<Manager>();
        
        page.setCurrentPage(currentPage);
        
        page.setPerRows(12);
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
		page.setWhereSql(whereSql);
		// 获取登入user的id
        Manager ma = getLoginUser(request);
		Long managerId = ma.getManagerId();
		//分页查询菜单信息
        managerService.queryPage(page,isViewOtherData(request),isViewChildData(request),managerId);
        
        List<Manager> managerList = page.getRoot();
        request.setAttribute("list", managerList);
        request.setAttribute("page", page);
        
        return "system/user_list";
    }  
    
    /**
     * 用户管理编辑初始化
     */
    @RequestMapping(value="/v/managerEditInit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String managerEditInit(Long managerId,HttpServletRequest request) throws Exception{
       return  managerService.managerEditInit(managerId,request);
    } 
    /**
     * 用户管理查看页面
     */
    @RequestMapping(value="/nv/managerView", method = {RequestMethod.POST, RequestMethod.GET})
    public String managerView(Long userId,HttpServletRequest request) throws Exception{
        
        Manager manager = null;
        //查看
        if(MyUtils.isLongGtZero(userId)){
        	this.navIndex(PageConstant.PageMsg.NAV_MANAGER_4.getStatus(), request);
            manager = managerService.getById(userId);
            request.setAttribute("manager", manager);
        }
        return "system/user_view";
    } 
    /**
     * 用户管理编辑
     */
    @RequestMapping(value="/nv/managerEdit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String managerEdit(@Valid @ModelAttribute("manager") Manager manager,BindingResult bindingResult,String[] roleIds,HttpServletRequest request) throws Exception{
    	//判断用户名是否重名
    	boolean isUserNameExist = managerService.queryUserByUserName(manager);
    	if(isUserNameExist){
	    	bindingResult.rejectValue("username", StatusConstant.Status.USER_NAME_EXISTED.getMsg(), StatusConstant.Status.USER_NAME_EXISTED.getMsg());  
	        return managerEditInit(manager.getManagerId(),request);
    	}
    	String password = manager.getPassword();
    	Long id = manager.getManagerId();
    	String text = "";
    	if(MyUtils.isLongGtZero(id)){//修改
    		text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    		Manager ma = managerService.getById(id);
    		//编辑页面 默认显示是加密后的密文，当用户没有改变时，则不需要修改密码。
    		if(password!=null && password.equals(ma.getPassword())){
    		}else{
    			manager.setPassword(MD5.GetMD5Code(password));
    		}
    	}else{
    		text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    		manager.setPassword(MD5.GetMD5Code(password));
    	}
    	HttpSession session = request.getSession();
    	Manager login = (Manager)session.getAttribute(CommConstant.SESSION_MANAGER);
    	int status = 1;
    	if(login==null || login.getManagerId()==null){
    		status = StatusConstant.Status.USER_SESSION_EXPIRED.getStatus();
    		return managerEditInit(manager.getManagerId(),request);
    	}
    	boolean flag =  (Boolean)request.getSession().getAttribute(CommConstant.SESSION_ISCREATE_CHILD);
        status = managerService.updateManager(manager,roleIds,login.getManagerId(),flag);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(manager.getManagerId(),manager.getUsername(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_MANAGER,"");
        	return managerList(request);
        }else{
        	return managerEditInit(manager.getManagerId(),request);
        }
    } 
    /**
     * 系统管理首界面
     */
    @RequestMapping(value="/v/system.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String systemIndex(HttpServletRequest request) throws Exception{
    	this.navIndex(PageConstant.PageMsg.NAV_MENU_0.getStatus(), request);
    	Long managerId = getLoginUser(request).getManagerId();
        List<Role> roles = roleService.getRolesByUserId(managerId);
        List<Resc> rescs = loginService.obtainCurrentManagerAuthority(managerId.longValue());
        request.setAttribute("roles",roles);
        request.setAttribute("rescs",rescs);
        return "system/system";
    } 
    /**
	 * 冻结/解冻用户
	 */
	@RequestMapping(value="/v/managerFreeze.do",method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public String freeze(Long id, HttpServletRequest request){
		try {
			Manager manager = managerService.getById(id);
			int status = manager.getStatus();
			String text = "";
			if(status==1){
				text = OperationLogConstant.OPE_MODULE_TYPE_FREEZE;
				manager.setStatus((byte)0);
			}else if(status==0){
				text = OperationLogConstant.OPE_MODULE_TYPE_UNFREEZE;
				manager.setStatus((byte)1);
			}
			int falg = managerService.batchCommitFreeze(manager);
			if(falg == 1){
				//记录日志
				Manager user = getLoginUser(request);
				Manager logManager = managerService.getById(id);
				String opeObject = "";
				if(logManager!=null){ opeObject = logManager.getUsername();}
				operationLogService.oplog(id,opeObject,text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_MANAGER,"");
			}
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}
    /**
     * 用户删除
     */
    @RequestMapping(value="/v/managerDelete.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String managerDelete(Long managerId,String idList,HttpServletRequest request) throws Exception{
        
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(managerId);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = managerService.deleteManager(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
		for (Long id : ids){
			if(MyUtils.isLongGtZero(id)){
				//记录日志
				Manager user = getLoginUser(request);
				Manager logManager = managerService.getById(id);
				String opeObject = "";
				if(logManager!=null){ opeObject = logManager.getUsername();}
				operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_MANAGER,"");
			}
		}
        return managerList(request);
    } 
    
    /**
     * 给角色关联用户
     */
    @RequestMapping(value="/nv/roleUserEdit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String roleUserEdit(Long roleId,String[] checkedUserIds,HttpServletRequest request) throws Exception{
    	int status = roleService.updateRoleUser(roleId,checkedUserIds);
    	noticeMsg(StatusConstant.getMsg(status),request);
		if(status == 1){
			//记录日志
			Manager user = getLoginUser(request);
			Role role = roleService.getById(roleId);
			String opeObject = "";
			if(role!=null){ opeObject = role.getRoleName();}
			operationLogService.oplog(roleId,opeObject,OperationLogConstant.OPE_MODULE_TYPE_FOREIGN_USER,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_ROLE,"");
		}
        return roleList(request);
    } 
    /**
     * 给角色关联权限
     */
    @RequestMapping(value="/nv/roleRescEdit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String roleRescEdit(Long roleId,String[] checkedRescIds,HttpServletRequest request) throws Exception{
    	int status = roleService.updateRoleResc(roleId,checkedRescIds);
    	noticeMsg(StatusConstant.getMsg(status),request);
		if(status == 1){
			//记录日志
			Manager user = getLoginUser(request);
			Role role = roleService.getById(roleId);
			String opeObject = "";
			if(role!=null){ opeObject = role.getRoleName();}
			operationLogService.oplog(roleId,opeObject,OperationLogConstant.OPE_MODULE_TYPE_FOREIGN_RESC,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_ROLE,"");
		}
        return roleList(request);
    } 
    
    /**
     * 角色分配用戶列表
     */
    @RequestMapping(value="/v/roleUserList.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String roleUserList(Long roleId,HttpServletRequest request) throws Exception{
    	this.navIndex(PageConstant.PageMsg.NAV_ROLE_4.getStatus(), request);
    	 Role role = null;
         //修改
         if(MyUtils.isLongGtZero(roleId)){
             role = roleService.getById(roleId);
             //新增
         }else{
             role = new Role();
         }
         request.setAttribute("role", role);
        
        List<Long> idList = new ArrayList<Long>(Arrays.asList(roleId));
        
      
        //查询角色对应有哪些用户
        List<Manager> mList = managerService.getQueryByRoleIdList(idList);
        //查询角色 没有选择的用户
        List<Manager> otherList = managerService.getNoManagerByRoleIds(idList);
        
        request.setAttribute("mList", mList);
        request.setAttribute("otherList", otherList);
        return "system/role_user";
    } 
    
    /**
     * 角色分配权限列表
     */
    @RequestMapping(value="/v/roleRescList.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String rolePermissionList(Long roleId,HttpServletRequest request) throws Exception{
    	this.navIndex(PageConstant.PageMsg.NAV_ROLE_5.getStatus(), request);
    	Role role = null;
         //修改
         if(MyUtils.isLongGtZero(roleId)){
             role = roleService.getById(roleId);
             //新增
         }else{
             role = new Role();
         }
         request.setAttribute("role", role);
        
        List<Long> idList = new ArrayList<Long>(Arrays.asList(roleId));
        
        //查询角色对应有哪些权限
        List<Resc> mList = rescService.getQueryByRoleIdList(idList);
        //对list按照rescGroup字段进行排序
        mList = SortBean.getSortBeans(mList,"getRescGroup");
        request.setAttribute("mList", mList);
        //查询角色 没有选择的权限
        List<Resc> otherList = rescService.getNoRescByRoleIds(idList); 
        //对list按照rescGroup字段进行排序
        otherList = SortBean.getSortBeans(otherList,"getRescGroup");
        request.setAttribute("otherList", otherList);
        
        return "system/role_resc";
    } 
    
    /**
     * 角色管理列表
     */
    @RequestMapping(value="/v/roleList.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String roleList(HttpServletRequest request) throws Exception{
        
    	this.navIndex(PageConstant.PageMsg.NAV_ROLE_1.getStatus(), request);
    	
        Role role = new Role();
        
        Pages<Role> page = new Pages<Role>();
        
        page.setCurrentPage(currentPage);
        
        page.setPerRows(12);
        
        String queryRoleName = request.getParameter("queryRoleName");
        
        if(StringUtils.isNotEmpty(queryRoleName)){
            
            role.setRoleName(queryRoleName);
            
            request.setAttribute("queryRoleName", queryRoleName);
        }
        
        //分页查询菜单信息
        roleService.queryPage(page,role);
        
        List<Role> roleList = page.getRoot();
        
        if(!MyUtils.isListEmpty(roleList)){
            
            List<Long> idList = new ArrayList<Long>();
            
            for (Role r : roleList)
            {
                idList.add(r.getRoleId());
            }
            
            //查询角色对应有哪些用户
            List<Manager> mList = managerService.getQueryByRoleIdList(idList);
            //得到角色对应于用户的别名放在map中
            
            if(!MyUtils.isListEmpty(mList)){
                
                Map<Long, String> map = new HashMap<Long, String>();
                
                for (Manager m : mList)
                {
                    Long roleId = Long.valueOf(m.getRoleId());
                    if(map.containsKey(roleId)){
//                      map.put(roleId, map.get(m.getRoleId()) + "," + m.getChineseName());
                    	map.put(Long.valueOf(m.getRoleId()), map.get(roleId) + "," + m.getUsername());
                    }else{
//                      map.put(roleId, m.getChineseName());
                        map.put(roleId, m.getUsername());
                    }
                }
                request.setAttribute("map", map);
            }
            
        }
        
        request.setAttribute("list", page.getRoot());
        
        request.setAttribute("page", page);
        
        return "system/role_list";
    } 
    
    /**
     * 角色管理编辑初始化
     */
    @RequestMapping(value="/v/roleEditInit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String roleEditInit(Long roleId,HttpServletRequest request) throws Exception{
        
    	
    	Role role = null;
        
        //修改
        if(MyUtils.isLongGtZero(roleId)){
        	this.navIndex(PageConstant.PageMsg.NAV_ROLE_3.getStatus(), request);
            role = roleService.getById(roleId);
            
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_ROLE_2.getStatus(), request);
            role = new Role();
        }
        
        request.setAttribute("role", role);
        
        return "system/role_edit";
    } 
    
    /**
     * 角色管理编辑
     */
    @RequestMapping(value="/nv/roleEdit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String roleEdit(Role role,HttpServletRequest request) throws Exception{
    	String text = "";
    	this.navIndex(PageConstant.PageMsg.NAV_ROLE_1.getStatus(), request);
    	 if(MyUtils.isLongGtZero(role.getRoleId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	int status = roleService.updateRole(role);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return roleEditInit(role.getRoleId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(role.getRoleId(),role.getRoleName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_ROLE,"");
        }
        return roleList(request);
    } 
    
    /**
     * 角色删除
     */
    @RequestMapping(value="/v/roleDelete.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String roleDelete(Long roleId,String idList,HttpServletRequest request) throws Exception{
        
        List<Long> ids = null;
        
        if(!StringUtils.isNotEmpty(idList)){
            
            ids = new ArrayList<Long>();
            
            ids.add(roleId);
        }else{
            
            ids = MyUtils.convertToLongList(idList, ",");
            
        }
        
        int status = roleService.deleteRole(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
		if(status == 1){
			for (Long id : ids){
				if(MyUtils.isLongGtZero(id)){
					//记录日志
					Manager user = getLoginUser(request);
					Role role = roleService.getById(id);
					String opeObject = "";
					if(role!=null){ opeObject = role.getRoleName();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_ROLE,"");
				}
			}
		}
        return roleList(request);
    } 
    /**
     * 给主账号分配子账号
     */
    @RequestMapping(value="/nv/userPCEdit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String userParentChildEdit(Long managerId,String[] checkedUserIds,HttpServletRequest request) throws Exception{
    	int status = managerService.updateUserChild(managerId,checkedUserIds);
    	noticeMsg(StatusConstant.getMsg(status),request);
		if(status == 1){
			//记录日志
			Manager user = getLoginUser(request);
			Manager loguser = managerService.getById(managerId);
			String opeObject = "";
			if(loguser!=null){ opeObject = loguser.getUsername();}
			operationLogService.oplog(loguser.getManagerId(),opeObject,OperationLogConstant.OPE_MODULE_TYPE_ASSIGN_CHILD,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_MANAGER,"");
		}
        return managerList(request);
    }
    /**
     * 跳转到 给主账号分配子账号 页面中
     */
    @RequestMapping(value="/v/userPCEditInit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String userPCEditInit(Long managerId,HttpServletRequest request) throws Exception{
    	this.navIndex(PageConstant.PageMsg.NAV_MANAGER_5.getStatus(), request);
    	 Manager manager = null;
         //修改
         if(MyUtils.isLongGtZero(managerId)){
        	 manager = managerService.getById(managerId);
             //新增
         }else{
        	 manager = new Manager();
         }
         request.setAttribute("manager", manager);
        
        //查询该主账户对应有哪些子账户
        List<Manager> mList = managerService.getManagersByUserId(managerId);
        //查询角色 没有选择的用户
        List<Manager> otherList = managerService.getNoManagerByUserId(managerId);
        
        request.setAttribute("mList", mList);
        request.setAttribute("otherList", otherList);
        return "system/user_child";
    } 
   
    /**
     * 可选角色分配
     */
    @RequestMapping(value="/nv/roleChildEdit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String roleChildEdit(Long roleId,String[] checkedRoleIds,HttpServletRequest request) throws Exception{
    	int status = roleService.updateRoleChild(roleId,checkedRoleIds);
    	noticeMsg(StatusConstant.getMsg(status),request);
		if(status == 1){
			//记录日志
			Manager user = getLoginUser(request);
			Role role = roleService.getById(roleId);
			String opeObject = "";
			if(role!=null){ opeObject = role.getRoleName();}
			operationLogService.oplog(roleId,opeObject,OperationLogConstant.OPE_MODULE_TYPE_ASSIGN_ROLE,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_ROLE,"");
		}
        return roleList(request);
    }
    /**
     * 跳转到 可选角色分配页面
     */
    @RequestMapping(value="/v/roleChildEditInit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String roleChildEditInit(Long roleId,HttpServletRequest request) throws Exception{
    	this.navIndex(PageConstant.PageMsg.NAV_ROLE_6.getStatus(), request);
    	 Role role = null;
         //修改
         if(MyUtils.isLongGtZero(roleId)){
        	 role = roleService.getById(roleId);
             //新增
         }else{
        	 role = new Role();
         }
         request.setAttribute("role", role);
        
        //查询该主账户对应有哪些子账户
        List<Role> mList = roleService.getCheckedRolesByRoleId(roleId);
        //查询角色 没有选择的用户
        List<Role> otherList = roleService.getNoCheckedRolesByRoleId(roleId);
        
        request.setAttribute("mList", mList);
        request.setAttribute("otherList", otherList);
        return "system/role_child";
    } 
    
    /**
	 * 
	 * @Title: getPermissionScops
	 * @Description: TODO(取得所有权限类型信息)
	 * @param @return    
	 * @return Map<Long,String> 
	 * @throws
	 */
	@ModelAttribute("permissionScops")
	public Map<Integer, String> getPermissionScops() {
		Map<Integer, String> m = new java.util.HashMap<Integer, String>();
		m.put(new Integer(11), "权限管理");
		m.put(new Integer(12), "数据管理");
		m.put(new Integer(13), "子账号管理");
		m.put(new Integer(14), "用户管理");
		m.put(new Integer(15), "系统管理");
		m.put(new Integer(16), "项目管理");
		m.put(new Integer(17), "订单");
		m.put(new Integer(18), "猪肉券");
		m.put(new Integer(19), "资讯管理");
		m.put(new Integer(20), "线下门店");
		m.put(new Integer(21), "首页");
		return m;
	}
	
}
