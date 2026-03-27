package com.mqttsnet.thinglinks.card.api;

import com.mqttsnet.basic.constant.Constants;
import com.mqttsnet.thinglinks.card.api.hystrix.CardApiFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * -----------------------------------------------------------------------------
 * File Name: CardApi
 * -----------------------------------------------------------------------------
 * Description:
 * Card 远程接口
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/7/20       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/7/20 17:00
 */
@FeignClient(name = "${" + Constants.PROJECT_PREFIX + ".feign.tenant-server:thinglinks-card-server}", fallback = CardApiFallback.class, path = "/card")
public interface CardApi {
}
