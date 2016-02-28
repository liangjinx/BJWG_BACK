<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>用户猪肉券使用记录</title>
    
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
			
			var url = "<common:WebRoot/>/v/mycoupon/use/list?";
	    
	    	var coupon_code_sear = $("#coupon_code_sear").val();
	    	var username_sear = $("#username_sear").val();
	    	var use_type_sear = $("#use_type_sear").val();
	    	var currentPage = $("#currentPage").val();
	    	var pageCount = $("#pageCount").val();
	    
	    	url+="startTime="+valSt+"&endTime="+valEnd+"&filter_LIKES_t.coupon_code="+coupon_code_sear+"&filter_LIKES_u.username="+username_sear+"&filter_EQI_t.use_type="+use_type_sear;
	    	window.location.href = encodeURI(url);
		}
		/**
		 * 资源编辑
		 */
		function projectEidt(type,id){
			var action = "";
			switch (type) {
			case 1://新增
				action = basePath + "/v/mycoupon/use/editInit";
				break;
			case 2://修改
				action = basePath + "/v/mycoupon/use/editInit";
				$("#id").val(id);
				break;
			case 3://单个删除
				action = basePath + "/v/mycoupon/use/delete";
				if(confirm("是否确定删除记录？")){
					$("#id").val(id);
				}else{
					return ;
				}
				break;
			case 4://多个删除
				action = basePath + "/v/mycoupon/use/delete";
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
        
        	<form action="<common:WebRoot/>/v/mycoupon/use/list" method="post" id="allProjectForm" name="allProjectForm">
        	
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="id" name="id" value="">
        		
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>用户猪肉券使用记录</strong></div>
			        <div class="padding border-bottom">
			            <strong style="padding-left: 150px;">
				       		券的编号：<input type="text" name="filter_LIKES_t.coupon_code" id="coupon_code_sear" value="${param['filter_LIKES_t.coupon_code']}" size="25"/>&nbsp;
				       		用户名：<input type="text" name="filter_LIKES_u.username" id="username_sear" value="${param['filter_LIKES_u.username']}" size="25"/>&nbsp;
							&nbsp;类型：<select name="filter_EQI_t.use_type" id="use_type_sear" style="width: 140px;">
			            		<option value="">所有</option>
			            		<c:forEach items="${typelist}" var="v">
			            			<option value="${v.value}" <c:if test="${param['filter_EQI_t.use_type']== v.value}">selected="selected"</c:if>>${v.name}</option>
			            		</c:forEach>
							</select>
							&nbsp;使用日期 :从
							<input type="text" style="width: 120px;" value="${startTime}"
								name="startTime" id="startTime" onclick="javascript:selectDate();">
							到
							<input type="text" style="width: 120px;" value="${endTime}"
								name="endTime" id="endTime" onclick="javascript:selectDate();">
								
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
			            </strong>
			        </div>
			        <table class="table table-hover">
			        	<tr>
				        	<th>用户名称</th>
				        	<th>券编号</th>
				        	<th>券额</th>
				        	<th>使用日期</th>
				        	<th>使用金额</th>
				        	<th>剩余金额</th>
				        	<th>使用类型</th>
				        	<th>使用地址</th>
			        	</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="l">
				            <tr>
				            	<td>${l.username}</td>
				            	<td>${l.couponCode}</td>
				            	<td><fmt:formatNumber value="${l.couponMoney}" pattern="0.00"/></td>
				            	<td><fmt:formatDate value='${l.useTime }' pattern='yyyy-MM-dd HH:mm:ss' /></td>
				            	<td><fmt:formatNumber value="${l.useMoney}" pattern="0.00"/></td>
				            	<td><fmt:formatNumber value="${l.canUseMoney}" pattern="0.00"/></td>
				            	<td>
				            		<c:forEach items="${typelist}" var="v">
					            		<c:if test="${l.useType == v.value }">${v.name}</c:if>
					            	</c:forEach>
				            	</td>
				            	<td><div style="width:400px;display: block;word-wrap: break-word;">${l.useProvince}${l.useCity}${l.useAddress}</div></td>
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
