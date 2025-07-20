CREATE TABLE user_staffing_events (
                                      user_id UUID NOT NULL,
                                      event_id UUID NOT NULL,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                      PRIMARY KEY (user_id, event_id),

                                      CONSTRAINT fk_staffing_user FOREIGN KEY (user_id)
                                          REFERENCES users(id) ON DELETE CASCADE,
                                      CONSTRAINT fk_staffing_event FOREIGN KEY (event_id)
                                          REFERENCES events(id) ON DELETE CASCADE
);