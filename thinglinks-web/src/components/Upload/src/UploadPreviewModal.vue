<template>
  <BasicModal
    width="800px"
    :title="t('component.upload.preview')"
    class="upload-preview-modal"
    v-bind="$attrs"
    @register="register"
    :showOkBtn="false"
  >
    <FileList :dataSource="fileListRef" :columns="columns" :actionColumn="actionColumn" />
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, watch, ref } from 'vue';
  import FileList from './FileList.vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { previewProps } from './props';
  import { createPreviewColumns, createPreviewActionColumn } from './data';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { isArray } from '/@/utils/is';
  import { downloadFile } from '/@/utils/file/download.ts';
  import { downloadFile as downloadFileApi } from '/@/api/thinglinks/file/upload';
  import { FileResultVO } from '/@/api/thinglinks/file/model/uploadModel';
  import { handleCopyTextV2 } from '/@/utils/thinglinks/common.tsx';

  export default defineComponent({
    components: { BasicModal, FileList },
    props: previewProps,
    emits: ['list-change', 'register', 'delete'],
    setup(props, { emit }) {
      const [register, { closeModal }] = useModalInner();
      const { t } = useI18n();

      const fileListRef = ref<FileResultVO[]>([]);
      watch(
        () => props.value,
        (value) => {
          if (!isArray(value)) value = [];
          fileListRef.value = value.filter((item) => !!item);
        },
        { immediate: true },
      );

      // 删除
      function handleRemove(record: FileResultVO) {
        const index = fileListRef.value.findIndex((item) => item.id === record.id);
        if (index !== -1) {
          const removed = fileListRef.value.splice(index, 1);
          emit('delete', removed[0].id);
          emit('list-change', fileListRef.value);
        }
      }

      // 下载
      async function handleDownload(record: FileResultVO) {
        const { id } = record;
        const download = downloadFileApi;
        const response = await download([id]);
        downloadFile(response);
      }

      const handleCopyMd5 = (record: FileResultVO) => {
        const { fileMd5 = '' } = record;
        handleCopyTextV2(fileMd5 as string);
      };

      const handleCopySHA256 = (record: FileResultVO) => {
        const { fileSha256 = '' } = record;
        handleCopyTextV2(fileSha256 as string);
      };


      return {
        t,
        register,
        closeModal,
        fileListRef,
        columns: createPreviewColumns() as any[],
        actionColumn: createPreviewActionColumn({
          handleRemove,
          handleDownload,
          handleCopyMd5,
          handleCopySHA256,
        }, {
          isShowDeleteBtn: props.isShowDeleteBtn
        }) as any,
      };
    },
  });
</script>
<style lang="less">
  .upload-preview-modal {
    .ant-upload-list {
      display: none;
    }

    .ant-table-wrapper .ant-spin-nested-loading {
      padding: 0;
    }
  }
</style>
