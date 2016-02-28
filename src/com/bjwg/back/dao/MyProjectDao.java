package com.bjwg.back.dao;

import com.bjwg.back.model.MyProject;
import com.bjwg.back.model.MyProjectExample;


import java.util.List;
import java.util.Map;

public interface MyProjectDao {
	
	
	
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_my_earnings
     *
     * @mbggenerated
     */
    int countByExample(MyProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_my_earnings
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long earningsId);
    /**
     * 通过用户ID删除我的收益
     * @param userIds
     * @return
     */
    int deleteByUserIds(List<Long> userIds);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_my_earnings
     *
     * @mbggenerated
     */
    int insert(MyProject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_my_earnings
     *
     * @mbggenerated
     */
    int insertSelective(MyProject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_my_earnings
     *
     * @mbggenerated
     */
    List<MyProject> selectByExample(MyProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_my_earnings
     *
     * @mbggenerated
     */
    MyProject selectByPrimaryKey(Long earningsId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_my_earnings
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MyProject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_my_earnings
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MyProject record);
    
    int updateBatch(List<MyProject> list);
    
    void updateDealType(byte type,byte beforeByte,Long userId,Long projectId);
    

}