/* eslint-disable no-unused-vars */
export enum ChatCategoryEnum {
  BAR = 'Bars',
  PIE = 'Pies',
  LINE = 'Lines',
  SCATTER = 'Scatters',
  MAP = 'Maps',
  MORE = 'Mores'
}

// 获取分类名称（支持多语言）
export const getChatCategoryEnumName = () => {
  const t = window['$t'] || ((key: string) => key)
  return {
    BAR: t('project.category_bar'),
    PIE: t('project.category_pie'),
    LINE: t('project.category_line'),
    SCATTER: t('project.category_scatter'),
    MAP: t('project.category_map'),
    MORE: t('project.category_chart_more')
  }
}
