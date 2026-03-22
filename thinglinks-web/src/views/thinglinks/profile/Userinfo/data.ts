import { FormSchema } from '/@/components/Form/index';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { DictEnum } from '/@/enums/commonEnum';
import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();

// 基础设置 form
export const userInfoSchemas: FormSchema[] = [
  {
    field: 'id',
    component: 'Input',
    label: 'ID',
    colProps: { span: 18 },
    show: false,
  },
  {
    field: 'username',
    component: 'Input',
    label: t('thinglinks.profile.basic.account'),
    colProps: { span: 18 },
    dynamicDisabled: () => {
      return true;
    },
  },
  {
    field: 'email',
    component: 'Input',
    label: t('thinglinks.profile.basic.email'),
    colProps: { span: 18 },
    dynamicDisabled: () => {
      return true;
    },
  },
  {
    field: 'mobile',
    component: 'Input',
    label: t('thinglinks.profile.basic.mobile'),
    colProps: { span: 18 },
    dynamicDisabled: () => {
      return true;
    },
  },
  {
    field: 'idCard',
    component: 'Input',
    label: t('thinglinks.profile.basic.idCard'),
    colProps: { span: 18 },
  },
  {
    field: 'nickName',
    component: 'Input',
    label: t('thinglinks.profile.basic.nickname'),
    colProps: { span: 18 },
  },
  {
    field: 'sex',
    component: 'ApiSelect',
    label: t('thinglinks.profile.basic.gender'),
    colProps: { span: 18 },
    componentProps: {
      ...dictComponentProps(DictEnum.SEX),
    },
  },
  {
    field: 'nation',
    component: 'ApiSelect',
    label: t('thinglinks.profile.basic.nation'),
    colProps: { span: 18 },
    componentProps: {
      ...dictComponentProps(DictEnum.NATION),
    },
  },
  {
    field: 'education',
    component: 'ApiSelect',
    label: t('thinglinks.profile.basic.education'),
    colProps: { span: 18 },
    componentProps: {
      ...dictComponentProps(DictEnum.EDUCATION),
    },
  },
  {
    field: 'workDescribe',
    component: 'InputTextArea',
    label: t('thinglinks.profile.basic.introduction'),
    colProps: { span: 18 },
  },
];
