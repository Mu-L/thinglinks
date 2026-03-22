<template>
  <div class="device-location">
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
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted, PropType } from 'vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { Button } from 'ant-design-vue';
import { EditOutlined } from '@ant-design/icons-vue';
import EditModal from '/@/views/iot/link/device/location/Edit.vue';
import { useModal } from '/@/components/Modal';
import { ActionEnum } from '/@/enums/commonEnum';
import AMap from '/@/components/Form/src/components/AMap.vue';

export default defineComponent({
  name: 'DeviceLocation',
  components: {
    EditModal,
    AButton: Button,
    EditOutlined,
    AMap
  },
  emits: ['success'],
  props: {
    deviceIdentification: {
      type: String,
      default: '',
    },
    deviceLocationResultVO: {
      type: Object as PropType<any>,
      default: () => ({}),
    }
  },
  setup(props, { emit }) {
    const { t } = useI18n();
    let deviceLocationResultVO = ref({});
    const [registerModal, { openModal }] = useModal();

    onMounted(() => {
      load();
    });

    const load = () => {
      deviceLocationResultVO.value = props.deviceLocationResultVO || {};
    };

    function handleAdd(e: Event) {
      e?.stopPropagation();
      openModal(true, {
        deviceIdentification: props.deviceIdentification,
        type: ActionEnum.ADD,
      });
    }

    function handleEdit(e: Event) {
      e?.stopPropagation();
      openModal(true, {
        record: deviceLocationResultVO.value,
        type: ActionEnum.EDIT,
      });
    }

    function handleSuccess(deviceLocationSaveVO: any) {
      deviceLocationResultVO.value = deviceLocationSaveVO;
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

<style lang="less" scoped>
.device-location {
  // 样式保留
}
</style>