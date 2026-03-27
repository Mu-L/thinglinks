package com.mqttsnet.thinglinks.sdk.param;

import com.mqttsnet.thinglinks.sdk.request.GetBaseEmployeeRequest;
import com.mqttsnet.thinglinks.sdk.response.GetBaseEmployeeResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

public class GetBaseEmployeeParam extends BaseParam<GetBaseEmployeeRequest, GetBaseEmployeeResponse> {
    @Override
    protected String method() {
        return "openapi.employee.get";
    }

}
