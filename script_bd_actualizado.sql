CREATE DATABASE TiendaVideojuegosGO
GO

USE TiendaVideojuegosGO
GO

-- Tabla de Usuarios
CREATE TABLE Usuarios (
    id_usuario INT PRIMARY KEY IDENTITY(1,1),
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL CHECK (tipo_usuario IN ('administrador', 'usuario')),
    estado BIT DEFAULT 1,
    fecha_registro DATETIME DEFAULT GETDATE()
);

-- Tabla de Proveedores
CREATE TABLE Proveedores (
    id_proveedor INT PRIMARY KEY IDENTITY(1,1),
    numero_identificacion VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    nombre_contacto VARCHAR(100),
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    correo_electronico VARCHAR(100),
    celular VARCHAR(20),
    estado BIT DEFAULT 1,
    fecha_registro DATETIME DEFAULT GETDATE()
);

-- Tabla de Productos (Videojuegos)
CREATE TABLE Productos (
    id_producto INT PRIMARY KEY IDENTITY(1,1),
    clave_producto VARCHAR(50) NOT NULL UNIQUE,
    nombre_producto VARCHAR(150) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    caracteristicas TEXT,
    marca VARCHAR(100),
    id_proveedor INT,
    existencia INT DEFAULT 0,
    estado BIT DEFAULT 1,
    fecha_registro DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor)
);

-- Tabla de Clientes
CREATE TABLE Clientes (
    id_cliente INT PRIMARY KEY IDENTITY(1,1),
    rfc VARCHAR(13) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    correo_electronico VARCHAR(100),
    celular VARCHAR(20),
    tipo_cliente VARCHAR(50),
    estado BIT DEFAULT 1,
    fecha_registro DATETIME DEFAULT GETDATE()
);

-- Tabla de Ventas
CREATE TABLE Ventas (
    id_venta INT PRIMARY KEY IDENTITY(1,1),
    id_cliente INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    fecha_venta DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

-- Usuarios de prueba
INSERT INTO Usuarios (nombre_usuario, password, tipo_usuario) VALUES 
('admin', '123456', 'administrador'),
('usuario1', '123456', 'usuario');

-- Insertar 25 Proveedores
INSERT INTO Proveedores (numero_identificacion, nombre, nombre_contacto, direccion, telefono, correo_electronico, celular) VALUES
('PROV001', 'Nintendo Distribution Mexico', 'Mario Rossi', 'Av. Juarez 123, CDMX', '5555-1234', 'contacto@nintendo.mx', '55-1234-5678'),
('PROV002', 'Sony Interactive Entertainment', 'Luis Gomez', 'Reforma 456, CDMX', '5555-5678', 'ventas@sony.mx', '55-8765-4321'),
('PROV003', 'Microsoft Gaming Mexico', 'Carlos Ruiz', 'Insurgentes Sur 789, CDMX', '5555-9012', 'ventas@microsoft.mx', '55-9012-3456'),
('PROV004', 'Electronic Arts Mexico', 'Ana Martinez', 'Polanco 321, CDMX', '5555-3456', 'info@ea.mx', '55-3456-7890'),
('PROV005', 'Ubisoft Mexico', 'Pedro Sanchez', 'Santa Fe 654, CDMX', '5555-7890', 'contacto@ubisoft.mx', '55-7890-1234'),
('PROV006', 'Activision Blizzard', 'Laura Torres', 'Condesa 987, CDMX', '5555-2345', 'ventas@activision.mx', '55-2345-6789'),
('PROV007', 'Bandai Namco', 'Jorge Ramirez', 'Roma Norte 147, CDMX', '5555-6789', 'info@bandai.mx', '55-6789-0123'),
('PROV008', 'Capcom Mexico', 'Sofia Hernandez', 'Del Valle 258, CDMX', '5555-0123', 'contacto@capcom.mx', '55-0123-4567'),
('PROV009', 'Square Enix', 'Miguel Castro', 'Coyoacan 369, CDMX', '5555-4567', 'ventas@squareenix.mx', '55-4567-8901'),
('PROV010', 'Sega Mexico', 'Diana Lopez', 'Narvarte 741, CDMX', '5555-8901', 'info@sega.mx', '55-8901-2345'),
('PROV011', 'Konami Digital', 'Roberto Flores', 'Benito Juarez 852, CDMX', '5555-1357', 'contacto@konami.mx', '55-1357-2468'),
('PROV012', 'Take-Two Interactive', 'Patricia Morales', 'Miguel Hidalgo 963, CDMX', '5555-2468', 'ventas@take2.mx', '55-2468-3579'),
('PROV013', 'Bethesda Softworks', 'Fernando Diaz', 'Cuauhtemoc 159, CDMX', '5555-3579', 'info@bethesda.mx', '55-3579-4680'),
('PROV014', 'CD Projekt Red', 'Gabriela Vargas', 'Tlalpan 357, CDMX', '5555-4680', 'contacto@cdprojekt.mx', '55-4680-5791'),
('PROV015', 'Rockstar Games', 'Alejandro Reyes', 'Iztapalapa 468, CDMX', '5555-5791', 'ventas@rockstar.mx', '55-5791-6802'),
('PROV016', 'Epic Games', 'Valeria Mendoza', 'Azcapotzalco 579, CDMX', '5555-6802', 'info@epicgames.mx', '55-6802-7913'),
('PROV017', 'Valve Corporation', 'Ricardo Ortiz', 'Gustavo A Madero 680, CDMX', '5555-7913', 'contacto@valve.mx', '55-7913-8024'),
('PROV018', 'Riot Games', 'Monica Silva', 'Venustiano Carranza 791, CDMX', '5555-8024', 'ventas@riot.mx', '55-8024-9135'),
('PROV019', 'Blizzard Entertainment', 'Sergio Gutierrez', 'Alvaro Obregon 802, CDMX', '5555-9135', 'info@blizzard.mx', '55-9135-0246'),
('PROV020', 'FromSoftware', 'Claudia Rojas', 'Magdalena Contreras 913, CDMX', '5555-0246', 'contacto@fromsoftware.mx', '55-0246-1357'),
('PROV021', 'Atlus Games', 'Javier Medina', 'Milpa Alta 024, CDMX', '5555-1470', 'ventas@atlus.mx', '55-1470-2581'),
('PROV022', 'Koei Tecmo', 'Adriana Cruz', 'Cuajimalpa 135, CDMX', '5555-2581', 'info@koeitecmo.mx', '55-2581-3692'),
('PROV023', 'Arc System Works', 'Daniel Jimenez', 'Xochimilco 246, CDMX', '5555-3692', 'contacto@arcsystem.mx', '55-3692-4703'),
('PROV024', 'Platinum Games', 'Isabel Romero', 'Tlahuac 357, CDMX', '5555-4703', 'ventas@platinum.mx', '55-4703-5814'),
('PROV025', 'Devolver Digital', 'Oscar Navarro', 'Iztacalco 468, CDMX', '5555-5814', 'info@devolver.mx', '55-5814-6925');
