CREATE DATABASE IF NOT EXISTS tallerwebi_db;
INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha) VALUES  ("Paquete 1","Fragil", "DS225", 5, 10, "2021-01-01");
INSERT INTO Pedido (nombre, tipo, codigo, tamanio, peso, fecha) VALUES ('Pedido 1', 'Tipo A', 'A123', 10, 5, '2023-12-25');
INSERT INTO Pedido (nombre, tipo, codigo, tamanio, peso, fecha) VALUES ('Pedido 2', 'Tipo B', 'B456', 20, 10, '2023-12-26');

INSERT INTO Vehiculo(marca,modelo,tipo,kilometrajeMaximo,combustible, resistencia,capacidad,patente,status) VALUES ("Fiat","Uno","Auto",100000,10000,1000,100,"ABC123",true);