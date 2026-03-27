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
      <!-- <template #portRangeStart="{ model, field }">
        <div class="editor_container">
          <a-form
            ref="formRef"
            :model="formState"
            :rules="rules"
            layout="vertical"
            @submit.prevent="handleSubmit"
          >
            <a-form-item label="端口号" name="portRange" :rules="rules" required>
              <a-space>
                <a-input-number
                  v-model:value="formState.startPort"
                  placeholder="起始端口号"
                  :min="1"
                  :max="65535"
                  :style="{ width: 'calc(50% - 8px)' }"
                />
                <span style="margin: 0 8px">-</span>
                <a-input-number
                  v-model:value="formState.endPort"
                  :rules="rules"
                  placeholder="结束端口号"
                  :min="1"
                  :max="65535"
                  :style="{ width: 'calc(50% - 8px)' }"
                />
              </a-space>
            </a-form-item>
          </a-form>
        </div>
      </template> -->
    </BasicForm>
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, unref, reactive } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum, VALIDATE_API } from '/@/enums/commonEnum';
  import { Api, save, update, available } from '/@/api/iot/rule/plugin/pluginInstance';
  import { getValidateRules } from '/@/api/thinglinks/common/formValidateService';
  import { customFormSchemaRules, editFormSchema } from './pluginInstance.data';

  export default defineComponent({
    name: '编辑插件实例',
    components: { BasicModal, BasicForm },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const type = ref<ActionEnum>(ActionEnum.ADD);
      const { createMessage } = useMessage();
      const instanceList = ref([]);
      const formRef = ref(null);
      const formState = reactive({
        startPort: undefined,
        endPort: undefined,
      });
      const rules = {
        portRange: [
          {
            validator(_, value) {
              const [startPort, endPort] = value;
              if (!startPort || !endPort) {
                return Promise.reject(new Error('请输入完整的端口号区间'));
              }
              if (startPort >= endPort) {
                return Promise.reject(new Error('起始端口号必须小于结束端口号'));
              }
              return Promise.resolve();
            },
            trigger: 'blur',
          },
        ],
      };
      const [registerForm, { setFieldsValue, resetFields, updateSchema, validate, resetSchema }] =
        useForm({
          name: 'PluginInstanceEdit',
          labelWidth: 100,
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
          setProps({ confirmLoading: false });
          await resetSchema(editFormSchema(type));
          await resetFields();
          type.value = data?.type || ActionEnum.ADD;
          const res = await available();
          instanceList.value = res;
          if (unref(type) !== ActionEnum.ADD) {
            // 赋值
            const record = { ...data?.record };
            await setFieldsValue(record);
          }
        },
      );

      async function handleSubmit() {
        try {
          const params = await validate();
          setProps({ confirmLoading: true });
          const instance = instanceList.value.find(
            (item) => item.instanceId == params.instanceIdentification,
          );
          console.log(instance);

          // params.instanceIdentification = instance.instanceId;
          params.machineIp = instance.ip;
          params.applicationName = instance.applicationName;
          params.machinePort = instance.port;
          params.weight = instance.weight;
          params.healthy = instance.healthy;
          params.enabled = instance.enabled;
          params.ephemeral = instance.ephemeral;
          params.clusterName = instance.clusterName;
          params.extendParams = JSON.stringify(instance.metadata);
          params.heartBeatInterval = instance.instanceHeartBeatInterval;
          params.heartBeatTimeOut = instance.instanceHeartBeatTimeOut;
          params.ipDeleteTimeOut = instance.ipDeleteTimeout;

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

      return { type, t, registerModel, registerForm, handleSubmit, formRef, formState, rules };
    },
  });
</script>
