<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModel"
    :title="t('devOperation.sop.sopIsvInfo.setGroup')"
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
  import { listByGroupId, updateIsvGroup } from '/@/api/devOperation/sop/sopPermGroup';

  export default defineComponent({
    name: '设置分组',
    components: { BasicModal, BasicForm },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const { createMessage } = useMessage();

      const [registerForm, { setFieldsValue, resetFields, validate, resetSchema }] = useForm({
        name: 'SopIsvInfoGroup',
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
          const groupIdList = await listByGroupId(isvId);
          await setFieldsValue({ isvId, groupIdList });
        },
      );

      async function handleSubmit() {
        try {
          const params = await validate();
          setProps({ confirmLoading: true });

          await updateIsvGroup(params);

          createMessage.success(t('devOperation.sop.sopIsvInfo.settingSuccess'));
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
