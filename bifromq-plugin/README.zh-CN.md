<div align="center">

<a href="https://mqttsnet.com"><img src="../docs/images/logo.png" alt="ThingLinks" width="160"></a>

# bifromq-plugin

**ThingLinks 物联网平台 — BifroMQ MQTT Broker 插件库**

[English](README.md) | 简体中文 | [日本語](README.ja.md) | [한국어](README.ko.md)

[![JDK](https://img.shields.io/badge/JDK-17+-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![BifroMQ](https://img.shields.io/badge/BifroMQ-3.3.5-blue?style=flat-square)](https://github.com/baidu/bifromq)

</div>

---

## 简介

`bifromq-plugin` 是 [ThingLinks](https://github.com/mqttsnet/thinglinks) 多租户 SaaS 云物联网平台的 BifroMQ MQTT Broker 插件库，通过 BifroMQ 插件机制实现自定义业务与 ThingLinks IoT 系统的运行时集成。

- [部署使用教程](https://mqttsnet.yuque.com/trgbro/thinglinks)
- [BifroMQ 官方 Wiki](https://bifromq.io/zh-Hans/docs/plugin/plugin/)

## 插件列表

| 插件 | 说明 |
|------|------|
| `bifromq-auth-provider-plugin` | MQTT 客户端认证授权（用户名密码、证书、ACL 访问控制） |
| `bifromq-event-collector-plugin` | BifroMQ 事件采集、过滤及性能监控 |
| `bifromq-resource-throttler-plugin` | 多租户资源分配与限流 |
| `bifromq-setting-provider-plugin` | 运行时动态租户级别配置 |

## 环境要求

- **JDK** 17+
- **Maven** 3.6+
- **BifroMQ** 3.3.5（服务端版本必须与插件版本一致）

## 构建

```bash
# 构建所有插件
mvn clean package -DskipTests

# 构建单个插件
cd bifromq-auth-provider-plugin && mvn clean package -DskipTests
```

构建产物生成在各插件的 `target/` 目录中，上传至 BifroMQ 插件目录即可部署。

## 开源协议

本项目是 [ThingLinks](https://github.com/mqttsnet/thinglinks) 的子模块，协议遵循主项目 [LICENSE](../LICENSE) 和 [LICENSE-COMMERCIAL](../LICENSE-COMMERCIAL)。

Copyright &copy; 2019-present [MqttsNet](https://mqttsnet.com)
