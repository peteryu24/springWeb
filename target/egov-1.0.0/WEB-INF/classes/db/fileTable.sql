CREATE TABLE exam.files (
    file_id SERIAL PRIMARY KEY NOT NULL,
    post_id INT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_path TEXT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES exam.posts(post_id) ON UPDATE CASCADE ON DELETE CASCADE
);

COMMENT ON TABLE exam.files IS '파일 업로드 테이블';
COMMENT ON COLUMN exam.files.file_id IS '파일 식별 id';
COMMENT ON COLUMN exam.files.post_id IS '관련 게시물 id';
COMMENT ON COLUMN exam.files.file_name IS '서버에 저장된 파일 이름';
COMMENT ON COLUMN exam.files.file_path IS '파일 저장 경로';
