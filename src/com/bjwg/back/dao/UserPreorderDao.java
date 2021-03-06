package com.bjwg.back.dao;

import com.bjwg.back.model.UserPreorder;
import com.bjwg.back.model.UserPreorderExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserPreorderDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_user_preorder
     *
     * @mbggenerated
     */
    int countByExample(UserPreorderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_user_preorder
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("projectId") Long projectId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_user_preorder
     *
     * @mbggenerated
     */
    int insert(UserPreorder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_user_preorder
     *
     * @mbggenerated
     */
    int insertSelective(UserPreorder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_user_preorder
     *
     * @mbggenerated
     */
    List<UserPreorder> selectByExample(UserPreorderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_user_preorder
     *
     * @mbggenerated
     */
    UserPreorder selectByPrimaryKey(@Param("userId") Long userId, @Param("projectId") Long projectId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_user_preorder
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserPreorder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_user_preorder
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserPreorder record);
    
    List<UserPreorder> getAllUserPreOrder(Map<String, Object> map);
    
    
    List<UserPreorder> selectLoadNameByMap(Map<String, Object> map) throws Exception;
}