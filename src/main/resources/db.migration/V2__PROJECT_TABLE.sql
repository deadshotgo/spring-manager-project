CREATE TABLE table_project (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               name VARCHAR(255),
                               description TEXT,
                               start_date DATE,
                               end_date DATE,
                               status VARCHAR(50),
                               password VARCHAR(255),
                               email VARCHAR(64) NOT NULL
);
