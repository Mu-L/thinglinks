package com.mqttsnet.thinglinks.sop.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mqttsnet.thinglinks.sop.manager.SopIsvInfoManager;
import com.mqttsnet.thinglinks.sop.manager.SopPermIsvGroupManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.sop.entity.SopPermGroupPermission;
import com.mqttsnet.thinglinks.sop.manager.SopPermGroupPermissionManager;
import com.mqttsnet.thinglinks.sop.service.SopPermGroupPermissionService;
import com.mqttsnet.thinglinks.sop.vo.save.SopPermGroupPermissionSaveVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 组权限表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SopPermGroupPermissionServiceImpl extends SuperServiceImpl<SopPermGroupPermissionManager, Long, SopPermGroupPermission> implements SopPermGroupPermissionService {

    private final SopIsvInfoManager sopIsvInfoManager;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public <SaveVO> SopPermGroupPermission save(SaveVO saveVO) {
        SopPermGroupPermissionSaveVO param = (SopPermGroupPermissionSaveVO) saveVO;
        Long groupId = param.getGroupId();
        List<Long> apiIdList = param.getApiIdList();
        if (CollectionUtils.isEmpty(apiIdList)) {
            return null;
        }

        List<SopPermGroupPermission> existList = superManager.list(Wrappers.<SopPermGroupPermission>lambdaQuery().eq(SopPermGroupPermission::getGroupId, groupId));
        List<Long> existApiIdList = existList.stream().map(SopPermGroupPermission::getApiId).distinct().toList();

        List<SopPermGroupPermission> saveList = apiIdList.stream()
                // 已存在的不添加
                .filter(apiId -> !existApiIdList.contains(apiId))
                .map(apiId -> {
                    SopPermGroupPermission permGroupPermission = new SopPermGroupPermission();
                    permGroupPermission.setGroupId(groupId);
                    permGroupPermission.setApiId(apiId);
                    return permGroupPermission;
                })
                .collect(Collectors.toList());
        superManager.saveBatch(saveList);

        sopIsvInfoManager.refreshIsvPerm();
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long groupId, List<Long> apiIdList) {
        boolean cnt = superManager.remove(Wrappers.<SopPermGroupPermission>lambdaQuery()
                .eq(SopPermGroupPermission::getGroupId, groupId).in(SopPermGroupPermission::getApiId, apiIdList));
        sopIsvInfoManager.refreshIsvPerm();
        return cnt;
    }
}


