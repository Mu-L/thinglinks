import type { RouteLocationNormalized, RouteRecordNormalized } from 'vue-router';
import type { App, Component } from 'vue';

import { unref } from 'vue';
import { isObject } from '/@/utils/is';
import { cloneDeep } from 'lodash-es';

export const noop = () => {};

/**
 * @description:  Set ui mount node
 */
export function getPopupContainer(node?: HTMLElement): HTMLElement {
  return (node?.parentNode as HTMLElement) ?? document.body;
}

/**
 * Add the object as a parameter to the URL
 * @param baseUrl url
 * @param obj
 * @returns {string}
 * eg:
 *  let obj = {a: '3', b: '4'}
 *  setObjToUrlParams('www.baidu.com', obj)
 *  ==>www.baidu.com?a=3&b=4
 */
export function setObjToUrlParams(baseUrl: string, obj: any): string {
  let parameters = '';
  for (const key in obj) {
    parameters += key + '=' + encodeURIComponent(obj[key]) + '&';
  }
  parameters = parameters.replace(/&$/, '');
  return /\?$/.test(baseUrl) ? baseUrl + parameters : baseUrl.replace(/\/?$/, '?') + parameters;
}

// 深度合并
export function deepMerge<T = any>(src: any = {}, target: any = {}): T {
  let key: string;
  const res: any = cloneDeep(src);
  for (key in target) {
    res[key] = isObject(res[key]) ? deepMerge(res[key], target[key]) : (res[key] = target[key]);
  }
  return res;
}

export function openWindow(
  url: string,
  opt?: { target?: TargetContext | string; noopener?: boolean; noreferrer?: boolean },
) {
  const { target = '__blank', noopener = true, noreferrer = true } = opt || {};
  const feature: string[] = [];

  noopener && feature.push('noopener=yes');
  noreferrer && feature.push('noreferrer=yes');

  window.open(url, target, feature.join(','));
}

// dynamic use hook props
export function getDynamicProps<T extends Record<string, unknown>, U>(props: T): Partial<U> {
  const ret: Recordable = {};

  Object.keys(props).map((key) => {
    ret[key] = unref((props as Recordable)[key]);
  });

  return ret as Partial<U>;
}

export function getRawRoute(route: RouteLocationNormalized): RouteLocationNormalized {
  if (!route) return route;
  const { matched, ...opt } = route;
  return {
    ...opt,
    matched: (matched
      ? matched.map((item) => ({
          meta: item.meta,
          name: item.name,
          path: item.path,
        }))
      : undefined) as RouteRecordNormalized[],
  };
}

// https://github.com/vant-ui/vant/issues/8302
type EventShim = {
  new (...args: any[]): {
    $props: {
      onClick?: (...args: any[]) => void;
    };
  };
};

export type WithInstall<T> = T & {
  install(app: App): void;
} & EventShim;

export type CustomComponent = Component & { displayName?: string };

export const withInstall = <T extends CustomComponent>(component: T, alias?: string) => {
  (component as Record<string, unknown>).install = (app: App) => {
    const compName = component.name || component.displayName;
    if (!compName) return;
    app.component(compName, component);
    if (alias) {
      app.config.globalProperties[alias] = component;
    }
  };
  return component as WithInstall<T>;
};

export function randomNum(len: number, radix: number) {
  const chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
  const uuid = [] as string[];
  radix = radix || chars.length;

  if (len) {
    // Compact form
    for (let i = 0; i < len; i++) {
      uuid[i] = chars[0 | (Math.random() * radix)];
    }
  } else {
    // rfc4122, version 4 form
    let r;

    // rfc4122 requires these characters
    uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
    uuid[14] = '4';

    // Fill in random data.  At i==19 set the high bits of clock sequence as
    // per rfc4122, sec. 4.1.5
    for (let i = 0; i < 36; i++) {
      if (!uuid[i]) {
        r = 0 | (Math.random() * 16);
        uuid[i] = chars[i === 19 ? (r & 0x3) | 0x8 : r];
      }
    }
  }
  return uuid.join('') + new Date().getTime();
}

/**
 * Parse the time to string
 * @param {(Object|string|number)} time
 * @param {string} cFormat
 * @returns {string}
 */
export function parseTime(time, cFormat) {
  if (arguments.length === 0) {
    return null;
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}';
  let date;
  if (typeof time === 'object') {
    date = time;
  } else {
    if (typeof time === 'string' && /^[0-9]+$/.test(time)) {
      time = parseInt(time);
    }
    if (typeof time === 'number' && time.toString().length === 10) {
      time = time * 1000;
    }
    date = new Date(time);
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay(),
  };
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key];
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value];
    }
    if (result.length > 0 && value < 10) {
      value = '0' + value;
    }
    return value || 0;
  });
  return time_str;
}

/**
 * 延迟执行事件
 * @param {number} delay 延迟时间
 */
export function delay(delay) {
  return new Promise((resolve) => {
    setTimeout(() => resolve(), delay);
  });
}
/**
 * 将给定值转换为指定类型
 *
 * @param type 目标类型，包括'string'、'int'、'decimal'、'DateTime'、'jsonObject'、'timestamp'、'bool'
 * @param value 要转换的值
 * @returns 转换后的值，如果转换失败则返回错误信息
 */
export function convertToType(type, value) {
  switch (type) {
    case 'string':
      return String(value);
    case 'int':
      const intValue = parseInt(value, 10);
      if (isNaN(intValue)) throw new Error('Cannot convert to int');
      return intValue;
    case 'decimal':
      const decimalValue = parseFloat(value);
      if (isNaN(decimalValue)) throw new Error('Cannot convert to decimal');
      return decimalValue;
    case 'DateTime':
      // 检查字符串是否是一个有效的时间戳（数字字符串）
      const dateIntValue = parseInt(value, 10);
      if (!isNaN(dateIntValue) && dateIntValue > 0) {
        return new Date(dateIntValue);
      }
      // 检查字符串是否是一个有效的日期格式
      const dateValue = new Date(value);
      if (isNaN(dateValue.getTime())) throw new Error('Cannot convert to DateTime');
      return dateValue;
    case 'jsonObject':
      try {
        if (typeof value === 'string') {
          return JSON.parse(value);
        }
        return value;
      } catch (error) {
        throw new Error('Cannot convert to jsonObject');
      }
    case 'timestamp':
      // 检查字符串是否是一个有效的时间戳（数字字符串）
      const timestampIntValue = parseInt(value, 10);
      if (!isNaN(timestampIntValue) && timestampIntValue > 0) {
        return timestampIntValue;
      }
      // 检查字符串是否是一个有效的日期格式
      const timestampDateValue = Date.parse(value);
      if (isNaN(timestampDateValue)) throw new Error('Cannot convert to timestamp');
      return timestampDateValue;
    case 'bool':
      if (value.toLowerCase() === 'true') return true;
      if (value.toLowerCase() === 'false') return false;
      throw new Error('Cannot convert to bool');
    default:
      throw new Error('Unknown type');
  }
}
/**
 * 判断传入的参数值是否可以成功转换为指定类型
 * @param {'string' | 'int' | 'decimal' | 'DateTime' | 'jsonObject' | 'timestamp' | 'bool'} type
 * @param { string } value
 * @returns { boolean }
 */
export const canConvertType = (type, value) => {
  try {
    switch (type) {
      case 'string':
        return typeof value === 'string';
      case 'int':
        const intValue = parseInt(value, 10);
        return !isNaN(intValue);
      case 'decimal':
        const decimalValue = parseFloat(value);
        return !isNaN(decimalValue);
      case 'DateTime':
        // 检查字符串是否是一个有效的时间戳（数字字符串）
        const dateIntValue = parseInt(value, 10);
        if (!isNaN(dateIntValue) && dateIntValue > 0) {
          return true;
        }
        // 检查字符串是否是一个有效的日期格式
        const dateValue = new Date(value);
        return !isNaN(dateValue.getTime());
      case 'jsonObject':
        if (typeof value === 'string') {
          return JSON.parse(value);
        }
        return typeof value === 'object';
      case 'timestamp':
        // 检查字符串是否是一个有效的时间戳（数字字符串）
        const timestampIntValue = parseInt(value, 10);
        if (!isNaN(timestampIntValue) && timestampIntValue > 0) {
          return true;
        }
        // 检查字符串是否是一个有效的日期格式
        const timestampDateValue = Date.parse(value);
        return !isNaN(timestampDateValue);
      case 'bool':
        return value.toLowerCase() === 'true' || value.toLowerCase() === 'false';
      default:
        return false;
    }
  } catch (error) {
    return false;
  }
};
/**
 * 判断当前传入的参数是否为空,参数如果是基础类型，则返回原值， 如果是对象或数组类型，则需要判断对象中每个属性是否为空，为空的话则移除此属性
 * @param {any} input - 传入参数
 */
export const cleanInput = (input: any) => {
  if (input === null || input === undefined) {
    return input;
  }

  if (
    typeof input === 'string' ||
    typeof input === 'number' ||
    typeof input === 'boolean' ||
    input instanceof Date
  ) {
    return input;
  }

  if (Array.isArray(input)) {
    return input.filter((item) => !isEmpty(item)).map((item) => cleanInput(item));
  }

  if (typeof input === 'object') {
    const cleanedObject = {};
    for (const key in input) {
      if (input.hasOwnProperty(key) && !isEmpty(input[key])) {
        cleanedObject[key] = cleanInput(input[key]);
      }
    }
    return cleanedObject;
  }

  return input;
};

/**
 * 判断值是否为空
 * @param {any} value - 要判断的值
 * @returns {boolean} - 是否为空
 */
export const isEmpty = (value: any): Boolean => {
  if (value === null || value === undefined) {
    return true;
  }

  if (typeof value === 'string') {
    return value.trim() === '';
  }

  if (typeof value === 'number') {
    return value === 0 || isNaN(value);
  }

  if (typeof value === 'boolean') {
    return false;
  }

  if (value instanceof Date) {
    return isNaN(value.getTime());
  }

  if (Array.isArray(value)) {
    return value.length === 0;
  }

  if (typeof value === 'object') {
    return Object.keys(value).length === 0;
  }

  return false;
};

/**
 * 判断传入的参数值是否在指定范围内
 * @param {'string' | 'int' | 'decimal' | 'DateTime' | 'jsonObject' | 'timestamp' | 'bool'} type
 * @param { string } value
 * @param { int } min
 * @param { int } max
 * @returns { boolean }
 */
export const isWithinScope = (type: string, value: string, min: any, max: any): boolean => {
  // 不是int跟decimal类型，则不进行范围判断
  if (type != 'int' && type != 'decimal') {
    return true;
  }
  // 判断是否在指定范围内
  if ((min || min === 0) && (max || max === 0)) {
    const intValue = parseInt(value, 10);
    if (intValue < min || intValue > max) {
      return false;
    }
  }
  return true;
};

/**
 * 判断传入的参数值长度是否超过最大长度
 * @param {'string' | 'int' | 'decimal' | 'DateTime' | 'jsonObject' | 'timestamp' | 'bool'} type
 * @param { string } value
 * @param { int } maxlength
 * @returns { boolean }
 */
export const isExceedMaxLength = (type: string, value: string, maxlength: any): boolean => {
  if (type != 'int' && type != 'decimal' && type != 'string') {
    return true;
  }
  if (maxlength || maxlength === 0) {
    if (value.length > maxlength) {
      return false;
    }
  }
  return true;
};
