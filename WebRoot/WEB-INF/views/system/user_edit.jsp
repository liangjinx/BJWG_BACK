<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>管理员增加/编辑</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<common:WebRoot/>/resources/modular/css/jquery.multiSelect.css" type="text/css">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript" src="<common:WebRoot/>/resources/system/system.js"></script>
	<script type="text/javascript" src="<common:WebRoot/>/resources/modular/js/jquery.multiSelect.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
			$('#multiple').multiSelect({
				selectAllText:"全选",
				noneSelected:"--请选择--",
				oneOrMoreSelected:"% 已选择"
			});
		});
	</script>
  </head>

<body>

<div class="admin">
 <div class="tab">
  <div class="tab-head">
        <strong>权限管理</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">添加管理员</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	<form action="<common:WebRoot/>/nv/managerEdit.do" method="post" id="allProjectForm" name="allProjectForm" class="form-x" enctype="multipart/form-data" >
        	 
        	 	<input type="hidden" name="typeAU" id="typeAU" value="${type}" />
				<input type="hidden" name="managerId" id="managerId" value="${manager.managerId}" />
				<input type="hidden" name="roleIds" id="roleIds" value="" />
        	
                <div class="form-group">
                    <div class="label"><label>用户名<font color="red">*</font></label></div>
                    <div class="field">
                    	<input type="text"  class="input" size="50" name="username" id="username" data-validate="required:请填写用户名" value="${manager.username}"/>
                    	<form:errors path="manager.username" cssStyle="color:red"> </form:errors>
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="label"><label>密码<font color="red">*</font></label></div>
                    <div class="field">
                    	<input class="input" size="50" type="password"" name="password" id="password"  data-validate="required:请填写密码"  value="${manager.password}"/>
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="label"><label>邮箱</label></div>
                    <div class="field">
                    	 <input type="text" name="email" class="input" size="50" data-validate="email:请填写正确的邮箱" value="${manager.email}"/>
                    </div>
                </div>
              		   
              	<div class="form-group">
                    <div class="label"><label>中文名称</label></div>
                    <div class="field">
                    	 <input type="text" name="chineseName" class="input" size="50" value="${manager.chineseName}"/>
                    </div>
                </div>
                	   
              	 <div class="form-group">
                    <div class="label"><label>电话</label></div>
                    <div class="field">
                    	 <input type="text" name="phone" class="input" size="50" data-validate="tel:请填写正确的电话号码" value="${manager.phone}"/>
                    </div>
                </div>	
              	<div class="form-group">
                    <div class="label"><label>性别</label></div>
                    <div class="field">
                    	<input type="radio" value="1" name="sex" class="radio" <c:if test="${manager.sex!=0}"> checked="checked"</c:if>>  男
						<input type="radio" value="0" name="sex" class="radio" <c:if test="${manager.sex==0}"> checked="checked"</c:if>>  女
					</div>
                </div>
                <div class="form-group">
                	<div class="label"><label>角色</label></div>
               		<div class="field">
                		<select multiple="multiple" id="multiple" name="multiple" style="width: 120px;">
	                    	<c:forEach items="${roles}" var="role">
	                    		<c:set var="isselected" value=""/>
								<c:forEach var="pid" items="${manager.roles}">
									<c:if test="${pid.roleId==role.roleId}">
										<c:set var="isselected" value="selected"/>
									</c:if>
								</c:forEach>
	                    		<option value="${role.roleId}" ${isselected}>${role.roleName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
                 <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisUser();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
</body>
</html>