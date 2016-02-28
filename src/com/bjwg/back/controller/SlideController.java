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
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Slide;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.SlideService;
import com.bjwg.back.util.MyUtils;

@Controller
@Scope("prototype")
public class SlideController extends BaseAction{
	
	@Autowired
	private SlideService slideService;
	@Autowired
	private OperationLogService operationLogService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/slide/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_10.getStatus(), request);
        Pages<Slide> page = new Pages<Slide>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
      //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
		//分页查询菜单信息
        slideService.queryPage(page);
        List<Slide> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
        setPhotoDomain(request);
        return "slide/list";
	}
	
	
    /**
     * 跳转到配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/slide/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long id,HttpServletRequest request) throws Exception{
    	Slide slide = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_12.getStatus(), request);
        	slide = slideService.getById(id);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_11.getStatus(), request);
        	slide = new Slide();
        }
        request.setAttribute("slide", slide);
        setPhotoDomain(request);
        return "slide/edit";
    } 
    
    /**
     * 字典配置
     * @param slide
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/slide/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(Slide slide,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(slide.getSlideId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
  		int status = 0;
 		//店铺logo
		Map<String, Object> map = UploadFile.uploadSingleFile(request,"");
		if(map.get("status")!=null){
			Integer ret = Integer.parseInt(map.get("status").toString());
			if(ret.intValue()!=StatusConstant.Status.UPLOAD_NOFILE.getStatus() && ret.intValue()!=StatusConstant.Status.SUCCESS.getStatus()){
				status = ret.intValue();
				noticeMsg(StatusConstant.getMsg(status),request);
				return editInit(slide.getSlideId(),request);
			}
		}
		if(map.get("filePath")!=null && StringUtils.isNotEmpty(map.get("filePath").toString())){
			slide.setPath(map.get("filePath").toString());
		}else{
			String nopath = request.getParameter("nopath");
			if(nopath!=null&&!nopath.equals("")){
				slide.setPath("");
			}
		}
		
    	status = slideService.saveOrupdate(slide);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(slide.getSlideId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(slide.getSlideId(),slide.getName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_DICT,"");
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
    @RequestMapping(value="/v/slide/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String slideDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = slideService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					Slide slide = slideService.getById(temp);
					String opeObject = "";
					if(slide!=null){ opeObject = slide.getName();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SLIDE,"");
				}
			}
		}
        return list(request);
    } 
    
}
