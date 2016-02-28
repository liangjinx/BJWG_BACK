package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Area;

/**
 * 公告service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */

public interface AreaService
{
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Area> page) throws Exception;
    /**
     * 根据省份查询城市列表
     * @return
     * @throws Exception
     */
    public List<Area> queryCity(Long parent) throws Exception;
	/**
	 * 查询所有省份
	 * @return
	 */
	public List<Area> getProvince() throws Exception;
}
