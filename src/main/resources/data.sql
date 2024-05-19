CREATE DATABASE IF NOT EXISTS tallerwebi_db;
INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha) VALUES  ("Paquete 1","Fragil", "DS225", 5, 10, "2021-01-01");
INSERT INTO Vehiculo(marca,modelo,tipo,kilometrajeMaximo,combustible, resistencia,capacidad,patente,status) VALUES ("Fiat","Uno","Auto",100000,10000,1000,100,"ABC123",true);