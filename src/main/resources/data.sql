CREATE DATABASE IF NOT EXISTS tallerwebi_db;
INSERT INTO Usuario(email,nombreUsuario, password,passwordRepetida, rol, activo) VALUES('test@unlam.edu.ar', 'admin', 'admin', 'admin', 'ADMIN', false);
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha) VALUES  ("Paquete 1","Fragil", "DS225", 5, 10, "2021-01-01");
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha) VALUES  ("Paquete 2","Fragil", "DS226", 5, 10, "2021-01-01");
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha) VALUES  ("Paquete 3","Fragil", "DS227", 5, 10, "2021-01-01");
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha) VALUES  ("Paquete 4","Fragil", "DS228", 5, 10, "2021-01-01");
INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha) VALUES  ("Paquete 5","Fragil", "DS229", 5, 10, "2021-01-01");
INSERT INTO Vehiculo(marca,modelo,tipo,kilometrajeMaximo,combustible, resistencia,capacidad,patente,status) VALUES ("Fiat","Uno","Auto",100000,10000,1000,100,"ABC123",true);