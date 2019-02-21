package com.azxx.picture.controller;

import com.alibaba.fastjson.JSON;
import com.azxx.picture.vo.BaseResult;
import com.azxx.picture.vo.OperateTypeEm;
import com.azxx.picture.vo.ResultCodeEm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version V1.0
 * @class: BaseController
 * @description: base controller
 * @author: xuzheng
 * @create: 2019-02-02 11:16
 **/
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 返回结果
     * @param object 返回对象
     * @param operateType 操作类型
     * @return
     */
    public String ok(Object object,String operateType){
        BaseResult result = new BaseResult();
        if(StringUtils.isBlank(operateType)){
            operateType = OperateTypeEm.QUERY.toString();
        }
        if(operateType.equals(OperateTypeEm.QUERY.toString())){
            result.setCode(ResultCodeEm.QUERY_SUCCESS.getCode());
            result.setMsg(ResultCodeEm.QUERY_SUCCESS.getMsg());
        }else if(operateType.equals(OperateTypeEm.UPDATE.toString())){
            result.setCode(ResultCodeEm.UPDATE_SUCCESS.getCode());
            result.setMsg(ResultCodeEm.UPDATE_SUCCESS.getMsg());
        }else if(operateType.equals(OperateTypeEm.DELETE.toString())){
            result.setCode(ResultCodeEm.DELETE_SUCCESS.getCode());
            result.setMsg(ResultCodeEm.DELETE_SUCCESS.getMsg());
        }
        result.setData(object);
        return JSON.toJSONString(result);
    }

    public String queryOk(Object object){
        BaseResult result = new BaseResult();
        result.setCode(ResultCodeEm.QUERY_SUCCESS.getCode());
        result.setMsg(ResultCodeEm.QUERY_SUCCESS.getMsg());
        result.setData(object);
        return JSON.toJSONString(result);
    }

    public String updateOk(Object object){
        BaseResult result = new BaseResult();
        result.setCode(ResultCodeEm.UPDATE_SUCCESS.getCode());
        result.setMsg(ResultCodeEm.UPDATE_SUCCESS.getMsg());
        result.setData(object);
        return JSON.toJSONString(result);
    }

    public String ok(Object object,String code,String msg){
        BaseResult result = new BaseResult();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return JSON.toJSONString(result);
    }

    public String paramsError(){
        BaseResult result = new BaseResult();
        result.setCode(ResultCodeEm.PARAMS_ERROR.getCode());
        result.setMsg(ResultCodeEm.PARAMS_ERROR.getMsg());
        return JSON.toJSONString(result);
    }

    /**
     *
     * @param object
     * @param operateType
     * @return
     */
    public String error(Object object,String operateType){
        BaseResult result = new BaseResult();
        if(StringUtils.isBlank(operateType)){
            operateType = OperateTypeEm.QUERY.toString();
        }
        if(operateType.equals(OperateTypeEm.QUERY.toString())){
            result.setCode(ResultCodeEm.QUERY_FIALURE.getCode());
            result.setMsg(ResultCodeEm.QUERY_FIALURE.getMsg());
        }else if(operateType.equals(OperateTypeEm.UPDATE.toString())){
            result.setCode(ResultCodeEm.UPDATE_FIALURE.getCode());
            result.setMsg(ResultCodeEm.UPDATE_FIALURE.getMsg());
        }else if(operateType.equals(OperateTypeEm.DELETE.toString())){
            result.setCode(ResultCodeEm.DELETE_FIALURE.getCode());
            result.setMsg(ResultCodeEm.DELETE_FIALURE.getMsg());
        }
        result.setData(object);
        return JSON.toJSONString(result);
    }



}
