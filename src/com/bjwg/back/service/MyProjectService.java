package com.bjwg.back.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.MyProject;
import com.bjwg.back.vo.SearchVo;

/**
 * 我的收益service接口
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
public interface MyProjectService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<MyProject> page,SearchVo vo) throws Exception;
    
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<MyProject> getAll() throws Exception;
   
	/**
	 * 修改或增加我的收益
	 * @param myProject
	 * @return
	 * @throws Exception
	 */
//	public int saveOrupdate(MyProject myProject) throws Exception;
	 
    /**
     * 删除我的收益
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询我的收益
     * @param id
     * @return
     * @throws Exception
     */
    public MyProject getById(Long id)throws Exception;
    /**
     * 修改我的项目的处理状态，根据用户id和项目id
     * @return
     * @throws Exception
     */
    public int updateDealStatusByUidAndPid(Long userId,Long projectId) throws Exception;
    /**
	 * 导出用户收益列表之前先检验
	 * @param request
	 * @param response
	 * @return
	 */
	public int checkExportList(String sql,SearchVo vo);
	
	/**
	 * 导出用户收益列表
	 * @param request
	 * @param response
	 * @return
	 */
	public int exportList(HttpServletRequest request,HttpServletResponse response) throws Exception;
}
