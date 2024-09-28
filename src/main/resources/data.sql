INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Opcion(id, descripcion) VALUES (1, 'A'), (2, 'B'), (3, 'C');

INSERT INTO Ejercicio(id, consigna, opcionCorrecta_id) VALUES (1, '¿Cuál es la letra que aparece en la siguiente imagen? ', 1);

INSERT INTO Ejercicio_Opcion(Ejercicio_id, opcionesIncorrectas_id) VALUES (1,2), (1,3);

