INSERT INTO Usuario(id, email, password, rol, activo) VALUES
(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Opcion(id, descripcion) VALUES
(1, 'A'),
(2, 'B'),
(3, 'C');

INSERT INTO Leccion(id, titulo) VALUES
(1, 'leccion 1:'),
(2, 'leccion 2:'),
(3, 'leccion 3:');

INSERT INTO Ejercicio(id, consigna, opcionCorrecta_id, leccion_id) VALUES
(1, 'Ejercicio 1:', 1, 1),
(2, 'Ejercicio 2:', 2, 1),
(3, 'Ejercicio 3:', 3, 1),
(10, 'Forma la letra: A', 1, 2),
(11, 'Forma la letra: B', 2, 2),
(12, 'Forma la letra: C', 3, 2);

INSERT INTO Ejercicio_Opcion(Ejercicio_id, opcionesIncorrectas_id) VALUES
(1,2),
(1,3),
(2, 1),
(2, 3),
(3, 1),
(3, 2);

INSERT INTO Matriz (id, ejercicio_id, puntos) VALUES
(1, 10, '100000'),
(2, 11, '101000'),
(3, 12, '110000');

INSERT INTO EjercicioFormaPalabra(id, imagen, respuestaCorrecta, letras) values
(1, 'gato.png', "GATO", 'T, A, G, P, S, O, R'),
(2, 'perro.png', "PERRO", 'R, A, P, E, S, O, N'),
(3, 'libro.png', "LIBRO", 'L, O, B, N, S, I, R');