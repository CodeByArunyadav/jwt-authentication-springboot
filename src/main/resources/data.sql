INSERT INTO employees (emp_data)
VALUES (
'{
    "name":"Rahul Sharma",
    "age":28,
    "department":"Cyber Security",
    "salary":85000
}'
);
INSERT INTO employees (emp_data)
VALUES (
'{
    "name":"Rahul Sharma",
    "age":28,
    "department":"Cyber Security",
    "salary":85000,
    "email":"rahul@company.com",
    "skills":["Linux","Python","SIEM"],
    "address":{
        "city":"Delhi",
        "country":"India"
    }
}'
);

INSERT INTO employees (emp_data)
VALUES (
'{
    "name":"Priya Verma",
    "age":32,
    "department":"Cloud",
    "salary":120000,
    "email":"priya@company.com",
    "skills":["AWS","Terraform","Docker"],
    "address":{
        "city":"Mumbai",
        "country":"India"
    }
}'
);

INSERT INTO employees (emp_data)
VALUES (
'{
    "name":"Amit Kumar",
    "age":30,
    "department":"DevOps",
    "salary":95000,
    "email":"amit@company.com",
    "skills":["Kubernetes","Jenkins","Docker"],
    "address":{
        "city":"Bangalore",
        "country":"India"
    }
}'
);


INSERT INTO departments (dept_name, department_location) VALUES 
('Engineering', 'Austin'),
('Data Analytics', 'New York'),
('Information Security', 'Remote');


INSERT INTO projects (
    project_name,
    project_description,
    start_date,
    end_date,
    status
)
VALUES (
    'Employee Management System',
    '{
        "technology":"Spring Boot",
        "database":"PostgreSQL",
        "version":"1.0",
        "modules":["Employee","Department","Project"]
    }',
    '2026-01-01',
    '2026-06-30',
    'IN_PROGRESS'
);

INSERT INTO projects (
    project_name,
    project_description,
    start_date,
    end_date,
    status
)
VALUES (
    'Banking Application',
    '{
        "technology":"Java",
        "database":"PostgreSQL",
        "version":"2.1",
        "modules":["Accounts","Loans","Transactions","Reports"]
    }',
    '2026-02-01',
    '2026-12-31',
    'IN_PROGRESS'
);

INSERT INTO projects (
    project_name,
    project_description,
    start_date,
    end_date,
    status
)
VALUES (
    'E-Commerce Platform',
    '{
        "technology":"Spring Boot",
        "database":"MySQL",
        "version":"3.0",
        "modules":["Catalog","Cart","Orders","Payments","Users"]
    }',
    '2026-03-15',
    '2026-11-30',
    'PLANNED'
);

