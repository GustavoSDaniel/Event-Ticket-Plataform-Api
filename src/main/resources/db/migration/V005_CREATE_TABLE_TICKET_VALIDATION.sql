CREATE TABLE ticket_validations (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    status VARCHAR(50) NOT NULL,
                                    validation_method VARCHAR(50) NOT NULL,
                                    ticket_id UUID,
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key
                                    CONSTRAINT fk_ticket_validation_ticket FOREIGN KEY (ticket_id)
                                        REFERENCES tickets(id) ON DELETE CASCADE
);