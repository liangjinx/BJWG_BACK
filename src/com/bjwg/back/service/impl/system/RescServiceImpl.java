package com.bjwg.back.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.RescDao;
import com.bjwg.back.model.RescExample;
import com.bjwg.back.model.Resc;
import com.bjwg.back.service.RescService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;

/**
 * 资源service接口实现类
 * @author Kim
 * @version 创建时间：2015-8-6 下午04:03:17
 * @Modified By:Kim Version: 1.0 jdk : 1.6 类说明：
 */
public class RescServiceImpl implements RescService {

	@Autowired
	private RescDao rescDao;

	@Override
	public void queryPage(Pages<Resc> page)
			throws Exception {
		RescExample example = new RescExample();
		RescExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		int count = rescDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("resc.resc_group,resc.resc_link");
		List<Resc> rescs = rescDao.selectByExample(example);
		page.setRoot(rescs);
	}

	@Override
	public int updateResc(Resc resc) throws Exception{
		try {
			// 查询资源编号或 资源标识 或资源名称是否已存在
			Long rescId = resc.getRescId();
			RescExample example = new RescExample();
			RescExample.Criteria criteria = example.createCriteria();
			if(!MyUtils.isLongGtZero(rescId)){
				resc.setRescId(-1L);
			}
			criteria.addCriterion(" and resc.resc_id <> "+rescId+" and (resc.resc_code = '"+resc.getRescCode()+"' or resc.resc_name = '"+resc.getRescName()+"' or resc.resc_link = '"+resc.getRescLink()+"')");
			int count = rescDao.countByExample(example);
			if (count > 0) {
				return StatusConstant.Status.RESC_EXISTED.getStatus();
			}
			
			if(!MyUtils.isLongGtZero(rescId)){
				rescDao.insertSelective(resc);
			}else{
				rescDao.updateByPrimaryKeySelective(resc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return StatusConstant.Status.SUCCESS.getStatus();
	}

	@Override
	public int delete(List<Long> idlist) throws Exception{
		try {
			for (Long id : idlist) {
				int count = rescDao.deleteByPrimaryKey(id);
				// 删除成功后，删除角色与资源关系记录
				if (count > 0) {
					rescDao.deleteResc2RoleByRescId(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return StatusConstant.Status.SUCCESS.getStatus();
	}

//	@Override
//	public boolean save(Resc entity) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean update(Resc entity) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Resc getById(Long id) throws Exception {
//		return rescDao.getById(id, Resc.class);
//	}
//
//	@Override
//	public List<Resc> getQuery() throws Exception {
//		return rescDao.getQuery(Resc.class);
//	}
//
//	@Override
//	public List<Resc> findQuery() throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean delete(Long id) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public List<Resc> getQueryByRoleIdList(List<Long> idList)
			throws Exception {
		String roleIds = StringUtils.stringList2StrinNot(idList,",");
		return rescDao.getQueryByRoleIdList(roleIds);
	}

	@Override
	public List<Resc> getNoRescByRoleIds(List<Long> idList) throws Exception {
		// 改角色能选择的所有用户
		List<Resc> allList = rescDao.selectByExample(new RescExample());
		// 查询角色对应有哪些用户
		List<Resc> mList = getQueryByRoleIdList(idList);
		List<Resc> otherList = new ArrayList<Resc>();
		if (!MyUtils.isListEmpty(allList)) {
			// 将List中的数据存到Map中
			Map<Long, Long> maxMap = new HashMap<Long, Long>(
					allList.size());
			for (Resc resc : allList) {
				maxMap.put(resc.getRescId(), 1L);
			}
			if (!MyUtils.isListEmpty(mList)) {
				// 循环minList中的值，标记 maxMap中 相同的 数据2
				for (Resc resc : mList) {
					// 相同的
					if (maxMap.get(resc.getRescId()) != null) {
						maxMap.put(resc.getRescId(), 2L);
						continue;
					}
					// 不相等的
					otherList.add(resc);
				}
			}
			// 循环maxMap
			for (Map.Entry<Long, Long> entry : maxMap.entrySet()) {
				if (entry.getValue() == 1) {
					otherList.add(rescDao.selectByPrimaryKey(entry.getKey()));
				}
			}
		}
		return otherList;
	}

	@Override
	public Resc getById(Long id) throws Exception {
		return rescDao.selectByPrimaryKey(id);
	}
}
