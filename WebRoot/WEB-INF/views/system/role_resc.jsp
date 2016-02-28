<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>角色分配权限</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript" src="<common:WebRoot/>/resources/modular/js/jquery.js"></script>
	<script type="text/javascript" src="<common:WebRoot/>/resources/system/system.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
	$(":checkbox").click(function(){
        checkBgColor();
    });
    // 添加成员
    $("#addCy").click(function(){
        var fcy = $("#fcyList").find("input[name^='fcy_']:checked");
         $(fcy).each(function(){
        	var temp = $(this).attr("name");
        	var group = temp.substring(4,temp.length);
        	if($("#cyall"+group).length <= 0){
        		var label = "";
        		switch(parseInt(group)){
        			case 11:
        				label = "权限管理";
        				break;
        			case 12:
        				label = "数据管理";
        				break;
        			case 13:
        				label = "子账号管理";
        				break;
        			case 14:
        				label = "用户管理";
        				break;
        			case 15:
        				label = "系统管理";
        				break;	
        			case 16:
        				label = "项目管理";
        				break;
        			case 17:
        				label = "订单";
        				break;
        			case 18:
        				label = "猪肉券";
        				break;
        			case 19:
        				label = "资讯管理";
        				break;
        			case 20:
        				label = "线下门店";
        				break;
        			case 21:
        				label = "首页";
        				break;
        			default:
        				break;
        		}
        		var fieldset = $("<fieldset style='padding:6px;margin:6px;border:#BBDCF1 dashed 1px;' id='cyfieldset"+group+"' class='cyset'/>");
        		var content = fieldset.append($("<legend><input id='cyall"+group+"' onclick='selAll(this)' type='checkbox'><font color='#FF6600'>"+label+"</font></legend>").appendTo(fieldset));
        		content.appendTo($("#cyList .content"));
        	}
            $(this).attr("checked", false).parent().appendTo($("#cyfieldset"+group));
            var del = $("#fcyfieldset"+group).find("input[name^='fcy_']");
            if(del.length<=0){
            	$("#fcyfieldset"+group).remove();
            }
            checkBgColor();
        });
    });
    
    // 移除成员
    $("#delCy").click(function(){
         var cy = $("#cyList").find("input[name^='fcy_']:checked");
         $(cy).each(function(){
         	var temp = $(this).attr("name");
        	var group = temp.substring(4,temp.length);
        	if($("#fcyall"+group).length <= 0){
        		var label = "";
        		switch(parseInt(group)){
        			case 11:
        				label = "权限管理";
        				break;
        			case 12:
        				label = "数据管理";
        				break;
        			case 13:
        				label = "子账号管理";
        				break;
        			case 14:
        				label = "用户管理";
        				break;
        			case 15:
        				label = "系统管理";
        				break;	
        			case 16:
        				label = "项目管理";
        				break;
        			case 17:
        				label = "订单";
        				break;
        			case 18:
        				label = "猪肉券";
        				break;
        			case 19:
        				label = "资讯管理";
        				break;
        			case 20:
        				label = "线下门店";
        				break;
        			case 21:
        				label = "首页";
        				break;
        			default:
        				break;
        		}
        		var fieldset = $("<fieldset style='padding:6px;margin:6px;border:#BBDCF1 dashed 1px;' id='fcyfieldset"+group+"' class='cyset'/>");
        		var content = fieldset.append($("<legend><input id='fcyall"+group+"' onclick='selAll(this)' type='checkbox'><font color='#FF6600'>"+label+"</font></legend>").appendTo(fieldset));
        		content.appendTo($("#fcyList .content"));
        	}
            $(this).attr("checked", false).parent().appendTo($("#fcyfieldset"+group));
            var del = $("#cyfieldset"+group).find("input[name^='fcy_']");
            if(del.length<=0){
            	$("#cyfieldset"+group).remove();
            }
            checkBgColor();
         });
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
 //全选
    function selAll(obj){
    	$(obj).parents(".cyset").find(":checkbox").each(function(){
    		if($(this).attr("id").indexOf("all")<0){
    			$(this).attr("checked", !$(this).attr("checked"));
    		}
    	});
    	checkBgColor();
	}
	</script>
  </head>

<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/nv/roleUserEdit.do">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="roleId" name="roleId" value="${requestScope.role.roleId }">
        		<input type="hidden" id="checkedRescIds" name="checkedRescIds" value="">
        		<label class="icon-user-md">  角色名称 : ${requestScope.role.roleName}</label>
       <br/>&nbsp;<br/>&nbsp;
      <div id="mainList">  		
       <div id="leftDiv">
        <div class="title">已有服务</div>
        <div id="cyList" class="cyList">
            <div class="content" style="overflow:scroll; width:410px; height:470px;">
            	<c:set var="cscope" value="0"/>
					<c:forEach var="per" items="${mList}">
						<c:if test="${per.rescGroup!=cscope}">
							<c:if test="${cscope!=0}"></fieldset></c:if>
							<c:set var="cscope" value="${per.rescGroup}"/>
							<fieldset style="padding:6px;margin:6px;border:#BBDCF1 dashed 1px;" id='cyfieldset${per.rescGroup}' class='cyset'>
							<c:forEach var="ps" items="${permissionScops}">
								<c:if test="${per.rescGroup==ps.key}">
									<legend>
										<input id="cyall${per.rescGroup}" type="checkbox" onclick="selAll(this)">
										<font color="#FF6600">${ps.value}</font>
									</legend>
								</c:if>
							</c:forEach>
						</c:if>
							<span style="width: 160px;"><input id="other_${per.rescId}" name="fcy_${per.rescGroup}" type="checkbox" value="${per.rescId}"/>${per.rescName}</span>
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
        <div class="title">未选择服务</div>
        <div id="fcyList" class="cyList">
            <div class="content" style="overflow:scroll; width:410px; height:470px;">
						<c:set var="tscope" value="0"/>
							<c:forEach var="per" items="${otherList}">
								<c:if test="${per.rescGroup!=tscope}">
									<c:if test="${tscope!=0}"></fieldset></c:if>
									<c:set var="tscope" value="${per.rescGroup}"/>
									<fieldset style="padding:6px;margin:6px;border:#BBDCF1 dashed 1px;" id='fcyfieldset${per.rescGroup}' class='cyset'>
									<c:forEach var="ps" items="${permissionScops}">
										<c:if test="${per.rescGroup==ps.key}">
											<legend>
												<input id="fcyall${per.rescGroup}" type="checkbox" onclick="selAll(this)">
												<font color="#FF6600">${ps.value}</font>
											</legend>
										</c:if>
									</c:forEach>
								</c:if>
								<span style="width: 160px;"><input id="other_${per.rescId}" name="fcy_${per.rescGroup}" type="checkbox" value="${per.rescId}"/>${per.rescName}</span>
							</c:forEach>
            <!-- 
               <c:forEach items="${otherList}" var="user">
            		<span style="width: 120px;"><input id="other_${user.rescId}" type="checkbox" value="${user.rescId}"/>${user.rescName}</span>
            	</c:forEach>
             -->
            </div>
            <div class="controller">
                <a id="checkAllChk" href="javascript:void(0);">全选</a> | <a id="reverseChk" href="javascript:void(0);">反选</a>
            </div>
        </div>
    </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisRoleResc();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>