<template>
  <collapse-item :name="t('components.common.attribute')" :expanded="true">
    <setting-item-box :name="t('components.common.path')" :alone="true">
      <setting-item :name="t('components.common.httpsUrl')">
        <n-input v-model:value="optionData.dataset" size="small"></n-input>
      </setting-item>
    </setting-item-box>
    <setting-item-box :name="t('components.common.style')">
      <setting-item :name="t('components.common.borderRadius')">
        <n-input-number
          v-model:value="optionData.borderRadius"
          size="small"
          :min="0"
          :placeholder="t('components.common.enterBorderRadius')"
        ></n-input-number>
      </setting-item>
    </setting-item-box>
  </collapse-item>
</template>

<script setup lang="ts">
import { PropType } from "vue";
import { option } from "./config";
import {
  CollapseItem,
  SettingItemBox,
  SettingItem,
} from "@/components/Pages/ChartItemSetting";

const t = window['$t']

const props = defineProps({
  optionData: {
    type: Object as PropType<typeof option>,
    required: true,
  },
});
</script>
