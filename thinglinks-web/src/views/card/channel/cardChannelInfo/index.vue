<template>
  <PageWrapper dense contentFullHeight>
    <BasicTable
      @register="registerTable"
      @switch-change="getSwitchChange"
      :is-iot-channel-card="true"
      :switchFlag="switchFlag"
    >
      <template #toolbar>
        <a-button
          type="primary"
          color="error"
          preIcon="ant-design:delete-outlined"
          @click="handleBatchDelete"
          v-hasAnyPermission="['card:channel:cardChannelInfo:delete']"
        >
          {{ t('common.title.delete') }}
        </a-button>
        <a-button
          type="primary"
          preIcon="ant-design:plus-outlined"
          @click="handleAdd"
          v-hasAnyPermission="['card:channel:cardChannelInfo:add']"
        >
          {{ t('common.title.add') }}
        </a-button>
        <a-button preIcon="ant-design:swap-outlined" @click="switchView"
          >{{ t('card.channel.cardChannelInfo.switchView') }}
        </a-button>
      </template>
       <template #officialFlag="{ record }">
        {{ getDictLabel('CARD_CHANNEL_OFFICIAL_FLAG', record?.officialFlag, '') }}
      </template>
       <template #refreshFlag="{ record }">
        {{ record?.refreshFlag == 1 ? '是' : '否' }}
      </template>
       <template #operatorType="{ record }">
        {{ getDictLabel('CARD_CHANNEL_OPERATOR', record?.operatorType, '') }}
      </template>
       <template #status="{ record }">
        {{ getDictLabel('CARD_CHANNEL_STATUS', record?.status, '') }}
      </template>
       <template #channelType="{ record }">
        {{ getDictLabel('CARD_CHANNEL_TYPE', record?.channelType, '') }}
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                tooltip: t('common.title.view'),
                icon: 'ant-design:search-outlined',
                onClick: handleView.bind(null, record),
                auth: 'card:channel:cardChannelInfo:view'
              },
              {
                tooltip: t('common.title.edit'),
                icon: 'ant-design:edit-outlined',
                onClick: handleEdit.bind(null, record),
                auth: 'card:channel:cardChannelInfo:edit',
              },
              {
                tooltip: t('common.title.copy'),
                icon: 'ant-design:copy-outlined',
                onClick: handleCopy.bind(null, record),
                auth: 'card:channel:cardChannelInfo:copy',
              },
              {
                tooltip: t('common.title.delete'),
                icon: 'ant-design:delete-outlined',
                color: 'error',
                auth: 'card:channel:cardChannelInfo:delete',
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
import { page, remove, deleteSingle } from '/@/api/card/channel/cardChannelInfo';
import { columns, searchFormSchema } from './cardChannelInfo.data';
import EditModal from './Edit.vue';
import { useRouter } from 'vue-router';
import { useDict } from '/@/components/Dict';
const { getDictLabel } = useDict();
export default defineComponent({
  // 若需要开启页面缓存，请将此参数跟菜单名保持一致
  name: '渠道接入',
  components: {
    BasicTable,
    PageWrapper,
    TableAction,
    EditModal,
  },
  setup() {
    const { t } = useI18n();
    const { createMessage, createConfirm } = useMessage();
    const [registerModal, { openModal }] = useModal();
    const { push } = useRouter();

    const switchFlag = ref<boolean>(true);
    function switchView() {
      console.log('switchView');
      switchFlag.value = !switchFlag.value;
    }
    function getSwitchChange(e) {
      switchFlag.value = e;
    }

    // 表格
    const [registerTable, { reload, getSelectRowKeys }] = useTable({
      title: t('card.channel.cardChannelInfo.table.title'),
      api: page,
      columns: columns(),
      formConfig: {
        name: 'CardChannelInfoSearch',
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
    const channelId = ref<string>('');
    function handleView(record: Recordable, e: Event) {
      e?.stopPropagation();
      channelId.value = record.id;
      push({
        name: '渠道详情',
        params: { id: record.id },
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

    async function handleDeleteSingle(id: string) {
      await deleteSingle(id);
      createMessage.success(t('common.tips.deleteSuccess'));
      handleSuccess();
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
        handleDeleteSingle(record.id);
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
          } catch (e) { }
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
      switchFlag,
      switchView,
      getSwitchChange,
      getDictLabel
    };
  },
});
</script>
