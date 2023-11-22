CREATE TABLE exam.users
(
  username character varying(50) NOT NULL, -- 사용자 이메일
  nickname character varying(10) NOT NULL, -- 사용자 닉네임
  password character varying(15) NOT NULL, -- 사용자 비밀번호
  createtime timestamp without time zone DEFAULT now(), -- 사용자 계정 생성 시간
  CONSTRAINT users_pkey PRIMARY KEY (username)
);
COMMENT ON TABLE exam.users IS '유저 정보 테이블';
COMMENT ON COLUMN exam.users.username IS '사용자 이메일';
COMMENT ON COLUMN exam.users.nickname IS '사용자 닉네임';
COMMENT ON COLUMN exam.users.password IS '사용자 비밀번호';
COMMENT ON COLUMN exam.users.createtime IS '사용자 계정 생성 시간';

