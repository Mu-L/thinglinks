<template>
  <div id="chart" ref="chartRef" :style="style"></div>
</template>

<script lang="ts" setup>
  import { ref, Ref, onMounted, computed } from 'vue';
  import { useECharts } from '../../../../src/hooks/web/useECharts';

  interface Props {
    height: string;
    width: string;
    // 图表背景色
    backgroundColor: string;
    // 图表标题
    text: string;
  }
  const props = defineProps<Props>();
  const style = computed(() => {
    return {
      height: props.height,
      width: '100%',
    };
  });

  const chartRef = ref<HTMLDivElement | null>(null);
  const { setOptions } = useECharts(chartRef as Ref<HTMLDivElement>);

  const option = {
    title: {
      text: 'ECharts Line Chart',
    },
    tooltip: {
      trigger: 'axis',
    },
    legend: {
      data: ['Sales'],
    },
    xAxis: {
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
    },
    yAxis: {},
    series: [
      {
        name: 'Sales',
        type: 'line',
        data: [820, 932, 901, 934, 1290, 1330, 1320],
      },
    ],
  };

  onMounted(() => {
    setOptions(option);
  });
</script>
