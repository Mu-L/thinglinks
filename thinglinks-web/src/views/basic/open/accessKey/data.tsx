import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { Tag, Tooltip } from 'ant-design-vue';
import { dateUtil } from '/@/utils/dateUtil';
import { KeyFormatEnum } from '/@/enums/biz/base';

const { t } = useI18n();

// 列表页字段
export const columns = (openShowDrawer): BasicColumn[] => {
  return [
    {
      title: 'AppID',
      dataIndex: 'appId',
    },
    {
      title: t('basic.open.accessKey.name'),
      dataIndex: 'name',
    },
    {
      title: t('basic.open.accessKey.hasKeys'),
      dataIndex: 'hasKeys',
      width: 100,
      customRender: ({ record }) => {
        function handleView(_, e: Event) {
          e?.stopPropagation();
          openShowDrawer(true, {
            record,
          });
        }

        return (
          <a-button type="link" onClick={handleView.bind(null, record)}>
            查看
          </a-button>
        );
      },
    },
    {
      title: t('basic.open.accessKey.status'),
      dataIndex: 'status',
      width: 80,
      format: (_, record) => {
        return record.status === 1 ? t('thinglinks.common.enable') : t('thinglinks.common.disable');
      },
    },
    {
      title: t('basic.open.accessKey.auditStatus'),
      dataIndex: 'auditStatus',
      width: 100,
      customRender: ({ record }) => {
        switch (record.auditStatus) {
          case 1:
            return <Tooltip title="请等待审核">{record.echoMap?.auditStatus}</Tooltip>;
          case 2:
            return <Tooltip title="请下载你的秘钥，开始开发吧">{record.echoMap?.auditStatus}</Tooltip>;
          case 99:
            const str = `审核意见： ${record.reviewComments} | 审核时间：${record.auditTime}`;
            return <Tooltip title={str}>{record.echoMap?.auditStatus}</Tooltip>;
          default:
            return <Tooltip title="请提交申请">{record.echoMap?.auditStatus}</Tooltip>;
        }
      },
    },
    {
      title: t('common.expirationDate'),
      dataIndex: 'expirationTime',
      width: 150,
      customRender: ({ record }) => {
        if (record.endExpirationTime) {
          if (dateUtil(record.endExpirationTime).isBefore(Date.now())) {
            return <Tag color="error">{t('common.expired')}</Tag>;
          } else if (
            dateUtil(record.endExpirationTime).isBefore(dateUtil().add(30, 'days'))) {
            const duration = dateUtil.duration(dateUtil(record.endExpirationTime).diff(Date.now()));
            if (duration.days() > 0) {
              return <Tag color="warning">{t('common.expiresInDays', {s: duration.days() + 1})}</Tag>;
            } else {
              return <Tag color="warning">{t('common.expiresInHours', {s: duration.hours()})}</Tag>;
            }
          } else {
            return <Tag color="processing">{record.endExpirationTime}</Tag>;
          }
        } else {
          return <Tag color="success">{t('common.permanentlyValid')}</Tag>;
        }
      },
    },
    {
      title: t('basic.open.accessKey.remark'),
      dataIndex: 'remark',
      width: 180,
    },
    {
      title: t('thinglinks.common.createdTime'),
      dataIndex: 'createdTime',
      sorter: true,
      width: 180,
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('AppID'),
      field: 'appId',
      component: 'Input',
      colProps: { span: 8 },
    },
  ];
};

// 编辑页字段
export const editFormSchema = (_): FormSchema[] => {
  return [
    {
      field: 'id',
      label: 'ID',
      component: 'Input',
      show: false,
    },
    {
      field: 'appId',
      label: 'AppID',
      component: 'Input',
      componentProps: {
        placeholder: '系统自动生成',
        disabled: true,
      },
    },
    {
      label: t('basic.open.accessKey.name'),
      field: 'name',
      component: 'Input',
      required: true,
    },
    {
      label: t('basic.open.accessKey.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
  ];
};

export const showFormSchema = (_): FormSchema[] => {
  return [
    {
      field: 'id',
      label: 'ID',
      component: 'Input',
      show: false,
    },
    {
      field: 'appId',
      label: 'AppID',
      component: 'Input',
      componentProps: {
        placeholder: '系统自动生成',
      },
    },
    {
      label: t('秘钥格式'),
      field: 'keyFormat',
      component: 'RadioButtonGroup',
      componentProps: {
        disabled: true,
        options: [
          {
            label: 'PKCS8(Java适用)',
            value: KeyFormatEnum.PKCS8,
          },
          {
            label: 'PKCS1(非Java适用)',
            value: KeyFormatEnum.PKCS1,
          },
        ],
      },
    },
    {
      field: 'privateKeyIsv',
      label: '开发者私钥',
      helpMessage: '用来生成签名',
      component: 'InputTextArea',
      componentProps: {
        // readonly: true,
        showWordLimit: false,
        autosize: { minRows: 2, maxRows: 4 },
      },
    },
    {
      field: 'publicKeyPlatform',
      label: '平台公钥',
      helpMessage: '用来校验签名是否正确',
      component: 'InputTextArea',
      componentProps: {
        // readonly: true,
        showWordLimit: false,
        autosize: { minRows: 2, maxRows: 4 },
      },
    },
  ];
};

export const downloadTemplate = `1. 请妥善保管以下参数，切勿将其泄露给第三方，切勿将其配置在客户端！
2. 请定期重置 开发者私钥、平台公钥

AppID(*):
{appId}

开发者私钥(*): 在调用接口时使用，用于生成签名
{privateKeyIsv}

平台公钥: 在接收到接口返回值时使用，用于验证签名
{publicKeyPlatform}

`;
