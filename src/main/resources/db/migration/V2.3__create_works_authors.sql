CREATE TABLE works_authors
(
    id        BIGINT NOT NULL AUTO_INCREMENT,
    work_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    CONSTRAINT PK_works_authors PRIMARY KEY (id),
    CONSTRAINT FK_works_authors_work_id FOREIGN KEY (work_id) REFERENCES works (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_works_authors_author_id FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE ON UPDATE CASCADE
);