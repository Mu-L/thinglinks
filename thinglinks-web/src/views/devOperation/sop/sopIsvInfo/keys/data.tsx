import { Ref } from 'vue';
import { FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { createKeys } from '/@/api/devOperation/sop/sopIsvInfo';

const { t } = useI18n();

async function handleRestPlatformKeys(values: any, model) {
  try {
    const result = await createKeys({
      keyFormat: values.keyFormat,
    });

    model.publicKeyPlatform = result.publicKey;
    model.privateKeyPlatform = result.privateKey;
  } finally {
  }
}

async function handleResetIsvKeys(values: any, model) {
  try {
    const result = await createKeys({
      keyFormat: values.keyFormat,
    });

    model.publicKeyIsv = result.publicKey;
    model.privateKeyIsv = result.privateKey;
  } finally {
  }
}

// 编辑页字段
export const editFormSchema = (type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      field: 'isvId',
      label: 'isvId',
      component: 'Input',
      show: false,
    },
    {
      label: 'AppKey',
      field: 'appId',
      component: 'Input',
      componentProps: {
        readonly: true,
      },
    },
    {
      label: t('devOperation.sop.sopIsvKeys.keyFormat'),
      field: 'keyFormat',
      component: 'RadioGroup',
      dynamicDisabled: true,
      componentProps: {
        options: [
          { label: 'PKCS8(JAVA适用)', value: 1 },
          { label: 'PKCS1(非JAVA适用)', value: 2 },
        ],
      },
    },
    {
      label: t('devOperation.sop.sopIsvKeys.publicKeyIsv'),
      field: 'publicKeyIsv',
      component: 'InputTextArea',
    },
    {
      label: t('devOperation.sop.sopIsvKeys.privateKeyIsv'),
      field: 'privateKeyIsv',
      component: 'InputTextArea',
    },
    {
      label: ' ',
      field: 'resetIsvKeys',
      component: 'Input',
      show: type.value === ActionEnum.EDIT,
      render: ({ values, model }) => {
        return (
          <>
            <a-button onClick={() => handleResetIsvKeys(values, model)} type="primary">
              {t('devOperation.sop.sopIsvKeys.resetIsvKeys')}
            </a-button>
          </>
        );
      },
    },
    {
      label: t('devOperation.sop.sopIsvKeys.publicKeyPlatform'),
      field: 'publicKeyPlatform',
      component: 'InputTextArea',
    },
    {
      label: t('devOperation.sop.sopIsvKeys.privateKeyPlatform'),
      field: 'privateKeyPlatform',
      component: 'InputTextArea',
    },
    {
      label: ' ',
      field: 'restPlatformKeys',
      component: 'Input',
      show: type.value === ActionEnum.EDIT,
      render: ({ values, model }) => {
        return (
          <>
            <a-button onClick={() => handleRestPlatformKeys(values, model)} type="primary">
              {t('devOperation.sop.sopIsvKeys.restPlatformKeys')}
            </a-button>
          </>
        );
      },
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};

export const downloadTemplate = `
请妥善保管以下参数，切勿泄露。

AppId:
{appId}

开发者私钥(用来生成签名):
{privateKeyIsv}

平台公钥(用来校验签名是否正确):
{publicKeyPlatform}

`;
