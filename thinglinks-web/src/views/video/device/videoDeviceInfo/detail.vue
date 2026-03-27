<template>
  <PageWrapper contentFullHeight>
    <div class="detail-info">
      <Card title="" :bordered="false">
        <div class="device_title">
          <div style="display: flex; align-items: center">
            <span>{{ videoNodeDetail.deviceName }}</span>
          </div>
          <a-button
            type="primary"
            danger
            @click="handleChannel"
            v-hasAnyPermission="['video:media:videoMediaServer:edit']"
          >
            <template #icon><EditOutlined /></template
            >{{ t('video.device.videoDeviceInfo.channel') }}
          </a-button>
        </div>
        <div class="base_data">
          <div class="item">
            <span>{{ t('video.device.videoDeviceInfo.transport') }}：</span>
            <span>{{
              getDictLabel('VIDEO_DEVICE_TRANSPORT', videoNodeDetail?.transport, '')
            }}</span>
          </div>
          <div class="item">
            <span>{{ t('video.device.videoDeviceInfo.ip') }}：</span>
            <span>{{ videoNodeDetail.ip }}</span>
          </div>
          <div class="item">
            <span>{{ t('video.device.videoDeviceInfo.deviceIdentification') }}：</span>
            <span>{{ videoNodeDetail.deviceIdentification }}</span>
          </div>
          <div class="item">
            <span>{{ t('video.device.videoDeviceInfo.createdTime') }}：</span>
            <span>{{ videoNodeDetail.createdTime }}</span>
          </div>
        </div>
      </Card>
    </div>
    <div class="detail-info">
      <Card title="" :bordered="false">
        <a-descriptions :title="t('video.device.videoDeviceInfo.basicInformation')" bordered>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.customName')"
          >
            {{ videoNodeDetail.customName }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.mediaIdentification')"
          >
            {{ videoNodeDetail.mediaIdentification }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.manufacturer')"
          >
            {{ videoNodeDetail.manufacturer }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.model')"
          >
            {{ videoNodeDetail.model }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.firmware')"
          >
            {{ videoNodeDetail.firmware }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.streamMode')"
          >
            {{ getDictLabel('VIDEO_DEVICE_STREAM_MODE', videoNodeDetail?.streamMode, '') }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.onlineStatus')"
          >
            {{ videoNodeDetail.onlineStatus }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.registerTime')"
          >
            {{ videoNodeDetail.registerTime }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.keepaliveTime')"
          >
            {{ videoNodeDetail.keepaliveTime }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.port')"
          >
            {{ videoNodeDetail.port }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.wanIp')"
          >
            {{ videoNodeDetail.wanIp }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.lanIp')"
          >
            {{ videoNodeDetail.lanIp }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.hostAddress')"
          >
            {{ videoNodeDetail.hostAddress }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.expires')"
          >
            {{ videoNodeDetail.expires }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.subscribeCycleForCatalog')"
          >
            {{ videoNodeDetail.subscribeCycleForCatalog }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.subscribeCycleForMobilePosition')"
          >
            {{ videoNodeDetail.subscribeCycleForMobilePosition }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.subscribeCycleForAlarm')"
          >
            {{ videoNodeDetail.subscribeCycleForAlarm }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.mobilePositionSubmissionInterval')"
          >
            {{ videoNodeDetail.mobilePositionSubmissionInterval }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.charset')"
          >
            {{ getDictLabel('VIDEO_DEVICE_CHARSET', videoNodeDetail?.charset, '') }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.ssrcCheck')"
          >
            {{ videoNodeDetail.ssrcCheck }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.geoCoordSys')"
          >
            {{ videoNodeDetail.geoCoordSys }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.sdpIp')"
          >
            {{ videoNodeDetail.sdpIp }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.password')"
          >
            {{ videoNodeDetail.password }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.asMessageChannel')"
          >
            {{ videoNodeDetail.asMessageChannel }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.keepaliveIntervalTime')"
          >
            {{ videoNodeDetail.keepaliveIntervalTime }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.keepaliveTimeoutCount')"
          >
            {{ videoNodeDetail.keepaliveTimeoutCount }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.positionCapability')"
          >
            {{ videoNodeDetail.positionCapability }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.broadcastPushAfterAck')"
          >
            {{ videoNodeDetail.broadcastPushAfterAck }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.ability')"
          >
            {{ videoNodeDetail.ability }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.extendParams')"
          >
            {{ videoNodeDetail.extendParams }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.remark')"
          >
            {{ videoNodeDetail.remark }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.device.videoDeviceInfo.createdTime')"
          >
            {{ videoNodeDetail.createdTime }}
          </a-descriptions-item>
        </a-descriptions>
      </Card>
    </div>
  </PageWrapper>
</template>
<script lang="ts" setup>
  import { ref, reactive, onMounted, defineComponent, h, PropType } from 'vue';
  import { useRouter } from 'vue-router';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { detail } from '/@/api/video/device/videoDeviceInfo';
  import { PageWrapper } from '/@/components/Page';
  import { EditOutlined } from '@ant-design/icons-vue';
  import { useDict } from '/@/components/Dict';
  import { useModal } from '/@/components/Modal';
  import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
  import { Tag, Card } from 'ant-design-vue';

  // 定义boolean渲染函数
  const RenderYesNoComponent = defineComponent({
    name: 'RenderYesNoComponent',
    props: {
      text: {
        type: [Boolean, null] as PropType<boolean | null>,
        required: true,
      },
      withTag: {
        type: Boolean,
        default: false,
      },
    },
  });

  const { t } = useI18n();
  const { currentRoute, push } = useRouter();

  const { getDictLabel } = useDict();
  let videoNodeDetail = reactive({});
  const [registerModal, { openModal }] = useModal();
  let id = ref('');
  onMounted(() => {
    const { params } = currentRoute.value;
    id.value = params.id as string;
    load();
  });
  const load = async () => {
    const res = await detail(id.value);
    videoNodeDetail = Object.assign(videoNodeDetail, res);
  };

  const handleChannel = () => {
    push({
      name: '设备通道',
      params: {
        id: id.value,
      },
    });
  };
</script>
<style lang="less" scope>
  .detail-info {
    & + .detail-info {
      margin-top: 16px;
    }

    .device_title {
      font-size: 16px;
      font-family: PingFang SC-Medium, PingFang SC;
      font-weight: 600;
      color: #2e3033;
      line-height: 19px;
      margin-bottom: 10px;
      display: flex;
      justify-content: space-between;

      .status {
        width: 15px;
        height: 15px;
        border-radius: 50%;
        margin-left: 10px;

        &.online {
          background-color: #00c100;
          color: #fff;
        }

        &.offline {
          background-color: #ff0000;
          color: #fff;
        }
      }
    }

    .base_data {
      display: flex;
      align-items: center;
      font-size: 12px;
      color: #a6a6a6;
      line-height: 17px;

      .item {
        padding-right: 12px;

        & + .item {
          padding-left: 12px;
          border-left: 1px solid #e0e0e0;
        }

        span {
          &.red {
            color: #fa3758;
          }

          &.green {
            color: #43cf7c;
          }
        }
      }
    }
  }
</style>
