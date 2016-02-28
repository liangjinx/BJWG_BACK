package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.DictDetail;

/**
 * 字典项明细service接口
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
public interface DictDetailService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<DictDetail> page) throws Exception;
   
	/**
	 * 修改或增加字典项明细
	 * @param config
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(DictDetail config) throws Exception;
	 
    /**
     * 删除字典项明细
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询字典项明细
     * @param id
     * @return
     * @throws Exception
     */
    public DictDetail getById(Long id)throws Exception; 
    
    /**
     * 通过编号得到字典明细
     * @return
     * @throws Exception
     */
    public List<DictDetail> getDictDetailsByCode(String code) throws Exception;
    /**
     * 通过父编号得到字典明细
     * @return
     * @throws Exception
     */
    public List<DictDetail> getDictDetailsByParentCode(String parentCode) throws Exception;
    
    /**
     * 分页查询资讯分类列表
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryInfoSortPage(Pages<DictDetail> page) throws Exception;
    /**
     * 得到value的最大值
     * @param code
     * @return
     */
    int getMaxValue(String code);
}
