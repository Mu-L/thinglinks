package com.mqttsnet.thinglinks.model.constant;

/**
 * Echo注解中dictType的常量
 * <p>
 * 存放系统中常用的类型
 * <p>
 * 本类中的 @thinglinks.generator auto insert 请勿删除
 *
 * @author mqttsnet
 * @date 2019/07/26
 */
public interface EchoDictType {
    // @thinglinks.generator auto insert EchoDictType

    /**
     * 全局字典类型
     */
    interface Global {
        // @thinglinks.generator auto insert Global

        /**
         * 行政级别
         * [10-国家 20-省份/直辖市 30-地市 40-区县 50-乡镇]
         */
        String AREA_LEVEL = "GLOBAL_AREA_LEVEL";
        /**
         * 民族
         * [01-汉族 02-...]
         */
        String NATION = "GLOBAL_NATION";
        /**
         * 学历
         * [01-小学 02-中学 03-高中 04-专科 05-本科 06-硕士 07-博士 08-博士后 99-其他]
         */
        String EDUCATION = "GLOBAL_EDUCATION";
        /**
         * 性别
         */
        String SEX = "Sex";
        /**
         * 激活状态
         * [10-未激活 20-已激活]
         */
        String ACTIVE_STATUS = "GLOBAL_ACTIVE_STATUS";
        /**
         * 数据类型
         * [10-系统值 20-业务值]
         */
        String DATA_TYPE = "GLOBAL_DATA_TYPE";
        String httpMethod = "httpMethod";
    }

    /**
     * 基础服务
     */
    interface Base {
        // @thinglinks.generator auto insert Base
        String MSG_INTERFACE_LOGGING_STATUS = "MsgInterfaceLoggingStatusEnum";
        String INTERFACE_EXEC_MODE = "InterfaceExecModeEnum";
        String MSG_TEMPLATE_TYPE = "MsgTemplateTypeEnum";
        String NOTICE_TARGET = "NOTICE_TARGET";
        String NOTICE_REMIND_MODE = "NoticeRemindModeEnum";

        String CreationMethodEnum = "CreationMethodEnum";
        String AuditStatusEnum = "AuditStatusEnum";
        String DataScopeEnum = "DataScopeEnum";
        String DefTenantStatusEnum = "DefTenantStatusEnum";
        String FileStorageType = "FileStorageType";
        String MsgInterfaceLoggingStatusEnum = "MsgInterfaceLoggingStatusEnum";
        String VxeComponentEnum = "VxeComponentEnum";
        String ComponentEnum = "ComponentEnum";
        String FileOverrideStrategyEnum = "FileOverrideStrategyEnum";
        String GenTypeEnum = "GenTypeEnum";
        String NoticeRemindModeEnum = "NoticeRemindModeEnum";
        String ProjectTypeEnum = "ProjectTypeEnum";
        String SqlConditionEnum = "SqlConditionEnum";
        String DefTenantRegisterTypeEnum = "DefTenantRegisterTypeEnum";
        String FileType = "FileType";
        String LogType = "LogType";
        String SuperClassEnum = "SuperClassEnum";
        String TemplateEnum = "TemplateEnum";
        String ClientTypeEnum = "ClientTypeEnum";
        String GrantType = "GrantType";
        String MsgTemplateCodeEnum = "MsgTemplateCodeEnum";
        String OrgTypeEnum = "OrgTypeEnum";
        String ProductType = "ProductType";
        String TaskStatus = "TaskStatus";
        String DateType = "DateType";
        String DefGenTestSimpleType2Enum = "DefGenTestSimpleType2Enum";
        String InterfaceExecModeEnum = "InterfaceExecModeEnum";
        String RoleCategoryEnum = "RoleCategoryEnum";
        String UserStatusEnum = "UserStatusEnum";
        String DataTypeEnum = "DataTypeEnum";
        String DefGenTestTreeType2Enum = "DefGenTestTreeType2Enum";
        String LoginStatusEnum = "LoginStatusEnum";
        String PopupTypeEnum = "PopupTypeEnum";
        String TplEnum = "TplEnum";
        String DictClassifyEnum = "DictClassifyEnum";
        String EntitySuperClassEnum = "EntitySuperClassEnum";
        String NotificationChannelType = "NotificationChannelType";
        String ResourceTypeEnum = "ResourceTypeEnum";
        String SoyComponentEnum = "SoyComponentEnum";
        String TenantConnectTypeEnum = "TenantConnectTypeEnum";
        String ActiveStatusEnum = "ActiveStatusEnum";
        String MsgTemplateTypeEnum = "MsgTemplateTypeEnum";
        String ResourceOpenWithEnum = "ResourceOpenWithEnum";
        String SourceType = "SourceType";
        String SendStatus = "SendStatus";
        String ProviderType = "ProviderType";
        String MsgBizType = "MsgBizType";
        String MsgType = "MsgType";
        String ApplicationAppTypeEnum = "ApplicationAppTypeEnum";
        String TenantStatusEnum = "TenantStatusEnum";
        String TenantTypeEnum = "TenantTypeEnum";
        String AuthorizeType = "AuthorizeType";
        String DataScopeType = "DataScopeType";

        /**
         * 职位状态
         * [10-在职 20-离职]
         */
        String POSITION_STATUS = "BASE_POSITION_STATUS";

        /**
         * 机构类型
         * [10-单位 20-部门]
         */
        String ORG_TYPE = "OrgTypeEnum";

        /**
         * 角色类别
         * [10-功能角色 20-桌面角色 30-数据角色]
         */
        String ROLE_CATEGORY = "RoleCategoryEnum";
    }

    /**
     * 租户服务
     */
    interface System {
        // @thinglinks.generator auto insert System

        String DefTenantRegisterTypeEnum = "DefTenantRegisterTypeEnum";
        /**
         * 数据范围 [01-全部 02-本单位及子级 03-本单位 04-本部门 05-本部门及子级 06-个人 07-自定义]
         */
        String RESOURCE_DATA_SCOPE = "DataScopeEnum";
        /**
         * 资源类型 [10-应用 20-菜单 30-视图 40-按钮 50-字段 06-数据]
         */
        String RESOURCE_TYPE = "ResourceTypeEnum";
        /**
         * 打开方式 [01-组件 02-内链 03-外链]
         */
        String RESOURCE_OPEN_WITH = "ResourceOpenWithEnum";

        /**
         * 字典分类 [10-系统字典 20-业务字典]
         */
        String DICT_CLASSIFY = "DictClassifyEnum";

        /**
         * 应用类型 [10-自建应用 20-第三方应用]
         */
        String APPLICATION_TYPE = "TENANT_APPLICATION_TYPE";
        /**
         * 授权类型 [10-应用授权 20-应用续期 30-取消授权]
         */
        String APPLICATION_GRANT_TYPE = "ApplicationGrantTypeEnum";
        /**
         * 参数类型 [10-系统参数 20-业务参数]
         */
        String PARAMETER_TYPE = "TENANT_PARAMETER_TYPE";
        /**
         * 地区来源
         * [10-爬取 20-新增]
         */
        String AREA_SOURCE = "TENANT_AREA_SOURCE";
        /**
         * 客户端类型
         * [10-WEB网站;15-移动端应用;20-手机H5网页;25-内部服务; 30-第三方应用]
         */
        String CLIENT_TYPE = "TENANT_CLIENT_TYPE";
        /**
         * 租户审批状态
         * [05-正常 10-待初始化 15-已撤回 20-待审核 25-已拒绝 30-已同意]
         */
        String TENANT_STATUS = "DefTenantStatusEnum";
        /**
         * 登录状态
         * [01-登录成功 02-验证码错误 03-密码错误 04-账号锁定 05-切换租户 06-短信验证码错误]
         */
        String LOGIN_STATUS = "LoginStatusEnum";


    }

    /**
     * 开放平台管理端
     */
    interface SopAdmin {
        // @thinglinks.generator auto insert SopAdmin
        String NotifyStatusEnum = "NotifyStatusEnum";
    }

    /**
     * 认证服务
     */
    interface Oauth {
        // @thinglinks.generator auto insert Oauth

    }

    /**
     * 文件服务
     */
    interface File {
        // @thinglinks.generator auto insert File

    }

    /**
     * 消息服务
     */
    interface Msg {
        // @thinglinks.generator auto insert Msg

    }

    /**
     * 网关服务
     */
    interface Gateway {
        // @thinglinks.generator auto insert Gateway

    }

    /**
     * Link服务
     */
    interface Link {
        // @thinglinks.generator auto insert Link

        /**
         * 设备认证方式
         * [0-用户名密码 1-ssl证书]
         */
        String LINK_DEVICE_AUTH_MODE = "LINK_DEVICE_AUTH_MODE";

        /**
         * 设备加密方式
         * [0-明文传输 1-SM4 2-AES]
         */
        String LINK_DEVICE_ENCRYPT_METHOD = "LINK_DEVICE_ENCRYPT_METHOD";

        /**
         * 设备连接实例
         */
        String LINK_DEVICE_CONNECTOR = "LINK_DEVICE_CONNECTOR";

        /**
         * 设备状态
         */
        String LINK_DEVICE_STATUS = "LINK_DEVICE_STATUS";

        /**
         * 设备连接状态
         */
        String LINK_DEVICE_CONNECT_STATUS = "LINK_DEVICE_CONNECT_STATUS";


        /**
         * 设备类型
         */
        String LINK_DEVICE_NODE_TYPE = "LINK_DEVICE_NODE_TYPE";
        /**
         * 设备分组类型
         */
        String LINK_DEVICE_GROUP_TYPE = "LINK_DEVICE_GROUP_TYPE";

        /**
         * ACL规则等级
         */
        String LINK_ACL_RULE_LEVEL = "LINK_ACL_RULE_LEVEL";

        /**
         * ACL规则动作类型
         */
        String LINK_ACL_RULE_ACTION_TYPE = "LINK_ACL_RULE_ACTION_TYPE";

        /**
         * 产品数据格式
         */
        String LINK_PRODUCT_DATA_FORMAT = "LINK_PRODUCT_DATA_FORMAT";


        /**
         * 产品协议类型
         */
        String LINK_PRODUCT_PROTOCOL_TYPE = "LINK_PRODUCT_PROTOCOL_TYPE";


        /**
         * 产品状态
         */
        String LINK_PRODUCT_STATUS = "LINK_PRODUCT_STATUS";


        /**
         * 产品类型
         */
        String LINK_PRODUCT_TYPE = "LINK_PRODUCT_TYPE";


        /**
         * 产品服务类型
         */
        String LINK_PRODUCT_SERVICE_TYPE = "LINK_PRODUCT_SERVICE_TYPE";


        /**
         * 产品服务属性是否必填
         */
        String LINK_PRODUCT_SERVICE_REQUIRED = "LINK_PRODUCT_SERVICE_REQUIRED";


        /**
         * 产品服务属性访问模式
         */
        String LINK_PRODUCT_SERVICE_ACCESS_MODE = "ACCESS_MODE";


        /**
         * 产品服务命令数据类型
         */
        String LINK_PRODUCT_SERVICE_COMMAND_DATA_TYPE = "LINK_PRODUCT_SERVICE_COMMAND_DATA_TYPE";


        /**
         * 设备动作类型
         */
        String LINK_DEVICE_ACTION_TYPE = "LINK_DEVICE_ACTION_TYPE";


        /**
         * 产品服务属性数据类型
         */
        String LINK_PRODUCT_SERVICE_PROPERTY_DATA_TYPE = "LINK_PRODUCT_SERVICE_PROPERTY_DATA_TYPE";

        /**
         * LINK_PRODUCT_TOPIC_SUBSCRIBER 产品Topic订阅者
         */
        String LINK_PRODUCT_TOPIC_SUBSCRIBER = "LINK_PRODUCT_TOPIC_SUBSCRIBER";

        /**
         * LINK_PRODUCT_TOPIC_PUBLISHER 产品Topic发布者
         */
        String LINK_PRODUCT_TOPIC_PUBLISHER = "LINK_PRODUCT_TOPIC_PUBLISHER";
        /**
         * LINK_PRODUCT_TOPIC_FUNCTION_TYPE 产品Topic功能类型
         */
        String LINK_PRODUCT_TOPIC_FUNCTION_TYPE = "LINK_PRODUCT_TOPIC_FUNCTION_TYPE";
        /**
         * LINK_PRODUCT_TOPIC_TYPE 产品Topic类型
         */
        String LINK_PRODUCT_TOPIC_TYPE = "LINK_PRODUCT_TOPIC_TYPE";
        /**
         * LINK_OTA_UPGRADE_SCOPE OTA任务升级范围
         */
        String LINK_OTA_UPGRADE_SCOPE = "LINK_OTA_UPGRADE_SCOPE";
        /**
         * LINK_OTA_UPGRADE_METHOD OTA任务升级方式
         */
        String LINK_OTA_UPGRADE_METHOD = "LINK_OTA_UPGRADE_METHOD";

        /**
         * LINK_OTA_TASK_STATUS OTA任务状态
         */
        String LINK_OTA_TASK_STATUS = "LINK_OTA_TASK_STATUS";
        /**
         * LINK_OTA_PACKAGES_STATUS OTA升级包状态
         */
        String LINK_OTA_PACKAGES_STATUS = "LINK_OTA_PACKAGES_STATUS";
        /**
         * LINK_OTA_PACKAGES_STATUS OTA升级包签名方法
         */
        String LINK_OTA_PACKAGES_SIGN_METHOD = "LINK_OTA_PACKAGES_SIGN_METHOD";
        /**
         * LINK_OTA_PACKAGES_TYPE OTA升级包类型
         */
        String LINK_OTA_PACKAGES_TYPE = "LINK_OTA_PACKAGES_TYPE";
        /**
         * LINK_OTA_TASK_RECORD_STATUS OTA升级任务记录状态
         */
        String LINK_OTA_TASK_RECORD_STATUS = "LINK_OTA_TASK_RECORD_STATUS";

        /**
         * LINK_OTA_TASK_RECORD_APP_CONFIRM_STATUS OTA升级任务记录APP确认状态
         */
        String LINK_OTA_TASK_RECORD_APP_CONFIRM_STATUS = "LINK_OTA_TASK_RECORD_APP_CONFIRM_STATUS";

        /**
         * LINK_OTA_TASK_RECORD_COMMAND_SEND_STATUS OTA升级任务记录指令下发状态
         */
        String LINK_OTA_TASK_RECORD_COMMAND_SEND_STATUS = "LINK_OTA_TASK_RECORD_COMMAND_SEND_STATUS";

    }

    // 新增内部 Xxx 接口后，请在PackageUtils的static代码块中新增 putDictType(EchoDictType.Xxx.class)， 否则代码生成器会重复生成
}
