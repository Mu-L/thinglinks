import { BaseEvent, EventLife, InteractEvents, InteractEventOn, InteractActionsType } from '@/enums/eventEnum'
import type { GlobalThemeJsonType } from '@/settings/chartThemes/index'
import type { RequestConfigType } from '@/store/modules/chartEditStore/chartEditStore.d'

export enum ChartFrameEnum {
  // 支持 dataset 的 echarts 框架
  ECHARTS = 'echarts',
  // UI 组件框架
  NAIVE_UI = 'naiveUI',
  // 自定义带数据组件
  COMMON = 'common',
  // 无数据变更
  STATIC = 'static'
}

// 组件配置
export type ConfigType = {
  // 组件 key
  key: string
  // 画布组件 key
  chartKey: string
  // 右侧设置面板组件 key
  conKey: string
  // 标题
  title: string
  // 分类
  category: string
  // 分类名称
  categoryName: string
  // 所属包
  package: string
  // 归类
  chartFrame?: ChartFrameEnum
  // 预览图
  image: string
  // 从指定路径创建创建该组件
  redirectComponent?: string
  // 组件预设的 dataset 值(图片/图标)
  dataset?: any
  // 禁用 拖拽或双击生成组件
  disabled?: boolean
  // 图标
  icon?: string
  // 事件
  configEvents?: { [T: string]: Function }
}

// 数据请求
interface requestConfig {
  request: RequestConfigType
}

// Echarts 数据类型
interface EchartsDataType {
  dimensions: string[]
  source: any[]
}

// 组件状态
export interface StatusType {
  lock: boolean
  hide: boolean
}

// 滤镜/变换枚举
export enum FilterEnum {
  // 是否启用
  FILTERS_SHOW = 'filterShow',

  // 透明度
  OPACITY = 'opacity',
  // 饱和度
  SATURATE = 'saturate',
  // 对比度
  CONTRAST = 'contrast',
  // 色相
  HUE_ROTATE = 'hueRotate',
  // 亮度
  BRIGHTNESS = 'brightness',

  // 旋转
  ROTATE_Z = 'rotateZ',
  ROTATE_X = 'rotateX',
  ROTATE_Y = 'rotateY',

  // 倾斜
  SKEW_X = 'skewX',
  SKEW_Y = 'skewY',

  // 混合模式
  BLEND_MODE = 'blendMode'
}

// 获取混合模式列表（支持多语言）
export const getBlendModeEnumList = (): Array<{ label: string; value: string }> => {
  const t = window['$t'] || ((key: string) => key)
  return [
    { label: t('project.blend_normal'), value: 'normal' },
    { label: t('project.blend_multiply'), value: 'multiply' },
    { label: t('project.blend_overlay'), value: 'overlay' },
    { label: t('project.blend_screen'), value: 'screen' },
    { label: t('project.blend_darken'), value: 'darken' },
    { label: t('project.blend_lighten'), value: 'lighten' },
    { label: t('project.blend_color_dodge'), value: 'color-dodge' },
    { label: t('project.blend_color_burn'), value: 'color-burn' },
    { label: t('project.blend_hard_light'), value: 'hard-light' },
    { label: t('project.blend_soft_light'), value: 'soft-light' },
    { label: t('project.blend_difference'), value: 'difference' },
    { label: t('project.blend_exclusion'), value: 'exclusion' },
    { label: t('project.blend_hue'), value: 'hue' },
    { label: t('project.blend_saturation'), value: 'saturation' },
    { label: t('project.blend_color'), value: 'color' },
    { label: t('project.blend_luminosity'), value: 'luminosity' }
  ]
}

// 组件实例类
export interface PublicConfigType {
  id: string
  isGroup: boolean
  attr: { x: number; y: number; w: number; h: number; zIndex: number; offsetX: number; offsetY: number }
  styles: {
    [FilterEnum.FILTERS_SHOW]: boolean
    [FilterEnum.OPACITY]: number
    [FilterEnum.SATURATE]: number
    [FilterEnum.CONTRAST]: number
    [FilterEnum.HUE_ROTATE]: number
    [FilterEnum.BRIGHTNESS]: number

    [FilterEnum.ROTATE_Z]: number
    [FilterEnum.ROTATE_X]: number
    [FilterEnum.ROTATE_Y]: number

    [FilterEnum.SKEW_X]: number
    [FilterEnum.SKEW_Y]: number
    [FilterEnum.BLEND_MODE]: string
    // 动画
    animations: string[]
  }
  preview?: {
    // 预览超出隐藏
    overFlowHidden?: boolean
  }
  filter?: string
  status: StatusType
  interactActions?: InteractActionsType[]
  events: {
    baseEvent: {
      [K in BaseEvent]?: string
    }
    advancedEvents: {
      [K in EventLife]?: string
    }
    interactEvents: {
      [InteractEvents.INTERACT_ON]: InteractEventOn | undefined
      [InteractEvents.INTERACT_COMPONENT_ID]: string | undefined
      [InteractEvents.INTERACT_FN]: { [name: string]: string }
    }[]
  }
}

export interface CreateComponentType extends PublicConfigType, requestConfig {
  key: string
  chartConfig: ConfigType
  option: GlobalThemeJsonType
  groupList?: Array<CreateComponentType>
}

// 组件成组实例类
export interface CreateComponentGroupType extends CreateComponentType {
  groupList: Array<CreateComponentType>
}

// 获取组件实例类中某个key对应value类型的方法
export type PickCreateComponentType<T extends keyof CreateComponentType> = Pick<CreateComponentType, T>[T]

// 包分类枚举
export enum PackagesCategoryEnum {
  CHARTS = 'Charts',
  TABLES = 'Tables',
  INFORMATIONS = 'Informations',
  PHOTOS = 'Photos',
  ICONS = 'Icons',
  DECORATES = 'Decorates'
}

// 包分类名称（获取多语言版本）
export const getPackagesCategoryName = () => {
  const t = window['$t'] || ((key: string) => key)
  return {
    CHARTS: t('project.package_charts'),
    TABLES: t('project.package_tables'),
    INFORMATIONS: t('project.package_informations'),
    PHOTOS: t('project.package_photos'),
    ICONS: t('project.package_icons'),
    DECORATES: t('project.package_decorates')
  }
}

// 获取组件
export enum FetchComFlagType {
  VIEW,
  CONFIG
}

// 图表包类型
export type PackagesType = {
  [PackagesCategoryEnum.CHARTS]: ConfigType[]
  [PackagesCategoryEnum.INFORMATIONS]: ConfigType[]
  [PackagesCategoryEnum.TABLES]: ConfigType[]
  [PackagesCategoryEnum.PHOTOS]: ConfigType[]
  [PackagesCategoryEnum.ICONS]: ConfigType[]
  [PackagesCategoryEnum.DECORATES]: ConfigType[]
}
