package com.azxx.picture.controller;

import com.alibaba.fastjson.JSON;
import com.azxx.picture.entity.FileInfo;
import com.azxx.picture.entity.GroupInfo;
import com.azxx.picture.service.GroupService;
import com.azxx.picture.vo.OperateTypeEm;
import com.azxx.picture.vo.groupInfo.GroupReqVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version V1.0
 * @class: GroupManage
 * @description: group controller
 * @author: xuzheng
 * @create: 2019-02-02 14:56
 **/
@RestController
@RequestMapping(path = "/groupManage")
@Api(tags = "GroupManage", description = "分组管理")
public class GroupController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private GroupService groupService;

    @RequestMapping(path = "/getGroups",method = RequestMethod.POST)
    @ApiOperation(value = "获取图片列表" ,notes = "获取图片列表")
    @ApiResponses({ @ApiResponse(code = 200, message = "处理成功", response = GroupInfo.class) })
    public String getGroups(GroupReqVo reqVo){

        if(reqVo == null){
            return paramsError();
        }

        logger.info("获取文件列表开始，参数：{}", JSON.toJSON(reqVo));
        try {
            List<GroupInfo> result = groupService.getGroups(reqVo);
            return  queryOk(result);
        }catch (Exception e){
            e.printStackTrace();
            return  error(null, OperateTypeEm.QUERY.toString());
        }
    }

    @RequestMapping(path = "/pages",method = RequestMethod.POST)
    @ApiOperation(value = "获取分组信息" ,notes = "获取分组信息")
    @ApiResponses({ @ApiResponse(code = 200, message = "处理成功", response = GroupInfo.class) })
    public String pages(GroupReqVo reqVo){
        if(reqVo == null){
            return paramsError();
        }
        logger.info("获取分组信息开始，参数：{}", JSON.toJSON(reqVo));
        try {
            PageInfo<GroupInfo> result1 = groupService.pages(reqVo);
            return  queryOk(result1);
        }catch (Exception e){
            e.printStackTrace();
            return  error(null, OperateTypeEm.QUERY.toString());
        }
    }

    @RequestMapping(path = "/addOrUpdate",method = RequestMethod.POST)
    @ApiOperation(value = "增加或更新分组信息" ,notes = "增加或更新图片信息")
    @ApiResponses({ @ApiResponse(code = 200, message = "处理成功", response = FileInfo.class) })
    public String addOrUpdate(GroupReqVo reqVo){

        if(reqVo == null){
            return paramsError();
        }

        logger.info("增加或更新分组信息，参数：{}", JSON.toJSON(reqVo));
        try{
            groupService.addOrUpdateGroup(reqVo);
            return  updateOk(null);
        }catch (Exception e){
            e.printStackTrace();
            return  error(null,OperateTypeEm.UPDATE.toString());
        }
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "删除分组信息" ,notes = "删除分组信息")
    @ApiResponses({ @ApiResponse(code = 200, message = "处理成功", response = FileInfo.class) })
    public String delete(Integer id){

        if(id == null){
            return paramsError();
        }

        logger.info("删除分组信息，参数：{}", id);
        try{
            groupService.deleteGroup(id);
            return updateOk(null);
        }catch (Exception e){
            e.printStackTrace();
            return  error(null,OperateTypeEm.UPDATE.toString());
        }
    }

}
