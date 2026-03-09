package com.mqttsnet.thinglinks.common.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.util.StringUtils;

/**
 * Kafka生产者配置类
 * <p>
 * 使用Spring Boot自动配置，支持所有标准Kafka配置属性
 * 新增属性时只需在application.yml中配置，无需修改代码
 *
 * @program: thinglinks-cloud
 * @description: Kafka生产者配置，基于Spring Boot自动配置
 * @author: mqttsnet
 * @email: mqttsnet@163.com
 * @date: 2023-06-18 11:28
 */
@Slf4j
@Configuration
public class KafkaProviderConfig {

    private final KafkaProperties kafkaProperties;

    /**
     * 构造函数注入KafkaProperties
     * Spring Boot会自动读取application.yml中的spring.kafka.*配置
     */
    public KafkaProviderConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
        log.info("KafkaProviderConfig初始化完成，bootstrap-servers: {}",
                kafkaProperties.getBootstrapServers());
    }

    /**
     * 生产者工厂Bean
     * 使用Spring Boot自动配置的生产者属性
     *
     * @return ProducerFactory实例
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        log.info("开始创建Kafka生产者工厂...");

        // 使用Spring Boot自动配置的生产者属性
        DefaultKafkaProducerFactory<String, String> factory =
                new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());

        // 开启事务（如果配置了transaction-id-prefix）
        String transactionIdPrefix = kafkaProperties.getProducer().getTransactionIdPrefix();
        if (StringUtils.hasText(transactionIdPrefix)) {
            factory.setTransactionIdPrefix(transactionIdPrefix);
            log.info("Kafka事务已启用，事务ID前缀: {}", transactionIdPrefix);
        } else {
            log.info("Kafka事务未启用");
        }

        log.info("Kafka生产者工厂创建完成");
        return factory;
    }

    /**
     * Kafka事务管理器Bean
     * 只有在开启事务时才会创建
     *
     * @param producerFactory 生产者工厂
     * @return KafkaTransactionManager实例
     */
    @Bean
    @ConditionalOnMissingBean
    public KafkaTransactionManager<String, String> kafkaTransactionManager(
            ProducerFactory<String, String> producerFactory) {
        // 只有在配置了事务ID前缀时才创建事务管理器
        if (StringUtils.hasText(kafkaProperties.getProducer().getTransactionIdPrefix())) {
            log.info("创建Kafka事务管理器");
            return new KafkaTransactionManager<>(producerFactory);
        }
        log.info("未配置事务ID前缀，跳过Kafka事务管理器创建");
        return null;
    }

    /**
     * Kafka模板Bean
     * 用于发送消息到Kafka
     *
     * @param producerFactory 生产者工厂
     * @return KafkaTemplate实例
     */
    @Bean
    public KafkaTemplate<String, String> thingLinksProKafkaTemplate(
            ProducerFactory<String, String> producerFactory) {
        log.info("开始创建KafkaTemplate...");

        KafkaTemplate<String, String> template = new KafkaTemplate<>(producerFactory);

        // 设置默认主题（可选）
        String defaultTopic = kafkaProperties.getTemplate().getDefaultTopic();
        if (StringUtils.hasText(defaultTopic)) {
            template.setDefaultTopic(defaultTopic);
            log.info("Kafka默认主题已设置: {}", defaultTopic);
        }

        log.info("KafkaTemplate创建完成");
        return template;
    }
}
