<template>
  <PageWrapper dense contentFullHeight contentClass="flex">
    <DeviceGroupTree
      class="md:w-2/7"
      @select="handleGroupSelect"
      @add="handleGroupRefAdd"
      @edit="handleGroupRefEdit"
      ref="groupRef"
    />
    <DeviceGroupDetail class="md:w-5/7" ref="groupDetailRef" />
    <DeviceGroupEdit @register="registerModal" @success="handleEditSuccess" ref="editRef" />
  </PageWrapper>
</template>
<script lang="ts">
  // enum
  import { ActionEnum } from '/@/enums/commonEnum';
  // hooks
  import { useModal } from '/@/components/Modal';
  // components
  import { defineComponent, ref, unref } from 'vue';
  import { PageWrapper } from '/@/components/Page';
  import DeviceGroupTree from './components/deviceGroupTree/index.vue';
  import DeviceGroupDetail from './components/deviceGroupDetail/index.vue';
  import DeviceGroupEdit from './components/deviceGroupEdit/index.vue';

  export default defineComponent({
    // 若需要开启页面缓存，请将此参数跟菜单名保持一致
    name: '设备分组',
    components: {
      DeviceGroupEdit,
      DeviceGroupTree,
      PageWrapper,
      DeviceGroupDetail,
    },
    setup() {
      const editRef = ref<any>(null);
      const groupRef = ref<any>(null);
      const groupDetailRef = ref<any>(null);
      const [registerModal, { openModal, setModalProps }] = useModal();

      // 获取树
      function getGroupRef() {
        return unref(groupRef);
      }

      // 编辑成功回调
      function handleEditSuccess() {
        getGroupRef().fetch();
      }

      // 编辑
      function handleGroupRefEdit(parent = {}, record = {}) {
        openModal(true, {
          record,
          parent,
          type: ActionEnum.EDIT,
        });
      }

      // 点击树的新增按钮
      function handleGroupRefAdd(parent = {}) {
        openModal(true, {
          parent,
          type: ActionEnum.ADD,
        });
      }

      // 分组数据首次加载完毕后初始化右侧详细信息
      function handleGroupSelect(parentGroup, group) {
        unref(groupDetailRef).fetchDetail(group ? group : parentGroup);
      }

      return {
        editRef,
        groupRef,
        groupDetailRef,
        registerModal,
        handleEditSuccess,
        handleGroupSelect,
        handleGroupRefAdd,
        handleGroupRefEdit,
      };
    },
  });
</script>
