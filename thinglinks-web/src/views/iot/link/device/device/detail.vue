<template>
  <PageWrapper ref="pageWrapper">
    <div class="content-wrap" ref="contentDom">
      <div class="detail-info">
        <a-card title="" :bordered="false">
          <div class="device-header">
            <div class="product-image-section" v-if="deviceDetail.productResultVO?.icon">
              <ImageDisplay
                :fileId="deviceDetail.productResultVO.icon"
                :imageWidth="120"
                :imageHeight="120"
                :imageStyle="{ 'max-width': '120px', 'max-height': '120px', 'object-fit': 'contain' }"
                :showBorder="true"
                :preview="true"
              />
            </div>
            <div class="device-info-section" :class="{ 'full-width': !deviceDetail.productResultVO?.icon }">
              <div class="device_title">
                <div>
                  <div class="device_title_content">
                    <span>{{ deviceDetail.deviceName }}</span>
                    <renderQrcode
                      :deviceIdentification="deviceDetail?.deviceIdentification || ''"
                      :deviceName="deviceDetail?.deviceName || '未知设备'"
                    />
                  </div>
                  <span v-if="deviceDetail.nodeType === DeviceNodeType.SUB_DEVICE && Boolean(deviceDetail.gatewayId)">
                    <span class="gateWay"
                      >{{ t('iot.link.device.device.affiliatedGateway') }}：<span ref="textToCopy">{{
                        deviceDetail.gatewayId
                      }}</span>
                    </span>
                    <Tooltip placement="top" :title="t('common.title.copy')">
                      <span class="copy_btn" @click="handleCopyText"
                        ><SvgIcon name="copy" :size="12" /></span
                    ></Tooltip>
                  </span>
                </div>
                <div>
                  <a-button
                    type="primary"
                    danger
                    style="margin-right: 10px"
                    :disabled="deviceDetail?.connectStatus !== DeviceConnectStatus.ONLINE"
                    v-if="deviceDetail.nodeType !== DeviceNodeType.SUB_DEVICE"
                    @click="handleDisconnect"
                  >
                    <template #icon><DisconnectOutlined /></template>
                    {{ t('iot.link.device.device.disconnect') }}
                  </a-button>
                  <a-button type="primary" danger @click="handleEdit">
                    <template #icon><EditOutlined /></template
                    >{{ t('iot.link.device.device.updateDeviceButton') }}
                  </a-button></div
                >
              </div>
              <div class="base_data">
                <div class="item">
                  <span>{{ t('iot.link.device.device.status') }}：</span>
                  <template v-if="deviceDetail?.connectStatus !== undefined">
                    <span :class="getConnectStatusOption(deviceDetail.connectStatus)?.cssClass">
                      {{ getDictLabel('LINK_DEVICE_CONNECT_STATUS', deviceDetail.connectStatus) }}
                    </span>
                  </template>
                </div>
                <div class="item">
                  <span>{{ t('iot.link.device.device.appId') }}:</span>
                  <span>{{ deviceDetail.appId }}</span>
                </div>
                <div class="item">
                  <span>{{ t('iot.link.product.product.productName') }}：</span>
                  <span
                    class="product-link"
                    @click="handleGoToProductDetail"
                    v-if="deviceDetail.productResultVO?.id"
                  >
                    {{ deviceDetail.productResultVO?.productName }}
                  </span>
                  <span v-else>{{ deviceDetail.productResultVO?.productName }}</span>
                </div>
                <div class="item">
                  <span>{{ t('iot.link.device.device.connector') }}：</span>
                  <span>{{ deviceDetail.connector }}</span>
                </div>
                <div class="item">
                  <span>{{ t('iot.link.device.device.clientId') }}：</span>
                  <span>{{ deviceDetail.clientId }}</span>
                </div>
              </div>
            </div>
          </div>
        </a-card>
      </div>
      <CopyModal @register="registerCopyModal" />
      <div class="detail-info" v-if="deviceDetail.deviceIdentification && cardTabList.length">
        <a-card title="" :bordered="false">
          <a-tabs default-active-key="1" v-model:activeKey="currentKey">
            <a-tab-pane v-for="item in cardTabList" :tab="item.name" :key="item.key" />
          </a-tabs>
          <basicInfo v-if="currentKey == '0'" :deviceDetail="deviceDetail" />
          <location
            @success="handleSuccess"
            v-hasAnyPermission="['link:device:device:detail:location']"
            v-else-if="currentKey == '1'"
            :deviceIdentification="deviceDetail.deviceIdentification"
            :deviceLocationResultVO="deviceDetail.deviceLocationResultVO"
          />
          <topic
            v-else-if="currentKey == '2'"
            :nodeType="deviceDetail?.nodeType"
            :deviceIdentification="
              deviceDetail?.nodeType === DeviceNodeType.SUB_DEVICE
                ? deviceDetail.gatewayId
                : deviceDetail.deviceIdentification
            "
            :deviceSdkVersion="deviceDetail.deviceSdkVersion"
            :productIdentification="deviceDetail.productIdentification"
            :appId="deviceDetail.appId"
            :userName="deviceDetail.userName"
          />
          <action
            v-else-if="currentKey == '3'"
            :deviceIdentification="deviceDetail.deviceIdentification"
          />
          <running
            ref="runningRef"
            v-else-if="currentKey == '4'"
            :deviceIdentification="deviceDetail.deviceIdentification"
            :productResultVO="deviceDetail.productResultVO"
          />
          <command
            v-else-if="currentKey == '5'"
            :deviceIdentification="deviceDetail.deviceIdentification"
          />
          <subDevice
            v-else-if="currentKey == '6'"
            :deviceIdentification="deviceDetail.deviceIdentification"
          />
          <deviceGroup
            v-else-if="currentKey == '7'"
            :deviceIdentification="deviceDetail.deviceIdentification"
          />
        </a-card>
      </div>
      <EditModal @register="registerModal" @success="handleSuccess" />
    </div>
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, ref, reactive, onMounted, watch, nextTick, computed } from 'vue';
  import { useRouter } from 'vue-router';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useTabs } from '/@/hooks/web/useTabs';
  import { detail, disconnect } from '/@/api/iot/link/device/device';
  import { copyTextToClipboard } from '/@/hooks/web/useCopyToClipboard';
  import { PageWrapper } from '/@/components/Page';
  import { Card, Descriptions, Tabs, Button, Tooltip } from 'ant-design-vue';
  import { Description, useDescription } from '/@/components/Description/index';
  import {
    EyeOutlined,
    EyeInvisibleOutlined,
    EditOutlined,
    DisconnectOutlined,
  } from '@ant-design/icons-vue';
  import action from '../detail/deviceAction.vue';
  import topic from '../detail/deviceTopic.vue';
  import running from '../running/index.vue';
  import location from '../detail/deviceLocation.vue';
  import command from '../detail/deviceCommand.vue';
  import subDevice from '../detail/subDeviceList.vue';
  import deviceGroup from '../detail/deviceGroupList.vue';
  import basicInfo from '../detail/basicInfo.vue';
  import { useDict } from '/@/components/Dict';
  import EditModal from './Edit.vue';
  import { useModal } from '/@/components/Modal';
  import CopyModal from '/@/components/CopyModal/index.vue';
  import ImageDisplay from '/@/components/ImageDisplay/index.ts';
  const { getDictLabel } = useDict();
  import { ActionEnum } from '/@/enums/commonEnum';
  import {
    DeviceConnectStatus,
    DeviceNodeType,
    DeviceAuthMode,
    getConnectStatusOption,
  } from '/@/enums/link/device';
  import type { DevicePageQuery } from '/@/api/iot/link/device/model/deviceModel';
  import SvgIcon from '/@/components/Icon/src/SvgIcon.vue';
  import renderQrcode from '../qrcode/index.vue';
  import { handleCopyTextV2 } from '/@/utils/thinglinks/common.tsx';
  import { usePermission } from '/@/hooks/web/usePermission';
  import { detailDeviceSchema } from './device.data';

  export default defineComponent({
    name: '设备详情',
    components: {
      ACard: Card,
      [Descriptions.name]: Descriptions,
      [Descriptions.Item.name]: Descriptions.Item,
      [Tabs.name]: Tabs,
      [Tabs.TabPane.name]: Tabs.TabPane,
      EyeOutlined,
      EyeInvisibleOutlined,
      PageWrapper,
      topic,
      action,
      command,
      subDevice,
      deviceGroup,
      EditModal,
      AButton: Button,
      EditOutlined,
      DisconnectOutlined,
      location,
      running,
      SvgIcon,
      Tooltip,
      renderQrcode,
      Description,
      basicInfo,
      CopyModal,
      ImageDisplay,
    },
    emits: ['success', 'register'],
    setup() {
      // 是否显示密码明文
      const isShow = ref(false);
      const textToCopy = ref(null);
      const infoShow = reactive({
        encryptKeyShow: false,
        encryptVectorShow: false,
        signKeyShow: false,
      });
      const { t } = useI18n();
      const { createMessage, createConfirm, notification } = useMessage();
      const router = useRouter();
      const { currentRoute } = router;
      const { isPermission } = usePermission();
      const { setTitle } = useTabs();

      let deviceDetail = reactive<DevicePageQuery>({});
      const [registerModal, { openModal }] = useModal();
      const [registerCopyModal, { openModal: openCopyModal }] = useModal();
      const [registerViewValueModal, { openModal: openViewValueModal }] = useModal();
      let id = ref('');
      let currentKey = ref('0');

      const runningRef = ref(null);
      watch(currentKey, (val) => {
        if (val !== '4') {
          if (runningRef.value?.wsInstance) {
            runningRef.value.wsInstance.close();
          }
        }
      });

      onMounted(() => {
        const { params } = currentRoute.value;
        id.value = params.id as string;
        load();
        setContentDomHeight();

        if (cardTabList.value?.length) {
          if (cardTabList.value.every((i) => i.key !== '0')) {
            currentKey.value = cardTabList.value?.[0].key;
          }
        } else {
          currentKey.value = null;
        }
      });
      const load = async () => {
        const res = await detail(id.value);
        deviceDetail = Object.assign(deviceDetail, res);
        // setTitle(`${t('iot.link.device.device.deviceName')}-${deviceDetail.deviceName}`);
        setTitle(deviceDetail.deviceName);
        await nextTick();
      };
      function copyFn(text) {
        let result = copyTextToClipboard(text);
        if (result) {
          createMessage.success(t('common.tips.copySuccess'));
        } else {
          createMessage.warning(t('common.tips.copyFail'));
        }
      }

      function changeShow() {
        isShow.value = !isShow.value;
      }

      // 弹出编辑页面
      function handleEdit(e: Event) {
        e?.stopPropagation();
        openModal(true, {
          record: deviceDetail,
          type: ActionEnum.EDIT,
        });
      }

      // 断开连接
      function handleDisconnect(e: Event) {
        e?.stopPropagation();
        createConfirm({
          iconType: 'warning',
          content: '是否确定断开连接？',
          onOk: async () => {
            try {
              await disconnect(deviceDetail.deviceIdentification || '');
              notification.success({
                message: t('common.tips.tips'),
                description: '断开连接成功！',
              });
              load();
            } catch (e) {}
          },
        });
      }

      // 新增或编辑成功回调
      function handleSuccess() {
        deviceDetail.deviceIdentification = '';
        load();
      }
      const handleCopyText = async () => {
        const text = (textToCopy.value as any).innerText;
        handleCopyTextV2(text || '');
      };

      // 跳转到产品详情页
      const handleGoToProductDetail = () => {
        if (!deviceDetail.productResultVO?.id) return;
        router.replace({
          name: '产品详情',
          params: { id: deviceDetail.productResultVO.id },
        });
      };

      const changeInfoShow = (key: string) => {
        infoShow[key] = !infoShow[key];
      };

      // 优化contentDom高度，解决Drawer高度问题
      const pageWrapper = ref(null);
      const contentDom = ref(null);
      const setContentDomHeight = () => {
        const viewportHeight = window.innerHeight;
        const pageWrapperDomInfo = pageWrapper.value.contentRef.getBoundingClientRect();
        contentDom.value.style.height = `${viewportHeight - pageWrapperDomInfo.top}px`;
      };

      const cardTabList = computed(() => {
        const list = [
          {
            name: t('iot.link.device.device.tabs[0]'),
            key: '0',
            isShowAuth: isPermission(['link:device:device:detail:basicInfo']),
            component: basicInfo,
          },
          {
            name: t('iot.link.device.device.tabs[1]'),
            key: '1',
            isShowAuth: isPermission(['link:device:device:detail:location']),
            component: location,
          },
          {
            name: t('iot.link.device.device.tabs[2]'),
            key: '2',
            isShowAuth: isPermission(['link:device:device:detail:topic']),
            component: topic,
          },
          {
            name: t('iot.link.device.device.tabs[3]'),
            key: '3',
            isShowAuth: isPermission(['link:device:device:detail:action']),
            component: action,
          },
          {
            name: t('iot.link.device.device.tabs[4]'),
            key: '4',
            isShowAuth: isPermission(['link:device:device:detail:shadow']),
            component: running,
          },
          {
            name: t('iot.link.device.device.tabs[5]'),
            key: '5',
            isShowAuth: isPermission(['link:device:device:detail:command']),
            component: command,
          },
          {
            name: t('iot.link.device.device.tabs[6]'),
            key: '6',
            isShowAuth:
              isPermission(['link:device:device:detail:subdevice']) && deviceDetail.nodeType === DeviceNodeType.GATEWAY,
            component: subDevice,
          },
          {
            name: t('iot.link.device.device.tabs[7]'),
            key: '7',
            isShowAuth: isPermission(['link:device:device:detail:shadow']),
            component: deviceGroup,
          },
        ];
        return list.filter((i) => i.isShowAuth);
      });

      const baseInfoSchema = computed(() => detailDeviceSchema());
      const [registerBaseInfo] = useDescription({
        title: t('iot.link.device.device.basicInformation'),
        column: 3,
        data: deviceDetail,
        schema: baseInfoSchema.value,
      });

      return {
        t,
        copyFn,
        deviceDetail,
        currentKey,
        isShow,
        changeShow,
        getDictLabel,
        registerModal,
        handleEdit,
        handleSuccess,
        handleCopyText,
        textToCopy,
        infoShow,
        changeInfoShow,
        pageWrapper,
        contentDom,
        isPermission,
        cardTabList,
        handleDisconnect,
        runningRef,
        registerBaseInfo,
        handleGoToProductDetail,
        registerCopyModal,
        // 枚举值
        DeviceConnectStatus,
        DeviceNodeType,
        DeviceAuthMode,
        getConnectStatusOption,
      };
    },
  });
</script>
<style lang="less" scope>
  .content-wrap {
    overflow-y: auto;
  }

  .detail-info {
    & + .detail-info {
      margin-top: 16px;
    }

    .device-header {
      display: flex;
      gap: 24px;
      align-items: flex-start;
    }

    .product-image-section {
      flex-shrink: 0;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .device-info-section {
      flex: 1;
      min-width: 0;

      &.full-width {
        width: 100%;
      }
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

      &_content {
        display: flex;
        align-items: center;
        gap: 12px;
      }

      .gateWay {
        font-weight: 400;
        color: #999;
        font-size: 12px;
        margin-left: 12px;
      }

      .copy_btn {
        margin-left: 4px;
      }
    }

    .base_data {
      display: flex;
      align-items: center;
      flex-wrap: wrap;
      font-size: 12px;
      color: #a6a6a6;
      line-height: 17px;

      .item {
        padding-right: 12px;
        margin-bottom: 8px;

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

          &.product-link {
            color: #1890ff;
            cursor: pointer;
            text-decoration: underline;
            transition: color 0.3s;

            &:hover {
              color: #40a9ff;
            }
          }
        }
      }
    }
  }

  .ant-descriptions-item-label,
  .ant-descriptions-item-content {
    padding: 16px 24px !important;
  }

  .ant-descriptions-item-content {
    font-size: 14px;
    font-weight: 600;
  }
</style>
