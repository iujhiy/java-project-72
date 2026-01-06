DROP TABLE IF EXISTS urls_check;
DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE urls_check (
    id SERIAL PRIMARY KEY,
    status_code INT,
    title VARCHAR(255),
    h1 VARCHAR(63),
    description TEXT,
    created_at TIMESTAMP,
    url_id INT
);

ALTER TABLE urls_check
ADD CONSTRAINT fk_urls_urls_check
    FOREIGN KEY (url_id)
    REFERENCES urls(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;