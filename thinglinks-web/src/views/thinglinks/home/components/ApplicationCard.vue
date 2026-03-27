<template>
  <div class="system-app-list" :class="{ 'recommend-only': showRecommendOnly }">
    <!-- 推荐模式：仅显示应用列表 -->
    <template v-if="showRecommendOnly">
      <div class="recommend-app-container">
        <div class="recommend-header">
          <h3 class="recommend-title">{{
            t('thinglinks.home.application.recommendApplication')
          }}</h3>
          <span v-if="!showAllApps" class="recommend-subtitle">{{
            t('thinglinks.home.application.more')
          }}</span>
        </div>

        <div class="recommend-app-list" :class="{ 'show-all': showAllApps }">
          <div
            class="recommend-app-item"
            v-for="item in displayApplications"
            :key="item.id"
            @click="customClick(item)"
          >
            <Popover placement="top" :overlayStyle="{ 'max-width': '300px' }">
              <template #content>
                <span>{{ item.remark || '暂无描述' }}</span>
              </template>
              <div class="app-icon">
                <img :src="item.logoUrl" alt="" />
              </div>
              <div class="app-name">{{ item.name }}</div>
            </Popover>
          </div>
        </div>

        <div v-if="hasMoreItems" class="recommend-toggle">
          <Button type="link" @click="toggleShowAll" class="toggle-btn">
            <template #icon>
              <DownOutlined v-if="!showAllApps" />
              <UpOutlined v-else />
            </template>
            {{
              showAllApps
                ? `收起 (${totalApplications}个)`
                : `查看全部推荐 (${totalApplications}个)`
            }}
          </Button>
        </div>
      </div>
    </template>

    <!-- 完整模式：显示所有内容 -->
    <template v-else>
      <Row :gutter="16">
        <!-- 用户信息卡片 -->
        <Col :span="8">
          <Card class="message-card-item">
            <img class="more" src="../../../../assets/images/index/ddd@3x.png" alt="" />
            <div class="user-info">
              <AvatarPreview
                :errorTxt="getUserInfo?.nickName?.substring(0, 1)"
                :fileId="getUserInfo?.avatarId"
                :isDef="true"
                :style="{ 'margin-right': '0.5rem' }"
                @click="openProfile"
              />
              <div class="user-name">
                <span class="name">{{ getUserInfo.nickName }}</span>
                <span class="address">{{ getUserInfo.workDescribe }}</span>
              </div>
            </div>
          </Card>
        </Col>

        <!-- 应用列表卡片 -->
        <Col :span="8">
          <Card class="system-card-item">
            <div class="system-card-content">
              <div class="my-app">
                <div class="app-title-section">
                  <span>{{ t('thinglinks.home.application.myApplication') }}</span>
                  <span v-if="totalPages > 1" class="page-indicator">
                    {{ currentPage }}/{{ totalPages }}
                  </span>
                </div>

                <div class="app-list" :class="{ scrollable: useScrollMode }">
                  <div
                    class="app-item"
                    v-for="item in displayApplications"
                    :key="item.id"
                    @click="customClick(item)"
                  >
                    <Popover placement="topLeft" :overlayStyle="{ 'max-width': '300px' }">
                      <template #content>
                        <span>{{ item.remark }}</span>
                      </template>
                      <img :src="item.logoUrl" alt="" />
                      <span>{{ item.name }}</span>
                    </Popover>
                  </div>
                </div>

                <!-- 分页控制 -->
                <div v-if="!useScrollMode && totalPages > 1" class="pagination-controls">
                  <Button type="text" size="small" :disabled="currentPage === 1" @click="prevPage">
                    <LeftOutlined />
                  </Button>
                  <Button
                    type="text"
                    size="small"
                    :disabled="currentPage === totalPages"
                    @click="nextPage"
                  >
                    <RightOutlined />
                  </Button>
                </div>
              </div>
            </div>
          </Card>
        </Col>

        <!-- 应用指标卡片 -->
        <Col :span="8">
          <Card class="app-data-card-item">
            <div class="app-data-card-content">
              <span class="app-data-card-title">{{
                t('thinglinks.home.indicator.applicationIndicators')
              }}</span>
              <List :split="false">
                <template v-for="item in appInfoList" :key="item.title">
                  <ListItem>
                    <ListItemMeta>
                      <template #title>
                        <span class="app-data-title">
                          {{ item.title }}
                        </span>
                      </template>
                    </ListItemMeta>
                    <template #actions>
                      <Popover :overlayStyle="{ 'max-width': '300px' }" placement="topRight">
                        <template #content>{{ item.data }}</template>
                        <span class="app-data-info">
                          {{ item.data }}
                        </span>
                      </Popover>
                    </template>
                  </ListItem>
                </template>
              </List>
            </div>
          </Card>
        </Col>
      </Row>

      <!-- IoT 系统相关的图表 -->
      <Row v-if="applicationKey === 'iotSystem'" :gutter="16">
        <Col :span="8">
          <Card class="data-Statistics">
            <div class="data-Statistics-title">
              <span class="app-data-card-title">{{
                t('thinglinks.home.chart.onlineStatistics')
              }}</span>
            </div>
            <dataLineChart :options="deviceRadar" height="300px" />
          </Card>
        </Col>
        <Col :span="16">
          <Card class="data-Statistics">
            <div class="data-Statistics-title">
              <span class="app-data-card-title">
                {{ t('thinglinks.home.chart.upDown') }}
              </span>
              <span> ({{ t('thinglinks.home.chart.nearlyThreeDays') }}) </span>
            </div>
            <Row :gutter="40">
              <Col :span="2" />
              <Col :span="10">
                <Card class="type-card" :hoverable="true">
                  <span class="type">{{ t('thinglinks.home.chart.up') }}</span>
                  <div class="type-data">
                    <span class="num">{{ deviceLineData?.totalUplinkData }}</span>
                  </div>
                </Card>
              </Col>
              <Col :span="10">
                <Card class="type-card" :hoverable="true">
                  <span class="type">{{ t('thinglinks.home.chart.down') }}</span>
                  <div class="type-data">
                    <span class="num">{{ deviceLineData?.totalDownlinkData }}</span>
                  </div>
                </Card>
              </Col>
              <Col :span="2" />
            </Row>
            <dataLineChart :options="deviceLine" :height="'224px'" />
          </Card>
        </Col>
      </Row>

      <Row v-if="applicationKey === 'iotSystem'" :gutter="16">
        <Col :span="8">
          <Card class="data-Statistics">
            <div class="data-Statistics-title">
              <span class="app-data-card-title">{{
                t('thinglinks.home.chart.usableStatistics')
              }}</span>
            </div>
            <dataLineChart :options="deviceUsable" :height="'320px'" />
          </Card>
        </Col>
        <Col :span="16">
          <Card class="data-Statistics">
            <div class="data-Statistics-title">
              <span class="app-data-card-title">{{
                t('thinglinks.home.chart.alarmStatistics')
              }}</span>
            </div>
            <ScrollTable :height="300" :scroll="true" />
          </Card>
        </Col>
      </Row>

      <!-- 查看全部推荐应用弹窗 -->
      <Modal v-model:visible="showAllModal" title="全部推荐应用" width="800px" :footer="null">
        <div class="all-apps-grid">
          <div
            class="all-app-item"
            v-for="item in applicationList"
            :key="item.id"
            @click="handleModalAppClick(item)"
          >
            <img :src="item.logoUrl" alt="" />
            <div class="all-app-info">
              <span class="all-app-name">{{ item.name }}</span>
              <span class="all-app-remark">{{ item.remark || '暂无描述' }}</span>
            </div>
          </div>
        </div>
      </Modal>
    </template>
  </div>
</template>

<script lang="ts">
  import { defineComponent, onMounted, ref, computed, reactive } from 'vue';
  import {
    Card,
    Row,
    Col,
    List,
    ListItem,
    ListItemMeta,
    Popover,
    Button,
    Modal,
  } from 'ant-design-vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { AvatarPreview } from '/@/components/AvatarPreview';
  import { LeftOutlined, RightOutlined, UpOutlined, DownOutlined } from '@ant-design/icons-vue';
  import { useLoading } from '/@/components/Loading';
  import { usePermission } from '/@/hooks/web/usePermission';
  import { useRouter } from 'vue-router';
  import { useUserStore } from '/@/store/modules/user';
  import { DefApplicationResultVO } from '/@/api/devOperation/application/model/defApplicationModel';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { isUrl } from '/@/utils/is';
  import { router } from '/@/router';
  import { useTabs } from '/@/hooks/web/useTabs';
  import { propTypes } from '/@/utils/propTypes';
  import { getDefApp } from '/@/api/thinglinks/profile/userInfo';
  import { checkEmployeeHaveApplication } from '/@/api/thinglinks/common/oauth';
  import { assetSummary, topologySummary } from '/@/api/iot/link/dashboard/dashboard';
  import { useECharts } from '/@/hooks/web/useECharts';
  import { CountTo } from '/@/components/CountTo/index';
  import ScrollTable from './ScrollTable.vue';
  import dataLineChart from '/@/views/iot/link/dashboard/assetStats/components/dataLineChart.vue';
  import { asyncFindUrlById } from '/@/api/thinglinks/file/upload';
  // 图标组
  import app1 from '../../../../assets/images/index/427319430@3x.png';
  import app2 from '../../../../assets/images/index/427319433@3x.png';
  import app3 from '../../../../assets/images/index/427319435@3x.png';
  import app4 from '../../../../assets/images/index/427319432@3x.png';
  import app5 from '../../../../assets/images/index/427319434@3x.png';
  import { t } from 'vxe-table';

  export default defineComponent({
    components: {
      Card,
      Col,
      Row,
      List,
      ListItem,
      ListItemMeta,
      Popover,
      Button,
      Modal,
      AvatarPreview,
      dataLineChart,
      CountTo,
      ScrollTable,
      LeftOutlined,
      RightOutlined,
      UpOutlined,
      DownOutlined,
    },
    props: {
      title: propTypes.string.def(t('thinglinks.home.application.myApplication')),
      updateDef: propTypes.bool.def(false),
      description: propTypes.string.def(t('thinglinks.home.application.description')),
      api: {
        type: Function as PropType<PromiseFn>,
        default: null,
        required: true,
      },
      handleClick: {
        type: Function as PropType<() => void>,
        default: null,
      },
      showRecommendOnly: propTypes.bool.def(false),
      maxDisplayCount: propTypes.number.def(6),
    },
    emits: ['more'],
    setup: function (props, { emit }) {
      const { t } = useI18n();
      const { echarts } = useECharts();
      const { createMessage, createConfirm } = useMessage();
      const { refreshMenu } = usePermission();
      const { replace } = useRouter();
      const [openFullLoading, closeFullLoading] = useLoading({
        tip: t('common.loadingText'),
      });

      const applicationList = ref<DefApplicationResultVO[]>([]);
      const currentApp = ref({});
      const defApplicationId = ref<string>('');
      const loading = ref<boolean>(true);
      const userStore = useUserStore();

      // 推荐模式的展开/收起状态
      const showAllApps = ref<boolean>(false);
      const currentPage = ref<number>(1);
      const pageSize = ref<number>(6);
      const useScrollMode = ref<boolean>(false);
      const showAllModal = ref<boolean>(false);

      const totalApplications = computed(() => applicationList.value.length);

      const displayApplications = computed(() => {
        if (props.showRecommendOnly) {
          if (showAllApps.value) {
            return applicationList.value;
          } else {
            return applicationList.value.slice(0, props.maxDisplayCount);
          }
        } else {
          if (useScrollMode.value) {
            return applicationList.value;
          }
          const start = (currentPage.value - 1) * pageSize.value;
          const end = start + pageSize.value;
          return applicationList.value.slice(start, end);
        }
      });

      const totalPages = computed(() => {
        if (props.showRecommendOnly || useScrollMode.value) return 1;
        return Math.ceil(applicationList.value.length / pageSize.value);
      });

      const hasMoreItems = computed(() => {
        return props.showRecommendOnly && applicationList.value.length > props.maxDisplayCount;
      });

      const toggleShowAll = () => {
        showAllApps.value = !showAllApps.value;
      };

      const nextPage = () => {
        if (currentPage.value < totalPages.value) {
          currentPage.value++;
        }
      };

      const prevPage = () => {
        if (currentPage.value > 1) {
          currentPage.value--;
        }
      };

      const handleModalAppClick = () => {
        showAllModal.value = false;
        if (props.handleClick) {
          props.handleClick();
        }
      };

      const switchApp = async (item) => {
        userStore.setApplicationId(item.id as string);
        userStore.setApplicationName(item.name as string);
        userStore.setApplicationKey(item.appKey as string);
        await userStore.getUserInfoAction();
        const { closeAll } = useTabs(router);
        await closeAll();
        await refreshMenu();
        createMessage.success(
          t('thinglinks.home.application.switchApplication') + `：[${item.name}]`,
        );

        applicationKey.value = userStore.getApplicationKey || 'basicPlatform';
        setTimeout(async () => {
          location.reload();
        }, 200);
      };

      const getUserInfo = computed(() => {
        return userStore.getUserInfo;
      });

      const openProfile = () => {
        replace({
          name: 'profileIndex',
        });
      };

      async function handlerTurnToApplication(item: DefApplicationResultVO) {
        if (userStore.getApplicationId === item?.id) {
          createMessage.warn(
            t('thinglinks.home.application.currentlyIn') +
              `[${item.name}]，` +
              t('thinglinks.home.application.noSwitch'),
          );
          return;
        }
        if (!item || !item.id) {
          createMessage.error(t('thinglinks.home.application.selectSwitch'));
          return;
        }
        if (item.expirationTime) {
          if (new Date(item.expirationTime) < new Date()) {
            createMessage.warn(t('thinglinks.home.application.expiration'));
            return '';
          }
        }
        try {
          openFullLoading();

          const canJump = await checkEmployeeHaveApplication(item.id);
          if (!canJump) {
            createMessage.warn(t('thinglinks.home.application.noPermission'));
            return '';
          }

          const isOpen = item.url && isUrl(item.url);
          createConfirm({
            iconType: 'warning',
            content:
              t('thinglinks.home.application.confirm') +
              `${
                isOpen
                  ? t('thinglinks.home.application.jump')
                  : t('thinglinks.home.application.switch')
              }` +
              t('thinglinks.home.application.toApplication') +
              `：【${item.name}】， ` +
              t('thinglinks.home.application.reload'),
            onOk: async () => {
              if (isOpen) {
                window.open(item.url);
              } else {
                switchApp(item);
              }
            },
          });
        } finally {
          closeFullLoading();
        }
      }

      const customClick = props.handleClick ? props.handleClick : handlerTurnToApplication;

      // 应用指标
      const appInfoList = ref([
        {
          title: t('thinglinks.home.indicator.name'),
          data: '',
        },
        {
          title: t('thinglinks.home.indicator.description'),
          data: '',
        },
        {
          title: t('thinglinks.home.indicator.version'),
          data: '',
        },
        {
          title: t('thinglinks.home.indicator.createdTime'),
          data: '',
        },
        {
          title: t('thinglinks.home.indicator.updatedTime'),
          data: '',
        },
      ]);

      const appList = ref([app1, app2, app3, app4, app5]);
      const applicationKey = ref('');

      onMounted(async () => {
        try {
          applicationList.value = await props.api();
          if (props.updateDef && !props.showRecommendOnly) {
            const defApp = await getDefApp();
            defApplicationId.value = defApp?.id;
          }
          applicationKey.value = userStore.getApplicationKey || 'basicPlatform';

          if (applicationKey.value) {
            applicationList.value.map((appItem, index) => {
              const imgId = appItem?.echoMap?.DEF__APPLICATION__LOGO?.[0]?.id || '';
              if (imgId) {
                asyncFindUrlById(appItem?.echoMap?.DEF__APPLICATION__LOGO?.[0]?.id)
                  .then((res) => {
                    appItem.logoUrl = (res.data || appList.value[index]) as string;
                  })
                  .catch(() => {
                    appItem.logoUrl = appList.value[index];
                  });
              } else {
                appItem.logoUrl = appList.value[index];
              }
            });

            // 只在非推荐模式下设置当前应用信息和IoT数据
            if (!props.showRecommendOnly) {
              currentApp.value = applicationList.value.find(
                (item) => item.appKey === applicationKey.value,
              ) as {};

              if (currentApp.value && Object.keys(currentApp.value).length) {
                Object.keys(currentApp.value).map((key) => {
                  switch (key) {
                    case 'name':
                      appInfoList.value[0].data = currentApp.value[key];
                      break;
                    case 'introduce':
                      appInfoList.value[1].data = currentApp.value[key];
                      break;
                    case 'version':
                      appInfoList.value[2].data = currentApp.value[key];
                      break;
                    case 'createdTime':
                      appInfoList.value[3].data = currentApp.value[key];
                      break;
                    case 'updatedTime':
                      appInfoList.value[4].data = currentApp.value[key];
                      break;
                  }
                });
              }

              if (applicationKey.value === 'iotSystem') {
                fetchDeviceOverView();
              }

              if (applicationList.value.length > 9) {
                useScrollMode.value = true;
              }
            }
          }
        } finally {
          loading.value = false;
        }
      });

      // 设备概况统计信息（仅在非推荐模式需要）
      const deviceOverview = reactive({});
      const deviceRadar = ref({});
      const deviceUsable = ref({});
      const deviceLine = ref({});
      const deviceLineData = reactive({
        category: [],
        barData: [],
        lineData: [],
        totalData: [],
      });

      async function fetchDeviceOverView() {
        await Promise.all([fetchAssetSummaryData(), fetchTopologyData()]);
      }

      async function fetchAssetSummaryData() {
        try {
          const res = await assetSummary();
          Object.assign(deviceOverview, res);

          const radarData = [
            {
              name: t('thinglinks.home.chart.online'),
              value: res.deviceOverviewResultVO?.onlineCount,
            },
            {
              name: t('thinglinks.home.chart.offline'),
              value: res.deviceOverviewResultVO?.offlineCount,
            },
            {
              name: t('thinglinks.home.chart.notConnected'),
              value: res.deviceOverviewResultVO?.notConnectedCount,
            },
          ];
          setDeviceRadar(radarData);

          const usableData = [
            {
              name: t('thinglinks.home.chart.notActivatedCount'),
              value: res.deviceOverviewResultVO?.notActivatedCount,
            },
            {
              name: t('thinglinks.home.chart.activatedCount'),
              value: res.deviceOverviewResultVO?.activatedCount,
            },
            {
              name: t('thinglinks.home.chart.lockedCount'),
              value: res.deviceOverviewResultVO?.lockedCount,
            },
          ];
          setDeviceUsable(usableData);
        } catch (error) {
          console.error('fetchAssetSummaryData error:', error);
        }
      }

      async function fetchTopologyData() {
        try {
          const res = await topologySummary();
          deviceLineData.totalUplinkData = res?.totalUplinkData;
          deviceLineData.totalDownlinkData = res?.totalDownlinkData;
          deviceLineData.category = [];
          deviceLineData.lineData = [];
          deviceLineData.barData = [];
          deviceLineData.totalData = [];

          if (res.dashboardDetailsResultVo?.uplinkDetails) {
            res.dashboardDetailsResultVo.uplinkDetails.forEach((item) => {
              deviceLineData.category.push(item.timeString);
              deviceLineData.lineData.push(item.value);
            });
          }

          if (res.dashboardDetailsResultVo?.downlinkDetails) {
            res.dashboardDetailsResultVo.downlinkDetails.forEach((item) => {
              deviceLineData.barData.push(item.value);
            });

            deviceLineData.totalData = res.dashboardDetailsResultVo.downlinkDetails.map(
              (item, index) => {
                const uplinkValue = res.dashboardDetailsResultVo.uplinkDetails[index]?.value || 0;
                return item.value + uplinkValue;
              },
            );
          }

          setDeviceLine();
        } catch (error) {
          console.error('fetchTopologyData error:', error);
        }
      }

      const setDeviceRadar = (arr: Array<any>): void => {
        deviceRadar.value = {
          tooltip: {
            trigger: 'item',
          },
          legend: {
            y: 'bottom',
            x: 'center',
            orient: 'horizontal',
            width: '80%',
          },
          grid: {
            top: '20%',
            bottom: '20%',
          },
          series: [
            {
              color: ['#5ab1ef', '#b6a2de', '#67e0e3', '#2ec7c9'],
              name: t('basic.system.baseChart.deviceStatus'),
              type: 'pie',
              left: 'center',
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
                  fontSize: '14',
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

      const setDeviceLine = (): void => {
        deviceLine.value = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow',
              label: {
                show: true,
                backgroundColor: '#333',
              },
            },
          },
          legend: {
            data: [t('basic.system.baseChart.upLink'), t('basic.system.baseChart.downLink')],
            textStyle: {
              color: '#ccc',
            },
          },
          xAxis: {
            data: deviceLineData.category,
            axisLine: {
              lineStyle: {
                color: '#ccc',
              },
            },
          },
          yAxis: {
            splitLine: { show: false },
            axisLine: {
              lineStyle: {
                color: '#ccc',
              },
            },
          },
          series: [
            {
              name: t('basic.system.baseChart.upLink'),
              type: 'line',
              smooth: true,
              showAllSymbol: 'auto',
              symbol: 'emptyCircle',
              symbolSize: 15,
              data: deviceLineData.lineData,
            },
            {
              name: t('basic.system.baseChart.downLink'),
              type: 'bar',
              barWidth: 10,
              itemStyle: {
                borderRadius: 5,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: '#14c8d4' },
                  { offset: 1, color: '#43eec6' },
                ]),
              },
              data: deviceLineData.barData,
            },
            {
              name: t('basic.system.baseChart.total'),
              type: 'pictorialBar',
              symbol: 'rect',
              symbolRepeat: true,
              symbolSize: [12, 4],
              symbolMargin: 1,
              z: -10,
              data: deviceLineData.totalData,
            },
          ],
        };
      };

      const setDeviceUsable = (arr: Array<any>): void => {
        deviceUsable.value = {
          tooltip: {
            trigger: 'item',
          },
          series: [
            {
              name: t('basic.system.baseChart.deviceStatus'),
              type: 'pie',
              radius: '80%',
              center: ['50%', '50%'],
              color: ['#5ab1ef', '#b6a2de', '#67e0e3', '#2ec7c9'],
              data: arr.sort(function (a, b) {
                return a.value - b.value;
              }),
              roseType: 'radius',
              animationType: 'scale',
              animationEasing: 'exponentialInOut',
              animationDelay: function () {
                return Math.random() * 400;
              },
            },
          ],
        };
      };

      function handleMore() {
        emit('more');
      }

      return {
        t,
        applicationList,
        customClick,
        loading,
        defApplicationId,
        handleMore,
        appInfoList,
        appList,
        applicationKey,
        getUserInfo,
        openProfile,
        fetchDeviceOverView,
        deviceOverview,
        deviceRadar,
        deviceLineData,
        deviceUsable,
        deviceLine,
        currentPage,
        totalPages,
        displayApplications,
        nextPage,
        prevPage,
        useScrollMode,
        showAllModal,
        handleModalAppClick,
        hasMoreItems,
        totalApplications,
        showAllApps,
        toggleShowAll,
      };
    },
  });
</script>

<style lang="less" scoped>
  .system-app-list {
    padding: 0 16px;
    box-sizing: border-box;
    margin-bottom: 8px;

    // 推荐模式专用样式
    &.recommend-only {
      padding: 0;
      margin-bottom: 0;

      .recommend-app-container {
        background: #fff;
        border-radius: 12px;
        margin: 0 16px;
        padding: 20px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);

        .recommend-header {
          margin-bottom: 20px;

          .recommend-title {
            font-size: 20px;
            font-weight: 600;
            color: #312f61;
            margin: 0 0 8px 0;
          }

          .recommend-subtitle {
            color: #666;
            font-size: 14px;
          }
        }

        .recommend-app-list {
          display: grid;
          grid-template-columns: repeat(auto-fill, calc(15.666% - 16px));
          justify-content: center;
          justify-items: center;
          gap: 16px;
          max-height: 300px;
          overflow: hidden;

          &.show-all {
            max-height: 500px;
            overflow-y: auto;

            &::-webkit-scrollbar {
              width: 4px;
            }

            &::-webkit-scrollbar-track {
              background: #f1f1f1;
              border-radius: 4px;
            }

            &::-webkit-scrollbar-thumb {
              background: #bcbcd9;
              border-radius: 4px;
            }
          }

          .recommend-app-item {
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 12px 8px;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.2s ease;
            min-height: 90px;
            justify-content: center;

            &:hover {
              background: #f8f9fa;
              transform: translateY(-2px);
              box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }

            .app-icon {
              margin-bottom: 8px;

              img {
                width: 40px;
                height: 40px;
                object-fit: contain;
                margin: auto;
              }
            }

            .app-name {
              font-size: 12px;
              color: #333;
              text-align: center;
              line-height: 1.2;
              word-break: break-all;
              max-width: 100%;
            }
          }
        }

        .recommend-toggle {
          margin-top: 16px;
          text-align: center;
          border-top: 1px solid #f0f0f0;
          padding-top: 16px;

          .toggle-btn {
            color: #401ced;
            font-size: 14px;
            display: inline-flex;
            align-items: center;
            gap: 4px;

            .anticon {
              font-size: 12px;
            }
          }
        }
      }
    }

    // 完整模式样式
    .ant-col {
      margin: 20px 0px;

      .system-card-item,
      .app-data-card-item,
      .data-Statistics,
      .message-card-item {
        border-radius: 20px;
        height: 400px;

        .ant-card-body {
          height: 400px;
          margin: auto;

          .user-info {
            height: 400px;
          }
        }
      }

      .system-card-item {
        display: flex;
        align-items: center;

        .system-card-content {
          display: flex;
          align-items: center;
          justify-content: space-between;
          width: 100%;

          .my-app {
            display: flex;
            flex-direction: column;
            color: #312f61;
            height: 100%;
            width: 100%;

            .app-title-section {
              display: flex;
              justify-content: space-between;
              align-items: baseline;
              margin-bottom: 8px;

              span:first-child {
                font-size: 35px;
                font-weight: bold;
              }

              .page-indicator {
                color: #bcbcd9;
                font-size: 14px;
              }
            }

            .app-list {
              display: flex;
              flex-wrap: wrap;
              margin-top: 20px;
              flex: 1;
              gap: 8px;

              &.scrollable {
                overflow-y: auto;
                max-height: 260px;

                &::-webkit-scrollbar {
                  width: 4px;
                }

                &::-webkit-scrollbar-track {
                  background: #f1f1f1;
                  border-radius: 4px;
                }

                &::-webkit-scrollbar-thumb {
                  background: #bcbcd9;
                  border-radius: 4px;
                }
              }

              .app-item {
                width: calc(33.333% - 6px);
                min-height: 70px;
                margin-bottom: 8px;
                cursor: pointer;
                transition: transform 0.2s;

                &:hover {
                  transform: translateY(-2px);
                }

                span {
                  display: block;
                  display: flex;
                  align-items: center;
                  flex-direction: column;

                  img {
                    width: 40px;
                    height: 40px;
                    margin: auto;
                    object-fit: contain;
                  }

                  &:last-child {
                    font-size: 12px;
                    margin-top: 4px;
                    text-align: center;
                    line-height: 1.2;
                    word-break: break-all;
                  }
                }
              }
            }

            .pagination-controls {
              display: flex;
              justify-content: center;
              gap: 8px;
              margin-top: 12px;

              .ant-btn {
                width: 24px;
                height: 24px;
                min-width: 24px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 0;

                &:disabled {
                  opacity: 0.3;
                }
              }
            }
          }
        }
      }

      .app-data-card-content {
        .app-data-card-title {
          font-size: 25px;
          font-weight: bold;
          color: #312f61;
          margin-bottom: 15px;
        }

        .ant-list {
          margin-top: 35px;

          :deep(.ant-list-item-meta) {
            flex: auto;
          }
          .ant-list-item-action {
            flex: 1;
          }

          :deep(.app-data-title) {
            font-size: 16px;
            color: #bcbcd9;
          }

          :deep(.app-data-info) {
            color: #312f61;
            font-weight: bold;
            display: inline-block;
            // width: 150px;
            max-width: 160px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            text-align: end;
          }
        }
      }

      .data-Statistics-title {
        display: flex;
        align-items: flex-end;

        span:last-child {
          font-size: 18px;
          font-weight: bold;
          color: #05cd99;
          margin: 0 0 0 5px;
          padding-bottom: 3px;
        }

        span:first-child {
          font-size: 25px;
          font-weight: bold;
          color: #312f61;
          margin: 0 5px 0 0;
        }
      }
    }

    // 用户信息卡片样式
    .ant-col:nth-child(1) {
      .message-card-item {
        .ant-card-body {
          .more {
            position: absolute;
            right: 25px;
            top: 15px;
          }

          .user-info {
            width: 90%;
            display: flex;
            flex-direction: column;
            justify-content: space-around;
            align-items: center;
            padding: 20px 0;
            margin: auto;

            .ant-avatar {
              width: 130px;
              height: 130px;
            }

            .user-name {
              display: flex;
              flex-direction: column;

              span {
                text-align: center;
              }

              span:first-child {
                font-size: 28px;
                font-weight: bold;
              }
            }
          }
        }
      }
    }

    // 类型卡片样式
    :deep(.type-card) {
      background: #f4f7ff;
      border-radius: 15px;
      margin-bottom: 10px;

      .ant-card-body {
        padding: 12px 24px !important;

        .type {
          color: #bcbcd9;
        }

        .type-data {
          display: flex;
          align-items: flex-end;

          .num {
            font-size: 25px;
            font-weight: bold;
            color: #312f61;
          }
        }
      }
    }

    // 弹窗样式
    .all-apps-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
      gap: 16px;
      max-height: 60vh;
      overflow-y: auto;

      .all-app-item {
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 16px;
        border: 1px solid #f0f0f0;
        border-radius: 8px;
        cursor: pointer;
        transition: all 0.2s;

        &:hover {
          border-color: #401ced;
          transform: translateY(-2px);
        }

        img {
          width: 48px;
          height: 48px;
          object-fit: contain;
          margin-bottom: 8px;
        }

        .all-app-info {
          display: flex;
          flex-direction: column;
          align-items: center;

          .all-app-name {
            font-weight: 500;
            margin-bottom: 4px;
          }

          .all-app-remark {
            font-size: 12px;
            color: #666;
            text-align: center;
          }
        }
      }
    }
  }
</style>
