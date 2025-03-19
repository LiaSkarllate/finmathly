CREATE TABLE modality (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v7(),
    
    name 					VARCHAR(25)                 UNIQUE NOT NULL,
    yield_type				yield_type_enum             NOT NULL,
    capitalization_period	capitalization_period_enum  NOT NULL,
    supports_flows 			BOOLEAN                     NOT NULL DEFAULT FALSE,
    created_at 				TIMESTAMPTZ                 DEFAULT CURRENT_TIMESTAMP,
    updated_at 				TIMESTAMPTZ                 DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER update_modality_updated_at
BEFORE UPDATE ON modality
FOR EACH ROW EXECUTE FUNCTION set_updated_at();