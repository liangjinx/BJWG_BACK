<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>字典配置增加/编辑</title>
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
		function saveThisDict(){	
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
			$("#allProjectForm").attr("action","<common:WebRoot/>/v/dict/edit").submit();
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
          <li class="active"><a href="#tab-set">字典设置</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/v/dict/edit">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="id" name="id" value="${dict.dictId}">
				<div class="form-group">
                	<div class="label">
                		<label>名称<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="name" name="name" value="${dict.name }" 
                			size="50" maxlength="30"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>编号<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="code" name="code" value="${dict.code}" 
                			size="50" maxlength="30"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>值</label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="value" name="value" value="${dict.value}" 
                			size="50" maxlength="30" />
                    </div>
                </div>   
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisDict();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
</body>
  
</html>
