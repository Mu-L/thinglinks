package com.mqttsnet.thinglinks.common.config.kafka;

import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * Kafka消费者配置类
 * <p>
 * 使用Spring Boot自动配置，支持所有标准Kafka配置属性
 * 新增属性时只需在application.yml中配置，无需修改代码
 *
 * @program: thinglinks-cloud
 * @description: Kafka消费者配置，基于Spring Boot自动配置
 * @author: mqttsnet
 * @email: mqttsnet@163.com
 * @date: 2023-06-18 11:29
 */
@Slf4j
@Configuration
public class KafkaConsumerConfig {

    private final KafkaProperties kafkaProperties;

    /**
     * 构造函数注入KafkaProperties
     * Spring Boot会自动读取application.yml中的spring.kafka.*配置
     */
    public KafkaConsumerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
        log.info("KafkaConsumerConfig初始化完成，bootstrap-servers: {}, group-id: {}",
                kafkaProperties.getBootstrapServers(),
                kafkaProperties.getConsumer().getGroupId());
    }

    /**
     * 消费者工厂Bean
     * 使用Spring Boot自动配置的消费者属性
     *
     * @return ConsumerFactory实例
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        log.info("开始创建Kafka消费者工厂...");

        // 使用Spring Boot自动配置的消费者属性
        // 配置消费者的 Json 反序列化的可信赖包，反序列化实体类需要（已在properties中配置）
        ConsumerFactory<String, String> factory =
                new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());

        log.info("Kafka消费者工厂创建完成");
        return factory;
    }

    /**
     * Kafka监听器容器工厂Bean
     * 配置批量监听、手动确认等高级特性
     *
     * @param consumerFactory 消费者工厂
     * @return KafkaListenerContainerFactory实例
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {

        log.info("开始创建Kafka监听器容器工厂...");

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        // 设置消费者工厂
        factory.setConsumerFactory(consumerFactory);

        // 在侦听器容器中运行的线程数，一般设置为机器数*分区数
        Integer concurrency = kafkaProperties.getListener().getConcurrency();
        if (concurrency != null && concurrency > 0) {
            factory.setConcurrency(concurrency);
            log.info("Kafka监听器并发数: {}", concurrency);
        }

        // 消费监听接口监听的主题不存在时，默认会报错，所以设置为false忽略错误
        boolean missingTopicsFatal = kafkaProperties.getListener().isMissingTopicsFatal();
        factory.setMissingTopicsFatal(missingTopicsFatal);
        log.info("主题不存在时是否报错: {}", missingTopicsFatal);

        // 获取容器属性进行配置
        ContainerProperties containerProperties = factory.getContainerProperties();

        // 自动提交关闭，需要设置手动消息确认
        ContainerProperties.AckMode ackMode = kafkaProperties.getListener().getAckMode();
        containerProperties.setAckMode(ackMode);
        log.info("消息确认模式: {}", ackMode);

        // 设置poll超时时间（Duration类型）
        Duration pollTimeout = kafkaProperties.getListener().getPollTimeout();
        if (pollTimeout != null) {
            containerProperties.setPollTimeout(pollTimeout.toMillis());
            log.info("Kafka Poll超时时间: {}ms", pollTimeout.toMillis());
        }

        // 设置为批量监听，需要用List接收
        // 根据配置的监听器类型决定是否启用批量监听
        KafkaProperties.Listener.Type listenerType = kafkaProperties.getListener().getType();
        if (listenerType == KafkaProperties.Listener.Type.BATCH) {
            factory.setBatchListener(true);
            log.info("Kafka批量监听已启用");
        } else {
            factory.setBatchListener(false);
            log.info("Kafka单条监听已启用");
        }

        log.info("Kafka监听器容器工厂创建完成");
        return factory;
    }
}
