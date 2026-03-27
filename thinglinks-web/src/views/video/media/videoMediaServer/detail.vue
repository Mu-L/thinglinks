<template>
  <PageWrapper contentFullHeight>
    <div class="detail-info">
      <Card title="" :bordered="false">
        <div class="device_title">
          <div style="display: flex; align-items: center">
            <span>{{ videoNodeDetail.name }}</span>
            <div class="status" :class="videoNodeDetail?.onlineStatus ? 'online' : 'offline'"></div
          ></div>
          <a-button
            type="primary"
            danger
            @click="handleEdit"
            v-hasAnyPermission="['video:media:videoMediaServer:edit']"
          >
            <template #icon><EditOutlined /></template
            >{{ t('video.media.videoMediaServer.updateNodeInfo') }}
          </a-button>
        </div>
        <div class="base_data">
          <div class="item">
            <span>{{ t('video.media.videoMediaServer.type') }}：</span>
            <span>{{ getDictLabel(DictEnum.VIDEO_MEDIA_SERVER_TYPE, videoNodeDetail?.type) }}</span>
          </div>
          <div class="item">
            <span>{{ t('video.media.videoMediaServer.ip') }}：</span>
            <span>{{ videoNodeDetail.ip }}</span>
          </div>
          <div class="item">
            <span>{{ t('video.media.videoMediaServer.mediaIdentification') }}：</span>
            <span>{{ videoNodeDetail.mediaIdentification }}</span>
          </div>
          <div class="item">
            <span>{{ t('video.media.videoMediaServer.createdTime') }}：</span>
            <span>{{ videoNodeDetail.createdTime }}</span>
          </div>
        </div>
      </Card>
    </div>
    <div class="detail-info">
      <Card title="" :bordered="false">
        <a-descriptions :title="t('video.media.videoMediaServer.baseInfo')" bordered>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.hookIp')"
          >
            {{ videoNodeDetail.hookIp }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.sdpIp')"
          >
            {{ videoNodeDetail.sdpIp }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.streamIp')"
          >
            {{ videoNodeDetail.streamIp }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.httpPort')"
          >
            {{ videoNodeDetail.httpPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.httpSslPort')"
          >
            {{ videoNodeDetail.httpSslPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.rtmpPort')"
          >
            {{ videoNodeDetail.rtmpPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.rtmpSslPort')"
          >
            {{ videoNodeDetail.rtmpSslPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.rtpProxyPort')"
          >
            {{ videoNodeDetail.rtpProxyPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.rtspPort')"
          >
            {{ videoNodeDetail.rtspPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.rtspSslPort')"
          >
            {{ videoNodeDetail.rtspSslPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.flvPort')"
          >
            {{ videoNodeDetail.flvPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.flvSslPort')"
          >
            {{ videoNodeDetail.flvSslPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.wsFlvPort')"
          >
            {{ videoNodeDetail.wsFlvPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.wsFlvSslPort')"
          >
            {{ videoNodeDetail.wsFlvSslPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.secret')"
          >
            {{ videoNodeDetail.secret }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.rtpPortRange')"
          >
            {{ videoNodeDetail.rtpPortRange }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.sendRtpPortRange')"
          >
            {{ videoNodeDetail.sendRtpPortRange }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.recordAssistPort')"
          >
            {{ videoNodeDetail.recordAssistPort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.hookAliveInterval')"
          >
            {{ videoNodeDetail.hookAliveInterval }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.recordPath')"
          >
            {{ videoNodeDetail.recordPath }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.recordDay')"
          >
            {{ videoNodeDetail.recordDay }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.transcodeSuffix')"
          >
            {{ videoNodeDetail.transcodeSuffix }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.remark')"
          >
            {{ videoNodeDetail.remark }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.autoConfig')"
          >
            <RenderYesNoComponent :text="videoNodeDetail.autoConfig" :withTag="true" />
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.defaultServer')"
          >
            <RenderYesNoComponent :text="videoNodeDetail.defaultServer" :withTag="true" />
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.rtpEnable')"
          >
            <RenderYesNoComponent :text="videoNodeDetail.rtpEnable" :withTag="true" />
          </a-descriptions-item>

          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.createdBy')"
          >
            {{ videoNodeDetail.createdBy }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.createdTime')"
          >
            {{ videoNodeDetail.createdTime }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.updatedBy')"
          >
            {{ videoNodeDetail.updatedBy }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('video.media.videoMediaServer.updatedTime')"
          >
            {{ videoNodeDetail.updatedTime }}
          </a-descriptions-item>
        </a-descriptions>
      </Card>
    </div>
    <EditModal @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts" setup>
  import { ref, reactive, onMounted, defineComponent, h, PropType } from 'vue';
  import { useRouter } from 'vue-router';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { detail } from '/@/api/video/media/videoMediaServer';
  import type { VideoMediaServerPageQuery } from '/@/api/video/media/model/videoMediaServerModel';
  import { PageWrapper } from '/@/components/Page';
  import { EditOutlined } from '@ant-design/icons-vue';
  import { useDict } from '/@/components/Dict';
  import EditModal from './Edit.vue';
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
    setup(props) {
      return () => {
        let _text = '';
        let color = '';
        switch (props.text) {
          case null:
            _text = t('thinglinks.common.all');
            color = 'processing';
            break;
          case true:
            _text = t('thinglinks.common.yes');
            color = 'success';
            break;
          case false:
            _text = t('thinglinks.common.no');
            color = 'error';
            break;
        }
        return props.withTag ? h(Tag, { color }, _text) : _text;
      };
    },
  });

  const { getDictLabel } = useDict();
  const { t } = useI18n();
  const { currentRoute } = useRouter();

  let videoNodeDetail = reactive<VideoMediaServerPageQuery>({});
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

  // 弹出编辑页面
  function handleEdit(e: Event) {
    e?.stopPropagation();
    openModal(true, {
      record: videoNodeDetail,
      type: ActionEnum.EDIT,
    });
  }

  // 新增或编辑成功回调
  function handleSuccess() {
    load();
  }
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
