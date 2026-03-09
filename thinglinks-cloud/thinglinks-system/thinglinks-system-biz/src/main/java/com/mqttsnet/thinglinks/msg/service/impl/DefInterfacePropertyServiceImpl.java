package com.mqttsnet.thinglinks.msg.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.msg.entity.DefInterfaceProperty;
import com.mqttsnet.thinglinks.msg.manager.DefInterfacePropertyManager;
import com.mqttsnet.thinglinks.msg.service.DefInterfacePropertyService;
import com.mqttsnet.thinglinks.msg.vo.save.DefInterfacePropertyBatchSaveVO;
import com.mqttsnet.thinglinks.msg.vo.save.DefInterfacePropertySaveVO;
import com.mqttsnet.thinglinks.msg.vo.update.DefInterfacePropertyUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 接口属性
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-04 15:51:37
 * @create [2022-07-04 15:51:37] [mqttsnet]
 */
@DS(DsConstant.DEFAULTS)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DefInterfacePropertyServiceImpl extends SuperServiceImpl<DefInterfacePropertyManager, Long, DefInterfaceProperty> implements DefInterfacePropertyService {
    @Override
    public Map<String, Object> listByInterfaceId(Long id) {
        return superManager.listByInterfaceId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchSave(DefInterfacePropertyBatchSaveVO saveVO) {
        List<DefInterfacePropertySaveVO> insertRecords = saveVO.getInsertRecords();
        List<DefInterfacePropertyUpdateVO> updateRecords = saveVO.getUpdateRecords();
        List<DefInterfacePropertyUpdateVO> removeRecords = saveVO.getRemoveRecords();
        List<DefInterfacePropertyUpdateVO> pendingRecords = saveVO.getPendingRecords();
        Set<String> insertKeys = insertRecords.stream().map(DefInterfacePropertySaveVO::getKey).collect(Collectors.toSet());
        Set<String> updateKeys = updateRecords.stream().map(DefInterfacePropertyUpdateVO::getKey).collect(Collectors.toSet());
        if (updateKeys.size() + insertKeys.size() != insertRecords.size() + updateRecords.size()) {
            throw BizException.wrap("参数健重复");
        }

        List<Long> removeIdList = removeRecords.stream().map(DefInterfacePropertyUpdateVO::getId).toList();
        superManager.removeByIds(removeIdList);
        List<Long> pendingList = pendingRecords.stream().map(DefInterfacePropertyUpdateVO::getId).toList();
        superManager.removeByIds(pendingList);

        List<DefInterfaceProperty> updateList = BeanUtil.copyToList(updateRecords, DefInterfaceProperty.class);
        superManager.updateBatchById(updateList);

        List<DefInterfaceProperty> saveList = BeanUtil.copyToList(insertRecords, DefInterfaceProperty.class);
        superManager.saveBatch(saveList);

        return true;
    }
}


