<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>资源增加/编辑</title>
    
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
          <li class="active"><a href="#tab-set">添加资源</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/v/roleEdit.do">
        	
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="rescId" name="rescId" value="${requestScope.resc.rescId}">
        		
				<div class="form-group">
                	<div class="label">
                		<label>资源名称<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="rescName" name="rescName" value="${requestScope.resc.rescName }" 
                			size="50" maxlength="30" data-validate="required:请填写资源名称" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>资源链接<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="roleLink" name="rescLink" value="${requestScope.resc.rescLink }" 
                			size="50" maxlength="30" data-validate="required:请填写资源链接" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>资源编号<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="roleCode" name="rescCode" value="${requestScope.resc.rescCode }" 
                			size="50" maxlength="30" data-validate="required:请填写资源编号" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>资源标识（控制按钮）</label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="securityCode" name="securityCode" value="${requestScope.resc.securityCode }" 
                			size="50" maxlength="30" />
                    </div>
                </div>   
                    <div class="form-group">
	                    <div class="label">
	                    	<label for="readme">资源组别</label>
	                    </div>
	                    <div class="field">
	                    	<select name="rescGroup" id="rescGroup">
	                    		<c:forEach var="pers" items="${permissionScops}" >
									<option value="${pers.key}" <c:if test="${requestScope.resc.rescGroup==pers.key}">selected</c:if> >${pers.value}</option>
								</c:forEach>
	                		</select>
	                    </div>
	                </div>
	                <div class="form-group">
                    <div class="label"><label for="sitename">资源描述</label></div>
                    <div class="field">
                    	<input type="text" class="input" id="description" name="description" value="${requestScope.resc.description }" 
                    		size="50" maxlength="80" />
                    </div>
                </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisResc();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
</body>
</html>
