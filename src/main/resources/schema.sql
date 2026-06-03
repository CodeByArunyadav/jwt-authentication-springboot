DROP TABLE IF EXISTS employees;
-- ============================================
-- DEPARTMENTS TABLE
-- ============================================

CREATE TABLE departments (
    dept_id SERIAL PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL,
    department_location VARCHAR(100) NOT NULL
);

-- ============================================
-- EMPLOYEES TABLE
-- ============================================

CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dept_id INT,
    emp_data JSONB NOT NULL,

    CONSTRAINT fk_employee_department
        FOREIGN KEY (dept_id)
        REFERENCES departments(dept_id)
        ON DELETE SET NULL
);

-- ============================================
-- PROJECTS TABLE
-- ============================================

CREATE TABLE projects (
    project_id SERIAL PRIMARY KEY,
    project_name VARCHAR(200) NOT NULL,
    project_description JSONB,
    start_date DATE,
    end_date DATE,
    status VARCHAR(50)
);