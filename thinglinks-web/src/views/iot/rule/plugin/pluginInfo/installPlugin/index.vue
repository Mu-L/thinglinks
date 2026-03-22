<template>
  <div style="margin-top: 10px; background-color: #fff">
    <a-button
      style="float: right; margin: 10px"
      type="primary"
      danger
      :disabled="pluginInstanceDetailsList.length == 0"
      @click="handleAllDelete"
    >
      {{ t('iot.rule.plugin.pluginInfo.allUninstall') }}
    </a-button>
    <BasicTable @register="registerTable">
      <template #status="{ record }">
        {{ getDictLabel('RULE_PLUGIN_INSTANCE_PORT_STATUS', record?.status, '') }}
      </template>
      <template #healthy="{ record }">
        {{ record?.healthy }}
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
                tooltip: t('iot.rule.plugin.pluginInfo.uninstallation'),
                icon: 'ant-design:delete-outlined',
                color: 'error',
                popConfirm: {
                  title: t('common.tips.confirmUninstall'),
                  confirm: handleDelete.bind(null, record),
                },
              },
            ]"
            :stopButtonPropagation="true"
          />
        </template> </template
    ></BasicTable>
    <EditModal @register="registerModal" @success="handleSuccess" />
  </div>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { install } from '/@/api/iot/rule/plugin/pluginInfo';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { getBasicColumns } from './installPlugin.data';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useDict } from '/@/components/Dict';
  import { useModal } from '/@/components/Modal';
  import EditModal from './Edit.vue';
  import { ActionEnum } from '/@/enums/commonEnum';
  const { getDictLabel } = useDict();

  export default defineComponent({
    components: { BasicTable, TableAction, EditModal },
    props: {
      pluginInstanceDetailsList: {
        type: Array,
        default: () => [],
      },
    },
    emits: ['unInstall', 'uninstallAll'],
    setup(props, { emit }) {
      const { t } = useI18n();
      const [registerModal, { openModal }] = useModal();
      // 表格
      const [registerTable, { reload, getSelectRowKeys }] = useTable({
        columns: getBasicColumns(),
        bordered: true,
        dataSource: props.pluginInstanceDetailsList,
        showTableSetting: true,
        pagination: false,
        rowKey: 'id',
        actionColumn: {
          width: 200,
          title: t('common.column.action'),
          dataIndex: 'action',
        },
      });

      function handleEdit(record, e: MouseEvent) {
        e?.stopPropagation();
        openModal(true, {
          record,
          type: ActionEnum.EDIT,
        });
      }
      // 点击单行卸载
      function handleDelete(record: Recordable, e: Event) {
        e?.stopPropagation();
        emit('unInstall', { instanceId: record.id, status: 1 });
      }
      // 全部卸载
      function handleAllDelete() {
        emit('uninstallAll');
      }
      // 新增或编辑成功回调
      function handleSuccess() {
        reload();
      }
      return {
        t,
        getDictLabel,
        pluginInstanceDetailsList: props.pluginInstanceDetailsList,
        columns: getBasicColumns(),
        registerTable,
        handleSuccess,
        registerModal,
        handleEdit,
        handleDelete,
        handleAllDelete,
      };
    },
  });
</script>
