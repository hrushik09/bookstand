CREATE TABLE works
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    open_library_key VARCHAR(20)  NOT NULL,
    title            VARCHAR(200) NOT NULL,
    subtitle         VARCHAR(200) NULL,
    cover_id         VARCHAR(20)  NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_works PRIMARY KEY (id),
    CONSTRAINT UK_works_open_library_key UNIQUE (open_library_key)
);