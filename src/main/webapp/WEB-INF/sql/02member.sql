USE test;

CREATE TABLE Member (	
	id VARCHAR(30) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

ALTER TABLE Member ADD COLUMN inserted DATETIME NOT NULL DEFAULT NOW();
ALTER TABLE Member ADD COLUMN nickName VARCHAR(30); -- 컬럼추가

-- 이전 값들을 기본값으로 채워넣기. (세이프 모드를 풀고 해야함)
UPDATE Member SET nickName=id;
-- 닉네임에 제약사항 추가 unique, not null
ALTER TABLE Member MODIFY COLUMN nickName VARCHAR(30) UNIQUE NOT NULL;

SELECT * FROM Member ORDER BY inserted DESC;
SELECT * FROM Board ORDER BY inserted DESC;

DESC Member;

-- Board 테이블 작성자 열을 Member 테이블 id 값으로 수정
SET SQL_SAFE_UPDATES = 1;
UPDATE Board SET writer = (SELECT id FROM Member ORDER BY inserted DESC LIMIT 1);

-- 게시물 조회, 작성자의 nickName 포함
SELECT b.id, b.title, b.content, b.writer, b.inserted, b.updated, m.nickName
FROM Board b JOIN Member m ON b.writer = m.id
ORDER BY id DESC;

-- Member 조회 with 작성한 게시글 수
SELECT m.id, m.password, m.email, m.address, m.inserted, m.nickName, COUNT(b.id)
FROM Member m LEFT JOIN Board b ON m.id = b.writer
GROUP BY m.id
ORDER BY m.inserted DESC;










