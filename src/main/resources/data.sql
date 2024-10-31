INSERT INTO Vida (cantidadDeVidasActuales, ultimaVezQueSeRegeneroLaVida)
VALUES (5, CURRENT_TIMESTAMP);

INSERT INTO Usuario(id, email, password, rol, activo, vida_id, comunicoPoints)
VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true, 1, 0);

INSERT INTO Opcion(id, descripcion) VALUES
                                        (1, 'A'),
                                        (2, 'B'),
                                        (3, 'C'),
                                        (4, 'D'),
                                        (5, 'E'),
                                        (6, 'F'),
                                        (7, 'G'),
                                        (8, 'H'),
                                        (9, 'I'),
                                        (10, 'J'),
                                        (11, 'K'),
                                        (12, 'L'),
                                        (13, 'M'),
                                        (14, 'N'),
                                        (15, 'O'),
                                        (16, 'P'),
                                        (17, 'Q'),
                                        (18, 'R'),
                                        (19, 'S'),
                                        (20, 'T'),
                                        (21, 'U'),
                                        (22, 'V'),
                                        (23, 'W'),
                                        (24, 'X'),
                                        (25, 'Y'),
                                        (26, 'Z');

INSERT INTO Leccion(id, tipo) VALUES
                                    (1, 'traduccion'),
                                    (2, 'Lección 2:'),
                                    (3, 'Lección 3:'),
                                    (4, 'traduccion'),
                                    (5, 'traduccion'),
                                    (6, 'traduccion'),
                                    (7, 'traduccion'),
                                    (8, 'traduccion'),
                                    (9, 'traduccion'),
                                    (10, 'traduccion'),
                                    (11, 'traduccion'),
                                    (12, 'Lección 12');

INSERT INTO Ejercicio(id, tipoEjercicio, leccion_id) VALUES
                                                         (1, 'traduccion', 1),
                                                         (2, 'traduccion', 1),
                                                         (3, 'traduccion', 1),
                                                         (4, 'traduccionSenia', 12),
                                                         (5, 'traduccionSenia', 12),
                                                         (6, 'traduccionSenia', 12),
                                                         (7, 'matriz', 2),
                                                         (8, 'matriz', 2),
                                                         (9, 'matriz', 2),
                                                         (10, 'forma_palabra', 3),
                                                         (11, 'forma_palabra', 3),
                                                         (12, 'forma_palabra', 3),
                                                         (13, 'traduccion', 4),
                                                         (14, 'traduccion', 4),
                                                         (15, 'traduccion', 4),
                                                         (16, 'traduccion', 5),
                                                         (17, 'traduccion', 5),
                                                         (18, 'traduccion', 5),
                                                         (19, 'traduccion', 6),
                                                         (20, 'traduccion', 6),
                                                         (21, 'traduccion', 6),
                                                         (22, 'traduccion', 7),
                                                         (23, 'traduccion', 7),
                                                         (24, 'traduccion', 7),
                                                         (25, 'traduccion', 8),
                                                         (26, 'traduccion', 8),
                                                         (27, 'traduccion', 8),
                                                         (28, 'traduccion', 9),
                                                         (29, 'traduccion', 9),
                                                         (30, 'traduccion', 9),
                                                         (31, 'traduccion', 10),
                                                         (32, 'traduccion', 10),
                                                         (33, 'traduccion', 10),
                                                         (34, 'traduccion', 11),
                                                         (35, 'traduccion', 11);


INSERT INTO EjercicioTraduccion(id, consigna, opcionCorrecta_id) VALUES
                                                                     (1, 'Ejercicio 1:', 1),
                                                                     (2, 'Ejercicio 2:', 2),
                                                                     (3, 'Ejercicio 3:', 3),
                                                                     (13, 'Ejercicio 1:', 4),
                                                                     (14, 'Ejercicio 2:', 5),
                                                                     (15, 'Ejercicio 3:', 6),
                                                                     (16, 'Ejercicio 1:', 7),
                                                                     (17, 'Ejercicio 2:', 8),
                                                                     (18, 'Ejercicio 3:', 9),
                                                                     (19, 'Ejercicio 1:', 10),
                                                                     (20, 'Ejercicio 2:', 11),
                                                                     (21, 'Ejercicio 3:', 12),
                                                                     (22, 'Ejercicio 1:', 13),
                                                                     (23, 'Ejercicio 2:', 14),
                                                                     (24, 'Ejercicio 3:', 15),
                                                                     (25, 'Ejercicio 1:', 16),
                                                                     (26, 'Ejercicio 2:', 17),
                                                                     (27, 'Ejercicio 3:', 18),
                                                                     (28, 'Ejercicio 1:', 19),
                                                                     (29, 'Ejercicio 2:', 20),
                                                                     (30, 'Ejercicio 3:', 21),
                                                                     (31, 'Ejercicio 1:', 22),
                                                                     (32, 'Ejercicio 2:', 23),
                                                                     (33, 'Ejercicio 3:', 24),
                                                                     (34, 'Ejercicio 1:', 25),
                                                                     (35, 'Ejercicio 2:', 26);
INSERT INTO EjercicioMatriz(id, puntos) VALUES
                                            (7, '100000'),
                                            (8, '101000'),
                                            (9, '110000');

INSERT INTO EjercicioFormaPalabra(id, imagen, respuestaCorrecta, letras) VALUES
                                                                             (10, 'gato.png', 'GATO', 'T, A, G, P, S, O, R'),
                                                                             (11, 'perro.png', 'PERRO', 'R, A, P, N, S, O, E'),
                                                                             (12, 'libro.png', 'LIBRO', 'L, O, B, N, S, I, R');

INSERT INTO ejerciciotraduccionsenia(id, consigna, opcionCorrecta_id) VALUES
                                                                     (4, 'Ejercicio 1:', 1),
                                                                     (5, 'Ejercicio 2:', 2),
                                                                     (6, 'Ejercicio 3:', 3);

INSERT INTO EjercicioTraduccion_Opcion(EjercicioTraduccion_id, opcionesIncorrectas_id) VALUES
                                                                                           (1, 2),
                                                                                           (1, 3),
                                                                                           (2, 1),
                                                                                           (2, 3),
                                                                                           (3, 1),
                                                                                           (3, 2),

                                                                                           (13, 5),
                                                                                           (13, 6),
                                                                                           (14, 4),
                                                                                           (14, 6),
                                                                                           (15, 4),
                                                                                           (15, 5),

                                                                                           (16, 8),
                                                                                           (16, 9),
                                                                                           (17, 7),
                                                                                           (17, 9),
                                                                                           (18, 7),
                                                                                           (18, 8),

                                                                                           (19, 11),
                                                                                           (19, 12),
                                                                                           (20, 10),
                                                                                           (20, 12),
                                                                                           (21, 10),
                                                                                           (21, 11),

                                                                                           (22, 14),
                                                                                           (22, 15),
                                                                                           (23, 13),
                                                                                           (23, 15),
                                                                                           (24, 13),
                                                                                           (24, 14),

                                                                                           (25, 17),
                                                                                           (25, 18),
                                                                                           (26, 16),
                                                                                           (26, 18),
                                                                                           (27, 16),
                                                                                           (27, 17),

                                                                                           (28, 20),
                                                                                           (28, 21),
                                                                                           (29, 19),
                                                                                           (29, 21),
                                                                                           (30, 19),
                                                                                           (30, 20),

                                                                                           (31, 23),
                                                                                           (31, 24),
                                                                                           (32, 22),
                                                                                           (32, 24),
                                                                                           (33, 22),
                                                                                           (33, 23),

                                                                                           (34, 26),
                                                                                           (34, 23),
                                                                                           (35, 25),
                                                                                           (35, 24),
                                                                                           (33, 26),
                                                                                           (33, 25);

INSERT INTO EjercicioTraduccionSenia_Opcion(EjercicioTraduccionSenia_id, opcionesIncorrectas_id) VALUES
                                                                                                     (4, 2),
                                                                                                     (4, 3),
                                                                                                     (5, 1),
                                                                                                     (5, 3),
                                                                                                     (6, 1),
                                                                                                     (6, 2);

INSERT INTO Curso(id, nombre, descripcion, fecha, hora, tipo, nivel, capacidad, inscriptos) VALUES
                                                                                                (1, 'Curso básico de lengua de señas', 'Introducción a la lengua de señas para principiantes.', '2024-10-15', '10:00:00', 'SEÑAS', 'BÁSICO', 20, 0),
                                                                                                (2, 'Curso intermedio de lengua de señas', 'Profundización en la lengua de señas para quienes ya tienen conocimientos básicos.', '2024-10-22', '11:00:00', 'SEÑAS', 'INTERMEDIO', 15, 0),
                                                                                                (3, 'Curso avanzado de lengua de señas', 'Dominio avanzado de la lengua de señas y su uso en situaciones complejas.', '2024-11-01', '09:00:00', 'SEÑAS', 'AVANZADO', 10, 0),
                                                                                                (4, 'Curso de braille para principiantes', 'Introducción al sistema Braille y su aplicación en la vida diaria.', '2024-11-05', '14:00:00', 'BRAILLE', 'BÁSICO', 25, 0),
                                                                                                (5, 'Curso de braille avanzado', 'Profundización en el sistema Braille para usuarios con experiencia.', '2024-11-10', '16:00:00', 'BRAILLE', 'AVANZADO', 10, 0);
