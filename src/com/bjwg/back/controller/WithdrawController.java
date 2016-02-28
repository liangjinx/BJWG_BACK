package com.bjwg.back.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Withdraw;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.WithdrawService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.vo.SearchVo;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class WithdrawController extends BaseAction{
	
	@Autowired
	private WithdrawService withdrawService;
	@Autowired
	private OperationLogService operationLogService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/withdraw/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_USER_9.getStatus(), request);
        Pages<Withdraw> page = new Pages<Withdraw>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
        //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
        String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
        SearchVo vo = new SearchVo();
        vo.setStartTime(startTime);
        vo.setEndTime(endTime);
        
		//分页查询菜单信息
        withdrawService.queryPage(page,vo);
        List<Withdraw> logList = page.getRoot();
        
        
        System.out.println("提现size"+logList.size());
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
        return "withdraw/list";
	}
	/**
	 * 提现信息 审核
	 */
	@RequestMapping(value="/v/withdraw/auth",method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public void auth(@RequestParam("id") Long id,@RequestParam("auth") Integer auth,HttpServletRequest request, HttpServletResponse response){
		try{
			
			Manager manager = getLoginUser(request);
			int status = withdrawService.batchCommitExamine(id,auth,manager);
			jsonOut(response,StatusConstant.getMsg(status));
			if(status==1){
				//记录日志
				Withdraw withdraw = withdrawService.getById(id);
				String opeObject = "";
				if(withdraw!=null){ opeObject = withdraw.getUsername();}
				operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_EXAMINE,manager.getManagerId(),manager.getUsername(),OperationLogConstant.OPE_MODULE_WITHDRAW,"");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	 /**
     * 提现记录查看
     */
    @RequestMapping(value="/v/withdraw/view", method = {RequestMethod.POST, RequestMethod.GET})
    public String userView(Long withwradalsId,HttpServletRequest request) throws Exception{
        
    	this.navIndex(PageConstant.PageMsg.NAV_USER_10.getStatus(), request);
    	//查看
    	Withdraw withdraw = withdrawService.getById(withwradalsId);
    	request.setAttribute("withdraw", withdraw);
        return "withdraw/view";
    } 
//    /**
//     * 跳转到用户收益页面
//     * @param id
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value="/v/withdraw/editInit", method = {RequestMethod.POST, RequestMethod.GET})
//    public String editInit(Long id,HttpServletRequest request) throws Exception{
//    	Withdraw withdraw = null;
//        //修改
//        if(MyUtils.isLongGtZero(id)){
//        	this.navIndex(PageConstant.PageMsg.NAV_DICT_3.getStatus(), request);
//        	withdraw = withdrawService.getById(id);
//            //新增
//        }else{
//        	this.navIndex(PageConstant.PageMsg.NAV_DICT_2.getStatus(), request);
//        	withdraw = new Withdraw();
//        }
//        request.setAttribute("withdraw", withdraw);
//        return "withdraw/edit";
//    } 
//    
//    /**
//     * 用户收益
//     * @param withdraw
//     * @param request
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value="/v/withdraw/edit", method = {RequestMethod.POST, RequestMethod.GET})
//    public String edit(Withdraw withdraw,HttpServletRequest request) throws Exception{
//    	String text = "";
//    	 if(MyUtils.isLongGtZero(withdraw.getWithdrawId())){
//    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
//    	 }else{
//    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
//    	 }
//    	int status = withdrawService.saveOrupdate(withdraw);
//    	noticeMsg(StatusConstant.getMsg(status),request);
//        if(status != 1){
//        	return editInit(withdraw.getWithdrawId(),request);
//        }else{
//			//记录日志
//			Manager user = getLoginUser(request);
//			operationLogService.oplog(withdraw.getWithdrawId(),withdraw.getName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_WITHDRAW,"");
//        }
//        return list(request);
//    }

   
    /**
     * @param id
     * @param idList
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/withdraw/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String withdrawDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = withdrawService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					Withdraw withdraw = withdrawService.getById(temp);
					String opeObject = "";
					if(withdraw!=null){ opeObject = withdraw.getUsername();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_WITHDRAW,"");
				}
			}
		}
        return list(request);
    } 
    /**
	 *  验证导出页面
	 * @param request
	 * @param response
	 * @return
	 * @user Kim
	 */
	@RequestMapping(value = "/v/withdraw/checkExport", method = { RequestMethod.POST,RequestMethod.GET })
	public void checkExportList(HttpServletRequest request,HttpServletResponse response) {
		try {
			//查询条件
	        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
	        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
	        
	        String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
	        SearchVo vo = new SearchVo();
	        vo.setStartTime(startTime);
	        vo.setEndTime(endTime);
	        
			int status = withdrawService.checkExportList(whereSql,vo);
			if(status < 0){
				jsonOut(response,StatusConstant.getMsg(status));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导出页面
	 * @param request
	 * @param response
	 * @return
	 * @user Kim
	 */
	@RequestMapping(value = "/v/withdraw/export", method = { RequestMethod.POST,RequestMethod.GET })
	public void exportListShop(HttpServletRequest request,HttpServletResponse response) {
		try {
			int status = withdrawService.exportList(request,response);
			if(status==1){
				//记录日志
				Manager user = getLoginUser(request);
				operationLogService.oplog(null,"",OperationLogConstant.OPE_MODULE_TYPE_EXPORT,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_WITHDRAW,"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
