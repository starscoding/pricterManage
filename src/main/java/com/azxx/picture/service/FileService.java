package com.azxx.picture.service;

import com.azxx.picture.entity.FileInfo;
import com.azxx.picture.mapper.FileInfoMapper;
import com.azxx.picture.vo.fileInfo.FileReqVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @class: FileService
 * @description: file service
 * @author: xuzheng
 * @create: 2019-02-02 10:14
 **/
@Service
public class FileService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    public List<FileInfo> getFiles(FileReqVo reqVo) {
        List<FileInfo> result = null;
        if (reqVo == null) {
            return result;
        }
        return fileInfoMapper.getFiles(reqVo);
    }

    public PageInfo<FileInfo> pages(FileReqVo reqVo) {
        return PageHelper.startPage(reqVo.getPage(), reqVo.getRows()).doSelectPageInfo(() -> fileInfoMapper.getFiles(reqVo));
    }

    public Boolean addOrUpdateFile(FileReqVo reqVo) {
        int effectRows = 0;
        if (reqVo == null) {
            return false;
        }
        if(StringUtils.isBlank(reqVo.getName())){
            return false;
        }
        FileInfo fileInfo = fileInfoMapper.getFileByName(reqVo.getName());
        if(fileInfo==null ){
            fileInfo = new FileInfo();
        }
        Integer id = fileInfo.getId();
        BeanUtils.copyProperties(reqVo, fileInfo);
        fileInfo.setId(id);
        if (fileInfo.getId() == null) {
            fileInfo.setRecordTime(new Date());
            fileInfo.setCreateTime(new Date());
            effectRows = fileInfoMapper.insert(fileInfo);
        } else {
            effectRows = fileInfoMapper.updateByPrimaryKeySelective(fileInfo);
        }
        return effectRows > 0 ? true : false;
    }

    public boolean deleteFile(String ids) {
        int effectRows = 0;
        if (StringUtils.isBlank(ids)) {
            return false;
        }
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            effectRows = effectRows+fileInfoMapper.deleteByPrimaryKey(Integer.parseInt(idArray[i]));
        }
        return effectRows > 0 ? true : false;
    }

}
