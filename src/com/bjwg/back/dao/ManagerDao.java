package com.bjwg.back.dao;

import com.bjwg.back.model.Manager;
import com.bjwg.back.model.ManagerExample;
import java.util.List;

public interface ManagerDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_manager
     *
     * @mbggenerated
     */
    int countByExample(ManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_manager
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long managerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_manager
     *
     * @mbggenerated
     */
    int insert(Manager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_manager
     *
     * @mbggenerated
     */
    int insertSelective(Manager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_manager
     *
     * @mbggenerated
     */
    List<Manager> selectByExample(ManagerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_manager
     *
     * @mbggenerated
     */
    Manager selectByPrimaryKey(Long managerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_manager
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Manager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_manager
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Manager record);
    
    /**
     * 根据管理员名称查询管理员
     * @param username
     * @return
     */
    Manager selectByName(String username);
    /**
     * 根据角色id查询该角色对应的用户
     * @param idList
     * @return
     */
    List<Manager> getQueryByRoleIdList(List<Long> idList);
    /**
     * 根据登入用户增加子账号 插入子账号数据
     * @param managerId
     * @param userId
     * @return
     */
    int saveUserChild(Long parentId, Long childId);
    /**
     * 根据管理员ID和角色ID删除管理员与角色的对应关系
     * @param managerId
     * @param roleId
     * @return
     */
    int deleteManager2Role(Long managerId,Long roleId);
    /**
     * 删除主子账号关联
     * @param parentId
     * @param childId
     * @return
     */
    int deleteUserChild(Long parentId,Long childId);
    /**
     * 删除角色与用户的关系,通过用户ID
     * @param managerId
     * @return
     */
    int deleteManager2RoleByManagerId(Long managerId);
    /**
     * 删除角色与用户的关系,通过角色ID
     * @param managerId
     * @return
     */
    int deleteManager2RoleByRoleId(Long roleId);
    /**
     * 插入管理员与角色的对应关系
     * @param managerId
     * @param roleId
     */
    void saveManager2Role(Long managerId,Long roleId);
    
    /**
     * 查看改用户下的字账号
     * @param managerId
     * @return
     */
    List<Manager> getManagersByUserId(Long managerId);
}