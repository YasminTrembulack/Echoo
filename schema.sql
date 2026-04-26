-- Habilita o gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Create Database
CREATE DATABASE echoo;

-- =========================
-- Main Tables
-- =========================

CREATE TABLE locations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    establishment VARCHAR(150),
    street VARCHAR(150),
    number VARCHAR(20),
    neighborhood VARCHAR(100),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    postal_code VARCHAR(20),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    cpf CHAR(14) UNIQUE NOT NULL,
    birth_date DATE,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    capacity INT,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    status VARCHAR(20),

    location_id UUID,
    organizer_id UUID,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (location_id) REFERENCES locations(id),
    FOREIGN KEY (organizer_id) REFERENCES users(id)
);

-- =========================
-- Structural Tables
-- =========================

CREATE TABLE themes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(30),
    description VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE event_themes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id UUID,
    theme_id UUID,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (theme_id) REFERENCES themes(id)
);

CREATE TABLE tickets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    batch INT,
    price DECIMAL(10,2),
    quantity INT,
    type VARCHAR(50),
    sale_start TIMESTAMP,
    sale_end TIMESTAMP,
    event_id UUID,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE TABLE participations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role VARCHAR(50),
    user_id UUID,
    event_id UUID,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT unique_participation UNIQUE (user_id, event_id),

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);