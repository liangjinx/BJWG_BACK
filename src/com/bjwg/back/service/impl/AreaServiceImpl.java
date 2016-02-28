package com.bjwg.back.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.dao.AreaDao;
import com.bjwg.back.model.Area;
import com.bjwg.back.model.AreaExample;
import com.bjwg.back.service.AreaService;
import com.bjwg.back.util.MyUtils;

/**
 * 系统配置接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class AreaServiceImpl implements AreaService 
{
	@Autowired
    private AreaDao areaDao;
	
	@Override
	public void queryPage(Pages<Area> page) throws Exception {
		
		AreaExample example = new AreaExample();
		AreaExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		
		int count = areaDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.area_id");
		List<Area> ops = areaDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public List<Area> queryCity(Long parent) throws Exception {
		AreaExample example = new AreaExample();
		if(parent!=null){
			AreaExample.Criteria criteria = example.createCriteria();
			criteria.andParentEqualTo(parent);
		}
		return areaDao.selectByExample(example);
	}
	/**
	 * 查询所有省份
	 */
	public List<Area> getProvince() throws Exception {
		AreaExample example = new AreaExample();
		AreaExample.Criteria criteria = example.createCriteria();
		criteria.andParentEqualTo(0L);
		return areaDao.selectByExample(example);
	}
}
