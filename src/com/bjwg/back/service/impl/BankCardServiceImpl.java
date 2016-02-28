package com.bjwg.back.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjwg.back.base.Pages;
import com.bjwg.back.dao.BankCardDao;
import com.bjwg.back.model.BankCard;
import com.bjwg.back.model.BankCardExample;
import com.bjwg.back.service.BankCardService;

/**
 * @author Allen
 * @version 创建时间：2015-9-30 下午03:43:09
 * @Modified By:Administrator
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
@Service
public class BankCardServiceImpl implements BankCardService {

	@Autowired
	private BankCardDao bankCardDao;
	
	/**
	 * 分页查询银行卡记录
	 */
	@Override
	public void queryPage(Pages<BankCard> pages,BankCardExample example) throws Exception {

		int count = bankCardDao.countByExample(example);
		
		pages.setCountRows(count);
		
		example.setPage(pages);
		
		example.setOrderByClause(" card_id desc ");
		
		List<BankCard> bankList = bankCardDao.selectByExample(example);
		
		pages.setRoot(bankList);
		
	}

}
