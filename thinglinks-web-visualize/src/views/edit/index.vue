<template>
  <div class="go-edit">
    <n-layout>
      <n-layout-header class="go-edit-header go-px-5 go-flex-items-center" bordered>
        <div>
          <n-text class="go-edit-title go-mr-4">{{ $t('global.editor_title') }}</n-text>
          <n-button v-if="showOpenFilePicker" class="go-mr-3" size="medium" @click="importJSON">
            <template #icon>
              <n-icon>
                <download-icon></download-icon>
              </n-icon>
            </template>
            {{ $t('global.import') }}
          </n-button>
        </div>
        <n-space>
          <!-- 暂时关闭 -->
          <!-- <n-tag :bordered="false" type="warning"> 「页面失焦保存」 </n-tag> -->
          <n-tag :bordered="false" type="warning"> {{ $t('global.ctrl_s_update_view') }} </n-tag>
          <n-button v-if="showOpenFilePicker" class="go-mr-3" size="medium" @click="updateSync">
            <template #icon>
              <n-icon>
                <analytics-icon></analytics-icon>
              </n-icon>
            </template>
            {{ $t('global.save') }}
          </n-button>
        </n-space>
      </n-layout-header>
      <n-layout-content>
        <monaco-editor
          v-model:modelValue="content"
          language="json"
          :editorOptions="{
            lineNumbers: 'on',
            minimap: { enabled: true }
          }"
          />
      </n-layout-content>
    </n-layout>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useI18n } from 'vue-i18n'
import { MonacoEditor } from '@/components/Pages/MonacoEditor'
import { SavePageEnum } from '@/enums/editPageEnum'
import { getSessionStorageInfo } from '../preview/utils'
import { setSessionStorage, fetchRouteParamsLocation, JSONStringify, JSONParse, setTitle, goDialog } from '@/utils'
import { StorageEnum } from '@/enums/storageEnum'
import { icon } from '@/plugins'
import { useSync } from '@/views/chart/hooks/useSync.hook'
import { useChartEditStore } from '@/store/modules/chartEditStore/chartEditStore'
import { ProjectInfoEnum } from '@/store/modules/chartEditStore/chartEditStore.d'
import type { ChartEditStorageType } from '../preview/index.d'

const { t } = useI18n()
const chartEditStore = useChartEditStore()
const { dataSyncUpdate } = useSync()

const { ChevronBackOutlineIcon, DownloadIcon, AnalyticsIcon } = icon.ionicons5
const showOpenFilePicker: Function = (window as any).showOpenFilePicker
const content = ref('')

window['$message'].warning(t('global.do_not_refresh_window'))

// 从sessionStorage 获取数据
async function getDataBySession() {
  const localStorageInfo: ChartEditStorageType = (await getSessionStorageInfo()) as unknown as ChartEditStorageType
  setTitle(`${t('global.r_edit')}-${localStorageInfo.editCanvasConfig.projectName}`)
  content.value = JSONStringify(localStorageInfo)
}
setTimeout(getDataBySession)

// 返回父窗口
function back() {
  window.opener.name = Date.now()
  window.open(window.opener.location.href, window.opener.name)
}

// 导入json文本
function importJSON() {
  goDialog({
    message: t('global.import_data_confirm'),
    isMaskClosable: true,
    transformOrigin: 'center',
    onPositiveCallback: async () => {
      try {
        const files = await showOpenFilePicker()
        const file = await files[0].getFile()
        const fr = new FileReader()
        fr.readAsText(file)
        fr.onloadend = () => {
          content.value = (fr.result || '').toString()
        }
        window['$message'].success(t('global.import_success'))
      } catch (error) {
        window['$message'].error(t('global.import_failed'))
        console.log(error)
      }
    }
  })
}

// 同步数据编辑页
window.opener.addEventListener(SavePageEnum.CHART, (e: any) => {
  window['$message'].success(t('global.updating'))
  setSessionStorage(StorageEnum.GO_CHART_STORAGE_LIST, [e.detail])
  content.value = JSONStringify(e.detail)
})

// 保存按钮同步数据
document.addEventListener('keydown', function (e) {
  if (e.keyCode == 83 && (navigator.platform.match('Mac') ? e.metaKey : e.ctrlKey)) {
    e.preventDefault()
    updateSync()
  }
})

// 失焦保存（暂时关闭）
// addEventListener('blur', updateSync)

// 同步更新
async function updateSync() {
  if (!window.opener) {
    return window['$message'].error(t('global.source_window_closed'))
  }
  goDialog({
    message: t('global.overwrite_source_confirm'),
    isMaskClosable: true,
    transformOrigin: 'center',
    onPositiveCallback: async () => {
      try {
        const detail = JSONParse(content.value)
        delete detail.id
        // 保持id不变
        // 带后端版本额外处理请求
        if (dataSyncUpdate) {
          chartEditStore.setProjectInfo(ProjectInfoEnum.PROJECT_ID, fetchRouteParamsLocation())
          await dataSyncUpdate(false) // JSON界面保存不上传缩略图
        }
        window.opener.dispatchEvent(new CustomEvent(SavePageEnum.JSON, { detail }))
        window['$message'].success(t('global.syncing_content'))
      } catch (e) {
        window['$message'].error(t('global.content_format_error'))
        console.log(e)
      }
    }
  })
}

// 关闭页面发送关闭事件
window.onbeforeunload = () => {
  if (window.opener) {
    window.opener.dispatchEvent(new CustomEvent(SavePageEnum.CLOSE))
  }
}
</script>

<style lang="scss" scoped>
.go-edit {
  display: flex;
  flex-direction: column;
  height: 100vh;
  .go-edit-header {
    display: flex;
    align-items: center;
    height: 60px;
    justify-content: space-between;
    .go-edit-title {
      position: relative;
      bottom: 3px;
      font-size: 18px;
      font-weight: bold;
    }
  }
  @include deep() {
    .go-editor-area {
      height: calc(100vh - 60px) !important;
    }
  }
}
</style>
