<template>
  <div class="scrollContainer" :key="currentTime" :style="{ height: `${height}px` }">
    <div
      class="scrollHead"
      :style="{
        height: headerHeight + 'px',
      }"
    >
      <div v-for="l in columns" :key="l.key" :style="{ width: `${l.width}px` }">
        {{ l.title }}
      </div>
      <div>
        {{ t('common.column.action') }}
      </div>
    </div>
    <ul class="scrollBody" ref="wrapperDom" :style="{ height: `${height - headerHeight}px` }">
      <ul ref="childDom1" @mouseenter="handleEnter" @mouseleave="handleLeave">
        <li
          v-for="(item, i) in dataSource"
          :data-key="rowKey ? item[rowKey] : `list${i}`"
          :key="rowKey ? item[rowKey] : `list${i}`"
          :style="{ height: `${rowHeight}px` }"
        >
          <div
            v-for="(p, c) in columns"
            :key="`p${c}`"
            :style="getStyle(p, item)"
            @click="
              (e) => {
                e.stopPropagation();
                onCellClick(item, p);
                onRowClick?.(item);
              }
            "
          >
            {{ p?.render?.(i, item, item[p.dataIndex]) || item[p.dataIndex] }}
          </div>
          <div @click="handleView(item)"> {{ t('common.title.view') }} </div>
          <!-- <a-button type="primary"> {{t('common.title.view')}} </a-button> -->
        </li>
      </ul>
      <ul ref="childDom2"></ul>
    </ul>
  </div>
</template>

<script setup lang="ts">
  import { onMounted, watch, ref, onBeforeUnmount, computed, nextTick } from 'vue';
  import { ruleAssetSummary } from '/@/api/iot/rule/dashboard/dashboard';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useDict } from '/@/components/Dict';
  const { getDictLabel } = useDict();
  import { useRouter } from 'vue-router';
  const { push, replace } = useRouter();
  const { t } = useI18n();
  const canResize = ref(true);
  const ruleAlarmList = ref([]);
  async function fetchDeviceOverView() {
    let res = await ruleAssetSummary();
    ruleAlarmList.value = res.ruleAlarmRecordResultVOList;
    ruleAlarmList.value.map((item) => {
      item.handledStatus = getDictLabel('RULE_ALARM_RECORD_HANDLED_STATUE', item?.handledStatus);
    });
  }
  fetchDeviceOverView();
  const columns = ref([
    {
      title: t('iot.link.engine.alarm.id'),
      width: 180,
      dataIndex: 'id',
    },
    {
      title: t('iot.link.engine.alarm.alarmIdentification'),
      width: 200,
      dataIndex: 'alarmIdentification',
    },
    {
      title: t('iot.link.engine.alarmRecord.occurredTime'),
      width: 150,
      dataIndex: 'occurredTime',
    },
    {
      title: t('iot.link.engine.alarmRecord.handlingNotes'),
      width: 150,
      dataIndex: 'handlingNotes',
    },
    {
      title: t('iot.link.engine.alarmRecord.handledStatus'),
      width: 100,
      dataIndex: 'handledStatus',
    },
  ]);
  interface ViewProps {
    height: number;
    headerHeight?: number;
    rowHeight?: number;
    onRowClick?: (l: Record<string, any>) => void;
    rowKey?: string;
    scroll?: boolean;
  }

  const props = defineProps<ViewProps>();
  const { height, rowHeight = 27.5, headerHeight = 36, rowKey } = props;

  const wrapperDom = ref<any>();
  const childDom1 = ref<any>();
  const childDom2 = ref<any>();
  const currentTime = ref(new Date().getTime());
  let count = 0;
  let reqAnimation: number;

  onMounted(() => {
    nextTick(() => {
      reqAnimation = window.requestAnimationFrame(taskStart);
    });
  });
  onBeforeUnmount(() => {
    handleEnter();
  });
  const dataSource = computed(() => {
    return ruleAlarmList.value;
  });
  watch(
    () => ruleAlarmList.value,
    () => {
      currentTime.value = new Date().getTime();
    },
  );

  const getStyle = (p, l) => {
    let pStyle = { width: `${p.width}px` };
    if (l.lineColor) {
      pStyle['color'] = l.lineColor;
    }
    return pStyle;
  };

  var startTime = null,
    stepInMs = 100,
    drawCount = 0;
  const taskStart = (timestamp: any) => {
    var progress;
    if (startTime === null) {
      startTime = timestamp;
    }
    progress = timestamp - startTime!;
    if (progress > stepInMs) {
      startTime = timestamp;
      if (
        childDom1.value?.clientHeight >= wrapperDom.value?.clientHeight &&
        childDom2.value?.clientHeight < 10
      ) {
        childDom2.value.innerHTML = childDom1.value.innerHTML;
      }
      if (wrapperDom.value?.scrollTop >= childDom1.value?.scrollHeight) {
        wrapperDom.value.scrollTop = 0;
        count = 0;
      } else {
        count += 1;
        wrapperDom.value.scrollTop = count;
      }
    }
    if (props.scroll) {
      reqAnimation = window.requestAnimationFrame(taskStart);
    }
  };

  const handleEnter = () => {
    window.cancelAnimationFrame(reqAnimation);
  };
  const handleLeave = () => {
    reqAnimation = window.requestAnimationFrame(taskStart);
  };
  const onCellClick = (l: Record<string, any>, p: TableColumn) => {
    p?.onClick?.(l);
  };
  // 弹出查看页面
  const handleView = (record: Recordable, e: Event) => {
    e?.stopPropagation();
    push({
      name: '告警记录详情',
      params: { id: record.id },
    });
  };
</script>

<style lang="less" scoped>
  .scrollContainer {
    width: 100%;

    div {
      text-align: center;
      display: inline-block;
      margin: 0;
      font-size: 14px;
      font-weight: normal;
      font-stretch: normal;
      letter-spacing: 0;
      opacity: 0.9;
    }

    .scrollHead {
      display: flex;
      align-items: center;
      background-color: #009688;

      div {
        font-size: 14px;
        font-stretch: normal;
        letter-spacing: 0;
        font-family: MicrosoftYaHei, sans-serif;
        font-weight: bold;
        color: #ffffff;
        opacity: 0.47;
      }
    }

    .scrollBody {
      overflow-y: scroll;
      width: 100%;
      padding: 0;
      scrollbar-width: none;
      -ms-overflow-style: none;

      ul {
        height: auto;
        padding: 0;
        margin: 0;
      }

      li {
        list-style: none;
        position: relative;
        cursor: pointer;
        display: flex;
        height: 100px;
        color: #fff;
        align-items: center;
      }

      li div {
        line-height: 100px;
        color: #24acef;
        white-space: nowrap; /* 文本不换行 */
        overflow: hidden; /* 溢出部分隐藏 */
        text-overflow: ellipsis; /* 溢出部分用"..."代替 */
      }

      li:hover {
        background: rgba(43, 143, 171, 0.52);

        > div {
          color: #fff;
        }
      }

      &::-webkit-scrollbar {
        display: none;
      }

      li:nth-child(even) {
        background-color: rgba(197, 210, 214, 0.13);
      }

      li:nth-child(even):hover {
        background: rgba(43, 171, 160, 0.52);
        color: #fff;
      }
    }
  }
</style>
