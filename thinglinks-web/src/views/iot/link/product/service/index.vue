<template>
  <div class="service-box">
    <div class="service-left">
      <div class="title-btns">
        <div class="title">{{ t('iot.link.productService.productService.serviceList') }}</div>
        <div class="btns">
          <a-button type="primary" shape="circle" size="small" @click="handleAdd">
            <template #icon>
              <PlusOutlined />
            </template>
          </a-button>
          <a-button
            type="primary"
            shape="circle"
            size="small"
            @click="handleEdit"
            :disabled="!Boolean(state.serviceId)"
          >
            <template #icon>
              <EditOutlined />
            </template>
          </a-button>
          <a-button type="primary" shape="circle" size="small" @click="handleList">
            <template #icon>
              <RedoOutlined />
            </template>
          </a-button>
          <a-button type="primary" shape="circle" size="small" @click="handleDelete">
            <template #icon>
              <DeleteOutlined />
            </template>
          </a-button>
        </div>
      </div>
      <div class="list">
        <div
          v-for="item in state.list"
          :key="item.id"
          :class="item.id == state.serviceId ? 'item active' : 'item'"
          @click="changeService(item.id)"
        >
          <div class="name">{{ item.serviceName }}</div>
          <!-- <div class="type">{{ item.serviceType }}</div> -->
          <div class="type">{{
            getDictLabel('LINK_PRODUCT_SERVICE_TYPE', item.serviceType, '')
          }}</div>
          <div class="desc"
            >{{ item.remark }}
            <div>
              <a-tooltip placement="rightBottom" color="#fff">
                <template #title>
                  <span style="color: #707070; font-size: 12px">{{ item.description }}</span>
                </template>
                <p>{{ item.description }}</p>
              </a-tooltip>
            </div>
          </div>
          <div class="tag">
            <LinkOutlined v-if="item.serviceStatus == 0" style="color: #43cf7c" />
            <DisconnectOutlined v-else style="color: #f50" />
            <!-- <a-tag  :color="item.serviceStatus == 0 ? '#43cf7c' : '#f50'">{{ getDictLabel('LINK_PRODUCT_STATUS', item.serviceStatus, '') }}</a-tag> -->
          </div>
        </div>
      </div>
    </div>
    <div class="service-right" v-if="state.serviceId">
      <div class="operate">
        <div class="tabs">
          <div :class="state.type == 1 ? 'active tab' : 'tab'" @click="changeTab(1)">{{
            t('iot.link.productService.productService.attributeList')
          }}</div>
          <div :class="state.type == 2 ? 'active tab' : 'tab'" @click="changeTab(2)">{{
            t('iot.link.productService.productService.commandList')
          }}</div>
        </div>
        <div class="table-content">
          <property v-if="state.type == 1" :serviceId="state.serviceId" />
          <command v-else-if="state.type == 2" :serviceId="state.serviceId" />
        </div>
      </div>
    </div>
  </div>
  <EditModal @register="registerModal" @success="handleSuccess" />
</template>
<script lang="ts">
  import { defineComponent, ref, reactive } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useModal } from '/@/components/Modal';
  import { query, page, remove } from '/@/api/iot/link/productService/productService';
  import { Tabs, Button } from 'ant-design-vue';
  import { Tag, Badge } from 'ant-design-vue';
  import {
    PlusOutlined,
    EditOutlined,
    DeleteOutlined,
    RedoOutlined,
    LinkOutlined,
    DisconnectOutlined,
  } from '@ant-design/icons-vue';
  import property from '../../productProperty/productProperty/index.vue';
  import command from '../../productCommand/productCommand/index.vue';
  import { ActionEnum } from '/@/enums/commonEnum';
  import EditModal from './Edit.vue';
  import { useDict } from '/@/components/Dict';
  const { getDictLabel } = useDict();
  export default defineComponent({
    name: 'ProductServiceDetail',
    components: {
      AButton: Button,
      [Tabs.name]: Tabs,
      [Tabs.TabPane.name]: Tabs.TabPane,
      PlusOutlined,
      EditOutlined,
      DeleteOutlined,
      RedoOutlined,
      LinkOutlined,
      DisconnectOutlined,
      command,
      property,
      EditModal,
    },
    props: {
      id: {
        type: String,
        default: '',
      },
    },
    emits: ['success', 'register'],
    setup(props) {
      // const isShow = ref(false);
      // // 此处可以得到用户ID
      const { t } = useI18n();
      const [registerModal, { openModal }] = useModal();
      const { createMessage, createConfirm } = useMessage();
      const state = reactive({
        list: [
          {
            id: '',
            serviceName: '',
            serviceType: '',
            remark: '',
          },
        ],
        productId: '',
        serviceId: '',
        type: 1,
      });
      state.productId = props.id;
      function changeTab(type) {
        if (state.type != type) {
          state.type = type;
        }
      }
      async function handleList() {
        state.list = await query({ productId: state.productId });
        if (state.list.length > 0) {
          state.serviceId = state.list[0].id;
        } else {
          state.serviceId = '';
        }
      }
      handleList();

      function changeService(serviceId) {
        state.serviceId = serviceId;
      }

      // 弹出新增页面
      function handleAdd() {
        openModal(true, {
          productId: state.productId,
          type: ActionEnum.ADD,
        });
      }

      // 弹出查看页面
      function handleView(record: Recordable, e: Event) {
        e?.stopPropagation();
        openModal(true, {
          record,
          type: ActionEnum.VIEW,
        });
      }

      // 弹出编辑页面
      function handleEdit(e: Event) {
        e?.stopPropagation();
        openModal(true, {
          serviceId: state.serviceId,
          productId: state.productId,
          type: ActionEnum.EDIT,
        });
      }

      // 新增或编辑成功回调
      function handleSuccess() {
        handleList();
      }

      async function batchDelete(ids: string[]) {
        await remove(ids);
        createMessage.success(t('common.tips.deleteSuccess'));
        handleSuccess();
      }

      // 点击单行删除
      function handleDelete(e: Event) {
        e?.stopPropagation();
        if (state.serviceId) {
          createConfirm({
            iconType: 'warning',
            content: t('common.tips.confirmDelete'),
            onOk: async () => {
              try {
                batchDelete([state.serviceId]);
              } catch (e) {}
            },
          });
        }
      }

      return {
        t,
        state,
        changeTab,
        registerModal,
        handleView,
        handleAdd,
        handleEdit,
        handleDelete,
        handleSuccess,
        changeService,
        handleList,
        getDictLabel,
      };
    },
  });
</script>
<style lang="less" scope>
  .service-box {
    min-height: 600px;
    height: calc(100vh - 280px);
    display: flex;

    .service-left {
      width: 200px;
      min-width: 200px;
      border-right: 2px solid #d9d9d9;
      padding-right: 14px;

      .title-btns {
        display: flex;
        justify-content: space-between;
        margin-bottom: 16px;

        .title {
          font-size: 14px;
          font-weight: 600;
          color: #2e3033;
        }

        .btns {
          .ant-btn {
            transform: scale(0.85);

            & + .ant-btn {
              margin-left: 4px;
            }
          }
        }
      }

      .list {
        height: calc(100% - 52px);
        overflow-x: hidden;
        overflow-y: auto;

        .item {
          border-radius: 6px;
          border: 1px solid #f0f2f5;
          padding: 12px;
          position: relative;

          &.active {
            background-color: #f0f2f5;

            .name {
              color: #1a66ff;
            }
          }

          & + .item {
            margin-top: 12px;
          }

          .name {
            font-size: 14px;
            font-weight: bold;
            color: #2e3033;
            line-height: 18px;
            margin-bottom: 4px;
          }

          .type {
            font-size: 12px;
            font-weight: 400;
            color: #2e3033;
            line-height: 14px;
          }

          .desc {
            font-size: 10px;
            font-weight: 400;
            color: #707070;
            line-height: 16px;
            margin-top: 8px;

            p {
              width: 100%;
              word-break: break-all;
              text-overflow: ellipsis;
              display: -webkit-box;
              -webkit-box-orient: vertical;
              -webkit-line-clamp: 2;
              overflow: hidden;
            }
          }

          .tag {
            position: absolute;
            right: 15px;
            top: 15px;
          }
        }
      }
    }

    .service-right {
      flex: 1;
      width: calc(100% - 200px);
      min-width: 0;
      padding-left: 24px;

      .tabs {
        display: flex;

        .tab {
          width: 83px;
          height: 36px;
          background: #f0f2f5;
          border-radius: 6px 6px 0px 0px;
          font-size: 16px;
          font-weight: 500;
          color: #656565;
          text-align: center;
          line-height: 36px;
          cursor: pointer;

          &.active {
            background: #1a66ff;
            color: #fff;
          }

          & + .tab {
            margin-left: 12px;
          }
        }
      }

      .table-content {
        height: calc(100% - 36px);
        overflow: auto;
      }
    }
  }
</style>
