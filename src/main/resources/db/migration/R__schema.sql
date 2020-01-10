CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS customer;

CREATE TABLE customer
(
    id          uuid        NOT NULL PRIMARY KEY DEFAULT uuid_generate_v1(),
    name        text        NOT NULL,
    email       text,
    address     text,
    create_time timestamptz NOT NULL             DEFAULT now(),
    last_update timestamptz NOT NULL             DEFAULT now()
);

INSERT INTO customer (id, name, email, address)
VALUES ('00061ca9-bc15-417d-9d4e-0608cd7609d0', 'Jeff', 'jeff.fang@airwallex.com', null),
       ('003d1a24-b767-4e5d-848c-9c5630542717', 'John', null, 'shanghai');

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    id          UUID      NOT NULL PRIMARY KEY,
    create_time TIMESTAMP NOT NULL DEFAULT now(),
    last_update TIMESTAMP NOT NULL DEFAULT now(),
    data        JSONB     NOT NULL,
    version     INTEGER   NOT NULL DEFAULT 0
);
-- Triggers omitted

INSERT INTO orders (id, data)
VALUES ('00061ca9-bc15-417d-9d4e-0608cd7609d0', '{
  "customerId": "003d1a24-b767-4e5d-848c-9c5630542717",
  "amount": 100,
  "ccy": "AUD",
  "createTime": "2020-01-03T11:33:51.543+0000",
  "id": "5b0bb553-2048-43fa-8350-b0b3af5da4b8",
  "lastUpdate": "2020-01-03T11:33:51.543+0000",
  "version": 0
}'),
       ('003d1a24-b767-4e5d-848c-9c5630542717', '{
         "customerId": "003d1a24-b767-4e5d-848c-9c5630542717",
         "amount": 50,
         "ccy": "USD",
         "createTime": "2020-01-03T11:34:28.626+0000",
         "id": "22588b9b-ef7e-4fa6-9813-b09b9da107ef",
         "lastUpdate": "2020-01-03T11:34:28.626+0000",
         "version": 0
       }');
