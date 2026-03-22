<template>
  <CollapseContainer :title="t('thinglinks.profile.tabTitle.basic')" :canExpan="false">
    <a-row :gutter="24">
      <a-col :span="14">
        <BasicForm @register="register" />
      </a-col>
      <a-col :span="10">
        <div class="change-avatar">
          <div class="mb-2"> {{ t('thinglinks.profile.basic.avatar') }} </div>
          <CropperAvatar
            :uploadApi="uploadFile"
            :uploadParams="{ bizType: FileBizTypeEnum.BASE_USER_AVATAR }"
            :value="avatarId"
            :btnText="t('thinglinks.profile.basic.changeAvatar')"
            :btnProps="{ preIcon: 'ant-design:cloud-upload-outlined' }"
            @change="handleUpdateAvatar"
            width="150"
          />
        </div>
      </a-col>
    </a-row>
    <Button type="primary" @click="handleSubmit">
      {{ t('thinglinks.profile.basic.updateInfo') }}
    </Button>
  </CollapseContainer>
</template>
<script lang="ts">
  import { Button, Row, Col } from 'ant-design-vue';
  import { computed, defineComponent, onMounted } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { CollapseContainer } from '/@/components/Container';
  import { CropperAvatar } from '/@/components/Cropper';

  import { useMessage } from '/@/hooks/web/useMessage';

  import { FileBizTypeEnum } from '/@/enums/commonEnum';
  import { updateBaseInfo, updateAvatar } from '/@/api/thinglinks/profile/userInfo';
  import { userInfoSchemas } from './Userinfo/data';
  import { useUserStore } from '/@/store/modules/user';
  import { uploadFile } from '/@/api/thinglinks/file/upload';
  import { i18n } from '/@/locales/setupI18n';

  export default defineComponent({
    components: {
      BasicForm,
      CollapseContainer,
      Button,
      ARow: Row,
      ACol: Col,
      CropperAvatar,
    },
    setup() {
      const { createMessage } = useMessage();
      const { t } = useI18n();
      const userStore = useUserStore();

      const locale = (i18n.global.locale as any).value;

      const [register, { setFieldsValue, validate }] = useForm({
        name: 'userInfoForm',
        labelWidth: locale === 'zh' ? 120 : 150,
        schemas: userInfoSchemas,
        showActionButtonGroup: false,
      });

      onMounted(async () => {
        const data = userStore.getUserInfo;
        setFieldsValue(data);
      });

      const avatarId = computed(() => {
        return userStore.getUserInfo?.avatarId;
      });

      async function handleUpdateAvatar(data: any) {
        const userinfo = userStore.getUserInfo;

        const params = { id: userinfo.id, appendixAvatar: data };
        await updateAvatar(params);

        const userInfo = userStore.getUserInfoAction();
        setFieldsValue(userInfo);
      }

      async function handleSubmit() {
        try {
          const params = await validate();
          await updateBaseInfo(params);

          await userStore.getUserInfoAction();

          createMessage.success(t(`common.tips.updateSuccess`));
        } finally {
        }
      }
      return {
        t,
        avatarId,
        register,
        uploadFile,
        handleUpdateAvatar,
        handleSubmit,
        FileBizTypeEnum,
      };
    },
  });
</script>

<style lang="less" scoped>
  .change-avatar {
    img {
      display: block;
      margin-bottom: 15px;
      border-radius: 50%;
    }
  }
</style>
