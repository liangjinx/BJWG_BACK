<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>系统配置列表</title>
    
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
		//搜索
		function search(){
			var url = "<common:WebRoot/>/v/config/list?";
	    
	    	var name_sear = $("#name_sear").val();
	    	var code_sear = $("#code_sear").val();
	    	var parent_code_sear = $("#parent_code_sear").val();
	    
	    	url+="filter_LIKES_s.name="+name_sear+"&filter_LIKES_s.code="+code_sear+"&filter_LIKES_s.parent_code="+parent_code_sear;
	    	window.location.href = encodeURI(url);
		}
		/**
 * 资源编辑
 */
function sysconfigEidt(type,id){
	var action = "";
	switch (type) {
	case 1://新增
		action = basePath + "/v/config/editInit";
		break;
	case 2://修改
		action = basePath + "/v/config/editInit";
		$("#id").val(id);
		break;
	case 3://单个删除
		action = basePath + "/v/config/delete";
		if(confirm("是否确定删除记录？")){
			$("#id").val(id);
		}else{
			return ;
		}
		break;
	case 4://多个删除
		action = basePath + "/v/config/delete";
		var idList = $(":input[name=idList]:checked");
		if(idList.length < 1){
			alert("请选择要删除的记录");
			return ;
		}else{
			if(!confirm("是否确定批量删除记录？")){
				return;
			}
		}
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
        
        	<form action="<common:WebRoot/>/v/config/list" method="post" id="allProjectForm" name="allProjectForm">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="id" name="id" value="">
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>系统配置列表</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            <strong style="padding-left: 100px;">
				       		名称：<input type="text" name="filter_LIKES_s.name" id="name_sear" value="${param['filter_LIKES_s.name']}" size="40"/>&nbsp;
				       		&nbsp;编号：<input type="text" name="filter_LIKES_s.code" id="code_sear" value="${param['filter_LIKES_s.code']}" size="40"/>&nbsp;
				       		&nbsp;父级编号：
				       		<input type="text" name="filter_LIKES_s.parent_code" id="parent_code_sear" value="${param['filter_LIKES_s.parent_code']}" size="40"/>&nbsp;
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				            <input type="button" class="button button-small border-green" value="新增配置项" onclick="sysconfigEidt(1)"/>
				            <input type="button" class="button button-small border-yellow" value="批量删除" onclick="sysconfigEidt(4)"/>
			            </strong>
			            <!--<input type="button" class="button button-small border-blue" value="回收站" />
			        --></div>
			        <table class="table table-hover">
			        	<tr>
				        	<th width="50">选择</th>
				        	<th>名称</th>
				        	<th>编号</th>
				        	<th>父级名称</th>
				        	<th>父级编号</th>
				        	<th>值</th>
				        	<th>操作</th>
			        	</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="l">
				            <tr>
				            	<td><input type="checkbox" name="idList" id="idList" value="${l.id }" /></td>
				            	<td>${l.name }</td>
				            	<td>${l.code }</td>
				            	<td>${l.parentName }</td>
				            	<td>${l.parentCode }</td>
				            	<td>${l.value }</td>
				            	<td>
				            		<a class="button border-blue button-little" href="#" onclick="sysconfigEidt(2,${l.id });return false;">编辑</a>
				            		<a class="button border-yellow button-little" href="#" onclick="sysconfigEidt(3,${l.id });return false;">删除</a>
				            	</td>
				            </tr>
			        	</c:forEach>
			        	
			        </table>
			        
			        <jsp:include page="/WEB-INF/views/page.jsp" flush="true"></jsp:include>
			        
			    </div>
			 </form>
        </div>
    </div>
</div>

</body>
  
</html>
