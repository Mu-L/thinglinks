package com.mqttsnet.thinglinks.msg.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.model.entity.system.SysUser;
import com.mqttsnet.thinglinks.msg.entity.ExtendMsg;
import com.mqttsnet.thinglinks.msg.entity.ExtendMsgTemplate;
import com.mqttsnet.thinglinks.msg.vo.result.ExtendMsgResultVO;
import com.mqttsnet.thinglinks.msg.vo.update.ExtendMsgPublishVO;
import com.mqttsnet.thinglinks.msg.vo.update.ExtendMsgSendVO;


/**
 * <p>
 * 业务接口
 * 消息
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-10 11:41:17
 * @create [2022-07-10 11:41:17] [mqttsnet]
 */
public interface ExtendMsgService extends SuperService<Long, ExtendMsg> {

    /**
     * 发送消息
     *
     * @param data        消息
     * @param msgTemplate 消息模版
     * @param sysUser     当前用户
     * @return 是否执行
     * @date 2022/10/28 4:57 PM
     * @create [2022/10/28 4:57 PM ] [mqttsnet] [初始创建]
     */

    Boolean send(ExtendMsgSendVO data, ExtendMsgTemplate msgTemplate, SysUser sysUser);


    /**
     * 定时发布通知
     *
     * @param msgId 消息id
     */
    void publishNotice(Long msgId);


    /**
     * 发布消息
     *
     * @param data
     * @param sysUser
     * @return
     * @author mqttsnet
     * @date 2022/10/28 4:57 PM
     * @create [2022/10/28 4:57 PM ] [mqttsnet] [初始创建]
     */
    Boolean publish(ExtendMsgPublishVO data, SysUser sysUser);

    /**
     * 查询消息详情
     *
     * @param id
     * @return
     */
    ExtendMsgResultVO getResultById(Long id);
}


