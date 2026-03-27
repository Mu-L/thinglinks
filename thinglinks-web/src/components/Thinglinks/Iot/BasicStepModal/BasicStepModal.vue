<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    :destroyOnClose="true"
    :keyboard="true"
    :showOkBtn="false"
    :showCancelBtn="false"
    :width="width"
  >
    <div :class="stepClass">
      <a-steps :current="activeStep" v-if="showSteps">
        <a-step
          v-for="(step, index) in steps"
          :key="index"
          :title="step.title"
          v-show="step.visible !== false"
        />
      </a-steps>
      <div
        class="form_box"
        v-for="(step, index) in steps"
        :key="index"
        v-show="activeStep === index && step.visible !== false"
      >
        <component
          :is="step.component"
          v-bind="step.props || {}"
          @update="handleStepUpdate"
          :ref="(el) => setStepRef(el, index)"
        />
      </div>
    </div>
    <template #appendFooter>
      <a-button @click="prev" v-if="activeStep > 0">{{
        prevText || t('iot.link.engine.linkage.prev')
      }}</a-button>
      <a-button type="primary" @click="next" v-if="activeStep < lastIndex">{{
        nextText || t('iot.link.engine.linkage.next')
      }}</a-button>
      <a-button type="primary" @click="finish" v-if="activeStep === lastIndex">{{
        finishText || t('common.confirmText')
      }}</a-button>
    </template>
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref, reactive, computed, toRefs, PropType } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { Steps } from 'ant-design-vue';
  import type { StepConfig } from '../types';
  export default defineComponent({
    name: 'BasicStepModal',
    components: { BasicModal, [Steps.name]: Steps, [Steps.Step.name]: Steps.Step },
    props: {
      title: { type: String, default: '' },
      width: { type: String, default: '1200px' },
      steps: { type: Array as PropType<StepConfig[]>, default: () => [] },
      stepClass: { type: String, default: '' },
      showSteps: { type: Boolean, default: true },
      prevText: String,
      nextText: String,
      finishText: String,
    },
    emits: ['register', 'stepChange', 'finish', 'stepUpdate'],
    setup(props, { emit, expose }) {
      const { t } = useI18n();
      const state = reactive({ activeStep: 0, stepData: {} as Record<number, any> });
      const stepRefs = ref<Record<number, any>>({});
      const visibleSteps = computed(() => props.steps.filter((s) => s.visible !== false));
      const lastIndex = computed(() => visibleSteps.value.length - 1);
      const [registerModal, { closeModal }] = useModalInner(async (data) => {
        state.activeStep = data?.activeStep ?? 0;
        state.stepData = data?.stepData ?? {};
        emit('stepChange', { step: state.activeStep, data: state.stepData, type: 'init' });
      });
      const setStepRef = (el: any, index: number) => {
        if (el) stepRefs.value[index] = el;
      };
      const handleStepUpdate = (data: any) => {
        state.stepData[state.activeStep] = data;
        emit('stepUpdate', { step: state.activeStep, data, allData: state.stepData });
      };
      const validateCurrentStep = async (): Promise<boolean> => {
        const current = props.steps[state.activeStep];
        if (current?.validator) {
          const ok = await current.validator();
          if (!ok) return false;
        }
        const inst = stepRefs.value[state.activeStep];
        if (inst && typeof inst.validate === 'function') {
          const ok = await inst.validate();
          if (!ok) return false;
        }
        return true;
      };
      const next = async () => {
        if (!(await validateCurrentStep())) return;
        if (state.activeStep < lastIndex.value) {
          state.activeStep++;
          emit('stepChange', { step: state.activeStep, data: state.stepData, type: 'next' });
        }
      };
      const prev = () => {
        if (state.activeStep > 0) {
          state.activeStep--;
          emit('stepChange', { step: state.activeStep, data: state.stepData, type: 'prev' });
        }
      };
      const finish = async () => {
        if (!(await validateCurrentStep())) return;
        emit('finish', { data: state.stepData });
        handleSubmit();
      };
      const handleSubmit = () => closeModal();
      expose({
        getActiveIndex: () => state.activeStep,
        getStepRef: (index: number) => stepRefs.value[index],
      });
      return {
        t,
        registerModal,
        setStepRef,
        handleStepUpdate,
        next,
        prev,
        finish,
        lastIndex,
        handleSubmit,
        ...toRefs(state),
      };
    },
  });
</script>
<style lang="less" scoped>
  .form_box {
    padding: 20px;
  }
  .two :deep(.ant-steps) {
    width: 60%;
    margin: 0 auto;
  }
</style>
