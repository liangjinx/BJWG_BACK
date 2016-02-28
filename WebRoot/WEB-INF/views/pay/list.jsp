<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>支付记录列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common.jsp" flush="true"></jsp:include>
	<link rel="stylesheet" href="<common:WebRoot/>/resources/jquery/jquery-ui-1.8.4.custom.css">
	<script src="<common:WebRoot/>/resources/jquery/jquery-ui-1.8.4.custom.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
		//删除左右两端的空格
		function trim(str){ 
			return str.replace(/(^\s*)|(\s*$)/g, "");
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
		        alert("创建时间,开始日期需在结束日期之前，请重新选择");
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
		var url = "<common:WebRoot/>/v/pay/list?";
	    
	    var trade_no = $.trim($("#trade_no_sear").val());
	    var order_code = $.trim($("#order_code_sear").val());
	    var buyer = $.trim($("#buyer_sear").val());
	    var seller = $.trim($("#seller_sear").val()); 
	    var pay_type = $.trim($("#pay_type_sear").val());
	    
	    url+="cStartTime="+cvalSt+"&cEndTime="+cvalEnd+"&pStartTime="+pvalSt+"&pEndTime="+pvalEnd+"&filter_EQS_t.trade_no="+trade_no+"&filter_EQS_t.order_code="+order_code+"&filter_LIKES_t.buyer="+buyer+"&filter_LIKES_t.seller="+seller+"&filter_EQS_t.pay_type="+pay_type;
	    window.location.href = encodeURI(url);
	}
	//日期选择框
	function selectDate() { 
		WdatePicker({dateFmt:'yyyy-MM-dd', isShowToday: false, isShowClear: false});
    }
    //查看
	function viewOrder(code){
		$("#orderCode").val(code);
		$("#allProjectForm").attr("action","<common:WebRoot/>/v/pay/view/order");
		$("#allProjectForm").submit();
	}
	</script>
  </head>
<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/pay/list" method="post" id="allProjectForm" name="allProjectForm">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="orderCode" name="orderCode" value=""/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>支付记录列表</strong></div>
			        <div class="padding border-bottom">
			            <!-- <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" /> -->
			            <strong style="padding-left: 100px;">
			            	交易号:<input type="text" style="width: 150px;" value="${param['filter_EQS_t.trade_no']}"
								name="filter_EQS_t.trade_no" id="trade_no_sear">
							&nbsp;商品订单号:<input type="text" style="width: 150px;" value="${param['filter_EQS_t.order_code']}"
								name="filter_EQS_t.order_code" id="order_code_sear">
							&nbsp;买家:<input type="text" style="width: 150px;" value="${param['filter_LIKES_t.buyer']}"
								name="filter_LIKES_t.buyer" id="buyer_sear">
							&nbsp;卖家:<input type="text" style="width: 150px;" value="${param['filter_LIKES_t.seller']}"
								name="filter_LIKES_t.seller" id="seller_sear">
				       		&nbsp;支付方式:<select name="filter_EQS_t.pay_type" id="pay_type_sear" style="width: 150px;">
								<option value="">--请选择--</option>
								<c:forEach var="per" items="${payTypes}" >
									<option value="${per.value}" <c:if test="${param['filter_EQS_t.pay_type']==per.value}">selected</c:if> >${per.name}</option>
								</c:forEach>
							</select>
							</strong>
							<br><br>
							<strong style="padding-left: 130px;">
				       		&nbsp;交易创建时间 :从
							<input type="text" style="width: 100px;" value="${cStartTime}"
								name="cStartTime" id="cStartTime" onclick="javascript:selectDate();">
							到
							<input type="text" style="width: 100px;" value="${cEndTime}"
								name="cEndTime" id="cEndTime" onclick="javascript:selectDate();">
							&nbsp;交易付款时间 :从
							<input type="text" style="width: 100px;" value="${pStartTime}"
								name="pStartTime" id="pStartTime" onclick="javascript:selectDate();">
							到
							<input type="text" style="width: 100px;" value="${pEndTime}"
								name="pEndTime" id="pEndTime" onclick="javascript:selectDate();">
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
			            </strong>
			        </div>
			        <table class="table table-hover">
			        	<tr>
                    		<th scope="col" style="width:5%">交易号 </th>
							<th>商户订单号</th>
							<th>买家</th>
							<th>卖家</th>
							<th>交易创建时间</th>
							<th>交易付款时间</th>
							<th>交易金额</th>
							<th>支付方式</th>
 						</tr>
			        	<c:forEach items="${requestScope.list}" var="u">
				            <tr>
				            	<!--<td><input type="checkbox" name="idList" id="idList" value="${u.tradeNo}" />&nbsp;&nbsp;${u.tradeNo}</td> -->
				            	<td>${u.tradeNo}</td>
				            	<td><a href="javascript:viewOrder('${u.orderCode}')" class="text-sub">${u.orderCode}</a></td>
								<td>${u.buyer}</td>
								<td>${u.seller}</td>
								<td><fmt:formatDate value='${u.gmtCreateTime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
								<td><fmt:formatDate value='${u.gmtPayTime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
								<td><fmt:formatNumber value="${u.totalMoney}" pattern="0.00"/></td>
								<td>
									<c:forEach items="${payTypes}" var="s">
										<c:if test="${u.payType==s.value}">${s.name}</c:if>
									</c:forEach>
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