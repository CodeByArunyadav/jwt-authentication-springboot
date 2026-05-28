DROP TABLE IF EXISTS employees;

CREATE TABLE employees (

    id SERIAL PRIMARY KEY,

    emp_data JSONB,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);