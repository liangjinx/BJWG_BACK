<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>用户钱包</title>
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
		//删除左右两端的空格
		function trim(str){ 
			return str.replace(/(^\s*)|(\s*$)/g, "");
		}
	/**
	*搜索
	*/
	function search(){
	
		var username = $("#username_sear").val();
		var status = $("#status_sear").val();
	    var currentPage = $("#currentPage").val();
	    var pageCount = $("#pageCount").val();
	    
	    var url = "<common:WebRoot/>/v/wallet/list?";
	    url+="filter_LIKES_u.username="+username+"&filter_EQS_t.STATUS="+status+"&currentPage="+currentPage+"&pageCount="+pageCount;
	    window.location.href = encodeURI(url);
	}
	/**
	 * 用户余额列表管理
	 */
	function wallet_edit(type,id){
	
		var action = "";
		switch (type) {
		case 1://新增
			action = basePath + "/v/wallet/editInit";
			break;
		case 2://修改
			action = basePath + "/v/wallet/editInit";
			$("#walletId").val(id);
			break;
		case 3://单个删除
			action = basePath + "/v/wallet/delete";
			if(confirm("是否确定删除记录？")){
				$("#walletId").val(id);
			}else{
				return ;
			}
			break;
		case 4://多个删除
			action = basePath + "/v/wallet/delete";
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
	</script>
  </head>
<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/wallet/list" method="post" id="allProjectForm" name="allProjectForm">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="walletId" name="walletId" value=""/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>用户钱包</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            <strong style="padding-left: 100px;">
			            	用户名称:<input type="text" style="width: 150px;" value="${param['filter_LIKES_u.username']}"
								name="filter_LIKES_u.username" id="username_sear">
				       		&nbsp;状态:<select name="filter_EQS_t.STATUS" id="status_sear" style="width: 150px;">
								<option value="">--请选择--</option>
								<option value="1" <c:if test="${param['filter_EQS_t.STATUS']=='1'}"> selected</c:if>>正常使用</option>
								<option value="2" <c:if test="${param['filter_EQS_t.STATUS']=='2'}"> selected</c:if>>已冻结</option>
							</select>
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				       		<man:hasPermission name="wallet_edit_init">
				       			<input type="button" class="button button-small border-blue" value="新增" onclick="javascript:wallet_edit(1);"/>&nbsp;
				       		</man:hasPermission>
				       		<man:hasPermission name="wallet_delete">
				            	<input type="button" class="button button-small border-yellow" value="批量删除" onclick="wallet_edit(4);"/>
				            </man:hasPermission>
			            </strong>
			        </div>
			        <table class="table table-hover">
			        	<tr>
                    		<th scope="col" style="width:5%">ID </th>
							<th>用户名</th>
							<th>余额</th>
							<th>状态</th>
							<man:hasPermission name="wallet_edit_init_OR_wallet_delete">
								<th>操作</th>
							</man:hasPermission>
 						</tr>
			        	<c:forEach items="${requestScope.list}" var="u">
				            <tr>
				            	<td><input type="checkbox" name="idList" id="idList" value="${u.walletId}" />&nbsp;&nbsp;${u.walletId}</td>
								<td>
									<c:if test="${u.userId!=null && u.username!='无'}">
										<a href="<common:WebRoot/>/v/user/view?userId=${u.userId}" class="text-sub">${u.username}</a>
									</c:if>
									<c:if test="${u.userId==null || u.username=='无'}">${u.username}</c:if>
								</td>
								<td><fmt:formatNumber value="${u.money}" pattern="0.00"/></td>
								<td>
									<c:if test="${u.status == 1 }">正常使用</c:if>
			   						<c:if test="${u.status == 2 }"><font color="#f60">已冻结</font></c:if>
								</td>
								<td>
									<man:hasPermission name="wallet_edit_init">
										<a class="button border-blue button-little" href="javascript:wallet_edit(2,${u.walletId});">编辑</a>
									</man:hasPermission>
									<man:hasPermission name="wallet_delete">
				            			<a class="button border-yellow button-little" href="javascript:wallet_edit(3,${u.walletId});">删除</a>
				            		</man:hasPermission>
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