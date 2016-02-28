<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>">
    
    <title>查看用户</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
 <body>
<!-- head -->
<jsp:include page="../common.jsp" flush="true"></jsp:include>
  
 <!-- 主体 -->
<div class="admin">

    <div class="tab">
      <div class="tab-head">
        <strong>用户</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">用户查看</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	 <form action="<common:WebRoot/>/v/user/submit" method="post" class="form-x" >
        	 
                <input type="hidden" name="typeAU" id="typeAU" value="${type}" />
				<input type="hidden" name="userId" id="userId" value="${user.userId}" />
				 <div class="form-group">
                    <div class="label"><label for="logo">用户头像</label></div>
                    <div class="field">
	    				<c:if test="${user.headImg != ''}">
	    					<img class="w-img" src="<c:if test="${user.headImg!='' && user.headImg!='null' && user.headImg!=null && !fn:containsIgnoreCase(user.headImg,'http://')}">${domain}</c:if>${user.headImg}" width="100px" height="80px">
	    				</c:if>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">用户ID</label></div>
                    <div class="field">
                    	<label>${user.userId}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">用户名称</label></div>
                    <div class="field">
                    	<label>${user.username}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">昵称</label></div>
                    <div class="field">
                    	<label>${user.nickname}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">手机号码</label></div>
                   <div class="field">
                    	<label>${user.phone}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">邮箱</label></div>
                   <div class="field">
                    	<label>${user.email}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">性别</label></div>
                   <div class="field">
                    	<label>
                    		<c:if test="${user.sex==0}">男</c:if>
                    		<c:if test="${user.sex==1}">女</c:if>
                    	</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">注册时间</label></div>
                   <div class="field">
                    	<label><fmt:formatDate value='${user.registerTime }' pattern='yyyy-MM-dd HH:mm:ss' /> </label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">注册IP</label></div>
                   <div class="field">
                    	<label>${user.registerIp }</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">最后登入时间</label></div>
                   <div class="field">
                    	<label><fmt:formatDate value='${user.lastLoginTime }' pattern='yyyy-MM-dd HH:mm:ss' /> </label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">最后登入IP</label></div>
                   <div class="field">
                    	<label>${user.lastLoginIp }</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">用户类型</label></div>
                   <div class="field">
                    	<label>
							<c:if test="${user.userType==1}">八戒王国用户</c:if>
							<c:if test="${user.userType==2}">微信用户</c:if>
						</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">状态</label></div>
                   <div class="field">
                    	<label>
				          <c:if test="${user.status == 0 }">冻结 </c:if>
				   		  <c:if test="${user.status == 1 }">正常使用</c:if>
						</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">邀请码</label></div>
                   <div class="field">
                    	<label>${user.inviteCode}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="logo">二维码</label></div>
                    <div class="field">
	    				<c:if test="${user.qrCode != ''}">
	    					<img class="w-img" src="<c:if test="${user.qrCode!='' && user.qrCode!='null' && user.qrCode!=null && !fn:containsIgnoreCase(user.qrCode,'http://')}">${domain}</c:if>${user.qrCode}" width="100px" height="80px">
	    				</c:if>
                    </div>
                </div>
                <button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
            </form>
        </div>
      </div>
    </div>
</div>
  </body>
</html>
	    					