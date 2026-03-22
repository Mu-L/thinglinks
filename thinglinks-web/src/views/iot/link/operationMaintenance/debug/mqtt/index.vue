<template>
  <PageWrapper :title="t('iot.link.operationMaintenance.debug.webSocket.mqttTitle')">
    <div class="flex">
      <div class="w-3/3 bg-white p-4 justify-items-center">
        <!-- <div class="flex items-center">
          <span>qos：</span>
          <a-input v-model:value="server">
            <template #addonBefore> 服务地址 </template>
          </a-input>
        </div>
        <div class="flex items-center">
          <span>topic：</span>
          <InputTextArea
            placeholder="需要发送到服务器的内容"
            v-model:value="sendValue"
            allowClear
          />
        </div> -->
        <div style="width: 600px">
          <a-form
            ref="formRef"
            :model="formState"
            name="basic"
            :label-col="{ span: 7 }"
            :wrapper-col="{ span: 17 }"
            autocomplete="off"
            @finish="onFinish"
            @finishFailed="onFinishFailed"
          >
            <a-form-item
              style="margin-bottom: 10px"
              label="topic"
              name="topic"
              :rules="[
                {
                  required: true,
                  message:
                    t('iot.link.operationMaintenance.debug.webSocket.pleaseEnter') + 'topic!',
                },
              ]"
            >
              <a-input v-model:value="formState.topic" />
            </a-form-item>
            <a-form-item
              style="margin-bottom: 10px"
              label="qos"
              name="qos"
              :rules="[
                {
                  required: true,
                  message: t('iot.link.operationMaintenance.debug.webSocket.pleaseEnter') + 'qos!',
                },
              ]"
            >
              <a-select v-model:value="formState.qos" @change="scaleChange">
                <a-select-option value="0">{{ t('iot.link.operationMaintenance.debug.mqtt.qos0') }}</a-select-option>
                <a-select-option value="1">{{ t('iot.link.operationMaintenance.debug.mqtt.qos1') }}</a-select-option>
                <a-select-option value="2">{{ t('iot.link.operationMaintenance.debug.mqtt.qos2') }}</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item
              style="margin-bottom: 10px"
              label="expirySeconds"
              name="expirySeconds"
              :rules="[
                {
                  required: true,
                  message:
                    t('iot.link.operationMaintenance.debug.webSocket.pleaseEnter') +
                    'expirySeconds!',
                },
                {
                  pattern: /^[0-9]\d*$/,
                  message: t('iot.link.operationMaintenance.debug.mqtt.positiveNumberValidation'),
                },
              ]"
            >
              <a-input-number v-model:value="formState.expirySeconds" />
            </a-form-item>
            <a-form-item
              style="margin-bottom: 5px"
              label="payload"
              name="payload"
              :rules="[
                {
                  required: true,
                  message:
                    t('iot.link.operationMaintenance.debug.webSocket.pleaseEnter') + 'payload!',
                },
              ]"
            >
              <InputTextArea
                :placeholder="t('iot.link.operationMaintenance.debug.webSocket.payload')"
                v-model:value="formState.payload"
                allowClear
              />
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 14, offset: 10 }">
              <a-button preIcon="ant-design:send-outlined" type="primary" @click="handlerSend">{{
                t('iot.link.operationMaintenance.debug.webSocket.send')
              }}</a-button>
              <a-button style="margin-left: 10px" @click="resetForm">{{
                t('common.resetText')
              }}</a-button>
            </a-form-item>
          </a-form>
        </div>
      </div>

      <!-- <div class="w-2/3 bg-white ml-4 p-4">
        <span class="text-lg font-medium mr-4"> 消息记录: </span>
        <hr class="my-4" />

        <div class="max-h-80 overflow-auto">
          <ul>
            <li v-for="item in getList" class="mt-2" :key="item.time">
              <div class="flex items-center">
                <span class="mr-2 text-primary font-medium">收到消息:</span>
                <span>{{ formatToDateTime(item.time) }}</span>
              </div>
              <div>
                {{ item.res }}
              </div>
            </li>
          </ul>
        </div>
      </div> -->
    </div>
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, reactive, watchEffect, computed, toRefs, ref, onMounted } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { Tag, Input } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import { formatToDateTime } from '/@/utils/dateUtil';
  import { sendMsg } from '../../../../../../api/iot/link/operationMaintenance/mqtt/mqtt';
  import { useUserStore } from '/@/store/modules/user';
  import { useMessage } from '/@/hooks/web/useMessage';
  const userStore = useUserStore();

  export default defineComponent({
    components: {
      PageWrapper,
      [Input.name]: Input,
      InputTextArea: Input.TextArea,
      Tag,
    },
    setup() {
      const { t } = useI18n();
      const { notification } = useMessage();
      const formRef = ref(null);
      const recordList = reactive([]);
      const formState = reactive({
        qos: '',
        expirySeconds: '',
        topic: '',
        payload: '',
      });

      const getList = computed(() => {
        return [...recordList].reverse();
      });

      function handlerSend() {
        formRef.value
          .validate()
          .then(async () => {
            formState.tenantId = userStore.getTenantId;
            await sendMsg(formState);
            notification.success({ message: t('common.tips.sendSuccess') });
          })
          .catch((error) => {
            console.log('error', error);
          });
      }
      function resetForm() {
        formRef.value.resetFields();
      }
      return {
        t,
        formRef,
        formatToDateTime,
        handlerSend,
        resetForm,
        recordList,
        getList,
        formState,
      };
    },
  });
</script>
