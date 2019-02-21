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
    private String description;

    @ApiModelProperty(value = "分组名称",dataType = "string")
    private String name;

    @ApiModelProperty(value = "ID",dataType = "int")
    private Integer id;

    private Integer page;

    private Integer rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
