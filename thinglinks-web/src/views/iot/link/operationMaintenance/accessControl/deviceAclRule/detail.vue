<template>
  <PageWrapper title="ACL规则详情" contentFullHeight>
    <div class="detail-info">
      <Card title="" :bordered="false">
        <div class="device_title">
          <div style="display: flex; align-items: center">
            <span>{{ aclRileDetail.ruleName }}</span>
          </div>
        </div>
        <div class="base_data">
          <div class="item">
            <span
              >{{
                t('iot.link.operationMaintenance.accessControl.deviceAclRule.ruleLevel')
              }}：</span
            >
            <span>{{ getDictLabel('LINK_ACL_RULE_LEVEL', aclRileDetail?.ruleLevel, '') }}</span>
          </div>
          <div class="item">
            <span
              >{{
                t('iot.link.operationMaintenance.accessControl.deviceAclRule.actionType')
              }}：</span
            >
            <span>{{
              getDictLabel('LINK_ACL_RULE_ACTION_TYPE', aclRileDetail?.actionType, '')
            }}</span>
          </div>
          <div class="item">
            <span
              >{{
                t('iot.link.operationMaintenance.accessControl.deviceAclRule.deviceIdentification')
              }}：</span
            >
            <span>{{ aclRileDetail.deviceIdentification }}</span>
          </div>
        </div>
      </Card>
    </div>
    <div class="detail-info">
      <Card title="" :bordered="false">
        <a-descriptions title="基本信息" bordered>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="
              t('iot.link.operationMaintenance.accessControl.deviceAclRule.productIdentification')
            "
          >
            {{ aclRileDetail.productIdentification }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.accessControl.deviceAclRule.priority')"
          >
            {{ aclRileDetail.priority }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.accessControl.deviceAclRule.topicPattern')"
          >
            {{ aclRileDetail.topicPattern }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.accessControl.deviceAclRule.ipWhitelist')"
          >
            {{ aclRileDetail.ipWhitelist }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.accessControl.deviceAclRule.decision')"
          >
            {{ aclRileDetail.decision }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.accessControl.deviceAclRule.enabled')"
          >
            {{ aclRileDetail?.enabled }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.accessControl.deviceAclRule.remark')"
          >
            {{ aclRileDetail.remark }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.accessControl.deviceAclRule.createdTime')"
          >
            {{ aclRileDetail.createdTime }}
          </a-descriptions-item>
        </a-descriptions>
      </Card>
    </div>
  </PageWrapper>
</template>
<script lang="ts" setup>
  import { ref, reactive, onMounted, defineComponent, h, PropType } from 'vue';
  import { useRouter } from 'vue-router';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { detail } from '/@/api/iot/link/operationMaintenance/accessControl/deviceAclRule';
  import { PageWrapper } from '/@/components/Page';
  import { EditOutlined } from '@ant-design/icons-vue';
  import { useDict } from '/@/components/Dict';
  import { useModal } from '/@/components/Modal';
  import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
  import { Tag, Card } from 'ant-design-vue';

  // 定义boolean渲染函数
  const RenderYesNoComponent = defineComponent({
    name: 'RenderYesNoComponent',
    props: {
      text: {
        type: [Boolean, null] as PropType<boolean | null>,
        required: true,
      },
      withTag: {
        type: Boolean,
        default: false,
      },
    },
  });

  const { t } = useI18n();
  const { currentRoute, push } = useRouter();

  const { getDictLabel } = useDict();
  let aclRileDetail = reactive({});
  const [registerModal, { openModal }] = useModal();
  let id = ref('');
  onMounted(() => {
    const { params } = currentRoute.value;
    id.value = params.id as string;
    load();
  });
  const load = async () => {
    const res = await detail(id.value);
    aclRileDetail = Object.assign(aclRileDetail, res);
  };
</script>
<style lang="less" scope>
  .detail-info {
    & + .detail-info {
      margin-top: 16px;
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

      .status {
        width: 15px;
        height: 15px;
        border-radius: 50%;
        margin-left: 10px;

        &.online {
          background-color: #00c100;
          color: #fff;
        }

        &.offline {
          background-color: #ff0000;
          color: #fff;
        }
      }
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
        }
      }
    }
  }
</style>
