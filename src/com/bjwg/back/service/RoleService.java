package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Role;

/**
 * 角色service接口
 * @author Kim
 * @version 创建时间：2015-4-3 下午03:51:42
 * @Modified By:Kim
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public interface RoleService
{
    
    
    /**
     * 分页查询
     * @param page
     * @param entity
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Role> page,Role entity) throws Exception;
    
    
    /**
     * 更新角色信息
     * @param entity
     * @return
     */
    public int updateRole(Role entity)throws Exception;
    
    /**
     * 删除角色信息
     * @param idList
     * @return
     * @throws Exception
     */
    public int deleteRole(List<Long> idList) throws Exception;
    
    
    /**
     * 角色关联用户
     * @param roleId
     * @param userIds
     * @return
     */
    public int updateRoleUser(Long roleId,String[] userIds)throws Exception;
    
    
    /**
     * 角色关联权限
     * @param roleId
     * @param userIds
     * @return
     */
    public int updateRoleResc(Long roleId,String[] userIds)throws Exception;
    
    /**
     * 查询当前管理所拥有的角色
     * @param managerId
     * @return
     * @throws Exception
     */
    public List<Role> getRolesByUserId(Long managerId) throws Exception;
    
    /**
     * 查询当前角色的成员 可选择的角色
     * @param roleId
     * @return
     * @throws Exception
     */
    public List<Role> getCheckedRolesByRoleId(Long roleId) throws Exception;
    /**
     * 查询当前角色的成员 不可以选择的角色
     * @param managerId
     * @return
     * @throws Exception
     */
    public List<Role> getNoCheckedRolesByRoleId(Long roleId) throws Exception;
    
    /**
     * 配置 当前角色可以选择的角色
     * @param roleId
     * @param roleIds
     * @return
     * @throws Exception
     */
    public int updateRoleChild(Long roleId,String[] roleIds) throws Exception;
    
    /**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Role getById(Long id)throws Exception;  
    
}
