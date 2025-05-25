CREATE TABLE cardholders
(
    id      CHAR(36) PRIMARY KEY,
    name    VARCHAR(100)   NOT NULL,
    email   VARCHAR(100)   NOT NULL,
    balance DECIMAL(10, 2) NOT NULL
);

CREATE TABLE notifications
(
    id        UUID PRIMARY KEY,
    recipient UUID,
    type      VARCHAR(255),
    category  VARCHAR(255),
    content   VARCHAR(255),
    timestamp TIMESTAMP,
    FOREIGN KEY (recipient) REFERENCES cardholders (id)
);

CREATE TABLE notification_preferences
(
    id            CHAR(36) PRIMARY KEY,
    cardholder_id CHAR(36)    NOT NULL,
    category      VARCHAR(50) NOT NULL,
    enabled       BOOLEAN     NOT NULL,
    FOREIGN KEY (cardholder_id) REFERENCES cardholders (id)
);