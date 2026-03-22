<template>
  <BasicModal
    v-bind="$attrs"
    @register="register"
    :title="t('iot.link.engine.alarmRecord.alarmContent')"
    :maskClosable="false"
    :keyboard="true"
    :destroyOnClose="true"
    @cancel="handleCancel"
    @ok="handleOk"
  >
    <div class="container">
      <div class="form-item">
        <div class="label">锚点变量:</div>
        <div class="value">
          <a-select :style="{ width: '100%' }" v-model:value="selectVal" placeholder="请选择">
            <a-select-option :value="item.value" v-for="item in optionList" :key="item.value">{{
              item.label
            }}</a-select-option>
          </a-select>
        </div>
      </div>
    </div>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { reactive, ref, onMounted } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { useDict } from '/@/components/Dict';
  import { DictEnum } from '/@/enums/commonEnum';
  const { t } = useI18n();
  const { initGetDictList } = useDict();
  const [register, { closeModal }] = useModalInner();

  const emit = defineEmits(['success']);

  const selectVal = ref('');
  const optionList = reactive<Record<string, any>[]>([]);

  onMounted(async () => {
    const list = await initGetDictList(DictEnum.RULE_ALARM_TEMPLATE_VAR_ANCHORS);
    optionList.push(...list);
  });

  const handleReset = () => {
    selectVal.value = '';
  };
  const handleCancel = () => {
    handleReset();
    closeModal();
  };
  const handleOk = () => {
    emit('success', { sign: selectVal.value });
    handleReset();
    closeModal();
  };
</script>

<style lang="less" scoped>
  .container {
    display: flex;
    flex-direction: column;
    gap: 20px;

    .form-item {
      .label {
        margin-bottom: 8px;
      }
    }
  }
</style>
