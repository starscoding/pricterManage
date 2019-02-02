package com.azxx.picture.controller;

import com.alibaba.fastjson.JSON;
import com.azxx.picture.entity.FileInfo;
import com.azxx.picture.service.FileService;
import com.azxx.picture.vo.OperateTypeEm;
import com.azxx.picture.vo.fileInfo.FileReqVo;
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
 * @class: PictureController
 * @description: 图片操作、查询类
 * @author: xuzheng
 * @create: 2019-02-02 10:08
 **/

@Controller
@RequestMapping(path = "/fileManage")
@Api(tags = "FileManage", description = "图片管理")
public class FileController extends  BaseController{
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @RequestMapping(path = "/getFiles",method = RequestMethod.POST)
    @ApiOperation(value = "获取图片列表" ,notes = "获取图片列表")
    @ApiResponses({ @ApiResponse(code = 200, message = "处理成功", response = FileInfo.class) })
    public String getFiles(FileReqVo reqVo){

        if(reqVo == null){
            return paramsError();
        }

        logger.info("获取文件列表开始，参数：{}", JSON.toJSON(reqVo));
        try {
            List<FileInfo> result = fileService.getFiles(reqVo);
            return  queryOk(result);
        }catch (Exception e){
            e.printStackTrace();
            return  error(null,OperateTypeEm.QUERY.toString());
        }

    }

    @RequestMapping(path = "/addOrUpdateFile",method = RequestMethod.POST)
    @ApiOperation(value = "增加或更新图片信息" ,notes = "增加或更新图片信息")
    @ApiResponses({ @ApiResponse(code = 200, message = "处理成功", response = FileInfo.class) })
    public String addOrUpdateFile(FileReqVo reqVo){

        if(reqVo == null){
            return paramsError();
        }

        logger.info("增加或更新图片信息，参数：{}", JSON.toJSON(reqVo));
        try{
            fileService.addOrUpdateFile(reqVo);
            return  updateOk(null);
        }catch (Exception e){
            e.printStackTrace();
            return  error(null,OperateTypeEm.UPDATE.toString());
        }
    }

    @RequestMapping(path = "/deleteFile",method = RequestMethod.POST)
    @ApiOperation(value = "删除图片信息" ,notes = "删除图片信息")
    @ApiResponses({ @ApiResponse(code = 200, message = "处理成功", response = FileInfo.class) })
    public String deleteFile(Integer fileId){

        if(fileId == null){
            return paramsError();
        }

        logger.info("删除图片信息，参数：{}", fileId);
        try{
            fileService.deleteFile(fileId);
            return updateOk(null);
        }catch (Exception e){
            e.printStackTrace();
            return  error(null,OperateTypeEm.UPDATE.toString());
        }
    }

}
