<template>
  <PageWrapper dense contentFullHeight>
    <div class="operate">
      <div class="tabs">
        <div
          v-for="item in topicTypeList"
          :key="item.id"
          :class="activeTab == item.key ? 'active tab' : 'tab'"
          @click="activeTab = item.key"
          >{{ item.name }}</div
        >
      </div>
      <div class="table-content">
        <BasicTopic
          v-if="activeTab == 0"
          :topicType="activeTab"
          :productIdentification="productIdentification"
        />
        <CustomizeTopic
          v-else-if="activeTab == 1"
          :topicType="activeTab"
          :productIdentification="productIdentification"
        />
      </div>
    </div>
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, ref, onMounted, computed } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useLocale } from '/@/locales/useLocale';
  import { PageWrapper } from '/@/components/Page';
  import { useModal } from '/@/components/Modal';
  import BasicTopic from './basicTopic/index.vue';
  import CustomizeTopic from './customizeTopic/index.vue';
  import { useDict } from '/@/components/Dict';
  const { getDictList } = useDict();

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '产品Topic',
    components: {
      PageWrapper,
      BasicTopic,
      CustomizeTopic,
    },
    props: {
      id: {
        type: String,
        default: '',
      },
    },
    setup(props) {
      const { t } = useI18n();
      const [registerModal] = useModal();
      const topicTypeList = ref([]);
      const activeTab = ref(0);
      const productIdentification = ref(props.id);
      const langName = computed(() => {
        const lang = useLocale().getLocale.value;
        return ['zh_CN', 'en', 'ja'].includes(lang) ? lang : 'zh_CN';
      });
      onMounted(() => {
        topicTypeList.value = getDictList('LINK_PRODUCT_TOPIC_TYPE');
      });
      return {
        t,
        activeTab,
        registerModal,
        productIdentification,
        langName,
        topicTypeList,
      };
    },
  });
</script>
<style lang="less" scope>
  .operate {
    margin-bottom: 16px;

    .tabs {
      display: flex;

      .tab {
        min-width: 103px;
        height: 36px;
        background: #f0f2f5;
        border-radius: 6px 6px 0px 0px;
        font-size: 16px;
        font-weight: 500;
        color: #656565;
        text-align: center;
        line-height: 36px;
        cursor: pointer;

        &.active {
          background: #1a66ff;
          color: #fff;
        }

        & + .tab {
          margin-left: 12px;
        }
      }
    }
  }
</style>
