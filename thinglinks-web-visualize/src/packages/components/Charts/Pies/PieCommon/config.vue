<template>
  <!-- Echarts 全局设置 -->
  <global-setting :optionData="optionData"></global-setting>
  <CollapseItem :name="t('components.charts.pieConfig')" :expanded="true">
    <SettingItemBox :name="t('components.common.type')">
      <SettingItem>
        <n-select v-model:value="optionData.type" size="small" :options="fontWeightOptions" />
      </SettingItem>
    </SettingItemBox>
    <SettingItemBox :name="t('components.common.animation')" :alone="true">
      <SettingItem>
        <n-space>
          <n-switch v-model:value="optionData.isCarousel" size="small"></n-switch>
          <n-text>{{ t('components.common.enableText') }}<n-text :depth="3">{{ t('components.common.willAutoHideLegend') }}</n-text></n-text>
        </n-space>
      </SettingItem>
      <SettingItem>
        <n-text :depth="3">{{ t('components.common.noMouseClickLegend') }}</n-text>
      </SettingItem>
    </SettingItemBox>
    <SettingItemBox :name="t('components.common.label')">
      <SettingItem>
        <n-space>
          <n-switch v-model:value="optionData.series[0].label.show" size="small"></n-switch>
          <n-text>{{ t('components.common.showLabel') }}</n-text>
        </n-space>
      </SettingItem>
      <setting-item>
        <n-space>
          <n-switch v-model:value="optionData.series[0].labelLine.show" size="small"></n-switch>
          <n-text>{{ t('components.common.guideLine') }}</n-text>
        </n-space>
      </setting-item>
      <SettingItem :name="t('components.common.position')">
        <n-select v-model:value="optionData.series[0].label.position" size="small" :options="labelConfig.position" />
      </SettingItem>
      <setting-item :name="t('components.common.formatter')">
        <n-select v-model:value="optionData.series[0].label.formatter" size="small" :options="labelFormatterOptions" />
      </setting-item>
    </SettingItemBox>
    <setting-item-box :name="t('components.common.borderRadius')">
      <setting-item>
        <n-space>
          <n-input-number
            v-model:value="optionData.series[0].itemStyle.borderRadius"
            size="small"
            :min="0"
          ></n-input-number>
          <n-text>{{ t('components.common.borderRadiusSize') }}</n-text>
        </n-space>
      </setting-item>
      <setting-item>
        <n-space>
          <n-input-number
            v-model:value="optionData.series[0].itemStyle.borderWidth"
            size="small"
            :min="0"
          ></n-input-number>
          <n-text>{{ t('components.common.lineWidth') }}</n-text>
        </n-space>
      </setting-item>
    </setting-item-box>
  </CollapseItem>
</template>

<script setup lang="ts">
import { PropType, watch } from 'vue'
import { GlobalThemeJsonType } from '@/settings/chartThemes/index'
import { GlobalSetting, CollapseItem, SettingItemBox, SettingItem } from '@/components/Pages/ChartItemSetting'
import { PieTypeObject, PieTypeEnum } from './config'
import { labelConfig } from '@/packages/chartConfiguration/echarts'

const props = defineProps({
  optionData: {
    type: Object as PropType<GlobalThemeJsonType>,
    required: true
  }
})
const fontWeightOptions = [
  {
    label: PieTypeEnum.NORMAL,
    value: PieTypeObject[PieTypeEnum.NORMAL]
  },
  {
    label: PieTypeEnum.RING,
    value: PieTypeObject[PieTypeEnum.RING]
  },
  {
    label: PieTypeEnum.ROSE,
    value: PieTypeObject[PieTypeEnum.ROSE]
  }
]

const t = window['$t']

const labelFormatterOptions = [
  { label: t('components.common.dataName'), value: '{b}' },
  { label: t('components.common.percentage'), value: '{d}' },
  { label: t('components.common.namePercentage'), value: '{b}:{d}%' }
]
</script>
