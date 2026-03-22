export interface SopIsvKeysPageQuery {
  isvId?: string; // ISV
  keyFormat?: number; // 秘钥格式
  publicKeyIsv?: string; // 开发者生成的公钥
  privateKeyIsv?: string; // 开发者生成的私钥
  publicKeyPlatform?: string; // 平台生成的公钥
  privateKeyPlatform?: string; // 平台生成的私钥
}

export interface SopIsvKeysUpdateVO {
  isvId?: string; // ISV
  keyFormat?: number; // 秘钥格式
  publicKeyIsv?: string; // 开发者生成的公钥
  privateKeyIsv?: string; // 开发者生成的私钥
  publicKeyPlatform?: string; // 平台生成的公钥
  privateKeyPlatform?: string; // 平台生成的私钥
}

export interface SopIsvKeysResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 添加时间
  updatedTime?: string; // 修改时间
  createdBy?: string; // 创建人id
  updatedBy?: string; // 修改人id
  isvId?: string; // ISV
  keyFormat?: number; // 秘钥格式
  publicKeyIsv?: string; // 开发者生成的公钥
  privateKeyIsv?: string; // 开发者生成的私钥
  publicKeyPlatform?: string; // 平台生成的公钥
  privateKeyPlatform?: string; // 平台生成的私钥
}

export interface SopIsvKeysSaveVO {
  isvId?: string; // ISV
  keyFormat?: number; // 秘钥格式
  publicKeyIsv?: string; // 开发者生成的公钥
  privateKeyIsv?: string; // 开发者生成的私钥
  publicKeyPlatform?: string; // 平台生成的公钥
  privateKeyPlatform?: string; // 平台生成的私钥
}

export interface IsvKeysGenVO {
  keyFormat?: number; // 秘钥格式
}

export interface KeyStore {
  publicKey?: string;
  privateKey?: string;
}
