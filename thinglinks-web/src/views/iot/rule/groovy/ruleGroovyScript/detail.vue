<template>
  <PageWrapper contentFullHeight>
    <div class="detail-info">
      <a-card title="" :bordered="false">
        <div class="device_title">
          <span>{{ ruleGroovyScriptDetail.name }}</span>
        </div>
        <div class="base_data">
          <div class="item">
            <span>{{ t('iot.link.device.device.appId') }}:</span>
            <span>{{ ruleGroovyScriptDetail.appId }}</span>
          </div>
          <div class="item">
            <span>{{ t('iot.rule.groovy.ruleGroovyScript.enable') }}：</span>
            <span class="green" v-if="ruleGroovyScriptDetail?.enable == true">{{ t('thinglinks.common.enable') }}</span>
            <span class="red" v-else>{{ t('thinglinks.common.disable') }}</span>
          </div>
        </div>
      </a-card>
    </div>
    <div class="detail-info">
      <a-card title="" :bordered="false">
        <a-descriptions :title="t('iot.rule.groovy.ruleGroovyScript.details.basicInfo')" bordered>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.groovy.ruleGroovyScript.namespace')"
          >
            {{
              getDictLabel(
                'RULE_GROOVY_SCRIPT_NAMESPACE_TYPE',
                ruleGroovyScriptDetail?.namespace,
                '',
              )
            }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.groovy.ruleGroovyScript.platformCode')"
          >
            {{
              getDictLabel(
                'RULE_GROOVY_SCRIPT_PLATFORM_CODE',
                ruleGroovyScriptDetail?.platformCode,
                '',
              )
            }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.groovy.ruleGroovyScript.productCode')"
          >
            {{
              getDictLabel(
                'RULE_GROOVY_SCRIPT_PRODUCT_CODE',
                ruleGroovyScriptDetail?.productCode,
                '',
              )
            }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.groovy.ruleGroovyScript.channelCode')"
          >
            {{
              getDictLabel(
                'RULE_GROOVY_SCRIPT_CHANNEL_CODE',
                ruleGroovyScriptDetail?.channelCode,
                '',
              )
            }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.groovy.ruleGroovyScript.businessCode')"
          >
            {{
              getDictLabel(
                'RULE_GROOVY_SCRIPT_BUSINESS_CODE',
                ruleGroovyScriptDetail?.businessCode,
                '',
              )
            }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.groovy.ruleGroovyScript.businessIdentification')"
          >
            {{ ruleGroovyScriptDetail.businessIdentification }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.groovy.ruleGroovyScript.objectVersion')"
          >
            {{ ruleGroovyScriptDetail.objectVersion }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.groovy.ruleGroovyScript.remark')"
          >
            {{ ruleGroovyScriptDetail.remark }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.rule.groovy.ruleGroovyScript.extendParams')"
          >
            {{ ruleGroovyScriptDetail.extendParams }}
          </a-descriptions-item>
        </a-descriptions>
      </a-card>
    </div>
    <div class="detail-info">
      <a-card :title="t('iot.rule.groovy.ruleGroovyScript.details.editScript')" :bordered="false">
        <template #extra
          ><div style="display: flex; justify-content: center; align-items: center">
            <span>{{ t('iot.rule.groovy.ruleGroovyScript.details.scriptingLanguage') }}：</span>
            <a-select ref="select" v-model:value="language" style="width: 120px">
              <a-select-option value="Groovy">Groovy</a-select-option>
            </a-select>
          </div></template
        >
        <codemirror
          v-model="ruleGroovyScriptDetail.scriptContent"
          :autofocus="true"
          :extensions="scriptExtensions"
          :indent-with-tab="true"
          :style="{ height: '600px' }"
          :tab-size="2"
          placeholder="groovy 脚本"
        />
      </a-card>
    </div>
    <div class="detail-info">
      <a-card :bordered="false">
        <a-tabs :default-active-key="currentKey" v-model:activeKey="currentKey">
          <a-tab-pane key="1" :tab="t('iot.rule.groovy.ruleGroovyScript.details.analogInput')">
            <codemirror
              v-model="executeParams"
              :autofocus="true"
              :extensions="scriptExtensions"
              :indent-with-tab="true"
              :style="{ height: '300px' }"
              :tab-size="2"
              :placeholder="t('iot.rule.groovy.ruleGroovyScript.details.placeholder')"
            />
          </a-tab-pane>
          <a-tab-pane key="2" :tab="t('iot.rule.groovy.ruleGroovyScript.details.runningResult')">
            <div v-if="compileData.executionStatus">
              <p
                >{{ t('iot.rule.groovy.ruleGroovyScript.details.executionResult') }}：{{
                  compileData.executionStatus
                }}</p
              >
              <div style="display: flex; margin-bottom: 10px">
                <p style="min-width: 80px"
                  >{{ t('iot.rule.groovy.ruleGroovyScript.details.result') }}：</p
                >
                <codemirror
                  v-model="compileData.context"
                  :autofocus="true"
                  :extensions="scriptExtensions"
                  :indent-with-tab="true"
                  :style="{ height: '300px', width: '100%' }"
                  :tab-size="2"
              /></div>
              <div style="display: flex; margin-bottom: 10px" v-if="compileData.exception">
                <p style="min-width: 80px"
                  >{{ t('iot.rule.groovy.ruleGroovyScript.details.abnormalInfo') }}：</p
                >
                <codemirror
                  v-model="compileData.exception"
                  :autofocus="true"
                  :extensions="scriptExtensions"
                  :indent-with-tab="true"
                  :style="{ height: '300px', width: '100%' }"
                  :tab-size="2"
              /></div>
              <div style="display: flex" v-if="compileData.errorMessage">
                <p style="max-width: 80px"
                  >{{ t('iot.rule.groovy.ruleGroovyScript.details.customExceptionInfo') }}：</p
                >
                <codemirror
                  v-model="compileData.errorMessage"
                  :autofocus="true"
                  :extensions="scriptExtensions"
                  :indent-with-tab="true"
                  :style="{ height: '300px', width: '100%' }"
                  :tab-size="2"
              /></div>
            </div>
            <p v-else>{{ t('iot.rule.groovy.ruleGroovyScript.details.emptyResult') }}</p>
          </a-tab-pane>
        </a-tabs>
        <div style="margin-top: 10px">
          <a-button
            type="primary"
            style="margin-right: 10px"
            :disabled="!executeParams"
            @click="handleExecute"
            v-hasAnyPermission="['rule:groovy:ruleGroovyScript:mockDebug']"
            ><CaretRightOutlined />{{
              t('iot.rule.groovy.ruleGroovyScript.details.execution')
            }}</a-button
          >
          <a-button type="primary" @click="handleSave"
            ><FileDoneOutlined />{{ t('common.saveText') }}</a-button
          >
        </div>
      </a-card>
    </div>
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, ref, reactive, onMounted, watch } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { detail, update, runCompile } from '/@/api/iot/rule/groovy/ruleGroovyScript';
  import { PageWrapper } from '/@/components/Page';
  import { useRouter } from 'vue-router';
  import { Card, Select, Row, Col, Descriptions, Tag, Tabs, Button, Table } from 'ant-design-vue';
  import { CaretRightOutlined, FileDoneOutlined } from '@ant-design/icons-vue';
  import { useModal } from '/@/components/Modal';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum } from '/@/enums/commonEnum';
  import EditModal from './Edit.vue';
  import { CountTo } from '/@/components/CountTo/index';
  import 'echarts/lib/chart/gauge';
  import { Codemirror } from 'vue-codemirror';
  import { java } from '@codemirror/lang-java';
  import { oneDark } from '@codemirror/theme-one-dark';
  import dataLineChart from '/@/views/iot/link/dashboard/assetStats/components/dataLineChart.vue';
  import { useDict } from '/@/components/Dict';
  const { getDictLabel } = useDict();
  export default defineComponent({
    name: 'RuleGroovyScriptDetail',
    components: {
      ACard: Card,
      ASelect: Select,
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
      CaretRightOutlined,
      FileDoneOutlined,
      CountTo,
      Codemirror,
    },
    emits: ['success', 'register'],
    setup() {
      // 是否显示密码明文
      const { t } = useI18n();
      const { currentRoute } = useRouter();
      const { createMessage } = useMessage();
      const [registerModal, { openModal }] = useModal();
      let ruleGroovyScriptDetail = reactive({});
      let id = ref('');
      const language = ref('Groovy');
      const scriptContent = ref('');
      const executeParams = ref('');
      const scriptExtensions = [java(), oneDark];
      const load = async () => {
        const res = await detail(id.value);
        ruleGroovyScriptDetail = Object.assign(ruleGroovyScriptDetail, res);
      };
      let currentKey = ref('1');
      onMounted(() => {
        const { params } = currentRoute.value;
        id.value = params.id as string;
        load();
      });
      // 保存
      function handleSave() {
        update({ ...ruleGroovyScriptDetail });
        createMessage.success(t('common.tips.saveSuccess'));
      }
      // 执行
      let compileData = ref({});
      const handleExecute = async () => {
        try {
          const res = await runCompile({
            executeParams: executeParams.value,
            scriptContent: ruleGroovyScriptDetail.scriptContent,
          });
          currentKey.value = '2';
          compileData.value = res;
        } catch (error) {
          compileData.value = {};
        }
      };

      // 新增或编辑成功回调
      function handleSuccess() {
        load();
      }
      return {
        t,
        ruleGroovyScriptDetail,
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
        handleSave,
        registerModal,
        language,
        scriptExtensions,
        scriptContent,
        executeParams,
        compileData,
        handleExecute,
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
