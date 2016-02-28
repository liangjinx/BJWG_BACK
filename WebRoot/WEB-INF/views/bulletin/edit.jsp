<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>公告资讯增加/编辑</title>
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
		function saveThisBulletin(){	
			var title = $.trim($("#title").val());
			if(title==null || title==""){
				alert("请填写标题");
				$("#title").focus();
				return;
			}
			var content = $.trim($("#content").val());
			if(content==null || content==""){
				alert("请填写内容");
				$("#content").focus();
				return;
			}
			$("#allProjectForm").attr("action","<common:WebRoot/>/v/bulletin/edit").submit();
			$("button[name=btn]").attr("disabled",true);
		}
		
	</script>
  </head>
<body>

<div class="admin">
<div class="tab">
  <div class="tab-head">
        <strong>公告资讯</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">发布公告</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/v/bulletin/edit">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="bulletinId" name="bulletinId" value="${bulletin.bulletinId}">
				<div class="form-group">
                	<div class="label">
                		<label>类型<font color="red">*</font></label>
                	</div>
                	<div class="field" class="input">
                		<select name="type" id="type" class="input">
                    		<c:forEach var="v" items="${typelist}" >
								<option value="${v.value}" <c:if test="${bulletin.type==v.value}">selected</c:if> >${v.name}</option>
							</c:forEach>
                		</select>
                    </div>
                </div>
				<div class="form-group">
                	<div class="label">
                		<label>标题<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="title" name="title" value="${bulletin.title }" placeholder="填写一下你的标题吧" size="50" maxlength="100"/>
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
							    ue.setContent($("#contentDiv").html());
							});
                    	</script>
                    </div>
                </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisBulletin();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
<div id="contentDiv" style="display: none;">
	${bulletin.content}
</div>
</body>
</html>
