package com.mqttsnet.thinglinks.device.service.group.impl;

import java.util.Collection;
import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.TreeUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.device.entity.group.DeviceGroup;
import com.mqttsnet.thinglinks.device.manager.group.DeviceGroupManager;
import com.mqttsnet.thinglinks.device.service.group.DeviceGroupRelService;
import com.mqttsnet.thinglinks.device.service.group.DeviceGroupService;
import com.mqttsnet.thinglinks.device.vo.query.group.DeviceGroupPageQuery;
import com.mqttsnet.thinglinks.device.vo.result.group.DeviceGroupResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 设备分组表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-06-19 18:05:14
 * @create [2025-06-19 18:05:14] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class DeviceGroupServiceImpl extends SuperServiceImpl<DeviceGroupManager, Long, DeviceGroup> implements DeviceGroupService {

    private final DeviceGroupRelService deviceGroupRelService;


    @Override
    public List<DeviceGroupResultVO> findTree(DeviceGroupPageQuery query) {
        List<DeviceGroup> list = superManager.list(Wraps.<DeviceGroup>lbQ().orderByAsc(DeviceGroup::getSortValue));
        return BeanPlusUtil.toBeanList(TreeUtil.buildTree(list), DeviceGroupResultVO.class);
    }

    @Override
    public List<DeviceGroupResultVO> getDeviceGroupResultVOList(DeviceGroupPageQuery query) {
        List<DeviceGroup> list = superManager.getList(query);
        return BeanPlusUtil.toBeanList(list, DeviceGroupResultVO.class);
    }

    @Override
    public boolean removeByIds(Collection<Long> idList) {
        boolean flag = super.removeByIds(idList);
        if (flag) {
            // 删除分组资源
            deviceGroupRelService.removeByGroupIds(idList);
        }
        return flag;
    }
}


