CREATE TABLE exam.comments (
    comment_id SERIAL PRIMARY KEY NOT NULL, -- 댓글 식별
    email VARCHAR(50), -- user table의 PK를 FK로 받음
    post_id INT, -- post table의 PK를 FK로 받음
    comment TEXT NOT NULL, -- 댓글 내용
    create_time TIMESTAMP DEFAULT NOW(), -- 댓글 작성 날짜
    FOREIGN KEY (email) REFERENCES exam.users(email) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES exam.posts(post_id)  MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE
);
COMMENT ON TABLE exam.comments IS '댓글 테이블';
COMMENT ON COLUMN exam.comments.comment_id IS '댓글 식별 id';
COMMENT ON COLUMN exam.comments.email IS '댓글 작성자 식별 id(외래키)';
COMMENT ON COLUMN exam.comments.post_id IS '댓글이 달린 게시글 식별 id(외래키)';
COMMENT ON COLUMN exam.comments.comment IS '댓글 내용';
COMMENT ON COLUMN exam.comments.create_time IS '댓글 작성 시간';