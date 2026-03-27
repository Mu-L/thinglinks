<template>
  <BasicModal
    @register="register"
    v-bind="$attrs"
    :title="title"
    :width="width"
    :showOkBtn="false"
    @cancel="handleCancel"
  >
    <div class="value-viewer">
      <div class="value-label">
        <div class="label-icon-wrapper">
          <LockOutlined class="label-icon" />
        </div>
        <div class="label-info">
          <span class="label-text">{{ title }}</span>
        </div>
      </div>
      <div class="value-content">
        <div class="value-text">{{ value || '-' }}</div>
        <div class="copy-hint" v-if="value">
          <CopyOutlined />
          <span>{{ t('common.title.copy') }}</span>
        </div>
      </div>
    </div>
    <template #footer>
      <div class="footer-actions">
        <a-button @click="handleCancel">
          {{ t('common.closeText') }}
        </a-button>
        <a-button type="primary" @click="handleCopy" :disabled="!value">
          <template #icon><CopyOutlined /></template>
          {{ t('common.title.copy') }}
        </a-button>
      </div>
    </template>
  </BasicModal>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { BasicModal, useModalInner } from '/@/components/Modal';
import { useI18n } from '/@/hooks/web/useI18n';
import { useMessage } from '/@/hooks/web/useMessage';
import { CopyOutlined, LockOutlined } from '@ant-design/icons-vue';
import { handleCopyTextV2 } from '/@/utils/thinglinks/common';

export default defineComponent({
  name: 'ViewValueModal',
  components: {
    BasicModal,
    CopyOutlined,
    LockOutlined,
  },
  setup() {
    const { t } = useI18n();
    const { createMessage } = useMessage();

    const title = ref('');
    const value = ref('');
    const width = ref(520);

    const [register, { closeModal }] = useModalInner(async (data) => {
      title.value = data.title || '';
      value.value = data.value || '';
      width.value = data.width || 520;
      return data;
    });

    const handleCopy = () => {
      if (!value.value) {
        createMessage.warning(t('common.noData'));
        return;
      }
      handleCopyTextV2(value.value);
    };

    const handleCancel = () => {
      closeModal();
    };

    return {
      t,
      title,
      value,
      width,
      register,
      handleCopy,
      handleCancel,
    };
  },
});
</script>

<style lang="less" scoped>
.value-viewer {
  padding: 4px 0;

  .value-label {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 20px;
    padding: 14px 16px;
    background: linear-gradient(135deg, #474849 0%, #e6f4ff 100%);
    border-radius: 10px;
    border: 1px solid #d6e4ff;

    .label-icon-wrapper {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 40px;
      height: 40px;
      background: linear-gradient(135deg, #1890ff 0%, #096dd9 100%);
      border-radius: 10px;
      box-shadow: 0 4px 12px rgba(24, 144, 255, 0.25);

      .label-icon {
        font-size: 20px;
        color: #ffffff;
      }
    }

    .label-info {
      flex: 1;
      display: flex;
      flex-direction: column;
      gap: 4px;

      .label-text {
        font-size: 16px;
        font-weight: 600;
        color: #262626;
        line-height: 1.4;
      }
    }
  }

  .value-content {
    position: relative;
    background: #fafafa;
    border: 2px dashed #e8e8e8;
    border-radius: 10px;
    padding: 18px 20px;
    min-height: 80px;
    transition: all 0.3s ease;

    &:hover {
      border-color: #1890ff;
      background: #f0f5ff;
      box-shadow: 0 4px 12px rgba(24, 144, 255, 0.08);
    }

    .value-text {
      font-size: 15px;
      font-family: 'Courier New', Consolas, Monaco, 'PingFang SC', 'Microsoft YaHei', monospace;
      color: #262626;
      word-break: break-all;
      line-height: 1.8;
      letter-spacing: 0.5px;
      font-weight: 500;
      padding-right: 80px;
    }

    .copy-hint {
      position: absolute;
      right: 16px;
      top: 50%;
      transform: translateY(-50%) translateX(10px);
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 4px;
      padding: 8px 12px;
      background: #ffffff;
      border-radius: 6px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
      opacity: 0;
      transition: all 0.3s ease;

      :deep(.anticon) {
        font-size: 18px;
        color: #1890ff;
      }

      span {
        font-size: 12px;
        color: #595959;
        white-space: nowrap;
      }
    }

    &:hover .copy-hint {
      opacity: 1;
      transform: translateY(-50%) translateX(0);
    }
  }
}

.footer-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding-top: 20px;
  margin-top: 8px;
  border-top: 1px solid #f0f0f0;
}
</style>