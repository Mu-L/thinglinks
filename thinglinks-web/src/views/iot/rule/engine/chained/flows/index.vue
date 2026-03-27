<template>
  <div class="rule-flow-node">
    <iframe :src="iframeSrc" ref="iframe" @load="onIframeLoad" @error="onIframeError"></iframe>
    <div v-if="loading">Loading...</div>
    <div v-if="error">{{ error }}</div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';
import { useUserStoreWithOut } from '/@/store/modules/user';

export default defineComponent({
  name: '规则流节点',
  setup() {
    const route = useRoute();
    const userStore = useUserStoreWithOut();
    const token = userStore.getToken;

    const iframeSrc = ref('');
    const loading = ref(false);
    const error = ref('');

    // 监听路由参数的变化，并根据参数重新加载 iframe 的地址
    watchEffect(() => {
      const { instanceAddress, flowId } = route.params;
      loading.value = true;
      error.value = '';
      iframeSrc.value = `${instanceAddress}/?access_token=${token}#flow/${flowId}`;
    });

    // 处理 iframe 加载完成的事件
    const onIframeLoad = () => {
      loading.value = false;
    };

    // 处理 iframe 加载失败的事件
    const onIframeError = () => {
      loading.value = false;
      error.value = 'Failed to load iframe.';
    };

    return {
      iframeSrc,
      loading,
      error,
      onIframeLoad,
      onIframeError,
    };
  },
});
</script>

<style lang="less" scoped>
div {
  width: 100%;
  height: 100%;

  iframe {
    width: 100%;
    height: 100%;
  }
}
</style>
