<template>
  <n-modal v-model:show="modelShowRef" @afterLeave="closeHandle">
    <n-list bordered class="go-system-setting">
      <template #header>
        <n-space justify="space-between">
          <n-h3 class="go-mb-0">{{ t('global.sys_set') }}</n-h3>
          <n-icon size="20" class="go-cursor-pointer" @click="closeHandle">
            <close-icon></close-icon>
          </n-icon>
        </n-space>
      </template>

      <n-list-item v-for="item in list" :key="item.key">
        <!-- 分割线 -->
        <n-divider v-if="item.type === 'divider'" style="margin: 0;" />
        <n-space v-else :size="40">
          <n-space>
            <!-- 左侧标题 -->
            <n-text class="item-left">{{ item.name }}</n-text>

            <!-- 数据操作 -->
            <template v-if="item.type === 'switch'">
              <n-switch
                v-model:value="item.value"
                size="small"
                @update:value="handleChange($event, item)"
              ></n-switch>
            </template>

            <template v-else-if="item.type === 'number'">
              <n-input-number
                v-model:value="item.value"
                class="input-num-width"
                size="small"
                :step="item.step || null"
                :suffix="item.suffix || null"
                :min="item.min || 0"
                @update:value="handleChange($event, item)"
              ></n-input-number>
            </template>

            <template v-else-if="item.type === 'select'">
              <n-select
                class="select-min-width"
                v-model:value="item.value"
                size="small"
                :options="item.options"
                @update:value="handleChange($event, item)"
              />
            </template>
          </n-space>

          <!-- 右侧描述 -->
          <n-space>
            <n-text class="item-right">{{ item.desc }}</n-text>
            <n-tooltip v-if="item.tip" trigger="hover">
              <template #trigger>
                <n-icon size="21">
                  <help-outline-icon></help-outline-icon>
                </n-icon>
              </template>
              <span>{{ item.tip }}</span>
            </n-tooltip>
          </n-space>
        </n-space>
      </n-list-item>
    </n-list>
  </n-modal>
</template>

<script script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import { ListType } from './index.d'
import { useSettingStore } from '@/store/modules/settingStore/settingStore'
import { useLangStore } from '@/store/modules/langStore/langStore'
import { SettingStoreEnums, ToolsStatusEnum } from '@/store/modules/settingStore/settingStore.d'
import { icon } from '@/plugins'

const props = defineProps({
  modelShow: Boolean
})

const emit = defineEmits(['update:modelShow'])
const { HelpOutlineIcon, CloseIcon } = icon.ionicons5
const settingStore = useSettingStore()
const langStore = useLangStore()
const modelShowRef = ref(false)

const t = window['$t']

// 更新列表文本的函数
const updateListText = () => {
  list[0].name = t('global.menu_collapse')
  list[0].desc = t('global.menu_collapse_desc')
  list[1].name = t('global.hide_category')
  list[1].desc = t('global.hide_category_desc')
  list[2].name = t('global.switch_lang')
  list[2].desc = t('global.switch_lang_desc')
  list[2].tip = t('global.switch_lang_tip')
  list[4].name = t('global.hide_toolbar')
  list[4].desc = t('global.hide_toolbar_desc')
  list[5].name = t('global.toolbar_display')
  list[5].desc = t('global.toolbar_display_desc')
  if (list[5].options) {
    list[5].options[0].label = t('global.toolbar_aside')
    list[5].options[1].label = t('global.toolbar_dock')
  }
  list[7].name = t('global.move_distance')
  list[7].desc = t('global.move_distance_desc')
  list[8].name = t('global.align_range')
  list[8].desc = t('global.align_range_desc')
}

const list = reactive<ListType[]>([
  {
    key: SettingStoreEnums.ASIDE_ALL_COLLAPSED,
    value: settingStore.getAsideAllCollapsed,
    type: 'switch',
    name: t('global.menu_collapse'),
    desc: t('global.menu_collapse_desc')
  },
  {
    key: SettingStoreEnums.HIDE_PACKAGE_ONE_CATEGORY,
    value: settingStore.getHidePackageOneCategory,
    type: 'switch',
    name: t('global.hide_category'),
    desc: t('global.hide_category_desc')
  },
  {
    key: SettingStoreEnums.CHANGE_LANG_RELOAD,
    value: settingStore.getChangeLangReload,
    type: 'switch',
    name: t('global.switch_lang'),
    desc: t('global.switch_lang_desc'),
    tip: t('global.switch_lang_tip')
  },
  {
    key: 'divider1',
    type: 'divider',
    name: '',
    desc: '',
    value: ''
  },
  {
    key: SettingStoreEnums.CHART_TOOLS_STATUS_HIDE,
    value: settingStore.getChartToolsStatusHide,
    type: 'switch',
    name: t('global.hide_toolbar'),
    desc: t('global.hide_toolbar_desc'),
  },
  {
    key: SettingStoreEnums.CHART_TOOLS_STATUS,
    value: settingStore.getChartToolsStatus,
    type: 'select',
    name: t('global.toolbar_display'),
    desc: t('global.toolbar_display_desc'),
    options: [
      {
        label: t('global.toolbar_aside'),
        value: ToolsStatusEnum.ASIDE
      },
      {
        label: t('global.toolbar_dock'),
        value: ToolsStatusEnum.DOCK
      }
    ]
  },
  {
    key: 'divider0',
    type: 'divider',
    name: '',
    desc: '',
    value: ''
  },
  {
    key: SettingStoreEnums.CHART_MOVE_DISTANCE,
    value: settingStore.getChartMoveDistance,
    type: 'number',
    name: t('global.move_distance'),
    min: 1,
    step: 1,
    suffix: 'px',
    desc: t('global.move_distance_desc')
  },
  {
    key: SettingStoreEnums.CHART_ALIGN_RANGE,
    value: settingStore.getChartAlignRange,
    type: 'number',
    name: t('global.align_range'),
    min: 10,
    step: 2,
    suffix: 'px',
    desc: t('global.align_range_desc')
  }
])

// 监听语言变化，更新列表文本
watch(() => langStore.getLang, () => {
  updateListText()
})

watch(() => props.modelShow, (newValue) => {
  modelShowRef.value = newValue
})

const closeHandle = () => {
  emit('update:modelShow', false)
}

const handleChange = (e: MouseEvent, item: ListType) => {
  settingStore.setItem(item.key, item.value)
}
</script>

<style lang="scss" scoped>
@include go("system-setting") {
  @extend .go-background-filter;
  min-width: 100px;
  max-width: 60vw;
  padding-bottom: 20px;
  .item-left {
    width: 200px;
  }
  .input-num-width {
    width: 100px;
  }
  .select-min-width {
    width: 115px;
  }
  @include deep() {
    .n-list-item {
      border-bottom: 0!important;
    }
    .n-list-item__divider {
      display: none!important;
    }
  }
}
</style>
