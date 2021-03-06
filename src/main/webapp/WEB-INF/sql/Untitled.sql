USE test;

CREATE TABLE Board(
	id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content VARCHAR(2000) NOT NULL,
	writer VARCHAR(50) NOT NULL,
    inserted DATETIME DEFAULT NOW(),
    updated DATETIME DEFAULT NOW()
);

SELECT * FROM Board ORDER BY id DESC;

DESC Board;

SET SQL_SAFE_UPDATES = 1;

DELETE FROM Board WHERE updated IS NULL;

ALTER TABLE Board MODIFY COLUMN inserted DATETIME NOT NULL DEFAULT NOW();
ALTER TABLE Board MODIFY COLUMN updated DATETIME NOT NULL DEFAULT NOW();