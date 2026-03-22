import { Ref } from 'vue';
import { FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import {
  dictComponentProps,
  stateComponentProps,
  yesNoComponentProps,
} from '/@/utils/thinglinks/common';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { FormSchemaExt, RuleType } from '/@/api/thinglinks/common/formValidateService';
import { DataScopeEnum, ResourceOpenWithEnum, ResourceTypeEnum } from '/@/enums/biz/tenant';
import { check, checkName, checkPath } from '/@/api/devOperation/application/defResource';
import { isUrl } from '/@/utils/is';

const { t } = useI18n();

// 编辑页字段
export const editFormSchema = (type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      field: 'id',
      label: 'ID',
      component: 'Input',
      show: false,
    },
    {
      label: t('devOperation.application.defResource.applicationId'),
      field: 'applicationId',
      component: 'Input',
      defaultValue: '0',
      colProps: {
        span: 12,
      },
      show: false,
    },
    {
      label: t('devOperation.application.defResource.parentId'),
      field: 'parentId',
      component: 'Input',
      show: false,
      defaultValue: '0',
      colProps: {
        span: 12,
      },
    },
    {
      label: t('devOperation.application.defResource.parentId'),
      field: 'parentResourceType',
      component: 'Input',
      show: false,
    },
    {
      label: t('devOperation.application.defResource.parentId'),
      field: 'parentIsHidden',
      component: 'RadioGroup',
      componentProps: {
        ...yesNoComponentProps(),
      },
      show: false,
    },
    {
      field: 'divider-selects1',
      component: 'Divider',
      label: t('devOperation.application.defResource.basicInfo'),
      colProps: {
        span: 24,
      },
    },
    {
      label: t('devOperation.application.defResource.resourceType'),
      field: 'resourceType',
      component: 'ApiRadioGroup',
      defaultValue: ResourceTypeEnum.MENU,
      componentProps: {
        ...dictComponentProps(DictEnum.ResourceTypeEnum),
      },
      helpMessage: [
        t('devOperation.application.defResource.resourceTypeHelpMsg[0]'),
        t('devOperation.application.defResource.resourceTypeHelpMsg[1]'),
        t('devOperation.application.defResource.resourceTypeHelpMsg[2]'),
        t('devOperation.application.defResource.resourceTypeHelpMsg[3]'),
      ],
      colProps: {
        span: 12,
      },
      dynamicRules: ({ model }) => {
        return [
          {
            trigger: 'change',
            validator: async (_, value) => {
              if (type.value === ActionEnum.VIEW) {
                return Promise.resolve();
              }
              if (model?.parentId === '0' || !model?.parentId) {
                if (value === ResourceTypeEnum.DATA) {
                  return Promise.reject(
                    t(
                      'devOperation.application.defResource.resourceTypeValidatorRule.validateRule1',
                    ),
                  );
                }
              } else {
                if (value === ResourceTypeEnum.MENU) {
                  if (ResourceTypeEnum.MENU !== model?.parentResourceType) {
                    return Promise.reject(
                      t(
                        'devOperation.application.defResource.resourceTypeValidatorRule.validateRule2',
                      ),
                    );
                  }
                  if (model?.parentIsHidden) {
                    return Promise.reject(
                      t(
                        'devOperation.application.defResource.resourceTypeValidatorRule.validateRule3',
                      ),
                    );
                  }
                }
              }

              if (model?.parentResourceType === ResourceTypeEnum.FUNCTION) {
                if (value === ResourceTypeEnum.MENU) {
                  return Promise.reject(
                    t(
                      'devOperation.application.defResource.resourceTypeValidatorRule.validateRule4',
                    ),
                  );
                } else if (value === ResourceTypeEnum.DATA) {
                  return Promise.reject(
                    t(
                      'devOperation.application.defResource.resourceTypeValidatorRule.validateRule5',
                    ),
                  );
                }
              } else if (model?.parentResourceType === ResourceTypeEnum.FIELD) {
                return Promise.reject(
                  t('devOperation.application.defResource.resourceTypeValidatorRule.validateRule6'),
                );
              }
              return Promise.resolve();
            },
          },
        ];
      },
    },
    {
      label: t('devOperation.application.defResource.parentName'),
      field: 'parentName',
      component: 'Input',
      dynamicDisabled: true,
      colProps: {
        span: 12,
      },
    },
    {
      label: t('devOperation.application.defResource.code'),
      field: 'code',
      component: 'Input',
      helpMessage: [
        t('devOperation.application.defResource.codeHelpMsg[0]'),
        t('devOperation.application.defResource.codeHelpMsg[1]'),
        t('devOperation.application.defResource.codeHelpMsg[2]'),
        t('devOperation.application.defResource.codeHelpMsg[3]'),
      ],
      colProps: {
        span: 12,
      },
      dynamicDisabled: () => {
        return [ActionEnum.VIEW].includes(type.value);
      },
    },
    {
      label: t('devOperation.application.defResource.name'),
      field: 'name',
      component: 'Input',
      colProps: {
        span: 12,
      },
      dynamicRules: ({ model }) => {
        return [
          {
            trigger: ['change', 'blur'],
            async validator(_, value) {
              if (type.value === ActionEnum.VIEW) {
                return Promise.resolve();
              }
              if (value) {
                if ([ResourceTypeEnum.MENU].includes(model.resourceType)) {
                  if (await checkName(value, model.applicationId, model?.id)) {
                    return Promise.reject(
                      t('devOperation.application.defResource.nameValidatorRule'),
                    );
                  }
                }
              }
              return Promise.resolve();
            },
          },
        ];
      },
    },
    {
      label: t('devOperation.application.defResource.icon'),
      field: 'icon',
      component: 'IconPicker',
      componentProps: {
        copy: true,
      },
      colProps: {
        span: 12,
      },
    },
    {
      label: t('devOperation.application.defResource.sortValue'),
      field: 'sortValue',
      component: 'InputNumber',
      defaultValue: 1000,
      helpMessage: t('devOperation.application.defResource.sortValueHelpMsg'),
      colProps: {
        span: 12,
      },
    },
    {
      label: t('devOperation.application.defResource.state'),
      field: 'state',
      component: 'Switch',
      defaultValue: true,
      componentProps: {
        ...stateComponentProps(),
        'checked-children': t('thinglinks.common.enable'),
        'un-checked-children': t('thinglinks.common.disable'),
      },
      helpMessage: t('devOperation.application.defResource.stateHelpMsg'),
      colProps: {
        span: 12,
      },
    },
    {
      label: t('devOperation.application.defResource.isGeneral'),
      field: 'isGeneral',
      component: 'Switch',
      defaultValue: false,
      componentProps: {
        // size: 'default',
        ...yesNoComponentProps(),
        'checked-children': t('thinglinks.common.yes'),
        'un-checked-children': t('thinglinks.common.no'),
      },
      helpMessage: t('devOperation.application.defResource.isGeneralHelpMsg'),
      colProps: {
        span: 12,
      },
    },
    {
      field: 'divider-selects2',
      component: 'Divider',
      label: t('devOperation.application.defResource.featuresInfo'),
      colProps: {
        span: 24,
      },
      helpMessage: [t('devOperation.application.defResource.featuresInfoHelpMsg')],
      ifShow: ({ values }) => {
        return [ResourceTypeEnum.MENU, ResourceTypeEnum.FIELD, ResourceTypeEnum.DATA].includes(
          values.resourceType,
        );
      },
    },
    {
      label: t('devOperation.application.defResource.openWith'),
      field: 'openWith',
      component: 'ApiRadioGroup',
      defaultValue: ResourceOpenWithEnum.INNER_COMPONENT,
      ifShow: ({ values }) => {
        return [ResourceTypeEnum.MENU].includes(values.resourceType);
      },
      colProps: {
        span: 12,
      },
      helpMessage: [
        t('devOperation.application.defResource.openWithHelpMsg[0]'),
        t('devOperation.application.defResource.openWithHelpMsg[1]'),
        t('devOperation.application.defResource.openWithHelpMsg[2]'),
      ],
      componentProps: ({ formActionType }) => {
        return {
          ...dictComponentProps(DictEnum.ResourceOpenWithEnum),
          onChange: (value: string) => {
            const { setFieldsValue, validateFields } = formActionType;
            switch (value) {
              case ResourceOpenWithEnum.INNER_CHAIN:
                setFieldsValue({
                  component: 'IFRAME',
                });
                break;
              case ResourceOpenWithEnum.OUTER_CHAIN:
                setFieldsValue({
                  component: 'IFRAME',
                });
                break;
              default:
                break;
            }
            validateFields(['link', 'path', 'component']);
          },
        };
      },
    },
    {
      label: t('devOperation.application.defResource.isHidden'),
      field: 'isHidden',
      component: 'Switch',
      defaultValue: false,
      componentProps: {
        ...yesNoComponentProps(),
        'checked-children': t('thinglinks.common.yes'),
        'un-checked-children': t('thinglinks.common.no'),
      },
      helpMessage: t('devOperation.application.defResource.isHiddenHelpMsg'),
      colProps: {
        span: 12,
      },
      ifShow: ({ values }) => {
        return [ResourceTypeEnum.MENU].includes(values.resourceType);
      },
    },
    {
      label: t('devOperation.application.defResource.link'),
      field: 'link',
      component: 'Input',
      colProps: {
        span: 24,
      },
      ifShow: ({ values }) => {
        return [ResourceOpenWithEnum.INNER_CHAIN, ResourceOpenWithEnum.OUTER_CHAIN].includes(
          values.openWith,
        );
      },
      dynamicRules: () => {
        return [
          {
            trigger: ['change', 'blur'],
            validator: async (_, value) => {
              if (type.value === ActionEnum.VIEW) {
                return Promise.resolve();
              }
              if (value) {
                if (isUrl(value)) {
                  return Promise.resolve();
                } else {
                  return Promise.reject(
                    t('devOperation.application.defResource.linkValidatorRule.validateRule1'),
                  );
                }
              }
              return Promise.reject(
                t('devOperation.application.defResource.linkValidatorRule.validateRule2'),
              );
            },
          },
        ];
      },
    },
    {
      label: t('devOperation.application.defResource.path'),
      field: 'path',
      component: 'Input',
      helpMessage: [t('devOperation.application.defResource.pathHelpMsg')],
      itemProps: {
        extra: t('devOperation.application.defResource.pathDescription'),
      },
      colProps: {
        span: 12,
      },
      ifShow: ({ values }) => {
        return [ResourceTypeEnum.MENU].includes(values.resourceType);
      },
      dynamicRules: ({ model }) => {
        return [
          {
            trigger: ['change', 'blur'],
            validator: async (_, value) => {
              if (type.value === ActionEnum.VIEW) {
                return Promise.resolve();
              }
              if (value) {
                if (model?.parentId === '0' && !value.startsWith('/')) {
                  return Promise.reject(
                    t('devOperation.application.defResource.pathValidateRule.validateRule1'),
                  );
                }
                if (await checkPath(value, model.applicationId, model?.id)) {
                  return Promise.reject(
                    t('devOperation.application.defResource.pathValidateRule.validateRule2'),
                  );
                }
              }

              return Promise.resolve();
            },
          },
        ];
      },
    },
    {
      label: t('devOperation.application.defResource.component'),
      field: 'component',
      component: 'AutoComplete',
      itemProps: {
        extra: t('devOperation.application.defResource.componentExtra'),
      },
      helpMessage: [
        t('devOperation.application.defResource.componentHelpMsg[0]'),
        t('devOperation.application.defResource.componentHelpMsg[1]'),
      ],
      colProps: {
        span: 12,
      },
      componentProps: () => {
        return {
          allowClear: true,
          getPopupContainer: () => document.body,
          filterOption: (input: string, option) => {
            return option.value.toUpperCase().indexOf(input.toUpperCase()) >= 0;
          },
          options: [{ value: 'LAYOUT' }, { value: 'IFRAME' }],
        };
      },
      dynamicDisabled: ({ values }) => {
        return (
          [ResourceOpenWithEnum.INNER_CHAIN, ResourceOpenWithEnum.OUTER_CHAIN].includes(
            values.openWith,
          ) || [ActionEnum.VIEW].includes(type.value)
        );
      },
      ifShow: ({ values }) => {
        return [ResourceTypeEnum.MENU].includes(values.resourceType);
      },
      dynamicRules: () => {
        return [
          {
            trigger: ['change', 'blur'],
            async validator(_, value) {
              if (type.value === ActionEnum.VIEW) {
                return Promise.resolve();
              }
              if (value) {
                return Promise.resolve();
              }
              return Promise.reject(`请输入${t('devOperation.application.defResource.component')}`);
            },
          },
        ];
      },
    },
    {
      label: t('devOperation.application.defResource.redirect'),
      field: 'redirect',
      component: 'Input',
      colProps: {
        span: 12,
      },
      ifShow: ({ values }) => {
        return [ResourceTypeEnum.MENU].includes(values.resourceType);
      },
    },
    {
      label: t('devOperation.application.defResource.subGroup'),
      field: 'subGroup',
      component: 'Input',
      helpMessage: [t('devOperation.application.defResource.subGroupHelpMsg')],
      colProps: {
        span: 12,
      },
      ifShow: ({ values }) => {
        return [ResourceTypeEnum.MENU].includes(values.resourceType);
      },
    },

    {
      label: t('devOperation.application.defResource.dataScope'),
      field: 'dataScope',
      component: 'ApiRadioGroup',
      defaultValue: DataScopeEnum.SELF,
      componentProps: {
        ...dictComponentProps(DictEnum.DataScopeEnum),
      },
      ifShow: ({ values }) => {
        return values.resourceType === ResourceTypeEnum.DATA;
      },
      helpMessage: [
        t('devOperation.application.defResource.dataScopeHelpMsg[0]'),
        t('devOperation.application.defResource.dataScopeHelpMsg[1]'),
      ],
      colProps: {
        span: 24,
      },
    },
    {
      label: t('devOperation.application.defResource.isDef'),
      field: 'isDef',
      component: 'Switch',
      defaultValue: false,
      componentProps: {
        ...yesNoComponentProps(),
        'checked-children': t('thinglinks.common.yes'),
        'un-checked-children': t('thinglinks.common.no'),
      },
      ifShow: ({ values }) => {
        return values.resourceType === ResourceTypeEnum.DATA;
      },
      helpMessage: [t('devOperation.application.defResource.isDefHelpMsg')],
      colProps: {
        span: 12,
      },
    },
    {
      label: t('devOperation.application.defResource.customClass'),
      field: 'customClass',
      component: 'Input',
      ifShow: ({ values }) => {
        return (
          values.resourceType === ResourceTypeEnum.DATA && values.dataScope === DataScopeEnum.CUSTOM
        );
      },
      colProps: {
        span: 12,
      },
      helpMessage: [
        t('devOperation.application.defResource.customClassHelpMsg[0]'),
        t('devOperation.application.defResource.customClassHelpMsg[1]'),
        t('devOperation.application.defResource.customClassHelpMsg[2]'),
      ],
      componentProps: {
        placeholder: t('devOperation.application.defResource.customClassPlaceHolder'),
      },
      dynamicRules: ({ model }) => {
        return [
          {
            trigger: 'blur',
            async validator(_, value) {
              if (type.value === ActionEnum.VIEW) {
                return Promise.resolve();
              }
              if (model.dataScope === DataScopeEnum.CUSTOM && !value) {
                return Promise.reject(t('common.rules.require'));
              }
              return Promise.resolve();
            },
          },
        ];
      },
    },

    {
      label: t('devOperation.application.defResource.fieldIsSecret'),
      field: 'fieldIsSecret',
      component: 'Switch',
      defaultValue: false,
      componentProps: {
        ...yesNoComponentProps(),
        'checked-children': t('thinglinks.common.yes'),
        'un-checked-children': t('thinglinks.common.no'),
      },
      ifShow: ({ values }) => {
        return values.resourceType === ResourceTypeEnum.FIELD;
      },
      colProps: {
        span: 12,
      },
    },
    {
      label: t('devOperation.application.defResource.fieldIsEdit'),
      field: 'fieldIsEdit',
      component: 'Switch',
      defaultValue: true,
      componentProps: {
        ...yesNoComponentProps(),
        'checked-children': t('thinglinks.common.yes'),
        'un-checked-children': t('thinglinks.common.no'),
      },
      ifShow: ({ values }) => {
        return values.resourceType === ResourceTypeEnum.FIELD;
      },
      colProps: {
        span: 12,
      },
    },
    {
      label: t('devOperation.application.defResource.apiList'),
      field: 'resourceApiList',
      component: 'Input',
      slot: 'resourceApiList',
      defaultValue: [],
      ifShow: ({ values }) => {
        return [ResourceTypeEnum.MENU, ResourceTypeEnum.FUNCTION].includes(values.resourceType);
      },
    },
    {
      field: 'divider-selects3',
      component: 'Divider',
      label: t('devOperation.application.defResource.expansionInfo'),
      colProps: {
        span: 24,
      },
    },
    {
      label: t('devOperation.application.defResource.metaJson'),
      field: 'metaJson',
      component: 'Input',
      slot: 'metaJson',
      helpMessage: [
        t('devOperation.application.defResource.metaJsonHelpMsg[0]'),
        t('devOperation.application.defResource.metaJsonHelpMsg[1]'),
      ],
    },
    {
      label: t('devOperation.application.defResource.describe'),
      field: 'describe',
      component: 'InputTextArea',
      colProps: {
        span: 24,
      },
      componentProps: {
        'auto-size': { minRows: 2, maxRows: 3 },
      },
    },
  ];
};

const CODE_REG = /^[a-zA-Z0-9_:,;*]*$/;

// 前端自定义表单验证规则
export const customFormSchemaRules = (
  type: Ref<ActionEnum>,
  getFieldsValue: () => Recordable,
): Partial<FormSchemaExt>[] => {
  return [
    {
      field: 'code',
      type: RuleType.append,
      rules: [
        {
          trigger: 'blur',
          async validator(_, value) {
            if (![ActionEnum.EDIT, ActionEnum.ADD].includes(type.value)) {
              return Promise.resolve();
            }

            const model = await getFieldsValue();

            if (value) {
              if (!CODE_REG.test(value)) {
                return Promise.reject(
                  t('devOperation.application.defResource.codeValidatorRule.validateRule1'),
                );
              }
              if (await check(value, model.id)) {
                return Promise.reject(
                  t('devOperation.application.defResource.codeValidatorRule.validateRule2'),
                );
              }
            }

            return Promise.resolve();
          },
        },
      ],
    },
  ];
};
