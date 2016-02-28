<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>资讯增加/编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<common:WebRoot/>/resources/css/image.css">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript" charset="utf-8" src="<common:WebRoot/>/resources/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<common:WebRoot/>/resources/ueditor/ueditor.all.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
			if($("#w-img").attr("src")!=''){
				$("#w-img").css({"width":"122px"});
				$("#w-img").css({"height":"122px"});
			}
			$("#inputfile").change(function(){
		     	 if(fileChange(this,'inputfile')){
			        var url = getObjectURL(this.files[0]);
			        if (url) {
			        	$("#w-img").attr("src",url).show();
			            $("#close").show();
			            $("#w-img").css({"width":"122px"});
						$("#w-img").css({"height":"122px"});
			        }
			     }
		    });
		});
		function saveThisInfo(){	
			var title = $.trim($("#title").val());
			if(title==null || title==""){
				alert("请填写标题");
				$("#title").focus();
				return;
			}
			var detail = $.trim($("#detail").val());
			if(detail==null || detail==""){
				alert("请填写内容详情");
				$("#detail").focus();
				return;
			}
			var imgFile =$("#w-img").attr("src");
			if(imgFile==null || imgFile==""){
				alert("请上传首页图片");
				return ;
			}
			var sort = $.trim($("#sort").val());
			if(sort!=null && sort!=""){
				var re1 = /^-?\d+$/;
			   	if (!re1.test(sort)) { 
			       alert("请输入正确的排序"); 
			       $("#sort").focus();
			       return;
			   	}
			}
			$("#allProjectForm").attr("action","<common:WebRoot/>/v/info/edit").submit();
			$("button[name=btn]").attr("disabled",true);
		}
		//上传图片的大小控制
		var isIE = /msie/i.test(navigator.userAgent) && !window.opera;  
		function fileChange(target,id) {
	        var fileSize = 0;          
	        if (isIE && !target.files) {      
	              var filePath = target.value;     
	              var fileSystem = new ActiveXObject("Scripting.FileSystemObject");  
	                
	              if(!fileSystem.FileExists(filePath)){  
	                 alert("附件不存在，请重新输入！");  
	                 return false;  
	              }  
	              var file = fileSystem.GetFile (filePath);  
	              fileSize = file.Size;     
	        } else {     
	              fileSize = target.files[0].size;   
	        }    
	        var size = fileSize / 1024;    
	        if(size>2048){   
	             alert("图片大小不能大于2M！");   
	             return false; 
	        }    
	        if(size<=0){  
	            alert("图片大小不能为0M！");   
	             return false; 
	        }  
	        return true;
		}
		function removeClose(obj){
			$("#close").hide();
			$("#inputfile").remove();
			var html = "<input type=\"file\" multiple id=\"inputfile\" name=\"imgFile\" class=\"inputfile\">";
			$(obj).parent().append(html);
			$("#w-img").attr("src","").hide();
		    $("#img").show();
		    $("#w-img").css({"width":""});
			$("#w-img").css({"height":""});
		  	var pathId = $("#path").val();
		   	if(pathId!=''){
		   		$("#nopath").val(pathId);
		   	}
		    $("#inputfile").change(function(){
		    	var url = getObjectURL(this.files[0]);
		        if (url) {
		            $("#w-img").attr("src",url).show();
		            $("#img").hide();
		            $("#close").show();
		            $("#w-img").css({"width":"122px"});
					$("#w-img").css({"height":"122px"});
		        }
		    });
		}
		//建立一個可存取到該file的url  
	   function getObjectURL(file) {  
	       var url = null ;  
	       if (window.createObjectURL!=undefined) { // basic  
	           url = window.createObjectURL(file) ;  
	       } else if (window.URL!=undefined) { // mozilla(firefox)  
	           url = window.URL.createObjectURL(file) ;  
	       } else if (window.webkitURL!=undefined) { // webkit or chrome  
	           url = window.webkitURL.createObjectURL(file) ;  
	       }  
	       return url ;  
	   }   	
	</script>
	<style type="text/css">
		.imgmsg{
			float: left; 
			margin-top: 6px; 
			font-size: 14px; 
			color: #ff6600;	
		}
		.b-contant ul { width: 100%; overflow: hidden;}
	</style>
  </head>
<body>

<div class="admin">
<div class="tab">
  <div class="tab-head">
        <strong>公告资讯</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">发布资讯</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/v/info/edit" enctype="multipart/form-data">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="infoId" name="infoId" value="${info.infoId}">
				<div class="form-group">
                	<div class="label">
                		<label>类型<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<select name="type" id="type" class="input">
                    		<c:forEach var="v" items="${typelist}" >
								<option value="${v.value}" <c:if test="${info.type==v.value}">selected</c:if> >${v.name}</option>
							</c:forEach>
                		</select>
                    </div>
                </div>
				<div class="form-group">
                	<div class="label">
                		<label>标题<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="title" name="title" value="${info.title }" placeholder="填写一下你的标题吧" size="50" maxlength="100"/>
                    </div>
                </div>
                <div class="form-group">
                	<div class="label">
                		<label>首页图片<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<div class="b-contant">
						   	<ul id="goods_pic">
					   			<span class="bg-color mr15">
					      			<p class="text-center">
					      				<c:if test="${info.path!=null && info.path!=''}">
					      					<img class="w-img" id="w-img" src="<c:if test="${info.path!='' && info.path!='null' && info.path!=null && !fn:containsIgnoreCase(info.path,'http://')}">${domain}</c:if>${info.path}">
					       				</c:if>
					      				<c:if test="${info.path==null || info.path==''}">
					      					<img class="w-img" id="w-img" src="">
					      				</c:if>
					      				<img class="b-p" id="img" src="<common:WebRoot/>/resources/images/plus.png">
					      			</p>
					      			<p class="b-p1">上传首页图片</p>
					      			<input type="hidden" name="path" value="${info.path}" id="logo"/>
					      			<input type="hidden" name="nopath" id="nopath" value="">
					      			<input type="file" multiple  id="inputfile" name="imgFile" class="inputfile"  >
					      			<p class="b-close" id="close" onclick="removeClose(this)" <c:if test="${info.path==null || info.path==''}"> style="display: none;" </c:if>></p>
					      		</span>
						       </ul>
						       <span class="imgmsg">建议尺寸500*300像素,最小尺寸183*137像素</span>
	    				</div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>排序(只能输入整数，为空则默认为0)</label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="sort" name="sort" value="${info.sort}" size="50"/>
                    </div>
                </div>  
                <div class="form-group">
                	<div class="label">
                		<label>简介</label>
                	</div>
                	<div class="field">
                		<textarea name="summary" id="summary" class="input" cols="50" rows="5" placeholder="这里写你的简介">${info.summary}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>内容详情<font color="red">*</font></label>
                	</div>
                	<div class="field">
     					<textarea name="detail" id="detail" placeholder="这里写你的初始化内容详情"></textarea>
                    	<script type="text/javascript">
                    		var ue = UE.getEditor('detail',{
                    			autoClearinitialContent:true,
                    			initialFrameWidth :998,
                    			initialFrameHeight:500,
                    			//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            					toolbars:[['fullscreen', 'source', '|', 'undo', 'redo', '|',
            					            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
            					            'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
            					            'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
            					            'directionalityltr', 'directionalityrtl', 'indent', '|',
            					            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
            					            'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
            					            'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
            					            'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
            					            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
            					            'print', 'preview', 'searchreplace', 'help', 'drafts']],
                    			autoClearinitialContent:true,
                    			wordCount:false,
                    			//关闭elementPath
            					elementPathEnabled:false
                    		});
							//对编辑器的操作最好在编辑器ready之后再做
							ue.ready(function() {
							    //设置编辑器的内容
							    ue.setContent($("#detailDiv").html());
							});
                    	</script>
                    </div>
                </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisInfo();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
    </div>
    </div>
</div>
<div id="detailDiv" style="display: none;">
	${info.detail}
</div>
</body>
</html>
