-- Create Database
CREATE DATABASE echoo;
USE echoo;

-- =========================
-- Main Tables
-- =========================

CREATE TABLE locations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    establishment VARCHAR(100),
    street VARCHAR(100),
    number VARCHAR(6),
    neighborhood VARCHAR(50),
    city VARCHAR(50),
    zip_code VARCHAR(9),
    state VARCHAR(2),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(100),
    cpf CHAR(11) UNIQUE NOT NULL,
    birth_date DATE,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    description VARCHAR(255),
    capacity INT,
    start_date DATETIME,
    end_date DATETIME,
    status ENUM('active', 'canceled', 'finished'),
    location_id INT,
    organizer_id INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (location_id) REFERENCES locations(id),
    FOREIGN KEY (organizer_id) REFERENCES users(id)
);

-- =========================
-- Structural Tables
-- =========================

CREATE TABLE themes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30),
    description VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE event_themes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    theme_id INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (theme_id) REFERENCES themes(id)
);

CREATE TABLE tickets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    batch INT,
    price DECIMAL(10,2),
    quantity INT,
    type VARCHAR(50),
    sale_start DATETIME,
    sale_end DATETIME,
    event_id INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE TABLE participations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role VARCHAR(50),
    user_id INT,
    event_id INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT unique_participation UNIQUE (user_id, event_id),

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);
