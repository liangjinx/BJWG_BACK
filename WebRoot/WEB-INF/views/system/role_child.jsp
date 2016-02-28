<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>可选择角色配置界面</title>
    
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
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/nv/roleChildEdit.do">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="roleId" name="roleId" value="${requestScope.role.roleId }">
        		<input type="hidden" id="checkedRoleIds" name="checkedRoleIds" value="">
        		<label class="icon-user-md">  角色名称 : ${requestScope.role.roleName}</label>
         <br/>&nbsp;<br/>&nbsp;		
      <div id="mainList">  		
       <div id="leftDiv">
        <div class="title">已选择角色</div>
        <div id="cyList" class="cyList">
            <div class="content"  style="overflow:scroll; width:410px; height:470px;">
            <c:forEach items="${mList}" var="r">
            	<span style="width: 120px;"><input id="cy_${role.roleId}" name="chengyuan" type="checkbox" value="${r.roleId}"/>&nbsp;&nbsp;${r.roleName}</span>
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
        <div class="title">未选择角色</div>
        <div id="fcyList" class="cyList">
            <div class="content"  style="overflow:scroll; width:410px; height:470px;">
               <c:forEach items="${otherList}" var="r">
            		<span style="width: 120px;"><input id="other_${r.roleId}" type="checkbox" value="${r.roleId}"/>&nbsp;&nbsp;${r.roleName}</span>
            	</c:forEach>
            </div>
            <div class="controller">
                <a id="checkAllChk" href="javascript:void(0);">全选</a> | <a id="reverseChk" href="javascript:void(0);">反选</a>
            </div>
        </div>
    </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisRoleChild();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
