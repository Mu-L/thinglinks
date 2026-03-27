package com.mqttsnet.thinglinks.mqtt.handler.factory;


import com.mqttsnet.thinglinks.mqtt.handler.AddSubDeviceHandler;
import com.mqttsnet.thinglinks.mqtt.handler.ChatgptRequestHandler;
import com.mqttsnet.thinglinks.mqtt.handler.CommandResponseHandler;
import com.mqttsnet.thinglinks.mqtt.handler.DefaultHandler;
import com.mqttsnet.thinglinks.mqtt.handler.DeleteSubDeviceHandler;
import com.mqttsnet.thinglinks.mqtt.handler.DeviceDatasHandler;
import com.mqttsnet.thinglinks.mqtt.handler.OtaCommandResponseHandler;
import com.mqttsnet.thinglinks.mqtt.handler.OtaPullHandler;
import com.mqttsnet.thinglinks.mqtt.handler.OtaReadResponseHandler;
import com.mqttsnet.thinglinks.mqtt.handler.OtaReportHandler;
import com.mqttsnet.thinglinks.mqtt.handler.QueryDeviceHandler;
import com.mqttsnet.thinglinks.mqtt.handler.SecretKeyHandler;
import com.mqttsnet.thinglinks.mqtt.handler.TimeSyncRequestHandler;
import com.mqttsnet.thinglinks.mqtt.handler.TopicHandler;
import com.mqttsnet.thinglinks.mqtt.handler.UpdateSubDeviceHandler;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: thinglinks-cloud
 * @description: MQTT系统Topic 处理工厂类
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-05-05 22:00
 **/
@Component
public class MqttTopicHandlerFactory {

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
    private final DefaultHandler defaultHandler;
    private final SecretKeyHandler secretKeyHandler;
    private final AddSubDeviceHandler addSubDeviceHandler;
    private final DeleteSubDeviceHandler deleteSubDeviceHandler;
    private final UpdateSubDeviceHandler updateSubDeviceHandler;
    private final QueryDeviceHandler queryDeviceHandler;
    private final DeviceDatasHandler deviceDatasHandler;
    private final CommandResponseHandler commandResponseHandler;
    private final TimeSyncRequestHandler timeSyncRequestHandler;
    private final OtaCommandResponseHandler otaCommandResponseHandler;
    private final OtaPullHandler otaPullHandler;
    private final OtaReportHandler otaReportHandler;
    private final OtaReadResponseHandler otaReadResponseHandler;
    private final ChatgptRequestHandler chatgptRequestHandler;
    private List<TopicHandlerEntry> topicHandlerEntries;

    public MqttTopicHandlerFactory(DefaultHandler defaultHandler, SecretKeyHandler secretKeyHandler,
                                   AddSubDeviceHandler addSubDeviceHandler, DeleteSubDeviceHandler deleteSubDeviceHandler,
                                   UpdateSubDeviceHandler updateSubDeviceHandler, QueryDeviceHandler queryDeviceHandler, DeviceDatasHandler deviceDatasHandler,
                                   CommandResponseHandler commandResponseHandler, OtaCommandResponseHandler otaCommandResponseHandler, OtaPullHandler otaPullHandler,
                                   OtaReportHandler otaReportHandler, TimeSyncRequestHandler timeSyncRequestHandler, OtaReadResponseHandler otaReadResponseHandler,
                                   ChatgptRequestHandler chatgptRequestHandler) {
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
    public TopicHandler findMatchingHandler(String topic) {
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
        private final TopicHandler handler;

        public TopicHandlerEntry(Pattern pattern, TopicHandler handler) {
            this.pattern = pattern;
            this.handler = handler;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public TopicHandler getHandler() {
            return handler;
        }
    }
}