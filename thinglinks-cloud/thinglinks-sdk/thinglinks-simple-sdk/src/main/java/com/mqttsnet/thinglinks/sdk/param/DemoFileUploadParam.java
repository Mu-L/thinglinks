package com.mqttsnet.thinglinks.sdk.param;


import com.mqttsnet.thinglinks.sdk.request.DemoFileUploadRequest;
import com.mqttsnet.thinglinks.sdk.response.GetProductResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * @author 六如
 */
public class DemoFileUploadParam extends BaseParam<DemoFileUploadRequest, GetProductResponse> {
    @Override
    protected String method() {
        return "openapi.upload.more";
    }
}
