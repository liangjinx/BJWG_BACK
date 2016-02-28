package com.bjwg.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.dao.MyCouponUseDao;
import com.bjwg.back.model.MyCouponUse;
import com.bjwg.back.model.MyCouponUseExample;
import com.bjwg.back.service.MyCouponUseService;
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
public class MyCouponUseServiceImpl implements MyCouponUseService 
{
	@Autowired
    private MyCouponUseDao myCouponUseDao;
	
	@Override
	public void queryPage(Pages<MyCouponUse> page,SearchVo vo) throws Exception {
		MyCouponUseExample example = new MyCouponUseExample();
		MyCouponUseExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		
		//使用日期查询语句
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andUseTimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andUseTimeLessThanOrEqualTo(date2);
		}
		
		int count = myCouponUseDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.my_coupon_use_id");
		List<MyCouponUse> ops = myCouponUseDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public MyCouponUse getById(Long id) throws Exception {
		return myCouponUseDao.selectByPrimaryKey(id);
	}

	@Override
	public List<MyCouponUse> getAll() throws Exception {
		return myCouponUseDao.selectByExample(new MyCouponUseExample());
	}
}
