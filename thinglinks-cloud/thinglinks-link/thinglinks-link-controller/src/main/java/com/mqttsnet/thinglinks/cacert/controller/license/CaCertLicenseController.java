package com.mqttsnet.thinglinks.cacert.controller.license;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;

import cn.hutool.core.io.FileUtil;
import com.mqttsnet.basic.annotation.log.WebLog;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.cacert.entity.license.CaCertLicense;
import com.mqttsnet.thinglinks.cacert.service.license.CaCertLicenseService;
import com.mqttsnet.thinglinks.cacert.vo.query.license.CaCertLicensePageQuery;
import com.mqttsnet.thinglinks.cacert.vo.result.license.CaCertLicenseResultVO;
import com.mqttsnet.thinglinks.cacert.vo.save.license.CaCertLicenseSaveVO;
import com.mqttsnet.thinglinks.cacert.vo.save.license.CaCertPemImportSaveVO;
import com.mqttsnet.thinglinks.cacert.vo.update.license.CaCertLicenseUpdateVO;
import com.mqttsnet.thinglinks.datascope.DataScopeHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * <p>
 * 前端控制器
 * CA许可证证书表
 * </p>
 *
 * @author mqttsnet
 * @since 2025-06-27 15:48:10
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/caCertLicense")
@Tag(name = "CA许可证证书")
public class CaCertLicenseController extends SuperController<CaCertLicenseService, Long, CaCertLicense
        , CaCertLicenseSaveVO, CaCertLicenseUpdateVO, CaCertLicensePageQuery, CaCertLicenseResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

    @Override
    public QueryWrap<CaCertLicense> handlerWrapper(CaCertLicense model, PageParams<CaCertLicensePageQuery> params) {
        QueryWrap<CaCertLicense> queryWrap = super.handlerWrapper(model, params);
        // 开启数据权限
        DataScopeHelper.startDataScope("ca_cert_license");
        return queryWrap;
    }

    /**
     * 导入PEM格式CA根证书
     *
     * @param caCertPemImportSaveVO 证书导入请求体
     * @return 证书导入结果
     * @apiNote 证书需为标准的PEM格式
     */
    @Operation(summary = "导入CA根证书(PEM)", description = "CA根证书(不支持证书链)")
    @PostMapping("/importPemCertificate")
    public R<CaCertLicenseResultVO> importPemCertificate(
            @Parameter(description = "证书导入参数", required = true)
            @Valid @RequestBody CaCertPemImportSaveVO caCertPemImportSaveVO) {

        CaCertLicenseResultVO result = superService.importPemCertificate(caCertPemImportSaveVO);
        return R.success(result);
    }

//    /**
//     * 颁发CA证书
//     * <p>
//     * 根据RFC 5280和CA/Browser Forum规范生成X.509证书：
//     * 1. 生成符合规范的随机序列号（至少8字节熵）
//     * 2. 设置证书有效期（需满足最小/最大有效期要求）
//     * 3. 使用指定签名算法（RSA/ECC）签发证书
//     * </p>
//     *
//     * @param id       CA证书ID
//     * @param notAfter 证书过期时间（必须晚于当前时间且不超过最大有效期）
//     * @return 证书颁发结果
//     * @throws BizException 当参数无效或证书颁发失败时抛出业务异常
//     * @see <a href="https://tools.ietf.org/html/rfc5280">RFC 5280</a>
//     * @see <a href="https://cabforum.org/baseline-requirements/">CA/Browser Forum BR</a>
//     */
//    @Deprecated
//    @Operation(summary = "颁发CA证书", description = "根据证书请求ID颁发符合X.509规范的CA证书")
//    @PutMapping("/issue/{id}")
//    @WebLog(value = "颁发CA证书", request = false)
//    @Parameters({
//            @Parameter(name = "id", description = "CA证书ID", required = true),
//            @Parameter(name = "notAfter", description = "证书过期时间（格式：yyyy-MM-dd HH:mm:ss）", example = "2025-12-31 23:59:59", required = true)
//    })
//    public R<CaCertLicenseResultVO> issueCertificate(
//            @PathVariable("id") @NotNull Long id,
//            @RequestParam @Future LocalDateTime notAfter) {
//        log.info("颁发CA证书请求 - ID: {}, 过期时间: {}", id, notAfter);
//        CaCertLicenseResultVO result = superService.issueCertificate(id, notAfter);
//        return R.success(result);
//    }
//
//    /**
//     * 撤销CA证书
//     * <p>
//     * 将证书状态标记为已撤销并记录撤销时间，符合RFC 5280 CRL规范
//     * </p>
//     *
//     * @param id               证书ID
//     * @param revocationReason 撤销原因（可选）
//     * @return 撤销操作结果
//     * @throws BizException 当证书不存在或已撤销时抛出业务异常
//     * @see <a href="https://tools.ietf.org/html/rfc5280#section-5.3.1">RFC 5280 CRL</a>
//     */
//    @Deprecated
//    @Operation(summary = "撤销CA证书", description = "根据证书ID撤销已颁发的CA证书")
//    @PutMapping("/revoke/{id}")
//    @WebLog(value = "撤销CA证书", request = false)
//    @Parameters({
//            @Parameter(name = "id", description = "证书ID", required = true),
//            @Parameter(name = "revocationReason", description = "撤销原因(可选)",
//                    schema = @Schema(allowableValues = {
//                            "unspecified", "keyCompromise", "cACompromise",
//                            "affiliationChanged", "superseded", "cessationOfOperation"
//                    }))
//    })
//    public R<Boolean> revokeCertificate(
//            @PathVariable("id") @NotNull Long id,
//            @RequestParam(required = false) String revocationReason) {
//        log.info("撤销CA证书请求 - ID: {}, 原因: {}", id, revocationReason);
//        return R.success(superService.revokeCertificate(id, revocationReason));
//    }
//
//    /**
//     * 签发客户端证书
//     *
//     * @param id       客户端唯一标识
//     * @param notAfter 证书过期时间（必须晚于当前时间且不超过最大有效期）
//     * @return 包含证书文件的ZIP包
//     */
//    @Operation(summary = "签发客户端证书", description = "根据根CA证书签发客户端证书")
//    @Parameters({
//            @Parameter(name = "id", description = "CA证书ID", required = true),
//            @Parameter(name = "notAfter", description = "证书过期时间（格式：yyyy-MM-dd HH:mm:ss）", example = "2025-12-31 23:59:59", required = true)
//    })
//    @Deprecated
//    @PostMapping("/issueClientCert")
//    public ResponseEntity<StreamingResponseBody> issueClientCert(
//            @RequestParam @NotNull Long id,
//            @RequestParam @Future LocalDateTime notAfter) throws Exception {
//        log.info("签发客户端证书 - ID: {}, 过期时间: {}", id, notAfter);
//        File zipFile = superService.generateClientCertPackage(id, notAfter);
//        return ResponseEntity.ok()
//                .header("Content-Disposition", "attachment; filename=client_cert_" + id + ".zip")
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body((StreamingResponseBody) outputStream -> {
//                    try (InputStream in = Files.newInputStream(zipFile.toPath())) {
//                        IOUtils.copy(in, outputStream);
//                    } finally {
//                        FileUtil.del(zipFile);
//                    }
//                });
//    }


}


