<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>角色增加/编辑</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript" src="<common:WebRoot/>/resources/system/system.js"></script>
	
	<script type="text/javascript">
	
		$(document).ready(function(){
			
			commonTip("${requestScope.msg}");
			
		});
	
	</script>
	
  </head>

<body>
<div class="admin">
<div class="tab">
  <div class="tab-head">
        <strong>权限管理</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">添加角色</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/v/roleEdit.do">
        	
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="roleId" name="roleId" value="${requestScope.role.roleId }">
        		
				<div class="form-group">
                	<div class="label">
                		<label>角色名称<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="roleName" name="roleName" value="${requestScope.role.roleName }" 
                			size="50" maxlength="30" data-validate="required:请填写角色名称" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="sitename">角色描述</label></div>
                    <div class="field">
                    	<input type="text" class="input" id="description" name="description" value="${requestScope.role.description }" 
                    		size="50" maxlength="80" />
                    </div>
                </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisRole();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
</body>
</html>
