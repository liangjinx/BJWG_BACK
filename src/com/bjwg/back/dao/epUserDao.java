package com.bjwg.back.dao;

import java.util.List;

import com.bjwg.back.model.User;
import com.bjwg.back.model.epUser;


public interface epUserDao {
	
	int updateByPrimaryKeySelective(epUser record);
    
	epUser selectByPrimaryKey(Long userId);
	
	void insertUser(epUser user);
	int deleteByPrimaryKeys(List<Long> userIds);
	
}