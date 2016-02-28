<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>抢购项目列表</title>
    
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
			
			var valSt = $("#startTime").val();
			var valEnd = $("#endTime").val();
			if(valSt!="" && valEnd!=""){
		    	var date1 = new Date(valSt.replace(/\-/g, "\/"));// /g 全文搜索,/i 忽略大小写,也可以这样写： /gi
				var date2 = new Date(valEnd.replace(/\-/g, "\/"));
		    	var a = (date2 - date1)/(1000*60);
			    if (a < 0) {
			        alert("开始日期需在结束日期之前，请重新选择");
			        return;
			    }
		    }
			
			var url = "<common:WebRoot/>/v/project/list?";
	    
	    	var name_sear = $("#name_sear").val();
	    	var type_sear = $("#type_sear").val();
	    
	    	url+="startTime="+valSt+"&endTime="+valEnd+"&filter_LIKES_t.name="+name_sear+"&filter_EQI_t.type="+type_sear;
	    	window.location.href = encodeURI(url);
		}
		/**
		 * 资源编辑
		 */
		function projectEidt(type,id){
			var action = "";
			switch (type) {
			case 1://新增
				action = basePath + "/v/project/editInit";
				break;
			case 2://修改
				action = basePath + "/v/project/editInit";
				$("#id").val(id);
				break;
			case 3://单个删除
				action = basePath + "/v/project/delete";
				if(confirm("是否确定删除记录？")){
					$("#id").val(id);
				}else{
					return ;
				}
				break;
			case 4://多个删除
				action = basePath + "/v/project/delete";
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
		//时间选择
		function selectDate() { 
			WdatePicker({dateFmt:'yyyy-MM-dd', isShowToday: false, isShowClear: false});
	    } 
	</script>
  </head>

<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/project/list" method="post" id="allProjectForm" name="allProjectForm">
        	
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="id" name="id" value="">
        		
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>抢购项目列表</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            <strong style="padding-left: 100px;">
				       		名称：<input type="text" name="filter_LIKES_t.name" id="name_sear" value="${param['filter_LIKES_t.name']}" size="15"/>&nbsp;
				       		&nbsp;类型：<select name="filter_EQI_t.type" id="type_sear" style="width: 140px;">
			            		<option value="">所有</option>
			            		<c:forEach items="${typelist}" var="v">
			            			<option value="${v.value}" <c:if test="${param['filter_EQI_t.type']== v.value}">selected="selected"</c:if>>${v.name}</option>
			            		</c:forEach>
							</select>
							&nbsp;抢购时间 :从
							<input type="text" style="width: 100px;" value="${startTime}"
								name="startTime" id="startTime" onclick="javascript:selectDate();">
							到
							<input type="text" style="width: 100px;" value="${endTime}"
								name="endTime" id="endTime" onclick="javascript:selectDate();">
								
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				            <man:hasPermission name="project_delete">
				            	<input type="button" class="button button-small border-yellow" value="批量删除" onclick="projectEidt(4)"/>
				            </man:hasPermission>
			            </strong>
			            <!--<input type="button" class="button button-small border-blue" value="回收站" />
			        --></div>
			        <table class="table table-hover">
			        	<tr>
				        	<th width="50">选择</th>
				        	<th>周期</th>
				        	<th>开始抢购时间</th>
				        	<th>结束抢购时间</th>
				        	<th>猪仔总数量</th>
				        	<th>猪仔剩余数量</th>
				        	<th>单价</th>
				        	<th>状态</th>
				        	<th>操作</th>
			        	</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="l">
				            <tr>
				            	<td><input type="checkbox" name="idList" id="idList" value="${l.paincbuyProjectId}" />&nbsp;${l.paincbuyProjectId}</td>
				            	<td>
				            		${l.name}
				            	</td>
				            	<td><fmt:formatDate value='${l.beginTime }' pattern='yyyy-MM-dd HH:mm:ss' /></td>
				            	<td><fmt:formatDate value='${l.endTime }' pattern='yyyy-MM-dd HH:mm:ss' /></td>
				            	<td>${l.num}</td>
				            	<td>${l.leftNum}</td>
				            	<td><fmt:formatNumber value="${l.price}" pattern="0.00"/></td>
				            	<td>
					            	<c:forEach items="${statuslist}" var="v">
					            		<c:if test="${l.status == v.value }">${v.name}</c:if>
					            	</c:forEach>
				            	</td>
				            	<td>
				            		<man:hasPermission name="project_edit_init">
				            			<a class="button border-blue button-little" href="javascript:projectEidt(2,${l.paincbuyProjectId});">编辑</a>
				            		</man:hasPermission>
				            		<man:hasPermission name="project_delete">
				            			<a class="button border-yellow button-little" href="javascript:projectEidt(3,${l.paincbuyProjectId});">删除</a>
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
