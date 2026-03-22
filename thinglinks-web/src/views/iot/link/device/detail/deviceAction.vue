<template>
  <BasicTable @register="registerTable" />
  <EditModal @register="registerModal" @success="handleSuccess" />
  <ViewValueModal @register="registerModalCode" />
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { BasicTable, useTable } from '/@/components/Table';
import { useModal } from '/@/components/Modal';
import { handleFetchParams } from '/@/utils/thinglinks/common';
import { page } from '/@/api/iot/link/deviceAction/deviceAction';
import { columns, searchFormSchema } from '/@/views/iot/link/device/action/action.data';
import EditModal from '/@/views/iot/link/device/action/Edit.vue';
import ViewValueModal from '/@/components/ViewValueModal/index.vue';

export default defineComponent({
  name: 'DeviceAction',
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
      openRemarkModal,
    };
  },
});
</script>