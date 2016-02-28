<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>系统配置增加/编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
	function saveThisConfig(){	
		var name = $.trim($("#name").val());
		if(name==null || name==""){
			alert("请填写名称");
			$("#name").focus();
			return;
		}
		var code = $.trim($("#code").val());
		if(code==null || code==""){
			alert("请填写编号");
			return;
		}
		var parentCode = $.trim($("#parentCode").val());
		if(parentCode==null || parentCode==""){
			alert("请填写父级编号");
			return;
		}
		$("#allProjectForm").attr("action","<common:WebRoot/>/v/config/edit").submit();
		$("button[name=btn]").attr("disabled",true);
	}
		
	</script>
  </head>
<body>

<div class="admin">
 <div class="tab">
  <div class="tab-head">
        <strong>系统设置</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">系统设置</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/v/config/edit">
        		<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"/>
        		<input type="hidden" id="id" name="id" value="${sysconfig.id}">
				<div class="form-group">
                	<div class="label">
                		<label>名称<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="name" name="name" value="${sysconfig.name }" 
                			size="50" maxlength="45"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>编号<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="code" name="code" value="${sysconfig.code}" 
                			size="50" maxlength="125"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>父级名称</label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="parentName" name="parentName" value="${sysconfig.parentName}" 
                			size="50" maxlength="45"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>父级编号<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="parentCode" name="parentCode" value="${sysconfig.parentCode}" 
                			size="50" maxlength="125" />
                    </div>
                </div>   
                <div class="form-group">
                    <div class="label">
                		<label>值</label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="value" name="value" value="${sysconfig.value}" 
                			size="50" maxlength="255" />
                    </div>
                </div>   
	            <div class="form-group">
                    <div class="label"><label for="sitename">备注</label></div>
                    <div class="field">
                    	<textarea name="remark" id="remark" class="input" cols="50" rows="5" placeholder="这里写备注">${sysconfig.remark}</textarea>
                    </div>
                </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisConfig();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
</body>
</html>
