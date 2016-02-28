package com.bjwg.back.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.MyProject;
import com.bjwg.back.service.DictDetailService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.MyProjectService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.vo.SearchVo;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class MyProjectController extends BaseAction{
	
	@Autowired
	private MyProjectService myProjectService;
	@Autowired
	private DictDetailService dictDetailService;
	@Autowired
	private OperationLogService operationLogService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/myproject/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_USER_14.getStatus(), request);
        Pages<MyProject> page = new Pages<MyProject>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
        //处理时间
        String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		SearchVo vo = new SearchVo();
		vo.setStartTime(startTime);
		vo.setEndTime(endTime);
        
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
        //处理方式
        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_MY_EARNINGS_DEAL_TYPE);
        request.setAttribute("typelist", list);
        
		//分页查询菜单信息
        myProjectService.queryPage(page,vo);
        List<MyProject> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        return "myproject/list";
	}
	
	
//    /**
//     * 跳转到用户收益页面
//     * @param id
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value="/v/myProject/editInit", method = {RequestMethod.POST, RequestMethod.GET})
//    public String editInit(Long id,HttpServletRequest request) throws Exception{
//    	MyProject myProject = null;
//        //修改
//        if(MyUtils.isLongGtZero(id)){
//        	this.navIndex(PageConstant.PageMsg.NAV_DICT_3.getStatus(), request);
//        	myProject = myProjectService.getById(id);
//            //新增
//        }else{
//        	this.navIndex(PageConstant.PageMsg.NAV_DICT_2.getStatus(), request);
//        	myProject = new MyProject();
//        }
//        request.setAttribute("myProject", myProject);
//        return "myProject/edit";
//    } 
//    
//    /**
//     * 用户收益
//     * @param myProject
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value="/v/myProject/edit", method = {RequestMethod.POST, RequestMethod.GET})
//    public String edit(MyProject myProject,HttpServletRequest request) throws Exception{
//    	String text = "";
//    	 if(MyUtils.isLongGtZero(myProject.getMyProjectId())){
//    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
//    	 }else{
//    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
//    	 }
//    	int status = myProjectService.saveOrupdate(myProject);
//    	noticeMsg(StatusConstant.getMsg(status),request);
//        if(status != 1){
//        	return editInit(myProject.getMyProjectId(),request);
//        }else{
//			//记录日志
//			Manager user = getLoginUser(request);
//			operationLogService.oplog(myProject.getMyProjectId(),myProject.getName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_MY_PROJECT,"");
//        }
//        return list(request);
//    }

   
    /**
     * @param id
     * @param idList
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/myproject/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String myProjectDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = myProjectService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					MyProject myProject = myProjectService.getById(temp);
					String opeObject = "";
					if(myProject!=null){ opeObject = myProject.getPaincbuyProjectName();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_MY_PROJECT,"");
				}
			}
		}
        return list(request);
    } 
    /**
	 *  验证导出页面
	 * @param request
	 * @param response
	 * @return
	 * @user Kim
	 */
	@RequestMapping(value = "/v/myproject/checkExport", method = { RequestMethod.POST,RequestMethod.GET })
	public void checkExportList(HttpServletRequest request,HttpServletResponse response) {
		try {
	        //查询条件
	        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
	        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
	        //处理时间
	        String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			
	        SearchVo vo = new SearchVo();
	        vo.setStartTime(startTime);
	        vo.setEndTime(endTime);
	        
			int status = myProjectService.checkExportList(whereSql,vo);
			if(status < 0){
				jsonOut(response,StatusConstant.getMsg(status));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导出页面
	 * @param request
	 * @param response
	 * @return
	 * @user Kim
	 */
	@RequestMapping(value = "/v/myproject/export", method = { RequestMethod.POST,RequestMethod.GET })
	public void exportListShop(HttpServletRequest request,HttpServletResponse response) {
		try {
	        
			int status = myProjectService.exportList(request,response);
			if(status==1){
				//记录日志
				Manager user = getLoginUser(request);
				operationLogService.oplog(null,"",OperationLogConstant.OPE_MODULE_TYPE_EXPORT,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_MY_PROJECT,"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
