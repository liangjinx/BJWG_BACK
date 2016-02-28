<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>首页轮播图增加/编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<common:WebRoot/>/resources/css/image.css">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
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
		  	var logoId = $("#path").val();
		   	if(logoId!=''){
		   		$("#nopath").val(logoId);
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
		function saveThisDict(){	
			var imgFile =$("#w-img").attr("src");
			if(imgFile==null || imgFile==""){
				alert("请上传轮播图图片");
				return ;
			}
			var name = $.trim($("#name").val());
			if(name==null || name==""){
				alert("请填写图片名称");
				$("#name").focus();
				return;
			}
			
			var link = $.trim($("#link").val());
			if(link!=''){
				//不是url格式
				if(!isURL(link)){
					alert("请输入正确的图片链接"); 
				    $("#link").focus();
				    return;
				}
			}
			
			var sort = $.trim($("#sort").val());
			if(sort!=null && sort!=""){
				var re1 = /^([1-9]\d*|[0]{1,1})$/;
			   	if (!re1.test(sort)) { 
			       alert("请输入正确的排序"); 
			       $("#sort").focus();
			       return;
			   	}
			}
			$("#allProjectForm").attr("action","<common:WebRoot/>/v/slide/edit").submit();
			$("button[name=btn]").attr("disabled",true);
		}
		// 验证url
		function isURL(str_url) {
			var strRegex = "[a-zA-z]+://[^\s]*";
			var re = new RegExp(strRegex);
			return re.test(str_url);
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
        <strong>系统管理</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">首页轮播图</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" enctype="multipart/form-data" action="<common:WebRoot/>/v/slide/edit">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="slideId" name="slideId" value="${slide.slideId}">
				 <div class="form-group">
                    <div class="label"><label for="logo">轮播图<font color="red">*</font></label></div>
                    <div class="field">
	                    <div class="b-contant">
						   	<ul id="goods_pic">
					   			<span class="bg-color mr15">
					      			<p class="text-center">
					      				<c:if test="${slide.path!=null && slide.path!=''}">
					      					<img class="w-img" id="w-img" src="<c:if test="${slide.path!='' && slide.path!='null' && slide.path!=null && !fn:containsIgnoreCase(slide.path,'http://')}">${domain}</c:if>${slide.path}">
					       				</c:if>
					      				<c:if test="${slide.path==null || slide.path==''}">
					      					<img class="w-img" id="w-img" src="">
					      				</c:if>
					      				<img class="b-p" id="img" src="<common:WebRoot/>/resources/images/plus.png">
					      			</p>
					      			<p class="b-p1">轮播图上传</p>
					      			<input type="hidden" name="path" value="${slide.path}" id="path"/>
					      			<input type="hidden" name="nopath" id="nopath" value="">
					      			<input type="file" multiple  id="inputfile" name="imgFile" class="inputfile"  >
					      			<p class="b-close" id="close" onclick="removeClose(this)" <c:if test="${slide.path==null || slide.path==''}"> style="display: none;" </c:if>></p>
					      		</span>
						       </ul>
						       <span class="imgmsg">建议尺寸1904*560像素</span>
	    				</div>
    				</div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>图片名称<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="name" name="name" value="${slide.name}" size="50"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>图片链接</label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="link" name="link" value="${slide.link}" size="50" />
                		<span class="imgmsg">以https|http|ftp|rtsp|mms://开头</span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>排序(只能输入整数)</label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="sort" name="sort" value="${slide.sort}" size="50" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>
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
