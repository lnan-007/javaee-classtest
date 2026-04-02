---
description: 项目特定上下文与架构说明
---

### 🏗️ 项目架构
- **核心目录**:
  - `src/core`: 核心业务逻辑，不依赖具体框架。
  - `src/api`: 外部 API 接口定义。
  - `src/utils`: 纯工具函数，无副作用。
- **状态管理**: 使用 Zustand (React) 或 Pinia (Vue) 管理全局状态。
- **API 请求**: 统一封装在 `src/api/client.ts`，使用 Axios 拦截器处理 Token。

### 🚫 禁止事项
- 禁止在 `utils` 目录中引入 UI 组件。
- 禁止直接 import 深层嵌套的文件，必须使用 `@/` 别名导入。
- 禁止修改 `src/types` 中的核心接口定义，除非经过确认。