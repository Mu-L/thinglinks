package com.mqttsnet.thinglinks.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * -----------------------------------------------------------------------------
 * File Name: ServletProperties
 * -----------------------------------------------------------------------------
 * Description:
 * <p>
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/4/16       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/4/16 18:03
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "server.servlet")
public class ServletProperties {
    private String contextPath;
    private ExcludedOnline excludedOnline;

    public static class ExcludedOnline {
        private List<String> services;

        public List<String> getServices() {
            if (services == null) {
                services = new ArrayList<>();
            }
            return services;
        }
    }
}