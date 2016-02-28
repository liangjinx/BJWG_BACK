<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>用户列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common.jsp" flush="true"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
		//搜索
		function search(){
			var url = "<common:WebRoot/>/v/bank/list?";
	    
	    	var card_no = $("#card_no").val();
	    	var account_name = $("#account_name").val();
	    	var cardId = $("#cardIdSear").val();
	    
	    	url+="&filter_LIKES_bc.CARD_NO="+card_no+"&filter_LIKES_bc.ACCOUNT_NAME="+account_name+"&filter_EQI_bc.CARD_ID="+cardId;
	    	window.location.href = encodeURI(url);
		}
		//查看该用户的订单
		function view_order(id){
			var url = "<common:WebRoot/>/v/bank/list";
	    	$("#order_user_id").val(id);
	    	$("#viewForm").attr("action",url).submit();
			disabledBtn();
		}
	</script>
  </head>

<body>

<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/bank/list" method="post" id="allProjectForm" name="allProjectForm">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="cardId" name="cardId" value=""/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>用户列表</strong></div>
			        <div class="padding border-bottom">
			            <strong style="padding-left: 100px;">
			            	卡号ID：<input type="text" name="filter_EQI_bc.CARD_ID" id="cardIdSear" value="${param['filter_EQI_bc.CARD_ID']}" size="15" maxlength="15" />
			            	&nbsp;卡号:<input type="text" style="width: 150px;" value="${param['filter_LIKES_bc.CARD_NO']}"
								name="filter_LIKES_bc.CARD_NO" id="card_no">
				       		&nbsp;持卡人：<input type="text" name="filter_LIKES_bc.ACCOUNT_NAME" id="account_name" value="${param['filter_LIKES_bc.ACCOUNT_NAME']}" size="15" maxlength="15" />
				       		<input type="button" class="button button-small border-blue" name="btn" value="搜索" onclick="search();"/>&nbsp;
			            </strong>
			        </div>
			        <table class="table table-hover">
			        	<tr>
                    		<th scope="col">ID </th>
							<th>持卡人</th>
							<th>卡号</th>
							<th>银行</th>
							<th>身份证号</th>
							<th>省份</th>
							<th>城市</th>
 						</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="u">
				            <tr>
				            	<td>&nbsp;&nbsp;${u.cardId}</td>
								<td><a href="<common:WebRoot/>/v/user/view?cardId=${u.cardId}" class="text-sub">${u.accountName}</a></td>
								<td>${u.cardNo}</td>
								<td>${u.bank }</td>
								<td>${u.idCard }</td>
								<td>${u.provinceName }</td>
								<td>${u.cityName }</td>
				            </tr>
			        	</c:forEach>
			        </table>
			        <jsp:include page="../page.jsp" flush="true"></jsp:include>
			    </div>
			 </form>
			 <form action="<common:WebRoot/>/v/order/list" id="viewForm" name="viewForm">
			 	<input type="hidden" id="order_user_id" name="filter_EQI_bc.CARD_ID" value=""/>
			 </form>
        </div>
    </div>
</div>
</body>
</html>