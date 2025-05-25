CREATE TABLE users
(
    id      UUID PRIMARY KEY,
    name    VARCHAR(100)   NOT NULL,
    email   VARCHAR(100)   NOT NULL
);

CREATE TABLE notifications
(
    id        UUID PRIMARY KEY,
    recipient UUID,
    type      VARCHAR(255),
    category  VARCHAR(255),
    content   VARCHAR(255),
    timestamp TIMESTAMP,
    FOREIGN KEY (recipient) REFERENCES users (id)
);

CREATE TABLE notification_preferences
(
    id            UUID PRIMARY KEY,
    user_id UUID    NOT NULL,
    category      VARCHAR(50) NOT NULL,
    enabled       BOOLEAN     NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);