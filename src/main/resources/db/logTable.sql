CREATE TABLE exam.logs
(
  log_id serial NOT NULL, -- 로그 id
  username character varying(50), -- 로그 생성자 식별 id(외래키)
  category smallint NOT NULL, -- 로그의 종류
  activity text, -- 활동 내용
  create_time timestamp without time zone DEFAULT now(), -- 로그 생성 시간
  CONSTRAINT logs_pkey PRIMARY KEY (log_id),
  CONSTRAINT logs_username_fkey FOREIGN KEY (username)
      REFERENCES exam.users (username) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);
COMMENT ON TABLE exam.logs IS '로그 테이블';
COMMENT ON COLUMN exam.logs.log_id IS '로그 id';
COMMENT ON COLUMN exam.logs.username IS '로그 생성자 식별 id(외래키)';
COMMENT ON COLUMN exam.logs.category IS '로그의 종류';
COMMENT ON COLUMN exam.logs.activity IS '활동 내용';
COMMENT ON COLUMN exam.logs.create_time IS '로그 생성 시간';

