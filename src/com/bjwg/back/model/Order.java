package com.bjwg.back.model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.order_id
     *
     * @mbggenerated
     */
    private Long orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.order_code
     *
     * @mbggenerated
     */
    private String orderCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.ctime
     *
     * @mbggenerated
     */
    private Date ctime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.type
     *
     * @mbggenerated
     */
    private Byte type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.status
     *
     * @mbggenerated
     */
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.num
     *
     * @mbggenerated
     */
    private Short num;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.price
     *
     * @mbggenerated
     */
    private BigDecimal price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.total_money
     *
     * @mbggenerated
     */
    private BigDecimal totalMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.pay_time
     *
     * @mbggenerated
     */
    private Date payTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.product_name
     *
     * @mbggenerated
     */
    private String productName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.pay_type
     *
     * @mbggenerated
     */
    private Byte payType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.relation_id
     *
     * @mbggenerated
     */
    private Long relationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.relation_name
     *
     * @mbggenerated
     */
    private String relationName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.over_time
     *
     * @mbggenerated
     */
    private Date overTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.product_img
     *
     * @mbggenerated
     */
    private String productImg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.sub_order_id
     *
     * @mbggenerated
     */
    private Long subOrderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.confirm_time
     *
     * @mbggenerated
     */
    private Date confirmTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.prepay_order_code
     *
     * @mbggenerated
     */
    private String prepayOrderCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_order.is_show
     *
     * @mbggenerated
     */
    private Byte isShow;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.order_id
     *
     * @return the value of bjwg_order.order_id
     *
     * @mbggenerated
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.order_id
     *
     * @param orderId the value for bjwg_order.order_id
     *
     * @mbggenerated
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.order_code
     *
     * @return the value of bjwg_order.order_code
     *
     * @mbggenerated
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.order_code
     *
     * @param orderCode the value for bjwg_order.order_code
     *
     * @mbggenerated
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.ctime
     *
     * @return the value of bjwg_order.ctime
     *
     * @mbggenerated
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.ctime
     *
     * @param ctime the value for bjwg_order.ctime
     *
     * @mbggenerated
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.type
     *
     * @return the value of bjwg_order.type
     *
     * @mbggenerated
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.type
     *
     * @param type the value for bjwg_order.type
     *
     * @mbggenerated
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.status
     *
     * @return the value of bjwg_order.status
     *
     * @mbggenerated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.status
     *
     * @param status the value for bjwg_order.status
     *
     * @mbggenerated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.num
     *
     * @return the value of bjwg_order.num
     *
     * @mbggenerated
     */
    public Short getNum() {
        return num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.num
     *
     * @param num the value for bjwg_order.num
     *
     * @mbggenerated
     */
    public void setNum(Short num) {
        this.num = num;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.price
     *
     * @return the value of bjwg_order.price
     *
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.price
     *
     * @param price the value for bjwg_order.price
     *
     * @mbggenerated
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.total_money
     *
     * @return the value of bjwg_order.total_money
     *
     * @mbggenerated
     */
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.total_money
     *
     * @param totalMoney the value for bjwg_order.total_money
     *
     * @mbggenerated
     */
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.pay_time
     *
     * @return the value of bjwg_order.pay_time
     *
     * @mbggenerated
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.pay_time
     *
     * @param payTime the value for bjwg_order.pay_time
     *
     * @mbggenerated
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.user_id
     *
     * @return the value of bjwg_order.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.user_id
     *
     * @param userId the value for bjwg_order.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.remark
     *
     * @return the value of bjwg_order.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.remark
     *
     * @param remark the value for bjwg_order.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.product_name
     *
     * @return the value of bjwg_order.product_name
     *
     * @mbggenerated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.product_name
     *
     * @param productName the value for bjwg_order.product_name
     *
     * @mbggenerated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.pay_type
     *
     * @return the value of bjwg_order.pay_type
     *
     * @mbggenerated
     */
    public Byte getPayType() {
        return payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.pay_type
     *
     * @param payType the value for bjwg_order.pay_type
     *
     * @mbggenerated
     */
    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.relation_id
     *
     * @return the value of bjwg_order.relation_id
     *
     * @mbggenerated
     */
    public Long getRelationId() {
        return relationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.relation_id
     *
     * @param relationId the value for bjwg_order.relation_id
     *
     * @mbggenerated
     */
    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.relation_name
     *
     * @return the value of bjwg_order.relation_name
     *
     * @mbggenerated
     */
    public String getRelationName() {
        return relationName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.relation_name
     *
     * @param relationName the value for bjwg_order.relation_name
     *
     * @mbggenerated
     */
    public void setRelationName(String relationName) {
        this.relationName = relationName == null ? null : relationName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.over_time
     *
     * @return the value of bjwg_order.over_time
     *
     * @mbggenerated
     */
    public Date getOverTime() {
        return overTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.over_time
     *
     * @param overTime the value for bjwg_order.over_time
     *
     * @mbggenerated
     */
    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.product_img
     *
     * @return the value of bjwg_order.product_img
     *
     * @mbggenerated
     */
    public String getProductImg() {
        return productImg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.product_img
     *
     * @param productImg the value for bjwg_order.product_img
     *
     * @mbggenerated
     */
    public void setProductImg(String productImg) {
        this.productImg = productImg == null ? null : productImg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.sub_order_id
     *
     * @return the value of bjwg_order.sub_order_id
     *
     * @mbggenerated
     */
    public Long getSubOrderId() {
        return subOrderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.sub_order_id
     *
     * @param subOrderId the value for bjwg_order.sub_order_id
     *
     * @mbggenerated
     */
    public void setSubOrderId(Long subOrderId) {
        this.subOrderId = subOrderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.confirm_time
     *
     * @return the value of bjwg_order.confirm_time
     *
     * @mbggenerated
     */
    public Date getConfirmTime() {
        return confirmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.confirm_time
     *
     * @param confirmTime the value for bjwg_order.confirm_time
     *
     * @mbggenerated
     */
    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.prepay_order_code
     *
     * @return the value of bjwg_order.prepay_order_code
     *
     * @mbggenerated
     */
    public String getPrepayOrderCode() {
        return prepayOrderCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.prepay_order_code
     *
     * @param prepayOrderCode the value for bjwg_order.prepay_order_code
     *
     * @mbggenerated
     */
    public void setPrepayOrderCode(String prepayOrderCode) {
        this.prepayOrderCode = prepayOrderCode == null ? null : prepayOrderCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_order.is_show
     *
     * @return the value of bjwg_order.is_show
     *
     * @mbggenerated
     */
    public Byte getIsShow() {
        return isShow;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_order.is_show
     *
     * @param isShow the value for bjwg_order.is_show
     *
     * @mbggenerated
     */
    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }
    private String username;
private Byte flag;


	public Byte getFlag() {
	return flag;
}

public void setFlag(Byte flag) {
	this.flag = flag;
}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
}