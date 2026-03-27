<template>
  <div class="basic-info-container">
    <div class="info-content">
      <div class="info-left">
        <a-descriptions :column="3" bordered>
          <a-descriptions-item :label="t('iot.link.product.product.productName')">
            {{ productDetail.productName }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.productIdentification')">
            {{ productDetail.productIdentification }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.productStatus')">
            <Tag color="green" v-if="productDetail?.productStatus == 0">
              {{ getDictLabel('LINK_PRODUCT_STATUS', productDetail?.productStatus, '') }}
            </Tag>
            <Tag color="red" v-else-if="productDetail?.productStatus == 1">
              {{ getDictLabel('LINK_PRODUCT_STATUS', productDetail?.productStatus, '') }}
            </Tag>
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.appId')">
            {{ productDetail.appId }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.productType')">
            {{ getDictLabel('LINK_PRODUCT_TYPE', productDetail?.productType, '') }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.manufacturerName')">
            {{ productDetail.manufacturerName || '-' }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.manufacturerId')">
            {{ productDetail.manufacturerId || '-' }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.model')">
            {{ productDetail.model || '-' }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.productVersion')">
            {{ productDetail.productVersion || '-' }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.deviceType')">
            {{ productDetail.deviceType || '-' }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.dataFormat')">
            {{ productDetail.dataFormat || '-' }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.protocolType')">
            {{ productDetail.protocolType || '-' }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.remark')" :span="3">
            {{ productDetail.remark || '-' }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.createdTime')">
            {{ productDetail.createdTime || '-' }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.createdBy')">
            {{ productDetail.createdBy || '-' }}
          </a-descriptions-item>
          <a-descriptions-item :label="t('iot.link.product.product.updatedTime')">
            {{ productDetail.updatedTime || '-' }}
          </a-descriptions-item>
        </a-descriptions>
      </div>
      <div class="info-right" v-if="productDetail?.icon">
        <ImageDisplay
          :title="t('iot.link.product.product.productImage')"
          :fileId="productDetail.icon"
          :imageStyle="{ 'max-width': '200px', 'max-height': '200px' }"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { Descriptions, Tag } from 'ant-design-vue';
import ImageDisplay from '/@/components/ImageDisplay/src/ImageDisplay.vue';
import { useDict } from '/@/components/Dict';
import type { ProductResultVO } from '/@/api/iot/link/product/model/productModel';

const { getDictLabel } = useDict();

export default defineComponent({
  name: 'ProductBasicInfo',
  components: {
    [Descriptions.name]: Descriptions,
    [Descriptions.Item.name]: Descriptions.Item,
    ImageDisplay,
    Tag,
  },
  props: {
    productDetail: {
      type: Object as PropType<ProductResultVO>,
      required: true,
    },
  },
  setup() {
    const { t } = useI18n();

    return {
      t,
      getDictLabel,
    };
  },
});
</script>

<style lang="less" scoped>
.basic-info-container {
  .info-content {
    display: flex;
    gap: 24px;

    .info-left {
      flex: 1;
      min-width: 0;
    }

    .info-right {
      flex-shrink: 0;
      width: 240px;
    }
  }

  :deep(.ant-descriptions-item-label) {
    font-weight: 600;
    background: #fafafa;
  }
}
</style>