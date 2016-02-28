package com.bjwg.back.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.base.constant.SystemConstant;
import com.bjwg.back.dao.SysconfigDao;
import com.bjwg.back.dao.WalletChangeDao;
import com.bjwg.back.dao.WalletDao;
import com.bjwg.back.dao.WithdrawDao;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.model.SysconfigExample;
import com.bjwg.back.model.Wallet;
import com.bjwg.back.model.WalletChange;
import com.bjwg.back.model.Withdraw;
import com.bjwg.back.model.WithdrawExample;
import com.bjwg.back.service.WithdrawService;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.UploadFile;
import com.bjwg.back.util.ValidateUtil;
import com.bjwg.back.vo.HomeView;
import com.bjwg.back.vo.SearchVo;
import com.bjwg.back.model.Manager;

/**
 * 提现接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class WithdrawServiceImpl implements WithdrawService 
{
	@Autowired
    private WithdrawDao withdrawDao;
	@Autowired
    private WalletChangeDao walletChangeDao;
	@Autowired
    private WalletDao walletDao;
	@Autowired
    private SysconfigDao sysconfigDao;
	
	@Override
	public void queryPage(Pages<Withdraw> page,SearchVo vo) throws Exception {
		WithdrawExample example = new WithdrawExample();
		WithdrawExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		
		//时间查询语句
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andCtimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andCtimeLessThanOrEqualTo(date2);
		}
		
		int count = withdrawDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.withwradals_id");
		List<Withdraw> ops = withdrawDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public int saveOrupdate(Withdraw wallet) throws Exception{
		
		if(!MyUtils.isLongGtZero(wallet.getUserId())){
			return StatusConstant.Status.WALLET_USER_ID_NULL.getStatus();
		}
		if(wallet.getMoney()==null){
			return StatusConstant.Status.WALLET_MONEY_NULL.getStatus();
		}
		if(!ValidateUtil.validateDoublue(wallet.getMoney().toString(),false,0f,9999999.99f)){
			return StatusConstant.Status.WALLET_MONEY_OVER_LEN.getStatus();
		}
		
		Long id = wallet.getWithwradalsId();
		//自动增加父级目录
		//新增
		if(!MyUtils.isLongGtZero(id)){
			wallet.setStatus((byte)1);//正常使用
			return withdrawDao.insertSelective(wallet);
		}else{
			return withdrawDao.updateByPrimaryKeySelective(wallet);
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
            	withdrawDao.deleteByPrimaryKey(id);
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
	public Withdraw getById(Long id) throws Exception {
		return withdrawDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Withdraw> getAll() throws Exception {
		return withdrawDao.selectByExample(new WithdrawExample());
	}
	@Override
	public int batchCommitExamine(Long id, Integer status, Manager manager) throws Exception{
		
		if(status==null || (status.intValue()!=2 && status.intValue()!=3)){
			return StatusConstant.Status.WITHDRAW_FAIL_FORMAT_ERROR.getStatus();
		}
		if(manager==null){
			return StatusConstant.Status.USER_SESSION_EXPIRED.getStatus();
		}
		//修改提现信息状态
		Date now = new Date();
		Withdraw w = withdrawDao.selectByPrimaryKey(id);
		w.setStatus(status.byteValue());
		w.setAuditingMan(manager.getUsername());
		w.setAuditingManId(manager.getManagerId());
		w.setAuditingTime(now);
		int ret = withdrawDao.updateByPrimaryKeySelective(w);
		//修改认证表中信息
		if(ret>0){
			//如果提现不同意
			if(status.intValue()==3){
				Wallet wallet = null;
				//1、插入余额变更记录表一条数据
				try {
					WalletChange wc = new WalletChange();
					Long walletId = w.getWalletId();
					wc.setWalletId(w.getWalletId());
					wc.setUserId(w.getUserId());
					wallet = walletDao.selectByPrimaryKey(walletId);
					BigDecimal curMoney = wallet.getMoney();
					wc.setBeforeMoney(curMoney);
					wc.setChangeMoney(w.getMoney());
					wc.setAfterMoney(curMoney.add(w.getMoney()));
					wc.setChangeType((byte)3);//1、在线支付;2、收入;3、提现
					wc.setChangeTime(now);
					wc.setWalletChangeId(w.getWithwradalsId());
					walletChangeDao.insertSelective(wc);
				//2、还原余额表信息
					wallet.setMoney(wallet.getMoney().add(w.getMoney()));
					walletDao.updateByPrimaryKeySelective(wallet);
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
		return ret;
	}

	@Override
	public HomeView getWithdrawCount(Byte type) throws Exception {
		WithdrawExample example = new WithdrawExample();
		WithdrawExample.Criteria criteria = example.createCriteria();
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
		criteria.andCtimeGreaterThanOrEqualTo(date1);
		criteria.andCtimeLessThanOrEqualTo(date2);
		
		HomeView home = withdrawDao.selectWithdrawCount(example);
		return home;
	}
	
	@Override
	public int checkExportList(String sql,SearchVo vo) {
		try {
			WithdrawExample example = new WithdrawExample();
			WithdrawExample.Criteria criteria = example.createCriteria();
			criteria.addCriterion(sql);
			
			//时间查询语句
			if(StringUtils.isNotEmpty(vo.getStartTime())){
				Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
				criteria.andCtimeGreaterThanOrEqualTo(date1);
			}
			if(StringUtils.isNotEmpty(vo.getEndTime())){
				Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
				criteria.andCtimeLessThanOrEqualTo(date2);
			}
			int count = withdrawDao.countByExample(example);
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
	        
	        //提现时间
	        String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			
			WithdrawExample example = new WithdrawExample();
			WithdrawExample.Criteria criteria = example.createCriteria();
			criteria.addCriterion(whereSql);
			
			if(StringUtils.isNotEmpty(startTime)){
				Date date1 = DateUtil.to24Date(startTime+" 00:00:00");
				criteria.andCtimeGreaterThanOrEqualTo(date1);
			}
			if(StringUtils.isNotEmpty(endTime)){
				Date date2 = DateUtil.to24Date(endTime+" 23:59:59");
				criteria.andCtimeLessThanOrEqualTo(date2);
			}
			
			int count = withdrawDao.countByExample(example);
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
			List<Withdraw> list = withdrawDao.selectByExample(example);
			
			String webSavePath = request.getSession().getServletContext().getRealPath(SAVE_PATH);
			File root = new File(webSavePath);
			if (!root.exists()) {
				root.mkdirs();
			}
			String filename = webSavePath+"\\"+longSdf.format(new Date() )+".xls";
			exportWithdraw(list,filename,request);
			UploadFile.download(filename,request,response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return StatusConstant.Status.SUCCESS.getStatus();
	} 
	
	/**
	 * 导出提现信息
	 * @param list
	 * @param filename
	 * @param request
	 */
	private void exportWithdraw(List<Withdraw> list,String filename,HttpServletRequest request){
		try{
		// 创建excel文件
		WritableWorkbook book = Workbook.createWorkbook(new File(filename));
		// 生成工作簿
		WritableSheet sheet = book.createSheet("提现信息", 0);
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

		Label label1 = new Label(0, 0, "用户ID", wcfFC);
		sheet.addCell(label1);

		label1 = new Label(1, 0, "用户名", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(2, 0, "户名", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(3, 0, "银行", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(4, 0, "银行卡号", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(5, 0, "提现时间", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(6, 0, "提现金额（元）", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(7, 0, "身份证号码", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(8, 0, "审核人", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(9, 0, "审核时间", wcfFC);
		sheet.addCell(label1);
		
		int size = list.size();
		for(int i=0;i<size;i++){
			int j=0;
			Withdraw withdraw = list.get(i);
			String tag = withdraw.getUserId()+"";//用户ID
			jxl.write.Number label3 = new jxl.write.Number(j++,i+1,new Double(tag));
			sheet.addCell(label3);
			tag = withdraw.getUsername();//用户名
			Label label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag= withdraw.getAccountName();//户名
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag= withdraw.getBank()+"";//银行
			label2 = new Label(j++,i+1, tag);;
			sheet.addCell(label2);
			tag= withdraw.getCardNo();//银行卡号
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			if(withdraw.getCtime()!=null){
				tag= sdf.format(withdraw.getCtime());//提现时间
			}else{
				tag = "";
			}
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag= withdraw.getMoney()+"";//提现金额（元）
			label3 = new jxl.write.Number(j++,i+1,new Double(tag));
			sheet.addCell(label3);
			tag = withdraw.getIdCard();//身份证号码
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			tag= withdraw.getAuditingMan();//审核人
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			if(withdraw.getAuditingTime()!=null){
				tag= sdf.format(withdraw.getAuditingTime());//审核时间
			}else{
				tag = "";
			}
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
		}
			book.write();
			book.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
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
	private static final String SAVE_PATH = "/upload/excel/";
	private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
