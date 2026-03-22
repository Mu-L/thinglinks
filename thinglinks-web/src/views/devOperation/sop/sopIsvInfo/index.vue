<template>
  <PageWrapper dense contentFullHeight>
    <BasicTable @register="registerTable">
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
                label: t('devOperation.sop.sopIsvInfo.setKey'),
                onClick: handleKeys.bind(null, record.id),
              },
              {
                label: t('devOperation.sop.sopIsvInfo.setGroup'),
                onClick: handleGroup.bind(null, record.id),
              },
              {
                label: t('common.title.review'),
                ifShow: record.creationMethod === 1 && record.auditStatus === 1,
                onClick: handleExamine.bind(null, record.id),
              },
            ]"
            :drop-down-actions="[
              {
                label: t('common.title.view'),
                onClick: handleView.bind(null, record),
              },
              {
                label: t('common.title.edit'),
                onClick: handleEdit.bind(null, record),
              },
              {
                label: t('common.title.copy'),
                onClick: handleCopy.bind(null, record),
              },
              {
                label: t('common.title.delete'),
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
    <KeysModal @register="registerKeysModal" @success="handleSuccess" />
    <GroupModal @register="registerGroupModal" @success="handleSuccess" />
    <ExamineModal @register="registerExamineModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { useModal } from '/@/components/Modal';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { page, remove } from '/@/api/devOperation/sop/sopIsvInfo';
  import { useIsvIndex } from './sopIsvInfo.data';
  import EditModal from './Edit.vue';
  import KeysModal from './keys/index.vue';
  import GroupModal from './group/index.vue';
  import ExamineModal from './examine/index.vue';

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: 'Isv信息表管理',
    components: {
      BasicTable,
      PageWrapper,
      TableAction,
      EditModal,
      GroupModal,
      KeysModal,
      ExamineModal,
    },
    setup() {
      const { t } = useI18n();
      const { createMessage, createConfirm } = useMessage();
      const { handleKeys, registerKeysModal, columns, searchFormSchema } = useIsvIndex();
      const [registerModal, { openModal }] = useModal();
      const [registerGroupModal, { openModal: openGroupModal }] = useModal();
      const [registerExamineModal, { openModal: openExamineModal }] = useModal();

      // 表格
      const [registerTable, { reload, getSelectRowKeys }] = useTable({
        title: t('devOperation.sop.sopIsvInfo.table.title'),
        api: page,
        columns: columns(),
        formConfig: {
          name: 'SopIsvInfoSearch',
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
          width: 250,
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
      function handleGroup(isvId: string) {
        openGroupModal(true, {
          isvId,
        });
      }
      function handleExamine(isvId: string) {
        openExamineModal(true, {
          isvId,
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
        registerKeysModal,
        handleKeys,
        handleGroup,
        handleExamine,
        registerExamineModal,
        registerGroupModal,
      };
    },
  });
</script>
