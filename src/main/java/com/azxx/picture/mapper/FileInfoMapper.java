package com.azxx.picture.mapper;

import com.azxx.picture.entity.FileInfo;
import com.azxx.picture.vo.fileInfo.FileReqVo;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {

    List<FileInfo> getFiles(FileReqVo reqVo);

}