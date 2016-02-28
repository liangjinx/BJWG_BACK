package com.bjwg.back.service.impl.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.ManagerDao;
import com.bjwg.back.dao.RescDao;
import com.bjwg.back.dao.RoleDao;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Resc;
import com.bjwg.back.model.Role;
import com.bjwg.back.model.RoleExample;
import com.bjwg.back.service.RoleService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;



/**
 * 角色service接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class RoleServiceImpl implements RoleService
{

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private RescDao rescDao;
    
    @Override
    public int updateRole(Role entity) throws Exception
    {
        try
        {
        	if(!MyUtils.isLongGtZero(entity.getRoleId())){
        		entity.setRoleId(-1L);
        	}
            //查询角色名是否重复
        	RoleExample example = new RoleExample();
        	RoleExample.Criteria criteria = example.createCriteria();
        	criteria.andRoleNameEqualTo(entity.getRoleName());
        	criteria.andRoleIdNotEqualTo(entity.getRoleId());
        	int count = roleDao.countByExample(example);
            if(count>0){
	          return StatusConstant.Status.ROLE_NAME_EXISTED.getStatus();
            }
            //修改
            if(MyUtils.isLongGtZero(entity.getRoleId())){
                roleDao.updateByPrimaryKeySelective(entity);
               //新增
            }else{
            	System.out.println("==============="+entity.getRoleName());
                roleDao.insertSelective(entity);
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
    public int deleteRole(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
                int count = roleDao.deleteByPrimaryKey(id);
                //删除成功后，删除角色与用户关系记录 及 资源与角色关系记录
                if(count > 0){
                	managerDao.deleteManager2RoleByRoleId(id);
                	rescDao.deleteResc2RoleByRoleId(id);
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
    public int updateRoleUser(Long roleId,String[] userIds) throws Exception{
    	try
        {
    		List<String> userPageTemp = Arrays.asList(userIds);
        	List<Long> userData = null;//数据库中查询出来的角色关联用户
        	List<Long> idList = new ArrayList<Long>(Arrays.asList(roleId));
            //查询角色对应有哪些用户
//            List<Manager> mList = managerDao.getQueryByRoleIdList(StringUtils.stringList2StrinNot(idList,""));
        	 List<Manager> mList = managerDao.getQueryByRoleIdList(idList);
            if(!MyUtils.isListEmpty(mList)){
            	userData = new ArrayList<Long>();
            	for(Manager m:mList){
            		userData.add(m.getManagerId());
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
	    	        for (Long managerId : userData) {  
	    	            // 相同的  
	    	            if (maxMap.get(managerId) != null) {  
	    	                maxMap.put(managerId, 2L);  
	    	                continue;  
	    	            }  
	    	            // 不相等的  
	    	            managerDao.deleteManager2Role(managerId, roleId);
	    	        }  
    	        }
    	        // 循环maxMap  
	            for (Map.Entry<Long, Long> entry : maxMap.entrySet()) {  
	                if (entry.getValue() == 1) { 
	                	managerDao.saveManager2Role(entry.getKey(),roleId); 
	                }  
	            }  
            }else{
            	  if(!MyUtils.isListEmpty(userData)){
             	     // 循环minList中的值，标记 maxMap中 相同的 数据2  
	 	    	        for (Long manageId : userData) {  
	 	    	            // 不相等的  
	 	    	           managerDao.deleteManager2Role(manageId,roleId);
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
    public int updateRoleResc(Long roleId,String[] rescIds) throws Exception{
        
    	try
        {
    		List<String> rescPageTemp = Arrays.asList(rescIds);
        	List<Long> rescData = null;//数据库中查询出来的角色关联用户
        	List<Long> idList = new ArrayList<Long>(Arrays.asList(roleId));
            //查询角色对应有哪些用户
            List<Resc> mList = rescDao.getQueryByRoleIdList(StringUtils.stringList2StrinNot(idList,","));
            if(!MyUtils.isListEmpty(mList)){
            	rescData = new ArrayList<Long>();
            	for(Resc m:mList){
            		rescData.add(m.getRescId());
            	}
            }
            Map<Long, Long> maxMap = null;
            if(!MyUtils.isListEmpty(rescPageTemp)){
    	        // 将List中的数据存到Map中  
    	        maxMap = new HashMap<Long, Long>(rescPageTemp.size());  
    	        for (String rescId : rescPageTemp) { 
    	            maxMap.put(Long.parseLong(rescId), 1L);  
    	        }
            }
    	        if(!MyUtils.isListEmpty(rescData)){
    	     // 循环minList中的值，标记 maxMap中 相同的 数据2  
	    	        for (Long rescId : rescData) {  
	    	            // 相同的  
	    	            if (maxMap!=null && maxMap.get(rescId) != null) {  
	    	                maxMap.put(rescId, 2L);  
	    	                continue;  
	    	            }  
	    	            // 不相等的  
	    	            rescDao.deleteResc2Role(roleId, rescId);
	    	        }  
    	        }
    	        if(maxMap!=null){
    	        // 循环maxMap  
		            for (Map.Entry<Long, Long> entry : maxMap.entrySet()) {  
		                if (entry.getValue() == 1) { 
		            		 rescDao.saveResc2Role(entry.getKey(),roleId);
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
    public int updateRoleChild(Long roleId,String[] roleIds) throws Exception{
    	try
        {
    		List<Long> roleIdList = null;
    		if(roleIds != null && roleIds.length > 0){
    			roleIdList = new ArrayList<Long>();
    			for(String a : roleIds){
    				roleIdList.add(Long.parseLong(a));
    			}
    		}
        	List<Long> rescData = null;//数据库中查询出来的可以选择的角色
            List<Role> mList = null;
            if(roleIdList!=null){
            	mList = roleDao.getCheckedRolesByRoleIds(roleIdList);
            }
            if(!MyUtils.isListEmpty(mList)){
            	rescData = new ArrayList<Long>();
            	for(Role m:mList){
            		rescData.add(m.getRoleId());
            	}
            }
            Map<Long, Long> maxMap = null;
            if(!MyUtils.isListEmpty(roleIdList)){
    	        // 将List中的数据存到Map中  
    	        maxMap = new HashMap<Long, Long>(roleIdList.size());  
    	        for (Long rescId : roleIdList) { 
    	            maxMap.put(rescId, 1L);  
    	        }
            }
    	        if(!MyUtils.isListEmpty(rescData)){
    	     // 循环minList中的值，标记 maxMap中 相同的 数据2  
	    	        for(Long rescId : rescData) {  
	    	            // 相同的  
	    	            if (maxMap!=null && maxMap.get(rescId) != null) {  
	    	                maxMap.put(rescId, 2L);  
	    	                continue;  
	    	            }  
	    	            // 不相等的  
	    	            roleDao.deleteRoleChild(roleId,rescId);
	    	        }  
    	        }
    	        if(maxMap!=null){
    	        // 循环maxMap  
		            for (Map.Entry<Long, Long> entry : maxMap.entrySet()) {  
		                if (entry.getValue() == 1) { 
		            		 roleDao.saveRoleChild(roleId,entry.getKey()); 
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
	public List<Role> getNoCheckedRolesByRoleId(Long roleId)
			throws Exception {
		//能选择的所有角色
        List<Role> allList = roleDao.selectByExample(new RoleExample());
        //查询该角色所能选择的角色
        List<Role> mList = roleDao.getCheckedRolesByRoleId(roleId);
        List<Role> otherList = new ArrayList<Role>();  
        if(!MyUtils.isListEmpty(allList)){
	        // 将List中的数据存到Map中  
	        Map<Long, Long> maxMap = new HashMap<Long, Long>(allList.size());  
	        for (Role role : allList) { 
	            maxMap.put(role.getRoleId(), 1L);  
	        }
        if(!MyUtils.isListEmpty(mList)){
	     // 循环minList中的值，标记 maxMap中 相同的 数据2  
	        for (Role role : mList) {  
	            // 相同的  
	            if (maxMap.get(role.getRoleId()) != null) {  
	                maxMap.put(role.getRoleId(), 2L);  
	                continue;  
	            }  
	            // 不相等的  
	            otherList.add(role);  
	        }  
        }
     // 循环maxMap  
        for (Map.Entry<Long, Long> entry : maxMap.entrySet()) {  
            if (entry.getValue() == 1) {  
            	otherList.add(roleDao.selectByPrimaryKey(entry.getKey()));  
            }  
        }  
        }
		return otherList;
	}

//    @Override
//    public boolean save(Role entity) throws Exception
//    {
//        return false;
//    }
//
//    @Override
//    public boolean update(Role entity) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public boolean delete(Long id) throws Exception
//    {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public Role getById(Long id) throws Exception
//    {
//        return roleDao.getById(id, Role.class);
//    }
//
//    @Override
//    public List<Role> getQuery() throws Exception
//    {
//        return roleDao.getQuery(Role.class);
//    }
//    @Override
//    public void queryPage(Pages<Role> page,Role entity,Class<Role> clazz) throws Exception{
//        roleDao.queryPage(page, entity, clazz);
//    }

	@Override
	public List<Role> getRolesByUserId(Long managerId) throws Exception {
		return roleDao.getRolesByManagerId(managerId);
	}
	@Override
	public List<Role> getCheckedRolesByRoleId(Long roleId) throws Exception {
		return roleDao.getCheckedRolesByRoleId(roleId);
	}

	@Override
	public void queryPage(Pages<Role> page,Role role)
			throws Exception {
		RoleExample example = new RoleExample();
		if(StringUtils.isNotEmpty(role.getRoleName())){
			RoleExample.Criteria criteria = example.createCriteria();
			criteria.addCriterion("role.role_name like '%"+role.getRoleName()+"%'");
        }
		int count = roleDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("role.ROLE_ID DESC");
		List<Role> roles = roleDao.selectByExample(example);
		page.setRoot(roles);
	}

	@Override
	public Role getById(Long id) throws Exception {
		return roleDao.selectByPrimaryKey(id);
	}
}
