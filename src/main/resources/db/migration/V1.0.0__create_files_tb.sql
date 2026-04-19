CREATE TABLE IF NOT EXISTS files_tb (
    file_id UUID PRIMARY KEY,
    original_filename VARCHAR(100) NOT NULL,
    file_url TEXT NOT NULL,
    owner_id UUID NOT NULL,
    creator_id UUID NOT NULL,
    owner_type VARCHAR(20) NOT NULL,
    mime_type VARCHAR(50) NOT NULL,
    file_size_bytes DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);