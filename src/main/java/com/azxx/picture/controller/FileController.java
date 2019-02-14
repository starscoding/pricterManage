package com.azxx.picture.controller;

import com.alibaba.fastjson.JSON;
import com.azxx.picture.entity.FileInfo;
import com.azxx.picture.service.FileService;
import com.azxx.picture.vo.OperateTypeEm;
import com.azxx.picture.vo.fileInfo.FileReqVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

/**
 * @version V1.0
 * @class: PictureController
 * @description: 图片操作、查询类
 * @author: xuzheng
 * @create: 2019-02-02 10:08
 **/

@RestController
@RequestMapping(path = "/fileManage")
@Api(tags = "FileManage", description = "图片管理")
public class FileController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @Value("${img.dir}")
    private String filePath;

    @Value("${img.url}")
    private String imgUrl;

    @RequestMapping(path = "/getFiles", method = RequestMethod.POST)
    @ApiOperation(value = "获取图片列表", notes = "获取图片列表")
    @ApiResponses({@ApiResponse(code = 200, message = "处理成功", response = FileInfo.class)})
    public String getFiles(FileReqVo reqVo) {

        if (reqVo == null) {
            return paramsError();
        }

        logger.info("获取文件列表开始，参数：{}", JSON.toJSON(reqVo));
        try {
            List<FileInfo> result = fileService.getFiles(reqVo);
            return queryOk(result);
        } catch (Exception e) {
            e.printStackTrace();
            return error(null, OperateTypeEm.QUERY.toString());
        }

    }

    @RequestMapping(path = "/pages", method = RequestMethod.POST)
    @ApiOperation(value = "获取图片列表", notes = "获取图片列表")
    @ApiResponses({@ApiResponse(code = 200, message = "处理成功", response = FileInfo.class)})
    public String pages(FileReqVo reqVo) {

        if (reqVo == null) {
            return paramsError();
        }

        logger.info("获取文件列表开始，参数：{}", JSON.toJSON(reqVo));
        try {
            PageInfo<FileInfo> result = fileService.pages(reqVo);
            return queryOk(result);
        } catch (Exception e) {
            e.printStackTrace();
            return error(null, OperateTypeEm.QUERY.toString());
        }

    }

    @RequestMapping(path = "/uploadFile", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiResponses({@ApiResponse(code = 200, message = "处理成功")})
    public String uploadFile(@RequestParam("fileToUpload") MultipartFile file, FileReqVo reqVo, HttpServletRequest request) {
        if (reqVo == null) {
            return paramsError();
        }
        String fileName = file.getOriginalFilename();
        reqVo.setSize((int) (file.getSize() / 1000));
        if (StringUtils.isBlank(reqVo.getName())) {
            reqVo.setName(fileName);
        }
        if (StringUtils.isBlank(reqVo.getTitle())) {
            reqVo.setName(fileName);
        }
        if (fileName.indexOf(".") > -1) {
            reqVo.setType(fileName.substring(fileName.lastIndexOf(".")));
        }
        reqVo.setUrl(imgUrl+"static/customer/img/"+reqVo.getName());
        try {
            String pathname = filePath + reqVo.getName();
            if(reqVo.getName().indexOf(reqVo.getType())<0){
                reqVo.setUrl(imgUrl+"static/customer/img/"+reqVo.getName()+reqVo.getType());
                pathname = filePath + reqVo.getName()+reqVo.getType();
            }
            fileService.addOrUpdateFile(reqVo);
            String url = request.getContextPath();
            logger.info("绝对路径：{}，工程路径：{}", filePath, url);
            File tmpFile = new File(pathname);
            if (!tmpFile.exists()) {
                tmpFile.createNewFile();
            }
            InputStream in = file.getInputStream();
            FileOutputStream fos = new FileOutputStream(tmpFile);
            byte[] b = new byte[1024];
            int length;
            while ((length = in.read(b)) != -1) {
                fos.write(b, 0, length);
            }
            in.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queryOk(null);
    }

    @RequestMapping(path = "/addOrUpdateFile", method = RequestMethod.POST)
    @ApiOperation(value = "增加或更新图片信息", notes = "增加或更新图片信息")
    @ApiResponses({@ApiResponse(code = 200, message = "处理成功", response = FileInfo.class)})
    public String addOrUpdateFile(FileReqVo reqVo) {
        if (reqVo == null || StringUtils.isBlank(reqVo.getName())) {
            return paramsError();
        }

        logger.info("增加或更新图片信息，参数：{}", JSON.toJSON(reqVo));
        try {
            boolean result = fileService.addOrUpdateFile(reqVo);
            if(result){
                return updateOk(null);
            }else{
                return paramsError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return error(null, OperateTypeEm.UPDATE.toString());
        }
    }

    @RequestMapping(path = "/deleteFile", method = RequestMethod.POST)
    @ApiOperation(value = "删除图片信息", notes = "删除图片信息")
    @ApiResponses({@ApiResponse(code = 200, message = "处理成功", response = FileInfo.class)})
    public String deleteFile(String id) {
        if (StringUtils.isBlank(id)) {
            return paramsError();
        }
        logger.info("删除图片信息，参数：{}", id);
        try {
            fileService.deleteFile(id);
            return updateOk(null);
        } catch (Exception e) {
            e.printStackTrace();
            return error(null, OperateTypeEm.UPDATE.toString());
        }
    }

}
