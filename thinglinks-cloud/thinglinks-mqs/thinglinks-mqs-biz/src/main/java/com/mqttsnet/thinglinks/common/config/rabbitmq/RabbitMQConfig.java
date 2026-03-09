package com.mqttsnet.thinglinks.common.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ============================================================================
 * Description:
 * RabbitMQConfig
 * ============================================================================
 *
 * @author mqttsnet
 * @version 1.0.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/2/8      mqttsnet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/2/8 11:17
 */
@Slf4j
@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private Integer port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String vhost;
    @Value("${spring.rabbitmq.listener.direct.prefetch}")
    private Integer prefetchCount;
    @Value("${spring.rabbitmq.listener.direct.concurrency}")
    private Integer concurrency;
    @Value("${spring.rabbitmq.listener.direct.max-concurrency}")
    private Integer maxConcurrency;


    @Bean(name = "connectionFactory")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost(vhost);
        factory.setUsername(username);
        factory.setPassword(password);

        // 连接池参数优化
        factory.setConnectionTimeout(30000);   // TCP连接超时30秒
        factory.setRequestedHeartBeat(60);     // 心跳间隔60秒
        return factory;
    }

    /**
     * RabbitTemplate配置（生产者使用）
     * 关键特性：消息重试、确认机制
     */
    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        template.setMandatory(true); // 强制消息可路由

        // 发布确认机制（确保日志ID可跟踪）
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (correlationData != null) {
                String msgId = correlationData.getId();
                if (ack) {
                    log.info("消息到达Broker成功: {}", msgId);
                } else {
                    log.error("消息丢失: {} | 原因: {}", msgId, cause);
                }
            }
        });

        // 不可路由消息处理
        template.setReturnsCallback(returned -> {
            log.error("MQ拒绝路由消息: 交换机[{}] → 路由键[{}] | 响应码: {}",
                    returned.getExchange(),
                    returned.getRoutingKey(),
                    returned.getReplyCode());
        });

        return template;
    }

    /**
     * 消费者监听容器工厂（核心消费配置）
     * 建议：手动确认模式 + 流量控制
     */
    @Bean(name = "rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        // 消息确认模式（必须与代码中的ack/nack配合使用）
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);  //  手动确认

        // 流量控制参数
        factory.setPrefetchCount(prefetchCount);    // 每个消费者最大预取数
        factory.setConcurrentConsumers(concurrency);       // 初始消费者数量
        factory.setMaxConcurrentConsumers(maxConcurrency); // 最大并发消费者

        // 异常处理（消息被拒时是否重新入队）
        factory.setDefaultRequeueRejected(false);  // 默认不重新入队(防止死循环)
        return factory;
    }


}
