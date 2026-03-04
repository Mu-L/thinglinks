import { defineStore } from 'pinia';
import { router } from '@/router';
/* enum */ 
import {
  APPLICATION_ID_KEY,
  APPLICATION_NAME_KEY,
  EXPIRE_TIME_KEY,
  REFRESH_TOKEN_KEY,
  ROLES_KEY,
  TENANT_ID_KEY,
  TOKEN_KEY,
  USER_INFO_KEY,
} from '@/enums/cacheEnum';
import { PageEnum } from '@/enums/pageEnum'
import { StorageEnum } from '@/enums/storageEnum'
import type { LoginParamVO, LogoutParams } from '@/api/thinglinks/common/model/userModel';
import type { ErrorMessageMode } from '/#/axios';
import type { DefUserInfoResultVO } from '/#/store';

/* api */
import { getUserInfoById, loginApi, logout, switchTenantAndOrg } from '@/api/thinglinks/common/oauth'
/* utils */
import { getAuthCache, setAuthCache } from '@/utils/auth';
import { routerTurnByName, cryptoEncode, setLocalStorage } from '@/utils'
/* hooks */
import { useGlobSetting } from '@/hooks/setting'

const globSetting = useGlobSetting();
const DEF_APP_ID = globSetting.defApplicationId;
const { GO_LOGIN_INFO_STORE } = StorageEnum;

interface UserState {
  userInfo: Nullable<DefUserInfoResultVO>;
  token?: string;
  roleList: RoleEnum[];
  sessionTimeout?: boolean;
  lastUpdateTime: number;
  refreshToken?: string;
  expireTime?: string;
  tenantId?: string;
  applicationId: string;
  applicationName: string;
}

export const useUserStore = defineStore({
  id: 'app-user',
  state: (): UserState => ({
    // user info
    userInfo: null,
    // token
    token: undefined,
    // roleList
    roleList: [],
    // Whether the login expired
    sessionTimeout: false,
    // Last fetch time
    lastUpdateTime: 0,
    refreshToken: '',
    expireTime: '',
    // 租户ID
    tenantId: '',
    // 应用id
    applicationId: '',
    applicationName: '',
  }),
  getters: {
    // 当前用户信息
    getUserInfo(): DefUserInfoResultVO {
      return this.userInfo || getAuthCache<DefUserInfoResultVO>(USER_INFO_KEY) || {};
    },
    // 当前用户的Token
    getToken(): string {
      return this.token || getAuthCache<string>(TOKEN_KEY);
    },
    getLastUpdateTime(): number {
      return this.lastUpdateTime;
    },
    // 当前租户ID
    getTenantId(): string {
      return this.tenantId || getAuthCache<string>(TENANT_ID_KEY);
    },
    // 当前应用ID
    getApplicationId(): string {
      return this.applicationId || getAuthCache<string>(APPLICATION_ID_KEY);
    },
    // 当前应用名称
    getApplicationName(): string {
      return this.applicationName || getAuthCache<string>(APPLICATION_NAME_KEY);
    },
    getSessionTimeout(): boolean {
      return !!this.sessionTimeout;
    },
  },
  actions: {
    setToken(info: string | undefined) {
      this.token = info ? info : ''; // for null or undefined value
      setAuthCache(TOKEN_KEY, info);
    },
    setRefreshToken(info: string) {
      this.refreshToken = info;
      setAuthCache(REFRESH_TOKEN_KEY, info);
    },
    setExpireTime(info: string) {
      this.expireTime = info;
      setAuthCache(EXPIRE_TIME_KEY, info);
    },
    // 租户id
    setTenantId(info: string) {
      this.tenantId = info;
      setAuthCache(TENANT_ID_KEY, info);
    },
    setSessionTimeout(flag: boolean) {
      this.sessionTimeout = flag;
    },

    async switchTenantAndOrg(companyId: string, orgId?: string | null) {
      try {
        const { isSuccess = false, code = null, data = {}, errorMsg } = await switchTenantAndOrg(companyId, orgId);
        if (isSuccess && code === 0 && data) {
          const { token, tenantId, refreshToken, expiration } = data;
          // save token
          this.setToken(token);
          this.setRefreshToken(refreshToken);
          this.setExpireTime(expiration);
          this.setTenantId(tenantId);

          this.setSessionTimeout(false);
          window['$message'].success('切换成功，即将刷新页面');
          setTimeout(() => {
            location.reload();
          }, 200);
        } else {
          window['$message'].error(errorMsg || '切换租户失败，请重试');
        }
      } catch (error) {
        return Promise.reject(error);
      }
    },

    /**
    * @description: login
    */
    async login(
      params: LoginParamVO & {
        goHome?: boolean;
        mode?: ErrorMessageMode;
      },
    ): Promise<DefUserInfoResultVO | null> {
      try {
        const { goHome = true, mode, ...loginParams } = params;
        const data:any = await loginApi(loginParams, mode);
        if (data.isSuccess) {
          const { token, tenantId, refreshToken, expiration } = data.data;
          window['$message'].success(window['$t']('login.login_success'));
          setLocalStorage(
            GO_LOGIN_INFO_STORE,
            cryptoEncode(
              JSON.stringify({
                username: params.username,
                password: params.password,
              })
            )
          );
          // save token
          this.setRefreshToken(refreshToken);
          this.setExpireTime(expiration);
          this.setTenantId(tenantId);
          this.setToken(token);
          routerTurnByName(PageEnum.BASE_HOME_NAME, true)
          
          return this.getUserInfoAction(mode, true);
        }else{
          window['$message'].error(data.msg)
          return Promise.reject(data);
        }
      } catch (error) {
        const {data} = error.response
        window['$message'].error(data.msg)
        return Promise.reject(data);
      }
    },
    // 刷新时加载用户信息
    async getUserInfoAction(
      mode: ErrorMessageMode = 'none',
      isSetAppId = false,
    ): Promise<DefUserInfoResultVO> {
      const userInfo = await getUserInfoById()
      if (isSetAppId) {
        this.setApplicationId(userInfo?.defApplication?.id ?? DEF_APP_ID)
        this.setApplicationName(userInfo?.defApplication?.name ?? '')
      }
      if (userInfo) {
        //@ts-ignore
        this.setUserInfo(userInfo)
        //@ts-ignore
        return userInfo
      } else {
        throw new Error('获取用户信息失败')
      }
    },
    setUserInfo(info: DefUserInfoResultVO | null) {
      this.userInfo = info;
      this.lastUpdateTime = new Date().getTime();
      setAuthCache(USER_INFO_KEY, info);
    },
    setApplicationId(info: string) {
      this.applicationId = info;
      setAuthCache(APPLICATION_ID_KEY, info);
    },
    setApplicationName(info: string) {
      this.applicationName = info;
      setAuthCache(APPLICATION_NAME_KEY, info);
    },
    /**
     * @description: logout
     */
    async logout() {
      if (this.getToken) {
        const param: LogoutParams = {
          token: this.getToken,
        };
        await logout(param).finally(() => {
          this.setToken('');
          routerTurnByName(PageEnum.BASE_LOGIN_NAME);
        });
      } else {
        this.setToken('');
        routerTurnByName(PageEnum.BASE_LOGIN_NAME);
      }
    },
  }
})
