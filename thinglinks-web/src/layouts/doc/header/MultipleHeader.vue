<template>
  <div v-if="getIsShowPlaceholderDom" :style="getPlaceholderDomStyle"></div>
  <div :class="getClass" :style="getWrapStyle">
    <LayoutHeader />
  </div>
</template>
<script lang="ts">
  import { computed, CSSProperties, defineComponent, unref } from 'vue';

  import LayoutHeader from './index.vue';

  import { useHeaderSetting } from '/@/hooks/setting/useHeaderSetting';
  import { useMenuSetting } from '/@/hooks/setting/useMenuSetting';
  import { useFullContent } from '/@/hooks/web/useFullContent';
  import { useMultipleTabSetting } from '/@/hooks/setting/useMultipleTabSetting';
  import { useDesign } from '/@/hooks/web/useDesign';
  import { useLayoutHeight } from '../../default/content/useContentViewHeight';

  // 这几个参数跟 src/design/var/index.less 中的高度要一致
  // @multiple-height: 30px;
  // @multiple-card-height: 50px;
  // @multiple-smooth-height: 50px;
  // @header-height: 48px;
  const HEADER_HEIGHT = 48;

  export default defineComponent({
    name: 'LayoutMultipleHeader',
    components: { LayoutHeader },
    setup() {
      const { setHeaderHeight } = useLayoutHeight();
      const { prefixCls } = useDesign('layout-multiple-header');

      const { getSplit } = useMenuSetting();
      const {
        getFixed,
        getShowInsetHeaderRef,
        getShowFullHeaderRef,
        getHeaderTheme,
        getShowHeader,
      } = useHeaderSetting();

      const { getFullContent } = useFullContent();

      const { getShowMultipleTab } = useMultipleTabSetting();

      const getShowTabs = computed(() => {
        return unref(getShowMultipleTab) && !unref(getFullContent);
      });

      const getIsShowPlaceholderDom = computed(() => {
        return unref(getFixed) || unref(getShowFullHeaderRef);
      });

      const getWrapStyle = computed((): CSSProperties => {
        const style: CSSProperties = {};
        if (unref(getFixed)) {
          style.width = '100%';
        }
        return style;
      });

      const getIsFixed = computed(() => {
        return unref(getFixed) || unref(getShowFullHeaderRef);
      });

      const getPlaceholderDomStyle = computed((): CSSProperties => {
        let height = 0;
        if (
          (unref(getShowFullHeaderRef) || !unref(getSplit)) &&
          unref(getShowHeader) &&
          !unref(getFullContent)
        ) {
          height += HEADER_HEIGHT;
        }

        setHeaderHeight(height);
        return {
          height: `${height}px`,
        };
      });

      const getClass = computed(() => {
        return [
          prefixCls,
          `${prefixCls}--${unref(getHeaderTheme)}`,
          { [`${prefixCls}--fixed`]: unref(getIsFixed) },
        ];
      });

      return {
        getClass,
        prefixCls,
        getPlaceholderDomStyle,
        getIsFixed,
        getWrapStyle,
        getIsShowPlaceholderDom,
        getShowTabs,
        getShowInsetHeaderRef,
      };
    },
  });
</script>
<style lang="less" scoped>
  @prefix-cls: ~'@{namespace}-layout-multiple-header';

  .@{prefix-cls} {
    transition: width 0.2s;
    flex: 0 0 auto;

    &--dark {
      margin-left: -1px;
    }

    &--fixed {
      position: fixed;
      top: 0;
      z-index: @multiple-tab-fixed-z-index;
      width: 100%;
    }
  }
</style>
