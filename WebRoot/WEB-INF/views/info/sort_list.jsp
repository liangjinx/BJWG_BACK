<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>资讯分类列表</title>
    
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
function infoEidt(type,id){
	var action = "";
	switch (type) {
	case 1://新增
		action = basePath + "/v/info/sort/editInit";
		break;
	case 2://修改
		action = basePath + "/v/info/sort/editInit";
		$("#id").val(id);
		break;
	case 3://单个删除
		action = basePath + "/v/info/sort/delete";
		if(confirm("是否确定删除记录？")){
			$("#id").val(id);
		}else{
			return ;
		}
		break;
	case 4://多个删除
		action = basePath + "/v/info/sort/delete";
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
        
        	<form action="<common:WebRoot/>/v/info/sort/list" method="post" id="allProjectForm" name="allProjectForm">
        	
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="id" name="id" value="">
        		
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>资讯分类列表</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            <strong style="padding-left: 100px;">
			            	<man:hasPermission name="info_sort_edit_init">
				            	<input type="button" class="button button-small border-green" value="新增" onclick="infoEidt(1)"/>
				            </man:hasPermission>
				            <man:hasPermission name="info_sort_delete">
				            	<input type="button" class="button button-small border-yellow" value="批量删除" onclick="infoEidt(4)"/>
				            </man:hasPermission>
			            </strong>
			        </div>
			        <table class="table table-hover">
			        	<tr>
				        	<th width="50">选择</th>
				        	<th>名称</th>
				        	<th>操作</th>
			        	</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="l">
				            <tr>
				            	<td><input type="checkbox" name="idList" id="idList" value="${l.dictDetailId}" />&nbsp;&nbsp;${l.dictDetailId}</td>
				            	<td>
				            		${l.name}
				            	</td>
				            	<td>
				            		<man:hasPermission name="info_sort_edit_init">
				            			<a class="button border-blue button-little" href="javascript:infoEidt(2,${l.dictDetailId});">编辑</a>
				            		</man:hasPermission>
				            		<man:hasPermission name="info_sort_delete">
				            			<a class="button border-yellow button-little" href="javascript:infoEidt(3,${l.dictDetailId});">删除</a>
				            		</man:hasPermission>
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
