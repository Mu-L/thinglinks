/**
 * 设备相关枚举定义
 */

/**
 * 设备连接状态枚举值
 */
export enum DeviceConnectStatus {
  /** 未连接 */
  NOT_CONNECTED = 0,
  /** 在线 */
  ONLINE = 1,
  /** 离线 */
  OFFLINE = 2,
}

/**
 * 设备连接状态配置
 */
export const DEVICE_CONNECT_STATUS_OPTIONS = [
  {
    label: 'iot.link.device.device.online',
    value: DeviceConnectStatus.ONLINE,
    color: '#43cf7c',
    cssClass: 'green',
  },
  {
    label: 'iot.link.device.device.offline',
    value: DeviceConnectStatus.OFFLINE,
    color: '#999',
    cssClass: '',
  },
  {
    label: 'iot.link.device.device.notConnected',
    value: DeviceConnectStatus.NOT_CONNECTED,
    color: '#fa3758',
    cssClass: 'red',
  },
];

/**
 * 设备状态枚举值
 */
export enum DeviceStatus {
  /** 未激活 */
  NOT_ACTIVATED = 0,
  /** 启用 */
  ENABLED = 1,
  /** 禁用 */
  DISABLED = 2,
}

/**
 * 设备状态配置
 */
export const DEVICE_STATUS_OPTIONS = [
  {
    label: 'iot.link.device.device.enable',
    value: DeviceStatus.ENABLED,
    color: '#43cf7c',
    cssClass: 'green',
  },
  {
    label: 'iot.link.device.device.deactivate',
    value: DeviceStatus.DISABLED,
    color: '#fa3758',
    cssClass: 'red',
  },
  {
    label: 'iot.link.device.device.deviceAllStatus.notActivat',
    value: DeviceStatus.NOT_ACTIVATED,
    color: '#999',
    cssClass: '',
  },
];

/**
 * 设备节点类型枚举值
 */
export enum DeviceNodeType {
  /** 直连设备 */
  DIRECT = 0,
  /** 网关设备 */
  GATEWAY = 1,
  /** 子设备 */
  SUB_DEVICE = 2,
}

/**
 * 设备节点类型配置
 */
export const DEVICE_NODE_TYPE_OPTIONS = [
  {
    label: '直连设备',
    value: DeviceNodeType.DIRECT,
  },
  {
    label: '网关设备',
    value: DeviceNodeType.GATEWAY,
  },
  {
    label: '子设备',
    value: DeviceNodeType.SUB_DEVICE,
  },
];

/**
 * 设备认证方式枚举值
 */
export enum DeviceAuthMode {
  /** 用户名密码认证 */
  USERNAME_PASSWORD = 0,
  /** SSL/TLS证书认证 */
  SSL_TLS_CERTIFICATE = 1,
}

/**
 * 设备认证方式配置
 */
export const DEVICE_AUTH_MODE_OPTIONS = [
  {
    label: 'iot.link.device.device.authModeMsg[0]',
    value: DeviceAuthMode.USERNAME_PASSWORD,
  },
  {
    label: 'iot.link.device.device.authModeMsg[2]',
    value: DeviceAuthMode.SSL_TLS_CERTIFICATE,
  },
];

/**
 * 根据连接状态值获取配置
 * @param status 连接状态值
 * @returns 连接状态配置
 */
export function getConnectStatusOption(status: DeviceConnectStatus) {
  return DEVICE_CONNECT_STATUS_OPTIONS.find((item) => item.value === status);
}

/**
 * 根据设备状态值获取配置
 * @param status 设备状态值
 * @returns 设备状态配置
 */
export function getDeviceStatusOption(status: DeviceStatus) {
  return DEVICE_STATUS_OPTIONS.find((item) => item.value === status);
}

/**
 * 根据节点类型值获取配置
 * @param nodeType 节点类型值
 * @returns 节点类型配置
 */
export function getNodeTypeOption(nodeType: DeviceNodeType) {
  return DEVICE_NODE_TYPE_OPTIONS.find((item) => item.value === nodeType);
}

/**
 * 根据认证方式值获取配置
 * @param authMode 认证方式值
 * @returns 认证方式配置
 */
export function getAuthModeOption(authMode: DeviceAuthMode) {
  return DEVICE_AUTH_MODE_OPTIONS.find((item) => item.value === authMode);
}