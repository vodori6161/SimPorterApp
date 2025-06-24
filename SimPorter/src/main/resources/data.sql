INSERT INTO users (aadhaar, phone_number, join_date, plantype, balance, postpaid_due)
VALUES ('1234', '9876543210', '2024-01-01', 'PREPAID', 0.0, 0.0);

INSERT INTO port_request (id, payment_done, status, porting_charge, aadhaar)
VALUES (1, false, 'PENDING', 100.0, '1234');

