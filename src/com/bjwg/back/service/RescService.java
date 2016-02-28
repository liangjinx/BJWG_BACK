package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Resc;

/**
 * 资源service接口
 * @author Kim
 * @version 创建时间：2015-4-3 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public interface RescService
{
    
    /**
     * 分页查询
     * @param page
     * @param class1
     * @throws Exception
     */
    public void queryPage(Pages<Resc> page) throws Exception;

    /**
	 *  根据id查询
	 * @param id
	 * @return
	 */
	public Resc getById(Long id)throws Exception;  
    /**
     * 删除
     * @param idlist
     * @return
     */
    int delete(List<Long> idlist)throws Exception;
    /**
     * 根据角色id查询该角色对应的权限
     * @param idList
     * @return
     * @throws Exception
     */
    List<Resc> getQueryByRoleIdList(List<Long> idList) throws Exception;
    
	/**
	 * 通过角色id 查询该角色未包括的资源
	 * @param idList 角色id列表
	 * @return
	 * @throws Exception
	 */
	public List<Resc> getNoRescByRoleIds(List<Long> idList) throws Exception;
	/**
	 * 增加、修改资源
	 * @param r
	 * @return
	 */
	public int updateResc(Resc r)throws Exception;
	
}
