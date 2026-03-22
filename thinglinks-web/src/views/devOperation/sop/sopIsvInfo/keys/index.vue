<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModel"
    :title="t('devOperation.sop.sopIsvInfo.setKey')"
    :maskClosable="false"
    @ok="handleSubmit"
    width="80%"
    :keyboard="true"
    :showOkBtn="type === ActionEnum.EDIT"
  >
    <BasicForm @register="registerForm" />
    <template #insertFooter>
      <a-button @click="handleDownload">{{ t('devOperation.sop.sopIsvInfo.download') }}</a-button>
    </template>
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { Api, getKeys, updateKeys } from '/@/api/devOperation/sop/sopIsvInfo';
  import { getValidateRules } from '/@/api/thinglinks/common/formValidateService';
  import { customFormSchemaRules, downloadTemplate, editFormSchema } from './data';
  import { downloadByData } from '/@/utils/file/download';

  export default defineComponent({
    name: '设置秘钥',
    components: { BasicModal, BasicForm },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const type = ref<ActionEnum>(ActionEnum.VIEW);
      const { createMessage } = useMessage();

      const [
        registerForm,
        { getFieldsValue, setFieldsValue, resetFields, updateSchema, validate, resetSchema },
      ] = useForm({
        name: 'SopIsvInfoKey',
        labelWidth: 150,
        schemas: editFormSchema(type),
        showActionButtonGroup: false,
        disabled: (_) => {
          return unref(type) === ActionEnum.VIEW;
        },
        baseColProps: { span: 24 },
        actionColOptions: {
          span: 23,
        },
      });

      const [registerModel, { setModalProps: setProps, closeModal: close }] = useModalInner(
        async (data) => {
          type.value = data?.type || ActionEnum.ADD;
          setProps({ confirmLoading: false });
          await resetSchema(editFormSchema(type));
          await resetFields();
          const isvId = data?.isvId;

          // 赋值
          const form = await getKeys(isvId);
          form.isvId = isvId;
          await setFieldsValue(form);

          if (unref(type) !== ActionEnum.VIEW) {
            let validateApi = Api.UpdateKeys;
            await getValidateRules(validateApi, customFormSchemaRules(type)).then(async (rules) => {
              rules && rules.length > 0 && (await updateSchema(rules));
            });
          }
        },
      );

      async function handleSubmit() {
        try {
          const params = await validate();
          setProps({ confirmLoading: true });

          if (unref(type) !== ActionEnum.VIEW) {
            await updateKeys(params);

            createMessage.success(t('devOperation.sop.sopIsvInfo.settingSuccess'));
          }
          close();
          emit('success');
        } finally {
          setProps({ confirmLoading: false });
        }
      }

      async function handleDownload() {
        const form = await getFieldsValue();

        const text = downloadTemplate
          .replace('{appId}', form.appId)
          .replace('{privateKeyIsv}', form.privateKeyIsv)
          .replace('{publicKeyPlatform}', form.publicKeyPlatform);

        downloadByData(text, `${form.appId}_秘钥_${Date.now()}.txt`);
      }

      return { handleDownload, ActionEnum, type, t, registerModel, registerForm, handleSubmit };
    },
  });
</script>
