<div align="center">

<a href="https://mqttsnet.com"><img src="../docs/images/logo.png" alt="ThingLinks" width="160"></a>

# thinglinks-web

**ThingLinks IoT プラットフォーム — 管理コンソール**

[English](README.md) | [简体中文](README.zh-CN.md) | 日本語 | [한국어](README.ko.md)

</div>

---

## 概要

`thinglinks-web` は [ThingLinks](https://github.com/mqttsnet/thinglinks) マルチテナント SaaS クラウド IoT プラットフォームの管理コンソールフロントエンドです。Vue 3 + TypeScript + Vite で構築されています。

## 技術スタック

![Vue 3](https://img.shields.io/badge/Vue-3.3-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-4.6-3178C6?style=flat-square&logo=typescript&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-4.3-646CFF?style=flat-square&logo=vite&logoColor=white)
![Pinia](https://img.shields.io/badge/Pinia-2.0-FFD859?style=flat-square)
![Ant Design Vue](https://img.shields.io/badge/Ant%20Design%20Vue-3.x-1890FF?style=flat-square&logo=antdesign&logoColor=white)
![ECharts](https://img.shields.io/badge/ECharts-5.x-AA344D?style=flat-square&logo=apacheecharts&logoColor=white)

## 特徴

- マルチテナント SaaS アーキテクチャ（NONE / COLUMN / DATASOURCE_COLUMN）
- 動的ルートベースの権限管理
- テーマ設定と国際化（i18n）対応
- Ant Design Vue ベースの豊富なコンポーネント
- ECharts によるデータ可視化
- 開発用 Mock データ対応

## クイックスタート

### 環境要件

- **Node.js** >= 18.0.0
- **pnpm**（インストール：`npm install -g pnpm`）

### インストールと実行

```bash
# 依存関係のインストール
pnpm install

# 開発サーバーの起動（デフォルト：http://localhost:3100）
pnpm dev

# 本番ビルド
pnpm build

# テスト環境ビルド
pnpm build:test
```

## ライセンス

本プロジェクトは [ThingLinks](https://github.com/mqttsnet/thinglinks) のサブモジュールです。ライセンスはメインプロジェクトの [LICENSE](../LICENSE) および [LICENSE-COMMERCIAL](../LICENSE-COMMERCIAL) に準じます。

Copyright &copy; 2019-present [MqttsNet](https://mqttsnet.com)
