package com.azxx.picture.vo;

/**
 * @version V1.0
 * @class: ResultCodeEm
 * @description:
 * @author: xuzheng
 * @create: 2019-02-02 11:27
 **/
public enum ResultCodeEm {

    UPDATE_SUCCESS("200","修改成功！"),

    QUERY_SUCCESS("200","查询成功！"),

    DELETE_SUCCESS("200","删除成功！"),

    UPDATE_FIALURE("500","修改失败！"),

    QUERY_FIALURE("500","查询失败！"),

    DELETE_FIALURE("500","删除失败！"),

    PARAMS_ERROR("500","参数错误!")
    ;

    ResultCodeEm(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
