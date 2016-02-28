package com.bjwg.back.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Manager;

/**
 * 管理员service接口
 * @author Kim
 * @version 创建时间：2015-4-3 下午03:51:42
 * @Modified By:Kim
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public interface ManagerService
{
    
	/**
	 * 根据用户ID查询用户
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Manager getById(Long id) throws Exception;
    /**
     * 根据用户名查询系统用户信息
     * @param username
     * @return 系统用户信息
     * @throws Exception
     */
    public Manager queryByName(String username) throws Exception;
    
	/**
	 * 判断系统用户名是否重复
	 * @param manager
	 * @return
	 * @throws Exception
	 */
	boolean queryUserByUserName(Manager manager) throws Exception;

    /**
     * 根据角色id查询该角色对应的系统用户
     * @param idList
     * @throws Exception 
     */
    public List<Manager> getQueryByRoleIdList(List<Long> idList) throws Exception;
    
    /**
     * 系统用户分页查询
     * @param page
     * @param clazz
     * @param isViewAllData
     * @param isViewChildData
     * @param userId
     * @throws Exception
     */
    public void queryPage(Pages<Manager> page,boolean isViewAllData,boolean isViewChildData,Long userId) throws Exception;
    
    /**
     * 保存系统用户信息
     * @param entity
     * @param roleIds
     * @param parentId
     * @param isCreateChild
     * @return
     */
    public int updateManager(Manager entity,String[] roleIds,Long parentId,Boolean isCreateChild) throws Exception;
    /**
     * 修改管理员用户
     * @param entity
     * @return
     * @throws Exception
     */
    public int updateManager(Manager entity) throws Exception;
    
    /**
     * 删除系统用户信息
     * @param idList
     * @return
     * @throws Exception
     */
    public int deleteManager(List<Long> idList) throws Exception;
    
	/**
	 * 冻结系统账号
	 * @param ma
	 * @return
	 */
	int batchCommitFreeze(Manager ma)throws Exception;
    
	
	/**
	 * 通过角色id 查询该角色未包括的用户
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	public List<Manager> getNoManagerByRoleIds(List<Long> idList) throws Exception; 
	
    /**
     * 用户管理增加、修改初始化
     * @param managerId
     * @param request
     * @return
     * @throws Exception
     */
    public String managerEditInit(Long managerId,HttpServletRequest request) throws Exception;
    /**
     * 用户关联角色
     * @param userId
     * @param roleIds
     * @return
     * @throws Exception
     */
    public int updateUserRole(Long userId,String[] roleIds) throws Exception;
    /**
     * 查询当前管理用户所拥有的子账号
     * @param managerId
     * @return
     * @throws Exception
     */
    public List<Manager> getManagersByUserId(Long managerId) throws Exception;
	/**
	 * 通过当前管理用户id 查询该用户未包括的子账号
	 * @param managerId 当前管理用户id
	 * @return
	 * @throws Exception
	 */
	public List<Manager> getNoManagerByUserId(Long managerId) throws Exception; 
	
    /**
     * 子账号分配子账号
     * @param managerId
     * @param userIds
     * @return
     * @throws Exception
     */
    public int updateUserChild(Long managerId,String[] userIds) throws Exception;
}
