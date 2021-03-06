package com.bjwg.back.dao;

import com.bjwg.back.model.Resc;
import com.bjwg.back.model.RescExample;
import java.util.List;

public interface RescDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_resc
     *
     * @mbggenerated
     */
    int countByExample(RescExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_resc
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long rescId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_resc
     *
     * @mbggenerated
     */
    int insert(Resc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_resc
     *
     * @mbggenerated
     */
    int insertSelective(Resc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_resc
     *
     * @mbggenerated
     */
    List<Resc> selectByExample(RescExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_resc
     *
     * @mbggenerated
     */
    Resc selectByPrimaryKey(Long rescId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_resc
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Resc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_resc
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Resc record);
    
    /**
     * 通过资源ID删除资源与角色的对应关系
     * @param rescId
     * @return
     */
    int deleteResc2RoleByRescId(Long rescId);
    /**
     * 通过角色ID删除资源与角色的对应关系
     * @param roleId
     * @return
     */
    int deleteResc2RoleByRoleId(Long roleId);
    /**
     * 通过角色ID与资源ID删除资源与角色的对应关系
     * @param roleId
     * @param rescId
     * @return
     */
    int deleteResc2Role(Long roleId,Long rescId);
    
    /**
     * 根据角色id查询该角色对应的权限
     * @param roleIds
     * @return
     */
    List<Resc> getQueryByRoleIdList(String roleIds);
    
    /**
     * 增加资源角色关联
     * @param rescId
     * @param roleId
     * @return
     */
    int saveResc2Role(Long rescId,Long roleId);
    
    /**
     * 查询当前管理所拥有的权限资源
     * @param managerId
     * @return
     * @throws Exception
     */
    public List<Resc> obtainCurrentManagerAuthority(Long managerId);
}