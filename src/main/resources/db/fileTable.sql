CREATE TABLE exam.files
(
  file_id serial NOT NULL, -- 파일 식별 id
  post_id integer NOT NULL, -- 관련 게시물 id
  file_name character varying(255) NOT NULL, -- 서버에 저장된 파일 이름
  file_path text NOT NULL, -- 파일 저장 경로
  CONSTRAINT files_pkey PRIMARY KEY (file_id),
  CONSTRAINT files_post_id_fkey FOREIGN KEY (post_id)
      REFERENCES exam.posts (post_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);
COMMENT ON TABLE exam.files IS '파일 업로드 테이블';
COMMENT ON COLUMN exam.files.file_id IS '파일 식별 id';
COMMENT ON COLUMN exam.files.post_id IS '관련 게시물 id';
COMMENT ON COLUMN exam.files.file_name IS '서버에 저장된 파일 이름';
COMMENT ON COLUMN exam.files.file_path IS '파일 저장 경로';

