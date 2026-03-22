import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { yesNoComponentProps } from '/@/utils/thinglinks/common';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { FormSchemaExt, RuleType } from '/@/api/thinglinks/common/formValidateService';
import { check } from '/@/api/devOperation/ops/defInterface';
import { Rule } from '/@/components/Form';

const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.rule.groovy.ruleGroovyScript.appId'),
      dataIndex: 'appId',
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.namespace'),
      dataIndex: 'namespace',
      slots: { customRender: 'namespace' },
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.platformCode'),
      dataIndex: 'platformCode',
      slots: { customRender: 'platformCode' },
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.productCode'),
      dataIndex: 'productCode',
      slots: { customRender: 'platformCode' },
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.channelCode'),
      dataIndex: 'channelCode',
      slots: { customRender: 'channelCode' },
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.businessCode'),
      dataIndex: 'businessCode',
      slots: { customRender: 'businessCode' },
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.businessIdentification'),
      dataIndex: 'businessIdentification',
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.enable'),
      dataIndex: 'enable',
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.scriptContent'),
      dataIndex: 'scriptContent',
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.extendParams'),
      dataIndex: 'extendParams',
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.objectVersion'),
      dataIndex: 'objectVersion',
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('iot.rule.groovy.ruleGroovyScript.createdOrgId'),
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
      label: t('iot.rule.groovy.ruleGroovyScript.appId'),
      field: 'appId',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_APPLICATION_SCENARIO),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.namespace'),
      field: 'namespace',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.RULE_GROOVY_SCRIPT_NAMESPACE_TYPE),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.platformCode'),
      field: 'platformCode',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.RULE_GROOVY_SCRIPT_PLATFORM_CODE),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.productCode'),
      field: 'productCode',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.RULE_GROOVY_SCRIPT_PRODUCT_CODE),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.channelCode'),
      field: 'channelCode',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.RULE_GROOVY_SCRIPT_CHANNEL_CODE),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.businessCode'),
      field: 'businessCode',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.RULE_GROOVY_SCRIPT_BUSINESS_CODE),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.businessIdentification'),
      field: 'businessIdentification',
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
export const editFormSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      field: 'id',
      label: 'ID',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.name'),
      field: 'name',
      component: 'Input',
    },
    {
      label: t('iot.link.device.device.appId'),
      field: 'appId',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_APPLICATION_SCENARIO),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.namespace'),
      field: 'namespace',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.RULE_GROOVY_SCRIPT_NAMESPACE_TYPE),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.platformCode'),
      field: 'platformCode',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.RULE_GROOVY_SCRIPT_PLATFORM_CODE),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.productCode'),
      field: 'productCode',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.RULE_GROOVY_SCRIPT_PRODUCT_CODE),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.channelCode'),
      field: 'channelCode',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.RULE_GROOVY_SCRIPT_CHANNEL_CODE),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.businessCode'),
      field: 'businessCode',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.RULE_GROOVY_SCRIPT_BUSINESS_CODE),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.businessIdentification'),
      field: 'businessIdentification',
      component: 'Input',
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.enable'),
      field: 'enable',
      component: 'RadioGroup',
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.objectVersion'),
      field: 'objectVersion',
      component: 'Input',
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.scriptContent'),
      field: 'scriptContent',
      component: 'Input',
      slot: 'scriptContent',
      itemProps: {
        extra: 'groovy 脚本',
      },
      colProps: { span: 24 },
      dynamicRules: ({ values }) => {
        const rules: Rule[] = [];
        rules.push({ required: true, message: t('common.rules.require'), ruleType: RuleType.append });
        return rules;
      },
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.extendParams'),
      field: 'extendParams',
      component: 'Input',
    },
    {
      label: t('iot.rule.groovy.ruleGroovyScript.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (
  _type: Ref<ActionEnum>,
  getFieldsValue: () => Recordable,
): Partial<FormSchemaExt>[] => {
  return [
    {
      field: 'code',
      type: RuleType.append,
      rules: [
        {
          trigger: ['change', 'blur'],
          async validator(_, value) {
            const model = await getFieldsValue();
            if (value && (await check(value, model?.id))) {
              return Promise.reject(t('devOperation.ops.defInterface.code') + '已经存在');
            }
            return Promise.resolve();
          },
        },
      ],
    },
  ];
};
