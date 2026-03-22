<template>
  <div class="hi"> Hi {{ nickName }}, </div>
  <div class="welcome_btns">
    <div class="welcome_txt">Welcome to ThingLinks!</div>
  </div>
  <div class="proclamation-line" style="display: flex">
    <img src="../../../../assets/images/index/Frame427319506@3x.png" alt="" />
    <img src="../../../../assets/images/index/Subtract@3x.png" alt="" />
    <img src="../../../../assets/images/index/Frame427319507@3x.png" alt="" />
  </div>
  <Row v-if="applicationKey == 'iotSystem'" class="top-card-list" :gutter="16">
    <Col :span="6">
      <Card class="top-card-item">
        <div class="card-text-content">
          <span>{{ t('thinglinks.home.deviceOverview.productsTotalCount') }}</span>
          <CountTo
            prefix=""
            :color="'#8165ff'"
            :startVal="0"
            :endVal="deviceOverview.productOverviewResultVO?.productsTotalCount || 0"
            :duration="3000"
          />
        </div>
        <img src="../../../../assets/images/index/card1@3.png" alt="" />
      </Card>
    </Col>
    <Col :span="6">
      <Card class="top-card-item">
        <img src="../../../../assets/images/index/card@4x.png" alt="" />
        <div class="card-text-content">
          <span>{{ t('thinglinks.home.deviceOverview.gatewayDeviceCount') }}</span>
          <CountTo
            prefix=""
            :color="'#8165ff'"
            :startVal="0"
            :endVal="deviceOverview.deviceOverviewResultVO?.gatewayDeviceCount || 0"
            :duration="3000"
          />
        </div>
        <img src="../../../../assets/images/index/card22.png" alt="" />
      </Card>
    </Col>
    <Col :span="6">
      <Card class="top-card-item">
        <img src="../../../../assets/images/index/Icon@3x.png" alt="" />
        <div class="card-text-content">
          <span>{{ t('thinglinks.home.deviceOverview.ordinaryCount') }}</span>
          <CountTo
            prefix=""
            :color="'#8165ff'"
            :startVal="0"
            :endVal="deviceOverview.deviceOverviewResultVO?.ordinaryCount || 0"
            :duration="3000"
          />
        </div>
        <img src="../../../../assets/images/index/card22.png" alt="" />
      </Card>
    </Col>
    <Col :span="6">
      <Card class="top-card-item">
        <div class="card-text-content">
          <span>{{ t('thinglinks.home.deviceOverview.subDeviceCount') }}</span>
          <CountTo
            prefix=""
            :color="'#409EFF'"
            :startVal="0"
            :endVal="deviceOverview.deviceOverviewResultVO?.subDeviceCount || 0"
            :duration="3000"
          />
        </div>
        <img src="../../../../assets/images/index/card22.png" alt="" />
      </Card>
    </Col>
  </Row>
</template>
<script lang="ts" setup>
  import { reactive } from 'vue';
  import { Card, Row, Col, Input } from 'ant-design-vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  const { t } = useI18n();
  // import { computed } from 'vue';
  // import { SearchOutlined } from '@ant-design/icons-vue';
  // import { AvatarPreview } from '/@/components/AvatarPreview';
  import { useUserStore } from '/@/store/modules/user';
  import { assetSummary } from '/@/api/iot/link/dashboard/dashboard';
  import { CountTo } from '/@/components/CountTo/index';
  const userStore = useUserStore();
  const applicationKey = userStore.getApplicationKey;
  // const userinfo = computed(() => userStore.getUserInfo);
  const { nickName } = userStore.getUserInfo;
  // 设备概况统计信息
  const deviceOverview = reactive({});

  const fetchDeviceOverView = async () => {
    let res = await assetSummary();
    Object.assign(deviceOverview, res);
  };
  if (applicationKey == 'iotSystem') {
    fetchDeviceOverView();
  }
</script>
<style lang="less" scoped>
  .hi {
    font-size: 14px;
    font-weight: bold;
    color: #707eae;
    line-height: 24px;
    padding: 0 20px;
  }

  .welcome_btns {
    padding: 0 20px;
    display: flex;
    justify-content: space-between;

    .welcome_txt {
      font-size: 34px;
      font-weight: bold;
      color: #2b3674;
      line-height: 42px;
    }

    .btns {
      display: flex;
      align-items: center;

      .btn {
        width: 30px;
        line-height: 30px;
        text-align: center;
        margin-right: 8px;

        img {
          width: 20px;
          height: 20px;
          vertical-align: middle;
        }
      }
    }
  }

  .proclamation-line {
    padding: 10px 16px;

    img:nth-child(1) {
      width: 45%;
    }

    img:nth-child(2) {
      width: 32%;
      margin-left: -4%;
    }

    img:nth-child(3) {
      width: 35%;
      margin-left: -3%;
    }
  }

  .top-card-list {
    display: flex;
    padding: 0 16px;
    box-sizing: border-box;

    .ant-col {
      margin: 20px 0px;

      .top-card-item {
        border-radius: 20px;

        :deep(.ant-card-body) {
          display: flex;
          justify-content: space-around;
          align-items: center;
          height: 140px;

          &::after,
          &::before {
            display: none;
          }

          .card-text-content {
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-evenly;

            span {
              white-space: nowrap;
            }

            span:last-child {
              font-size: 20px;
              font-weight: bold;
            }
          }
        }
      }
    }

    .ant-col:nth-child(1),
    .ant-col:nth-child(4) {
      .top-card-item {
        .ant-card-body {
          img {
            width: 30%;
          }
        }
      }
    }

    .ant-col:nth-child(2),
    .ant-col:nth-child(3) {
      .top-card-item {
        .ant-card-body {
          img:first-child {
            width: 25%;
          }

          img:last-child {
            width: 40%;
          }
        }
      }
    }

    .ant-col:nth-child(4) {
      .top-card-item {
        color: white;
        background: #8165ff;
      }
    }
  }
</style>
