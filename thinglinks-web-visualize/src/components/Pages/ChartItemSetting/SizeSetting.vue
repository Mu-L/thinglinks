<template>
  <setting-item-box :name="t('project.setting_size')">
    <n-input-number
      v-model:value="chartAttr.w"
      :min="50"
      :disabled="isGroup"
      size="small"
      placeholder="px"
    >
      <template #prefix>
        <n-text depth="3">{{ t('project.setting_size_width') }}</n-text>
      </template>
    </n-input-number>
    <n-input-number
      v-model:value="chartAttr.h"
      :min="50"
      :disabled="isGroup"
      size="small"
      placeholder="px"
    >
      <template #prefix>
        <n-text depth="3">{{ t('project.setting_size_height') }}</n-text>
      </template>
    </n-input-number>
  </setting-item-box>
</template>

<script setup lang="ts">
import { PropType } from 'vue'
import { useI18n } from 'vue-i18n'
import { PickCreateComponentType } from '@/packages/index.d'
import { SettingItemBox } from '@/components/Pages/ChartItemSetting'

const { t } = useI18n()

const props = defineProps({
  chartAttr: {
    type: Object as PropType<Omit<PickCreateComponentType<'attr'>, 'node' | 'conNode'>>,
    required: true
  },
  isGroup: {
    type: Boolean,
    required: false
  }
})
</script>
