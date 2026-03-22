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
      <template #topicPattern="{ model, field }">
        <div class="editor_container">
          <FormItem :name="field" :rules="[{ required: true }]">
            <template #label>
              <div>{{
                t('iot.link.operationMaintenance.accessControl.deviceAclRule.topicPattern')
              }}</div>
            </template>
            <a-input
              v-model:value="model[field]"
              :rules="[{ required: true }]"
              :placeholder="t('common.inputText')"
            />
          </FormItem>
          <a-button
            :disabled="!model.productIdentification"
            class="editor_btn"
            @click="openCodeModal(model)"
          >
            <PlusCircleOutlined />
          </a-button>
        </div> </template
    ></BasicForm>
    <SelectTopicModal v-bind="$attrs" @register="registerModal" @success="handleSuccess" />
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, unref } from 'vue';
  import { useAttrs } from '/@/hooks/core/useAttrs';
  import { Input, Space, Form, Button } from 'ant-design-vue';
  import { PlusCircleOutlined } from '@ant-design/icons-vue';
  import { BasicModal, useModalInner, useModal } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum, VALIDATE_API } from '/@/enums/commonEnum';
  import {
    Api,
    save,
    update,
  } from '/@/api/iot/link/operationMaintenance/accessControl/deviceAclRule';
  import { getValidateRules } from '/@/api/thinglinks/common/formValidateService';
  import { customFormSchemaRules, editFormSchema } from './deviceAclRule.data';
  import SelectTopicModal from './selectTopic.vue';

  export default defineComponent({
    name: '编辑ACL规则',
    components: {
      BasicModal,
      BasicForm,
      FormItem: Form.Item,
      Input,
      Space,
      Button,
      PlusCircleOutlined,
      SelectTopicModal,
    },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const attrs = useAttrs();
      const type = ref<ActionEnum>(ActionEnum.ADD);
      const { createMessage } = useMessage();

      const [registerForm, { setFieldsValue, resetFields, updateSchema, validate, resetSchema }] =
        useForm({
          name: 'DeviceAclRuleEdit',
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

      const [registerModal, { openModal }] = useModal();
      const openCodeModal = (form) => {
        openModal(true, { productIdentification: form.productIdentification });
      };

      const handleSuccess = (value: string) => {
        setFieldsValue({ topicPattern: value });
      };

      return {
        type,
        t,
        registerModel,
        registerForm,
        handleSubmit,
        ActionEnum,
        attrs,
        registerModal,
        openCodeModal,
        handleSuccess,
      };
    },
  });
</script>
<style lang="less" scoped>
  .editor_container {
    display: flex;
    align-items: center;

    .editor_btn {
      width: 50px;
      margin-left: 10px;
    }
  }

  :deep(.ant-col .ant-form-item-label) {
    width: 120px;
  }

  :deep(.ant-col .ant-form-item-control) {
    flex: 1;
  }

  :deep(.ant-row .ant-form-item) {
    flex: 1;
  }
</style>
