<template>
  <PageWrapper
    :title="t('iot.link.operationMaintenance.cacert.caCertLicense.detailTitle')"
    contentFullHeight
  >
    <div class="detail-info">
      <Card title="" :bordered="false">
        <div class="device_title">
          <div style="display: flex; align-items: center">
            <span>{{ caCertLicenseDetail.certName }}</span>
          </div>
        </div>
        <div class="base_data">
          <div class="item">
            <span
              >{{ t('iot.link.operationMaintenance.cacert.caCertLicense.serialNumber') }}：</span
            >
            <span>{{ caCertLicenseDetail?.serialNumber }}</span>
          </div>
          <div class="item">
            <span>{{ t('common.expirationDate') }}：</span>
            <span>{{ caCertLicenseDetail.notBefore }}-{{ caCertLicenseDetail?.notAfter }}</span>
          </div>
          <div class="item">
            <span>{{ t('iot.link.operationMaintenance.cacert.caCertLicense.state') }}：</span>
            <span class="green" v-if="caCertLicenseDetail?.state == 1">已颁发</span>
            <span class="red" v-else>{{
              caCertLicenseDetail.state == 0 ? '待完善' : '已撤销'
            }}</span>
          </div>
          <div class="item">
            <span>{{ t('iot.link.operationMaintenance.cacert.caCertLicense.createdTime') }}：</span>
            <span>{{ caCertLicenseDetail.createdTime }}</span>
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
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.commonName')"
          >
            {{ caCertLicenseDetail.commonName }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.organization')"
          >
            {{ caCertLicenseDetail.organization }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.organizationalUnit')"
          >
            {{ caCertLicenseDetail.organizationalUnit }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.countryName')"
          >
            {{ caCertLicenseDetail.countryName }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.provinceName')"
          >
            {{ caCertLicenseDetail.provinceName }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.localityName')"
          >
            {{ caCertLicenseDetail?.localityName }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.email')"
          >
            {{ caCertLicenseDetail?.email }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.licenseBase64')"
          >
          <div class="licenseBase64-item">
            <CopyOutlined
            @click="handleCopyTextV2(caCertLicenseDetail?.licenseBase64 || '')"
            />
            <div class="licenseBase64-content">{{ caCertLicenseDetail?.licenseBase64 }}</div>
          </div>
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.businessLicenseFileid')"
          >
            {{ caCertLicenseDetail?.businessLicenseFileid }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.algorithm')"
          >
            {{ caCertLicenseDetail?.algorithm }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.authorizationCertFileid')"
          >
            {{ caCertLicenseDetail?.authorizationCertFileid }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.param1')"
          >
            {{ caCertLicenseDetail?.param1 }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.param2')"
          >
            {{ caCertLicenseDetail?.param2 }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.remark')"
          >
            {{ caCertLicenseDetail.remark }}
          </a-descriptions-item>
          <a-descriptions-item
            :labelStyle="labelStyle"
            :contentStyle="contentStyle"
            :label="t('iot.link.operationMaintenance.cacert.caCertLicense.createdTime')"
          >
            {{ caCertLicenseDetail.createdTime }}
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
  import { detail } from '/@/api/iot/link/operationMaintenance/cacert/caCertLicense';
  import { PageWrapper } from '/@/components/Page';
  import { EditOutlined, CopyOutlined } from '@ant-design/icons-vue';
  import { useDict } from '/@/components/Dict';
  import { useModal } from '/@/components/Modal';
  import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
  import { Tag, Card } from 'ant-design-vue';
  import { handleCopyTextV2 } from '/@/utils/thinglinks/common.tsx';

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
  let caCertLicenseDetail = reactive({});
  const [registerModal, { openModal }] = useModal();
  let id = ref('');
  onMounted(() => {
    const { params } = currentRoute.value;
    id.value = params.id as string;
    load();
  });
  const load = async () => {
    const res = await detail(id.value);
    caCertLicenseDetail = Object.assign(caCertLicenseDetail, res);
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

    .licenseBase64-item{
      display: flex;
      align-items: center;

      .licenseBase64-content{
        max-width: 220px;
        height: 200px;
        max-height: 5.4em;
        line-height: 1.8em;
        margin-left: 8px;
        overflow: hidden;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 3;
        text-overflow: ellipsis;
      }
    }
  }
</style>
