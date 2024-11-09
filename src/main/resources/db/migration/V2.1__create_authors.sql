CREATE TABLE authors
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    open_library_key VARCHAR(20)  NOT NULL,
    name             VARCHAR(200) NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_authors_id PRIMARY KEY (id),
    CONSTRAINT UK_authors_open_library_key UNIQUE (open_library_key)
);