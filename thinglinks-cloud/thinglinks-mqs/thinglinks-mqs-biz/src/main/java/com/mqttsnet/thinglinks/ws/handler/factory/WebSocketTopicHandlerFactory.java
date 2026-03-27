package com.mqttsnet.thinglinks.ws.handler.factory;


import com.mqttsnet.thinglinks.ws.handler.WebSocketAddSubDeviceHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketChatgptRequestHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketCommandResponseHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketDefaultHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketDeleteSubDeviceHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketDeviceDatasHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketOtaCommandResponseHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketOtaPullHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketOtaReadResponseHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketOtaReportHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketQueryDeviceHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketSecretKeyHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketTimeSyncRequestHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketTopicHandler;
import com.mqttsnet.thinglinks.ws.handler.WebSocketUpdateSubDeviceHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: thinglinks-cloud
 * @description: WebSocket系统Topic 处理工厂类
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-05-05 22:00
 **/
@Component
public class WebSocketTopicHandlerFactory {

    // Define patterns as constants
    private static final Pattern DEFAULT_PATTERN = Pattern.compile("");
    private static final Pattern SECRET_KEY_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/topo/secretKey$");
    private static final Pattern ADD_SUB_DEVICE_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/topo/add$");
    private static final Pattern DELETE_SUB_DEVICE_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/topo/delete$");
    private static final Pattern UPDATE_SUB_DEVICE_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/topo/update$");
    private static final Pattern QUERY_DEVICE_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/topo/query$");
    private static final Pattern DEVICE_DATAS_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/datas$");
    private static final Pattern COMMAND_RESPONSE_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/commandResponse$");
    private static final Pattern SYNC_TIME_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/topo/timeSyncRequest$");
    private static final Pattern OTA_COMMAND_RESPONSE_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/topo/otaCommandResponse$");
    private static final Pattern OTA_PULL_RESPONSE_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/topo/otaPull$");
    private static final Pattern OTA_REPORT_RESPONSE_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/topo/otaReport$");
    private static final Pattern OTA__RESPONSE_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/topo/otaReadResponse$");
    private static final Pattern CHATGPT_REQUEST_PATTERN = Pattern.compile("^/([^/]+)/devices/([^/]+)/chatgpt/request$");
    private final WebSocketDefaultHandler defaultHandler;
    private final WebSocketSecretKeyHandler secretKeyHandler;
    private final WebSocketAddSubDeviceHandler addSubDeviceHandler;
    private final WebSocketDeleteSubDeviceHandler deleteSubDeviceHandler;
    private final WebSocketUpdateSubDeviceHandler updateSubDeviceHandler;
    private final WebSocketQueryDeviceHandler queryDeviceHandler;
    private final WebSocketDeviceDatasHandler deviceDatasHandler;
    private final WebSocketCommandResponseHandler commandResponseHandler;
    private final WebSocketTimeSyncRequestHandler timeSyncRequestHandler;
    private final WebSocketOtaCommandResponseHandler otaCommandResponseHandler;
    private final WebSocketOtaPullHandler otaPullHandler;
    private final WebSocketOtaReportHandler otaReportHandler;
    private final WebSocketOtaReadResponseHandler otaReadResponseHandler;
    private final WebSocketChatgptRequestHandler chatgptRequestHandler;
    private List<TopicHandlerEntry> topicHandlerEntries;

    public WebSocketTopicHandlerFactory(WebSocketDefaultHandler defaultHandler, WebSocketSecretKeyHandler secretKeyHandler,
                                        WebSocketAddSubDeviceHandler addSubDeviceHandler, WebSocketDeleteSubDeviceHandler deleteSubDeviceHandler,
                                        WebSocketUpdateSubDeviceHandler updateSubDeviceHandler, WebSocketQueryDeviceHandler queryDeviceHandler, WebSocketDeviceDatasHandler deviceDatasHandler,
                                        WebSocketCommandResponseHandler commandResponseHandler, WebSocketOtaCommandResponseHandler otaCommandResponseHandler, WebSocketOtaPullHandler otaPullHandler,
                                        WebSocketOtaReportHandler otaReportHandler, WebSocketTimeSyncRequestHandler timeSyncRequestHandler, WebSocketOtaReadResponseHandler otaReadResponseHandler,
                                        WebSocketChatgptRequestHandler chatgptRequestHandler) {
        this.defaultHandler = defaultHandler;
        this.secretKeyHandler = secretKeyHandler;
        this.addSubDeviceHandler = addSubDeviceHandler;
        this.deleteSubDeviceHandler = deleteSubDeviceHandler;
        this.updateSubDeviceHandler = updateSubDeviceHandler;
        this.queryDeviceHandler = queryDeviceHandler;
        this.deviceDatasHandler = deviceDatasHandler;
        this.commandResponseHandler = commandResponseHandler;
        this.timeSyncRequestHandler = timeSyncRequestHandler;
        this.otaCommandResponseHandler = otaCommandResponseHandler;
        this.otaPullHandler = otaPullHandler;
        this.otaReportHandler = otaReportHandler;
        this.otaReadResponseHandler = otaReadResponseHandler;
        this.chatgptRequestHandler = chatgptRequestHandler;
    }

    // Use the @PostConstruct annotation to initialize the handler entries list after the dependencies are injected.
    @PostConstruct
    public void initTopicHandlerEntries() {
        topicHandlerEntries = new ArrayList<>();
        topicHandlerEntries.add(new TopicHandlerEntry(DEFAULT_PATTERN, defaultHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(SECRET_KEY_PATTERN, secretKeyHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(ADD_SUB_DEVICE_PATTERN, addSubDeviceHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(DELETE_SUB_DEVICE_PATTERN, deleteSubDeviceHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(UPDATE_SUB_DEVICE_PATTERN, updateSubDeviceHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(QUERY_DEVICE_PATTERN, queryDeviceHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(DEVICE_DATAS_PATTERN, deviceDatasHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(COMMAND_RESPONSE_PATTERN, commandResponseHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(SYNC_TIME_PATTERN, timeSyncRequestHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(OTA_COMMAND_RESPONSE_PATTERN, otaCommandResponseHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(OTA_PULL_RESPONSE_PATTERN, otaPullHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(OTA_REPORT_RESPONSE_PATTERN, otaReportHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(OTA__RESPONSE_PATTERN, otaReadResponseHandler));
        topicHandlerEntries.add(new TopicHandlerEntry(CHATGPT_REQUEST_PATTERN, chatgptRequestHandler));
    }

    // This method searches for a matching handler based on the topic.
    public WebSocketTopicHandler findMatchingHandler(String topic) {
        for (TopicHandlerEntry handlerEntry : topicHandlerEntries) {
            Matcher matcher = handlerEntry.getPattern().matcher(topic);
            if (matcher.matches()) {
                return handlerEntry.getHandler();
            }
        }
        return defaultHandler;
    }

    private static final class TopicHandlerEntry {
        private final Pattern pattern;
        private final WebSocketTopicHandler handler;

        public TopicHandlerEntry(Pattern pattern, WebSocketTopicHandler handler) {
            this.pattern = pattern;
            this.handler = handler;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public WebSocketTopicHandler getHandler() {
            return handler;
        }
    }
}