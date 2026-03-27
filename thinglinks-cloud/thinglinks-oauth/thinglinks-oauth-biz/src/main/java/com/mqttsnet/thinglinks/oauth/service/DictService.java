package com.mqttsnet.thinglinks.oauth.service;

import com.mqttsnet.basic.interfaces.echo.LoadService;
import com.mqttsnet.thinglinks.model.vo.result.Option;
import com.mqttsnet.thinglinks.oauth.vo.param.CodeQueryVO;
import com.mqttsnet.thinglinks.system.vo.result.system.DefDictItemResultVO;
import com.mqttsnet.thinglinks.system.vo.result.system.DefDictResultVO;

import java.util.List;
import java.util.Map;

/**
 * 字典查询服务
 *
 * @author mqttsnet
 * @date 2021/10/7 13:27
 */
public interface DictService extends LoadService {
    List<DefDictResultVO> findAll();

    Map<String, List<DefDictItemResultVO>> findDictItemByType(List<String> query);

    void syncEnumToDict();

    /**
     * 根据字典key查询字典条目
     * <p>
     * 1. 先查询租户自己的字典项。
     * 2. 若不存在，则查询系统默认的字典项
     *
     * @param dictKeys 字典key
     * @return key： 字典key  value: item list
     */
    @Deprecated
    Map<String, List<DefDictItemResultVO>> findDictMapByType(List<String> dictKeys);

    @Deprecated
    List<Option> findEnumByType(CodeQueryVO type);

    @Deprecated
    Map<String, List<Option>> findEnumMapByType(List<CodeQueryVO> types);

    @Deprecated
    Map<String, List<Option>> mapOptionByDict(Map<String, List<DefDictItemResultVO>> map, List<CodeQueryVO> codeQueryVO);

    @Deprecated
    List<Option> mapOptionByDict(Map<String, List<DefDictItemResultVO>> map, CodeQueryVO codeQueryVO);
}
