# thinglinks-rule-server 服务说明

`thinglinks-rule-server` 是物联网业务系统中的规则引擎服务。它负责处理和执行业务规则，支持插件实例的动态加载与管理。此服务同时监听多个端口，分别用于规则引擎和插件实例通信。

## 服务概述

- **服务名称**: `thinglinks-rule-server`
- **服务类型**: 物联网业务系统 - 规则引擎服务
- **主要功能**: 提供物联网设备数据的规则处理、插件实例管理以及服务健康检查。

## 端口说明

### 1. 规则引擎服务端口

- **端口**: `18786` (TCP)
- **说明**: 规则引擎服务的主要通信端口，所有来自客户端或其他系统的请求将通过该端口进入规则引擎进行处理。

### 2. 插件实例通信端口

- **端口范围**: `50000-51000` (TCP/UDP)
- **说明**: 规则引擎服务支持多个插件实例运行，每个插件实例将占用该范围内的一个或多个端口进行通信。
- **协议**: 支持 TCP 和 UDP 流量。
- **用途**: 该端口范围用于插件实例之间的交互和数据传输，确保插件实例可以独立地接收和发送数据。

## 服务架构

- **网络模式**: 宿主机网络 (`network_mode: "host"`)。这意味着容器共享宿主机的网络栈，所有端口会直接映射到宿主机网络，不需要显式端口映射。

- **健康检查**: 服务包含健康检查，通过 HTTP 请求 `/actuator/health` 来判断服务的健康状态。

## 配置说明

### 环境变量

服务的环境配置使用以下变量：

- `TZ`: 设置时区（默认为 `Asia/Shanghai`）
- `SPRING_PROFILES_ACTIVE`: Spring 配置文件的活动环境（默认为 `test`）
- `NACOS_IP`, `NACOS_PORT`: Nacos 服务的 IP 和端口，用于服务发现与配置管理。
- `NACOS_NAMESPACE`: Nacos 命名空间 ID
- `NACOS_USERNAME`, `NACOS_PASSWORD`: 用于 Nacos 的用户名和密码
- `NACOS_DISCOVERY_IP`: Nacos 服务注册时的 IP 地址

### 卷映射

服务挂载了以下本地目录和宿主机路径：

- `/data01/mqttsnet/thinglinks` 映射到容器内的 `/home/www/mqttsnet`
- `/etc/localtime` 映射到容器内，确保容器时间与宿主机一致。

## 使用方法

### 启动服务

使用 Docker Compose 启动服务：

```bash
docker-compose up -d
```

## 其他

