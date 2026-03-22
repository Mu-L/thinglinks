<template>
  <div class="product-selector">
    <a-row :gutter="[24, 24]">
      <a-col :span="showSelectedProduct ? 6 : 0" class="heightwrapper" v-if="showSelectedProduct">
        <SelectedProduct :selectedProduct="selectedProduct" @deleteProduct="deleteProduct" />
      </a-col>
      <a-col :span="showSelectedProduct ? 18 : 24" class="heightwrapper">
        <ProductCardList
          :isSelect="true"
          @selectProductCard="selectProductCard"
          :productIdentification="selectedProduct.productIdentification"
          v-bind="cardListProps"
        />
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts">
  import { defineComponent, reactive, toRefs, watch, PropType } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { Row, Col } from 'ant-design-vue';
  import ProductCardList from '/@/components/Table/src/types/components/iot/link/product/ProductCardList.vue';
  import SelectedProduct from './SelectedProduct.vue';
  import { getFullProductInfo } from '/@/api/iot/link/product/product';
  import type { ProductInfo } from '../../types';

  export default defineComponent({
    name: 'BasicProductSelector',
    components: { ProductCardList, SelectedProduct, ARow: Row, ACol: Col },
    props: {
      modelValue: { type: Object as PropType<ProductInfo>, default: () => ({}) },
      showSelectedProduct: { type: Boolean, default: true },
      cardListProps: { type: Object, default: () => ({}) },
      multiple: { type: Boolean, default: false },
    },
    emits: ['update:modelValue', 'change', 'validate'],
    setup(props, { emit, expose }) {
      const { t } = useI18n();
      const { notification } = useMessage();

      const state = reactive({ selectedProduct: { ...props.modelValue } as ProductInfo });

      watch(
        () => props.modelValue,
        (val) => (state.selectedProduct = { ...val }),
        { deep: true, immediate: true },
      );

      watch(
        () => state.selectedProduct,
        (val) => {
          emit('update:modelValue', val);
          emit('change', val);
        },
        { deep: true },
      );

      const selectProductCard = (product: any) => {
        if (state.selectedProduct.productIdentification === product.productIdentification) {
          notification.warn({
            message: t('common.tips.tips'),
            description: t('iot.link.productCommand.productCommand.description4'),
          });
          return;
        }
        state.selectedProduct = { ...product };
      };

      const deleteProduct = () => (state.selectedProduct = {} as any);

      const getProductDetail = async (productIdentification: string) => {
        const res = await getFullProductInfo(productIdentification);
        state.selectedProduct = res;
      };

      const validate = () => {
        if (!state.selectedProduct.productIdentification) {
          notification.warn({
            message: t('common.tips.tips'),
            description: t('iot.link.productCommand.productCommand.description5'),
          });
          return false;
        }
        return true;
      };

      expose({ validate, getProductDetail });

      return { t, selectProductCard, deleteProduct, getProductDetail, validate, ...toRefs(state) };
    },
  });
</script>

<style lang="less" scoped>
  .heightwrapper {
    height: 100%;
  }
</style>
