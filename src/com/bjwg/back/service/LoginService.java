package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Resc;

/**
 * @author Kim
 * @version 创建时间：2015-4-2 下午04:55:08
 */

public interface LoginService {

	/**
	 * 管理员登陆
	 * 
	 * @param m
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public int updateLogin(Manager m, String password, String ip)
			throws Exception;

	/**
	 * 查询当前管理所拥有的权限资源
	 * 
	 * @param managerId
	 * @return
	 * @throws Exception
	 */
	public List<Resc> obtainCurrentManagerAuthority(Long managerId)
			throws Exception;

	/**
	 * 是否可以查看所有数据
	 * @param userId 当前用户ID
	 * @return true 是;false 否
	 * @throws Exception
	 */
	public boolean isViewAllData(Long userId) throws Exception;
	/**
	 * 是否可以创建子账号
	 * @param userId 当前用户ID
	 * @return true 是;false 否
	 * @throws Exception
	 */
	public boolean isCreateChild(Long userId) throws Exception;
	/**
	 * 是否可以查看子账号数据
	 * @param userId 当前用户ID
	 * @return true 是;false 否
	 * @throws Exception
	 */
	public boolean isViewChild(Long userId) throws Exception;
	
}
