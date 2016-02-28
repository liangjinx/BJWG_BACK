package com.bjwg.back.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.User;
import com.bjwg.back.model.Wallet;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.UserService;
import com.bjwg.back.service.WalletService;
import com.bjwg.back.util.MyUtils;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class WalletController extends BaseAction{
	
	@Autowired
	private WalletService walletService;
	@Autowired
	private UserService userService;
	@Autowired
	private OperationLogService operationLogService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/wallet/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_USER_5.getStatus(), request);
        Pages<Wallet> page = new Pages<Wallet>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
      //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
		//分页查询菜单信息
        walletService.queryPage(page);
        List<Wallet> logList = page.getRoot();
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
        return "wallet/list";
	}
	
	
    /**
     * 跳转到配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/wallet/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long walletId,HttpServletRequest request) throws Exception{
    	Wallet wallet = null;
        //修改
        if(MyUtils.isLongGtZero(walletId)){
        	this.navIndex(PageConstant.PageMsg.NAV_USER_7.getStatus(), request);
        	wallet = walletService.getById(walletId);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_USER_6.getStatus(), request);
        	wallet = new Wallet();
        }
        List<User> users = userService.getAllUser();
        request.setAttribute("wallet", wallet);
        request.setAttribute("users", users);
        return "wallet/save";
    } 
    
    /**
     * 用户钱包
     * @param wallet
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/wallet/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(Wallet wallet,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(wallet.getWalletId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	int status = walletService.saveOrupdate(wallet);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(wallet.getWalletId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(wallet.getWalletId(),wallet.getUserId()+"",text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_WALLET,"");
        }
        return list(request);
    }

   
    /**
     * @param id
     * @param idList
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/wallet/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String walletDelete(Long walletId,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(walletId);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = walletService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(walletId)){
					Wallet wallet = walletService.getById(temp);
					String opeObject = "";
					if(wallet!=null){ opeObject = wallet.getUserId()+"";}
					operationLogService.oplog(walletId,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_WALLET,"");
				}
			}
		}
        return list(request);
    } 
    
}
