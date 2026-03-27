<template>
  <ConfigProvider :locale="getAntdLocale">
    <AppProvider>
      <RouterView :key="cRoute" />
    </AppProvider>
  </ConfigProvider>
</template>

<script lang="ts" setup>
  import { computed } from 'vue';
  import { ConfigProvider } from 'ant-design-vue';
  import { AppProvider } from '/@/components/Application';
  import { useTitle } from '/@/hooks/web/useTitle';
  import { useLocale } from '/@/locales/useLocale';
  import { useRouter } from 'vue-router';
  const { currentRoute } = useRouter();

  let cRoute = computed(() => {
    return currentRoute.value.fullPath;
  });

  import 'dayjs/locale/zh-cn';
  // support Multi-language
  const { getAntdLocale } = useLocale();

  // Listening to page changes and dynamically changing site titles
  useTitle();
</script>
