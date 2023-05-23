INSERT INTO proveedores (categoria, code, nombre, retencion) VALUES ('D', '1000', 'nombre0', 0);
INSERT INTO proveedores (categoria, code, nombre, retencion) VALUES ('B', '1001', 'lechera1', 0);
INSERT INTO proveedores (categoria, code, nombre, retencion) VALUES ('C', '1002', 'nombre2', 0);
INSERT INTO proveedores (categoria, code, nombre, retencion) VALUES ('C', '1003', 'nombre3', 0);
INSERT INTO proveedores (categoria, code, nombre, retencion) VALUES ('B', '1004', 'nombre4', 1);
INSERT INTO proveedores (categoria, code, nombre, retencion) VALUES ('A', '1005', 'nombre5', 0);
INSERT INTO proveedores (categoria, code, nombre, retencion) VALUES ('C', '1006', 'nombre6', 1);
INSERT INTO proveedores (categoria, code, nombre, retencion) VALUES ('D', '1007', 'nombre7', 1);
INSERT INTO proveedores (categoria, code, nombre, retencion) VALUES ('A', '1008', 'nombre8', 1);
INSERT INTO proveedores (categoria, code, nombre, retencion) VALUES ('A', '1009', 'nombre9', 0);

INSERT INTO porcentajes (fecha, grasas, proveedor, solidos) VALUES (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 37, '1000', 42);
INSERT INTO porcentajes (fecha, grasas, proveedor, solidos) VALUES (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 12, '1001', 13);
INSERT INTO porcentajes (fecha, grasas, proveedor, solidos) VALUES (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 27, '1002', 26);
INSERT INTO porcentajes (fecha, grasas, proveedor, solidos) VALUES (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 52, '1003', 81);
INSERT INTO porcentajes (fecha, grasas, proveedor, solidos) VALUES (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 33, '1004', 19);
INSERT INTO porcentajes (fecha, grasas, proveedor, solidos) VALUES (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 19, '1005', 40);
INSERT INTO porcentajes (fecha, grasas, proveedor, solidos) VALUES (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 24, '1006', 28);
INSERT INTO porcentajes (fecha, grasas, proveedor, solidos) VALUES (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 25, '1007', 37);
INSERT INTO porcentajes (fecha, grasas, proveedor, solidos) VALUES (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 41, '1008', 34);
INSERT INTO porcentajes (fecha, grasas, proveedor, solidos) VALUES (DATE_SUB(CURDATE(), INTERVAL 15 DAY), 38, '1009', 23);


