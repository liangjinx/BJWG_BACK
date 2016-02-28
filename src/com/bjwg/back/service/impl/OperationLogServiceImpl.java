package com.bjwg.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.OperationLogDao;
import com.bjwg.back.model.OperationLog;
import com.bjwg.back.model.OperationLogExample;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.vo.SearchVo;

/**
 * 操作日志接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * @Modified By:Kim
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class OperationLogServiceImpl implements OperationLogService 
{
	@Autowired
    private OperationLogDao operationLogDao;
	
	@Override
	public void queryPage(Pages<OperationLog> page,SearchVo vo,boolean isViewAllData,boolean isViewChildData,Long userId) throws Exception {
		
		OperationLogExample example = new OperationLogExample();
		OperationLogExample.Criteria criteria = example.createCriteria();
		
		String sq = "";
		//不可以查看所有数据
		if(!isViewAllData){
			//可以查看子账号数据
			if(isViewChildData){
				sq = " and (op.operator ="+userId+" or exists (select 1 from bjwg_user_child_relation uc where uc.USER_ID = "+userId+" and uc.user_id_child = op.operator))";
			}else{
				sq = " and (op.operator ="+userId+")";
			}
		}
		criteria.addCriterion(sq+page.getWhereSql());
		
		//使用日期查询语句
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andOpeTimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andOpeTimeLessThanOrEqualTo(date2);
		}
		
		int count = operationLogDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("op.ope_Time desc,op.ope_id");
		List<OperationLog> ops = operationLogDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int updateOperationLog(OperationLog log) throws Exception{
//		if(log.getOpeObject()==null){
//			return StatusConstant.SHOP_COMMENT_CONTENT_NULL; 
//		}
//		if(log.getOpeObjectId()==null){
//			return StatusConstant.OPE_LOG_OBJECT_ID_NULL; 
//		}
		if(log.getOpeTime()==null){
			return StatusConstant.Status.OPE_LOG_TIME_NULL.getStatus();
		}
		if(log.getOpeType()==null){
			return StatusConstant.Status.OPE_LOG_TYPE_NULL.getStatus();
		}
		if(log.getOperator()==null){
			return StatusConstant.Status.OPE_LOG_OPERATOR_NULL.getStatus(); 
		}
		Long id = log.getOpeId();
		//新增
		if(!MyUtils.isLongGtZero(id)){
			log.setOpeTime(new Date());//设置操作时间
			log.setOpeId(-1L);
			return operationLogDao.insertSelective(log);
		}else{
			return operationLogDao.updateByPrimaryKeySelective(log);
		}
	}
	@Override
    public int deleteOperationLog(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
            	if(!MyUtils.isLongGtZero(id)){
            		return StatusConstant.Status.OPE_LOG_DEL_FAIL.getStatus(); 
            	}
                //删除数据库数据
            	operationLogDao.deleteByPrimaryKey(id);
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
	public void oplog(Long opeObjectId,String opeObject,String opeType,Long operator,String operatorName,String opeModule,String remark) throws Exception{
		  try{
			  OperationLog log = new OperationLog();
			  log.setOpeObjectId(opeObjectId);
			  log.setOpeObject(opeObject);
			  log.setOpeTime(new Date());
			  log.setOpeType(opeType);
			  log.setOperator(operator);
			  log.setOperatorName(operatorName);
			  log.setOpeModule(opeModule);
			  log.setRemark(remark);
			  operationLogDao.insertSelective(log);
		  }catch(Exception e){
			  e.printStackTrace();
			  throw e;
		  }
	}
}
