<template>
  <PageWrapper dense contentFullHeight>
    <div class="sim-info">
      <a-row :gutter="20">
        <a-col :span="6">
          <a-card title="" :bordered="false">
            <div class="card-box">
              <!-- <img :src="Icon1" alt="" class="img" /> -->
              <div class="info-box">
                <div class="title">{{ t('card.sim.cardSimInfo.cardTatalNumber') }}</div>
                <div class="list">
                  <div class="item">
                    <div class="num" style="color: #ffbb54">{{
                      cardSimOverview?.totalCardsCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.cardTotal') }}</div>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
         <a-col :span="9">
          <a-card title="" :bordered="false">
            <div class="card-box">
              <!-- <img :src="Icon1" alt="" class="img" /> -->
              <div class="info-box">
                <div class="title">{{ t('card.sim.cardSimInfo.operatorCardNumber') }}</div>
                <div class="list">
                  <div class="item">
                    <div class="num" style="color: #0061ff">{{
                      cardSimOverview?.carrierTypeMobileCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.move') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: #6aa2fe">{{
                      cardSimOverview?.carrierTypeTelecomCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.telecom') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: rgba(0, 52, 255, 0.3)">{{
                      cardSimOverview?.carrierTypeUnicomCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.link') }}</div>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="9">
          <a-card title="" :bordered="false">
            <div class="card-box">
              <!-- <img :src="Icon1" alt="" class="img" /> -->
              <div class="info-box">
                <div class="title">{{ t('card.sim.cardSimInfo.typeOfUse') }}</div>
                <div class="list">
                  <div class="item">
                    <div class="num" style="color: rgba(52, 168, 83, 1)">{{
                      cardSimOverview?.useTypeNormalCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.puka') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: rgba(52, 168, 83, 0.8)">{{
                      cardSimOverview?.useTypeSharedPoolCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.sharedPool') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color:rgba(52, 168, 83, 0.5)">{{
                      cardSimOverview?.useTypeTrafficPoolCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.trafficPool') }}</div>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
        </a-row>
        <a-row :gutter="20">
        <a-col :span="6">
          <a-card title="" :bordered="false">
            <div class="card-box">
              <!-- <img :src="Icon1" alt="" class="img" /> -->
              <div class="info-box">
                <div class="title">{{ t('card.sim.cardSimInfo.cardType') }}</div>
                <div class="list">
                  <div class="item">
                    <div class="num" style="color: #ffbb54">{{
                      cardSimOverview?.cardTypePlugInCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.insertCard') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: #ffbb54">{{
                      cardSimOverview?.cardTypePatchCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.paterIC') }}</div>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="9">
          <a-card title="" :bordered="false">
            <div class="card-box">
              <!-- <img :src="Icon3" alt="" class="img" /> -->
              <div class="info-box">
                <div class="title">{{ t('card.sim.cardSimInfo.networkCardStatus') }}</div>
                <div class="list">
                  <div class="item">
                    <div class="num" style="color: #0061ff">{{
                      cardSimOverview?.statusActivatedCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.activated') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: #6aa2fe">{{
                      cardSimOverview?.statusPendingActivationCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.notActivated') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: rgba(0, 52, 255, 0.3)">{{
                      cardSimOverview?.statusDeactivatedCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.halt') }}</div>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="9">
          <a-card title="" :bordered="false">
            <div class="card-box">
              <!-- <img :src="Icon2" alt="" class="img" /> -->
              <div class="info-box">
                <div class="title">{{ t('card.sim.cardSimInfo.connectionStatus') }}</div>
                <div class="list">
                  <div class="item">
                    <div class="num" style="color: rgba(52, 168, 83, 1)">{{
                      cardSimOverview?.onlineFlagOnlineCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.onLine') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: rgba(52, 168, 83, 0.8)">{{
                      cardSimOverview?.onlineFlagOfflineCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.offLine') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: rgba(52, 168, 83, 0.5)">{{
                      cardSimOverview?.notConnectedCount || 0
                    }}</div>
                    <div class="name">{{ t('card.sim.cardSimInfo.ununited') }}</div>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </div>
    <BasicTable
      @register="registerTable"
      @switch-change="getSwitchChange"
      :is-iot-sim-card="true"
      :switchFlag="switchFlag"
    >
      <template #toolbar>
        <a-button
          type="primary"
          color="error"
          preIcon="ant-design:delete-outlined"
          @click="handleBatchDelete"
          v-hasAnyPermission="['card:sim:cardSimInfo:delete']"
        >
          {{ t('common.title.delete') }}
        </a-button>
        <a-button
          type="primary"
          preIcon="ant-design:plus-outlined"
          @click="handleAdd"
          v-hasAnyPermission="['card:sim:cardSimInfo:add']"
        >
          {{ t('common.title.add') }}
        </a-button>
        <a-button preIcon="ant-design:swap-outlined" @click="switchView"
          >{{ t('card.channel.cardChannelInfo.switchView') }}
        </a-button>
      </template>
       <template #cardType="{ record }">
        {{ getDictLabel('CARD_SIMINFO_CARD_TYPE', record?.cardType, '') }}
      </template>
       <template #smsFlag="{ record }">
        {{ getDictLabel('CARD_SIMINFO_SMSFLAG', record?.smsFlag, '') }}
      </template>
       <template #gprsFlag="{ record }">
        {{ getDictLabel('CARD_SIMINFO_GPRSFLAG', record?.gprsFlag, '') }}
      </template>
       <template #carrierType="{ record }">
        {{ getDictLabel('CARD_SIMINFO_CARRIER_TYPE', record?.carrierType, '') }}
      </template>
       <template #status="{ record }">
        {{ getDictLabel('CARD_SIMINFO_STATUS', record?.status, '') }}
      </template>
       <template #useType="{ record }">
        {{ getDictLabel('CARD_SIMINFO_USETYPE', record?.useType, '') }}
      </template>
       <template #onlineFlag="{ record }">
        {{ getDictLabel('CARD_SIMINFO_ONLINE_FLAG', record?.onlineFlag, '') }}
      </template>
       <template #stopCardType="{ record }">
        {{ getDictLabel('CARD_SIMINFO_STOP_CARD_TYPE', record?.stopCardType, '') }}
      </template>
       <template #limitFlag="{ record }">
        {{ getDictLabel('CARD_SIMINFO_LIMIT_FLAG', record?.limitFlag, '') }}
      </template>
       <template #limitStatus="{ record }">
        {{ getDictLabel('CARD_SIMINFO_LIMIT_STATUS', record?.limitStatus, '') }}
      </template>
       <template #searchableStatus="{ record }">
        {{ getDictLabel('CARD_SIMINFO_SEARCHABLE_STATUS', record?.searchableStatus, '') }}
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <TableAction
            :actions="[
              {
                tooltip: t('common.title.view'),
                icon: 'ant-design:search-outlined',
                onClick: handleView.bind(null, record),
                auth: 'card:sim:cardSimInfo:view',
              },
              {
                tooltip: t('common.title.edit'),
                icon: 'ant-design:edit-outlined',
                onClick: handleEdit.bind(null, record),
                auth: 'card:sim:cardSimInfo:edit',
              },
              {
                tooltip: t('common.title.copy'),
                icon: 'ant-design:copy-outlined',
                onClick: handleCopy.bind(null, record),
                auth: 'card:sim:cardSimInfo:copy',
              },
              {
                tooltip: t('common.title.delete'),
                icon: 'ant-design:delete-outlined',
                color: 'error',
                auth: 'card:sim:cardSimInfo:delete',
                popConfirm: {
                  title: t('common.tips.confirmDelete'),
                  confirm: handleDelete.bind(null, record),
                },
              },
            ]"
            :stopButtonPropagation="true"
          />
        </template>
      </template>
    </BasicTable>
    <EditModal @register="registerModal" @success="handleSuccess" />
  </PageWrapper>
</template>
<script lang="ts">
import { defineComponent, ref, reactive, watch } from 'vue';
import { useI18n } from '/@/hooks/web/useI18n';
import { useMessage } from '/@/hooks/web/useMessage';
import { BasicTable, useTable, TableAction } from '/@/components/Table';
import { PageWrapper } from '/@/components/Page';
import { Card, Row, Col, Tag, Tabs, Tooltip } from 'ant-design-vue';
import { useModal } from '/@/components/Modal';
import { handleFetchParams } from '/@/utils/thinglinks/common';
import { ActionEnum } from '/@/enums/commonEnum';
import { page, remove, overview, deleteSingle } from '/@/api/card/sim/cardSimInfo';
import { columns, searchFormSchema } from './cardSimInfo.data';
import { useRouter } from 'vue-router';
import EditModal from './Edit.vue';
import { useDict } from '/@/components/Dict';
const { getDictLabel } = useDict();

export default defineComponent({
  // 若需要开启页面缓存，请将此参数跟菜单名保持一致
  name: 'SIM卡列表',
  components: {
    ACard: Card,
    ARow: Row,
    ACol: Col,
    ATag: Tag,
    [Tabs.name]: Tabs,
    [Tabs.TabPane.name]: Tabs.TabPane,
    Tooltip,
    BasicTable,
    PageWrapper,
    TableAction,
    EditModal,
  },
  setup() {
    const { t } = useI18n();
    const { createMessage, createConfirm } = useMessage();
    const [registerModal, { openModal }] = useModal();
    const { replace } = useRouter();
    const switchFlag = ref<boolean>(true);

    // 表格
    const [registerTable, { reload, getSelectRowKeys }] = useTable({
      title: t('card.sim.cardSimInfo.table.title'),
      api: page,
      columns: columns(),
      immediate: false,
      formConfig: {
        name: 'CardSimInfoSearch',
        labelWidth: 120,
        schemas: searchFormSchema(),
        autoSubmitOnEnter: true,
        resetButtonOptions: {
          preIcon: 'ant-design:rest-outlined',
        },
        submitButtonOptions: {
          preIcon: 'ant-design:search-outlined',
        },
      },
      beforeFetch: handleFetchParams,
      useSearchForm: true,
      showTableSetting: true,
      bordered: true,
      rowKey: 'id',
      rowSelection: {
        type: 'checkbox',
        columnWidth: 40,
      },
      actionColumn: {
        width: 200,
        title: t('common.column.action'),
        dataIndex: 'action',
      },
    });

    const getSwitchChange = (e) => {
      switchFlag.value = e;
    };
    // 弹出复制页面
    function handleCopy(record: Recordable, e: Event) {
      e?.stopPropagation();
      openModal(true, {
        record,
        type: ActionEnum.COPY,
      });
    }
    // 弹出新增页面
    function handleAdd() {
      openModal(true, {
        type: ActionEnum.ADD,
      });
    }

    // 弹出查看页面
    // function handleView(record: Recordable, e: Event) {
    //   e?.stopPropagation();
    //   openModal(true, {
    //     record,
    //     type: ActionEnum.VIEW,
    //   });
    // }
    // 弹出查看页面
    function handleView(record: Recordable, e: Event) {
      e?.stopPropagation();
      replace({
        name: 'SIM卡详情',
        params: { id: record.id },
      });
    }

    // 弹出编辑页面
    function handleEdit(record: Recordable, e: Event) {
      e?.stopPropagation();
      openModal(true, {
        record,
        type: ActionEnum.EDIT,
      });
    }

    // 新增或编辑成功回调
    function handleSuccess() {
      reload();
    }

    async function handleDeleteSingle(id: string) {
      await deleteSingle(id);
      createMessage.success(t('common.tips.deleteSuccess'));
      handleSuccess();
    }

    async function batchDelete(ids: string[]) {
      await remove(ids);
      createMessage.success(t('common.tips.deleteSuccess'));
      handleSuccess();
    }

    // 点击单行删除
    function handleDelete(record: Recordable, e: Event) {
      e?.stopPropagation();
      if (record?.id) {
        handleDeleteSingle(record.id);
      }
    }

    // 点击批量删除
    function handleBatchDelete() {
      const ids = getSelectRowKeys();
      if (!ids || ids.length <= 0) {
        createMessage.warning(t('common.tips.pleaseSelectTheData'));
        return;
      }
      createConfirm({
        iconType: 'warning',
        content: t('common.tips.confirmDelete'),
        onOk: async () => {
          try {
            await batchDelete(ids);
          } catch (e) { }
        },
      });
    }

    // 设备概况统计信息
    let cardSimOverview = reactive({});

    async function fetchCardSimOverview() {
      let res = await overview();
      Object.assign(cardSimOverview, res);
    }
    fetchCardSimOverview()
    function switchView() {
      switchFlag.value = !switchFlag.value;
    }

    watch(switchFlag, (newValue) => {
      if (newValue === false) {
        reload();
      }
    });
    return {
      t,
      registerTable,
      registerModal,
      handleView,
      handleAdd,
      handleCopy,
      handleEdit,
      handleDelete,
      handleBatchDelete,
      handleSuccess,
      switchFlag,
      switchView,
      getSwitchChange,
      getDictLabel,
      cardSimOverview,
      fetchCardSimOverview
    };
  },
});
</script>
<style lang="less" scope>
.sim-info {
  padding: 16px 16px 0;

  .card-box {
    display: flex;

    .img {
      width: 104px;
      margin-right: 8px;
    }

    .info-box {
      flex: 1;

      .title {
        font-size: 12px;
        line-height: 1;
        font-weight: 600;
        margin-bottom: 10px;
      }

      .list {
        display: flex;

        .item {
          flex: 1;
          text-align: center;

          .num {
            font-size: 26px;
            font-weight: 600;
          }

          .name {
            font-weight: 600;
          }

          &+.item {
            border-left: 1px solid #ebebeb;
          }
        }
      }
    }
  }

  .status-num {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    font-weight: 600;
    font-size: 18px;

    p {
      margin-bottom: 0;
    }

    .img {
      width: 30%;
    }
  }

  .mr6 {
    margin-right: 6px;
  }
}

.red {
  color: rgb(255, 146, 146);
}

.green {
  color: rgb(113, 226, 163);
}

.yellow {
  color: rgb(238, 180, 34);
}
</style>
