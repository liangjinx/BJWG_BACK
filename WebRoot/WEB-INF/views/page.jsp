<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <script type="text/javascript" src="<common:WebRoot/>/resources/system/page.js"></script>
    <title>分页</title>
  </head>
<body>
	<input type="hidden" name="currentPage" id="currentPage" value="${requestScope.page.currentPage }">
	<input type="hidden" name="pageCount" id="pageCount" value="${requestScope.page.pageCount }">
	
	<div class="panel-foot text-center">
         <ul class="pagination">
         	<li><a href="#" onclick="getPageList('index');return false;">首页</a></li>
         </ul>
         <ul class="pagination">
         	<li><a href="#" onclick="getPageList('upage');return false;">上一页</a></li>
         </ul>
         
         <c:set var="sPage" value="1"></c:set>
         <c:set var="ePage" value="${requestScope.page.pageCount}"></c:set>
         
         <c:choose>
	        <c:when test="${requestScope.page.pageCount <= 10}">
	         	
	         	<c:forEach begin="1" end="${requestScope.page.pageCount }" step="1" var="p" varStatus="status">
			         <ul class="pagination pagination-group">
			             <li <c:if test="${requestScope.page.currentPage == p}"> class="active"</c:if>>
			             	<a href="#" onclick="gotoSelectPage(${p});return false;">${p}</a>
			             </li>
			         </ul>
		         </c:forEach>
	         	
	        </c:when>
         	<c:otherwise>
         		
      			<!--  当前页大于5时 ，只显示当且页的前三条 再追加三条 -->
      			<c:choose>
	  				<c:when test="${requestScope.page.currentPage > 5 }">
	  					<c:choose>
	  						<c:when test="${requestScope.page.pageCount >=  requestScope.page.currentPage + 7}">
			  					<c:set var="sPage" value="${requestScope.page.currentPage -3}"></c:set>
			  					<c:set var="ePage" value="${requestScope.page.currentPage + 7 }"></c:set>
	  						</c:when>
	  						<c:otherwise>
			  					<c:set var="sPage" value="${requestScope.page.pageCount - 10}"></c:set>
			  					<c:set var="ePage" value="${requestScope.page.pageCount }"></c:set>
	  						</c:otherwise>
	  					</c:choose>
	  				</c:when>
	  				<c:otherwise>
	  					<c:set var="sPage" value="1"></c:set>
			  			<c:set var="ePage" value="10"></c:set>
	  				</c:otherwise>
      			</c:choose>
	  				
         		<c:forEach begin="${sPage}" end="${ePage }" step="1" var="p" varStatus="status">
			         <ul class="pagination pagination-group">
			             <li <c:if test="${requestScope.page.currentPage == p}"> class="active"</c:if>>
			             	<a href="#" onclick="gotoSelectPage(${p});return false;">${p}</a>
			             </li>
			         </ul>
		        </c:forEach>
         	</c:otherwise>
         </c:choose>
         
         
         <ul class="pagination">
         	<li><a href="#" onclick="getPageList('next');return false;">下一页</a></li>
         </ul>
         <ul class="pagination">
         	<li><a href="#" onclick="getPageList('last');return false;">尾页</a></li>
         </ul>
         <ul class="pagination">
         <li style="padding:4px 10px">跳转到:
         	<INPUT id=goPage onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" maxlength="4"/> 
         	<INPUT type="button" value="Go" onclick="javascript:gotoSelectPage($('#goPage').val());return false;"/>
	 	</li>
         	<li style="padding:7px 8px">第${requestScope.page.currentPage}页/共${requestScope.page.pageCount}页</li>
         </ul>
     </div>
     
</body>
  
</html>