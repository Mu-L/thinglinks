package com.mqttsnet.thinglinks.common.mq;

/**
 * @Description: Rabbitmq 消费者常量（队列、交换机等信息）
 * @Author: ShiHuan SUN
 * @E-mail: 13733918655@163.com
 * @Website: https://mqttsnet.com
 * @CreateDate: 2022/4/15$ 15:53$
 * @UpdateUser: ShiHuan SUN
 * @UpdateDate: 2022/4/15$ 15:53$
 * @UpdateRemark: 修改内容
 * @Version: V1.0
 */
public interface RabbitmqConsumerConstant {

    interface Mqs {

        /**
         * MQS MQTT Broker 监听主题
         */
        interface MqttInfra {
            // ================== 交换机定义 ==================
            interface Exchanges {
                /**
                 * MQTT主题交换机 (TOPIC类型)
                 */
                String MQTT_TOPIC_EXCHANGE = "mqtt.topic.exchange";
            }

            // ================== 队列定义 ==================
            interface Queues {
                /**
                 * MQTT消息处理队列（持久化）
                 */
                String MQTT_MESSAGE_QUEUE = "mqtt.message.queue";
            }

            // ================== 路由键定义 ==================
            interface RouteKeys {
                /**
                 * MQTT消息路由键通配符
                 */
                String MQTT_MESSAGE_PATTERN = "mqtt.message.#";
            }

        }


        /**
         * WebSocket集群 下文信息
         */
        interface WebSocketClusterInfra {

            // ================== 交换机定义 ==================
            interface Exchanges {
                /**
                 * WebSocket集群事件交换机 (FANOUT类型)
                 */
                String WEBSOCKET_EVENTS_EXCHANGE = "websocket.cluster.events";

                /**
                 * 设备指令消息交换机 (FANOUT类型)
                 */
                String DEVICE_DIRECT_EXCHANGE = "websocket.device.command";
            }

            // ================== 队列定义 ==================
            interface Queues {
                // 集群状态队列（所有节点都需要监听）
                String WEBSOCKET_CLUSTER_STATUS_QUEUE = "websocket.cluster.status.queue";

                // 设备连接队列
                String WEBSOCKET_DEVICE_CONNECT_QUEUE = "websocket.device.connect.queue.{instanceId}";

                // 指令下发队列（所有节点都需要监听）
                String DEVICE_COMMAND_QUEUE = "websocket.device.command.queue";
            }

            // ================== 路由键定义 ==================
            interface RouteKeys {
                // 集群事件路由
                String CLUSTER_INSTANCE_UP = "cluster.instance.up";
                String CLUSTER_INSTANCE_DOWN = "cluster.instance.down";

                // 设备连接路由
                String DEVICE_CONNECTED = "device.connected.{productKey}";
                String DEVICE_DISCONNECTED = "device.disconnected.{productKey}";

                // 指令下发路由
                String DEVICE_COMMAND_SEND = "device.command.send";
            }

        }

        interface Link {


        }


        interface Rule {


        }


    }


}