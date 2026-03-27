package com.mqttsnet.thinglinks.card.utils.analysisUtil;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.basic.utils.StringUtils;
import com.mqttsnet.thinglinks.card.entity.sim.SimSession;

import java.util.List;


/**
 * 联通数据解析工具类
 *
 * @Author: shisen
 * @Date: 2024/06/27 19:17
 */
public class LtDataParsingUtil {


    /**
     * 解析返回结果
     *
     * @param jsonObject
     * @return
     */
    public static String splitResult(JSONObject jsonObject) {
        JSONObject jsonObjectData = JSONObject.parseObject(jsonObject.getString("data"));
        JSONArray array = jsonObjectData.getJSONArray("terminals");  //设备详情
        JSONArray sessionInfo = jsonObjectData.getJSONArray("sessionInfo"); //会话

        if (StringUtils.isNotEmpty(array)) {
            return array.getString(0);
        } else if (StringUtils.isNotEmpty(sessionInfo)) {
            List<SimSession> simSessionLists = sessionInfo.toJavaList(SimSession.class);
            simSessionLists.get(0).setStatus("01"); //联通没发现有在线属性,只能通过会话来判断是否在线  如果会话信息不为空 表示在线状态
            return JSON.toJSONString(simSessionLists.get(0));
        }
        return null;
    }
}
