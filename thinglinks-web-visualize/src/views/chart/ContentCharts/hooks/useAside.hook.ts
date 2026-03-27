import { ref, watch, computed } from 'vue'
import { icon } from '@/plugins'
import { renderLang, renderIcon } from '@/utils'
import { themeColor, setItem, getCharts } from './useLayout.hook'
import { PackagesCategoryEnum, ConfigType } from '@/packages/index.d'
import { usePackagesStore } from '@/store/modules/packagesStore/packagesStore'
import { ChartLayoutStoreEnum } from '@/store/modules/chartLayoutStore/chartLayoutStore.d'
// 图标
const { AirPlaneOutlineIcon, ImageIcon, BarChartIcon } = icon.ionicons5
const { TableSplitIcon, RoadmapIcon, SpellCheckIcon, GraphicalDataFlowIcon } = icon.carbon

// 图表
export type MenuOptionsType = {
  key: string
  icon: ReturnType<typeof renderIcon>
  label: ReturnType<typeof renderLang>
  list: ConfigType[]
}

const getPackagesListObj = () => {
  return {
    [PackagesCategoryEnum.CHARTS]: {
      icon: renderIcon(RoadmapIcon),
      label: renderLang('project.package_charts')
    },
    [PackagesCategoryEnum.INFORMATIONS]: {
      icon: renderIcon(SpellCheckIcon),
      label: renderLang('project.package_informations')
    },
    [PackagesCategoryEnum.TABLES]: {
      icon: renderIcon(TableSplitIcon),
      label: renderLang('project.package_tables')
    },
    [PackagesCategoryEnum.DECORATES]: {
      icon: renderIcon(GraphicalDataFlowIcon),
      label: renderLang('project.package_decorates')
    },
    [PackagesCategoryEnum.PHOTOS]: {
      icon: renderIcon(ImageIcon),
      label: renderLang('project.package_photos')
    },
    [PackagesCategoryEnum.ICONS]: {
      icon: renderIcon(AirPlaneOutlineIcon),
      label: renderLang('project.package_icons')
    }
  }
}

export const useAsideHook = () => {
  const packagesStore = usePackagesStore()
  const menuOptions: MenuOptionsType[] = []
  const packagesListObj = getPackagesListObj()

  // 处理列表
  const handlePackagesList = () => {
    for (const val in packagesStore.getPackagesList) {
      menuOptions.push({
        key: val,
        // @ts-ignore
        icon: packagesListObj[val].icon,
        // @ts-ignore
        label: packagesListObj[val].label,
        // @ts-ignore
        list: packagesStore.getPackagesList[val]
      })
    }
  }
  handlePackagesList()

  // 记录选中值
  let beforeSelect: string = menuOptions[0]['key']
  const selectValue = ref<string>(menuOptions[0]['key'])

  // 选中的对象值
  const selectOptions = ref(menuOptions[0])

  // 点击 item
  const clickItemHandle = (key: string, item: any) => {
    selectOptions.value = item
    // 处理折叠
    if (beforeSelect === key) {
      setItem(ChartLayoutStoreEnum.CHARTS, !getCharts.value, false)
    } else {
      setItem(ChartLayoutStoreEnum.CHARTS, true, false)
    }
    beforeSelect = key
  }

  return {
    getCharts,
    BarChartIcon,
    themeColor,
    selectOptions,
    selectValue,
    clickItemHandle,
    menuOptions
  }
}
