CREATE TABLE market_index (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v7(),
    
    name 			VARCHAR(25) UNIQUE NOT NULL,
    description 	TEXT,
    created_at 		TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at 		TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER update_market_index_updated_at
BEFORE UPDATE ON market_index
FOR EACH ROW EXECUTE FUNCTION set_updated_at();