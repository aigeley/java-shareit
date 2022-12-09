CREATE SEQUENCE IF NOT EXISTS user_seq;
CREATE TABLE IF NOT EXISTS users
(
    user_id   BIGINT DEFAULT NEXTVAL('user_seq') NOT NULL,
    user_name VARCHAR(255)                       NOT NULL,
    email     VARCHAR(512)                       NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (user_id),
    CONSTRAINT user_email_uq UNIQUE (email)
);
CREATE SEQUENCE IF NOT EXISTS request_seq;
CREATE TABLE IF NOT EXISTS requests
(
    request_id   BIGINT DEFAULT NEXTVAL('request_seq') NOT NULL,
    description  VARCHAR(512)                          NOT NULL,
    requestor_id BIGINT                                NOT NULL,
    created      TIMESTAMP                             NOT NULL,
    CONSTRAINT request_pk PRIMARY KEY (request_id),
    CONSTRAINT request_requestor_fk FOREIGN KEY (requestor_id) REFERENCES users (user_id) ON DELETE CASCADE
);
CREATE SEQUENCE IF NOT EXISTS item_seq;
CREATE TABLE IF NOT EXISTS items
(
    item_id      BIGINT DEFAULT NEXTVAL('item_seq') NOT NULL,
    item_name    VARCHAR(255)                       NOT NULL,
    description  VARCHAR(512)                       NOT NULL,
    is_available BOOLEAN                            NOT NULL,
    owner_id     BIGINT                             NOT NULL,
    request_id   BIGINT,
    CONSTRAINT item_pk PRIMARY KEY (item_id),
    CONSTRAINT item_owner_fk FOREIGN KEY (owner_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT item_request_fk FOREIGN KEY (request_id) REFERENCES requests (request_id) ON DELETE RESTRICT
);
CREATE SEQUENCE IF NOT EXISTS booking_seq;
CREATE TABLE IF NOT EXISTS bookings
(
    booking_id BIGINT DEFAULT NEXTVAL('booking_seq') NOT NULL,
    start_date TIMESTAMP                             NOT NULL,
    end_date   TIMESTAMP                             NOT NULL,
    item_id    BIGINT                                NOT NULL,
    booker_id  BIGINT                                NOT NULL,
    status     VARCHAR(255)                          NOT NULL,
    CONSTRAINT booking_pk PRIMARY KEY (booking_id),
    CONSTRAINT booking_item_fk FOREIGN KEY (item_id) REFERENCES items (item_id) ON DELETE CASCADE,
    CONSTRAINT booking_booker_fk FOREIGN KEY (booker_id) REFERENCES users (user_id) ON DELETE CASCADE
);
CREATE SEQUENCE IF NOT EXISTS comment_seq;
CREATE TABLE IF NOT EXISTS comments
(
    comment_id BIGINT    DEFAULT NEXTVAL('comment_seq') NOT NULL,
    text       VARCHAR(512)                             NOT NULL,
    item_id    BIGINT                                   NOT NULL,
    author_id  BIGINT                                   NOT NULL,
    created    TIMESTAMP DEFAULT CURRENT_TIMESTAMP      NOT NULL,
    CONSTRAINT comment_pk PRIMARY KEY (comment_id),
    CONSTRAINT comment_item_fk FOREIGN KEY (item_id) REFERENCES items (item_id) ON DELETE CASCADE,
    CONSTRAINT comment_author_fk FOREIGN KEY (author_id) REFERENCES users (user_id) ON DELETE CASCADE
);