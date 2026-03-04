<template>
  <collapse-item :name="$t('project.section_info')" :expanded="true">
    <setting-item-box :name="$t('project.setting_text')" :alone="true">
      <setting-item>
        <n-input v-model:value="optionData.dataset" type="textarea" size="small"></n-input>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="$t('project.setting_link')" :alone="true">
      <setting-item>
        <n-input-group>
          <n-select
            v-model:value="optionData.linkHead"
            size="small"
            :style="{ width: '80%' }"
            :options="linkHeadOptions"
          />
          <n-input v-model:value="optionData.link" size="small"></n-input>
          <n-button :disabled="!optionData.link" secondary size="small" @click="handleLinkClick">{{ $t('project.action_jump') }}</n-button>
        </n-input-group>
      </setting-item>
    </setting-item-box>
  </collapse-item>

  <collapse-item :name="$t('project.section_style')" :expanded="true">
    <setting-item-box :name="$t('project.setting_text')">
      <setting-item :name="$t('project.setting_color')">
        <n-color-picker size="small" :modes="['hex']" v-model:value="optionData.fontColor"></n-color-picker>
      </setting-item>
      <setting-item :name="$t('project.setting_font_size')">
        <n-input-number v-model:value="optionData.fontSize" size="small" :placeholder="$t('project.placeholder_font_size')"></n-input-number>
      </setting-item>
      <setting-item :name="$t('project.setting_font_weight')">
        <n-select v-model:value="optionData.fontWeight" size="small" :options="fontWeightOptions" />
      </setting-item>
      <setting-item :name="$t('project.setting_padding_x')">
        <n-input-number v-model:value="optionData.paddingX" size="small" :placeholder="$t('project.placeholder_padding')"></n-input-number>
      </setting-item>
      <setting-item :name="$t('project.setting_padding_y')">
        <n-input-number v-model:value="optionData.paddingY" size="small" :placeholder="$t('project.placeholder_padding')"></n-input-number>
      </setting-item>

      <setting-item :name="$t('project.setting_horizontal_align')">
        <n-select v-model:value="optionData.textAlign" size="small" :options="textAlignOptions" />
      </setting-item>
      <setting-item :name="$t('project.setting_text_direction')">
        <n-select v-model:value="optionData.writingMode" size="small" :options="verticalOptions" />
      </setting-item>

      <setting-item :name="$t('project.setting_letter_spacing')">
        <n-input-number v-model:value="optionData.letterSpacing" size="small" :placeholder="$t('project.placeholder_letter_spacing')"></n-input-number>
      </setting-item>
    </setting-item-box>

    <setting-item-box :name="$t('project.setting_border')">
      <setting-item :name="$t('project.setting_border_width')">
        <n-input-number
          v-model:value="optionData.borderWidth"
          size="small"
          :min="0"
          :placeholder="$t('project.placeholder_width')"
        ></n-input-number>
      </setting-item>
      <setting-item :name="$t('project.setting_border_color')">
        <n-color-picker size="small" :modes="['hex']" v-model:value="optionData.borderColor"></n-color-picker>
      </setting-item>
      <setting-item :name="$t('project.setting_border_radius')">
        <n-input-number
          v-model:value="optionData.borderRadius"
          size="small"
          :min="0"
          :placeholder="$t('project.placeholder_radius')"
        ></n-input-number>
      </setting-item>
    </setting-item-box>

    <setting-item-box :name="$t('project.setting_background')" :alone="true">
      <setting-item :name="$t('project.setting_background_color')">
        <n-color-picker size="small" :modes="['hex']" v-model:value="optionData.backgroundColor"></n-color-picker>
      </setting-item>
    </setting-item-box>
  </collapse-item>
</template>

<script setup lang="ts">
import { PropType, computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { option, WritingModeEnum, WritingModeObject, FontWeightEnum, FontWeightObject } from './config'
import { CollapseItem, SettingItemBox, SettingItem } from '@/components/Pages/ChartItemSetting'

const { t } = useI18n()

const props = defineProps({
  optionData: {
    type: Object as PropType<typeof option>,
    required: true
  }
})

const textAlignOptions = computed(() => [
  { label: t('project.option_left_align'), value: 'start' },
  { label: t('project.option_center'), value: 'center' },
  { label: t('project.option_right_align'), value: 'end' }
])

const verticalOptions = [
  {
    label: WritingModeEnum.HORIZONTAL,
    value: WritingModeObject[WritingModeEnum.HORIZONTAL]
  },
  {
    label: WritingModeEnum.VERTICAL,
    value: WritingModeObject[WritingModeEnum.VERTICAL]
  }
]
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
const handleLinkClick = () => {
  window.open(props.optionData.linkHead + props.optionData.link)
}
const linkHeadOptions = [
  { label: 'http://', value: 'http://' },
  { label: 'https://', value: 'https://' }
]
</script>
