package com.mqttsnet.thinglinks.sdk;

import com.alibaba.fastjson2.JSON;
import junit.framework.TestCase;
import com.mqttsnet.thinglinks.sdk.param.DemoFileDownloadParam;
import com.mqttsnet.thinglinks.sdk.param.DemoFileUploadParam;
import com.mqttsnet.thinglinks.sdk.param.GetBaseEmployeeParam;
import com.mqttsnet.thinglinks.sdk.param.PayTradeWapPayParam;
import com.mqttsnet.thinglinks.sdk.request.DemoFileDownloadRequest;
import com.mqttsnet.thinglinks.sdk.request.DemoFileUploadRequest;
import com.mqttsnet.thinglinks.sdk.request.GetBaseEmployeeRequest;
import com.mqttsnet.thinglinks.sdk.request.PayTradeWapPayRequest;
import com.mqttsnet.thinglinks.sdk.response.GetBaseEmployeeResponse;
import com.mqttsnet.thinglinks.sdk.response.GetProductResponse;
import com.mqttsnet.thinglinks.sdk.response.PayTradeWapPayResponse;
import com.mqttsnet.thinglinks.sdkcore.client.OpenClient;
import com.mqttsnet.thinglinks.sdkcore.common.Result;
import com.mqttsnet.thinglinks.sdkcore.common.UploadFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试SDK
 */
public class SdkTest extends TestCase {
    String url = "http://localhost:18750/api";
    String appId = "2019032617262200001";
    /**
     * 开发者私钥
     */
    String privateKeyIsv = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXJv1pQFqWNA/++OYEV7WYXwexZK/J8LY1OWlP9X0T6wHFOvxNKRvMkJ5544SbgsJpVcvRDPrcxmhPbi/sAhdO4x2PiPKIz9Yni2OtYCCeaiE056B+e1O2jXoLeXbfi9fPivJZkxH/tb4xfLkH3bA8ZAQnQsoXA0SguykMRZntF0TndUfvDrLqwhlR8r5iRdZLB6F8o8qXH6UPDfNEnf/K8wX5T4EB1b8x8QJ7Ua4GcIUqeUxGHdQpzNbJdaQvoi06lgccmL+PHzminkFYON7alj1CjDN833j7QMHdPtS9l7B67fOU/p2LAAkPMtoVBfxQt9aFj7B8rEhGCz02iJIBAgMBAAECggEARqOuIpY0v6WtJBfmR3lGIOOokLrhfJrGTLF8CiZMQha+SRJ7/wOLPlsH9SbjPlopyViTXCuYwbzn2tdABigkBHYXxpDV6CJZjzmRZ+FY3S/0POlTFElGojYUJ3CooWiVfyUMhdg5vSuOq0oCny53woFrf32zPHYGiKdvU5Djku1onbDU0Lw8w+5tguuEZ76kZ/lUcccGy5978FFmYpzY/65RHCpvLiLqYyWTtaNT1aQ/9pw4jX9HO9NfdJ9gYFK8r/2f36ZE4hxluAfeOXQfRC/WhPmiw/ReUhxPznG/WgKaa/OaRtAx3inbQ+JuCND7uuKeRe4osP2jLPHPP6AUwQKBgQDUNu3BkLoKaimjGOjCTAwtp71g1oo+k5/uEInAo7lyEwpV0EuUMwLA/HCqUgR4K9pyYV+Oyb8d6f0+Hz0BMD92I2pqlXrD7xV2WzDvyXM3s63NvorRooKcyfd9i6ccMjAyTR2qfLkxv0hlbBbsPHz4BbU63xhTJp3Ghi0/ey/1HQKBgQC2VsgqC6ykfSidZUNLmQZe3J0p/Qf9VLkfrQ+xaHapOs6AzDU2H2osuysqXTLJHsGfrwVaTs00ER2z8ljTJPBUtNtOLrwNRlvgdnzyVAKHfOgDBGwJgiwpeE9voB1oAV/mXqSaUWNnuwlOIhvQEBwekqNyWvhLqC7nCAIhj3yvNQKBgQCqYbeec56LAhWP903Zwcj9VvG7sESqXUhIkUqoOkuIBTWFFIm54QLTA1tJxDQGb98heoCIWf5x/A3xNI98RsqNBX5JON6qNWjb7/dobitti3t99v/ptDp9u8JTMC7penoryLKK0Ty3bkan95Kn9SC42YxaSghzqkt+uvfVQgiNGQKBgGxU6P2aDAt6VNwWosHSe+d2WWXt8IZBhO9d6dn0f7ORvcjmCqNKTNGgrkewMZEuVcliueJquR47IROdY8qmwqcBAN7Vg2K7r7CPlTKAWTRYMJxCT1Hi5gwJb+CZF3+IeYqsJk2NF2s0w5WJTE70k1BSvQsfIzAIDz2yE1oPHvwVAoGAA6e+xQkVH4fMEph55RJIZ5goI4Y76BSvt2N5OKZKd4HtaV+eIhM3SDsVYRLIm9ZquJHMiZQGyUGnsvrKL6AAVNK7eQZCRDk9KQz+0GKOGqku0nOZjUbAu6A2/vtXAaAuFSFx1rUQVVjFulLexkXR3KcztL1Qu2k5pB6Si0K/uwQ=";
    /**
     * 开放平台提供的公钥
     * 前往【开发运营系统】--开放平台，ISV管理--秘钥管理，生成平台提供的公私钥，然后把【平台公钥】放到这里
     */
    String publicKeyPlatform = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj0CaMfudpfsrzgT7014aIGQPiEHvk5JPMlHH7YI5JYk+yAgePntojJ8/q1nmeHAauJqEYuCZHfqcjxzLM2hVvttrXtiacTMlr/ea9CGJtx4m20ltrsPOIXPXXZUToxXgO7X1FNvgXgeBBPcWLrsmJUgAQbM1KG/bo9QdNp/cFf5tBuo+1fXB9qXlZnSCbvQwrhfDGAF7NmEYkvkoQeys9YkASAl+zeEOXdBkPQjKDd9USyb/tIkrgLmeo0EOp+PytmEOAsMPSeIEdRcwrgg16X9BvMvnPKLTetQxXILG7r6kkkLj1pVA8EGinRDFu0jwp/Wu+wwUvRlpDRvUbyWEOQIDAQAB";

    // 声明一个就行
    OpenClient client = new OpenClient(url, appId, privateKeyIsv, publicKeyPlatform);

    /**
     * 测试 查询员工
     */
    public void testGetBaseEmployee() {
        // 创建请求对象
        GetBaseEmployeeParam param = new GetBaseEmployeeParam();
        // 请求参数
        GetBaseEmployeeRequest model = new GetBaseEmployeeRequest();
        model.setId(339843960220418056L);
        param.setBizModel(model);

        // 发送请求
        Result<GetBaseEmployeeResponse> result = client.execute(param);

        if (result.isSuccess()) {
            GetBaseEmployeeResponse response = result.getData();
            // 返回结果
            System.out.printf("response:%s%n", JSON.toJSONString(response));
        } else {
            System.out.println("错误，subCode:" + JSON.toJSONString(result) + ", subMsg:" + result.getSubMsg());
        }
    }

    /**
     * 测试 - 手机网站支付接口
     */
    public void testTradeWapPay() {
        PayTradeWapPayParam param = new PayTradeWapPayParam();
        PayTradeWapPayRequest model = new PayTradeWapPayRequest();
        model.setOutTradeNo("70501111111S001111119");
        model.setTotalAmount(new BigDecimal("1000"));
        model.setSubject("衣服");
        model.setProductCode("QUICK_WAP_WAY");

        param.setBizModel(model);

        Result<PayTradeWapPayResponse> result = client.execute(param);
        if (result.isSuccess()) {
            PayTradeWapPayResponse response = result.getData();
            System.out.println(response);
        } else {
            System.out.println(result);
        }
    }


    // 文件上传
    public void testUpload() throws IOException {
        DemoFileUploadParam request = new DemoFileUploadParam();

        DemoFileUploadRequest model = new DemoFileUploadRequest();
        model.setProductName("上传文件参数");
        model.setAddTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        request.setBizModel(model);

        String root = System.getProperty("user.dir");
        System.out.println(root);
        // 这里演示将resources下的两个文件上传到服务器
        request.addFile(new UploadFile("idCardFront", new File(root + "/src/test/resources/file1.txt")));
        request.addFile(new UploadFile("idCardBack", new File(root + "/src/test/resources/file2.txt")));

        Result<GetProductResponse> result = client.execute(request);

        System.out.println("--------------------");
        if (result.isSuccess()) {
            GetProductResponse response = result.getData();
            System.out.println("您上传的文件信息：" + response);
        } else {
            System.out.println(JSON.toJSONString(result));
        }
        System.out.println("--------------------");
    }


    public void testDownload() {
        DemoFileDownloadParam request = new DemoFileDownloadParam();
        DemoFileDownloadRequest model = new DemoFileDownloadRequest();
        model.setId(123);
        request.setBizModel(model);
        Result<Object> execute = client.execute(request);

        System.out.println(execute);
    }

}
