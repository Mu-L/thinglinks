<template>
  <PageWrapper dense contentFullHeight>
    <BasicTable @register="registerTable" />
    <EditModal @register="registerModal" @success="handleSuccess" />
    <CopyModal @register="registerModalCode" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { useModal } from '/@/components/Modal';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { page } from '/@/api/iot/link/deviceAction/deviceAction';
  import { columns, searchFormSchema } from './action.data';
  import EditModal from './Edit.vue';
  import CopyModal from '/@/components/CopyModal/index.vue';

  // icon
  import { SearchOutlined, CopyOutlined } from '@ant-design/icons-vue';
  import { Card, Row, Col } from 'ant-design-vue';
  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '设备动作',
    components: {
      BasicTable,
      PageWrapper,
      TableAction,
      EditModal,
      SearchOutlined,
      CopyOutlined,
      ACard: Card,
      ARow: Row,
      ACol: Col,
      CopyModal,
    },
    props: {
      deviceIdentification: {
        type: String,
        default: '',
      },
    },
    setup(_) {
      const { t } = useI18n();
      const [registerModal] = useModal();

      const [registerModalCode, { openModal: openCode }] = useModal();
      const openRemarkModal = (value: string, title: string) => {
        openCode(true, { value, title });
      };

      // 表格
      const [registerTable, { reload }] = useTable({
        title: '',
        api: page,
        columns: columns({
          openRemarkModal,
        }),
        formConfig: {
          name: 'DeviceSearch',
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
        searchInfo: {
          deviceIdentification: _.deviceIdentification,
        },
        defSort: {
          sort: 'createdTime',
          order: 'descend',
        },
      });

      // 新增或编辑成功回调
      function handleSuccess() {
        reload();
      }

      return {
        t,
        registerTable,
        registerModal,
        handleSuccess,
        registerModalCode,
        openRemarkModal,
      };
    },
  });
</script>
<style lang="less" scoped>
  .thinglinks-basic-table {
    padding: 0 16px;
  }

  :deep(.ant-form) {
    margin-bottom: 0;
    padding: 0;
  }

  :deep(.ant-table-body) {
    min-height: 250px;
  }

  :deep(.ant-table-expanded-row-fixed) {
    min-height: 250px;
  }</style
>../../../../../api/iot/link/deviceAction/deviceAction
