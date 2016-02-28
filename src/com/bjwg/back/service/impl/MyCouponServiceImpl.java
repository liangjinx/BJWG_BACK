package com.bjwg.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.dao.MyCouponDao;
import com.bjwg.back.model.MyCoupon;
import com.bjwg.back.model.MyCouponExample;
import com.bjwg.back.service.MyCouponService;
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
public class MyCouponServiceImpl implements MyCouponService 
{
	@Autowired
    private MyCouponDao myCouponDao;
	
	@Override
	public void queryPage(Pages<MyCoupon> page,SearchVo vo) throws Exception {
		MyCouponExample example = new MyCouponExample();
		MyCouponExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		
		//使用日期查询语句
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andBeginTimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andEndTimeLessThanOrEqualTo(date2);
		}
		
		int count = myCouponDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.my_coupon_id");
		List<MyCoupon> ops = myCouponDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public MyCoupon getById(Long id) throws Exception {
		return myCouponDao.selectByPrimaryKey(id);
	}

	@Override
	public List<MyCoupon> getAll() throws Exception {
		return myCouponDao.selectByExample(new MyCouponExample());
	}

	@Override
	public List<MyCoupon> getMyCoupon(Long userId) throws Exception {
		MyCouponExample example = new MyCouponExample();
		MyCouponExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<MyCoupon> ops = myCouponDao.selectByExample(example);
		return ops;
	}
}
