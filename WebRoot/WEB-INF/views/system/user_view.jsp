<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>管理员信息查看</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
  </head>

<body>

<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	<form action="<common:WebRoot/>/v/managerEdit.do" method="post" id="allProjectForm" name="allProjectForm" enctype="multipart/form-data" >
        	 
                <div class="form-group">
                    <div class="label"><label for="readme">用户名<font color="red">*</font></label></div>
                    <div class="field">
                    	<label>${manager.username}</label>
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="label"><label for="siteurl">邮箱</label></div>
                    <div class="field">
                    	<label>${manager.email}</label>
                    </div>
                </div>
              		   
              	<div class="form-group">
                    <div class="label"><label for="siteurl">中文名称</label></div>
                    <div class="field">
                    	<label>${manager.chineseName}</label>
                    </div>
                </div>
                	   
              	 <div class="form-group">
                    <div class="label"><label for="siteurl">电话</label></div>
                    <div class="field">
                    	<label>${manager.phone}</label>
                    </div>
                </div>	
                    
              	<div class="form-group">
                    <div class="label"><label for="siteurl">性别</label>
                    	<input type="radio" value="1" name="sex" <c:if test="${manager.sex!=0}"> checked="checked"</c:if> readonly="readonly">  男
						<input type="radio" value="0" name="sex" <c:if test="${manager.sex==0}"> checked="checked"</c:if> readonly="readonly">  女
					</div>
                </div>
                 <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>