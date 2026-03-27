<template>
  <div :class="['input-item-' + type]" class="input-item">
    <!-- input -->
    <template v-if="type && type === 'input'">
      <a-input
        v-model:value="dataValue"
        :size="size"
        :value="state"
        clearable
        v-bind="$attrs"
        @input="inputValChange"
      ></a-input>
    </template>
    <!-- inputNumber -->
    <template v-if="type && type === 'inputNumber'">
      <a-input-number
        v-model:value="dataValue"
        :size="size"
        :value="state"
        clearable
        v-bind="$attrs"
        @input="inputValChange"
      ></a-input-number>
    </template>
    <!--  select  -->
    <template v-if="type && type === 'select'">
      <a-select
        v-model:value="dataValue"
        :size="size"
        :options="options"
        clearable
        v-bind="$attrs"
        @change="selectChange"
      >
      </a-select>
    </template>

    <!--  daterange  -->
    <template v-if="type && type === 'daterange'">
      <a-range-picker
        v-model:value="dataValue"
        :size="size"
        type="daterange"
        v-bind="$attrs"
        value-format="yyyy-MM-dd"
        @change="daterangeChange"
      ></a-range-picker>
    </template>
    <!--  date && dateTime{show-time}  -->
    <template v-if="type && type === 'date'">
      <a-date-picker
        v-model:value="dataValue"
        :size="size"
        clearable
        v-bind="$attrs"
        format="yyyy-MM-dd"
        value-format="yyyy-MM-dd"
        @change="dateChange"
      ></a-date-picker>
    </template>
  </div>
</template>
<script lang="ts">
  import { defineComponent, PropType, toRefs,reactive, watch } from 'vue';
  import { useDesign } from '/@/hooks/web/useDesign';
  import { useRuleFormItem } from '/@/hooks/component/useFormItem';

  export default defineComponent({
    name: 'InputItem',
    props: {
      type: {
        type: String as PropType<string>,
        default: 'input',
      },
      value:{
        type: [String, Number] as PropType<string | number>,
        default: '',
      }
    },
    emits: ['inputValChange', 'selectChange', 'daterangeChange', 'dateChange'],
    setup(props) {
      console.log(props.value)
      const state = reactive({
        type: props.type,
        dataValue: props.value,
      });
      watch(() => props.value, () => {
        state.dataValue = props.value
      }, { immediate: true })

      return { ...toRefs(state) };
    },
  });
</script>
<style lang="less">

</style>
