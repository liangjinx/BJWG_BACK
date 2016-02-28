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
			$("#allProjectForm").attr("action","<common:WebRoot/>/v/dictDetail/edit").submit();
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
          <li class="active"><a href="#tab-set">字典项明细设置</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/v/dictDetail/edit">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="dictDetailId" name="dictDetailId" value="${dictDetail.dictDetailId}">
				<div class="form-group">
                	<div class="label">
                		<label>字典</label>
                	</div>
                	<div class="field">
                		<select name="dictId" id="dictId" class="input">
                			<option value="">--请选择--</option>
                    		<c:forEach var="pers" items="${dictlist}" >
								<option value="${pers.dictId}" <c:if test="${dictDetail.dictId==pers.dictId}">selected</c:if> >${pers.name}</option>
							</c:forEach>
                		</select>
                    </div>
                </div>
				<div class="form-group">
                	<div class="label">
                		<label>名称<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="name" name="name" value="${dictDetail.name }" 
                			size="50"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>编号<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="code" name="code" value="${dictDetail.code}" 
                			size="50"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>值</label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="value" name="value" value="${dictDetail.value}" 
                			size="50" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>描述</label>
                	</div>
                	<div class="field">
                		<textarea name="remark" id="remark" class="input" cols="50" rows="5" placeholder="这里写备注">${dictDetail.remark}</textarea>  
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
