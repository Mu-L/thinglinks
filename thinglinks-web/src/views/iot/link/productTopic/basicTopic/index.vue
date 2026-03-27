<template>
  <PageWrapper dense contentFullHeight>
    <BasicTable @register="registerTable" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { BasicTable, useTable } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { page } from '/@/api/iot/link/productTopic/productTopic';
  import { columns } from './basicTopic.data';

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '产品Topic',
    components: {
      BasicTable,
      PageWrapper,
    },
    props: {
      topicType: {
        type: String,
        default: '',
      },
      productIdentification: {
        type: String,
        default: '',
      },
    },
    setup(props) {
      const { t } = useI18n();

      // 表格
      const [registerTable] = useTable({
        title: t('iot.link.productTopic.productTopic.table.title'),
        api: page,
        columns: columns(),
        formConfig: {
          name: 'ProductTopicSearch',
          labelWidth: 120,
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
        rowKey: 'id',
        searchInfo: {
          topicType: props.topicType,
          productIdentification: props.productIdentification,
        },
      });

      return {
        t,
        registerTable,
      };
    },
  });
</script>
../../../../../api/iot/link/productTopic/productTopic
