<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<script language="javascript" type="text/javascript" src="<common:WebRoot/>/resources/My97DatePicker/WdatePicker.js"></script>
	<!-- 模块 -->
	<link rel="stylesheet" href="<common:WebRoot/>/resources/modular/css/pintuer.css">
	<link rel="stylesheet" href="<common:WebRoot/>/resources/modular/css/admin.css">
	<script src="<common:WebRoot/>/resources/uploadify/jquery.min.js"></script>
	<script src="<common:WebRoot/>/resources/modular/js/pintuer.js"></script>
	<script src="<common:WebRoot/>/resources/modular/js/json.js"></script>
	<script src="<common:WebRoot/>/resources/modular/js/respond.js"></script>
	<script src="<common:WebRoot/>/resources/modular/js/admin.js"></script>
	<script src="<common:WebRoot/>/resources/system/norepeat.js"></script>
	<link type="image/x-icon" href="/favicon.ico" rel="shortcut icon" />
	<link href="/favicon.ico" rel="bookmark icon" />
</head>

<body>
  
<div class="lefter">
<!--    <div class="logo"><a href="#" target="_blank"><img src="<common:WebRoot/>/resources/modular/images/logo-brand.png" alt="后台管理系统" /></a></div>-->
</div>
 </body>
</html>
