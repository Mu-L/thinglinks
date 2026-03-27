<script setup lang="ts">
  import { useRouter } from 'vue-router';

  import { Button, Card, Result } from 'ant-design-vue';

  const { replace } = useRouter();
  function handleLogin() {
    replace({ path: '/login' });
  }

  function handleDoc() {
    replace({ path: '/doc/api' });
  }
  function handleHome() {
    replace({ path: '/home/welcome' });
  }
</script>
<template>
  <Card class="h-full">
    <Result title="欢迎访问 ThingLinks 开放平台" status="404" sub-title="想要调用文档？请立即注册">
      <template #extra>
        <Button type="primary" @click="handleHome">已登录，去申请秘钥</Button>
        <Button type="primary" @click="handleLogin">去登录</Button>
        <Button @click="handleDoc">看文档</Button>
      </template>
    </Result>
  </Card>
</template>
