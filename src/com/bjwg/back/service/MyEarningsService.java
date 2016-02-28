package com.bjwg.back.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.MyEarnings;
import com.bjwg.back.model.MyEarningsExample;




/**
 * 我的收益service
 * @author Administrator
 *
 */
public interface MyEarningsService {

	
	/**
	 * 查询我的收益
	 * @param MyEarningsExample
	 * @return
	 * @throws Exception
	 */
	List<MyEarnings> selectByExample(MyEarningsExample example)throws Exception;
	
	

	
    /**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public void insert(MyEarnings myEarnings)throws Exception;
	
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public int updateByPrimaryKeySelective(MyEarnings myEarnings)throws Exception;
	
	
  
	
}
