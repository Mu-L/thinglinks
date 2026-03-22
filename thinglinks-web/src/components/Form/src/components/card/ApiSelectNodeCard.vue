<template>
  <a-button type="primary" @click="handleOpenModal" :disabled="$attrs.disabled">{{ t('card.channel.cardChannelInfo.select') }}</a-button>
  <span class="show_name">{{ selectNode?.channelName }}</span>
  <SelectedNodeModal
    @register="registerModal"
    @success="handleSuccess"
    :value="props.value"
    @update-select-node="handleUpdateSelectNode"
    v-bind="$attrs"
  />
</template>
<script setup lang="ts">
import { reactive, watch } from 'vue';
import { useModal } from '/@/components/Modal';
import SelectedNodeModal from './components/SelectedNodeModal.vue';
import type { CardChannelInfoPageQuery } from '/@/api/card/channel/model/cardChannelInfoModel';
import {useI18n} from "/@/hooks/web/useI18n";
const props = defineProps({ type: String, value: String });
const emit = defineEmits(['select']);
const { t } = useI18n();

const selectNode = reactive<CardChannelInfoPageQuery>({ channelName: '' });

const [registerModal, { openModal }] = useModal();

const handleOpenModal = () => {
  openModal();
};
const handleSuccess = (data: CardChannelInfoPageQuery) => {
  Object.assign(selectNode, data);
  emit('select', data);

};
const handleUpdateSelectNode = (data: CardChannelInfoPageQuery) => {
  Object.assign(selectNode, data);
};
watch(
  () => props.value,
  (newValue) => {
    if (!newValue) {
      selectNode.channelName = '';
    }
  },
);
</script>
<style scoped lang="less">
.show_name {
  display: inline-block;
  margin-left: 10px;
  font-size: 12px;
  color: #999;
}
</style>
