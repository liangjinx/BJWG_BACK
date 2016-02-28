$(function(){
	//头部导航
	var hover_nav_a = $('.nav_ul li ').find('.hover_nav_a');
	$('.nav_ul li a').hover(function(){	
		$('.nav_ul li a').removeClass('hover_nav_a');
		$(this).addClass('hover_nav_a');
				
		},function(){
		$('.nav_ul li a').removeClass('hover_nav_a');
		hover_nav_a.addClass('hover_nav_a');	
		})
	//头部二维码
		// right
		$('#code_but_box').hover(function(){	
			$(this).find('.banner_code_box').css('display','block');
		},function(){
			$(this).find('.banner_code_box').css('display','none');
		})
		
		// left
		$('#wechat_code').hover(function(){	
			$(this).find('.banner_code_box').css('display','block');
		},function(){
			$(this).find('.banner_code_box').css('display','none');
		})
	//首页养殖 观察 收益 消费 特权
	$('.zxyz_but').click(function(){
		$('.pig_show_ul').css('display','none');
		$('#zxyz_ul').css('display','block');
		})
	$('.xzgc_but').click(function(){
		$('.pig_show_ul').css('display','none');
		$('#xcgc_ul').css('display','block');
		})
	$('.wdsy_but').click(function(){
		$('.pig_show_ul').css('display','none');
		$('#wdsy_ul').css('display','block');
		})
	$('.jtxf_but').click(function(){
		$('.pig_show_ul').css('display','none');
		$('#jtxf_ul').css('display','block');
		})
	$('.yhtq_but').click(function(){
		$('.pig_show_ul').css('display','none');
		$('#yhtq_ul').css('display','block');
		})
		
	//-我的猪场左侧导航
	var this_a = $('.mypig_nav li').find('.this_a');
	$('.mypig_nav li a').hover(function(){	
		//$('.mypig_nav li a').removeClass('this_a');取消当前标签的选中状态
		$(this).addClass('this_a');
				
		},function(){
		$('.mypig_nav li a').removeClass('this_a');
		this_a.addClass('this_a');	
		})
	//-我的猪场回报方式选择
	/*$('.mian_way li').click(function(){
		
		$('.mian_way li .select_pig').removeClass('checked');
		$(this).find('.select_pig').addClass('checked');	
		
		})*/
	//我的猪场选择档期
	$('.pig_list_tit .selected').click(function(){
		
		$('.pig_list_tit .selected').removeClass('this_s');
		$(this).addClass('this_s');	
		
		})
		
	})
