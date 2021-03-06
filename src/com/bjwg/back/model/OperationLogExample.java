package com.bjwg.back.model;

import com.bjwg.back.base.Pages;
import com.bjwg.back.model.PayExample.Criterion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperationLogExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    protected Pages page;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public OperationLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public void setPage(Pages page) {
        this.page=page;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public Pages getPage() {
        return page;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        public void addCriterion(String condition) {
        	condition = "1=1 "+condition;
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andOpeIdIsNull() {
            addCriterion("op.ope_id is null");
            return (Criteria) this;
        }

        public Criteria andOpeIdIsNotNull() {
            addCriterion("op.ope_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpeIdEqualTo(Long value) {
            addCriterion("op.ope_id =", value, "opeId");
            return (Criteria) this;
        }

        public Criteria andOpeIdNotEqualTo(Long value) {
            addCriterion("op.ope_id <>", value, "opeId");
            return (Criteria) this;
        }

        public Criteria andOpeIdGreaterThan(Long value) {
            addCriterion("op.ope_id >", value, "opeId");
            return (Criteria) this;
        }

        public Criteria andOpeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("op.ope_id >=", value, "opeId");
            return (Criteria) this;
        }

        public Criteria andOpeIdLessThan(Long value) {
            addCriterion("op.ope_id <", value, "opeId");
            return (Criteria) this;
        }

        public Criteria andOpeIdLessThanOrEqualTo(Long value) {
            addCriterion("op.ope_id <=", value, "opeId");
            return (Criteria) this;
        }

        public Criteria andOpeIdIn(List<Long> values) {
            addCriterion("op.ope_id in", values, "opeId");
            return (Criteria) this;
        }

        public Criteria andOpeIdNotIn(List<Long> values) {
            addCriterion("op.ope_id not in", values, "opeId");
            return (Criteria) this;
        }

        public Criteria andOpeIdBetween(Long value1, Long value2) {
            addCriterion("op.ope_id between", value1, value2, "opeId");
            return (Criteria) this;
        }

        public Criteria andOpeIdNotBetween(Long value1, Long value2) {
            addCriterion("op.ope_id not between", value1, value2, "opeId");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIsNull() {
            addCriterion("op.ope_Object is null");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIsNotNull() {
            addCriterion("op.ope_Object is not null");
            return (Criteria) this;
        }

        public Criteria andOpeObjectEqualTo(String value) {
            addCriterion("op.ope_Object =", value, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectNotEqualTo(String value) {
            addCriterion("op.ope_Object <>", value, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectGreaterThan(String value) {
            addCriterion("op.ope_Object >", value, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectGreaterThanOrEqualTo(String value) {
            addCriterion("op.ope_Object >=", value, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectLessThan(String value) {
            addCriterion("op.ope_Object <", value, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectLessThanOrEqualTo(String value) {
            addCriterion("op.ope_Object <=", value, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectLike(String value) {
            addCriterion("op.ope_Object like", value, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectNotLike(String value) {
            addCriterion("op.ope_Object not like", value, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIn(List<String> values) {
            addCriterion("op.ope_Object in", values, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectNotIn(List<String> values) {
            addCriterion("op.ope_Object not in", values, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectBetween(String value1, String value2) {
            addCriterion("op.ope_Object between", value1, value2, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectNotBetween(String value1, String value2) {
            addCriterion("op.ope_Object not between", value1, value2, "opeObject");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdIsNull() {
            addCriterion("op.ope_Object_id is null");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdIsNotNull() {
            addCriterion("op.ope_Object_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdEqualTo(Long value) {
            addCriterion("op.ope_Object_id =", value, "opeObjectId");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdNotEqualTo(Long value) {
            addCriterion("op.ope_Object_id <>", value, "opeObjectId");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdGreaterThan(Long value) {
            addCriterion("op.ope_Object_id >", value, "opeObjectId");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdGreaterThanOrEqualTo(Long value) {
            addCriterion("op.ope_Object_id >=", value, "opeObjectId");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdLessThan(Long value) {
            addCriterion("op.ope_Object_id <", value, "opeObjectId");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdLessThanOrEqualTo(Long value) {
            addCriterion("op.ope_Object_id <=", value, "opeObjectId");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdIn(List<Long> values) {
            addCriterion("op.ope_Object_id in", values, "opeObjectId");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdNotIn(List<Long> values) {
            addCriterion("op.ope_Object_id not in", values, "opeObjectId");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdBetween(Long value1, Long value2) {
            addCriterion("op.ope_Object_id between", value1, value2, "opeObjectId");
            return (Criteria) this;
        }

        public Criteria andOpeObjectIdNotBetween(Long value1, Long value2) {
            addCriterion("op.ope_Object_id not between", value1, value2, "opeObjectId");
            return (Criteria) this;
        }

        public Criteria andOpeTimeIsNull() {
            addCriterion("op.ope_Time is null");
            return (Criteria) this;
        }

        public Criteria andOpeTimeIsNotNull() {
            addCriterion("op.ope_Time is not null");
            return (Criteria) this;
        }

        public Criteria andOpeTimeEqualTo(Date value) {
            addCriterion("op.ope_Time =", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeNotEqualTo(Date value) {
            addCriterion("op.ope_Time <>", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeGreaterThan(Date value) {
            addCriterion("op.ope_Time >", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("op.ope_Time >=", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeLessThan(Date value) {
            addCriterion("op.ope_Time <", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeLessThanOrEqualTo(Date value) {
            addCriterion("op.ope_Time <=", value, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeIn(List<Date> values) {
            addCriterion("op.ope_Time in", values, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeNotIn(List<Date> values) {
            addCriterion("op.ope_Time not in", values, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeBetween(Date value1, Date value2) {
            addCriterion("op.ope_Time between", value1, value2, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTimeNotBetween(Date value1, Date value2) {
            addCriterion("op.ope_Time not between", value1, value2, "opeTime");
            return (Criteria) this;
        }

        public Criteria andOpeTypeIsNull() {
            addCriterion("op.ope_Type is null");
            return (Criteria) this;
        }

        public Criteria andOpeTypeIsNotNull() {
            addCriterion("op.ope_Type is not null");
            return (Criteria) this;
        }

        public Criteria andOpeTypeEqualTo(String value) {
            addCriterion("op.ope_Type =", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeNotEqualTo(String value) {
            addCriterion("op.ope_Type <>", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeGreaterThan(String value) {
            addCriterion("op.ope_Type >", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("op.ope_Type >=", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeLessThan(String value) {
            addCriterion("op.ope_Type <", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeLessThanOrEqualTo(String value) {
            addCriterion("op.ope_Type <=", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeLike(String value) {
            addCriterion("op.ope_Type like", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeNotLike(String value) {
            addCriterion("op.ope_Type not like", value, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeIn(List<String> values) {
            addCriterion("op.ope_Type in", values, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeNotIn(List<String> values) {
            addCriterion("op.ope_Type not in", values, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeBetween(String value1, String value2) {
            addCriterion("op.ope_Type between", value1, value2, "opeType");
            return (Criteria) this;
        }

        public Criteria andOpeTypeNotBetween(String value1, String value2) {
            addCriterion("op.ope_Type not between", value1, value2, "opeType");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("op.operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("op.operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(Long value) {
            addCriterion("op.operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(Long value) {
            addCriterion("op.operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(Long value) {
            addCriterion("op.operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(Long value) {
            addCriterion("op.operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(Long value) {
            addCriterion("op.operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(Long value) {
            addCriterion("op.operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<Long> values) {
            addCriterion("op.operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<Long> values) {
            addCriterion("op.operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(Long value1, Long value2) {
            addCriterion("op.operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(Long value1, Long value2) {
            addCriterion("op.operator not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNameIsNull() {
            addCriterion("op.operator_Name is null");
            return (Criteria) this;
        }

        public Criteria andOperatorNameIsNotNull() {
            addCriterion("op.operator_Name is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorNameEqualTo(String value) {
            addCriterion("op.operator_Name =", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotEqualTo(String value) {
            addCriterion("op.operator_Name <>", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameGreaterThan(String value) {
            addCriterion("op.operator_Name >", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameGreaterThanOrEqualTo(String value) {
            addCriterion("op.operator_Name >=", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameLessThan(String value) {
            addCriterion("op.operator_Name <", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameLessThanOrEqualTo(String value) {
            addCriterion("op.operator_Name <=", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameLike(String value) {
            addCriterion("op.operator_Name like", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotLike(String value) {
            addCriterion("op.operator_Name not like", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameIn(List<String> values) {
            addCriterion("op.operator_Name in", values, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotIn(List<String> values) {
            addCriterion("op.operator_Name not in", values, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameBetween(String value1, String value2) {
            addCriterion("op.operator_Name between", value1, value2, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotBetween(String value1, String value2) {
            addCriterion("op.operator_Name not between", value1, value2, "operatorName");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("op.remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("op.remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("op.remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("op.remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("op.remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("op.remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("op.remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("op.remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("op.remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("op.remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("op.remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("op.remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("op.remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("op.remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andOpeModuleIsNull() {
            addCriterion("op.ope_Module is null");
            return (Criteria) this;
        }

        public Criteria andOpeModuleIsNotNull() {
            addCriterion("op.ope_Module is not null");
            return (Criteria) this;
        }

        public Criteria andOpeModuleEqualTo(String value) {
            addCriterion("op.ope_Module =", value, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleNotEqualTo(String value) {
            addCriterion("op.ope_Module <>", value, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleGreaterThan(String value) {
            addCriterion("op.ope_Module >", value, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleGreaterThanOrEqualTo(String value) {
            addCriterion("op.ope_Module >=", value, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleLessThan(String value) {
            addCriterion("op.ope_Module <", value, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleLessThanOrEqualTo(String value) {
            addCriterion("op.ope_Module <=", value, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleLike(String value) {
            addCriterion("op.ope_Module like", value, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleNotLike(String value) {
            addCriterion("op.ope_Module not like", value, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleIn(List<String> values) {
            addCriterion("op.ope_Module in", values, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleNotIn(List<String> values) {
            addCriterion("op.ope_Module not in", values, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleBetween(String value1, String value2) {
            addCriterion("op.ope_Module between", value1, value2, "opeModule");
            return (Criteria) this;
        }

        public Criteria andOpeModuleNotBetween(String value1, String value2) {
            addCriterion("op.ope_Module not between", value1, value2, "opeModule");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table bjwg_operation_log
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}