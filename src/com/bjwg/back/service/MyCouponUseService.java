package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.MyCouponUse;
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
public interface MyCouponUseService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<MyCouponUse> page,SearchVo vo) throws Exception;
    
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<MyCouponUse> getAll() throws Exception;
   
    /**
     * 根据id查询字典项
     * @param id
     * @return
     * @throws Exception
     */
    public MyCouponUse getById(Long id)throws Exception;  
}
