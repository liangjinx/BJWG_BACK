package com.bjwg.back.base.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.bjwg.back.base.constant.CommConstant;
import com.bjwg.back.model.Manager;
import com.bjwg.back.model.Resc;
import com.bjwg.back.util.MyUtils;
import com.bjwg.back.util.StringUtils;

/**
 * @author Kim
 * @version 创建时间：2015-5-1 下午03:12:32
 * @Modified By:Kim Version: 1.0 jdk : 1.6 类说明：
 */
@SuppressWarnings("serial")
public class PermissionTag extends TagSupport {
	public static final String OR_SEPARATOR = "_OR_";
	private String[] propertyNames = null;
	//资源code
	private String name = null;

	public PermissionTag() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int doStartTag(){
		boolean show = showTagBody(getName());
		if (show) {
            return TagSupport.EVAL_BODY_INCLUDE;
        } else {
            return TagSupport.SKIP_BODY;
        }
	}

	public int doEndTag() throws JspTagException {
		return Tag.EVAL_PAGE;
	}

	private boolean showTagBody(String name) {
		boolean flag = false;
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		HttpSession session = request.getSession(false);
		if (session != null) {
			String loginFlag = (String) session
					.getAttribute(CommConstant.SESSION_MANAGER_LOGIN_FLAG);
			if (StringUtils.isNotEmpty(loginFlag)
					&& CommConstant.SUCCESS.equals(loginFlag)) {
				// 管理员有所有权限
				Manager user = (Manager) request.getSession().getAttribute(
						CommConstant.SESSION_MANAGER);
				Long managerId = user.getManagerId();
				if (managerId != null && managerId.equals(1L)) {
					flag = true;
				//不是管理员 用资源code进行判断,数据库对应RESC_CODE,这个是唯一的
				} else {
					@SuppressWarnings("unchecked")
					List<Resc> rescList = (List<Resc>) session
							.getAttribute(CommConstant.SESSION_MANAGER_AUTHORITY_LINK);
					if (!MyUtils.isListEmpty(rescList)) {
						//可以配置多个权限，并满足一项就可以通过
						for (Resc s : rescList) {
							if(name.indexOf(OR_SEPARATOR)>=0){
								propertyNames = org.apache.commons.lang.StringUtils.splitByWholeSeparator(name, PermissionTag.OR_SEPARATOR);
								for(String rescname:propertyNames){
									if (rescname.equals(s.getRescCode())) {
										flag = true;
										break;
									}
								}
							}else{
								if (name.equals(s.getRescCode())) {
									flag = true;
									break;
								}
							}
							
						}
					}
				}
			}
		}
		return flag;
	}
}
