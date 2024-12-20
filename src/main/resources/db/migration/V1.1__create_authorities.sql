CREATE TABLE authorities
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    value      VARCHAR(200) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PK_authorities PRIMARY KEY (id),
    CONSTRAINT UK_authorities_value UNIQUE (value)
);