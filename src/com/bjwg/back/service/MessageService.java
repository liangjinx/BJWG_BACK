package com.bjwg.back.service;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.Message;
import com.bjwg.back.vo.SearchVo;

/**
 * 公告service接口
 * @author Kim
 * @version 创建时间：2015-9-13 下午03:51:42
 * Version: 1.0
 * jdk : 1.6
 * 类说明：
 */

public interface MessageService
{
    /**
     * 分页查询
     * @param page
     * @param clazz
     * @throws Exception
     */
    public void queryPage(Pages<Message> page,SearchVo vo) throws Exception;
   
}
