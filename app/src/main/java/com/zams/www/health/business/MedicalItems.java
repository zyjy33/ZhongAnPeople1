package com.zams.www.health.business;

public class MedicalItems {
    private int id;
    private int medical_type;
    private String medical_name;
    private int medical_price;
    private int points;
    private String normal_value;
    private String medical_barcode;
    private String service_ids;
    private String img_url;
    private String content;
    private int goodsCount = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedical_type() {
        return medical_type;
    }

    public void setMedical_type(int medical_type) {
        this.medical_type = medical_type;
    }

    public String getMedical_name() {
        return medical_name;
    }

    public void setMedical_name(String medical_name) {
        this.medical_name = medical_name;
    }

    public int getMedical_price() {
        return medical_price;
    }

    public void setMedical_price(int medical_price) {
        this.medical_price = medical_price;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getNormal_value() {
        return normal_value;
    }

    public void setNormal_value(String normal_value) {
        this.normal_value = normal_value;
    }

    public String getMedical_barcode() {
        return medical_barcode;
    }

    public void setMedical_barcode(String medical_barcode) {
        this.medical_barcode = medical_barcode;
    }

    public String getService_ids() {
        return service_ids;
    }

    public void setService_ids(String service_ids) {
        this.service_ids = service_ids;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }
}
