package com.mqttsnet.thinglinks.video.dto.media.zlm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;


/**
 * 流地址信息
 *
 * @author xiaonannet
 */
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Schema(description = "流地址信息")
public class StreamUrl implements Serializable, Cloneable {

    @Schema(description = "协议")
    private String protocol;

    @Schema(description = "主机地址")
    private String host;

    @Schema(description = "端口")
    private Integer port = -1;

    @Schema(description = "定位位置")
    private String file;

    @Schema(description = "拼接后的地址")
    private String url;

    public StreamUrl(String protocol, String host, Integer port, String file) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.file = file;
        // 生成URL
        this.url = generateUrl();
    }

    private String generateUrl() {
        return Optional.ofNullable(protocol)
                .flatMap(p -> Optional.ofNullable(host)
                        .flatMap(h -> port != -1
                                ? Optional.of(String.format("%s://%s:%d/%s", p, h, port, file))
                                : Optional.empty()))
                .orElse(null);
    }

    @Override
    public StreamUrl clone() throws CloneNotSupportedException {
        return (StreamUrl) super.clone();
    }

    // Getters and Setters for all fields

    public void setProtocol(String protocol) {
        this.protocol = protocol;
        // Update URL
        this.url = generateUrl();
    }

    public void setHost(String host) {
        this.host = host;
        // Update URL
        this.url = generateUrl();
    }

    public void setPort(Integer port) {
        this.port = port;
        // Update URL
        this.url = generateUrl();
    }

    public void setFile(String file) {
        this.file = file;
        // Update URL
        this.url = generateUrl();
    }

}
