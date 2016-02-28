package com.bjwg.back.vo;

public class HomeView {
	
	private Long userCount;
	private Long orderCount;
	private Long waitOrderCount;
	private Double orderTotalMoney;
	private Double withdrawMoney;
	private Long withdrawCount;
	
	
	public Long getUserCount() {
		return userCount==null?0:userCount;
	}
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}
	public Long getOrderCount() {
		return orderCount==null?0:orderCount;
	}
	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
	public Long getWaitOrderCount() {
		return waitOrderCount==null?0:waitOrderCount;
	}
	public void setWaitOrderCount(Long waitOrderCount) {
		this.waitOrderCount = waitOrderCount;
	}
	public Double getOrderTotalMoney() {
		return orderTotalMoney==null?0.00:orderTotalMoney;
	}
	public void setOrderTotalMoney(Double orderTotalMoney) {
		this.orderTotalMoney = orderTotalMoney;
	}
	public Double getWithdrawMoney() {
		return withdrawMoney==null?0.00:withdrawMoney;
	}
	public void setWithdrawMoney(Double withdrawMoney) {
		this.withdrawMoney = withdrawMoney;
	}
	public Long getWithdrawCount() {
		return withdrawCount==null?0:withdrawCount;
	}
	public void setWithdrawCount(Long withdrawCount) {
		this.withdrawCount = withdrawCount;
	}
	
}
