CREATE TABLE users (
  id            SERIAL PRIMARY KEY,
  name          CHAR(255),
  surname       VARCHAR(255),
  password_hash VARCHAR(255),
  registered_at TIMESTAMP WITHOUT TIME ZONE DEFAULT (now() AT TIME ZONE 'utc')
);

CREATE TABLE event (
  id         SERIAL PRIMARY KEY,
  name       CHAR(255),
  manager_id INT CONSTRAINT fk_event_manager REFERENCES users (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);