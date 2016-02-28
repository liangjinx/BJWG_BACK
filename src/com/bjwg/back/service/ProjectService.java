package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Project;
import com.bjwg.back.vo.ProjectFeeInfo;
import com.bjwg.back.vo.SearchVo;

/**
 * 抢购项目service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */

public interface ProjectService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Project> page,SearchVo vo) throws Exception;
    
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<Project> getAll() throws Exception;
   
	/**
	 * 修改或增加抢购项目
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(Project info) throws Exception;
	 
    /**
     * 删除抢购项目
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询抢购项目
     * @param id
     * @return
     * @throws Exception
     */
    public Project getById(Long id)throws Exception;  
    
    /**
     * 解析项目的其他费用详情的json串
     * @param jsonFee
     * @return
     * @throws Exception
     */
    public List<ProjectFeeInfo> parseFeeDetail(String jsonFee) throws Exception;
}
