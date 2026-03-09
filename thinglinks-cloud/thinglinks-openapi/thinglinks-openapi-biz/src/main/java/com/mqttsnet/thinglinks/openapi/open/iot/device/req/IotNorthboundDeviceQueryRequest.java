package com.mqttsnet.thinglinks.openapi.open.iot.device.req;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * 北向API-查询设备信息请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IotNorthboundDeviceQueryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备标识集合
     * @mock ["DEVICE_001","DEVICE_002"]
     */
    @NotNull(message = "请填写设备标识集合")
    @Size(min = 1, max = 100, message = "设备标识集合大小必须在1到100之间")
    private List<String> deviceIds;

}
