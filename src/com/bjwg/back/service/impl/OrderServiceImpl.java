package com.bjwg.back.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.CommConstant;
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.base.constant.SysConstant;
import com.bjwg.back.base.constant.SystemConstant;
import com.bjwg.back.dao.DictDetailDao;
import com.bjwg.back.dao.OrderDao;
import com.bjwg.back.dao.SysconfigDao;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.DictDetailExample;
import com.bjwg.back.model.MyEarnings;
import com.bjwg.back.model.MyEarningsExample;
import com.bjwg.back.model.Order;
import com.bjwg.back.model.OrderExample;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.model.SysconfigExample;
import com.bjwg.back.service.MyEarningsService;
import com.bjwg.back.service.OrderService;
import com.bjwg.back.service.SysconfigService;
import com.bjwg.back.util.ConsoleUtil;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.UploadFile;
import com.bjwg.back.vo.HomeView;
import com.bjwg.back.vo.SearchVo;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 订单实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class OrderServiceImpl implements OrderService 
{
	@Autowired
    private OrderDao orderDao;
	@Autowired
	private DictDetailDao dictDetailDao;
	@Autowired
    private SysconfigDao sysconfigDao;
	@Autowired
	private MyEarningsService myEarningsService;
	@Autowired
	private SysconfigService sysConfigService;
	@Override
	public void queryPage(Pages<Order> page,SearchVo vo) throws Exception {
		
		OrderExample example = new OrderExample();
		OrderExample.Criteria criteria = example.createCriteria();
		criteria.addCriterion(page.getWhereSql());
		
		//下单时间
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andCtimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andCtimeLessThanOrEqualTo(date2);
		}
		
		//付款时间
		if(StringUtils.isNotEmpty(vo.getStartTime2())){
			Date date1 = DateUtil.to24Date(vo.getStartTime2()+" 00:00:00");
			criteria.andPayTimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime2())){
			Date date2 = DateUtil.to24Date(vo.getEndTime2()+" 23:59:59");
			criteria.andPayTimeLessThanOrEqualTo(date2);
		}
		
		int count = orderDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.ctime desc");
		List<Order> ops = orderDao.selectByExample(example);
		page.setRoot(ops);
	}

	@Override
	public Order getById(Long id) throws Exception {
		return orderDao.selectByPrimaryKey(id);
	}
	
	@Override
	public int confirmOrder(Order order) throws Exception {
		
		this.insertMyEarningsAfterPay(order.getUserId(), order.getRelationId(), order);
		return orderDao.updateByPrimaryKeySelective(order);
	}
	
	

	
	
	
	
	/**
	 * 支付成功后插入我的收益（我参与的项目）记录
	 * @param userId
	 * @param projectId
	 * @param order
	 * @throws Exception
	 */

	private void insertMyEarningsAfterPay(Long userId ,Long projectId,Order order) throws Exception{
		
		try {
			
			//插入我的收益（我参与的项目）记录
			MyEarningsExample earningsExample = new MyEarningsExample();
			MyEarningsExample.Criteria criteria2 = earningsExample.createCriteria();
			criteria2.andUserIdEqualTo(userId);
			criteria2.andPaincbuyProjectIdEqualTo(projectId);
			
			List<MyEarnings> myEarningsList = myEarningsService.selectByExample(earningsExample);
			
			ConsoleUtil.println("myEarningsList size:"+myEarningsList.size());
			
			List<String> codeList = new ArrayList<String>(Arrays.asList(SysConstant.YEAR_RETURN_RATE,SysConstant.GROW_UP_DAYS));
		
			
			List<Sysconfig> cfList = sysConfigService.queryList(codeList);
			
			Integer days = 0;
			BigDecimal rate = BigDecimal.ZERO;
			
			//两个配置项都有
			if(!MyUtils.isListEmpty(cfList) && cfList.size() == 2){
				
				for (Sysconfig sconf : cfList) {
					
					if(SysConstant.GROW_UP_DAYS.equals(sconf.getCode())){
						
						days = Integer.valueOf(sconf.getValue());
						
					}else{
						
						rate = new BigDecimal(sconf.getValue());
					}
					
				}
			}
			BigDecimal expectEarning = BigDecimal.ZERO;
			//有多个订单同一期则进行叠加
			if(!MyUtils.isListEmpty(myEarningsList)){
				
				ConsoleUtil.println("myEarningsList update:"+myEarningsList);
				
				MyEarnings myEarnings = myEarningsList.get(0);
				
				//预期收益 = 总成本 * 年收益率 * 时间 /360天 
				expectEarning = myEarnings.getExpectEarning().add(
													myEarnings.getMoney().multiply(BigDecimal.valueOf(order.getNum())).multiply(myEarnings.getRate().divide(BigDecimal.valueOf(100)))
													.multiply(BigDecimal.valueOf(myEarnings.getDays())).divide(BigDecimal.valueOf(360),4,BigDecimal.ROUND_HALF_UP));
				//累加数量，预期收益
				MyEarnings updateEarnings = new MyEarnings();
				updateEarnings.setNum(myEarnings.getNum()+order.getNum());
				updateEarnings.setExpectEarning(expectEarning);
				updateEarnings.setEarningsId(myEarnings.getEarningsId());
				
				myEarningsService.updateByPrimaryKeySelective(updateEarnings);
			}else{
				
				MyEarnings myEarnings = new MyEarnings();
				myEarnings.setDays(days);
				myEarnings.setRate(rate);
				myEarnings.setBeginTime(order.getPayTime());
				myEarnings.setEndTime(MyUtils.dateFormat4(MyUtils.addDateL(myEarnings.getBeginTime(), myEarnings.getDays()),0));
				myEarnings.setMoney(order.getPrice());
				
				System.out.println("num="+order.getNum().intValue());
				myEarnings.setNum(order.getNum().intValue());
				
				//预期收益 = 总成本 * 年收益率 * 时间 /360天 
				expectEarning = myEarnings.getMoney().multiply(BigDecimal.valueOf(myEarnings.getNum())).multiply(myEarnings.getRate().divide(BigDecimal.valueOf(100)))
													.multiply(BigDecimal.valueOf(myEarnings.getDays())).divide(BigDecimal.valueOf(360),4,BigDecimal.ROUND_HALF_UP);
				myEarnings.setExpectEarning(expectEarning);
				myEarnings.setPaincbuyProjectId(order.getRelationId());
				myEarnings.setPaincbuyProjectName(order.getRelationName());
				myEarnings.setPresentNum(0);
//				myEarnings.setRemark(remark);
				myEarnings.setUserId(userId);
				myEarnings.setDealStatus(CommConstant.DEAL_STATUS_0);
				myEarningsService.insert(myEarnings);
				
				ConsoleUtil.println("myEarningsList insert:"+myEarningsList);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	
	
	
	
	
	@Override
	public HomeView getOrderCount(Byte type) throws Exception {
		
		OrderExample example = new OrderExample();
		OrderExample.Criteria criteria = example.createCriteria();
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
		
		HomeView home = orderDao.selectOrderCount(example);
		return home;
	}
	@Override
	public int checkExportList(String sql,SearchVo vo){
		try {
			OrderExample example = new OrderExample();
			OrderExample.Criteria criteria = example.createCriteria();
			criteria.addCriterion(sql);
			
			//下单时间
			if(StringUtils.isNotEmpty(vo.getStartTime())){
				Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
				criteria.andCtimeGreaterThanOrEqualTo(date1);
			}
			if(StringUtils.isNotEmpty(vo.getEndTime())){
				Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
				criteria.andCtimeLessThanOrEqualTo(date2);
			}
			
			//付款时间
			if(StringUtils.isNotEmpty(vo.getStartTime2())){
				Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
				criteria.andPayTimeGreaterThanOrEqualTo(date1);
			}
			if(StringUtils.isNotEmpty(vo.getEndTime2())){
				Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
				criteria.andPayTimeLessThanOrEqualTo(date2);
			}
			int count = orderDao.countByExample(example);
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
	        //下单时间
	        String startTime = request.getParameter("cStartTime");
			String endTime = request.getParameter("cEndTime");
			//付款时间
			String startTime2 = request.getParameter("pStartTime");
			String endTime2 = request.getParameter("pEndTime");
			
			OrderExample example = new OrderExample();
			OrderExample.Criteria criteria = example.createCriteria();
			criteria.addCriterion(whereSql);
			
			example.setOrderByClause("t.ctime desc");
			
			//下单时间
			if(StringUtils.isNotEmpty(startTime)){
				Date date1 = DateUtil.to24Date(startTime+" 00:00:00");
				criteria.andCtimeGreaterThanOrEqualTo(date1);
			}
			if(StringUtils.isNotEmpty(endTime)){
				Date date2 = DateUtil.to24Date(endTime+" 23:59:59");
				criteria.andCtimeLessThanOrEqualTo(date2);
			}
			
			//付款时间
			if(StringUtils.isNotEmpty(startTime2)){
				Date date1 = DateUtil.to24Date(startTime2+" 00:00:00");
				criteria.andPayTimeGreaterThanOrEqualTo(date1);
			}
			if(StringUtils.isNotEmpty(endTime2)){
				Date date2 = DateUtil.to24Date(endTime2+" 23:59:59");
				criteria.andPayTimeLessThanOrEqualTo(date2);
			}
			
			int count = orderDao.countByExample(example);
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
			
			List<Order> list = orderDao.selectByExample(example);
			
			String webSavePath = request.getSession().getServletContext().getRealPath(SAVE_PATH);
			File root = new File(webSavePath);
			if (!root.exists()) {
				root.mkdirs();
			}
			String filename = webSavePath+"\\"+longSdf.format(new Date() )+".xls";
			exportOrder(list,filename,request);
			UploadFile.download(filename,request,response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return StatusConstant.Status.SUCCESS.getStatus();
	} 
	
	/**
	 * 导出订单
	 * @param list
	 * @param filename
	 * @param request
	 */
	private void exportOrder(List<Order> list,String filename,HttpServletRequest request){
		try{
		Map<String,String> map1 = null;
		Map<String,String> map2 = null;
		Map<String,String> map3 = null;
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
			for(DictDetail d:typelist){
				map2.put(d.getValue(), d.getName());
			}
		}
		
		List<DictDetail> payTypelist = getDictDetailsByCode(DictConstant.BJWG_ORDER_PAY_TYPE);
		if(!MyUtils.isListEmpty(payTypelist)){
			map3 = new HashMap<String,String>();
			for(DictDetail d:payTypelist){
				map3.put(d.getValue(), d.getName());
			}
		}
		
		// 创建excel文件
		WritableWorkbook book = Workbook.createWorkbook(new File(filename));
		// 生成工作簿
		WritableSheet sheet = book.createSheet("订单列表", 0);
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
		
		Label label1 = new Label(0, 0, "ID", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(1, 0, "订单编号", wcfFC);
		sheet.addCell(label1);

		label1 = new Label(2, 0, "下单时间", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(3, 0, "付款时间", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(4, 0, "联系人", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(5, 0, "订单总金额", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(6, 0, "数量", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(7, 0, "订单状态", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(8, 0, "项目周期", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(9, 0, "订单类型", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(10, 0, "确认收货时间", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(11, 0, "支付方式", wcfFC);
		sheet.addCell(label1);
		
		label1 = new Label(12, 0, "备注", wcfFC);
		sheet.addCell(label1);
		
		int size = list.size();
		for(int i=0;i<size;i++){
			int j=0;
			Order order = list.get(i);
			String tag = order.getOrderId()+"";//ID
			Label label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag = order.getOrderCode()+"";//订单编号
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag = sdf.format(order.getCtime());//下单时间
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			if(order.getPayTime()!=null){
				tag= sdf.format(order.getPayTime());//付款时间
			}else{
				tag = "";
			}
			
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag= order.getUsername();//联系人
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag= order.getTotalMoney()+"";//订单总金额
			jxl.write.Number label3 = new jxl.write.Number(j++,i+1,new Double(tag));
			sheet.addCell(label3);
			tag= order.getNum()+"";//数量
			label3 = new jxl.write.Number(j++,i+1,new Double(tag));
			sheet.addCell(label3);
			tag= map1.get(order.getStatus()+"");//订单状态
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			tag= order.getRelationName();//项目名称
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			tag= map2.get(order.getType()+"");//订单类型
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			if(order.getConfirmTime()!=null){
				tag= sdf.format(order.getConfirmTime());//确认收货时间
			}else{
				tag = "";
			}
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			tag= map3.get(order.getPayType()+"");//支付方式
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			tag= order.getRemark();//备注
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

}
