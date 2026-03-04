export enum ChatCategoryEnum {
  TABLE = 'Tables',
}

// 获取分类名称（支持多语言）
export const getChatCategoryEnumName = () => {
  const t = window['$t'] || ((key: string) => key)
  return {
    TABLE: t('project.category_table')
  }
}