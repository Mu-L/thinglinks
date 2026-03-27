<template>
  <BasicModal v-bind="$attrs" @register="registerModel" :title="t(`common.title.${type}`)" :maskClosable="false"
  :destroyOnClose="true"
    @ok="handleSubmit" :keyboard="true">
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>
<script lang="ts">
import { defineComponent, ref, unref, reactive } from 'vue';
import { BasicModal, useModalInner } from '/@/components/Modal';
import { BasicForm, useForm } from '/@/components/Form/index';
import { useI18n } from '/@/hooks/web/useI18n';
import { useMessage } from '/@/hooks/web/useMessage';
import { ActionEnum, VALIDATE_API } from '/@/enums/commonEnum';
import { Api, saveDeviceLocation, updateDeviceLocation, deviceLocationDetail } from '/@/api/iot/link/deviceLocation/deviceLocation';
import { getValidateRules } from '/@/api/thinglinks/common/formValidateService';
import { customFormSchemaRules, editFormSchema } from './location.data';
type OptionsItem = { label: string; value: string; disabled?: boolean };
export default defineComponent({
  name: '编辑设备',
  components: { BasicModal, BasicForm },
  props: {
    api: {
      type: Function as PropType<(arg?: Recordable | string) => Promise<OptionsItem[]>>,
      default: null,
    },
  },
  emits: ['success', 'register'],
  setup(_, { emit }) {
    const { t } = useI18n();
    const type = ref<ActionEnum>(ActionEnum.ADD);
    const { createMessage } = useMessage();
    const id = ref('')
    const deviceIdentification = ref('')
    const [registerForm, { setFieldsValue, resetFields, updateSchema, validate, resetSchema }] =
      useForm({
        name: 'DeviceEdit',
        labelWidth: 120,
        schemas: editFormSchema(type),
        showActionButtonGroup: false,
        disabled: (_) => {
          return unref(type) === ActionEnum.VIEW;
        },
        baseColProps: { span: 11 },
        actionColOptions: {
          span: 22,
        },
      });

    const [registerModel, { setModalProps: setProps, closeModal: close }] = useModalInner(
      async (data) => {
        console.log(data,'sssddd')
        setProps({ confirmLoading: false, width: 1000 });
        await resetSchema(editFormSchema(type));
        await resetFields();
        type.value = data?.type || ActionEnum.ADD;

        if (unref(type) !== ActionEnum.ADD) {
          // 赋值
          let record = { ...data?.record };
          record.area = [record.provinceCode,record.cityCode,record.regionCode]
          record.map = [record.longitude,record.latitude]
          record.address = record.fullName || ''
          // if(record.deviceLocationResultVO){
          //   if(record.deviceLocationResultVO.provinceCode){
          //     record.area = [record.deviceLocationResultVO.provinceCode,record.deviceLocationResultVO.cityCode,record.deviceLocationResultVO.regionCode]
          //   }
          //   if(record.deviceLocationResultVO.latitude){
          //     record.map = [record.deviceLocationResultVO.longitude,record.deviceLocationResultVO.latitude]
          //   }
          //   record.address = record.deviceLocationResultVO?.fullName || ''
          // }
          // record.authMode= String(record.authMode)
          // record.nodeType= String(record.nodeType)
          // record.deviceStatus= String(record.deviceStatus)
          // record.encryptMethod= String(record.encryptMethod)
          // record.protocolType = record.productResultVO?.protocolType
          // record.productName = record.productResultVO?.productName
          // const record = { ...data?.record };
          await setFieldsValue(record);
        } else {
          deviceIdentification.value = data.deviceIdentification
          await setFieldsValue({
            area: [],
            map: null,
            address: ''
          });
        }

        // if (unref(type) !== ActionEnum.VIEW) {
        //   let validateApi = Api[VALIDATE_API[unref(type)]];
        //   await getValidateRules(validateApi, customFormSchemaRules(type)).then(async (rules) => {
        //     rules && rules.length > 0 && (await updateSchema(rules));
        //   });
        // }
      },
    );

    async function handleSubmit() {
      try {
        const params = await validate();
        console.log(params, 'pm')
        setProps({ confirmLoading: true });
        const obj = { ...params }
        const deviceLocationSaveVO = {
          provinceCode : obj.area[0],
          cityCode : obj.area[1],
          regionCode : obj.area[2],
          fullName: obj.address,
          longitude : obj.map[0],
          latitude : obj.map[1],
          deviceIdentification: deviceIdentification.value,
          remark: obj.remark
        }
        const deviceLocationUpdateVO = {
          id: obj.id,
          provinceCode : obj.area[0],
          cityCode : obj.area[1],
          regionCode : obj.area[2],
          fullName: obj.address,
          longitude : obj.map[0],
          latitude : obj.map[1],
          remark: obj.remark
        }
        if (unref(type) !== ActionEnum.VIEW) {
          let res = null;
          if (unref(type) === ActionEnum.EDIT) {
            res = await updateDeviceLocation(deviceLocationUpdateVO);
          } else {
            delete obj.id
            res = await saveDeviceLocation(deviceLocationSaveVO);
          }
          
          emit('success',res);
          createMessage.success(t(`common.tips.${type.value}Success`));
        }
        close();
      } finally {
        setProps({ confirmLoading: false });
      }
    }

    return { type, t, registerModel, registerForm, handleSubmit, deviceIdentification, id };
  },
});
</script>
../../../../../api/iot/link/deviceLocation/deviceLocation