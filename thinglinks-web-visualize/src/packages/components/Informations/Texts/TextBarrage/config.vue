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
      <setting-item :name="t('components.common.color')">
        <n-color-picker size="small" :modes="['hex']" v-model:value="optionData.fontColor"></n-color-picker>
      </setting-item>
      <setting-item :name="t('components.common.fontSize')">
        <n-input-number v-model:value="optionData.fontSize" size="small" :placeholder="t('components.common.enterFontSize')"></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.fontWeight')">
        <n-select v-model:value="optionData.fontWeight" size="small" :options="fontWeightOptions" />
      </setting-item>

      <setting-item :name="t('components.common.letterSpacing')">
        <n-input-number v-model:value="optionData.letterSpacing" size="small" :placeholder="t('components.common.enterLetterSpacing')"></n-input-number>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.shadow')">
      <setting-item>
        <n-space>
          <n-switch v-model:value="optionData.showShadow" size="small" />
          <n-text>{{ t('components.common.showShadow') }}</n-text>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.color')">
        <n-color-picker size="small" :modes="['hex']" v-model:value="optionData.colorShadow"></n-color-picker
      ></setting-item>
      <setting-item name="x">
        <n-input-number v-model:value="optionData.hShadow" size="small"></n-input-number
      ></setting-item>
      <setting-item name="y">
        <n-input-number v-model:value="optionData.vShadow" size="small"></n-input-number
      ></setting-item>
      <setting-item :name="t('components.common.blur')">
        <n-input-number v-model:value="optionData.blurShadow" size="small"></n-input-number
      ></setting-item>
    </setting-item-box>

    <setting-item-box :name="t('components.common.animation')">
      <setting-item :name="t('components.common.animationSpeed')">
        <n-input-number
          v-model:value="optionData.animationSpeed"
          size="small"
          :placeholder="t('components.common.enterAnimationSpeed')"
          :min="0"
        ></n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.animationInterval')">
        <n-input-number
          v-model:value="optionData.animationTime"
          size="small"
          :placeholder="t('components.common.enterAnimationInterval')"
          :min="0"
        ></n-input-number>
      </setting-item>
    </setting-item-box>
  </collapse-item>
</template>

<script setup lang="ts">
import { PropType } from 'vue'
import { option, FontWeightEnum, FontWeightObject } from './config'
import { CollapseItem, SettingItemBox, SettingItem } from '@/components/Pages/ChartItemSetting'

const t = window['$t']

const props = defineProps({
  optionData: {
    type: Object as PropType<typeof option>,
    required: true
  }
})

const fontWeightOptions = [
  {
    label: FontWeightEnum.NORMAL,
    value: FontWeightObject[FontWeightEnum.NORMAL]
  },
  {
    label: FontWeightEnum.BOLD,
    value: FontWeightObject[FontWeightEnum.BOLD]
  }
]
</script>
