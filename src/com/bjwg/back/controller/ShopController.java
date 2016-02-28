package com.bjwg.back.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjwg.back.util.PropertyFilter;
import com.bjwg.back.util.StringUtils;
import com.bjwg.back.util.UploadFile;
import com.bjwg.back.base.BaseAction;
import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.OperationLogConstant;
import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Shop;
import com.bjwg.back.service.AreaService;
import com.bjwg.back.service.OperationLogService;
import com.bjwg.back.service.ShopService;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.model.Area;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class ShopController extends BaseAction{
	
	@Autowired
	private ShopService shopService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private OperationLogService operationLogService;
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/v/shop/list",method = {RequestMethod.POST, RequestMethod.GET})
	public String list(HttpServletRequest request) throws Exception{
		 //系统管理导航
		this.navIndex(PageConstant.PageMsg.NAV_SHOP_1.getStatus(), request);
        Pages<Shop> page = new Pages<Shop>();
        page.setCurrentPage(currentPage);
        page.setPerRows(10);
        
      //查询条件
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String whereSql = PropertyFilter.buildStringByPropertyFilter(filters,false);
        page.setWhereSql(whereSql);
        
		//分页查询菜单信息
        shopService.queryPage(page);
        List<Shop> logList = page.getRoot();
        
		List<Area> areas = areaService.getProvince();
		request.setAttribute("province", areas);
        
        request.setAttribute("list", logList);
        request.setAttribute("page", page);
        return "shop/list";
	}
	
	
    /**
     * 跳转到配置页面
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/shop/editInit", method = {RequestMethod.POST, RequestMethod.GET})
    public String editInit(Long id,HttpServletRequest request) throws Exception{
    	Shop shop = null;
        //修改
        if(MyUtils.isLongGtZero(id)){
        	this.navIndex(PageConstant.PageMsg.NAV_SHOP_3.getStatus(), request);
        	shop = shopService.getById(id);
            //新增
        }else{
        	this.navIndex(PageConstant.PageMsg.NAV_SHOP_2.getStatus(), request);
        	shop = new Shop();
        }
		List<Area> areas = areaService.getProvince();
		request.setAttribute("province", areas);
		setPhotoDomain(request);
        request.setAttribute("shop", shop);
        return "shop/edit";
    } 
    
    /**
     * 字典配置
     * @param shop
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/v/shop/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(Shop shop,HttpServletRequest request) throws Exception{
    	String text = "";
    	 if(MyUtils.isLongGtZero(shop.getShopId())){
    		 text = OperationLogConstant.OPE_MODULE_TYPE_UPDATE;
    	 }else{
    		 text = OperationLogConstant.OPE_MODULE_TYPE_ADD;
    	 }
    	 
 		int status = 0;
 		//店铺logo
		Map<String, Object> map = UploadFile.uploadSingleFile(request,"");
		if(map.get("status")!=null){
			Integer ret = Integer.parseInt(map.get("status").toString());
			if(ret.intValue()!=StatusConstant.Status.UPLOAD_NOFILE.getStatus() && ret.intValue()!=StatusConstant.Status.SUCCESS.getStatus()){
				status = ret.intValue();
				noticeMsg(StatusConstant.getMsg(status),request);
				return editInit(shop.getShopId(),request);
			}
		}
		
		if(map.get("filePath")!=null && StringUtils.isNotEmpty(map.get("filePath").toString())){
			shop.setLogo(map.get("filePath").toString());
		}else{
			String nopath = request.getParameter("nologo");
			if(nopath!=null&&!nopath.equals("")){
				shop.setLogo("");
			}
		}
 		
    	status = shopService.saveOrUpdate(shop);
    	noticeMsg(StatusConstant.getMsg(status),request);
        if(status != 1){
        	return editInit(shop.getShopId(),request);
        }else{
			//记录日志
			Manager user = getLoginUser(request);
			operationLogService.oplog(shop.getShopId(),shop.getName(),text,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SHOP,"");
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
    @RequestMapping(value="/v/shop/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String shopDelete(Long id,String idList,HttpServletRequest request) throws Exception{
        List<Long> ids = null;
        if(!StringUtils.isNotEmpty(idList)){
            ids = new ArrayList<Long>();
            ids.add(id);
        }else{
            ids = MyUtils.convertToLongList(idList, ",");
        }
        int status = shopService.delete(ids);
        noticeMsg(StatusConstant.getMsg(status),request);
        if(status==1){
			//记录日志
			Manager user = getLoginUser(request);
			for (Long temp : ids){
				if(MyUtils.isLongGtZero(id)){
					Shop shop = shopService.getById(temp);
					String opeObject = "";
					if(shop!=null){ opeObject = shop.getName();}
					operationLogService.oplog(id,opeObject,OperationLogConstant.OPE_MODULE_TYPE_DEL,user.getManagerId(),user.getUsername(),OperationLogConstant.OPE_MODULE_SHOP,"");
				}
			}
		}
        return list(request);
    } 
    /**
	 * 根据省查询市
	 * 
	 * @param code
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/nv/shop/city", method = RequestMethod.POST)
	@ResponseBody
	public void city(Long code, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			List<Area> list = areaService.queryCity(code);

			JSONArray array = JSONArray.fromObject(list);

			PrintWriter out = response.getWriter();
			System.out.println("ddd"+array.toString());
			out.write("{\"des\":" + array + "}");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 检查店铺地址是否有效
	 * 
	 * @return
	 */
	@RequestMapping(value = "/nv/shop/checkAddress")
	public void checkAddress(HttpServletRequest request,HttpServletResponse response) {
		try {
			String address = request.getParameter("address");
			if(address!=null && !address.equals("")){
				address = address.replaceAll(" ", "").trim();
			}
			int status = shopService.checkAddress(address);
			jsonOut(response,"\""+StatusConstant.getMsg(status)+"\"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
