
/**
 * 禁用按钮
 */
function disabledBtn(){
	
	$("button[name=btn]").attr("disabled",true);
}

/**
 * 加载
 */
function load(type,url){
	
	
	switch (type) {
	
	case 1://菜单管理
		
		$("#treeFrame",parent.document.body).attr("src",url);
		
		break;
	case 2://角色管理
		
		$("#roleFrame",parent.document.body).attr("src",url);
		break;
	case 3://资源管理
		
		$("#rescFrame",parent.document.body).attr("src",url);
		
		break;
	case 4://用户管理
		$("#userFrame",parent.document.body).attr("src",url);
		break;

	default:
		break;
	}
	
}

/**
 * 角色编辑
 */
function roleEidt(type,id){
	
	var action = "";
	
	switch (type) {
	
	case 1://新增
		
		action = basePath + "/v/roleEditInit.do";
		
		break;
	case 2://修改
		
		action = basePath + "/v/roleEditInit.do";
		
		$("#roleId").val(id);
		
		break;
	case 3://单个删除
		
		action = basePath + "/v/roleDelete.do";
		
		if(confirm("是否确定删除记录？")){
			
			$("#roleId").val(id);
		}else{
			
			return ;
		}
		
		
		break;
	case 4://多个删除
		
		action = basePath + "/v/roleDelete.do";
		
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
	case 5://用户权限
		
		action = basePath + "/v/roleUserList.do";
		$("#roleId").val(id);
		break;
	case 6://分配权限
		
		action = basePath + "/v/roleRescList.do";
		$("#roleId").val(id);
		break;
	case 7://分配可选角色
		
		action = basePath + "/v/roleChildEditInit.do";
		$("#roleId").val(id);
		break;
	default:
		
		break;
	}
	
	$("#allProjectForm").attr("action",action).submit();
	
	//disabledBtn();
	
}

/**
 * 保存角色
 */
function saveThisRole(){
	$("#allProjectForm").attr("action",basePath + "/nv/roleEdit.do").submit();
	//disabledBtn();
}

function ArrToJson(arr) {
    var length = arr.length;
    var json = {};
    for(var i=0;i<length;i++) {
         json.push(arr[i].toString());
    }
    return json;
}

/**
 * 给角色关联用户
 */
function saveThisRoleUser(){
	 //刷新状态信息
	var items = $("#cyList :checkbox");
	var info = "";
	 for (var i = 0; i < items.length; i++) {
	      // 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
	      info = (info + items.get(i).value) + (((i + 1)== items.length) ? '':','); 
	 }
	 $("#checkedUserIds").val(info);
	 $("#allProjectForm").attr("action",basePath + "/nv/roleUserEdit.do").submit();
	 disabledBtn();
}
/**
 * 给主账号分配子账号
 */
function saveThisUserChild(){
	 //刷新状态信息
	var items = $("#cyList :checkbox");
	var info = "";
	 for (var i = 0; i < items.length; i++) {
	      // 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
	      info = (info + items.get(i).value) + (((i + 1)== items.length) ? '':','); 
	 }
	 $("#checkedUserIds").val(info);
	 $("#allProjectForm").attr("action",basePath + "/nv/userPCEdit.do").submit();
	 disabledBtn();
}
/**
 * 给角色配置可选择角色
 */
function saveThisRoleChild(){
	 //刷新状态信息
	var items = $("#cyList :checkbox");
	var info = "";
	 for (var i = 0; i < items.length; i++) {
	      // 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
	      info = (info + items.get(i).value) + (((i + 1)== items.length) ? '':','); 
	 }
	 $("#checkedRoleIds").val(info);
	 $("#allProjectForm").attr("action",basePath + "/nv/roleChildEdit.do").submit();
	 disabledBtn();
}

/**
 * 给角色关联权限
 */
function saveThisRoleResc(){
	 //刷新状态信息
	var items = $("#cyList input[name^='fcy_']");
	var info = "";
	 for (var i = 0; i < items.length; i++) {
	      // 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
	      info = (info + items.get(i).value) + (((i + 1)== items.length) ? '':','); 
	 }
	 $("#checkedRescIds").val(info);
	 $("#allProjectForm").attr("action",basePath + "/nv/roleRescEdit.do").submit();
	 disabledBtn();
}
/**
 * 资源编辑
 */
function rescEidt(type,id){
	
	var action = "";
	
	switch (type) {
	
	case 1://新增
		
		action = basePath + "/v/rescEditInit.do";
		
		break;
	case 2://修改
		
		action = basePath + "/v/rescEditInit.do";
		
		$("#rescId").val(id);
		
		break;
	case 3://单个删除
		
		action = basePath + "/v/rescDelete.do";
		
		if(confirm("是否确定删除记录？")){
			
			$("#rescId").val(id);
		}else{
			
			return ;
		}
		
		
		break;
	case 4://多个删除
		
		action = basePath + "/v/rescDelete.do";
		
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
	
	//disabledBtn();
	
}
	
/**
 * 保存资源
 */
function saveThisResc(){
	
	var rescName = $.trim($("#rescName").val());
	
	if(rescName == ""){
		
		alert("请填写资源名");
		
		return ;
	}
	var roleLink = $.trim($("#roleLink").val());
	
	if(roleLink == ""){
		
		alert("请填写资源链接");
		
		return ;
	}
	var roleCode = $.trim($("#roleCode").val());
	
	if(roleCode == ""){
		
		alert("请填写资源编号");
		
		return ;
	}
	
	$("#allProjectForm").attr("action",basePath + "/nv/rescEdit.do").submit();
	
	//disabledBtn();
	
}

/**
 * 用户管理
 */
function managerEidt(type,id){
	
	var action = "";
	
	switch (type) {
	
	case 1://新增
		
		action = basePath + "/v/managerEditInit.do";
		
		break;
	case 2://修改
		action = basePath + "/v/managerEditInit.do";
		
		$("#managerId").val(id);
		break;
	case 3://单个删除
		
		action = basePath + "/v/managerDelete.do";
		
		if(confirm("是否确定删除记录？")){
			
			$("#managerId").val(id);
		}else{
			
			return ;
		}
		
		
		break;
	case 4://多个删除
		
		action = basePath + "/v/managerDelete.do";
		
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
	case 5://分配子账号
		
		action = basePath + "/v/userPCEditInit.do";
		$("#managerId").val(id);
		break;
	default:
		
		break;
	}
	
	$("#allProjectForm").attr("action",action).submit();
	//disabledBtn();
	
}
/**
 * 保存用户
 */
function saveThisUser(){
	var items = $("input[name^='multiple']:checked");
	var info = "";
	 for (var i = 0; i < items.length; i++) {
	      // 如果i+1等于选项长度则取值后添加空字符串，否则为逗号
	      info = (info + items.get(i).value) + (((i + 1)== items.length) ? '':','); 
	 }
	$("#roleIds").val(info);
	$("#allProjectForm").attr("action",basePath + "/nv/managerEdit.do").submit();
	//disabledBtn();
}
