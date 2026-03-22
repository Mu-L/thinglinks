<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModel"
    :title="t(`common.title.${type}`)"
    :maskClosable="false"
    @ok="handleSubmit"
    :keyboard="true"
  >
    <a-form ref="formRef" :model="formState" :label-col="{ span: 4 }" :wrapper-col="{ span: 16 }">
      <a-form-item
        v-for="(item, index) in formState.ports"
        :key="index"
        :label=" t('iot.rule.plugin.pluginInfo.installationSuccessful', {ind: index + 1})"
        :name="`ports[${index}].value`"
        :rules="[
          { required: true, message: t('iot.rule.plugin.pluginInfo.portPlaceholder') },
          { pattern: /^(?:[1-9]\d{0,4}|65535)$/, message: t('iot.rule.plugin.pluginInfo.portValidation') },
          { validator: validatePortUniqueness, message: t('iot.rule.plugin.pluginInfo.portDuplicateError'), trigger: 'blur' },
        ]"
      >
        <a-input-group compact style="display: flex">
          <a-input-number
            v-model:value="item.value"
            placeholder="请输入端口号"
            style="margin-right: 15px"
          />
          <!-- <a-button @click="removePort(index)" danger>删除 </a-button> -->
        </a-input-group>
      </a-form-item>
      <!-- <a-form-item :wrapper-col="{ span: 15, offset: 4 }">
        <a-button type="dashed" @click="addPort" style="width: 100%">
          <PlusOutlined />
          新增端口
        </a-button>
      </a-form-item> -->
    </a-form>
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, unref, reactive } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { PlusOutlined } from '@ant-design/icons-vue';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum, VALIDATE_API } from '/@/enums/commonEnum';
  import { Api, save, update } from '/@/api/iot/link/deviceAction/deviceAction';
  // import { CloseOutlined } from '@ant-design/icons-vue';
  import { Form } from 'ant-design-vue';
  // import { customFormSchemaRules, editFormSchema } from './installPlugin.data';

  export default defineComponent({
    name: '编辑设备',
    components: { BasicModal, BasicForm, AForm: Form, PlusOutlined },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const type = ref<ActionEnum>(ActionEnum.EDIT);
      const { createMessage } = useMessage();
      const formRef = ref();
      const formState = ref({
        ports: [{ value: '' }],
      });
      const validatePortUniqueness = (_, value, callback) => {
        const ports = formState.value.ports.map((port) => port.value.trim());
        const uniquePorts = new Set(ports);
        if (ports.length !== uniquePorts.size) {
          callback(t('iot.rule.plugin.pluginInfo.portDuplicateError'));
        } else {
          callback();
        }
      };

      const addPort = () => {
        formState.value.ports.push({ value: '' });
      };

      const removePort = (index) => {
        formState.value.ports.splice(index, 1);
      };

      const [registerModel, { setModalProps: setProps, closeModal: close }] = useModalInner(
        async (data) => {
          formRef.value.resetFields();
          formState.value.ports = data.record.portMappings.map((item) => ({
            value: String(item.port),
          }));
        },
      );

      async function handleSubmit() {
        console.log(formState.value);

        close();

        // try {
        //   const params = await validate();
        //   setProps({ confirmLoading: true });
        //   if (unref(type) !== ActionEnum.VIEW) {
        //     if (unref(type) === ActionEnum.EDIT) {
        //       await update(params);
        //     } else {
        //       params.id = null;
        //       await save(params);
        //     }
        //     createMessage.success(t(`common.tips.${type.value}Success`));
        //   }
        //   close();
        //   emit('success');
        // } finally {
        //   setProps({ confirmLoading: false });
        // }
      }

      return {
        type,
        t,
        formRef,
        registerModel,
        handleSubmit,
        formState,
        validatePortUniqueness,
        addPort,
        removePort,
      };
    },
  });
</script>
../../../../../api/iot/link/deviceAction/deviceAction
