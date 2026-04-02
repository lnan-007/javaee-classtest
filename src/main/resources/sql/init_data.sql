-- 删除旧表并创建新表（使用下划线命名 user_name）
DROP TABLE IF EXISTS user;

-- 创建新表：使用下划线命名风格
CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,  -- 下划线命名，对应实体类 userName（驼峰命名）
    age INT
);

-- Insert diverse test data (simulating a user system)
INSERT INTO user (user_name, age) VALUES
-- Regular users
('Zhang San', 25),
('Li Si', 28),
('Wang Wu', 22),
('Zhao Liu', 30),
('Sun Qi', 26),
-- Technical staff
('Zhou Ba', 35),
('Wu Jiu', 29),
('Zheng Shi', 24),
-- Management
('Liu Bei', 40),
('Cao Cao', 42),
('Sun Quan', 38),
-- Young users
('Xiao Ming', 18),
('Xiao Hong', 19),
('Xiao Gang', 20),
-- Special test data
('Zhang Wei', 27),
('Li Ming', 31),
('Wang Fang', 25);
