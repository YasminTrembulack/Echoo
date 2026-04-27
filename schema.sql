-- database: :memory:
-- Habilita o gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Create Database
CREATE DATABASE echoo;


-- =========================
-- Enum Types
-- =========================

CREATE TYPE user_role_enum AS ENUM ('ADMIN', 'CLIENT');
CREATE TYPE event_status_enum AS ENUM ('ACTIVE', 'CANCELED', 'FINISHED');
CREATE TYPE order_status_enum AS ENUM ('PENDING', 'COMPLETED', 'CANCELED');
CREATE TYPE payment_method_enum AS ENUM ('CREDIT_CARD', 'DEBIT_CARD', 'PAYPAL', 'PIX');


-- =========================
-- Tables
-- =========================

CREATE TABLE locations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    establishment VARCHAR(150),
    street VARCHAR(150) NOT NULL,
    number VARCHAR(20),
    complement VARCHAR(100),
    neighborhood VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,

    CONSTRAINT unique_location UNIQUE (number, postal_code, complement),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- CPF/Email: O back-end deve validar se o CPF é matematicamente válido (cálculo do dígito verificador) antes de tentar o INSERT no banco.
-- Password: O back-end deve garantir que a senha tenha complexidade mínima (tamanho, caracteres especiais) antes de gerar o password_hash.
-- Idade: Validar se o usuário tem a idade mínima permitida (ex: 18 anos) caso o evento tenha restrição etária.
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    cpf CHAR(11) UNIQUE NOT NULL,
    birth_date DATE,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    user_role user_role_enum NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Data de Início vs. Fim: O start_date deve ser obrigatoriamente menor que o end_date.
-- Status Automático: O sistema deve ter um job ou disparador (scheduler) para alterar o event_status de 'ACTIVE' para 'FINISHED' automaticamente após o end_date.
CREATE TABLE events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    capacity INT,

    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,

    event_status event_status_enum NOT NULL,

    location_id UUID,
    organizer_id UUID NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (location_id) REFERENCES locations(id),
    FOREIGN KEY (organizer_id) REFERENCES users(id)
);

CREATE TABLE themes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(30) UNIQUE NOT NULL,
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

    CONSTRAINT unique_event_theme UNIQUE (event_id, theme_id),

    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (theme_id) REFERENCES themes(id)
);

-- Janela de Vendas: O sistema só pode permitir a criação de order_items se a data atual estiver dentro do intervalo [sale_start, sale_end].
-- Disponibilidade (Estoque): A soma das quantidades vendidas (order_items.quantity) para um ticket_id não pode ultrapassar o valor de tickets.quantity.
CREATE TABLE tickets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    batch INT,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    ticket_type VARCHAR(50) NOT NULL,
    sale_start TIMESTAMP NOT NULL,
    sale_end TIMESTAMP NOT NULL,
    event_id UUID NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (event_id) REFERENCES events(id)
);

-- Validação de Usuário vs. Nome: Se user_id for nulo, full_name é obrigatório (e vice-versa).
-- Duplicidade: Não permitir que um user_id específico seja vinculado ao mesmo event_id mais de uma vez (a UNIQUE constraint no banco já resolve, mas o back-end deve retornar um erro amigável ao usuário).
CREATE TABLE participations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(150),
    participation_role VARCHAR(50),
    user_id UUID ,
    event_id UUID NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT unique_participation UNIQUE (user_id, event_id),

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);

-- Cálculo do Total: O valor total_amount na tabela orders deve ser igual à soma de (quantity * price_at_purchase) de todos os order_items vinculados àquela ordem. O cálculo deve ser feito no back-end (nunca confiar no valor enviado pelo front-end).
-- Imutabilidade do Preço: Uma vez que o item foi inserido na tabela order_items, o campo price_at_purchase nunca deve ser alterado, mesmo que o preço do ticket mude no futuro.
CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    total_amount DECIMAL(10,2),
    order_status order_status_enum NOT NULL,
    payment_method payment_method_enum NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL,
    ticket_id UUID NOT NULL,
    quantity INT NOT NULL,
    price_at_purchase DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (ticket_id) REFERENCES tickets(id)
);

-- =========================
-- Indexes
-- =========================
CREATE INDEX idx_events_start_date ON events(start_date);
CREATE INDEX idx_events_location_id ON events(location_id);
CREATE INDEX idx_tickets_event_id ON tickets(event_id);
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_ticket_id ON order_items(ticket_id);
CREATE INDEX idx_event_themes_event_id ON event_themes(event_id);
CREATE INDEX idx_events_status ON events(event_status);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_cpf ON users(cpf);
CREATE INDEX idx_participations_event_id ON participations(event_id);