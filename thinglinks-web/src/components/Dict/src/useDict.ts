/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * No deletion without permission, or be held responsible to law.
 * @author ThinkGem
 */
import { isEmpty, isNullOrUnDef } from '/@/utils/is';
import { useI18n } from '/@/hooks/web/useI18n';
import { useDictStore } from '/@/store/modules/dict';
import { i18n } from '/@/locales/setupI18n';

const { t } = useI18n();

export function useDict() {
  // 在函数内部获取 dictStore，避免在模块加载时就调用
  const dictStore = useDictStore();

  // 初始化动作，迁移到 dictStore 了，页面刷新时，执行一次全局加载
  function getDictList(dictType: string): Recordable[] {
    return dictStore.getDict(dictType);
  }

  async function getDicListUpgrade(dictType: string) {
    return getDictList(dictType);
  }

  /**
   * 根据字典项标识，拼接字典项的显示名称
   *
   * 1. 根据字典类型查找字典项列表
   * 2. 在字典项列表中，使用字典项标识进行匹配，能匹配上的，
   * 2.1 优先取字典项中的 name 字段
   * 2.2 若配置了 i18nJson， 则根据当前语言环境，取i18nJson中的值
   * 3. 若字典项列表中不存在 value 值， 则返回 defaultValue 默认值
   *
   * @param dictType 字典类型
   * @param value 字典项标识
   * @param defaultValue 默认值
   */
  function getDictLabel(dictType: string, value?: string, defaultValue = t('未知')): string {
    const result: string[] = [];
    for (const item of getDictList(dictType)) {
      if (item && (',' + value + ',').includes(',' + item.key + ',')) {
        // 默认取name， 若后端配置了i18n，就显示i8n值
        let label = item.name;
        if (!isNullOrUnDef(item.i18nJson) && !isEmpty(item.i18nJson)) {
          try {
            const i18nJson = JSON.parse(item.i18nJson);
            label = i18nJson[(i18n.global.locale as any).value];
          } catch {}
        }
        result.push(label);
      }
    }
    return result.length > 0 ? result.join(',') : defaultValue;
  }

  async function initGetDictList(dictType: string): Promise<Recordable[]> {
    return getDictList(dictType);
  }

  /**
   * 异步获取字典列表，确保数据已加载
   * @param dictType 字典类型
   */
  async function getDictListAsync(dictType: string): Promise<Recordable[]> {
    // 如果数据已存在，直接返回
    const existData = dictStore.getDict(dictType);
    if (existData.length > 0) {
      return existData;
    }

    // 如果正在加载，等待完成
    if (dictStore.loadingPromise) {
      await dictStore.loadingPromise;
    }

    return dictStore.getDict(dictType);
  }

  return {
    getDictList,
    getDictLabel,
    initGetDictList,
    getDicListUpgrade,
    getDictListAsync,
  };
}
