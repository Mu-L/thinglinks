<template>
  <div class="service-box">
    <!-- <a-row :gutter="[24, 24]">
      <a-col :span="6"> -->
    <div class="service-left">
      <div class="title-btns">
        <div class="title">
          {{ t('iot.link.device.device.serviceList') }}
          <LinkOutlined v-if="socketIsSuccess" style="color: #43cf7c" />
          <DisconnectOutlined v-else style="color: #f50" />
        </div>
      </div>
      <div class="list">
        <div
          class="item"
          :class="index === currentServiceIndex ? 'active' : ''"
          v-for="(item, index) in services"
          :key="index"
          @click="changeService(index)"
        >
          <div class="name">{{ item.serviceName }}</div>
          <div class="desc">{{ item.description }}</div>
        </div>
      </div>
    </div>
    <!-- </a-col>
      <a-col :span="18"> -->
    <div class="service-right">
      <div class="properties" v-for="item in properties" :key="item.propertyCode">
        <a-card hoverable style="width: 260px">
          <template #cover>
            <div class="content">
              <div class="name">
                <div class="title">
                  <div class="title-txt">{{ item.propertyName }}</div>
                  <div class="right-btn">
                    <a-tooltip placement="top" :title="t('common.title.view') + 'Icon'">
                      <EyeOutlined
                        class="view-icon"
                        v-if="productPropertiesIcon[item.icon]"
                        @click="iconPreview(productPropertiesIcon[item.icon])"
                      />
                    </a-tooltip>
                    <a-tooltip placement="top" :title="item.description">
                      <QuestionCircleOutlined />
                    </a-tooltip>
                  </div>
                </div>
              </div>
              <div class="value">
                <a-tooltip placement="topLeft" :title="`${item.propertyValue} ${item.unit}`">{{
                  `${item.propertyValue} ${item.unit}`
                }}</a-tooltip>
              </div>
              <!-- <div class="time">上报时间：{{ item.eventTime }}</div> -->
              <!-- <div class="time">更新时间：{{ item.ts }}</div> -->
              <div class="time">{{ t('common.updatedTime') }}：{{ item.createdTime }}</div>
            </div>
          </template>
          <template #actions>
            <a-tooltip placement="top" :title="t('common.redo')">
              <RedoOutlined key="refresh" @click="refresh" />
            </a-tooltip>
            <a-tooltip placement="top" :title="t('common.title.details')">
              <UnorderedListOutlined key="detail" @click="openDetailDrawer(item)" />
            </a-tooltip>
          </template>
        </a-card>
      </div>
    </div>
    <!-- </a-col> -->
    <!-- </a-row> -->
    <runningDetail @register="runningDetailRegister" />
  </div>
</template>
<script lang="ts">
  // util
  import { defineComponent, ref, toRefs, reactive, onMounted } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useUserStore } from '/@/store/modules/user';
  // components
  import {
    EyeOutlined,
    RedoOutlined,
    UnorderedListOutlined,
    QuestionCircleOutlined,
    LinkOutlined,
    DisconnectOutlined,
  } from '@ant-design/icons-vue';
  import { Card } from 'ant-design-vue';
  import runningDetail from './components/runningDetail.vue';
  import { useDrawer } from '/@/components/Drawer';
  // api
  import { queryDeviceShadow } from '/@/api/iot/link/deviceShadow/deviceShadow';
  import { findUrlById } from '/@/api/thinglinks/file/upload.ts';
  import { query } from '/@/api/iot/link/productService/productService';

  import { useDict } from '/@/components/Dict';
  import { useWebSocket } from '@vueuse/core';
  import { isEmpty } from 'lodash-es';
  import { createImgPreview } from '/@/components/Preview/index';
  const { getDictLabel } = useDict();
  export default defineComponent({
    name: 'Running',
    components: {
      ACard: Card,
      RedoOutlined,
      UnorderedListOutlined,
      QuestionCircleOutlined,
      LinkOutlined,
      DisconnectOutlined,
      EyeOutlined,
      runningDetail,
    },
    props: {
      deviceIdentification: {
        type: String,
        default: '',
      },
      productResultVO: {
        type: Object,
        default: () => {},
      },
    },
    setup(props) {
      // 是否显示密码明文
      const { t } = useI18n();
      const { notification } = useMessage();
      const currentServiceIndex = ref(0);
      const [runningDetailRegister, { openDrawer: openDetail }] = useDrawer();

      const state = reactive({
        deviceIdentification: props.deviceIdentification,
        services: [],
        serviceCode: '',
        properties: [],
        productResultVO: props.productResultVO,
      });

      // 使用websocket长连获取相关影子数据
      const socketIsSuccess = ref(false);
      const protocol = window.location.protocol;
      const host = window.location.host;
      const userStore = useUserStore();
      const tenantId = userStore.getTenantId;
      const wsState = reactive({
        server: `${
          protocol.includes('https') ? 'wss' : 'ws'
        }://${host}/api/wsLink/anyTenant/deviceOpenSocket/queryDeviceShadow/${tenantId}/${
          state.deviceIdentification
        }`,
        // }://test-gateway.thinglinks.mqttsnet.com/api/wsLink/anyTenant/deviceOpenSocket/queryDeviceShadow/${tenantId}/4061840969121792`,
        sendValue: '',
        recordList: [] as { id: number; time: number; res: string }[],
      });

      // 当前产品属性icon信息
      // key为各服务icon，value为对应临时图片地址
      const productPropertiesIcon = ref({});
      // 上次图片icon信息，主要用于初始化以及新旧数据对比
      const iconIdInfo = ref([]);
      // 根据icon id获取相关图片信息
      const getProductPropertiesIconInfo = async (data) => {
        try {
          const res = await findUrlById(Object.values(data));
          if (res) {
            productPropertiesIcon.value = {
              ...productPropertiesIcon.value,
              ...res,
            };
          }
        } catch (err) {
          console.log(`获取影子相关icon信息失败,设备标识：${state.deviceIdentification}`, err);
        }
      };

      const setDeviceShadowIconInfo = (res) => {
        if (res && res.services?.length) {
          state.properties = res.services[0].properties;
          let currentProductPropertiesIconInfo =
            res.services
              .map((item) => (item.properties?.length ? item.properties : []))
              ?.flat(1) || [];
          const currentIconIds = currentProductPropertiesIconInfo.map((i) => i.icon);

          if (isEmpty(iconIdInfo.value)) {
            iconIdInfo.value = currentIconIds;
            getProductPropertiesIconInfo(iconIdInfo.value?.filter((i) => i));
          } else {
            const newIconIdInfo = currentIconIds.filter(
              (item) => !iconIdInfo.value?.includes(item),
            );
            if (newIconIdInfo.length) {
              iconIdInfo.value = currentIconIds;
              getProductPropertiesIconInfo(newIconIdInfo);
            }
          }
        }
      };

      function onMessage(_: WebSocket, event: MessageEvent) {
        const jsonStr = event.data;
        if (!jsonStr) {
          return;
        }
        const jsonResult = JSON.parse(jsonStr);
        if (jsonResult) {
          socketIsSuccess.value = true;
          setDeviceShadowIconInfo(jsonResult);
        }
      }

      let wsInstance = ref(null);

      const handleInitWs = () => {
        if (wsInstance.value) {
          wsInstance.value.close();
        }
        wsInstance.value = useWebSocket(wsState.server, {
          autoReconnect: {
            retries: 3,
            delay: 10000,
            onFailed() {
              socketIsSuccess.value = false;
              console.log('Failed to connect WebSocket after 3 retires');
            },
          },
          heartbeat: {
            message: state.serviceCode,
            interval: 5000,
          },
          onMessage: onMessage,
        });
        wsInstance.value.send(state.serviceCode);
      };

      const load = async () => {
        const res = await queryDeviceShadow({
          deviceIdentification: state.deviceIdentification,
          serviceCode: state.serviceCode,
        });

        setDeviceShadowIconInfo(res);
      };
      const changeService = (index) => {
        currentServiceIndex.value = index;
        state.serviceCode = state.services[index].serviceCode;
        handleInitWs();
        load();
      };
      const refresh = async () => {
        await load();
        notification.success({
          message: t('iot.link.device.device.refreshSuccess'),
        });
      };

      const iconPreview = (url) => {
        createImgPreview({ imageList: [url], defaultWidth: 700, rememberState: true });
      };

      const getProductServiceList = async () => {
        const { id } = state.productResultVO;
        const res = await query({ productId: id });
        if (res.length) {
          state.services = res;
          if (state.services[0]?.['serviceCode']) {
            state.serviceCode = state.services[0]['serviceCode'];
            handleInitWs();
            load();
          }
        }
      };

      onMounted(() => {
        getProductServiceList();
      });

      const openDetailDrawer = (item) => {
        openDetail(true, {
          ...item,
          deviceIdentification: state.deviceIdentification,
          serviceCode: state.services[currentServiceIndex.value]['serviceCode'],
        });
      };

      return {
        t,
        getDictLabel,
        changeService,
        refresh,
        ...toRefs(state),
        currentServiceIndex,
        socketIsSuccess,
        productPropertiesIcon,
        iconPreview,
        openDetailDrawer,
        runningDetailRegister,
        wsInstance,
      };
    },
  });
</script>
<style lang="less" scope>
  .service-box {
    height: calc(100vh - 200px);
    display: flex;

    .service-left {
      width: 200px;
      height: 100%;
      border-right: 2px solid #d9d9d9;
      padding-right: 14px;
      overflow-y: auto;

      .title-btns {
        display: flex;
        justify-content: space-between;
        margin-bottom: 16px;

        .title {
          font-size: 14px;
          font-weight: 600;
          color: #2e3033;
        }

        .btns {
          .ant-btn {
            transform: scale(0.85);

            & + .ant-btn {
              margin-left: 4px;
            }
          }
        }
      }

      .list {
        height: 670px;
        overflow: hidden;
        overflow-y: auto;

        .item {
          border-radius: 6px;
          border: 1px solid #f0f2f5;
          padding: 12px;

          &.active {
            background-color: #f0f2f5;

            .name {
              color: #1a66ff;
            }
          }

          & + .item {
            margin-top: 12px;
          }

          .name {
            font-size: 14px;
            font-weight: bold;
            color: #2e3033;
            line-height: 18px;
            margin-bottom: 4px;
          }

          .type {
            font-size: 12px;
            font-weight: 400;
            color: #2e3033;
            line-height: 14px;
          }

          .desc {
            font-size: 10px;
            font-weight: 400;
            color: #707070;
            line-height: 16px;
            margin-top: 8px;
          }
        }
      }
    }

    .service-right {
      width: calc(100% - 200px);
      height: 100%;
      padding-left: 24px;
      display: flex;
      flex-wrap: wrap;
      align-content: flex-start;
      overflow-y: auto;

      .properties {
        margin: 0 12px 12px 0;
      }

      .content {
        padding: 20px;
        padding-bottom: 10px;

        .title {
          display: flex;
          align-items: center;
          justify-content: space-between;

          .right-btn {
            display: flex;
            align-items: center;

            .view-icon {
              display: inline-block;
              margin-right: 10px;
              font-size: 16px;
            }
          }
        }

        .value {
          padding: 20px;
          font-size: 20px;
          font-weight: bold;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        .time {
          color: #666;
        }
      }
    }
  }
</style>
