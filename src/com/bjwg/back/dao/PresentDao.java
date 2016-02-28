package com.bjwg.back.dao;

import com.bjwg.back.model.Present;
import com.bjwg.back.model.PresentExample;
import java.util.List;

public interface PresentDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_present_freinds
     *
     * @mbggenerated
     */
    int countByExample(PresentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_present_freinds
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long presentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_present_freinds
     *
     * @mbggenerated
     */
    int insert(Present record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_present_freinds
     *
     * @mbggenerated
     */
    int insertSelective(Present record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_present_freinds
     *
     * @mbggenerated
     */
    List<Present> selectByExample(PresentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_present_freinds
     *
     * @mbggenerated
     */
    Present selectByPrimaryKey(Long presentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_present_freinds
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Present record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_present_freinds
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Present record);
}