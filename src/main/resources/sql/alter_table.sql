-- 修改表结构：将 name 字段改为 user_name，用于演示 mapUnderscoreToCamelCase 配置
-- 下划线命名(user_name)会自动映射为驼峰命名(userName)

-- 1. 修改字段名
ALTER TABLE user CHANGE name user_name VARCHAR(50);

-- 2. 查看表结构确认
DESCRIBE user;
