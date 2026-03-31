-- Minimal schema to support forms, admin auth, and media metadata.
-- Extend later for products/news/cms pages.

CREATE TABLE IF NOT EXISTS admin_user (
  id SERIAL PRIMARY KEY,
  username VARCHAR(64) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS form_submission (
  id SERIAL PRIMARY KEY,
  type VARCHAR(32) NOT NULL,
  name VARCHAR(128) NOT NULL,
  email VARCHAR(256) NOT NULL,
  phone VARCHAR(64) NULL,
  message TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS media_object (
  id SERIAL PRIMARY KEY,
  oss_key VARCHAR(256) NOT NULL,
  url VARCHAR(512) NOT NULL,
  mime_type VARCHAR(128) NOT NULL,
  size BIGINT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

