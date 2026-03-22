<template>
  <PageWrapper>
    <template #headerContent>
      <WorkbenchHeader />
    </template>
    <div v-if="isUser">
      <div>
        <!-- <QuickNav :loading="loading"/> -->

        <ApplicationCard
          :api="findMyApplication"
          :class="['enter-y', myAppCls]"
          title="我的应用 (点击应用进行切换应用，体验不同应用的功能)"
          @more="handleStart(true)"
        />

        <!-- <Card :loading="loading" class="!my-4 enter-y">
          <img :src="illustration" class="mx-auto xl:h-50 h-30" />
        </Card> -->

        <!-- <SaleRadar :loading="loading" class="enter-y" /> -->
      </div>
    </div>
    <div v-else class="p-8">
      <Empty
        :image="illustration"
        :image-style="{
          'justify-content': 'center',
          'align-items': 'center',
          display: 'flex',
          height: '250px',
        }"
      >
        <template #description>
          <div class="mx-auto mt-10 mb-10" style="font-size: 1.75rem"> 您还不属于任何企业</div>
        </template>
        <a-button :class="[rtCls]" type="primary" @click="handleTenant">注册企业</a-button>
        <a-button :class="[beCls, 'ml-20']" type="primary" @click="handleEmployee">
          成为员工
        </a-button>
      </Empty>
    </div>
  </PageWrapper>
</template>
<script lang="ts">
  export default {
    name: 'Welcome',
  };
</script>
<script lang="ts" setup>
  import { computed, ref, unref, onMounted, nextTick } from 'vue';
  import { Empty } from 'ant-design-vue';
  import { useI18n } from '/@/hooks/web/useI18n';
  const { t } = useI18n();
  import { PageWrapper } from '/@/components/Page';
  import WorkbenchHeader from './components/WorkbenchHeader.vue';
  import ApplicationCard from './components/ApplicationCard.vue';
  import { useUserStore } from '/@/store/modules/user';
  import { isDevMode } from '/@/utils/env';
  import { useRouter } from 'vue-router';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useDesign } from '/@/hooks/web/useDesign';
  import QuickNav from './components/QuickNav.vue';
  import { findMyApplication } from '/@/api/thinglinks/profile/userInfo';
  import illustration from '/@/assets/svg/illustration.svg';

  import Driver from 'driver.js';
  import 'driver.js/dist/driver.min.css';
  // import { nextTick } from 'process';

  const loading = ref(false);
  const userStore = useUserStore();
  const { createSuccessModal } = useMessage();
  const { replace } = useRouter();
  const isUser = computed(
    () => userStore.getUserInfo?.employeeId && userStore.getUserInfo?.employeeId !== '0',
  );
  const { prefixCls: myAppCls, prefixVar } = useDesign('myApplication');
  const rtCls = `${prefixVar}-register-tenant`;
  const beCls = `${prefixVar}-become-employee`;

  function handleEmployee() {
    createSuccessModal({ content: '请联系贵公司管理员邀请您加入公司。' });
  }

  const driver = new Driver({
    allowClose: false, //禁止点击外部关闭
    keyboardControl: false, // 是否允许通告键盘控制(escape关闭，箭头键用于移动)
    doneBtnText: '知道了', // 完成按钮标题
    closeBtnText: '跳过', // 关闭按钮标题
    nextBtnText: '下一步 &rarr;', // 下一步按钮标题
    prevBtnText: '上一步 &larr;', // 上一步按钮标题
  });

  function handleStart(flag = false) {
    let steps = [] as any[];
    // 可以根据自身的需求，改成用户首次登陆系统时提示（可以通过redis记录用户是否首次登陆，或增加一张表来记录。）
    if (unref(isUser)) {
      steps = [
        {
          // element: `.${prefixVar}-layout-header-left`,
          element: document.querySelector(`.${prefixVar}-layout-header-left`)!,
          stageBackground: 'rgb(255, 255, 255, 0.3)',
          popover: {
            title: '欢迎访问',
            description:
              '👋👋👋 欢迎体验《ThingLinks》物联网一体化平台 <br/><span style="color: red;">首次使用，请认真阅读引导！</span>',
            position: 'right',
          },
        },
        {
          // element: `.${prefixVar}-layout-header-action__tenant-item`,
          element: document.querySelector(`.${prefixVar}-layout-header-action__tenant-item`)!,
          stageBackground: 'rgb(255, 255, 255, 0.3)',
          popover: {
            // title: '切换企业',
            title: '<span style="color: red;">切换企业 和 组织机构</span>',
            description: '当您属于多个企业或组织机构时，可以在此切换当前所属的企业和组织机构。',
            position: 'bottom',
          },
        },
        {
          element: document.querySelector(`.${prefixVar}-layout-header-action__userinfo`)!,
          stageBackground: 'rgb(255, 255, 255, 0.3)',
          popover: {
            title: '用户功能区',
            description: '您可以在此修改您的个人信息。',
            position: 'bottom-right',
          },
        },
        {
          element: document.querySelector(`.${prefixVar}-layout-header-action__setting`)!,
          stageBackground: 'rgb(255, 255, 255, 0.3)',
          popover: {
            title: '前端布局设置',
            description: '您可以在此修改系统的前端布局样式。',
            position: 'bottom-right',
          },
        },
        {
          element: document.querySelector(`.${myAppCls}`)!,
          popover: {
            title: '我的应用',
            description:
              '当您所在的企业购买了多个应用时，可以在此切换应用，每个应用拥有不同的功能。',
          },
        },
        {
          element: document.querySelector(`.${prefixVar}-basicPlatform`)!,
          popover: {
            title: '基础平台',
            description:
              '企业(租户)的工作台，拥有平台最基础最核心的功能，开发者可以继续开发和完善平台的基础功能。',
          },
        },
        {
          element: document.querySelector(`.${prefixVar}-devOperation`)!,
          popover: {
            title: '开发运营系统',
            description:
              '开发者或运营者使用的系统，拥有平台级功能；开发者或运营者可以在此给企业（租户）开通企业账号和应用权限等。',
          },
        },
        {
          element: document.querySelector(`.${prefixVar}-businessSystem`)!,
          popover: {
            title: '业务系统',
            description:
              '此系统是开发者根据自身的业务需求进行二次开发。开发完毕后，通过应用授权或购买的方式给企业开通访问权限。',
          },
        },
      ];
    } else {
      steps = [
        {
          element: document.querySelector(`.${prefixVar}-layout-header-left`)!,
          stageBackground: 'rgb(255, 255, 255, 0.3)',
          popover: {
            title: '欢迎访问',
            description:
              '👋👋👋 欢迎体验《ThingLinks》多租户快速开发平台 <br/> <span style="color: red;">首次使用，请认真阅读引导！</span>',
          },
        },
        {
          element: document.querySelector(`.${rtCls}`)!,
          popover: {
            title: '注册企业',
            description: '您可以点击此按钮，填写企业信息成为企业主~',
            position: 'left',
          },
        },
        {
          element: document.querySelector(`.${beCls}`)!,
          popover: {
            title: '成为员工',
            description: '您也可以申请成为其他企业的员工~',
            position: 'right',
          },
        },
        {
          element: document.querySelector(`.${prefixVar}-layout-header-action__tenant-item`)!,
          stageBackground: 'rgb(255, 255, 255, 0.3)',
          popover: {
            title: '当前企业',
            description: '当您属于多个企业或组织机构时，可以在此切换当前所属的企业和组织机构。',
            position: 'bottom',
          },
        },
        {
          element: document.querySelector(`.${prefixVar}-layout-header-action__userinfo`)!,
          stageBackground: 'rgb(255, 255, 255, 0.3)',
          popover: {
            title: '用户功能区',
            description: '您可以在此修改您的个人信息。',
            position: 'bottom-right',
          },
        },
        {
          element: document.querySelector(`.${prefixVar}-layout-header-action__setting`)!,
          stageBackground: 'rgb(255, 255, 255, 0.3)',
          popover: {
            title: '前端布局设置',
            description: '您可以在此修改系统的前端布局样式。',
            position: 'bottom-right',
          },
        },
      ];
    }
    if (true) {
      console.log('steps', steps);
      console.log('driver', driver.defineSteps(steps));
      // if (!isDevMode() || flag) {
      driver.reset();
      driver.defineSteps(steps);
      driver.start();
    }
  }

  onMounted(async () => {
    // 必须要setTimeout才能让document.querySelector正确选取某些元素？
    // 这里是否可以优化，知道的朋友欢迎pr  需要等元素加载完才可以 否则会报错
    // nextTick(() => {
    //   handleStart()
    // })
    // TODO 先去掉提示，等后续完善后再开启
    // setTimeout(() => handleStart(), 3000);
  });

  function handleTenant() {
    replace({
      name: 'myTenantInfo',
    });
  }
</script>

<style lang="less" scoped>
  .ant-empty-image img {
    margin: auto;
    height: 100%;
  }
</style>
