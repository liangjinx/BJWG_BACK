package com.bjwg.back.service.impl.timer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjwg.back.base.constant.CommConstant;
import com.bjwg.back.base.constant.OrderConstant;
import com.bjwg.back.base.constant.ProjectConstant;
import com.bjwg.back.base.constant.SysConstant;
import com.bjwg.back.base.constant.SystemConstant;
import com.bjwg.back.dao.MyCouponDao;
import com.bjwg.back.dao.MyProjectDao;
import com.bjwg.back.dao.OrderDao;
import com.bjwg.back.dao.SysconfigDao;
import com.bjwg.back.dao.WalletChangeDao;
import com.bjwg.back.dao.WalletDao;
import com.bjwg.back.model.MyCoupon;
import com.bjwg.back.model.MyCouponExample;
import com.bjwg.back.model.MyProject;
import com.bjwg.back.model.MyProjectExample;
import com.bjwg.back.model.Order;
import com.bjwg.back.model.OrderExample;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.model.SysconfigExample;
import com.bjwg.back.model.Wallet;
import com.bjwg.back.model.WalletChange;
import com.bjwg.back.service.SysconfigService;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.FileConstant;
import com.bjwg.back.util.FileUtils;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.RandomUtils;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.ToolKit;

/**
 * @author Administrator
 *
 */
@Service
public class MyProjectTimerImpl {
	
	@Autowired
    private SysconfigDao sysconfigDao;
	@Autowired
    private MyProjectDao myProjectDao;
	@Autowired
    private MyCouponDao myCouponDao;
	@Autowired
    private OrderDao orderDao;
	@Autowired
    private WalletDao walletDao;
	@Autowired
    private WalletChangeDao walletChangeDao;
	
//	protected static Logger logger = LogManager.getLogger();
	public static final Logger  logger = LoggerFactory.getLogger("LOGISTICS-COMPONENT"); 
	
	public void execute() throws Exception{
		Date now = new Date();
		try{
			//1、每天晚上00:00:01秒定时扫描 我的收益记录里面未处理的记录，当前时间超过过期时间的记录，自动设置回报方式为第一种
			//查询未处理的我的收益项目
			setUpProjectDealType(now);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("定时设置未处理的收益项目的默认回报方式出错:");
			logger.error(e.toString());
			throw e;
		}
		
		try{
			getBalance(now);
			
		}catch(Exception e){
			
			e.printStackTrace();
			logger.error("定时取消未付款、委托寄卖错误:");
			logger.error(e.toString());
			throw e;
		}
		
		
		try{
			//2、屠宰配送、未付款的订单，并且当前时间 > 支付过期时间,取消订单
			executeOrderCancel(now);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("定时取消未付款、屠宰配送订单出错:");
			logger.error(e.toString());
			throw e;
		}
		try{
			//3、领取活猪,当前时间超过过期时间的记录，自动设置回报方式为第一种,并扣除费用
			setUpTypeOfPigs(now);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("定时取消未确认收货、领取活猪订单出错:");
			logger.error(e.toString());
			throw e;
		}
		try{
			//4、选择领取猪肉券 到时间时生成猪肉券记录
			//generateCoupon(now);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("定时生成猪肉券出错:");
			logger.error(e.toString());
			throw e;
		}
	}
	
	
	/**1.委托寄卖
	 * 若用户购买仔猪到期，则每头生成2059.2的收益
	 * 
	 * @param now
	 * @throws Exception 
	 */
	private void getBalance(Date now) throws Exception {
		
		logger.info("定时扫描... 选择委托寄卖的用户到期后每头猪获得2059.2的余额收益");
		
		MyProjectExample example = new MyProjectExample();
		MyProjectExample.Criteria criteria = example.createCriteria();
		criteria.andDealTypeEqualTo(ProjectConstant.REWARDS_1);
		criteria.andDealStatusEqualTo(ProjectConstant.DEALS_TATUS_0);
		criteria.andEndTimeLessThan(now);
		List<MyProject> ops = myProjectDao.selectByExample(example);
		
		List<MyProject> temp = new ArrayList<MyProject>();
		List<Long> list = new ArrayList<Long>();
	
		Map<Long, Integer> map = new HashMap<Long, Integer>();
		
		for(MyProject op : ops){
			
			//判断是否到期
			boolean isExpired = DateUtil.compareDate(now,op.getEndTime());
			
			if(isExpired){
				//到期后
				op.setDealType(ProjectConstant.REWARDS_1);
				op.setDealStatus(ProjectConstant.DEALS_TATUS_1);
				op.setDealTime(now);
				temp.add(op);
				Long userId = op.getUserId();
				list.add(userId);
				if(map.containsKey(userId)){  
                	map.put(userId, map.get(userId) + op.getNum());
                }else{
                    map.put(userId, op.getNum());
                }
				
			}
		
		}
		//修改未处理和过期订单的取消
		if(!MyUtils.isListEmpty(temp)  && !MyUtils.isListEmpty(list)){
			//批量处理
			myProjectDao.updateBatch(temp);
			String userIds = StringUtils.stringList2StrinNot(list, ",");
			//取消订单
			orderDao.updateStatusBatch(OrderConstant.ORDER_STATUS_0,OrderConstant.ORDER_TYPE_1,OrderConstant.ORDER_STATUS_4,userIds);
			//增加收入
			changeTheBalance2(map,now);
		}
		
		
		logger.info(">>>>>4.定时扫描结束 委托寄卖,未处理 的");	
		
	}

	/**
	 * 每天晚上00:00:01秒定时扫描 我的收益记录里面未处理的记录，当前时间超过过期时间的记录，自动设置回报方式为第一种
	 * 查询未处理的我的收益项目
	 */
	private void setUpProjectDealType(Date now) throws Exception{
		
		logger.info(">>>>>1.定时扫描开始 我的收益记录里面未处理的记录，当前时间超过过期时间的记录，自动设置回报方式为第一种");
		
		MyProjectExample example = new MyProjectExample();
		MyProjectExample.Criteria criteria = example.createCriteria();
		criteria.andDealTypeIsNull();
		List<MyProject> ops = myProjectDao.selectByExample(example);
		
		if(!MyUtils.isListEmpty(ops)){
			logger.info(">>>>>2.定时扫描 我的收益记录里面未处理的记录，查找到的数据有："+ops.size() +"条记录");
			System.out.println("--------------------------");
			System.out.println("zi"+ops.size());
			System.out.println("--------------------------");
			List<MyProject> temp = new ArrayList<MyProject>();
			int days = 0;
			Sysconfig sys = getByCode(SystemConstant.ORDER_CONFIG_CONFIRM_REWARDS_BEFORE_N_DAYS);
			if(sys!=null){
				if(StringUtils.isNumeric(sys.getValue())){
					days =  Integer.parseInt(sys.getValue());
				}
			}
			System.out.println("days===="+days);
			for(MyProject op : ops){
				Date endTime = DateUtil.getDateBefore(op.getEndTime(),days);
				boolean isExpired = DateUtil.compareDate(now,endTime);
				
	           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				Date date =sdf.parse("2016-02-27");
				boolean isExpired2 = DateUtil.compareDate(date,op.getEndTime());
				if(isExpired && isExpired2){
					logger.info(">>>>>3.定时扫描 我的收益记录里面未处理的记录。打印数据："+op.toString());
					//op.setDealType(ProjectConstant.REWARDS_1);
					op.setDealStatus(ProjectConstant.DEALS_TATUS_1);
					op.setDealTime(now);
					temp.add(op);
				}
				else if(isExpired){
					logger.info(">>>>>3.定时扫描 我的收益记录里面未处理的记录。打印数据："+op.toString());
					op.setDealType(ProjectConstant.REWARDS_1);
					op.setDealStatus(ProjectConstant.DEALS_TATUS_1);
					op.setDealTime(now);
					temp.add(op);	
				}
			}
			if(!MyUtils.isListEmpty(temp)){
				//批量处理
				myProjectDao.updateBatch(temp);
			}
		}
		logger.info(">>>>>4.定时扫描结束 我的收益记录里面未处理的记录");
	}
	
	/**
	 * 领取活猪,未处理 的，当前时间超过过期时间的记录，自动设置回报方式为第一种，并扣除费用
	 * 删除订单
	 */
	private void setUpTypeOfPigs(Date now) throws Exception{
		
		logger.info(">>>>>1.定时扫描开始 领取活猪,未处理 的，当前时间超过过期时间的记录，自动设置回报方式为第一种");
		
		MyProjectExample example = new MyProjectExample();
		MyProjectExample.Criteria criteria = example.createCriteria();
		criteria.andDealTypeEqualTo(ProjectConstant.REWARDS_3);
		criteria.andDealStatusEqualTo(ProjectConstant.DEALS_TATUS_0);
		List<MyProject> ops = myProjectDao.selectByExample(example);
		if(!MyUtils.isListEmpty(ops)){
			
			logger.info(">>>>>2.定时扫描开始 领取活猪,未处理 的，符合的数据有："+ops.size() + "条");
			
			List<MyProject> temp = new ArrayList<MyProject>();
			int days = 5;
			Sysconfig sys = getByCode(SystemConstant.ORDER_CONFIG_OVERDUE_DAYS_FOR_PIGS);
			if(sys!=null){
				if(StringUtils.isNumeric(sys.getValue())){
					days =  Integer.parseInt(sys.getValue());
				}
			}
			List<Long> list = new ArrayList<Long>();
			System.out.println("领取活猪逾期天数days===="+days);
			Map<Long, Integer> map = new HashMap<Long, Integer>();
			for(MyProject op : ops){
				//加5天
				Date endTime = DateUtil.getDateAfter(op.getEndTime(),days);
				boolean isExpired = DateUtil.compareDate(now,endTime);
				if(isExpired){
					logger.info(">>>>>3.定时扫描开始 领取活猪,未处理 的，打印数据："+op.toString());
					op.setDealType(ProjectConstant.REWARDS_1);
					op.setDealStatus(ProjectConstant.DEALS_TATUS_1);
					op.setDealTime(now);
					temp.add(op);
					Long userId = op.getUserId();
					list.add(userId);
					if(map.containsKey(userId)){
                    	map.put(userId, map.get(userId) + op.getNum());
                    }else{
                        map.put(userId, op.getNum());
                    }
				}
			}
			if(!MyUtils.isListEmpty(temp)  && !MyUtils.isListEmpty(list)){
				//批量处理
				myProjectDao.updateBatch(temp);
				String userIds = StringUtils.stringList2StrinNot(list, ",");
				//取消订单
				orderDao.updateStatusBatch(OrderConstant.ORDER_STATUS_0,OrderConstant.ORDER_TYPE_3,OrderConstant.ORDER_STATUS_4,userIds);
				//扣除费用
				changeTheBalance(map,now);
			}
		}
		logger.info(">>>>>4.定时扫描结束 领取活猪,未处理 的");
	}
	
	/**
	 * 屠宰配送、未付款的订单，并且当前时间 > 支付过期时间,取消订单
	 * @param now
	 */
	private void executeOrderCancel(Date now){
		
		logger.info(">>>>>1.定时扫描开始 屠宰配送、未付款的订单，并且当前时间 > 支付过期时间,取消订单");
		OrderExample orderExample = new OrderExample();
		OrderExample.Criteria orderCriteria = orderExample.createCriteria();
		orderCriteria.andTypeEqualTo(OrderConstant.ORDER_TYPE_2);//1:抢购 2:屠宰寄送3:领取活猪
		orderCriteria.andStatusEqualTo(OrderConstant.ORDER_STATUS_1);//未付款
		orderCriteria.andOverTimeLessThan(now);
		List<Order> orders = orderDao.selectByExample(orderExample);
		if(!MyUtils.isListEmpty(orders)){
			logger.info(">>>>>2.屠宰配送、未付款的订单，符合的数据有："+orders.size() +"条");
			List<Order> temp = new ArrayList<Order>();
			for(Order order : orders){
				logger.info(">>>>>3.屠宰配送、未付款的订单，打印数据：order_id:"+order.getOrderId()+" ,order_code"+order.getOrderCode()+"order_type:"+order.getType()+" ,status:"+order.getStatus()+" ,over_time:"+order.getOverTime());
				order.setStatus(OrderConstant.ORDER_STATUS_0);//订单取消
				temp.add(order);
				//我的收益里面回收方式改成第一种
				Long pid = order.getRelationId();//抢购项目id
				Long uid = order.getUserId();
				//根据抢购项目id 和 用户ID 修改 我的收益的回报方式
				myProjectDao.updateDealType(ProjectConstant.REWARDS_1,ProjectConstant.REWARDS_2,pid,uid);
			}
			if(!MyUtils.isListEmpty(temp)){
				//批量处理
				orderDao.updateBatch(temp);
			}
		}
		
		logger.info(">>>>>4.定时扫描结束 屠宰配送、未付款的订单，并且当前时间 > 支付过期时间,取消订单");
	}
	
	/**
	 * 选择领取猪肉券 到时间时生成猪肉券记录
	 */
	private void generateCoupon(Date now) throws Exception{
		
		logger.info(">>>>>1.定时扫描开始 领取猪肉券,但未生成猪肉券");
		
		MyProjectExample example = new MyProjectExample();
		MyProjectExample.Criteria criteria = example.createCriteria();
		criteria.andDealTypeEqualTo(ProjectConstant.REWARDS_4);
		criteria.andDealStatusEqualTo(ProjectConstant.DEALS_TATUS_0);
		criteria.andEndTimeLessThan(now);
		List<MyProject> ops = myProjectDao.selectByExample(example);
		System.out.println("猪肉券"+ops.size());
		if(!MyUtils.isListEmpty(ops)){
			
			logger.info(">>>>>2.定时扫描开始 领取猪肉券,符合的数据有："+ops.size() + "条");
			//猪肉券金额从配置文件取
			
			SysconfigExample sysExample = new SysconfigExample();
			sysExample.or().andCodeEqualTo(SystemConstant.PROJECT_IMG_ACCESS_URL);
			sysExample.or().andCodeEqualTo(SystemConstant.PROJECT_IMG_UPLOAD_ROOT_PATH);
			sysExample.or().andCodeEqualTo(SystemConstant.QR_CODE_URL);
			sysExample.or().andCodeEqualTo(SystemConstant.PROJECT_ACCESS_URL);
			sysExample.or().andCodeEqualTo(SystemConstant.SYS_CONFIG_PIG_COUPON_MONEY);
			sysExample.or().andCodeEqualTo(SystemConstant.SYS_CONFIG_PIG_COUPON_VALIDITY);
			List<Sysconfig> syslist = sysconfigDao.selectByExample(sysExample);
			
			Double couponMoney = null;
			//二维码url
			String url = null;
			//二维码图片地址
			String imgPath = null;
			//访问地址
			String accessUrl = null;
			//项目地址
			String projectUrl = null;
			
			Integer validity = null;
			
			if(!MyUtils.isListEmpty(syslist)){
				for (Sysconfig syscon : syslist) {
					if(SystemConstant.SYS_CONFIG_PIG_COUPON_VALIDITY.equals(syscon.getCode())){
						validity = Integer.parseInt(syscon.getValue());
					}else if(SystemConstant.SYS_CONFIG_PIG_COUPON_MONEY.equals(syscon.getCode())){
						couponMoney = Double.parseDouble(syscon.getValue());
					}else if(SystemConstant.PROJECT_IMG_ACCESS_URL.equals(syscon.getCode())){
						//http://testweixin.hzd.com/
						url = syscon.getValue();
					}else if(SystemConstant.QR_CODE_URL.equals(syscon.getCode())){
						///wpnv/ixhome?inviteCode=
						accessUrl = syscon.getValue();
					}else if(SystemConstant.PROJECT_ACCESS_URL.equals(syscon.getCode())){
						///wpnv/ixhome?inviteCode=
						projectUrl = syscon.getValue();
					}else{
						///mnt/tomcat_bjwg/webapps/ROOT/resources/
						imgPath = syscon.getValue();
					}
				}
			}
			Date endTime = DateUtil.getDateAfter(now, validity);
			List<MyCoupon> list = new ArrayList<MyCoupon>();
			for(MyProject op : ops){
				boolean isExpired = DateUtil.compareDate(now,op.getEndTime());
				
				
				if(isExpired){
					logger.info(">>>>>3.定时扫描开始 领取猪肉券,打印数据："+op.toString());
					//查询之前有没有增加过
					MyCouponExample myCouponExample = new MyCouponExample();
					MyCouponExample.Criteria myCouponCriteria = myCouponExample.createCriteria();
					myCouponCriteria.andRelationTypeEqualTo(ProjectConstant.MY_COUPON_RELATION_TYPE_1);//关联类型为1,选择猪肉券
					myCouponCriteria.andRelationIdEqualTo(op.getEarningsId());
					int count = myCouponDao.countByExample(myCouponExample);
					
					
					//未生成猪肉券就生成一个
					if(count <= 0){
						
						BigDecimal money_b = new BigDecimal(couponMoney);
						MyCoupon myCoupon = new MyCoupon();
						
						myCoupon.setCouponName(op.getPaincbuyProjectName());
						myCoupon.setCouponMoney(money_b);
						myCoupon.setBeginTime(now);
						myCoupon.setEndTime(endTime);
						myCoupon.setCouponCode(RandomUtils.getOrderCode());
						myCoupon.setRelationId(op.getEarningsId());
						myCoupon.setRelationType(ProjectConstant.MY_COUPON_RELATION_TYPE_1);
						myCoupon.setUserId(op.getUserId());
						myCoupon.setCouponId(0L);
						if(StringUtils.isAllNotEmpty(url,imgPath)){
							//邀请码
							String inviteCode = RandomUtils.getNumAndWord(10);
							String imgName = "qr_"+System.currentTimeMillis()+".jpg";
							String filePath = "/" + MyUtils.getYYYYMMDD(0)+ToolKit.getInstance().getSingleConfig(FileConstant.UPLOAD_INVITE_CODE_PATH)+"/";
							//二维码
							FileUtils.createQRCodeFile(projectUrl+accessUrl+CommConstant.INVITECODE_PREFIX+inviteCode, imgPath+filePath, imgName, "jpg");
							myCoupon.setCouponImg(url + filePath +imgName);
						}
						
						myCoupon.setStatus(ProjectConstant.MY_COUPON_STATUS_0);
						myCoupon.setCanUseMoney(money_b);
						myCoupon.setCtime(now);
						list.add(myCoupon);
					}
				}
			}
			if(!MyUtils.isListEmpty(list)){
				//批量处理
				myCouponDao.insertBatch(list);
			}
		}
		logger.info(">>>>>4.定时扫描结束 领取猪肉券.");
	}
	/**
	 * 根据code得到配置model
	 * @param code
	 * @return
	 * @throws Exception
	 */
	private Sysconfig getByCode(String code) throws Exception {
		Sysconfig sys = null;
		SysconfigExample example = new SysconfigExample();
		SysconfigExample.Criteria criteria = example.createCriteria();
		criteria.andCodeEqualTo(code);
		List<Sysconfig> ops = sysconfigDao.selectByExample(example);
		if(!MyUtils.isListEmpty(ops)){
			sys = ops.get(0);
		}
		return sys;
	}
	
	/**
	 * 修改用户余额信息
	 * @param map
	 * @param now
	 * @throws Exception
	 */
	private void changeTheBalance(Map<Long, Integer> map,Date now) throws Exception{
		try {
			if(!MyUtils.isMapEmpty(map)){
				//扣除50元/头的逾期领取综合费用
				double per = 0;
				Sysconfig overdueSys = getByCode(SystemConstant.ORDER_CONFIG_OVERDUE_FEE_FOR_PIGS);
				if(overdueSys!=null){
					if(StringUtils.isNumeric(overdueSys.getValue())){
						per =  Integer.parseInt(overdueSys.getValue());
					}
				}
				List<WalletChange> list = new ArrayList<WalletChange>();
				List<Wallet> list2 = new ArrayList<Wallet>();
				for (Map.Entry<Long,Integer> entry : map.entrySet()) {
					Long userId = entry.getKey();
					Integer total = entry.getValue();
					Double money = total * per;
					//1、插入余额变更记录表一条数据
					Wallet w = walletDao.selectByUserId(userId);
					BigDecimal curMoney = w.getMoney();
					Long walletId = w.getWalletId();
					BigDecimal changeMoney = new BigDecimal(money);
					
					WalletChange wc = new WalletChange();
					wc.setWalletId(walletId);
					wc.setUserId(userId);
					wc.setBeforeMoney(curMoney);
					wc.setChangeMoney(changeMoney);
					wc.setAfterMoney(curMoney.subtract(changeMoney));
					wc.setRelationType(ProjectConstant.WALLET_CHANGE_RELATION_TYPE_5);//1:抢购购买2:委托寄卖3:提现4:收益5:逾期扣款
					wc.setChangeType(ProjectConstant.WALLET_CHANGE_CHANGE_TYPE_1); //1:消费2:收入
					wc.setChangeTime(now);
					list.add(wc);
					//2、减少余额表余额信息
					Wallet temp = new Wallet();
					temp.setWalletId(walletId);
					temp.setMoney(w.getMoney().subtract(changeMoney));
					list2.add(temp);
				}
				if(!MyUtils.isListEmpty(list)  && !MyUtils.isListEmpty(list2)){
					walletChangeDao.insertBatch(list);
					walletDao.updateBatch(list2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	
	
	
	/**
	 * 修改用户余额信息
	 * @param map
	 * @param now
	 * @throws Exception
	 */
	private void changeTheBalance2(Map<Long, Integer> map,Date now) throws Exception{
		try {
			if(!MyUtils.isMapEmpty(map)){
				double per=0;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				Date date =sdf.parse("2016-06-04");
				
				boolean isExpired = DateUtil.compareDate(now,date);
				if(isExpired){
					Sysconfig rate = getByCode(SysConstant.YEAR_RETURN_RATE);
					 per = 1980+1980*Double.valueOf(rate.getValue())/360*150/100;
				}
				else{
		 per = 1980+1980*12/360*150/100;
				}
	
				
				List<WalletChange> list = new ArrayList<WalletChange>();
				List<Wallet> list2 = new ArrayList<Wallet>();
				for (Map.Entry<Long,Integer> entry : map.entrySet()) {
					Long userId = entry.getKey();
					Integer total = entry.getValue();
					Double money = total * per;
					//1、插入余额变更记录表一条数据
					Wallet w = walletDao.selectByUserId(userId);
					if(w!=null){
					BigDecimal curMoney = w.getMoney();
					Long walletId = w.getWalletId();
					BigDecimal changeMoney = new BigDecimal(money);
					
					WalletChange wc = new WalletChange();
					wc.setWalletId(walletId);
					wc.setUserId(userId);
					wc.setBeforeMoney(curMoney);
					wc.setChangeMoney(changeMoney);
					wc.setAfterMoney(curMoney.add(changeMoney));
					wc.setRelationType(ProjectConstant.WALLET_CHANGE_RELATION_TYPE_2);//1:抢购购买2:委托寄卖3:提现4:收益5:逾期扣款
					wc.setChangeType(ProjectConstant.WALLET_CHANGE_CHANGE_TYPE_2); //1:消费2:收入
					wc.setChangeTime(now);
					list.add(wc);
					//2、增加余额表余额信息
					Wallet temp = new Wallet();
					temp.setWalletId(walletId);
					temp.setMoney(w.getMoney().add(changeMoney));
					list2.add(temp);
					}
				}
				if(!MyUtils.isListEmpty(list)  && !MyUtils.isListEmpty(list2)){
					walletChangeDao.insertBatch(list);
					walletDao.updateBatch(list2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	
}
