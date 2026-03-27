<template>
  <div class="property-container">
    <BasicTable @register="registerTable" ref="basicTableRef">
      <template #toolbar>
        <a-button
          type="primary"
          color="error"
          preIcon="ant-design:delete-outlined"
          @click="handleBatchDelete"
        >
          {{ t('common.title.delete') }}
        </a-button>
        <a-button type="primary" preIcon="ant-design:plus-outlined" @click="handleAdd">
          {{ t('common.title.add') }}
        </a-button>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'icon'">
          <ThumbUrl :fileId="record.icon" :imageStyle="{ 'max-height': '104px' }" />
        </template>
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                tooltip: t('common.title.view'),
                icon: 'ant-design:search-outlined',
                onClick: handleView.bind(null, record),
              },
              {
                tooltip: t('common.title.edit'),
                icon: 'ant-design:edit-outlined',
                onClick: handleEdit.bind(null, record),
              },
              {
                tooltip: t('common.title.copy'),
                icon: 'ant-design:copy-outlined',
                onClick: handleCopy.bind(null, record),
              },
              {
                tooltip: t('common.title.delete'),
                icon: 'ant-design:delete-outlined',
                color: 'error',
                popConfirm: {
                  title: t('common.tips.confirmDelete'),
                  confirm: handleDelete.bind(null, record),
                },
              },
            ]"
            :stopButtonPropagation="true"
          />
        </template>
      </template>
    </BasicTable>
    <EditModal @register="registerModal" @success="handleSuccess" />
  </div>
</template>
<script lang="ts">
  import { defineComponent, ref, watch } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useModal } from '/@/components/Modal';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { ActionEnum } from '/@/enums/commonEnum';
  import ThumbUrl from '/@/components/Upload/src/ThumbUrl.vue';
  import { page, remove } from '/@/api/iot/link/productProperty/productProperty';
  import { columns, searchFormSchema } from './productProperty.data';
  import EditModal from './Edit.vue';
  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '产品服务属性',
    components: {
      BasicTable,
      TableAction,
      ThumbUrl,
      EditModal,
    },
    props: {
      serviceId: {
        type: String,
        default: '',
      },
    },
    setup(props) {
      const { t } = useI18n();
      const { createMessage, createConfirm } = useMessage();
      const [registerModal, { openModal }] = useModal();
      const serviceId = ref<string>('');
      serviceId.value = props.serviceId;

      watch(
        () => props.serviceId,
        (data: string) => {
          serviceId.value = data;
          reload();
        },
      );
      // 表格
      const [registerTable, { reload, getSelectRowKeys }] = useTable({
        api: page,
        columns: columns(),
        formConfig: {
          name: 'ProductPropertySearch',
          labelWidth: 100,
          schemas: searchFormSchema(),
          autoSubmitOnEnter: true,
          baseColProps: { span: 8 },
          actionColOptions: { span: 24, style: { textAlign: 'right' } },
          resetButtonOptions: {
            preIcon: 'ant-design:rest-outlined',
          },
          submitButtonOptions: {
            preIcon: 'ant-design:search-outlined',
          },
        },
        beforeFetch: handleFetchParams,
        useSearchForm: true,
        showTableSetting: true,
        bordered: true,
        searchInfo: {
          serviceId: serviceId,
        },
        rowKey: 'id',
        rowSelection: {
          type: 'checkbox',
          columnWidth: 40,
        },
        actionColumn: {
          width: 180,
          title: t('common.column.action'),
          dataIndex: 'action',
          fixed: 'right',
        },
      });

      const basicTableRef = ref(null);
      // 弹出复制页面
      function handleCopy(record: Recordable, e: Event) {
        e?.stopPropagation();
        openModal(true, {
          record,
          type: ActionEnum.COPY,
          getContainer: () => basicTableRef.value?.$el,
        });
      }
      // 弹出新增页面
      function handleAdd() {
        openModal(true, {
          serviceId: serviceId.value,
          type: ActionEnum.ADD,
          getContainer: () => basicTableRef.value?.$el,
        });
      }

      // 弹出查看页面
      function handleView(record: Recordable, e: Event) {
        e?.stopPropagation();
        openModal(true, {
          record,
          type: ActionEnum.VIEW,
          getContainer: () => basicTableRef.value?.$el,
        });
      }

      // 弹出编辑页面
      function handleEdit(record: Recordable, e: Event) {
        e?.stopPropagation();
        openModal(true, {
          record,
          type: ActionEnum.EDIT,
          getContainer: () => basicTableRef.value?.$el,
        });
      }

      // 新增或编辑成功回调
      function handleSuccess() {
        reload();
      }

      async function batchDelete(ids: string[]) {
        await remove(ids);
        createMessage.success(t('common.tips.deleteSuccess'));
        handleSuccess();
      }

      // 点击单行删除
      function handleDelete(record: Recordable, e: Event) {
        e?.stopPropagation();
        if (record?.id) {
          batchDelete([record.id]);
        }
      }

      // 点击批量删除
      function handleBatchDelete() {
        const ids = getSelectRowKeys();
        if (!ids || ids.length <= 0) {
          createMessage.warning(t('common.tips.pleaseSelectTheData'));
          return;
        }
        createConfirm({
          iconType: 'warning',
          content: t('common.tips.confirmDelete'),
          onOk: async () => {
            try {
              await batchDelete(ids);
            } catch (e) {}
          },
        });
      }

      return {
        t,
        registerTable,
        registerModal,
        handleView,
        handleAdd,
        handleCopy,
        handleEdit,
        handleDelete,
        handleBatchDelete,
        handleSuccess,
        basicTableRef,
      };
    },
  });
</script>
<style lang="less" scoped>
.property-container {
  height: 100%;
}
</style>