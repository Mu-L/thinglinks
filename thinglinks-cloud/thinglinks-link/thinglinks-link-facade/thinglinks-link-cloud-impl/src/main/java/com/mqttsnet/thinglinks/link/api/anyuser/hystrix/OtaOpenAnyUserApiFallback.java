package com.mqttsnet.thinglinks.link.api.anyuser.hystrix;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.link.api.anyuser.OtaOpenAnyUserApi;
import com.mqttsnet.thinglinks.ota.vo.param.DeviceOtaUpgradeAppConfirmationParam;
import com.mqttsnet.thinglinks.ota.vo.result.DeviceOtaUpgradeAppConfirmationResultVO;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaCommandResponseParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaListUpgradeableVersionsResponseParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaPullParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaPullResponseParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaReadResponseParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaReportParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaReportResponseParam;
import org.springframework.stereotype.Component;

/**
 * Description:
 * OTA相关开放接口-API熔断
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/12/26
 */
@Component
public class OtaOpenAnyUserApiFallback implements OtaOpenAnyUserApi {

    @Override
    public R<DeviceOtaUpgradeAppConfirmationResultVO> otaUpgradeAppConfirmation(DeviceOtaUpgradeAppConfirmationParam param) {
        return R.timeout();
    }

    @Override
    public R<TopoOtaCommandResponseParam> saveOtaUpgradeRecordByMqtt(TopoOtaCommandResponseParam topoOtaCommandResponseParam) {
        return R.timeout();
    }

    @Override
    public R<TopoOtaCommandResponseParam> saveOtaUpgradeRecordByNorthbound(TopoOtaCommandResponseParam topoOtaCommandResponseParam) {
        return R.timeout();
    }

    @Override
    public R<TopoOtaPullResponseParam> otaPullByMqtt(TopoOtaPullParam topoOtaPullParam) {
        return R.timeout();
    }

    @Override
    public R<TopoOtaPullResponseParam> otaPullByNorthbound(TopoOtaPullParam topoOtaPullParam) {
        return R.timeout();
    }

    @Override
    public R<TopoOtaReportResponseParam> otaReportByMqtt(TopoOtaReportParam topoOtaReportParam) {
        return R.timeout();
    }

    @Override
    public R<TopoOtaReportResponseParam> otaReportByNorthbound(TopoOtaReportParam topoOtaReportParam) {
        return R.timeout();
    }

    @Override
    public R<?> otaReadResponseByMqtt(TopoOtaReadResponseParam topoOtaReadResponseParam) {
        return R.timeout();
    }

    @Override
    public R<?> otaReadResponseByNorthbound(TopoOtaReadResponseParam topoOtaReadResponseParam) {
        return R.timeout();
    }

    @Override
    public R<TopoOtaListUpgradeableVersionsResponseParam> getAvailableUpgradeVersionsByNorthbound(String deviceIdentification, Integer packageType) {
        return R.timeout();
    }
}
