<template>
  <BasicTable @register="registerTable">
    <template #commandType="{ record }">
      {{ getDictLabel('LINK_DEVICE_COMMAND_TYPE', record?.commandType, '') }}
    </template>
    <template #status="{ record }">
      <a-tag :color="record.status == 0 ? 'orange' : record.status == 1 ? 'green' : 'red'">
        {{ getDictLabel('LINK_DEVICE_COMMAND_STATUS', record.status, '') }}
      </a-tag>
    </template>
  </BasicTable>
  <EditModal @register="registerModal" @success="handleSuccess" />
  <ViewValueModal @register="registerModalCode" />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { BasicTable, useTable } from '/@/components/Table';
import { useModal } from '/@/components/Modal';
import { handleFetchParams } from '/@/utils/thinglinks/common';
import { page } from '/@/api/iot/link/deviceCommand/deviceCommand';
import { columns, searchFormSchema } from '/@/views/iot/link/device/command/command.data';
import EditModal from '/@/views/iot/link/device/command/Edit.vue';
import ViewValueModal from '/@/components/ViewValueModal/index.vue';
import { useDict } from '/@/components/Dict';

const { getDictLabel } = useDict();

export default defineComponent({
  name: 'DeviceCommand',
  components: {
    BasicTable,
    EditModal,
    ViewValueModal,
  },
  props: {
    deviceIdentification: {
      type: String,
      default: '',
    },
  },
  setup(props) {
    const { t } = useI18n();
    const [registerModal] = useModal();
    const [registerModalCode, { openModal: openCode }] = useModal();

    const openRemarkModal = (value: string, title: string) => {
      openCode(true, { value, title, width: 500 });
    };

    const [registerTable, { reload }] = useTable({
      title: '',
      api: page,
      columns: columns({
        openRemarkModal,
      }),
      formConfig: {
        name: 'DeviceCommandSearch',
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
        deviceIdentification: props.deviceIdentification,
      },
      defSort: {
        sort: 'createdTime',
        order: 'descend',
      },
    });

    function handleSuccess() {
      reload();
    }

    return {
      t,
      registerTable,
      registerModal,
      handleSuccess,
      registerModalCode,
      getDictLabel,
    };
  },
});
</script>