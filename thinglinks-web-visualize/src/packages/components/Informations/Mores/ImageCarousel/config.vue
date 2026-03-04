<template>
  <collapse-item :name="t('components.common.path')" :expanded="true">
      <setting-item v-for="(item, index) in optionData.dataset" :key="index">
        <n-input-group>
          <n-input v-model:value="optionData.dataset[index]" size="small" :placeholder="t('components.common.imageAddress')"></n-input>
          <n-button ghost @click="optionData.dataset.splice(index, 1)"> - </n-button>
        </n-input-group>
      </setting-item>
      <setting-item>
        <n-button size="small" @click="optionData.dataset.push('')"> + {{ t('components.common.add') }}</n-button>
      </setting-item>
  </collapse-item>
  <collapse-item :name="t('components.common.carousel')" :expanded="true">
    <setting-item-box :name="t('components.common.player')">
      <setting-item>
        <n-space>
          <n-switch v-model:value="optionData.autoplay" size="small" />
          <n-text>{{ t('components.common.autoplay') }}</n-text>
        </n-space>
      </setting-item>
      <!-- 开启自动播放时，设置间隔时间 -->
      <setting-item :name="t('components.common.intervalTime')">
        <n-input-number v-model:value="optionData.interval" size="small" placeholder="">
          <template #suffix> {{ t('components.common.millisecond') }} </template>
        </n-input-number>
      </setting-item>
      <setting-item :name="t('components.common.direction')">
        <n-select v-model:value="optionData.direction" :options="directions" :placeholder="t('components.common.selectDirection')" />
      </setting-item>
      <setting-item :name="t('components.common.transitionEffect')">
        <n-select v-model:value="optionData.effect" :options="effects" :placeholder="t('components.common.selectEffect')" />
      </setting-item>
      <setting-item :name="t('components.common.perPageCount')">
        <n-input-number v-model:value="optionData.slidesPerview" size="small" placeholder=""></n-input-number>
      </setting-item>
      <setting-item>
        <n-space>
          <n-switch v-model:value="optionData.centeredSlides" size="small" />
          <n-text>{{ t('components.common.centerDisplay') }}</n-text>
        </n-space>
      </setting-item>
      <setting-item :name="t('components.common.imageStyle')">
        <n-select v-model:value="optionData.fit" :options="fitList" :placeholder="t('components.common.selectStyle')" />
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.indicator')">
      <setting-item :name="t('components.common.style')">
        <n-select v-model:value="optionData.dotType" :options="dotTypes" :placeholder="t('components.common.selectDotType')" />
      </setting-item>
      <setting-item :name="t('components.common.position')">
        <n-select v-model:value="optionData.dotPlacement" :options="dotPlacements" :placeholder="t('components.common.selectDotPlacement')" />
      </setting-item>
      <setting-item>
        <n-space>
          <n-switch v-model:value="optionData.showDots" size="small" />
          <n-text>{{ t('components.common.show') }}</n-text>
        </n-space>
      </setting-item>
      <setting-item>
        <n-space>
          <n-switch v-model:value="optionData.showArrow" size="small" />
          <n-text>{{ t('components.common.arrow') }}</n-text>
        </n-space>
      </setting-item>
      <setting-item>
        <n-space>
          <n-switch v-model:value="optionData.draggable" size="small" />
          <n-text>{{ t('components.common.dragSwitch') }}</n-text>
        </n-space>
      </setting-item>
    </setting-item-box>
  </collapse-item>
</template>

<script setup lang="ts">
import { PropType } from 'vue'
import { option } from './config'
import { CollapseItem, SettingItemBox, SettingItem } from '@/components/Pages/ChartItemSetting'

const props = defineProps({
  optionData: {
    type: Object as PropType<typeof option>,
    required: true
  }
})

const t = window['$t']

// 字典
const dotTypes = [
  {
    label: t('components.common.dot'),
    value: 'dot'
  },
  {
    label: t('components.common.line'),
    value: 'line'
  }
]
const directions = [
  {
    label: t('components.common.horizontal'),
    value: 'horizontal'
  },
  {
    label: t('components.common.vertical'),
    value: 'vertical'
  }
]
const effects = [
  {
    label: 'slide',
    value: 'slide'
  },
  {
    label: 'fade',
    value: 'fade'
  },
  {
    label: 'card',
    value: 'card'
  },
  {
    label: 'custom',
    value: 'custom'
  }
]
const dotPlacements = [
  {
    label: t('components.common.topSide'),
    value: 'top'
  },
  {
    label: t('components.common.bottomSide'),
    value: 'bottom'
  },
  {
    label: t('components.common.leftSide'),
    value: 'left'
  },
  {
    label: t('components.common.rightSide'),
    value: 'right'
  }
]

// 适应类型
const fitList = [
  {
    value: 'fill',
    label: 'fill'
  },
  {
    value: 'contain',
    label: 'contain'
  },
  {
    value: 'cover',
    label: 'cover'
  },
  {
    value: 'scale-down',
    label: 'scale-down'
  },
  {
    value: 'none',
    label: 'none'
  }
]
</script>
