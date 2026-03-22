<template>
  <div class="overflow-hidden">
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleAdd" v-hasAnyPermission="[permCode.authorize]">
          {{ t('devOperation.sop.sopPermGroup.authorization') }}
        </a-button>
        <a-button
          type="primary"
          color="error"
          @click="handleBatchDelete"
          v-hasAnyPermission="[permCode.delete]"
        >
          {{ t('common.title.delete') }}
        </a-button>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                tooltip: t('common.title.delete'),
                icon: 'ant-design:delete-outlined',
                color: 'error',
                auth: [permCode.delete],
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
  import { defineComponent, ref } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useModal } from '/@/components/Modal';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { pageByGroup } from '/@/api/devOperation/sop/sopApiInfo';
  import { deleteByGroupId } from '/@/api/devOperation/sop/sopPermGroupPermission';
  import { columns, searchFormSchema } from './sopApiInfo.data';
  import EditModal from './Authorize.vue';
  import { PermCode } from '/@/enums/perm';

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '开放接口',
    components: {
      BasicTable,
      TableAction,
      EditModal,
    },
    setup() {
      const { t } = useI18n();
      const { createMessage, createConfirm } = useMessage();
      const [registerModal, { openModal }] = useModal();
      const permCode = PermCode.devOperation.sop.sopPermGroup;
      const groupIdRef = ref<string>('');
      const groupNameRef = ref<string>('');

      // 表格
      const [registerTable, { reload, getSelectRowKeys }] = useTable({
        title: t('devOperation.sop.sopApiInfo.table.title'),
        api: pageByGroup,
        columns: columns(),
        formConfig: {
          name: 'SopApiInfoSearch',
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
        immediate: false,
        searchInfo: {
          groupId: groupIdRef,
        },
        useSearchForm: true,
        showTableSetting: true,
        bordered: true,
        rowKey: 'id',
        rowSelection: {
          type: 'checkbox',
          columnWidth: 40,
        },
        actionColumn: {
          width: 60,
          title: t('common.column.action'),
          dataIndex: 'action',
        },
      });

      // 弹出新增页面
      function handleAdd() {
        openModal(true, {
          type: ActionEnum.ADD,
          groupId: groupIdRef.value,
        });
      }

      // 新增或编辑成功回调
      function handleSuccess() {
        reload();
      }

      async function batchDelete(ids: string[]) {
        await deleteByGroupId({ groupId: groupIdRef.value, apiIdList: ids });
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

      async function setData(data: any) {
        groupIdRef.value = data.id;
        groupNameRef.value = data.groupName;
        reload();
      }

      return {
        t,
        setData,
        registerTable,
        registerModal,
        handleAdd,
        handleDelete,
        handleBatchDelete,
        handleSuccess,
        permCode,
      };
    },
  });
</script>
<style scoped lang="less">
  @prefix-cls: ~'@{namespace}-basic-table';

  .@{prefix-cls} {
    &-form-container {
      padding: 16px 16px 16px 0;
    }
  }
</style>
