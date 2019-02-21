package com.azxx.picture.service;

import com.azxx.picture.entity.FileInfo;
import com.azxx.picture.entity.GroupInfo;
import com.azxx.picture.mapper.GroupInfoMapper;
import com.azxx.picture.vo.groupInfo.GroupReqVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @version V1.0
 * @class: GroupService
 * @description: group service
 * @author: xuzheng
 * @create: 2019-02-02 10:15
 **/
@Service
public class GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    public List<GroupInfo> getGroups(GroupReqVo reqVo){
        List<GroupInfo> result = null;
        if(reqVo == null){
            return result;
        }
        GroupInfo groupInfo = new GroupInfo();
        BeanUtils.copyProperties(reqVo,groupInfo);
        return groupInfoMapper.select(groupInfo);
    }

    public PageInfo<GroupInfo> pages(GroupReqVo reqVo){
        GroupInfo groupInfo = new GroupInfo();
        BeanUtils.copyProperties(reqVo,groupInfo);
        return PageHelper.startPage(reqVo.getPage(), reqVo.getRows()).doSelectPageInfo(() -> groupInfoMapper.select(groupInfo));
    }

    public boolean addOrUpdateGroup(GroupReqVo reqVo){
        int effectRows = 0;
        if(reqVo == null) {
            return false;
        }
        GroupInfo groupInfo = new GroupInfo();
        BeanUtils.copyProperties(reqVo,groupInfo);
        groupInfo.setRecordTime(new Date());
        if(reqVo.getId()==null || reqVo.getId() == 0){
            effectRows = groupInfoMapper.insert(groupInfo);
        }else{
            effectRows = groupInfoMapper.updateByPrimaryKeySelective(groupInfo);
        }
        return effectRows>0?true:false;
    }

    public boolean deleteGroup(Integer id){
        int effectRows = 0;
        if(id == null) {
            return false;
        }
        effectRows = groupInfoMapper.deleteByPrimaryKey(id);
        return effectRows>0?true:false;
    }
}
