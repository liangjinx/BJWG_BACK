package com.bjwg.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.BulletinDao;
import com.bjwg.back.model.Bulletin;
import com.bjwg.back.model.BulletinExample;
import com.bjwg.back.service.BulletinService;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.ValidateUtil;
import com.bjwg.back.vo.SearchVo;

/**
 * 公告接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class BulletinServiceImpl implements BulletinService 
{
	@Autowired
    private BulletinDao bulletinDao;
	
	@Override
	public void queryPage(Pages<Bulletin> page,SearchVo vo) throws Exception {
		
		BulletinExample example = new BulletinExample();
		BulletinExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		
		//时间查询语句
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andCtimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andCtimeLessThanOrEqualTo(date2);
		}
		
		int count = bulletinDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.bulletin_id");
		List<Bulletin> ops = bulletinDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrupdate(Bulletin dict) throws Exception{
		
		if(!StringUtils.isNotEmpty(dict.getTitle())){
			return StatusConstant.Status.BULLETIN_TITLE_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(dict.getTitle().trim(),false,1,100)){
			return StatusConstant.Status.BULLETIN_TITLE_OVER_LEN.getStatus();
		}
		if(!StringUtils.isNotEmpty(dict.getContent())){
			return StatusConstant.Status.BULLETIN_CONTENT_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(dict.getContent().trim(),false,1,500)){
			return StatusConstant.Status.BULLETIN_CONTENT_OVER_LEN.getStatus();
		}
		
		Long id = dict.getBulletinId();
		//自动增加父级目录
		//新增
		if(!MyUtils.isLongGtZero(id)){
			dict.setStatus((byte)1);
			dict.setCtime(new Date());
			if(dict.getType()==null){
				dict.setType((short)1);
			}
			return bulletinDao.insertSelective(dict);
		}else{
			return bulletinDao.updateByPrimaryKeySelective(dict);
		}
	}
	@Override
    public int delete(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
            	if(!MyUtils.isLongGtZero(id)){
            		return StatusConstant.Status.BULLETIN_DEL_FAIL.getStatus(); 
            	}
                //删除数据库数据
            	bulletinDao.deleteByPrimaryKey(id);
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
	public Bulletin getById(Long id) throws Exception {
		return bulletinDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Bulletin> getAll() throws Exception {
		return bulletinDao.selectByExample(new BulletinExample());
	}
}
