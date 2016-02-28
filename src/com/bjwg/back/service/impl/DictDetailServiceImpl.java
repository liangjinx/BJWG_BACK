package com.bjwg.back.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.DictDetailDao;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.DictDetailExample;
import com.bjwg.back.service.DictDetailService;
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
public class DictDetailServiceImpl implements DictDetailService 
{
	@Autowired
    private DictDetailDao dictDetailDao;
	
	@Override
	public void queryPage(Pages<DictDetail> page) throws Exception {
		DictDetailExample example = new DictDetailExample();
		DictDetailExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		int count = dictDetailDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.dict_detail_id");
		List<DictDetail> ops = dictDetailDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrupdate(DictDetail dictDetail) throws Exception{
		if(!StringUtils.isNotEmpty(dictDetail.getName())){
			return StatusConstant.Status.DICT_DETAIL_NAME_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(dictDetail.getName().trim(),false,1,45)){
			return StatusConstant.Status.DICT_DETAIL_NAME_OVER_LEN.getStatus();
		}
		if(!StringUtils.isNotEmpty(dictDetail.getCode())){
			return StatusConstant.Status.DICT_DETAIL_CODE_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(dictDetail.getCode().trim(),false,1,45)){
			return StatusConstant.Status.DICT_DETAIL_CODE_OVER_LEN.getStatus();
		}
		if(!ValidateUtil.validateString(dictDetail.getCode().trim(),true,0,255)){
			return StatusConstant.Status.DICT_DETAIL_REMARK_OVER_LEN.getStatus();
		}
		Long id = dictDetail.getDictDetailId();
		//新增
		if(!MyUtils.isLongGtZero(id)){
			return dictDetailDao.insertSelective(dictDetail);
		}else{
			return dictDetailDao.updateByPrimaryKeySelective(dictDetail);
		}
	}
	@Override
    public int delete(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
            	if(!MyUtils.isLongGtZero(id)){
            		return StatusConstant.Status.DICT_DETAIL_DEL_FAIL.getStatus(); 
            	}
                //删除数据库数据
            	dictDetailDao.deleteByPrimaryKey(id);
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
	public DictDetail getById(Long id) throws Exception {
		return dictDetailDao.selectByPrimaryKey(id);
	}

	@Override
	public List<DictDetail> getDictDetailsByCode(String code) throws Exception {
		DictDetailExample example = new DictDetailExample();
		DictDetailExample.Criteria criteria = example.createCriteria();
		criteria.andCodeEqualTo(code);
		List<DictDetail> ops = dictDetailDao.selectByExample(example);
		return ops;
	}
	@Override
	public List<DictDetail> getDictDetailsByParentCode(String parentCode) throws Exception {
		List<DictDetail> ops = dictDetailDao.getDictDetailsByParentCode(parentCode);
		return ops;
	}

	@Override
	public void queryInfoSortPage(Pages<DictDetail> page) throws Exception {
		DictDetailExample example = new DictDetailExample();
		DictDetailExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		criteria.andCodeEqualTo(DictConstant.BJWG_INFO_TYPE);
		int count = dictDetailDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.dict_detail_id");
		List<DictDetail> ops = dictDetailDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int getMaxValue(String code) {
		return dictDetailDao.getMaxValue(DictConstant.BJWG_INFO_TYPE);
	}
}
