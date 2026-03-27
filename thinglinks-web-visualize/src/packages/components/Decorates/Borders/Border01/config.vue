<template>
  <CollapseItem :name="t('components.common.border')" :expanded="true">
    <SettingItemBox
      :name="`${t('components.special.colorPrefix')}-${index + 1}`"
      v-for="(item, index) in optionData.colors"
      :key="index"
    >
      <SettingItem :name="t('components.common.color')">
        <n-color-picker
          size="small"
          :modes="['hex']"
          v-model:value="optionData.colors[index]"
        ></n-color-picker>
      </SettingItem>
      <SettingItem>
        <n-button
          size="small"
          @click="optionData.colors[index] = option.colors[index]"
        >
          {{ t('components.common.restoreDefault') }}
        </n-button>
      </SettingItem>
    </SettingItemBox>
  </CollapseItem>

  <CollapseItem :name="t('components.common.animation')" :expanded="true">
    <SettingItemBox :name="t('components.special.speedSuffix')">
      <SettingItem>
        <n-input-number
          v-model:value="optionData.dur"
          size="small"
          :step="0.5"
          :min="0.5"
        ></n-input-number>
      </SettingItem>
    </SettingItemBox>
  </CollapseItem>

  <CollapseItem :name="t('components.common.background')" :expanded="true">
    <SettingItemBox :name="t('components.common.color')">
      <SettingItem>
        <n-color-picker
          v-model:value="optionData.backgroundColor"
          size="small"
          :modes="['hex']"
        ></n-color-picker>
      </SettingItem>
    </SettingItemBox>
  </CollapseItem>
</template>

<script setup lang="ts">
import { PropType } from 'vue'
import {
  CollapseItem,
  SettingItemBox,
  SettingItem
} from '@/components/Pages/ChartItemSetting'
import { option } from './config'

const t = window['$t']

const props = defineProps({
  optionData: {
    type: Object as PropType<typeof option>,
    required: true
  }
})
</script>
