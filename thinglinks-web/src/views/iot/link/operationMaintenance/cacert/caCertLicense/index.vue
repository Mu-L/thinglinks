<template>
  <PageWrapper dense contentFullHeight>
    <BasicTable
      @register="registerTable"
      @switch-change="getSwitchChange"
      :switchFlag="switchFlag"
      :isCaCertLicense="true"
    >
      <template #toolbar>
        <a-button
          type="primary"
          color="error"
          preIcon="ant-design:delete-outlined"
          @click="handleBatchDelete"
          v-hasAnyPermission="['link:cacert:caCertLicense:delete']"
        >
          {{ t('common.title.delete') }}
        </a-button>
        <a-button type="primary" preIcon="ant-design:plus-outlined" @click="handleImport">
          {{ t('iot.link.operationMaintenance.cacert.caCertLicense.importTitle') }}
        </a-button>
        <!-- <a-button
          type="primary"
          preIcon="ant-design:plus-outlined"
          @click="handleAdd"
          v-hasAnyPermission="['link:cacert:caCertLicense:add']"
        >
          {{ t('common.title.add') }}
        </a-button> -->
        <a-button preIcon="ant-design:swap-outlined" @click="switchView"
          >{{ t('iot.link.device.device.switchView') }}
        </a-button>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                tooltip: t('common.title.view'),
                icon: 'ant-design:search-outlined',
                onClick: handleView.bind(null, record),
              },
              {
                tooltip: t('common.title.delete'),
                icon: 'ant-design:delete-outlined',
                color: 'error',
                auth: 'link:cacert:caCertLicense:delete',
                popConfirm: {
                  title: t('common.tips.confirmDelete'),
                  confirm: handleDelete.bind(null, record),
                },
              },
            ]"
            :stopButtonPropagation="true"
          />
        </template>
        <!-- {
                tooltip: t('common.title.edit'),
                icon: 'ant-design:edit-outlined',
                onClick: handleEdit.bind(null, record),
                auth: 'link:cacert:caCertLicense:edit',
              },
              {
                tooltip: t('common.title.copy'),
                icon: 'ant-design:copy-outlined',
                onClick: handleCopy.bind(null, record),
                auth: 'link:cacert:caCertLicense:copy',
               -->
      </template>
    </BasicTable>
    <ImportModal @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, ref } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useModal } from '/@/components/Modal';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { useRouter } from 'vue-router';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { page, remove } from '/@/api/iot/link/operationMaintenance/cacert/caCertLicense';
  import ImportModal from './ImportModal.vue';
  import { columns, searchFormSchema } from './caCertLicense.data';

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: 'CA许可证证书',
    components: {
      BasicTable,
      PageWrapper,
      TableAction,
      ImportModal,
    },
    setup() {
      const { t } = useI18n();
      const [registerModal, { openModal }] = useModal();
      const { createMessage, createConfirm } = useMessage();
      const { replace, push } = useRouter();
      const menuName = '编辑CA许可证证书';

      // 表格
      const [registerTable, { reload, getSelectRowKeys }] = useTable({
        title: t('iot.link.operationMaintenance.cacert.caCertLicense.table.title'),
        api: page,
        columns: columns(),
        formConfig: {
          name: 'CaCertLicenseSearch',
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
      // 弹出导入页面
      function handleImport() {
        openModal(true);
      }
      // 弹出新增页面
      function handleAdd() {
        replace({
          name: menuName,
          params: { id: '0' },
          query: { type: ActionEnum.ADD },
        });
      }

      function handleView(record: Recordable, e: Event) {
        e?.stopPropagation();
        push({
          name: 'CA许可证证书详情',
          params: { id: record.id },
        });
      }

      // 弹出编辑页面
      function handleEdit(record: Recordable, e: Event) {
        e?.stopPropagation();
        replace({
          // name 一定要唯一，且跟存储在def_resource表中的name一致
          name: menuName,
          params: { id: record.id },
          query: { type: ActionEnum.EDIT },
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
      function switchView() {
        switchFlag.value = !switchFlag.value;
      }

      const switchFlag = ref<boolean>(true);

      function getSwitchChange(e) {
        switchFlag.value = e;
      }

      return {
        t,
        registerModal,
        registerTable,
        handleView,
        handleAdd,
        handleImport,
        handleEdit,
        handleDelete,
        handleBatchDelete,
        handleSuccess,
        switchView,
        getSwitchChange,
        switchFlag,
      };
    },
  });
</script>
