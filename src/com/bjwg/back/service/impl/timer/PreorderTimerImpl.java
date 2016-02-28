package com.bjwg.back.service.impl.timer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjwg.back.base.constant.OrderConstant;
import com.bjwg.back.base.constant.ProjectConstant;
import com.bjwg.back.base.constant.SystemConstant;
import com.bjwg.back.dao.OrderDao;
import com.bjwg.back.dao.ProjectDao;
import com.bjwg.back.dao.SysconfigDao;
import com.bjwg.back.dao.UserPreorderDao;
import com.bjwg.back.model.Order;
import com.bjwg.back.model.Project;
import com.bjwg.back.model.ProjectExample;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.model.SysconfigExample;
import com.bjwg.back.model.UserPreorder;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.RandomUtils;
import com.bjwg.back.util.StringUtils;

@Service
public class PreorderTimerImpl {
	
	@Autowired
    private ProjectDao projectDao;
	@Autowired
	private UserPreorderDao userPreorderDao;
	@Autowired
    private OrderDao orderDao;
	@Autowired
    private SysconfigDao sysconfigDao;
	
//	protected static Logger logger = LogManager.getLogger();
	public static final Logger  logger = LoggerFactory.getLogger("LOGISTICS-COMPONENT"); 
	
	public void execute() throws Exception{
		Date now = new Date();
		try{
			//用户预标表、用户表扩展 生成订单
			generateOrder(now);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("用户预标表、用户表扩展 生成订单出错:");
			logger.error(e.toString());
			throw e;
		}
	}
	
	/**
	 * 用户预标表、用户表扩展 生成订单
	 * @param now
	 */
	private void generateOrder(Date now){
		
		logger.info(">>>>>1.定时扫描开始 用户预标表、用户表扩展 生成订单");
		ProjectExample projectExample = new ProjectExample();
		ProjectExample.Criteria projectCriteria = projectExample.createCriteria();
		System.out.println("当前时间---------------------"+(sdf.format(now)));
		projectCriteria.andBeginTimeBetween(DateUtil.getMinBefore(now,1),DateUtil.getMinAfter(now,1));// 开始时间 在 当前时间的前后一分钟
		List<Project> projects = projectDao.selectByExample(projectExample);
		if(!MyUtils.isListEmpty(projects)){
			
			Project project = projects.get(0);
			Date beginTime = project.getBeginTime();//开始预抢时间
			System.out.println("开始时间---------------------"+(sdf.format(beginTime)));
			//预抢时间 在  当前时间 前后1分钟之内
			boolean flag = DateUtil.nowInDateEquals(beginTime,DateUtil.getMinBefore(now,1),DateUtil.getMinAfter(now,1));
			if(flag){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", OrderConstant.ORDER_PRE_TYPE_1);
				map.put("projectId", project.getPaincbuyProjectId());
				//默认0:未选择1:每期固定抢标2:定制哪期抢标，没有生成订单的用户
				List<UserPreorder> list = userPreorderDao.getAllUserPreOrder(map);
				if(!MyUtils.isListEmpty(list)){
					
					logger.info(">>>>>2.定时扫描 用户预标表、用户表扩展 生成订单，符合的数据有："+list.size()+"条");
					
					BigDecimal price = project.getPrice();
					short leftNum = project.getLeftNum();
					//根据业务，需要一个一个生成订单，不能批量生成
					for(UserPreorder p : list){
						short num = p.getNum().shortValue();
						
						if(num > leftNum){
							num = leftNum;
						}
						if(leftNum > 0){//生成订单
							Order order = new Order();
							order.setOrderCode(RandomUtils.getOrderCode());//生成订单编号
							order.setCtime(now);
							order.setNum((short)num);
							order.setPrice(price);
							order.setProductImg(project.getImgs());
							order.setProductName(project.getName());
							order.setRelationId(project.getPaincbuyProjectId());
							order.setRelationName(project.getName());
							order.setRemark("预抢");
							order.setStatus(OrderConstant.ORDER_STATUS_1);
							order.setTotalMoney(new BigDecimal(num).multiply(price));
							order.setType(OrderConstant.ORDER_TYPE_1);
							order.setUserId(p.getUserId());
							order.setUsername(p.getUsername());
							//配置 支付超时时间
							String overLimitHour = getSysConfigValue(SystemConstant.ORDER_PAY_OVER_LIMIT_HOURS);
							if(StringUtils.isNotEmpty(overLimitHour)){
								//支付截止时间
								order.setOverTime(MyUtils.addDate2(order.getCtime(), Integer.valueOf(overLimitHour)));
							}
							order.setSubOrderId(-1L);
							orderDao.insertSelective(order);
							//修改剩余数量
							leftNum -= num;
							project.setLeftNum(leftNum);
							project.setStatus(ProjectConstant.PROJECT_STATUS_1);
							projectDao.updateByPrimaryKeySelective(project);
							
							logger.info(">>>>>3.定时扫描 用户预标表、用户表扩展 生成订单，打印数据：order_id:"+order.getOrderId()+"project_id:"+project.getPaincbuyProjectId()+",原预定数量："+p.getNum()
									+" ,计算后的预定数量："+num+" ,剩余数量"+leftNum+"");
						}else{
							break;
						}
					}
				}
			}
		}
		
		logger.info(">>>>>4.定时扫描结束 用户预标表、用户表扩展 生成订单");
	}
	
	private List<Sysconfig> getSysConfigByCode(String code) throws Exception {
		SysconfigExample example = new SysconfigExample();
		SysconfigExample.Criteria criteria = example.createCriteria();
		criteria.andCodeEqualTo(code);
		List<Sysconfig> ops = sysconfigDao.selectByExample(example);
		return ops;
	}
	private String getSysConfigValue(String code){
		String value = "";
    	List<Sysconfig> list;
		try {
			list = getSysConfigByCode(code);
			if(!MyUtils.isListEmpty(list)){
				value = list.get(0).getValue();
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
    }
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
