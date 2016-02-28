package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Sysconfig;

/**
 * 系统配置service接口
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
public interface SysconfigService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Sysconfig> page) throws Exception;
   
	/**
	 * 修改或增加系统配置
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(Sysconfig config) throws Exception;
	/**
	 * 用户修改系统配置
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public int userUpdate(Sysconfig config) throws Exception;
    /**
     * 删除系统配置
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询系统配置
     * @param id
     * @return
     * @throws Exception
     */
    public Sysconfig getById(Long id)throws Exception; 
    
    
    /**
     * 得到所有系统基本设置信息
     * @return
     * @throws Exception
     */
    public List<Sysconfig> getListByParentCode(String parentCode) throws Exception;
    
    /**
     * 通过code得到系统基本设置信息
     * @return
     * @throws Exception
     */
    public Sysconfig getByCode(String Code) throws Exception;
    
    /**
     * 得到系统基本设置信息
     * @return
     * @throws Exception
     */
    public List<Sysconfig> queryList(List<String> codes) throws Exception;
  
	
}
