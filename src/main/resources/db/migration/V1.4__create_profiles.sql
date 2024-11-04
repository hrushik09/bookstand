CREATE TABLE profiles
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    user_id    BIGINT       NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    country    ENUM ('INDIA','UNITED_STATES','UNITED_KINGDOM','FRANCE','GERMANY','ITALY','SOUTH_AFRICA','BRAZIL'),
    email      VARCHAR(100) NULL,
    bio        VARCHAR(500) NULL,
    CONSTRAINT PK_profiles PRIMARY KEY (id),
    CONSTRAINT UK_profiles_user_id UNIQUE (user_id),
    CONSTRAINT FK_profiles_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UK_profiles_email UNIQUE (email)
);