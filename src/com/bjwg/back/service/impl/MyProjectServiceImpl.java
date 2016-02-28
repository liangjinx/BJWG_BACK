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
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.ProjectConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.base.constant.SystemConstant;
import com.bjwg.back.dao.DictDetailDao;
import com.bjwg.back.dao.MyProjectDao;
import com.bjwg.back.dao.SysconfigDao;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.DictDetailExample;
import com.bjwg.back.model.MyProject;
import com.bjwg.back.model.MyProjectExample;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.model.SysconfigExample;
import com.bjwg.back.service.MyProjectService;
import com.bjwg.back.util.DateUtil;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.UploadFile;
import com.bjwg.back.vo.SearchVo;

/**
 * 系统配置接口实现类
 * @author Kim
 * @version 创建时间：2015-4-3 下午04:03:17
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class MyProjectServiceImpl implements MyProjectService 
{
	@Autowired
    private MyProjectDao myProjectDao;
	@Autowired
    private SysconfigDao sysconfigDao;
	@Autowired
	private DictDetailDao dictDetailDao;
	
	@Override
	public void queryPage(Pages<MyProject> page,SearchVo vo) throws Exception {
		MyProjectExample example = new MyProjectExample();
		MyProjectExample.Criteria criteria = example.createCriteria();
		System.out.println("-------------------"+page.getWhereSql());
		criteria.addCriterion(page.getWhereSql());
		
		//处理时间
		if(StringUtils.isNotEmpty(vo.getStartTime())){
			Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
			criteria.andDealTimeGreaterThanOrEqualTo(date1);
		}
		if(StringUtils.isNotEmpty(vo.getEndTime())){
			Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
			criteria.andDealTimeLessThanOrEqualTo(date2);
		}
		
		int count = myProjectDao.countByExample(example);
		page.setCountRows(count);
		example.setPage(page);
		example.setOrderByClause("t.earnings_id");
		List<MyProject> ops = myProjectDao.selectByExample(example);
		page.setRoot(ops);
	}

//	@Override
//	public int saveOrupdate(MyProject dict) throws Exception{
//		
//		if(!StringUtils.isNotEmpty(dict.getName())){
//			return StatusConstant.Status.DICT_NAME_NULL.getStatus();
//		}
//		if(!ValidateUtil.validateString(dict.getName().trim(),false,1,45)){
//			return StatusConstant.Status.DICT_NAME_OVER_LEN.getStatus();
//		}
//		if(!StringUtils.isNotEmpty(dict.getCode())){
//			return StatusConstant.Status.DICT_CODE_NULL.getStatus();
//		}
//		if(!ValidateUtil.validateString(dict.getCode().trim(),false,1,45)){
//			return StatusConstant.Status.DICT_CODE_OVER_LEN.getStatus();
//		}
//		
//		Long id = dict.getEarningsId();
//		//自动增加父级目录
//		//新增
//		if(!MyUtils.isLongGtZero(id)){
//			return myProjectDao.insertSelective(dict);
//		}else{
//			return myProjectDao.updateByPrimaryKeySelective(dict);
//		}
//	}
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
            	myProjectDao.deleteByPrimaryKey(id);
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
	public MyProject getById(Long id) throws Exception {
		return myProjectDao.selectByPrimaryKey(id);
	}

	@Override
	public List<MyProject> getAll() throws Exception {
		return myProjectDao.selectByExample(new MyProjectExample());
	}

	@Override
	public int updateDealStatusByUidAndPid(Long userId,Long projectId) throws Exception {
		MyProjectExample example = new MyProjectExample();
		MyProjectExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andPaincbuyProjectIdEqualTo(projectId);
		List<MyProject> ops = myProjectDao.selectByExample(example);
		MyProject myProject = null;
		if(!MyUtils.isListEmpty(ops)){
			myProject = ops.get(0);
			myProject.setDealStatus(ProjectConstant.DEALS_TATUS_1);
			myProject.setDealTime(new Date());
		}
		return StatusConstant.Status.SUCCESS.getStatus();
	}

	@Override
	public int checkExportList(String sql,SearchVo vo){
		try {
			MyProjectExample example = new MyProjectExample();
			MyProjectExample.Criteria criteria = example.createCriteria();
			criteria.addCriterion(sql);
			
			//处理时间
			if(StringUtils.isNotEmpty(vo.getStartTime())){
				Date date1 = DateUtil.to24Date(vo.getStartTime()+" 00:00:00");
				criteria.andDealTimeGreaterThanOrEqualTo(date1);
			}
			if(StringUtils.isNotEmpty(vo.getEndTime())){
				Date date2 = DateUtil.to24Date(vo.getEndTime()+" 23:59:59");
				criteria.andDealTimeLessThanOrEqualTo(date2);
			}
			
			
			int count = myProjectDao.countByExample(example);
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
	        String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			
			MyProjectExample example = new MyProjectExample();
			MyProjectExample.Criteria criteria = example.createCriteria();
			criteria.addCriterion(whereSql);
			
			//下单时间
			if(StringUtils.isNotEmpty(startTime)){
				Date date1 = DateUtil.to24Date(startTime+" 00:00:00");
				criteria.andDealTimeGreaterThanOrEqualTo(date1);
			}
			if(StringUtils.isNotEmpty(endTime)){
				Date date2 = DateUtil.to24Date(endTime+" 23:59:59");
				criteria.andDealTimeLessThanOrEqualTo(date2);
			}
			
			int count = myProjectDao.countByExample(example);
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
			
			List<MyProject> list = myProjectDao.selectByExample(example);
			
			String webSavePath = request.getSession().getServletContext().getRealPath(SAVE_PATH);
			File root = new File(webSavePath);
			if (!root.exists()) {
				root.mkdirs();
			}
			String filename = webSavePath+"\\"+longSdf.format(new Date() )+".xls";
			exportMyProject(list,filename,request);
			UploadFile.download(filename,request,response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return StatusConstant.Status.SUCCESS.getStatus();
	} 
	
	/**
	 * 导出我的收益
	 * @param list
	 * @param filename
	 * @param request
	 */
	private void exportMyProject(List<MyProject> list,String filename,HttpServletRequest request){
		try{
		Map<String,String> map1 = null;
		List<DictDetail> statuslist = getDictDetailsByCode(DictConstant.BJWG_MY_EARNINGS_DEAL_TYPE);
		if(!MyUtils.isListEmpty(statuslist)){
			map1 = new HashMap<String,String>();
			for(DictDetail d:statuslist){
				map1.put(d.getValue(), d.getName());
			}
		}
		// 创建excel文件
		WritableWorkbook book = Workbook.createWorkbook(new File(filename));
		// 生成工作簿
		WritableSheet sheet = book.createSheet("用户收益列表", 0);
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

		Label label1 = new Label(0, 0, "用户名称", wcfFC);
		sheet.addCell(label1);

		label1 = new Label(1, 0, "项目名称", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(2, 0, "数量", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(3, 0, "年化率", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(4, 0, "预期收益", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(5, 0, "成本", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(6, 0, "开始累计时间", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(7, 0, "结束累计时间", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(8, 0, "处理状态", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(9, 0, "处理时间", wcfFC);
		sheet.addCell(label1);
		label1 = new Label(10, 0, "处理方式", wcfFC);
		sheet.addCell(label1);
		
		int size = list.size();
		for(int i=0;i<size;i++){
			int j=0;
			MyProject myProject = list.get(i);
			String tag = myProject.getUsername();//用户名称
			Label label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag = myProject.getPaincbuyProjectName();//项目名称
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			tag= (((myProject.getNum()-myProject.getPresentNum())<0)?0:(myProject.getNum()-myProject.getPresentNum()))+"";//数量
			jxl.write.Number label3 = new jxl.write.Number(j++,i+1,new Double(tag));
			sheet.addCell(label3);
			tag= myProject.getRate()+"";//年化率
			label2 = new Label(j++,i+1,new Double(tag)+"%");
			sheet.addCell(label2);
			tag= myProject.getExpectEarning()+"";//预期收益
			label3 = new jxl.write.Number(j++,i+1,new Double(tag));
			sheet.addCell(label3);
			tag= myProject.getMoney()+"";//成本
			label3 = new jxl.write.Number(j++,i+1,new Double(tag));
			sheet.addCell(label3);
			
			if(myProject.getBeginTime()!=null){
				tag= shortSdf.format(myProject.getBeginTime());//开始累计时间
			}else{
				tag = "";
			}
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			if(myProject.getEndTime()!=null){
				tag= shortSdf.format(myProject.getEndTime());//结束累计时间
			}else{
				tag = "";
			}
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			if(myProject.getDealStatus().intValue()==0){
				tag = "未处理";
			}else if(myProject.getDealStatus().intValue()==1){
				tag = "处理完成";
			}else{
				tag = "";
			}
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			if(myProject.getDealTime()!=null){
				tag = shortSdf.format(myProject.getDealTime());//处理时间
			}else{
				tag = "";
			}
			label2 = new Label(j++,i+1, tag);
			sheet.addCell(label2);
			
			tag= map1.get(myProject.getDealType()+"");//处理方式
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
	private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
}
