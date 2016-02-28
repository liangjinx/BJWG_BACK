<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix= "form" uri= "http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>">
    
    <title>修改用户</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common.jsp" flush="true"></jsp:include>
	<link rel="stylesheet" href="<common:WebRoot/>/resources/css/image.css">
<script type="text/javascript">
$(function(){

   $("#tab-set2").css("display","none");
var su=$("#tab-set");
var bu=$("#tab-set2");
  if(<%=request.getAttribute("fg")%>=='2'){
  $("#tab-set").remove();
 $("#hf").html(bu);
   $("#tab-set2").css("display","block");

  }
  

  
  
  $("#suser").click(function(){

$("#tab-set2").remove();
  $("#hf").html(su);
     $("#tab-set").css("display","block");

  });
  
  $("#buser").click(function(){
  $("#tab-set").remove();
 $("#hf").html(bu);
   $("#tab-set2").css("display","block");
  
  });


	//add by oyja
	var pic_count = $('#goods_pic span').length;
	if (pic_count > 2) {
		$('span.bg-color').hide();
	};
	if($("#w-img").attr("src")!=''){
		$("#w-img").css({"width":"122px"});
		$("#w-img").css({"height":"122px"});
	}
	commonTip("${requestScope.msg}");
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
 /**
 * 保存用户
 */
function saveThisUser(){

	var username = $.trim($("#username").val());
	
	if(username==null || username==""){
		alert("请填写用户名称");
		$("#username").focus();
		return;
	}
	var password = $("#password").val();
	if(password==null || password==""){
		alert("请填写用户密码");
		$("#password").focus();
		return;
	}
	var phone = $.trim($("#phone").val());
	if(phone==null || phone==""){
	alert("a="+phone);
		alert("请填写手机号码");
		$("#phone").focus();
		return;
	}
	if(phone!=null && phone!=""){
		var re = /^0?1[3|4|5|8][0-9]\d{8} || ^[48]00\d{7} || ^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/;
		if(!re.test(phone)){
			alert("请填写正确的手机号码");
			$("#phone").val("");
			$("#phone").focus();
			return;
		}
	}
	var email = $.trim($("#email").val());
	if(email!=null && email!=''){
		var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if(!myreg.test(email)){
			alert("请填写正确的邮箱");
			$("#email").val("");
			$("#email").focus();
			return;
		}
	}
	
	$("#allProjectForm").attr("action","<common:WebRoot/>/v/user/edit").submit();
	$("button[name=btn]").attr("disabled",true);
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
	  	var logoId = $("#headImg").val();
	   	if(logoId!=''){
	   		$("#noheadImg").val(logoId);
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
  
 <!-- 主体 -->
<div class="admin">

    <div class="tab">
      <div class="tab-head">
        <strong>用户</strong>
        <ul class="tab-nav">
          <li class="active"  id="suser"><a href="#tab-set">个人用户设置</a></li>
            <li class="active" id="buser"><a href="#tab-set2" >企业用户设置</a></li>
        </ul>
      </div>
      <div class="tab-body" id="hf">
        <br />
        <div class="tab-panel active" id="tab-set">
        	 <form action="<common:WebRoot/>/nv/user/edit" method="post" class="form-x" enctype="multipart/form-data" id="allProjectForm">
        	 
                <input type="hidden" name="typeAU" id="typeAU" value="${type}" />
                <input type="hidden" name="flag" id="flag" value="1" />          
				<input type="hidden" name="userId" id="userId" value="${user.userId}" />
				<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"/>
				<div class="form-group">
                    <div class="label"><label for="readme">用户名称<font color="red">*</font></label></div>
                    <div class="field">
                    	<input type="text"  class="input" size="50" id="username" name="username" class="span6" value="${user.username}" placeholder="用户名称"/>
                    </div>
                </div>
				 <div class="form-group">
                    <div class="label"><label for="readme">用户密码<font color="red">*</font></label></div>
                    <div class="field">
                    	<input type="password"  class="input" size="50" id="password" name="password" class="span6" value="${user.password}" placeholder="用户密码"/>
                    </div>
                </div>
                 <div class="form-group">
                    <div class="label"><label for="readme">用户昵称</label></div>
                    <div class="field">
                    	<input type="text"  class="input" size="50" id="nickname" name="nickname" class="span6" value="${user.nickname}" placeholder="用户昵称"/>
                    </div>
                </div>
				 <div class="form-group">
                    <div class="label"><label for="logo">用户头像</label></div>
                    <div class="field">
	                    <div class="b-contant">
						   	<ul id="goods_pic">
						   			<span class="bg-color mr15">
						      			<p class="text-center">
						      				<c:if test="${user.headImg!=null && user.headImg!=''}">
						      					<img class="w-img" id="w-img" src="<c:if test="${user.headImg!='' && user.headImg!='null' && user.headImg!=null && !fn:containsIgnoreCase(user.headImg,'http://')}">${domain}</c:if>${user.headImg}">
						      				</c:if>
						      				<c:if test="${user.headImg==null || user.headImg==''}">
						      					<img class="w-img" id="w-img" src="">
						      				</c:if>
						      				<img class="b-p" id="img" src="<common:WebRoot/>/resources/images/plus.png">
						      			</p>
						      			<p class="b-p1">上传用户头像</p>
						      			<input type="hidden" name="headImg" value="${user.headImg}" id="headImg"/>
						      			<input type="hidden" name="noheadImg" id="noheadImg" value="">
						      			<input type="file" multiple  id="inputfile" name="imgFile" class="inputfile"  >
						      			<p class="b-close" id="close" onclick="removeClose(this)" <c:if test="${user.headImg==null || user.headImg==''}"> style="display: none;" </c:if>></p>
						      		</span>
						       </ul>
						       <span class="imgmsg">建议尺寸80*80像素</span>
	    				</div>
    				</div>
                </div>
                
                <div class="form-group">
                    <div class="label"><label for="siteurl">手机号码<font color="red">*</font></label></div>
                    <div class="field">
                    	 <input type="text" name="phone" id="phone" value="${user.phone}" class="input" size="50" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="siteurl">邮箱</label></div>
                    <div class="field">
                    	 <input type="text" name="email" id="email" value="${user.email }" class="input" size="50"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="siteurl">性别</label></div>
                    <div class="field">
                    	<input type="radio" value="1" name="sex" <c:if test="${user.sex!=0}"> checked="checked"</c:if>>  男
						<input type="radio" value="0" name="sex" <c:if test="${user.sex==0}"> checked="checked"</c:if>>  女
					</div>
                </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisUser();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
  
      
      
      
         
     <!-- 企业用户 -->
      
        <div class="tab-panel active" id="tab-set2" id="busiUser">
        	 <form action="<common:WebRoot/>/nv/user/edit" method="post" class="form-x" enctype="multipart/form-data" id="allProjectForm">
        	 
                <input type="hidden" name="typeAU" id="typeAU" value="${type}" />
                 <input type="hidden" name="flag" id="flag" value="2" />
				<input type="hidden" name="userId" id="userId" value="${user.userId}" />
				<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"/>
				<div class="form-group">
                    <div class="label"><label for="readme">用户名<font color="red">*</font></label></div>
                    <div class="field">
                    	<input type="text"  class="input" size="50" id="username" name="username" class="span6" value="${user.username}" placeholder="用户名称"/>
                    </div>
                </div>
				 <div class="form-group">
                    <div class="label"><label for="readme">密码<font color="red">*</font></label></div>
                    <div class="field">
                    	<input type="password"  class="input" size="50" id="password" name="password" class="span6" value="${user.password}" placeholder="用户密码"/>
                    </div>
                </div>
                 <div class="form-group">
                    <div class="label"><label for="readme">公司名称</label></div>
                    <div class="field">
                    	<input type="text"  class="input" size="50" id="nickname" name="nickname" class="span6" value="${user.nickname}" placeholder="用户昵称"/>
                    </div>
                </div>
				 <div class="form-group">
                    <div class="label"><label for="logo">企业头像</label></div>
                    <div class="field">
	                    <div class="b-contant">
						   	<ul id="goods_pic">
						   			<span class="bg-color mr15">
						      			<p class="text-center">
						      				<c:if test="${user.headImg!=null && user.headImg!=''}">
						      					<img class="w-img" id="w-img" src="<c:if test="${user.headImg!='' && user.headImg!='null' && user.headImg!=null && !fn:containsIgnoreCase(user.headImg,'http://')}">${domain}</c:if>${user.headImg}">
						      				</c:if>
						      				<c:if test="${user.headImg==null || user.headImg==''}">
						      					<img class="w-img" id="w-img" src="">
						      				</c:if>
						      				<img class="b-p" id="img" src="<common:WebRoot/>/resources/images/plus.png">
						      			</p>
						      			<p class="b-p1">上传企业头像</p>
						      			<input type="hidden" name="headImg" value="${user.headImg}" id="headImg"/>
						      			<input type="hidden" name="noheadImg" id="noheadImg" value="">
						      			<input type="file" multiple  id="inputfile" name="imgFile" class="inputfile"  >
						      			<p class="b-close" id="close" onclick="removeClose(this)" <c:if test="${user.headImg==null || user.headImg==''}"> style="display: none;" </c:if>></p>
						      		</span>
						       </ul>
						       <span class="imgmsg">建议尺寸80*80像素</span>
	    				</div>
    				</div>
                </div>
                
                <div class="form-group">
                    <div class="label"><label for="siteurl">企业联系号码<font color="red">*</font></label></div>
                    <div class="field">
                    	 <input type="text" name="phone" id="phone" value="${user.phone}" class="input" size="50" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="siteurl">企业法人</label></div>
                    <div class="field">
                    	 <input type="text" name="legalperson" id="nickname" value="${user.legalperson }" class="input" size="50"/>
                    </div>
                </div>
                  <div class="form-group">
                    <div class="label"><label for="siteurl">企业营业执照</label></div>
                    <div class="field">
                    	 <input type="text" name="businessLicense" id="nickname" value="${user.businessLicense }" class="input" size="50"/>
                    </div>
                </div>
                   <div class="form-group">
                    <div class="label"><label for="siteurl">企业地址</label></div>
                    <div class="field">
                    	 <input type="text" name="address" id="nickname" value="${user.address }" class="input" size="50"/>
                    </div>
                </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisUser();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
      </div> 
      
      
      
      
      
      
      
    </div>
</div>
  </body>
</html>
