---
description: React 前端开发规范
globs: *.tsx, *.ts, *.css, *.scss
---

### 🧩 组件规范
- **函数组件**: 必须使用箭头函数定义 (`const Component = () => {}`)。
- **类型定义**:
  - 必须使用 TypeScript。
  - Props 必须定义明确的 Interface (如 `interface UserProps { name: string }`)。
  - 禁止使用 `any`，如果不确定类型，使用 `unknown` 并进行类型守卫。
- **Hooks 使用**:
  - 自定义 Hooks 必须以 `use` 开头 (如 `useAuth`, `useWindowSize`)。
  - `useEffect` 依赖数组必须完整，禁止遗漏依赖项。

### 🎨 样式与 UI
- **Tailwind CSS**: 优先使用 Tailwind 类名，避免内联样式。
- **类名组织**: 使用 `clsx` 或 `classnames` 库处理动态类名。
- **响应式**: 必须遵循 "Mobile First" (移动端优先) 原则。

### 📁 目录结构
- 组件文件与其样式/测试文件同级存放 (Feature-based 结构)。
- 示例:
  - `components/Button/index.tsx`
  - `components/Button/Button.test.tsx`
  - `components/Button/Button.styles.css`