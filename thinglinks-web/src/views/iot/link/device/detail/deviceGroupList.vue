<template>
  <div>
    <a-button type="primary" @click="addDeviceToGroup">{{
      t('iot.link.device.device.addGroup')
    }}</a-button>
    <BasicTable @register="registerTable" :scroll="{ y: 700 }">
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
    <deviceToGroup @register="registerModal" @success="handleSuccess" />
  </div>
</template>

<script lang="ts">
import { defineComponent, toRefs } from 'vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { useModal } from '/@/components/Modal';
import { useRouter } from 'vue-router';
import { columns } from '/@/views/iot/link/device/deviceGroup/deviceGroup.data';
import { Button } from 'ant-design-vue';
import deviceToGroup from '/@/views/iot/link/device/deviceGroup/deviceToGroup.vue';
import { BasicTable, TableAction, useTable } from '/@/components/Table';
import { page } from '/@/api/iot/link/group/deviceGroupRel';

export default defineComponent({
  name: 'DeviceGroupList',
  components: {
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
    const router = useRouter();
    const [registerModal, { openModal }] = useModal();
    const { deviceIdentification } = toRefs(props);

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

    function addDeviceToGroup() {
      openModal(true, {
        deviceIdentification: deviceIdentification.value,
      });
    }

    function goGroupView(record: Recordable) {
      router.push({
        name: '设备分组',
        query: { id: record.groupId },
      });
    }

    function handleSuccess() {
      reload();
    }

    return {
      t,
      registerTable,
      registerModal,
      addDeviceToGroup,
      goGroupView,
      handleSuccess,
    };
  },
});
</script>