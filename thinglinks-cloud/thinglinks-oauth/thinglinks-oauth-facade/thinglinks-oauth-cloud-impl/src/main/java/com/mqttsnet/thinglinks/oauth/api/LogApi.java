package com.mqttsnet.thinglinks.oauth.api;

import com.mqttsnet.basic.constant.Constants;
import com.mqttsnet.basic.model.log.OptLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 操作日志保存 API
 *
 * @author zuihou
 * @date 2019/07/02
 */
@FeignClient(name = "${" + Constants.PROJECT_PREFIX + ".feign.oauth-server:thinglinks-oauth-server}")
public interface LogApi {

    /**
     * 保存日志
     *
     * @param log 操作日志
     */
    @RequestMapping(value = "/optLog", method = RequestMethod.POST)
    void save(@RequestBody OptLogDTO log);

}
