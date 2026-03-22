import { Ref } from 'vue';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { query } from '/@/api/devOperation/tenant/tenant';
import { stateNumberComponentProps } from '/@/utils/thinglinks/common';
import { useModal } from '/@/components/Modal';

export function useIsvIndex() {
  const { t } = useI18n();
  const [registerKeysModal, { openModal: openKeysModal }] = useModal();

  function handleViewKeys(isvId: string, e: Event) {
    e?.stopPropagation();
    openKeysModal(true, { isvId, type: ActionEnum.VIEW });
  }
  function handleKeys(isvId: string, e: Event) {
    e?.stopPropagation();
    openKeysModal(true, { isvId, type: ActionEnum.EDIT });
  }

  // 列表页字段
  const columns = (): BasicColumn[] => {
    return [
      {
        title: t('devOperation.sop.sopIsvInfo.appId'),
        dataIndex: 'appId',
        width: 80,
        customRender: ({ record }) => {
          return (
            <a-button block onClick={(e) => handleViewKeys(record.id, e)} type="link">
              {t('common.title.view')}
            </a-button>
          );
        },
      },
      {
        title: t('devOperation.sop.sopIsvInfo.name'),
        dataIndex: 'name',
      },
      {
        title: t('devOperation.sop.sopIsvInfo.status'),
        dataIndex: 'status',
        width: 80,
        format: (_, record) => {
          return record.status === 1
            ? t('thinglinks.common.enable')
            : t('thinglinks.common.disable');
        },
      },
      {
        title: t('devOperation.sop.sopIsvInfo.remark'),
        dataIndex: 'remark',
      },
      {
        title: t('common.expirationDate'),
        dataIndex: 'expirationTime',
        width: 350,
        format: (_, row) => {
          if (row.startExpirationTime !== null && row.endExpirationTime !== null) {
            return `${row.startExpirationTime}至${row.endExpirationTime}`;
          } else if (row.startExpirationTime === null && row.endExpirationTime !== null) {
            return `截止至${row.endExpirationTime}`;
          } else if (row.startExpirationTime !== null && row.endExpirationTime === null) {
            return `${row.startExpirationTime}至永久`;
          } else {
            return t('common.permanentlyValid');
          }
        },
      },
      {
        title: t('devOperation.sop.sopIsvInfo.creationMethod'),
        dataIndex: ['echoMap', 'creationMethod'],
      },
      {
        title: t('devOperation.sop.sopIsvInfo.auditStatus'),
        dataIndex: ['echoMap', 'auditStatus'],
      },
      {
        title: t('devOperation.sop.sopIsvInfo.auditTime'),
        dataIndex: 'auditTime',
        width: 180,
      },
      {
        title: t('devOperation.sop.sopIsvInfo.submissionTime'),
        dataIndex: 'submissionTime',
        width: 180,
      },
      {
        title: t('thinglinks.common.createdTime'),
        dataIndex: 'createdTime',
        sorter: true,
        width: 180,
      },
    ];
  };

  const searchFormSchema = (): FormSchema[] => {
    return [
      {
        label: t('devOperation.sop.sopIsvInfo.tenantId'),
        field: 'tenantId',
        component: 'ApiSelect',
        colProps: { span: 6 },
        componentProps: {
          api: query,
          labelField: 'name',
          valueField: 'id',
          showSearch: true,
        },
      },
      {
        label: t('devOperation.sop.sopIsvInfo.appId'),
        field: 'appId',
        component: 'Input',
        colProps: { span: 6 },
      },
      {
        label: t('devOperation.sop.sopIsvInfo.name'),
        field: 'name',
        component: 'Input',
        colProps: { span: 6 },
      },
      {
        label: t('devOperation.sop.sopIsvInfo.status'),
        field: 'status',
        component: 'Input',
        colProps: { span: 6 },
      },
      {
        label: t('devOperation.sop.sopIsvInfo.creationMethod'),
        field: 'creationMethod',
        component: 'Input',
        colProps: { span: 6 },
      },

      {
        field: 'createTimeRange',
        label: t('thinglinks.common.createdTime'),
        component: 'RangePicker',
        colProps: { span: 6 },
      },
    ];
  };

  // 编辑页字段
  const editFormSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
    return [
      {
        field: 'id',
        label: 'ID',
        component: 'Input',
        show: false,
      },
      {
        label: t('devOperation.sop.sopIsvInfo.tenantId'),
        field: 'tenantId',
        component: 'ApiSelect',
        componentProps: {
          api: query,
          labelField: 'name',
          valueField: 'id',
          showSearch: true,
        },
      },
      {
        label: t('devOperation.sop.sopIsvInfo.appId'),
        field: 'appId',
        component: 'Input',
        dynamicDisabled: true,
      },
      {
        label: t('devOperation.sop.sopIsvInfo.name'),
        field: 'name',
        component: 'Input',
      },
      {
        label: t('devOperation.sop.sopIsvInfo.status'),
        field: 'status',
        component: 'RadioGroup',
        componentProps: {
          ...stateNumberComponentProps(),
        },
        defaultValue: '0',
      },
      {
        label: t('devOperation.sop.sopIsvInfo.notifyUrl'),
        field: 'notifyUrl',
        component: 'Input',
      },
      {
        label: t('devOperation.sop.sopIsvInfo.remark'),
        field: 'remark',
        component: 'InputTextArea',
      },
    ];
  };

  // 前端自定义表单验证规则
  const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
    return [];
  };
  return {
    columns,
    searchFormSchema,
    editFormSchema,
    customFormSchemaRules,
    registerKeysModal,
    handleKeys,
  };
}
