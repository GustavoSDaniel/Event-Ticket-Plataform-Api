CREATE TABLE ticket_types (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              name VARCHAR(255) NOT NULL,
                              price DECIMAL(10,2) NOT NULL,
                              total_available INTEGER,
                              description TEXT,
                              event_id UUID,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key
                              CONSTRAINT fk_ticket_type_event FOREIGN KEY (event_id)
                                  REFERENCES events(id) ON DELETE CASCADE,

    -- Constraints de validação
                              CONSTRAINT chk_price_positive CHECK (price >= 0),
                              CONSTRAINT chk_total_available_positive CHECK (
                                  total_available IS NULL OR total_available >= 0
                                  )
);