<template>
  <BasicDrawer
    v-bind="$attrs"
    @register="registerDrawer"
    showFooter
    width="50%"
    :maskClosable="false"
    title="查看秘钥"
    okText="下载开发者秘钥"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm" />
  </BasicDrawer>
</template>
<script lang="ts">
  import { defineComponent, ref } from 'vue';
  import { BasicDrawer, useDrawerInner } from '/@/components/Drawer';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { getKeys } from '/@/api/basic/open/accessKey';
  import { showFormSchema, downloadTemplate } from './data';
  import { downloadByData } from '/@/utils/file/download';

  export default defineComponent({
    name: '查看秘钥',
    components: { BasicDrawer, BasicForm },
    emits: ['success', 'register'],
    setup(_) {
      const { t } = useI18n();
      const type = ref<ActionEnum>(ActionEnum.VIEW);
      const [registerForm, { setFieldsValue, getFieldsValue, resetFields, resetSchema }] = useForm({
        labelWidth: 100,
        schemas: showFormSchema(type),
        readonly: true,
        showActionButtonGroup: false,
        baseColProps: { span: 24 },
        actionColOptions: {
          span: 23,
        },
      });

      const [registerDrawer, { setDrawerProps }] = useDrawerInner(async (data) => {
        await resetSchema(showFormSchema(type));
        await resetFields();
        setDrawerProps({ confirmLoading: false });

        const result = await getKeys(data?.record?.id);
        // 赋值
        const record = { ...result };
        await setFieldsValue(record);
      });

      async function handleSubmit() {
        try {
          setDrawerProps({ confirmLoading: true });

          const item = await getFieldsValue();
          let text = downloadTemplate
            .replace('{appId}', item.appId)
            .replace('{privateKeyIsv}', item.privateKeyIsv)
            .replace('{publicKeyPlatform}', item.publicKeyPlatform);

          downloadByData(text, `${item.appId}_${new Date().getTime()}.txt`);
        } finally {
          setDrawerProps({ confirmLoading: false });
        }
      }

      return { t, registerDrawer, registerForm, type, ActionEnum, handleSubmit };
    },
  });
</script>
