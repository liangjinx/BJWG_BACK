<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>线下门店增加/编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<link rel="stylesheet" href="<common:WebRoot/>/resources/css/image.css">
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
		    var code = '${shop.province}';
			var city = '${shop.city}';
			if(code!='' && code!=null){
				$.ajax({  
			   		type: "POST",  
			        url: '<common:WebRoot/>/nv/shop/city',  
			        data: "code="+code,
			        success: function(data){
				        var json = eval("("+data+")"); 
			 			var option = "";
				        $.each(json.des, function (i, item) { 
							if(item != null){
			                   //循环获取数据      
			                   var id = item.areaId; 
			                   var name = item.name;
			                   if(name!=null && name!="") {
				                   if( id == city){
				                   		option += "<option value='"+id+"' selected='selected' >"+name+"</option>";
				                   }else{
				                   		option += "<option value='"+id+"'>"+name+"</option>";
				                   }
			                   }
			                }
			            });  
						$("#select_city").html(option); 
					}  
				}); 
			}
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
		  	var logoId = $("#logo").val();
		   	if(logoId!=''){
		   		$("#nologo").val(logoId);
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
		function saveThisShop(){
			var imgFile =$("#w-img").attr("src");
			if(imgFile==null || imgFile==""){
				alert("请上传LOGO图片");
				return ;
			}
			var name = $.trim($("#name").val());
			if(name==null || name==""){
				alert("请填写店名");
				$("#name").focus();
				return;
			}
			var province = $("#select_province").val();
			if(province==null || province==""){
				alert("请选择省份");
				$("#select_province").focus();
				return;
			}
			var address = $.trim($("#address").val());
			if(address==null || address==""){
				alert("请填写详细地址，建议在100字以内");
				$("#address").focus();
				return;
			}
			
			$.ajax({
		 		url:"<common:WebRoot/>/nv/shop/checkAddress",
		 		data:{'address':address},
		 		success:function(data){
		 			//无效地址或者请求超时
					 if(data!="" && data!="操作成功" && data!=null){
						alert(data);
						return; 
					 }else{
					 	$("#allProjectForm").action="<common:WebRoot/>/v/shop/edit";
						$("#allProjectForm").submit();
						$("button[name=btn]").attr("disabled",true);
					 }
		 		},
		 		error:function(data){
		 		}
			});
		}
		//根据省份选择城市
		function cityName(){
			var code = $("#select_province").val();
			$.ajax({  
		   		type: "POST",  
		        url: '<common:WebRoot/>/nv/shop/city',  
		        data: "code="+code,
		        success: function(data){
		        var json = eval("("+data+")"); 
	 			var option = "";
		        $.each(json.des, function (i, item) {  
	                   //循环获取数据      
	                  if(item!=null){
		                  var id = item.areaId; 
		                   var name = item.name; 
		                   if(name!=null && name!="") {
		                   	option += "<option value='"+id+"'>"+name+"</option>";
		                   }
		              }
	               });  
					$("#select_city").html(option); 
				}  
			});  
		}
	</script>
  </head>
<body>

<div class="admin">
 <div class="tab">
  <div class="tab-head">
        <strong>线下门店</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">线下门店设置</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" action="<common:WebRoot/>/v/shop/edit" enctype="multipart/form-data">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="shopId" name="shopId" value="${shop.shopId}">
                <div class="form-group">
                    <div class="label"><label for="logo">LOGO<font color="red">*</font></label></div>
                    <div class="field">
	                    <div class="b-contant">
						   	<ul id="goods_pic">
						   			<span class="bg-color mr15">
						      			<p class="text-center">
						      				<c:if test="${shop.logo!=null && shop.logo!=''}">
						      					<img class="w-img" id="w-img" src="<c:if test="${shop.logo!='' && shop.logo!='null' && shop.logo!=null && !fn:containsIgnoreCase(shop.logo,'http://')}">${domain}</c:if>${shop.logo}">
						       				</c:if>
						      				<c:if test="${shop.logo==null || shop.logo==''}">
						      					<img class="w-img" id="w-img" src="">
						      				</c:if>
						      				<img class="b-p" id="img" src="<common:WebRoot/>/resources/images/plus.png">
						      			</p>
						      			<p class="b-p1">logo</p>
						      			<input type="hidden" name="logo" value="${shop.logo}" id="logo"/>
						      			<input type="hidden" name="nologo" id="nologo" value="">
						      			<input type="file" multiple  id="inputfile" name="imgFile" class="inputfile"  >
						      			<p class="b-close" id="close" onclick="removeClose(this)" <c:if test="${shop.logo==null || shop.logo==''}"> style="display: none;" </c:if>></p>
						      		</span>
						       </ul>
	    				</div>
    				</div>
                </div>
                <div class="form-group">
                	<div class="label">
                		<label>店名<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="name" name="name" value="${shop.name }" 
                			size="50" maxlength="100" placeholder="在100字以内"/>
                    </div>
                </div>
               <div class="form-group">
	              <div class="label"><label for="address">店铺地址<font color="red">*</font></label></div>
	              <div class="field">
	              	<select name="province" id="select_province"  onchange="cityName()" style="width: 25%;display:inline;" class="input"> 
	              	<!-- <select name="province" id="select_province"  onchange="cityName()" style="width: 10%;">-->
	              		<OPTION value="" >--请选择--</OPTION>
	              		<c:forEach items="${province}" var="pr" >
	              			<OPTION value="${pr.areaId}" <c:if test="${shop.province == pr.areaId}">selected="selected"</c:if> >${pr.name }</OPTION>
	              		</c:forEach>
	              	</select>
	              	<select name="city" id="select_city" style="width: 25%;display:inline;" class="input">
	              		<OPTION value="" >--请选择--</OPTION>
	              	</select>
	              	<input type="text" name="address" id="address" class="input" value="${shop.address }" style="width: 49%;display:inline;" placeholder="店铺地址，用于定位店铺，在100字以内"/>
	              </div>
               </div>
               <div class="form-group">
                    <div class="label"><label for="sitename">备注</label></div>
                    <div class="field">
                    	<textarea name="remark" id="remark" class="input" cols="50" rows="5" placeholder="这里写备注,在500字以内">${shop.remark}</textarea>
                    </div>
                </div>
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisShop();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
</body>
  
</html>
