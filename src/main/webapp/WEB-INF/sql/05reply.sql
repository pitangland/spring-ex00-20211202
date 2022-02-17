USE test;

DESC Member;

CREATE TABLE Reply (
	id INT PRIMARY KEY AUTO_INCREMENT,
	boardId INT NOT NULL,
    reply VARCHAR(500) NOT NULL,
    memberId VARCHAR(30) NOT NULL,
    inserted DATETIME NOT NULL DEFAULT NOW(),
    updated DATETIME NOT NULL DEFAULT NOW()
);

SELECT * FROM Board ORDER BY id DESC; -- 1521
SELECT * FROM Member ORDER BY inserted DESC; -- myid11

-- test 데이터 입력
INSERT INTO Reply (boardId, reply, memberId)
VALUES (1521, '테스트용 댓글', 'myid11');

SELECT * FROM Reply ORDER BY id DESC;

SELECT r. * , m.nickName FROM Reply r JOIN Member m ON r.memberId = m.id;

-- 게시물의 댓글 수 
SELECT b.id, b.title, b.content, b.writer, b.inserted, b.updated, m.nickName, count(r.id) replyCount
FROM Board b 
JOIN Member m 
ON b.writer = m.id
LEFT JOIN Reply r ON b.id = r.boardId
GROUP BY b.id
ORDER BY id DESC
LIMIT 0, 10;
















