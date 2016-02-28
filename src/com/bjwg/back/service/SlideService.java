package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Slide;

/**
 * 轮播图service接口
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
public interface SlideService
{
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Slide> page) throws Exception;
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<Slide> getAll() throws Exception;
   
	/**
	 * 修改或增加轮播图
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(Slide slide) throws Exception;
	 
    /**
     * 删除轮播图
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询轮播图
     * @param id
     * @return
     * @throws Exception
     */
    public Slide getById(Long id)throws Exception;  
}
