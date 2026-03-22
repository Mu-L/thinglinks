<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModel"
    :title="t(`common.title.${type}`)"
    :maskClosable="false"
    @ok="handleSubmit"
    defaultFullscreen
    :keyboard="true"
  >
    <BasicTable @register="registerTable">
      <template #toolbar> {{ t('devOperation.sop.sopPermGroup.tips') }} </template>
    </BasicTable>
  </BasicModal>
</template>
<script lang="ts">
  import { defineComponent, ref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicTable, useTable } from '/@/components/Table';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { page } from '/@/api/devOperation/sop/sopApiInfo';
  import { handleFetchParams } from '/@/utils/thinglinks/common';
  import { columns, searchFormSchema } from './sopApiInfo.data';
  import { save } from '/@/api/devOperation/sop/sopPermGroupPermission';

  export default defineComponent({
    name: '授权',
    components: { BasicModal, BasicTable },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const type = ref<ActionEnum>(ActionEnum.ADD);
      const { createMessage } = useMessage();
      const groupIdRef = ref();

      // 表格
      const [registerTable, { getSelectRowKeys }] = useTable({
        title: t('devOperation.sop.sopApiInfo.table.title'),
        api: page,
        columns: columns(),
        formConfig: {
          name: 'SopApiInfoSearch',
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
        searchInfo: {
          isPermission: 1,
        },
        useSearchForm: true,
        showTableSetting: true,
        bordered: true,
        rowKey: 'id',
        rowSelection: {
          type: 'checkbox',
          columnWidth: 40,
        },
      });

      const [registerModel, { setModalProps: setProps, closeModal: close }] = useModalInner(
        async (data) => {
          type.value = data?.type || ActionEnum.ADD;
          groupIdRef.value = data.groupId;
          setProps({ confirmLoading: false });
        },
      );

      async function handleSubmit() {
        try {
          setProps({ confirmLoading: true });

          const ids = getSelectRowKeys();

          if (!ids || ids.length === 0) {
            createMessage.error(t('devOperation.sop.sopPermGroup.selectTips'));
            return;
          }

          if (!groupIdRef.value) {
            createMessage.error(t('devOperation.sop.sopPermGroup.SelectOptGroupTips'));
            return;
          }
          await save({
            groupId: groupIdRef.value,
            apiIdList: ids,
          });
          createMessage.success(t('devOperation.sop.sopPermGroup.authorizationSuccess'));

          close();
          emit('success');
        } finally {
          setProps({ confirmLoading: false });
        }
      }

      return { type, t, registerModel, registerTable, handleSubmit };
    },
  });
</script>
