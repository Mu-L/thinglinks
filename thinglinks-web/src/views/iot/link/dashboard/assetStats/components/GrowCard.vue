<template>
  <div class="md:flex">
    <Card
      size="small"
      :title="t('iot.link.dashboard.assetStats.productCount')"
      class="md:w-1/4 w-full !md:mt-0 !md:mr-4"
    >
      <div class="card-content">
        <div class="card-item">
          <div class="main-num">
            {{ cardDetails?.productOverviewResultVO?.productsTotalCount }}
            <span class="unit">{{ t('iot.link.dashboard.assetStats.unit') }}</span>
            <div class="name">{{ t('iot.link.dashboard.assetStats.total') }}</div>
          </div>
        </div>

        <div class="card-img">
          <img :src="commonproduct" alt="" width="80" />
        </div>
        <div class="card-footer flex justify-between">
          <div
            >{{ t('iot.link.dashboard.assetStats.gatewayProducts') }}：
            <span class="num">{{
              cardDetails?.productOverviewResultVO?.gatewayProductsCount || 0
            }}</span>
            <span class="unit">{{ t('iot.link.dashboard.assetStats.unit') }}</span>
          </div>
          <div
            >{{ t('iot.link.dashboard.assetStats.ordinaryProduct') }}：
            <span class="num">{{
              cardDetails?.productOverviewResultVO?.ordinaryProductsCount || 0
            }}</span>
            <span class="unit">{{ t('iot.link.dashboard.assetStats.unit') }}</span>
          </div>
        </div>
      </div>
    </Card>
    <Card
      size="small"
      :title="t('iot.link.dashboard.assetStats.deviceCount')"
      class="md:w-1/4 w-full !md:mt-0 !md:mr-4"
    >
      <div class="card-content">
        <div class="card-item">
          <div class="contitem contitem2">
            <div class="num-txt"
              >{{ t('iot.link.dashboard.assetStats.ordinaryDevices') }}
              <span class="num"
                >{{ cardDetails?.deviceOverviewResultVO?.ordinaryCount || 0
                }}<span class="unit">{{ t('iot.link.dashboard.assetStats.unit') }}</span></span
              >
            </div>
          </div>
          <div class="contitem contitem2">
            <div class="num-txt">
              {{ t('iot.link.dashboard.assetStats.gatewayDevices') }}
              <span class="num"
                >{{ cardDetails?.deviceOverviewResultVO?.gatewayDeviceCount || 0
                }}<span class="unit">{{ t('iot.link.dashboard.assetStats.unit') }}</span></span
              >
            </div>
          </div>
          <div class="contitem contitem2">
            <div class="num-txt"
              >{{ t('iot.link.dashboard.assetStats.gatewaySubDevices') }}
              <span class="num"
                >{{ cardDetails?.deviceOverviewResultVO?.subDeviceCount || 0
                }}<span class="unit">{{ t('iot.link.dashboard.assetStats.taiwan') }}</span></span
              >
            </div>
          </div>
        </div>
        <div class="card-img card-img2">
          <img :src="commonDevice" alt="" width="80" />
        </div>

        <div class="card-footer flex justify-between">
          <span>{{ t('iot.link.dashboard.assetStats.deviceTotal') }}</span>
          <span class="num"
            >{{ cardDetails?.deviceOverviewResultVO?.totalDevicesCount || 0
            }}<span class="unit">{{ t('iot.link.dashboard.assetStats.taiwan') }}</span></span
          >
        </div>
      </div>
    </Card>
    <Card
      size="small"
      :title="t('iot.link.dashboard.assetStats.deviceStatus')"
      class="md:w-1/4 w-full !md:mt-0 !md:mr-4"
    >
      <div class="card-content">
        <dataLineChart :options="deviceRadar" height="100px" />

        <div class="card-footer flex justify-between">
          <div
            >{{ t('iot.link.dashboard.assetStats.online') }}：<span class="num">{{
              cardDetails?.deviceOverviewResultVO?.onlineCount || 0
            }}</span
            ><span class="unit">{{ t('iot.link.dashboard.assetStats.taiwan') }}</span></div
          >
          <div
            >{{ t('iot.link.dashboard.assetStats.offline') }}：<span class="num">{{
              cardDetails?.deviceOverviewResultVO?.offlineCount || 0
            }}</span
            ><span class="unit">{{ t('iot.link.dashboard.assetStats.taiwan') }}</span></div
          >
          <div
            >{{ t('iot.link.dashboard.assetStats.notConnected') }}：<span class="num">{{
              cardDetails?.deviceOverviewResultVO?.notConnectedCount || 0
            }}</span
            ><span class="unit">{{ t('iot.link.dashboard.assetStats.taiwan') }}</span></div
          >
        </div>
      </div>
    </Card>
    <Card
      size="small"
      :title="t('iot.link.dashboard.assetStats.deviceData')"
      class="md:w-1/4 w-full !md:mt-0"
    >
      <div class="card-content">
        <dataLineChart :options="uplinkDetails" height="100px" />
        <div class="card-footer flex justify-between">
          <div
            >{{ t('iot.link.dashboard.assetStats.upLink') }}：{{
              topologySummaryDetail?.totalUplinkData
            }}<span class="unit">{{ t('iot.link.dashboard.assetStats.article') }}</span></div
          >
          <div
            >{{ t('iot.link.dashboard.assetStats.downLink') }}：{{
              topologySummaryDetail?.totalDownlinkData
            }}<span class="unit">{{ t('iot.link.dashboard.assetStats.article') }}</span></div
          >
        </div>
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
  import commonproduct from '/@/assets/images/iot/link/deviceAndProduct/commonproduct.png';
  import commonDevice from '/@/assets/images/iot/link/deviceAndProduct/deviceTotal.png';
  // api
  import { assetSummary, topologySummary } from '/@/api/iot/link/dashboard/dashboard';

  export default defineComponent({
    name: 'GrowCard',
    components: {
      CountTo,
      Card,
      dataLineChart,
    },
    props: {},
    setup(props) {
      // 是否显示密码明文
      const { t } = useI18n();

      const detail = ref({});
      const topologyDetail = ref({});
      const loading = ref(false);

      const state = reactive({
        cardDetails: null,
        uplinkDetails: null,
        deviceRadar: null,
        topologySummaryDetail: null,
      });

      const initDetails = async () => {
        try {
          const detailsRes = await assetSummary();
          if (detailsRes) {
            detail.value = detailsRes;
            state.cardDetails = detail.value;
          }
          setDeviceRadar([
            {
              name: t('iot.link.dashboard.assetStats.online'),
              value: detail.value.deviceOverviewResultVO?.onlineCount,
            },
            {
              name: t('iot.link.dashboard.assetStats.offline'),
              value: detail.value.deviceOverviewResultVO?.offlineCount,
            },
            {
              name: t('iot.link.dashboard.assetStats.notConnected'),
              value: detail.value.deviceOverviewResultVO?.notConnectedCount,
            },
          ]);
        } catch (err) {
          console.log('资产统计查询顶部统计数据-非近3天', err);
        }
      };
      const initTopologyDetail = async () => {
        try {
          const topologySummaryRes = await topologySummary();
          if (topologySummaryRes) {
            topologyDetail.value = topologySummaryRes;
            state.topologySummaryDetail = topologySummaryRes;
            const uplinkDetails =
              topologyDetail.value.dashboardDetailsResultVo?.uplinkDetails || [];
            const downlinkDetails =
              topologyDetail.value.dashboardDetailsResultVo?.downlinkDetails || [];
            setUplinkDetails(
              uplinkDetails.map((item) => item.timeString),
              uplinkDetails.map((item) => item.value),
              downlinkDetails.map((item) => item.value),
            );
          }
        } catch (err) {
          console.log('资产统计查询顶部统计数据-近3天', err);
        }
      };

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
            show: false,
            data: x,
          },
          yAxis: {
            type: 'value',
            show: false,
          },
          grid: {
            top: '2%',
            bottom: 0,
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
      const setDeviceRadar = (arr: Array<any>): void => {
        state.deviceRadar = {
          tooltip: {
            trigger: 'item',
          },
          legend: {
            y: 'middle',
            x: 'left',
            width: 30,
            orient: 'vertical',
            // formatter: function (name) {
            //   let value = arr.find(item => item.name == name).value;
            //   return name + '：' + value;
            // }
          },
          grid: {
            top: 0,
            bottom: 0,
          },
          series: [
            {
              color: ['#5ab1ef', '#b6a2de', '#67e0e3', '#2ec7c9'],
              name: t('basic.system.baseChart.deviceStatus'),
              type: 'pie',
              left: '35%',
              radius: ['60%', '90%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 6,
                borderColor: '#fff',
                borderWidth: 2,
              },
              label: {
                show: false,
                position: 'center',
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '12',
                },
              },
              labelLine: {
                show: false,
              },
              data: arr,
              animationType: 'scale',
              animationEasing: 'exponentialInOut',
              animationDelay: function () {
                return Math.random() * 100;
              },
            },
          ],
        };
      };

      onMounted(async () => {
        loading.value = true;
        initDetails();
        initTopologyDetail();
        loading.value = false;
      });

      return {
        t,
        commonproduct,
        commonDevice,
        ...toRefs(state),
      };
    },
  });
</script>
<style lang="less" scoped>
  .ant-card-body {
    height: 100%;
  }

  .card-content {
    height: 100%;
    min-height: 140px;

    .main-num {
      font-weight: bold;
      font-size: 32px;
      padding: 10px 30px;

      .name {
        font-size: 14px;
      }
    }

    .num {
      font-weight: bold;
      font-size: 18px;
    }

    .contitem {
      padding-right: 100px;

      .num-txt {
        display: flex;
        align-items: center;
        justify-content: space-between;
      }

      // &.contitem2 {
      //   padding-bottom: 5px;
      // }

      span {
        font-weight: bold;
      }
    }
    // 图片定位到右边
    .card-img {
      position: absolute;
      left: 50%;
      top: 60px;

      &.card-img2 {
        left: auto;
        right: 10px;
      }
    }

    .card-footer {
      position: absolute;
      bottom: 0;
      left: 0;
      height: 40px;
      padding: 6px 10px;
      box-sizing: border-box;
      width: 100%;
      align-items: center;
      border-top: 1px solid #f0f0f0;
    }

    .unit {
      font-size: 14px;
      font-weight: normal !important;
    }
  }
</style>
