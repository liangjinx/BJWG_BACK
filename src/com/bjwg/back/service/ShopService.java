package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Shop;

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
public interface ShopService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Shop> page) throws Exception;
    
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<Shop> getAll() throws Exception;
   
	/**
	 * 修改或增加线下门店
	 * @param shop
	 * @return
	 * @throws Exception
	 */
	public int saveOrUpdate(Shop shop) throws Exception;
	 
    /**
     * 删除字典项
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询字典项
     * @param id
     * @return
     * @throws Exception
     */
    public Shop getById(Long id)throws Exception; 
    /**
	 * 检查店铺是否有效
	 * @param phone
	 * @param shopId
	 * @throws Exception
	 */
	public int checkAddress(String address) throws Exception;
}
