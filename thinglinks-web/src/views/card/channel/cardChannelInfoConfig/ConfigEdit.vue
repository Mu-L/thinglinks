<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModel"
    :title="t(`common.title.${type}`)"
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
import { ActionEnum, VALIDATE_API } from '/@/enums/commonEnum';
import { Api, save, update } from '/@/api/card/channel/cardChannelInfoConfig';
import { getValidateRules } from '/@/api/thinglinks/common/formValidateService';
import { customFormSchemaRules, editFormSchema } from './cardChannelInfoConfig.data';

export default defineComponent({
  name: '编辑物联卡渠道信息配置表维护',
  components: { BasicModal, BasicForm },
  emits: ['success', 'register'],
  setup(_, { emit }) {
    const { t } = useI18n();
    const type = ref<ActionEnum>(ActionEnum.ADD);
    const infoId = ref()
    const { createMessage } = useMessage();

    const [registerForm, { setFieldsValue, resetFields, updateSchema, validate, resetSchema }] =
      useForm({
        name: 'CardChannelInfoConfigEdit',
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

    const [registerModel, { setModalProps: setProps, closeModal: close }] = useModalInner(
      async (data) => {
        setProps({ confirmLoading: false });
        await resetSchema(editFormSchema(type));
        await resetFields();
        type.value = data?.type || ActionEnum.ADD;
        infoId.value = data?.infoId;

        if (unref(type) !== ActionEnum.ADD) {
          // 赋值
          const record = { ...data?.record };
          await setFieldsValue(record);
        }

        // if (unref(type) !== ActionEnum.VIEW) {
        //   let validateApi = Api[VALIDATE_API[unref(type)]];
        //   await getValidateRules(validateApi, customFormSchemaRules(type)).then(async (rules) => {
        //     rules && rules.length > 0 && (await updateSchema(rules));
        //   });
        // }
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
            params.channelId = infoId.value
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

    return { type, t, registerModel, registerForm, handleSubmit };
  },
});
</script>
