import { Ref } from 'vue';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import {
  dictComponentProps,
  dictComponentProps2,
  yesNoComponentProps,
} from '/@/utils/thinglinks/common';

const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.ruleName'),
      dataIndex: 'ruleName',
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.ruleLevel'),
      dataIndex: 'ruleLevel',
      slots: { customRender: 'ruleLevel' },
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.productIdentification'),
      dataIndex: 'productIdentification',
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.deviceIdentification'),
      dataIndex: 'deviceIdentification',
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.priority'),
      dataIndex: 'priority',
      sorter: true,
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.actionType'),
      dataIndex: 'actionType',
      slots: { customRender: 'actionType' },
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.topicPattern'),
      dataIndex: 'topicPattern',
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.ipWhitelist'),
      dataIndex: 'ipWhitelist',
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.decision'),
      dataIndex: 'decision',
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.enabled'),
      dataIndex: 'enabled',
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('iot.link.operationMaintenance.accessControl.deviceAclRule.createdOrgId'),
      dataIndex: 'createdOrgId',
    },
    {
      title: t('thinglinks.common.createdTime'),
      dataIndex: 'createdTime',
      sorter: true,
      width: 180,
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.ruleName'),
      field: 'ruleName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.ruleLevel'),
      field: 'ruleLevel',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_ACL_RULE_LEVEL),
      },
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.productIdentification'),
      field: 'productIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.deviceIdentification'),
      field: 'deviceIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.actionType'),
      field: 'actionType',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_ACL_RULE_ACTION_TYPE),
      },
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.decision'),
      field: 'decision',
      component: 'RadioButtonGroup',
      componentProps: {
        ...yesNoComponentProps(),
      },
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
export const editFormSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      field: 'id',
      label: 'ID',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.ruleName'),
      field: 'ruleName',
      component: 'Input',
      required: true,
      helpMessage: [
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ruleName[0]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ruleName[1]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ruleName[2]'),
      ],
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.ruleLevel'),
      field: 'ruleLevel',
      component: 'ApiSelect',
      required: true,
      defaultValue: 0,
      componentProps: {
        ...dictComponentProps2({
          type: DictEnum.LINK_ACL_RULE_LEVEL,
          extendFirst: true,
          stringToNumber: true,
        }),
      },
      helpMessage: [
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ruleLevel[0]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ruleLevel[1]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ruleLevel[2]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ruleLevel[3]'),
      ],
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.productIdentification'),
      field: 'productIdentification',
      required: true,
      component: 'Input',
      helpMessage: [
        t(
          'iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.productIdentification[0]',
        ),
        t(
          'iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.productIdentification[1]',
        ),
        t(
          'iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.productIdentification[2]',
        ),
      ],
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.deviceIdentification'),
      show: ({ values }) => {
        return values.ruleLevel == 1;
      },
      field: 'deviceIdentification',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.priority'),
      field: 'priority',
      component: 'InputNumber',
      required: true,
      defaultValue: 500,
      helpMessage: [
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.priority[0]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.priority[1]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.priority[2]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.priority[3]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.priority[4]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.priority[5]'),
      ],
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.actionType'),
      field: 'actionType',
      component: 'ApiSelect',
      defaultValue: 0,
      required: true,
      componentProps: {
        ...dictComponentProps2({
          type: DictEnum.LINK_ACL_RULE_ACTION_TYPE,
          extendFirst: true,
          stringToNumber: true,
        }),
      },
      helpMessage: [
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.actionType[0]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.actionType[1]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.actionType[2]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.actionType[3]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.actionType[4]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.actionType[5]'),
      ],
    },

    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.ipWhitelist'),
      field: 'ipWhitelist',
      component: 'Input',
      required: true,
      helpMessage: [
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ipWhitelist[0]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ipWhitelist[1]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ipWhitelist[2]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.ipWhitelist[3]'),
      ],
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.decision'),
      field: 'decision',
      component: 'RadioGroup',
      required: true,
      defaultValue: '1',
      componentProps: {
        ...yesNoComponentProps(),
      },
      helpMessage: [
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.decision[0]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.decision[1]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.decision[2]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.decision[3]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.decision[4]'),
      ],
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.enabled'),
      field: 'enabled',
      component: 'RadioGroup',
      required: true,
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.topicPattern'),
      field: 'topicPattern',
      component: 'Input',
      required: true,
      colProps: { span: 22 },
      helpMessage: [
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.topicPattern[0]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.topicPattern[1]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.topicPattern[2]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.topicPattern[3]'),
        t('iot.link.operationMaintenance.accessControl.deviceAclRule.helpMessage.topicPattern[4]'),
      ],
      colSlot: 'topicPattern',
    },
    {
      label: t('iot.link.operationMaintenance.accessControl.deviceAclRule.remark'),
      field: 'remark',
      component: 'InputTextArea',
      colProps: { span: 22 },
    },
  ];
};

export const topicColumns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.productTopic.productTopic.functionType'),
      dataIndex: ['echoMap', 'functionType'],
    },
    {
      title: t('iot.link.productTopic.productTopic.topic'),
      dataIndex: 'topic',
    },
    {
      title: t('iot.link.productTopic.productTopic.publisher'),
      dataIndex: ['echoMap', 'publisher'],
    },
    {
      title: t('iot.link.productTopic.productTopic.subscriber'),
      dataIndex: ['echoMap', 'subscriber'],
    },
    {
      title: t('iot.link.productTopic.productTopic.remark'),
      dataIndex: 'remark',
    },
  ];
};
// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
