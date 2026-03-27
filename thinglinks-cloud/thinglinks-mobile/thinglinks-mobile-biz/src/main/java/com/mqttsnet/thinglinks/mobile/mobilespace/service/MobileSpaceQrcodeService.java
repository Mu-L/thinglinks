package com.mqttsnet.thinglinks.mobile.mobilespace.service;

/**
 * @author xiaonannet
 */
public interface MobileSpaceQrcodeService {


    /**
     * 生成带有Logo的空间二维码
     *
     * @return 生成的二维码字节数组
     * @throws Exception 如果生成过程中发生错误
     */
    byte[] generateQrcode(Long spaceId, Integer memberType) throws Exception;

}
