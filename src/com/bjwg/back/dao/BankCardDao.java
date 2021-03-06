package com.bjwg.back.dao;

import com.bjwg.back.model.BankCard;
import com.bjwg.back.model.BankCardExample;
import java.util.List;

public interface BankCardDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bank_card
     *
     * @mbggenerated
     */
    int countByExample(BankCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bank_card
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long cardId);
    /**
     * 通过用户ID删除银行卡记录表
     * @param userIds
     * @return
     */
    int deleteByUserIds(List<Long> userIds);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bank_card
     *
     * @mbggenerated
     */
    int insert(BankCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bank_card
     *
     * @mbggenerated
     */
    int insertSelective(BankCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bank_card
     *
     * @mbggenerated
     */
    List<BankCard> selectByExample(BankCardExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bank_card
     *
     * @mbggenerated
     */
    BankCard selectByPrimaryKey(Long cardId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bank_card
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(BankCard record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_bank_card
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(BankCard record);
}