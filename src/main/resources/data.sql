INSERT INTO Vida (cantidadDeVidasActuales, ultimaVezQueSeRegeneroLaVida)
VALUES (5, CURRENT_TIMESTAMP);
INSERT INTO Experiencia (cantidadExperiencia,nivel)
VALUES(0,0);

INSERT INTO Usuario (email, password, rol, activo, vida_id, comunicoPoints, experiencia_id, titulo)
VALUES ('test@unlam.edu.ar', 'test', 'ADMIN', true, 1, 0, 1, 'Principiante');


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
                                  (2, 'matriz'),
                                  (3, 'forma-palabras'),
                                  (4, 'traduccion'),
                                  (5, 'traduccion'),
                                  (6, 'traduccion'),
                                  (7, 'traduccion'),
                                  (8, 'traduccion'),
                                  (9, 'traduccion'),
                                  (10, 'traduccion'),
                                  (11, 'traduccion'),
                                  (12, 'traduccionSenia'),
                                  (13, 'matriz'),
                                  (14, 'matriz'),
                                  (15, 'matriz'),
                                  (16, 'matriz'),
                                  (17, 'matriz'),
                                  (18, 'matriz'),
                                  (19, 'matriz'),
                                  (20, 'matriz'),
                                  (21, 'forma-palabras'),
                                  (22, 'forma-palabras'),
                                  (23, 'forma-palabras'),
                                  (24, 'forma-palabras');


INSERT INTO EjercicioTraduccion(id, consigna, opcionCorrecta_id, tipoEjercicio, leccion_id) VALUES
                                                                                                (1, 'Ejercicio 1:', 1, 'traduccion', 1),
                                                                                                (2, 'Ejercicio 2:', 2, 'traduccion', 1),
                                                                                                (3, 'Ejercicio 3:', 3, 'traduccion', 1),
                                                                                                (13, 'Ejercicio 1:', 4, 'traduccion', 4),
                                                                                                (14, 'Ejercicio 2:', 5, 'traduccion', 4),
                                                                                                (15, 'Ejercicio 3:', 6, 'traduccion', 4),
                                                                                                (16, 'Ejercicio 1:', 7, 'traduccion', 5),
                                                                                                (17, 'Ejercicio 2:', 8, 'traduccion', 5),
                                                                                                (18, 'Ejercicio 3:', 9, 'traduccion', 5),
                                                                                                (19, 'Ejercicio 1:', 10, 'traduccion', 6),
                                                                                                (20, 'Ejercicio 2:', 11, 'traduccion', 6),
                                                                                                (21, 'Ejercicio 3:', 12, 'traduccion', 6),
                                                                                                (22, 'Ejercicio 1:', 13, 'traduccion', 7),
                                                                                                (23, 'Ejercicio 2:', 14, 'traduccion', 7),
                                                                                                (24, 'Ejercicio 3:', 15, 'traduccion', 7),
                                                                                                (25, 'Ejercicio 1:', 16, 'traduccion', 8),
                                                                                                (26, 'Ejercicio 2:', 17, 'traduccion', 8),
                                                                                                (27, 'Ejercicio 3:', 18, 'traduccion', 8),
                                                                                                (28, 'Ejercicio 1:', 19, 'traduccion', 9),
                                                                                                (29, 'Ejercicio 2:', 20, 'traduccion', 9),
                                                                                                (30, 'Ejercicio 3:', 21, 'traduccion', 9),
                                                                                                (31, 'Ejercicio 1:', 22, 'traduccion', 10),
                                                                                                (32, 'Ejercicio 2:', 23, 'traduccion', 10),
                                                                                                (33, 'Ejercicio 3:', 24, 'traduccion', 10),
                                                                                                (34, 'Ejercicio 1:', 25, 'traduccion', 11),
                                                                                                (35, 'Ejercicio 2:', 26, 'traduccion', 11);


INSERT INTO EjercicioMatriz(id, puntos, letra, tipoEjercicio, leccion_id) VALUES
                                                                              (7, '100000', 'A', 'matriz', 2),
                                                                              (8, '101000', 'B', 'matriz', 2),
                                                                              (9, '110000', 'C', 'matriz', 2),
                                                                              (36, '110100', 'D', 'matriz', 13),
                                                                              (37, '100100', 'E', 'matriz', 13),
                                                                              (38, '111000', 'F', 'matriz', 13),
                                                                              (39, '111100', 'G', 'matriz', 14),
                                                                              (40, '101100', 'H', 'matriz', 14),
                                                                              (41, '011000', 'I', 'matriz', 14),
                                                                              (42, '011100', 'J', 'matriz', 15),
                                                                              (43, '100010', 'K', 'matriz', 15),
                                                                              (44, '101010', 'L', 'matriz', 15),
                                                                              (45, '110010', 'M', 'matriz', 16),
                                                                              (46, '110110', 'N', 'matriz', 16),
                                                                              (47, '100110', 'O', 'matriz', 16),
                                                                              (48, '111010', 'P', 'matriz', 17),
                                                                              (49, '111110', 'Q', 'matriz', 17),
                                                                              (50, '101110', 'R', 'matriz', 17),
                                                                              (51, '011010', 'S', 'matriz', 18),
                                                                              (52, '011110', 'T', 'matriz', 18),
                                                                              (53, '100011', 'U', 'matriz', 18),
                                                                              (54, '101011', 'V', 'matriz', 19),
                                                                              (55, '011101', 'W', 'matriz', 19),
                                                                              (56, '110011', 'X', 'matriz', 19),
                                                                              (57, '110111', 'Y', 'matriz', 20),
                                                                              (58, '100111', 'Z', 'matriz', 20),
                                                                              (59, '110011', 'X', 'matriz', 20);


INSERT INTO EjercicioFormaPalabra(id, imagen, respuestaCorrecta, letras, tipoEjercicio, leccion_id) VALUES
                                                                                                        (10, 'gato.png', 'GATO', 'T, A, G, P, S, O, R', 'forma_palabras', 3),
                                                                                                        (11, 'perro.png', 'PERRO', 'R, A, P, N, S, O, E', 'forma_palabras', 3),
                                                                                                        (12, 'libro.png', 'LIBRO', 'L, O, B, N, S, I, R', 'forma_palabras', 3),
                                                                                                        (60, 'casa.png', 'CASA', 'S, C, A, T, H, Z, A', 'forma_palabras', 21),
                                                                                                        (61, 'pato.png', 'PATO', 'A, P, R, T, M, O, N', 'forma_palabras', 21),
                                                                                                        (62, 'flor.png', 'FLOR', 'L, F, P, R, O, S, C', 'forma_palabras', 21),
                                                                                                        (63, 'luz.png', 'LUZ', 'Z, R, U, L, P, E, A', 'forma_palabras', 22),
                                                                                                        (64, 'sol.png', 'SOL', 'L, S, R, T, N, O, M', 'forma_palabras', 22),
                                                                                                        (65, 'mesa.png', 'MESA', 'A, E, S, L, M, T, P', 'forma_palabras', 22),
                                                                                                        (66, 'luna.png', 'LUNA', 'A, N, U, L, R, Z, O', 'forma_palabras', 23),
                                                                                                        (67, 'ojo.png', 'OJO', 'O, M, J, L, T, N, O', 'forma_palabras', 23),
                                                                                                        (68, 'rey.png', 'REY', 'E, R, A, U, Y, M, N', 'forma_palabras', 23),
                                                                                                        (69, 'paz.png', 'PAZ', 'A, Z, M, P, T, L, R', 'forma_palabras', 24),
                                                                                                        (70, 'rio.png', 'RIO', 'R, O, M, I, L, T, S', 'forma_palabras', 24),
                                                                                                        (71, 'mar.png', 'MAR', 'A, R, Z, M, T, B, L', 'forma_palabras', 24);

INSERT INTO EjercicioTraduccionSenia(id, consigna, opcionCorrecta_id, tipoEjercicio, leccion_id) VALUES
                                                                                                     (4, 'Ejercicio 1:', 1, 'traduccionSenia', 12),
                                                                                                     (5, 'Ejercicio 2:', 2, 'traduccionSenia', 12),
                                                                                                     (6, 'Ejercicio 3:', 3, 'traduccionSenia', 12);

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
