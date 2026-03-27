<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModel"
    :title="t(`common.title.${type}`)"
    :maskClosable="false"
    @ok="handleSubmit"
    :keyboard="true"
  >
    <BasicForm @register="registerForm">
      <template #scriptContent="{ model, field }">
        <codemirror
          v-model="model[field]"
          :autofocus="true"
          :extensions="scriptExtensions"
          :indent-with-tab="true"
          :style="{ height: '600px' }"
          :tab-size="2"
          placeholder="groovy 脚本"
        /> </template
    ></BasicForm>
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum, VALIDATE_API } from '/@/enums/commonEnum';
  import { Api, save, update } from '/@/api/iot/rule/groovy/ruleGroovyScript';
  import { getValidateRules } from '/@/api/thinglinks/common/formValidateService';
  import { customFormSchemaRules, editFormSchema } from './ruleGroovyScript.data';
  import { Codemirror } from 'vue-codemirror';
  import { java } from '@codemirror/lang-java';
  import { oneDark } from '@codemirror/theme-one-dark';

  export default defineComponent({
    name: '编辑规则脚本',
    components: { BasicModal, BasicForm, Codemirror },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const type = ref<ActionEnum>(ActionEnum.ADD);
      const { createMessage } = useMessage();
      const scriptExtensions = [java(), oneDark];

      const [registerForm, { setFieldsValue, resetFields, updateSchema, validate, resetSchema }] =
        useForm({
          name: 'RuleGroovyScriptEdit',
          labelWidth: 120,
          schemas: editFormSchema(type),
          showActionButtonGroup: false,
          disabled: (_) => {
            return unref(type) === ActionEnum.VIEW;
          },
          baseColProps: { span: 11 },
          actionColOptions: {
            span: 22,
          },
        });

      const [registerModel, { setModalProps: setProps, closeModal: close }] = useModalInner(
        async (data) => {
          type.value = data?.type || ActionEnum.ADD;
          setProps({ confirmLoading: false });
          await resetSchema(editFormSchema(type));
          await resetFields();

          if (unref(type) !== ActionEnum.ADD) {
            // 赋值
            const record = { ...data?.record };
            record.appId = String(record?.appId);
            record.namespace = String(record?.namespace);
            record.platformCode = String(record?.platformCode);
            record.productCode = String(record?.productCode);
            record.channelCode = String(record?.channelCode);
            record.businessCode = String(record?.businessCode);
            await setFieldsValue(record);
          }

          if (unref(type) !== ActionEnum.VIEW) {
            let validateApi = Api[VALIDATE_API[unref(type)]];
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
            if (unref(type) === ActionEnum.EDIT) {
              await update(params);
            } else {
              params.id = null;
              await save(params);
            }
            createMessage.success(t(`common.tips.${type.value}Success`));
          }
          close();
          emit('success');
        } finally {
          setProps({ confirmLoading: false });
        }
      }

      return { type, t, scriptExtensions, registerModel, registerForm, handleSubmit };
    },
  });
</script>
