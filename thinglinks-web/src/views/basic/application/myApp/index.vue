<template>
  <PageWrapper>
    <div class="w-full enter-y">
      <ApplicationCard
        :title="t('thinglinks.home.application.myApplication')"
        :api="findMyApplication"
        class="enter-y"
        :updateDef="true"
      />

      <ApplicationCard
        :title="t('thinglinks.home.application.recommendApplication')"
        :handleClick="handleClick"
        :description="t('thinglinks.home.application.allActivat')"
        :api="findRecommendApplication"
        :showRecommendOnly="true"
        :maxDisplayCount="6"
        class="enter-y !my-4"
      />
    </div>
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import ApplicationCard from '/@/views/thinglinks/home/components/ApplicationCard.vue';
  import { PageWrapper } from '/@/components/Page';
  import { findMyApplication, findRecommendApplication } from '/@/api/thinglinks/profile/userInfo';
  import { useMessage } from '/@/hooks/web/useMessage';

  export default defineComponent({
    name: '我的应用',
    components: { PageWrapper, ApplicationCard },
    setup() {
      const { t } = useI18n();
      const { createMessage } = useMessage();

      function handleClick() {
        createMessage.warn(t('thinglinks.home.application.contactOpen'));
      }

      return {
        t,
        handleClick,
        findMyApplication,
        findRecommendApplication,
      };
    },
  });
</script>
