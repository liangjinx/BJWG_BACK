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
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Dict;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.DictService;
import com.bjwg.back.util.MyUtils;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class DictController extends BaseAction{
	
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
	@RequestMapping(value="/v/dict/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_DICT_1.getStatus(), request);
        Pages<Dict> page = new Pages<Dict>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
      //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
		//分页查询菜单信息
        dictService.queryPage(page);
        List<Dict> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
        return "dict/list";
	}
	
	
    /**
     * 跳转到配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/dict/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long id,HttpServletRequest request) throws Exception{
    	Dict dict = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_DICT_3.getStatus(), request);
        	dict = dictService.getById(id);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_DICT_2.getStatus(), request);
        	dict = new Dict();
        }
        request.setAttribute("dict", dict);
        return "dict/edit";
    } 
    
    /**
     * 字典配置
     * @param dict
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/dict/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(Dict dict,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(dict.getDictId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	int status = dictService.saveOrupdate(dict);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(dict.getDictId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(dict.getDictId(),dict.getName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_DICT,"");
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
    @RequestMapping(value="/v/dict/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String dictDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = dictService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					Dict dict = dictService.getById(temp);
					String opeObject = "";
					if(dict!=null){ opeObject = dict.getName();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_DICT,"");
				}
			}
		}
        return list(request);
    } 
    
}
