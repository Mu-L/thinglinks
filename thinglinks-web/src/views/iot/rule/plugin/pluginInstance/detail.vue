<template>
  <PageWrapper contentFullHeight>
    <div class="detail-info">
      <a-card title="" :bordered="false">
        <div class="device_title">
          <span>{{ pluginInstanceDetail.instanceName }}</span>
          <a-button
            type="primary"
            danger
            @click="handleEdit"
            v-hasAnyPermission="['rule:plugin:pluginInstance:edit']"
          >
            <template #icon> <EditOutlined /> </template
            >{{ t('iot.rule.plugin.pluginInstance.updateButton') }}
          </a-button>
        </div>
        <div class="base_data">
          <!-- <div class="item">
            <span>{{ t('iot.rule.plugin.pluginInstance.status') }}：</span>
            <span class="orange" v-if="pluginInstanceDetail?.status == 0">{{
              getDictLabel('RULE_PLUGIN_INSTANCE_STATUS', pluginInstanceDetail?.status, '')
            }}</span>
            <span class="orange" v-if="pluginInstanceDetail?.status == 2">{{
              getDictLabel('RULE_PLUGIN_INSTANCE_STATUS', pluginInstanceDetail?.status, '')
            }}</span>
            <span class="green" v-else-if="pluginInstanceDetail?.status == 1">{{
              getDictLabel('RULE_PLUGIN_INSTANCE_STATUS', pluginInstanceDetail?.status, '')
            }}</span>
            <span class="red" v-else-if="pluginInstanceDetail?.status == 3">{{
              getDictLabel('RULE_PLUGIN_INSTANCE_STATUS', pluginInstanceDetail?.status, '')
            }}</span>
          </div> -->
          <div class="item">
            <span>{{ t('iot.rule.plugin.pluginInstance.machineIp') }}：</span>
            <span>{{ pluginInstanceDetail.machineIp }}</span>
          </div>
          <div class="item">
            <span>{{ t('iot.rule.plugin.pluginInstance.instanceIdentification') }}：</span>
            <span>{{ pluginInstanceDetail.instanceIdentification }}</span>
          </div>
        </div>
      </a-card>
    </div>
    <div class="detail-info">
      <a-card title="" :bordered="false">
        <a-descriptions :title="t('iot.rule.plugin.pluginInstance.pluginInstanceInfo')" bordered>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.applicationName')"
          >
            {{ pluginInstanceDetail.applicationName }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="
              t('iot.rule.plugin.pluginInstance.portRangeStart') +
              '-' +
              t('iot.rule.plugin.pluginInstance.portRangeEnd')
            "
          >
            {{ pluginInstanceDetail.portRangeStart }} - {{ pluginInstanceDetail.portRangeEnd }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.machinePort')"
          >
            {{ pluginInstanceDetail.machinePort }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.weight')"
          >
            {{ pluginInstanceDetail.weight }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.healthy')"
          >
            {{ pluginInstanceDetail.healthy }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.enabled')"
          >
            {{ pluginInstanceDetail.enabled }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.ephemeral')"
          >
            {{ pluginInstanceDetail.ephemeral }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.clusterName')"
          >
            {{ pluginInstanceDetail.clusterName }}
          </a-descriptions-item>

          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.heartBeatInterval')"
          >
            {{ pluginInstanceDetail.heartBeatInterval }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.heartBeatTimeOut')"
          >
            {{ pluginInstanceDetail.heartBeatTimeOut }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.ipDeleteTimeOut')"
          >
            {{ pluginInstanceDetail.ipDeleteTimeOut }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.remark')"
          >
            {{ pluginInstanceDetail.remark }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.plugin.pluginInstance.extendParams')"
          >
            {{ pluginInstanceDetail.extendParams }}
          </a-descriptions-item>
        </a-descriptions>
      </a-card>
    </div>
    <div class="detail-info">
      <a-card :title="t('iot.rule.plugin.pluginInstance.serviceMonitoring')" :bordered="false">
        <a-tabs :default-active-key="monitorValue" @change="handleChange">
          <a-tab-pane key="1" tab="CPU">
            <a-row :gutter="[24, 12]">
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.cpu.cpuNum') }}</p>
                  <p class="conent"
                    ><CountTo
                      prefix=""
                      :color="'#8165ff'"
                      :startVal="0"
                      :endVal="serverMonitorData?.cpu?.cpuNum || 0"
                      :duration="3000"
                  /></p>
                </div>
              </a-col>
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.cpu.total') }}</p>
                  <p class="conent"
                    ><CountTo
                      prefix=""
                      :color="'#3982DC'"
                      :startVal="0"
                      :endVal="serverMonitorData?.cpu?.total || 0"
                      :duration="3000"
                  /></p>
                </div>
              </a-col>
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.cpu.wait') }}</p>
                  <p class="conent"
                    ><CountTo
                      prefix=""
                      :color="'#FFAF40'"
                      :startVal="0"
                      :endVal="serverMonitorData?.cpu?.wait || 0"
                      :duration="3000"
                  /></p>
                </div>
              </a-col>
            </a-row>
            <a-row :gutter="24">
              <a-col :span="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.cpu.sys') }}</p>
                  <dataLineChart :options="chartData?.sys" height="200px" />
                </div>
              </a-col>
              <a-col :span="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.cpu.used') }}</p>
                  <dataLineChart :options="chartData?.used" height="200px" />
                </div>
              </a-col>
              <a-col :span="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.cpu.free') }}</p>
                  <dataLineChart :options="chartData?.free" height="200px" />
                </div>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane key="2" :tab="t('iot.rule.plugin.pluginInstance.memory.title')">
            <a-row :gutter="[24, 12]">
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.memory.total') }}</p>
                  <p class="conent"
                    ><CountTo
                      prefix=""
                      :color="'#8165ff'"
                      :startVal="0"
                      :endVal="serverMonitorData?.mem?.total || 0"
                      :duration="3000"
                  /></p>
                </div>
              </a-col>
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.memory.used') }}</p>
                  <p class="conent"
                    ><CountTo
                      prefix=""
                      :color="'#3982DC'"
                      :startVal="0"
                      :endVal="serverMonitorData?.mem?.used || 0"
                      :duration="3000"
                  /></p>
                </div>
              </a-col>
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.memory.free') }}</p>
                  <p class="conent"
                    ><CountTo
                      prefix=""
                      :color="'#FFAF40'"
                      :startVal="0"
                      :endVal="serverMonitorData?.cpu?.free || 0"
                      :duration="3000"
                  /></p>
                </div>
              </a-col>
            </a-row>
            <a-row :gutter="24">
              <a-col :span="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.memory.usage') }}</p>
                  <dataLineChart :options="chartData.usage" height="200px" />
                </div>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane key="3" :tab="t('iot.rule.plugin.pluginInstance.server.title')">
            <a-row :gutter="[24, 12]">
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.server.computerName') }}</p>
                  <p class="conent">{{ serverMonitorData?.sys?.computerName }}</p>
                </div>
              </a-col>
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.server.computerIp') }}</p>
                  <p class="conent">{{ serverMonitorData?.sys?.computerIp }}</p>
                </div>
              </a-col>
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.server.userDir') }}</p>
                  <p class="conent" :title="serverMonitorData?.sys?.userDir">{{
                    serverMonitorData?.sys?.userDir
                  }}</p>
                </div>
              </a-col>
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.server.osName') }}</p>
                  <p class="conent">{{ serverMonitorData?.sys?.osName }}</p>
                </div>
              </a-col>
              <a-col :xs="24" :sm="24" :md="12" :lg="12" :xl="8" :xxl="8">
                <div class="shadow">
                  <p class="title">{{ t('iot.rule.plugin.pluginInstance.server.osArch') }}</p>
                  <p class="conent">{{ serverMonitorData?.sys?.osArch }}</p>
                </div>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane key="4" :tab="t('iot.rule.plugin.pluginInstance.jvm.title')">
            <a-descriptions bordered>
              <a-descriptions-item
                :labelStyle="labelStyle"
                :contentStyle="contentStyle"
                :label="t('iot.rule.plugin.pluginInstance.jvm.total')"
              >
                {{ serverMonitorData?.jvm?.total }}
              </a-descriptions-item>
              <a-descriptions-item
                :labelStyle="labelStyle"
                :contentStyle="contentStyle"
                :label="t('iot.rule.plugin.pluginInstance.jvm.max')"
              >
                {{ serverMonitorData?.jvm?.max }}
              </a-descriptions-item>
              <a-descriptions-item
                :labelStyle="labelStyle"
                :contentStyle="contentStyle"
                :label="t('iot.rule.plugin.pluginInstance.jvm.free')"
              >
                {{ serverMonitorData?.jvm?.free }}
              </a-descriptions-item>
              <a-descriptions-item
                :labelStyle="labelStyle"
                :contentStyle="contentStyle"
                :label="t('iot.rule.plugin.pluginInstance.jvm.usage')"
              >
                {{ serverMonitorData?.jvm?.usage }}
              </a-descriptions-item>
              <a-descriptions-item
                :labelStyle="labelStyle"
                :contentStyle="contentStyle"
                :label="t('iot.rule.plugin.pluginInstance.jvm.name')"
              >
                {{ serverMonitorData?.jvm?.name }}
              </a-descriptions-item>
              <a-descriptions-item
                :labelStyle="labelStyle"
                :contentStyle="contentStyle"
                :label="t('iot.rule.plugin.pluginInstance.jvm.version')"
              >
                {{ serverMonitorData?.jvm?.version }}
              </a-descriptions-item>
              <a-descriptions-item
                :labelStyle="labelStyle"
                :contentStyle="contentStyle"
                :label="t('iot.rule.plugin.pluginInstance.jvm.startTime')"
              >
                {{ serverMonitorData?.jvm?.startTime }}
              </a-descriptions-item>
              <a-descriptions-item
                :labelStyle="labelStyle"
                :contentStyle="contentStyle"
                :label="t('iot.rule.plugin.pluginInstance.jvm.runTime')"
              >
                {{ serverMonitorData?.jvm?.runTime }}
              </a-descriptions-item>
              <a-descriptions-item
                :labelStyle="labelStyle"
                :contentStyle="contentStyle"
                :label="t('iot.rule.plugin.pluginInstance.jvm.home')"
              >
                {{ serverMonitorData?.jvm?.home }}
              </a-descriptions-item>
              <a-descriptions-item
                :labelStyle="labelStyle"
                :contentStyle="contentStyle"
                :label="t('iot.rule.plugin.pluginInstance.jvm.inputArgs')"
              >
                {{ serverMonitorData?.jvm?.inputArgs }}
              </a-descriptions-item>
            </a-descriptions>
          </a-tab-pane>
          <a-tab-pane key="5" :tab="t('iot.rule.plugin.pluginInstance.disk.title')">
            <a-table
              :data-source="serverMonitorData?.sysFiles"
              :columns="detailColumns"
              :pagination="false"
            />
          </a-tab-pane>
          <!-- <a-tab-pane key="6" tab="拓扑图">
            <a-table
              :data-source="serverMonitorData?.sysFiles"
              :columns="detailColumns"
              :pagination="false"
            />
          </a-tab-pane> -->
        </a-tabs>
      </a-card>
    </div>
    <EditModal @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, ref, reactive, onMounted, watch } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { detail, ruleServerMonitor } from '/@/api/iot/rule/plugin/pluginInstance';
  import { PageWrapper } from '/@/components/Page';
  import { useRouter } from 'vue-router';
  import { Card, Row, Col, Descriptions, Tag, Tabs, Button, Table } from 'ant-design-vue';
  import { EditOutlined } from '@ant-design/icons-vue';
  import { useModal } from '/@/components/Modal';
  import { ActionEnum } from '/@/enums/commonEnum';
  import EditModal from './Edit.vue';
  import { detailColumns } from './pluginInstance.data';
  import { CountTo } from '/@/components/CountTo/index';
  import 'echarts/lib/chart/gauge';
  import dataLineChart from '/@/views/iot/link/dashboard/assetStats/components/dataLineChart.vue';
  import { useDict } from '/@/components/Dict';
  const { getDictLabel } = useDict();
  export default defineComponent({
    name: 'PluginInstanceDetail',
    components: {
      ACard: Card,
      ARow: Row,
      ACol: Col,
      ATag: Tag,
      ATable: Table,
      [Descriptions.name]: Descriptions,
      [Descriptions.Item.name]: Descriptions.Item,
      [Tabs.name]: Tabs,
      [Tabs.TabPane.name]: Tabs.TabPane,
      PageWrapper,
      AButton: Button,
      EditOutlined,
      EditModal,
      dataLineChart,
      CountTo,
    },
    emits: ['success', 'register'],
    setup() {
      // 是否显示密码明文
      const { t } = useI18n();
      const { currentRoute } = useRouter();
      const [registerModal, { openModal }] = useModal();
      let pluginInstanceDetail = reactive({});
      let id = ref('');
      const load = async () => {
        const res = await detail(id.value);
        pluginInstanceDetail = Object.assign(pluginInstanceDetail, res);
      };
      let currentKey = ref('1');
      onMounted(() => {
        const { params } = currentRoute.value;
        id.value = params.id as string;
        load();
      });
      // 弹出编辑页面
      function handleEdit(e: Event) {
        e?.stopPropagation();
        openModal(true, {
          record: pluginInstanceDetail,
          type: ActionEnum.EDIT,
        });
      }

      // 新增或编辑成功回调
      function handleSuccess() {
        load();
      }
      const monitorValue = ref('1');
      const serverMonitorData = reactive({});
      const chartData = reactive({});
      async function fetchRleServerMonitor() {
        const res = await ruleServerMonitor();
        Object.assign(serverMonitorData, res);
        chartData.sys = createCpuEchart(serverMonitorData.cpu.sys, 'cpu使用率');
        chartData.used = createCpuEchart(serverMonitorData.cpu.used, '用户使用率');
        chartData.free = createCpuEchart(serverMonitorData.cpu.free, '当前空闲率');
        chartData.usage = createCpuEchart(serverMonitorData.mem.usage, '使用率');
      }
      function createCpuEchart(value, name) {
        return {
          tooltip: {
            formatter: '{a} <br/>{b} : {c}%',
          },
          series: [
            {
              name: 'Pressure',
              type: 'gauge',
              radius: '100%',
              axisLine: {
                lineStyle: {
                  width: 10, // 设置仪表盘的宽度为10
                  color: [
                    [0.6, '#37a2da'], // 60以下为蓝色
                    [0.8, '#FFC107'], // 60-80为黄色
                    [1, '#FF0000'], // 80以上为红色
                  ],
                },
              },
              progress: {
                show: true,
                overlap: true,
                roundCap: true,
                width: 10,
                // itemStyle: {
                //   color: [
                //     [0.6, '#37a2da'], // 60以下为蓝色
                //     [0.8, '#FFC107'], // 60-80为黄色
                //     [1, '#FF0000'], // 80以上为红色
                //   ],
                // },
              },
              detail: {
                valueAnimation: true,
                fontSize: 17,
                formatter: '{value}%',
                color: 'auto',
              },
              data: [
                {
                  value: value,
                  name: name,
                },
              ],
            },
          ],
        };
      }
      fetchRleServerMonitor();
      return {
        t,
        pluginInstanceDetail,
        currentKey,
        getDictLabel,
        labelStyle: {
          maxWidth: '200px',
          'font-weight': '600',
          'font-size': '14px',
        },
        contentStyle: {
          'font-weight': '600',
          'font-size': '14px',
        },
        handleSuccess,
        handleEdit,
        registerModal,
        serverMonitorData,
        chartData,
        monitorValue,
        detailColumns,
      };
    },
  });
</script>
<style lang="less" scope>
  .detail-info {
    padding: 16px 16px 0;

    &:nth-last-child(1) {
      padding-bottom: 16px;
    }

    .device_title {
      font-size: 16px;
      font-family: PingFang SC-Medium, PingFang SC;
      font-weight: 600;
      color: #2e3033;
      line-height: 19px;
      margin-bottom: 10px;
      display: flex;
      justify-content: space-between;
    }

    .base_data {
      display: flex;
      align-items: center;
      font-size: 12px;
      color: #a6a6a6;
      line-height: 17px;

      .item {
        padding-right: 12px;

        & + .item {
          padding-left: 12px;
          border-left: 1px solid #e0e0e0;
        }

        span {
          &.red {
            color: #fa3758;
          }

          &.green {
            color: #43cf7c;
          }

          &.orange {
            color: #cfa543;
          }
        }
      }
    }

    .shadow {
      padding: 10px;
      box-shadow: 0px 0px 10px 1px rgba(0, 0, 0, 0.2);
      margin: 10px;
      display: flex;
      flex-direction: column;
      border-radius: 5px;

      .title {
        float: left;
      }

      .conent {
        text-align: center;
        margin: 5px 0;
        font-size: 20px;
        font-weight: bold;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
      }
    }
  }

  .ts-img {
    width: 100%;
  }
</style>
