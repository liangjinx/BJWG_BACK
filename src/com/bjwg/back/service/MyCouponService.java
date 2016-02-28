package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.MyCoupon;
import com.bjwg.back.vo.SearchVo;

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
public interface MyCouponService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<MyCoupon> page,SearchVo vo) throws Exception;
    
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<MyCoupon> getAll() throws Exception;
    
    /**
     * 得到用户数据
     * @throws Exception
     */
    public List<MyCoupon> getMyCoupon(Long userId) throws Exception;
   
	/**
	 * 修改或增加字典项
	 * @param mycoupon
	 * @return
	 * @throws Exception
	 */
//	public int saveOrupdate(MyCoupon mycoupon) throws Exception;
	 
    /**
     * 删除字典项
     * @param idList
     * @return
     * @throws Exception
     */
//    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询字典项
     * @param id
     * @return
     * @throws Exception
     */
    public MyCoupon getById(Long id)throws Exception;  
}
