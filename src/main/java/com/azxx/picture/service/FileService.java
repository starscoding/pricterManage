package com.azxx.picture.service;

import com.azxx.picture.entity.FileInfo;
import com.azxx.picture.mapper.FileInfoMapper;
import com.azxx.picture.vo.fileInfo.FileReqVo;
import org.apache.commons.lang3.StringUtils;
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

    public Boolean addOrUpdateFile(FileReqVo reqVo){
        int effectRows = 0;
        if(reqVo == null) {
            return false;
        }
        FileInfo fileInfo = new FileInfo();
        BeanUtils.copyProperties(reqVo,fileInfo);
        if(reqVo.getId()==null){
            effectRows = fileInfoMapper.insert(fileInfo);
        }else{
            effectRows = fileInfoMapper.updateByPrimaryKeySelective(fileInfo);
        }
        return effectRows>0?true:false;
    }

    public boolean deleteFile(Integer id){
        int effectRows = 0;
        if(id == null) {
            return false;
        }
        effectRows = fileInfoMapper.deleteByPrimaryKey(id);
        return effectRows>0?true:false;
    }

}
