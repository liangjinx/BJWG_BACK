package com.bjwg.back.dao;

import com.bjwg.back.model.Bulletin;
import com.bjwg.back.model.BulletinExample;
import java.util.List;

public interface BulletinDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bulletin
     *
     * @mbggenerated
     */
    int countByExample(BulletinExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bulletin
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long bulletinId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bulletin
     *
     * @mbggenerated
     */
    int insert(Bulletin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bulletin
     *
     * @mbggenerated
     */
    int insertSelective(Bulletin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bulletin
     *
     * @mbggenerated
     */
    List<Bulletin> selectByExample(BulletinExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bulletin
     *
     * @mbggenerated
     */
    Bulletin selectByPrimaryKey(Long bulletinId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bulletin
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Bulletin record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bulletin
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Bulletin record);
}