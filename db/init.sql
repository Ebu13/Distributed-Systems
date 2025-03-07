CREATE TABLE IF NOT EXISTS note (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL
);

INSERT INTO note (title, content) VALUES ('Docker Öğreniyorum', 'Docker Compose ile sistem kuruyorum.');
