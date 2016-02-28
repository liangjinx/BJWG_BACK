package com.bjwg.back.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.DictDao;
import com.bjwg.back.model.Dict;
import com.bjwg.back.model.DictExample;
import com.bjwg.back.service.DictService;
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
public class DictServiceImpl implements DictService 
{
	@Autowired
    private DictDao dictDao;
	
	@Override
	public void queryPage(Pages<Dict> page) throws Exception {
		DictExample example = new DictExample();
		DictExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		int count = dictDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.dict_id");
		List<Dict> ops = dictDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrupdate(Dict dict) throws Exception{
		
		if(!StringUtils.isNotEmpty(dict.getName())){
			return StatusConstant.Status.DICT_NAME_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(dict.getName().trim(),false,1,45)){
			return StatusConstant.Status.DICT_NAME_OVER_LEN.getStatus();
		}
		if(!StringUtils.isNotEmpty(dict.getCode())){
			return StatusConstant.Status.DICT_CODE_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(dict.getCode().trim(),false,1,45)){
			return StatusConstant.Status.DICT_CODE_OVER_LEN.getStatus();
		}
		
		Long id = dict.getDictId();
		//自动增加父级目录
		//新增
		if(!MyUtils.isLongGtZero(id)){
			return dictDao.insertSelective(dict);
		}else{
			return dictDao.updateByPrimaryKeySelective(dict);
		}
	}
	@Override
    public int delete(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
            	if(!MyUtils.isLongGtZero(id)){
            		return StatusConstant.Status.DICT_DEL_FAIL.getStatus(); 
            	}
                //删除数据库数据
            	dictDao.deleteByPrimaryKey(id);
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
	public Dict getById(Long id) throws Exception {
		return dictDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Dict> getAll() throws Exception {
		return dictDao.selectByExample(new DictExample());
	}

	@Override
	public Dict getByCode() throws Exception {
		DictExample example = new DictExample();
		DictExample.Criteria criteria = example.createCriteria();
		criteria.andCodeEqualTo(DictConstant.BJWG_INFO_TYPE);
		List<Dict> ops = dictDao.selectByExample(example);
		Dict dict = null;
		if(!MyUtils.isListEmpty(ops)){
			dict = ops.get(0);
		}
		return dict;
	}
}
