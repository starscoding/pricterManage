package com.azxx.picture.vo.groupInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version V1.0
 * @class: GroupReqVo
 * @description: group request vo
 * @author: xuzheng
 * @create: 2019-02-02 10:44
 **/
public class GroupReqVo {

    @ApiModelProperty(value = "分组描述",dataType = "string")
    private String desc;

    @ApiModelProperty(value = "分组名称",dataType = "string")
    private String name;

    @ApiModelProperty(value = "ID",dataType = "int")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
