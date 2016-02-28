
package com.bjwg.back.service.impl.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bjwg.back.base.constant.CommConstant;
import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.dao.RescDao;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Resc;
import com.bjwg.back.service.LoginService;
import com.bjwg.back.service.ManagerService;
import com.bjwg.back.util.MyUtils;

/**
 * @author Kim
 * @version 创建时间：2015-4-2 下午04:55:56
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */

public class LoginServiceImpl implements LoginService{

    @Autowired
    private ManagerService managerService;
    
    @Autowired
    private RescDao rescDao;
    
    
    /**
     * 管理员登陆
     * @param username
     * @return
     * @throws Exception
     */
    public int updateLogin(Manager m, String password, String ip) throws Exception
    {
        
        if(m == null || !password.trim().equals(m.getPassword()))
        {
            return StatusConstant.Status.MANAGER_USERNAME_OR_PSW_IS_WRONG.getStatus();
            
        }else if(m.getStatus() == StatusConstant.Status.MANAGER_IS_FORBID.getStatus())
        {
            return StatusConstant.Status.MANAGER_IS_FORBID.getStatus();
        }else if(m.getStatus() == 0)
        {
            return StatusConstant.Status.MANAGER_IS_FREEZE.getStatus();
        }
        try
        {
            Manager manager = new Manager();
            
            manager.setLastLoginIp(MyUtils.ip2long(ip));
            
            manager.setLastLoginTime(new Date());
            
            m.setLastLoginIp(manager.getLastLoginIp());
            
            m.setLastLoginTime(manager.getLastLoginTime());
            
            manager.setManagerId(m.getManagerId());
            
            managerService.updateManager(manager);
            
            return StatusConstant.Status.SUCCESS.getStatus();
        }
        catch (Exception e)
        {
         
            e.printStackTrace();
            throw e;
        }
        
    }
    
    /**
     * 查询当前管理所拥有的权限资源
     * @param managerId
     * @return
     * @throws Exception
     */
    public List<Resc> obtainCurrentManagerAuthority(Long managerId) throws Exception{
        
        return rescDao.obtainCurrentManagerAuthority(managerId);
    }
    
	@Override
	public boolean isViewAllData(Long userId) throws Exception {
		if(userId!=null && userId.equals(1L)){
			return true;
		}
		boolean flag = false;
		List<Resc> rescs = rescDao.obtainCurrentManagerAuthority(userId);
		if(!MyUtils.isListEmpty(rescs)){
			for(Resc resc:rescs){
				String rescCode = resc.getRescCode();
				if(CommConstant.VIEW_ALL_DATA_CODE.equals(rescCode)){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	@Override
	public boolean isCreateChild(Long userId) throws Exception {
		if(userId!=null && userId.equals(1L)){
			return false;
		}
//		boolean flag = false;
//		List<Resc> rescs = rescDao.obtainCurrentManagerAuthority(userId);
//		if(!MyUtils.isListEmpty(rescs)){
//			for(Resc resc:rescs){
//				String rescCode = resc.getRescCode();
//				if(Resc.CREATE_CHILD_USER_CODE.equals(rescCode)){
//					flag = true;
//					break;
//				}
//			}
//		}
		return true;
	}
	@Override
	public boolean isViewChild(Long userId) throws Exception {
		if(userId!=null && userId.equals(1L)){
			return false;
		}
		boolean flag = false;
		List<Resc> rescs = rescDao.obtainCurrentManagerAuthority(userId);
		if(!MyUtils.isListEmpty(rescs)){
			for(Resc resc:rescs){
				String rescCode = resc.getRescCode();
				if(CommConstant.VIEW_CHILD_USER_DATA_CODE.equals(rescCode)){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}
