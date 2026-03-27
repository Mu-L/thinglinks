<template>
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
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { copyTextToClipboard } from '/@/hooks/web/useCopyToClipboard';
import { useMessage } from '/@/hooks/web/useMessage';
import { BasicTable, useTable } from '/@/components/Table';
import { handleFetchParams } from '/@/utils/thinglinks/common';
import { replaceTopicDynamicParams } from '/@/utils/thinglinks/iot/topic';
import { page } from '/@/api/iot/link/productTopic/productTopic';
import { columns, searchFormSchema } from '/@/views/iot/link/device/topic/topic.data';
import { CopyOutlined } from '@ant-design/icons-vue';
import { Tooltip } from 'ant-design-vue';

export default defineComponent({
  name: 'DeviceTopic',
  components: {
    BasicTable,
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

    const customApi = async (params: any) => {
      const result = await page(params);

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

    const [registerTable] = useTable({
      title: t('iot.link.device.device.deviceTopic.table.title'),
      api: customApi,
      columns: columns(),
      formConfig: {
        name: 'DeviceTopicSearch',
        labelWidth: 100,
        schemas: searchFormSchema(),
        autoSubmitOnEnter: true,
        showResetButton: true,
        showSubmitButton: true,
        resetButtonOptions: {
          preIcon: 'ant-design:rest-outlined',
        },
        submitButtonOptions: {
          preIcon: 'ant-design:search-outlined',
        },
        baseColProps: {
          span: 6,
        },
        actionColOptions: {
          span: 24,
          style: { textAlign: 'right' },
        },
        alwaysShowLines: 1,
      },
      beforeFetch: handleFetchParams,
      useSearchForm: true,
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
    flex-shrink: 0;

    &:hover {
      color: #40a9ff;
    }
  }

  span {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}
</style>