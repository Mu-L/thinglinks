<template>
  <div style="padding: 30px">
    <div v-for="(path, name) in services" :key="name" style="margin-bottom: 10px;">
      {{ name }}:
      <a :href="buildUrl(path)" target="_blank">
        点击访问druid
      </a>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      services: {
        "thinglinks-oauth-server":  "oauth",
        "thinglinks-generator-server":  "generator",
        "thinglinks-view-server":  "view",
        "thinglinks-system-server":  "system",
        "thinglinks-base-executor":  "base-executor",
        "thinglinks-mqs-server":  "mqs",
        "thinglinks-broker-server":  "broker",
        "thinglinks-iot-executor":  "iot-executor",
        "thinglinks-link-server":  "link",
        "thinglinks-tds-server":  "tds",
        "thinglinks-monitor-server":  "monitor",
        "thinglinks-rule-server":  "rule",
        "thinglinks-base-server":  "base"
        // ... 其他服务
      }
    };
  },
  methods: {
    buildUrl(path) {
      // 解析环境变量中的JSON字符串
      const proxyConfig = JSON.parse(import.meta.env.VITE_PROXY);
      
      // 查找API代理配置
      const apiProxy = proxyConfig.find(entry => entry[0] === '/api');

      // 如果找到API代理配置，使用它的值；否则使用一个默认值
      const apiUrl = apiProxy ? apiProxy[1] : 'https://test-gateway.thinglinks.mqttsnet.com';

      return `${apiUrl}/${path}/druid/index.html`;
    }
  }
};
</script>
