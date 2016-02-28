<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>主菜单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		//得到订单的统计
		//type 表示时间参数。1、当日;2、本周;3、本月
		function getUserCount(type){
			$.ajax({  
		   		type: "POST",  
		        url: '<common:WebRoot/>/nv/index/user/count',  
		        data: "type="+type,
		        success: function(data){
			        var json = eval("("+data+")"); 
		            $("#userCount").text(json.userCount);
				}  
			});  
		}
		//得到订单的统计
		//type 表示时间参数。1、当日;2、本周;3、本月
		function getOrderCount(type){
			$.ajax({  
		   		type: "POST",  
		        url: '<common:WebRoot/>/nv/index/order/count',  
		        data: "type="+type,
		        success: function(data){
			        var json = eval("("+data+")"); 
		            $("#orderCount").text(json.orderCount);
		            $("#waitOrderCount").text(json.waitOrderCount);
		            $("#orderTotalMoney").text(json.orderTotalMoney+"元");
				}  
			});  
		}
		//得到提现的统计
		//type 表示时间参数。1、当日;2、本周;3、本月
		function getWithdrawCount(type){
			$.ajax({  
		   		type: "POST",  
		        url: '<common:WebRoot/>/nv/index/withdraw/count',  
		        data: "type="+type,
		        success: function(data){
			        var json = eval("("+data+")"); 
		            $("#withdrawCount").text(json.withdrawCount);
		            $("#withdrawMoney").text(json.withdrawMoney+"元");
				}  
			});  
		}
	</script>
  </head>
<body>
<jsp:include page="common.jsp" flush="true"></jsp:include>

<div class="admin">
	<div class="line-big">
	<man:hasPermission name="order_list_OR_withdraw_list_OR_user_list">
	<div class="xm3">
	<man:hasPermission name="user_list">
		<div class="panel">
        	<div class="panel-head"><strong>用户统计信息</strong></div>
        	<div style="margin-top: 10px;text-align: center;">
	        	<div class="button-group button-group-small radio">
	            	<label class="button active"><input name="type" value="1" checked="checked" type="radio" onclick="javascript:getUserCount(1);"><span class="icon icon-check"></span> 今日</label>
	            	<label class="button"><input name="type" value="2" type="radio" onclick="javascript:getUserCount(2);"><span class="icon icon-check"></span> 本周</label>
	            	<label class="button"><input name="type" value="3" type="radio" onclick="javascript:getUserCount(3);"><span class="icon icon-check"></span> 本月</label>
	            </div>
        	</div>
            <ul class="list-group">
            	<li><span class="float-right badge bg-main" id="userCount">${homeView.userCount}</span><span class="icon-user"></span> 注册用户（人）</li>
            </ul>
       	</div>
       	</man:hasPermission>
       	<br>
		<man:hasPermission name="order_list">
		<div class="panel">
        	<div class="panel-head"><strong>订单统计信息</strong></div>
        	<div style="margin-top: 10px;text-align: center;">
	        	<div class="button-group button-group-small radio">
	            	<label class="button active"><input name="type" value="1" checked="checked" type="radio" onclick="javascript:getOrderCount(1);"><span class="icon icon-check"></span> 今日</label>
	            	<label class="button"><input name="type" value="2" type="radio" onclick="javascript:getOrderCount(2);"><span class="icon icon-check"></span> 本周</label>
	            	<label class="button"><input name="type" value="3" type="radio" onclick="javascript:getOrderCount(3);"><span class="icon icon-check"></span> 本月</label>
	            </div>
        	</div>
            <ul class="list-group">
            	<li><span class="float-right badge bg-main" id="orderCount">${homeView.orderCount}</span><span class="icon-user"></span> 订单总数</li>
                <li><span class="float-right badge bg-main" id="waitOrderCount">${homeView.waitOrderCount}</span><span class="icon-file"></span> 待收货订单</li>
                <li><span class="float-right badge bg-main" id="orderTotalMoney">${homeView.orderTotalMoney}元</span><span class="icon-shopping-cart"></span> 订单总金额</li>
            </ul>
       	</div>
       	</man:hasPermission>
       	 <br>
       	 <man:hasPermission name="withdraw_list">
        <div class="panel">
        	<div class="panel-head"><strong>提现统计信息</strong></div>
        	<div style="margin-top: 10px;text-align: center;">
      	<div class="button-group button-group-small radio">
          	<label class="button active"><input name="type" value="1" checked="checked" type="radio" onclick="javascript:getWithdrawCount(1);"><span class="icon icon-check"></span> 今日</label>
          	<label class="button"><input name="type" value="2" type="radio" onclick="javascript:getWithdrawCount(2);"><span class="icon icon-check"></span> 本周</label>
          	<label class="button"><input name="type" value="3" type="radio" onclick="javascript:getWithdrawCount(3);"><span class="icon icon-check"></span> 本月</label>
          </div>
    		</div>
            <ul class="list-group">
            	<li><span class="float-right badge bg-main" id="withdrawCount">${homeView.withdrawCount}</span><span class="icon-file"></span> 提现交易次数</li>
            	<li><span class="float-right badge bg-main" id="withdrawMoney">${homeView.withdrawMoney}元</span><span class="icon-user"></span> 提现金额</li>
            </ul>
        </div>
        </man:hasPermission>
        </div>
        </man:hasPermission>
        <div class="xm9">
            <div class="alert">
            	您当前的ip：${sessionScope.session_manager.lastLoginIpStr }，当前系统时间：<jsp:useBean id="now" class="java.util.Date" /><fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm" />
            </div>
            <div class="alert">
                <h4>${sessionScope.session_manager.username }当前信息</h4>
                <p class="text-gray padding-top">
                <br>
                	所属角色:
                	<c:if test="${sessionScope.session_manager.managerId==1}">
                		<label class="badge bg-main">系统管理员</label>
                	</c:if>
                	<c:if test="${sessionScope.session_manager.managerId!=1}">
                	<c:forEach items="${roles}" var="role">
                		<label class="badge bg-main">${role.roleName}</label>&nbsp;
                	</c:forEach>
                	</c:if>
                </p>
                <p class="text-gray padding-top">
                	可以操作以下资源:
                	<c:if test="${sessionScope.session_manager.managerId==1}">
                		<label class="badge bg-main">所有资源</label>
                	</c:if>
                	<c:if test="${sessionScope.session_manager.managerId!=1}">
                	<c:forEach items="${rescs}" var="resc">
                		<label class="badge bg-main">${resc.rescName}</label>&nbsp;
                	</c:forEach>
                	</c:if>
                </p>
            </div>
            <br>
            <div class="alert">
                <h4>八戒王国后台管理系统介绍</h4>
                <p class="text-gray padding-top">八戒王国后台管理系统主要是为了...</p>
            </div>
        </div>
    </div>
    <br />
</div>

</body>
  
</html>
