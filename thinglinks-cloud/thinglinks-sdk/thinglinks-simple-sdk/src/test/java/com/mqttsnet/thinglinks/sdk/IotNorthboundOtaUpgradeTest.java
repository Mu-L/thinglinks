package com.mqttsnet.thinglinks.sdk;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.mqttsnet.thinglinks.sdk.param.ota.IotNorthboundOtaConfirmTaskParam;
import com.mqttsnet.thinglinks.sdk.param.ota.IotNorthboundOtaGetAvailableUpgradeVersionsParam;
import com.mqttsnet.thinglinks.sdk.param.ota.IotNorthboundOtaPullParam;
import com.mqttsnet.thinglinks.sdk.param.ota.IotNorthboundOtaReadResponseParam;
import com.mqttsnet.thinglinks.sdk.param.ota.IotNorthboundOtaRejectTaskParam;
import com.mqttsnet.thinglinks.sdk.param.ota.IotNorthboundOtaReportParam;
import com.mqttsnet.thinglinks.sdk.param.ota.IotNorthboundOtaSaveUpgradeRecordParam;
import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaConfirmTaskRequest;
import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaListUpgradeableVersionsRequest;
import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaPullRequest;
import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaReadResponseRequest;
import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaRejectTaskRequest;
import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaReportRequest;
import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaSaveUpgradeRecordRequest;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaConfirmTaskResponse;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaGetAvailableUpgradeVersionsResponse;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaPullResponse;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaReadResponseResponse;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaRejectTaskResponse;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaReportResponse;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaSaveUpgradeRecordResponse;
import com.mqttsnet.thinglinks.sdkcore.client.OpenClient;
import com.mqttsnet.thinglinks.sdkcore.common.Result;
import junit.framework.TestCase;

/**
 * Description:
 * 物联网北向API-OTA升级测试
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/1/6
 */
public class IotNorthboundOtaUpgradeTest extends TestCase {

    String url = "http://localhost:18750/api";
    String appId = "20250622646351801644697600";
    /**
     * 开发者私钥
     */
    String privateKeyIsv = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDJR5pDEncZPAWEIYgTLLC2jJNBO0eX401vrlzPzKVdbFDjr+m+tYkB6ysfLZZxmMZsjZS10ZbEwko2Z7F+VgRkYhqHFa5yiimgoHCJcUSgBnsoCyBDtYUdzzkU6IFD/VRx02ssxaOWebGi3aJ3qfYj9ThDiSvrZ5JhFIJNA4Qw98894UIG4P8Ypqf95/ztLh13LhRzlbPmzsDlru1tCixObidq1/r5f2NpJNF0H94JerGMZC3550+s9hlcf0NTpdaeMnVsyBzXkbiZFPwn4iGUIezroeswhIgdxuv+nKDlEXGE71+0fOM7asgGdiBFs+9Q1bWfZtgFcT15XaiD91GrAgMBAAECggEAUJAr0w/Xgs6u6ImhVQdsvcx7fj8Tc1yEGKECPhxLzh52LAQzu2Ue7xkpW+Pb1SEQvs9WiAXZYmzf1nHfrdERFfrcYlhPyEG70rKVCLZBAQpHDiqR4fgMTXetgcxkPQnvXFYCjluXFDomWyl6B4qMXi0fNYz4etMsWFYkp12ycgRwQEltkhmnXREkexEKn64zQ4w3CqpYa6SDsbPbIuWU8TRKwMRu1O34GKjYQzT0t7fMfeVLIkgwRt2NvTAHdOA6WJpRJZbu0BGRJ/MoIgumfY0dZGkMKgQ8Ss2/fgAC4ezb2uQq7VEIkr+X5MBoTfd7ure1lJaXaRBO5yi4zx1AuQKBgQDXD7OxE3FCH/svxazOUmIZBQ8eKSZ5WtrxN25inBF7FUAt7JzkH658Nl9bI8mjRywMqIO5+TGy1EbHmRT3NXFoGE7PKFpYJQq3uAWTFUpmm6jq3Hz0K9loZpNHE2X7vQmuNckmH7n52DVoHdrS2SNwMMnVDsbnoVH+9wOTnX7IiQKBgQDvmExlqJhsxILqH3AETk47FyA12945i+Y7my/Zwag0URKmKb97fpph/PHZGLY46u4RvjaHjgF2CIgA0VRNri5teHz6ycxMJ+c0ALsW8k0qFSn0Judl3P9/2oykETxJgf5i7J6fLhWoGGgzEPuOtboPMbxTmquU80ELsqszbwgTkwKBgQDVjvMaWTsztbxioQJL08U0ITD0+1oIUi9uL2Q/Kcm2FtEniXn2kntbP0cLowsdR5S3cTN1nr46Xc93b01BeuGFw/WmguTL+OSesW9fslmycMqZhp/SfCSlJo8DmoDALeUKCMEZSZINRyUpodPlYthtFRWnq8faxfg9np7/m/sFOQKBgQDPobIDHxoImDvDXL8cXCfbeW0Gf6WTRTOeUBwln3d+7ws0SFWSVeLALA4CTzILHTx2z9o9msiVW0tROhbOwrWlVJcqVprfHG12WXPIrO5GG9Uhql05n00jpWff/re6FBc570h/Nda6t3wFVSBLSh0Vccp8wTsUd8HUu1810XC0VQKBgDrOLBKAgdR+ckJuw/y0Fa3SJWCBRrbPAZfUtOQ4n3+DQ825X28/ycn5h5ArWeEXd6zftOrERJtGLujl+Nwc/mv8C/EeOQig9HfqASFKlL4XnMTuMYRglK5ItU9+B1FuwGnzzmsFzrch8ymdMgAr4auwySfvH7zeBEM4CM5eHNmc";
    /**
     * 开放平台提供的公钥
     * 前往【开发运营系统】--开放平台，开放应用--ISV秘钥，生成平台提供的公私钥，然后把【平台公钥】放到这里
     */
    String publicKeyPlatform = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzSNidMVQg73jPrGpn4m5xqYWI7+zxkYeZJ2W3M5/mamqh98xkcOI3k4BqiF75PJLqJzDPwAmC/15yao3jcUU5/jOpe/2PVZQic7xwxiv44Jg2uOUdpv8Y/JwiRRJY80UABlK8wzr60h8+Mxx0h4FiO44XdIOmPiCg/cElnqKR0kOMMINVBoex4BVjGKxoLKuFRzYFxtyRkd/dEee9+TJBKl/W6B3swWYRoKjg55RoogfFjEp2I4CSm2GE23qHLNB+uZsB0rB1FCHbkDXeC2tTa5zbX9NjjGS4LECH0qDqd5qWvldWLkEXikKP0bWuqVukRV0lN113UdpIIDZeM/xpwIDAQAB";

    // 声明一个就行
    OpenClient client = new OpenClient(url, appId, privateKeyIsv, publicKeyPlatform);

    /**
     * 测试确认OTA升级任务
     */
    public void testConfirmOtaTask() {
        // 1. 构建请求参数
        IotNorthboundOtaConfirmTaskRequest request = buildConfirmRequest();

        // 2. 封装API参数
        IotNorthboundOtaConfirmTaskParam param = new IotNorthboundOtaConfirmTaskParam();
        param.setBizModel(request);
        param.setVersion("1.0");


        try {
            // 3. 打印请求参数
            System.out.println("=== 请求参数 ===");
            System.out.println(JSON.toJSONString(param));

            // 4. 调用接口
            System.out.println("正在调用OTA确认升级任务接口...");
            Result<IotNorthboundOtaConfirmTaskResponse> result = client.execute(param);
            System.out.println("=== 确认接口响应 ===");
            System.out.println(JSON.toJSONString(result));

            // 5. 处理结果
            if (result.isSuccess()) {
                IotNorthboundOtaConfirmTaskResponse response = result.getData();
                System.out.println("=== 确认成功 ===");
                System.out.println("完整响应: " + JSON.toJSONString(response));
            } else {
                System.out.println("=== 确认失败 ===");
                System.out.println("错误码: " + result.getSubCode());
                System.out.println("错误信息: " + result.getSubMsg());
                System.out.println("解决方案: " + result.getSolution());
            }
        } catch (Exception e) {
            System.out.println("!!! 确认OTA升级任务发生异常 !!!");
            e.printStackTrace();
        }
    }

    /**
     * 测试拒绝OTA升级任务
     */
    public void testRejectOtaTask() {
        // 1. 构建请求参数
        IotNorthboundOtaRejectTaskRequest request = buildRejectRequest();

        // 2. 封装API参数
        IotNorthboundOtaRejectTaskParam param = new IotNorthboundOtaRejectTaskParam();
        param.setBizModel(request);
        param.setVersion("1.0");

        try {
            // 3. 打印请求参数
            System.out.println("=== 请求参数 ===");
            System.out.println(JSON.toJSONString(param));

            // 4. 调用接口
            System.out.println("正在调用OTA拒绝升级任务接口...");
            Result<IotNorthboundOtaRejectTaskResponse> result = client.execute(param);
            System.out.println("=== 拒绝接口响应 ===");
            System.out.println(JSON.toJSONString(result));

            // 5. 处理结果
            if (result.isSuccess()) {
                IotNorthboundOtaRejectTaskResponse response = result.getData();
                System.out.println("=== 拒绝成功 ===");
                System.out.println("完整响应: " + JSON.toJSONString(response));
            } else {
                System.out.println("=== 拒绝失败 ===");
                System.out.println("错误码: " + result.getSubCode());
                System.out.println("错误信息: " + result.getSubMsg());
                System.out.println("解决方案: " + result.getSolution());
            }
        } catch (Exception e) {
            System.out.println("!!! 拒绝OTA升级任务发生异常 !!!");
            e.printStackTrace();
        }
    }

    /**
     * 构建测试用的OTA确认请求
     */
    private IotNorthboundOtaConfirmTaskRequest buildConfirmRequest() {
        IotNorthboundOtaConfirmTaskRequest request = new IotNorthboundOtaConfirmTaskRequest();

        // 升级任务ID
        request.setTaskId(467832692856061957L);

        // 设备标识列表
        List<String> deviceList = new ArrayList<>();
        deviceList.add("5980714290524161");
        deviceList.add("5981535694630913");
        deviceList.add("9038507956805633");
        request.setDeviceIdentificationList(deviceList);

        return request;
    }

    /**
     * 构建测试用的OTA拒绝请求
     */
    private IotNorthboundOtaRejectTaskRequest buildRejectRequest() {
        IotNorthboundOtaRejectTaskRequest request = new IotNorthboundOtaRejectTaskRequest();

        // 升级任务ID
        request.setTaskId(467832692856061957L);

        // 设备标识列表
        List<String> deviceList = new ArrayList<>();
        deviceList.add("5980714290524161");
        deviceList.add("5981535694630913");
        request.setDeviceIdentificationList(deviceList);

        return request;
    }

    /**
     * 测试保存OTA升级记录
     */
    public void testSaveOtaUpgradeRecord() {
        // 1. 构建请求参数
        IotNorthboundOtaSaveUpgradeRecordRequest request = new IotNorthboundOtaSaveUpgradeRecordRequest();
        request.setDeviceIdentification("DEVICE_001");
        request.setOtaTaskId(123456L);
        request.setUpgradeStatus(2); // 升级成功
        request.setProgress(100);
        request.setStartTime(System.currentTimeMillis() - 60000);
        request.setEndTime(System.currentTimeMillis());
        request.setSuccessDetails("固件升级成功，已重启设备");

        // 2. 封装API参数
        IotNorthboundOtaSaveUpgradeRecordParam param = new IotNorthboundOtaSaveUpgradeRecordParam();
        param.setBizModel(request);
        param.setVersion("1.0");

        try {
            System.out.println("=== 请求参数 ===");
            System.out.println(JSON.toJSONString(param));

            System.out.println("正在调用保存OTA升级记录接口...");
            Result<IotNorthboundOtaSaveUpgradeRecordResponse> result = client.execute(param);

            if (result.isSuccess()) {
                IotNorthboundOtaSaveUpgradeRecordResponse response = result.getData();
                System.out.println("=== 保存成功 ===");
                System.out.println("操作结果: " + response.getSuccess());
                System.out.println("设备标识: " + response.getDeviceIdentification());
                System.out.println("任务ID: " + response.getOtaTaskId());
                System.out.println("升级状态: " + response.getUpgradeStatus());
                System.out.println("升级进度: " + response.getProgress() + "%");
            } else {
                System.out.println("=== 保存失败 ===");
                System.out.println("错误码: " + result.getSubCode());
                System.out.println("错误信息: " + result.getSubMsg());
            }
        } catch (Exception e) {
            System.out.println("!!! 发生异常 !!!");
            e.printStackTrace();
        }
    }

    /**
     * 测试OTA拉取软固件信息
     */
    public void testOtaPull() {
        // 1. 构建请求参数
        IotNorthboundOtaPullRequest request = new IotNorthboundOtaPullRequest();
        request.setDeviceIdentification("4001308136820736_2000000");
        request.setPackageType(0);
        request.setCurrentVersion("v1.0.1");
        request.setRequestVersion("v2.0.0"); // 请求指定版本

        // 2. 封装API参数
        IotNorthboundOtaPullParam param = new IotNorthboundOtaPullParam();
        param.setBizModel(request);
        param.setVersion("1.0");

        try {
            System.out.println("=== 请求参数 ===");
            System.out.println(JSON.toJSONString(param));

            System.out.println("正在调用OTA拉取软固件信息接口...");
            Result<IotNorthboundOtaPullResponse> result = client.execute(param);

            if (result.isSuccess()) {
                IotNorthboundOtaPullResponse response = result.getData();
                System.out.println("=== 拉取成功 ===");
                System.out.println("设备标识: " + response.getDeviceIdentification());
                System.out.println("产品标识: " + response.getProductIdentification());
                System.out.println("任务ID: " + response.getOtaTaskId());
                System.out.println("任务名称: " + response.getOtaTaskName());
                System.out.println("包名称: " + response.getPackageName());
                System.out.println("包类型: " + response.getPackageType());
                System.out.println("版本: " + response.getVersion());
                System.out.println("下载地址: " + response.getFileLocation());
                System.out.println("描述: " + response.getDescription());
                System.out.println("签名方法: " + response.getSignMethod());
                System.out.println("签名值: " + response.getSign());
            } else {
                System.out.println("=== 拉取失败 ===");
                System.out.println("错误码: " + result.getSubCode());
                System.out.println("错误信息: " + result.getSubMsg());
            }
        } catch (Exception e) {
            System.out.println("!!! 发生异常 !!!");
            e.printStackTrace();
        }
    }

    /**
     * 测试OTA上报软固件版本
     */
    public void testOtaReport() {
        // 1. 构建请求参数
        IotNorthboundOtaReportRequest request = new IotNorthboundOtaReportRequest();
        request.setDeviceIdentification("4001308136820736_2000000");
        request.setPackageType(0);
        request.setCurrentVersion("1.3.0");

        // 2. 封装API参数
        IotNorthboundOtaReportParam param = new IotNorthboundOtaReportParam();
        param.setBizModel(request);
        param.setVersion("1.0");

        try {
            System.out.println("=== 请求参数 ===");
            System.out.println(JSON.toJSONString(param));

            System.out.println("正在调用OTA上报软固件版本接口...");
            Result<IotNorthboundOtaReportResponse> result = client.execute(param);

            if (result.isSuccess()) {
                IotNorthboundOtaReportResponse response = result.getData();
                System.out.println("=== 上报成功 ===");
                System.out.println("设备标识: " + response.getDeviceIdentification());
                System.out.println("包类型: " + response.getPackageType());
                System.out.println("当前版本: " + response.getCurrentVersion());
            } else {
                System.out.println("=== 上报失败 ===");
                System.out.println("错误码: " + result.getSubCode());
                System.out.println("错误信息: " + result.getSubMsg());
            }
        } catch (Exception e) {
            System.out.println("!!! 发生异常 !!!");
            e.printStackTrace();
        }
    }

    /**
     * 测试OTA读取设备软固件版本信息响应
     */
    public void testOtaReadResponse() {
        // 1. 构建请求参数
        IotNorthboundOtaReadResponseRequest request = new IotNorthboundOtaReadResponseRequest();
        request.setDeviceIdentification("4001308136820736_2000000");
        request.setPackageType(0);
        request.setCurrentVersion("v2.0.0");

        // 2. 封装API参数
        IotNorthboundOtaReadResponseParam param = new IotNorthboundOtaReadResponseParam();
        param.setBizModel(request);
        param.setVersion("1.0");

        try {
            System.out.println("=== 请求参数 ===");
            System.out.println(JSON.toJSONString(param));

            System.out.println("正在调用OTA读取设备软固件版本信息响应接口...");
            Result<IotNorthboundOtaReadResponseResponse> result = client.execute(param);

            if (result.isSuccess()) {
                IotNorthboundOtaReadResponseResponse response = result.getData();
                System.out.println("=== 响应成功 ===");
                System.out.println("操作结果: " + response.getSuccess());
                System.out.println("设备标识: " + response.getDeviceIdentification());
                System.out.println("包类型: " + response.getPackageType());
                System.out.println("当前版本: " + response.getCurrentVersion());
                System.out.println("消息: " + response.getMessage());
            } else {
                System.out.println("=== 响应失败 ===");
                System.out.println("错误码: " + result.getSubCode());
                System.out.println("错误信息: " + result.getSubMsg());
            }
        } catch (Exception e) {
            System.out.println("!!! 发生异常 !!!");
            e.printStackTrace();
        }
    }

    /**
     * 测试OTA获取可用升级版本
     */
    public void testGetAvailableUpgradeVersions() {
        // 1. 构建请求参数
        IotNorthboundOtaListUpgradeableVersionsRequest request = new IotNorthboundOtaListUpgradeableVersionsRequest();
        request.setDeviceIdentification("4001308136820736_2000000");
        request.setPackageType(0);

        // 2. 封装API参数
        IotNorthboundOtaGetAvailableUpgradeVersionsParam param = new IotNorthboundOtaGetAvailableUpgradeVersionsParam();
        param.setBizModel(request);
        param.setVersion("1.0");

        try {
            System.out.println("=== 请求参数 ===");
            System.out.println(JSON.toJSONString(param));

            System.out.println("正在调用OTA获取可用升级版本接口...");
            Result<IotNorthboundOtaGetAvailableUpgradeVersionsResponse> result = client.execute(param);

            if (result.isSuccess()) {
                IotNorthboundOtaGetAvailableUpgradeVersionsResponse response = result.getData();
                System.out.println("=== 获取成功 ===");
                System.out.println("设备标识: " + response.getDeviceIdentification());
                System.out.println("产品标识: " + response.getProductIdentification());
                System.out.println("包类型: " + response.getPackageType());
                System.out.println("当前版本: " + response.getCurrentVersion());
                System.out.println("可升级版本数量: " + (response.getUpgradeVersions() != null ? response.getUpgradeVersions().size() : 0));

                if (response.getUpgradeVersions() != null && !response.getUpgradeVersions().isEmpty()) {
                    System.out.println("=== 可升级版本列表 ===");
                    for (IotNorthboundOtaGetAvailableUpgradeVersionsResponse.UpgradeVersionInfo versionInfo : response.getUpgradeVersions()) {
                        System.out.println("------------------------------------------------");
                        System.out.println("任务ID: " + versionInfo.getOtaTaskId());
                        System.out.println("任务名称: " + versionInfo.getOtaTaskName());
                        System.out.println("包名称: " + versionInfo.getPackageName());
                        System.out.println("版本号: " + versionInfo.getVersion());
                        System.out.println("下载地址: " + versionInfo.getFileLocation());
                        System.out.println("描述: " + versionInfo.getDescription());
                        System.out.println("签名方法: " + versionInfo.getSignMethod());
                        System.out.println("签名值: " + versionInfo.getSign());
                    }
                }
            } else {
                System.out.println("=== 获取失败 ===");
                System.out.println("错误码: " + result.getSubCode());
                System.out.println("错误信息: " + result.getSubMsg());
            }
        } catch (Exception e) {
            System.out.println("!!! 发生异常 !!!");
            e.printStackTrace();
        }
    }
}