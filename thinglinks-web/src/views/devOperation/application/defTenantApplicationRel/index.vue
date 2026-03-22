<template>
  <PageWrapper contentFullHeight dense>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button
          v-hasAnyPermission="[RoleEnum.APPLICATION_AUTHORIZE_CANCEL]"
          color="error"
          @click="handleAuthorize"
        >
          {{ t('devOperation.application.defTenantApplicationRel.authorization') }}
        </a-button>
        <a-button
          v-hasAnyPermission="[RoleEnum.APPLICATION_AUTHORIZE_GRANT]"
          type="primary"
          @click="handleBatchCancelAuthorize"
        >
          {{ t('devOperation.application.defTenantApplicationRel.cancelAuthorization') }}
        </a-button>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                label: t('common.renewal'),
                onClick: handleRenewal.bind(null, record),
                auth: RoleEnum.APPLICATION_AUTHORIZE_RENEWAL,
              },
              {
                label: t('devOperation.application.defTenantApplicationRel.cancelAuthorization'),
                color: 'error',
                auth: RoleEnum.APPLICATION_AUTHORIZE_CANCEL,
                popConfirm: {
                  title: t('devOperation.application.defTenantApplicationRel.cancelMsg'),
                  confirm: handleCancelAuthorize.bind(null, record),
                },
              },
            ]"
          />
        </template>
      </template>
    </BasicTable>
    <EditModal @register="registerDrawer" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useRouter } from 'vue-router';
  import { BasicTable, TableAction, useTable } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { useDrawer } from '/@/components/Drawer';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { RoleEnum } from '/@/enums/roleEnum';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { cancel, page } from '/@/api/devOperation/application/defTenantApplicationRel';
  import { columns, searchFormSchema } from './defTenantApplicationRel.data';
  import EditModal from './Edit.vue';
  import { RouteEnum } from '/@/enums/biz/tenant';

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: RouteEnum.APPLICATION_GRANT_MANAGE,
    components: { BasicTable, PageWrapper, EditModal, TableAction },
    setup() {
      const { t } = useI18n();
      const { createMessage, createConfirm } = useMessage();
      // 编辑页弹窗
      const [registerDrawer, { openDrawer }] = useDrawer();
      const { replace } = useRouter();

      // 表格
      const [registerTable, { reload, getSelectRowKeys }] = useTable({
        title: t('devOperation.application.defTenantApplicationRel.table.title'),
        api: page,
        columns: columns(),
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
          minWidth: 160,
          title: t('common.column.action'),
          dataIndex: 'action',
        },
      });

      // 弹出续期页面
      function handleRenewal(record: Recordable, e: Event) {
        e?.stopPropagation();
        openDrawer(true, {
          record,
          type: ActionEnum.EDIT,
        });
      }

      // 弹出授权
      function handleAuthorize() {
        replace({
          name: RouteEnum.APPLICATION_GRANT,
        });
      }

      // 点击取消授权
      async function handleCancelAuthorize(record: Recordable, e: Event) {
        e?.stopPropagation();
        if (record?.id) {
          await batchCancelAuthorize([record.id]);
        }
      }

      async function batchCancelAuthorize(ids: string[]) {
        await cancel(ids);
        createMessage.success('取消成功');
        handleSuccess();
      }

      function handleBatchCancelAuthorize() {
        const ids = getSelectRowKeys();
        if (!ids || ids.length <= 0) {
          createMessage.warning(t('common.tips.pleaseSelectTheData'));
          return;
        }
        createConfirm({
          iconType: 'warning',
          content: t('devOperation.application.defTenantApplicationRel.cancelMsg'),
          onOk: async () => {
            await batchCancelAuthorize(ids);
          },
        });
      }

      // 授权成功回调
      function handleSuccess() {
        reload();
      }

      return {
        t,
        registerTable,
        registerDrawer,
        handleAuthorize,
        handleRenewal,
        handleCancelAuthorize,
        handleBatchCancelAuthorize,
        handleSuccess,
        RoleEnum,
      };
    },
  });
</script>
