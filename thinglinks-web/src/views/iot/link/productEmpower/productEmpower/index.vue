<template>
  <PageWrapper dense contentFullHeight upwardSpace="2" contentBackground>
    <a-collapse v-model:activeKey="activeKey" style="width: 100%">
      <a-collapse-panel key="1">
        <template #header>
          <h4>{{ t('iot.link.productEmpower.productEmpower.empowermentIntroduction') }}</h4>
        </template>
        <a-typography-paragraph>
          {{ t('iot.link.productEmpower.productEmpower.empowermentIntroduction1') }}
        </a-typography-paragraph>
        <a-typography-paragraph>
          {{ t('iot.link.productEmpower.productEmpower.empowermentIntroduction2') }}
        </a-typography-paragraph>
        <a-typography-paragraph>
          {{ t('iot.link.productEmpower.productEmpower.empowermentIntroduction3') }}
        </a-typography-paragraph>
      </a-collapse-panel>
    </a-collapse>
    <a-button
      type="primary"
      style="margin-top: 12px"
      preIcon="ant-design:edit-outlined"
      @click="handleAdd"
    >
      {{ t('iot.link.productEmpower.productEmpower.title') }}
    </a-button>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <!-- <a-button type="primary" color="error" preIcon="ant-design:delete-outlined" @click="handleBatchDelete">
          {{ t('common.title.delete') }}
        </a-button>
        <a-button type="primary" preIcon="ant-design:plus-outlined" @click="handleAdd">
          {{ t('common.title.add') }}
        </a-button> -->
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
            ]"
            :stopButtonPropagation="true"
          />
        </template>
      </template>
    </BasicTable>
    <EditModal @register="registerModal" @success="handleSuccess" :productId="id" />
    <feedback @register="registerModal" @success="handleSuccess" :productId="id" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, reactive, toRefs } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { BasicTable, TableAction, useTable } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { useModal } from '/@/components/Modal';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { page, remove } from '/@/api/iot/link/productEmpower/productEmpower';
  import { columns, searchFormSchema } from './productEmpower.data';
  import EditModal from './Edit.vue';

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '产品赋能',
    components: {
      BasicTable,
      PageWrapper,
      TableAction,
      EditModal,
    },
    props: {
      id: {
        type: String,
        default: '',
      },
    },
    setup(props) {
      const { t } = useI18n();
      const { createMessage, createConfirm } = useMessage();
      const [registerModal, { openModal }] = useModal();
      console.log(props);

      const state = reactive({
        activeKey: ['1', '2'],
        productId: props.id,
      });

      // 表格
      const [registerTable, { reload }] = useTable({
        title: t('iot.link.productEmpower.productEmpower.table.title'),
        api: page,
        columns: columns(),
        formConfig: {
          name: 'productEmpower',
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
        useSearchForm: false,
        showTableSetting: true,
        bordered: true,
        searchInfo: {
          empowermentIdentification: props.id,
        },
        rowKey: 'id',
        // actionColumn: {
        //   width: 120,
        //   title: t('common.column.action'),
        //   dataIndex: 'action',
        // },
      });

      // 弹出新增页面
      function handleAdd() {
        openModal(true, {
          empowermentIdentification: state.productId,
          type: ActionEnum.ADD,
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

      return {
        t,
        registerTable,
        registerModal,
        handleView,
        handleAdd,
        handleEdit,
        handleSuccess,
        ...toRefs(state),
      };
    },
  });
</script>
../../../../../api/iot/link/productEmpower/productEmpower
