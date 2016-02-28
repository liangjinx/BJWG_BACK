<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>订单列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<common:WebRoot/>/resources/css/menu-page.css">
	<jsp:include page="../common.jsp" flush="true"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
	//时间选择控件
	function selectDate() { 
		WdatePicker({dateFmt:'yyyy-MM-dd', isShowToday: false, isShowClear: false});
    } 
	/**
	*搜索 
	*/
	function search(){
		
		var cvalSt = $("#cStartTime").val();
		var cvalEnd = $("#cEndTime").val();
		if(cvalSt!="" && cvalEnd!=""){
	    	var date1 = new Date(cvalSt.replace(/\-/g, "\/"));// /g 全文搜索,/i 忽略大小写,也可以这样写： /gi
			var date2 = new Date(cvalEnd.replace(/\-/g, "\/"));
	    	var a = (date2 - date1)/(1000*60);
		    if (a < 0) {
		        alert("下单时间,开始日期需在结束日期之前，请重新选择");
		        return;
		    }
	    }
	    var pvalSt = $("#pStartTime").val();
		var pvalEnd = $("#pEndTime").val();
		if(pvalSt!="" && pvalEnd!=""){
	    	var date3 = new Date(pvalSt.replace(/\-/g, "\/"));// /g 全文搜索,/i 忽略大小写,也可以这样写： /gi
			var date4 = new Date(pvalEnd.replace(/\-/g, "\/"));
	    	var a = (date4 - date3)/(1000*60);
		    if (a < 0) {
		        alert("付款时间,开始日期需在结束日期之前，请重新选择");
		        return;
		    }
	    }
	
		var url = "<common:WebRoot/>/v/order/list?";
	    
	    var orderId = $("#orderIdSear").val();
	    var order_code = $("#order_code_sear").val();
	    var link_man_sear = $("#link_man_sear").val();
	    var order_status_sear = $("#order_status_sear").val();
	    var order_type_sear = $("#order_type_sear").val();
	    var pay_type_sear = $("#pay_type_sear").val();

	    url+="cStartTime="+cvalSt+"&cEndTime="+cvalEnd+"&pStartTime="+pvalSt+"&pEndTime="+pvalEnd+"&filter_EQI_t.ORDER_ID="+orderId+"&filter_EQS_t.ORDER_CODE="+order_code+"&filter_LIKES_u.USERNAME="+link_man_sear+"&filter_EQS_t.STATUS="+order_status_sear+"&filter_EQS_t.TYPE="+order_type_sear+"&filter_EQS_t.PAY_TYPE="+pay_type_sear;
	    window.location.href = encodeURI(url);
	}
	//订单导出
    function exportData(){
    
     	var cvalSt = $("#cStartTime").val();
		var cvalEnd = $("#cEndTime").val();
		if(cvalSt!="" && cvalEnd!=""){
	    	var date1 = new Date(cvalSt.replace(/\-/g, "\/"));// /g 全文搜索,/i 忽略大小写,也可以这样写： /gi
			var date2 = new Date(cvalEnd.replace(/\-/g, "\/"));
	    	var a = (date2 - date1)/(1000*60);
		    if (a < 0) {
		        alert("下单时间,开始日期需在结束日期之前，请重新选择");
		        return;
		    }
	    }
	    var pvalSt = $("#pStartTime").val();
		var pvalEnd = $("#pEndTime").val();
		if(pvalSt!="" && pvalEnd!=""){
	    	var date3 = new Date(pvalSt.replace(/\-/g, "\/"));// /g 全文搜索,/i 忽略大小写,也可以这样写： /gi
			var date4 = new Date(pvalEnd.replace(/\-/g, "\/"));
	    	var a = (date4 - date3)/(1000*60);
		    if (a < 0) {
		        alert("付款时间,开始日期需在结束日期之前，请重新选择");
		        return;
		    }
	    }
	    var orderId = $("#orderIdSear").val();
	    var order_code = $("#order_code_sear").val();
	    var link_man_sear = $("#link_man_sear").val();
	    var order_status_sear = $("#order_status_sear").val();
	    var order_type_sear = $("#order_type_sear").val();
	    var pay_type_sear = $("#pay_type_sear").val();
	    
		$("button[name=btn]").attr("disabled",true);
		$.ajax({
			type: "POST",
			url: "<common:WebRoot/>/v/order/checkExport",
			dataType: "text",
			data:{"filter_EQI_t.ORDER_ID":orderId,"filter_EQS_t.ORDER_CODE":order_code,"filter_LIKES_u.USERNAME":link_man_sear,"filter_EQS_t.STATUS":order_status_sear,"filter_EQS_t.TYPE":order_type_sear,"filter_EQS_t.PAY_TYPE":pay_type_sear,"cStartTime":cvalSt,"cEndTime":cvalEnd,"pStartTime":pvalSt,"pEndTime":pvalEnd},
			success: function(data){
				if(data!="" && data!="操作成功" && data!=null && data.toString()!="" && data.charAt(data.length - 1)== "!"){
					alert(data);
					return; 
				}else{
					$("#allProjectForm").attr("action","<common:WebRoot/>/v/order/export");
	    			$("#allProjectForm").submit();
	    			$("#allProjectForm").attr("action","<common:WebRoot/>/v/order/list");
				}
			}
		});
	}
	
		//确认支付
	function confirmPay(id,id2){
		$("#orderId").val(id);
		$("#user_id_sear").val(id2);
		var t=confirm("您确定订单"+id+"已支付吗?");
		if(t){
		
		$("#allProjectForm").attr("action","<common:WebRoot/>/v/order/confirmPay");
		$("#allProjectForm").submit();
		}
		
		return;
	}
	
	
	
	//查看
	function view(id){
		$("#orderId").val(id);
		$("#allProjectForm").attr("action","<common:WebRoot/>/v/order/view");
		$("#allProjectForm").submit();
	}
	//确认收货
	function confirmShou(id){
		$("#orderId").val(id);
		
		
		
		$("#allProjectForm").attr("action","<common:WebRoot/>/v/order/confirm");
		$("#allProjectForm").submit();
	}
	//表头查询
    function headSearch(type){
    	var url = "<common:WebRoot/>/v/order/list?";
    	if(type==2){
    		url += "filter_LEI_t.STATUS=2&filter_GEI_t.STATUS=1";
    	}else if(type==3){
    		url += "filter_EQS_t.STATUS=4";
    	}
    	window.location.href = encodeURI(url);
    }
	</script>
  </head>
<body>
<div class="admin">
      <div class="tab-body">
        <div class="tab-panel active" id="tab-set">
        	<form action="<common:WebRoot/>/v/order/list" method="post" id="allProjectForm" name="allProjectForm" class="form-x">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="orderId" name="orderId" value=""/>
        		<input type="hidden" id="user_id_sear" name="filter_EQI_t.USER_ID" value="${param['filter_EQI_t.USER_ID']}"/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong><c:if test="${username!=null && username!=''}">【<a href="<common:WebRoot/>/v/user/view?userId=${param['filter_EQI_t.USER_ID']}" class="text-sub">${username}</a>】的</c:if>订单列表</strong></div>
			        <div class="padding border-bottom">
			           	<table class="table">
							<tbody>
 							<tr>
 								<td>
									<label>ID：</label><input type="text" style="width: 150px;" value="${param['filter_EQI_t.ORDER_ID']}" name="filter_EQI_t.ORDER_ID" id="orderIdSear">
								</td>
 								<td>
									<label>订单编号：</label><input type="text" style="width: 150px;" value="${param['filter_EQS_t.ORDER_CODE']}" name="filter_EQS_t.ORDER_CODE" id="order_code_sear">
								</td>
								<td>
									<label>联系人：</label><input type="text" style="width: 150px;" value="${param['filter_LIKES_u.USERNAME']}" name="filter_LIKES_u.USERNAME" id="link_man_sear">
								</td>
							</tr>
 							<tr>
 								<td>
 									<label>订单状态：</label>
									<select name="filter_EQS_t.STATUS" id="order_status_sear" style="width:180px;">
										<option value="">所有</option>
										<c:forEach var="per" items="${statuslist}" >
											<option value="${per.value}" <c:if test="${param['filter_EQS_t.STATUS']==per.value}">selected</c:if> >${per.name}</option>
										</c:forEach>
									</select>
								</td>
								<td>
									<label>订单类型：</label>
									<select name="filter_EQS_t.TYPE" id="order_type_sear" style="width:180px;">
										<option value="">所有</option>
										<c:forEach var="per" items="${typelist}" >
											<option value="${per.value}" <c:if test="${param['filter_EQS_t.TYPE']==per.value}">selected</c:if> >${per.name}</option>
										</c:forEach>
									</select>
 								</td>
 								<td>
									<label>支付方式：</label>
									<select name="filter_EQS_t.PAY_TYPE" id="pay_type_sear" style="width:180px;">
										<option value="">所有</option>
										<c:forEach var="per" items="${payTypes}" >
											<option value="${per.value}" <c:if test="${param['filter_EQS_t.PAY_TYPE']==per.value}">selected</c:if> >${per.name}</option>
										</c:forEach>
									</select>
								</td>
 							</tr>
 							<tr>
 								<td>
									<label>下单时间 :从</label><input type="text" style="width: 150px;" value="${cStartTime}" name="cStartTime" id="cStartTime" onclick="javascript:selectDate();">
									到
									<input type="text" style="width: 150px;" value="${cEndTime}" name="cEndTime" id="cEndTime" onclick="javascript:selectDate();">
								</td>
								<td>
									付款时间 :从
									<input type="text" style="width: 150px;" value="${pStartTime}" name="pStartTime" id="pStartTime" onclick="javascript:selectDate();">
									到
									<input type="text" style="width: 150px;" value="${pEndTime}" name="pEndTime" id="pEndTime" onclick="javascript:selectDate();">
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
							 	<td>&nbsp;</td>
							 	<td> 
									<span>
										<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
										<man:hasPermission name="order_export">
											<input type="button" class="button button-small border-blue" value="导出" onclick="exportData();"/>&nbsp;
										</man:hasPermission>
							 		</span>
							 	</td>
							 	<td>&nbsp;</td>
							</tr> 
 						</tbody>
					</table>
			        </div>
			        <div class="panel-head" style="padding:0px 0px;">
			    		<ul class="tabs">  
	       					<li <c:if test="${(param['filter_EQS_t.STATUS']=='' || param['filter_EQS_t.STATUS']== null) && (param['filter_LEI_t.STATUS']!='2' && param['filter_GEI_t.STATUS']!='1')}">class="active"</c:if>><a href="javascript:headSearch(1);">全部</a></li>  
	       					<li <c:if test="${param['filter_LEI_t.STATUS']=='2' && param['filter_GEI_t.STATUS']=='1'}">class="active"</c:if>><a href="javascript:headSearch(2);">待付款</a></li>
	       					<li <c:if test="${param['filter_EQS_t.STATUS']=='4'}">class="active"</c:if>><a href="javascript:headSearch(3);">待收货</a></li>
	   					</ul>
			    	</div>
			        <table class="table table-hover">
			        	<tr>
                    		<th scope="col">ID</th>
							<th>订单编号</th>
							<th>下单时间</th>
							<th>付款时间</th>
							<th>联系人</th>
							<th>订单总金额</th>
							<th>数量</th>
							<th>订单状态</th>
							<th>项目周期</th>
							<th>订单类型</th>
							<th>支付方式</th>
							<man:hasPermission name="order_view">
								<th>操作</th>
							</man:hasPermission>
 						</tr>
			        	<c:forEach items="${requestScope.list}" var="u">
				            <tr>
				            	<td>&nbsp;&nbsp;${u.orderId}</td>
								<td>${u.orderCode}</td>
								<td><fmt:formatDate value='${u.ctime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
								<td><fmt:formatDate value='${u.payTime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
								<td>${u.username}</td>
								<td><fmt:formatNumber value="${u.totalMoney}" pattern="0.00"/></td>
								<td>${u.num}</td>
								<td>
									<c:forEach items="${statuslist}" var="s">
										<c:if test="${u.status==s.value}">${s.name}</c:if>
									</c:forEach>
								</td>
								<td>${u.relationName}</td>
								<td>
									<c:forEach items="${typelist}" var="s">
										<c:if test="${u.type==s.value}">${s.name}</c:if>
									</c:forEach>
								</td>
								<%-- <td>
								${u.flag}
								<!--<c:if test="${u.flag eq 2}">-->
								<a href="javascript:void(0)">确认支付</a>
								
							
								
								
								</td> --%>
								
								<td>
								<!--<c:if test="${u.flag eq 2}"></c:if>-->
								<c:if test="${u.status lt 3}">
								<a href="javascript:confirmPay('${u.orderId}','${u.userId}')" class="button border-blue button-little">确认支付</a>
								</c:if>
								
									<c:forEach items="${payTypes}" var="s">
										<c:if test="${u.payType==s.value}">${s.name}</c:if>
									</c:forEach>
								</td>
								<td>
									<man:hasPermission name="order_view">
										<a href="javascript:view('${u.orderId}')" class="button border-blue button-little" >查看</a>
									</man:hasPermission>
									<c:if test="${u.status == ORDER_STATUS_4 && u.type == ORDER_TYPE_3}">
										<man:hasPermission name="order_confirm">
											<a href="javascript:confirm('${u.orderId}')" class="button border-blue button-little" >确认收货</a>
										</man:hasPermission>
									</c:if>
								</td>
				            </tr>
			        	</c:forEach>
			        </table>
			        <jsp:include page="../page.jsp" flush="true"></jsp:include>
			    </div>
			 </form>
        </div>
    </div>
</div>
</body>
</html>