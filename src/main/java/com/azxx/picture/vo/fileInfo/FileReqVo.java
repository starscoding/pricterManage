package com.azxx.picture.vo.fileInfo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @version V1.0
 * @class: FileReqVo
 * @description: file request vo
 * @author: xuzheng
 * @create: 2019-02-02 10:18
 **/
public class FileReqVo {


    @ApiModelProperty(value = "文件名称",dataType = "string")
    private String name;

    @ApiModelProperty(value = "文件类型",dataType = "string")
    private String type;

    @ApiModelProperty(value = "照片标题",dataType = "string")
    private String title;

    @ApiModelProperty(value = "照片描述",dataType = "string")
    private String description;

    @ApiModelProperty(value = "照片大小",dataType = "string")
    private Integer size;

    @ApiModelProperty(value = "归属分组ID",dataType = "string")
    private Integer groupId;

    @ApiModelProperty(value = "分组名称",dataType = "string")
    private String groupName;

    @ApiModelProperty(value = "创建时间",dataType = "string")
    private Date createTime;

    @ApiModelProperty(value = "记录时间",dataType = "string")
    private Date recordTime;

    @ApiModelProperty(value = "照片URL",dataType = "string")
    private String url;

    @ApiModelProperty(value = "文件创建开始时间（格式YYYY-MM-DD）",dataType = "string")
    private String startTime;

    @ApiModelProperty(value = "文件创建结束时间（格式YYYY-MM-DD）",dataType = "string")
    private String endTime;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
