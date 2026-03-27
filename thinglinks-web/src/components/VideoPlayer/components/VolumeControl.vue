<template>
  <div class="volume-control">
    <div @click="toggleMute">
      <template v-if="localMuted">
        <SvgIcon name="jingyin" class="operate-icon" />
      </template>
      <template v-else>
        <SvgIcon name="kaiqishengyin" class="operate-icon" />
      </template>
    </div>
    <div class="slider-wrapper">
      <a-slider
        v-model:value="localVolume"
        :min="0"
        :max="100"
        :step="1"
        :style="{ width: '100px', marginLeft: '10px', background: '#fff' }"
      />
    </div>
  </div>
</template>

<script setup>
  import { ref, watch, onMounted } from 'vue';
  import SvgIcon from '/@/components/Icon/src/SvgIcon.vue';
  const props = defineProps({
    volume: { type: Number, default: 0.5, required: true },
    muted: { type: Boolean, default: false, required: true },
  });

  const emits = defineEmits(['update:volume', 'update:muted']);

  const localVolume = ref(props.volume * 100);
  const localMuted = ref(props.muted);

  const toggleMute = () => {
    localMuted.value = !localMuted.value;
    emits('update:muted', localMuted.value);
  };

  watch(localVolume, (newVolume) => {
    emits('update:volume', newVolume / 100);
  });
  onMounted(() => {
    localVolume.value = props.volume * 100;
    localMuted.value = props.muted;
  });
</script>

<style scoped lang="less">
  .volume-control {
    .operate-icon {
      transition: all linear 0.2s;
      cursor: pointer;
      &:hover {
        transform: scale(1.1);
      }
    }
    display: flex;
    align-items: center;
    .slider-wrapper {
      width: 100px;
    }
  }
</style>
