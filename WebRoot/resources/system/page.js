/**
 *  分页 请求
 */
function  getPageList(obj)
{
	var currentPage=document.getElementById("currentPage").value;
	var pageCount=document.getElementById("pageCount").value;

	if(obj=='index')
	{
		//首页
		currentPage=1;

	}else if(obj=='upage')
	{
		//上一页
		currentPage--;

	}else if(obj=='next')
	{
		//下一页
		currentPage++;

	}else if(obj=='last')
	{
		//最后一页
		currentPage=pageCount;
		
	}else if(obj=='jump'){
		//跳页
		currentPage = parseInt(currentPage);
		pageCount = parseInt(pageCount);
	}
	if(currentPage<1)
	{
		currentPage=1;
	}else if(currentPage>pageCount)
	{
		currentPage=pageCount;
	}

	document.getElementById("currentPage").value=currentPage;
	document.allProjectForm.submit();
}

/**
 * 跳页
 */
function gotoSelectPage(pageIndex){
	if(pageIndex==''){
		alert("请填写页码");
		return;
	}
	document.getElementById("currentPage").value = pageIndex;
	
	getPageList('jump');
}

/**
 * 直接跳转页面, 检查表单是否非法
 */
function gotoPages()
{
	var currentPage=document.getElementById("currentPage").value;
	var pageCount=document.getElementById("pageCount").value;

	var currentPageGo=document.getElementById("currentPageGo").value;



	var reg = /\s/g;
	var currentPageGo= currentPageGo.replace(reg, "");
	if(currentPageGo==""||currentPageGo.length<0||isNaN(currentPageGo))
	{
		alert("请输入正确的页码");
		document.getElementById("currentPageGo").select();
		return false;
	}else
	{
		if(currentPageGo>=pageCount)
		{
			document.getElementById("currentPage").value=pageCount;
		}else if(currentPageGo<=1)
		{
			document.getElementById("currentPage").value=1;
		}else
		{
			document.getElementById("currentPage").value=currentPageGo;
		}

		document.allProjectForm.submit();
	}
}


