interface GroupItem {
  title: string;
  icon: string;
  color: string;
  desc: string;
  date: string;
  group: string;
}

interface NavItem {
  title: string;
  icon: string;
  color: string;
  href: string;
}

interface DynamicInfoItem {
  percent: number;
  name: string;
  date: string;
  desc: string;
}

export const navItems: NavItem[] = [
  {
    title: '首页',
    icon: 'ion:home-outline',
    color: '#1fdaca',
  },
  {
    title: '仪表盘',
    icon: 'ion:grid-outline',
    color: '#bf0c2c',
  },
  {
    title: '组件',
    icon: 'ion:layers-outline',
    color: '#e18525',
  },
  {
    title: '系统管理',
    icon: 'ion:settings-outline',
    color: '#3fb27f',
  },
  {
    title: '权限管理',
    icon: 'ion:key-outline',
    color: '#4daf1bc9',
  },
  {
    title: '图表',
    icon: 'ion:bar-chart-outline',
    color: '#00d8ff',
  },
];

export const dynamicInfoItems: DynamicInfoItem[] = [
  {
    percent: 0,
    name: '4.13.0 尚未开始：',
    date: '2022-12-??',
    desc: `<a href="###">录制配套视频</a>`,
  },
  {
    percent: 100,
    name: '4.12.5 已发布：',
    date: '2022-11-16',
    desc: `<a href="###">《ThingLinks》4.12.5 已发布，升级Spring Boot 2.7.6</a>`,
  },
  {
    percent: 100,
    name: '4.12.4 已发布：',
    date: '2022-11-16',
    desc: `<a href="###">《ThingLinks》4.12.4 已发布，调整 cloud 版本，thinglinks-oauth-userinfo 模块依赖关系 </a>`,
  },
  {
    percent: 100,
    name: '4.12.3 已发布：',
    date: '2022-11-16',
    desc: `<a href="###">《ThingLinks》4.12.3 已发布，增加经常使用接口的缓存、按阿里代码规范、FindBug规范扫描代码，并修复beta版本的bug</a>`,
  },
  {
    percent: 100,
    name: '4.12.3-beta 已发布：',
    date: '2022-11-07',
    desc: `<a href="###">《ThingLinks》4.12.3-beta 已发布，增加缓存、按阿里代码规范、FindBug规范扫描代码</a>`,
  },
  {
    percent: 100,
    name: '4.12.2 已发布：',
    date: '2022-10-17',
    desc: `<a href="###">《ThingLinks》4.12.2 已发布，资源维护优化、代码优化</a>`,
  },
  {
    percent: 100,
    name: '4.12.1-beta 已发布：',
    date: '2022-10-08',
    desc: `<a href="###">《ThingLinks》4.12.1-beta已发布，认证服务重构 & 前端项目优化</a>`,
  },
  {
    percent: 100,
    name: '4.12.0-beta 已发布：',
    date: '2022-09-28',
    desc: `<a href="###">《ThingLinks》4.12.0-beta已发布，前端项目优化</a>`,
  },
  {
    percent: 100,
    name: '4.11.0 已发布：',
    date: '2022-09-21',
    desc: `<a href="###">《ThingLinks》4.11.0已发布，DATASOURCE_COLUMN模式中的COLUMN部分</a>`,
  },
  {
    percent: 100,
    name: '4.10.0 已发布：',
    date: '2022-08-16',
    desc: `<a href="###">《ThingLinks》4.10.0发布，适配Oracle、SQL Server</a>`,
  },
  {
    percent: 100,
    name: '4.10.0-beta.0 已发布：',
    date: '2022-08-09',
    desc: `<a href="###">《ThingLinks》4.10.0-beta.0发布，适配SQL Server</a>`,
  },
  {
    percent: 100,
    name: '4.9.0 已发布：',
    date: '2022-08-01',
    desc: `<a href="###">《ThingLinks》4.9.0 正式版发布，消息中心全新上线，支持解析Groovy脚本、动态替换Freemarker格式的消息模板、提供消息发送接口</a>`,
  },
  {
    percent: 100,
    name: '4.9.0-beta.0 已发布：',
    date: '2022-07-21',
    desc: `<a href="###">《ThingLinks》4.9.0-beta.0发布，消息中心重构，支持发送短信、邮件、站内信</a>`,
  },
  {
    percent: 100,
    name: '4.8.0 已发布：',
    date: '2022-06-28',
    desc: `<a href="###">《ThingLinks》4.8.0发布，Activiti 工作流首次发布(仅none、column模式)</a>`,
  },
  {
    percent: 100,
    name: '4.7.0 已发布：',
    date: '2022-06-17',
    desc: `<a href="###">《ThingLinks》4.7.0发布，代码生成器支持批量生成、预览、下载、修改；支持下载项目结构;</a>`,
  },
  {
    percent: 100,
    name: '4.6.1 已发布：',
    date: '2022-05-24',
    desc: `<a href="###">《ThingLinks》4.6.1发布，修复fastjson安全漏洞！</a>`,
  },
  {
    percent: 100,
    name: '4.6.0 已发布：',
    date: '2022-05-10',
    desc: `<a href="###">代码生成器适配3种模式、6个项目，column、none模式首次发布！</a>`,
  },
  {
    percent: 100,
    name: '4.5.2 已发布：',
    date: '2022-04-12',
    desc: `<a href="###">spring boot 2.6.6、代码生成器支持生成项目、集成vxe-table、升级ant-design-vue3.1.1</a>`,
  },
  {
    percent: 100,
    name: '4.5.1 已发布：',
    date: '2022-03-29',
    desc: `<a href="###">资源维护页面显示优化、资源维护页面配置接口功能优化、通过接口获取真实的后台存活接口数据、配置代码生成器功能相关的菜单、uri、按钮等资源</a>`,
  },
  {
    percent: 100,
    name: '4.5.0 已发布：',
    date: '2022-03-28',
    desc: `<a href="###">可视化代码生成器</a>`,
  },
  {
    percent: 100,
    name: '4.4.1 已发布：',
    date: '2022-03-07',
    desc: `<a href="###">修复 spring cloud gateway 漏洞</a>`,
  },
  {
    percent: 100,
    name: '4.4.0 已发布：',
    date: '2022-02-22',
    desc: `<a href="###">COLUMN模式上线，以租户ID字段隔离租户数据</a>`,
  },
  {
    percent: 100,
    name: '4.3.0 已发布：',
    date: '2022-01-17',
    desc: `<a href="###">新增完整的数据权限配置、控制、鉴权、拦截流程</a> `,
  },
  {
    percent: 100,
    name: '4.2.0 已发布：',
    date: '2021-12-29',
    desc: `<a href="###">spring boot单体版首发、4.x版本文档首发</a> `,
  },
  {
    percent: 100,
    name: '4.1.0 已发布：',
    date: '2021-12-16',
    desc: `<a href="###">服务合并、租户库合并、URI权限校验等近百余项更新</a> `,
  },
  {
    percent: 100,
    name: '4.0.0 已发布：',
    date: '2021-11-18',
    desc: `<a href="###">全新的租户体系、应用体系、权限体系</a>`,
  },
];

export const groupItems: GroupItem[] = [
  {
    title: '基础平台',
    icon: 'carbon:logo-github',
    color: '',
    desc: '不要等待机会，而要创造机会。',
    group: '有效',
    date: '2021-04-01',
  },
  {
    title: '运营系统',
    icon: 'ion:logo-vue',
    color: '#3fb27f',
    desc: '现在的你决定将来的你。',
    group: '已过期',
    date: '2021-04-01',
  },
  {
    title: 'Html5',
    icon: 'ion:logo-html5',
    color: '#e18525',
    desc: '没有什么才能比努力更重要。',
    group: '上班摸鱼',
    date: '2021-04-01',
  },
  {
    title: 'Angular',
    icon: 'ion:logo-angular',
    color: '#bf0c2c',
    desc: '热情和欲望可以突破一切难关。',
    group: 'UI',
    date: '2021-04-01',
  },
  {
    title: 'React',
    icon: 'bx:bxl-react',
    color: '#00d8ff',
    desc: '健康的身体是实目标的基石。',
    group: '技术牛',
    date: '2021-04-01',
  },
  // {
  //   title: 'Js',
  //   icon: 'ion:logo-javascript',
  //   color: '#4daf1bc9',
  //   desc: '路是走出来的，而不是空想出来的。',
  //   group: '架构组',
  //   date: '2021-04-01',
  // },
];
