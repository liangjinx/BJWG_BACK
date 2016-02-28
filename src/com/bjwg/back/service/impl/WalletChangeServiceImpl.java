package com.bjwg.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.dao.WalletChangeDao;
import com.bjwg.back.model.WalletChange;
import com.bjwg.back.model.WalletChangeExample;
import com.bjwg.back.service.WalletChangeService;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.vo.SearchVo;

/**
 * 系统配置接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class WalletChangeServiceImpl implements WalletChangeService 
{
	@Autowired
    private WalletChangeDao walletChangeDao;
	
	@Override
	public void queryPage(Pages<WalletChange> page,SearchVo vo) throws Exception {
		WalletChangeExample example = new WalletChangeExample();
		WalletChangeExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		
		//时间查询语句
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andChangeTimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andChangeTimeLessThanOrEqualTo(date2);
		}
		
		int count = walletChangeDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.wallet_change_id");
		List<WalletChange> ops = walletChangeDao.selectByExample(example);
		page.setRoot(ops);
	}

//	@Override
//	public int saveOrupdate(WalletChange wallet) throws Exception{
//		
//		if(!MyUtils.isLongGtZero(wallet.getUserId())){
//			return StatusConstant.Status.WALLET_USER_ID_NULL.getStatus();
//		}
//		if(wallet.getMoney()==null){
//			return StatusConstant.Status.WALLET_MONEY_NULL.getStatus();
//		}
//		if(!ValidateUtil.validateDoublue(wallet.getMoney().toString(),false,0f,999999999.99f)){
//			return StatusConstant.Status.WALLET_MONEY_OVER_LEN.getStatus();
//		}
//		
//		Long id = wallet.getWalletChangeId();
//		//自动增加父级目录
//		//新增
//		if(!MyUtils.isLongGtZero(id)){
//			wallet.setStatus((byte)1);//正常使用
//			return walletChangeDao.insertSelective(wallet);
//		}else{
//			return walletChangeDao.updateByPrimaryKeySelective(wallet);
//		}
//	}
//	@Override
//    public int delete(List<Long> idList) throws Exception{
//        try
//        {
//            for (Long id : idList)
//            {
//            	if(!MyUtils.isLongGtZero(id)){
//            		return StatusConstant.Status.DICT_DEL_FAIL.getStatus(); 
//            	}
//                //删除数据库数据
//            	walletChangeDao.deleteByPrimaryKey(id);
//            }
//            return StatusConstant.Status.SUCCESS.getStatus(); 
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            throw e;
//        }
//    }

	@Override
	public WalletChange getById(Long id) throws Exception {
		return walletChangeDao.selectByPrimaryKey(id);
	}

//	@Override
//	public List<WalletChange> getAll() throws Exception {
//		return walletChangeDao.selectByExample(new WalletChangeExample());
//	}
}
