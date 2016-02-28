package com.bjwg.back.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjwg.back.base.Pages;
import com.bjwg.back.base.constant.StatusConstant.Status;
import com.bjwg.back.model.User;
import com.bjwg.back.model.UserExt;
import com.bjwg.back.model.UserPreorder;
import com.bjwg.back.model.epUser;
import com.bjwg.back.vo.HomeView;
import com.bjwg.back.vo.SearchVo;


/**
 * 用户service接口
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
public interface UserService
{
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<User> page,SearchVo vo) throws Exception;
    
    /**
     * 得到所有数据
     * @throws Exception
     */
    public List<User> getAll() throws Exception;
   
	/**
	 * 修改或增加用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int saveOrupdate(User user,HttpServletRequest request) throws Exception;
	 
    /**
     * 删除用户
     * @param idList
     * @return
     * @throws Exception
     */
    public int delete(List<Long> idList) throws Exception;
    
    public int deleteEpusers(List<Long> userids) throws Exception;
    /**
     * 根据id查询用户
     * @param id
     * @return
     * @throws Exception
     */
    public User getById(Long id)throws Exception; 
   public  epUser getepUser(Long id)throws Exception;
	/**
	 * 冻结用户
	 * @param id
	 * @return
	 */
	int batchCommitFreeze(Long id)throws Exception;
	/**
	 * 得到所有用户
	 * @return
	 * @throws Exception
	 */
	List<User> getAllUser() throws Exception;
	
	/**
     * 得到用户的统计信息
     * @param type 1、当日;2、本周;3、本月
     * @return
     * @throws Exception
     */
    public HomeView getUserCount(Byte type) throws Exception;
    
    /**
	 * 导出用户之前先检验
	 * @param request
	 * @param response
	 * @return
	 */
	public int checkExportList(String sql,SearchVo vo);
	/**
	 * 导出用户
	 * @param request
	 * @param response
	 * @return
	 */
	public int exportList(HttpServletRequest request,HttpServletResponse response) throws Exception;

	//查看用户预抢
	public UserExt selectUserExtByPrimaryKey(Long userId);
	
	//查看预抢表
	public List<UserPreorder> selectLoadNameByUserId(Long userId) throws Exception;
//修改预抢
	Status updateUserPreorder(Long userId, String projectId,String num,String settingType,boolean isCancel) throws Exception;


}
