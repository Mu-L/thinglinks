<template>
  <BasicModal v-bind="$attrs" @register="registerModel" :title="t(`common.title.${type}`)" :maskClosable="false"
    @ok="handleSubmit" :keyboard="true">
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>
<script lang="ts">
import { defineComponent, ref, unref, computed } from 'vue';
import { BasicModal, useModalInner } from '/@/components/Modal';
import { BasicForm, useForm } from '/@/components/Form/index';
import { useI18n } from '/@/hooks/web/useI18n';
import { useMessage } from '/@/hooks/web/useMessage';
import { ActionEnum, VALIDATE_API } from '/@/enums/commonEnum';
import { save } from '/@/api/iot/link/productEmpower/productEmpower';

import { editFormSchema } from './productEmpower.data';
import { useUserStore } from '/@/store/modules/user'

export default defineComponent({
  name: '新增产品赋能',
  components: { BasicModal, BasicForm },
  emits: ['success', 'register'],
  props: {
    productId: {
      type: String,
      default: '',
    }
  },
  setup(props, { emit }) {
    const { t } = useI18n();
    const type = ref<ActionEnum>(ActionEnum.ADD);
    const { createMessage } = useMessage();
    const serviceId = ref('')
    const userStore = useUserStore();
    const getUserInfo = computed(() => {
      return userStore.getUserInfo;
    });
    const [registerForm, { setFieldsValue, resetFields, updateSchema, validate, resetSchema }] =
      useForm({
        name: 'productEmpower',
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
        console.log(data, 'data-pro')
        setProps({ confirmLoading: false, width: 1000 });
        await resetSchema(editFormSchema(type));
        await resetFields();
        type.value = data?.type || ActionEnum.ADD;

        if (unref(type) !== ActionEnum.ADD) {
          // 赋值
          const record = { ...data?.record };
          await setFieldsValue(record);
        } else {
          serviceId.value = data?.serviceId
          await setFieldsValue({
            serviceId: data?.serviceId,
            createdOrgId: getUserInfo.value?.baseEmployee?.createdOrgId
          });
        }

        // if (unref(type) !== ActionEnum.VIEW) {
        //   let validateApi = Api[VALIDATE_API[unref(type)]];
        //   await getValidateRules(validateApi, customFormSchemaRules(type)).then(async (rules) => {
        //     rules && rules.length > 0 && (await updateSchema(rules));
        //   });
        // }
      },
    );
    console.log(props);

    async function handleSubmit() {
      try {
        const params = await validate();
        const fixedParams = {
          empowermentIdentification: props.productId,
          empowermentType: 1,
        }
        // console.log(params);

        // return false;
        setProps({ confirmLoading: true });
        await save({ ...params, ...fixedParams });
        createMessage.success(t(`common.tips.${type.value}Success`));
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
../../../../../api/iot/link/productEmpower/productEmpower