<template>
  <!-- Echarts 全局设置 -->
  <global-setting :optionData="optionData"> </global-setting>
  <!-- 漏斗图 -->
  <collapse-item v-for="(item, index) in seriesList" :key="index" :name="t('components.charts.funnelChart')" expanded>
    <setting-item-box :name="t('components.common.sort')" alone>
      <setting-item>
        <n-select v-model:value="item.sort" :options="FunnelOrderEnumList" size="small" />
      </setting-item>
    </setting-item-box>

    <SettingItemBox :name="t('components.common.range')" :alone="true">
      <setting-item :name="`${t('components.special.topDistancePrefix')}${item.top}px`">
        <n-slider v-model:value="item.top" :min="0" :max="300" :format-tooltip="sliderFormatTooltip"></n-slider>
      </setting-item>
    </SettingItemBox>

    <setting-item-box :name="t('components.common.block')">
      <setting-item :name="t('components.common.borderWidth')">
        <n-input-number v-model:value="item.itemStyle.borderWidth" :min="0" :max="10" size="small" />
      </setting-item>
      <setting-item :name="t('components.common.borderColor')">
        <n-color-picker v-model:value="item.itemStyle.borderColor" :modes="['hex']" size="small" />
      </setting-item>
      <setting-item :name="t('components.common.gap')">
        <n-input-number v-model:value="item.gap" :min="0" :max="20" size="small" />
      </setting-item>
    </setting-item-box>

    <setting-item-box :name="t('components.common.label')">
      <setting-item :name="t('components.common.isShow')">
        <n-checkbox v-model:checked="item.label.show" size="small">{{ t('components.common.label') }}</n-checkbox>
      </setting-item>
      <setting-item :name="t('components.common.position')">
        <n-select v-model:value="item.label.position" :options="FunnelLabelPositionEnumList" size="small" />
      </setting-item>
      <setting-item :name="t('components.common.size')">
        <n-input-number v-model:value="item.label.fontSize" :min="0" size="small" />
      </setting-item>
      <setting-item :name="t('components.common.hoverSize')">
        <n-input-number v-model:value="item.emphasis.label.fontSize" :min="0" size="small" />
      </setting-item>
    </setting-item-box>
  </collapse-item>
</template>

<script setup lang="ts">
import { PropType, computed } from 'vue'
import { GlobalSetting, CollapseItem, SettingItemBox, SettingItem } from '@/components/Pages/ChartItemSetting'
import { GlobalThemeJsonType } from '@/settings/chartThemes/index'
import { option, FunnelOrderEnumList, FunnelLabelPositionEnumList } from './config'

const t = window['$t']

const props = defineProps({
  optionData: {
    type: Object as PropType<typeof option & GlobalThemeJsonType>,
    required: true
  }
})

const seriesList = computed(() => {
  return props.optionData.series
})

const sliderFormatTooltip = (v: number) => {
  return `${v}px`
}
</script>
