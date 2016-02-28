package com.bjwg.back.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.WalletDao;
import com.bjwg.back.model.Wallet;
import com.bjwg.back.model.WalletExample;
import com.bjwg.back.service.WalletService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.ValidateUtil;

/**
 * 系统配置接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class WalletServiceImpl implements WalletService 
{
	@Autowired
    private WalletDao walletDao;
	
	@Override
	public void queryPage(Pages<Wallet> page) throws Exception {
		WalletExample example = new WalletExample();
		WalletExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		int count = walletDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.wallet_id");
		List<Wallet> ops = walletDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrupdate(Wallet wallet) throws Exception{
		
		if(!MyUtils.isLongGtZero(wallet.getUserId())){
			return StatusConstant.Status.WALLET_USER_ID_NULL.getStatus();
		}
		if(wallet.getMoney()==null){
			return StatusConstant.Status.WALLET_MONEY_NULL.getStatus();
		}
		if(!ValidateUtil.validateDoublue(wallet.getMoney().toString(),false,0f,9999999.99f)){
			return StatusConstant.Status.WALLET_MONEY_OVER_LEN.getStatus();
		}
		
		Long id = wallet.getWalletId();
		//自动增加父级目录
		//新增
		if(!MyUtils.isLongGtZero(id)){
			wallet.setStatus((byte)1);//正常使用
			return walletDao.insertSelective(wallet);
		}else{
			return walletDao.updateByPrimaryKeySelective(wallet);
		}
	}
	@Override
    public int delete(List<Long> idList) throws Exception{
        try
        {
            for (Long id : idList)
            {
            	if(!MyUtils.isLongGtZero(id)){
            		return StatusConstant.Status.DICT_DEL_FAIL.getStatus(); 
            	}
                //删除数据库数据
            	walletDao.deleteByPrimaryKey(id);
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
	public Wallet getById(Long id) throws Exception {
		return walletDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Wallet> getAll() throws Exception {
		return walletDao.selectByExample(new WalletExample());
	}
}
