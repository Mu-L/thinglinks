<template>
  <PageWrapper contentFullHeight dense>
    <div class="device-info">
      <a-row :gutter="20">
        <a-col :span="6">
          <a-card title="" :bordered="false">
            <div class="card-box">
              <img :src="Icon1" alt="" class="img" />
              <div class="info-box">
                <div class="title">{{ t('iot.link.device.device.deviceTotal') }}</div>
                <div class="list">
                  <div class="item">
                    <div class="num" style="color: #ffbb54">{{
                      deviceOverview?.totalDevicesCount || 0
                    }}</div>
                    <div class="name">{{ t('iot.link.device.device.deviceTotalAmount') }}</div>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="9">
          <a-card title="" :bordered="false">
            <div class="card-box">
              <img :src="Icon2" alt="" class="img" />
              <div class="info-box">
                <div class="title">{{ t('iot.link.device.device.connectStatus') }}</div>
                <div class="list">
                  <div class="item">
                    <div class="num" style="color: #0061ff">{{
                      deviceOverview?.onlineCount || 0
                    }}</div>
                    <div class="name">{{ t('iot.link.device.device.online') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: #6aa2fe">{{
                      deviceOverview?.offlineCount || 0
                    }}</div>
                    <div class="name">{{ t('iot.link.device.device.offline') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: rgba(0, 52, 255, 0.3)">{{
                      deviceOverview?.notConnectedCount || 0
                    }}</div>
                    <div class="name">{{ t('iot.link.device.device.notConnected') }}</div>
                  </div>
                </div>
              </div>
            </div>
          </a-card>
        </a-col>
        <a-col :span="9">
          <a-card title="" :bordered="false">
            <div class="card-box">
              <img :src="Icon3" alt="" class="img" />
              <div class="info-box">
                <div class="title">{{ t('iot.link.device.device.deviceStatus') }}</div>
                <div class="list">
                  <div class="item">
                    <div class="num" style="color: rgba(52, 168, 83, 1)">{{
                      deviceOverview?.activatedCount || 0
                    }}</div>
                    <div class="name">{{ t('iot.link.device.device.deviceAllStatus.active') }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: rgba(52, 168, 83, 0.8)">{{
                      deviceOverview?.notActivatedCount || 0
                    }}</div>
                    <div class="name">{{
                      t('iot.link.device.device.deviceAllStatus.notActivat')
                    }}</div>
                  </div>
                  <div class="item">
                    <div class="num" style="color: rgba(52, 168, 83, 0.5)">{{
                      deviceOverview?.lockedCount || 0
                    }}</div>
                    <div class="name">{{ t('iot.link.device.device.deviceAllStatus.lock') }}</div>
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
      :switchFlag="switchFlag"
      :is-device="true"
    >
      <template #toolbar>
        <a-button type="primary" preIcon="ant-design:plus-outlined" @click="handleAdd">
          {{ t('common.title.add') }}
        </a-button>
        <a-button
          type="primary"
          color="error"
          preIcon="ant-design:delete-outlined"
          @click="handleBatchDelete"
        >
          {{ t('common.title.delete') }}
        </a-button>
        <a-button type="primary" preIcon="ant-design:download-outlined" @click="handleImport">{{
          t('common.title.import')
        }}</a-button>
        <a-button preIcon="ant-design:upload-outlined" @click="handleExport">{{
          t('common.title.bulkExport')
        }}</a-button>
        <a-button preIcon="ant-design:swap-outlined" @click="switchView"
          >{{ t('iot.link.device.device.switchView') }}
        </a-button>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <div class="table-operation-device">
            <TableAction
              :actions="[
                {
                  tooltip: t('common.title.view'),
                  icon: 'ant-design:search-outlined',
                  onClick: handleView.bind(null, record),
                },
                {
                  tooltip: t('common.title.edit'),
                  icon: 'ant-design:edit-outlined',
                  onClick: handleEdit.bind(null, record),
                },
                {
                  tooltip: t('common.title.copy'),
                  icon: 'ant-design:copy-outlined',
                  onClick: handleCopy.bind(null, record),
                },
                {
                  tooltip: t('common.title.delete'),
                  icon: 'ant-design:delete-outlined',
                  color: 'error',
                  popConfirm: {
                    title: t('common.tips.confirmDelete'),
                    confirm: handleDelete.bind(null, record),
                  },
                },
              ]"
              :stopButtonPropagation="true"
            />
            <a-divider type="vertical" class="qrcode_divider" />
            <renderQrCode
              :deviceIdentification="record?.deviceIdentification || ''"
              :deviceName="record?.deviceName || '未知设备'"
            >
              <template #trigger>
                <a-tooltip
                  ><template #title>{{ t('iot.link.device.device.qrcode') }}</template>
                  <QrcodeOutlined :style="{ color: '#009688', fontSize: '16px' }" />
                </a-tooltip>
              </template>
            </renderQrCode>
          </div>
        </template>
      </template>
    </BasicTable>
    <EditModal @register="registerModal" @success="handleSuccess" />
    <ImportModal @register="importModal" @reload="reload" />
  </PageWrapper>
</template>
<script lang="ts">
  import { defineComponent, ref, reactive } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { copyTextToClipboard } from '/@/hooks/web/useCopyToClipboard';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { PageWrapper } from '/@/components/Page';
  import { useModal } from '/@/components/Modal';
  import { useRouter } from 'vue-router';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { page, remove, overview, bulkExport, deleteSingle } from '/@/api/iot/link/device/device';
  import { columns, searchFormSchema } from './device.data';
  import EditModal from './Edit.vue';
  import ImportModal from './ImportModal.vue';
  import renderQrCode from '../qrcode/index.vue';

  // icon
  import { QrcodeOutlined } from '@ant-design/icons-vue';
  import { Card, Row, Col } from 'ant-design-vue';
  import GifImg from '/@/assets/images/iot/link/device/deviceManagement.gif';
  import Icon1 from '/@/assets/images/iot/link/deviceAndProduct/deviceTotal.png';
  import Icon2 from '/@/assets/images/iot/link/deviceAndProduct/gatwayTotal.png';
  import Icon3 from '/@/assets/images/iot/link/deviceAndProduct/hasConnect.png';
  import { downloadByData } from '/@/utils/file/download';
  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '设备',
    components: {
      BasicTable,
      PageWrapper,
      TableAction,
      EditModal,
      ACard: Card,
      ARow: Row,
      ACol: Col,
      ImportModal,
      renderQrCode,
      QrcodeOutlined,
    },
    setup() {
      const { t } = useI18n();
      const { createMessage, createConfirm, notification } = useMessage();
      const [registerModal, { openModal }] = useModal();
      const [importModal, { openModal: openImportModal }] = useModal();
      const { replace } = useRouter();

      // 表格
      const [registerTable, { reload, getSelectRowKeys, clearSelectedRowKeys, setLoading }] =
        useTable({
          title: t('iot.link.device.device.table.title'),
          api: page,
          columns: columns(),
          formConfig: {
            name: 'DeviceSearch',
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
      function handleView(record: Recordable, e: Event) {
        e?.stopPropagation();
        // openModal(true, {
        //   record,
        //   type: ActionEnum.VIEW,
        // });
        // go('/device/device/detail?id=' + record.id)
        // return
        replace({
          name: '设备详情',
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

      // 批量删除
      async function batchDelete(ids: string[]) {
        await remove(ids);
        createMessage.success(t('common.tips.deleteSuccess'));
        handleSuccess();
      }

      // 删除单个
      const handleDeleteSingle = async (id: string) => {
        await deleteSingle(id);
        createMessage.success(t('common.tips.deleteSuccess'));
        handleSuccess();
      };

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
            } catch (e) {}
          },
        });
      }

      // 查看设备对应二维码
      async function handleQrCode(record: Recordable) {}
      // 切换视图 卡片&&列表
      const switchFlag = ref<boolean>(true);
      function switchView() {
        console.log('switchView');
        switchFlag.value = !switchFlag.value;
      }

      // 设备概况统计信息
      let deviceOverview = reactive({});

      async function fetchDeviceOverView() {
        let res = await overview();
        Object.assign(deviceOverview, res);
      }

      fetchDeviceOverView();

      function copyFn(text) {
        let result = copyTextToClipboard(text);
        console.log(result, 'result');
        if (result) {
          createMessage.success(t('common.tips.copySuccess'));
        } else {
          createMessage.warning(t('common.tips.copyFail'));
        }
      }

      function getSwitchChange(e) {
        switchFlag.value = e;
      }
      const handleImport = async () => {
        openImportModal(true);
      };
      const handleExport = async () => {
        try {
          setLoading(true);
          if (getSelectRowKeys().length === 0) {
            notification.warn({
              message: t('common.tips.tips'),
              description: t('iot.link.device.device.validateExportMsg'),
            });
            setLoading(false);
            return;
          }
          const res = await bulkExport(getSelectRowKeys());
          await downloadByData(res, 'device-export.xlsx');
          clearSelectedRowKeys();
          setLoading(false);
        } catch (error) {
          throw error;
        }
      };

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
        deviceOverview,
        fetchDeviceOverView,
        GifImg,
        Icon1,
        Icon2,
        Icon3,
        copyFn,
        getSwitchChange,
        handleExport,
        importModal,
        handleImport,
        reload,
      };
    },
  });
</script>
<style lang="less" scope>
  .device-info {
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

            & + .item {
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

  .table-operation-device {
    display: flex;
    align-items: center;

    .qrcode_divider.ant-divider-vertical {
      margin: 0 10px 0 2px;
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
../../../../../api/iot/link/device/device
