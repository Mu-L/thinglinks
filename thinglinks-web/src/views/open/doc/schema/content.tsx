import { ref } from 'vue';

export const commonParams = ref([
  {
    name: 'app_id',
    type: 'String',
    maxLength: 32,
    required: 1,
    description: '应用id',
    example: '2014072300007148',
  },
  {
    name: 'method',
    type: 'String',
    maxLength: 128,
    required: 1,
    description: '接口名称',
    example: 'shop.order.create',
  },
  {
    name: 'format',
    type: 'String',
    maxLength: 40,
    required: 0,
    description: '返回结果格式,JSON/XML,固定填:JSON',
    example: 'JSON',
  },
  {
    name: 'charset',
    type: 'String',
    maxLength: 10,
    required: 1,
    description: '请求使用的编码格式，如utf-8,gbk,gb2312等',
    example: 'utf8',
  },
  {
    name: 'sign_type',
    type: 'String',
    maxLength: 10,
    required: 1,
    description: '商户生成签名字符串所使用的签名算法类型,固定填:RSA2',
    example: 'RSA2',
  },
  {
    name: 'sign',
    type: 'String',
    maxLength: 344,
    required: 1,
    description: '商户请求参数的签名串，详见签名',
    example: '',
  },
  {
    name: 'timestamp',
    type: 'String',
    maxLength: 19,
    required: 1,
    description: `发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"`,
    example: '2014-07-24 03:07:50',
  },
  {
    name: 'version',
    type: 'String',
    maxLength: 3,
    required: 1,
    description: '调用的接口版本，固定为：1.0',
    example: '1.0',
  },
  {
    name: 'app_auth_token',
    type: 'String',
    maxLength: 40,
    required: 0,
    description: '详见应用授权概述',
    example: 'xxxx',
  },
  {
    name: 'biz_content',
    type: 'String',
    maxLength: '不限',
    required: 1,
    description:
      '请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档',
    example: '',
  },
]);

export const resultData = ref([
  {
    name: 'code',
    type: 'String',
    description: `网关返回码`,
    example: `40004`,
  },
  {
    name: 'msg',
    type: 'String',
    description: `网关返回码描述`,
    example: `Business Failed`,
  },
  {
    name: 'sub_code',
    type: 'String',
    description: `业务返回码，参见具体的API接口文档`,
    example: `isv.invalid-parameter`,
  },
  {
    name: 'sub_msg',
    type: 'String',
    description: `业务返回码描述，参见具体的API接口文档`,
    example: `参数不正确`,
  },
  {
    name: 'data',
    type: 'Object',
    description: '返回数据，见下表 业务返回参数',
    example: '',
  },
]);
