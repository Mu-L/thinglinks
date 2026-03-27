<template>
  <n-modal v-model:show="modelShowRef" @afterLeave="closeHandle">
    <div class="n-list switch-tenant-setting">
      <n-list bordered>
        <template #header>
          <n-space justify="space-between">
            <n-h3 class="go-mb-0">{{ t('global.switch_tenant_title') }}</n-h3>
            <n-icon size="20" class="go-cursor-pointer" @click="closeHandle">
              <close-icon></close-icon>
            </n-icon>
          </n-space>
        </template>

        <div class="tenant-list-wrap">
          <n-list-item v-for="item in tenantLs" :key="item.id">
            <div class="tenant-item">
              <n-radio
                :checked="selectTenantId === item.id"
                :value="item.id"
                @change="handleChange(item)"
              >
                {{ item.name }}
              </n-radio>
              <span 
                v-if="!item.isDefault" 
                :style="{ color: designStore.appTheme }"
                @click="setDefault(item)"
              >
                {{ t('global.set_default') }}
              </span>
            </div>
          </n-list-item>
        </div>
      </n-list>
      <div class="switch-tenant-info">
        <n-alert :title="t('global.notes')" :show-icon="false">
          <p>{{ t('global.note_user') }}</p>
          <p>{{ t('global.note_employee') }}</p>
          <p>{{ t('global.note_company') }}</p>
          <p>{{ t('global.note_unit') }}</p>
          <p>{{ t('global.note_department') }}</p>
        </n-alert>
      </div>
      <div class="switch-tenant-btn">
        <n-button quaternary @click="modelShowRef = false">
          {{ t('global.cancel') }}
        </n-button>
        <n-button quaternary type="primary" @click="switchTenant">
          {{ t('global.confirm') }}
        </n-button>
      </div>
    </div>
  </n-modal>
</template>

<script script lang="ts" setup>
// util
import { getTenantId } from '@/utils/auth';
import { reactive, ref, watch } from 'vue'
import { useSettingStore } from '@/store/modules/settingStore/settingStore'
import { useDesignStore } from '@/store/modules/designStore/designStore'
// enum
import { ListType } from './index.d'
import { SettingStoreEnums, ToolsStatusEnum } from '@/store/modules/settingStore/settingStore.d'
// components
import { icon } from '@/plugins'
/* api */
import { findCompanyDept, switchTenantAndOrg, updateDefaultTenant } from '@/api/thinglinks/common/oauth'
// store
import { useUserStore } from '@/store/modules/user/user'

const t = window['$t']

const props = defineProps({
  modelShow: Boolean
})

const emit = defineEmits(['update:modelShow'])
const { HelpOutlineIcon, CloseIcon } = icon.ionicons5
const settingStore = useSettingStore()
const designStore = useDesignStore()
const userStore = useUserStore()

const modelShowRef = ref(false)

let tenantLs = ref<ListType[]>([])
const currentTenant = ref<string>(getTenantId());
const selectTenantId = ref<string>('');

const initTenantList = async () => {
  try {
    const { isSuccess = false, code = null, data = {}, errorMsg = '' } = await findCompanyDept(currentTenant.value);
    if (isSuccess && code === 0) {
      const { tenantList = [] } = data;
      if (tenantList.length) {
        tenantLs.value = tenantList;
      }
    } else {
      window['$message'].error(errorMsg || t('global.get_tenant_list_failed'));
    }
  }catch (err){
    console.log('获取租户列表 findCompanyDept err', err);
  }
}

watch(() => props.modelShow, (newValue) => {
  selectTenantId.value = currentTenant.value;
  if (newValue) {
    initTenantList();
  } else {
    tenantLs.value = [];
  }
  modelShowRef.value = newValue
})

const closeHandle = () => {
  emit('update:modelShow', false)
}

const handleChange = (item: ListType) => {
  selectTenantId.value = item.id;
}

const confirmSetDefaultTenant = async (item: ListType) => {
  try {
    const { isSuccess = false, code = null, data = false } = await updateDefaultTenant(item.id);
    if (data) {
      window['$message'].success(t('global.set_default_tenant_success'));
      initTenantList();
    }
  } catch (err) {
    console.log('设置默认租户 confirmSetDefaultTenant err', err)
  }
}
const setDefault = (item: ListType) => {
  window['$dialog'].warning({
    title: t('global.confirm_set_default_tenant', { name: item.name }),
    content: t('global.set_default_tenant_tip'),
    negativeText: t('global.cancel'),
    positiveText: t('global.confirm'),
    onPositiveClick: () => {
      confirmSetDefaultTenant(item);
    }
  })
}

const switchTenant = async () => {
  const tenant = tenantLs.value.find(item => item.id === selectTenantId.value);
  if (!tenant) {
    const errorMsg = t('global.cannot_switch_tenant');
    window['$message'].error(errorMsg);
    throw Error(errorMsg);
  }
  if (!tenant.state) {
    const errorMsg = t('global.tenant_disabled');
    window['$message'].error(errorMsg);
    throw Error(errorMsg);
  }

  if (!tenant.employeeState) {
    const errorMsg = t('global.employee_disabled');
    window['$message'].error(errorMsg);
    throw Error(errorMsg);
  }

  // console.log(selectTenantId.value);
  // TODO 机构相关逻辑待开发
  await userStore.switchTenantAndOrg(selectTenantId.value)
}
</script>

<style lang="scss" scoped>
.switch-tenant-setting{
  max-width: 500px;
  overflow-y: auto;
  .tenant-list-wrap{
    .tenant-item{
      display: flex;
      align-items: center;
      justify-content: space-between;
      span{
        color: green;
        cursor: pointer;
      }
    }
  }
  .switch-tenant-info{
    padding: 0 20px;
    margin-top: 20px;
    p{
      margin-bottom: 1em;
    }
    p:last-child{
      margin: 0;
    }
  }
  .switch-tenant-btn{
    padding: 5px 12px;
    text-align: end;
  }
}
</style>
