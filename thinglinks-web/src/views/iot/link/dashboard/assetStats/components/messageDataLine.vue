<template>
  <div class="md">
    <Card size="small" :title="t('iot.link.dashboard.assetStats.deviceMessage')">
      <template #extra>
        <a-radio-group v-model:value="quickValue" button-style="solid" @change="quickClickChange">
          <a-radio-button :value="item.value" v-for="item in quickClickOptions" :key="item.value">{{
            item.label
          }}</a-radio-button>
        </a-radio-group>
        <a-range-picker
          v-model:value="rangeTime"
          @change="onChange"
          format="YYYY-MM-DD HH:mm:ss"
          valueFormat="YYYY-MM-DD HH:mm:ss"
          style="margin-left: 12px; width: 320px"
          :show-time="{ format: 'HH:mm:ss' }"
        >
          <template #suffixIcon>
            <SmileOutlined />
          </template>
        </a-range-picker>
      </template>
      <div class="card-content">
        <dataLineChart :options="uplinkDetails" height="460px" />
      </div>
    </Card>
  </div>
</template>
<script lang="ts">
  import { defineComponent, ref, reactive, onMounted, watch, toRefs } from 'vue';
  import { CountTo } from '/@/components/CountTo/index';
  import dataLineChart from './dataLineChart.vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { Icon } from '/@/components/Icon';
  import { Tag, Card } from 'ant-design-vue';
  import { assetStatsDetails } from '/@/api/iot/link/dashboard/dashboard';
  import dayjs from 'dayjs';

  export default defineComponent({
    name: 'GrowCard',
    components: {
      CountTo,
      Card,
      dataLineChart,
    },
    props: {
      loading: {
        loading: Boolean,
        default: true,
      },
      details: {
        type: Object,
        default: {},
      },
    },
    setup(props) {
      // 是否显示密码明文
      const { t } = useI18n();

      const state = reactive({
        loading: props.loading,
        cardDetails: null,
        uplinkDetails: null,
        deviceRadar: null,
        quickClickOptions: [
          { label: t('iot.link.dashboard.assetStats.hour'), value: 'hour' },
          { label: t('iot.link.dashboard.assetStats.day'), value: 'day' },
          { label: t('iot.link.dashboard.assetStats.week'), value: 'week' },
        ],
        quickValue: 'day',
        rangeTime: [],
      });
      onMounted(() => {
        quickClickChange();
      });

      const setUplinkDetails = (
        x: Array<any>,
        y1: Array<number>,
        y2: Array<number>,
        color: string,
      ): void => {
        state.uplinkDetails = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow',
            },
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: x,
          },
          yAxis: {
            type: 'value',
          },
          grid: {
            left: '4%',
            right: '3%',
            top: '2%',
            bottom: '5%',
          },
          series: [
            {
              name: t('basic.system.baseChart.upLinkMessage'),
              data: y1,
              type: 'line',
              smooth: true, // 是否平滑曲线
              symbolSize: 0, // 拐点大小
              color: '#F29B55',
              areaStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [
                    {
                      offset: 0,
                      color: '#FBBB87', // 100% 处的颜色
                    },
                    {
                      offset: 1,
                      color: '#FFFFFF', //   0% 处的颜色
                    },
                  ],
                  global: false, // 缺省为 false
                },
              },
            },
            {
              name: t('basic.system.baseChart.downLinkMessage'),
              data: y2,
              type: 'line',
              smooth: true, // 是否平滑曲线
              symbolSize: 0, // 拐点大小
              color: '#ADC6FF',
              areaStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [
                    {
                      offset: 0,
                      color: '#ADC6FF', // 100% 处的颜色
                    },
                    {
                      offset: 1,
                      color: '#FFFFFF', //   0% 处的颜色
                    },
                  ],
                  global: false, // 缺省为 false
                },
              },
            },
          ],
        };
      };

      const quickClickChange = (e: string): void => {
        let value = state.quickValue;
        if (e) {
          value = e.target.value;
          state.quickValue = value;
        }
        console.log(value);
        let endTime = dayjs(new Date()).valueOf();
        let startTime = getTimeByType(value);

        console.log(dayjs(startTime).format('YYYY-MM-DD HH:mm:ss'));
        console.log(dayjs(endTime).format('YYYY-MM-DD HH:mm:ss'));

        state.rangeTime = [
          dayjs(startTime).format('YYYY-MM-DD HH:mm:ss'),
          dayjs(endTime).format('YYYY-MM-DD HH:mm:ss'),
        ];
        getMessageData({
          endTime,
          startTime,
        });
      };
      const onChange = (e) => {
        console.log(e);
        if (e && e.length) {
          state.quickValue = null;
          let startTime = dayjs(e[0]).valueOf();
          let endTime = dayjs(e[1]).valueOf();
          getMessageData({
            endTime,
            startTime,
          });
        }
      };

      //   MINUTE("1m", "yyyyMMddHHmm", ChronoUnit.MINUTES),
      // HOUR("1h", "yyyyMMddHH", ChronoUnit.HOURS),
      // DAY("1d", "yyyyMMdd", ChronoUnit.DAYS),
      // MONTH("1M", "yyyyMM", ChronoUnit.MONTHS);

      const getMessageData = async (data) => {
        let _time = '1m';
        let limit = 60;
        const distance = data.endTime - data.startTime; // 毫秒间隔
        const hour = 60 * 60 * 1000; // 小时
        const days = hour * 24; // 天
        const months = days * 30; // 月
        const year = 365 * days; // 年

        if (distance <= hour + 10) {
          limit = 60;
        } else if (distance > hour && distance <= days) {
          _time = '1h';
          limit = 24;
        } else if (distance > days && distance < year) {
          limit = Math.abs(Math.ceil(distance / days)) + 1;
          _time = '1d';
        } else if (distance >= year) {
          limit = Math.abs(Math.floor(distance / months));
          _time = '1M';
        }
        const details2 = await assetStatsDetails({
          endTime: dayjs(data.endTime).format('YYYYMMDDHHmm'),
          limit: limit,
          startTime: dayjs(data.startTime).format('YYYYMMDDHHmm'),
          time: _time,
        });
        state.cardDetails = details2;
        const uplinkDetails = details2.uplinkDetails || [];
        const downlinkDetails = details2.downlinkDetails || [];
        setUplinkDetails(
          uplinkDetails.map((item) => item.timeString),
          uplinkDetails.map((item) => item.value),
          downlinkDetails.map((item) => item.value),
        );
      };
      const getTimeByType = (type: string) => {
        switch (type) {
          case 'hour':
            return dayjs().subtract(1, 'hours').valueOf();
          case 'week':
            return dayjs().subtract(7, 'days').valueOf();
          case 'month':
            return dayjs().subtract(29, 'days').valueOf();
          case 'year':
            return dayjs().subtract(365, 'days').valueOf();
          case 'day':
            return dayjs().subtract(24, 'hours').valueOf();
          default:
            return dayjs().startOf('day').valueOf();
        }
      };

      return {
        t,
        ...toRefs(state),
        quickClickChange,
        onChange,
      };
    },
  });
</script>
../../../../../../api/iot/link/dashboard/dashboard
