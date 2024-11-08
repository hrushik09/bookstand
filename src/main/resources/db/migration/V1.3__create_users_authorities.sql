CREATE TABLE users_authorities
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    user_id      BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    CONSTRAINT PK_users_authorities PRIMARY KEY (id),
    CONSTRAINT FK_users_authorities_user_id FOREIGN KEY (user_id) references users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_users_authorities_authority_id FOREIGN KEY (authority_id) references authorities (id) ON DELETE CASCADE ON UPDATE CASCADE
);