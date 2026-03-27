export default {
  footer: { onlinePreview: 'Preview', onlineDocument: 'Document' },
  header: {
    // user dropdown
    registeredEnterprise: 'Registered enterprise',
    dropdownItemDoc: 'Document',
    dropdownItemDocFrontEnd: 'FrontEnd Document',
    dropdownItemLoginOut: 'Login Out',
    dropdownProfile: 'Profile',

    enterprise: 'Enterprise',
    switchingCompanies: 'Switching Companies',
    setAsDefault: 'Set as default',
    switchingCompaniesTips: {
      title: 'Attention',
      content: [
        '1.【User】：A user is a unique user data on the platform. The data in the user table can be understood as an account. The uniqueness is determined by unique information such as phone number, ID card, login account, etc. The id in the user table is similar to the unionId on the WeChat platform.',
        '2.【Employee】：A user belongs to a tenant, and he is an employee of that tenant. A user can belong to multiple tenants, and the relationship between user and employee is many-to-many. When a user belongs to multiple tenants, there is one data in the user table and multiple data in the employee table. The id in the employee table is similar to the openId on the WeChat platform.',
        '3.【Enterprise】：A tenant; A user can belong to multiple enterprises. When a user belongs to multiple enterprises, the default enterprise will be entered when logging in to the system.',
        '4.【Unit】：An "Employee" can belong to multiple units under an "Enterprise". An Employee can be directly attached to a unit or a department. It is recommended to attach it to a department.',
        '5.【Department】：An "Employee" can belong to multiple departments under an "Enterprise".',
      ],
    },
    disabledTips: '(Disabled)',
    defaultTips: '(Default)',
    setDefaultTips: 'Are you sure to modify {value} as the default enterprise?',
    setDefaultTipsContent:
      'The modification will take effect immediately, and the default enterprise will be entered when logging in next time!',
    switchTenantTips: 'Unable to switch the enterprise, please select a normal enterprise',
    tenantDisabledTips: 'The enterprise has been disabled',
    employeeDisabledTips:
      'Your account in the company has been disabled, please contact the company administrator',
    selectOrgTips: 'Please select the organization',
    switchTenantConfirmTips: 'Are you sure to switch to the enterprise: {value} ?',
    switchTenantTipsContent: 'Switch successful',
    currentTips: '(Current)',
    tooltipErrorLog: 'Error log',
    tooltipLock: 'Lock screen',
    tooltipNotify: 'Notification',

    tooltipEntryFull: 'Full Screen',
    tooltipExitFull: 'Exit Full Screen',

    // lock
    lockScreenPassword: 'Lock screen password',
    lockScreen: 'Lock screen',
    lockScreenBtn: 'Locking',

    home: 'Home',
  },
  multipleTab: {
    reload: 'Refresh current',
    close: 'Close current',
    closeLeft: 'Close Left',
    closeRight: 'Close Right',
    closeOther: 'Close Other',
    closeAll: 'Close All',
  },
  setting: {
    // content mode
    contentModeFull: 'Full',
    contentModeFixed: 'Fixed width',
    // topMenu align
    topMenuAlignLeft: 'Left',
    topMenuAlignRight: 'Center',
    topMenuAlignCenter: 'Right',
    // menu trigger
    menuTriggerNone: 'Not Show',
    menuTriggerBottom: 'Bottom',
    menuTriggerTop: 'Top',
    // menu type
    menuTypeSidebar: 'Left menu mode',
    menuTypeMixSidebar: 'Left menu mixed mode',
    menuTypeMix: 'Top Menu Mix mode',
    menuTypeTopMenu: 'Top menu mode',

    on: 'On',
    off: 'Off',
    minute: 'Minute',

    operatingTitle: 'Successful!',
    operatingContent:
      'The copy is successful, please go to src/settings/projectSetting.ts to modify the configuration!',
    resetSuccess: 'Successfully reset!',

    copyBtn: 'Copy',
    clearBtn: 'Clear cache and to the login page',

    drawerTitle: 'Configuration',

    darkMode: 'Dark mode',
    navMode: 'Navigation mode',
    interfaceFunction: 'Interface function',
    interfaceDisplay: 'Interface display',
    animation: 'Animation',
    splitMenu: 'Split menu',
    closeMixSidebarOnChange: 'Switch page to close menu',

    sysTheme: 'System theme',
    headerTheme: 'Header theme',
    sidebarTheme: 'Menu theme',

    menuDrag: 'Drag Sidebar',
    menuSearch: 'Menu search',
    menuAccordion: 'Sidebar accordion',
    menuCollapse: 'Collapse menu',
    collapseMenuDisplayName: 'Collapse menu display name',
    topMenuLayout: 'Top menu layout',
    menuCollapseButton: 'Menu collapse button',
    contentMode: 'Content area width',
    expandedMenuWidth: 'Expanded menu width',

    breadcrumb: 'Breadcrumbs',
    breadcrumbIcon: 'Breadcrumbs Icon',
    tabs: 'Tabs',
    tabDetail: 'Tab Detail',
    tabsQuickBtn: 'Tabs quick button',
    tabsRedoBtn: 'Tabs redo button',
    tabsFoldBtn: 'Tabs flod button',
    tabsTheme: 'tabs theme',
    tabsThemeSmooth: 'Smooth',
    tabsThemeCard: 'Card',
    tabsThemeSimple: 'Simple',
    sidebar: 'Sidebar',
    header: 'Header',
    footer: 'Footer',
    fullContent: 'Full content',
    grayMode: 'Gray mode',
    colorWeak: 'Color Weak Mode',
    backgroundWatermark: 'Background Watermark',

    progress: 'Progress',
    switchLoading: 'Switch Loading',
    switchAnimation: 'Switch animation',
    animationType: 'Animation type',

    autoScreenLock: 'Auto screen lock',
    notAutoScreenLock: 'Not auto lock',

    fixedHeader: 'Fixed header',
    fixedSideBar: 'Fixed Sidebar',

    mixSidebarTrigger: 'Mixed menu Trigger',
    triggerHover: 'Hover',
    triggerClick: 'Click',

    mixSidebarFixed: 'Fixed expanded menu',
  },
};
