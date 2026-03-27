package com.mqttsnet.thinglinks.cacert.service.license;

import java.io.File;
import java.time.LocalDateTime;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.cacert.entity.license.CaCertLicense;
import com.mqttsnet.thinglinks.cacert.vo.result.license.CaCertLicenseResultVO;
import com.mqttsnet.thinglinks.cacert.vo.save.license.CaCertPemImportSaveVO;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;


/**
 * <p>
 * 业务接口
 * CA许可证证书表
 * </p>
 *
 * @author mqttsnet
 * @since 2025-06-27 15:48:10
 */
public interface CaCertLicenseService extends SuperService<Long, CaCertLicense> {

    /**
     * 导入PEM格式证书
     *
     * @param caCertPemImportSaveVO 导入参数
     * @return {@link CaCertLicenseResultVO} 证书信息
     */
    CaCertLicenseResultVO importPemCertificate(CaCertPemImportSaveVO caCertPemImportSaveVO);

    /**
     * 颁发CA证书
     *
     * @param id       证书ID
     * @param notAfter 证书有效期
     * @return {@link CaCertLicenseResultVO} 颁发的证书信息
     */
    CaCertLicenseResultVO issueCertificate(@NotNull Long id, @Future LocalDateTime notAfter);


    /**
     * 吊销CA证书
     *
     * @param id               证书ID
     * @param revocationReason 吊销原因
     * @return {@link Boolean} 是否成功
     */
    Boolean revokeCertificate(@NotNull Long id, String revocationReason);

    /**
     * 根据证书序列号查询证书
     *
     * @param certSerialNumber 证书序列号
     * @return {@link CaCertLicenseResultVO} 证书信息
     */
    CaCertLicenseResultVO getByCertSerialNumber(String certSerialNumber);

    /**
     * 生成客户端证书包
     *
     * @param id       根CA证书ID
     * @param notAfter 证书有效期
     * @return {@link File} 客户端证书包
     */
    File generateClientCertPackage(@NotNull Long id, @Future LocalDateTime notAfter) throws Exception;
}


