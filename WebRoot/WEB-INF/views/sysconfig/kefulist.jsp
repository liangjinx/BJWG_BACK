<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>在线客服设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
		/**
		 * 资源编辑
		 */
		function sysconfigEidt(type,id){
			var action = "";
			switch (type) {
			case 2://修改
				action = basePath + "/v/kefu/config/editInit";
				$("#id").val(id);
				break;
			default:
				break;
			}
			
			$("#allProjectForm").attr("action",action).submit();
			$("button[name=btn]").attr("disabled",true);
		}
	</script>
  </head>
<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	<form action="<common:WebRoot/>/v/kefu/config/list" method="post" id="allProjectForm" name="allProjectForm">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="id" name="id" value="">
        		
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>在线客服设置</strong></div>
			        <table class="table table-hover">
			        	<c:forEach items="${requestScope.list }" var="l">
				            <tr>
				            	<td>${l.name }</td>
				            	<td>${l.value }</td>
				            	<td>
				            		<a class="button border-blue button-little" href="#" onclick="sysconfigEidt(2,${l.id });return false;">编辑</a>
				            	</td>
				            </tr>
			        	</c:forEach>
			        </table>
			    </div>
			 </form>
        </div>
    </div>
</div>
</body>
</html>
