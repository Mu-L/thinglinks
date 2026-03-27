<template>
  <PageWrapper title="" contentFullHeight>
    <div style="margin-bottom: 20px">
      <a-button type="primary" danger @click="handleAdd" v-if="!deviceLocationResultVO.longitude">
        <template #icon>
          <EditOutlined />
        </template>新增位置信息
      </a-button>
      <a-button type="primary" danger @click="handleEdit" v-else>
        <template #icon>
          <EditOutlined />
        </template>{{ t('iot.link.device.device.updatePositionButton') }}
      </a-button>
    </div>
    <AMap v-if="deviceLocationResultVO.longitude" :value="[deviceLocationResultVO.longitude, deviceLocationResultVO.latitude]"></AMap>
    <EditModal @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts">
import { defineComponent, ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useI18n } from '/@/hooks/web/useI18n';
import { useMessage } from '/@/hooks/web/useMessage';
import { deviceLocationDetail } from '/@/api/iot/link/deviceLocation/deviceLocation';
import { PageWrapper } from '/@/components/Page';
import { Button } from 'ant-design-vue';
import { EditOutlined } from '@ant-design/icons-vue';
import { useDict } from '/@/components/Dict';
import EditModal from './Edit.vue';
import { useModal } from '/@/components/Modal';
import { ActionEnum } from '/@/enums/commonEnum';
import AMap from '/@/components/Form/src/components/AMap.vue';
export default defineComponent({
  name: '设备详情',
  components: {
    PageWrapper,
    EditModal,
    AButton: Button,
    EditOutlined,
    AMap
  },
  emits: ['success', 'register'],
  props: {
    deviceIdentification: {
      type: String,
      default: '',
    },
    deviceLocationResultVO: {
      type: Object,
      default: {},
    }
  },
  setup(_, { emit }) {
    const { t } = useI18n();
    const { createMessage, createConfirm } = useMessage();
    let deviceLocationResultVO = ref({})
    const [registerModal, { openModal }] = useModal();
    onMounted(() => {
      load();
    });
    const load = () => {
      deviceLocationResultVO.value = _.deviceLocationResultVO || {}
    }
    // 弹出编辑页面
    function handleAdd(e: Event) {
      e?.stopPropagation();
      openModal(true, {
        deviceIdentification: _.deviceIdentification,
        type: ActionEnum.ADD,
      });
    }

    // 弹出编辑页面
    function handleEdit(e: Event) {
      e?.stopPropagation();
      openModal(true, {
        record: deviceLocationResultVO.value,
        type: ActionEnum.EDIT,
      });
    }

    // 新增或编辑成功回调
    function handleSuccess(deviceLocationSaveVO) {
      deviceLocationResultVO.value = deviceLocationSaveVO
      emit('success', deviceLocationSaveVO);
    }

    return {
      t,
      registerModal,
      handleEdit,
      handleAdd,
      handleSuccess,
      deviceLocationResultVO,
    };
  },
});
</script>
<style lang="less" scope>
.detail-info {
  &+.detail-info {
    margin-top: 16px;
  }

  .device_title {
    font-size: 16px;
    font-family: PingFang SC-Medium, PingFang SC;
    font-weight: 600;
    color: #2E3033;
    line-height: 19px;
    margin-bottom: 10px;
    display: flex;
    justify-content: space-between;
  }

  .base_data {
    display: flex;
    align-items: center;
    font-size: 12px;
    color: #a6a6a6;
    line-height: 17px;

    .item {
      padding-right: 12px;

      &+.item {
        padding-left: 12px;
        border-left: 1px solid #e0e0e0;
      }

      span {
        &.red {
          color: #FA3758;
        }

        &.green {
          color: #43CF7C;
        }
      }
    }
  }
}
</style>../../../../../api/iot/link/deviceLocation/deviceLocation