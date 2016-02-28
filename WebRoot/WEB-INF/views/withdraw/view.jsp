<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>">
    <title>查看提现信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
 <body>
<!-- head -->
<jsp:include page="../common.jsp" flush="true"></jsp:include>
 <!-- 主体 -->
<div class="admin">
    <div class="tab">
      <div class="tab-head">
        <strong>提现记录</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">提现信息查看</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	 <form action="#" method="post" name="allProjectForm" id="allProjectForm">
				 <div class="form-group">
                    <div class="label"><label for="logo">用户名称</label></div>
                    <div class="field">
	    				<label>${withdraw.username}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="logo">户名</label></div>
                    <div class="field">
	    				<label>${withdraw.accountName}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="logo">银行代号</label></div>
                    <div class="field">
	    				<label>${withdraw.bankCode}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="logo">银行</label></div>
                    <div class="field">
	    				<label>${withdraw.bank}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="logo">身份证号</label></div>
                    <div class="field">
	    				<label>${withdraw.idCard}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">提现时间</label></div>
                   <div class="field">
                    	<label><fmt:formatDate value='${withdraw.ctime}' pattern='yyyy-MM-dd HH:mm' /> </label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">提现金额</label></div>
                   <div class="field">
                    	<label><fmt:formatNumber value="${withdraw.money}" pattern="0.00"/></label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">审核人</label></div>
                   <div class="field">
                    	<label>${withdraw.auditingMan}</label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">审核时间</label></div>
                   <div class="field">
                    	<label><fmt:formatDate value='${withdraw.auditingTime}' pattern='yyyy-MM-dd HH:mm' /> </label>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="readme">审核状态</label></div>
                   <div class="field">
                    	<label>
							<c:if test="${withdraw.status==1}">未审核</c:if>
							<c:if test="${withdraw.status==2}">已审核</c:if>
							<c:if test="${withdraw.status==3}">未通过</c:if>	
						</label>
                    </div>
                </div>
                <div class="form-group">
	               <div class="label"><label for="type">备注</label></div>
	               <div class="field">
	               		<label>${withdraw.remark}</label>
	               </div>
               	</div>
               	<man:hasPermission name="withdraw_auth">
					<c:if test="${withdraw.status == 1 || withdraw.status == '' || withdraw.status == null}">
						<button class="button bg-main" type="button" name="btn" onclick="javascript:auth('${withdraw.withwradalsId}','2')">审核通过</button>
						<button class="button bg-main" type="button" name="btn" onclick="javascript:auth('${withdraw.withwradalsId}','3')">审核不通过</button>
					</c:if>
				 </man:hasPermission>
                <button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
            </form>
        </div>
      </div>
    </div>
</div>
  </body>
<script type="text/javascript">
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
</script>
</html>
