package com.bjwg.back.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

import com.bjwg.back.base.constant.CommConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Resc;
import com.bjwg.back.base.constant.RescOperationUtil;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;


/**
 * 过滤器
 * @author Kim
 * @version 创建时间：2015-8-6 下午02:12:04
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class SecurityFilter extends OncePerRequestFilter
{
    
    //不需要过滤的url
    private static final String[] noNeedFilterUrl = {"/nv/","index.jsp","/resources/","#","noAuthority.jsp","tree.jsp","head.jsp","login.jsp",".html",".jpg",".png",".js",".css"};
//    private static final String[] needSessionUrl = {"/nv/"};
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain arg2) throws ServletException, IOException
    {
        String contextPath = request.getContextPath();
        
        //是否需要过滤?
//        if(false)//!noNeedFiltedUrl(request))
        if(!noNeedFiltedUrl(request))
        {
            //是否已登录?
            HttpSession session = request.getSession(false);
            if(session == null)
            {
                response.sendRedirect(contextPath + "/index.jsp");
                return;
            }
            else
            {
                String loginFlag = (String)session.getAttribute(CommConstant.SESSION_MANAGER_LOGIN_FLAG);
                if(!StringUtils.isNotEmpty(loginFlag) || !CommConstant.SUCCESS.equals(loginFlag))
                {
                    response.sendRedirect(contextPath + "/index.jsp");
                    return;
                }
                else
                {
                    //管理员有所有权限
                	Manager user = (Manager)request.getSession().getAttribute(CommConstant.SESSION_MANAGER);
                	if(user==null){
                		response.sendRedirect(contextPath + "/index.jsp");
                        return;
                	}
                	Long managerId = user.getManagerId();
                	if(managerId!=null && managerId.equals(1L)){
                		arg2.doFilter(new MyRequestWrapper(request), response);
                	}else{
                    //取得当前用户拥有的权限
                    @SuppressWarnings("unchecked")
					List<Resc> rescList = (List<Resc>)session.getAttribute(CommConstant.SESSION_MANAGER_AUTHORITY_LINK);
                    
                    if(MyUtils.isListEmpty(rescList)){
                    	request.setAttribute("nav", PageConstant.PageMsg.NAV_HOME.getStatus());
                    	request.getRequestDispatcher("/pass/noAuthority.jsp").forward(request,response);
                    	return;
                    }else{
                        
                        int rootLength = request.getContextPath().length();
                        
                        String currentURL = request.getRequestURI();
                        
                        String subURL = "";
                        
                        if(currentURL.length() > rootLength){
                            
                            subURL = currentURL.substring(rootLength);
                        }
                        boolean flag = false;
                        //是否拥有当前链接的权限?
                        for (Resc s : rescList)
                        {
                        	String rescLink = s.getRescLink();
                            if(subURL.equals(rescLink)){
                                arg2.doFilter(new MyRequestWrapper(request), response);
                                flag = true;
                                break;
                            }else{
                            	boolean ifUseable = RescOperationUtil.isUseableOperation(rescLink, subURL);
                            	if(ifUseable){
                            		arg2.doFilter(new MyRequestWrapper(request), response);
                                    flag = true;
                                    break;
                            	}
                            }
                            
                        }
                        if(!flag){
	                    	request.setAttribute("nav", PageConstant.PageMsg.NAV_HOME.getStatus());
	                    	request.getRequestDispatcher("/pass/noAuthority.jsp").forward(request,response);
	                    	return;
                        }
                        
                    }
                }
                }
            }
        }
        else
        {
//	    	 HttpSession session = request.getSession(false);
//	         if(session == null){
//	             response.sendRedirect(contextPath + "/index.jsp");
//	             return;
//	         }
        	//是否已登录?
//        	if(needSessionUrl(request)){
//	            HttpSession session = request.getSession(false);
//	            
//	            if(session == null)
//	            {
//	                
//	                response.sendRedirect(contextPath + "/index.jsp");
//	                return;
//	            }
//	            String loginFlag = (String)session.getAttribute(CommConstant.SESSION_MANAGER_LOGIN_FLAG);
//	            
//	            if(!StringUtils.isNotEmpty(loginFlag) || !CommConstant.SUCCESS.equals(loginFlag))
//	            {
//	                response.sendRedirect(contextPath + "/index.jsp");
//	                return;
//	            }
//        	}
        	arg2.doFilter(new MyRequestWrapper(request), response);
        }
    }
    
    /**
     * 无需过滤的url
     * @param request
     * @return
     */
    private boolean noNeedFiltedUrl(HttpServletRequest request){
        
        String contextPath = request.getContextPath();
        
        String currentUrl = request.getRequestURI();//取得根目录的相对路径
        System.out.println("currentUrl=="+currentUrl);
        if(StringUtils.isNotEmpty(currentUrl)){
            
            for (String s : noNeedFilterUrl)
            {
                
                if(currentUrl.startsWith(contextPath + s) || currentUrl.endsWith(s) || currentUrl.equals(contextPath + "/")){
                    
                    return true;
                }
                
            }
        }
        
        return false;
    }
    /**
     * 需要session的url
     * @param request
     * @return
     */
//    private boolean needSessionUrl(HttpServletRequest request){
//        
//        String contextPath = request.getContextPath();
//        
//        String currentUrl = request.getRequestURI();//取得根目录的相对路径
//        System.out.println("currentUrl=="+currentUrl);
//        if(StringUtils.isNotEmpty(currentUrl)){
//            
//            for (String s : needSessionUrl)
//            {
//                
//                if(currentUrl.startsWith(contextPath + s) || currentUrl.endsWith(s) || currentUrl.equals(contextPath + "/")){
//                    
//                    return true;
//                }
//                
//            }
//        }
//        
//        return false;
//    }
}
