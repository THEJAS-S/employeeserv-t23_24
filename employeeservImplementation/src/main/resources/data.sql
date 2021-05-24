DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS employees;
CREATE TABLE address (
                         id INT AUTO_INCREMENT  PRIMARY KEY,
                         line1 VARCHAR(255) NOT NULL,
                         line2 VARCHAR(255),
                         city VARCHAR(255) NOT NULL,
                         state VARCHAR(255) NOT NULL,
                         country VARCHAR(255) NOT NULL,
                         zipcode VARCHAR(255) NOT NULL
);

CREATE TABLE employees (
                              id INT AUTO_INCREMENT  PRIMARY KEY,
                              first_name VARCHAR(255) NOT NULL,
                              last_name VARCHAR(255) NOT NULL,
                              date_of_birth DATE NOT NULL,
                              address_id INT,
                              FOREIGN KEY (address_id) REFERENCES address(id)

);