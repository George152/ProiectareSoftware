USE assignment;
drop table users;
drop table tags;
drop table questions;
drop table questions_tags;
drop table answers;
drop table votes;
drop table bans;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('user', 'moderator')),
    score FLOAT DEFAULT 0,
    password TEXT NOT NULL,
    picture TEXT,
    phoneNumber VARCHAR(20),
    isBanned BOOLEAN DEFAULT FALSE
);

CREATE TABLE tags (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    authorId INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    picture VARCHAR(255),
    status VARCHAR(50) NOT NULL CHECK (status IN ('received', 'in progress', 'solved')),
    FOREIGN KEY (authorId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE questions_tags (
    questionId INT NOT NULL,
    tagId INT NOT NULL,
    PRIMARY KEY (questionId, tagId),
    FOREIGN KEY (questionId) REFERENCES questions(id) ON DELETE CASCADE,
    FOREIGN KEY (tagId) REFERENCES tags(id) ON DELETE CASCADE
);

CREATE TABLE answers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    questionId INT NOT NULL,
    authorId INT NOT NULL,
    content VARCHAR(255) NOT NULL,
    picture VARCHAR(255),
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (questionId) REFERENCES questions(id) ON DELETE CASCADE,
    FOREIGN KEY (authorId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE votes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    questionId INT NULL,
    answerId INT NULL,
    voteType INT NOT NULL CHECK (voteType IN (1, -1)),
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (questionId) REFERENCES questions(id) ON DELETE CASCADE,
    FOREIGN KEY (answerId) REFERENCES answers(id) ON DELETE CASCADE,
    CHECK ((questionId IS NOT NULL AND answerId IS NULL) OR (questionId IS NULL AND answerId IS NOT NULL))
);

CREATE TABLE bans (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT UNIQUE NOT NULL,
    moderatorId INT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    bannedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (moderatorId) REFERENCES users(id) ON DELETE CASCADE
);