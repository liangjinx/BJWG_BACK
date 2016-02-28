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
 * 登录
 */
function login()
{
	var username = $.trim($("#username").val());
	
	var password = $.trim($("#password").val());
	
	if(username == ""){
		
		alert("请输入用户名");
		return false;
	}
	if(password == ""){
		alert("请输入密码");
		return false;
	}
	return true;
}

/**
 * 登录时先加密密码
 */
function md5Password()
{
	var password = $("#password").val();
	if(password != ""){
		$("#password").val(hex_md5(password));
	}
}

	
