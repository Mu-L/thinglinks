package com.mqttsnet.thinglinks.base.service.system;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.base.entity.system.BaseOperationLog;
import com.mqttsnet.thinglinks.base.vo.result.system.BaseOperationLogResultVO;

import java.time.LocalDateTime;

/**
 * <p>
 * 业务接口
 * 操作日志
 * </p>
 *
 * @author mqttsnet
 * @date 2021-11-08
 */
public interface BaseOperationLogService extends SuperService<Long, BaseOperationLog> {
    /**
     * 清理日志
     *
     * @param clearBeforeTime 多久之前的
     * @param clearBeforeNum  多少条
     * @return 是否成功
     */
    boolean clearLog(LocalDateTime clearBeforeTime, Integer clearBeforeNum);


    /**
     * 根据id查询详情
     *
     * @param id id
     * @return com.mqttsnet.thinglinks.base.vo.result.system.BaseOperationLogResultVO
     * @author mqttsnet
     * @date 2022/10/13 10:31 AM
     * @create [2022/10/13 10:31 AM ] [mqttsnet] [初始创建]
     */
    BaseOperationLogResultVO getDetail(Long id);
}
