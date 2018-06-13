package com.zams.www.health.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class HealthOrder {
    /**
     * id : 58260
     * datatype : 2
     * order_no : B180601141820182003
     * trade_no : T180601141820182076
     * company_id : 96
     * company_name : 中安民生养老服务有限公司
     * user_id : 61219
     * user_name : 13249089599
     * consigner_id : 0
     * consigner_name : null
     * sale_id : 0
     * sale_name : null
     * pay_id : 0
     * pay_name : null
     * share_id : 0
     * share_name : null
     * payment_id : 2
     * payment_fee : 0
     * payment_status : 4
     * payment_time : 2018-06-01 14:18:29
     * express_id : 1007
     * express_no : null
     * express_fee : 0
     * express_status : 1
     * express_time : null
     * express_type : 1
     * accept_name : 范德萨
     * accept_no : null
     * post_code :
     * telphone :
     * mobile : 151511664654
     * email :
     * province : 北京
     * area : 东城区
     * city : 北京市内
     * street :
     * address : fff
     * message :
     * remark : null
     * is_invoice : 0
     * invoice_title : null
     * invoice_taxes : 0
     * cashing_packet_total : 0
     * cashing_point_total : 0
     * exchange_price_total : 0
     * exchange_point_total : 1
     * sell_price_total : 0
     * cost_price_total : 0
     * rebate_price_total : 0
     * give_packet_total : 0
     * give_sinup_point_total : 0
     * give_sinin_point_total : 0
     * give_pension_total : 0
     * give_sinup_exp_total : 0
     * give_sinin_exp_total : 0
     * payable_amount : 0
     * real_amount : 0
     * confirm_time : 2018-06-01 14:18:20
     * complete_time : null
     * rebate_status : 1
     * rebate_time : null
     * platform_id : 2
     * expenses_id : 6
     * status : 4
     * settlement_amount : 0
     * settlement_status : 1
     * settlement_time : null
     * settlement_date : null
     * add_time : 2018-06-01 14:18:20
     * update_time : 2018-06-01 14:18:20
     * medical_record : []
     */

    private int id;
    private int datatype;
    private String order_no;
    private String trade_no;
    private int company_id;
    private String company_name;
    private int user_id;
    private String user_name;
    private int consigner_id;
    private Object consigner_name;
    private int sale_id;
    private Object sale_name;
    private int pay_id;
    private Object pay_name;
    private int share_id;
    private Object share_name;
    private int payment_id;
    private double payment_fee;
    private int payment_status;
    private String payment_time;
    private int express_id;
    private Object express_no;
    private double express_fee;
    private int express_status;
    private Object express_time;
    private int express_type;
    private String accept_name;
    private Object accept_no;
    private String post_code;
    private String telphone;
    private String mobile;
    private String email;
    private String province;
    private String area;
    private String city;
    private String street;
    private String address;
    private String message;
    private Object remark;
    private int is_invoice;
    private Object invoice_title;
    private int invoice_taxes;
    private double cashing_packet_total;
    private int cashing_point_total;
    private double exchange_price_total;
    private int exchange_point_total;
    private double sell_price_total;
    private double cost_price_total;
    private double rebate_price_total;
    private double give_packet_total;
    private int give_sinup_point_total;
    private int give_sinin_point_total;
    private int give_pension_total;
    private int give_sinup_exp_total;
    private int give_sinin_exp_total;
    private double payable_amount;
    private double real_amount;
    private String confirm_time;
    private Object complete_time;
    private int rebate_status;
    private Object rebate_time;
    private int platform_id;
    private int expenses_id;
    @JSONField(name = "status")
    private int statusX;
    private int settlement_amount;
    private int settlement_status;
    private Object settlement_time;
    private Object settlement_date;
    private String add_time;
    private String update_time;
    private List<MedicalRecordBean> medical_record;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDatatype() {
        return datatype;
    }

    public void setDatatype(int datatype) {
        this.datatype = datatype;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
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

    public int getConsigner_id() {
        return consigner_id;
    }

    public void setConsigner_id(int consigner_id) {
        this.consigner_id = consigner_id;
    }

    public Object getConsigner_name() {
        return consigner_name;
    }

    public void setConsigner_name(Object consigner_name) {
        this.consigner_name = consigner_name;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public Object getSale_name() {
        return sale_name;
    }

    public void setSale_name(Object sale_name) {
        this.sale_name = sale_name;
    }

    public int getPay_id() {
        return pay_id;
    }

    public void setPay_id(int pay_id) {
        this.pay_id = pay_id;
    }

    public Object getPay_name() {
        return pay_name;
    }

    public void setPay_name(Object pay_name) {
        this.pay_name = pay_name;
    }

    public int getShare_id() {
        return share_id;
    }

    public void setShare_id(int share_id) {
        this.share_id = share_id;
    }

    public Object getShare_name() {
        return share_name;
    }

    public void setShare_name(Object share_name) {
        this.share_name = share_name;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public double getPayment_fee() {
        return payment_fee;
    }

    public void setPayment_fee(double payment_fee) {
        this.payment_fee = payment_fee;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public int getExpress_id() {
        return express_id;
    }

    public void setExpress_id(int express_id) {
        this.express_id = express_id;
    }

    public Object getExpress_no() {
        return express_no;
    }

    public void setExpress_no(Object express_no) {
        this.express_no = express_no;
    }

    public double getExpress_fee() {
        return express_fee;
    }

    public void setExpress_fee(double express_fee) {
        this.express_fee = express_fee;
    }

    public int getExpress_status() {
        return express_status;
    }

    public void setExpress_status(int express_status) {
        this.express_status = express_status;
    }

    public Object getExpress_time() {
        return express_time;
    }

    public void setExpress_time(Object express_time) {
        this.express_time = express_time;
    }

    public int getExpress_type() {
        return express_type;
    }

    public void setExpress_type(int express_type) {
        this.express_type = express_type;
    }

    public String getAccept_name() {
        return accept_name;
    }

    public void setAccept_name(String accept_name) {
        this.accept_name = accept_name;
    }

    public Object getAccept_no() {
        return accept_no;
    }

    public void setAccept_no(Object accept_no) {
        this.accept_no = accept_no;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public int getIs_invoice() {
        return is_invoice;
    }

    public void setIs_invoice(int is_invoice) {
        this.is_invoice = is_invoice;
    }

    public Object getInvoice_title() {
        return invoice_title;
    }

    public void setInvoice_title(Object invoice_title) {
        this.invoice_title = invoice_title;
    }

    public int getInvoice_taxes() {
        return invoice_taxes;
    }

    public void setInvoice_taxes(int invoice_taxes) {
        this.invoice_taxes = invoice_taxes;
    }

    public double getCashing_packet_total() {
        return cashing_packet_total;
    }

    public void setCashing_packet_total(double cashing_packet_total) {
        this.cashing_packet_total = cashing_packet_total;
    }

    public int getCashing_point_total() {
        return cashing_point_total;
    }

    public void setCashing_point_total(int cashing_point_total) {
        this.cashing_point_total = cashing_point_total;
    }

    public double getExchange_price_total() {
        return exchange_price_total;
    }

    public void setExchange_price_total(double exchange_price_total) {
        this.exchange_price_total = exchange_price_total;
    }

    public int getExchange_point_total() {
        return exchange_point_total;
    }

    public void setExchange_point_total(int exchange_point_total) {
        this.exchange_point_total = exchange_point_total;
    }

    public double getSell_price_total() {
        return sell_price_total;
    }

    public void setSell_price_total(double sell_price_total) {
        this.sell_price_total = sell_price_total;
    }

    public double getCost_price_total() {
        return cost_price_total;
    }

    public void setCost_price_total(double cost_price_total) {
        this.cost_price_total = cost_price_total;
    }

    public double getRebate_price_total() {
        return rebate_price_total;
    }

    public void setRebate_price_total(double rebate_price_total) {
        this.rebate_price_total = rebate_price_total;
    }

    public double getGive_packet_total() {
        return give_packet_total;
    }

    public void setGive_packet_total(double give_packet_total) {
        this.give_packet_total = give_packet_total;
    }

    public int getGive_sinup_point_total() {
        return give_sinup_point_total;
    }

    public void setGive_sinup_point_total(int give_sinup_point_total) {
        this.give_sinup_point_total = give_sinup_point_total;
    }

    public int getGive_sinin_point_total() {
        return give_sinin_point_total;
    }

    public void setGive_sinin_point_total(int give_sinin_point_total) {
        this.give_sinin_point_total = give_sinin_point_total;
    }

    public int getGive_pension_total() {
        return give_pension_total;
    }

    public void setGive_pension_total(int give_pension_total) {
        this.give_pension_total = give_pension_total;
    }

    public int getGive_sinup_exp_total() {
        return give_sinup_exp_total;
    }

    public void setGive_sinup_exp_total(int give_sinup_exp_total) {
        this.give_sinup_exp_total = give_sinup_exp_total;
    }

    public int getGive_sinin_exp_total() {
        return give_sinin_exp_total;
    }

    public void setGive_sinin_exp_total(int give_sinin_exp_total) {
        this.give_sinin_exp_total = give_sinin_exp_total;
    }

    public double getPayable_amount() {
        return payable_amount;
    }

    public void setPayable_amount(double payable_amount) {
        this.payable_amount = payable_amount;
    }

    public double getReal_amount() {
        return real_amount;
    }

    public void setReal_amount(double real_amount) {
        this.real_amount = real_amount;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public Object getComplete_time() {
        return complete_time;
    }

    public void setComplete_time(Object complete_time) {
        this.complete_time = complete_time;
    }

    public int getRebate_status() {
        return rebate_status;
    }

    public void setRebate_status(int rebate_status) {
        this.rebate_status = rebate_status;
    }

    public Object getRebate_time() {
        return rebate_time;
    }

    public void setRebate_time(Object rebate_time) {
        this.rebate_time = rebate_time;
    }

    public int getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(int platform_id) {
        this.platform_id = platform_id;
    }

    public int getExpenses_id() {
        return expenses_id;
    }

    public void setExpenses_id(int expenses_id) {
        this.expenses_id = expenses_id;
    }

    public int getStatusX() {
        return statusX;
    }

    public void setStatusX(int statusX) {
        this.statusX = statusX;
    }

    public int getSettlement_amount() {
        return settlement_amount;
    }

    public void setSettlement_amount(int settlement_amount) {
        this.settlement_amount = settlement_amount;
    }

    public int getSettlement_status() {
        return settlement_status;
    }

    public void setSettlement_status(int settlement_status) {
        this.settlement_status = settlement_status;
    }

    public Object getSettlement_time() {
        return settlement_time;
    }

    public void setSettlement_time(Object settlement_time) {
        this.settlement_time = settlement_time;
    }

    public Object getSettlement_date() {
        return settlement_date;
    }

    public void setSettlement_date(Object settlement_date) {
        this.settlement_date = settlement_date;
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

    public List<MedicalRecordBean> getMedical_record() {
        return medical_record;
    }

    public void setMedical_record(List<MedicalRecordBean> medical_record) {
        this.medical_record = medical_record;
    }
}