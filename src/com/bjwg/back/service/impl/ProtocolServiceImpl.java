package com.bjwg.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.ProtocolDao;
import com.bjwg.back.model.ProjectExample;
import com.bjwg.back.model.Protocol;
import com.bjwg.back.model.ProtocolExample;
import com.bjwg.back.service.ProtocolService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;

/**
 * 系统配置接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class ProtocolServiceImpl implements ProtocolService 
{
	@Autowired
    private ProtocolDao protocolDao;
	
	@Override
	public int saveOrupdate(Protocol protocol) throws Exception{
		
		if(!StringUtils.isNotEmpty(protocol.getContent())){
			return StatusConstant.Status.SERVICE_PROTOCOL_CONTENT_NULL.getStatus();
		}
		Long id = protocol.getSpId();
		if(!MyUtils.isLongGtZero(id)){
			protocol.setSpId(-1L);
		}
		//编辑之前看有没有重复的value值
		ProtocolExample example = new ProtocolExample();
		ProtocolExample.Criteria criteria = example.createCriteria();
		criteria.andTypeEqualTo(protocol.getType());
		criteria.andSpIdNotEqualTo(protocol.getSpId());
		List<Protocol> list = protocolDao.selectByExample(example);
		//数据库已经存在当前协议
		if(!MyUtils.isListEmpty(list)){
			return StatusConstant.Status.SERVICE_PROTOCOL_EXIST.getStatus();
		}
		
		//自动增加父级目录
		protocol.setCtime(new Date());
		if(!MyUtils.isLongGtZero(id)){
			return protocolDao.insertSelective(protocol);
		}else{
			return protocolDao.updateByPrimaryKeySelective(protocol);
		}
	}

	@Override
	public Protocol getById(Long id) throws Exception {
		return protocolDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Protocol> getAll() throws Exception {
		return protocolDao.getAll(new ProtocolExample());
	}
}
