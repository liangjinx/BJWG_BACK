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
import com.bjwg.back.model.Info;
import com.bjwg.back.service.DictDetailService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.InfoService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.vo.SearchVo;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class InfoController extends BaseAction{
	
	@Autowired
	private InfoService infoService;
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
	@RequestMapping(value="/v/info/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_INFO_5.getStatus(), request);
        Pages<Info> page = new Pages<Info>();
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
        infoService.queryPage(page,vo);
        
        List<Info> logList = page.getRoot();
        
        //类型
        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_INFO_TYPE);
        request.setAttribute("typelist", list);
        
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
        return "info/list";
	}

	/**
     * 跳转到配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/info/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long id,HttpServletRequest request) throws Exception{
    	Info info = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_INFO_7.getStatus(), request);
        	info = infoService.getById(id);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_INFO_6.getStatus(), request);
        	info = new Info();
        }
        //类型
        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_INFO_TYPE);
        request.setAttribute("typelist", list);
        request.setAttribute("info", info);
        setPhotoDomain(request);
        return "info/edit";
    } 
    
    /**
     * 字典配置
     * @param info
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/info/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(Info info,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(info.getInfoId())){
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
 				return editInit(info.getInfoId(),request);
 			}
 		}
 		
 		if(map.get("filePath")!=null && StringUtils.isNotEmpty(map.get("filePath").toString())){
 			info.setPath(map.get("filePath").toString());
 		}else{
 			String nopath = request.getParameter("nopath");
 			if(nopath!=null&&!nopath.equals("")){
 				info.setPath("");
 			}
 		}
 		
    	status = infoService.saveOrupdate(info);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(info.getInfoId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(info.getInfoId(),info.getTitle(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_INFO,"");
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
    @RequestMapping(value="/v/info/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String infoDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = infoService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					Info info = infoService.getById(temp);
					String opeObject = "";
					if(info!=null){ opeObject = info.getTitle();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_INFO,"");
				}
			}
		}
        return list(request);
    } 
    
}
