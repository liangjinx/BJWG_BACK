package com.bjwg.back.model;

import java.math.BigDecimal;
import java.util.Date;

public class MyCoupon {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.my_coupon_id
     *
     * @mbggenerated
     */
    private Long myCouponId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.coupon_name
     *
     * @mbggenerated
     */
    private String couponName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.coupon_money
     *
     * @mbggenerated
     */
    private BigDecimal couponMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.coupon_id
     *
     * @mbggenerated
     */
    private Long couponId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.begin_time
     *
     * @mbggenerated
     */
    private Date beginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.end_time
     *
     * @mbggenerated
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.coupon_code
     *
     * @mbggenerated
     */
    private String couponCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.relation_id
     *
     * @mbggenerated
     */
    private Long relationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.relation_type
     *
     * @mbggenerated
     */
    private Byte relationType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.coupon_img
     *
     * @mbggenerated
     */
    private String couponImg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.status
     *
     * @mbggenerated
     */
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.last_use_time
     *
     * @mbggenerated
     */
    private Date lastUseTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.can_use_money
     *
     * @mbggenerated
     */
    private BigDecimal canUseMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.ctime
     *
     * @mbggenerated
     */
    private Date ctime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_my_coupon.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.my_coupon_id
     *
     * @return the value of bjwg_my_coupon.my_coupon_id
     *
     * @mbggenerated
     */
    public Long getMyCouponId() {
        return myCouponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.my_coupon_id
     *
     * @param myCouponId the value for bjwg_my_coupon.my_coupon_id
     *
     * @mbggenerated
     */
    public void setMyCouponId(Long myCouponId) {
        this.myCouponId = myCouponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.coupon_name
     *
     * @return the value of bjwg_my_coupon.coupon_name
     *
     * @mbggenerated
     */
    public String getCouponName() {
        return couponName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.coupon_name
     *
     * @param couponName the value for bjwg_my_coupon.coupon_name
     *
     * @mbggenerated
     */
    public void setCouponName(String couponName) {
        this.couponName = couponName == null ? null : couponName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.coupon_money
     *
     * @return the value of bjwg_my_coupon.coupon_money
     *
     * @mbggenerated
     */
    public BigDecimal getCouponMoney() {
        return couponMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.coupon_money
     *
     * @param couponMoney the value for bjwg_my_coupon.coupon_money
     *
     * @mbggenerated
     */
    public void setCouponMoney(BigDecimal couponMoney) {
        this.couponMoney = couponMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.coupon_id
     *
     * @return the value of bjwg_my_coupon.coupon_id
     *
     * @mbggenerated
     */
    public Long getCouponId() {
        return couponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.coupon_id
     *
     * @param couponId the value for bjwg_my_coupon.coupon_id
     *
     * @mbggenerated
     */
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.begin_time
     *
     * @return the value of bjwg_my_coupon.begin_time
     *
     * @mbggenerated
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.begin_time
     *
     * @param beginTime the value for bjwg_my_coupon.begin_time
     *
     * @mbggenerated
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.end_time
     *
     * @return the value of bjwg_my_coupon.end_time
     *
     * @mbggenerated
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.end_time
     *
     * @param endTime the value for bjwg_my_coupon.end_time
     *
     * @mbggenerated
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.coupon_code
     *
     * @return the value of bjwg_my_coupon.coupon_code
     *
     * @mbggenerated
     */
    public String getCouponCode() {
        return couponCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.coupon_code
     *
     * @param couponCode the value for bjwg_my_coupon.coupon_code
     *
     * @mbggenerated
     */
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode == null ? null : couponCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.relation_id
     *
     * @return the value of bjwg_my_coupon.relation_id
     *
     * @mbggenerated
     */
    public Long getRelationId() {
        return relationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.relation_id
     *
     * @param relationId the value for bjwg_my_coupon.relation_id
     *
     * @mbggenerated
     */
    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.relation_type
     *
     * @return the value of bjwg_my_coupon.relation_type
     *
     * @mbggenerated
     */
    public Byte getRelationType() {
        return relationType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.relation_type
     *
     * @param relationType the value for bjwg_my_coupon.relation_type
     *
     * @mbggenerated
     */
    public void setRelationType(Byte relationType) {
        this.relationType = relationType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.user_id
     *
     * @return the value of bjwg_my_coupon.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.user_id
     *
     * @param userId the value for bjwg_my_coupon.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.coupon_img
     *
     * @return the value of bjwg_my_coupon.coupon_img
     *
     * @mbggenerated
     */
    public String getCouponImg() {
        return couponImg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.coupon_img
     *
     * @param couponImg the value for bjwg_my_coupon.coupon_img
     *
     * @mbggenerated
     */
    public void setCouponImg(String couponImg) {
        this.couponImg = couponImg == null ? null : couponImg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.status
     *
     * @return the value of bjwg_my_coupon.status
     *
     * @mbggenerated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.status
     *
     * @param status the value for bjwg_my_coupon.status
     *
     * @mbggenerated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.last_use_time
     *
     * @return the value of bjwg_my_coupon.last_use_time
     *
     * @mbggenerated
     */
    public Date getLastUseTime() {
        return lastUseTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.last_use_time
     *
     * @param lastUseTime the value for bjwg_my_coupon.last_use_time
     *
     * @mbggenerated
     */
    public void setLastUseTime(Date lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.can_use_money
     *
     * @return the value of bjwg_my_coupon.can_use_money
     *
     * @mbggenerated
     */
    public BigDecimal getCanUseMoney() {
        return canUseMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.can_use_money
     *
     * @param canUseMoney the value for bjwg_my_coupon.can_use_money
     *
     * @mbggenerated
     */
    public void setCanUseMoney(BigDecimal canUseMoney) {
        this.canUseMoney = canUseMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.ctime
     *
     * @return the value of bjwg_my_coupon.ctime
     *
     * @mbggenerated
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.ctime
     *
     * @param ctime the value for bjwg_my_coupon.ctime
     *
     * @mbggenerated
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_my_coupon.remark
     *
     * @return the value of bjwg_my_coupon.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_my_coupon.remark
     *
     * @param remark the value for bjwg_my_coupon.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
}