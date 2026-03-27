<template>
  <collapse-item :name="t('components.common.renderer')">
    <setting-item-box :alone="true">
      <template #name>
        <n-text>{{ t('components.common.global') }}</n-text>
        <n-tooltip trigger="hover">
          <template #trigger>
            <n-icon size="21" :depth="3">
              <help-outline-icon></help-outline-icon>
            </n-icon>
          </template>
          <n-text>{{ t('components.common.globalRendererTooltip') }}</n-text>
        </n-tooltip>
      </template>
      <EchartsRendererSetting v-model="themeSetting.renderer" />
    </setting-item-box>
    <setting-item-box :alone="true">
      <template #name>
        <n-text>{{ t('components.common.current') }}</n-text>
        <n-tooltip trigger="hover">
          <template #trigger>
            <n-icon size="21" :depth="3">
              <help-outline-icon></help-outline-icon>
            </n-icon>
          </template>
          <n-text>{{ t('components.common.currentRendererTooltip') }}</n-text>
        </n-tooltip>
      </template>
      <EchartsRendererSetting v-model="optionData.renderer" includeInherit />
    </setting-item-box>
  </collapse-item>
  <collapse-item v-if="title" :name="t('components.common.title')">
    <template #header>
      <n-switch v-model:value="title.show" size="small"></n-switch>
    </template>
    <setting-item-box :name="t('components.common.title')">
      <setting-item :name="t('components.common.color')">
        <n-color-picker v-model:value="title.textStyle.color" size="small"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.size')">
        <n-input-number v-model:value="title.textStyle.fontSize" :min="1" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.subtitle')">
      <setting-item :name="t('components.common.color')">
        <n-color-picker size="small" v-model:value="title.subtextStyle.color"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.size')">
        <n-input-number v-model:value="title.subtextStyle.fontSize" :min="1" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>
  </collapse-item>

  <collapse-item v-if="grid" :name="t('components.common.container')">
    <setting-item-box :name="t('components.common.distance')">
      <setting-item :name="t('components.common.leftDistance')">
        <n-input v-model:value="grid.left" size="small"></n-input>
      </setting-item>
      <setting-item :name="t('components.common.rightDistance')">
        <n-input v-model:value="grid.right" size="small"></n-input>
      </setting-item>
      <setting-item :name="t('components.common.topDistance')">
        <n-input v-model:value="grid.top" size="small"></n-input>
      </setting-item>
      <setting-item :name="t('components.common.bottomDistance')">
        <n-input v-model:value="grid.bottom" size="small"></n-input>
      </setting-item>
    </setting-item-box>
  </collapse-item>

  <collapse-item v-if="xAxis" :name="t('components.common.xAxis')">
    <template #header>
      <n-switch v-model:value="xAxis.show" size="small"></n-switch>
    </template>
    <setting-item-box :name="t('components.common.unit')">
      <setting-item :name="t('components.common.name')">
        <n-input v-model:value="xAxis.name" size="small"></n-input>
      </setting-item>
      <setting-item :name="t('components.common.color')">
        <n-color-picker size="small" v-model:value="xAxis.nameTextStyle.color"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.size')">
        <n-input-number v-model:value="xAxis.nameTextStyle.fontSize" :min="12" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.offset')">
        <n-input-number v-model:value="xAxis.nameGap" :min="5" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.label')">
      <setting-item :name="t('components.common.show')">
        <n-space>
          <n-switch v-model:value="xAxis.axisLabel.show" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.color')">
        <n-color-picker size="small" v-model:value="xAxis.axisLabel.color"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.size')">
        <n-input-number v-model:value="xAxis.axisLabel.fontSize" :min="8" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.offset')">
        <n-input-number v-model:value="xAxis.axisLabel.rotate" :min="-90" :max="90" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.axis')">
      <setting-item :name="t('components.common.show')">
        <n-space>
          <n-switch v-model:value="xAxis.axisLine.show" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.color')">
        <n-color-picker v-model:value="xAxis.axisLine.lineStyle.color" size="small"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.weight')">
        <n-input-number v-model:value="xAxis.axisLine.lineStyle.width" :min="1" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.position')">
        <n-select v-model:value="xAxis.position" size="small" :options="axisConfig.xposition"></n-select>
      </setting-item>
      <setting-item :name="t('components.common.alignZero')">
        <n-space>
          <n-switch v-model:value="xAxis.axisLine.onZero" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.reverse')">
        <n-space>
          <n-switch v-model:value="xAxis.inverse" size="small"></n-switch>
        </n-space>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.tick')">
      <setting-item :name="t('components.common.show')">
        <n-space>
          <n-switch v-model:value="xAxis.axisTick.show" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.length')">
        <n-input-number v-model:value="xAxis.axisTick.length" :min="1" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.splitLine')">
      <setting-item :name="t('components.common.show')">
        <n-space>
          <n-switch v-model:value="xAxis.splitLine.show" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.color')">
        <n-color-picker v-model:value="xAxis.splitLine.lineStyle.color" size="small"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.weight')">
        <n-input-number v-model:value="xAxis.splitLine.lineStyle.width" :min="1" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.type')">
        <n-select
          v-model:value="xAxis.splitLine.lineStyle.type"
          size="small"
          :options="axisConfig.splitLint.lineStyle.type"
        ></n-select>
      </setting-item>
    </setting-item-box>
  </collapse-item>

  <collapse-item v-if="yAxis" :name="t('components.common.yAxis')">
    <template #header>
      <n-switch v-model:value="yAxis.show" size="small"></n-switch>
    </template>
    <setting-item-box :name="t('components.common.unit')">
      <setting-item :name="t('components.common.name')">
        <n-input v-model:value="yAxis.name" size="small"></n-input>
      </setting-item>
      <setting-item :name="t('components.common.color')">
        <n-color-picker size="small" v-model:value="yAxis.nameTextStyle.color"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.size')">
        <n-input-number v-model:value="yAxis.nameTextStyle.fontSize" :min="8" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.offset')">
        <n-input-number v-model:value="yAxis.nameGap" :min="5" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.label')">
      <setting-item :name="t('components.common.show')">
        <n-space>
          <n-switch v-model:value="yAxis.axisLabel.show" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.color')">
        <n-color-picker size="small" v-model:value="yAxis.axisLabel.color"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.size')">
        <n-input-number v-model:value="yAxis.axisLabel.fontSize" :min="8" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.offset')">
        <n-input-number v-model:value="yAxis.axisLabel.rotate" :min="-90" :max="90" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.axis')">
      <setting-item :name="t('components.common.show')">
        <n-space>
          <n-switch v-model:value="yAxis.axisLine.show" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.color')">
        <n-color-picker v-model:value="yAxis.axisLine.lineStyle.color" size="small"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.weight')">
        <n-input-number v-model:value="yAxis.axisLine.lineStyle.width" :min="1" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.position')">
        <n-select v-model:value="yAxis.position" size="small" :options="axisConfig.yposition"></n-select>
      </setting-item>
      <setting-item :name="t('components.common.alignZero')">
        <n-space>
          <n-switch v-model:value="yAxis.axisLine.onZero" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.reverse')">
        <n-space>
          <n-switch v-model:value="yAxis.inverse" size="small"></n-switch>
        </n-space>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.tick')">
      <setting-item :name="t('components.common.show')">
        <n-space>
          <n-switch v-model:value="yAxis.axisTick.show" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.length')">
        <n-input-number v-model:value="yAxis.axisTick.length" :min="1" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.splitLine')">
      <setting-item :name="t('components.common.show')">
        <n-space>
          <n-switch v-model:value="yAxis.splitLine.show" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.color')">
        <n-color-picker v-model:value="yAxis.splitLine.lineStyle.color" size="small"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.weight')">
        <n-input-number v-model:value="yAxis.splitLine.lineStyle.width" :min="1" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.type')">
        <n-select
          v-model:value="yAxis.splitLine.lineStyle.type"
          size="small"
          :options="axisConfig.splitLint.lineStyle.type"
        ></n-select>
      </setting-item>
    </setting-item-box>
  </collapse-item>

  <collapse-item v-if="legend" :name="t('components.common.legend')">
    <template #header>
      <n-switch v-model:value="legend.show" size="small"></n-switch>
    </template>
    <setting-item-box :name="t('components.common.legendText')">
      <setting-item :name="t('components.common.color')">
        <n-color-picker size="small" v-model:value="legend.textStyle.color"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.size')">
        <n-input-number v-model:value="legend.textStyle.fontSize" :min="1" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.legendPosition')">
      <setting-item :name="t('components.common.xAxis')">
        <n-select v-model:value="legend.x" size="small" :options="legendConfig.lengendX" />
      </setting-item>
      <setting-item :name="t('components.common.yAxis')">
        <n-select v-model:value="legend.y" size="small" :options="legendConfig.lengendY" />
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.legendInfo')">
      <setting-item :name="t('components.common.direction')">
        <n-select v-model:value="legend.orient" size="small" :options="legendConfig.orient" />
      </setting-item>
      <setting-item :name="t('components.common.shape')">
        <n-select v-model:value="legend.icon" size="small" :options="legendConfig.shape" />
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.legendSize')">
      <setting-item :name="t('components.common.width')">
        <n-input-number v-model:value="legend.itemWidth" :min="1" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.height')">
        <n-input-number v-model:value="legend.itemHeight" :min="1" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>
  </collapse-item>

  <collapse-item v-if="visualMap" :name="t('components.common.visualMap')">
    <template #header>
      <n-switch v-model:value="visualMap.show" size="small"></n-switch>
    </template>

    <setting-item-box :name="t('components.common.range')">
      <setting-item :name="t('components.common.minValue')">
        <n-input-number v-model:value="visualMap.min" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.maxValue')">
        <n-input-number v-model:value="visualMap.max" size="small"></n-input-number>
      </setting-item>
    </setting-item-box>

    <setting-item-box :name="t('components.common.color')">
      <setting-item :name="`${t('components.common.level')}-${index + 1}`" v-for="(item, index) in visualMap.inRange.color" :key="index">
        <n-color-picker v-model:value="visualMap.inRange.color[index]" size="small"></n-color-picker>
      </setting-item>
    </setting-item-box>

    <setting-item-box :name="t('components.common.controlBlock')">
      <setting-item :name="t('components.common.placementDirection')">
        <n-select v-model:value="visualMap.orient" size="small" :options="axisConfig.visualMap.orient"></n-select>
      </setting-item>
      <setting-item :name="t('components.common.width')">
        <n-input-number v-model:value="visualMap.itemWidth" :min="5" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.height')">
        <n-input-number v-model:value="visualMap.itemHeight" :min="5" size="small"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.inverse')">
        <n-space>
          <n-switch v-model:value="visualMap.inverse" size="small"></n-switch>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.dragRealtimeUpdate')">
        <n-space>
          <n-switch v-model:value="visualMap.realtime" size="small"></n-switch>
        </n-space>
      </setting-item>
    </setting-item-box>
    <global-setting-position :targetData="visualMap"></global-setting-position>
  </collapse-item>
</template>

<script setup lang="ts">
import { PropType, computed, watch } from 'vue'
import { GlobalThemeJsonType } from '@/settings/chartThemes/index'
import { axisConfig, legendConfig } from '@/packages/chartConfiguration/echarts/index'
import { CollapseItem, SettingItemBox, SettingItem, GlobalSettingPosition } from '@/components/Pages/ChartItemSetting'
import { icon } from '@/plugins'
import { useChartEditStore } from '@/store/modules/chartEditStore/chartEditStore'
import EchartsRendererSetting from './EchartsRendererSetting.vue'

const t = window['$t']
const { HelpOutlineIcon } = icon.ionicons5

const props = defineProps({
  optionData: {
    type: Object as PropType<GlobalThemeJsonType>,
    required: true
  },
  inChart: {
    type: Boolean,
    required: false,
    default: false
  }
})

const chartEditStore = useChartEditStore()
const themeSetting = computed(() => {
  const chartThemeSetting = chartEditStore.getEditCanvasConfig.chartThemeSetting
  return chartThemeSetting
})

const title = computed(() => {
  return props.optionData.title
})

const xAxis = computed(() => {
  return props.optionData.xAxis
})

const yAxis = computed(() => {
  return props.optionData.yAxis
})

const legend = computed(() => {
  return props.optionData.legend
})

const grid = computed(() => {
  return props.optionData.grid
})

const visualMap = computed(() => {
  return props.optionData.visualMap
})

// 监听legend color颜色改变type = scroll的颜色
watch(() => legend.value && legend.value.textStyle.color, (newVal) => {
  if (legend.value && newVal) {
    legend.value.pageTextStyle.color = newVal
  } 
}, {
  immediate: true,
  deep: true,
})
</script>
