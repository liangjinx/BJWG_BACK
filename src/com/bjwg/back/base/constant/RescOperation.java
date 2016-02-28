package com.bjwg.back.base.constant;

public enum RescOperation {
	childDataDel("/v/childDataDel","删除子账号数据"),childDataExamine("/v/childDataExamine","审核子账号数据"),
	childDataOperate("/v/childDataOperate","编辑/冻结子账号数据"),childDataView("/v/childDataView","查看子账号的数据"),
	
	othersDataDel("/v/othersDataDel","删除他人数据"),othersDataExamine("/v/othersDataExamine","审核他人数据"),
	othersDataOperate("/v/othersDataOperate","编辑/冻结他人数据"),othersDataView("/v/othersDataView","查看他人的数据"),
	
	roleDelete("/v/roleDelete.do","角色删除"),managerDelete("/v/managerDelete.do","管理员删除"),
	userDelete("/v/user/delete","用户删除"),walletDelete("/v/wallet/delete","用户钱包删除"),
	projectDelete("/v/project/delete","项目删除"),mycouponDelete("/v/mycoupon/delete","用户猪肉券删除"),
	infoDelete("/v/info/delete","资讯删除"),infoSortDelete("/v/info/sort/delete","资讯分类删除"),
	bulletinDelete("/v/bulletin/delete","公告删除"),shopDelete("/v/shop/delete","线下门店删除"),
	
	
	managerEditInit("/v/managerEditInit.do","管理员增加/编辑"),managerFreeze("/v/managerFreeze.do","管理员冻结/解冻"),
	roleChildList("/v/roleChildEditInit.do","分配可选角色"),roleEditInit("/v/roleEditInit.do","角色增加/编辑"),
	roleRescList("/v/roleRescList.do","分配可选角色"),roleUserList("/v/roleUserList.do","角色分配用户"),
	userPCEditInit("/v/userPCEditInit.do","分配子账号"),
	projectEditInit("/v/project/editInit","项目发布/编辑初始化"),projectEdit("/v/project/edit","项目发布/编辑"),
	bulletinEditInit("/v/bulletin/editInit","发布/编辑公告初始化"),bulletinEdit("/v/bulletin/edit","发布/编辑公告"),
	infoEditInit("/v/info/editInit","发布/编辑资讯初始化"),infoEdit("/v/info/edit","发布/编辑资讯"),
	infoSortEditInit("/v/info/sort/editInit","增加/编辑资讯分类初始化"),infoSortEdit("/v/info/sort/edit","增加/编辑资讯分类"),
	shopEditInit("/v/shop/editInit","增加/编辑线下门店初始化"),shopEdit("/v/shop/edit","增加/编辑线下门店"),
	userEditInit("/v/user/editInit","用户增加/编辑初始化"),userEdit("/v/user/edit","用户增加/编辑"),
	userFreeze("/v/user/freeze","用户解冻/冻结"),
	walletEditInit("/v/wallet/editInit","用户钱包新增/编辑初始化"),walletEdit("/v/wallet/edit","用户钱包新增/编辑"),
	
	configList("/v/config/list","设置列表（管理员)"),configEditInit("/v/config/editInit","设置列表（管理员)新增/编辑初始化"),
	configEdit("/v/config/edit","设置列表（管理员)新增/编辑"),configDelete("/v/config/delete","设置列表（管理员)删除"),
	
	dictList("/v/dict/list","字典设置"),dictEditInit("/v/dict/editInit","字典设置新增/编辑初始化"),
	dictEdit("/v/dict/edit","字典设置新增/编辑"),dictDelete("/v/config/delete","字典设置删除"),
	
	dictDetailList("/v/dictDetail/list","字典明细设置"),dictDetailEditInit("/v/dictDetail/editInit","字典明细设置新增/编辑初始化"),
	dictDetailEdit("/v/dictDetail/edit","字典明细设置新增/编辑"),dictDetailDelete("/v/dictDetail/delete","字典明细设置删除"),
	
	kefulList("/v/kefu/config/list","在线客服设置"),kefuEditInit("/v/kefu/config/editInit","在线客服新增/编辑初始化"),
	kefuEdit("/v/kefu/config/edit","在线客服新增/编辑"),
	
	pactList("/v/pact/config/list","服务协议设置"),pactEditInit("/v/pact/config/editInit","服务协议设置新增/编辑初始化"),
	pactEdit("/v/pact/config/edit","服务协议设置新增/编辑"),
	
	siteList("/v/site/config/list","系统基本设置"),siteEditInit("/v/site/config/editInit","系统基本设置新增/编辑初始化"),
	siteEdit("/v/site/config/edit","系统基本设置新增/编辑"),
	
	slideList("/v/slide/list","首页轮播图"),slideEditInit("/v/slide/editInit","首页轮播图新增/编辑初始化"),
	slideEdit("/v/slide/edit","首页轮播图新增/编辑"),slideDelete("/v/slide/delete","首页轮播图删除"),
	
	userConfigList("/v/user/config/list","设置列表"),userConfigEditInit("/v/user/config/editInit","设置列表编辑初始化"),
	userConfigEdit("/v/user/config/edit","设置列表编辑"),
	
	withdrawAuth("/v/withdraw/auth","用户提现信息审核"),
	
	orderExport("/v/order/export","订单导出"),orderExportCheck("/v/order/checkExport","订单导出检验"),
	userExport("/v/user/export","用户导出"),userExportCheck("/v/user/checkExport","用户导出检验"),
	withdrawExport("/v/withdraw/export","提现记录导出"),withdrawExportCheck("/v/withdraw/checkExport","提现记录导出检验"),
	myprojectExport("/v/myproject/export","用户收益导出"),myprojectExportCheck("/v/myproject/checkExport","用户收益导出检验");
	
	private String value;
    private String text;

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
    public String toString() {
        return text;
    }

    private RescOperation(String value, String text) {
        this.value = value;
        this.text = text;
    }
    public static RescOperation findByValue(String value){
    	RescOperation[] operations =  RescOperation.values();
    	for(RescOperation operation : operations){
    		if(operation.getValue().equalsIgnoreCase(value)){
    			return operation;
    		}
    	}
        return null;
    }
}
