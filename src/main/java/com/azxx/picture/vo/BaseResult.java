package com.azxx.picture.vo;

/**
 * @version V1.0
 * @class: BaseResult
 * @description: 返回结果基础类
 * @author: xuzheng
 * @create: 2019-02-02 11:13
 **/
public class BaseResult {

    private String code;

    private String msg;

    private String desc;

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
