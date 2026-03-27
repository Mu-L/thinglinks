<template>
  <setting-item-box v-if="targetData" :name="t('components.common.position')">
    <setting-item :name="`${t('components.common.offsetX')}：${targetData.left || 0}px`">
      <n-input-number v-model:value="targetData.left" size="small" step="10"></n-input-number>
    </setting-item>
    <setting-item :name="`${t('components.common.offsetY')}：${targetData.top || 0}px`">
      <n-input-number v-model:value="targetData.top" size="small" step="10"></n-input-number>
    </setting-item>
  </setting-item-box>
</template>

<script setup lang="ts">
import { PropType, reactive } from 'vue'
import { CollapseItem, SettingItemBox, SettingItem } from '@/components/Pages/ChartItemSetting'

const t = window['$t']

type positionType = {
  top?: number | string | null
  left?: number | string | null
  right?: number | string | null
  bottom?: number | string | null
}

const props = defineProps({
  targetData: {
    type: Object as PropType<positionType>,
    required: true
  }
})
</script>
