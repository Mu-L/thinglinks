<template>
  <div>
    <Tooltip
      :mouseEnterDelay="0.5"
      placement="bottom"
      :title="t('layout.header.switchingCompanies')"
    >
      <span @click="handleSwitch">
        <SwapOutlined />
      </span>
    </Tooltip>
    <SwitchTenant @register="registerModal" />
  </div>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';

  import { useI18n } from '/@/hooks/web/useI18n';
  import { Tooltip } from 'ant-design-vue';
  import { SwapOutlined } from '@ant-design/icons-vue';
  import { useModal } from '/@/components/Modal';
  import SwitchTenant from './SwitchTenant.vue';

  export default defineComponent({
    name: 'TenantCompanyList',
    components: { Tooltip, SwapOutlined, SwitchTenant },
    setup() {
      const { t } = useI18n();
      const [registerModal, { openModal }] = useModal();

      function handleSwitch() {
        openModal(true, {});
      }

      return {
        t,
        handleSwitch,
        registerModal,
      };
    },
  });
</script>
