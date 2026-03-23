<div align="center">

<a href="https://mqttsnet.com"><img src="./docs/images/logo.png" alt="ThingLinks" width="200"></a>

# ThingLinks 物联网平台

**多租户 SaaS 云物联网平台 — 高性能 · 高吞吐量 · 高扩展性**

[English](README.md) | 简体中文 | [日本語](README.ja.md) | [한국어](README.ko.md)

[![JDK](https://img.shields.io/badge/JDK-17+-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025-green?style=flat-square&logo=spring)](https://spring.io/projects/spring-cloud)
[![Vue](https://img.shields.io/badge/Vue-3.x-brightgreen?style=flat-square&logo=vuedotjs)](https://vuejs.org/)
[![TDengine](https://img.shields.io/badge/TDengine-3.0+-blue?style=flat-square)](https://tdengine.com/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue?style=flat-square)](LICENSE)
[![GitHub Stars](https://img.shields.io/github/stars/mqttsnet/thinglinks?style=flat-square)](https://github.com/mqttsnet/thinglinks/stargazers)
[![GitHub Forks](https://img.shields.io/github/forks/mqttsnet/thinglinks?style=flat-square)](https://github.com/mqttsnet/thinglinks/network/members)

<br>

[![Website](https://img.shields.io/badge/官网-mqttsnet.com-blue?style=for-the-badge)](https://mqttsnet.com)
[![GitHub](https://img.shields.io/badge/GitHub-mqttsnet/thinglinks-181717?style=for-the-badge&logo=github)](https://github.com/mqttsnet/thinglinks)
[![Docs](https://img.shields.io/badge/文档-在线文档-green?style=for-the-badge)](https://mqttsnet.yuque.com/trgbro/thinglinks)

</div>

---

## 平台简介

ThingLinks 是一款企业级**多租户 SaaS 云物联网平台**，基于 Spring Cloud 微服务架构构建，具备**高性能、高吞吐量、高扩展性**的设备接入能力。单机支持**百万级并发连接**，支持插件化扩展开发和多协议适配。

## 核心特性

| 特性 | 说明 |
|------|------|
| **多租户 SaaS** | 企业级多租户架构，完整租户隔离 |
| **百万级连接** | 单节点支持百万级设备并发连接 |
| **多协议支持** | MQTT、WebSocket、TCP、UDP、CoAP、HTTP 等 |
| **设备管理** | 统一产品模型、设备全生命周期管理、子设备管理 |
| **规则引擎** | 灵活的告警规则、消息通知、数据转发（Kafka、RocketMQ、RabbitMQ、MySQL、HTTP 等） |
| **SCADA 与可视化** | 设备地理位置可视化、SCADA 组态、大屏展示 |
| **时序数据库** | TDengine — 每个设备一张表，每类设备一个超级表 |
| **插件系统** | 插件化开发，支持自定义协议和功能扩展 |
| **物联卡管理** | SIM 卡通道管理、卡片生命周期管理 |
| **流媒体** | 流媒体服务集成、视频流代理 |

## 技术栈

![Java 17+](https://img.shields.io/badge/Java-17+-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2022-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Netty](https://img.shields.io/badge/Netty-4.x-0E83CD?style=flat-square)
![Vue 3](https://img.shields.io/badge/Vue.js-3.x-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white)
![TDengine](https://img.shields.io/badge/TDengine-3.0+-0080FF?style=flat-square)
![Nacos](https://img.shields.io/badge/Nacos-3.x-1890FF?style=flat-square)
![Sentinel](https://img.shields.io/badge/Sentinel-1.x-00BFFF?style=flat-square)
![Seata](https://img.shields.io/badge/Seata-2.x-00CED1?style=flat-square)
![Redis](https://img.shields.io/badge/Redis-7.x-DC382D?style=flat-square&logo=redis&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-支持-2496ED?style=flat-square&logo=docker&logoColor=white)

## 产品截图

<details>
<summary><b>基础平台</b>（4 张截图）</summary>
<br>
<p>
  <a href="docs/images/pc/login.png"><img src="docs/images/pc/login.png" width="270" alt="登录页"></a>
  <a href="docs/images/pc/basic/myApplication.png"><img src="docs/images/pc/basic/myApplication.png" width="270" alt="我的应用"></a>
  <a href="docs/images/pc/basic/openAccessKey.png"><img src="docs/images/pc/basic/openAccessKey.png" width="270" alt="访问密钥"></a>
</p>
<p>
  <a href="docs/images/pc/basic/openApi.png"><img src="docs/images/pc/basic/openApi.png" width="270" alt="开放接口"></a>
</p>
</details>

<details>
<summary><b>开发运营系统</b>（7 张截图）</summary>
<br>
<p>
  <a href="docs/images/pc/devOperation/tenant.png"><img src="docs/images/pc/devOperation/tenant.png" width="270" alt="租户管理"></a>
  <a href="docs/images/pc/devOperation/project.png"><img src="docs/images/pc/devOperation/project.png" width="270" alt="项目管理"></a>
  <a href="docs/images/pc/devOperation/application.png"><img src="docs/images/pc/devOperation/application.png" width="270" alt="应用管理"></a>
</p>
<p>
  <a href="docs/images/pc/devOperation/resource.png"><img src="docs/images/pc/devOperation/resource.png" width="270" alt="资源管理"></a>
  <a href="docs/images/pc/devOperation/generator.png"><img src="docs/images/pc/devOperation/generator.png" width="270" alt="代码生成器"></a>
  <a href="docs/images/pc/devOperation/opsInterface.png"><img src="docs/images/pc/devOperation/opsInterface.png" width="270" alt="运维接口"></a>
</p>
<p>
  <a href="docs/images/pc/devOperation/sopIsvInfo.png"><img src="docs/images/pc/devOperation/sopIsvInfo.png" width="270" alt="ISV 信息"></a>
</p>
</details>

<details>
<summary><b>物联网业务系统</b>（15 张截图）</summary>
<br>
<p>
  <a href="docs/images/pc/iotSystem/product.png"><img src="docs/images/pc/iotSystem/product.png" width="270" alt="产品管理"></a>
  <a href="docs/images/pc/iotSystem/productDetails.png"><img src="docs/images/pc/iotSystem/productDetails.png" width="270" alt="产品详情"></a>
  <a href="docs/images/pc/iotSystem/productService.png"><img src="docs/images/pc/iotSystem/productService.png" width="270" alt="产品服务"></a>
</p>
<p>
  <a href="docs/images/pc/iotSystem/device.png"><img src="docs/images/pc/iotSystem/device.png" width="270" alt="设备管理"></a>
  <a href="docs/images/pc/iotSystem/deviceDebug.png"><img src="docs/images/pc/iotSystem/deviceDebug.png" width="270" alt="设备调试"></a>
  <a href="docs/images/pc/iotSystem/deviceShadow.png"><img src="docs/images/pc/iotSystem/deviceShadow.png" width="270" alt="设备影子"></a>
</p>
<p>
  <a href="docs/images/pc/iotSystem/deviceShadow_1.png"><img src="docs/images/pc/iotSystem/deviceShadow_1.png" width="270" alt="设备影子详情"></a>
  <a href="docs/images/pc/iotSystem/assetStats.png"><img src="docs/images/pc/iotSystem/assetStats.png" width="270" alt="资产统计"></a>
  <a href="docs/images/pc/iotSystem/assetmap.png"><img src="docs/images/pc/iotSystem/assetmap.png" width="270" alt="资产地图"></a>
</p>
<p>
  <a href="docs/images/pc/iotSystem/pluginInfo.png"><img src="docs/images/pc/iotSystem/pluginInfo.png" width="270" alt="插件信息"></a>
  <a href="docs/images/pc/iotSystem/pluginInstance.png"><img src="docs/images/pc/iotSystem/pluginInstance.png" width="270" alt="插件实例"></a>
  <a href="docs/images/pc/iotSystem/engineChained.png"><img src="docs/images/pc/iotSystem/engineChained.png" width="270" alt="链式规则引擎"></a>
</p>
<p>
  <a href="docs/images/pc/iotSystem/engineLinkage.png"><img src="docs/images/pc/iotSystem/engineLinkage.png" width="270" alt="设备联动"></a>
  <a href="docs/images/pc/iotSystem/ruleGroovyScript.png"><img src="docs/images/pc/iotSystem/ruleGroovyScript.png" width="270" alt="Groovy 脚本规则"></a>
  <a href="docs/images/pc/iotSystem/scada.png"><img src="docs/images/pc/iotSystem/scada.png" width="270" alt="SCADA 组态"></a>
</p>
</details>

<details>
<summary><b>物联卡业务系统</b>（2 张截图）</summary>
<br>
<p>
  <a href="docs/images/pc/iotCard/cardChannelInfo.png"><img src="docs/images/pc/iotCard/cardChannelInfo.png" width="270" alt="卡通道信息"></a>
  <a href="docs/images/pc/iotCard/cardSimInfo.png"><img src="docs/images/pc/iotCard/cardSimInfo.png" width="270" alt="SIM 卡信息"></a>
</p>
</details>

<details>
<summary><b>大屏可视化系统</b>（1 张截图）</summary>
<br>
<p>
  <a href="docs/images/pc/view/visualization.png"><img src="docs/images/pc/view/visualization.png" width="540" alt="可视化大屏"></a>
</p>
</details>

<details>
<summary><b>流媒体业务系统</b>（2 张截图）</summary>
<br>
<p>
  <a href="docs/images/pc/videoSystem/videoMediaServer.png"><img src="docs/images/pc/videoSystem/videoMediaServer.png" width="270" alt="流媒体服务"></a>
  <a href="docs/images/pc/videoSystem/videoStreamProxy.png"><img src="docs/images/pc/videoSystem/videoStreamProxy.png" width="270" alt="视频流代理"></a>
</p>
</details>

<details>
<summary><b>移动端 H5</b>（5 张截图）</summary>
<br>
<p>
  <a href="docs/images/h5/login.jpg"><img src="docs/images/h5/login.jpg" width="160" alt="登录"></a>
  <a href="docs/images/h5/index.jpg"><img src="docs/images/h5/index.jpg" width="160" alt="首页"></a>
  <a href="docs/images/h5/dashboard.jpg"><img src="docs/images/h5/dashboard.jpg" width="160" alt="仪表盘"></a>
  <a href="docs/images/h5/myHome.jpg"><img src="docs/images/h5/myHome.jpg" width="160" alt="我的"></a>
  <a href="docs/images/h5/scene.jpg"><img src="docs/images/h5/scene.jpg" width="160" alt="场景"></a>
</p>
</details>

## 版本对比

| 功能 | 社区版 | 商业版 | 旗舰版 |
|------|:------:|:------:|:------:|
| 业务层源码 | ✔ 完整（GitHub/Gitee 公开） | ✔ 100% 完整 | ✔ Pro 版 100% 全部 |
| ThingLinks-util 底层库 | ✕ JAR 引用 | ✕ JAR 引用 | ✔ 完整源码 |
| 技术文档 | 社区文档 | 社区文档 | 完整技术 + 架构文档 |
| 私有仓库权限 | ✕ | ✔ | ✔ |
| 修改 package 包名 | ✕ 禁止 | ✔ 允许 | ✔ 不受限制 |
| 修改 Maven groupId | ✕ 禁止 | ✔ 允许 | ✔ 不受限制 |
| 修改作者信息 | ✕ 禁止 | ⚠ 可改，须保留版权 | ✔ 不受限制 |
| 修改版权信息 | ✕ 禁止 | ✕ 须保留 | ✔ 不受限制 |

> **社区版用户请注意：** 根据 Apache 2.0 协议与 ThingLinks 授权协议，社区版源码中的 package 包名、Maven groupId、作者署名及版权声明均不可修改或移除。违反此规定将构成侵权。如需修改标识信息，请升级至商业版或旗舰版。

> **商业版 / 旗舰版授权激活：** 购买后，请将我们提供的授权 ID 填入 [LICENSE-COMMERCIAL](LICENSE-COMMERCIAL) 文件中的授权信息区域，并通过 git commit 提交。git 提交记录将作为授权生效的关键证明。通过 [mqttsnet.com](https://mqttsnet.com) 可验证授权状态。

## 贡献者

感谢所有为本项目做出贡献的优秀开发者！

<a href="https://github.com/mqttsnet/thinglinks/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=mqttsnet/thinglinks&max=100&columns=12" alt="Contributors" />
</a>

欢迎参与贡献！请查阅 [贡献者指南](CONTRIBUTING.md)。

## 联系我们

- 商业合作：[mqttsnet@163.com](mailto:mqttsnet@163.com)
- 问题反馈：[GitHub Issues](https://github.com/mqttsnet/thinglinks/issues)
- 提交 PR：[GitHub Pull Requests](https://github.com/mqttsnet/thinglinks/pulls)

> **声明：** 本项目同步镜像至多个代码托管平台。Bug 反馈、功能建议、技术讨论的**唯一官方渠道**为 [GitHub Issues](https://github.com/mqttsnet/thinglinks/issues)，其他平台（Gitee、Gitea 等）提交的 Issue 不予处理。

## 开源协议

ThingLinks 社区版基于 [Apache License 2.0](LICENSE) 开源，附加商业条款详见 [LICENSE-COMMERCIAL](LICENSE-COMMERCIAL)。

商业版 / 旗舰版授权请联系 [mqttsnet@163.com](mailto:mqttsnet@163.com)。

---

<div align="center">

Copyright &copy; 2019-present [MqttsNet](https://mqttsnet.com). All rights reserved.

[感谢 JetBrains 提供免费 IDE 许可](https://www.jetbrains.com)

</div>
