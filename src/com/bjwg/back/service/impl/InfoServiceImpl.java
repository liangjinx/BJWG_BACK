package com.bjwg.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.InfoDao;
import com.bjwg.back.model.Info;
import com.bjwg.back.model.InfoExample;
import com.bjwg.back.service.InfoService;
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
public class InfoServiceImpl implements InfoService 
{
	@Autowired
    private InfoDao infoDao;
	
	@Override
	public void queryPage(Pages<Info> page,SearchVo vo) throws Exception {
		
		InfoExample example = new InfoExample();
		InfoExample.Criteria criteria = example.createCriteria();
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
		
		int count = infoDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.info_id");
		List<Info> ops = infoDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrupdate(Info info) throws Exception{
		
		if(!StringUtils.isNotEmpty(info.getTitle())){
			return StatusConstant.Status.INFO_TITLE_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(info.getTitle().trim(),false,1,100)){
			return StatusConstant.Status.INFO_TITLE_OVER_LEN.getStatus();
		}
		if(!StringUtils.isNotEmpty(info.getDetail())){
			return StatusConstant.Status.INFO_DETAIL_NULL.getStatus();
		}
//		if(!ValidateUtil.validateString(info.getDetail().trim(),false,1,500)){
//			return StatusConstant.Status.INFO_DETAIL_OVER_LEN.getStatus();
//		}
		if(!StringUtils.isNotEmpty(info.getPath())){
			return StatusConstant.Status.INFO_PATH_NULL.getStatus();
		}
		Long id = info.getInfoId();
		if(info.getSort()==null){
			info.setSort(0L);
		}
		if(!ValidateUtil.validateLong(info.getSort()+"", true, -99999L, 99999L)){
			return StatusConstant.Status.INFO_SORT_OVER_LENGTH.getStatus();
		}
		//自动增加父级目录
		//新增
		if(!MyUtils.isLongGtZero(id)){
			info.setStatus((byte)1);
			info.setCtime(new Date());
			if(info.getType()==null){
				info.setType(Byte.parseByte("1"));
			}
			return infoDao.insertSelective(info);
		}else{
			return infoDao.updateByPrimaryKeySelective(info);
		}
	}
	@Override
    public int delete(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
            	if(!MyUtils.isLongGtZero(id)){
            		return StatusConstant.Status.INFO_DEL_FAIL.getStatus(); 
            	}
                //删除数据库数据
            	infoDao.deleteByPrimaryKey(id);
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
	public Info getById(Long id) throws Exception {
		return infoDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Info> getAll() throws Exception {
		return infoDao.selectByExample(new InfoExample());
	}
}
