INSERT INTO Vida (cantidadDeVidasActuales, ultimaVezQueSeRegeneroLaVida)
VALUES (5, CURRENT_TIMESTAMP);

INSERT INTO Usuario(id, email, password, rol, activo, vida_id)
VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true, 1);

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
                                                                       (2, 'Ejercicio con video', 2, 1),
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

INSERT INTO Curso(id, nombre, descripcion, fecha, hora, tipo, nivel, capacidad, inscriptos) VALUES
                                                                                                (1, 'Curso básico de lengua de señas', 'Introducción a la lengua de señas para principiantes.', '2024-10-15', '10:00:00', 'SEÑAS', 'BÁSICO', 20, 0),
                                                                                                (2, 'Curso intermedio de lengua de señas', 'Profundización en la lengua de señas para quienes ya tienen conocimientos básicos.', '2024-10-22', '11:00:00', 'SEÑAS', 'INTERMEDIO', 15, 0),
                                                                                                (3, 'Curso avanzado de lengua de señas', 'Dominio avanzado de la lengua de señas y su uso en situaciones complejas.', '2024-11-01', '09:00:00', 'SEÑAS', 'AVANZADO', 10, 0),
                                                                                                (4, 'Curso de braille para principiantes', 'Introducción al sistema Braille y su aplicación en la vida diaria.', '2024-11-05', '14:00:00', 'BRAILLE', 'BÁSICO', 25, 0),
                                                                                                (5, 'Curso de braille avanzado', 'Profundización en el sistema Braille para usuarios con experiencia.', '2024-11-10', '16:00:00', 'BRAILLE', 'AVANZADO', 10, 0);
