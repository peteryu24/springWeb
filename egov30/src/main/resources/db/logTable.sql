CREATE TABLE exam.logs (
    log_id SERIAL PRIMARY KEY NOT NULL, 
    email VARCHAR(50), 
    category VARCHAR(50) NOT NULL,
    post_title TEXT, 
    create_time TIMESTAMP DEFAULT NOW(), 
    FOREIGN KEY (email) REFERENCES exam.users(email) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE
);
COMMENT ON TABLE exam.logs IS '로그 테이블';
COMMENT ON COLUMN exam.logs.log_id IS '로그 id';
COMMENT ON COLUMN exam.logs.email IS '로그 생성자 식별 id(외래키)';
COMMENT ON COLUMN exam.logs.category IS '로그의 종류';
COMMENT ON COLUMN exam.logs.post_title IS '게시글 작성 로그일 경우 게시글의 제목';
COMMENT ON COLUMN exam.logs.create_time IS '로그 생성 시간';