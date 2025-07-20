CREATE TABLE events (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        name VARCHAR(255) NOT NULL,
                        event_start TIMESTAMP,
                        event_end TIMESTAMP,
                        venue VARCHAR(255) NOT NULL,
                        sales_start TIMESTAMP,
                        sales_end TIMESTAMP,
                        status VARCHAR(50) NOT NULL,
                        organizer_id UUID,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key
                        CONSTRAINT fk_event_organizer FOREIGN KEY (organizer_id)
                            REFERENCES users(id) ON DELETE SET NULL,

    -- Constraints de validação
                        CONSTRAINT chk_event_dates CHECK (
                            event_end IS NULL OR event_start IS NULL OR event_end >= event_start
                            ),
                        CONSTRAINT chk_sales_dates CHECK (
                            sales_end IS NULL OR sales_start IS NULL OR sales_end >= sales_start
                            )
);