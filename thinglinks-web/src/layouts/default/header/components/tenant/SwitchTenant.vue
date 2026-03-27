<template>
  <BasicModal
    :keyboard="true"
    :maskClosable="false"
    :min-height="300"
    :title="t('layout.header.switchingCompanies')"
    v-bind="$attrs"
    :okText="t('component.app.toNavigate')"
    width="40%"
    @ok="handleSubmit"
    @register="registerModal"
  >
    <Card :bodyStyle="{ padding: 0 }" size="small" :title="t('layout.header.enterprise')">
      <RadioGroup v-model:value="formData.tenant" style="width: 100%" @change="changeTenant">
        <div class="pl-2">
          <List :data-source="formState.tenantList">
            <template #renderItem="{ item }">
              <ListItem style="cursor: pointer">
                <ListItemMeta>
                  <template #title>
                    <Radio :value="item.id" :disabled="disabledItem(item)">
                      {{ getTenantName(item) }}
                    </Radio>
                  </template>
                  <template #description>
                    <RadioGroup
                      v-model:value="formData.orgId"
                      :disabled="disabledItem(item)"
                      @change="changeOrg(item.id)"
                    >
                      <RadioButton v-for="org in item.orgList" :key="org.id" :value="org.id">
                        {{ getOrgName(org) }}
                      </RadioButton>
                    </RadioGroup>
                  </template>
                </ListItemMeta>

                <template #actions>
                  <a
                    key="more"
                    href="javascript:void(0)"
                    v-if="!item.isDefault"
                    @click="setDefaults(item, $event)"
                  >
                    {{ t('layout.header.setAsDefault') }}
                  </a>
                </template>
              </ListItem>
            </template>
          </List>
        </div>
      </RadioGroup>
    </Card>
    <Alert :message="t('layout.header.switchingCompaniesTips.title')">
      <template #description>
        <p>
          {{ t('layout.header.switchingCompaniesTips.content[0]') }}
        </p>
        <p>
          {{ t('layout.header.switchingCompaniesTips.content[1]') }}
        </p>
        <p>
          {{ t('layout.header.switchingCompaniesTips.content[2]') }}
        </p>
        <p>
          {{ t('layout.header.switchingCompaniesTips.content[3]') }}
        </p>
        <p>
          {{ t('layout.header.switchingCompaniesTips.content[4]') }}
        </p>
      </template>
    </Alert>
  </BasicModal>
</template>
<script lang="ts">
  import { computed, defineComponent, reactive } from 'vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  import { Alert, Card, List, Radio, RadioGroup } from 'ant-design-vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useUserStore } from '/@/store/modules/user';
  import { findCompanyDept, updateDefaultTenant } from '/@/api/thinglinks/common/oauth';
  import { BaseOrgResultVO } from '/@/api/basic/user/model/baseOrgModel';
  import { Tenant } from '/@/api/devOperation/tenant/model/tenantModel';
  import { ORG_TYPE_MAP } from '/@/enums/biz/base';

  export default defineComponent({
    name: 'SwitchTenant',
    components: {
      BasicModal,
      Card,
      List,
      ListItem: List.Item,
      ListItemMeta: List.Item.Meta,
      Radio,
      RadioButton: Radio.Button,
      RadioGroup,
      Alert,
    },
    emits: ['success', 'register'],
    setup(_, { emit }) {
      const { t } = useI18n();
      const { createMessage, createConfirm } = useMessage();
      const userStore = useUserStore();

      const formData = reactive({
        tenant: '',
        orgId: null,
      });
      const formState = reactive({
        // 当前拥有拥有的租户列表
        tenantList: [] as Tenant[],
        // 所属单位id
        currentCompanyId: '',
        // 所属部门id
        currentDeptId: '',
      });

      // 当前企业id
      const currentTenantId = computed(() => userStore.getTenantId);

      const [registerModal, { setModalProps, closeModal }] = useModalInner(async () => {
        setModalProps({ confirmLoading: false });

        formData.tenant = userStore.getTenantId;
        formData.orgId = null;
        await loadOrgByTenant(userStore.getTenantId);
      });

      async function loadOrgByTenant(tenantId: string) {
        const org = await findCompanyDept(tenantId);

        formState.tenantList = org.tenantList;
        formState.currentCompanyId = org.currentCompanyId;
        formState.currentDeptId = org.currentDeptId;
      }

      async function changeOrg(tenantId: string) {
        formData.tenant = tenantId;
      }

      async function changeTenant() {
        formData.orgId = null;
      }

      function disabledItem(tenant: Recordable) {
        return !tenant.state || !tenant.employeeState;
      }

      function getTenantName(tenant: Recordable) {
        if (!tenant) {
          return '';
        }
        // const name = tenant?.name?.length > 10 ? tenant?.name?.substr(0, 10) + '...' : tenant?.name;
        const name = tenant?.name;
        const strList = [name];
        if (!tenant.state) {
          // 企业被禁用
          strList.push(t('layout.header.disabledTips'));
        } else if (!tenant.employeeState) {
          // 员工被禁用
          strList.push(t('layout.header.disabledTips'));
        } else if (tenant.isDefault) {
          // 默认企业
          strList.push(t('layout.header.defaultTips'));
        }
        return strList.join(' ');
      }

      async function setDefaults(tenant: Recordable, e: Event) {
        e?.stopPropagation();
        e?.preventDefault();
        createConfirm({
          iconType: 'warning',
          title: t('layout.header.setDefaultTips', { value: tenant?.name }),
          content: t('layout.header.setDefaultTipsContent'),
          onOk: async () => {
            try {
              await updateDefaultTenant(tenant.id as string);
              await loadOrgByTenant(tenant.id);
            } catch (e) {}
          },
        });
      }

      function switchTenantConfirm() {
        const tenant = formState.tenantList?.find((item) => item.id === formData.tenant);
        if (!tenant) {
          createMessage.error(t('layout.header.switchTenantTips'));
          throw Error('无法切换该企业，请选择正常的企业');
        }
        if (!tenant.state) {
          createMessage.error(t('layout.header.tenantDisabledTips'));
          throw Error('该企业已被禁用');
        }

        if (!tenant.employeeState) {
          createMessage.error(t('layout.header.employeeDisabledTips'));
          throw Error('您在该公司的账号被禁用，请联系公司管理员');
        }

        for (const tenant of formState.tenantList) {
          if (tenant.id === formData.tenant) {
            if (tenant.orgList && tenant.orgList.length > 0 && !formData.orgId) {
              createMessage.error(t('layout.header.selectOrgTips'));
              throw Error('请选择机构');
            }
          }
        }

        createConfirm({
          iconType: 'warning',
          content: t('layout.header.switchTenantConfirmTips', { value: tenant?.name }),
          onOk: async () => {
            try {
              const userInfo = await userStore.switchTenantAndOrg(formData.tenant, formData.orgId);
              if (userInfo) {
                createMessage.success(t('layout.header.switchTenantTipsContent'));
              }
            } catch (e) {}
          },
        });
      }

      async function handleSubmit() {
        try {
          setModalProps({ confirmLoading: true });
          switchTenantConfirm();
          emit('success');
          closeModal();
        } finally {
          setModalProps({ confirmLoading: false });
        }
      }

      function getOrgName(org: BaseOrgResultVO) {
        let name = `[${ORG_TYPE_MAP.get(org.type)}] `;

        name += org.name;
        if (
          (formState.currentDeptId !== null && formState.currentDeptId === org.id) ||
          (formState.currentDeptId === null && formState.currentCompanyId === org.id)
        ) {
          name += t('layout.header.currentTips');
        }
        return name;
      }

      return {
        t,
        registerModal,
        formData,
        formState,
        currentTenantId,
        getTenantName,
        switchTenantConfirm,
        setDefaults,
        handleSubmit,
        disabledItem,
        changeTenant,
        getOrgName,
        changeOrg,
      };
    },
  });
</script>
