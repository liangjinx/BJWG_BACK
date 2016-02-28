<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<input type="hidden" id="webRoot" value="<common:WebRoot/>" />
    <base href="<common:WebRoot/>">
    <title>管理员登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="八戒,登录">
	<meta http-equiv="description" content="八戒后台管理登录页面">
	<link rel="stylesheet" href="<common:WebRoot/>/resources/modular/css/pintuer.css">
	<link rel="stylesheet" href="<common:WebRoot/>/resources/modular/css/admin.css">
	<script src="<common:WebRoot/>/resources/modular/js/jquery.js"></script>
	<script src="<common:WebRoot/>/resources/modular/js/login.js"></script>
	<script src="<common:WebRoot/>/resources/modular/js/md5.js"></script>
	<link type="image/x-icon" href="/favicon.ico" rel="shortcut icon" />
	<link href="/favicon.ico" rel="bookmark icon" />
	<jsp:include page="head/head.jsp"></jsp:include>
	<script type="text/javascript">
		
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
		
	</script>
 </head>

<body>
<div class="container">
    <div class="line">
        <div class="xs6 xm4 xs3-move xm4-move">
            <br /><br />
            <div class="media media-y">
            </div>
            <br /><br />
            <form action="<common:WebRoot/>/nv/login.do" method="post" id="allProjectForm" onsubmit="return login();">
            <div class="panel">
                <div class="panel-head"><strong>八戒后台管理系统</strong></div>
                <div class="panel-body" style="padding:30px;">
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="text" class="input" id="username" name="username" maxlength="30" placeholder="登录账号"/>
                            <span class="icon icon-user"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="field field-icon-right">
                            <input type="password" class="input" id="password" name="password" maxlength="35" placeholder="登录密码"/>
                            <span class="icon icon-key"></span>
                        </div>
                    </div>
                </div>
                <div class="panel-foot text-center"><button type="submit" class="button button-block bg-main text-big">立即登录后台</button></div>
            </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
