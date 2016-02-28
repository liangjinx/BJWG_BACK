package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.model.Protocol;

/**
 * 服务协议设置service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 */
public interface ProtocolService
{
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<Protocol> getAll() throws Exception;
   
	/**
	 * 修改或增加服务协议设置
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(Protocol protocol) throws Exception;
	 
    /**
     * 根据id查询服务协议设置
     * @param id
     * @return
     * @throws Exception
     */
    public Protocol getById(Long id)throws Exception;  
}
