package com.bjwg.back.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.SysconfigDao;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.model.SysconfigExample;
import com.bjwg.back.service.SysconfigService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.ValidateUtil;



/**
 * 系统配置接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class SysconfigServiceImpl implements SysconfigService 
{
	@Autowired
    private SysconfigDao sysconfigDao;
	
	@Override
	public void queryPage(Pages<Sysconfig> page) throws Exception {
		SysconfigExample example = new SysconfigExample();
		SysconfigExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		int count = sysconfigDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("s.parent_code,s.id");
		List<Sysconfig> ops = sysconfigDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrupdate(Sysconfig config) throws Exception{
		if(!StringUtils.isNotEmpty(config.getName())){
			return StatusConstant.Status.SYSCONFIG_NAME_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(config.getName().trim(),false,1,45)){
			return StatusConstant.Status.SYSCONFIG_NAME_OVER_LEN.getStatus();
		}
		
		if(!StringUtils.isNotEmpty(config.getCode())){
			return StatusConstant.Status.SYSCONFIG_CODE_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(config.getCode().trim(),false,1,45)){
			return StatusConstant.Status.SYSCONFIG_CODE_OVER_LEN.getStatus();
		}
		if(!StringUtils.isNotEmpty(config.getParentCode())){
			return StatusConstant.Status.SYSCONFIG_PARENT_CODE_NULL.getStatus(); 
		}
		if(!ValidateUtil.validateString(config.getParentCode().trim(),false,1,45)){
			return StatusConstant.Status.SYSCONFIG_PARENT_CODE_OVER_LEN.getStatus();
		}
		Long id = config.getId();
		//自动增加父级目录
		if((config.getCode()+".").indexOf((config.getParentCode()+".")) < 0){
			config.setCode(config.getParentCode()+"."+config.getCode());
		}
		//新增
		if(!MyUtils.isLongGtZero(id)){
			return sysconfigDao.insertSelective(config);
		}else{
			return sysconfigDao.updateByPrimaryKeySelective(config);
		}
	}
	@Override
	public int userUpdate(Sysconfig config) throws Exception{
		
		if(!StringUtils.isNotEmpty(config.getValue())){
			return StatusConstant.Status.SYSCONFIG_VALUE_NULL.getStatus(); 
		}
		if(!ValidateUtil.validateString(config.getValue().trim(),false,1,255)){
			return StatusConstant.Status.SYSCONFIG_VALUE_OVER_LEN.getStatus();
		}
		Long id = config.getId();
		Sysconfig c = getById(id);
		c.setValue(config.getValue());
		return sysconfigDao.updateByPrimaryKeySelective(c);
	}
	@Override
    public int delete(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
            	if(!MyUtils.isLongGtZero(id)){
            		return StatusConstant.Status.SYSCONFIG_DEL_FAIL.getStatus(); 
            	}
                //删除数据库数据
            	sysconfigDao.deleteByPrimaryKey(id);
            }
            return StatusConstant.Status.SUCCESS.getStatus(); 
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

	@Override
	public Sysconfig getById(Long id) throws Exception {
		return sysconfigDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Sysconfig> getListByParentCode(String parentCode) throws Exception {
		SysconfigExample example = new SysconfigExample();
		SysconfigExample.Criteria criteria = example.createCriteria();
		criteria.andParentCodeEqualTo(parentCode);
		List<Sysconfig> ops = sysconfigDao.selectByExample(example);
		return ops;
	}
	@Override
	public Sysconfig getByCode(String code) throws Exception {
		Sysconfig sys = null;
		SysconfigExample example = new SysconfigExample();
		SysconfigExample.Criteria criteria = example.createCriteria();
		criteria.andCodeEqualTo(code);
		List<Sysconfig> ops = sysconfigDao.selectByExample(example);
		if(!MyUtils.isListEmpty(ops)){
			sys = ops.get(0);
		}
		return sys;
	}

	@Override
	public List<Sysconfig> queryList(List<String> codeList) throws Exception {
		System.out.println("OO--------------");
	
		SysconfigExample example = new SysconfigExample();
		SysconfigExample.Criteria criteria = example.createCriteria();
		criteria.andCodeIn(codeList);
		List<Sysconfig> list = sysconfigDao.selectByExample(example);
		return list;
		
	}

	
	
}
