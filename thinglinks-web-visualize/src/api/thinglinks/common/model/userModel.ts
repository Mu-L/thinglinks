
export type GrantType = 'CAPTCHA' | 'PASSWORD' | 'MOBILE' | 'REFRESH_TOKEN';

/**
 * @description: Login interface parameters
 */
export interface LoginParamVO {
  username?: string;
  password?: string;
  code?: string;
  key?: string;
  grantType: GrantType;
  refreshToken?: string;
  mobile?: string;
}

export interface RegisterVO {
  code: string;
  key: string;
  password: string;
  confirmPassword: GrantType;
  nickName?: string;
}

export interface RegisterByMobileVO extends RegisterVO {
  mobile: string;
}

export interface RegisterByEmailVO extends RegisterVO {
  email: string;
}

/**
 * @description: Login interface return value
 */
export interface LoginResultVO {
  tenantId: string;
  token: string;
  uuid: string;
  refreshToken: string;
  expire: string;
  expiration: string;
}

/**
 * @description: Logout interface parameters
 */
export interface LogoutParams {
  token: string;
}
