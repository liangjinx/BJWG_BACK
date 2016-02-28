package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Info;
import com.bjwg.back.vo.SearchVo;

/**
 * 资讯service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */

public interface InfoService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Info> page,SearchVo vo) throws Exception;
    
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<Info> getAll() throws Exception;
   
	/**
	 * 修改或增加资讯
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(Info info) throws Exception;
	 
    /**
     * 删除资讯
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询资讯
     * @param id
     * @return
     * @throws Exception
     */
    public Info getById(Long id)throws Exception;  
}
