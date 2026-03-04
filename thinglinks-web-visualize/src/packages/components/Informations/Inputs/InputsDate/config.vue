<template>
  <collapse-item :name="t('components.common.displayMode')" :expanded="true">
    <setting-item-box :name="t('components.common.selectMode')">
      <n-select v-model:value="optionData.isPanel" size="small" :options="panelOptions" />
    </setting-item-box>
  </collapse-item>

  <collapse-item :name="t('components.common.timeConfig')" :expanded="true">
    <setting-item-box :name="t('components.common.basic')">
      <setting-item :name="t('components.common.type')">
        <n-select v-model:value="optionData.componentInteractEventKey" size="small" :options="datePickerTypeOptions" />
      </setting-item>
    </setting-item-box>

    <setting-item-box :name="t('components.common.defaultValue')" :alone="true">
      <n-date-picker size="small" v-model:value="optionData.dataset" :type="optionData.componentInteractEventKey" />
    </setting-item-box>

    <setting-item-box :alone="true">
      <template #name>
        <n-text>{{ t('components.common.dynamic') }}</n-text>
        <n-tooltip trigger="hover">
          <template #trigger>
            <n-icon size="21" :depth="3">
              <help-outline-icon></help-outline-icon>
            </n-icon>
          </template>
          <n-text>{{ t('components.common.dynamicTooltip') }}</n-text>
        </n-tooltip>
      </template>
      <n-input-number v-model:value="optionData.differValue" class="input-num-width" size="small" :min="-40" :max="40">
        <template #suffix> {{ t('components.common.day') }} </template>
      </n-input-number>
    </setting-item-box>
  </collapse-item>
</template>

<script lang="ts" setup>
import { PropType } from 'vue'
import { icon } from '@/plugins'
import { CollapseItem, SettingItemBox, SettingItem } from '@/components/Pages/ChartItemSetting'
import { option } from './config'
import { ComponentInteractEventEnum } from './interact'

const t = window['$t']
const { HelpOutlineIcon } = icon.ionicons5

const props = defineProps({
  optionData: {
    type: Object as PropType<typeof option>,
    required: true
  }
})

const panelOptions = [
  {
    label: t('components.common.dropdown'),
    value: 0
  },
  {
    label: t('components.common.panel'),
    value: 1
  }
]

const datePickerTypeOptions = [
  {
    label: t('components.common.date'),
    value: ComponentInteractEventEnum.DATE
  },
  {
    label: t('components.common.dateTime'),
    value: ComponentInteractEventEnum.DATE_TIME
  },
  {
    label: t('components.common.dateRange'),
    value: ComponentInteractEventEnum.DATE_RANGE
  },
  {
    label: t('components.common.month'),
    value: ComponentInteractEventEnum.MONTH
  },
  {
    label: t('components.common.monthRange'),
    value: ComponentInteractEventEnum.MONTH_RANGE
  },
  {
    label: t('components.common.year'),
    value: ComponentInteractEventEnum.YEAR
  },
  {
    label: t('components.common.yearRange'),
    value: ComponentInteractEventEnum.YEAR_RANGE
  },
  {
    label: t('components.common.quarter'),
    value: ComponentInteractEventEnum.QUARTER
  },
  {
    label: t('components.common.quarterRange'),
    value: ComponentInteractEventEnum.QUARTER_RANGE
  }
]
</script>
