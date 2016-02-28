<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>主账号分配子账号</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript" src="<common:WebRoot/>/resources/system/system.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
			$(":checkbox").click(function(){
        	checkBgColor();
    });
    // 添加成员
    $("#addCy").click(function(){
        var fcy = $("#fcyList :checkbox:checked");
        if(fcy.length){
            fcy.attr("checked", false).parent().appendTo($("#cyList .content"));
            checkBgColor();
        }
    });
    // 移除成员
    $("#delCy").click(function(){
        var cy = $("#cyList :checkbox:checked");
        if(cy.length){
            cy.attr("checked", false).parent().appendTo($("#fcyList .content"));
            checkBgColor();
        }
    });
    // 全选
    $(".controller #checkAllChk").click(function(){
        $(this).parents(".cyList").find(":checkbox").attr("checked", true);
        checkBgColor();
    });
    // 反选
    $(".controller #reverseChk").click(function(){
        $(this).parents(".cyList").find(":checkbox").each(function(){
            $(this).attr("checked", !$(this).attr("checked"));
            checkBgColor();
        });
    });
    checkBgColor();
		});
		function checkBgColor(){
    $(":checkbox").parent().css("background-color", "");
    $(":checkbox:checked").parent().css("background-color", "#f9b87e");
}
	</script>
  </head>

<body>

<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/nv/userPCEdit.do">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="managerId" name="managerId" value="${requestScope.manager.managerId }">
        		<input type="hidden" id="checkedUserIds" name="checkedUserIds" value="">
        		<label class="icon-user-md">  用户名称 : ${requestScope.manager.username}</label>
         <br/>&nbsp;<br/>&nbsp;		
      <div id="mainList">  		
       <div id="leftDiv">
        <div class="title">已有子账号</div>
        <div id="cyList" class="cyList">
            <div class="content"  style="overflow:scroll; width:410px; height:270px;">
            <c:forEach items="${mList}" var="user">
            	<span style="width: 120px;"><input id="cy_${user.managerId}" name="chengyuan" type="checkbox" value="${user.managerId}"/>&nbsp;&nbsp;${user.username}</span>
            </c:forEach>
            </div>
            <div class="controller">
                <a id="checkAllChk" href="javascript:void(0);">全选</a> | <a id="reverseChk" href="javascript:void(0);">反选</a>
            </div>
        </div>
    </div>
    <div id="btnsDiv">
        <input id="addCy" type="button" value="<< 添加" />
        <input id="delCy" type="button" value="移除 >>" />
    </div>
    <div id="rightDiv">
        <div class="title">未选择账号</div>
        <div id="fcyList" class="cyList">
            <div class="content"  style="overflow:scroll; width:410px; height:270px;">
               <c:forEach items="${otherList}" var="user">
            		<span style="width: 120px;"><input id="other_${user.managerId}" type="checkbox" value="${user.managerId}"/>&nbsp;&nbsp;${user.username}</span>
            	</c:forEach>
            </div>
            <div class="controller">
                <a id="checkAllChk" href="javascript:void(0);">全选</a> | <a id="reverseChk" href="javascript:void(0);">反选</a>
            </div>
        </div>
    </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisUserChild();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
