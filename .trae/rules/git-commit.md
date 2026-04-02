---
description: Git 提交信息规范
---

请遵循 Conventional Commits 规范生成提交信息：

### 格式
<type>(<scope>): <subject>

### Type 类型
- `feat`: 新功能
- `fix`: 修补 bug
- `docs`: 文档变更
- `style`: 代码格式（不影响代码运行）
- `refactor`: 重构（既不是新功能也不是修 bug）
- `test`: 增加测试
- `chore`: 构建过程或辅助工具变动

### 示例
- `feat(auth): 添加用户 JWT 登录功能`
- `fix(api): 修复空指针异常导致的服务崩溃`
- `docs(readme): 更新安装说明`