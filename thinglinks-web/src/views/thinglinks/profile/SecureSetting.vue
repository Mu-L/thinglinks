<template>
  <CollapseContainer :canExpan="false" :title="t('thinglinks.profile.tabTitle.security')">
    <List>
      <ListItem>
        <ListItemMeta>
          <template #title>
            {{ t('thinglinks.profile.security.password') }}
            <div class="extra" @click="handleExtra('1')">
              {{ t('thinglinks.profile.security.modify') }}</div
            >
          </template>
          <template #description>
            <div> {{ t('thinglinks.profile.security.passwordDesc') }}</div>
          </template>
        </ListItemMeta>
      </ListItem>
      <ListItem>
        <ListItemMeta>
          <template #title>
            {{ t('thinglinks.profile.security.mobileNumber') }}
            <div class="extra" @click="handleExtra('2')">
              {{ t('thinglinks.profile.security.modify') }}</div
            >
          </template>
          <template #description>
            <div> {{ t('thinglinks.profile.security.mobileDesc') }}</div>
          </template>
        </ListItemMeta>
      </ListItem>
      <ListItem>
        <ListItemMeta>
          <template #title>
            {{ t('thinglinks.profile.security.emailAddress') }}
            <div class="extra" @click="handleExtra('3')">
              {{ t('thinglinks.profile.security.modify') }}</div
            >
          </template>
          <template #description>
            <div> {{ t('thinglinks.profile.security.emailDesc') }}</div>
          </template>
        </ListItemMeta>
      </ListItem>
    </List>
    <UpdatePasswordModal @register="registerModal" @success="handleUpdatePasswordSuccess" />
    <UpdateEmailModal @register="registerEmailModal" @success="handleUpdatePasswordSuccess" />
    <UpdateMobileModal @register="registerMobileModal" @success="handleUpdatePasswordSuccess" />
  </CollapseContainer>
</template>
<script lang="ts">
  import { defineComponent } from 'vue';
  import { List } from 'ant-design-vue';
  import { CollapseContainer } from '/@/components/Container/index';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useModal } from '/@/components/Modal';
  import UpdatePasswordModal from './AccountPassword/index.vue';
  import UpdateMobileModal from './Mobile/index.vue';
  import UpdateEmailModal from './Email/index.vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  const { t } = useI18n();

  export default defineComponent({
    name: 'SecureSetting',
    components: {
      CollapseContainer,
      List,
      ListItem: List.Item,
      ListItemMeta: List.Item.Meta,
      UpdatePasswordModal,
      UpdateMobileModal,
      UpdateEmailModal,
    },
    setup() {
      const [registerModal, { openModal }] = useModal();
      const [registerEmailModal, { openModal: openEmailModal }] = useModal();
      const [registerMobileModal, { openModal: openMobileModal }] = useModal();
      const { createMessage } = useMessage();

      function handleExtra(key: string, e: Event) {
        e?.stopPropagation();
        e?.preventDefault();
        if ('1' === key) {
          openModal(true, {});
        } else if ('2' === key) {
          openMobileModal(true, {});
        } else if ('3' === key) {
          openEmailModal(true, {});
        } else {
          createMessage.warn(t('thinglinks.profile.comingSoon'));
        }
      }

      function handleUpdatePasswordSuccess() {}

      return {
        t,
        registerModal,
        registerEmailModal,
        registerMobileModal,
        handleExtra,
        handleUpdatePasswordSuccess,
      };
    },
  });
</script>
<style lang="less" scoped>
  .extra {
    float: right;
    margin-top: 10px;
    margin-right: 30px;
    font-weight: normal;
    color: #1890ff;
    cursor: pointer;
  }
</style>
