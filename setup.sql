CREATE DATABASE expense_dev;
CREATE DATABASE expense_prod;

CREATE USER 'dev_expense'@'localhost' IDENTIFIED BY 'dev_expense';
GRANT ALL PRIVILEGES ON expense_dev.* TO 'dev_expense'@'localhost';

CREATE USER 'prod_expense'@'localhost' IDENTIFIED BY 'prod_expense';
GRANT ALL PRIVILEGES ON expense_prod.* TO 'prod_expense'@'localhost';

use expense_dev;

show tables;

CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);

select * from users;

drop table users;