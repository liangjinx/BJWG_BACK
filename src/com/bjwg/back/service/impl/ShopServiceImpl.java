package com.bjwg.back.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.AreaDao;
import com.bjwg.back.dao.ShopDao;
import com.bjwg.back.model.Area;
import com.bjwg.back.model.AreaExample;
import com.bjwg.back.model.Shop;
import com.bjwg.back.model.ShopExample;
import com.bjwg.back.service.ShopService;
import com.bjwg.back.util.MapUtil;
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
public class ShopServiceImpl implements ShopService 
{
	@Autowired
    private ShopDao shopDao;
	@Autowired
	private AreaDao areaDao;
	
	@Override
	public void queryPage(Pages<Shop> page) throws Exception {
		ShopExample example = new ShopExample();
		ShopExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		int count = shopDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.shop_id");
		List<Shop> ops = shopDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrUpdate(Shop shop) throws Exception{
		
		if(!StringUtils.isNotEmpty(shop.getName())){
			return StatusConstant.Status.SHOP_NAME_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(shop.getName().trim(),false,1,100)){
			return StatusConstant.Status.SHOP_NAME_OVER_LEN.getStatus();
		}
		if(!MyUtils.isLongGtZero(shop.getProvince())){
			return StatusConstant.Status.SHOP_PROVINCE_NULL.getStatus();
		}
		if(!StringUtils.isNotEmpty(shop.getAddress())){
			return StatusConstant.Status.SHOP_ADDRESS_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(shop.getAddress().trim(),false,1,100)){
			return StatusConstant.Status.SHOP_ADDRESS_OVER_LEN.getStatus();
		}
		if(!StringUtils.isNotEmpty(shop.getLogo())){
			return StatusConstant.Status.SHOP_LOGO_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(shop.getRemark().trim(),true,0,500)){
			return StatusConstant.Status.SHOP_REMARK_OVER_LEN.getStatus();
		}
		//如果城市为空，设置成与省份一致
		if(!MyUtils.isLongGtZero(shop.getCity())){
			shop.setCity(shop.getProvince());
		}
		Long id = shop.getShopId();
		
		Double[] info = null;
		try {
			// 地址需要有经纬度信息
			String address = shop.getAddress();
			String province_text = "";
			String city_text = "";
			Area area1 = null;
			Area area2 = null;
			
			if(MyUtils.isLongGtZero(shop.getProvince())){
				area1 = areaDao.selectByPrimaryKey(shop.getProvince());
				if(area1!=null){
					province_text = area1.getName();
				}
			}if(MyUtils.isLongGtZero(shop.getCity())){
				area2 = areaDao.selectByPrimaryKey(shop.getCity());
				if(area2!=null){
					city_text = area2.getName();
				}
			}
			address = province_text+city_text+address;
			if (StringUtils.isNotEmpty(address)) {
				String mapAddress = MapUtil.getBaiduMapResult(address.replaceAll(" ", ""));
				if(mapAddress==null){
					return StatusConstant.Status.SHOP_ADDRESS_VALID.getStatus();
				}
				if(StatusConstant.Status.SHOP_ADDRESS_CHECK_FAIL.getMsg().replaceAll("\"", "").equals(mapAddress)){
					return StatusConstant.Status.SHOP_ADDRESS_CHECK_FAIL.getStatus();
				}
				info = MapUtil.parseJson(mapAddress);
				if(info==null){
					return StatusConstant.Status.SHOP_ADDRESS_VALID.getStatus();
				}
				
				shop.setLongitude(BigDecimal.valueOf(info[0]));
				shop.setLatitude(BigDecimal.valueOf(info[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//自动增加父级目录
		//新增
		if(!MyUtils.isLongGtZero(id)){
			return shopDao.insertSelective(shop);
		}else{
			return shopDao.updateByPrimaryKeySelective(shop);
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
            	shopDao.deleteByPrimaryKey(id);
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
	public Shop getById(Long id) throws Exception {
		return shopDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Shop> getAll() throws Exception {
		return shopDao.selectByExample(new ShopExample());
	}
	//ajax调用,为了保证错误地址的时候显示页面上的信息
	@Override
	public int checkAddress(String address) throws Exception{
		// 地址需要有经纬度信息
		Double[] info = null;
		if (!StringUtils.isNotEmpty(address)) {
			return StatusConstant.Status.SHOP_ADDRESS_NULL.getStatus();
		}
		String mapAddress = MapUtil.getBaiduMapResult(address.replaceAll(" ", ""));
		if(mapAddress==null){
			return StatusConstant.Status.SHOP_ADDRESS_VALID.getStatus();
		}
		if(StatusConstant.Status.SHOP_ADDRESS_CHECK_FAIL.getMsg().replaceAll("\"", "").equals(mapAddress)){
			return StatusConstant.Status.SHOP_ADDRESS_CHECK_FAIL.getStatus();
		}
		info = MapUtil.parseJson(mapAddress);
		if(info==null){
			return StatusConstant.Status.SHOP_ADDRESS_VALID.getStatus();
		}
		return StatusConstant.Status.SUCCESS.getStatus();
	}
}
