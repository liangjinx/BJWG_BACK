<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>">
    
    <title>添加/编辑赠送好友物品信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common.jsp" flush="true"></jsp:include>
<script type="text/javascript">
	$(function(){
		commonTip("${requestScope.msg}");
		
    });
	/**
	 * 保存赠送好友物品信息信息
	 */
	function saveThisPresent(){
		var presentUserId = $("#presentUserId").val();
		if(presentUserId==null || presentUserId==""){
			alert("请选择赠送人昵称");
			return ;
		}
		var type = $("#type").val();
		if(type==null || type==""){
			alert("请选择类型");
			return ;
		}
		var select_present = $("#select_present").val();
		if(select_present==null || select_present==""){
			alert("请选择赠送物品");
			$("#select_present").focus();
			return;
		}
		
		var presentNum = $("#presentNum").val();
		if(presentNum==null || presentNum==""){
			alert("请填写数量");
			return;
		}
		var re1 = /^\d+$/;
        if (!re1.test(presentNum)) { 
            alert("请输入正确的数量"); 
            $("#presentNum").val("");
            $("#presentNum").focus();
            return; 
        }
        
        var price = $("#price").val();
		if(price==null || price==""){
			alert("请填写单价");
			return;
		}
		var re2 = /^\d+(?=\.{0,1}\d+$|$)/;
		if(!re2.test(price)){
			alert("请填写正确的单价");
			$("#price").focus();
			return;
		}
        
		var totalMoney = $("#totalMoney").val();
		if(totalMoney==null || totalMoney==""){
			alert("请填写总额");
			returns;
		}
		if(!re2.test(totalMoney)){
			alert("请填写正确的总额");
			$("#totalMoney").focus();
			return;
		}
		$("#presentForm").attr("action",basePath + "/v/present/edit").submit();
		$("button[name=btn]").attr("disabled",true);
	}
	/**
	* 	赠送物品
	*/
    function loadPresent(){
    	var userId = $("#presentUserId").val();
		var type = $("#type").val();
		var presentRelationId = '${presentRelationId}';
		$.ajax({  
		   		type: "POST",  
		        url: '<common:WebRoot/>/nv/present/loadsss',  
		        data: {"presentUserId":userId,"type":type},
		        success: function(data){
		        var json = eval("("+data+")"); 
	 			var option = "";
		        $.each(json.des, function (i, item) { 
						if(item != null){
		                   //循环获取数据      
		                   var id = item.presentRelationId; 
		                   var name = item.paincbuyProjectName;
		                   if(name!=null && name!="") {
			                   if( id == presentRelationIdssss){
			                   		option += "<option value='"+id+"' selected='selected' >"+name+"</option>";
			                   }else{
			                   		option += "<option value='"+id+"'>"+name+"</option>";
			                   }
		                   }
		                 }
	               });  
					$("#select_present").html(option); 
				}  
		});  
    }
</script>
  </head>
<body class="sidebar-left" OnLoad="javascript:setUp()">
  <!-- 主体 -->
<div class="admin">
    <div class="tab">
      <div class="tab-head">
        <strong>赠送好友物品信息</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">赠送好友物品信息设置</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	 <form action="<common:WebRoot/>/v/present/edit" method="post" id="presentForm" name="presentForm" class="form-x">
    			<input type="hidden" name="presentId" id="presentId" value="${present.presentId}" />
    			<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"/>
    			<div class="form-group">
                    <div class="label"><label>赠送人昵称<font color="red">*</font></label></div>
                    <div class="field">
                    	<select name="presentUserId" style="width: 50%;display:inline;" id="presentUserId" class="input">
							<option value="">--请选择--</option>
							<c:forEach items="${users}" var="user">
								<option value="${user.userId }" <c:if test="${user.userId == present.presentUserId }"> selected="selected"</c:if>>${user.username }</option>
							</c:forEach>
						</select>
						<input type="text" name="entry1"  style="width: 49%;display:inline;" size="30" onKeyUp="javascript:obj1.bldUpdate();" class="input" placeholder="输入关键字进行用户搜索">
                    </div>
                </div>
                <div class="form-group">
                	<div class="label">
                		<label>类型<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<select name="type" id="type" class="input">
							<option <c:if test="${u.type == 1 }">selected="selected"</c:if>>送猪仔</option>
							<option <c:if test="${u.type == 2 }">selected="selected"</c:if>>送猪肉券</option>
                		</select>
                    </div>
                </div>
                <div class="form-group">
                	<div class="label">
                		<label>赠送物品<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<select name="presentRelationId" id="select_present" class="input">
                			<option value="">--请选择--</option>
                		</select>
                    </div>
                </div>
                
        		<div class="form-group">
                    <div class="label"><label for="readme">被赠送人昵称<font color="red">*</font></label></div>
                    <div class="field">
						<select name="presentedUserId" style="width: 50%;display:inline;" id="presentedUserId" class="input">
							<option value="">--请选择--</option>
							<c:forEach items="${users}" var="user">
								<option value="${user.userId }" <c:if test="${user.userId == present.presentedUserId }"> selected="selected"</c:if>>${user.username }</option>
							</c:forEach>
						</select>
						<input type="text" name="entry2"  style="width: 49%;display:inline;" size="30" onKeyUp="javascript:obj2.bldUpdate();" class="input" placeholder="输入关键字进行用户搜索">
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="phone">数量<font color="red">*</font></label></div>
                    <div class="field">
                    	 <input type="text" name="presentNum" value="${present.presentNum}" class="input" id="presentNum" size="50" placeholder="必填项"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="phone">单价<font color="red">*</font></label></div>
                    <div class="field">
                    	 <input type="text" name="price" value="<fmt:formatNumber value='${present.price}' pattern='0.00'/>" class="input" id="price" size="50" placeholder="必填项"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="phone">总额<font color="red">*</font></label></div>
                    <div class="field">
                    	 <input type="text" name="totalMoney" value="<fmt:formatNumber value='${present.totalMoney}' pattern='0.00'/>" class="input" id="totalMoney" size="50" placeholder="必填项"/>
                    </div>
                </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisPresent();">提交</button>
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
		obj1 = new SelObj('presentForm','presentUserId','entry1');
		obj1.bldInitial();
		obj2 = new SelObj('presentForm','presentedUserId','entry2');
		obj2.bldInitial();
	}
</script>
  </body>
</html>