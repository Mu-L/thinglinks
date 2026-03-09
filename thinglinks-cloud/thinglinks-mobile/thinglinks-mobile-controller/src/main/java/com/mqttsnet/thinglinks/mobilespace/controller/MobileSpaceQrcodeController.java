package com.mqttsnet.thinglinks.mobilespace.controller;

import com.mqttsnet.thinglinks.mobile.mobilespace.service.MobileSpaceQrcodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;


/**
 * @author mqttsnet
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/spaceQrcode")
@Tag(name = "空间二维码管理")
public class MobileSpaceQrcodeController {

    private final MobileSpaceQrcodeService mobileSpaceQrcodeService;


    /**
     * 根据空间ID生成带有Logo的空间二维码
     *
     * @param spaceId 空间ID
     * @param response             HTTP响应对象，用于将生成的二维码图片返回给客户端
     */
    /**
     * 根据空间唯一标识生成带有Logo的空间二维码
     *
     * @param spaceId    空间ID
     * @param memberType 人员类型
     * @param response   HTTP响应对象，用于将生成的二维码图片返回给客户端
     */
    @Operation(summary = "生成空间二维码", description = "生成带有Logo的空间二维码")
    @GetMapping("/generateMobileSpaceQrcode")
    public void generateMobileSpaceQrcode(
            @RequestParam @NotNull(message = "请填写空间ID") Long spaceId,
            @RequestParam @NotNull(message = "请填写人员类型( 0:成员 1:管理员  2:所有者)") Integer memberType,
            HttpServletResponse response) {

        try {
            byte[] qrcode = mobileSpaceQrcodeService.generateQrcode(spaceId, memberType);

            // 设置响应头信息
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            String fileName = "MobileSpace" + spaceId + ".png";
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            // 输出二维码图像
            try (OutputStream os = response.getOutputStream()) {
                os.write(qrcode);
                os.flush();
            }

        } catch (FileNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            handleErrorResponse(response, "Logo文件未找到：" + e.getMessage());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            handleErrorResponse(response, "生成二维码时发生I/O错误：" + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            handleErrorResponse(response, "生成二维码时发生错误：" + e.getMessage());
        }
    }

    /**
     * 处理错误响应
     *
     * @param response     响应对象
     * @param errorMessage 错误信息
     */
    private void handleErrorResponse(HttpServletResponse response, String errorMessage) {
        try {
            response.setContentType(MediaType.TEXT_PLAIN_VALUE);
            response.getWriter().write(errorMessage);
        } catch (IOException ioException) {
            log.error("写入错误响应时发生错误", ioException);
        }
    }

}
