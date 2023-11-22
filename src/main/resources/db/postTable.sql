CREATE TABLE exam.posts
(
  post_id serial NOT NULL, -- 게시글 식별 id
  username character varying(50), -- 게시글을 작성한 사용자 식별 id(외래키)
  title character varying(50) NOT NULL, -- 게시글 제목
  content text, -- 게시글 내용
  view integer DEFAULT 0, -- 조회수
  create_time timestamp without time zone DEFAULT now(), -- 게시글 작성 시간
  CONSTRAINT posts_pkey PRIMARY KEY (post_id),
  CONSTRAINT posts_username_fkey FOREIGN KEY (username)
      REFERENCES exam.users (username) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);
COMMENT ON TABLE exam.posts IS '게시글 테이블';
COMMENT ON COLUMN exam.posts.post_id IS '게시글 식별 id';
COMMENT ON COLUMN exam.posts.username IS '게시글을 작성한 사용자 식별 id(외래키)';
COMMENT ON COLUMN exam.posts.title IS '게시글 제목';
COMMENT ON COLUMN exam.posts.content IS '게시글 내용';
COMMENT ON COLUMN exam.posts.view IS '조회수';
COMMENT ON COLUMN exam.posts.create_time IS '게시글 작성 시간';

