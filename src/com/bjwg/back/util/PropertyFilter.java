/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: PropertyFilter.java 1627 2011-05-23 16:23:18Z calvinxiu $
 */
package com.bjwg.back.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Kim
 */
public class PropertyFilter {

	public static final String OR_SEPARATOR = "_OR_";
	//=,like,<,>,<=,>=
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE;
	}

	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

		private Class<?> clazz;

		private PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private MatchType matchType = null;
	private Object matchValue = null;

	private Class<?> propertyClass = null;
	private String[] propertyNames = null;

	public PropertyFilter() {
	}

	/**
	 *                   eg. LIKES_NAME_OR_LOGIN_NAME
	 * @param value
	 */
	public PropertyFilter(final String filterName, final String value) {

		String firstPart = StringUtils.substringBefore(filterName, "_");
		String matchTypeCode = StringUtils.substring(firstPart, 0, firstPart.length() - 1);
		String propertyTypeCode = StringUtils.substring(firstPart, firstPart.length() - 1, firstPart.length());

		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException(filterName, e);
		}

		try {
			propertyClass = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException(filterName, e);
		}

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		AssertUtils.isTrue(StringUtils.isNotBlank(propertyNameStr), filterName);
		propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, PropertyFilter.OR_SEPARATOR);

		this.matchValue = ObjectMapper.convertToObject(value, propertyClass);
	}

	/**
	 * @see #buildFromHttpRequest(HttpServletRequest, String)
	 */
	public static List<PropertyFilter> buildFromHttpRequest(final HttpServletRequest request) {
		return buildFromHttpRequest(request, "filter");
	}

	/**
	 * eg.
	 * filter_EQS_name
	 * filter_LIKES_name_OR_email
	 */
	public static List<PropertyFilter> buildFromHttpRequest(final HttpServletRequest request, final String filterPrefix) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		Map<String, Object> filterParamMap = ServletUtils.getParametersStartingWith(request, filterPrefix + "_");
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = ((String) entry.getValue()).trim();
			if (StringUtils.isNotBlank(value)) {
				PropertyFilter filter = new PropertyFilter(filterName, value);
				filterList.add(filter);
			}
		}

		return filterList;
	}

	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public Object getMatchValue() {
		return matchValue;
	}

	public String[] getPropertyNames() {
		return propertyNames;
	}

	public String getPropertyName() {
		AssertUtils.isTrue(propertyNames.length == 1, "There are not only one property in this filter.");
		return propertyNames[0];
	}

	public boolean hasMultiProperties() {
		return (propertyNames.length > 1);
	}
	//=,like,<,>,<=,>=
	protected static StringBuffer toSqlString(final String propertyName, final Object propertyValue, final MatchType matchType, final Class<?> propertyClass,StringBuffer sb) {
		AssertUtils.hasText(propertyName, "propertyName不为空");
		switch (matchType) {
		case EQ:
			sb.append(" and "+propertyName+"=");
			boolean ifString =(propertyClass==String.class);
			if(ifString){
				sb.append("'");
			}
			sb.append(propertyValue);
			if(ifString){
				sb.append("'");
			}
			break;
		case LIKE:
			sb.append(" and "+propertyName+" like '%"+propertyValue+"%'");
			break;
		case LE:
			if(propertyClass==String.class){
				sb.append(" and "+propertyName+"<=to_number(to_char(to_date("+propertyValue+", 'yyyy-mm-dd'),'yyyymmdd'))");
			}else if(propertyClass==Date.class){
				sb.append(" and "+propertyName+"<=to_date('"+propertyValue+"', 'yyyy-mm-dd')");
			}else{
				sb.append(" and "+propertyName+"<="+propertyValue);
			}
			break;
		case LT:
			if(propertyClass==String.class){
				sb.append(" and "+propertyName+"<to_number(to_char(to_date("+propertyValue+", 'yyyy-mm-dd'),'yyyymmdd'))");
			}else if(propertyClass==Date.class){
				sb.append(" and "+propertyName+"<to_date('"+propertyValue+"', 'yyyy-mm-dd')");
			}else{
				sb.append(" and "+propertyName+"<"+propertyValue);
			}
			break;
		case GE:
			if(propertyClass==String.class){
				sb.append(" and "+propertyName+">=to_number(to_char(to_date("+propertyValue+", 'yyyy-mm-dd'),'yyyymmdd'))");
			}else if(propertyClass==Date.class){
				sb.append(" and "+propertyName+">=to_date('"+propertyValue+"', 'yyyy-mm-dd')");
			}else{
				sb.append(" and "+propertyName+">="+propertyValue);
			}
			break;
		case GT:
			if(propertyClass==String.class){
				sb.append(" and "+propertyName+">to_number(to_char(to_date("+propertyValue+", 'yyyy-mm-dd'),'yyyymmdd'))");
			}else if(propertyClass==Date.class){
				sb.append(" and "+propertyName+">to_date('"+propertyValue+"', 'yyyy-mm-dd')");
			}else{
				sb.append(" and "+propertyName+">"+propertyValue);
			}
		}
		return sb;
	}
	//通过数组
	protected static StringBuffer toSqlString(final String[] propertyNames, final Object propertyValue, final MatchType matchType, final Class<?> propertyClass,StringBuffer sb) {
		switch (matchType) {
		case EQ:
			sb.append(" and (");
			for(int i =0;i<propertyNames.length;i++){
				String param = propertyNames[i];
				sb.append(param+"=");
				boolean ifString =(propertyClass==String.class);
				if(ifString){
					sb.append("'");
				}
				sb.append(propertyValue);
				if(ifString){
					sb.append("'");
				}
				if(i!=(propertyNames.length-1)){
					sb.append(" or ");
				}
			}
			sb.append(")");
			break;
		case LIKE:
			sb.append(" and (");
			for(int i =0;i<propertyNames.length;i++){
				String param = propertyNames[i];
				sb.append(param+" like '%"+propertyValue+"%'");
				if(i!=(propertyNames.length-1)){
					sb.append(" or ");
				}
			}
			sb.append(")");
			break;
		case LE:
			if(propertyClass==String.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<=to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else if(propertyClass==Date.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<=to_date("+propertyValue+", 'yyyy-mm-dd')");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else{
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<="+propertyValue);
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}
			break;
		case LT:
			if(propertyClass==String.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else if(propertyClass==Date.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<to_date('"+propertyValue+"', 'yyyy-mm-dd')");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else{
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<"+propertyValue);
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}
			break;
		case GE:
			if(propertyClass==String.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">=to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else if(propertyClass==Date.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">=to_date('"+propertyValue+"', 'yyyy-mm-dd')");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else{
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">="+propertyValue);
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}
			break;
		case GT:
			if(propertyClass==String.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else if(propertyClass==Date.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">to_date('"+propertyValue+"', 'yyyy-mm-dd')");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else{
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">"+propertyValue);
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}
		}
		return sb;
	}
	
	//=,like,<,>,<=,>=
	protected static StringBuffer toSqlString(final String propertyName, final Object propertyValue, final MatchType matchType, final Class<?> propertyClass,StringBuffer sb,final boolean ifAbs) {
		AssertUtils.hasText(propertyName, "propertyName不为空");
		switch (matchType) {
		case EQ:
			if(ifAbs){
				sb.append(" and "+propertyName+"=");
			}else{
				sb.append(" and "+propertyName+" like ");
			}
			boolean ifString =(propertyClass==String.class);
			if(ifString){
//				if(ifAbs){
//					sb.append("'");
//				}else{
//					sb.append("'%");
//				}
				sb.append("'");
			}
			sb.append(propertyValue);
			if(ifString){
//				if(ifAbs){
//					sb.append("'");
//				}else{
//					sb.append("%'");
//				}
				sb.append("'");
			}
			break;
		case LIKE:
			if(ifAbs){
				sb.append(" and "+propertyName+" = '"+propertyValue+"'");
			}else{
				sb.append(" and "+propertyName+" like '%"+propertyValue+"%'");
			}
			break;
		case LE:
			if(propertyClass==String.class){
				sb.append(" and "+propertyName+"<=to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
			}else if(propertyClass==Date.class){
				sb.append(" and "+propertyName+"<=to_date('"+propertyValue+"', 'yyyy-mm-dd')");
			}else{
				sb.append(" and "+propertyName+"<="+propertyValue);
			}
			break;
		case LT:
			if(propertyClass==String.class){
				sb.append(" and "+propertyName+"<to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
			}else if(propertyClass==Date.class){
				sb.append(" and "+propertyName+"<to_date('"+propertyValue+"', 'yyyy-mm-dd')");
			}else{
				sb.append(" and "+propertyName+"<"+propertyValue);
			}
			break;
		case GE:
			if(propertyClass==String.class){
				sb.append(" and "+propertyName+">=to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
			}else if(propertyClass==Date.class){
				sb.append(" and "+propertyName+">=to_date('"+propertyValue+"', 'yyyy-mm-dd')");
			}else{
				sb.append(" and "+propertyName+">="+propertyValue);
			}
			break;
		case GT:
			if(propertyClass==String.class){
				sb.append(" and "+propertyName+">to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
			}else if(propertyClass==Date.class){
				sb.append(" and "+propertyName+">to_date('"+propertyValue+"', 'yyyy-mm-dd')");
			}else{
				sb.append(" and "+propertyName+">"+propertyValue);
			}
		}
		return sb;
	}
	//通过数组
	protected static StringBuffer toSqlString(final String[] propertyNames, final Object propertyValue, final MatchType matchType, final Class<?> propertyClass,StringBuffer sb,final boolean ifAbs) {
		switch (matchType) {
		case EQ:
			sb.append(" and (");
			for(int i =0;i<propertyNames.length;i++){
				String param = propertyNames[i];
//				if(ifAbs){
//					sb.append(param+"=");
//				}else{
//					sb.append(param+" like ");
//				}
				sb.append(param+"=");
				boolean ifString =(propertyClass==String.class);
				if(ifString){
//					if(ifAbs){
//						sb.append("'");
//					}else{
//						sb.append("'%");
//					}
					sb.append("'");
				}
				sb.append(propertyValue);
				if(ifString){
//					if(ifAbs){
//						sb.append("'");
//					}else{
//						sb.append("%'");
//					}
					sb.append("'");
				}
				if(i!=(propertyNames.length-1)){
					sb.append(" or ");
				}
			}
			sb.append(")");
			break;
		case LIKE:
			sb.append(" and (");
			for(int i =0;i<propertyNames.length;i++){
				String param = propertyNames[i];
				if(ifAbs){
					sb.append(param+" = '"+propertyValue+"'");
				}else{
					sb.append(param+" like '%"+propertyValue+"%'");
				}
				if(i!=(propertyNames.length-1)){
					sb.append(" or ");
				}
			}
			sb.append(")");
			break;
		case LE:
			if(propertyClass==String.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<=to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else if(propertyClass==Date.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<=to_date('"+propertyValue+"', 'yyyy-mm-dd')");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else{
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<="+propertyValue);
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}
			break;
		case LT:
			if(propertyClass==String.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else if(propertyClass==Date.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<to_date('"+propertyValue+"', 'yyyy-mm-dd')");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else{
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+"<"+propertyValue);
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}
			break;
		case GE:
			if(propertyClass==String.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">=to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else if(propertyClass==Date.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">=to_date('"+propertyValue+"', 'yyyy-mm-dd')");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else{
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">="+propertyValue);
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}
			break;
		case GT:
			if(propertyClass==String.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">to_number(to_char(to_date('"+propertyValue+"', 'yyyy-mm-dd'),'yyyymmdd'))");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else if(propertyClass==Date.class){
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">to_date('"+propertyValue+"', 'yyyy-mm-dd')");
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}else{
				sb.append(" and (");
				for(int i =0;i<propertyNames.length;i++){
					String param = propertyNames[i];
					sb.append(param+">"+propertyValue);
					if(i!=(propertyNames.length-1)){
						sb.append(" or ");
					}
				}
				sb.append(")");
			}
		}
		return sb;
	}
	
	public static String buildStringByPropertyFilter(final List<PropertyFilter> filters) {
		StringBuffer sb = new StringBuffer();
		for (PropertyFilter filter : filters) {
			if (!filter.hasMultiProperties()) { //只查询一个字段,如filter_EQS_account
				sb = toSqlString(filter.getPropertyName(),filter.getMatchValue(), filter.getMatchType(),filter.getPropertyClass(),sb);
			} else {//查询两个字段,如filter_LIKES_realName_OR_email
				sb = toSqlString(filter.getPropertyNames(),filter.getMatchValue(), filter.getMatchType(),filter.getPropertyClass(),sb);
			}
		}
		if(sb==null||sb.length()<=0){
			return "";
		}
		return sb.toString();
	}
	public static String buildStringByPropertyFilter(final List<PropertyFilter> filters,final boolean ifAbs) {
		StringBuffer sb = new StringBuffer();
		for (PropertyFilter filter : filters) {
			if (!filter.hasMultiProperties()) { //只查询一个字段,如filter_EQS_account
				sb = toSqlString(filter.getPropertyName(),filter.getMatchValue(), filter.getMatchType(),filter.getPropertyClass(),sb,ifAbs);
			} else {//查询两个字段,如filter_LIKES_realName_OR_email
				sb = toSqlString(filter.getPropertyNames(),filter.getMatchValue(), filter.getMatchType(),filter.getPropertyClass(),sb,ifAbs);
			}
		}
		if(sb==null||sb.length()<=0){
			return "";
		}
		return sb.toString();
	}
}
