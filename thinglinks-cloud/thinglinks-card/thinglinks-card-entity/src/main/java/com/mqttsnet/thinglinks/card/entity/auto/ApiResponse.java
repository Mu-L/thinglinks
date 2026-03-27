package com.mqttsnet.thinglinks.card.entity.auto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 运营商认证返回结果
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    /**
     * 移动
     */
    private String status;
    private String message;
    private List<Result> result;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private String token;
        private String ttl;
    }

    /**
     * 联通
     */
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Unicom {
        private String token;
        private String transId;
        private String timestamp;
    }

    /**
     * 电信
     */
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Telecommunication {
        private String token;
        private String transId;
        private String timestamp;
    }
}