  CREATE TABLE exam.users (
    email VARCHAR(50) PRIMARY KEY NOT NULL UNIQUE, -- 사용자 메일 주소
    nickname VARCHAR(10) NOT NULL, -- 사용자 닉네임
    password VARCHAR(15) NOT NULL, -- 사용자 비밀번호
    createTime TIMESTAMP DEFAULT NOW() -- 계정 생성 날짜
);
COMMENT ON TABLE exam.users IS '유저 정보 테이블';
COMMENT ON COLUMN exam.users.email IS '사용자 이메일';
COMMENT ON COLUMN exam.users.nickname IS '사용자 닉네임';
COMMENT ON COLUMN exam.users.password IS '사용자 비밀번호';
COMMENT ON COLUMN exam.users.createTime IS '사용자 계정 생성 시간';