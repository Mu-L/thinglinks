import type { BasicColumn, ActionItem } from '/@/components/Table';
import { FileItem, UploadResultStatus } from './typing';
import { Progress, Tag } from 'ant-design-vue';
import TableAction from '/@/components/Table/src/components/TableAction.vue';
import ThumbUrl from './ThumbUrl.vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { formatFileSize } from '/@/utils/thinglinks/common';
import { FileResultVO } from '/@/api/thinglinks/file/model/uploadModel';

const { t } = useI18n();

// 文件上传列表
export function createTableColumns(): BasicColumn[] {
  return [
    {
      dataIndex: 'thumbUrl',
      title: t('component.upload.legend'),
      width: 100,
      customRender: ({ record }) => {
        const { thumbUrl } = (record as FileItem) || {};
        return thumbUrl && <ThumbUrl fileUrl={thumbUrl} />;
      },
    },
    {
      dataIndex: 'name',
      title: t('component.upload.fileName'),
      align: 'left',
      customRender: ({ text, record }) => {
        const { percent, status: uploadStatus } = (record as FileItem) || {};
        let status: 'normal' | 'exception' | 'active' | 'success' = 'normal';
        if (uploadStatus === UploadResultStatus.ERROR) {
          status = 'exception';
        } else if (uploadStatus === UploadResultStatus.UPLOADING) {
          status = 'active';
        } else if (uploadStatus === UploadResultStatus.SUCCESS) {
          status = 'success';
        }
        return (
          <span>
            <p class="truncate mb-1" title={text}>
              {text}
            </p>
            <Progress percent={percent} size="small" status={status} />
          </span>
        );
      },
    },
    {
      dataIndex: 'size',
      title: t('component.upload.fileSize'),
      width: 100,
      customRender: ({ text = 0 }) => {
        return text && formatFileSize(text);
      },
    },
    // {
    //   dataIndex: 'type',
    //   title: '文件类型',
    //   width: 100,
    // },
    {
      dataIndex: 'status',
      title: t('component.upload.fileStatue'),
      width: 100,
      customRender: ({ text }) => {
        if (text === UploadResultStatus.SUCCESS) {
          return <Tag color="green">{() => t('component.upload.uploadSuccess')}</Tag>;
        } else if (text === UploadResultStatus.ERROR) {
          return <Tag color="red">{() => t('component.upload.uploadError')}</Tag>;
        } else if (text === UploadResultStatus.UPLOADING) {
          return <Tag color="blue">{() => t('component.upload.uploading')}</Tag>;
        }

        return text;
      },
    },
  ];
}
export function createActionColumn(handleRemove: Function): BasicColumn {
  return {
    width: 120,
    title: t('component.upload.operating'),
    dataIndex: 'action',
    fixed: false,
    customRender: ({ record }) => {
      const actions: ActionItem[] = [
        {
          label: t('component.upload.del'),
          color: 'error',
          onClick: handleRemove.bind(null, record),
        },
      ];
      // if (checkImgType(record)) {
      //   actions.unshift({
      //     label: t('component.upload.preview'),
      //     onClick: handlePreview.bind(null, record),
      //   });
      // }
      return <TableAction actions={actions} outside={true} />;
    },
  };
}
// 文件预览列表
export function createPreviewColumns(): BasicColumn[] {
  return [
    {
      dataIndex: 'url',
      title: t('component.upload.legend'),
      width: 100,
      customRender: ({ record }) => {
        const { id, fileType } = (record as FileResultVO) || {};
        return fileType === 'IMAGE' && <ThumbUrl fileId={id} />;
      },
    },
    {
      dataIndex: 'originalFileName',
      title: t('component.upload.fileName'),
      align: 'left',
    },
  ];
}

export function createPreviewActionColumn(
  {
    handleRemove,
    handleDownload,
    handleCopyMd5,
    handleCopySHA256,
  }: {
    handleRemove: Fn;
    handleDownload: Fn;
    handleCopyMd5: Fn;
    handleCopySHA256: Fn;
  },
  {
    isShowDeleteBtn,
  }: {
    isShowDeleteBtn: Boolean;
  },
): BasicColumn {
  return {
    width: 160,
    title: t('component.upload.operating'),
    dataIndex: 'action',
    fixed: false,
    customRender: ({ record }) => {
      const actions: ActionItem[] = [
        {
          label: t('component.upload.download'),
          onClick: handleDownload.bind(null, record),
        },
      ];

      const DeleteBtn = {
        label: t('component.upload.del'),
        color: 'error',
        onClick: handleRemove.bind(null, record),
      };

      isShowDeleteBtn && actions.push(DeleteBtn);

      const CopyMd5ActionItem: ActionItem = {
        label: t('component.upload.md5'),
        color: 'success',
        onClick: handleCopyMd5.bind(null, record),
      };

      const CopySHA256ActionItem: ActionItem = {
        label: t('component.upload.SHA256'),
        color: 'success',
        onClick: handleCopySHA256.bind(null, record),
      };

      record?.fileMd5 && actions.push(CopyMd5ActionItem);

      record?.fileSha256 && actions.push(CopySHA256ActionItem);

      return <TableAction actions={actions} outside={true} />;
    },
  };
}
