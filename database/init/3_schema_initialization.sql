\c starter starter

CREATE TABLE IF NOT EXISTS app_users (
  "id" SERIAL PRIMARY KEY,
  "login" VARCHAR(32) UNIQUE NOT NULL,
  "email" VARCHAR(128)
);