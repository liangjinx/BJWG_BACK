package com.bjwg.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.MyProjectDao;
import com.bjwg.back.dao.PresentDao;
import com.bjwg.back.model.MyProject;
import com.bjwg.back.model.MyProjectExample;
import com.bjwg.back.model.Present;
import com.bjwg.back.model.PresentExample;
import com.bjwg.back.service.PresentService;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.ValidateUtil;
import com.bjwg.back.vo.SearchVo;

/**
 * 赠送好友物品接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class PresentServiceImpl implements PresentService 
{
	@Autowired
    private PresentDao presentDao;
	@Autowired
    private MyProjectDao myProjectDao;
	
	@Override
	public void queryPage(Pages<Present> page,SearchVo vo,Byte type) throws Exception {
		PresentExample example = new PresentExample();
		PresentExample.Criteria criteria = example.createCriteria();
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
		
		//type 类型  1:送猪仔2:送猪肉券
		criteria.andTypeEqualTo(type);
		
		int count = presentDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.present_id");
		List<Present> ops = presentDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrupdate(Present present) throws Exception{
		
		if(!MyUtils.isLongGtZero(present.getPresentUserId())){
			return StatusConstant.Status.PRESENT_PRESENT_USER_NULL.getStatus();
		}
		if(!MyUtils.isLongGtZero(present.getPresentedUserId())){
			return StatusConstant.Status.PRESENT_PRESENTED_USER_NULL.getStatus();
		}
		if(!MyUtils.isIntegerGtZero(present.getPresentNum())){
			return StatusConstant.Status.PRESENT_PRESENT_NUM_NULL.getStatus();
		}
		if(present.getPrice()==null){
			return StatusConstant.Status.PRESENT_PRICE_NULL.getStatus();
		}
		if(!ValidateUtil.validateDoublue(present.getPrice().toString(),false,0f,9999999.99f)){
			return StatusConstant.Status.PRESENT_PRICE_OVER_LEN.getStatus();
		}
		if(present.getTotalMoney()==null){
			return StatusConstant.Status.PRESENT_TOTAL_MONEY_NULL.getStatus();
		}
		if(!ValidateUtil.validateDoublue(present.getTotalMoney().toString(),false,0f,9999999.99f)){
			return StatusConstant.Status.PRESENT_TOTAL_MONEY_OVER_LEN.getStatus();
		}
		
		Long id = present.getPresentId();
		//自动增加父级目录
		//新增
		if(!MyUtils.isLongGtZero(id)){
			return presentDao.insertSelective(present);
		}else{
			return presentDao.updateByPrimaryKeySelective(present);
		}
	}
	@Override
    public int delete(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
            	if(!MyUtils.isLongGtZero(id)){
            		return StatusConstant.Status.PRESENT_DEL_FAIL.getStatus(); 
            	}
                //删除数据库数据
            	presentDao.deleteByPrimaryKey(id);
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
	public Present getById(Long id) throws Exception {
		return presentDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Present> getAll() throws Exception {
		return presentDao.selectByExample(new PresentExample());
	}

	@Override
	public List<MyProject> getMyProject(Long userId)
			throws Exception {
		MyProjectExample example = new MyProjectExample();
		MyProjectExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		return myProjectDao.selectByExample(example);
	}
}
