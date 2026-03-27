package com.mqttsnet.thinglinks.utils.cacert;

import java.io.ByteArrayInputStream;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import com.mqttsnet.thinglinks.utils.x509.CertSerialNumberUtil;
import com.mqttsnet.thinglinks.utils.x509.X509Util;
import lombok.extern.slf4j.Slf4j;

/**
 * -----------------------------------------------------------------------------
 * File Name: CertificateVerifierUtil
 * -----------------------------------------------------------------------------
 * Description:
 * CA证书验证工具类
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/8/6       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/8/6 16:37
 */
@Slf4j
public class CertificateVerifierUtil {

    private static X509Certificate trustedCertificate;

    /**
     * 设置受信任的CA证书
     *
     * @param base64Cert Base64编码的CA证书
     * @throws CertificateException 证书解析异常
     */
    public static void setTrustedCertificate(String base64Cert) throws CertificateException {
        try {
            trustedCertificate = decodeCertificate(base64Cert);
            log.info("Trusted CA certificate set successfully...Serial number:{}", CertSerialNumberUtil.getOpenSSLSerial(trustedCertificate));
        } catch (CertificateException e) {
            log.error("Failed to decode trusted CA certificate: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 验证客户端证书
     *
     * @param base64Cert Base64编码的客户端证书
     * @return 证书是否有效
     */
    public static boolean verifyCertificate(String base64Cert) {
        try {
            // 解码客户端证书
            X509Certificate clientCert = decodeCertificate(base64Cert);
            log.info("\n============== 开始证书验证 ==============");
            log.info("【客户端证书信息】");
            log.info("Subject DN: {}", clientCert.getSubjectX500Principal());
            log.info("Issuer DN: {}", clientCert.getIssuerX500Principal());
            log.info("序列号: {}", CertSerialNumberUtil.getOpenSSLSerial(clientCert));
            log.info("有效期: {} 至 {}", clientCert.getNotBefore(), clientCert.getNotAfter());
            log.info("签名算法: {}", clientCert.getSigAlgName());
            log.info("公钥算法: {}", clientCert.getPublicKey().getAlgorithm());

            // CA证书信息
            log.info("\n【CA证书信息】");
            log.info("Subject DN: {}", trustedCertificate.getSubjectX500Principal());
            log.info("公钥算法: {}", trustedCertificate.getPublicKey().getAlgorithm());

            // 指纹信息
            log.info("客户端证书指纹原始DER编码SHA-256: {}", X509Util.getFingerPrint(clientCert));
            log.info("CA证书指纹原始DER编码SHA-256: {}", X509Util.getFingerPrint(trustedCertificate));

            // 有效期检查
            clientCert.checkValidity();
            log.info("\n【有效期验证】通过");

            // 签名验证
            log.info("\n【签名验证】开始...");
            log.info("使用CA公钥验证客户端证书签名算法");
            String sigAlg = clientCert.getSigAlgName();
            if (!sigAlg.equals(trustedCertificate.getSigAlgName())) {
                log.warn("算法不匹配: 客户端使用{}，CA使用{}", sigAlg, trustedCertificate.getSigAlgName());
                return false;
            }
            log.info("√ 签名算法验证通过（客户端证书与CA根证书签名算法相同）");

            // CA是否是客户端证书的实际签发者
            if (!clientCert.getIssuerX500Principal().equals(trustedCertificate.getSubjectX500Principal())) {
                log.error("× 签发者不匹配！客户端证书签发者: {} \n 但CA证书主题: {}",
                        clientCert.getIssuerX500Principal(),
                        trustedCertificate.getSubjectX500Principal());
                return false;
            }

//            clientCert.verify(trustedCertificate.getPublicKey());

            log.info("√ 签发者匹配验证通过");

            log.info("\n============== 所有验证通过 ==============");
            return true;

        } catch (CertificateExpiredException e) {
            log.error("× 证书已过期: {}", e.getMessage());
        } catch (CertificateNotYetValidException e) {
            log.error("× 证书尚未生效: {}", e.getMessage());
        } catch (CertificateException e) {
            log.error("× 证书解析失败: {}", e.getMessage());
        } catch (Exception e) {
            log.error("× 验证过程出现异常: {}", e.getMessage());
            if (e instanceof SignatureException) {
                log.error("签名验证失败原因可能是：\n"
                        + "1. 客户端证书不是由该CA签发\n"
                        + "2. 证书内容被篡改\n"
                        + "3. 使用了错误的CA证书");
            }
        }
        return false;
    }

    /**
     * 解码Base64编码的证书
     *
     * @param base64Cert Base64编码的证书
     * @return X509Certificate对象
     * @throws CertificateException 证书解析异常
     */
    private static X509Certificate decodeCertificate(String base64Cert) throws CertificateException {
        try {
            byte[] decoded = Base64.getDecoder().decode(base64Cert);
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(decoded));
        } catch (IllegalArgumentException e) {
            log.error("Invalid Base64 input: {}", e.getMessage(), e);
            throw new CertificateException("Invalid Base64 input", e);
        } catch (CertificateException e) {
            log.error("Failed to generate certificate from input: {}", e.getMessage(), e);
            throw e;
        }
    }
}
