package com.mqttsnet.thinglinks.card.utils.analysisUtil;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.thinglinks.card.utils.chinamobile.RequestType;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * onelink返回结果解析工具类
 *
 * @Author: shisen
 * @Date: 2024/06/27 18:23
 */
public class OnelinkDataParsingUtil {


    /**
     * 单卡本月套餐内流量使用量实时 接口
     *
     * @param jsonObject    数据
     * @param parameterType 要获取的参数类型
     * @return
     */
    public static Map<String, Object> simDataMargin(JSONObject jsonObject, String parameterType) {
        Map<String, Object> restMap = new HashMap<>();

        String result = jsonObject.getString(parameterType);
        if (result == null || result.isEmpty()) {
            return restMap;
        }

        JSONArray jsonArray = JSONArray.parseArray(result);
        if (jsonArray.isEmpty()) {
            return restMap;
        }

        JSONObject parseObject = JSONObject.parseObject(jsonArray.getString(0));
        if (parseObject == null) {
            return restMap;
        }

        // 获取result里面的参数
        for (Map.Entry<String, Object> entry : parseObject.entrySet()) {
            //跳过 "pccCode" 键
            if ("pccCode".equalsIgnoreCase(entry.getKey())) {
                continue;
            }
            // 普通参数
            restMap.put(entry.getKey(), entry.getValue());
        }
        return restMap;
    }


    /**
     * 获取返回结果,提取参数数据,适用于onelink平台接口
     *
     * @param jsonObject
     * @return
     */
    public static Map<String, Object> splitResult(JSONObject jsonObject, String requestType) {
        Map<String, Object> restMap = new HashMap<>();

        String result = jsonObject.getString("result");
        JSONArray jsonArray = JSONArray.parseArray(result);

        if (jsonArray.isEmpty()) {
            return restMap;
        }

        JSONObject firstJsonObject = JSONObject.parseObject(jsonArray.getString(0));

        for (Map.Entry<String, Object> entry : firstJsonObject.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof JSONArray && (RequestType.GROUP_DATA_MARGIN.equalsIgnoreCase(requestType) || RequestType.API23M08.equalsIgnoreCase(requestType))) {
                saveResult(entry.getKey(), (JSONArray) value, restMap);
            } else {
                restMap.put(entry.getKey(), value);
            }
        }
        return restMap;
    }


    /**
     * 保存提取的数据,适用于onelink平台接口
     *
     * @param jsonArray
     * @param map
     */
    public static void saveResult(String key, JSONArray jsonArray, Map<String, Object> map) {
        if (jsonArray.isEmpty()) {
            map.put(key, jsonArray);
        } else {
            for (Object obj : jsonArray) {
                JSONObject jsonObject = JSONObject.parseObject(obj.toString());
                map.putAll(jsonObject.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            }
        }
    }

}
