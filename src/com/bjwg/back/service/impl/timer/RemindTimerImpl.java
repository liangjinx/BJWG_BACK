package com.bjwg.back.service.impl.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjwg.back.base.constant.SMSConstant;
import com.bjwg.back.base.constant.SystemConstant;
import com.bjwg.back.dao.MyProjectDao;
import com.bjwg.back.dao.SysconfigDao;
import com.bjwg.back.dao.UserDao;
import com.bjwg.back.model.MyProject;
import com.bjwg.back.model.MyProjectExample;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.model.SysconfigExample;
import com.bjwg.back.model.User;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.SMSUtil;

@Service
public class RemindTimerImpl {
	
	@Autowired
    private MyProjectDao myProjectDao;
	@Autowired
    private SysconfigDao sysconfigDao;
	@Autowired
    private UserDao userDao;
	
	public static final Logger  logger = LoggerFactory.getLogger("LOGISTICS-COMPONENT"); 
	
	public void execute() throws Exception{
		Date now = new Date();
		try{
			//1、未选择回报方式进行短信提醒
			remind(now);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("未选择回报方式发送短信提醒出错:");
			logger.error(e.toString());
			throw e;
		}
	}
	
	/**
	 * 未选择回报方式进行短信提醒
	 */
	private void remind(Date now) throws Exception{
		
		logger.info(">>>>>1.定时扫描开始 未选择回报方式,短信提醒");
		
		MyProjectExample example = new MyProjectExample();
		MyProjectExample.Criteria criteria = example.createCriteria();
		criteria.andDealTypeIsNull();
		List<MyProject> ops = myProjectDao.selectByExample(example);
		if(!MyUtils.isListEmpty(ops)){
			
			logger.info(">>>>>2.定时扫描开始 未选择回报方式的数据有："+ops.size() + "条");
			
			String days = "";
			Sysconfig sys = getByCode(SystemConstant.SYS_CONFIG_DEALTYPE_SMS_REMIND_DAY);
			if(sys!=null){
				days =  sys.getValue();
			}
			String[] dayArr = null;
			if(StringUtils.isNotEmpty(days)){
				dayArr = days.split(",");
			}else{
				return;
			}
			long day = 0;
			for(MyProject op : ops){
				
				User user = userDao.selectByPrimaryKey(op.getUserId());
				if(user==null){
					continue;
				}
				String phone = user.getPhone();
				if(!StringUtils.isNotEmpty(phone)){
					continue;
				}
				System.out.println("------------------结束日期-----"+sdf.format(op.getEndTime()));
				
				//计算时间天数
				day = DateUtil.getDaySub(now,op.getEndTime());
				if(day < 0){
					continue;
				}
				boolean flag = false;
				for(String temp : dayArr){
					if(StringUtils.isNumeric(temp)){
						long ha = Long.parseLong(temp);
						if(day == ha){
							flag = true;
							break;
						}
					}
				}
				
				Date beg=sdf.parse("2015-10-1");
				long day2 = DateUtil.getDaySub(beg,op.getBeginTime());
				
				if(flag && day2>0){
					SMSUtil.sendOne(phone,SMSConstant.getSettingDealTypeMsg(op.getPaincbuyProjectName()));
					logger.info(">>>>>4.定时扫描结束 未选择回报方式，短信提醒号码"+phone);
				}
			}
		}
		logger.info(">>>>>5.定时扫描结束 未选择回报方式，短信提醒结束.");
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
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
