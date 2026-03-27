package com.mqttsnet.thinglinks.mobile.api;

import com.mqttsnet.basic.constant.Constants;
import com.mqttsnet.thinglinks.mobile.api.hystrix.MobileApiFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Mobile 远程接口
 *
 * @author mqttsnet
 */
@FeignClient(name = "${" + Constants.PROJECT_PREFIX + ".feign.tenant-server:thinglinks-mobile-server}", fallback = MobileApiFallback.class, path = "/mobile")
public interface MobileApi {


}
