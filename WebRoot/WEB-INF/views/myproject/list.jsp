<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>用户收益列表</title>
    
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
			
			var url = "<common:WebRoot/>/v/myproject/list?";
	    
	    	var project_name_sear = $("#project_name_sear").val();
	    	var deal_type_sear = $("#deal_type_sear").val();
	    	var deal_status_sear = $("#deal_status_sear").val();
	    
	    	url+="startTime="+valSt+"&endTime="+valEnd+"&filter_EQS_t.deal_status="+deal_status_sear+"&filter_LIKES_t.paincbuy_project_name="+project_name_sear+"&filter_EQI_t.deal_type="+deal_type_sear;
	    	window.location.href = encodeURI(url);
		}
		//导出
		function exportData(){
			
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
		    
		    var project_name_sear = $("#project_name_sear").val();
	    	var deal_type_sear = $("#deal_type_sear").val();
	    	var deal_status_sear = $("#deal_status_sear").val();
		    
	    	$("button[name=btn]").attr("disabled",true);
			$.ajax({
				type: "POST",
				url: "<common:WebRoot/>/v/myproject/checkExport",
				dataType: "text",
				data:{"filter_LIKES_t.paincbuy_project_name":project_name_sear,"filter_EQI_t.deal_type":deal_type_sear,"filter_EQS_t.deal_status":deal_status_sear,"startTime":valSt,"endTime":valEnd},
				success: function(data){
					if(data!="" && data!="操作成功" && data!=null && data.toString()!="" && data.charAt(data.length - 1)== "!"){
						alert(data);
						return; 
					}else{
						$("#allProjectForm").attr("action","<common:WebRoot/>/v/myproject/export");
		    			$("#allProjectForm").submit();
		    			$("#allProjectForm").attr("action","<common:WebRoot/>/v/myproject/list");
					}
				},
				error:function(){
					alert('Error');
				}
			});
		}
		/**
		 * 资源编辑
		 */
		function projectEidt(type,id){
			var action = "";
			switch (type) {
			case 1://新增
				action = basePath + "/v/myproject/editInit";
				break;
			case 2://修改
				action = basePath + "/v/myproject/editInit";
				$("#id").val(id);
				break;
			case 3://单个删除
				action = basePath + "/v/myproject/delete";
				if(confirm("是否确定删除记录？")){
					$("#id").val(id);
				}else{
					return ;
				}
				break;
			case 4://多个删除
				action = basePath + "/v/myproject/delete";
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
        
        	<form action="<common:WebRoot/>/v/myproject/list" method="post" id="allProjectForm" name="allProjectForm">
        	
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="id" name="id" value="">
        		
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>用户收益列表</strong></div>
			        <div class="padding border-bottom">
			            <strong style="padding-left: 100px;">
				       		项目名称：<input type="text" name="filter_LIKES_t.paincbuy_project_name" id="project_name_sear" value="${param['filter_LIKES_t.paincbuy_project_name']}" size="15"/>
				       		&nbsp;处理状态：<select name="filter_EQS_t.deal_status" id="deal_status_sear" style="width: 140px;">
			            		<option value="">所有</option>
			            		<option value="0" <c:if test="${param['filter_EQS_t.deal_status']== '0'}">selected="selected"</c:if>>未处理</option>
			            		<option value="1" <c:if test="${param['filter_EQS_t.deal_status']== '1'}">selected="selected"</c:if>>处理完成</option>
							</select>
				       		&nbsp;处理方式：<select name="filter_EQI_t.deal_type" id="deal_type_sear" style="width: 140px;">
			            		<option value="">所有</option>
			            		<c:forEach items="${typelist}" var="v">
			            			<option value="${v.value}" <c:if test="${param['filter_EQI_t.deal_type']== v.value}">selected="selected"</c:if>>${v.name}</option>
			            		</c:forEach>
							</select>
							&nbsp;&nbsp;处理时间 :从
							<input type="text" style="width: 100px;" value="${startTime}"
								name="startTime" id="startTime" onclick="javascript:selectDate();">
							到
							<input type="text" style="width: 100px;" value="${endTime}"
								name="endTime" id="endTime" onclick="javascript:selectDate();">
								
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				       		<man:hasPermission name="myproject_export">
								<input type="button" class="button button-small border-blue" value="导出" onclick="exportData();"/>&nbsp;
							</man:hasPermission>
				       		<!-- 
				            <man:hasPermission name="myproject_delete">
				            	<input type="button" class="button button-small border-yellow" value="批量删除" onclick="projectEidt(4)"/>
				            </man:hasPermission>
				             -->
			            </strong>
			            <!--<input type="button" class="button button-small border-blue" value="回收站" />
			        --></div>
			        <table class="table table-hover">
			        	<tr>
				        	<th>用户名称</th>
				        	<th>项目名称</th>
				        	<th>数量</th>
				        	<th>年化率</th>
				        	<th>预期收益</th>
				        	<th>成本</th>
				        	<th>开始累计时间</th>
				        	<th>结束累计时间</th>
				        	<th>处理状态</th>
				        	<th>处理时间</th>
				        	<th>处理方式</th>
			        	</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="l">
				            <tr>
				            	<td>${l.username}</td>
				            	<td>${l.paincbuyProjectName}</td>
				            	<td>
				            	<c:choose>
				            		<c:when test="${l.num > l.presentNum}">${l.num-l.presentNum}</c:when>
				            		<c:otherwise>0</c:otherwise>
				            	</c:choose>
				            	</td>
				            	<td><fmt:formatNumber value="${l.rate}" pattern="0.00"/>%</td>
				            	<td><fmt:formatNumber value="${l.expectEarning}" pattern="0.00"/></td>
				            	<td><fmt:formatNumber value="${l.money}" pattern="0.00"/></td>
				            	<td><fmt:formatDate value='${l.beginTime }' pattern='yyyy-MM-dd' /></td>
				            	<td><fmt:formatDate value='${l.endTime }' pattern='yyyy-MM-dd' /></td>
				            	<td>
					            	<c:if test="${l.dealStatus == 0 }">未处理</c:if>
					            	<c:if test="${l.dealStatus == 1 }">处理完成</c:if>
				            	</td>
				            	<td><fmt:formatDate value='${l.dealTime }' pattern='yyyy-MM-dd' /></td>
				            	<td>
				            		<c:forEach items="${typelist}" var="v">
					            		<c:if test="${l.dealType == v.value }">${v.name}</c:if>
					            	</c:forEach>
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
