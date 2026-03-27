<template>
  <BasicModal
    v-bind="$attrs"
    :title="t('iot.link.group.deviceGroup.addDeviceToGroup.title')"
    :maskClosable="false"
    :keyboard="true"
    :min-height="0"
    :centered="true"
    @register="registerModel"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm"> </BasicForm>
  </BasicModal>
</template>
<script lang="ts">
  // util
  import { defineComponent, ref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  // api
  import { save } from '/@/api/iot/link/group/deviceGroupRel';
  // components
  import { Form } from 'ant-design-vue';
  import ApiTreeSelect from '/@/components/Form/src/components/ApiTreeSelect.vue';
  import { BasicTree } from '/@/components/Tree';
  // data
  import { addGroupFields } from './deviceGroup.data';

  export default defineComponent({
    name: '添加设备到分组',
    components: { BasicModal, BasicTree, ApiTreeSelect, BasicForm, FormItem: Form.Item },
    emits: ['success', 'register'],
    setup(props, { emit }) {
      const { t } = useI18n();
      const { createMessage } = useMessage();

      const deviceIdentification = ref('');

      const [registerModel, { closeModal: close }] = useModalInner(async (data) => {
        deviceIdentification.value = data.deviceIdentification;
        // ApiTreeSelect 组件已配置 immediate: true，会自动加载树数据
      });

      const [registerForm, { validate }] = useForm({
        name: 'addDeviceToGroup',
        labelWidth: 120,
        schemas: addGroupFields(),
        showActionButtonGroup: false,
        baseColProps: { span: 24 },
        actionColOptions: {
          span: 24,
        },
      });

      const handleSubmit = async () => {
        const params = await validate();
        const res = await save({
          deviceIdentification: deviceIdentification.value,
          updatedTime: new Date().getTime(),
          ...params,
        });
        if (res) {
          createMessage.success(t('iot.link.group.deviceGroup.addDeviceToGroup.addSuccess'));
          emit('success');
          close();
        }
      };

      return {
        t,
        registerModel,
        registerForm,
        handleSubmit,
      };
    },
  });
</script>
<style lang="less" scoped>
  .ant-tree-select {
    width: 100%;
  }

  i {
    color: #ff4d4f;
    font-size: 14px;
    margin-right: 4px;
  }
</style>
