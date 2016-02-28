package com.bjwg.back.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.constant.DictConstant;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.model.DictDetail;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Protocol;
import com.bjwg.back.service.DictDetailService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.ProtocolService;
import com.bjwg.back.util.MyUtils;

@Controller
@Scope("prototype")
public class ProtocolController extends BaseAction{
	
	@Autowired
	private ProtocolService protocolService;
	@Autowired
	private DictDetailService dictDetailService;
	@Autowired
	private OperationLogService operationLogService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/pact/config/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_13.getStatus(), request);
		List<Protocol> logList = protocolService.getAll();
        request.setAttribute("list", logList);
        return "sysconfig/pactlist";
	}
	
    /**
     * 跳转到配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/pact/config/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long id,HttpServletRequest request) throws Exception{
    	Protocol protocol = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_14.getStatus(), request);
        	protocol = protocolService.getById(id);
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_SYSCONFIG_14.getStatus(), request);
        	protocol = new Protocol();
        }
        //得到所有服务类型的协议
        List<DictDetail> list = dictDetailService.getDictDetailsByParentCode(DictConstant.BJWG_SERVICE_PROTOCOL_TYPE);
        request.setAttribute("typelist", list);
        
        request.setAttribute("protocol", protocol);
        return "sysconfig/pactedit";
    } 
    
    /**
     * 系统配置
     * @param protocol
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/pact/config/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(Protocol protocol,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(protocol.getSpId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	int status = protocolService.saveOrupdate(protocol);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(protocol.getSpId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(protocol.getSpId(),protocol.getTitle(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SYSCONFIG,"");
        }
        return list(request);
    }
}
