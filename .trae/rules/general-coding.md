---
alwaysApply: true
---
---
description: 全局通用编程规范，适用于所有代码生成任务
globs: *
---

### 🚀 核心原则
- **KISS (Keep It Simple, Stupid)**: 优先选择最简单的解决方案，避免过度设计。
- **DRY (Don't Repeat Yourself)**: 提取重复逻辑，封装成函数或组件。
- **YAGNI (You Aren't Gonna Need It)**: 不要添加当前不需要的功能或注释。

### ✍️ 代码风格
- **命名规范**:
  - 变量/函数: 使用 `camelCase` (如 `userData`, `calculateTotal`)。
  - 组件/类: 使用 `PascalCase` (如 `UserProfile`, `AuthService`)。
  - 常量: 使用 `UPPER_SNAKE_CASE` (如 `MAX_RETRY_COUNT`)。
  - **禁止**使用无意义的命名 (如 `data`, `info`, `temp`, `val`)。
- **注释规范**:
  - 只注释“为什么”这么做，而不是“做了什么”。
  - 禁止废话注释 (如 `// 初始化变量` 这种显而易见的注释)。
  - 复杂逻辑必须添加 `@example` 或 `// 解释`。
- **函数设计**:
  - 单一职责原则：一个函数只做一件事。
  - 函数长度尽量控制在 50 行以内。
  - 参数超过 3 个时，请使用对象/结构体传参。

### 🛡️ 错误处理
- 禁止忽略错误 (禁止空的 `catch` 块)。
- 使用 `try-catch` 包裹可能失败的异步操作。
- 抛出错误时，必须包含上下文信息 (如 `throw new Error('User not found: ' + userId)`).