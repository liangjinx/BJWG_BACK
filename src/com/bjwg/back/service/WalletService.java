package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Wallet;

/**
 * 字典项service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
/**
 * @author Administrator
 *
 */
public interface WalletService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Wallet> page) throws Exception;
    
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<Wallet> getAll() throws Exception;
   
	/**
	 * 修改或增加我的钱包
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(Wallet config) throws Exception;
	 
    /**
     * 删除我的钱包
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询我的钱包
     * @param id
     * @return
     * @throws Exception
     */
    public Wallet getById(Long id)throws Exception;  
}
