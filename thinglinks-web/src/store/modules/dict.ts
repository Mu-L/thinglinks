import { defineStore } from 'pinia';
import { findDictMapByType } from '/@/api/thinglinks/common/general';
import { DefDictItemResultVO } from '/@/api/devOperation/system/model/defDictItemModel';
import { store } from '/@/store';
import { isEmptyOrUnDef, isNullOrUnDef } from '/@/utils/is';
import { DictDataTypeEnum } from '/@/enums/biz/tenant';
import { i18n } from '/@/locales/setupI18n';

type NestedObject = {
  [key: string]: NestedObject | null | string;
};

// 字典存储接口
interface DictState {
  registeredTypes: string[]; // 注册的字典类型
  dictData: Record<string, DefDictItemResultVO[]>; // 字典数据
  dictMap: Record<string, Record<string, DefDictItemResultVO>>; // 字典数据
  loadingPromise: Promise<Record<string, DefDictItemResultVO[]>> | null; // 加载中的 Promise
}

export const useDictStore = defineStore('app-dict', {
  state: (): DictState => ({
    registeredTypes: [],
    dictData: {},
    dictMap: {},
    loadingPromise: null,
  }),

  getters: {
    // 获取指定类型的字典项列表数据
    getDictItemList: (state) => (type: string) => {
      return state.dictData[type] || [];
    },

    /**
     * 按照下拉框options格式返回
     * @param state
     * @returns 返回可以提供给select、radiogroup等组件直接使用的字典项列表
     */
    getDictItemOptionList: (state) => (type: string, locale?: string, disableInvalid?: boolean) => {
      const dictItemList = state.dictData[type] || [];
      return dictItemList.map((item) => {
        let label = item.name;
        let value: boolean | number | string = item.key as string;
        if (item.dataType === DictDataTypeEnum.INTEGER) {
          value = Number.isNaN(value) ? (item.key as string) : Number(value);
        } else if (item.dataType === DictDataTypeEnum.BOOLEAN) {
          value = ['1', 1, true, 'true'].includes(value);
        } else {
          value = String(value);
        }
        if (isEmptyOrUnDef(locale)) {
          locale = (i18n.global.locale as any).value;
        }

        if (!isEmptyOrUnDef(item.i18nJson) && !isEmptyOrUnDef(locale)) {
          try {
            const i18nJson = JSON.parse(item.i18nJson);
            label = i18nJson[locale];
            if (isEmptyOrUnDef(label)) {
              label = item.name;
            }
          } catch {}
        }
        return {
          label,
          value,
          disabled: disableInvalid ? false : !item.state,
          ...item,
        };
      });
    },

    // 获取指定类型的字典数据
    getDict: (state) => (type: string) => {
      return state.dictData[type] || [];
    },

    // 检查字典是否已加载
    isLoaded: (state) => (type: string) => {
      return !!state.dictData[type] && state.dictData[type].length > 0;
    },
  },

  actions: {
    extractValues(obj: NestedObject): string[] {
      const result: string[] = [];

      for (const key in obj) {
        const value = obj[key];

        // 排除 null 和非对象类型
        if (!isNullOrUnDef(value)) {
          if (typeof value === 'object') {
            result.push(...this.extractValues(value));
          } else {
            result.push(value.toString());
          }
        }
      }

      return result;
    },
    // 注册字典类型
    registerDictType(obj: NestedObject) {
      this.registerDictTypes(this.extractValues(obj));
    },

    // 注册字典类型
    registerDictTypes(types: string | string[]) {
      const typeList = Array.isArray(types) ? types : [types];

      typeList.forEach((type) => {
        if (!this.registeredTypes.includes(type)) {
          this.registeredTypes.push(type);
        }
      });
    },

    // 批量加载所有注册的字典
    loadAllRegisteredDicts(): Promise<Record<string, DefDictItemResultVO[]>> {
      if (this.registeredTypes.length === 0) {
        return Promise.resolve({});
      }

      // 如果已经在加载中，返回现有的 Promise
      if (this.loadingPromise) {
        return this.loadingPromise;
      }

      const params = this.registeredTypes.map((type) => {
        return { type, extendFirst: true };
      });

      // 保存 Promise，让外部可以 await
      this.loadingPromise = findDictMapByType(params)
        .then((res) => {
          this.dictData = res;

          const map: Record<string, Record<string, DefDictItemResultVO>> = {};
          for (const type in res) {
            const itemList = res[type];
            if (!itemList) {
              continue;
            }

            const itemMap: Record<string, DefDictItemResultVO> = {};
            for (const item of itemList) {
              itemMap[item.key as string] = item;
            }

            map[type] = itemMap;
          }

          this.dictMap = map;
          return res;
        })
        .catch((error) => {
          console.error(`加载字典失败:`, error);
          this.loadingPromise = null; // 失败后允许重试
          return {};
        });

      return this.loadingPromise;
    },

    // 清除所有字典
    clearAllDicts() {
      this.dictData = {};
    },
  },
});

// Need to be used outside the setup
export function useDictStoreWithOut() {
  return useDictStore(store);
}
