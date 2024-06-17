CREATE DATABASE IF NOT EXISTS tallerwebi_db;
INSERT INTO Usuario(email,nombreUsuario, password,passwordRepetida, rol, activo)
VALUES
    ('test@unlam.edu.ar', 'admin', 'admin', 'admin', 'ADMIN', true),
    ('test@unlam.edu.ar', 'admin2', 'admin2', 'admin2', 'ADMIN', false);

INSERT INTO Zona(lat,lon) VALUES (-34.6582977,-58.5701959), (-34.63523,-58.79215), (-34.6690,-58.5561);

INSERT INTO Pedido(nombre,tipo,codigo,tamanio,peso,fecha,estado,destino_id) VALUES
    ("Paquete 1","Electronica","DS225",10,10,"2024-06-08",'DISPONIBLE',1),
    ("Paquete 2","Alimentos","DS226",15,20,"2024-06-07",'DISPONIBLE',1),
    ("Paquete 3","Ropa","DS227",5,3,"2024-06-04",'DISPONIBLE',2),
    ("Paquete 4","Accesorios","DS228",5,10,"2024-06-07",'DISPONIBLE',2),
    ("Paquete 5","Herramientas","DS229",20,30,"2021-06-08",'DISPONIBLE',3),
    ("Paquete 6","Electronica","DS230",8,12,"2024-06-09",'DISPONIBLE',2),
    ("Paquete 7","Alimentos","DS231",12,18,"2024-06-06",'DISPONIBLE',2),
    ("Paquete 8","Ropa","DS232",6,4,"2024-06-05",'DISPONIBLE',3),
    ("Paquete 9","Accesorios","DS233",7,8,"2024-06-06",'DISPONIBLE',3),
    ("Paquete 10","Herramientas","DS234",18,25,"2022-06-08",'DISPONIBLE',3),
    ("Paquete 11","Electronica","DS235",9,11,"2024-06-10",'DISPONIBLE',1),
    ("Paquete 12","Alimentos","DS236",14,22,"2024-06-08",'DISPONIBLE',1),
    ("Paquete 13","Ropa","DS237",4,2,"2024-06-03",'DISPONIBLE',1),
    ("Paquete 14","Accesorios","DS238",6,12,"2024-06-05",'DISPONIBLE',1),
    ("Paquete 15","Herramientas","DS239",22,35,"2023-06-08",'DISPONIBLE',2),
    ("Paquete 16","Electronica","DS240",11,15,"2024-06-11",'DISPONIBLE',2),
    ("Paquete 17","Alimentos","DS241",16,24,"2024-06-09",'DISPONIBLE',3),
    ("Paquete 18","Ropa","DS242",7,5,"2024-06-02",'DISPONIBLE',3),
    ("Paquete 19","Accesorios","DS243",8,14,"2024-06-04",'DISPONIBLE',1),
    ("Paquete 20","Herramientas","DS244",24,40,"2020-06-08",'DISPONIBLE',3);



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
