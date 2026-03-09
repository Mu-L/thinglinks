package com.mqttsnet.thinglinks.mobile.mobilespace.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.qrcode.QrcodeUtils;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.common.constant.QrcodeConstant;
import com.mqttsnet.thinglinks.mobile.mobilespace.service.MobileSpaceQrcodeService;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.result.MobileSpaceQrcodeResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.enumeration.SpaceMemberTypeEnum;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.service.MobileSpaceMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiaonannet
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class MobileSpaceQrcodeServiceImpl implements MobileSpaceQrcodeService {

    private final MobileSpaceMemberService mobileSpaceMemberService;

    @Override
    public byte[] generateQrcode(Long spaceId, Integer memberType) throws Exception {
        log.info("开始生成空间: [{}] 的二维码,spaceId:{},memberType:{}", spaceId, memberType);
        //校验参数
        ArgumentAssert.notNull(spaceId, "spaceId Cannot be null");
        ArgumentAssert.notNull(memberType, "memberType Cannot be null");

        // 定义Logo文件路径，这里假设Logo存储在resources目录下的images文件夹中
        try (InputStream logoStream = getClass().getClassLoader().getResourceAsStream(QrcodeConstant.LOGO_RESOURCE_LOCATION)) {
            if (logoStream == null) {
                log.error("Logo文件未找到，请确保logo.png存储在resources/images目录下");
                throw new FileNotFoundException("Logo文件未找到，请确保logo.png存储在resources/images目录下");
            }
            //根据空间ID查询是否已经存在所有者
            if (SpaceMemberTypeEnum.OWNER.getValue().equals(memberType)) {
                if (mobileSpaceMemberService.isAdminExistsBySpaceId(spaceId)) {
                    throw BizException.wrap("This space already has administrator, do not add again");
                }
            }
            // 封装
            MobileSpaceQrcodeResultVO mobileSpaceQrcodeResultVO = new MobileSpaceQrcodeResultVO();
            mobileSpaceQrcodeResultVO.setScene(QrcodeConstant.SPACE_SCENE);
            mobileSpaceQrcodeResultVO.setSpaceId(spaceId);
            mobileSpaceQrcodeResultVO.setMemberType(memberType);

            // 生成二维码字节数组
            return QrcodeUtils.createQrcode(JsonUtil.toJson(mobileSpaceQrcodeResultVO), QrcodeConstant.QR_CODE_LENGTH, logoStream);

        } catch (FileNotFoundException e) {
            log.error("生成空间: [{}] 的二维码时发生文件未找到错误: {}", spaceId, e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            log.error("读取Logo文件时发生I/O错误: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("生成二维码时发生错误: {}", e.getMessage(), e);
            throw e;
        }
    }
}
