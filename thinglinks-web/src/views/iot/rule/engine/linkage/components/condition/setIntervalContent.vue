<template>
  <div class="container">
    <a-row>
      <a-col :span="18">
        <BasicForm @register="register" :showActionButtonGroup="false" :disabled="state.disabled" />
      </a-col>
      <a-col :span="6">
        <a-alert type="warning">
          <template #message>
            <p class="warn_title">{{
              t('iot.link.engine.executionLog.setIntervalContent.title')
            }}</p>
          </template>
          <template #description>
            <ul class="ul_custom">
              <li class="li_custom"
                >{{ t('iot.link.engine.executionLog.setIntervalContent.tip1[0]')
                }}<span class="font_strong">{{
                  t('iot.link.engine.executionLog.setIntervalContent.tip1[1]')
                }}</span
                >，{{ t('iot.link.engine.executionLog.setIntervalContent.tip1[2]') }}</li
              >
              <li class="li_custom"
                >2.<span class="font_strong">{{
                  t('iot.link.engine.executionLog.setIntervalContent.tip2[0]')
                }}</span
                >{{ t('iot.link.engine.executionLog.setIntervalContent.tip2[1]') }}</li
              >
              <li class="li_custom"
                >3.{{ t('iot.link.engine.executionLog.setIntervalContent.tip3') }}</li
              >
            </ul>
          </template>
        </a-alert>
      </a-col>
    </a-row>
  </div>
</template>

<script setup lang="ts">
  import { onMounted, reactive, watch, nextTick } from 'vue';
  import { BasicForm, FormSchema, useForm } from '/@/components/Form/index';
  import { DictEnum } from '/@/enums/commonEnum';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useDict } from '/@/components/Dict';

  const { getDictList, initGetDictList } = useDict();
  const { t } = useI18n();

  const props = defineProps({
    effectiveType: { type: Number, default: 0 },
    appointContent: { type: Object, default: () => {} },
    disabled: { type: Boolean, default: false },
  });

  const state: Record<string, any> = reactive({
    effectiveTypeOptions: [],
    disabled: props.disabled,
  });

  const schemas: FormSchema[] = [
    {
      field: 'effectiveType',
      component: 'RadioGroup',
      label: t('iot.link.engine.linkage.effectiveType'),
      colProps: {
        span: 10,
      },
      rules: [
        {
          required: true,
          message: t('common.chooseText') + `${t('iot.link.engine.linkage.effectiveType')}`,
        },
      ],
    },
    {
      field: 'frequency',
      component: 'InputNumber',
      label: t('iot.link.engine.linkage.frequency'),
      colProps: {
        span: 10,
      },
      rules: [
        {
          required: true,
          message: t('common.inputText') + `${t('iot.link.engine.linkage.frequency')}(Second)`,
        },
      ],
    },
    {
      field: 'week',
      component: 'CheckboxGroup',
      label: t('iot.link.engine.linkage.week'),
      required: true,
      colProps: {
        span: 20,
      },
      componentProps: {
        options: [
          { label: '周一', value: 'monday' },
          { label: '周二', value: 'tuesday' },
          { label: '周三', value: 'wednesday' },
          { label: '周四', value: 'thursday' },
          { label: '周五', value: 'friday' },
          { label: '周六', value: 'saturday' },
          { label: '周日', value: 'sunday' },
        ],
      },
    },
    {
      field: 'startTime',
      component: 'TimePicker',
      label: t('iot.link.engine.linkage.startTime'),
      componentProps: {
        valueFormat: 'HH:mm',
        format: 'HH:mm',
      },
      required: true,
      colProps: {
        span: 10,
      },
    },
    {
      field: 'endTime',
      component: 'TimePicker',
      label: t('iot.link.engine.linkage.endTime'),
      componentProps: {
        valueFormat: 'HH:mm',
        format: 'HH:mm',
      },
      required: true,
      colProps: {
        span: 10,
      },
    },
  ];

  const [register, { updateSchema, setFieldsValue }] = useForm({
    labelWidth: 120,
    schemas,
  });

  onMounted(async () => {
    const dictList = getDictList(DictEnum.RULE_EFFECTIVE_TYPE);

    state.effectiveTypeOptions =
      dictList.length === 0 ? await initGetDictList(DictEnum.RULE_EFFECTIVE_TYPE) : dictList;

    // 确保表单已经渲染之后再设置值
    nextTick(() => {
      setFieldsValue({
        effectiveType: String(props.effectiveType),
      });

      const { frequency, week, timeframe } = props.appointContent;
      const weekSelected = week.filter((item: any) => item.checked).map((item: any) => item.eg);

      setFieldsValue({
        frequency: frequency,
        week: weekSelected,
        startTime: timeframe.startTime,
        endTime: timeframe.endTime,
      });
    });
  });

  watch(
    () => state.effectiveTypeOptions,
    (newOptions) => {
      updateSchema([
        {
          field: 'effectiveType',
          componentProps: {
            options: newOptions,
          },
        },
      ]);
    },
  );
</script>

<style scoped lang="less">
  .container {
    :deep(.ant-checkbox-disabled.ant-checkbox-checked) {
      .ant-checkbox-inner {
        background-color: #f5f5f5;
      }
    }

    .warn_title {
      font-family: 'Arial', sans-serif;
      font-size: 14px;
      color: red;
    }

    .ul_custom {
      .li_custom {
        font-family: 'Arial', sans-serif;
        font-size: 12px;

        .font_strong {
          font-weight: 600;
          color: #409eff;
        }
      }
    }
  }
</style>
