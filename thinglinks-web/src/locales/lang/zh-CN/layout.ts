export default {
  footer: { onlinePreview: '在线预览', onlineDocument: '在线文档' },
  header: {
    // user dropdown
    registeredEnterprise: '注册企业',
    dropdownItemDoc: '文档',
    dropdownItemDocFrontEnd: '前端文档',
    dropdownItemLoginOut: '退出系统',
    dropdownProfile: '个人中心',

    enterprise: '企业',
    switchingCompanies: '切换企业和机构',
    setAsDefault: '设为默认',
    switchingCompaniesTips: {
      title: '注意事项',
      content: [
        '1.【用户】：即账号，任何人在本平台都有一条唯一的用户数据，用户表中的数据可理解为账号，通过手机号、身份证、登录账号等唯一信息来确定唯一性。用户表的id类似于微信平台的unionId.',
        '2.【员工】：用户属于某个租户，他就是这个租户的员工，一个用户可以属于多个租户，用户和员工是一对多关系。一个用户属于多个租户时，在用户表有1条数据，在员工表有多条数据。员工表的id类似于微信平台的openId。',
        '3.【企业】：即租户；1个用户可以属于多个企业，当用户属于多个企业，登录系统时，会进入默认企业。',
        '4.【单位】："员工"在某个"企业"下可以属于多个单位；员工可以直接挂在单位下，也可以挂在部门下，建议挂在部门下。',
        '5.【部门】："员工"在某个"企业"下可以属于多个部门；',
      ],
    },
    disabledTips: '(已禁用)',
    defaultTips: '(默认)',
    setDefaultTips: '确定要修改【{value}】为默认企业？',
    setDefaultTipsContent: '修改会立即生效，设置为默认企业后，下次登录将默认进入该企业！',
    switchTenantTips: '无法切换该企业，请选择正常的企业',
    tenantDisabledTips: '该企业已被禁用',
    employeeDisabledTips: '您在该公司的账号被禁用，请联系公司管理员',
    selectOrgTips: '请选择机构',
    switchTenantConfirmTips: '是否确认切换到企业： {value} ？',
    switchTenantTipsContent: '切换成功',
    currentTips: '(当前)',

    // tooltip
    tooltipErrorLog: '错误日志',
    tooltipLock: '锁定屏幕',
    tooltipNotify: '消息通知',

    tooltipEntryFull: '全屏',
    tooltipExitFull: '退出全屏',

    // lock
    lockScreenPassword: '锁屏密码',
    lockScreen: '锁定屏幕',
    lockScreenBtn: '锁定',

    home: '首页',
  },
  multipleTab: {
    reload: '重新加载',
    close: '关闭标签页',
    closeLeft: '关闭左侧标签页',
    closeRight: '关闭右侧标签页',
    closeOther: '关闭其它标签页',
    closeAll: '关闭全部标签页',
  },
  setting: {
    // content mode
    contentModeFull: '流式',
    contentModeFixed: '定宽',
    // topMenu align
    topMenuAlignLeft: '居左',
    topMenuAlignRight: '居中',
    topMenuAlignCenter: '居右',
    // menu trigger
    menuTriggerNone: '不显示',
    menuTriggerBottom: '底部',
    menuTriggerTop: '顶部',
    // menu type
    menuTypeSidebar: '左侧菜单模式',
    menuTypeMix: '顶部菜单混合模式',
    menuTypeTopMenu: '顶部菜单模式',
    menuTypeMixSidebar: '左侧菜单混合模式',

    on: '开',
    off: '关',
    minute: '分钟',

    operatingTitle: '操作成功',
    operatingContent: '复制成功,请到 src/settings/projectSetting.ts 中修改配置！',
    resetSuccess: '重置成功！',

    copyBtn: '拷贝',
    clearBtn: '清空缓存并返回登录页',

    drawerTitle: '项目配置',

    darkMode: '主题',
    navMode: '导航栏模式',
    interfaceFunction: '界面功能',
    interfaceDisplay: '界面显示',
    animation: '动画',
    splitMenu: '分割菜单',
    closeMixSidebarOnChange: '切换页面关闭菜单',

    sysTheme: '系统主题',
    headerTheme: '顶栏主题',
    sidebarTheme: '菜单主题',

    menuDrag: '侧边菜单拖拽',
    menuSearch: '菜单搜索',
    menuAccordion: '侧边菜单手风琴模式',
    menuCollapse: '折叠菜单',
    collapseMenuDisplayName: '折叠菜单显示名称',
    topMenuLayout: '顶部菜单布局',
    menuCollapseButton: '菜单折叠按钮',
    contentMode: '内容区域宽度',
    expandedMenuWidth: '菜单展开宽度',

    breadcrumb: '面包屑',
    breadcrumbIcon: '面包屑图标',
    tabs: '标签页',
    tabDetail: '标签详情页',
    tabsQuickBtn: '标签页快捷按钮',
    tabsRedoBtn: '标签页刷新按钮',
    tabsFoldBtn: '标签页折叠按钮',
    tabsTheme: '标签页样式',
    tabsThemeSmooth: '圆滑',
    tabsThemeCard: '卡片',
    tabsThemeSimple: '极简',
    sidebar: '左侧菜单',
    header: '顶栏',
    footer: '页脚',
    fullContent: '全屏内容',
    grayMode: '灰色模式',
    colorWeak: '色弱模式',
    backgroundWatermark: '背景水印',

    progress: '顶部进度条',
    switchLoading: '切换loading',
    switchAnimation: '切换动画',
    animationType: '动画类型',

    autoScreenLock: '自动锁屏',
    notAutoScreenLock: '不自动锁屏',

    fixedHeader: '固定header',
    fixedSideBar: '固定Sidebar',

    mixSidebarTrigger: '混合菜单触发方式',
    triggerHover: '悬停',
    triggerClick: '点击',

    mixSidebarFixed: '固定展开菜单',
  },
};
