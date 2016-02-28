<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>管理员增加</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript" src="<common:WebRoot/>/resources/system/system.js"></script>
	<script type="text/javascript" src="<common:WebRoot/>/resources/modular/js/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript" src="<common:WebRoot/>/resources/modular/js/jquery.multiSelect.js"></script>
	<link rel="stylesheet" href="<common:WebRoot/>/resources/modular/css/jquery.multiSelect.css">
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
			$("#roleIds").multiSelect({  
				selectAll: false,  
				oneOrMoreSelected: "*", 
				selectAllText: "全选", 
				noneSelected: "请选择",
				minWidth : 120
			});  
		});
	</script>
  </head>

<body>

<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	<form action="<common:WebRoot/>/nv/managerEdit.do" method="post" id="allProjectForm" name="allProjectForm" enctype="multipart/form-data" >
        	 
        	 	<input type="hidden" name="typeAU" id="typeAU" value="${type}" />
				<input type="hidden" name="managerId" id="managerId" value="" />
        	
                <div class="form-group">
                    <div class="label"><label for="readme">用户名<font color="red">*</font></label></div>
                    <div class="field">
                    	<input type="text"  class="input" size="50" name="username" id="username" data-validate="required:请填写用户名" />
                    	<form:errors path="manager.username" cssStyle="color:red"> </form:errors>
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="label"><label for="sitename">密码<font color="red">*</font></label></div>
                    <div class="field">
                    	<input class="input" size="50" type="password"" name="password" id="password"  data-validate="required:请填写密码"  />
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="label"><label for="siteurl">邮箱</label></div>
                    <div class="field">
                    	 <input type="text" name="email" class="input" size="50" data-validate="email:请填写正确的邮箱"/>
                    </div>
                </div>
              		   
              	<div class="form-group">
                    <div class="label"><label for="siteurl">中文名称</label></div>
                    <div class="field">
                    	 <input type="text" name="chineseName" class="input" size="50"/>
                    </div>
                </div>
                	   
              	 <div class="form-group">
                    <div class="label"><label for="siteurl">电话</label></div>
                    <div class="field">
                    	 <input type="text" name="phone" class="input" size="50" data-validate="tel:请填写正确的电话号码" />
                    </div>
                </div>	
              	<div class="form-group">
                    <div class="label"><label for="siteurl">性别</label>
                    	<input type="radio" value="1" name="sex" <c:if test="${manager.sex!=0}"> checked="checked"</c:if>>  男
						<input type="radio" value="0" name="sex" <c:if test="${manager.sex==0}"> checked="checked"</c:if>>  女
					</div>
                </div>
                <div class="form-group">
                	<div class="label"><label for="roleIds">角色</label>
                		<select id="sela" multiple="multiple" id="roleIds" name="roleIds">
	                    	<c:forEach items="${roles}" var="role">
	                    		<option value="${role.roleId}">${role.roleName}</option>
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
</body>
</html>