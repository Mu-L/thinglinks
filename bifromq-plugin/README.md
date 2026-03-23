<div align="center">

<a href="https://mqttsnet.com"><img src="../docs/images/logo.png" alt="ThingLinks" width="160"></a>

# bifromq-plugin

**ThingLinks IoT Platform — BifroMQ MQTT Broker Plugin Library**

[English](README.md) | [简体中文](README.zh-CN.md) | [日本語](README.ja.md) | [한국어](README.ko.md)

[![JDK](https://img.shields.io/badge/JDK-17+-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![BifroMQ](https://img.shields.io/badge/BifroMQ-3.3.5-blue?style=flat-square)](https://github.com/baidu/bifromq)

</div>

---

## About

`bifromq-plugin` is the BifroMQ MQTT broker plugin library for the [ThingLinks](https://github.com/mqttsnet/thinglinks) multi-tenant SaaS cloud IoT platform. It enables runtime integration of custom business logic with the ThingLinks IoT system through BifroMQ's plugin mechanism.

- [Deployment Guide](https://mqttsnet.yuque.com/trgbro/thinglinks)
- [BifroMQ Official Wiki](https://bifromq.io/zh-Hans/docs/plugin/plugin/)

## Plugins

| Plugin | Description |
|--------|-------------|
| `bifromq-auth-provider-plugin` | MQTT client authentication & authorization (username/password, certificate, ACL) |
| `bifromq-event-collector-plugin` | BifroMQ event collection, filtering, and performance monitoring |
| `bifromq-resource-throttler-plugin` | Multi-tenant resource allocation and rate limiting |
| `bifromq-setting-provider-plugin` | Dynamic tenant-level settings configuration at runtime |

## Requirements

- **JDK** 17+
- **Maven** 3.6+
- **BifroMQ** 3.3.5 (server version must match plugin version)

## Build

```bash
# Build all plugins
mvn clean package -DskipTests

# Build single plugin
cd bifromq-auth-provider-plugin && mvn clean package -DskipTests
```

Build artifacts are generated in each plugin's `target/` directory. Upload to BifroMQ's plugin directory for deployment.

## License

This project is part of [ThingLinks](https://github.com/mqttsnet/thinglinks). Licensed under the main project's [LICENSE](../LICENSE) and [LICENSE-COMMERCIAL](../LICENSE-COMMERCIAL).

Copyright &copy; 2019-present [MqttsNet](https://mqttsnet.com)
