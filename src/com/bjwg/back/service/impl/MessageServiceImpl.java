package com.bjwg.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.dao.MessageDao;
import com.bjwg.back.model.Message;
import com.bjwg.back.model.MessageExample;
import com.bjwg.back.service.MessageService;
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
public class MessageServiceImpl implements MessageService 
{
	@Autowired
    private MessageDao messageDao;
	
	@Override
	public void queryPage(Pages<Message> page,SearchVo vo) throws Exception {
		
		MessageExample example = new MessageExample();
		MessageExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		
		//交易创建时间
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andCtimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andCtimeLessThanOrEqualTo(date2);
		}
		
		int count = messageDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.message_id");
		List<Message> ops = messageDao.selectByExample(example);
		page.setRoot(ops);
	}
}
