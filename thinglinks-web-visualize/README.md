# ThingLinks 大屏可视化平台

<p align="center">
  <b>ThingLinks - 开源物联网大屏可视化</b>
</p>

---

## 📖 项目简介

ThingLinks 大屏可视化平台是 ThingLinks 物联网平台的前端可视化解决方案，基于 Vue 3 + TypeScript + Vite
构建的数据可视化大屏展示平台。支持多种图表类型、3D 可视化、地图展示等功能，适用于物联网数据监控、数据分析展示、业务大屏等场景。

## ✨ 功能特性

- 🎨 **丰富的图表组件**：基于 ECharts 封装多种常用图表（柱状图、折线图、饼图、雷达图、仪表盘等）
- 🗺️ **地图可视化**：集成高德地图，支持热力图、散点图、区域图等地图展示
- 🌊 **3D 可视化**：基于 Three.js 实现 3D 场景展示和动画效果
- 📊 **数据大屏**：灵活的大屏布局配置，支持多种分辨率适配
- 🎯 **拖拽编辑器**：可视化拖拽编辑大屏，所见即所得
- 🔌 **组件化开发**：高度模块化的组件设计，易于扩展和维护
- 📱 **响应式布局**：支持多种屏幕尺寸自适应
- 🎭 **主题定制**：支持自定义主题色和样式配置
- 🌐 **国际化**：内置国际化支持，多语言切换
- 💾 **数据缓存**：支持本地数据缓存和状态持久化

## 🛠️ 技术栈

### 核心框架

- **Vue 3.2.31** - 渐进式 JavaScript 框架
- **TypeScript 4.6.3** - JavaScript 的超集，提供类型支持
- **Vite 4.3.6** - 下一代前端构建工具
- **Pinia 2.0.13** - Vue 状态管理库
- **Vue Router 4.0.12** - Vue.js 官方路由

### UI 框架

- **Naive UI 2.34.3** - Vue 3 组件库

### 可视化库

- **ECharts 5.3.2** - 强大的图表可视化库
- **vue-echarts 6.0.2** - ECharts 的 Vue 封装
- **echarts-liquidfill** - 水球图插件
- **echarts-wordcloud** - 词云图插件
- **echarts-stat** - 统计图表插件
- **Three.js 0.145.0** - 3D 图形库

### 地图

- **高德地图 JS API** - 地图可视化支持

### 工具库

- **Axios 1.4.0** - HTTP 请求库
- **Lodash-es 4.17.21** - 实用工具库
- **Day.js 1.11.7** - 日期处理库
- **GSAP 3.11.3** - 高性能动画库
- **Mitt 3.0.0** - 事件总线

### 开发工具

- **ESLint** - 代码检查
- **Prettier** - 代码格式化
- **Sass** - CSS 预处理器
- **Husky** - Git Hooks
- **Commitlint** - 提交信息规范

## 📁 目录结构

```
thinglinks-web-visualize/
├── src/
│   ├── api/                 # API 接口管理
│   ├── assets/              # 静态资源
│   ├── components/          # 公共组件
│   ├── directives/          # 自定义指令
│   ├── enums/               # 枚举定义
│   ├── hooks/               # 组合式 API
│   ├── i18n/                # 国际化配置
│   ├── layout/              # 布局组件
│   ├── packages/            # 组件包
│   ├── plugins/             # 插件配置
│   ├── router/              # 路由配置
│   ├── settings/            # 配置文件
│   ├── store/               # 状态管理
│   ├── styles/              # 全局样式
│   ├── utils/               # 工具函数
│   ├── views/               # 页面视图
│   ├── App.vue              # 根组件
│   └── main.ts              # 入口文件
├── public/                  # 公共静态资源
├── vite.config.ts           # Vite 配置
├── tsconfig.json            # TypeScript 配置
├── package.json             # 项目依赖
└── README.md                # 项目说明文档
```

## 🚀 快速开始

### 环境要求

- **Node.js**: 版本 >= 18.0.0
- **pnpm**: 推荐使用 pnpm 作为包管理工具

### 安装

1. 克隆项目

```bash
git clone https://github.com/mqttsnet/thinglinks.git
cd thinglinks/thinglinks-web-visualize
```

2. 安装依赖

```bash
# 安装 pnpm（如已安装可跳过）
npm install -g pnpm

# 安装项目依赖
pnpm install

# 或者使用淘宝镜像
npm install --registry=https://registry.npm.taobao.org --force
```

### 开发环境

```bash
# 启动开发服务器
pnpm dev

# 或者
pnpm serve
```

### 生产环境打包

```bash
# 构建生产环境代码
pnpm build

# 打包并删除缓存
pnpm build:no-cache

# 生成构建包报表预览
pnpm report
```

## 📦 可用脚本

### 开发脚本

```bash
pnpm dev          # 启动开发服务器
pnpm serve        # 启动开发服务器（同 dev）
pnpm preview      # 本地进行打包预览
```

### 代码质量

```bash
pnpm lint         # 代码检查
pnpm lint:fix     # 代码检查并自动修复
pnpm lint:stylelint   # 样式格式化
pnpm lint:prettier    # js/ts 代码格式化
```

### 其他脚本

```bash
pnpm reinstall    # 删除依赖重新装，兼容 Windows
pnpm log          # 生成 CHANGELOG
pnpm clean:cache  # 删除缓存
pnpm clean:lib    # 删除 node_modules，兼容 Windows
pnpm new          # 创建新组件（基于 plop）
```

## ⚙️ 配置说明

### 环境变量配置

在项目根目录创建 `.env.development` 文件进行环境变量配置：

```env
# API 基础路径（ThingLinks 网关地址）
VITE_PROXY=http://127.0.0.1:18760
```

### 构建配置

项目使用 Vite 进行构建，配置文件为 `vite.config.ts`，主要配置包括：

- 路径别名配置
- 插件配置（Vue、Vue JSX、压缩等）
- 开发服务器配置
- 构建优化配置

## 🌐 浏览器支持

| 浏览器     | 支持版本 |
|---------|------|
| Chrome  | 最新版  |
| Firefox | 最新版  |
| Safari  | 最新版  |
| Edge    | 最新版  |
| IE      | 不支持  |

## 📖 开发指南

### 组件开发

项目采用组件化开发模式，推荐开发流程：

1. 在 `src/components/` 下创建通用组件
2. 在 `src/views/` 下创建页面级组件
3. 使用 TypeScript 定义类型
4. 遵循 ESLint 和 Prettier 代码规范

## 🚀 部署说明

### 构建

```bash
# 构建生产环境代码
pnpm build
```

构建产物将生成在 `dist` 目录下。

## 🤝 贡献指南

我们欢迎所有形式的贡献，包括但不限于：

- 提交 Issue 报告 Bug 或提出新功能建议
- 提交 Pull Request 修复 Bug 或实现新功能
- 完善文档
- 提供使用案例

### 提交规范

项目使用 Commitlint 进行提交信息规范检查，提交格式：

```
type(scope): subject

body

footer
```

类型（type）包括：

- `feat`: 新功能
- `fix`: 修复 Bug
- `docs`: 文档更新
- `style`: 代码格式调整
- `refactor`: 代码重构
- `perf`: 性能优化
- `test`: 测试相关
- `chore`: 构建配置或辅助工具的变动

## 📄 开源协议

本项目基于 [Apache 2.0](./LICENSE) 协议开源。

## 🔗 相关链接

- [ThingLinks 官网](https://thinglinks.mqttsnet.com)

## 💬 联系我们

- **作者**: mqttsnet
- **邮箱**: mqttsnet@163.com
- **GitHub**: https://github.com/mqttsnet

---

<p align="center">
  如果这个项目对你有帮助，请给一个 ⭐️ Star 支持一下！
</p>