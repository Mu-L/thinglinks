<template>
  <n-divider style="margin: 10px 0;" ></n-divider>
  <n-space :size="8" justify="space-between" style="margin-top: 10px;">
    <n-button
      secondary
      v-for="item in positionList"
      :key="item.key"
      @click="positonHandle(item.key)"
    >
      <template #icon>
        <component :is="item.icon" ></component>
      </template>
    </n-button>
  </n-space>
  <setting-item-box :name="t('project.setting_position')">
    <n-input-number
      v-model:value="chartAttr.y"
      :min="0"
      size="small"
      placeholder="px"
    >
      <template #prefix>
        <n-text depth="3">{{ t('project.setting_position_top') }}</n-text>
      </template>
    </n-input-number>
    <n-input-number
      v-model:value="chartAttr.x"
      :min="0"
      size="small"
      placeholder="px"
    >
      <template #prefix>
        <n-text depth="3">{{ t('project.setting_position_left') }}</n-text>
      </template>
    </n-input-number>
  </setting-item-box>
</template>

<script setup lang="ts">
import { PropType, computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { PickCreateComponentType } from '@/packages/index.d'
import { SettingItemBox } from '@/components/Pages/ChartItemSetting'
import { renderIcon } from '@/utils'
import { icon } from '@/plugins/index'
import { EditCanvasConfigType } from '@/store/modules/chartEditStore/chartEditStore.d'

const { t } = useI18n()

const {
  AlignHorizontalLeftIcon,
  AlignVerticalCenterIcon,
  AlignVerticalTopIcon,
  AlignHorizontalCenterIcon,
  AlignHorizontalRightIcon,
  AlignVerticalBottomIcon
} = icon.carbon

const positionList = computed(() => [
  {
    key: 'AlignHorizontalLeftIcon',
    lable: t('project.setting_position_align_left'),
    icon: renderIcon(AlignHorizontalLeftIcon)
  },
  {
    key: 'AlignVerticalCenterIcon',
    lable: t('project.setting_position_align_center_x'),
    icon: renderIcon(AlignVerticalCenterIcon)
  },
  {
    key: 'AlignHorizontalRightIcon',
    lable: t('project.setting_position_align_right'),
    icon: renderIcon(AlignHorizontalRightIcon)
  },
  {
    key: 'AlignVerticalTopIcon',
    lable: t('project.setting_position_align_top'),
    icon: renderIcon(AlignVerticalTopIcon)
  },
  {
    key: 'AlignHorizontalCenterIcon',
    lable: t('project.setting_position_align_center_y'),
    icon: renderIcon(AlignHorizontalCenterIcon)
  },
  {
    key: 'AlignVerticalBottomIcon',
    lable: t('project.setting_position_align_bottom'),
    icon: renderIcon(AlignVerticalBottomIcon)
  }
])

const props = defineProps({
  canvasConfig: {
    type: Object as PropType<EditCanvasConfigType>,
    required: true
  },
  chartAttr: {
    type: Object as PropType<PickCreateComponentType<'attr'>>,
    required: true
  }
})

const positonHandle = (key: string) => {
  switch (key) {
    // 局左
    case positionList.value[0]['key']:
      props.chartAttr.x = 0
      break
    // X轴居中
    case positionList.value[1]['key']:
      props.chartAttr.y = (props.canvasConfig.height - props.chartAttr.h) / 2
      break
    // 局右
    case positionList.value[2]['key']:
      props.chartAttr.x = props.canvasConfig.width - props.chartAttr.w
      break
    // 顶部
    case positionList.value[3]['key']:
      props.chartAttr.y = 0
      break
    // Y轴居中
    case positionList.value[4]['key']:
      props.chartAttr.x = (props.canvasConfig.width - props.chartAttr.w) / 2
      break
    // 底部
    case positionList.value[5]['key']:
      props.chartAttr.y = props.canvasConfig.height - props.chartAttr.h
      break
  }
}
</script>
