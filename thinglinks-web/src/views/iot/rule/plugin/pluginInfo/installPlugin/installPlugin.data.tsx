import { FormProps, FormSchema } from '/@/components/Table';
import { BasicColumn } from '/@/components/Table/src/types/table';
import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();

export function getBasicColumns(): BasicColumn[] {
  return [
    {
      title: t('iot.rule.plugin.pluginInfo.instanceName'),
      dataIndex: 'instanceName',
    },
    {
      title: t('iot.rule.plugin.pluginInfo.instanceIp'),
      dataIndex: 'instanceIp',
    },
    // {
    //   title: t('iot.rule.plugin.pluginInfo.status'),
    //   dataIndex: 'status',
    //   slots: { customRender: 'status' },
    // },
    {
      title: t('iot.rule.plugin.pluginInfo.pluginIdentification'),
      dataIndex: 'instanceIdentification',
    },
    {
      title: t('iot.rule.plugin.pluginInfo.healthy'),
      dataIndex: 'healthy',
      slots: { customRender: 'healthy' },
    },
  ];
}
