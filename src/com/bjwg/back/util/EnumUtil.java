package com.bjwg.back.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bjwg.back.base.constant.PageConstant;
import com.bjwg.back.base.constant.RescOperation;

public class EnumUtil {
	/**
	 * 传入枚举类型，就能转换出一个map
	 * @param em
	 * @return
	 */
	public static Map<Integer, String> toStatusMsgMap(Class emClazz){
		
        LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>(); 
        try { 
        	Method toMsg = emClazz.getMethod("getMsg");
            Method toStatus = emClazz.getMethod("getStatus"); 
            //得到enum的所有实例 
            Object[] objs = emClazz.getEnumConstants(); 
            for (Object obj : objs) { 
                map.put((Integer)toStatus.invoke(obj),(String)toMsg.invoke(obj)); 
            } 
        } catch (NoSuchMethodException e) { 
            e.printStackTrace(); 
        } catch (InvocationTargetException e) { 
            e.printStackTrace(); 
        } catch (IllegalAccessException e) { 
            e.printStackTrace(); 
        }
        return map;
	}
}
