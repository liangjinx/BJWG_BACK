<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>">
    
    <title>添加/编辑用户钱包信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common.jsp" flush="true"></jsp:include>
<script type="text/javascript">
	var temp = "${wallet.payPassword}";
	$(function(){
		commonTip("${requestScope.msg}");
		
    });
    
	/**
	 * 保存用户钱包信息
	 */
	function saveThisWallet(){
		var payPassword = $("#payPassword").val();
		if(payPassword==null || payPassword==""){
			alert("请输入用户的支付密码");
			return ;
		}
		if(payPassword!=temp){
			var te =  /^\d+$/;
			if(!te.test(payPassword)){
				alert("用户的支付密码必须是整数");
				return ;
			}
			if(payPassword.length!=6){
				alert("用户的支付密码长度必须是6位");
				return ;
			}
		}
		var userId = $("#userId").val();
		if(userId==null || userId==""){
			alert("请选择你的用户名称");
			return ;
		}
		var money = $.trim($("#money").val());
		if(money==null || money==""){
			alert("请填写用户余额");
			$("#money").focus();
			return;
		}
		if(money!=null && money!=""){
			var re = /^\d+(?=\.{0,1}\d+$|$)/;
			if(!re.test(money)){
				alert("请填写正确的用户余额");
				$("#money").focus();
				return;
			}
		}
		$("#walletForm").attr("action",basePath + "/v/wallet/edit").submit();
		$("button[name=btn]").attr("disabled",true);
	}
    
</script>
  </head>
<body class="sidebar-left" OnLoad="javascript:setUp()">
  <!-- 主体 -->
<div class="admin">
    <div class="tab">
      <div class="tab-head">
        <strong>用户钱包信息</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">用户钱包信息设置</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	 <form action="<common:WebRoot/>/v/wallet/edit" method="post" id="walletForm" name="walletForm" class="form-x">
    			<input type="hidden" name="walletId" id="walletId" value="${wallet.walletId}" />
    			<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"/>
    			<div class="form-group">
                    <div class="label"><label for="password">支付密码<font color="red">*</font></label></div>
                    <div class="field">
                    	<input class="input" size="50" type="password" name="payPassword" id="payPassword"  value="${wallet.payPassword}"/>
                    </div>
                </div>
        		<div class="form-group">
                    <div class="label"><label for="readme">用户名称<font color="red">*</font></label></div>
                    <div class="field">
                    	<!-- 
                    	<c:choose>
	                    	<c:when test="${wallet.walletId==null || wallet.walletId=='' || wallet.walletId=='-1'}">
		                    	<select name="userId" style="width: 40%;" id="userId" >
									<option value="">--请选择--</option>
									<c:forEach items="${users}" var="user">
										<option value="${user.userId }" <c:if test="${user.userId == wallet.userId }"> selected="selected"</c:if>>${user.username }</option>
									</c:forEach>
								</select>
								<input type="text" name="entry"  style="width: 200px;" size="30" onKeyUp="javascript:obj1.bldUpdate();">
								<form:errors path="wallet.userId" cssStyle="color:red"> </form:errors>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<input type="hidden" name="userId" id="userId" value="${wallet.userId}" />
	                    		<label>${wallet.username}</label>
	                    	</c:otherwise>
                    	</c:choose>
						-->
						<select name="userId" style="width: 50%;display:inline;" id="userId" class="input">
							<option value="">--请选择--</option>
							<c:forEach items="${users}" var="user">
								<option value="${user.userId }" <c:if test="${user.userId == wallet.userId }"> selected="selected"</c:if>>${user.username }</option>
							</c:forEach>
						</select>
						<input type="text" name="entry"  style="width: 49%;display:inline;" size="30" onKeyUp="javascript:obj1.bldUpdate();" class="input" placeholder="输入关键字进行用户搜索">
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="label"><label for="phone">余额<font color="red">*</font></label></div>
                    <div class="field">
                    	 <input type="text" name="money" value="<fmt:formatNumber value='${wallet.money}' pattern='0.00'/>" class="input" id="money" size="50" placeholder="必填项"/>
                    </div>
                </div>
                 <div class="form-group">
                    <div class="label"><label for="status">状态<font color="red">*</font></label></div>
                    <div class="field">
	                    <div class="button-group button-group-small radio">
	                    	<label class="button <c:if test="${wallet.status!=2}"> active</c:if>"><input name="status" value="1" <c:if test="${wallet.status!=2}"> checked="checked"</c:if> type="radio"><span class="icon icon-check"></span> 开启</label>
	                    	<label class="button <c:if test="${wallet.status==2}"> active</c:if>"><input name="status" value="2"<c:if test="${wallet.status==2}"> checked="checked"</c:if>  type="radio"><span class="icon icon-times"></span> 冻结</label>
	                    	 <!-- 
	                    	 <select name="status" id="status" style="width:180px;">
		                    	<option value="1" <c:if test="${wallet.status==1}"> selected="selected"</c:if>>正常使用</option>
		                    	<option value="2" <c:if test="${wallet.status==2}"> selected="selected"</c:if>>已冻结</option>
	                    	</select>
	                    	 -->
	                     </div>
                    </div>
                </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisWallet();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
      </div>
    </div>
</div>
<SCRIPT LANGUAGE="JavaScript">
	function SelObj(formname,selname,textname,str) {
		this.formname = formname;
		this.selname = selname;
		this.textname = textname;
		this.select_str = str || '';
		this.selectArr = new Array();
		this.initialize = initialize;
		this.bldInitial = bldInitial;
		this.bldUpdate = bldUpdate;
	}
	function initialize() {
		if (this.select_str =='') {
			for(var i=0;i<document.forms[this.formname][this.selname].options.length;i++) {
				this.selectArr[i] = document.forms[this.formname][this.selname].options[i];
				this.select_str += document.forms[this.formname][this.selname].options[i].value+":"+
				document.forms[this.formname][this.selname].options[i].text+",";
		   }
		}
		else {
			var tempArr = this.select_str.split(',');
			for(var i=0;i<tempArr.length;i++) {
				var prop = tempArr[i].split(':');
				this.selectArr[i] = new Option(prop[1],prop[0]);
		   	}
		}
		return;
	}
	function bldInitial() {
		this.initialize();
		for(var i=0;i<this.selectArr.length;i++)
		document.forms[this.formname][this.selname].options[i] = this.selectArr[i];
		document.forms[this.formname][this.selname].options.length = this.selectArr.length;
		return;
	}
	function bldUpdate() {
		var str = document.forms[this.formname][this.textname].value.replace('^\\s*','');
		if(str == '') {this.bldInitial();return;}
		this.initialize();
		var j = 0;
		pattern1 = new RegExp("^"+str,"i");
		for(var i=0;i<this.selectArr.length;i++)
		if(pattern1.test(this.selectArr[i].text))
		document.forms[this.formname][this.selname].options[j++] = this.selectArr[i];
		document.forms[this.formname][this.selname].options.length = j;
		if(j==1){
			document.forms[this.formname][this.selname].options[0].selected = true;
	//document.forms[this.formname][this.textname].value = document.forms[this.formname][this.selname].options[0].text;
	   	}
	}
	function setUp() {
		obj1 = new SelObj('walletForm','userId','entry');
		obj1.bldInitial();
	}
</script>
  </body>
</html>