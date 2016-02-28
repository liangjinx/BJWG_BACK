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


	
