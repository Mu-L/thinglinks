<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModel"
    :title="t(`common.title.${type}`)"
    :maskClosable="false"
    @ok="handleSubmit"
    :keyboard="true"
  >
    <a-table
      :columns="columns"
      :row-selection="{
        type: 'radio',
        columnWidth: '100px',
        onChange: onSelectChange,
        selectedRowKeys: selectedRowKeys,
      }"
      :data-source="data"
      :pagination="false"
      :scroll="{ y: 400 }"
      rowKey="topic"
    >
      <template #bodyCell="{ column, text }"> </template>
    </a-table>
    <div class="tr" style="position: absolute; bottom: 0; right: 0">
      <a-pagination
        @change="change"
        size="small"
        v-model:current="current"
        v-model:pageSize="size"
        :total="total"
        :show-total="(total) => t('component.table.total', { total })"
        show-size-changer
        show-quick-jumper
        :page-size-options="pageSizeOptions"
    /></div>
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, reactive, toRefs, toRaw } from 'vue';
  import { Pagination } from 'ant-design-vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { Table } from 'ant-design-vue';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { page, remove } from '/@/api/iot/link/productTopic/productTopic';
  type Key = string | number;

  export default defineComponent({
    name: '编辑设备',
    components: {
      BasicModal,
      ATable: Table,
      APagination: Pagination,
    },
    inheritAttrs: false,
    emits: ['success'],
    setup(props, { emit }) {
      const { t } = useI18n();
      const { createMessage } = useMessage();
      const type = ref<ActionEnum>(ActionEnum.ADD);
      const current = ref(1);
      const size = ref(20);
      const total = ref(0);
      const pageSizeOptions = ref<string[]>(['10', '20', '30', '40', '50']);
      const state = reactive<{
        selectedRowKeys: Key[];
        loading: boolean;
        data: Array<any>;
      }>({
        selectedRowKeys: [],
        loading: false,
        data: [],
      });
      const columns = ref([
        {
          title: t('iot.link.productTopic.productTopic.functionType'),
          dataIndex: ['echoMap', 'functionType'],
        },
        {
          title: t('iot.link.productTopic.productTopic.topic'),
          dataIndex: 'topic',
        },
        {
          title: t('iot.link.productTopic.productTopic.publisher'),
          dataIndex: ['echoMap', 'publisher'],
        },
        {
          title: t('iot.link.productTopic.productTopic.subscriber'),
          dataIndex: ['echoMap', 'subscriber'],
        },
        {
          title: t('iot.link.productTopic.productTopic.remark'),
          dataIndex: 'remark',
        },
      ]);
      const productIdentification = ref('');
      const getData = async () => {
        const res = await page({
          model: { productIdentification: productIdentification.value },
          current: current.value,
          size: size.value,
        });
        state.data = res?.records;
        total.value = res.total;
      };
      const [registerModel, { setModalProps: setProps, closeModal: close }] = useModalInner(
        async (data) => {
          state.data = [];
          setProps({ confirmLoading: false, width: 1000 });
          type.value = data?.type || ActionEnum.ADD;
          productIdentification.value = data.productIdentification;
          await getData();
          state.selectedRowKeys = [];
        },
      );

      async function handleSubmit() {
        try {
          if (state.selectedRowKeys.length > 0) {
            emit('success', state.selectedRowKeys[0]);
            close();
          } else {
            createMessage.warning(t(`common.chooseText`));
          }
        } finally {
          setProps({ confirmLoading: false });
        }
      }

      const onSelectChange = (selectedRowKeys) => {
        state.selectedRowKeys = selectedRowKeys;
      };
      function change() {
        getData();
      }
      return {
        type,
        t,
        registerModel,
        handleSubmit,
        onSelectChange,
        current,
        total,
        size,
        pageSizeOptions,
        productIdentification,
        ...toRefs(state),
        columns,
        change,
      };
    },
  });
</script>
