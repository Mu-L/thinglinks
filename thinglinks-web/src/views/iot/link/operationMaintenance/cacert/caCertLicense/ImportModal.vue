<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModel"
    :title="t('iot.link.operationMaintenance.cacert.caCertLicense.importTitle')"
    :maskClosable="false"
    @ok="handleSubmit"
    :keyboard="true"
  >
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { importPemCertificate } from '/@/api/iot/link/operationMaintenance/cacert/caCertLicense';
  import { importSchema } from './caCertLicense.data';

  export default defineComponent({
    name: '导入证书',
    components: { BasicModal, BasicForm },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const type = ref<ActionEnum>(ActionEnum.ADD);
      const { createMessage } = useMessage();

      const [registerForm, { setFieldsValue, resetFields, updateSchema, validate, resetSchema }] =
        useForm({
          name: 'DeviceAclRuleEdit',
          labelWidth: 120,
          schemas: importSchema(),
          showActionButtonGroup: false,
          baseColProps: { span: 22 },
          actionColOptions: {
            span: 22,
          },
        });

      const [registerModel, { setModalProps: setProps, closeModal: close }] = useModalInner(
        async () => {
          setProps({ confirmLoading: false });
          await resetSchema(importSchema());
          await resetFields();
        },
      );

      async function handleSubmit() {
        try {
          const params = await validate();
          setProps({ confirmLoading: true });
          await importPemCertificate(params);
          createMessage.success(t(`common.tips.importSuccess`));
          close();
          emit('success');
        } finally {
          setProps({ confirmLoading: false });
        }
      }

      return { type, t, registerModel, registerForm, handleSubmit };
    },
  });
</script>
