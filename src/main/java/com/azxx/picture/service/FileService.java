package com.azxx.picture.service;

import com.azxx.picture.entity.FileInfo;
import com.azxx.picture.mapper.FileInfoMapper;
import com.azxx.picture.vo.fileInfo.FileReqVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<FileInfo> getFiles(FileReqVo reqVo){
        List<FileInfo> result = null;
        if(reqVo == null){
            return result;
        }
        return fileInfoMapper.getFiles(reqVo);
    }

    public Integer addFile(FileReqVo reqVo){
        if(reqVo == null) {
            return 0;
        }
        FileInfo fileInfo = new FileInfo();
        BeanUtils.copyProperties(reqVo,fileInfo);
        return fileInfoMapper.insert(fileInfo);
    }
}
