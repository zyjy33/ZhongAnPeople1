package com.zams.www.health.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.android.hengyu.web.RealmName;

/**
 * Created by Administrator on 2018/6/13.
 */
public class SysNoticeTypeBean {
    /**
     * id : 1
     * datatype_id : 1
     * title : 系统通知
     * subtitle : 可随时编辑新的公告，随时发出平台通知
     * call_index : M201806072041287103
     * parent_id : 0
     * class_list : ,1,
     * class_layer : 1
     * sort_id : 1
     * img_url : /upload/201806/12/201806121647334864.png
     * content : {}
     * user_id : 1
     * user_name : administrator
     * is_system : 1
     * status : 0
     * update_time : 2018-06-12 16:47:35
     * reads : 0
     */

    private int id;
    private int datatype_id;
    private String title;
    private String subtitle;
    private String call_index;
    private int parent_id;
    private String class_list;
    private int class_layer;
    private int sort_id;
    private String img_url;
    private String content;
    private int user_id;
    private String user_name;
    private int is_system;
    @JSONField(name = "status")
    private int statusX;
    private String update_time;
    private int reads;

    public void setImgResId(int mImgResId) {
        this.mImgResId = mImgResId;
    }

    private int mImgResId = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDatatype_id() {
        return datatype_id;
    }

    public void setDatatype_id(int datatype_id) {
        this.datatype_id = datatype_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCall_index() {
        return call_index;
    }

    public void setCall_index(String call_index) {
        this.call_index = call_index;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getClass_list() {
        return class_list;
    }

    public void setClass_list(String class_list) {
        this.class_list = class_list;
    }

    public int getClass_layer() {
        return class_layer;
    }

    public void setClass_layer(int class_layer) {
        this.class_layer = class_layer;
    }

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }

    public String getImg_url() {
        if (img_url != null && img_url.startsWith("http")) {
            return img_url;
        } else {
            return RealmName.REALM_NAME + img_url;
        }
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getIs_system() {
        return is_system;
    }

    public void setIs_system(int is_system) {
        this.is_system = is_system;
    }

    public int getStatusX() {
        return statusX;
    }

    public void setStatusX(int statusX) {
        this.statusX = statusX;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getReads() {
        return reads;
    }

    public void setReads(int reads) {
        this.reads = reads;
    }

    public int getImgResId() {
        return mImgResId;
    }
}
