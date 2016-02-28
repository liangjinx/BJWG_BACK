<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld" %>
<%@ taglib prefix="man" uri="/WEB-INF/man.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">   
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" href="resources/css/image.css">
<link rel="stylesheet" href="resources/css/base.css"/>
<link rel="stylesheet" href="resources/css/main.css"/>
	

<script src="resources/js/public.js"></script>
	

</head>

<body>

<jsp:include page="../common.jsp" flush="true"></jsp:include>
	
       
  
    
<div class="my_pig_tit bg_f6"><!--my_pi	g_tit -->
    	<img src="resources/images/nav_tit_bg.png" alt="我要预抢"/>
        <div class="pig_tit"><p>我要预抢</p></div>
</div><!--my_pig_tit -->

<div class="biao_box">
	<div class="biao_tit">
    	<img src="resources/images/this_nav.png" alt="三角形"/><p style="margin-top: 16px;">每期固定数量抢标 </p><span style="color: red;">${userExt.settingType == 1 ? '已选择此方式' :'' }</span>
    </div>
    <div class="biao_box_p"><p class="p_tit" style="margin-top: 10px;">温馨提示：</p><p style=" height: auto; line-height:24px; margin-left: 200px; margin-right: 50px;">两种抢标方式只可选择一种；抢购成功需要在2小时内完成付款，否则视为放弃抢购。</p></div>
    <div class="biao_box_p biao_box_input">
    	<p class="p_tit">数量:</p>
    	<c:if test='${userExt.settingType eq 1 }'>
    		<input type="text" name="alwaysCount" class="input_text" value="${userExt.settingValue }" readonly/>
    		<input id="btnAlwaysBtn" type="button" value="取消" class="button"/>
    	</c:if>
    	<c:if test='${userExt.settingType ne 1}'>
    		<input type="text" name="alwaysCount" class="input_text" maxLength=9/>
    		<input id="btnAlwaysBtn" type="button" value="提交" class="button"/>
    	</c:if>
    </div>
</div>    <!--biao_box -->
    
<div class="biao_box biao_box2">
	<div class="biao_tit">
    	<img src="resources/images/this_nav.png" alt="三角形"/><p style="margin-top: 16px;">选择期数抢标</p>
    	<c:if test='${userExt.settingType eq 2}'>
    		&nbsp;已选择此方式
    	</c:if>
    </div>
    <c:choose>
    	<c:when test="${fn:length(preOrderList)>0 }">
    	<ul class="biaoqi_ul">
    	<c:forEach items="${preOrderList}" var="l">
	        <li data-id="${l.projectId }" data-num="${l.num }" <c:if test="${l.num > 0}">class="this_li"</c:if>>
	            	${l.username}
	            <c:if test="${l.num >  0}">/${l.num}</c:if>
	        </li>
    	</c:forEach>
    	</ul>
    	</c:when>
    	<c:otherwise>
    		<div class="z_no_yuqiang">暂时没有可以预抢的标哦。</div>
    	</c:otherwise>
    </c:choose>
    <div class="biao_box2_but">
    	<p>抢购数量：</p>
    	<input type="tel" name="" id="buyNum" class="input_text"/>
    	<input type="button" id="btnChooseSub" value="确定" class="button"/>
    </div>    
</div> <!--biao_box -->  


	
   

</body>
<script>
$(function(){
 var __base_path__ = '<%=basePath%>';
	var sw = true ;//开关
	$('.mypig_switch').click(function(){
		
		if(sw){
			$('.mypig_xq').css('display','block');
			$('.mypig_period').css('display','block');
			sw = false ;
			}else{
			$('.mypig_xq').css('display','none');
			$('.mypig_period').css('display','none');
			sw = true ;
				}
		
		});
		
	//固定抢标
	$('#btnAlwaysBtn').click(function(){

		var self = $(this);
		var count = $('[name=alwaysCount]').val().trim();
		
		if(self.val()=="取消"){
	
			count=0;
		} else{
			if(count == '' || count<=0 || !/^[0-9]*$/.test(count)){
				alert('请正确输入数量!');
				return;
			}
		}
		
		self.attr('disabled','disabled');
	

		$.ajax({
		    type: 'POST',
		    dataType: 'json',
		    url: __base_path__+'v/user/preOrderSave',
		    cache: false,
		    data: {
		    	settingType:1,
		    	num:count,
		    	cancel:count>0?false:true
		    },
		 
		    success: function(data){
		   
		    	if(data.msg == 'SUCCESS'){
		    	
		    		alert('操作成功');	
		    		window.location.href=__base_path__+'v/user/myPreorder';
		    	} else {
		    		alert(data.data.text);
		    		self.removeAttr('disabled');
		    	}
		    },
		    error: function(){
		    	alert('error');
		    }
		});
	});
		
		
	$('.biaoqi_ul li').click(function(){
		var self=$(this);
		var num = self.attr('data-num');
		var projId = self.attr('data-id');
		
		//取消抢标
		if(num!=null && num >0){
			var r=confirm("确认取消?");
			if(r){
				$.ajax({
		    type: 'POST',
		    dataType: 'json',
		    url: __base_path__+'v/user/preOrderSave',
		    cache: false,
		    data: {
		    	settingType: 2,
		    	num: 0,
		    	cancel: true,
		    	projectId: projId
		    },
		    success: function(data){
	
		    	if(data.msg == 'SUCCESS'){
		    		alert('取消成功');
		    		window.location.href=__base_path__+'v/user/myPreorder';
		    	} else {
		    		alert(data.data.text);
		    		self.removeAttr('disabled');
		    	}
		    },
		    error: function(){
		    	alert('error');
		    }
			});
			}
			return;
		}
		
		$('.biaoqi_ul li').removeClass('selected_li');
		self.addClass('selected_li');
		$('#buyNum').val('').focus();
	});
	
	$('#btnChooseSub').click(function(){
		var self = $(this);
		var count = $('#buyNum').val();
		
		if($('.selected_li').length<=0){
			alert('请选择一期');
			return;
		}
		if(count == '' || count<=0 || !/^[0-9]*$/.test(count)){
			alert('请正确输入数量');
			return;
		}
		
		self.attr('disabled','disabled');
		$.ajax({
		    type: 'POST',
		    dataType: 'json',
		    url: __base_path__+'v/user/preOrderSave',
		    cache: false,
		    data: {
		    	settingType: 2,
		    	num: count,
		    	cancel: false,
		    	projectId: $('.selected_li').attr('data-id')
		    },
		    success: function(data){
		   
		    	if(data.msg == 'SUCCESS'){
		    		alert('操作成功');
		    		window.location.href=__base_path__+'v/user/myPreorder';
		    	} else {
		    		alert(data.data.text);
		    		self.removeAttr('disabled');
		    	}
		    },
		    error: function(){
		    	alert('error');
		    }
		});
	});
	});
</script>
<script>
		/* $(function () {
			//活动的猪的个数。如：10只猪
			var pigs = CreatePig(${farmData.allCount!=null?farmData.allCount:0});  //
		}); */
		
		$(function(){
			var height_ = 200,
				layer_width = $(window).width()*80/100,
				window_heigit = $(window).height(),
				offset = 'auto';
				
			if(window_heigit <= height_) offset = "0px";
			if(layer_width>=640) layer_width = "640*90/100";
			
			var index = layer.open({
			    type: 1,
			    title:false,
			    shade: 0.6,
			    area: [layer_width+'px', height_+'px'],
			    offset: offset,
			    scrollbar: true,
			    closeBtn: true, //false:不显示关闭按钮
				shift:1, //0-6的动画形式，-1不开启
			    content: $(".yubiao_notice") 	//调预抢弹窗
			    //content: $('.harvest')			//调成猪出售弹窗
			}); 
			layer.style(index,{
				'border-radius':'5px'//修改layer最外层容器样式
			});
		});
	</script>
</html>



<!--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>我的猪场</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="styles.css">
	

  </head>
  
  <body>
    <h3>我的猪场</h3>
    <div>
    	<div>总数:${farmData.allCount }</div>
    	<div><a href="pv/user/myEarning?projectId=${farmData.projectId }">本期收益:${farmData.profit }</a></div>
    	<div>本期名:${farmData.projectName }</div>
    	<div>本期数量:${farmData.num}</div>
    	<div>阶段:${farmData.phase}</div>
    	<div>
    		回报方式:
    		<c:choose>
    			<c:when test="${farmData.way == null}">
    				<a href="pv/order/paybackChoose?projectId=${farmData.projectId }">选择回报方式</a>
    			</c:when>
    			<c:otherwise>
    				${farmData.way }
    			</c:otherwise>
    		</c:choose>
    	</div>
    	<div>
    		我参与的所有期:
    		<c:forEach items="${farmData.allMyMyEarnings}" var="ear">
    			<a href="pv/user/farm?earningsId=${ear.earningsId }">${ear.paincbuyProjectName }</a> | 
    		</c:forEach>
    	</div>
    	<div>
    		本期成长记录:
    		<c:forEach items="${groupRecordList}" var="rd">
    			<div>${rd.extend } ${rd.remark }</div>
    		</c:forEach>
    	</div>
    	<hr>
    </div>
  </body>
</html>






















-->