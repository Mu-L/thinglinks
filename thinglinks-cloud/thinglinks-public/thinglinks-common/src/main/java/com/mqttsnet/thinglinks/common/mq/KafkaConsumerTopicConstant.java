package com.mqttsnet.thinglinks.common.mq;

/**
 * @Description: Kafka 消费者主题常量（队列）
 * @Author: ShiHuan SUN
 * @E-mail: 13733918655@163.com
 * @Website: https://mqttsnet.com
 * @CreateDate: 2022/4/15$ 15:53$
 * @UpdateUser: ShiHuan SUN
 * @UpdateDate: 2022/4/15$ 15:53$
 * @UpdateRemark: 修改内容
 * @Version: V1.0
 */
public interface KafkaConsumerTopicConstant {

    interface Mqs {

        /**
         * MQS MQTT Broker 监听主题
         */
        interface MqsMqtt {
            /**
             * MQTT设备消息监听主题——》MQTT消息——》MQS
             */
            String THINGLINKS_MQS_MQTT_MSG = "thinglinks-pro-mqs-mqttMsg";

            /**
             * 设备上线
             */
            String THINGLINKS_MQTT_CLIENT_CONNECTED_TOPIC = "mqtt.client.connected.topic";

            /**
             * 客户端设备离线
             */
            String THINGLINKS_MQTT_CLIENT_DISCONNECTED_TOPIC = "mqtt.client.disconnect.topic";

            /**
             * 服务端主动断开了与客户端的连接
             */
            String THINGLINKS_MQTT_SERVER_CONNECTED_TOPIC = "mqtt.server.disconnect.topic";

            /**
             * 设备离线
             */
            String THINGLINKS_MQTT_DEVICE_KICKED_TOPIC = "mqtt.device.kicked.topic";

            /**
             * 消息订阅
             */
            String THINGLINKS_MQTT_SUBSCRIPTION_ACKED_TOPIC = "mqtt.subscription.acked.topic";

            /**
             * 取消订阅
             */
            String THINGLINKS_MQTT_UNSUBSCRIPTION_ACKED_TOPIC = "mqtt.unsubscription.acked.topic";

            /**
             * 消息分发错误
             */
            String THINGLINKS_MQTT_DISTRIBUTION_ERROR_TOPIC = "mqtt.distribution.error.topic";

            /**
             * 消息分发
             */
            String THINGLINKS_MQTT_DISTRIBUTION_COMPLETED_TOPIC = "mqtt.distribution.completed.topic";


            /**
             * PING 请求
             */
            String THINGLINKS_MQTT_PING_REQ_TOPIC = "mqtt.ping.req.topic";

        }

        /**
         * MQS WebSocket Topics
         */
        interface MqsWebSocket {
            /**
             * WebSocket device message listening topic - WebSocket messages - MQS
             */
            String THINGLINKS_MQS_WEBSOCKET_MSG = "thinglinks-pro-mqs-websocketMsg";

            /**
             * Device online
             */
            String THINGLINKS_WEBSOCKET_CLIENT_CONNECTED_TOPIC = "websocket.client.connected.topic";

            /**
             * Client device offline
             */
            String THINGLINKS_WEBSOCKET_CLIENT_DISCONNECTED_TOPIC = "websocket.client.disconnect.topic";

            /**
             * Server device offline
             */
            String THINGLINKS_WEBSOCKET_SERVER_DISCONNECTED_TOPIC = "websocket.server.disconnect.topic";

            /**
             * Device kicked off
             */
            String THINGLINKS_WEBSOCKET_DEVICE_KICKED_TOPIC = "websocket.device.kicked.topic";

            /**
             * Message subscription acknowledgment
             */
            String THINGLINKS_WEBSOCKET_SUBSCRIPTION_ACKED_TOPIC = "websocket.subscription.acked.topic";

            /**
             * Unsubscription acknowledgment
             */
            String THINGLINKS_WEBSOCKET_UNSUBSCRIPTION_ACKED_TOPIC = "websocket.unsubscription.acked.topic";

            /**
             * Message distribution error
             */
            String THINGLINKS_WEBSOCKET_DISTRIBUTION_ERROR_TOPIC = "websocket.distribution.error.topic";

            /**
             * Message distribution completed
             */
            String THINGLINKS_WEBSOCKET_DISTRIBUTION_COMPLETED_TOPIC = "websocket.distribution.completed.topic";

            /**
             * PING request
             */
            String THINGLINKS_WEBSOCKET_PING_REQ_TOPIC = "websocket.ping.req.topic";
        }

        /**
         * MQS Tcp Topics
         */
        interface MqsTcp {
            /**
             * Tcp device message listening topic - Tcp messages - MQS
             */
            String THINGLINKS_MQS_TCP_MSG = "thinglinks-pro-mqs-tcpMsg";

            /**
             * Device online
             */
            String THINGLINKS_TCP_CLIENT_CONNECTED_TOPIC = "tcp.client.connected.topic";

            /**
             * Client device offline
             */
            String THINGLINKS_TCP_CLIENT_DISCONNECTED_TOPIC = "tcp.client.disconnect.topic";

            /**
             * Server device offline
             */
            String THINGLINKS_TCP_SERVER_DISCONNECTED_TOPIC = "tcp.server.disconnect.topic";

            /**
             * Device kicked off
             */
            String THINGLINKS_TCP_DEVICE_KICKED_TOPIC = "tcp.device.kicked.topic";

            /**
             * Message subscription acknowledgment
             */
            String THINGLINKS_TCP_SUBSCRIPTION_ACKED_TOPIC = "tcp.subscription.acked.topic";

            /**
             * Unsubscription acknowledgment
             */
            String THINGLINKS_TCP_UNSUBSCRIPTION_ACKED_TOPIC = "tcp.unsubscription.acked.topic";

            /**
             * Message distribution error
             */
            String THINGLINKS_TCP_DISTRIBUTION_ERROR_TOPIC = "tcp.distribution.error.topic";

            /**
             * Message distribution completed
             */
            String THINGLINKS_TCP_DISTRIBUTION_COMPLETED_TOPIC = "tcp.distribution.completed.topic";

            /**
             * PING request
             */
            String THINGLINKS_TCP_PING_REQ_TOPIC = "tcp.ping.req.topic";
        }
    }

    interface Link {

        /**
         * 产品服务
         */
        String THINGLINKS_PRO_PRODUCT_SERVICE_MSG = "thinglinks-pro-product-service-msg";

        /**
         * 产品服务属性
         */
        String THINGLINKS_PRO_PRODUCT_PROPERTY_MSG = "thinglinks-pro-product-property-msg";
    }

}
