<div align="center">

<a href="https://mqttsnet.com"><img src="../docs/images/logo.png" alt="ThingLinks" width="160"></a>

# thinglinks-web

**ThingLinks 物联网平台 — 管理控制台**

[English](README.md) | 简体中文 | [日本語](README.ja.md) | [한국어](README.ko.md)

</div>

---

## 简介

`thinglinks-web` 是 [ThingLinks](https://github.com/mqttsnet/thinglinks) 多租户 SaaS 云物联网平台的管理控制台前端，基于 Vue 3 + TypeScript + Vite 构建。

## 技术栈

![Vue 3](https://img.shields.io/badge/Vue-3.3-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-4.6-3178C6?style=flat-square&logo=typescript&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-4.3-646CFF?style=flat-square&logo=vite&logoColor=white)
![Pinia](https://img.shields.io/badge/Pinia-2.0-FFD859?style=flat-square)
![Ant Design Vue](https://img.shields.io/badge/Ant%20Design%20Vue-3.x-1890FF?style=flat-square&logo=antdesign&logoColor=white)
![ECharts](https://img.shields.io/badge/ECharts-5.x-AA344D?style=flat-square&logo=apacheecharts&logoColor=white)

## 特性

- 多租户 SaaS 架构（NONE / COLUMN / DATASOURCE_COLUMN）
- 动态路由权限管理
- 可配置主题与国际化（i18n）
- 基于 Ant Design Vue 的丰富组件库
- ECharts 数据可视化
- 内置 Mock 数据方案

## 快速开始

### 环境要求

- **Node.js** >= 18.0.0
- **pnpm**（安装：`npm install -g pnpm`）

### 安装与运行

```bash
# 安装依赖
pnpm install

# 启动开发服务（默认：http://localhost:3100）
pnpm dev

# 生产环境打包
pnpm build

# 测试环境打包
pnpm build:test
```

### 环境变量配置

| 文件 | 环境 |
|------|------|
| `.env` | 基础配置 |
| `.env.development` | 开发环境 |
| `.env.test` | 测试环境 |
| `.env.production` | 生产环境 |

### 其他命令

```bash
pnpm preview         # 本地预览生产包
pnpm build:no-cache  # 清除缓存后打包
pnpm clean:cache     # 清除 Vite 缓存
pnpm clean:lib       # 删除 node_modules
pnpm lint:prettier   # 代码格式化
```

## 开源协议

本项目是 [ThingLinks](https://github.com/mqttsnet/thinglinks) 的子模块，开源协议遵循主项目 [LICENSE](../LICENSE) 和 [LICENSE-COMMERCIAL](../LICENSE-COMMERCIAL)。

Copyright &copy; 2019-present [MqttsNet](https://mqttsnet.com)
