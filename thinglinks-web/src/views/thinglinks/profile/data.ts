import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();
export interface ListItem {
  key: string;
  title: string;
  description?: string;
  extra?: string;
  avatar?: string;
  color?: string;
}

// tab的list
export const settingList = [
  {
    key: '1',
    name: t('thinglinks.profile.tabTitle.basic'),
    component: 'BaseSetting',
  },
  {
    key: '2',
    name: t('thinglinks.profile.tabTitle.security'),
    component: 'SecureSetting',
  },
  {
    key: '3',
    name: t('thinglinks.profile.tabTitle.loginLog'),
    component: 'LoginLog',
  },
  {
    key: '4',
    name: t('thinglinks.profile.tabTitle.accountBind'),
    component: 'AccountBind',
  },
  {
    key: '5',
    name: t('thinglinks.profile.tabTitle.notification'),
    component: 'MsgNotify',
  },
];

// 账号绑定 list
export const accountBindList: ListItem[] = [
  {
    key: '1',
    title: t('thinglinks.profile.bind.bindTaobao'),
    description: t('thinglinks.profile.bind.bindTaobao'),
    extra: t('thinglinks.profile.bind.bind'),
    avatar: 'ri:taobao-fill',
    color: '#ff4000',
  },
  {
    key: '2',
    title: t('thinglinks.profile.bind.bindAlipay'),
    description: t('thinglinks.profile.bind.noAlipay'),
    extra: t('thinglinks.profile.bind.bind'),
    avatar: 'fa-brands:alipay',
    color: '#2eabff',
  },
  {
    key: '3',
    title: t('thinglinks.profile.bind.bindDingTalk'),
    description: t('thinglinks.profile.bind.noDingTalk'),
    extra: t('thinglinks.profile.bind.bind'),
    avatar: 'ri:dingding-fill',
    color: '#2eabff',
  },
];

// 新消息通知 list
export const msgNotifyList: ListItem[] = [
  {
    key: '1',
    title: t('thinglinks.profile.msg.accountPassword'),
    description: t('thinglinks.profile.msg.userMessage'),
  },
  {
    key: '2',
    title: t('thinglinks.profile.msg.systemMessage'),
    description: t('thinglinks.profile.msg.systemNotify'),
  },
  {
    key: '3',
    title: t('thinglinks.profile.msg.todoTask'),
    description: t('thinglinks.profile.msg.todoNotify'),
  },
];
