CREATE TABLE qr_codes (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          status VARCHAR(50) NOT NULL,
                          value VARCHAR(255) NOT NULL UNIQUE,
                          ticket_id UUID,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key
                          CONSTRAINT fk_qr_code_ticket FOREIGN KEY (ticket_id)
                              REFERENCES tickets(id) ON DELETE CASCADE
);