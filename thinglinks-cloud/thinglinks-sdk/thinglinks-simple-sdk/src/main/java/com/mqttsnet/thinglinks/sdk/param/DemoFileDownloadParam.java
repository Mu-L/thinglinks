package com.mqttsnet.thinglinks.sdk.param;


import com.mqttsnet.thinglinks.sdk.request.DemoFileDownloadRequest;
import com.mqttsnet.thinglinks.sdk.request.DemoFileUploadRequest;
import com.mqttsnet.thinglinks.sdk.response.GetProductResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * @author 六如
 */
public class DemoFileDownloadParam extends BaseParam<DemoFileDownloadRequest, Object> {
    @Override
    protected String method() {
        return "openapi.download";
    }
}
