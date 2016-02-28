//admin.js
var basePath;
$(document).ready(function(){
	// js获得上下文路径
	if (basePath == null) 
	{
		if(document.getElementById("webRoot"))
		{
			basePath=document.getElementById("webRoot").value;
		}
	}
	if (basePath == null) 
	{
		if(window.top.document.getElementById("webRoot"))
		{
			basePath=window.top.document.getElementById("webRoot").value;
		}
	}
	
	if(basePath == null)
	{
		if(window.opener.window.top.document.getElementById("webRoot"))
		{
			basePath=window.opener.window.top.document.getElementById("webRoot").value;
		}	
	}
	
});
 

/***
 * alert提示窗口
 */
function commonTip(text)
{
	if(text && text !='')
	{
		alert(text);
	}
}

/**
 * 禁用按钮
 */
function disabledBtn(){
	
	$("button[name=btn]").attr("disabled",true);
}


/**
 * 保存服务
 */
function saveThisGood(){
	var shopId = $("#shopId").val();
	if(shopId==null || shopId==""){
		alert("请选择你的店铺名称");
		return ;
	}
	var name = $.trim($("#name").val());
	if(name==null || name==""){
		alert("请填写你的服务名称");
		return ;
	}
	var price = $.trim($("#price").val());
	if(price==null || price==""){
		alert("请填写服务价格");
		$("#price").focus();
		return;
	}
	if(price!=null && price!=""){
		var re = /^\d+(?=\.{0,1}\d+$|$)/;
		if(!re.test(price)){
			alert("请填写正确的服务价格");
			$("#price").focus();
			return;
		}
	}
	$("#allProjectForm").attr("action",basePath + "/nv/goods/submit").submit();
	disabledBtn();
}

/**
 * 保存幻灯图片
 */
function saveThisSlide(){
	var path =$("#path").val();
	var nopath =$("#nopath").val();
	
	if(path == "" && nopath!=""){
		alert("请上传首页幻灯图片");
		return ;
	}
	var imgFile =$("#w-img").attr("src");
	if(imgFile==null || imgFile==""){
		alert("请上传首页幻灯图片!"); 
		return ;
	}
	$("#allProjectForm").attr("action",basePath + "/nv/slide/submit").submit();
	//disabledBtn();
}
/**
 * 保存首页推荐
 */
function saveThisIndexTop(){
	
	var name =$("#name").val();
	
	if(name == ""){
		
		alert("首页推荐名称");
		
		return ;
	}
	
	var path =$("#path").val();
	
	if(path == ""){
		
		alert("请上传首页推荐图片");
		
		return ;
	}
	
	var link =$("#link").val();
	
	if(link == ""){
		
		alert("请填写跳转地址");
		
		return ;
	}
	
	$("#allProjectForm").attr("action",basePath + "/nv/indexTop/submit").submit();
	
	//disabledBtn();
}