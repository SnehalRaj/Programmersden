CREATE TABLE "Questions"(
 id BIGINT PRIMARY KEY,
 title VARCHAR (50)  NOT NULL,
 content VARCHAR (1000) NOT NULL,
 solution VARCHAR (2000) NOT NULL,
 upvotes INT  NOT NULL,
 correctAnswers INT NOT NULL
);