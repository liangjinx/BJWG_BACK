<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>服务协议设置</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript" charset="utf-8" src="<common:WebRoot/>/resources/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<common:WebRoot/>/resources/ueditor/ueditor.all.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
		});
	function saveThisConfig(){	
		var content = $.trim($("#content").val());
		if(content==null || content==""){
			alert("请填写内容");
			$("#value").focus();
			return;
		}
		$("#allProjectForm").attr("action","<common:WebRoot/>/v/pact/config/edit").submit();
		$("button[name=btn]").attr("disabled",true);
	}
	</script>
  </head>
<body>

<div class="admin">
 <div class="tab">
  <div class="tab-head">
        <strong>服务协议设置</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">服务协议设置</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/v/pact/config/edit">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="spId" name="spId" value="${protocol.spId}">
        		<div class="form-group">
                    <div class="label">
                		<label>类型<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<select name="type" id="type" class="input">
                    		<c:forEach var="pers" items="${typelist}" >
								<option value="${pers.value}" <c:if test="${protocol.type==pers.value}">selected</c:if> >${pers.name}</option>
							</c:forEach>
                		</select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>内容<font color="red">*</font></label>
                	</div>
                	<div class="field">
     					<textarea name="content" id="content" placeholder="这里写你的初始化内容"></textarea>
                    	<script type="text/javascript">
                    		var ue = UE.getEditor('content',{
                    			autoClearinitialContent:true,
                    			initialFrameWidth :998,
                    			initialFrameHeight:500,
                    			//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            					//toolbars:[['FullScreen', 'Source', 'Undo', 'Redo','Bold','test','simpleupload']],
            					toolbars:[['FullScreen', 'Source', 'Undo', 'Redo','Bold','test']],
                    			autoClearinitialContent:true,
                    			wordCount:false,
                    			//关闭elementPath
            					elementPathEnabled:false
                    		});
							//对编辑器的操作最好在编辑器ready之后再做
							ue.ready(function() {
							    //设置编辑器的内容
							    ue.setContent('${protocol.content}');
							});
                    	</script>
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
