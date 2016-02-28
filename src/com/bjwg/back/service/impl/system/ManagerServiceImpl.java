package com.bjwg.back.service.impl.system;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.CommConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.ManagerDao;
import com.bjwg.back.dao.RoleDao;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.ManagerExample;
import com.bjwg.back.model.Role;
import com.bjwg.back.model.RoleExample;
import com.bjwg.back.service.ManagerService;
import com.bjwg.back.util.MyUtils;


/**
 * 管理员service接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * @Modified Kim
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class ManagerServiceImpl implements ManagerService
{

    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private RoleDao roleDao;
    /**
     * 根据角色id查询该角色对应的用户
     * @param idList
     * @throws Exception 
     */
    @Override
    public List<Manager> getQueryByRoleIdList(List<Long> idList) throws Exception{
        return managerDao.getQueryByRoleIdList(idList);
    }
    
    /**
     * 根据用户名查询管理员资料
     * @param username
     * @return
     * @throws Exception
     */
    public Manager queryByName(String username) throws Exception{
        return managerDao.selectByName(username);
    }
    
    /**
     * 根据主键查询管理员资料
     * @param id
     */
    @Override
    public Manager getById(Long id) throws Exception
    {
        return managerDao.selectByPrimaryKey(id);
    }

    
//    @Override
//    public List<Manager> getQuery() throws Exception
//    {
//        return managerDao.getQuery();
//    }
    
//    @Override
//    public boolean save(Manager entity) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public boolean update(Manager entity) throws Exception
//    {
//        return false;
//    }
//
//    @Override
//    public boolean delete(Long id) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return false;
//    }

//    @Override
//    public List<Manager> findQuery() throws Exception
//    {
//        return null;
//    }

	@Override
	public void queryPage(Pages<Manager> page,boolean isViewAllData,boolean isViewChildData,Long userId) throws Exception {
		
		ManagerExample example = new ManagerExample();
		ManagerExample.Criteria criteria = example.createCriteria();
		String sq = "";
		//不可以查看所有数据
		if(!isViewAllData){
			//可以查看子账号数据
			if(isViewChildData){
				sq = " and (manager.MANAGER_ID ="+userId+" or exists (select 1 from bjwg_user_child_relation uc where uc.USER_ID = "+userId+" and uc.user_id_child = manager.MANAGER_ID))";
			}else{
				sq = " and (manager.MANAGER_ID ="+userId+")";
			}
		}
		criteria.addCriterion(sq+page.getWhereSql());
		example.setOrderByClause("manager.REGISTER_TIME desc");
		int count = managerDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		List<Manager> managers = managerDao.selectByExample(example);
		page.setRoot(managers);
	}

	@Override
    public int updateManager(Manager ma,String[] roleIds,Long parentId,Boolean isCreateChild) throws Exception
    {
    	Long managerId = ma.getManagerId();
    	try{
    		if(!MyUtils.isLongGtZero(managerId)){
    			managerDao.insertSelective(ma);
    			if(isCreateChild){
        			managerDao.saveUserChild(parentId,ma.getManagerId());
        		}
    		}else{
    			managerDao.updateByPrimaryKeySelective(ma);
    		}
//        	if(roleIds!=null && roleIds.length>0){
        		updateUserRole(ma.getManagerId(),roleIds);
//        	}
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return StatusConstant.Status.SUCCESS.getStatus();
    }
	public boolean queryUserByUserName(Manager entity) throws Exception{
		boolean flag = false;
		//查询用户名是否重复
    	Manager r = managerDao.selectByName(entity.getUsername());
        if(!MyUtils.isLongGtZero(entity.getManagerId())){
        	 //查询用户名是否重复
        	 if(r != null){
        		 flag = true;
        	 }
        	//获取ip 
    		String ipAddress = InetAddress.getLocalHost().getHostAddress();
    		entity.setRegisterIp(MyUtils.ip2long(ipAddress));
    		entity.setRegisterTime(new Date());
        }else{
        	if(r != null && r.getManagerId().intValue() != entity.getManagerId().intValue()){
        		flag = true;
            }
        }
        return flag;
	}
   
    
	/**
     * 删除用户信息 
     */
    public int deleteManager(List<Long> idList) throws Exception{
        
        try
        {
            for (Long id : idList)
            {
            	if(id!=null&&id.intValue()==1){
            		return StatusConstant.Status.USER_DEL_FORBID.getStatus();
            	}
            	 //删除主从关联表 和 管理员用户表
            	int count = managerDao.deleteByPrimaryKey(id);
                //删除成功后，删除角色与用户关系记录
                if(count > 0){
                	managerDao.deleteManager2RoleByManagerId(id);
                }
            }
            
            return StatusConstant.Status.SUCCESS.getStatus();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
    }
	@Override
	public int batchCommitFreeze(Manager ma) throws Exception{
		Manager temp = new Manager();
		temp.setManagerId(ma.getManagerId());
		temp.setStatus(ma.getStatus());
		return managerDao.updateByPrimaryKeySelective(ma);
	}

	/* (non-Javadoc)
	 * @see com.bjwg.o2o.service.ManagerService#getNoCheckedByRoleIds(java.util.List)
	 */
	@Override
	public List<Manager> getNoManagerByRoleIds(List<Long> idList)
			throws Exception {
		 //改角色能选择的所有用户
		 List<Manager> allList = managerDao.selectByExample(new ManagerExample());
        //查询角色对应有哪些用户
        List<Manager> mList = getQueryByRoleIdList(idList);
        List<Manager> otherList = new ArrayList<Manager>();  
        if(!MyUtils.isListEmpty(allList)){
	        // 将List中的数据存到Map中  
	        Map<Long, Long> maxMap = new HashMap<Long, Long>(allList.size());  
	        for (Manager manager : allList) { 
	            maxMap.put(manager.getManagerId(), 1L);  
	        }
        if(!MyUtils.isListEmpty(mList)){
	     // 循环minList中的值，标记 maxMap中 相同的 数据2  
	        for (Manager manager : mList) {  
	            // 相同的  
	            if (maxMap.get(manager.getManagerId()) != null) {  
	                maxMap.put(manager.getManagerId(), 2L);  
	                continue;  
	            }  
	            // 不相等的  
	            otherList.add(manager);  
	        }  
        }
     // 循环maxMap  
        for (Map.Entry<Long, Long> entry : maxMap.entrySet()) {  
            if (entry.getValue() == 1) {  
            	otherList.add(managerDao.selectByPrimaryKey(entry.getKey()));  
            }  
        }  
        }
		return otherList;
	}

	@Override
	public String managerEditInit(Long managerId, HttpServletRequest request)
			throws Exception {
		Manager manager = null;
		//配置好的角色
//		List<Role> roles = roleDao.getQuery(Role.class);
//		String[] roleIds = null;
		List<Long> roleIds = null;
		Manager login = (Manager)request.getSession().getAttribute(CommConstant.SESSION_MANAGER);
		List<Role> curRoles = null;
		if(login!=null){
			curRoles = roleDao.getRolesByManagerId(login.getManagerId());
		}
		if(!MyUtils.isListEmpty(curRoles)){
			roleIds = new ArrayList<Long>();
			for(int i=0;i<curRoles.size();i++){
				Role roleTemp = curRoles.get(i);
				roleIds.add(roleTemp.getRoleId());
			}
		}
		List<Role> roles = null;
		if(!MyUtils.isListEmpty(curRoles)){
			roles = roleDao.getCheckedRolesByRoleIds(roleIds);
		}
		if(login.getManagerId()==1){
			roles = roleDao.selectByExample(new RoleExample());
		}
		request.setAttribute("roles", roles);
        //修改
        if(MyUtils.isLongGtZero(managerId)){
        	this.navIndex(PageConstant.PageMsg.NAV_MANAGER_3.getStatus(), request);
            manager = getById(managerId);
            //设置已经有的角色
            List<Role> hasRoles = roleDao.getRolesByManagerId(managerId);
            manager.setRoles(hasRoles);
            request.setAttribute("manager", manager);
        }else{
        	//新增
        	this.navIndex(PageConstant.PageMsg.NAV_MANAGER_2.getStatus(), request);
            manager = new Manager();
            request.setAttribute("manager", manager);
        }
        return "system/user_edit";
	}
	/**
	 * 返回页面导航
	 * @param nav
	 * @param request
	 */
	public void navIndex(int nav, HttpServletRequest request){
	    request.setAttribute("nav", nav);
	}
	/**
     * 用户关联角色
     */
    public int updateUserRole(Long userId,String[] roleIds) throws Exception{
        
    	try
        {
    		List<String> userPageTemp = Arrays.asList(roleIds);
        	List<Long> userData = null;//数据库中查询出来的用户关联角色
            //查询用户对应有哪些角色
            List<Role> hasRoles =roleDao.getRolesByManagerId(userId);
            if(!MyUtils.isListEmpty(hasRoles)){
            	userData = new ArrayList<Long>();
            	for(Role r:hasRoles){
            		userData.add(r.getRoleId());
            	}
            }
            
            if(!MyUtils.isListEmpty(userPageTemp)){
    	        // 将List中的数据存到Map中  
    	        Map<Long, Long> maxMap = new HashMap<Long, Long>(userPageTemp.size());  
    	        for (String managerId : userPageTemp) { 
    	            maxMap.put(Long.parseLong(managerId), 1L);  
    	        }
    	        if(!MyUtils.isListEmpty(userData)){
    	     // 循环minList中的值，标记 maxMap中 相同的 数据2  
	    	        for (Long roleId : userData) {  
	    	            // 相同的  
	    	            if (maxMap.get(roleId) != null) {  
	    	                maxMap.put(roleId, 2L);  
	    	                continue;  
	    	            }
	    	            // 不相等的  
	    	            managerDao.deleteManager2Role(userId,roleId);
	    	        }  
    	        }
    	        // 循环maxMap  
	            for (Map.Entry<Long, Long> entry : maxMap.entrySet()) {  
	                if (entry.getValue() == 1) { 
	                	managerDao.saveManager2Role(userId,entry.getKey()); 
	                }  
	            }  
            }else{
            	if(!MyUtils.isListEmpty(userData)){
           	     // 循环minList中的值，标记 maxMap中 相同的 数据2  
   	    	        for (Long roleId : userData) {  
   	    	            // 不相等的  
   	    	        	managerDao.deleteManager2Role(userId,roleId);
   	    	        }  
           	    }
            }
            return StatusConstant.Status.SUCCESS.getStatus();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

	@Override
	public List<Manager> getManagersByUserId(Long managerId)
			throws Exception {
		return managerDao.getManagersByUserId(managerId);
	}

	@Override
	public List<Manager> getNoManagerByUserId(Long managerId)
			throws Exception {
		//能选择的所有用户
        List<Manager> allList = managerDao.selectByExample(new ManagerExample());
        //查询该主账号对应有哪些子账号
        List<Manager> mList = managerDao.getManagersByUserId(managerId);
        List<Manager> otherList = new ArrayList<Manager>();  
        if(!MyUtils.isListEmpty(allList)){
	        // 将List中的数据存到Map中  
	        Map<Long, Long> maxMap = new HashMap<Long, Long>(allList.size());  
	        for (Manager manager : allList) { 
	        	//对自己 分配 子账号是 自己的账号不应该显示 在列表中
	        	if(managerId.equals(manager.getManagerId())){
	        		continue;  
		        }
	            maxMap.put(manager.getManagerId(), 1L);  
	        }
        if(!MyUtils.isListEmpty(mList)){
	     // 循环minList中的值，标记 maxMap中 相同的 数据2  
	        for (Manager manager : mList) {  
	            // 相同的  
	            if (maxMap.get(manager.getManagerId()) != null) {  
	                maxMap.put(manager.getManagerId(), 2L);  
	                continue;  
	            } 
	            // 不相等的  
	            otherList.add(manager);  
	        }  
        }
     // 循环maxMap  
        for (Map.Entry<Long, Long> entry : maxMap.entrySet()) {  
            if (entry.getValue() == 1) {  
            	otherList.add(managerDao.selectByPrimaryKey(entry.getKey()));  
            }  
        }  
        }
		return otherList;
	}
	
	@Override
	public int updateUserChild(Long managerId, String[] userIds)
			throws Exception {
		try
        {
			if(!MyUtils.isLongGtZero(managerId)){
				return StatusConstant.Status.PARENT_USER_NOT_EXIST.getStatus();
			}
//			if(userIds==null || userIds.length<=0){
//				return StatusConstant.CHILD_USER_NOT_EXIST;
//			}
    		List<String> userPageTemp = Arrays.asList(userIds);
        	List<Long> userData = null;//数据库中查询出来的子账号
            //查询该主账号对应有哪些子账号
            List<Manager> mList = managerDao.getManagersByUserId(managerId);
            if(!MyUtils.isListEmpty(mList)){
            	userData = new ArrayList<Long>();
            	for(Manager m:mList){
            		userData.add(m.getManagerId());
            	}
            }
            
            if(!MyUtils.isListEmpty(userPageTemp)){
    	        // 将List中的数据存到Map中  
    	        Map<Long, Long> maxMap = new HashMap<Long, Long>(userPageTemp.size());  
    	        for (String userId : userPageTemp) { 
    	            maxMap.put(Long.parseLong(userId), 1L);  
    	        }
    	        if(!MyUtils.isListEmpty(userData)){
    	     // 循环minList中的值，标记 maxMap中 相同的 数据2  
	    	        for (Long userId : userData) {  
	    	            // 相同的  
	    	            if (maxMap.get(userId) != null) {  
	    	                maxMap.put(userId, 2L);  
	    	                continue;  
	    	            }  
	    	            // 不相等的  
	    	            managerDao.deleteUserChild(managerId,userId);
	    	        }  
    	        }
    	        // 循环maxMap  
	            for (Map.Entry<Long, Long> entry : maxMap.entrySet()) {  
	                if (entry.getValue() == 1) { 
	                	managerDao.saveUserChild(managerId,entry.getKey()); 
	                }  
	            }  
            }else{
	        	if(!MyUtils.isListEmpty(userData)){
	       	     // 循环minList中的值，标记 maxMap中 相同的 数据2  
	    	        for (Long userId : userData) {  
	    	            // 不相等的  
	    	            managerDao.deleteUserChild(managerId,userId);
	    	        }  
	       	    }
            }
            return StatusConstant.Status.SUCCESS.getStatus();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
	}

	@Override
	public int updateManager(Manager entity) throws Exception {
		return managerDao.updateByPrimaryKeySelective(entity);
	}
}
