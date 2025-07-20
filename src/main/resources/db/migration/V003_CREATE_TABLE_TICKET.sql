CREATE TABLE tickets (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         name VARCHAR(255) NOT NULL,
                         status VARCHAR(50) NOT NULL,
                         ticket_type_id UUID,
                         user_purchaser_id UUID,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Keys
                         CONSTRAINT fk_ticket_ticket_type FOREIGN KEY (ticket_type_id)
                             REFERENCES ticket_types(id) ON DELETE CASCADE,
                         CONSTRAINT fk_ticket_purchaser FOREIGN KEY (user_purchaser_id)
                             REFERENCES users(id) ON DELETE SET NULL
);