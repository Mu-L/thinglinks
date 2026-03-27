<template>
  <PageWrapper dense contentClass="flex">
    <BasicTable
      @register="registerTable"
      @selection-change="handleSelectionChange"
      @row-click="handleRowClick"
      @fetch-success="onFetchSuccess"
      class="md:w-1/3 xl:w-1/3"
    >
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
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                tooltip: t('common.title.edit'),
                icon: 'ant-design:edit-outlined',
                onClick: handleEdit.bind(null, record),
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

    <ApiTable class="md:w-2/3" ref="apiTableRef" />
    <EditModal @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, ref } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { useModal } from '/@/components/Modal';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { page, remove } from '/@/api/devOperation/sop/sopPermGroup';
  import { columns, searchFormSchema } from './sopPermGroup.data';
  import EditModal from './Edit.vue';
  import ApiTable from './modules/Api.vue';

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '应用分组',
    components: {
      BasicTable,
      PageWrapper,
      TableAction,
      EditModal,
      ApiTable,
    },
    setup() {
      const { t } = useI18n();
      const apiTableRef = ref();
      const { createMessage, createConfirm } = useMessage();
      const [registerModal, { openModal }] = useModal();

      // 表格
      const [registerTable, { reload, getSelectRows, getSelectRowKeys, setSelectedRowKeys }] =
        useTable({
          api: page,
          columns: columns(),
          formConfig: {
            name: 'SopPermGroupSearch',
            labelWidth: 120,
            schemas: searchFormSchema(),
            autoSubmitOnEnter: true,
            resetButtonOptions: {
              preIcon: 'ant-design:rest-outlined',
            },
            submitButtonOptions: {
              preIcon: 'ant-design:search-outlined',
            },
          },
          beforeFetch: handleFetchParams,
          useSearchForm: true,
          bordered: true,
          rowKey: 'id',
          rowSelection: {
            type: 'radio',
            columnWidth: 40,
          },
          actionColumn: {
            width: 100,
            title: t('common.column.action'),
            dataIndex: 'action',
          },
        });

      // 弹出复制页面
      function handleCopy(record: Recordable, e: Event) {
        e?.stopPropagation();
        openModal(true, {
          record,
          type: ActionEnum.COPY,
        });
      }
      // 弹出新增页面
      function handleAdd() {
        openModal(true, {
          type: ActionEnum.ADD,
        });
      }

      // 弹出查看页面
      function handleView(record: Recordable, e: Event) {
        e?.stopPropagation();
        openModal(true, {
          record,
          type: ActionEnum.VIEW,
        });
      }

      // 弹出编辑页面
      function handleEdit(record: Recordable, e: Event) {
        e?.stopPropagation();
        openModal(true, {
          record,
          type: ActionEnum.EDIT,
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

      async function onFetchSuccess(result: Recordable) {
        // 请求之后对返回值进行处理
        if (result && result.items && result.items.length > 0) {
          await setSelectedRowKeys([result.items[0].id]);
          emitChange();
        }
      }

      // 选择事件
      function emitChange() {
        const selectedKeys = getSelectRows();
        const row = selectedKeys?.[0] || {};

        apiTableRef.value.setData(row);
      }

      // 勾选事件触发
      function handleSelectionChange() {
        emitChange();
      }

      // 单击行回调
      function handleRowClick(record: Recordable) {
        setSelectedRowKeys([record.id]);
        emitChange();
      }

      return {
        handleSelectionChange,
        handleRowClick,
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
        apiTableRef,
        onFetchSuccess,
      };
    },
  });
</script>
