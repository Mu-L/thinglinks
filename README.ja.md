<div align="center">

<a href="https://mqttsnet.com"><img src="./docs/images/logo.png" alt="ThingLinks" width="200"></a>

# ThingLinks IoT プラットフォーム

**マルチテナント SaaS クラウド IoT プラットフォーム — 高性能 · 高スループット · 高拡張性**

[English](README.md) | [简体中文](README.zh_CN.md) | 日本語 | [한국어](README.ko.md)

[![JDK](https://img.shields.io/badge/JDK-17+-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025-green?style=flat-square&logo=spring)](https://spring.io/projects/spring-cloud)
[![Vue](https://img.shields.io/badge/Vue-3.x-brightgreen?style=flat-square&logo=vuedotjs)](https://vuejs.org/)
[![TDengine](https://img.shields.io/badge/TDengine-3.0+-blue?style=flat-square)](https://tdengine.com/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue?style=flat-square)](LICENSE)
[![GitHub Stars](https://img.shields.io/github/stars/mqttsnet/thinglinks?style=flat-square)](https://github.com/mqttsnet/thinglinks/stargazers)
[![GitHub Forks](https://img.shields.io/github/forks/mqttsnet/thinglinks?style=flat-square)](https://github.com/mqttsnet/thinglinks/network/members)

<br>

[![Website](https://img.shields.io/badge/公式サイト-mqttsnet.com-blue?style=for-the-badge)](https://mqttsnet.com)
[![GitHub](https://img.shields.io/badge/GitHub-mqttsnet/thinglinks-181717?style=for-the-badge&logo=github)](https://github.com/mqttsnet/thinglinks)
[![Docs](https://img.shields.io/badge/ドキュメント-オンライン-green?style=for-the-badge)](https://mqttsnet.yuque.com/trgbro/thinglinks)

</div>

---

## プラットフォーム概要

ThingLinks は、Spring Cloud マイクロサービスアーキテクチャに基づいて構築されたエンタープライズ向け**マルチテナント SaaS クラウド IoT プラットフォーム**です。**高性能・高スループット・高拡張性**のデバイス接続能力を提供し、単一ノードで**百万レベルの同時接続**をサポートします。プラグインベースの拡張開発とマルチプロトコル対応が可能です。

## 主な機能

| 機能 | 説明 |
|------|------|
| **マルチテナント SaaS** | エンタープライズ向けマルチテナントアーキテクチャ、完全なテナント分離 |
| **百万レベル接続** | 単一ノードで百万レベルのデバイス同時接続をサポート |
| **マルチプロトコル** | MQTT、WebSocket、TCP、UDP、CoAP、HTTP 等に対応 |
| **デバイス管理** | 統一製品モデル、デバイスライフサイクル管理、サブデバイス管理 |
| **ルールエンジン** | 柔軟なアラーム、通知、データ転送（Kafka、RocketMQ、RabbitMQ、MySQL、HTTP 等） |
| **SCADA & 可視化** | デバイス地理位置可視化、SCADA ダッシュボード、大画面表示 |
| **時系列データベース** | TDengine — デバイスごとに1テーブル、デバイスタイプごとに1スーパーテーブル |
| **プラグインシステム** | プラグインベース開発、カスタムプロトコル・機能拡張対応 |
| **IoT カード管理** | SIM カードチャネル管理、カードライフサイクル管理 |
| **ストリーミング** | メディアサーバー統合、ビデオストリームプロキシ |

## 技術スタック

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
![Docker](https://img.shields.io/badge/Docker-対応-2496ED?style=flat-square&logo=docker&logoColor=white)

## スクリーンショット

<details>
<summary><b>基盤プラットフォーム</b>（4 枚）</summary>
<br>
<p>
  <a href="docs/images/pc/login.png"><img src="docs/images/pc/login.png" width="270" alt="ログイン"></a>
  <a href="docs/images/pc/basic/myApplication.png"><img src="docs/images/pc/basic/myApplication.png" width="270" alt="マイアプリ"></a>
  <a href="docs/images/pc/basic/openAccessKey.png"><img src="docs/images/pc/basic/openAccessKey.png" width="270" alt="アクセスキー"></a>
</p>
<p>
  <a href="docs/images/pc/basic/openApi.png"><img src="docs/images/pc/basic/openApi.png" width="270" alt="オープンAPI"></a>
</p>
</details>

<details>
<summary><b>DevOps システム</b>（7 枚）</summary>
<br>
<p>
  <a href="docs/images/pc/devOperation/tenant.png"><img src="docs/images/pc/devOperation/tenant.png" width="270" alt="テナント管理"></a>
  <a href="docs/images/pc/devOperation/project.png"><img src="docs/images/pc/devOperation/project.png" width="270" alt="プロジェクト管理"></a>
  <a href="docs/images/pc/devOperation/application.png"><img src="docs/images/pc/devOperation/application.png" width="270" alt="アプリケーション管理"></a>
</p>
<p>
  <a href="docs/images/pc/devOperation/resource.png"><img src="docs/images/pc/devOperation/resource.png" width="270" alt="リソース管理"></a>
  <a href="docs/images/pc/devOperation/generator.png"><img src="docs/images/pc/devOperation/generator.png" width="270" alt="コードジェネレーター"></a>
  <a href="docs/images/pc/devOperation/opsInterface.png"><img src="docs/images/pc/devOperation/opsInterface.png" width="270" alt="運用インターフェース"></a>
</p>
<p>
  <a href="docs/images/pc/devOperation/sopIsvInfo.png"><img src="docs/images/pc/devOperation/sopIsvInfo.png" width="270" alt="ISV 情報"></a>
</p>
</details>

<details>
<summary><b>IoT システム</b>（15 枚）</summary>
<br>
<p>
  <a href="docs/images/pc/iotSystem/product.png"><img src="docs/images/pc/iotSystem/product.png" width="270" alt="製品管理"></a>
  <a href="docs/images/pc/iotSystem/productDetails.png"><img src="docs/images/pc/iotSystem/productDetails.png" width="270" alt="製品詳細"></a>
  <a href="docs/images/pc/iotSystem/productService.png"><img src="docs/images/pc/iotSystem/productService.png" width="270" alt="製品サービス"></a>
</p>
<p>
  <a href="docs/images/pc/iotSystem/device.png"><img src="docs/images/pc/iotSystem/device.png" width="270" alt="デバイス管理"></a>
  <a href="docs/images/pc/iotSystem/deviceDebug.png"><img src="docs/images/pc/iotSystem/deviceDebug.png" width="270" alt="デバイスデバッグ"></a>
  <a href="docs/images/pc/iotSystem/deviceShadow.png"><img src="docs/images/pc/iotSystem/deviceShadow.png" width="270" alt="デバイスシャドウ"></a>
</p>
<p>
  <a href="docs/images/pc/iotSystem/deviceShadow_1.png"><img src="docs/images/pc/iotSystem/deviceShadow_1.png" width="270" alt="デバイスシャドウ詳細"></a>
  <a href="docs/images/pc/iotSystem/assetStats.png"><img src="docs/images/pc/iotSystem/assetStats.png" width="270" alt="アセット統計"></a>
  <a href="docs/images/pc/iotSystem/assetmap.png"><img src="docs/images/pc/iotSystem/assetmap.png" width="270" alt="アセットマップ"></a>
</p>
<p>
  <a href="docs/images/pc/iotSystem/pluginInfo.png"><img src="docs/images/pc/iotSystem/pluginInfo.png" width="270" alt="プラグイン情報"></a>
  <a href="docs/images/pc/iotSystem/pluginInstance.png"><img src="docs/images/pc/iotSystem/pluginInstance.png" width="270" alt="プラグインインスタンス"></a>
  <a href="docs/images/pc/iotSystem/engineChained.png"><img src="docs/images/pc/iotSystem/engineChained.png" width="270" alt="チェーンルールエンジン"></a>
</p>
<p>
  <a href="docs/images/pc/iotSystem/engineLinkage.png"><img src="docs/images/pc/iotSystem/engineLinkage.png" width="270" alt="デバイス連携"></a>
  <a href="docs/images/pc/iotSystem/ruleGroovyScript.png"><img src="docs/images/pc/iotSystem/ruleGroovyScript.png" width="270" alt="Groovy スクリプトルール"></a>
  <a href="docs/images/pc/iotSystem/scada.png"><img src="docs/images/pc/iotSystem/scada.png" width="270" alt="SCADA"></a>
</p>
</details>

<details>
<summary><b>IoT カードシステム</b>（2 枚）</summary>
<br>
<p>
  <a href="docs/images/pc/iotCard/cardChannelInfo.png"><img src="docs/images/pc/iotCard/cardChannelInfo.png" width="270" alt="カードチャネル情報"></a>
  <a href="docs/images/pc/iotCard/cardSimInfo.png"><img src="docs/images/pc/iotCard/cardSimInfo.png" width="270" alt="SIM カード情報"></a>
</p>
</details>

<details>
<summary><b>可視化ダッシュボード</b>（1 枚）</summary>
<br>
<p>
  <a href="docs/images/pc/view/visualization.png"><img src="docs/images/pc/view/visualization.png" width="540" alt="可視化ダッシュボード"></a>
</p>
</details>

<details>
<summary><b>ビデオストリーミングシステム</b>（2 枚）</summary>
<br>
<p>
  <a href="docs/images/pc/videoSystem/videoMediaServer.png"><img src="docs/images/pc/videoSystem/videoMediaServer.png" width="270" alt="メディアサーバー"></a>
  <a href="docs/images/pc/videoSystem/videoStreamProxy.png"><img src="docs/images/pc/videoSystem/videoStreamProxy.png" width="270" alt="ストリームプロキシ"></a>
</p>
</details>

<details>
<summary><b>モバイル H5</b>（5 枚）</summary>
<br>
<p>
  <a href="docs/images/h5/login.jpg"><img src="docs/images/h5/login.jpg" width="160" alt="ログイン"></a>
  <a href="docs/images/h5/index.jpg"><img src="docs/images/h5/index.jpg" width="160" alt="ホーム"></a>
  <a href="docs/images/h5/dashboard.jpg"><img src="docs/images/h5/dashboard.jpg" width="160" alt="ダッシュボード"></a>
  <a href="docs/images/h5/myHome.jpg"><img src="docs/images/h5/myHome.jpg" width="160" alt="マイホーム"></a>
  <a href="docs/images/h5/scene.jpg"><img src="docs/images/h5/scene.jpg" width="160" alt="シーン"></a>
</p>
</details>

## エディション比較

| 機能 | コミュニティ版 | 商用版 | エンタープライズ版 |
|------|:------------:|:------:|:-----------------:|
| ビジネスレイヤーソースコード | ✔ 完全（GitHub/Gitee 公開） | ✔ 100% 完全 | ✔ Pro 版 100% 全体 |
| ThingLinks-util コアライブラリ | ✕ JAR 参照 | ✕ JAR 参照 | ✔ 完全ソースコード |
| 技術ドキュメント | コミュニティドキュメント | コミュニティドキュメント | 完全な技術 + アーキテクチャドキュメント |
| プライベートリポジトリアクセス | ✕ | ✔ | ✔ |
| パッケージ名の変更 | ✕ 禁止 | ✔ 許可 | ✔ 制限なし |
| Maven groupId の変更 | ✕ 禁止 | ✔ 許可 | ✔ 制限なし |
| 作者情報の変更 | ✕ 禁止 | ⚠ 可（著作権表示は保持） | ✔ 制限なし |
| 著作権情報の変更 | ✕ 禁止 | ✕ 保持必須 | ✔ 制限なし |

> **コミュニティ版ユーザーへの注意：** Apache 2.0 ライセンスおよび ThingLinks 商用ライセンスに基づき、コミュニティ版ソースコードのパッケージ名、Maven groupId、作者帰属表示、著作権表示の変更または削除は禁止されています。

## コントリビューター

このプロジェクトに貢献してくださった皆様に感謝します。[コントリビューターガイド](CONTRIBUTING.md)

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/xiaonannet"><img src="https://avatars.githubusercontent.com/u/57425893?s=400&u=7e19afcd215072e56e0168c6717342456b174b68&v=4" width="100px;" alt="xiaonannet"/><br /><sub><b>xiaonannet</b></sub></a><br /><a href="https://github.com/mqttsnet/thinglinks/commits?author=xiaonannet" title="Code">💻</a> <a href="https://github.com/mqttsnet/thinglinks/commits?author=xiaonannet" title="Documentation">📖</a> <a href="#xiaonannet" title="Design">🎨</a><a href="https://github.com/mqttsnet/thinglinks/commits?author=lvwshuai" title="Bug reports">🐛</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/lovely-shisen"><img src="https://avatars.githubusercontent.com/u/32031196?s=400&u=7e19afcd215072e56e0168c6717342456b174b68&v=4" width="100px;" alt="lovely-shisen"/><br /><sub><b>lovely-shisen</b></sub></a><br /><a href="https://github.com/mqttsnet/thinglinks/commits?author=lovely-shisen" title="Code">💻</a> <a href="https://github.com/mqttsnet/thinglinks/commits?author=lovely-shisen" title="Documentation">📖</a> <a href="#lovely-shisen" title="Design">🎨</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/whb-11"><img src="https://avatars.githubusercontent.com/u/95611581?v=4" width="100px;" alt="wanghoubang"/><br /><sub><b>whb-11</b></sub></a><br /><a href="https://github.com/mqttsnet/thinglinks/commits?author=whb-11" title="Code">💻</a> <a href="https://github.com/mqttsnet/thinglinks/commits?author=whb-11" title="Documentation">📖</a><a href="#lovely-shisen" title="Design">🎨</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/kangkaime"><img src="https://avatars.githubusercontent.com/u/4422866?v=4" width="100px;" alt="kangkaime"/><br /><sub><b>kangkaime</b></sub></a><br /><a href="https://github.com/mqttsnet/thinglinks/commits?author=kangkaime" title="Code">💻</a> <a href="https://github.com/mqttsnet/thinglinks/commits?author=kangkaime" title="Bug reports">🐛</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/lvwshuai"><img src="https://avatars.githubusercontent.com/u/2986900?v=4" width="100px;" alt="lvwshuai"/><br /><sub><b>lvwshuai</b></sub></a><br /><a href="https://github.com/mqttsnet/thinglinks/commits?author=lvwshuai" title="Code">💻</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/andyz168"><img src="https://avatars.githubusercontent.com/u/22382971?v=4" width="100px;" alt="andyz168"/><br /><sub><b>andyz168</b></sub></a><br /><a href="https://github.com/mqttsnet/thinglinks/commits?author=andyz168" title="Code">💻</a></td>
    </tr>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/llJam"><img src="https://avatars.githubusercontent.com/u/113015997?v=4" width="100px;" alt="llJam"/><br /><sub><b>llJam</b></sub></a><br /><a href="https://github.com/mqttsnet/thinglinks/commits?author=llJam" title="Code">💻</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/qianmenfei"><img src="https://avatars.githubusercontent.com/u/39989430?v=4" width="100px;" alt="qianmenfei"/><br /><sub><b>qianmenfei</b></sub></a><br /><a href="https://github.com/mqttsnet/thinglinks/commits?author=qianmenfei" title="Code">💻</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/wangfan1997"><img src="https://avatars.githubusercontent.com/u/108504684?v=4" width="100px;" alt="wangfan1997"/><br /><sub><b>wangfan1997</b></sub></a><br /><a href="https://github.com/mqttsnet/thinglinks/commits?author=wangfan1997" title="Code">💻</a></td>
    </tr>
  </tbody>
</table>
<!-- ALL-CONTRIBUTORS-LIST:END -->

## お問い合わせ

- ビジネス協力：[mqttsnet@163.com](mailto:mqttsnet@163.com)
- 問題報告：[GitHub Issues](https://github.com/mqttsnet/thinglinks/issues)
- プルリクエスト：[GitHub PRs](https://github.com/mqttsnet/thinglinks/pulls)

## ライセンス

ThingLinks コミュニティ版は [Apache License 2.0](LICENSE) に基づきオープンソースで提供されます。追加の商用条件は [LICENSE-COMMERCIAL](LICENSE-COMMERCIAL) をご参照ください。

商用版 / エンタープライズ版のライセンスについては [mqttsnet@163.com](mailto:mqttsnet@163.com) までお問い合わせください。

---

<div align="center">

Copyright &copy; 2019-present [MqttsNet](https://mqttsnet.com). All rights reserved.

[JetBrains による無料 IDE ライセンスの提供に感謝します](https://www.jetbrains.com)

</div>
