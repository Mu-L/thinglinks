/**
 * Topic 动态参数替换
 * @description 替换 Topic 字符串中的动态参数占位符为实际值
 * @param topic - 原始 Topic 字符串，包含占位符如 ${app_id}、${device_identification} 等
 * @param params - 动态参数对象
 * @param params.appId - 应用ID
 * @param params.userName - 用户名
 * @param params.deviceIdentification - 设备标识
 * @param params.deviceSdkVersion - 设备 SDK 版本
 * @param params.productIdentification - 产品标识
 * @returns 替换后的 Topic 字符串
 * @example
 * const topic = '/${app_id}/${device_identification}/data';
 * const params = { appId: 'app123', deviceIdentification: 'device456' };
 * const result = replaceTopicDynamicParams(topic, params);
 * // result: '/app123/device456/data'
 */
export const replaceTopicDynamicParams = (
  topic: string,
  params: {
    appId?: string;
    userName?: string;
    deviceIdentification?: string;
    deviceSdkVersion?: string;
    productIdentification?: string;
  },
): string => {
  if (!topic) return topic;

  let result = topic;

  // 替换所有动态参数
  result = result.replace(/\$\{app_id\}/g, params.appId || '');
  result = result.replace(/\$\{user_name\}/g, params.userName || '');
  result = result.replace(/\$\{device_identification\}/g, params.deviceIdentification || '');
  result = result.replace(/\$\{device_sdk_version\}/g, params.deviceSdkVersion || '');
  result = result.replace(/\$\{product_identification\}/g, params.productIdentification || '');

  return result;
};