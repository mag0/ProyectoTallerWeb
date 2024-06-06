CREATE DATABASE IF NOT EXISTS tallerwebi_db;
INSERT INTO Usuario(email,nombreUsuario, password,passwordRepetida, rol, activo)
VALUES
    ('test@unlam.edu.ar', 'admin', 'admin', 'admin', 'ADMIN', false),
    ('test@unlam.edu.ar', 'admin2', 'admin2', 'admin2', 'ADMIN', false);

INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha,estado) VALUES ("Paquete 1","Fragil", "DS225", 5, 10, "2021-01-01", 'RECIBIDO');
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha,estado) VALUES ("Paquete 2","Fragil", "DS226", 5, 10, "2021-01-01", 'ENVIADO');
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha,estado) VALUES ("Paquete 3","Fragil", "DS227", 5, 10, "2021-01-01", 'RECIBIDO');
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha,estado) VALUES ("Paquete 4","Fragil", "DS228", 5, 10, "2021-01-01", 'DESPACHADO');
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha,estado) VALUES ("Paquete 5","Fragil", "DS229", 5, 10, "2021-01-01", 'ENVIADO');


INSERT INTO Vehiculo(marca, modelo, tipo, kilometrajeMaximo, combustible, resistencia, capacidad, patente, status, usuario_id)
VALUES
    ("Fiat", "Uno", "Auto", 100000, 10000, 1000, 100, "ABC123", true, 1),
    ("Yamaha", "YZF-R3", "Moto", 30000, 5000, 800, 20, "MNO789", true, 2),
    ("Ford", "F-150", "Camion", 200000, 15000, 1500, 300, "JKL321", true, 2),
    ("Toyota", "Corolla", "Auto", 120000, 11000, 1050, 110, "DEF456", true, 2),
    ("Suzuki", "GSX-R600", "Moto", 35000, 6000, 850, 25, "PQR567", true, 2),
    ("Chevrolet", "Silverado", "Camion", 220000, 16000, 1550, 350, "GHI789", false, 1),
    ("Honda", "Civic", "Auto", 140000, 12000, 1100, 115, "STU234", false, 1),
    ("Ducati", "Monster 821", "Moto", 40000, 7000, 900, 30, "VWX890", true, 1),
    ("Toyota", "Corolla", "Auto", 120000, 11000, 1050, 110, "DEU456", false, 2),
    ("Suzuki", "GSX-R600", "Moto", 35000, 6000, 850, 25, "PQR567", true, 2),
    ("Chevrolet", "Silverado", "Camion", 220000, 16000, 1550, 350, "GHI289", true, 2),
    ("Honda", "Civic", "Auto", 140000, 12000, 1100, 115, "STU284", true, 2),
    ("Ducati", "Monster 821", "Moto", 40000, 7000, 900, 30, "VWX810", true, 2);
