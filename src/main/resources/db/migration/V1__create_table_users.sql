CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    street VARCHAR(255),
    suite VARCHAR(255),
    city VARCHAR(255),
    zipcode VARCHAR(255),
    lat DECIMAL(10, 7),
    lng DECIMAL(10, 7),
    phone VARCHAR(255),
    website VARCHAR(255),
    company_name VARCHAR(255),
    catch_phrase VARCHAR(255),
    bs VARCHAR(255)
);
