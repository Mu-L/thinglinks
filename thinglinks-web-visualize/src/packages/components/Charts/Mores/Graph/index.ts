import { ConfigType, PackagesCategoryEnum, ChartFrameEnum } from '@/packages/index.d'
import { ChatCategoryEnum, getChatCategoryEnumName } from '../../index.d'

export const GraphConfig: ConfigType = {
  key: 'Graph',
  chartKey: 'VGraph',
  conKey: 'VCGraph',
  title: 'project.component_graph',
  category: ChatCategoryEnum.MORE,
  categoryName: getChatCategoryEnumName().MORE,
  package: PackagesCategoryEnum.CHARTS,
  chartFrame: ChartFrameEnum.COMMON,
  image: 'graph.png'
}
