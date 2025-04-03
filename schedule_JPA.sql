
-- users 테이블
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_name VARCHAR(20) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- schedules 테이블
CREATE TABLE schedules (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           title VARCHAR(100) NOT NULL,
                           todo TEXT NOT NULL,
                           user_id BIGINT NOT NULL,
                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                           modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           CONSTRAINT fk_schedule_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- comments 테이블
CREATE TABLE comments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          schedule_id BIGINT NOT NULL,
                          author_name VARCHAR(30) NOT NULL,
                          comment TEXT NOT NULL,
                          password VARCHAR(255),
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT fk_comment_schedule FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
);
