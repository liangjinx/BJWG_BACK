package com.bjwg.back.dao;

import com.bjwg.back.model.Pay;
import com.bjwg.back.model.PayExample;
import java.util.List;

public interface PayDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_pay
     *
     * @mbggenerated
     */
    int countByExample(PayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_pay
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String tradeNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_pay
     *
     * @mbggenerated
     */
    int insert(Pay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_pay
     *
     * @mbggenerated
     */
    int insertSelective(Pay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_pay
     *
     * @mbggenerated
     */
    List<Pay> selectByExampleWithBLOBs(PayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_pay
     *
     * @mbggenerated
     */
    List<Pay> selectByExample(PayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_pay
     *
     * @mbggenerated
     */
    Pay selectByPrimaryKey(String tradeNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_pay
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Pay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_pay
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(Pay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_pay
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Pay record);
}