<template>
  <CollapseItem :name="t('components.components.list')" :expanded="true">
    <SettingItemBox :name="t('components.common.basic')">
      <SettingItem :name="t('components.common.rowNum')">
        <n-input-number
          v-model:value="optionData.rowNum"
          :min="1"
          size="small"
          :placeholder="t('components.common.enterAutoCalculate')"
        ></n-input-number>
      </SettingItem>
      <SettingItem :name="t('components.special.waitTimeSuffix')">
        <n-input-number
          v-model:value="optionData.waitTime"
          :min="1"
          size="small"
          :placeholder="t('components.common.enterCarouselTime')"
        ></n-input-number>
      </SettingItem>
      <SettingItem :name="t('components.common.unit')">
        <n-input
          v-model:value="optionData.unit"
          size="small"
          :placeholder="t('components.common.enterUnit')"
        ></n-input>
      </SettingItem>
    </SettingItemBox>

    <SettingItemBox :name="t('components.common.style')">
      <SettingItem :name="t('components.common.mainColor')">
        <n-color-picker
          size="small"
          :modes="['hex']"
          v-model:value="optionData.color"
        ></n-color-picker>
      </SettingItem>
      <SettingItem :name="t('components.common.dataColor')">
        <n-color-picker
          size="small"
          :modes="['hex']"
          v-model:value="optionData.textColor"
        ></n-color-picker>
      </SettingItem>
      <SettingItem :name="t('components.common.bottomLine')">
        <n-color-picker
          size="small"
          :modes="['hex']"
          v-model:value="optionData.borderColor"
        ></n-color-picker>
      </SettingItem>
    </SettingItemBox>

    <SettingItemBox :name="t('components.common.fontStyle')">
       <SettingItem :name="t('components.common.indexFont')">
        <n-input-number
          size="small"
          v-model:value="optionData.indexFontSize"
          :min="12"
        />
      </SettingItem>
      <SettingItem :name="t('components.common.leftDataFont')">
        <n-input-number
          size="small"
          v-model:value="optionData.leftFontSize"
          :min="12"
        />
      </SettingItem>
      <SettingItem :name="t('components.common.rightDataFont')">
        <n-input-number
          size="small"
          v-model:value="optionData.rightFontSize"
          :min="12"
        />
      </SettingItem>
    </SettingItemBox>
      
  </CollapseItem>
</template>

<script setup lang="ts">
import { PropType } from 'vue'
import {
  CollapseItem,
  SettingItemBox,
  SettingItem,
} from '@/components/Pages/ChartItemSetting'
import { option } from './config'

const t = window['$t']

const props = defineProps({
  optionData: {
    type: Object as PropType<typeof option>,
    required: true,
  },
})
</script>
