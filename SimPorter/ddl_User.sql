CREATE TABLE users
(
    aadhaar      VARCHAR(255)     NOT NULL,
    phone_number VARCHAR(255),
    join_date    date,
    plantype     VARCHAR(255),
    balance      DOUBLE PRECISION NOT NULL,
    postpaid_due DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (aadhaar)
);