import { Ref } from 'vue';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { Tooltip } from 'ant-design-vue';
import { dictComponentProps, handleCopyTextV2 } from '/@/utils/thinglinks/common';
import { DictEnum } from '/@/enums/commonEnum';
import { CopyOutlined, EyeOutlined } from '@ant-design/icons-vue';

const { t } = useI18n();

// 列表页字段
export const columns = ({ openRemarkModal }: { openRemarkModal: Fn }): BasicColumn[] => {
  return [
    {
      title: t('iot.link.device.device.commandIdentification'),
      dataIndex: 'commandIdentification',
    },
    {
      title: t('iot.link.device.device.commandType'),
      dataIndex: ['commandType'],
      width: 150,
      slots: { customRender: 'commandType' },
    },
    {
      title: t('iot.link.device.device.content'),
      dataIndex: 'content',
      ellipsis: true,
      width: 380,
      showSorterTooltip: true,
      customRender: ({ record }) => {
        return (
          <div>
            {record.content ? (
              <CopyOutlined
                onClick={() => handleCopyTextV2(record.content || '')}
                style={{ marginRight: '10px' }}
              />
            ) : null}
            {record.content ? (
              <EyeOutlined
                onClick={() => openRemarkModal(record.content, t('iot.link.device.device.content'))}
                style={{ marginRight: '10px' }}
              />
            ) : null}
            <Tooltip placement="topLeft" title={record.content}>
              <span>{record.content}</span>
            </Tooltip>
          </div>
        );
      },
    },
    {
      title: t('iot.link.device.device.remark'),
      dataIndex: 'remark',
      ellipsis: true,
      showSorterTooltip: true,
      customRender: ({ record }) => {
        return (
          <div>
            {record.remark ? (
              <EyeOutlined
                onClick={() => openRemarkModal(record.remark, t('iot.link.device.device.remark'))}
                style={{ marginRight: '10px' }}
              />
            ) : null}
            <span>{record.remark}</span>
          </div>
        );
      },
    },
    {
      title: `${t('iot.link.device.device.status')}`,
      dataIndex: 'status',
      slots: { customRender: 'status' },
    },
    {
      title: t('iot.link.device.device.createdTime'),
      dataIndex: 'createdTime',
      sorter: true,
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('iot.link.device.device.commandType'),
      field: 'commandType',
      component: 'ApiSelect',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: true,
        // placeholder: `请选择命令类型`,
        ...dictComponentProps(DictEnum.LINK_DEVICE_COMMAND_TYPE),
      },
      colProps: { span: 5 },
    },
    {
      label: t('iot.link.device.device.status'),
      field: 'status',
      component: 'ApiSelect',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: true,
        // placeholder: `请选择状态`,
        ...dictComponentProps(DictEnum.LINK_DEVICE_COMMAND_STATUS),
      },
      colProps: { span: 5 },
    },
    {
      field: 'createTimeRange',
      label: t('iot.link.device.device.createdTime'),
      component: 'RangePicker',
      colProps: { span: 8 },
    },
  ];
};

// 编辑页字段
export const editFormSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
  return [];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
