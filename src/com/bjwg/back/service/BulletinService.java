package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Bulletin;
import com.bjwg.back.vo.SearchVo;

/**
 * 公告service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */

public interface BulletinService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Bulletin> page,SearchVo vo) throws Exception;
    
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<Bulletin> getAll() throws Exception;
   
	/**
	 * 修改或增加公告
	 * @param bulletin
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(Bulletin bulletin) throws Exception;
	 
    /**
     * 删除公告
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询公告
     * @param id
     * @return
     * @throws Exception
     */
    public Bulletin getById(Long id)throws Exception;  
}
