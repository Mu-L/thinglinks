<template>
  <PageWrapper dense contentFullHeight>
    <BasicTable
      @register="registerTable"
      @switch-change="getSwitchChange"
      :switchFlag="switchFlag"
      :isRuleGroovyScript="true"
    >
      <template #toolbar>
        <a-button
          type="primary"
          color="error"
          preIcon="ant-design:delete-outlined"
          @click="handleBatchDelete"
          v-hasAnyPermission="['rule:groovy:ruleGroovyScript:delete']"
        >
          {{ t('common.title.delete') }}
        </a-button>
        <a-button
          type="primary"
          preIcon="ant-design:plus-outlined"
          @click="handleAdd"
          v-hasAnyPermission="['rule:groovy:ruleGroovyScript:add']"
        >
          {{ t('common.title.add') }}
        </a-button>
        <a-button preIcon="ant-design:swap-outlined" @click="switchView"
          >{{ t('iot.link.device.device.switchView') }}
        </a-button>
      </template>
      <template #namespace="{ record }">
        {{ getDictLabel('RULE_GROOVY_SCRIPT_NAMESPACE_TYPE', record?.namespace, '') }}
      </template>
      <template #platformCode="{ record }">
        {{ getDictLabel('RULE_GROOVY_SCRIPT_PLATFORM_CODE', record?.platformCode, '') }}
      </template>
      <template #productCode="{ record }">
        {{ getDictLabel('RULE_GROOVY_SCRIPT_PRODUCT_CODE', record?.productCode, '') }}
      </template>
      <template #channelCode="{ record }">
        {{ getDictLabel('RULE_GROOVY_SCRIPT_CHANNEL_CODE', record?.channelCode, '') }}
      </template>
      <template #businessCode="{ record }">
        {{ getDictLabel('RULE_GROOVY_SCRIPT_BUSINESS_CODE', record?.businessCode, '') }}
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                tooltip: t('common.title.view'),
                icon: 'ant-design:search-outlined',
                onClick: handleView.bind(null, record),
                auth: 'rule:groovy:ruleGroovyScript:view',
              },
              {
                tooltip: t('common.title.edit'),
                icon: 'ant-design:edit-outlined',
                onClick: handleEdit.bind(null, record),
                auth: 'rule:groovy:ruleGroovyScript:edit',
              },
              {
                tooltip: t('common.title.delete'),
                icon: 'ant-design:delete-outlined',
                color: 'error',
                auth: 'rule:groovy:ruleGroovyScript:delete',
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
  import { defineComponent, watch, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { useModal } from '/@/components/Modal';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { page, remove } from '/@/api/iot/rule/groovy/ruleGroovyScript';
  import { columns, searchFormSchema } from './ruleGroovyScript.data';
  import EditModal from './Edit.vue';
  import { useDict } from '/@/components/Dict';
  const { getDictLabel } = useDict();
  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '规则脚本',
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
      // 表格
      const [registerTable, { reload, getSelectRowKeys }] = useTable({
        title: t('iot.rule.groovy.ruleGroovyScript.table.title'),
        api: page,
        columns: columns(),
        formConfig: {
          name: 'RuleGroovyScriptSearch',
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
      function handleView(record: Recordable, e: Event) {
        e?.stopPropagation();
        push({
          name: '规则脚本详情',
          params: {
            id: record.id,
          },
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
      // 切换视图 卡片&&列表
      const switchFlag = ref<boolean>(true);
      function switchView() {
        switchFlag.value = !switchFlag.value;
      }

      function getSwitchChange(e) {
        switchFlag.value = e;
      }
      watch(switchFlag, (newValue) => {
        if (newValue === false) {
          reload();
        }
      });

      return {
        t,
        registerTable,
        registerModal,
        handleView,
        switchFlag,
        switchView,
        getSwitchChange,
        getDictLabel,
        handleAdd,
        handleCopy,
        handleEdit,
        handleDelete,
        handleBatchDelete,
        handleSuccess,
      };
    },
  });
</script>
