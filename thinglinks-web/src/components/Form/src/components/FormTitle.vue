<template>
  <div class="form_title">
    {{ title }}
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
export default defineComponent({
  name: 'FormTitle',
  components: {},
  props: {
    title: { type: String, default: 'DD' },
  },
  setup(props) {
    const title = ref(props.title)
    return {
      title
    };
  },
});
</script>
<style lang="less" scope>
.form_title {
  font-size: 16px;
  font-family: PingFang SC-Semibold, PingFang SC;
  font-weight: 600;
  color: #2E3033;
  line-height: 20px;
  padding-left: 9px;
  margin-bottom: 12px;
  border-left: 3px solid #1A66FF;
}
</style>
