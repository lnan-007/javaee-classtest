---
description: Python 后端开发规范
globs: *.py
---

### 🐍 代码规范
- **类型提示**: 所有函数参数和返回值必须添加 Type Hinting (如 `def get_user(id: int) -> User:`)。
- **文档字符串**: 公共函数必须包含 Google Style Docstring。
- **异步编程**: IO 密集型操作必须使用 `async/await`。

### 🗄️ 数据库与 ORM
- **ORM**: 优先使用 SQLAlchemy 或 Django ORM，禁止编写原始 SQL，除非性能极其敏感。
- **数据验证**: 使用 Pydantic 进行数据校验。
- **事务**: 涉及多表更新的操作，必须使用事务 (`with session.begin():`)。

### 🧪 测试
- 使用 `pytest` 框架。
- 测试文件名必须以 `test_` 开头。
- 每个测试用例只测试一个逻辑路径。