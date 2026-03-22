import { Ref, h } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { Popover, Table } from 'ant-design-vue';
import { handleCopyText } from '/@/utils/thinglinks/common';
import { SvgIcon } from '/@/components/Icon';

const { t } = useI18n();
const column = [
  {
    title: '时间',
    dataIndex: 'timestamp',
    key: 'timestamp',
  },
  {
    title: '更新属性',
    dataIndex: 'message',
    key: 'message',
    customRender: ({ text }) => {
      return (
        <div style={{ maxWidth: '500px', maxHeight: '160px', overflow: 'auto' }}>
          {text}
          <SvgIcon
            name="copy"
            size="12"
            style={{ marginLeft: 8, cursor: 'pointer' }}
            onClick={() => handleCopyText(text)}
          />
        </div>
      );
    },
  },
];
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.productEmpower.productEmpower.empowermentIdentification'),
      dataIndex: 'empowermentIdentification',
    },
    {
      title: t('iot.link.productEmpower.productEmpower.empowermentType'),
      dataIndex: ['echoMap', 'empowermentType'],
    },
    {
      title: t('iot.link.productEmpower.productEmpower.status'),
      dataIndex: ['echoMap', 'status'],
    },
    {
      title: t('iot.link.productEmpower.productEmpower.dependencies'),
      dataIndex: 'dependencies',
    },
    {
      title: t('iot.link.productEmpower.productEmpower.startTime'),
      dataIndex: 'startTime',
    },
    {
      title: t('iot.link.productEmpower.productEmpower.endTime'),
      dataIndex: 'endTime',
    },
    // {
    //   title: t('iot.link.productEmpower.productEmpower.feedback'),
    //   dataIndex: 'feedback',
    //   ellipsis: true,
    //   customRender: ({ record, text }) => {
    //     const popoverContent = h(Table, {
    //       columns: column,
    //       size: 'small',
    //       pagination: false,
    //       dataSource: JSON.parse(record.feedback),
    //     });
    //     return h(
    //       Popover,
    //       {
    //         placement: 'top',
    //         trigger: 'click',
    //         content: popoverContent,
    //       },
    //       {
    //         default: () => text,
    //       },
    //     );
    //   },
    // },
    {
      title: t('iot.link.productEmpower.productEmpower.feedback'),
      dataIndex: 'feedback',
      ellipsis: true,
      customRender: ({ record, text }) => {
        try {
          const feedbackData = record.feedback ? JSON.parse(record.feedback) : [];
          const popoverContent = h(Table, {
            columns: column, // 确保 column 已定义
            size: 'small',
            pagination: false,
            dataSource: Array.isArray(feedbackData) ? feedbackData : [feedbackData], // 兼容数组/对象
          });
          return h(
            Popover,
            {
              placement: 'top',
              trigger: 'click',
              content: popoverContent,
            },
            {
              default: () => text || '--',
            },
          );
        } catch (err) {
          console.error('渲染 feedback 失败:', err);
          return text || '--';
        }
      },
    },
    {
      title: t('iot.link.productEmpower.productEmpower.remark'),
      dataIndex: 'remark',
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [];
};

// 编辑页字段
export const editFormSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      label: t('iot.link.productEmpower.productEmpower.dependencies'),
      field: 'dependencies',
      component: 'InputTextArea',
      colProps: {
        span: 22,
      },
      rules: [{ required: true }],
    },

    {
      label: t('iot.link.productEmpower.productEmpower.remark'),
      field: 'remark',
      component: 'InputTextArea',
      colProps: {
        span: 22,
      },
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
