CREATE TABLE asset (
    id 					UUID            PRIMARY KEY DEFAULT uuid_generate_v7(),
    name 				VARCHAR(25)     UNIQUE NOT NULL,
    modality_id			UUID            NOT NULL REFERENCES modality(id),
    maturity_date 		DATE            NOT NULL,
    interest_rate 		NUMERIC(7, 4),
    market_index_id 	UUID            REFERENCES market_index(id),
    index_percentage	NUMERIC(7, 4),
    face_value 			NUMERIC(17, 2),
    created_at 			TIMESTAMPTZ     DEFAULT CURRENT_TIMESTAMP,
    updated_at 			TIMESTAMPTZ     DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER update_asset_updated_at
BEFORE UPDATE ON asset
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE INDEX ON asset (maturity_date);