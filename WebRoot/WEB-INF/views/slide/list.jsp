<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>首页轮播图列表</title>
    
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
			var url = "<common:WebRoot/>/v/slide/list?";
	    
	    	var name_sear = $("#name_sear").val();
	    	var code_sear = $("#code_sear").val();
	    
	    	url+="filter_LIKES_t.name="+name_sear;
	    	window.location.href = encodeURI(url);
		}
/**
 * 资源编辑
 */
function slideEidt(type,id){
	var action = "";
	switch (type) {
	case 1://新增
		action = basePath + "/v/slide/editInit";
		break;
	case 2://修改
		action = basePath + "/v/slide/editInit";
		$("#id").val(id);
		break;
	case 3://单个删除
		action = basePath + "/v/slide/delete";
		if(confirm("是否确定删除记录？")){
			$("#id").val(id);
		}else{
			return ;
		}
		break;
	case 4://多个删除
		action = basePath + "/v/slide/delete";
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
        
        	<form action="<common:WebRoot/>/v/slide/list" method="post" id="allProjectForm" name="allProjectForm">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="id" name="id" value="">
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>首页轮播图列表</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            <strong style="padding-left: 100px;">
				       		名称：<input type="text" name="filter_LIKES_t.name" id="name_sear" value="${param['filter_LIKES_t.name']}" size="15"/>&nbsp;
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				            <input type="button" class="button button-small border-green" value="新增" onclick="slideEidt(1)"/>
				            <input type="button" class="button button-small border-yellow" value="批量删除" onclick="slideEidt(4)"/>
			            </strong>
			        	</div>
			        <table class="table table-hover">
			        	<tr>
				        	<th width="50">选择</th>
				        	<th>图片名称</th>
				        	<th>图片链接</th>
				        	<th>排序</th>
				        	<th>操作</th>
			        	</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="l">
				            <tr>
				            	<td><input type="checkbox" name="idList" id="idList" value="${l.slideId }" /></td>
				            	<td>
									<div style="float:left;margin-right:5px;"><img class="w-img" id="w-img1" src="<c:if test="${l.path!='' && l.path!='null' && l.path!=null && !fn:containsIgnoreCase(l.path,'http://')}">${domain}</c:if>${l.path}" width="50px" height="50px"></div>
									<div style="width:400px;display: block;word-wrap: break-word;">${l.name}</div>
								</td>
				            	<td>${l.link}</td>
				            	<td>${l.sort }</td>
				            	<td>
				            		<a class="button border-blue button-little" href="#" onclick="slideEidt(2,${l.slideId });return false;">编辑</a>
				            		<a class="button border-yellow button-little" href="#" onclick="slideEidt(3,${l.slideId });return false;">删除</a>
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
