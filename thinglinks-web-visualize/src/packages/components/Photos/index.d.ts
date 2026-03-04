export enum ChatCategoryEnum {
  PRIVATE = 'Private',
  SHARE = 'Share'
}

// 获取分类名称（支持多语言）
export const getChatCategoryEnumName = () => {
  const t = window['$t'] || ((key: string) => key)
  return {
    PRIVATE: t('project.category_private_image'),
    SHARE: t('project.category_share_image')
  }
}
