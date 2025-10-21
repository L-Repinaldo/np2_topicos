-- Tabela de livros
CREATE TABLE IF NOT EXISTS books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    authors VARCHAR(1000),
    published_year INTEGER,
    info_link VARCHAR(500),
    thumbnail VARCHAR(500),
    favorite BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índice para busca por título
CREATE INDEX idx_books_title ON books(title);

-- Índice para busca por favoritos
CREATE INDEX idx_books_favorite ON books(favorite);

