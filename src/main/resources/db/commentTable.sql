CREATE TABLE exam.comments
(
  comment_id serial NOT NULL, -- 댓글 식별 id
  username character varying(50), -- 댓글 작성자 식별 id(외래키)
  post_id integer, -- 댓글이 달린 게시글 식별 id(외래키)
  comment text NOT NULL, -- 댓글 내용
  create_time timestamp without time zone DEFAULT now(), -- 댓글 작성 시간
  CONSTRAINT comments_pkey PRIMARY KEY (comment_id),
  CONSTRAINT comments_post_id_fkey FOREIGN KEY (post_id)
      REFERENCES exam.posts (post_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT comments_username_fkey FOREIGN KEY (username)
      REFERENCES exam.users (username) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);
COMMENT ON TABLE exam.comments IS '댓글 테이블';
COMMENT ON COLUMN exam.comments.comment_id IS '댓글 식별 id';
COMMENT ON COLUMN exam.comments.username IS '댓글 작성자 식별 id(외래키)';
COMMENT ON COLUMN exam.comments.post_id IS '댓글이 달린 게시글 식별 id(외래키)';
COMMENT ON COLUMN exam.comments.comment IS '댓글 내용';
COMMENT ON COLUMN exam.comments.create_time IS '댓글 작성 시간';

