import { ref, nextTick } from 'vue'
import { useI18n } from 'vue-i18n'
import { UploadCustomRequestOptions } from 'naive-ui'
import { FileTypeEnum } from '@/enums/fileTypeEnum'
import { readFile, goDialog, JSONParse } from '@/utils'
import { useSync } from '@/views/chart/hooks/useSync.hook'

export const useFile = () => {
  const { t } = useI18n()
  const importUploadFileListRef = ref()
  const { updateComponent } = useSync()
  // 上传-前置
  //@ts-ignore
  const importBeforeUpload = ({ file }) => {
    importUploadFileListRef.value = []
    const type = file.file.type
    if (type !== FileTypeEnum.JSON && type !== FileTypeEnum.TXT) {
      window['$message'].warning(t('global.only_support_json_file'))
      return false
    }
    return true
  }

  // 上传-导入
  const importCustomRequest = (options: UploadCustomRequestOptions) => {
    const { file } = options
    nextTick(() => {
      if (file.file) {
        readFile(file.file).then((fileData: any) => {
          goDialog({
            message: t('global.select_import_method'),
            positiveText: t('global.add_import'),
            negativeText: t('global.overwrite_import'),
            negativeButtonProps: { type: 'info', ghost: false },
            // 新增
            onPositiveCallback: async () => {
              try {
                fileData = JSONParse(fileData)
                await updateComponent(fileData, false, true)
                window['$message'].success(t('global.import_success'))
              } catch (error) {
                console.log(error)
                window['$message'].error(t('global.component_import_failed'))
              }
            },
            // 覆盖
            onNegativeCallback: async () => {
              try {
                fileData = JSONParse(fileData)
                await updateComponent(fileData, true, true)
                window['$message'].success(t('global.import_success'))
              } catch (error) {
                console.log(error)
                window['$message'].error(t('global.component_import_failed'))
              }
            }
          })
        })
      } else {
        window['$message'].error(t('global.import_failed_contact_admin'))
      }
    })
  }

  return {
    importUploadFileListRef,
    importBeforeUpload,
    importCustomRequest
  }
}
