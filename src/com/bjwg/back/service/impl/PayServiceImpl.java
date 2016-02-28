package com.bjwg.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.dao.PayDao;
import com.bjwg.back.model.Pay;
import com.bjwg.back.model.PayExample;
import com.bjwg.back.service.PayService;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.vo.SearchVo;

/**
 * 系统配置接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class PayServiceImpl implements PayService 
{
	@Autowired
    private PayDao payDao;
	
	@Override
	public void queryPage(Pages<Pay> page,SearchVo vo) throws Exception {
		
		PayExample example = new PayExample();
		PayExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		
		//交易创建时间
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andGmtCreateTimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andGmtCreateTimeLessThanOrEqualTo(date2);
		}
		
		//交易付款时间
		if(StringUtils.isNotEmpty(vo.getStartTime2())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andGmtPayTimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime2())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andGmtPayTimeLessThanOrEqualTo(date2);
		}
		
		int count = payDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.trade_no");
		List<Pay> ops = payDao.selectByExample(example);
		page.setRoot(ops);
	}
}
