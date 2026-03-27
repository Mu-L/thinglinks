<template>
  <Card :title="$t(`common.title.${type}`)" v-loading="loading">
    <BasicForm @register="registerForm" />

    <template #extra>
      <Button type="primary" :loading="buttonLoading" @click="handleSubmit">
        {{ $t('common.saveText') }}
      </Button>
    </template>
  </Card>
</template>
<script lang="ts">
  import { defineComponent, ref, unref } from 'vue';
  import { Card, Button } from 'ant-design-vue';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum, VALIDATE_API } from '/@/enums/commonEnum';
  import { Api, save, update } from '/@/api/devOperation/sop/sopHelpDoc';
  import { getValidateRules } from '/@/api/thinglinks/common/formValidateService';
  import { customFormSchemaRules, editFormSchema } from './sopHelpDoc.data';

  export default defineComponent({
    name: '编辑帮助内容管理',
    components: { BasicForm, Button, Card },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const type = ref<ActionEnum>(ActionEnum.ADD);
      const loading = ref(false);
      const buttonLoading = ref(false);
      const { createMessage } = useMessage();

      const [registerForm, { setFieldsValue, resetFields, updateSchema, validate, resetSchema }] =
        useForm({
          name: 'SopHelpDocEdit',
          labelWidth: 100,
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

      async function setData(data: Recordable) {
        loading.value = true;
        type.value = data?.type || ActionEnum.ADD;
        await resetSchema(editFormSchema(type));
        await resetFields();

        if (unref(type) !== ActionEnum.ADD) {
          // 赋值
          const record = { ...data?.record };
          await setFieldsValue(record);
        } else {
          await setFieldsValue({ parentId: data?.parent?.id });
        }

        if (unref(type) !== ActionEnum.VIEW) {
          let validateApi = Api[VALIDATE_API[unref(type)]];
          await getValidateRules(validateApi, customFormSchemaRules(type)).then(async (rules) => {
            rules && rules.length > 0 && (await updateSchema(rules));
          });
        }
        loading.value = false;
      }

      async function handleSubmit() {
        try {
          const params = await validate();
          buttonLoading.value = true;

          if (unref(type) === ActionEnum.EDIT) {
            await update(params);
          } else {
            params.id = null;
            await save(params);
          }
          createMessage.success(t(`common.tips.${type.value}Success`));
          emit('success');
        } finally {
          buttonLoading.value = false;
        }
      }

      return { type, t, setData, loading, buttonLoading, registerForm, handleSubmit };
    },
  });
</script>
