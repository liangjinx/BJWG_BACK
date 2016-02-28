package com.bjwg.back.util;

import java.lang.reflect.Method;
import java.util.List;

public class SortBean {
	// 排序
	public static <T> List<T> getSortBeans(List<T> datas, String methodName) {
		if (datas == null || datas.isEmpty()) {
			return datas;
		}
		T data = datas.get(0);
		Method method = getMethod(data, methodName);
		boolean b = StringUtils.isDigit(getValue(data, method));
		if (b) {
			T temp = null;
			boolean exchange = false;
			for (int i = 0; i < datas.size(); i++) {
				exchange = false;
				for (int j = datas.size() - 2; j >= i; j--) {

					int after = Integer.parseInt(getValue(datas.get(j + 1), method));
					int before = Integer.parseInt(getValue(datas.get(j), method));

					if (after < before) {
						temp = (T) datas.get(j + 1);
						datas.set(j + 1, datas.get(j));
						datas.set(j, temp);
						exchange = true;
					}
				}
				if (!exchange)
					break;
			}
		}
		return datas;
	}

	private static <T> String getValue(T data, Method method) {
		String value = "";
		if (method != null) {
			try {
				Object obj = method.invoke(data);
				if (obj != null) {
					value = obj.toString().replaceAll(" ", "");
				} else {
					value = "0";
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return value;
	}

	private static <T> Method getMethod(T data, String methodName) {
		Method method = null;
		try {
			method = data.getClass().newInstance().getClass().getMethod(methodName);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return method;
	}
}
