package com.zams.www.http.model;

import com.android.hengyu.web.RealmName;

/**
 * Created by Administrator on 2018/5/10.
 */

public class RedPackageData {
    /**
     * id : 2979
     * parent_id : 0
     * channel_id : 34
     * title : 米面粮油/干货/副食酒水
     * content :
     * img_url : /upload/201805/10/201805101650536057.jpg
     */

    private int id;
    private int parent_id;
    private int channel_id;
    private String title;
    private String content;
    private String img_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}