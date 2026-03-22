<template>
  <PageWrapper :title="t('iot.link.operationMaintenance.debug.webSocket.title')">
    <div class="flex">
      <div class="w-1/3 bg-white p-4">
        <div class="flex items-center">
          <span class="text-lg font-medium mr-4">
            {{ t('iot.link.operationMaintenance.debug.webSocket.connectionStatus') }}:
          </span>
          <a-switch
            v-model:checked="getIsOpen"
            @change="toggle"
            :checked-children="t('iot.link.operationMaintenance.debug.webSocket.closeConnection')"
            :un-checked-children="t('iot.link.operationMaintenance.debug.webSocket.openConnection')"
          />
        </div>
        <hr class="my-4" />

        <div class="flex">
          <a-input v-model:value="server">
            <template #addonBefore>
              {{ t('iot.link.operationMaintenance.debug.webSocket.server') }}
            </template>
          </a-input>
        </div>
        <p class="text-lg font-medium mt-4">{{
          t('iot.link.operationMaintenance.debug.webSocket.setting')
        }}</p>
        <hr class="my-4" />

        <InputTextArea
          :placeholder="t('iot.link.operationMaintenance.debug.webSocket.serverPlaceholder')"
          :disabled="!getIsOpen"
          v-model:value="sendValue"
          allowClear
        />

        <a-button type="primary" block class="mt-4" :disabled="!getIsOpen" @click="handlerSend">
          {{ t('iot.link.operationMaintenance.debug.webSocket.send') }}
        </a-button>
      </div>

      <div class="w-2/3 bg-white ml-4 p-4">
        <span class="text-lg font-medium mr-4">
          {{ t('iot.link.operationMaintenance.debug.webSocket.message') }}:
        </span>
        <hr class="my-4" />

        <div class="max-h-80 overflow-auto">
          <ul>
            <li v-for="item in getList" class="mt-2" :key="item.time">
              <div class="flex items-center">
                <span class="mr-2 text-primary font-medium"
                  >{{ t('iot.link.operationMaintenance.debug.webSocket.receivedMsg') }}:</span
                >
                <span>{{ formatToDateTime(item.time) }}</span>
              </div>
              <div>
                {{ item.res }}
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, reactive, watchEffect, computed, toRefs } from 'vue';
  import { Tag, Input } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import { useWebSocket } from '@vueuse/core';
  import { formatToDateTime } from '/@/utils/dateUtil';
  import { useI18n } from '/@/hooks/web/useI18n';

  export default defineComponent({
    components: {
      PageWrapper,
      [Input.name]: Input,
      InputTextArea: Input.TextArea,
      Tag,
    },
    setup() {
      const { t } = useI18n();
      let host = window.location.host;
      const protocol = window.location.protocol;
      const state = reactive({
        // server: '${protocol.includes('https') ? 'wss' : 'ws'}://localhost:8760/api/wsMsg/anno/test',
        server: `${protocol.includes('https') ? 'wss' : 'ws'}://${host}/api/wsMsg/anno/test`,
        // server: '${protocol.includes('https') ? 'wss' : 'ws'}://localhost:8768/anno/test',
        sendValue: '',
        recordList: [] as { id: number; time: number; res: string }[],
      });

      function onMessage(ws: WebSocket, event: MessageEvent) {
        console.log(ws);
        console.log(event);
      }
      const { status, data, send, close, open } = useWebSocket(state.server, {
        autoReconnect: false,
        heartbeat: false,
        onMessage: onMessage,
      });

      watchEffect(() => {
        if (data.value) {
          try {
            const res = JSON.parse(data.value);
            state.recordList.push(res);
          } catch (error) {
            state.recordList.push({
              res: data.value,
              id: Math.ceil(Math.random() * 1000),
              time: new Date().getTime(),
            });
          }
        }
      });

      const getIsOpen = computed(() => status.value === 'OPEN');
      const getTagColor = computed(() => (getIsOpen.value ? 'success' : 'red'));

      const getList = computed(() => {
        return [...state.recordList].reverse();
      });

      function handlerSend() {
        send(JSON.stringify({ aa: state.sendValue, UserId: '1459157721822527488', TenantId: '1' }));
        state.sendValue = '';
      }

      function toggle() {
        if (getIsOpen.value) {
          close();
        } else {
          open();
        }
      }
      return {
        t,
        status,
        formatToDateTime,
        ...toRefs(state),
        handlerSend,
        getList,
        toggle,
        getIsOpen,
        getTagColor,
      };
    },
  });
</script>
