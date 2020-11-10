package com.dubbo.util;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResultEntity implements Serializable {
    private int code;    //code=0 成功；code=其他 失败 默认为1
    private String msg;    //消息
    private Map<String, Object> data;   //存放的数据，可为空

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public ResultEntity() {
        this.code = 1;
        this.data = new HashMap<String, Object>();
    }

    public ResultEntity(int code) {
        this.code = code;
        data = new HashMap<String, Object>();
    }

    public ResultEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
        data = new HashMap<String, Object>();
    }

    public ResultEntity(int code, String msg, Map<String, Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 转化为Json字符串
     * @return Json字符串
     * 一般成功就 getJSONString(0, msg, data); 失败就 getJSONString(1, errorMsg);
     */
    public String toJSONString() {
        JSONObject json = new JSONObject();
        json.put("code", code);
        if (msg != null && msg.length() != 0) {
            json.put("msg", msg);
        }
        if (data != null && !data.isEmpty()) {
            json.put("data", data);
        }
        return json.toJSONString();
    }
}
