package com.bjwg.back.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Withdraw;
import com.bjwg.back.vo.HomeView;
import com.bjwg.back.vo.SearchVo;
import com.bjwg.back.model.Manager;

/**
 * 公告service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */

public interface WithdrawService
{
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Withdraw> page,SearchVo vo) throws Exception;
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<Withdraw> getAll() throws Exception;
   
	/**
	 * 修改或增加公告
	 * @param withdraw
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(Withdraw withdraw) throws Exception;
	 
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
    public Withdraw getById(Long id)throws Exception;  
    /**
	 * 审核
	 * @param id
	 * @param toExamineId
	 * @return
	 */
	int batchCommitExamine(Long id,Integer status,Manager manager) throws Exception;
	
	/**
	 * 得到提现统计
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public HomeView getWithdrawCount(Byte type) throws Exception;
	/**
	 * 导出订单之前先检验
	 * @param request
	 * @param response
	 * @return
	 */
	public int checkExportList(String sql,SearchVo vo);
	
	/**
	 * 导出订单
	 * @param request
	 * @param response
	 * @return
	 */
	public int exportList(HttpServletRequest request,HttpServletResponse response) throws Exception;
	
}
