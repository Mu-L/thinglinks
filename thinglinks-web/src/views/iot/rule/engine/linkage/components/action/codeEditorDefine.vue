<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    :title="t('iot.link.engine.executionLog.action.jsonEditor')"
    @ok="handleSubmit"
    width="800px"
  >
    <a-textarea
      v-model:value="editorValue"
      :rows="15"
      :placeholder="t('common.inputText')"
    />
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { useI18n } from '/@/hooks/web/useI18n';

  export default defineComponent({
    name: 'CodeEditorDefine',
    components: {
      BasicModal,
    },
    emits: ['submit-editor'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const editorValue = ref('');

      const [registerModal, { closeModal }] = useModalInner((data) => {
        editorValue.value = data.value || '';
      });

      const handleSubmit = () => {
        emit('submit-editor', editorValue.value);
        closeModal();
      };

      return {
        t,
        editorValue,
        registerModal,
        handleSubmit,
      };
    },
  });
</script>