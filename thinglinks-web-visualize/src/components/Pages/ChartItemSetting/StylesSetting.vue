<template>
  <div v-show="isGroup">
    <n-divider n-divider style="margin: 10px 0"></n-divider>
    <n-tag type="warning"> {{ t('project.style_ungroup_warning', { type: isCanvas ? t('project.style_filter') : t('project.style_filter_transform') }) }} </n-tag>
  </div>

  <collapse-item :name="isCanvas ? t('project.style_filter') : t('project.style_filter_transform')">
    <template #header>
      <n-switch v-model:value="chartStyles.filterShow" size="small"></n-switch>
    </template>
    <setting-item-box :name="t('project.style_hue')" :alone="true">
      <setting-item :name="`${t('project.style_value')}：${chartStyles.hueRotate}deg`">
        <!-- 透明度 -->
        <n-slider
          v-model:value="chartStyles.hueRotate"
          :step="3"
          :min="0"
          :max="360"
          :format-tooltip="degFormatTooltip"
        ></n-slider>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('project.style_saturation')" :alone="true">
      <setting-item :name="`${t('project.style_value')}：${(parseFloat(String(chartStyles.saturate)) * 100).toFixed(0)}%`">
        <!-- 透明度 -->
        <n-slider
          v-model:value="chartStyles.saturate"
          :step="0.1"
          :min="0"
          :max="2"
          :format-tooltip="sliderFormatTooltip"
        ></n-slider>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('project.style_contrast')" :alone="true">
      <setting-item :name="`${t('project.style_value')}：${(parseFloat(String(chartStyles.contrast)) * 100).toFixed(0)}%`">
        <!-- 透明度 -->
        <n-slider
          v-model:value="chartStyles.contrast"
          :step="0.1"
          :min="0"
          :max="2"
          :format-tooltip="sliderFormatTooltip"
        ></n-slider>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('project.style_brightness')" :alone="true">
      <setting-item :name="`${t('project.style_value')}：${(parseFloat(String(chartStyles.brightness)) * 100).toFixed(0)}%`">
        <!-- 透明度 -->
        <n-slider
          v-model:value="chartStyles.brightness"
          :step="0.1"
          :min="0"
          :max="2"
          :format-tooltip="sliderFormatTooltip"
        ></n-slider>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('project.style_opacity')" :alone="true">
      <setting-item :name="`${t('project.style_value')}：${(parseFloat(String(chartStyles.opacity)) * 100).toFixed(0)}%`">
        <!-- 透明度 -->
        <n-slider
          v-model:value="chartStyles.opacity"
          :step="0.1"
          :min="0"
          :max="1"
          :format-tooltip="sliderFormatTooltip"
        ></n-slider>
      </setting-item>
    </setting-item-box>

    <!-- 混合模式 -->
    <setting-item-box v-if="!isCanvas" :alone="true">
      <template #name>
        <n-text>{{ t('project.style_blend') }}</n-text>
        <n-tooltip trigger="hover">
          <template #trigger>
            <n-icon size="21" :depth="3">
              <help-outline-icon></help-outline-icon>
            </n-icon>
          </template>
          <n-text>{{ t('project.style_blend_tooltip') }}</n-text>
        </n-tooltip>
      </template>
      <setting-item>
        <n-select v-model:value="chartStyles.blendMode" size="small" filterable :options="blendModeOptions"></n-select>
      </setting-item>
    </setting-item-box>

    <!-- 变换 -->
    <setting-item-box v-if="!isCanvas" :name="t('project.style_rotate')">
      <setting-item :name="t('project.style_rotate_z')">
        <!-- 透明度 -->
        <n-input-number
          v-model:value="chartStyles.rotateZ"
          :min="0"
          :max="360"
          size="small"
          :placeholder="t('project.style_angle')"
        ></n-input-number>
      </setting-item>
      <setting-item :name="t('project.style_rotate_x')">
        <!-- 透明度 -->
        <n-input-number
          v-model:value="chartStyles.rotateX"
          :min="0"
          :max="360"
          size="small"
          :placeholder="t('project.style_angle')"
        ></n-input-number>
      </setting-item>
      <setting-item :name="t('project.style_rotate_y')">
        <!-- 透明度 -->
        <n-input-number
          v-model:value="chartStyles.rotateY"
          :min="0"
          :max="360"
          size="small"
          :placeholder="t('project.style_angle')"
        ></n-input-number>
      </setting-item>
    </setting-item-box>

    <!-- 倾斜 -->
    <setting-item-box v-if="!isCanvas" :name="t('project.style_skew')">
      <setting-item :name="t('project.style_skew_x')">
        <n-input-number
          v-model:value="chartStyles.skewX"
          :min="0"
          :max="360"
          size="small"
          :placeholder="t('project.style_angle')"
        ></n-input-number>
      </setting-item>
      <setting-item :name="t('project.style_skew_y')">
        <n-input-number
          v-model:value="chartStyles.skewY"
          :min="0"
          :max="360"
          size="small"
          :placeholder="t('project.style_angle')"
        ></n-input-number>
      </setting-item>
    </setting-item-box>

    <!-- 提示 -->
    <n-tag type="warning"> {{ t('project.style_preview_blur_tip') }} </n-tag>
  </collapse-item>
</template>

<script setup lang="ts">
import { PropType, computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { PickCreateComponentType, getBlendModeEnumList } from '@/packages/index.d'
import { SettingItemBox, SettingItem, CollapseItem } from '@/components/Pages/ChartItemSetting'
import { icon } from '@/plugins'

const { t } = useI18n()

const props = defineProps({
  isGroup: {
    type: Boolean,
    required: false
  },
  isCanvas: {
    type: Boolean,
    default: false
  },
  chartStyles: {
    type: Object as PropType<Omit<PickCreateComponentType<'styles'>, 'animations'>>,
    required: true
  }
})

const { HelpOutlineIcon } = icon.ionicons5

// 混合模式选项（支持多语言）
const blendModeOptions = computed(() => getBlendModeEnumList())

// 百分比格式化 person
const sliderFormatTooltip = (v: string) => {
  return `${(parseFloat(v) * 100).toFixed(0)}%`
}
// 角度格式化
const degFormatTooltip = (v: string) => {
  return `${v}deg`
}
</script>

<style lang="scss" scoped></style>
