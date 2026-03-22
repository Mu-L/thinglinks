<template>
  <PageWrapper contentFullHeight>
    <div class="detail-info">
      <a-card title="" :bordered="false">
        <div class="product-header">
          <div class="product-icon" v-if="productDetail?.icon">
            <ThumbUrl :fileId="productDetail.icon" :imageStyle="{ 'max-width': '80px', 'max-height': '80px' }" />
          </div>
          <div class="product-info" :style="{ flex: productDetail?.icon ? '1' : 'auto' }">
            <div class="device_title">
              <span>{{ productDetail.productName }}</span>
              <a-button type="primary" danger @click="handleEdit">
                <template #icon> <EditOutlined /> </template
                >{{ t('iot.link.product.product.updateProductButton') }}
              </a-button>
            </div>
            <div class="base_data">
              <div class="item">
                <span>{{ t('iot.link.product.product.productStatus') }}：</span>
                <Tag color="green" v-if="productDetail?.productStatus == 0">
                  {{ getDictLabel('LINK_PRODUCT_STATUS', productDetail?.productStatus, '') }}
                </Tag>
                <Tag color="red" v-else-if="productDetail?.productStatus == 1">
                  {{ getDictLabel('LINK_PRODUCT_STATUS', productDetail?.productStatus, '') }}
                </Tag>
              </div>
              <div class="item">
                <span>{{ t('iot.link.product.product.appId') }}：</span>
                <span>{{ productDetail.appId }}</span>
              </div>
              <div class="item">
                <span>{{ t('iot.link.product.product.productIdentification') }}：</span>
                <span>{{ productDetail.productIdentification }}</span>
              </div>
              <div class="item" v-if="productDetail.manufacturerName">
                <span>{{ t('iot.link.product.product.manufacturerName') }}：</span>
                <span>{{ productDetail.manufacturerName }}</span>
              </div>
              <div class="item" v-if="productDetail.model">
                <span>{{ t('iot.link.product.product.model') }}：</span>
                <span>{{ productDetail.model }}</span>
              </div>
            </div>
          </div>
        </div>
      </a-card>
    </div>
    <div class="detail-info" v-if="productDetail?.id">
      <a-card title="" :bordered="false">
        <a-tabs default-active-key="1" v-model:activeKey="currentKey">
          <a-tab-pane v-for="item in cardTabList" :tab="item.name" :key="item.key" />
        </a-tabs>
        <basicInfo v-if="currentKey == '0'" :productDetail="productDetail" />
        <modelDefinition v-else-if="currentKey == '1'" :id="productDetail?.id" />
        <topicList v-else-if="currentKey == '2'" :id="productDetail?.productIdentification" />
        <productEmpower v-else-if="currentKey == '3'" :id="productDetail?.productIdentification" />
        <deviceDebug v-else-if="currentKey == '4'" :productIdentification="productDetail.productIdentification" />
      </a-card>
    </div>
    <EditModal @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, ref, reactive, onMounted, computed } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { detail } from '/@/api/iot/link/product/product';
  import { copyTextToClipboard } from '/@/hooks/web/useCopyToClipboard';
  import { PageWrapper } from '/@/components/Page';
  import { useRouter } from 'vue-router';
  import { Card, Descriptions, Tabs, Button } from 'ant-design-vue';
  import { EditOutlined } from '@ant-design/icons-vue';
  import EditModal from '../product/Edit.vue';
  import { useModal } from '/@/components/Modal';
  import { ActionEnum } from '/@/enums/commonEnum';
  import tsImg from '/@/assets/images/iot/link/device/ts.jpg';
  import { usePermission } from '/@/hooks/web/usePermission';
  import { useDict } from '/@/components/Dict';
  import basicInfo from './basicInfo.vue';
  import modelDefinition from './modelDefinition.vue';
  import topicList from './topicList.vue';
  import productEmpower from './productEmpower.vue';
  import deviceDebug from './deviceDebug.vue';
  import ThumbUrl from '/@/components/Upload/src/ThumbUrl.vue';
  import type { ProductResultVO } from '/@/api/iot/link/product/model/productModel';
  const { getDictLabel } = useDict();
  export default defineComponent({
    name: 'ProductDetail',
    components: {
      ACard: Card,
      [Descriptions.name]: Descriptions,
      [Descriptions.Item.name]: Descriptions.Item,
      [Tabs.name]: Tabs,
      [Tabs.TabPane.name]: Tabs.TabPane,
      PageWrapper,
      productEmpower,
      deviceDebug,
      topicList,
      modelDefinition,
      basicInfo,
      AButton: Button,
      EditOutlined,
      EditModal,
      ThumbUrl,
    },
    emits: ['success', 'register'],
    setup() {
      // 是否显示密码明文
      const { t } = useI18n();
      const { createMessage } = useMessage();
      const { currentRoute } = useRouter();
      const [registerModal, { openModal }] = useModal();
      const { isPermission } = usePermission();
      let productDetail = reactive<ProductResultVO>({} as ProductResultVO);
      let id = ref('');
      onMounted(() => {
        const { params } = currentRoute.value;
        id.value = params.id as string;
        load();
        if (cardTabList.value?.length) {
          currentKey.value = cardTabList.value?.[0].key;
        } else {
          currentKey.value = null;
        }
      });
      const load = async () => {
        const res = await detail(id.value);

        productDetail = Object.assign(productDetail, res);
      };
      let currentKey = ref('0');
      function copyFn(text) {
        let result = copyTextToClipboard(text);
        console.log(result, 'result');
        if (result) {
          createMessage.success(t('common.tips.copySuccess'));
        } else {
          createMessage.warning(t('common.tips.copyFail'));
        }
      }

      // 弹出编辑页面
      function handleEdit(e: Event) {
        e?.stopPropagation();
        openModal(true, {
          record: productDetail,
          type: ActionEnum.EDIT,
        });
      }

      // 新增或编辑成功回调
      function handleSuccess() {
        load();
      }
      const cardTabList = computed(() => {
        const list = [
          {
            name: t('iot.link.product.product.tab0'),
            key: '0',
            isShowAuth: isPermission(['link:product:detail:basicInfo']),
            component: basicInfo,
          },
          {
            name: t('iot.link.product.product.tab1'),
            key: '1',
            isShowAuth: isPermission(['link:product:detail:modelDefinition']),
            component: modelDefinition,
          },
          {
            name: t('iot.link.product.product.tab2'),
            key: '2',
            isShowAuth: isPermission(['link:product:detail:topic']),
            component: topicList,
          },
          {
            name: t('iot.link.product.product.tab3'),
            key: '3',
            isShowAuth: isPermission(['link:product:detail:empowerment']),
            component: productEmpower,
          },
          {
            name: t('iot.link.product.product.tab4'),
            key: '4',
            isShowAuth: isPermission(['link:product:detail:debug']),
            component: deviceDebug,
          },
        ];
        return list.filter((i) => i.isShowAuth);
      });

      return {
        t,
        copyFn,
        productDetail,
        currentKey,
        getDictLabel,
        labelStyle: {
          width: '120px',
          'font-weight': '600',
          'font-size': '14px',
        },
        contentStyle: {
          'font-weight': '600',
          'font-size': '14px',
        },
        tsImg,
        handleSuccess,
        handleEdit,
        registerModal,
        cardTabList,
      };
    },
  });
</script>
<style lang="less" scope>
  .detail-info {
    padding: 16px 16px 0;

    &:nth-last-child(1) {
      padding-bottom: 16px;
    }

    .product-header {
      display: flex;
      align-items: flex-start;

      .product-icon {
        flex-shrink: 0;
        margin-right: 20px;
        padding: 8px;
        border: 1px solid #e8e8e8;
        border-radius: 4px;
        background: #fafafa;
      }

      .product-info {
        flex: 1;
        min-width: 0;
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