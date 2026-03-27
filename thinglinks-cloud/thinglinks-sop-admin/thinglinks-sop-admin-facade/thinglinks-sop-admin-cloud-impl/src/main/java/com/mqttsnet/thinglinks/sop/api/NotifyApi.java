package com.mqttsnet.thinglinks.sop.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.constant.Constants;
import com.mqttsnet.thinglinks.sop.dto.NotifyRequest;

/**
 *
 * @author tangyh
 * @since 2025/12/18 00:06
 */
@FeignClient(name = "${" + Constants.PROJECT_PREFIX + ".feign.sop-admin-server:thinglinks-sop-admin-server}")
public interface NotifyApi {

    /**
     * 回调
     *
     * @param request 参数
     * @return 返回结果 回调ID
     */
    @PostMapping(value = "/notify/exec")
    R<Long> notify(@RequestBody NotifyRequest request);

}
