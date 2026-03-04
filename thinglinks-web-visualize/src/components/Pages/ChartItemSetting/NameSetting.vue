<template>
  <setting-item-box :name="t('project.setting_name')" :alone="true">
    <n-input
      type="text"
      maxlength="12"
      minlength="1"
      :placeholder="t('project.setting_name_placeholder')"
      size="small"
      clearable
      show-count
      v-model:value="chartConfig.title"
      @focus="handleFocus"
      @blur="handleBlur"
    ></n-input>
  </setting-item-box>
</template>

<script setup lang="ts">
import { PropType } from 'vue'
import { useI18n } from 'vue-i18n'
import { SettingItemBox } from '@/components/Pages/ChartItemSetting'
import { ConfigType } from '@/packages/index.d'

const { t } = useI18n()

const props = defineProps({
  chartConfig: {
    type: Object as PropType<ConfigType>,
    required: true
  },
})

let valueCatch = ''

const handleFocus = () => {
  valueCatch = props.chartConfig.title
}

const handleBlur = () => {
  if(!props.chartConfig.title.length) {
    window['$message'].warning(t('project.setting_name_min_length'))
    props.chartConfig.title = valueCatch 
  }
}
</script>
