<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModel"
    :title="t(`common.title.${type}`)"
    :maskClosable="false"
    :destroyOnClose="true"
    @ok="handleSubmit"
    :keyboard="true"
  >
    <BasicForm @register="registerForm">
      <template #signKey="{ model, field }">
        <div class="editor_container">
          <FormItem :name="field" :rules="[{ required: true }]">
            <template #label>
              <div>{{ t('iot.link.device.device.signKey') }}</div>
            </template>
            <a-input
              v-model:value="model[field]"
              :rules="[{ required: true }]"
              :placeholder="t('common.inputText')"
            >
              <template #addonAfter>
                <ReloadOutlined @click="handleRandomString(field)" />
              </template>
            </a-input>
          </FormItem>
        </div>
      </template>
      <template #encryptKey="{ model, field }">
        <div class="editor_container">
          <FormItem :name="field" v-if="model.encryptMethod != 0" :rules="[{ required: true }]">
            <template #label>
              <div>{{ t('iot.link.device.device.encryptKey') }}</div>
            </template>
            <a-input v-model:value="model[field]" :placeholder="t('common.inputText')">
              <template #addonAfter>
                <ReloadOutlined @click="handleRandomString(field)" />
              </template>
            </a-input>
          </FormItem>
        </div>
      </template>
      <template #encryptVector="{ model, field }">
        <div class="editor_container">
          <FormItem :name="field" v-if="model.encryptMethod != 0" :rules="[{ required: true }]">
            <template #label>
              <div>{{ t('iot.link.device.device.encryptVector') }}</div>
            </template>
            <a-input
              v-model:value="model[field]"
              :rules="[{ required: true }]"
              :placeholder="t('common.inputText')"
            >
              <template #addonAfter>
                <ReloadOutlined @click="handleRandomString(field)" />
              </template>
            </a-input>
          </FormItem>
        </div>
      </template>
    </BasicForm>
  </BasicModal>
</template>
<script lang="ts">
  import { Form } from 'ant-design-vue';
  import { defineComponent, ref, unref } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { ActionEnum } from '/@/enums/commonEnum';
  import { DeviceNodeType } from '/@/enums/link/device';
  import {
    save,
    update,
    detail,
    detailBydeviceIdentification,
  } from '/@/api/iot/link/device/device';
  import { ReloadOutlined } from '@ant-design/icons-vue';
  import { editFormSchema } from './device.data';
  type OptionsItem = { label: string; value: string; disabled?: boolean };
  export default defineComponent({
    name: '编辑设备',
    components: { BasicModal, BasicForm, FormItem: Form.Item, ReloadOutlined },
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
      const { createMessage, createConfirm } = useMessage();
      const deviceLocationId = ref('');
      const [registerForm, { setFieldsValue, resetFields, validate, resetSchema }] = useForm({
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
          setProps({ confirmLoading: false, width: 1000 });
          type.value = data?.type || ActionEnum.ADD;
          await resetSchema(editFormSchema(type));
          await resetFields();

          if (unref(type) !== ActionEnum.ADD) {
            // 赋值
            let record = await detail(data?.record?.id);
            if (record.deviceLocationResultVO) {
              deviceLocationId.value = record.deviceLocationResultVO.id;
              if (record.deviceLocationResultVO.provinceCode) {
                record.area = [
                  record.deviceLocationResultVO.provinceCode,
                  record.deviceLocationResultVO.cityCode,
                  record.deviceLocationResultVO.regionCode,
                ];
              }
              if (record.deviceLocationResultVO.latitude) {
                record.map = [
                  record.deviceLocationResultVO.longitude,
                  record.deviceLocationResultVO.latitude,
                ];
              }
              record.address = record.deviceLocationResultVO?.fullName || '';
            }
            record.authMode = String(record.authMode);
            record.nodeType = String(record.nodeType);
            record.deviceStatus = String(record.deviceStatus);
            record.encryptMethod = String(record.encryptMethod);
            record.protocolType = record.productResultVO?.protocolType;
            record.productName = record.productResultVO?.productName;
            record.productIdentification = record.productResultVO?.productIdentification;
            if (record.nodeType == DeviceNodeType.SUB_DEVICE && record.gatewayId) {
              // 网关设备
              let deviceObj = await getDeviceDetail(record.gatewayId);
              // record.subDeviceResultVOList.find(item => { return item.id == record.gatewayId })
              console.log(deviceObj, 'deviceObj');
              record.gatewayName = deviceObj?.deviceName || '选择网关设备';
            }
            // const record = { ...data?.record };
            await setFieldsValue(record);
          }

          // if (unref(type) !== ActionEnum.VIEW) {
          //   let validateApi = Api[VALIDATE_API[unref(type)]];
          //   await getValidateRules(validateApi, customFormSchemaRules(type)).then(async (rules) => {
          //     rules && rules.length > 0 && (await updateSchema(rules));
          //   });
          // }
        },
      );

      const getDeviceDetail = async (deviceIdentification) => {
        if (!deviceIdentification || deviceIdentification === 'all') {
          return {};
        }
        return await detailBydeviceIdentification(deviceIdentification);
      };

      async function handleSubmit() {
        try {
          const params = await validate();
          console.log(params, 'pm');
          setProps({ confirmLoading: true });
          let obj = { ...params };

          let deviceLocationUpdateVO = {
            provinceCode: obj.area ? obj.area[0] : undefined,
            cityCode: obj.area ? obj.area[1] : undefined,
            regionCode: obj.area ? obj.area[2] : undefined,
            fullName: obj.address,
            longitude: obj.map ? obj.map[0] : undefined,
            latitude: obj.map ? obj.map[1] : undefined,
          };

          // if (obj.map) {
          if (obj.id) {
            obj.deviceLocationUpdateVO = {
              ...deviceLocationUpdateVO,
              id: deviceLocationId.value,
            };
          } else {
            obj.deviceLocationSaveVO = deviceLocationUpdateVO;
          }
          // }
          console.log(obj);
          // return false;
          if (unref(type) !== ActionEnum.VIEW) {
            if (unref(type) === ActionEnum.EDIT) {
              obj.id = obj.id;
              await update(obj);
            } else {
              delete obj.id;
              await save(obj);
            }
            createMessage.success(t(`common.tips.${type.value}Success`));
          }
          close();
          emit('success');
        } finally {
          setProps({ confirmLoading: false });
        }
      }
      const generateRandomString = () => {
        const chars = 'abcdefghijklmnopqrstuvwxyz0123456789';
        let result = '';
        for (let i = 0; i < 16; i++) {
          result += chars[Math.floor(Math.random() * chars.length)];
        }
        return result;
      };
      function handleRandomString(field) {
        createConfirm({
          iconType: 'warning',
          content: '是否随机生成一个 16 位字符？',
          onOk: async () => {
            // 生成随机字符串
            const randomString = generateRandomString();
            if (field == 'signKey') {
              await setFieldsValue({ signKey: randomString });
            } else if (field == 'encryptKey') {
              await setFieldsValue({ encryptKey: randomString });
            } else {
              await setFieldsValue({ encryptVector: randomString });
            }
          },
        });
      }
      return {
        type,
        t,
        registerModel,
        registerForm,
        handleSubmit,
        handleRandomString,
      };
    },
  });
</script>
<style lang="less" scoped>
  .editor_container {
    display: flex;
    align-items: center;
  }

  :deep(.ant-col .ant-form-item-label) {
    width: 120px;
  }

  :deep(.ant-col .ant-form-item-control) {
    flex: 1;
  }

  :deep(.ant-row .ant-form-item) {
    flex: 1;
  }
</style>
../../../../../api/iot/link/device/device
