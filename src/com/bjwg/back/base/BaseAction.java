package com.bjwg.back.base;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.bjwg.back.base.constant.CommConstant;
import com.bjwg.back.base.constant.StatusConstant.Status;
import com.bjwg.back.base.constant.SystemConstant;
//import com.bjwg.back.model.manager.Manager;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.service.SysconfigService;


/**
 * @author Carter
 * @version 创建时间：Sep 8, 2015 5:18:37 PM
 * @Modified By:Carter
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
@Controller
@Scope("prototype")
public class BaseAction {
	
	@Autowired
	private SysconfigService sysconfigService;
	
	//当前页码
	public int currentPage = 1;
	//页大小
	public int perRows = 10;

	protected static Logger logger = LogManager.getLogger();
	/**
	 * 设置导航标识
	 * @param nav
	 * @param request
	 */
	public void setNav(int nav, HttpServletRequest request){
	    
	    request.setAttribute("nav", nav);
	}
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response)
	{
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			//页码参数自动注入
			if(request != null && request.getParameter("currentPage") != null && !request.getParameter("currentPage").equals("")){
		        this.currentPage = Integer.valueOf(request.getParameter("currentPage"));
		    }
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		this.request = request;
		this.response = response;
		this.jsonResponseResult = new JSONObject();
		this.putJsonStatus(Status.SUCCESS);
	}
	protected JSONObject jsonResponseResult;
	/**
	 * 返回页面的操作提示
	 * @param type 成功或失败
	 * @param model
	 * @param msg
	 */
	public void noticeMsg(String msg,HttpServletRequest request){
	    request.setAttribute("msg", msg);
	}
	/**
	 * 清空提示
	 * @param request
	 */
	public void clearMsg(HttpServletRequest request){
		request.setAttribute("msg", "");
	}
	/**
	 * 返回页面导航
	 * @param nav
	 * @param request
	 */
	public void navIndex(int nav, HttpServletRequest request){
	    request.setAttribute("nav", nav);
	}
    /**
	 * 返回登入的userId
	 * @param nav
	 * @param request
	 */
	public Manager getLoginUser(HttpServletRequest request){
		return (Manager)request.getSession().getAttribute(CommConstant.SESSION_MANAGER);
	}
	 /**
     * 设置图片的属性
     * @param request
     * @param address 传入的图片地址
     */
    public void setPhotoDomain(HttpServletRequest request){
		String url = "";
		Sysconfig sys = null;
		try {
			sys = sysconfigService.getByCode(SystemConstant.PROJECT_IMG_ACCESS_URL);
			if(sys!=null){
        		url = sys.getValue();
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("domain",url);
    }
    /**
     * 是否有所有数据的权限
     * @param request
     * @return
     */
    protected boolean isViewOtherData(HttpServletRequest request){
    	boolean flag =  (Boolean)request.getSession().getAttribute(CommConstant.SESSION_ISVIEW_ALLDATA);
    	return flag;
    }
    /**
     * 是否有子账号的权限
     * @param request
     * @return
     */
    protected boolean isViewChildData(HttpServletRequest request){
    	boolean flag =  (Boolean)request.getSession().getAttribute(CommConstant.SESSION_ISVIEW_CHILD);
    	return flag;
    }
    /**
     * json数据返回到页面
     * @param response
     * @param htmlString
     * @throws Exception
     */
    protected void jsonOut(HttpServletResponse response,String htmlString) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(htmlString);
	}
    protected static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    public JSONObject putJsonStatus(Status jsonResult) throws JSONException
	{
	
		JSONObject obj = this.jsonResponseResult;
		int index=jsonResult.getStatus();
	
		obj.put("status", index).
			put("msg", jsonResult.toString()).
			put("data", obj.has("data")? 
						obj.getJSONObject("data").put("text", jsonResult.getMsg()): 
						new JSONObject().put("text", jsonResult.getMsg()));

		return obj;
	}

	public String outputJson() throws JSONException
	{	
		
		return this.jsonResponseResult.put("timestamp", Long.toString(new Date().getTime())).toString();
	}


}

