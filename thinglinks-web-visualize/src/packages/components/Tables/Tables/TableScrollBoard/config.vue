<template>
  <CollapseItem :name="$t('project.section_list')" :expanded="true">
    <SettingItemBox :name="$t('project.section_basic')">
      <SettingItem :name="$t('project.setting_table_rows')">
        <n-input-number
          v-model:value="optionData.rowNum"
          :min="1"
          size="small"
          :placeholder="$t('project.placeholder_auto_calculate')"
        ></n-input-number>
      </SettingItem>
      <SettingItem :name="$t('project.setting_carousel_time')">
        <n-input-number
          v-model:value="optionData.waitTime"
          :min="1"
          size="small"
          :placeholder="$t('project.placeholder_carousel_time')"
        ></n-input-number>
      </SettingItem>
      <SettingItem :name="$t('project.setting_header_height')">
        <n-input-number
          v-model:value="optionData.headerHeight"
          :min="1"
          size="small"
          :placeholder="$t('project.placeholder_header_height')"
        ></n-input-number>
      </SettingItem>
      <SettingItem :name="$t('project.setting_show_row_number')">
        <n-switch size="small" v-model:value="optionData.index" />
      </SettingItem>
    </SettingItemBox>

    <SettingItemBox :name="$t('project.section_config')" :alone="true">
      <SettingItem :name="$t('project.setting_header_data')">
        <n-input v-model:value="header" :min="1" size="small" :placeholder="$t('project.placeholder_header_data')"></n-input>
      </SettingItem>
      <SettingItem :name="$t('project.setting_column_align')">
        <n-input v-model:value="align" :min="1" size="small" :placeholder="$t('project.placeholder_align')"></n-input>
      </SettingItem>
      <SettingItem :name="$t('project.setting_column_width')">
        <n-input v-model:value="columnWidth" :min="1" size="small" :placeholder="$t('project.placeholder_column_width')"></n-input>
      </SettingItem>
      <SettingItem :name="$t('project.setting_carousel_mode')">
        <n-select
          v-model:value="optionData.carousel"
          :options="carouselOptions"
        />
      </SettingItem>
    </SettingItemBox>

    <SettingItemBox :name="$t('project.section_style')">
      <SettingItem :name="$t('project.setting_header_bg_color')">
        <n-color-picker size="small" :modes="['hex']" v-model:value="optionData.headerBGC"></n-color-picker>
      </SettingItem>
      <SettingItem :name="$t('project.setting_odd_row_bg_color')">
        <n-color-picker size="small" :modes="['hex']" v-model:value="optionData.oddRowBGC"></n-color-picker>
      </SettingItem>
      <SettingItem :name="$t('project.setting_even_row_bg_color')">
        <n-color-picker size="small" :modes="['hex']" v-model:value="optionData.evenRowBGC"></n-color-picker>
      </SettingItem>
    </SettingItemBox>
  </CollapseItem>
</template>

<script setup lang="ts">
import { PropType, ref, watch, computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { CollapseItem, SettingItemBox, SettingItem } from '@/components/Pages/ChartItemSetting'
import { option } from './config'

const { t } = useI18n()

const props = defineProps({
  optionData: {
    type: Object as PropType<typeof option>,
    required: true
  }
})

const header = ref()
const align = ref()
const columnWidth = ref()

const carouselOptions = computed(() => [
  { label: t('project.option_single_carousel'), value: 'single' },
  { label: t('project.option_page_carousel'), value: 'page' }
])

watch(
  () => props.optionData,
  newData => {
    header.value = props.optionData.header.toString()
    align.value = props.optionData.align.toString()
    columnWidth.value = props.optionData.columnWidth.toString()
  },
  {
    deep: false,
    immediate: true
  }
)

watch([header, align, columnWidth], ([headerNew, alignNew, columnWidthNew], [headerOld, alignOld, columnWidthOld]) => {
  if (headerNew !== headerOld) {
    props.optionData.header = headerNew.split(',')
  }
  if (alignNew !== alignOld) {
    props.optionData.align = alignNew.split(',')
  }
  if (columnWidthNew !== columnWidthOld) {
    // @ts-ignore
    props.optionData.columnWidth = columnWidthNew.split(',')
  }
})
</script>
