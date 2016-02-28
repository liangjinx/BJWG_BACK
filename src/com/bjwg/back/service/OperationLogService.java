package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.OperationLog;
import com.bjwg.back.vo.SearchVo;

/**
 * 操作日志service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public interface OperationLogService
{
    
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<OperationLog> page,SearchVo vo,boolean isViewAllData,boolean isViewChildData,Long userId) throws Exception;
    /**
	 * @user Kim
	 * @param mobile
	 * @param typeAU 
	 * @return
	 */
	public int updateOperationLog(OperationLog comment) throws Exception;
	 
    /**
     * 删除操作日志
     * @param idList
     * @return
     * @throws Exception
     */
    public int deleteOperationLog(List<Long> idList) throws Exception;
    
    /**
	 * 操作日志记录器
	 * @param opeObjectId 操作对象Id
	 * @param opeObject  操作对象
	 * @param opeType    操作类型
	 * @param operator   操作人Id
	 * @param operatorName 操作人姓名
	 * @param remark  备注
	 */
    public void oplog(Long opeObjectId,String opeObject,String opeType,Long operator,String operatorName,String opeModule,String remark)throws Exception;
   
}
