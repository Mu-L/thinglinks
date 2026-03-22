<script lang="tsx">
  import type { PropType } from 'vue';
  import { computed, defineComponent, toRef, unref } from 'vue';
  import { BasicMenu } from '/@/components/Menu';

  import { MenuModeEnum, MenuSplitTyeEnum } from '/@/enums/menuEnum';
  import { useMenuSetting } from '/@/hooks/setting/useMenuSetting';

  import { useGo } from '/@/hooks/web/usePage';
  import { useSplitMenu } from './useLayoutMenu';
  import { openWindow } from '/@/utils';
  import { propTypes } from '/@/utils/propTypes';
  import { isUrl } from '/@/utils/is';
  import { DocMenu1, DocMenu2 } from '/@/router/routes';

  export default defineComponent({
    name: 'LayoutMenu',
    props: {
      theme: propTypes.oneOf(['light', 'dark']),
      // 拆分类型
      splitType: {
        type: Number as PropType<MenuSplitTyeEnum>,
        default: MenuSplitTyeEnum.NONE,
      },
      // 是否水平菜单
      isHorizontal: propTypes.bool,
      // menu Mode
      menuMode: {
        type: [String] as PropType<Nullable<MenuModeEnum>>,
        default: '',
      },
    },
    setup(props) {
      const go = useGo();

      const { getMenuTheme, getCollapsed, getCollapsedShowTitle, getAccordion } = useMenuSetting();

      const { menusRef } = useSplitMenu(toRef(props, 'splitType'));

      const getComputedMenuTheme = computed(() => props.theme || unref(getMenuTheme));

      const getCommonProps = computed(() => {
        const menus = unref(menusRef);
        return {
          menus,
          beforeClickFn: beforeMenuClickFn,
          items: menus,
          theme: unref(getComputedMenuTheme),
          accordion: unref(getAccordion),
          collapse: unref(getCollapsed),
          collapsedShowTitle: unref(getCollapsedShowTitle),
          onMenuClick: handleMenuClick,
        };
      });

      /**
       * click menu
       * @param menu
       */

      function handleMenuClick(path: string) {
        go(path);
      }

      /**
       * before click menu
       * @param menu
       */
      async function beforeMenuClickFn(path: string) {
        if (!isUrl(path)) {
          return true;
        }
        openWindow(path);
        return false;
      }

      function renderMenu() {
        const { ...menuProps } = unref(getCommonProps);
        const menus = [...DocMenu1, ...DocMenu2];
        return <BasicMenu {...(menuProps as any)} mode={MenuModeEnum.HORIZONTAL} items={menus} />;
      }

      return () => {
        return <>{renderMenu()}</>;
      };
    },
  });
</script>
<style lang="less">
  @prefix-cls: ~'@{namespace}-layout-menu';
  @logo-prefix-cls: ~'@{namespace}-app-logo';

  .@{prefix-cls} {
    &-logo {
      height: @header-height;
      padding: 10px 4px 10px 10px;

      img {
        width: @logo-width;
        height: @logo-width;
      }
    }

    &--mobile {
      .@{logo-prefix-cls} {
        &__title {
          opacity: 1;
        }
      }
    }
  }
</style>
