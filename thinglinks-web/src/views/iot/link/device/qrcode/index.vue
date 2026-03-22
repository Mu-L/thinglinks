<template>
  <div :class="`qrcode_container ${props.class}`">
    <a-popover
      v-model:open="visible"
      :title="t('iot.link.device.device.qrcode')"
      trigger="click"
      placement="bottom"
      overlayClassName="content_name"
    >
      <template #content>
        <div class="img-wrapper" v-if="qrcodeUrl">
          <img :src="qrcodeUrl" class="img_qrcode" />
          <div class="overlay overlay-top-left"></div>
          <div class="overlay overlay-bottom-right"></div>
          <div class="download-text" @click="downloadImage">
            <SvgIcon name="download" :size="32" class="download_svg" />
          </div>
        </div>
        <a-spin :indicator="indicator" v-else />
      </template>
      <div @click="createQrcode" class="content">
        <slot name="trigger">
          <a-button type="primary">
            <SvgIcon name="iot-link-device-qrcode" />
          </a-button>
        </slot>
      </div>
    </a-popover>
  </div>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { getQrcode } from '/@/api/iot/link/deviceQrcode';
  import SvgIcon from '/@/components/Icon/src/SvgIcon.vue';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { LoadingOutlined } from '@ant-design/icons-vue';
  import { h } from 'vue';
  const { t } = useI18n();
  const indicator = h(LoadingOutlined, {
    style: {
      fontSize: '24px',
    },
    spin: true,
  });

  const props = defineProps({
    deviceIdentification: {
      type: String,
      required: true,
    },
    deviceName: {
      type: String,
      required: true,
    },
    class: {
      type: String,
    },
  });
  const qrcodeUrl = ref<string>('');
  const visible = ref<boolean>(false);

  const downloadImage = () => {
    const a = document.createElement('a');
    a.href = qrcodeUrl.value;
    a.download = `设备「${props.deviceName}」二维码.png`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  };

  const createQrcode = async () => {
    if (qrcodeUrl.value) {
      return;
    }
    try {
      const res = await getQrcode({ deviceIdentification: props.deviceIdentification });
      if (res.byteLength <= 100) {
        const { createMessage } = useMessage();
        createMessage.error('系统维护中，请稍微再试~');
        return '';
      }
      qrcodeUrl.value =
        'data:image/png;base64,' +
        btoa(new Uint8Array(res).reduce((data, byte) => data + String.fromCharCode(byte), ''));
    } catch (error) {
      console.log(error);
    }
  };
</script>

<style lang="less" scoped>
  .qrcode_container {
    .content {
      display: flex;
      align-items: center;
    }

    :deep(.ant-btn) {
      border-radius: 6px;
    }
  }

  .img-wrapper {
    position: relative;
    width: 200px;
    height: 200px;
    overflow: hidden;
  }

  .img_qrcode {
    width: 100%;
    height: 100%;
    display: block;
  }

  .overlay {
    position: absolute;
    width: 50%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    transition: transform 0.5s ease;
    cursor: pointer;
  }

  .overlay-top-left {
    top: 0;
    left: 0;
    transform: translate(-100%, 0);
  }

  .overlay-bottom-right {
    bottom: 0;
    right: 0;
    transform: translate(100%, 0);
  }

  .img-wrapper:hover .overlay-top-left {
    transform: translate(0, 0);
  }

  .img-wrapper:hover .overlay-bottom-right {
    transform: translate(0, 0);
  }

  .img-wrapper:hover .download_svg {
    opacity: 1;
  }

  .download-text {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: white;
    font-size: 18px;
    font-weight: bold;
  }

  .ant-spin {
    width: 200px;
    height: 200px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .download_svg {
    opacity: 0;
    cursor: pointer;
    transition: all 0.5s ease;

    &:hover {
      transform: scale(1.2);
    }
  }
</style>
../../../../../api/iot/link/deviceQrcode
