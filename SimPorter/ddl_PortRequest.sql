CREATE TABLE port_request
(
    id             BIGINT           NOT NULL,
    aadhaar        VARCHAR(255),
    status         VARCHAR(255),
    porting_charge DOUBLE PRECISION NOT NULL,
    payment_done   BOOLEAN          NOT NULL,
    transaction_id VARCHAR(255),
    CONSTRAINT pk_port_request PRIMARY KEY (id)
);