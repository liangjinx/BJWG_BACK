<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    <title>抢购项目增加/编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<link rel="stylesheet" href="<common:WebRoot/>/resources/css/image.css">
	<style type="text/css">
		.price td{
			border-top:0px solid #ddd;
		}
	</style>
	<script type="text/javascript" src="<common:WebRoot/>/resources/json/json2.js"></script>
    <script type="text/javascript" src="<common:WebRoot/>/resources/json/jquery.json-2.3.min.js"></script>
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
		function saveThisProject(){	
			var name = $.trim($("#name").val());
			if(name==null || name==""){
				alert("请填写项目周期");
				$("#title").focus();
				return;
			}
			
			var price = $.trim($("#price").val());
			if(price==null || price==""){
				alert("请填写猪仔单价");
				$("#price").focus();
				return;
			}
			var re = /^\d+(?=\.{0,1}\d+$|$)/;
			if(price!=null && price!=""){
				if(!re.test(price)){
					alert("请填写正确的猪仔单价");
					$("#price").focus();
					return;
				}
			}
			
			var num = $.trim($("#num").val());
			if(num==null || num==""){
				alert("请填写猪仔数量");
				$("#num").focus();
				return;
			}
			var re1 = /^([1-9]\d*|[0]{1,1})$/;
		   	if (!re1.test(num)) { 
		       alert("请输入正确的猪仔数量"); 
		       $("#num").focus();
		       return;
		   	}
			
			if(parseInt(num)>32767){
				alert("猪仔数量最大值为32767"); 
		       $("#num").focus();
		       return;
			}
			/**
			var totalMoney = $.trim($("#totalMoney").val());
			if(totalMoney==null || totalMoney==""){
				alert("请填写单只总费用");
				$("#totalMoney").focus();
				return;
			}
			if(totalMoney!=null && totalMoney!=""){
				if(!re.test(totalMoney)){
					alert("请填写正确的单只总费用");
					$("#totalMoney").focus();
					return;
				}
			}
			var otherFee = $.trim($("#otherFee").val());
			if(otherFee==null || otherFee==""){
				alert("请填写单只其他费用");
				$("#otherFee").focus();
				return;
			}
			if(otherFee!=null && otherFee!=""){
				if(!re.test(otherFee)){
					alert("请填写正确的单只其他费用");
					$("#otherFee").focus();
					return;
				}
			}
			*/
			
			var valSt = $("#stime").val();
			var valEnd = $("#etime").val();
			if(valSt==null || valSt==""){
				alert("请填写开始抢购时间");
				$("#start").focus();
				return;
			}
			if(valEnd==null || valEnd==""){
				alert("请填写结束抢购时间");
				$("#end").focus();
				return;
			}
			if(valSt!="" && valEnd!=""){
		    	var date1 = new Date(valSt.replace(/\-/g, "\/"));// /g 全文搜索,/i 忽略大小写,也可以这样写： /gi
				var date2 = new Date(valEnd.replace(/\-/g, "\/"));
		    	var a = (date2 - date1)/(1000*60);
			    if (a < 0) {
			        alert("开始抢购时间需在结束抢购时间之前，请重新选择");
			        return;
			    }
		    }
		    $("#otherFeeDetail").val(UE.getEditor('otherFeeDetail').getContent());
			var content = $("#otherFeeDetail").val();
			if(content==null || content==""){
				alert("请填写其他费用详情");
				UE.getEditor('otherFeeDetail').focus();
				return;
			}
			/**
			var flag = true;
			var priceModelArr = new Array();
			//费用阶梯 js 
			$("#group-table").find("input[id^='feename']").each(function(i){
				var index = $(this).attr("id").substring(7,$(this).attr("id").length);
				var feename = $.trim($("#feename"+index).val());
				if(feename==null || feename==""){
					alert("请填写费用名称");
					$("#feename"+index).val('');
					$("#feename"+index).focus();
					flag = false;
					return flag;
				}
				var pri = $.trim($("#pri"+index).val());
				if(pri==null || pri=="" || pri=="0"){
					alert("请填写价格区间");
					$("#pri"+index).val('');
					$("#pri"+index).focus();
					flag = false;
					return flag;
				}
				
			    if(!re.test(pri)) { 
			        alert("请输入正确的价格区间"); 
			        $("#pri"+index).val('');
					$("#pri"+index).focus();
					flag = false;
					return flag;
			    } 
			    
			    if(pri.indexOf(".")>=0){
			    	if(pri.substring(0,pri.indexOf(".")).length>8){
			    		alert("请输入正确的价格区间");
			    		flag = false;
						return flag;
			    	}
			    	if(pri.substring(pri.indexOf(".")+1,pri.length).length>2){
			    		alert("请输入正确的价格区间");
			    		flag = false;
						return flag;
			    	}
			    }else{
			    	if(pri.substring(0,pri.length).length>8){
			    		alert("请输入正确的价格区间");
			    		flag = false;
						return flag;
			    	}
			    }
				var price_model = new PriceModel(feename,pri);
				priceModelArr.push(price_model);
			});
			if(!flag){
				return;
			}
			$("#otherFeeDetail").val(JSON.stringify(priceModelArr));
			*/
			$("#allProjectForm").attr("action","<common:WebRoot/>/v/project/edit").submit();
			$("button[name=btn]").attr("disabled",true);
		}
		/**
	    //价格对象
		function PriceModel(feename, price) {
			this.feename = feename;//费用名称
			this.price = price;//价格
		}
		*/
		/**
		 * 新增一个价格阶梯
		 */
		 /**
		function addLadder(obj, amount, price){
		  var src  = obj.parentNode.parentNode.parentNode;
		  var idx  = $("#group-table tr").length;
		  var tbl  = document.getElementById('group-table');
		  var row  = tbl.insertRow(idx);
		  var cell = row.insertCell(-1);
		  cell.innerHTML = '';
		  var cell = row.insertCell(-1);
		  var curIndex = parseInt($("#priceSize").val())+1;
		  cell.innerHTML = "<font size='2px;'>费用名称:<input size='30' type='text' name='feename' id='feename"+(curIndex+1)+"'/>&nbsp;&nbsp;价格:<input size='30' type='text' name='pri' id='pri"+(curIndex+1)+"' placeholder='0'/><a href='javascript:;' onclick='removeLadder(this)'><strong>[-]</strong></a></font>";
		  $("#priceSize").val(curIndex);
		}
		*/
		/**  
		 * 删除一个价格阶梯
		 */
		 /**
		function removeLadder(obj){
		  var row = obj.parentNode.parentNode.parentNode.rowIndex;
		  var tbl = document.getElementById('group-table');
		  tbl.deleteRow(row);
		}
		*/
		//时间选择
		function selectDate() { 
			WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', isShowToday: false, isShowClear: false});
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
        <strong>项目</strong>
        <ul class="tab-nav">
          <li class="active"><a href="#tab-set">发布抢购项目</a></li>
        </ul>
      </div>
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        	<form id="allProjectForm" name="allProjectForm" method="post" class="form-x" enctype="multipart/form-data" action="<common:WebRoot/>/v/project/edit">
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="paincbuyProjectId" name="paincbuyProjectId" value="${project.paincbuyProjectId}">
        		<!-- 
        		<input type="hidden" name="otherFeeDetail" id="otherFeeDetail" value="${product.otherFeeDetail}"/>
				<input type="hidden" name="priceSize" id="priceSize" value="${priceSize}"/>
				 -->
				<div class="form-group">
                	<div class="label">
                		<label>类型<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<select name="type" id="type" class="input">
                    		<c:forEach var="v" items="${typelist}" >
								<option value="${v.value}" <c:if test="${project.type==v.value}">selected</c:if> >${v.name}</option>
							</c:forEach>
                		</select>
                    </div>
                </div>
                <!-- 
                <div class="form-group">
                	<div class="label">
                		<label>品种<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<select name="variety" id="variety" class="input">
                    		<c:forEach var="v" items="${varietylist}" >
								<option value="${v.value}" <c:if test="${project.variety==v.value}">selected</c:if> >${v.name}</option>
							</c:forEach>
                		</select>
                    </div>
                </div>
                 -->
				<div class="form-group">
                	<div class="label">
                		<label>项目周期<font color="red">*</font></label>
                	</div>
                	<div class="field">
                		<input type="text" class="input" id="name" name="name" value="${project.name }" placeholder="填写一下你的名称吧" size="50" maxlength="100"/>
                    </div>
                </div>
                <div class="form-group">
                	<div class="label">
                		<label>简介</label>
                	</div>
                	<div class="field">
                		<textarea name="summary" id="summary" class="input" cols="50" rows="5" placeholder="这里写你的简介">${project.summary}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="logo">图片</label></div>
                    <div class="field">
	                    <div class="b-contant">
						   	<ul id="goods_pic">
						   			<span class="bg-color mr15">
						      			<p class="text-center">
						      				<c:if test="${project.imgs!=null && project.imgs!=''}">
						      					<img class="w-img" id="w-img" src="<c:if test="${project.imgs!='' && project.imgs!='null' && project.imgs!=null && !fn:containsIgnoreCase(project.imgs,'http://')}">${domain}</c:if>${project.imgs}">
						       				</c:if>
						      				<c:if test="${project.imgs==null || project.imgs==''}">
						      					<img class="w-img" id="w-img" src="">
						      				</c:if>
						      				<img class="b-p" id="img" src="<common:WebRoot/>/resources/images/plus.png">
						      			</p>
						      			<p class="b-p1">上传项目图片</p>
						      			<input type="hidden" name="path" value="${project.imgs}" id="path"/>
						      			<input type="hidden" name="nopath" id="nopath" value="">
						      			<input type="file" multiple  id="inputfile" name="imgFile" class="inputfile"  >
						      			<p class="b-close" id="close" onclick="removeClose(this)" <c:if test="${project.imgs==null || project.imgs==''}"> style="display: none;" </c:if>></p>
						      		</span>
						       </ul>
						       <span class="imgmsg">建议尺寸300*215像素</span>
	    				</div>
    				</div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="keywords">猪仔单价（元）<font color="red">*</font></label></div>
                    <div class="field">
                   		<input class="input" size="50" type="text" name="price" id="price" value="${project.price}" placeholder="必填项"/>
                    </div>
                </div>
                <!--
                <div class="form-group">
                    <div class="label"><label for="keywords">单只总费用<font color="red">*</font></label></div>
                    <div class="field">
                   		<input class="input" size="50" type="text" name="totalMoney" id="totalMoney" value="${project.totalMoney}" placeholder="必填项"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="keywords">单只其他费用<font color="red">*</font></label></div>
                    <div class="field">
                   		<input class="input" size="50" type="text" name="otherFee" id="otherFee" value="${project.otherFee}" placeholder="必填项"/>
                    </div>
                </div>
                  -->
                <div class="form-group">
                    <div class="label"><label for="keywords">猪仔数量<font color="red">*</font></label></div>
                    <div class="field">
                   		<input class="input" size="50" type="text" name="num" id="num" value="${project.num}" placeholder="必填项" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label"><label for="stime">开始抢购时间<font color="red">*</font></label></div>
                    <div class="field">
                    	<input class="input" size="50" type="text" readonly="readonly" name="stimeStr" value="${stimeStr}" id="stime" onclick="javascript:selectDate();" placeholder="必填项"/>
	                </div>
	            </div>
	           <div class="form-group">
                    <div class="label"><label for="etime">结束抢购时间<font color="red">*</font></label></div>
                    <div class="field">
                    	<input class="input" size="50" type="text" readonly="readonly" name="etimeStr" value="${etimeStr}" id="etime" onclick="javascript:selectDate();" placeholder="必填项"/>
	                </div>
	            </div>
                <div class="form-group">
                    <div class="label">
                		<label>其他费用详情<font color="red">*</font></label>
                	</div>
                	<div class="field">
     					<textarea name="otherFeeDetail" id="otherFeeDetail" placeholder="这里写你的初始化内容详情"></textarea>
                    	<script type="text/javascript">
                    		var ue = UE.getEditor('otherFeeDetail',{
                    			autoClearinitialContent:true,
                    			initialFrameWidth :998,
                    			initialFrameHeight:500,
                    			//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            					toolbars:[['FullScreen', 'Source', 'Undo', 'Redo','Bold','test']],
                    			autoClearinitialContent:true,
                    			wordCount:false,
                    			//关闭elementPath
            					elementPathEnabled:false
                    		});
							//对编辑器的操作最好在编辑器ready之后再做
							ue.ready(function() {
							    //设置编辑器的内容
							    ue.setContent($("#otherFeeDetailHtml").html());
							});
                    	</script>
                    </div>
                </div>
                <div class="form-group">
                    <div class="label">
                		<label>项目详情</label>
                	</div>
                	<div class="field">
     					<textarea name="detail" id="detail" placeholder="这里写你的项目详情"></textarea>
                    	<script type="text/javascript">
                    		var ue2 = UE.getEditor('detail',{
                    			autoClearinitialContent:true,
                    			initialFrameWidth :998,
                    			initialFrameHeight:500,
                    			//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            					toolbars:[['FullScreen', 'Source', 'Undo', 'Redo','Bold','test','simpleupload','insertvideo']],
                    			autoClearinitialContent:true,
                    			wordCount:false,
                    			//关闭elementPath
            					elementPathEnabled:false
                    		});
							//对编辑器的操作最好在编辑器ready之后再做
							ue2.ready(function() {
							    //设置编辑器的内容
							    ue2.setContent($("#detailHtml").html());
							});
                    	</script>
                    </div>
                </div>
                <!-- 
                <div class="form-group">
                    <div class="label"><label for="keywords">其他费用详情<font color="red">*</font></label></div>
                    <div class="field">
                    	<table id="group-table" class="table price">
                    		<c:if test="${priceSize <= 0}">
                    			<tr><td>&nbsp;</td><td><font size="2px;">费用名称:<input size="30" type="text" name="feename" id="feename1">&nbsp;&nbsp;价格<input size="30" type="text" name="pri" id="pri1"  placeholder="0"/><a href="javascript:;" onclick="addLadder(this)"><strong>[+]</strong></a></font></td></tr>
                    		</c:if>
                    		<c:if test="${priceSize > 0}">
	                			<c:forEach items="${priceList}" var="info" varStatus="status">
	               					<c:if test="${status.index==0}">
	               						<tr><td>&nbsp;</td><td><font size="2px;">费用名称:<input size="30" type="text" name="feename" id="feename${status.index+1}" value="${info.feename}"/>&nbsp;&nbsp;价格<input size="30" type="text" name="pri" id="pri${status.index+1}" value="${info.price}"/><a href="javascript:;" onclick="addLadder(this)"><strong>[+]</strong></a></font></td></tr>
	               					</c:if>
	               					<c:if test="${status.index!=0}">
	               						<tr><td>&nbsp;</td><td><font size="2px;">费用名称:<input size="30" type="text" name="feename" id="feename${status.index+1}" value="${info.feename}"/>&nbsp;&nbsp;价格<input size="30" type="text" name="pri" id="pri${status.index+1}" value="${info.price}"/><a href="javascript:;" onclick="removeLadder(this)"><strong>[-]</strong></a></font></td></tr>
	               					</c:if>
	                			</c:forEach>
                			</c:if>
                    	</table>
                    </div>
                </div>
                -->
                <div class="form-button">
                	<button class="button bg-main" type="button" name="btn" onclick="saveThisProject();">提交</button>
                	<button class="button bg-main" type="button" name="btn" onclick="history.back();">返回</button>
                </div>
            </form>
        </div>
    </div>
    </div>
</div>
<div id="otherFeeDetailHtml" style="display: none;">
	${project.otherFeeDetail}
</div>
<div id="detailHtml" style="display: none;">
	${project.detail}
</div>
</body>
</html>
