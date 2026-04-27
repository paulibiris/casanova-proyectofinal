-- Añadir columna foto a la tabla propiedades
ALTER TABLE propiedades ADD COLUMN foto VARCHAR(255) DEFAULT 'default.jpg';
