package com.bjwg.back.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.UploadFile;
import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Project;
import com.bjwg.back.service.DictDetailService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.ProjectService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.vo.SearchVo;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class ProjectController extends BaseAction{
	
	@Autowired
	private ProjectService projectService;
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
	@RequestMapping(value="/v/project/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_PROJECT_1.getStatus(), request);
        Pages<Project> page = new Pages<Project>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
      //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
        //时间查询条件
        String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		SearchVo vo = new SearchVo();
		vo.setStartTime(startTime);
		vo.setEndTime(endTime);
        
		//分页查询菜单信息
        projectService.queryPage(page,vo);
        
        List<Project> logList = page.getRoot();
        
        //状态
        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_PANICBUY_PROJECT_STATUS);
        request.setAttribute("statuslist", list);
        
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
        return "project/list";
	}

	/**
     * 跳转到配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/project/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long id,HttpServletRequest request) throws Exception{
    	Project project = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_PROJECT_3.getStatus(), request);
        	project = projectService.getById(id);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_PROJECT_2.getStatus(), request);
        	project = new Project();
        }
        //类型
        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_PANICBUY_PROJECT_TYPE);
        request.setAttribute("typelist", list);
        //品种
        //List<DictDetail> varietylist = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_PANICBUY_PROJECT_VARIETY);
        //request.setAttribute("varietylist", varietylist);
        //其他费用详情
        /**
    	List<ProjectFeeInfo> priceList = projectService.parseFeeDetail(project.getOtherFeeDetail());
    	if(!MyUtils.isListEmpty(priceList)){
    		request.setAttribute("priceList", priceList);
    		request.setAttribute("priceSize", priceList.size());
    	}else{
    		request.setAttribute("priceSize", 0);
    	}
    	*/
        if(project.getBeginTime()!=null){
        	request.setAttribute("stimeStr",format.format(project.getBeginTime()));
        }
        if(project.getEndTime()!=null){
        	request.setAttribute("etimeStr",format.format(project.getEndTime()));
        }
        request.setAttribute("project", project);
        setPhotoDomain(request);
        return "project/edit";
    } 
    
    /**
     * 抢购项目
     * @param project
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/project/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(Project project,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(project.getPaincbuyProjectId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	//设置开始抢购时间
    	String stimeStr = request.getParameter("stimeStr");
    	//设置结束抢购时间
 	    String etimeStr = request.getParameter("etimeStr");
 	    if(StringUtils.isNotEmpty(stimeStr)){
 	    	project.setBeginTime(format.parse(stimeStr));
 	    }
 	    if(StringUtils.isNotEmpty(etimeStr)){
 	    	project.setEndTime(format.parse(etimeStr));
 	    }
 	    
		int status = 0;
		//图片
		Map<String, Object> map = UploadFile.uploadSingleFile(request,"");
		if(map.get("status")!=null){
			Integer ret = Integer.parseInt(map.get("status").toString());
			if(ret.intValue()!=StatusConstant.Status.UPLOAD_NOFILE.getStatus() && ret.intValue()!=StatusConstant.Status.SUCCESS.getStatus()){
				status = ret.intValue();
				noticeMsg(StatusConstant.getMsg(status),request);
				return editInit(project.getPaincbuyProjectId(),request);
			}
		}
		
		if(map.get("filePath")!=null && StringUtils.isNotEmpty(map.get("filePath").toString())){
			project.setImgs(map.get("filePath").toString());
		}else{
			String nopath = request.getParameter("noheadImg");
			if(nopath!=null&&!nopath.equals("")){
				project.setImgs("");
			}
		}
		
		status = projectService.saveOrupdate(project);
		noticeMsg(StatusConstant.getMsg(status),request);
	    if(status != 1){
	    	return editInit(project.getPaincbuyProjectId(),request);
	    }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(project.getPaincbuyProjectId(),project.getName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_PROJECT,"");
	    }
	    return list(request);
    }

   
    /**
     * @param id
     * @param idList
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/project/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String projectDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = projectService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					Project project = projectService.getById(temp);
					String opeObject = "";
					if(project!=null){ opeObject = project.getName();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_PROJECT,"");
				}
			}
		}
        return list(request);
    } 
    
}
