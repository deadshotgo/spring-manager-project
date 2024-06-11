CREATE TABLE table_task (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255),
                            description TEXT,
                            due_date DATE,
                            status VARCHAR(50),
                            assigned_to BIGINT NOT NULL,
                            project_id BIGINT NOT NULL,
                            CONSTRAINT fk_assigned_to FOREIGN KEY (assigned_to) REFERENCES table_user(id),
                            CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES table_project(id)
);
