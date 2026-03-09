package com.mqttsnet.thinglinks.file.strategy.impl.local;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.utils.CollHelper;
import com.mqttsnet.basic.utils.StrPool;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.file.domain.FileDeleteBO;
import com.mqttsnet.thinglinks.file.domain.FileGetUrlBO;
import com.mqttsnet.thinglinks.file.entity.File;
import com.mqttsnet.thinglinks.file.enumeration.FileStorageType;
import com.mqttsnet.thinglinks.file.mapper.FileMapper;
import com.mqttsnet.thinglinks.file.properties.FileServerProperties;
import com.mqttsnet.thinglinks.file.strategy.impl.AbstractFileStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mqttsnet
 * @date 2020/11/22 5:00 下午
 */
@Slf4j
@DS(DsConstant.DEFAULTS)
@Component("LOCAL")
@Primary
public class LocalFileStrategyImpl extends AbstractFileStrategy {
    public LocalFileStrategyImpl(FileServerProperties fileProperties, FileMapper fileMapper) {
        super(fileProperties, fileMapper);
    }

    @Override
    protected void uploadFile(File file, MultipartFile multipartFile, String bucket) throws Exception {
        FileServerProperties.Local local = fileProperties.getLocal();
        bucket = StrUtil.isEmpty(bucket) ? local.getBucket() : bucket;

        //生成文件名
        String uniqueFileName = getUniqueFileName(file);
        // 相对路径
        String path = getPath(file.getBizType(), uniqueFileName);
        // web服务器存放的绝对路径
        String absolutePath = Paths.get(local.getStoragePath(), bucket, path).toString();

        // 存储文件
        java.io.File outFile = new java.io.File(absolutePath);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), outFile);

        // 返回数据
        String url = local.getUrlPrefix() + bucket + StrPool.SLASH + path;
        file.setUrl(url);
        file.setUniqueFileName(uniqueFileName);
        file.setPath(path);
        file.setBucket(bucket);
        file.setStorageType(FileStorageType.LOCAL);
    }

    @Override
    public boolean delete(FileDeleteBO file) {
        FileServerProperties.Local local = fileProperties.getLocal();
        java.io.File ioFile = new java.io.File(Paths.get(local.getStoragePath(), file.getBucket(), file.getPath()).toString());
        FileUtils.deleteQuietly(ioFile);
        return true;
    }

    @Override
    public Map<String, String> findUrl(List<FileGetUrlBO> fileGets) {
        Map<String, String> map = new LinkedHashMap<>(CollHelper.initialCapacity(fileGets.size()));
        // 方式1 取上传时存的url （多查询一次数据库）
        /*
        List<String> paths = fileGets.stream().map(FileGetUrlBO::getPath).collect(Collectors.toList());
        List<File> list = fileMapper.selectList(Wraps.<File>lbQ().eq(File::getPath, paths));
        list.forEach(item -> map.put(item.getPath(), item.getUrl()));
        */

        // 方式2 重新拼接 （urlPrefix 可能跟上传时不一样）
        FileServerProperties.Local local = fileProperties.getLocal();
        fileGets.forEach(item -> {
            String url = local.getUrlPrefix() +
                         item.getBucket() +
                         StrPool.SLASH +
                         item.getPath();
            map.put(item.getPath(), url);
        });
        return map;
    }
}
