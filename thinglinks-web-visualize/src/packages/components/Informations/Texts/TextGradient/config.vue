<template>
  <collapse-item :name="t('components.common.info')" :expanded="true">
    <setting-item-box :name="t('components.common.text')" :alone="true">
      <setting-item>
        <n-input v-model:value="optionData.dataset" size="small"></n-input>
      </setting-item>
    </setting-item-box>
  </collapse-item>
  <collapse-item :name="t('components.common.style')" :expanded="true">
    <setting-item-box :name="t('components.common.text')">
      <setting-item :name="t('components.common.fontSize')">
        <n-input-number v-model:value="optionData.size" size="small" :placeholder="t('components.common.enterFontSize')"></n-input-number>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.gradientParams')">
      <setting-item :name="t('components.common.startValue')">
        <n-color-picker size="small" :modes="['hex']" v-model:value="optionData.gradient.from"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.endValue')">
        <n-color-picker size="small" :modes="['hex']" v-model:value="optionData.gradient.to"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.offsetAngle')">
        <n-input-number v-model:value="optionData.gradient.deg" size="small" :placeholder="t('components.common.colorRotation')"></n-input-number>
      </setting-item>
    </setting-item-box>

  </collapse-item>
</template>

<script setup lang="ts">
import { PropType } from 'vue'
import { option } from './config'
import {
  CollapseItem,
  SettingItemBox,
  SettingItem
} from '@/components/Pages/ChartItemSetting'

const t = window['$t']

const props = defineProps({
  optionData: {
    type: Object as PropType<typeof option>,
    required: true
  }
})
</script>
