package com.bjwg.back.service.impl.timer;

import java.util.ArrayList;
import java.util.Date;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjwg.back.base.constant.OrderConstant;
import com.bjwg.back.dao.OrderDao;
//import com.bjwg.back.dao.ProjectDao;
import com.bjwg.back.model.Order;
import com.bjwg.back.model.OrderExample;
//import com.bjwg.back.model.Project;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.MyUtils;

@Service
public class OrderTimerImpl {
	
	@Autowired
    private OrderDao orderDao;
//	@Autowired
//    private ProjectDao projectDao;
	
//	protected static Logger logger = LogManager.getLogger();
	public static final Logger  logger = LoggerFactory.getLogger("LOGISTICS-COMPONENT"); 
	
	public void execute() throws Exception{
		Date now = new Date();
		try{
			//1、抢购、未付款的订单，并且当前时间 > 支付过期时间,取消订单
			executeOrderCancel(now);
			//2、抢购、付款中的订单，并且当前时间 > 支付过期时间 + 1个小时,取消订单
			executeRunOrderCancel(now);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("定时取消未付款、抢购的订单出错:");
			logger.error(e.toString());
			throw e;
		}
	}
	
	/**
	 * 抢购、未付款的订单，并且当前时间 > 支付过期时间,取消订单
	 * @param now
	 */
	private void executeOrderCancel(Date now) throws Exception{
		
		logger.info(">>>>>1.定时扫描开始 抢购、未付款的订单，并且当前时间 > 支付过期时间,取消订单");
		
		OrderExample orderExample = new OrderExample();
		OrderExample.Criteria orderCriteria = orderExample.createCriteria();
		orderCriteria.andTypeEqualTo(OrderConstant.ORDER_TYPE_1);//1:抢购 2:屠宰寄送3:领取活猪
		orderCriteria.andStatusEqualTo(OrderConstant.ORDER_STATUS_1);//未付款
		orderCriteria.andOverTimeLessThan(now);
		List<Order> orders = orderDao.selectTotalNumByExample(orderExample);
		if(!MyUtils.isListEmpty(orders)){
			
			logger.info(">>>>>2.定时扫描开始 抢购、未付款的订单，符合的数据有："+orders.size()+"条");
			
			List<Order> temp = new ArrayList<Order>();
//			List<Long> ids = new ArrayList<Long>();
			//Map<Long, Integer> id2NumMap = new HashMap<Long, Integer>();
			for(Order order : orders){
				order.setStatus(OrderConstant.ORDER_STATUS_0);//订单取消
				temp.add(order);
//				Long projectId = order.getRelationId();
//				if(!ids.contains(projectId)){
//					ids.add(projectId);
//				}
//				if(id2NumMap.containsKey(projectId)){
//					
//					id2NumMap.put(projectId, id2NumMap.get(projectId) + order.getNum());
//				}else{
//					
//					id2NumMap.put(projectId, (int)order.getNum());
//				}
			}
			/**
			//还原项目的剩余数量
			List<Project> projects = projectDao.queryList(ids);
			if(!MyUtils.isListEmpty(projects)){
				List<Project> batchPro = new ArrayList<Project>();
				for(Project p:projects){
					Short leftNum = p.getLeftNum();
					if(!MyUtils.isListEmpty(temp)){
						Integer num = id2NumMap.get(p.getPaincbuyProjectId());
						logger.info(">>>>>3.定时扫描开始 抢购、未付款的订单，projectId："+p.getPaincbuyProjectId()+",还原前的数量："+leftNum+"，还原数量："+num+"，还原后数量："+(leftNum+num)+"，发布数量："+p.getNum());
						p.setLeftNum((short)(leftNum+num));
						batchPro.add(p);
					}
				}
				if(!MyUtils.isListEmpty(batchPro)){
					projectDao.updateBatch(batchPro);
				}
			}
			*/
			//取消订单
			if(!MyUtils.isListEmpty(temp)){
				//批量处理
				orderDao.updateBatch(temp);
			}
		}
		logger.info(">>>>>4.定时扫描结束 抢购、未付款的订单，并且当前时间 > 支付过期时间,取消订单");
	}
	
	/**
	 * 抢购、付款中的订单，并且当前时间 > 支付过期时间 + 1个小时,取消订单
	 * @param now
	 */
	private void executeRunOrderCancel(Date now) throws Exception{
		
		logger.info(">>>>>1.定时扫描开始 抢购、付款中的订单，并且当前时间  > 支付过期时间 + 1个小时,取消订单");
		
		OrderExample orderExample = new OrderExample();
		OrderExample.Criteria orderCriteria = orderExample.createCriteria();
		orderCriteria.andTypeEqualTo(OrderConstant.ORDER_TYPE_1);//1:抢购 2:屠宰寄送3:领取活猪
		orderCriteria.andStatusEqualTo(OrderConstant.ORDER_STATUS_2);//付款中
		orderCriteria.andOverTimeLessThan(DateUtil.getMinBefore(now,60));
		List<Order> orders = orderDao.selectTotalNumByExample(orderExample);
		if(!MyUtils.isListEmpty(orders)){
			logger.info(">>>>>2.定时扫描开始 抢购、付款中的订单，符合的数据有："+orders.size()+"条");
			List<Order> temp = new ArrayList<Order>();
			logger.info(">>>>当时时间："+ now);
			for(Order order : orders){
				logger.info(">>>>支付过期时间："+order.getOverTime());
				order.setStatus(OrderConstant.ORDER_STATUS_0);//订单取消
				temp.add(order);
			}
			//取消订单
			if(!MyUtils.isListEmpty(temp)){
				//批量处理
				orderDao.updateBatch(temp);
			}
			logger.info(">>>>>4.定时扫描结束 抢购、未付款的订单，并且当前时间 > 支付过期时间,取消订单");
		}
	}
}
