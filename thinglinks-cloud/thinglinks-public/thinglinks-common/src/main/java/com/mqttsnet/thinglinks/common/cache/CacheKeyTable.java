package com.mqttsnet.thinglinks.common.cache;

/**
 * 用于同一管理和生成缓存的key， 避免多个项目使用的key重复
 * <p>
 * 使用@Cacheable时， 其value值一定要在此处指定
 *
 * @author mqttsnet
 * @date 2020/10/21
 */
public interface CacheKeyTable {

    /**
     * 验证码 前缀
     * 完整key: captcha:{key} -> str
     */
    String CAPTCHA = "captcha";
    /**
     * token 前缀
     * 完整key： token:{token} -> userid
     */
    String TOKEN = "token";


    //------------------

    // 权限系统缓存 start
    /**
     * 总 登录次数
     * total_login_pv:{TENANT} -> Long
     */
    String TOTAL_LOGIN_PV = "total_login_pv";
    /**
     * 今日 登录次数
     * today_login_pv:{TENANT}:{today} -> Long
     */
    String TODAY_LOGIN_PV = "today_login_pv";
    /**
     * 今日登录总ip
     * today_login_iv:{TENANT}:{today} -> int
     */
    String TODAY_LOGIN_IV = "today_login_iv";
    /**
     * 今日登录总ip
     * TOTAL_LOGIN_IV:{TENANT} -> int
     */
    String TOTAL_LOGIN_IV = "total_login_iv";
    /**
     * 今日 PV
     * today_pv:{TENANT} -> int
     */
    String TODAY_PV = "today_pv";
    /**
     * 总 PV
     * total_pv:{TENANT} -> int
     */
    String TOTAL_PV = "total_pv";
    /**
     * 最近10访问记录
     * login_log_tenday:{TENANT}:{today}:{account} -> Map
     */
    String LOGIN_LOG_TEN_DAY = "login_log_tenday";
    /**
     * 登录总次数
     * login_log_browser:{TENANT} -> Map
     */
    String LOGIN_LOG_BROWSER = "login_log_browser";
    /**
     * 登录总次数
     * login_log_system{TENANT} -> Map
     */
    String LOGIN_LOG_SYSTEM = "login_log_system";
    /**
     * 参数 前缀
     * 完整key: parameter_key:{key} -> obj
     */
    String PARAMETER_KEY = "parameter_key";
    /**
     * 在用用户 前缀
     * 完整key: online:{userid} -> token (String)
     */
    String ONLINE = "online";
    /**
     * 用户token 前缀
     * 完整key: token_user_id:{token} -> userid (Long)
     */
    String TOKEN_USER_ID = "token_user_id";
    /**
     * 用户注册 前缀
     * 完整key: register:{注册类型}:{手机号}
     */
    String REGISTER_USER = "register";

    interface System {

        /**
         * 租户
         */
        String TENANT = "def_tenant";
        /**
         * 应用
         */
        String APPLICATION = "def_application";
        /**
         * 默认字典
         */
        String DICT = "def_dict";
        /**
         * 默认参数
         */
        String DEF_PARAMETER = "def_parameter";

        /**
         * 用户 前缀
         */
        String DEF_USER = "def_user";
        /**
         * 客户端
         */
        String DEF_CLIENT = "def_client";

        /**
         * 租户拥有的资源
         */
        String TENANT_APPLICATION_RESOURCE = "t_a_r";
        /**
         * 租户拥有的应用
         */
        String TENANT_APPLICATION = "t_a";

        /**
         * 资源
         */
        String RESOURCE = "dr";
        /**
         * 资源接口
         */
        String RESOURCE_API = "dra";
        String ALL_RESOURCE_API = "all_dra";
    }


    /**
     * 消息服务缓存 start
     */
    interface Base {

        /**
         * 租户自定义字典
         */
        String BASE_DICT = "base_dict";
        /**
         * 组织 前缀
         */
        String BASE_ORG = "base_org";
        /**
         * 岗位 前缀
         */
        String BASE_POSITION = "base_position";
        /**
         * 员工 前缀
         */
        String BASE_EMPLOYEE = "base_employee";
        /**
         * 全局员工 前缀
         */
        String DEF_USER_TENANT = "def_user_tenant";

        /**
         * 角色 前缀
         * 完整key: role:{roleId}
         */
        String ROLE = "role";
        /**
         * 角色拥有那些资源 前缀
         * 完整key: role_resource:{ROLE_ID} -> [RESOURCE_ID, ...]
         */
        String ROLE_RESOURCE = "role_resource";
        /**
         * 员工拥有那些角色 前缀
         * 完整key: employee_role:{EMPLOYEE_ID} -> [ROLE_ID, ...]
         */
        String EMPLOYEE_ROLE = "employee_role";

        /**
         * 角色拥有那些组织 前缀
         * 完整key: employee_org:{EMPLOYEE_ID} -> [ORG_ID, ...]
         */
        String EMPLOYEE_ORG = "employee_org";

        /**
         * 角色拥有那些组织 前缀
         * 完整key: org_role:{ORG_ID} -> [ROLE_ID, ...]
         */
        String ORG_ROLE = "org_role";
    }
    // 消息服务缓存 end

    // Mqs物联网业务系统缓存 start
    interface Mqs {


        /**
         * WS 协议 Session 前缀
         */
        String PROTOCOL_WS_SESSION = "def_protocol_ws_session";

    }

    // Mqs物联网业务系统缓存 end

    // Link物联网业务系统缓存 start
    interface Link {

        /**
         * 全局设备档案 前缀
         */
        String DEVICE = "def_device";

        /**
         * 全局设备ACL规则 前缀
         */
        String DEVICE_ACL_RULE = "def_device_acl_rule";

        /**
         * 全局产品信息 前缀
         */
        String PRODUCT = "def_product";

        /**
         * 全局产品模型 前缀
         */
        String PRODUCT_MODEL = "def_product_model";

        /**
         * 全局产品模型超级表 前缀
         */
        String PRODUCT_MODEL_SUPER_TABLE = "def_product_model_super_table";


        /**
         * 全局设备数据收集池 前缀
         */
        String DEVICE_DATA_COLLECTION_POOL = "def_device_data_collection_pool";

        /**
         * 全局设备动作收集池 前缀
         */
        String DEVICE_ACTION_COLLECTION_POOL = "def_device_action_collection_pool";


        /**
         * 全局设备上行原始数据收集池 前缀
         */
        String ASCENDING_DEVICE_ORIGINAL_DATA_COLLECTION_POOL = "def_ascending_device_original_data_collection_pool";

        /**
         * 全局设备下行原始数据收集池 前缀
         */
        String DESCENDING_DEVICE_ORIGINAL_DATA_COLLECTION_POOL = "def_descending_device_original_data_collection_pool";


        /**
         * 全局上行数据计数器 前缀
         */
        String UP_LINK_DATA_COUNTER = "def_up_link_data_counter";

        /**
         * 全局下行数据计数器 前缀
         */
        String DOWN_LINK_DATA_COUNTER = "def_down_link_data_counter";


        /**
         * OTA升级任务执行器偏移量 前缀
         */
        String OTA_UPGRADE_TASK_EXECUTOR_OFFSET = "def_ota_upgrade_task_executor_offset";

        /**
         * OTA升级记录 前缀
         */
        String OTA_UPGRADE_RECORDS = "def_ota_upgrade_records";
    }
    // Link物联网业务系统缓存 end

    // Rule 规则服务缓存 start
    interface Rule {

        /**
         * 默认插件信息  前缀
         */
        String DEF_PLUGIN_INFO = "def_plugin_info";


        /**
         * 默认规则脚本  前缀
         */
        String DEF_GROOVY_SCRIPT = "def_groovy_script";

    }
    // Rule规则服务缓存 end

    // Card物联卡业务系统缓存 start
    interface Card {

        /**
         * 移动 One Link平台 tokenKey  前缀
         */
        String DEF_CHANNEL_ONE_LINK_TOKEN_KEY = "def_channel_one_link_token_key";

    }
    // Card物联卡业务系统缓存 end


    // Video视频流系统缓存 start
    interface Video {


        /**
         * 全局流媒体服务器 前缀
         */
        String MEDIA_SERVER = "def_media_server";

        /**
         * 全局流媒体服务器 HOOK 前缀
         */
        String MEDIA_SERVER_HOOK = "def_media_server_hook";


        /**
         * 全局设备信息 前缀
         */
        String DEVICE_INFO = "def_device_info";

        /**
         * 全局设备通道信息 前缀
         */
        String DEVICE_CHANNEL = "def_device_channel";


        /**
         * 全局 SIP Session Call信息 前缀
         */
        String SIP_SESSION_CALL = "def_sip_session_call";


    }
    // Video视频流系统缓存 end
}
