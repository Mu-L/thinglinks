package com.mqttsnet.thinglinks.video.dto.media.zlm;

import lombok.Data;

/**
 * -----------------------------------------------------------------------------
 * File Name: ZlmRestfulResult
 * -----------------------------------------------------------------------------
 * Description:
 * Zlm响应结果
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/7/6       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/7/6 17:27
 */
@Data
public class ZlmRestfulResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public ZlmRestfulResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ZlmRestfulResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * Checks if the response is successful based on the code.
     *
     * @return true if code is 0, false otherwise.
     */
    public boolean isSuccess() {
        return this.code != null && this.code == 0;
    }
}
