package com.bjwg.back.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjwg.back.dao.MyEarningsDao;
import com.bjwg.back.model.MyEarnings;
import com.bjwg.back.model.MyEarningsExample;
import com.bjwg.back.service.MyEarningsService;
import com.bjwg.back.service.MyProjectService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.SysconfigService;




@Service
public class MyEarningsServiceImpl implements MyEarningsService {

	@Resource
	private MyEarningsDao myEarningsDao;


	
	
	
	
	
	
	
	
	
	/**
	 * 查询我的收益
	 * @param MyEarningsExample
	 * @return
	 * @throws Exception
	 */
	public List<MyEarnings> selectByExample(MyEarningsExample example)throws Exception{
		
		return myEarningsDao.selectByExample(example);				
	}
	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public void insert(MyEarnings myEarnings)throws Exception{
		
		try {
			
			myEarningsDao.insert(myEarnings);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public int updateByPrimaryKeySelective(MyEarnings myEarnings)throws Exception{
		
		try {
			
			return myEarningsDao.updateByPrimaryKeySelective(myEarnings);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	
    /**
     * 计算收益
     * @param num 总数
     * @param rate 年收益率
     * @param money 单只成本
     * @param days 累计天数
     * @return
     * @throws Exception
     */
    public BigDecimal calcEarnings(Integer num,BigDecimal rate,BigDecimal money,Integer days) throws Exception{
    	
    	return  money.multiply(BigDecimal.valueOf(num)).multiply(rate.divide(BigDecimal.valueOf(100)))
    				.multiply(BigDecimal.valueOf(days)).divide(BigDecimal.valueOf(360),2,BigDecimal.ROUND_HALF_UP);
    }
    
    
    
 
   
	

	
    
}
