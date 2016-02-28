package com.bjwg.back.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.MyCoupon;
import com.bjwg.back.model.MyProject;
import com.bjwg.back.model.User;
import com.bjwg.back.model.Present;
import com.bjwg.back.service.MyCouponService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.UserService;
import com.bjwg.back.service.PresentService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.vo.SearchVo;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class PresentController extends BaseAction{
	
	@Autowired
	private PresentService presentService;
	@Autowired
	private UserService userService;
	@Autowired
	private MyCouponService myCouponService;
	@Autowired
	private OperationLogService operationLogService;
	/**
	 * 猪肉券赠送记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/present/coupon/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String couponList(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_MYCOUPON_3.getStatus(), request);
        Pages<Present> page = new Pages<Present>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
      //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
        String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
        SearchVo vo = new SearchVo();
        vo.setStartTime(startTime);
        vo.setEndTime(endTime);
        
		//分页查询菜单信息
        presentService.queryPage(page,vo,(byte)2);
        List<Present> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("type",(byte)2);
        return "present/coupon_list";
	}
	/**
	 * 猪仔赠送记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/present/pig/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String pigList(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_MYCOUPON_4.getStatus(), request);
        Pages<Present> page = new Pages<Present>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
      //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
        String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
        SearchVo vo = new SearchVo();
        vo.setStartTime(startTime);
        vo.setEndTime(endTime);
        
		//分页查询菜单信息
        presentService.queryPage(page,vo,(byte)1);
        List<Present> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("type",(byte)1);
        return "present/pig_list";
	}
	
    /**
     * 跳转到赠送猪肉券/猪仔配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/present/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long presentId,HttpServletRequest request) throws Exception{
    	Present present = null;
    	this.navIndex(PageConstant.PageMsg.NAV_MYCOUPON_5.getStatus(), request);
        //修改
        if(MyUtils.isLongGtZero(presentId)){
        	present = presentService.getById(presentId);
            //新增
        }else{
        	present = new Present();
        }
        List<User> users = userService.getAllUser();
        request.setAttribute("present", present);
        request.setAttribute("users", users);
        return "present/save";
    } 
    
    /**
     * 赠送猪肉券/猪仔配置
     * @param present
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/present/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(Present present,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(present.getPresentId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	int status = presentService.saveOrupdate(present);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(present.getPresentId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(present.getPresentId(),present.getPresentUser()+"",text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_PRESENT,"");
        }
        Byte type = present.getType();
        if(type.intValue()==1){
        	return pigList(request);
        }
        	return couponList(request);
    }

   
    /**
     * @param id
     * @param idList
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/present/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String presentDelete(Long presentId,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(presentId);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = presentService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(presentId)){
					Present present = presentService.getById(temp);
					String opeObject = "";
					if(present!=null){ opeObject = present.getPresentUser()+"";}
					operationLogService.oplog(presentId,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_PRESENT,"");
				}
			}
		}
        String type = request.getParameter("type");
        if("1".equals(type)){
        	return pigList(request);
        }
        return couponList(request);
    } 
    /**
	 * 根据赠送人Id和赠送类型 查询关联的赠品列表
	 * @param code
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/nv/present/load", method = RequestMethod.POST)
	@ResponseBody
	public void loadPresent(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Long userId = null;
			Integer type = null;
			String presentUserId = request.getParameter("presentUserId");
			if(StringUtils.isNotEmpty(presentUserId)){
				userId = Long.parseLong(presentUserId);
			}
			String typeStr = request.getParameter("type");
			if(StringUtils.isNotEmpty(presentUserId)){
				type = Integer.parseInt(typeStr);
			}
			JSONArray array = null;
			if(type!=null){
				//1:送猪仔
				if(type.intValue()==1){
					List<MyProject> list = presentService.getMyProject(userId);
					array = JSONArray.fromObject(list);
				//2:送猪肉券
				}else if(type.intValue()==2){
					List<MyCoupon> couponList = myCouponService.getMyCoupon(userId);
					array = JSONArray.fromObject(couponList);
				}
			}
			if(array!=null){
				PrintWriter out = response.getWriter();
				System.out.println("ddd"+array.toString());
				out.write("{\"des\":" + array + "}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
