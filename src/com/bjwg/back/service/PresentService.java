package com.bjwg.back.service;

import java.util.List;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.MyProject;
import com.bjwg.back.model.Present;
import com.bjwg.back.vo.SearchVo;

/**
 * 字典项service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
/**
 * @author Administrator
 *
 */
public interface PresentService
{
    
  
    /**
     * 分页查询
     * type 类型  1:送猪仔2:送猪肉券
     * @param page
     * @param vo
     * @param type
     * @throws Exception
     */
    public void queryPage(Pages<Present> page,SearchVo vo,Byte type) throws Exception;
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<Present> getAll() throws Exception;
   
	/**
	 * 修改或增加我的钱包
	 * @param present
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(Present present) throws Exception;
	 
    /**
     * 删除我的钱包
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    /**
     * 根据id查询我的钱包
     * @param id
     * @return
     * @throws Exception
     */
    public Present getById(Long id)throws Exception;
    /**
     * @return
     * @throws Exception
     */
    public List<MyProject> getMyProject(Long userId) throws Exception;
    
}
