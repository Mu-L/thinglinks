package com.mqttsnet.thinglinks.file.manager.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.file.entity.File;
import com.mqttsnet.thinglinks.file.manager.FileManager;
import com.mqttsnet.thinglinks.file.mapper.FileMapper;
import com.mqttsnet.thinglinks.file.vo.result.FileResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件
 *
 * @author mqttsnet
 * @date 2021/10/31 10:24
 */
@Service
public class FileManagerImpl extends SuperManagerImpl<FileMapper, File> implements FileManager {
    @Override
    public List<FileResultVO> listByBizIdAndBizType(Long bizId, String bizType) {
        return baseMapper.listByBizIdAndBizType(bizId, bizType);
    }
}
