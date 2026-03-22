<template>
  <!-- <a-card style="width: 100%;height: calc(100vh - 100px)"> -->
  <div :loading="loading">
    <div
      :id="'container' + keyNum"
      class="map"
      style="width: 100%; height: calc(100vh - 100px)"
    ></div>
  </div>
  <!-- </a-card> -->
</template>
<script lang="ts">
  import {
    defineComponent,
    ref,
    reactive,
    watch,
    onMounted,
    nextTick,
    toRefs,
    onBeforeUnmount,
  } from 'vue';
  import { useRouter } from 'vue-router';

  import { LoadingOutlined } from '@ant-design/icons-vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import AMapLoader from '@amap/amap-jsapi-loader';
  import {
    getDeviceDetailsPage,
    detailBydeviceIdentification,
  } from '/@/api/iot/link/device/device';
  import icon1 from '/@/assets/images/iot/link/icon-status1.png';
  import icon2 from '/@/assets/images/iot/link/icon-status2.png';
  import icon3 from '/@/assets/images/iot/link/icon-status3.png';

  import { useDict } from '/@/components/Dict';
  import { DictEnum } from '/@/enums/commonEnum';

  const { getDictLabel, initGetDictList } = useDict();

  export default defineComponent({
    name: '资产地图',
    components: {
      LoadingOutlined,
    },
    setup() {
      const { push } = useRouter();

      const loading = ref(false);
      const state = reactive({
        deviceList: [],
        geoJson: {
          type: 'FeatureCollection',
          features: [],
        },
        subGeoJson: {
          type: 'FeatureCollection',
          features: [],
        },
        deviceInfos: {},
        current: 1,
        AMap: null,
      });
      const { t } = useI18n();

      onMounted(async () => {
        initMap();
        // getDeviceList();
      });

      const mapObj = reactive({
        map: null,
        geoCoder: null,
        geocoder: null,
        placeSearch: null,
        autoComplete: null,
        marker: null,
        linelayer: null,
      });
      const flag = ref(false);
      const keyNum = ref(parseInt(Math.random() * 100));

      const initMap = async (arr) => {
        state.AMap = await AMapLoader.load({
          key:
            (
              await initGetDictList(DictEnum.TENANT_AMAP__AAPPLICATION_JS_API_KEY)
            ).find((item) => item.key === 'key')?.name ?? '',
          version: '2.0',
          plugins: [
            'AMap.ToolBar',
            'AMap.ControlBar',
            'AMap.Marker',
            'AMap.Object3DLayer',
            'AMap.Object3D',
            'AMap.Icon',
            'AMap.MassMarks',
            'AMap.InfoWindow',
          ],
          Loca: {
            version: '2.0.0',
          },
        })
          .then((AMap) => {
            mapObj.map = new AMap.Map('container' + keyNum.value, {
              viewMode: '3D', //  是否为3D地图模式
              pitch: 52,
              zoom: 6,
              center: arr || [108.946651, 34.222718],
              resizeEnable: true,
              rotateEnable: true,
              pitchEnable: true,
              buildingAnimation: true,
              expandZoomRange: true,
              zooms: [3, 20],

              // mapStyle: 'amap://styles/blue',
            });
            // 地图放大缩小插件
            let toolBar = new AMap.ToolBar({
              position: {
                top: '120px',
                right: '51px',
              },
            });
            // 3D地图插件
            let controlBar = new AMap.ControlBar({
              position: {
                top: '20px',
                right: '20px',
              },
            });
            mapObj.map.addControl(toolBar); // 添加右上角的放大缩小
            mapObj.map.addControl(controlBar); // 添加右上角的放大缩小

            mapObj.loca = new Loca.Container({
              map: mapObj.map,
            });
          })
          .catch((e) => {
            console.error(e); //加载错误提示
          })
          .finally(() => {
            getDeviceList();
            loading.value = false;
          });
      };
      // 修改鼠标样式
      const changeCursor = (layer) => {
        //监听鼠标移动事件，如果移动到图层上的元素，就改变鼠标样式为手的样式
        mapObj.map.on('mousemove', (e) => {
          let features = layer.queryFeature(e.pixel.toArray());
          if (features) {
            mapObj.map.setDefaultCursor('pointer');
          } else {
            mapObj.map.setDefaultCursor('default');
          }
        });
      };

      const getDeviceList = async () => {
        const res = await getDeviceDetailsPage({
          current: state.current,
          extra: {},
          model: {},
          size: 1000,
        });
        state.deviceList = state.deviceList.concat(res.records);
        state.geoJson.features = state.geoJson.features.concat(object2Geojson(res.records));
        // console.log(state.geoJson)
        state.deviceInfos = res;

        setPoint();
        if (state.deviceList.length < parseInt(res.total)) {
          state.current += 1;
          getDeviceList();
        }
      };
      const setPoint = () => {
        // 设备点绘制
        let layer = new Loca.IconLayer({
          loca: mapObj.loca,
          zIndex: 1000,
          opacity: 1,
          visible: true,
          zooms: [2, 22],
        });
        let layerGeo = new Loca.GeoJSONSource({
          data: state.geoJson,
        });
        layer.setSource(layerGeo);
        layer.setStyle({
          unit: 'px',
          icon: (index, e) => {
            if (e.properties.connectStatus == 0) {
              // 未连接
              return icon1;
            } else if (e.properties.connectStatus == 1) {
              // 在线
              return icon2;
            } else if (e.properties.connectStatus == 2) {
              // 离线
              return icon3;
            } else {
              return icon1;
            }
            console.log(e);
          },
          iconSize: [20, 39],
          offset: [0, 25],
          anchor: [0.5, 1],
          rotation: 0,
        });
        state.geoJson.features.forEach((item) => {});
        mapObj.map.on('click', (e) => {
          const feat = layer.queryFeature(e.pixel.toArray());
          if (feat) {
            markerClick(feat);
          }
        });
        changeCursor(layer);
        mapObj.loca.add(layer);
        mapObj.loca.animate.start();
      };
      const object2Geojson = (data, center) => {
        let features = [];
        data.forEach((item) => {
          if (
            item.deviceLocationResultVO &&
            item.deviceLocationResultVO.longitude &&
            item.deviceLocationResultVO.latitude
          ) {
            let feature = { type: 'Feature' };
            feature.properties = item;
            if (!center) {
              let geometry = { type: 'Point' };
              geometry.coordinates = [
                item.deviceLocationResultVO.longitude,
                item.deviceLocationResultVO.latitude,
              ];
              feature.geometry = geometry;
            } else {
              let geometry = { type: 'LineString' };
              geometry.coordinates = [
                center,
                [item.deviceLocationResultVO.longitude, item.deviceLocationResultVO.latitude],
              ];
              feature.geometry = geometry;
            }
            features.push(feature);
          }
        });
        // console.log(features)
        return features;
      };

      const markerClick = async (feat) => {
        // 先移除图层
        mapObj.linelayer?.remove();
        mapObj.map.setCenter(feat.coordinates);
        // 为当前的feat信息，添加一个弹窗
        // 点击的时候以该点为中心点  地图zoom放大到18
        // console.log(feat.properties)
        // console.log(feat.properties);
        let deviceIdentification = feat.properties.deviceIdentification;
        // 子设备查询父设备  用来连线
        if (feat.properties.nodeType == 2 && feat.properties.gatewayId) {
          deviceIdentification = feat.properties.gatewayId;
        }

        if (!deviceIdentification || deviceIdentification === 'all') {
          return;
        }

        const deviceDetail = await detailBydeviceIdentification(deviceIdentification);
        // 网关子设备
        if (deviceDetail.subDeviceResultVOList?.length && feat.properties.nodeType != 0) {
          // 0 普通  1网关  2子设备
          if (feat.properties.nodeType == 1) {
            state.subGeoJson.features = object2Geojson(
              deviceDetail.subDeviceResultVOList,
              feat.coordinates,
            );
          } else {
            // 子设备需要查询付设备 并把关联的子设备查询回来并连线  信息窗口显示当前点击的子设备
            state.subGeoJson.features = object2Geojson(deviceDetail.subDeviceResultVOList, [
              deviceDetail.deviceLocationResultVO?.longitude,
              deviceDetail.deviceLocationResultVO?.latitude,
            ]);
          }

          const subSource = new Loca.GeoJSONSource({
            data: state.subGeoJson,
          });
          mapObj.linelayer = new Loca.PulseLineLayer({
            loca: mapObj.loca,
            zIndex: 11,
            opacity: 1,
            visible: true,
            zooms: [2, 22],
          });

          mapObj.linelayer.setStyle({
            altitude: 0,
            lineWidth: 3,
            headColor: '#ECFFB1',
            trailColor: 'rgba(20,105,104, 0.2)',
            interval: 0.25,
            duration: 5000,
          });
          mapObj.linelayer.setSource(subSource);
          mapObj.loca.add(mapObj.linelayer);
        }
        // 显示infowindow
        mapObj.infoWindow = new AMap.InfoWindow({
          content: createInfoWindowContent(feat.properties),
          offset: new AMap.Pixel(0, -38),
        });
        mapObj.map.setZoom(16);
        mapObj.infoWindow.open(mapObj.map, feat.coordinates);
        mapObj.infoWindow.on('close', removeGoDeviceDetailEvent);

        nextTick(() => {
          const goDetailEl = document.getElementById('map-device-detail');
          if (goDetailEl) {
            goDetailEl.addEventListener('click', (e) => {
              goDeviceDetail(e);
            });
          }
        });
      };
      const createInfoWindowContent = (obj) => {
        const deviceStatus = getDictLabel('LINK_DEVICE_STATUS', obj?.deviceStatus, '');
        const deviceStatusClassObj = {
          1: 'primary',
          2: 'danger',
          0: 'default',
        };
        const deviceStatusClass = deviceStatusClassObj[obj?.deviceStatus];
        const content = `
          <div class="windowInfo">
            <p
              class="map-item device-name"
            >
              <span
                class="name"
                id="map-device-detail"
                data-device-id=${obj.id}
              >
                ${obj.deviceName}
                <i class="iconfont icon-youjiantou1"></i>
              </span>
              <span class="device-status ${deviceStatusClass}">${deviceStatus}</span>
            </p>
            <p class="map-item device-type">
              <span>${t('iot.link.device.device.nodeType')}：</span>
              ${getDictLabel('LINK_DEVICE_NODE_TYPE', obj?.nodeType, '')}
            </p>
            <p class="map-item device-identification">
              <span>${t('iot.link.device.device.deviceIdentification')}：</span>
              ${obj.deviceIdentification}
            </p>
            <p
              class="map-item device-location"
            >
              <span>${t('iot.link.assetmap.assetmap.currentPosition')}：</span>
              ${obj.deviceLocationResultVO.fullName}
            </p>
          </div>`;
        return content;
      };
      //           <p class="map-item device-status">
      // <span>设备状态：</span>
      //   </p>
      const removeGoDeviceDetailEvent = () => {
        const goDetailEl = document.getElementById('map-device-detail');
        if (goDetailEl) {
          goDetailEl.removeEventListener('click', goDeviceDetail);
        }
      };

      const goDeviceDetail = (e) => {
        const deviceId = e.target.dataset.deviceId;
        if (deviceId) {
          push({
            name: '设备详情',
            params: { id: deviceId },
          });
        }
      };

      // 地图销毁
      function destroyMap() {
        mapObj.map && mapObj.map.destroy();
      }

      onBeforeUnmount(() => {
        console.log('地图销毁');
        destroyMap();
      });

      return {
        loading,
        t,
        mapObj,
        initMap,
        ...toRefs(state),
        keyNum,
      };
    },
  });
</script>
<style scoped lang="less">
  .map {
    width: 100%;
    height: 400px;
  }

  .search-box {
    position: absolute;
    z-index: 9;
    top: 20px;
    left: 20px;

    ::v-deep(.el-select) {
      width: 320px;
      border-radius: 50px;
      overflow: hidden;

      .el-input__wrapper {
        border-radius: 50px;
      }
    }
  }
</style>
<style lang="less">
  .amap-info-content {
    width: 400px;
    padding: 18px;
    border-radius: 20px;

    .amap-info-close {
      top: 15px;
      right: 15px;
      font-size: 20px;
    }

    .windowInfo {
      .map-item {
        display: flex;
        margin-bottom: 10px;

        span {
          min-width: 70px;
          font-weight: 700;
        }
      }

      .device-name {
        align-items: end;
        padding-right: 30px;

        span {
          min-width: auto;
          font-weight: normal;
        }

        .name {
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
    }
  }
</style>
../../../../../api/iot/link/device/device
