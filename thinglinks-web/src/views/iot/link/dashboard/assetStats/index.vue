<template>
  <div class="p-4">
    <GrowCard class="enter-y" />
    <messageDataLine class="!my-4 enter-y"/>
  </div>
</template>
<script lang="ts">
  import { defineComponent, ref, reactive, onMounted, watch, toRefs } from 'vue';
  import GrowCard from './components/GrowCard.vue';
  import messageDataLine from './components/messageDataLine.vue';
  export default defineComponent({
    name: 'assetStats',
    components: {
      GrowCard,
      messageDataLine,
    },
    setup() {   
    },
  });
</script>
../../../../../api/iot/link/dashboard/dashboard