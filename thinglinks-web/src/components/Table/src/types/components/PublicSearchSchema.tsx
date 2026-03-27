//  此文件存放卡片式列表的搜索表单项
import { FormSchema } from '/@/components/Table';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { DictEnum } from '/@/enums/commonEnum';
import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();
/**
 * 产品列表搜索表单项
 */
export const productSearchSchema = (): FormSchema[] => {
  return [
    {
      label: t('iot.link.device.device.appId'),
      field: 'appId',
      colProps: { span: 8 },
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_APPLICATION_SCENARIO),
      },
    },
    {
      label: t('iot.link.product.product.productName'),
      field: 'productName',
      component: 'Input',
      colProps: { span: 8 },
    },
    {
      label: t('iot.link.product.product.productIdentification'),
      field: 'productIdentification',
      component: 'Input',
      colProps: { span: 8 },
    },
    {
      label: t('iot.link.product.product.manufacturerName'),
      field: 'manufacturerName',
      component: 'Input',
      colProps: { span: 8 },
    },
  ];
};
