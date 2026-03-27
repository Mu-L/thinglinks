package com.mqttsnet.thinglinks.base.manager.common.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.cache.redis2.CacheResult;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.database.mybatis.conditions.query.LbQueryWrap;
import com.mqttsnet.basic.echo.properties.EchoProperties;
import com.mqttsnet.basic.model.cache.CacheHashKey;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.utils.CollHelper;
import com.mqttsnet.thinglinks.base.entity.common.BaseDict;
import com.mqttsnet.thinglinks.base.manager.common.BaseDictManager;
import com.mqttsnet.thinglinks.base.mapper.common.BaseDictMapper;
import com.mqttsnet.thinglinks.common.cache.base.common.BaseDictCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DefValConstants;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.system.vo.result.system.DefDictItemResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * 基础字典
 *
 * @author mqttsnet
 * @date 2021/10/10 23:03
 */
@RequiredArgsConstructor
@Service
public class BaseDictManagerImpl extends SuperManagerImpl<BaseDictMapper, BaseDict> implements BaseDictManager {

    private final CachePlusOps cachePlusOps;
    private final EchoProperties ips;

    @Override
    @DS(DsConstant.BASE_TENANT)
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Map<Serializable, BaseDict> findByIds(Set<Serializable> dictKeys) {
        if (dictKeys.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Serializable, BaseDict> codeValueMap = MapUtil.newHashMap();
        dictKeys.forEach(dictKey -> {
            Function<CacheKey, Map<String, BaseDict>> fun = ck -> {
                LbQueryWrap<BaseDict> wrap = Wraps.<BaseDict>lbQ().eq(BaseDict::getParentKey, dictKey).eq(BaseDict::getState, true)
                        .orderByAsc(BaseDict::getSortValue);
                List<BaseDict> list = baseMapper.selectList(wrap);
                if (CollUtil.isNotEmpty(list)) {
                    return CollHelper.uniqueIndex(list, BaseDict::getKey, item -> item);
                } else {
                    return MapBuilder.<String, BaseDict>create().put(DefValConstants.DICT_NULL_VAL_KEY, new BaseDict()).build();
                }
            };
            Map<String, CacheResult<BaseDict>> map = cachePlusOps.hGetAll(BaseDictCacheKeyBuilder.builder(dictKey), fun);
            map.forEach((itemKey, itemName) -> {
                if (!DefValConstants.DICT_NULL_VAL_KEY.equals(itemKey)) {
                    codeValueMap.put(StrUtil.join(ips.getDictSeparator(), dictKey, itemKey), itemName.getValue());
                }
            });
        });
        return codeValueMap;
    }

    @Override
    @DS(DsConstant.BASE_TENANT)
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Map<String, List<DefDictItemResultVO>> findDictMapItemListByKey(List<String> dictKeys) {
        if (CollUtil.isEmpty(dictKeys)) {
            return Collections.emptyMap();
        }
        LbQueryWrap<BaseDict> query = Wraps.<BaseDict>lbQ().in(BaseDict::getParentKey, dictKeys).orderByAsc(BaseDict::getSortValue);
        List<BaseDict> list = super.list(query);
        List<DefDictItemResultVO> voList = BeanUtil.copyToList(list, DefDictItemResultVO.class);

        //key 是类型
        return voList.stream().collect(groupingBy(DefDictItemResultVO::getParentKey, LinkedHashMap::new, toList()));
    }

}
