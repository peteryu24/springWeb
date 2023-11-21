CREATE TABLE exam.posts (
    post_id SERIAL PRIMARY KEY NOT NULL, -- 게시글 식별
    email VARCHAR(50), -- user table의 PK를 FK로 받음
    title VARCHAR(50) NOT NULL, -- 제목
    content TEXT, -- 내용
    view INT DEFAULT 0, -- 조회수
    create_time TIMESTAMP DEFAULT NOW(), -- 게시글 작성 날짜
    FOREIGN KEY (email) REFERENCES exam.users(email) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE
);
COMMENT ON TABLE exam.posts IS '게시글 테이블';
COMMENT ON COLUMN exam.posts.post_id IS '게시글 식별 id';
COMMENT ON COLUMN exam.posts.email IS '게시글을 작성한 사용자 식별 id(외래키)';
COMMENT ON COLUMN exam.posts.title IS '게시글 제목';
COMMENT ON COLUMN exam.posts.content IS '게시글 내용';
COMMENT ON COLUMN exam.posts.view IS '조회수';
COMMENT ON COLUMN exam.posts.create_time IS '게시글 작성 시간';