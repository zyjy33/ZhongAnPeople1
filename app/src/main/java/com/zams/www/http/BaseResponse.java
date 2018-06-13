package com.zams.www.http;


/**
 * Created by Administrator on 2018/5/10.
 */

public class BaseResponse {

    /**
     * status : y
     * code : yes
     * info : 返回指定类别下列表(一层)
     * data : [{"id":2979,"parent_id":0,"channel_id":34,"title":"米面粮油/干货/副食酒水","content":"","img_url":"/upload/201805/10/201805101650536057.jpg"},{"id":2977,"parent_id":0,"channel_id":34,"title":"生活用品/家居家电","content":"","img_url":"/upload/201805/10/201805101657583478.jpg"},{"id":2978,"parent_id":0,"channel_id":34,"title":"营养健康/保健品/医疗用品","content":"","img_url":"/upload/201805/10/201805101658113215.jpg"}]
     * record : 0
     * redirect :
     * timer : 0
     * callback :
     * datetime : 2018-05-10 17:10:08
     * timestamp : 1525943408
     */

    private String status;
    private String code;
    private String info;
    private int record;
    private String redirect;
    private int timer;
    private String callback;
    private String datetime;
    private String timestamp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
