<template>
  <PageWrapper dense contentFullHeight>
    <BasicTable @register="registerTable">
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'topic'">
          <div class="topic-cell">
            <CopyOutlined class="copy-icon" @click="copyFn(record.topic)" />
            <Tooltip :title="record.topic" placement="topLeft">
              <span>{{ record.topic }}</span>
            </Tooltip>
          </div>
        </template>
      </template>
    </BasicTable>
  </PageWrapper>
</template>
<script lang="ts">
import { defineComponent, computed } from 'vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { copyTextToClipboard } from '/@/hooks/web/useCopyToClipboard';
import { useMessage } from '/@/hooks/web/useMessage';
import { BasicTable, useTable } from '/@/components/Table';
import { PageWrapper } from '/@/components/Page';
import { handleFetchParams } from '/@/utils/thinglinks/common';
import { replaceTopicDynamicParams } from '/@/utils/thinglinks/iot/topic';
import { page } from '/@/api/iot/link/productTopic/productTopic';
import { columns } from './topic.data';
import { CopyOutlined } from '@ant-design/icons-vue';
import { Tooltip } from 'ant-design-vue';

export default defineComponent({
  name: '设备Topic',
  components: {
    BasicTable,
    PageWrapper,
    CopyOutlined,
    Tooltip,
  },
  props: {
    deviceIdentification: {
      type: String,
      default: '',
    },
    deviceSdkVersion: {
      type: String,
      default: '',
    },
    nodeType: {
      type: [String, Number],
      default: '',
    },
    productIdentification: {
      type: String,
      default: '',
    },
    appId: {
      type: String,
      default: '',
    },
    userName: {
      type: String,
      default: '',
    },
  },
  setup(props) {
    const { t } = useI18n();
    const { createMessage } = useMessage();

    // 自定义API处理函数
    const customApi = async (params: any) => {
      const result = await page(params);

      // 对返回的数据进行参数替换
      if (result?.records && Array.isArray(result.records)) {
        result.records = result.records.map((item: any) => ({
          ...item,
          topic: replaceTopicDynamicParams(item.topic, {
            appId: props.appId,
            userName: props.userName,
            deviceIdentification: props.deviceIdentification,
            deviceSdkVersion: props.deviceSdkVersion,
            productIdentification: props.productIdentification,
          }),
        }));
      }

      return result;
    };

    // 表格
    const [registerTable] = useTable({
      title: t('iot.link.device.device.deviceTopic.table.title'),
      api: customApi,
      columns: columns(),
      formConfig: {
        name: 'DeviceTopicSearch',
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
        productIdentification: props.productIdentification,
      },
    });

    function copyFn(text: string) {
      const result = copyTextToClipboard(text);
      if (result) {
        createMessage.success(t('common.tips.copySuccess'));
      } else {
        createMessage.warning(t('common.tips.copyFail'));
      }
    }

    return {
      t,
      registerTable,
      copyFn,
    };
  },
});
</script>
<style lang="less" scoped>
.topic-cell {
  display: flex;
  align-items: center;
  gap: 8px;

  .copy-icon {
    cursor: pointer;
    color: #1890ff;
    font-size: 14px;

    &:hover {
      color: #40a9ff;
    }
  }
}
</style>