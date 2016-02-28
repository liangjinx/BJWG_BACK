package com.bjwg.back.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.ProjectConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.ProjectDao;
import com.bjwg.back.model.Project;
import com.bjwg.back.model.ProjectExample;
import com.bjwg.back.service.ProjectService;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.ValidateUtil;
import com.bjwg.back.vo.ProjectFeeInfo;
import com.bjwg.back.vo.SearchVo;

/**
 * 抢购项目接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class ProjectServiceImpl implements ProjectService 
{
	@Autowired
    private ProjectDao projectDao;
	
	@Override
	public void queryPage(Pages<Project> page,SearchVo vo) throws Exception {
		
		ProjectExample example = new ProjectExample();
		ProjectExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		
		//时间查询语句
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andBeginTimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andEndTimeLessThanOrEqualTo(date2);
		}
		
		int count = projectDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.paincbuy_project_id desc");
		List<Project> ops = projectDao.selectByExample(example);
		page.setRoot(ops);
		
	}

	@Override
	public int saveOrupdate(Project project) throws Exception{
		
		if(!StringUtils.isNotEmpty(project.getName())){
			return StatusConstant.Status.DICT_NAME_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(project.getName().trim(),false,1,45)){
			return StatusConstant.Status.DICT_NAME_OVER_LEN.getStatus();
		}
		if(project.getPrice()==null){
			return StatusConstant.Status.PROJECT_PRICE_NULL.getStatus();
		}
		if(!ValidateUtil.validateDoublue(project.getPrice().toString(),false,0f,9999999.99f)){
			return StatusConstant.Status.PROJECT_PRICE_OVER_LEN.getStatus();
		}
		
		if(project.getNum()==null){
			return StatusConstant.Status.PROJECT_PRICE_NULL.getStatus();
		}
		if(!ValidateUtil.validateInteger(project.getNum().toString(),false,1,32767)){
			return StatusConstant.Status.PROJECT_NUM_OVER_LEN.getStatus();
		}
		
		/**
		if(project.getTotalMoney()==null){
			return StatusConstant.Status.PROJECT_TOTAL_MONEY_NULL.getStatus();
		}
		if(!ValidateUtil.validateDoublue(project.getTotalMoney().toString(),false,0f,9999999999.99f)){
			return StatusConstant.Status.PROJECT_TOTAL_MONEY_OVER_LEN.getStatus();
		}
		*/
		
		if(project.getBeginTime()==null){
			return StatusConstant.Status.PROJECT_BEGIN_TIME_NULL.getStatus();
		}
		if(project.getEndTime()==null){
			return StatusConstant.Status.PROJECT_END_TIME_NULL.getStatus();
		}
		Long id = project.getPaincbuyProjectId();
		if(!MyUtils.isLongGtZero(id)){
			project.setPaincbuyProjectId(-1L);
		}
		Date now = new Date();
		//查询所有进行的项目，不包括自己
		ProjectExample example = new ProjectExample();
		ProjectExample.Criteria criteria = example.createCriteria();
		criteria.andBeginTimeLessThanOrEqualTo(now);
		criteria.andEndTimeGreaterThanOrEqualTo(now);
		criteria.andPaincbuyProjectIdNotEqualTo(project.getPaincbuyProjectId());
		
		List<Project> deals = projectDao.selectByExample(example);
		if(!MyUtils.isListEmpty(deals)){
			for(Project deal : deals){
				boolean flag = DateUtil.isInDates(project.getBeginTime(),project.getEndTime(),deal.getBeginTime(),deal.getEndTime());
				if(flag){
					return StatusConstant.Status.PROJECT_DEALS_DATE_LIMIT.getStatus();
				}
			}
		}
		boolean flag = DateUtil.nowInDateEquals(now,project.getBeginTime(),project.getEndTime());
		if(flag){
			project.setStatus(ProjectConstant.PROJECT_STATUS_1);//进行中
		}else if(now.before(project.getBeginTime())){
			project.setStatus(ProjectConstant.PROJECT_STATUS_0);//未开始
		}else if(now.after(project.getEndTime())){
			project.setStatus(ProjectConstant.PROJECT_STATUS_2);//已结束
		}
		project.setTotalMoney(project.getPrice());
		//自动增加父级目录
		//新增
		if(!MyUtils.isLongGtZero(id)){
			BigDecimal d = new BigDecimal(0);
			project.setCtime(new Date());
			project.setLeftNum(project.getNum());
			project.setOtherFee(d);
			return projectDao.insertSelective(project);
		}else{
			Project project2 = projectDao.selectByPrimaryKey(project.getPaincbuyProjectId());
			
			//变更数量
			int changeNum = project2.getNum() - project.getNum();
			
			if((project2.getLeftNum() - changeNum) < 0){
				return StatusConstant.Status.PROJECT_LEFT_NUM_NULLITY.getStatus();
			}
			//剩余数量计算
			project.setLeftNum((short)(project2.getLeftNum() - changeNum));
			
			return projectDao.updateByPrimaryKeySelective(project);
		}
	}
	@Override
    public int delete(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
            	if(!MyUtils.isLongGtZero(id)){
            		return StatusConstant.Status.PROJECT_DEL_FAIL.getStatus(); 
            	}
                //删除数据库数据
            	projectDao.deleteByPrimaryKey(id);
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
	public Project getById(Long id) throws Exception {
		return projectDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Project> getAll() throws Exception {
		return projectDao.selectByExample(new ProjectExample());
	}

	@Override
	public List<ProjectFeeInfo> parseFeeDetail(String jsonFee) throws Exception {
		List<ProjectFeeInfo> priceList = null;
		if(StringUtils.isNotEmpty(jsonFee)){
			JSONArray jsonArray = null;
			try{
				jsonArray = JSONArray.fromObject(jsonFee);
			}catch(JSONException e){
				e.printStackTrace();
				throw e;
			}
			if(jsonArray!=null && jsonArray.size()>0){
				priceList = new ArrayList<ProjectFeeInfo>();
				for(int i=0;i<jsonArray.size();i++){
					JSONObject obj = jsonArray.getJSONObject(i);
					ProjectFeeInfo price = new ProjectFeeInfo();
					if(obj.containsKey("feename")){
						price.setFeename(obj.getString("feename"));
					}else{
						continue;
					}
					if(obj.containsKey("price")){
						price.setPrice(obj.getDouble("price"));
					}else{
						continue;
					}
					priceList.add(price);
				}
			}
		}
		return priceList;
	}
	
}
