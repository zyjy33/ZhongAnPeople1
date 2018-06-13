package com.zams.www.health.model;

/**
 * Created by Administrator on 2018/6/12.
 */


public  class HealthEvaluateBean {
    /**
     * id : 4
     * user_id : 61219
     * user_name : 13249089599
     * medical_id : 22127
     * medical_name : 辨色力
     * vip_card :
     * order_submit_time : 2018-06-11 09:36:25
     * mobile : 13249089599
     * company_id : 5808
     * company_name : 朝阳服务大厅
     * operators :
     * evaluate_status : 4
     * evaluate_desc : 评价服务
     * waiter_status : 4
     * waiter_desc : 评价医生
     * add_time : 2018-06-12 14:47:38
     * update_time : 2018-06-12 14:47:38
     */

    private int id;
    private int user_id;
    private String user_name;
    private String medical_id;
    private String medical_name;
    private String vip_card;
    private String order_submit_time;
    private String mobile;
    private int company_id;
    private String company_name;
    private String operators;
    private int evaluate_status;
    private String evaluate_desc;
    private int waiter_status;
    private String waiter_desc;
    private String add_time;
    private String update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMedical_id() {
        return medical_id;
    }

    public void setMedical_id(String medical_id) {
        this.medical_id = medical_id;
    }

    public String getMedical_name() {
        return medical_name;
    }

    public void setMedical_name(String medical_name) {
        this.medical_name = medical_name;
    }

    public String getVip_card() {
        return vip_card;
    }

    public void setVip_card(String vip_card) {
        this.vip_card = vip_card;
    }

    public String getOrder_submit_time() {
        return order_submit_time;
    }

    public void setOrder_submit_time(String order_submit_time) {
        this.order_submit_time = order_submit_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public int getEvaluate_status() {
        return evaluate_status;
    }

    public void setEvaluate_status(int evaluate_status) {
        this.evaluate_status = evaluate_status;
    }

    public String getEvaluate_desc() {
        return evaluate_desc;
    }

    public void setEvaluate_desc(String evaluate_desc) {
        this.evaluate_desc = evaluate_desc;
    }

    public int getWaiter_status() {
        return waiter_status;
    }

    public void setWaiter_status(int waiter_status) {
        this.waiter_status = waiter_status;
    }

    public String getWaiter_desc() {
        return waiter_desc;
    }

    public void setWaiter_desc(String waiter_desc) {
        this.waiter_desc = waiter_desc;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}