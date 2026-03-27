<template>
  <div class="product-empower">
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
    <EditModal @register="registerModal" @success="handleSuccess" :productId="productId" />
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, toRefs } from 'vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { BasicTable, TableAction, useTable } from '/@/components/Table';
import { Collapse, Typography, Button } from 'ant-design-vue';
import { useModal } from '/@/components/Modal';
import { handleFetchParams } from '/@/utils/thinglinks/common';
import { ActionEnum } from '/@/enums/commonEnum';
import { page } from '/@/api/iot/link/productEmpower/productEmpower';
import { columns, searchFormSchema } from '/@/views/iot/link/productEmpower/productEmpower/productEmpower.data';
import EditModal from '/@/views/iot/link/productEmpower/productEmpower/Edit.vue';

export default defineComponent({
  name: 'ProductEmpower',
  components: {
    BasicTable,
    TableAction,
    ACollapse: Collapse,
    ACollapsePanel: Collapse.Panel,
    ATypographyParagraph: Typography.Paragraph,
    AButton: Button,
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
    const [registerModal, { openModal }] = useModal();

    const state = reactive({
      activeKey: ['1'],
      productId: props.id,
    });

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
    });

    function handleAdd() {
      openModal(true, {
        empowermentIdentification: state.productId,
        type: ActionEnum.ADD,
      });
    }

    function handleView(record: Recordable, e: Event) {
      e?.stopPropagation();
      openModal(true, {
        record,
        type: ActionEnum.VIEW,
      });
    }

    function handleSuccess() {
      reload();
    }

    return {
      t,
      registerTable,
      registerModal,
      handleView,
      handleAdd,
      handleSuccess,
      ...toRefs(state),
    };
  },
});
</script>

<style lang="less" scoped>
.product-empower {
  h4 {
    margin: 0;
    font-size: 14px;
    font-weight: 600;
  }
}
</style>