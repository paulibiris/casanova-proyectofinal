-- ==============================
-- SCRIPT SQL - CasaNova DB
-- ==============================

CREATE DATABASE IF NOT EXISTS casanova_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE casanova_db;

-- TABLA USUARIOS
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    password_hash VARCHAR(255) NOT NULL,
    tipo ENUM('anfitrion', 'huesped', 'admin') NOT NULL DEFAULT 'huesped',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABLA PROPIEDADES
CREATE TABLE IF NOT EXISTS propiedades (
    id_propiedad INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    direccion VARCHAR(255),
    ciudad VARCHAR(100),
    pais VARCHAR(100),
    precio_noche DECIMAL(10,2) NOT NULL,
    capacidad INT NOT NULL,
    tipo_operacion ENUM('compra', 'alquiler') DEFAULT 'alquiler',
    tipo_propiedad ENUM('Casa', 'Apartamento', 'Chalet', 'Atico') DEFAULT 'Casa',
    activa BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- TABLA RESERVAS
CREATE TABLE IF NOT EXISTS reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_propiedad INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    precio_total DECIMAL(10,2),
    estado ENUM('pendiente', 'confirmada', 'cancelada') DEFAULT 'pendiente',
    fecha_reserva DATE DEFAULT (CURRENT_DATE),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_propiedad) REFERENCES propiedades(id_propiedad) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- TABLA COMENTARIOS
CREATE TABLE IF NOT EXISTS comentarios (
    id_comentario INT AUTO_INCREMENT PRIMARY KEY,
    id_propiedad INT NOT NULL,
    id_usuario INT NOT NULL,
    comentario TEXT NOT NULL,
    puntuacion INT CHECK (puntuacion BETWEEN 1 AND 5),
    fecha DATE DEFAULT (CURRENT_DATE),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_propiedad) REFERENCES propiedades(id_propiedad) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- DATOS DE PRUEBA
INSERT INTO usuarios (nombre, email, telefono, password_hash, tipo) VALUES
('Admin CasaNova', 'admin@casanova.com', '+34 600 000 000', 'admin123', 'admin'),
('María García', 'maria@correo.com', '+34 611 222 333', 'pass123', 'anfitrion'),
('Carlos López', 'carlos@correo.com', '+34 622 333 444', 'pass123', 'huesped');

INSERT INTO propiedades (id_usuario, titulo, descripcion, direccion, ciudad, pais, precio_noche, capacidad, tipo_operacion, tipo_propiedad) VALUES
(2, 'Casa en Madrid', 'Preciosa casa en el centro de Madrid con 5 habitaciones.', 'Calle Gran Vía 123', 'Madrid', 'España', 320000, 10, 'compra', 'Casa'),
(2, 'Apartamento en Barcelona', 'Moderno apartamento a 2 minutos del metro.', 'Av. Diagonal 456', 'Barcelona', 'España', 1200, 4, 'alquiler', 'Apartamento'),
(2, 'Chalet en Valencia', 'Amplio chalet con piscina y jardín privado.', 'Urb. Las Palmas 789', 'Valencia', 'España', 450000, 8, 'compra', 'Chalet');
