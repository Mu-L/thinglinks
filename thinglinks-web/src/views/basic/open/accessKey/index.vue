<template>
  <PageWrapper contentFullHeight dense>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button
          color="error"
          preIcon="ant-design:delete-outlined"
          type="primary"
          @click="handleBatchDelete"
          >{{ t('common.title.delete') }}
        </a-button>
        <a-button preIcon="ant-design:plus-outlined" type="primary" @click="handleAdd">
          {{ t('basic.open.accessKey.application') }}
        </a-button>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                icon: 'ant-design:edit-outlined',
                tooltip: t('common.title.edit'),
                onClick: handleEdit.bind(null, record),
              },
            ]"
          />
        </template>
      </template>
    </BasicTable>
    <EditModal @register="registerDrawer" @success="handleSuccess" />
    <ShowModal @register="registerShowDrawer" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { BasicTable, TableAction, useTable } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { useDrawer } from '/@/components/Drawer';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { page } from '/@/api/basic/open/accessKey';
  import { columns, searchFormSchema } from './data';
  import EditModal from './Edit.vue';
  import ShowModal from './Show.vue';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { remove } from '/@/api/devOperation/sop/sopIsvInfo';

  export default defineComponent({
    name: 'AccessKey',
    components: { BasicTable, PageWrapper, EditModal, TableAction, ShowModal },
    setup() {
      const { t } = useI18n();
      const { createMessage, createConfirm } = useMessage();
      // 编辑页弹窗
      const [registerDrawer, { openDrawer }] = useDrawer();
      const [registerShowDrawer, { openDrawer: openShowDrawer }] = useDrawer();
      // 表格
      const [registerTable, { reload, getSelectRowKeys }] = useTable({
        title: t('AccessKey'),
        api: page,
        columns: columns(openShowDrawer),
        formConfig: {
          labelWidth: 120,
          schemas: searchFormSchema(),
        },
        beforeFetch: handleFetchParams,
        useSearchForm: true,
        showTableSetting: true,
        bordered: true,
        rowKey: 'id',
        rowSelection: {
          type: 'checkbox',
        },
        actionColumn: {
          width: 100,
          title: t('common.column.action'),
          dataIndex: 'action',
        },
      });

      // 弹出编辑页面
      function handleEdit(record: Recordable, e: Event) {
        e?.stopPropagation();
        openDrawer(true, {
          record,
          type: ActionEnum.EDIT,
        });
      }

      // 新增或编辑成功回调
      function handleSuccess() {
        reload();
      }

      // 弹出新增页面
      function handleAdd() {
        openDrawer(true, {
          type: ActionEnum.ADD,
        });
      }

      async function batchDelete(ids: any[]) {
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
        registerDrawer,
        handleEdit,
        handleSuccess,
        handleAdd,
        handleBatchDelete,
        handleDelete,
        registerShowDrawer,
      };
    },
  });
</script>
