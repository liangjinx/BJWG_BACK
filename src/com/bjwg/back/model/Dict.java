package com.bjwg.back.model;

public class Dict {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_dict.dict_id
     *
     * @mbggenerated
     */
    private Long dictId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_dict.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_dict.code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bjwg_dict.value
     *
     * @mbggenerated
     */
    private String value;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_dict.dict_id
     *
     * @return the value of bjwg_dict.dict_id
     *
     * @mbggenerated
     */
    public Long getDictId() {
        return dictId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_dict.dict_id
     *
     * @param dictId the value for bjwg_dict.dict_id
     *
     * @mbggenerated
     */
    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_dict.name
     *
     * @return the value of bjwg_dict.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_dict.name
     *
     * @param name the value for bjwg_dict.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_dict.code
     *
     * @return the value of bjwg_dict.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_dict.code
     *
     * @param code the value for bjwg_dict.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bjwg_dict.value
     *
     * @return the value of bjwg_dict.value
     *
     * @mbggenerated
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bjwg_dict.value
     *
     * @param value the value for bjwg_dict.value
     *
     * @mbggenerated
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }
}