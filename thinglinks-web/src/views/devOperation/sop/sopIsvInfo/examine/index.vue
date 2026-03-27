<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModel"
    :title="t('common.title.review')"
    :maskClosable="false"
    @ok="handleSubmit"
    width="80%"
    :keyboard="true"
  >
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { editFormSchema } from './data';
  import { examine } from '/@/api/devOperation/sop/sopIsvInfo';

  export default defineComponent({
    name: '审核',
    components: { BasicModal, BasicForm },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const { createMessage } = useMessage();

      const [registerForm, { setFieldsValue, resetFields, validate, resetSchema }] = useForm({
        name: t('common.title.review'),
        labelWidth: 150,
        schemas: editFormSchema(),
        showActionButtonGroup: false,
        baseColProps: { span: 24 },
        actionColOptions: {
          span: 23,
        },
      });

      const [registerModel, { setModalProps: setProps, closeModal: close }] = useModalInner(
        async (data) => {
          setProps({ confirmLoading: false });
          await resetSchema(editFormSchema());
          await resetFields();
          const isvId = data?.isvId;

          // 赋值
          await setFieldsValue({ id: isvId });
        },
      );

      async function handleSubmit() {
        try {
          const params = await validate();
          setProps({ confirmLoading: true });

          await examine(params);

          createMessage.success(t('sys.api.operationSuccess'));
          close();
          emit('success');
        } finally {
          setProps({ confirmLoading: false });
        }
      }

      return { ActionEnum, t, registerModel, registerForm, handleSubmit };
    },
  });
</script>
