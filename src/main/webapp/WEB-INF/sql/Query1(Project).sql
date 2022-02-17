USE test;
DESC Board;
SELECT COUNT(*) FROM Board;

INSERT INTO Board (title, content, writer)
(SELECT title, content, writer FROM Board);	-- 이미 있는 레코드를 복사해서 다시 입력

-- 모든 리스트
SELECT
	b.id,
    b.title,
    b.content,
    b.writer,
    b.inserted,
    b.updated,
    m.nickName
FROM
	Board b
		JOIN
	Member m ON b.writer = m.id
ORDER BY id DESC
LIMIT 0, 10;

-- from(0-index), number
-- page 1 : 0, 10
-- page2 : 10, 10
-- page3 : 20, 10
-- page4 : 30, 10
-- page5 : 40, 10




