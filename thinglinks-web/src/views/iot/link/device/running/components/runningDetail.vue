<template>
  <BasicDrawer
    v-bind="$attrs"
    :isDetail="true"
    width="80%"
    showFooter
    @register="register"
    :title="t('common.title.details')"
  >
    <Description
      :title="t('iot.link.device.device.basicInformation')"
      :column="2"
      :data="propertyMetadata"
      :schema="schema"
    />
    <div class="range-picker-wrap" ref="rangePickerWrap">
      <a-range-picker
        v-model:value="timeValue"
        style="width: 400px"
        show-time
        format="YYYY/MM/DD HH:mm:ss"
        valueFormat="YYYY/MM/DD HH:mm:ss"
        :presets="rangePresets"
        :getPopupContainer="getPopupContainerRef"
      />
      <a-button
        class="mar-l-10"
        type="primary"
        shape="circle"
        :icon="h(SearchOutlined)"
        @click="onRangeSearch"
      />
    </div>
    <a-button class="mar-l-10" type="primary" @click="resetTime"
      >重置为最近十分钟</a-button
    >
    <BasicTable
      :columns="columns"
      :dataSource="echoList"
      :canResize="true"
      :loading="loading"
      :striped="true"
      :bordered="true"
      :pagination="false"
      showTableSetting
    />
  </BasicDrawer>
</template>
<script lang="ts" setup>
import { useI18n } from '/@/hooks/web/useI18n';
// util
import { computed, ref, h } from 'vue';
import dayjs from 'dayjs';
// api
import { queryDeviceShadow } from '/@/api/iot/link/deviceShadow/deviceShadow';
// components
import { SearchOutlined } from '@ant-design/icons-vue';

import { BasicDrawer, useDrawerInner } from '/@/components/Drawer';
import { Description } from '/@/components/Description/index';

import { BasicTable } from '/@/components/Table';
import {
  getRunningDetailColumns,
  getRunningDetailSchema,
} from './runningDetailSchema';

const propertyMetadata = ref({});
const timeValue = ref([]);
const { t } = useI18n();
const [register, getVisible] = useDrawerInner((data) => {
  // 基本信息赋值
  propertyMetadata.value = data;
  resetTime();
});

const echoList = ref([]);
const loading = ref(false);

// 表格列
const columns = computed(() => {
  const { propertyCode, propertyName, unit } = propertyMetadata.value;
  return getRunningDetailColumns({
    title: `${propertyName}${unit ? `(${unit})` : ''}`,
    dataIndex: propertyCode,
  });
});

// 顶部基本信息字段
const schema = computed(() => {
  return getRunningDetailSchema();
});

const rangePickerWrap = ref(null);
const getPopupContainerRef = () => {
  return rangePickerWrap.value;
};
// 日期快捷方式
const rangePresets = ref([
  { label: 'Last 7 Days', value: [dayjs().add(-7, 'd'), dayjs()] },
  { label: 'Last 14 Days', value: [dayjs().add(-14, 'd'), dayjs()] },
  { label: 'Last 30 Days', value: [dayjs().add(-30, 'd'), dayjs()] },
  { label: 'Last 90 Days', value: [dayjs().add(-90, 'd'), dayjs()] },
]);

// 获取当前时间最近10分钟的起止时间戳
const getLastTenMinutes = () => {
  const now = new Date().getTime();
  const end = now;
  const start = now - 10 * 60 * 1000;
  return { startTime: start, endTime: end };
};

// 获取详细信息
const getDetailInfo = async () => {
  loading.value = true;
  try {
    const { deviceIdentification, serviceCode } = propertyMetadata.value;
    const params = {
      startTime: dayjs(timeValue.value[0]).valueOf() * 1000000,
      endTime: dayjs(timeValue.value[1]).valueOf() * 1000000,
      deviceIdentification,
      serviceCode,
    };

    const res = await queryDeviceShadow(params);
    if (res.services?.length) {
      const { serviceCode } = propertyMetadata.value;
      echoList.value =
        res.services.find((i) => i.serviceCode === serviceCode)?.echoList || [];
    }
    loading.value = false;
  } catch (error) {
    console.log(error);
    echoList.value = [];
    loading.value = false;
  }
};

const onRangeSearch = () => {
  if (timeValue.value.length) {
    const timeInfo = {
      startTime: dayjs(timeValue.value[0]).valueOf(),
      endTime: dayjs(timeValue.value[1]).valueOf(),
    };
    getDetailInfo(timeInfo);
  } else {
    console.log('Clear');
  }
};

// 重置为最近十分钟
const resetTime = () => {
  const timeInfo = getLastTenMinutes();
  let startTime = dayjs(timeInfo.startTime).format('YYYY/MM/DD HH:mm:ss');
  let endTime = dayjs(timeInfo.endTime).format('YYYY/MM/DD HH:mm:ss');
  timeValue.value = [startTime, endTime];
  getDetailInfo();
};
</script>
<style lang="less" scoped>
.mar-l-10 {
  margin-left: 10px;
}

.range-picker-wrap {
  display: inline-block;

  :deep(.ant-picker-ranges) {
    // display: none;
  }
}
</style>
