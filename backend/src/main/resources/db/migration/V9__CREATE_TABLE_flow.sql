CREATE TABLE flow (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v7(),
    
    asset_id				UUID            NOT NULL REFERENCES asset(id),
    type 					event_type_enum NOT NULL,
    event_date 				DATE            NOT NULL, 
    amount					NUMERIC(17, 2),
    amortization_percentage	NUMERIC(7, 4),
    created_at 				TIMESTAMPTZ     DEFAULT CURRENT_TIMESTAMP,
    updated_at 				TIMESTAMPTZ     DEFAULT CURRENT_TIMESTAMP
);

CREATE TRIGGER update_flow_updated_at
BEFORE UPDATE ON flow
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

CREATE INDEX ON flow (event_date);