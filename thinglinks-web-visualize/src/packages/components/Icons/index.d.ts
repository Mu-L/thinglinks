export enum ChatCategoryEnum {
  ML = 'MaterialLine',
  COMMON = 'Common',
  WEATHER = 'Weather',
  DEFAULT = 'Default' // 这个仅用来表示组件分类目录，不要在 index.ts 中导入
}

// 获取分类名称（支持多语言）
export const getChatCategoryEnumName = () => {
  const t = window['$t'] || ((key: string) => key)
  return {
    ML: t('project.category_icon_ml'),
    COMMON: t('project.category_icon_common'),
    WEATHER: t('project.category_icon_weather'),
    DEFAULT: t('project.category_icon_default')
  }
}
