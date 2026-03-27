<template>
  <BasicModal v-bind="$attrs" @register="registerModel" :title="t(`common.title.${type}`)" :maskClosable="false"
    @ok="handleSubmit" :keyboard="true">
    <a-table :columns="columns"
      :row-selection="{ type: 'radio', columnWidth: '100px', onChange: onChange, selectedRowKeys: selectedRowKeys }"
      :data-source="data" :pagination="false" :scroll="{ y: 400 }" rowKey="id">
      <template #bodyCell="{ column, text }">
      </template>
    </a-table>
  </BasicModal>
</template>
<script lang="ts">
import { defineComponent, ref, reactive, toRefs, toRaw } from 'vue';
import { BasicModal, useModalInner } from '/@/components/Modal';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { Table } from 'ant-design-vue';
import { useMessage } from '/@/hooks/web/useMessage';
type Key = string | number;

type OptionsItem = { label: string; value: string; disabled?: boolean };
export default defineComponent({
  name: '编辑设备',
  components: {
    BasicModal,
    ATable: Table
  },
  inheritAttrs: false,
  props: {
    api: {
      type: Function as PropType<(arg?: Recordable | string) => Promise<OptionsItem[]>>,
      default: null,
    },
    params: {
      type: [Object, String] as PropType<Recordable | string>,
      default: () => ({}),
    },
    columns: {
      type: Array,
      default: []
    }
  },
  emits: ['success'],
  setup(props,{ emit }) {
    const { t } = useI18n();
    const { createMessage } = useMessage();
    const type = ref<ActionEnum>(ActionEnum.ADD);
    const state = reactive<{
      selectedRowKeys: Key[];
      loading: boolean;
      columns: Array<any>;
        data: Array<any>;
    }>({
      selectedRowKeys: [], // Check here to configure the default column
      loading: false,
      columns: props.columns,
      data: []
    });
    const [registerModel, { setModalProps: setProps, closeModal: close }] = useModalInner(
      async (data) => {
        state.data = []
        setProps({ confirmLoading: false, width: 1000 });
        type.value = data?.type || ActionEnum.ADD;
        const api = props.api;
        const params = props.params;
        state.data = await api(params)
        state.selectedRowKeys = []
      },
    );

    async function handleSubmit() {
      try {
        if(state.selectedRowKeys.length>0){
          emit('success', state.data.find(item=>{return item.id == state.selectedRowKeys[0]}))
          close()
        }else{
          createMessage.success(t(`common.chooseText`));
        }
      } finally {
        setProps({ confirmLoading: false });
      }
    }

    const onChange = (selectedRowKeys) => {
      state.selectedRowKeys = selectedRowKeys
    }
    return { type, t, registerModel, handleSubmit, onChange, ...toRefs(state) };
  },
});
</script>
