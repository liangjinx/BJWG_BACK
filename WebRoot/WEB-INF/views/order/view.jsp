<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>">
    
    <title>查看订单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common.jsp" flush="true"></jsp:include>
	<style>
		#orderdiv .form-group { float: left; text-align: left; width: 50%;}
		#orderdiv form .form-group:last-child { width: 100% !important;}
		#orderdiv .label { height: 40px; line-height: 40px; padding-bottom: 15px !important; border-radius: 4px 4px 0 0; background-color: #f5f5f5;}
		#orderdiv .form-group-2 label { height: 30px; line-height: 30px;}
	</style>
  </head>
 <body class="sidebar-left ">
<script type="text/javascript">
	$(document).ready(function() {
		commonTip("${requestScope.msg}");
	});
</script>
 <!-- 主体 -->
<div class="admin" id="orderdiv">
    <div class="tab">
      <div class="tab-head">
        <strong>订单</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">订单设置</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	 <form action="<common:WebRoot/>/v/shop/list" method="post" id="allProjectForm">
        	 
				<input type="hidden" name="orderId" id="orderId" value="${order.orderId}" />
				<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"/>
				<div class="form-group">
                    <div class="label"><label for="orderCode">订单编号</label></div>
                    <div class="form-group-2">
                    	<label for="readme">${order.orderCode}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="linkMan">联系人</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">${order.username}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="linkMan">数量</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">${order.num}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="linkMan">单价</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">${order.price}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="totalMoney">订单总金额</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">${order.totalMoney}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="ctime">下单时间</label></div>
                    <div class="field form-group-2">
                    	<label for="readme"><fmt:formatDate value='${order.ctime}' pattern='yyyy-MM-dd HH:mm' /></label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="totalMoney">项目ID</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">${order.relationId}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="totalMoney">项目周期</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">${order.relationName}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="logo">备注</label></div>
                    <div class="field form-group-2">
                    		<label for="readme">${order.remark}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="payTime">付款时间</label></div>
                    <div class="field form-group-2">
                    	<label for="readme"><fmt:formatDate value='${order.payTime}' pattern='yyyy-MM-dd HH:mm' /></label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="logo">订单状态</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">
                    		<c:forEach items="${statuslist}" var="status">
                    			<c:if test="${status.value==order.status}">
                    				${status.name}
                    			</c:if>
                    		</c:forEach>
                 		</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="address">付款方式</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">
                    		<c:forEach items="${paytypelist}" var="type">
                    			<c:if test="${type.value==order.payType}">
                    				${type.name}
                    			</c:if>
                    		</c:forEach>
                 		</label>
                    </div>
                </div>
                 <div class="form-group">
                    <div class="label"><label for="orderType">订单类型</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">
                    		<c:forEach items="${typelist}" var="orderType">
                    			<c:if test="${orderType.value==order.type}">
                    				${orderType.name}
                    			</c:if>
                    		</c:forEach>
                 		</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="payTime">过期时间</label></div>
                    <div class="field form-group-2">
                    	<label for="readme"><fmt:formatDate value='${order.overTime}' pattern='yyyy-MM-dd HH:mm' /></label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="payTime">确认收货时间</label></div>
                    <div class="field form-group-2">
                    	<label for="readme"><fmt:formatDate value='${order.confirmTime}' pattern='yyyy-MM-dd HH:mm' /></label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="payTime">子单id</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">${order.subOrderId}</label>
                    </div>
                </div>
                 <div class="form-group">
                    <div class="label"><label for="payTime">商品图片</label></div>
                    <div class="field form-group-2">
                    	<label for="readme">
                    		<img class="w-img" id="w-img" src="<c:if test="${order.productImg!='' && order.productImg!='null' && order.productImg!=null && !fn:containsIgnoreCase(order.productImg,'http://')}">${domain}</c:if>${order.productImg}" width="100px" height="100px">
                    	</label>
                    </div>
                </div>
                <br>
                <div class="form-group">
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
      </div>
    </div>
</div>
</body>
</html>
