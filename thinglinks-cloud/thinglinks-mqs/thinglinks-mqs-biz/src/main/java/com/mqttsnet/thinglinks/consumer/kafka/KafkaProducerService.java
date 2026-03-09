package com.mqttsnet.thinglinks.consumer.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

/**
 * @Description: kafka生产者
 * @Author: ShiHuan Sun
 * @E-mail: 13733918655@163.com
 * @CreateDate: 2023/01/06$ 16:02$
 * @UpdateUser: ShiHuan Sun
 * @UpdateDate: 2023/01/06$ 16:02$
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Service
@Slf4j
public class KafkaProducerService {

    @Autowired
    @Qualifier("thingLinksProKafkaTemplate")
    private KafkaTemplate thingLinksProKafkaTemplate;


    @Transactional
    public void thingLinksProKafkaTemplateSendMsg(String topic, String msg) {
        log.info("thingLinksProKafkaTemplateSendMsg sendMsg ,topic:{},msg:{}", topic, msg);
        CompletableFuture<SendResult<Integer, String>> future = thingLinksProKafkaTemplate.send(topic, msg);
        // 添加成功和异常处理
        future.thenAccept(result -> {
            // 成功回调
            log.info("消息发送成功，topic: {}, message: {}, offset: {}", topic, msg, result.getRecordMetadata().offset());
        }).exceptionally(ex -> {
            // 异常处理回调
            log.error("消息发送失败，topic: {}, message: {}, error: {}", topic, msg, ex.getMessage());
            return null;
        });
    }
}
