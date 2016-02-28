package com.bjwg.back.service;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.BankCard;
import com.bjwg.back.model.BankCardExample;

/**
 * 银行卡
 * @author Allen
 * @version 创建时间：2015-9-30 下午03:42:15
 * @Modified By:Administrator
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */

public interface BankCardService {

	/**
	 * 分页查询银行卡号记录
	 * @param page
	 * @throws Exception 
	 */
	public void queryPage(Pages<BankCard> pages,BankCardExample example) throws Exception;
}
