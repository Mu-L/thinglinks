<template>
  <PageWrapper dense contentFullHeight>
    <a-button type="primary" @click="addDeviceToGroup">{{
      t('iot.link.device.device.addGroup')
    }}</a-button>
    <BasicTable @register="registerTable" :dataSource="data" :scroll="{ y: 700 }">
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                label: t('common.title.view'),
                onClick: goGroupView.bind(null, record),
              },
            ]"
          />
        </template>
      </template>
    </BasicTable>
    <device-to-group @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, toRefs } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { PageWrapper } from '/@/components/Page';
  import { useModal } from '/@/components/Modal';
  import { useRouter } from 'vue-router';

  // data
  import { columns } from './deviceGroup.data';

  // components
  import { Card, Row, Col, Button } from 'ant-design-vue';
  import deviceToGroup from './deviceToGroup.vue';
  import { BasicTable, TableAction, useTable } from '/@/components/Table';
  // api
  import { page } from '/@/api/iot/link/group/deviceGroupRel';

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '设备分组',
    components: {
      PageWrapper,
      ACard: Card,
      ARow: Row,
      ACol: Col,
      AButton: Button,
      deviceToGroup,
      BasicTable,
      TableAction,
    },
    props: {
      deviceIdentification: {
        type: String,
        default: '',
      },
    },
    setup(props) {
      const { t } = useI18n();
      const [registerModal, { openModal }] = useModal();
      const { push } = useRouter();

      const { deviceIdentification } = toRefs(props);

      const addDeviceToGroup = () => {
        openModal(true, {
          deviceIdentification: deviceIdentification.value,
        });
      };

      // 表格1
      const [registerTable, { reload }] = useTable({
        api: page,
        columns: columns(),
        actionColumn: {
          width: 'auto',
          title: t('common.column.action'),
          dataIndex: 'action',
        },
        showTableSetting: true,
        pagination: { pageSize: 10 },
        beforeFetch: (params) => {
          return {
            ...params,
            model: {
              deviceIdentification: deviceIdentification.value,
            },
          };
        },
      });

      const goGroupView = (record) => {
        const { groupId } = record;
        push({
          name: '设备分组',
          query: { id: groupId },
        });
      };

      const handleSuccess = () => {
        reload();
      };

      return {
        t,
        registerModal,
        addDeviceToGroup,
        registerTable,
        goGroupView,
        handleSuccess,
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
  }
</style>
