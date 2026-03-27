<template>
  <Description class="mt-4" @register="registerBaseInfo" />
</template>

<script lang="ts">
import { defineComponent, PropType, computed } from 'vue';
import { Description, useDescription } from '/@/components/Description/index';
import { useI18n } from '/@/hooks/web/useI18n';
import { detailDeviceSchema } from '../device/device.data';
import type { DevicePageQuery } from '/@/api/iot/link/device/model/deviceModel';

export default defineComponent({
  name: 'DeviceBasicInfo',
  components: {
    Description,
  },
  props: {
    deviceDetail: {
      type: Object as PropType<DevicePageQuery>,
      required: true,
    },
  },
  setup(props) {
    const { t } = useI18n();

    const baseInfoSchema = computed(() => detailDeviceSchema());
    const [registerBaseInfo] = useDescription({
      title: t('iot.link.device.device.basicInformation'),
      column: 3,
      data: props.deviceDetail,
      schema: baseInfoSchema.value,
    });

    return {
      registerBaseInfo,
    };
  },
});
</script>