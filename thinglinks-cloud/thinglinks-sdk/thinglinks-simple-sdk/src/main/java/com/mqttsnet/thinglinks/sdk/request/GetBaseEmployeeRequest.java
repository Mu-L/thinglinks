package com.mqttsnet.thinglinks.sdk.request;

import lombok.Data;

/**
 * @author mqttsnet
 */
@Data
public class GetBaseEmployeeRequest {

    /**
     * ID
     */
    private Long id;
    /**
     * 员工名称
     */
    private String name;
}
