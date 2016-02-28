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
import com.bjwg.back.model.Dict;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.Manager;
import com.bjwg.back.service.DictService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.DictDetailService;
import com.bjwg.back.util.MyUtils;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class DictDetailController extends BaseAction{
	
	@Autowired
	private DictDetailService dictDetailService;
	@Autowired
	private DictService dictService;
	@Autowired
	private OperationLogService operationLogService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/dictDetail/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		//系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_DICT_4.getStatus(), request);
        Pages<DictDetail> page = new Pages<DictDetail>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
		//分页查询菜单信息
        dictDetailService.queryPage(page);
        List<DictDetail> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
        return "dictdetail/list";
	}
	
	
    /**
     * 跳转到配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/dictDetail/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long id,HttpServletRequest request) throws Exception{
    	DictDetail dict = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_DICT_6.getStatus(), request);
        	dict = dictDetailService.getById(id);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_DICT_5.getStatus(), request);
        	dict = new DictDetail();
        }
        List<Dict> dictlist = dictService.getAll();
        request.setAttribute("dictDetail", dict);
        request.setAttribute("dictlist", dictlist);
        return "dictdetail/edit";
    } 
    
    /**
     * 字典明细配置
     * @param dict
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/dictDetail/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(DictDetail dictDetail,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(dictDetail.getDictDetailId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	int status = dictDetailService.saveOrupdate(dictDetail);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(dictDetail.getDictDetailId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(dictDetail.getDictDetailId(),dictDetail.getName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_DICT_DETAIL,"");
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
    @RequestMapping(value="/v/dictDetail/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String dictDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = dictDetailService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					DictDetail dict = dictDetailService.getById(temp);
					String opeObject = "";
					if(dict!=null){ opeObject = dict.getName();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_DICT_DETAIL,"");
				}
			}
		}
        return list(request);
    } 
    
    /**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/info/sort/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String infoSortList(HttpServletRequest request) throws Exception{
		//系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_INFO_9.getStatus(), request);
        Pages<DictDetail> page = new Pages<DictDetail>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
		//分页查询菜单信息
        dictDetailService.queryInfoSortPage(page);
        List<DictDetail> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
        return "info/sort_list";
	}
	
	
    /**
     * 跳转到资讯分类设置界面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/info/sort/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String infoSortEditInit(Long id,HttpServletRequest request) throws Exception{
    	DictDetail dict = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_INFO_11.getStatus(), request);
        	dict = dictDetailService.getById(id);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_INFO_10.getStatus(), request);
        	dict = new DictDetail();
        }
        request.setAttribute("dictDetail", dict);
        return "info/sort_edit";
    } 
    
    /**
     * 资讯分类设置
     * @param dict
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/info/sort/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String infoSortEdit(DictDetail dictDetail,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(dictDetail.getDictDetailId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	
    	Long id = dictDetail.getDictDetailId();
    	if(!MyUtils.isLongGtZero(id)){
    		//设置资讯类型
       	 	dictDetail.setCode(DictConstant.BJWG_INFO_TYPE);
       	 	//设置id
       	 	if(dictService.getByCode()!=null){
       	 		dictDetail.setDictId(dictService.getByCode().getDictId());
       	 	}
       	 	//设置值
       	 	int maxValue = dictDetailService.getMaxValue(DictConstant.BJWG_INFO_TYPE);
       	 	dictDetail.setValue((maxValue+1)+"");
 		}else{
 			DictDetail temp = dictDetailService.getById(id);
 			dictDetail.setCode(temp.getCode());
 			dictDetail.setDictDetailId(temp.getDictDetailId());
 			dictDetail.setDictId(temp.getDictId());
 			dictDetail.setRemark(temp.getRemark());
 			dictDetail.setValue(temp.getValue());
 		}
    	 
    	int status = dictDetailService.saveOrupdate(dictDetail);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(dictDetail.getDictDetailId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(dictDetail.getDictDetailId(),dictDetail.getName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_INFO_SORT,"");
        }
        return infoSortList(request);
    }

   
    /**
     * @param id
     * @param idList
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/info/sort/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String infoSortDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = dictDetailService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					DictDetail dict = dictDetailService.getById(temp);
					String opeObject = "";
					if(dict!=null){ opeObject = dict.getName();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_INFO_SORT,"");
				}
			}
		}
        return infoSortList(request);
    } 
}
