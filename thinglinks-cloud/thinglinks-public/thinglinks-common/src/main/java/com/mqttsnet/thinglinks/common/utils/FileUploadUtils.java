package com.mqttsnet.thinglinks.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.apache.tika.Tika;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 * 文件上传工具类
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/7/2
 */
public class FileUploadUtils {
    private static final Tika TIKA = new Tika();
    // 内存阈值10MB
    private static final long MAX_MEMORY_SIZE = 10 * 1024 * 1024;

    private FileUploadUtils() {
    }


    /**
     * File → MultipartFile（自动内存优化）
     */
    public static MultipartFile toMultipartFile(File file) throws IOException {
        String mimeType = TIKA.detect(file.toPath());
        return file.length() <= MAX_MEMORY_SIZE ?
                new MockMultipartFile(
                        file.getName(),
                        file.getName(),
                        mimeType,
                        FileUtil.readBytes(file)
                ) :
                new MockMultipartFile(
                        file.getName(),
                        file.getName(),
                        mimeType,
                        FileUtil.getInputStream(file)
                );
    }

    /**
     * MultipartFile → File（自动创建父目录）
     */
    public static File saveToFile(MultipartFile file, Path targetDir) throws IOException {
        String filename = StrUtil.uuid() + "." + FileUtil.extName(file.getOriginalFilename());
        Path targetPath = targetDir.resolve(filename);
        FileUtil.mkdir(targetDir.toFile());
        FileUtil.writeFromStream(file.getInputStream(), targetPath.toFile());
        return targetPath.toFile();
    }


    /**
     * 创建临时文件
     */
    public static File createTempFile(String prefix, String suffix) throws IOException {
        return FileUtil.createTempFile(prefix, suffix, true);
    }

    /**
     * 安全获取文件扩展名
     */
    public static String getFileExtension(String filename) {
        return StrUtil.blankToDefault(FileUtil.extName(filename), "");
    }

    /**
     * 计算MultipartFile的MD5（自动内存优化）
     * 小文件(<10MB)使用字节数组，大文件使用流方式
     */
    public static String getMd5(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        long fileSize = multipartFile.getSize();

        if (fileSize <= MAX_MEMORY_SIZE) {
            // 小文件：使用字节数组
            return DigestUtil.md5Hex(multipartFile.getBytes());
        } else {
            // 大文件：使用输入流（避免内存溢出）
            try (InputStream inputStream = multipartFile.getInputStream()) {
                return DigestUtil.md5Hex(inputStream);
            }
        }
    }

    /**
     * 计算File的MD5
     */
    public static String getMd5(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("文件不存在");
        }
        return DigestUtil.md5Hex(file);
    }

    /**
     * 计算MultipartFile的SHA256（自动内存优化）
     * 小文件(<10MB)使用字节数组，大文件使用流方式
     */
    public static String getSha256(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        long fileSize = multipartFile.getSize();

        if (fileSize <= MAX_MEMORY_SIZE) {
            // 小文件：使用字节数组
            return DigestUtil.sha256Hex(multipartFile.getBytes());
        } else {
            // 大文件：使用输入流（避免内存溢出）
            try (InputStream inputStream = multipartFile.getInputStream()) {
                return DigestUtil.sha256Hex(inputStream);
            }
        }
    }

    /**
     * 计算File的SHA256
     */
    public static String getSha256(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("文件不存在");
        }
        return DigestUtil.sha256Hex(file);
    }
}
