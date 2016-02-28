package com.bjwg.back.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.bjwg.back.model.Bulletin;
import com.bjwg.back.service.DictDetailService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.BulletinService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.vo.SearchVo;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class BulletinController extends BaseAction{
	
	@Autowired
	private BulletinService bulletinService;
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
	@RequestMapping(value="/v/bulletin/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_INFO_1.getStatus(), request);
        Pages<Bulletin> page = new Pages<Bulletin>();
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
        bulletinService.queryPage(page,vo);
        
        List<Bulletin> logList = page.getRoot();
        
        //类型
        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_BULLETIN_TYPE);
        request.setAttribute("typelist", list);
        
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
        return "bulletin/list";
	}

	/**
     * 跳转到配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/bulletin/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long id,HttpServletRequest request) throws Exception{
    	Bulletin bulletin = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_INFO_3.getStatus(), request);
        	bulletin = bulletinService.getById(id);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_INFO_2.getStatus(), request);
        	bulletin = new Bulletin();
        }
        //类型
        List<DictDetail> list = dictDetailService.getDictDetailsByCode(DictConstant.BJWG_BULLETIN_TYPE);
        request.setAttribute("typelist", list);
        request.setAttribute("bulletin", bulletin);
        return "bulletin/edit";
    } 
    
    /**
     * 字典配置
     * @param bulletin
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/bulletin/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(Bulletin bulletin,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(bulletin.getBulletinId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	int status = bulletinService.saveOrupdate(bulletin);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(bulletin.getBulletinId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(bulletin.getBulletinId(),bulletin.getTitle(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_BULLETIN,"");
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
    @RequestMapping(value="/v/bulletin/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String bulletinDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = bulletinService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					Bulletin bulletin = bulletinService.getById(temp);
					String opeObject = "";
					if(bulletin!=null){ opeObject = bulletin.getTitle();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_BULLETIN,"");
				}
			}
		}
        return list(request);
    } 
    
}
