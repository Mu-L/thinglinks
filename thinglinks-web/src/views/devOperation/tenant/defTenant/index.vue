<template>
  <PageWrapper contentFullHeight dense>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button
          v-hasAnyPermission="[RoleEnum.TENANT_TENANT_ADD]"
          preIcon="ant-design:plus-outlined"
          type="primary"
          @click="handleAdd"
          >{{ t('common.title.add') }}
        </a-button>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                icon: 'ant-design:search-outlined',
                tooltip: t('common.title.view'),
                onClick: handleView.bind(null, record),
                auth: RoleEnum.TENANT_TENANT_VIEW,
              },
              {
                icon: 'ant-design:audit-outlined',
                tooltip: t('common.title.review'),
                onClick: handleToExamine.bind(null, record),
                ifShow: () => {
                  return [TenantStatusEnum.WAITING].includes(record?.status);
                },
                auth: RoleEnum.TENANT_TENANT_TO_EXAMINE,
              },
              {
                icon: 'ant-design:database-outlined',
                tooltip: t('devOperation.tenant.defTenant.initDb'),
                ifShow: () => {
                  return (
                    globSetting.multiTenantType ===
                      MultiTenantTypeEnum.COLUMN &&
                    [TenantStatusEnum.WAIT_INIT_SCHEMA].includes(record?.status)
                  );
                },
                popConfirm: {
                  title: t('devOperation.tenant.defTenant.confirmInitDb'),
                  confirm: handleColumnInitData.bind(null, record),
                },
                auth: RoleEnum.TENANT_TENANT_INIT_DATA,
              },
              {
                icon: 'ant-design:database-outlined',
                tooltip: t('devOperation.tenant.defTenant.initDbSuccess'),
                onClick: handleInitData.bind(null, record),
                ifShow: () => {
                  return (
                    globSetting.multiTenantType ===
                      MultiTenantTypeEnum.DATASOURCE_COLUMN &&
                    [TenantStatusEnum.WAIT_INIT_SCHEMA].includes(record?.status)
                  );
                },
                auth: RoleEnum.TENANT_TENANT_INIT_DATA,
              },
              {
                icon: 'ant-design:cloud-upload-outlined',
                tooltip: t('devOperation.tenant.defTenant.initOtherDataSource'),
                onClick: handleLinkDataSource.bind(null, record),
                ifShow: () => {
                  return (
                    [
                      TenantStatusEnum.NORMAL,
                      TenantStatusEnum.WAIT_INIT_DATASOURCE,
                      TenantStatusEnum.AGREED,
                    ].includes(record?.status) &&
                    globSetting.multiTenantType ===
                      MultiTenantTypeEnum.DATASOURCE_COLUMN
                  );
                },
                auth: RoleEnum.TENANT_TENANT_INIT_DATA_SOURCE,
              },
              {
                tooltip: t('common.title.edit'),
                icon: 'clarity:note-edit-line',
                onClick: handleEdit.bind(null, record),
                auth: RoleEnum.TENANT_TENANT_EDIT,
              },
              {
                tooltip: t('common.title.delete'),
                icon: 'ant-design:delete-outlined',
                color: 'error',
                popConfirm: {
                  title: t('common.tips.confirmDelete'),
                  confirm: handleDelete.bind(null, record),
                },
                auth: RoleEnum.TENANT_TENANT_DELETE,
              },
              {
                tooltip: t('devOperation.tenant.defTenant.bindUserAsAdmin'),
                icon: 'ant-design:usergroup-add-outlined',
                color: 'warning',
                onClick: handleBindUser.bind(null, record),
                ifShow: () => {
                  return [TenantStatusEnum.NORMAL].includes(record?.status);
                },
                auth: RoleEnum.TENANT_TENANT_BIND_USER,
              },
            ]"
            :stopButtonPropagation="true"
          />
        </template>
      </template>
    </BasicTable>
    <EditModal @register="registerDrawer" @success="handleSuccess" />
    <InitDataModal @register="registerInitModal" @success="handleInitSuccess" />
    <LinkDataSourceModal
      @register="registerLinkDrawer"
      @success="handleSuccess"
    />
    <BindUserModal @register="registerModal" @success="handleSuccess" />
    <ToExamineModal
      @register="registerToExamineModal"
      @success="handleSuccess"
    />
  </PageWrapper>
</template>
<script lang="ts">
import { defineComponent } from 'vue';
import { useRouter } from 'vue-router';
import { useI18n } from '/@/hooks/web/useI18n';
import { useMessage } from '/@/hooks/web/useMessage';
import { BasicTable, TableAction, useTable } from '/@/components/Table';
import { PageWrapper } from '/@/components/Page';
import { useDrawer } from '/@/components/Drawer';
import { useModal } from '/@/components/Modal';
import { handleFetchParams } from '/@/utils/thinglinks/common';
import { ActionEnum } from '/@/enums/commonEnum';
import { MultiTenantTypeEnum, RouteEnum, TenantStatusEnum } from '/@/enums/biz/tenant';
import { initData, page, remove } from '/@/api/devOperation/tenant/tenant';
import { columns, searchFormSchema } from './tenant.data';
import { RoleEnum } from '/@/enums/roleEnum';
import EditModal from './Edit.vue';
import InitDataModal from './InitData.vue';
import LinkDataSourceModal from './LinkDataSource.vue';
import BindUserModal from './BindUserModal.vue';
import ToExamineModal from './ToExamineModal.vue';
import { useGlobSetting } from '/@/hooks/setting';

export default defineComponent({
  name: '租户管理',
  components: {
    BasicTable,
    PageWrapper,
    EditModal,
    TableAction,
    InitDataModal,
    LinkDataSourceModal,
    BindUserModal,
    ToExamineModal,
  },
  setup() {
    const { t } = useI18n();
    const [registerDrawer, { openDrawer }] = useDrawer();
    const [registerModal, { openModal }] = useModal();
    const [registerToExamineModal, { openModal: openToExamineModal }] = useModal();
    const [registerLinkDrawer, { openDrawer: openLinkDrawer }] = useDrawer();
    const [registerInitModal, { openModal: openInitModal }] = useModal();
    const globSetting = useGlobSetting();

    const { replace } = useRouter();
    const { createMessage } = useMessage();

    const [registerTable, { reload }] = useTable({
      title: t('devOperation.tenant.defTenant.table.title'),
      api: page,
      columns,
      formConfig: {
        labelWidth: 120,
        schemas: searchFormSchema,
        baseColProps: { xs: 24, sm: 12, md: 12, lg: 12, xl: 8 },
        autoSubmitOnEnter: true,
        resetButtonOptions: {
          preIcon: 'ant-design:rest-outlined',
        },
        submitButtonOptions: {
          preIcon: 'ant-design:search-outlined',
        },
      },
      titleHelpMessage: [
        t('devOperation.tenant.defTenant.createTenantSteps'),
        t('devOperation.tenant.defTenant.step1'),
        t('devOperation.tenant.defTenant.step2'),
        t('devOperation.tenant.defTenant.step3'),
        t('devOperation.tenant.defTenant.step4'),
        t('devOperation.tenant.defTenant.step5'),
      ],
      clickToRowSelect: false,
      beforeFetch: handleFetchParams,
      useSearchForm: true,
      showTableSetting: true,
      bordered: true,
      rowKey: 'id',
      rowSelection: {
        type: 'checkbox',
      },
      actionColumn: {
        width: 220,
        title: t('common.column.action'),
        dataIndex: 'action',
      },
    });

    async function handleColumnInitData(record: Recordable, e: Event) {
      e?.stopPropagation();
      let flag = await initData({ id: record.id });
      if (flag) {
        createMessage.success(t('devOperation.tenant.defTenant.initTenantSuccessMsg'));
        reload();
      }
    }

    function handleInitData(record: Recordable, e: Event) {
      e?.stopPropagation();
      openInitModal(true, { record });
    }

    function handleLinkDataSource(record: Recordable, e: Event) {
      e?.stopPropagation();
      openLinkDrawer(true, { record });
    }

    function handleAdd() {
      openDrawer(true, {
        type: ActionEnum.ADD,
      });
    }

    function handleView(record: Recordable, e: Event) {
      e?.stopPropagation();
      replace({
        name: RouteEnum.TENANT_VIEW,
        params: { id: record.id },
      });
    }

    function handleEdit(record: Recordable, e: Event) {
      e?.stopPropagation();
      openDrawer(true, {
        record,
        type: ActionEnum.EDIT,
      });
    }

    function handleDelete(record: Recordable, e: Event) {
      e?.stopPropagation();
      if (record.readonly) {
        createMessage.warn(t('devOperation.tenant.defTenant.builtInTenantDeleteMsg'));
        return;
      }
      remove([record.id]).then(() => {
        createMessage.success(t('common.tips.deleteSuccess'));
        handleSuccess();
      });
    }

    function handleSuccess() {
      reload();
    }

    function handleInitSuccess() {
      reload();
    }

    function handleBindUser(record: Recordable, e: Event) {
      e?.stopPropagation();
      openModal(true, { record });
    }

    function handleToExamine(record: Recordable, e: Event) {
      e?.stopPropagation();
      openToExamineModal(true, { record });
    }

    return {
      TenantStatusEnum,
      t,
      registerTable,
      registerDrawer,
      registerModal,
      registerInitModal,
      registerLinkDrawer,
      registerToExamineModal,
      handleAdd,
      handleView,
      handleEdit,
      handleDelete,
      handleSuccess,
      handleColumnInitData,
      handleInitSuccess,
      handleInitData,
      handleLinkDataSource,
      handleBindUser,
      handleToExamine,
      RoleEnum,
      globSetting,
      MultiTenantTypeEnum,
    };
  },
});
</script>
