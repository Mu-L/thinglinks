<template>
  <div class="map-wrap" :loading="loading" style="position: relative;">
    <div ref="chartRef" style="width: 100%; height: 100vh;"></div>

    <!-- 弹窗 -->
    <div v-if="popup.visible" class="window-info" :style="{ left: popup.x + 'px', top: popup.y + 'px' }">
      <p class="map-item device-name">
        <Tooltip>
          <template #title>{{ device.deviceName }}</template>
          <span class="name" id="map-device-detail" @click="goDeviceDetail">
            {{ device.deviceName }}
          </span>
        </Tooltip>
        <i class="iconfont icon-youjiantou1" id="map-device-detail"></i>
        <span class="device-status" :class="deviceStatusClass">{{ deviceStatus }}</span>
      </p>
      <p class="map-item device-type">
        <span>{{ t('iot.link.device.device.nodeType') }}:&nbsp;&nbsp;</span>
        {{ device.deviceType }}
      </p>
      <p class="map-item device-identification">
        <span>{{ t('iot.link.device.device.deviceIdentification') }}:&nbsp;&nbsp;</span>
        {{ device.deviceIdentification }}
      </p>
      <p class="map-item device-location">
        <span>{{ t('iot.link.assetmap.assetmap.currentPosition') }}:&nbsp;&nbsp;</span>
        {{ device.deviceLocationResultVO.fullName }}
      </p>
      <Button @click="closePopup">{{ t('common.closeText') }}</Button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, reactive } from 'vue'
import * as echarts from 'echarts'
import 'echarts-extension-amap'
// util
import { useRouter } from 'vue-router'
import { useDict } from '/@/components/Dict'
import { useI18n } from '/@/hooks/web/useI18n';
// enum
import { DictEnum } from '/@/enums/commonEnum'
// api
import { getDeviceLocationPage } from '/@/api/iot/link/deviceLocation/deviceLocation'
import { detailBydeviceIdentification } from '/@/api/iot/link/device/device'
// component
import { Button, Tooltip } from 'ant-design-vue'

const { getDictLabel, initGetDictList } = useDict()

const { t } = useI18n();
const { push } = useRouter();

const chartRef = ref(null)
let chart = null
let amap = null

const loading = ref(false)

const popup = reactive({
  visible: false,
  x: 0,
  y: 0,
  lng: null,
  lat: null,
})

const device = ref({
  deviceLocationResultVO: {},
})
const deviceStatus = ref('')
const deviceStatusClass = ref('')

const state = reactive({
  current: 1,
  size: 10000,
  deviceLocationList: [],
  geoData: [],
  total: 0,
})

const object2Geojson = (list) => {
  return list
    .filter(d => d?.longitude && d?.latitude)
    .map(d => ({
      value: [
        Number(d.longitude),
        Number(d.latitude),
      ],
      raw: d,
    }))
}

const loadAMapScript = async (key) => {
  return new Promise((resolve, reject) => {
    if (window.AMap) return resolve(window.AMap)
    const script = document.createElement('script')
    script.src = `https://webapi.amap.com/maps?v=2.0&key=${key}`
    script.async = true
    script.onerror = reject
    script.onload = () => {
      window.AMap ? resolve(window.AMap) : reject(new Error('AMap 加载失败'))
    }
    document.head.appendChild(script)
  })
}

const closePopup = () => {
  popup.visible = false
  device.value = {};
  deviceStatus.value = '';
  deviceStatusClass.value = '';
}

const getDeviceInfo = async (params) => {
  try {
    loading.value = true;
    device.value = {};
    deviceStatus.value = '';
    deviceStatusClass.value = '';

    const { deviceIdentification } = params.data?.raw;
    const res = await detailBydeviceIdentification(deviceIdentification);
    device.value = {
      ...res,
      deviceType: getDictLabel('LINK_DEVICE_NODE_TYPE', res?.nodeType, ''),
    };
    deviceStatus.value = getDictLabel('LINK_DEVICE_STATUS', device.value?.deviceStatus, '');
    const deviceStatusClassObj = {
      1: 'primary',
      2: 'danger',
      0: 'default',
    };
    deviceStatusClass.value = deviceStatusClassObj[device.value?.deviceStatus];
    popup.visible = true;
  } catch (err) {
    console.error('assetmap index getDeviceInfo', err)
  }
  loading.value = false;
}

const goDeviceDetail = (id) => {
  const deviceId = device.value.id;
  if (deviceId) {
    push({
      name: '设备详情',
      params: { id: deviceId },
    });
  }
}

const initChart = async () => {
  chart = echarts.init(chartRef.value)
  chart.setOption({
    amap: {
      viewMode: '3D',
      pitch: 52,
      center: [116.3974, 39.9093],
      zoom: 10,
      resizeEnable: true,
    },
    tooltip: { show: false },
    series: [
      {
        name: '设备点',
        type: 'scatter',
        coordinateSystem: 'amap',
        symbolSize: 16,
        itemStyle: { color: '#4FC3F7' },
        data: [],
      },
    ],
  })

  amap = chart.getModel().getComponent('amap').getAMap()

  chart.on('click', params => {
    if (params.componentType === 'series') {
      const [lng, lat] = params.value
      popup.lng = lng
      popup.lat = lat

      const pixel = amap.lngLatToContainer([lng, lat])
      popup.x = pixel.x - 80
      popup.y = pixel.y - 200

      getDeviceInfo(params);
    }
  })

  amap.on('mapmove', () => {
    if (popup.visible && popup.lng && popup.lat) {
      const pixel = amap.lngLatToContainer([popup.lng, popup.lat])
      popup.x = pixel.x - 80
      popup.y = pixel.y - 200
    }
  })

  amap.on('click', e => {
    // 点击空白处关闭
    if (!e.target) {
      closePopup()
    }
  })
}

const updatePoints = () => {
  const geoData = state.geoData
  chart.setOption({
    series: [
      {
        name: '设备点',
        type: 'scatter',
        coordinateSystem: 'amap',
        symbolSize: 16,
        itemStyle: { color: '#4FC3F7' },
        data: geoData,
      },
    ],
  })
}

let isUnmounted = false;
const getDeviceLocationList = async () => {
  const res = await getDeviceLocationPage({
    current: state.current,
    size: state.size,
    model: {},
    extra: {},
  })

  const records = res.records || []
  const geoPoints = object2Geojson(records)

  state.deviceLocationList = state.deviceLocationList.concat(records)
  state.geoData = state.geoData.concat(geoPoints)
  state.total = parseInt(res.total)

  updatePoints()

  if (!isUnmounted && state.deviceLocationList.length < state.total) {
    state.current += 1
    await getDeviceLocationList()
  }
}

onBeforeUnmount(() => {
  isUnmounted = true
})

onMounted(async () => {
  loading.value = true
  initGetDictList(DictEnum.TENANT_AMAP__AAPPLICATION_JS_API_KEY)
    .then(async dictList => {
      const key = dictList.find(item => item.key === 'key')?.name ?? ''
      if (!key) {
        console.error('未获取到高德 Key')
        return
      }
      await loadAMapScript(key)
      await initChart()
      await getDeviceLocationList()
    })
    .finally(() => {
      loading.value = false
    })
})

onBeforeUnmount(() => {
  chart?.dispose()
})
</script>

<style lang="less" scoped>
.map-wrap {
  overflow: hidden;
}

.window-info {
  position: absolute;
  z-index: 999;
  background: white;
  padding: 8px 12px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
  border-radius: 4px;
  max-width: 400px;
  pointer-events: auto;
  text-align: end;
  color: @primary-color;

  .map-item {
    display: flex;
    margin-bottom: 10px;

    span {
      min-width: 70px;
      font-weight: 700;
    }
  }

  .device-name {
    justify-content: space-around;
    align-items: center;

    span {
      min-width: auto;
      font-weight: normal;
    }

    .name {
      display: inline-block;
      max-width: 70%;
      font-size: 20px;
      font-weight: 700;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .iconfont {
      font-size: 20px;
      font-weight: 700;
    }

    .device-status {
      flex: 1;
      text-align: end;
    }

    .primary {
      color: #43cf7c;
    }

    .danger {
      color: #fa3758;
    }

    .default {
      color: #808080;
    }

    .line {
      margin: 0 5px;
    }
  }

  .device-location {
    margin-bottom: 0;
  }

  #map-device-detail {
    cursor: pointer;
    color: @primary-color;
  }

  button {
    color: white;
    background-color: @primary-color;
    border-radius: 8px;
    font-size: 12px;
  }
}
</style>
