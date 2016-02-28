<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>提现记录列表</title>
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
	/**
	*搜索
	*/
	function search(){
		var valSt = $("#startTime").val();
		var valEnd = $("#endTime").val();
		if(valSt!="" && valEnd!=""){
	    	var date1 = new Date(valSt.replace(/\-/g, "\/"));// /g 全文搜索,/i 忽略大小写,也可以这样写： /gi
			var date2 = new Date(valEnd.replace(/\-/g, "\/"));
	    	var a = (date2 - date1)/(1000*60);
		    if (a < 0) {
		        alert("开始日期需在结束日期之前，请重新选择");
		        return;
		    }
	    }
		var url = "<common:WebRoot/>/v/withdraw/list?";
	    
	    var withwradals_id = $("#withwradals_id_sear").val();
	    var account_name = $("#account_name_sear").val();
	    var id_card = $("#id_card_sear").val();
	    var bank_code = $("#bank_code_sear").val();
	    var bank = $("#bank_sear").val();
	    var username = $("#username_sear").val();
	    var status = $("#status_sear").val();
	    
	    url+="startTime="+valSt+"&endTime="+valEnd+"&filter_EQI_t.withwradals_id="+withwradals_id+"&filter_LIKES_t.account_name="+account_name+"&filter_LIKES_t.ID_card="+id_card+"&filter_LIKES_t.bank_code="+bank_code+"&filter_LIKES_t.bank="+bank+"&filter_LIKES_t.username="+username+"&filter_EQS_t.status="+status;
	    window.location.href = encodeURI(url);
	}
	//订单导出
    function exportData(){
    
     	var valSt = $("#startTime").val();
		var valEnd = $("#endTime").val();
		if(valSt!="" && valEnd!=""){
	    	var date1 = new Date(valSt.replace(/\-/g, "\/"));// /g 全文搜索,/i 忽略大小写,也可以这样写： /gi
			var date2 = new Date(valEnd.replace(/\-/g, "\/"));
	    	var a = (date2 - date1)/(1000*60);
		    if (a < 0) {
		        alert("开始日期需在结束日期之前，请重新选择");
		        return;
		    }
	    }
		var url = "<common:WebRoot/>/v/withdraw/list?";
	    
	    var withwradals_id = $("#withwradals_id_sear").val();
	    var account_name = $("#account_name_sear").val();
	    var id_card = $("#id_card_sear").val();
	    var bank_code = $("#bank_code_sear").val();
	    var bank = $("#bank_sear").val();
	    var username = $("#username_sear").val();
	    var status = $("#status_sear").val();
	    
		$("button[name=btn]").attr("disabled",true);
		$.ajax({
			type: "POST",
			url: "<common:WebRoot/>/v/withdraw/checkExport",
			dataType: "text",
			data:{"filter_EQI_t.withwradals_id":withwradals_id,"filter_LIKES_t.account_name":account_name,"filter_LIKES_t.ID_card":id_card,"filter_LIKES_t.bank_code":bank_code,"filter_LIKES_t.bank":bank,"filter_LIKES_t.username":username,"filter_EQS_t.status":status,"startTime":valSt,"endTime":valEnd},
			success: function(data){
				if(data!="" && data!="操作成功" && data!=null && data.toString()!="" && data.charAt(data.length - 1)== "!"){
					alert(data);
					return; 
				}else{
					$("#allProjectForm").attr("action","<common:WebRoot/>/v/withdraw/export");
	    			$("#allProjectForm").submit();
	    			$("#allProjectForm").attr("action","<common:WebRoot/>/v/withdraw/list");
				}
			}
		});
	}
	/**
	 * 提现记录查看
	 */
	function withdraw_view(id){
		var action = "<common:WebRoot/>/v/withdraw/view";
		$("#withwradalsId").val(id);
		$("#allProjectForm").attr("action",action).submit();
		$("button[name=btn]").attr("disabled",true);
	}
	//审核提现记录
	function auth(id,auth){
		var showText = "";
		if(auth==2){
			showText = "通过";
		}else if(auth==3){
			showText = "不通过";
		}
		if(confirm('确认该提现记录'+showText+'审核吗?')){
			$.ajax({  
		   		type: "POST",  
		        url:  "<common:WebRoot/>/v/withdraw/auth",  
		        data: "id="+id+"&auth="+auth,
		        dataType: "text",
		        success: function(data){
		        	if(data!="" && data!="操作成功" && data!=null && data.toString()!=""){
						alert(data);
						return; 
				 	}else{
						$("#allProjectForm").attr("action","<common:WebRoot/>/v/withdraw/list");
			       		$("#allProjectForm").submit();
			       	}
				},
				error:function(data){
				}
			});
		}
	}
	//时间选择
	function selectDate() { 
		WdatePicker({dateFmt:'yyyy-MM-dd', isShowToday: false, isShowClear: false});
    } 
	</script>
  </head>
<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	<form action="<common:WebRoot/>/v/withdraw/list" method="post" id="allProjectForm" name="allProjectForm">
        		<!-- 隐藏域 用于编辑和删除使用 与搜索的ID区分开 -->
        		<input type="hidden" id="withwradalsId" name="withwradalsId" value=""/>
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>提现记录列表</strong></div>
			        <div class="padding border-bottom">
			            <strong style="padding-left: 100px;">
			            	ID:<input type="text" style="width: 150px;" value="${param['filter_EQI_t.withwradals_id']}"
								name="filter_EQI_t.withwradals_id" id="withwradals_id_sear">
				       		&nbsp;户名：<input type="text" name="filter_LIKES_t.account_name" id="account_name_sear" value="${param['filter_LIKES_t.account_name']}" size="15" maxlength="15" />
				       		&nbsp;身份证号码：<input type="text" name="filter_LIKES_t.ID_card" id="id_card_sear" value="${param['filter_LIKES_t.ID_card']}" size="20" maxlength="20" />
				       		&nbsp;银行代号：<input type="text" name="filter_LIKES_t.bank_code" id="bank_code_sear" value="${param['filter_LIKES_t.bank_code']}" size="15" maxlength="15" />
				       		&nbsp;银行：<input type="text" name="filter_LIKES_t.bank" id="bank_sear" value="${param['filter_LIKES_t.bank']}" size="15" maxlength="15" />
				       		&nbsp;用户名称：<input type="text" name="filter_LIKES_t.username" id="username_sear" value="${param['filter_LIKES_t.username']}" size="15" maxlength="15" />
				       	</strong>
				       	<br>
				     
				       	<strong style="padding-left: 100px;">
				       		&nbsp;状态：<select name="filter_EQS_t.status" id="status_sear" style="width: 80px;">
								<option value="">所有</option>
								<option value="1" <c:if test="${param['filter_EQS_t.status']== 1 }">selected="selected"</c:if>>未审核</option>
                  				<option value="2" <c:if test="${param['filter_EQS_t.status']== 2 }">selected="selected"</c:if>>已审核</option>
                  				<option value="3" <c:if test="${param['filter_EQS_t.status']== 3 }">selected="selected"</c:if>>未通过</option>
							</select>
				       		&nbsp;&nbsp;提现时间 :从
							<input type="text" style="width: 100px;" value="${startTime}"
								name="startTime" id="startTime" onclick="javascript:selectDate();">
							到
							<input type="text" style="width: 100px;" value="${endTime}"
								name="endTime" id="endTime" onclick="javascript:selectDate();">
				       		&nbsp;&nbsp;<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				       		<man:hasPermission name="withdraw_export">
								<input type="button" class="button button-small border-blue" value="导出" onclick="exportData();"/>&nbsp;
							</man:hasPermission>
			            </strong>
			        </div>
			        <table class="table table-hover">
			        	<tr>
                    		<th scope="col">ID</th>
                    		<th>用户名称</th>
							<th>户名</th>
							<th>银行代号</th>
							<th>银行</th>
							<th>提现时间</th>
							<th>提现金额(单位:元)</th>
							<th>银行卡号</th>
							<th>身份证号码</th>
							<th>状态</th>
							<man:hasPermission name="withdraw_view_OR_withdraw_auth">
								<th>操作</th>
							</man:hasPermission>
 						</tr>
			        	<c:forEach items="${requestScope.list}" var="u">
				            <tr>
				            	<td>&nbsp;&nbsp;${u.withwradalsId}</td>
								<td>
									<c:if test="${u.userId!=null && u.username!='无'}">
										<a href="<common:WebRoot/>/nv/user/view?userId=${u.userId}" class="text-sub">${u.username}</a>
									</c:if>
									<c:if test="${u.userId==null || u.username=='无'}">${u.username}</c:if>
								</td>
								<td>${u.accountName}</td>
								<td>${u.bankCode}</td>
								<td>${u.bank}</td>
								<td><fmt:formatDate value='${u.ctime}' pattern='yyyy-MM-dd HH:mm' /></td>
								<td><fmt:formatNumber value="${u.money}" pattern="0.00"/></td>
								<td>${u.cardNo}</td>
								<td>${u.idCard}</td>
								<td>
									<c:if test="${u.status == 1 }">未审核 </c:if>
			   						<c:if test="${u.status == 2 }">已审核</c:if>
			   						<c:if test="${u.status == 3 }">未通过</c:if>
								</td>
								<td>
				   					<man:hasPermission name="withdraw_auth">
				   						<c:if test="${u.status==2}">
				   							<a class="button[disabled] border-blue button-little">已审核</a>
				   						</c:if>
				   						<c:if test="${u.status==3}">
				   							<a class="button[disabled] border-blue button-little">审核不通过</a>
				   						</c:if>
				   						<c:if test="${u.status == 1 || u.status == '' || u.status == null}">
				   							<a href="javascript:auth('${u.withwradalsId}','2')" class="button border-blue button-little" >
				   								审核通过
				   							</a>
				   							<a href="javascript:auth('${u.withwradalsId}','3')" class="button border-blue button-little" >
				   								审核不通过
				   							</a>
				   						</c:if>
				   					</man:hasPermission>
				   					<man:hasPermission name="withdraw_view">
				   						<a href="javascript:withdraw_view(${u.withwradalsId})" class="button border-blue button-little" >
				   							查看
				   						</a>
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