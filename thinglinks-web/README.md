<div align="center">

<a href="https://mqttsnet.com"><img src="../docs/images/logo.png" alt="ThingLinks" width="160"></a>

# thinglinks-web

**ThingLinks IoT Platform — Admin Console**

[English](README.md) | [简体中文](README.zh-CN.md) | [日本語](README.ja.md) | [한국어](README.ko.md)

</div>

---

## About

`thinglinks-web` is the main admin console for the [ThingLinks](https://github.com/mqttsnet/thinglinks) multi-tenant SaaS cloud IoT platform, built with Vue 3 + TypeScript + Vite.

## Tech Stack

![Vue 3](https://img.shields.io/badge/Vue-3.3-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-4.6-3178C6?style=flat-square&logo=typescript&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-4.3-646CFF?style=flat-square&logo=vite&logoColor=white)
![Pinia](https://img.shields.io/badge/Pinia-2.0-FFD859?style=flat-square)
![Ant Design Vue](https://img.shields.io/badge/Ant%20Design%20Vue-3.x-1890FF?style=flat-square&logo=antdesign&logoColor=white)
![ECharts](https://img.shields.io/badge/ECharts-5.x-AA344D?style=flat-square&logo=apacheecharts&logoColor=white)

## Features

- Multi-tenant SaaS architecture (NONE / COLUMN / DATASOURCE_COLUMN)
- Dynamic route-based permission management
- Configurable themes and internationalization (i18n)
- Rich component library based on Ant Design Vue
- Data visualization with ECharts
- Mock data scheme for development

## Quick Start

### Requirements

- **Node.js** >= 18.0.0
- **pnpm** (install: `npm install -g pnpm`)

### Install & Run

```bash
# Install dependencies
pnpm install

# Start dev server (default: http://localhost:3100)
pnpm dev

# Build for production
pnpm build

# Build for test environment
pnpm build:test
```

### Environment Configuration

| File | Environment |
|------|-------------|
| `.env` | Base config |
| `.env.development` | Development |
| `.env.test` | Test |
| `.env.production` | Production |

### Other Commands

```bash
pnpm preview         # Preview production build locally
pnpm build:no-cache  # Build after clearing cache
pnpm clean:cache     # Clear Vite cache
pnpm clean:lib       # Remove node_modules
pnpm lint:prettier   # Format code
```

## License

This project is part of [ThingLinks](https://github.com/mqttsnet/thinglinks). Community edition is licensed under [Apache License 2.0](../LICENSE) with additional terms — see [LICENSE-COMMERCIAL](../LICENSE-COMMERCIAL).

Copyright &copy; 2019-present [MqttsNet](https://mqttsnet.com)
