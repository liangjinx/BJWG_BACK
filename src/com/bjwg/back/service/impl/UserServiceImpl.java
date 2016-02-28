package com.bjwg.back.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.CommConstant;
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.base.constant.StatusConstant.Status;
import com.bjwg.back.base.constant.SystemConstant;
import com.bjwg.back.base.constant.TypeConstant;
import com.bjwg.back.dao.BankCardDao;
import com.bjwg.back.dao.DictDetailDao;
import com.bjwg.back.dao.MyCouponDao;
import com.bjwg.back.dao.MyProjectDao;
import com.bjwg.back.dao.SysconfigDao;
import com.bjwg.back.dao.UserDao;
import com.bjwg.back.dao.UserExtDao;
import com.bjwg.back.dao.UserPreorderDao;
import com.bjwg.back.dao.epUserDao;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.DictDetailExample;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.model.SysconfigExample;
import com.bjwg.back.model.User;
import com.bjwg.back.model.UserExample;
import com.bjwg.back.model.UserExt;
import com.bjwg.back.model.UserPreorder;
import com.bjwg.back.model.UserPreorderExample;
import com.bjwg.back.model.epUser;
import com.bjwg.back.service.UserService;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.FileConstant;
import com.bjwg.back.util.FileUtils;
import com.bjwg.back.util.MD5;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.RandomUtils;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.ToolKit;
import com.bjwg.back.util.UploadFile;
import com.bjwg.back.util.ValidateUtil;
import com.bjwg.back.vo.HomeView;
import com.bjwg.back.vo.SearchVo;



/**
 * 系统配置接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class UserServiceImpl implements UserService 
{
	@Autowired
    private UserDao userDao;
	@Autowired
    private epUserDao epUserDao;
	@Autowired
    private UserExtDao userExtDao;
	@Autowired
    private UserPreorderDao userPreorderDao;
	@Autowired
    private MyCouponDao myCouponDao;
	@Autowired
    private MyProjectDao myProjectDao;
	@Autowired
    private BankCardDao bankCardDao;
	@Autowired
    private DictDetailDao dictDetailDao;
	@Autowired
    private SysconfigDao sysconfigDao;
	
	@Override
	public void queryPage(Pages<User> page,SearchVo vo) throws Exception {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		//时间查询语句
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andRegisterTimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andRegisterTimeLessThanOrEqualTo(date2);
		}
		//用户类型查询语句
		if("1".equals(vo.getUserType())){
			criteria.addCriterion(" and not exists (select 1 from BJWG_WEIXINUSER w where w.user_id = t.user_id)");
		}else if("2".equals(vo.getUserType())){
			criteria.addCriterion(" and exists (select 1 from BJWG_WEIXINUSER w where w.user_id = t.user_id)");
		}
		
		int count = userDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.user_id desc");
		List<User> ops = userDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrupdate(User user,HttpServletRequest request) throws Exception{
	System.out.println("flag="+user.getFlag());
		if(!StringUtils.isNotEmpty(user.getUsername())){
			return StatusConstant.Status.USER_USERNAME_NULL.getStatus();
		}
		//用户名长度过长
		if(!ValidateUtil.validateString(user.getUsername(),false,1,45)){
			return StatusConstant.Status.USER_USERNAME_LENGTH_OVER.getStatus();
		}
		if(!StringUtils.isNotEmpty(user.getPassword())){
			return StatusConstant.Status.USER_PASSWORD_NULL.getStatus();
		}
		if(!ValidateUtil.validateString(user.getPassword(),false,1,32)){
			return StatusConstant.Status.USER_PASSWORD_OVER_LEN.getStatus();
		}
		
		//查询用户名是否重复
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(user.getUsername().trim());
		List<User> list = userDao.selectByExample(example);
		if(!MyUtils.isListEmpty(list)){
			User r = list.get(0);
	    	if(r != null && r.getUserId().intValue() != user.getUserId().intValue()){
	    		return StatusConstant.Status.USER_NAME_EXISTED.getStatus(); 
	    	}
		}
		//用户头像
		Map<String, Object> map = UploadFile.uploadSingleFile(request,"");
		if(map.get("status")!=null){
			Integer ret = Integer.parseInt(map.get("status").toString());
			if(ret.intValue()!=StatusConstant.Status.UPLOAD_NOFILE.getStatus() && ret.intValue()!=StatusConstant.Status.SUCCESS.getStatus()){
				return ret.intValue();
			}
		}
		if(map.get("filePath")!=null && StringUtils.isNotEmpty(map.get("filePath").toString())){
			user.setHeadImg(map.get("filePath").toString());
		}else{
			String nopath = request.getParameter("noheadImg");
			if(nopath!=null&&!nopath.equals("")){
				user.setHeadImg("");
			}
		}
		
		Long id = user.getUserId();
		String password = user.getPassword();
		//自动增加父级目录
		//新增
		if(!MyUtils.isLongGtZero(id)){
			String ip = MyUtils.getIpAddr(request);
			user.setRegisterIp(MyUtils.ip2long(ip));
			user.setRegisterTime(new Date());
			user.setStatus((byte)1);//正常
			user.setPassword(MD5.GetMD5Code(MD5.GetMD5Code(password.trim()) + CommConstant.PASSWORD_SECRET_TEXT));
			//设置 邀请码 和 二维码
			if(user.getFlag()==2){
				user.setFlag((byte)2);
				epUser eu=new epUser();
				userDao.insertSelective(user);
			eu.setUserId(user.getUserId());
			eu.setAddress(user.getAddress());
			eu.setBusinessLicense(user.getBusinessLicense());
			eu.setLegalperson(user.getLegalperson());
			
			System.out.println("企业用户000"+user.getUserType());
				UserExt record = new UserExt();
				record.setUserId(user.getUserId());
				record.setLastButtinId(0L);
				userExtDao.insertSelective(record);
				epUserDao.insertUser(eu);
				return StatusConstant.Status.SUCCESS.getStatus();
			}
			setInviteAndQRCode(user);
			userDao.insertSelective(user);
			UserExt record = new UserExt();
			record.setUserId(user.getUserId());
			record.setLastButtinId(0L);
			userExtDao.insertSelective(record);
			
			return StatusConstant.Status.SUCCESS.getStatus();
		}else{
			User u = getById(id);
			if(!(password!=null && password.equals(u.getPassword()))){
				user.setPassword(MD5.GetMD5Code(MD5.GetMD5Code(password.trim()) + CommConstant.PASSWORD_SECRET_TEXT));
    		}
			if(user.getFlag()==2){
				epUser eu=new epUser();
				eu.setUserId(user.getUserId());
				eu.setAddress(user.getAddress());
				eu.setBusinessLicense(user.getBusinessLicense());
				eu.setLegalperson(user.getLegalperson());
				
				epUserDao.updateByPrimaryKeySelective(eu);
			}
			return userDao.updateByPrimaryKeySelective(user);
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
            }
            //猪肉券
            myCouponDao.deleteByUserIds(idList);
            //我的券使用记录
            //我的钱包(余额)
            //钱包变更记录表
            //提现表
            //收货地址
            userDao.deleteAddressByUserIds(idList);
            //我参与的项目
            myProjectDao.deleteByUserIds(idList);
            //银行个人信息
            userDao.deleteBankByUserIds(idList);
            //消息表
            //银行卡记录表
            bankCardDao.deleteByUserIds(idList);
            
            //删除微信用户表
            userDao.deleteWeixinByUserIds(idList);
            //删除用户表扩展
            userExtDao.deleteBatch(idList);
            //删除数据库数据
        	userDao.deleteBatch(idList);
            return StatusConstant.Status.SUCCESS.getStatus(); 
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

	@Override
	public User getById(Long id) throws Exception {
		return userDao.selectByPrimaryKey(id);
	}
	@Override
	public epUser getepUser(Long id) {
		return epUserDao.selectByPrimaryKey(id);
	}

	@Override
	public List<User> getAll() throws Exception {
		return userDao.selectByExample(new UserExample());
	}
	@Override
	public int batchCommitFreeze(Long id) throws Exception{
		try{
			User user = getById(id);
			int status = user.getStatus();
			Integer par = 1;
			if(status==1){
				par=0;
			}else if(status==0){
				par=1;
			}
			user.setStatus((byte)par.intValue());
			return userDao.updateByPrimaryKeySelective(user);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<User> getAllUser() throws Exception {
		return userDao.selectByExample(new UserExample());
	}
	@Override
	public int checkExportList(String sql,SearchVo vo) {
		try {
			UserExample example = new UserExample();
			UserExample.Criteria criteria = example.createCriteria();
			criteria.addCriterion(sql);
			//时间查询语句
			if(StringUtils.isNotEmpty(vo.getStartTime())){
				Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
				criteria.andRegisterTimeGreaterThanOrEqualTo(date1);
			}
			if(StringUtils.isNotEmpty(vo.getEndTime())){
				Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
				criteria.andRegisterTimeLessThanOrEqualTo(date2);
			}
			//用户类型查询语句
			if("1".equals(vo.getUserType())){
				criteria.addCriterion(" and not exists (select 1 from BJWG_WEIXINUSER w where w.user_id = t.user_id)");
			}else if("2".equals(vo.getUserType())){
				criteria.addCriterion(" and exists (select 1 from BJWG_WEIXINUSER w where w.user_id = t.user_id)");
			}
			int count = userDao.countByExample(example);
			if(count<=0){
				return StatusConstant.Status.HAS_NO_EXPORT_COUNT.getStatus();
			}
			int maxExportCount = 100;
			String value = getSysConfigValue(SystemConstant.EXPORT_FILE_MAX_COUNT);
			if(StringUtils.isNotEmpty(value)){
				if(StringUtils.isNumeric(value)){
					maxExportCount = Integer.parseInt(value);
				}
			}
			if(count > maxExportCount){
				return StatusConstant.Status.OVER_MAX_EXPORT_COUNT.getStatus();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StatusConstant.Status.SUCCESS.getStatus();
	}
	@Override
	public int exportList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			
			//查询条件
	        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
	        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
	        String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			
			UserExample example = new UserExample();
			UserExample.Criteria criteria = example.createCriteria();
			criteria.addCriterion(whereSql);
			//时间查询语句
			if(StringUtils.isNotEmpty(startTime)){
				Date date1 = DateUtil.to24Date(startTime+" 00:00:00");
				criteria.andRegisterTimeGreaterThanOrEqualTo(date1);
			}
			if(StringUtils.isNotEmpty(endTime)){
				Date date2 = DateUtil.to24Date(endTime+" 23:59:59");
				criteria.andRegisterTimeLessThanOrEqualTo(date2);
			}
			String userType = request.getParameter("userType");//1、八戒平台用户;2、微信用户
			//用户类型查询语句
			if("1".equals(userType)){
				criteria.addCriterion(" and not exists (select 1 from BJWG_WEIXINUSER w where w.user_id = t.user_id)");
			}else if("2".equals(userType)){
				criteria.addCriterion(" and exists (select 1 from BJWG_WEIXINUSER w where w.user_id = t.user_id)");
			}
			
			List<User> list = userDao.selectByExample(example);
			
			String webSavePath = request.getSession().getServletContext().getRealPath(SAVE_PATH);
			File root = new File(webSavePath);
			if (!root.exists()) {
				root.mkdirs();
			}
			String filename = webSavePath+"\\"+longSdf.format(new Date() )+".xls";
			exportUser(list,filename,request);
			UploadFile.download(filename,request,response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return StatusConstant.Status.SUCCESS.getStatus();
	} 
	
	/**
	 * 导出用户
	 * @param list
	 * @param filename
	 * @param request
	 */
	private void exportUser(List<User> list,String filename,HttpServletRequest request){
		try{
		Map<String,String> map1 = null;
		Map<String,String> map2 = null;
		List<DictDetail> statuslist = getDictDetailsByCode(DictConstant.BJWG_ORDER_STATUS);
		if(!MyUtils.isListEmpty(statuslist)){
			map1 = new HashMap<String,String>();
			for(DictDetail d:statuslist){
				map1.put(d.getValue(), d.getName());
			}
		}
		
		List<DictDetail> typelist = getDictDetailsByCode(DictConstant.BJWG_ORDER_TYPE);
		if(!MyUtils.isListEmpty(typelist)){
			map2 = new HashMap<String,String>();
			for(DictDetail d:statuslist){
				map2.put(d.getValue(), d.getName());
			}
		}
		// 创建excel文件
		WritableWorkbook book = Workbook.createWorkbook(new File(filename));
		// 生成工作簿
		WritableSheet sheet = book.createSheet("用户列表", 0);
		// 设置文件样式
		jxl.write.WritableFont wfc = new jxl.write.WritableFont(
				WritableFont.ARIAL, 10, WritableFont.BOLD, false,
				jxl.format.UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.RED);
		jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(
				wfc);
		jxl.write.WritableCellFormat wcsB = new jxl.write.WritableCellFormat();
		wcfFC.setBackground(jxl.format.Colour.YELLOW2);
		wcfFC.setBorder(jxl.format.Border.ALL,
				jxl.format.BorderLineStyle.THIN);
		wcsB.setBorder(jxl.format.Border.ALL,
				jxl.format.BorderLineStyle.THIN);
		// 添加内容标题

		Label label1 = new Label(0, 0, "用户名", wcfFC);
		sheet.addCell(label1);

		label1 = new Label(1, 0, "手机号码", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(2, 0, "最后登入时间", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(3, 0, "注册时间", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(4, 0, "用户类型", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(5, 0, "邮箱", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(6, 0, "性别", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(7, 0, "邀请码", wcfFC);
		sheet.addCell(label1);
		
		int size = list.size();
		for(int i=0;i<size;i++){
			int j=0;
			User user = list.get(i);
			String tag = user.getUsername();//用户名
			Label label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag = user.getUsername();//手机号码
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			if(user.getLastLoginTime()!=null){
				tag = sdf.format(user.getLastLoginTime());//最后登入时间
			}else{
				tag = "";
			}
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag= sdf.format(user.getRegisterTime());//注册时间
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			String text = "";
			if("1".equals(user.getUserType())){
				text = "八戒平台用户";
			}else if("2".equals(user.getUserType())){
				text = "微信用户";
			}
			label2 = new Label(j++,i+1, text);//用户类型
			sheet.addCell(label2);
			tag = user.getEmail();//邮箱
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			if(user.getSex().intValue()==0){
				text = "女";
			}else{
				text = "男";
			}
			label2 = new Label(j++,i+1, text);//性别
			sheet.addCell(label2);
			tag= user.getQrCode();//邀请码
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
		}
			book.write();
			book.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置邀请码和二维码
	 * @param user
	 * @throws Exception
	 */
	private void setInviteAndQRCode(User user) throws Exception{
		//邀请码
		String inviteCode = RandomUtils.getNumAndWord(10);
		user.setInviteCode(CommConstant.INVITECODE_PREFIX+inviteCode);
		
		SysconfigExample example = new SysconfigExample();
		example.or().andCodeEqualTo(SystemConstant.PROJECT_IMG_ACCESS_URL);
		example.or().andCodeEqualTo(SystemConstant.PROJECT_IMG_UPLOAD_ROOT_PATH);
		example.or().andCodeEqualTo(SystemConstant.QR_CODE_URL);
		example.or().andCodeEqualTo(SystemConstant.PROJECT_ACCESS_URL);
		List<Sysconfig> list = sysconfigDao.selectByExample(example);
		
		if(!MyUtils.isListEmpty(list)){
			
			//二维码url
			String url = null;
			//图片存储根路径
			String imgPath = null;
			//访问地址
			String accessUrl = null;
			//项目地址
			String projectUrl = null;
			for (Sysconfig syscon : list) {
				
				if(SystemConstant.PROJECT_IMG_ACCESS_URL.equals(syscon.getCode())){
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
			if(StringUtils.isAllNotEmpty(url,imgPath)){
				
				String imgName = "qr_"+System.currentTimeMillis()+".jpg";
				
				String filePath = "/" + MyUtils.getYYYYMMDD(0)+ToolKit.getInstance().getSingleConfig(FileConstant.UPLOAD_INVITE_CODE_PATH)+"/";
				
				String  test = ToolKit.getInstance().getSingleConfig("test");
				
				//二维码
				FileUtils.createQRCodeFile(projectUrl+accessUrl+user.getInviteCode(), imgPath+filePath, imgName, "jpg");
				
				if("yes".equals(test)){
					
					url += "resources";
				}
				
				user.setQrCode(url + filePath +imgName);
			}
		}
	}
	
	
	@Override
	public HomeView getUserCount(Byte type) throws Exception {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		Date date1 = null;
		Date date2 = null;
		if(type.byteValue()==1){//今日
			date1 = DateUtil.getTimesmorning();
			date2 = DateUtil.getTimesnight();
		}else if(type.byteValue()==2){//本周
			date1 = DateUtil.getTimesWeekmorning();
			date2 = DateUtil.getTimesWeeknight();
		}else if(type.byteValue()==3){//本月
			date1 = DateUtil.getTimesMonthmorning();
			date2 = DateUtil.getTimesMonthnight();
		}
		criteria.andRegisterTimeGreaterThanOrEqualTo(date1);
		criteria.andRegisterTimeLessThanOrEqualTo(date2);
		
		HomeView home = userDao.selectUserCount(example);
		return home;
	}
	private List<Sysconfig> getSysConfigByCode(String code) throws Exception {
		SysconfigExample example = new SysconfigExample();
		SysconfigExample.Criteria criteria = example.createCriteria();
		criteria.andCodeEqualTo(code);
		List<Sysconfig> ops = sysconfigDao.selectByExample(example);
		return ops;
	}
	private String getSysConfigValue(String code){
		String value = "";
    	List<Sysconfig> list;
		try {
			list = getSysConfigByCode(code);
			if(!MyUtils.isListEmpty(list)){
				value = list.get(0).getValue();
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
    }
	private List<DictDetail> getDictDetailsByCode(String code) throws Exception {
		DictDetailExample example = new DictDetailExample();
		DictDetailExample.Criteria criteria = example.createCriteria();
		criteria.andCodeEqualTo(code);
		List<DictDetail> ops = dictDetailDao.selectByExample(example);
		return ops;
	}
	private static final String SAVE_PATH = "/upload/excel/";
	private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public int deleteEpusers(List<Long> userids) throws Exception {
		
		return epUserDao.deleteByPrimaryKeys(userids);
	}
	
/**
 * 查看用户预抢
 */
	@Override
	public UserExt selectUserExtByPrimaryKey(Long userId) {
		
		return userExtDao.selectByPrimaryKey(userId);
	}

@Override
public List<UserPreorder> selectLoadNameByUserId(Long userId) throws Exception {
	Map<String, Object> map = new HashMap<String, Object>();
	
	map.put("beginTime", MyUtils.getYYYYMMDDHHmmss(0));
	map.put("userId", userId);
	map.put("top", 10);
	
	return userPreorderDao.selectLoadNameByMap(map);
	
}

@Override
public Status updateUserPreorder(Long userId, String projectId, String num,
		String settingType, boolean isCancel) throws Exception {
	try {
		
		if(null == userId || userId <= 0){
			
			return Status.useridNullity;
		}
		if(!ValidateUtil.validateInteger(settingType, false, 1, 2)){
			
			return Status.settingTypeNullity;
		}
		
		byte settype = Byte.valueOf(settingType);
		
		//设置某期抢标
		if(settype == TypeConstant.USER_SETTING_TYPE_2){
			
			if(!ValidateUtil.validateInteger(projectId, false, 0, null)){
				
				return Status.projectidNullity;
			}
			
			if(!isCancel){
				
				num = StringUtils.isEmpty(num) ? "0" : num;
				
				if(!ValidateUtil.validateInteger(num, false, 0, null)){
					
					return Status.numNullity;
				}
				//已有则更新，否则插入
				UserPreorder preorder = userPreorderDao.selectByPrimaryKey(userId,Long.valueOf(projectId));
				
				if(null == preorder){
					
					preorder = new UserPreorder();
					
					preorder.setUserId(userId);
					preorder.setProjectId(Long.valueOf(projectId));
					preorder.setCtime(new Date());
					preorder.setNum(Integer.valueOf(num));
					preorder.setUserId(userId);
					userPreorderDao.insert(preorder);
				}else{
					
					UserPreorder updatePreorder = new UserPreorder();
					updatePreorder.setUserId(userId);
					updatePreorder.setProjectId(preorder.getProjectId());
					updatePreorder.setNum(Integer.valueOf(num));
					updatePreorder.setCtime(new Date());
					userPreorderDao.updateByPrimaryKeySelective(preorder);
				}
				
				UserExt userExt = new UserExt();
				userExt.setSettingType(settype);
				userExt.setUserId(userId);
				userExt.setSettingTime(new Date());
				userExtDao.updateByPrimaryKeySelective(userExt);
			}else{
				
				UserPreorderExample example = new UserPreorderExample();
				UserPreorderExample.Criteria criteria = example.createCriteria();
				criteria.andUserIdEqualTo(userId);
				//查询是否为最后一个，是则默认全部不选
				if(userPreorderDao.countByExample(example) == 1){
					
					UserExt userExt = new UserExt();
					userExt.setSettingType(TypeConstant.USER_SETTING_TYPE_0);
					userExt.setUserId(userId);
					userExt.setSettingTime(new Date());
					userExtDao.updateByPrimaryKeySelective(userExt);
				}
				
				//取消设置
				userPreorderDao.deleteByPrimaryKey(userId,Long.valueOf(projectId));
			}
		}else if(settype == TypeConstant.USER_SETTING_TYPE_1){
			UserExt userExt = new UserExt();
			userExt.setSettingType(settype);
			if(isCancel){
				userExt.setSettingType(TypeConstant.USER_SETTING_TYPE_0);
			}else{
				
				UserPreorderExample example = new UserPreorderExample();
				UserPreorderExample.Criteria criteria = example.createCriteria();
				criteria.andUserIdEqualTo(userId);
				List<UserPreorder> list = userPreorderDao.selectByExample(example);
				
				for (UserPreorder up : list) {
					
					userPreorderDao.deleteByPrimaryKey(userId,up.getProjectId());
				}
			}
			userExt.setUserId(userId);
			userExt.setSettingValue(Integer.valueOf(num));
			userExt.setSettingTime(new Date());
			userExtDao.updateByPrimaryKeySelective(userExt);
			
			
		}
		
		return Status.SUCCESS;
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}
}
