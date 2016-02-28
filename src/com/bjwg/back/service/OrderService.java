package com.bjwg.back.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Order;
import com.bjwg.back.vo.HomeView;
import com.bjwg.back.vo.SearchVo;

/**
 * 公告service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */

public interface OrderService
{
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Order> page,SearchVo vo) throws Exception;
    /**
     * 根据id查询订单
     * @param id
     * @return
     * @throws Exception
     */
    public Order getById(Long id)throws Exception;
    
    /**
     * 得到订单的统计信息
     * @param type 1、当日;2、本周;3、本月
     * @return
     * @throws Exception
     */
    public HomeView getOrderCount(Byte type) throws Exception;
    
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
	
	/**
	 * 确认收货
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public int confirmOrder(Order order) throws Exception;
    
}
