package com.bjwg.back.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.SlideDao;
import com.bjwg.back.model.Slide;
import com.bjwg.back.model.SlideExample;
import com.bjwg.back.service.SlideService;
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
public class SlideServiceImpl implements SlideService 
{
	@Autowired
    private SlideDao slideDao;
	
	@Override
	public void queryPage(Pages<Slide> page) throws Exception {
		SlideExample example = new SlideExample();
		SlideExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		int count = slideDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.slide_id");
		List<Slide> ops = slideDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrupdate(Slide slide) throws Exception{
		
		if(!StringUtils.isNotEmpty(slide.getPath())){
			return StatusConstant.Status.SLIDE_PATH_NULL.getStatus();
		}
		if(!StringUtils.isNotEmpty(slide.getName())){
			return StatusConstant.Status.SLIDE_NAME_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(slide.getName().trim(),false,0,100)){
			return StatusConstant.Status.SLIDE_NAME_OVER_LEN.getStatus();
		}
		if(!ValidateUtil.validateString(slide.getLink().trim(),true,0,100)){
			return StatusConstant.Status.SLIDE_LINK_OVER_LEN.getStatus();
		}
		
		Long id = slide.getSlideId();
		//新增
		if(!MyUtils.isLongGtZero(id)){
			return slideDao.insertSelective(slide);
		}else{
			return slideDao.updateByPrimaryKeySelective(slide);
		}
	}
	@Override
    public int delete(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
            	if(!MyUtils.isLongGtZero(id)){
            		return StatusConstant.Status.SLIDE_DEL_NULL.getStatus(); 
            	}
                //删除数据库数据
            	slideDao.deleteByPrimaryKey(id);
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
	public Slide getById(Long id) throws Exception {
		return slideDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Slide> getAll() throws Exception {
		return slideDao.selectByExample(new SlideExample());
	}
}
