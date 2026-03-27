package com.mqttsnet.thinglinks.ws;


import com.mqttsnet.basic.utils.ArgumentAssert;

import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Web Socket 主题(被观察者)
 *
 * @author mqttsnet
 * @date 2024/9/20 14:59
 */
public class WebSocketSubject extends Observable {

    /**
     * subject键值
     */
    private final String principal;

    private WebSocketSubject(String principal) {
        this.principal = principal;
    }

    public String getPrincipal() {
        return principal;
    }

    /**
     * 通知客户端
     *
     * @param rawData 数据
     * @author mqttsnet
     * @date 2021/12/29 5:18 下午
     * @create [2021/12/29 5:18 下午 ] [mqttsnet] [初始创建]
     */
    public void notify(Object rawData) {
        super.setChanged();
        super.notifyObservers(rawData);
    }

    public static class Holder {
        private static final Map<String, WebSocketSubject> subjects = new ConcurrentHashMap<>();

        public static Map<String, WebSocketSubject> getSubject() {
            return subjects;
        }

        public static WebSocketSubject getSubject(Object principal) {
            ArgumentAssert.notNull(principal, "principal 不能为空");
            if (subjects.containsKey(principal.toString())) {
                return subjects.get(principal.toString());
            }

            WebSocketSubject subject = new WebSocketSubject(principal.toString());
            subjects.put(principal.toString(), subject);
            return subject;
        }
    }

}

