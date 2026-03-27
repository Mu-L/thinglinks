package com.mqttsnet.thinglinks.utils.x509;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.mqttsnet.thinglinks.cacert.dto.SubjectObjectDN;

/**
 * X509证书工具类测试
 *
 * @author mqttsnet
 */
public class X509UtilTest {

    private static final Date DEFAULT_NOT_BEFORE = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
    private static final Date DEFAULT_NOT_AFTER = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(365 * 10));

    private static final String PRIVATE_KEY_PATH = "/etc/mqttsnet/thinglinks/ssl/";
    private static final String PRIVATE_KEY_NAME = "root.privateKey";

    public static void main(String[] args) throws Exception {
        // 1. 初始化环境
        X509Util.printProviders();
        KeyPair caKeyPair = X509Util.generateAndSaveRootPrivateKey(PRIVATE_KEY_PATH, PRIVATE_KEY_NAME);

        // 2. 构建测试密钥和DN
        PublicKey genPublic = X509Util.customRSAPublicKey(
                "24868767854896092588786687407303686877773690333894080011495029830940309475198567361970464647890033375055631051642529002149924877185208899281385878129292186185663539529840044840486029367534836233880978521289120012975984751300675739372426145169450457401456744048334655400383404984013988838597751579412284019508476089195916626638069697313070046637646867604604740726032555131935812336403354554855906457653369726238938995882912391517969859021398013180784463551350234466530008007031446991135485103138878091303677685495071868293487620787584772055998565035817582108636071393569141751372185211443433829520886484151275155702087",
                "65537");

        // 注意：参数顺序已调整为工具类要求的顺序
        SubjectObjectDN rootSubjectDN = X509Util.generateDN(
                "www.mqttsnet.com",
                "mqttsnet",
                "DevOps",
                "CN",
                "海南",
                "海口",
                "mqttsnet@163.com",
                "https://license.example.com2");

        SubjectObjectDN userSubjectDN = X509Util.generateDN(
                "www.mqttsnet.com",
                "mqttsnet",
                "DevOps",
                "CN",
                "海南",
                "海口",
                "mqttsnet@163.com",
                "https://license.example.com1");

        // 3. 证书生成与验证
        generateAndVerifyCerts(caKeyPair,rootSubjectDN, userSubjectDN, genPublic);
    }

    private static void generateAndVerifyCerts(KeyPair caKeyPair,SubjectObjectDN rootSubjectDN, SubjectObjectDN userSubjectDN, PublicKey publicKey) throws Exception {
        // 3.1 生成CA根证书（使用API=3的推荐方式）
        X509Certificate rootCert = X509Util.generateRootCert(3, rootSubjectDN, null, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
        saveCertificate(PRIVATE_KEY_PATH + "root", rootCert);

        // 3.2 生成用户证书（必须使用根证书的颁发者DN）
        X509Certificate userCert = X509Util.generateUserCert(
                3,
                rootCert.getSubjectX500Principal(),
                userSubjectDN,
                publicKey,
                rootCert,
                DEFAULT_NOT_BEFORE,
                DEFAULT_NOT_AFTER);
        saveCertificate(PRIVATE_KEY_PATH + "user", userCert);

        // 3.3 证书验证
        verifyCertificates(rootCert, userCert);
    }

    private static void saveCertificate(String basePath, X509Certificate cert) throws Exception {
        // 保存DER格式
        X509Util.saveEncodedFile(basePath + ".cer", cert.getEncoded());
        // 保存PEM格式
        X509Util.saveEncodedFile(basePath + ".pem",
                X509Util.X509CertificateToPem(cert).getBytes(StandardCharsets.UTF_8));
    }

    private static void verifyCertificates(X509Certificate rootCert, X509Certificate userCert) throws Exception {
        // 4.1 验证根证书（自签名验证）
        System.out.println("\n=== 根证书验证 ===");
        System.out.println("颁发者: " + rootCert.getIssuerX500Principal());
        System.out.println("主题  : " + rootCert.getSubjectX500Principal());
        System.out.println("指纹  : " + X509Util.getFingerPrint(rootCert));
        System.out.println("验证结果: " + verifyRootCert(rootCert)); // 使用专用验证方法

        // 4.2 验证用户证书（需用根证书公钥验证）
        System.out.println("\n=== 用户证书验证 ===");
        System.out.println("颁发者: " + userCert.getIssuerX500Principal());
        System.out.println("主题  : " + userCert.getSubjectX500Principal());
        System.out.println("指纹  : " + X509Util.getFingerPrint(userCert));
        System.out.println("验证结果: " + verifyUserCert(userCert, rootCert)); // 使用专用验证方法
    }

    // 根证书验证专用方法（自签名验证）
    private static boolean verifyRootCert(X509Certificate cert) {
        try {
            cert.checkValidity();
            cert.verify(cert.getPublicKey()); // 自签名验证
            return true;
        } catch (Exception e) {
            System.err.println("根证书验证失败: " + e.getMessage());
            return false;
        }
    }

    // 用户证书验证专用方法（用CA公钥验证）
    private static boolean verifyUserCert(X509Certificate userCert, X509Certificate caCert) {
        try {
            userCert.checkValidity();
            userCert.verify(caCert.getPublicKey()); // 用CA公钥验证
            return true;
        } catch (Exception e) {
            System.err.println("用户证书验证失败: " + e.getMessage());
            return false;
        }
    }
}
