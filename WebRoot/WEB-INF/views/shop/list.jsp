<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<common:WebRoot/>/">
    
    <title>线下门店列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="/WEB-INF/views/common.jsp" flush="true"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function(){
			commonTip("${requestScope.msg}");
			var code = "${param['filter_EQI_t.province']}";
			var city = "${param['filter_EQI_t.city']}";
			if(code != null && code != ""){
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
		                   if( id == city){
		                   		option += "<option value='"+id+"' selected='selected' >"+name+"</option>";
		                   }else{
		                   		option += "<option value='"+id+"'>"+name+"</option>";
		                   }
		                  }
		               });  
						$("#select_city").html(option); 
					}  
				}); 
			} 
		});
		//搜索
		function search(){
			var url = "<common:WebRoot/>/v/shop/list?";
	    
	    	var name_sear = $("#name_sear").val();
	    	var address_sear = $("#address_sear").val();
	    	var serProvince = $("#select_province").val();
			var serCity = $("#select_city").val();
	    	
	    	if(serCity==null){
				serCity = '';
			}
	    	
	    	url+="filter_LIKES_t.name="+name_sear+"&filter_LIKES_t.address="+address_sear+"&filter_EQI_t.province="+serProvince+"&filter_EQI_t.city="+serCity;
	    	window.location.href = encodeURI(url);
		}
		/**
 * 资源编辑
 */
function shopEidt(type,id){
	var action = "";
	switch (type) {
	case 1://新增
		action = basePath + "/v/shop/editInit";
		break;
	case 2://修改
		action = basePath + "/v/shop/editInit";
		$("#id").val(id);
		break;
	case 3://单个删除
		action = basePath + "/v/shop/delete";
		if(confirm("是否确定删除记录？")){
			$("#id").val(id);
		}else{
			return ;
		}
		break;
	case 4://多个删除
		action = basePath + "/v/shop/delete";
		var idList = $(":input[name=idList]:checked");
		if(idList.length < 1){
			alert("请选择要删除的记录");
			return ;
		}else{
			if(!confirm("是否确定批量删除记录？")){
				return;
			}
		}
		break;
	default:
		break;
	}
	$("#allProjectForm").attr("action",action).submit();
	$("button[name=btn]").attr("disabled",true);
}
	//省市联动
	function cityName(){
		var code = $("#select_province").val();
		if(code != null && code != ""){
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
	}
	</script>
  </head>

<body>
<div class="admin">
      <div class="tab-body">
        <br />
        <div class="tab-panel active" id="tab-set">
        
        	<form action="<common:WebRoot/>/v/shop/list" method="post" id="allProjectForm" name="allProjectForm">
        	
        		<%-- 隐藏域 --%>
        		<input type="hidden" id="id" name="id" value="">
        		
			    <div class="panel admin-panel">
			    	<div class="panel-head"><strong>线下门店列表</strong></div>
			        <div class="padding border-bottom">
			            <input type="button" class="button button-small checkall" name="checkall" checkfor="idList" value="全选" />
			            <strong style="padding-left: 100px;">
				       		名称：<input type="text" name="filter_LIKES_t.name" id="name_sear" value="${param['filter_LIKES_t.name']}" size="15" />&nbsp;
				       		&nbsp;店铺地址：<select name="filter_EQI_t.province" id="select_province"  onchange="cityName()" style="width: 10%;"> 
                    		<OPTION value="" >--请选择--</OPTION>
                    		<c:forEach items="${province}" var="pr" >
                    			<OPTION value="${pr.areaId}" <c:if test="${param['filter_EQI_t.province'] == pr.areaId}">selected="selected"</c:if> >${pr.name }</OPTION>
                    		</c:forEach>
                    	</select>
                    	<select name="filter_EQI_t.city" id="select_city" style="width: 10%;">
                    		<OPTION value="" >--请选择--</OPTION>
                    	</select>
                    	&nbsp;<input type="text" name="filter_LIKES_t.address" id="address_sear" value="${param['filter_LIKES_t.address']}" size="40"/>&nbsp;
				       		<input type="button" class="button button-small border-blue" value="搜索" onclick="search();"/>&nbsp;
				       		<man:hasPermission name="shop_edit_init">
				            	<input type="button" class="button button-small border-green" value="新增线下门店" onclick="shopEidt(1)"/>
				            </man:hasPermission>
				            <man:hasPermission name="shop_delete">
				            	<input type="button" class="button button-small border-yellow" value="批量删除" onclick="shopEidt(4)"/>
				            </man:hasPermission>
			            </strong>
			            <!--<input type="button" class="button button-small border-blue" value="回收站" />
			        --></div>
			        <table class="table table-hover">
			        	<tr>
				        	<th width="50">选择</th>
				        	<th>店名</th>
				        	<th>地址</th>
				        	<th>操作</th>
			        	</tr>
			        	
			        	<c:set var="len" value="0"></c:set>
			        	<c:forEach items="${requestScope.list }" var="l">
				            <tr>
				            	<td><input type="checkbox" name="idList" id="idList" value="${l.shopId }" /></td>
				            	<td>${l.name }</td>
				            	<td>${l.provinceName}${l.cityName}${l.address}</td>
				            	<td>
				            		<man:hasPermission name="shop_edit_init">
				            			<a class="button border-blue button-little" href="#" onclick="shopEidt(2,${l.shopId });return false;">编辑</a>
				            		</man:hasPermission>
				            		<man:hasPermission name="shop_delete">
				            			<a class="button border-yellow button-little" href="#" onclick="shopEidt(3,${l.shopId });return false;">删除</a>
				            		</man:hasPermission>
				            	</td>
				            </tr>
			        	</c:forEach>
			        </table>
			        <jsp:include page="/WEB-INF/views/page.jsp" flush="true"></jsp:include>
			    </div>
			 </form>
        </div>
    </div>
</div>
</body>
</html>
				            			