import { DescItem } from '/@/components/Description/index';
import { BasicColumn } from '/@/components/Table/src/types/table';
import { Tooltip, message } from 'ant-design-vue';
import { CopyOutlined } from '@ant-design/icons-vue';
import { handleCopyTextV2 } from '/@/utils/thinglinks/common.tsx';
import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();


const isJSON = (value) => {
  try {
    JSON.parse(value);
    return true;
  } catch (err) {
    return false;
  }
};

export function getRunningDetailColumns(firstColumn): BasicColumn[] {
  const defaultColumns = [
    {
      title: 'ts',
      width: 150,
      dataIndex: 'ts',
    },
    {
      title: 'event_time',
      width: 150,
      dataIndex: 'event_time',
    },
  ];
  defaultColumns.unshift({
    ...firstColumn,
    width: 150,
    ellipsis: true,
    customRender: (value) => {
      const val = typeof value.value === 'object' ? JSON.stringify(value.value) : `${value.value}`;
      return (
        <div>
          <CopyOutlined
            onClick={() => handleCopyTextV2(val || '')}
            style={{ marginRight: '10px' }}
          />
          <Tooltip placement="topLeft" title={val}>
            <span>{val}</span>
          </Tooltip>
        </div>
      );
    },
  });
  return defaultColumns;
}

export function getRunningDetailSchema(): DescItem[] {
  return [
    {
      field: 'propertyName',
      label: t('iot.link.device.device.running.propertyName'),
    },
    {
      field: 'unit',
      label: t('iot.link.device.device.running.unit'),
    },
    {
      field: 'min',
      label: t('iot.link.device.device.running.min'),
    },
    {
      field: 'max',
      label: t('iot.link.device.device.running.max'),
    },
    {
      field: 'description',
      label: t('iot.link.device.device.running.description'),
      span: 2,
    },
    {
      field: 'remark',
      label: t('iot.link.device.device.remark'),
      span: 2,
    },
  ];
}
