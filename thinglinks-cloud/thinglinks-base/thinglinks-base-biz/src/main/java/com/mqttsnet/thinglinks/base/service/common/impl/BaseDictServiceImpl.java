package com.mqttsnet.thinglinks.base.service.common.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.model.cache.CacheHashKey;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.base.entity.common.BaseDict;
import com.mqttsnet.thinglinks.base.manager.common.BaseDictManager;
import com.mqttsnet.thinglinks.base.service.common.BaseDictService;
import com.mqttsnet.thinglinks.base.vo.save.common.BaseDictSaveVO;
import com.mqttsnet.thinglinks.base.vo.update.common.BaseDictUpdateVO;
import com.mqttsnet.thinglinks.common.cache.base.common.BaseDictCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DefValConstants;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.model.enumeration.system.DictClassifyEnum;
import com.mqttsnet.thinglinks.system.entity.system.DefDict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 字典
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-08
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@DS(DsConstant.BASE_TENANT)
public class BaseDictServiceImpl extends SuperServiceImpl<BaseDictManager, Long, BaseDict> implements BaseDictService {

    private final CachePlusOps cachePlusOps;

    @Override
    public boolean checkByKey(String key, Long id) {
        ArgumentAssert.notEmpty(key, "请填写字典标识");
        return superManager.count(Wraps.<BaseDict>lbQ().eq(BaseDict::getKey, key)
                .eq(BaseDict::getParentId, DefValConstants.PARENT_ID).ne(BaseDict::getId, id)) > 0;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public <SaveVO> BaseDict save(SaveVO saveVO) {
        BaseDictSaveVO dictSaveVO = (BaseDictSaveVO) saveVO;
        ArgumentAssert.isFalse(checkByKey(dictSaveVO.getKey(), null), "字典标识已存在");
        BaseDict dict = BeanPlusUtil.toBean(dictSaveVO, BaseDict.class);
        dict.setClassify(DictClassifyEnum.BUSINESS.getCode());
        dict.setParentId(DefValConstants.PARENT_ID);
        superManager.save(dict);
        return dict;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteDict(List<Long> ids) {
        List<BaseDict> list = superManager.listByIds(ids);
        if (CollUtil.isEmpty(list)) {
            return false;
        }
        if (CollUtil.isNotEmpty(ids)) {
            superManager.remove(Wraps.<BaseDict>lbQ().in(BaseDict::getParentId, ids));
        }
        boolean flag = removeByIds(ids);
        CacheHashKey[] typeKeys = list.stream().map(type -> BaseDictCacheKeyBuilder.builder(type.getKey())).toArray(CacheHashKey[]::new);
        cachePlusOps.del(typeKeys);
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <UpdateVO> BaseDict updateById(UpdateVO updateVO) {
        BaseDictUpdateVO dictUpdateVO = (BaseDictUpdateVO) updateVO;
        BaseDict dict = BeanPlusUtil.toBean(dictUpdateVO, BaseDict.class);
        dict.setParentId(DefValConstants.PARENT_ID);
        superManager.updateById(dict);
        return dict;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDictAndItem(DefDict defDict, List<DefDict> itemList) {
        BaseDict baseDict = BeanUtil.toBean(defDict, BaseDict.class);
        boolean flag = superManager.save(baseDict);
        if (!itemList.isEmpty()) {
            List<BaseDict> baseItemList = new ArrayList<>();
            itemList.forEach(item -> {
                BaseDict baseItem = new BaseDict();
                BeanUtil.copyProperties(item, baseItem);
                baseItem.setParentId(baseDict.getId());
                baseItemList.add(baseItem);
            });

            superManager.saveBatch(baseItemList);

            // 删除
            cachePlusOps.del(BaseDictCacheKeyBuilder.builder(defDict.getKey()));

            baseItemList.forEach(item -> {
                CacheHashKey hashKey = BaseDictCacheKeyBuilder.builder(item.getParentKey(), item.getKey());
                cachePlusOps.hSet(hashKey, item);
            });
        }
        return flag;
    }
}
