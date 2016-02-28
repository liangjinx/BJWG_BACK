package com.bjwg.back.base;

import java.util.List;


/**
 * 分页器
 * @author :Allen  
 * @CreateDate : 2015-3-16 上午11:48:31 
 * @lastModified : 2015-3-16 上午11:48:31 
 * @version : 1.0
 * @jdk：1.6
 * @param <T>
 */
public class Pages<T> {

	//当前页
	private int currentPage;
	//当前行
	private int currentRow;
	//当前截止行
	private int currentEndRow;
	//一页记录数
	private int perRows;
	//总页数
	private int pageCount;
	//总记录数
	private int countRows;
	//查询条件sql
	private String whereSql;
	
	//分页后的结果集
	private List<T> root;
	
	public int getCurrentPage() {
		
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		if(currentPage <= 0)
			currentPage = 1;
		this.currentPage = currentPage;
	}
	
	public int getCurrentRow() {
		return perRows * (currentPage - 1);
//		return perRows * (currentPage - 1) + 1;
	}
	public void setCurrentRow(int currentRow) {
		
		this.currentRow = currentRow;
	}
	public int getPerRows() {
		
		return perRows;
	}
	public void setPerRows(int perRows) {
		if(perRows <= 0)
			perRows = 1;
		this.perRows = perRows;
	}
	
	/**
	 * 计算总页数
	 * @return
	 */
	public int getPageCount() {
		
		if(countRows > 0){
			
			int left = countRows % perRows;
			
			pageCount = countRows / perRows ;
			
			if(left > 0){
				
				pageCount += 1;
			}
		}
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		
		
		this.pageCount = pageCount;
	}

	public int getCountRows() {
		return countRows;
	}

	public void setCountRows(int countRows) {
		
		pageCount = countRows / perRows;
		
		int left = countRows % perRows;
		
		if(left > 0){
			
			pageCount += 1;
		}

		if(pageCount < currentPage){
		
			currentPage = pageCount + 1;
		}
		
		this.countRows = countRows;
	}

	public int getCurrentEndRow() {
		
		currentEndRow = currentPage  * perRows;
		
		return currentEndRow;
	}

	public void setCurrentEndRow(int currentEndRow) {
		this.currentEndRow = currentEndRow;
	}

	public List<T> getRoot() {
		return root;
	}

	public void setRoot(List<T> root) {
		this.root = root;
	}

	public String getWhereSql() {
		return whereSql;
	}

	public void setWhereSql(String whereSql) {
		this.whereSql = whereSql;
	}
}
