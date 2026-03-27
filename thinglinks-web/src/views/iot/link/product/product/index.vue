<template>
  <PageWrapper dense contentFullHeight>
    <BasicTable
      @register="registerTable"
      @switch-change="getSwitchChange"
      :switchFlag="switchFlag"
      :is-product="true"
    >
      <template #toolbar>
        <a-button
          v-hasPermission="['link:product:product:delete']"
          type="primary"
          color="error"
          preIcon="ant-design:delete-outlined"
          @click="handleBatchDelete"
        >
          {{ t('common.title.delete') }}
        </a-button>
        <a-button
          v-hasPermission="['link:product:product:add']"
          type="primary"
          preIcon="ant-design:plus-outlined"
          @click="handleAdd"
        >
          {{ t('common.title.add') }}
        </a-button>
        <a-button
          v-hasPermission="['link:product:product:quick']"
          type="primary"
          preIcon="ant-design:plus-outlined"
          @click="onQuick"
        >
          {{ t('iot.link.device.device.quick') }}
        </a-button>
        <a-button
          v-hasPermission="['link:product:product:import']"
          type="primary"
          preIcon="ant-design:download-outlined"
          @click="handleImport"
        >{{ t('common.title.import') }}</a-button>
        <a-button preIcon="ant-design:swap-outlined" @click="switchView"
          >{{ t('iot.link.device.device.switchView') }}
        </a-button>
      </template>
      <template #productTypeColumn="{ record }">
        {{ getDictLabel('LINK_PRODUCT_TYPE', record?.productType, '') }}
      </template>
      <template #productStatus="{ record }">
        {{ getDictLabel('LINK_PRODUCT_STATUS', record?.productStatus, '') }}
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
                auth: 'link:product:product:view',
                onClick: handleView.bind(null, record),
              },
              {
                tooltip: t('common.title.edit'),
                icon: 'ant-design:edit-outlined',
                auth: 'link:product:product:edit',
                onClick: handleEdit.bind(null, record),
              },
              {
                tooltip: t('common.title.copy'),
                icon: 'ant-design:copy-outlined',
                auth: 'link:product:product:copy',
                onClick: handleCopy.bind(null, record),
              },
              {
                tooltip: t('common.title.export'),
                icon: 'ant-design:export-outlined',
                auth: 'link:product:product:export',
                onClick: handleExport.bind(null, record),
              },
              {
                tooltip: t('common.title.delete'),
                icon: 'ant-design:delete-outlined',
                color: 'error',
                auth: 'link:product:product:delete',
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
    <ImportModal @register="importModal" @reload="reload" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, ref } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { useModal } from '/@/components/Modal';
  import ThumbUrl from '/@/components/Upload/src/ThumbUrl.vue';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { downloadFile } from '/@/utils/file/download.ts';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { page, remove, exportJson, deleteSingle } from '/@/api/iot/link/product/product';
  import { columns, searchFormSchema } from './product.data';
  import EditModal from './Edit.vue';
  import ImportModal from './ImportModal.vue';
  import { useRouter } from 'vue-router';
  import { Button } from 'ant-design-vue';
  import { useDict } from '/@/components/Dict';
  import { usePermission } from '/@/hooks/web/usePermission';
  const { getDictLabel } = useDict();

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '产品模型',
    components: {
      BasicTable,
      PageWrapper,
      TableAction,
      EditModal,
      ImportModal,
      AButton: Button,
      ThumbUrl,
    },
    setup() {
      const { t } = useI18n();
      const { createConfirm, notification } = useMessage();
      const { hasPermission } = usePermission();
      const [registerModal, { openModal }] = useModal();
      const [importModal, { openModal: openImportModal }] = useModal();
      const { replace } = useRouter();

      // 表格
      const [registerTable, { reload, getSelectRowKeys }] = useTable({
        title: t('iot.link.product.product.table.title'),
        api: page,
        columns: columns(),
        formConfig: {
          name: 'ProductSearch',
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
        showTableSetting: true,
        bordered: true,
        rowKey: 'id',
        rowSelection: {
          type: 'checkbox',
          columnWidth: 40,
        },
        actionColumn: {
          width: 200,
          title: t('common.column.action'),
          dataIndex: 'action',
        },
      });
      // 导出JSON
      async function handleExport(record: Recordable, e: Event) {
        e?.stopPropagation();
        try {
          const response = await exportJson(record.productIdentification);
          downloadFile(response);
          notification.success({ message: t('common.tips.exportSuccess') });
        } catch (error) {
          console.error('导出失败:', error);
          notification.error({ message: t('common.tips.exportFail') });
        }
      }
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
        if (!hasPermission('link:product:product:view')) {
          notification.warning({
            message: t('common.tips.tips'),
            description: t('sys.api.operationFailed')
          });
          return;
        }
        replace({
          name: '产品详情',
          params: { id: record.id },
        });
      }

      function onQuick(e: Event) {
        e?.stopPropagation();
        replace({
          name: '快捷生成',
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
        notification.success({ message: t('common.tips.deleteSuccess') });
        handleSuccess();
      }

      // 删除单个
      const handleDeleteSingle = async (id: string) => {
        await deleteSingle(id);
        notification.success({ message: t('common.tips.deleteSuccess') });
        handleSuccess();
      };

      // 点击单行删除
      function handleDelete(record: Recordable, e: Event) {
        e?.stopPropagation();
        if (record?.id) {
          handleDeleteSingle(record.id);
        }
      }

      // 点击批量删除
      function handleBatchDelete() {
        const ids = getSelectRowKeys();
        if (!ids || ids.length <= 0) {
          notification.warn({ message: t('common.tips.pleaseSelectTheData') });
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

      // 切换视图 卡片&&列表
      const switchFlag = ref<boolean>(true);
      function switchView() {
        console.log('switchView');
        switchFlag.value = !switchFlag.value;
      }

      function getSwitchChange(e) {
        switchFlag.value = e;
      }

      const handleImport = () => {
        openImportModal(true);
      };
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
        switchFlag,
        switchView,
        getSwitchChange,
        getDictLabel,
        onQuick,
        handleExport,
        importModal,
        handleImport,
        reload,
      };
    },
  });
</script>
../../../../../api/iot/link/product/product
