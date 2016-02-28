package com.bjwg.back.base.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * @author Allen
 * @version 创建时间：2015-4-7 下午03:12:32
 * @Modified By:Allen
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */
public class WebRootTag extends TagSupport
{
    
    public int doStartTag() throws JspTagException
    {
        JspWriter out = pageContext.getOut();
        
        try
        {
            out.print(((HttpServletRequest)this.pageContext.getRequest()).getContextPath());
        }
        catch(IOException ex)
        {
            throw new JspTagException("IOError:"+ex.getMessage());
        }
        return Tag.SKIP_BODY;
    }
    
    public int doEndTag() throws JspTagException
    {
        return Tag.EVAL_PAGE;
    }

}
