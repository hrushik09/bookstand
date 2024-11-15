CREATE TABLE work_review_likes
(
    id             BIGINT    NOT NULL AUTO_INCREMENT,
    user_id        BIGINT    NOT NULL,
    work_review_id BIGINT    NOT NULL,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PK_work_review_likes PRIMARY KEY (id),
    CONSTRAINT FK_work_review_likes_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_work_review_likes_work_review_id FOREIGN KEY (work_review_id) REFERENCES work_reviews (id) ON DELETE CASCADE ON UPDATE CASCADE
);