<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    showFooter
    width="30%"
    :maskClosable="false"
    :title="t('basic.open.accessKey.application')"
    @ok="handleSave"
    :okText="t('common.saveText')"
  >
    <BasicForm @register="registerForm" />
    <template
      #appendFooter
      v-if="type === ActionEnum.EDIT && [0, 99].includes(row.auditStatus as number) && row.status !== 1"
    >
      <a-button type="primary" @click="handleSubmit">
        {{ t('basic.open.accessKey.submit') }}
      </a-button>
    </template>
  </BasicDrawer>
</template>
<script lang="ts">
  import { defineComponent, reactive, ref } from 'vue';
  import { BasicDrawer, useDrawerInner } from '/@/components/Drawer';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { applyFor, update, submit } from '/@/api/basic/open/accessKey';
  import { editFormSchema } from './data';
  import { IsvInfoVO } from '/@/api/basic/open/model/accessKeyModel';

  export default defineComponent({
    name: '申请AccessKey',
    components: { BasicDrawer, BasicForm },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const type = ref<ActionEnum>(ActionEnum.VIEW);
      const row = reactive<IsvInfoVO>({});
      const [registerForm, { validate, setFieldsValue, resetFields, resetSchema }] = useForm({
        labelWidth: 100,
        schemas: editFormSchema(type),
        showActionButtonGroup: false,
        baseColProps: { span: 24 },
        actionColOptions: {
          span: 23,
        },
      });

      const [registerDrawer, { setDrawerProps, closeDrawer }] = useDrawerInner(async (data) => {
        type.value = data?.type;
        await resetSchema(editFormSchema(type));
        await resetFields();
        setDrawerProps({ confirmLoading: false });
        // 赋值
        const record = { ...data?.record };
        row.auditStatus = record.auditStatus;
        row.status = record.status;
        await setFieldsValue(record);
      });

      async function handleSubmit() {
        try {
          setDrawerProps({ confirmLoading: true });
          const data = await validate();

          await submit(data);
          closeDrawer();
          emit('success');
        } finally {
          setDrawerProps({ confirmLoading: false });
        }
      }
      async function handleSave() {
        try {
          setDrawerProps({ confirmLoading: true });
          const data = await validate();

          if (type.value === ActionEnum.ADD) {
            await applyFor(data);
          } else {
            await update(data);
          }
          closeDrawer();
          emit('success');
        } finally {
          setDrawerProps({ confirmLoading: false });
        }
      }

      return { t, row, registerDrawer, registerForm, type, ActionEnum, handleSave, handleSubmit };
    },
  });
</script>
