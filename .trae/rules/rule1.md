---
description: SQL 数据库开发与优化规范
globs: *.sql
---

### 🚀 查询性能
- **禁止 SELECT ***: 必须明确列出需要的列名，以减少网络传输和内存消耗。
- **索引优化**: 在 `WHERE`, `JOIN`, `ORDER BY` 子句中涉及的列，必须检查是否有索引覆盖。
- **避免 N+1 问题**: 在循环中禁止执行 SQL 查询，必须使用 `JOIN` 或批量查询。

### 🛡️ 安全与规范
- **参数化查询**: 严禁拼接 SQL 字符串。必须使用占位符 (如 `?` 或 `:param`) 防止 SQL 注入。
- **事务管理**: 涉及多表更新（INSERT/UPDATE/DELETE）的操作，必须包裹在 `BEGIN; ... COMMIT;` 事务中。
- **命名规范**:
  - 表名: 小写复数，下划线分隔 (如 `users`, `order_items`)。
  - 字段名: 小写，下划线分隔 (如 `created_at`, `is_active`)。
  - 主键: 统一命名为 `id`。
  - 外键: 格式为 `table_name_id` (如 `user_id`)。

### 📝 示例
-- ❌ 错误：全表扫描，潜在注入风险
SELECT * FROM users WHERE name = ' + name;

-- ✅ 正确：指定列，使用参数
SELECT id, name, email FROM users WHERE name = $1;