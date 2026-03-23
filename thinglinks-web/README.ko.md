<div align="center">

<a href="https://mqttsnet.com"><img src="../docs/images/logo.png" alt="ThingLinks" width="160"></a>

# thinglinks-web

**ThingLinks IoT 플랫폼 — 관리 콘솔**

[English](README.md) | [简体中文](README.zh-CN.md) | [日本語](README.ja.md) | 한국어

</div>

---

## 소개

`thinglinks-web`은 [ThingLinks](https://github.com/mqttsnet/thinglinks) 멀티테넌트 SaaS 클라우드 IoT 플랫폼의 관리 콘솔 프론트엔드입니다. Vue 3 + TypeScript + Vite로 구축되었습니다.

## 기술 스택

![Vue 3](https://img.shields.io/badge/Vue-3.3-4FC08D?style=flat-square&logo=vuedotjs&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-4.6-3178C6?style=flat-square&logo=typescript&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-4.3-646CFF?style=flat-square&logo=vite&logoColor=white)
![Pinia](https://img.shields.io/badge/Pinia-2.0-FFD859?style=flat-square)
![Ant Design Vue](https://img.shields.io/badge/Ant%20Design%20Vue-3.x-1890FF?style=flat-square&logo=antdesign&logoColor=white)
![ECharts](https://img.shields.io/badge/ECharts-5.x-AA344D?style=flat-square&logo=apacheecharts&logoColor=white)

## 주요 기능

- 멀티테넌트 SaaS 아키텍처 (NONE / COLUMN / DATASOURCE_COLUMN)
- 동적 라우트 기반 권한 관리
- 테마 설정 및 국제화 (i18n) 지원
- Ant Design Vue 기반 풍부한 컴포넌트
- ECharts 데이터 시각화
- 개발용 Mock 데이터 지원

## 빠른 시작

### 환경 요구사항

- **Node.js** >= 18.0.0
- **pnpm** (설치: `npm install -g pnpm`)

### 설치 및 실행

```bash
# 의존성 설치
pnpm install

# 개발 서버 시작 (기본: http://localhost:3100)
pnpm dev

# 프로덕션 빌드
pnpm build

# 테스트 환경 빌드
pnpm build:test
```

## 라이선스

본 프로젝트는 [ThingLinks](https://github.com/mqttsnet/thinglinks)의 서브모듈입니다. 라이선스는 메인 프로젝트의 [LICENSE](../LICENSE) 및 [LICENSE-COMMERCIAL](../LICENSE-COMMERCIAL)을 따릅니다.

Copyright &copy; 2019-present [MqttsNet](https://mqttsnet.com)
