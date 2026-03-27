package com.mqttsnet.thinglinks.video.dto.media.zlm;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author xiaonannet
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Schema(description = "ZLM录像下载信息")
public class ZlmMediaDownloadFileInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String httpPath;
    private String httpsPath;
    private String httpDomainPath;
    private String httpsDomainPath;
}
