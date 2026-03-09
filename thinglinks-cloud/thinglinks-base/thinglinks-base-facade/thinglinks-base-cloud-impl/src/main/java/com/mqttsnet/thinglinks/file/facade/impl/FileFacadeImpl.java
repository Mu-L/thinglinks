package com.mqttsnet.thinglinks.file.facade.impl;


import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.file.api.FileApi;
import com.mqttsnet.thinglinks.file.enumeration.FileStorageType;
import com.mqttsnet.thinglinks.file.facade.FileFacade;
import com.mqttsnet.thinglinks.file.vo.result.FileResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文件接口
 *
 * @author zuihou
 * @since 2024年09月20日10:45:54
 */
@Service
public class FileFacadeImpl implements FileFacade {
    @Autowired
    @Lazy
    private FileApi fileApi;

    @Override
    public FileResultVO upload(MultipartFile file, String bizType, String bucket, FileStorageType storageType) {
        R<FileResultVO> result = fileApi.upload(file, bizType, bucket, storageType);
        return result.getData();
    }

    @Override
    public R<Map<Long, String>> findUrlFromDefById(List<Long> ids) {
        return fileApi.findUrlFromDefById(ids);
    }

    @Override
    public R<Map<Long, FileResultVO>> findInfoFromDefById(List<Long> ids) {
        return fileApi.findInfoFromDefById(ids);
    }
}
