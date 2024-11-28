INSERT INTO Vida (cantidadDeVidasActuales, ultimaVezQueSeRegeneroLaVida)
VALUES (5, CURRENT_TIMESTAMP), (5, CURRENT_TIMESTAMP), (5, CURRENT_TIMESTAMP), (5, CURRENT_TIMESTAMP);
INSERT INTO Experiencia (cantidadExperiencia,nivel)
VALUES(0,0), (0, 0), (0, 0), (0,0);

INSERT INTO tiposuscripcion (id, nombre, descripcion) VALUES (1, 'sin plan', '* Sin beneficio');
INSERT INTO tiposuscripcion (id, nombre, descripcion) VALUES (2, 'basico', '* 500 ComunicoPoints<br>* 5 ayudas<br>');
INSERT INTO tiposuscripcion (id, nombre, descripcion) VALUES (3, 'estandar', '* 1000 ComunicoPoints<br>* 10 ayudas<br>* 1 llave');
INSERT INTO tiposuscripcion (id, nombre, descripcion) VALUES (4, 'premium', '* 1500 ComunicoPoints<br>* 15 ayudas<br>* 5 llaves<br>* Obtener vidas ilimitadas');
INSERT INTO suscripcion (id,tipo_suscripcion_id) values (1,1);
INSERT INTO suscripcion (id,tipo_suscripcion_id) values (2,2);
INSERT INTO suscripcion (id,tipo_suscripcion_id) values (3,3);
INSERT INTO suscripcion (id,tipo_suscripcion_id) values (4,4);

INSERT INTO codigodescuento(id) values (1);


INSERT INTO Usuario (descripcion, email, emailVerificado, password, rol, activo, nombreDeUsuario, vida_id, comunicoPoints, experiencia_id, titulo, suscripcion_id, ayudas, llaves)
VALUES ('Interesada en aprender braille','test@unlam.edu.ar', true,'test', 'ADMIN', true,'María Paz', 1, 0, 1, 'Principiante', 1, 5,0),
       ('Interesada en aprender lenguaje de señas','lira@asd.com', true,'123', 'ADMIN', true,'Lirita', 2, 0, 2, 'Principiante', 1, 5, 0),
       ('Me gusta el paty','matthew@asd.com', true,'123', 'ADMIN', true,'Matthew', 3, 0, 3, 'Principiante', 1, 5, 0),
       ('putoelqueleexdxd','algo@asd.com', true,'123', 'ADMIN', true,'Yo pues quien mas', 4, 0, 4, 'Principiante', 1, 5, 0);

INSERT INTO Amigo (usuario_id, amigo_id) VALUES (1, 2), (2,1), (1, 3), (3,1);

INSERT INTO SolicitudAmistad (usuario_id_solicitante, usuario_id_solicitado) VALUES (4, 1);

INSERT INTO tiposuscripcion (id, nombre, descripcion) VALUES (1, 'sin plan', '* Sin beneficio');
INSERT INTO tiposuscripcion (id, nombre, descripcion) VALUES (2, 'basico', '* 500 ComunicoPoints<br>* 5 ayudas<br>');
INSERT INTO tiposuscripcion (id, nombre, descripcion) VALUES (3, 'estandar', '* 1000 ComunicoPoints<br>* 10 ayudas<br>* 1 llave');
INSERT INTO tiposuscripcion (id, nombre, descripcion) VALUES (4, 'premium', '* 1500 ComunicoPoints<br>* 15 ayudas<br>* 5 llaves<br>* Obtener vidas ilimitadas');
INSERT INTO suscripcion (id,tipo_suscripcion_id) values (1,1);
INSERT INTO suscripcion (id,tipo_suscripcion_id) values (2,2);
INSERT INTO suscripcion (id,tipo_suscripcion_id) values (3,3);
INSERT INTO suscripcion (id,tipo_suscripcion_id) values (4,4);

INSERT INTO codigodescuento(id) values (1);


INSERT INTO Usuario (descripcion, email, emailVerificado, password, rol, activo, nombreDeUsuario, vida_id, comunicoPoints, experiencia_id, titulo, suscripcion_id, ayudas, llaves)
VALUES ('Interesado en aprender braille','test@unlam.edu.ar', true,'test', 'ADMIN', true,'TestUnlam', 1, 0, 1, 'Principiante', 1, 5,0),
       ('asd','lira@asd.com', true,'123', 'ADMIN', true,'Lirita', 1, 0, 1, 'Principiante', 1, 5, 0);

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
                                  (12, 'senia'),
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
                                  (24, 'forma-palabras'),
                                  (25, 'combinado'),
                                  (26, 'combinado'),
                                  (27, 'combinado'),
                                  (28, 'combinado'),
                                  (29, 'combinado'),

                                  (30, 'senia'),
                                  (31, 'senia'),
                                  (32, 'senia'),
                                  (33, 'senia'),
                                  (34, 'senia'),
                                  (35, 'senia'),
                                  (36, 'senia'),
                                  (37, 'senia');



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
                                                                                                (34, 'Ejercicio 2:', 25, 'traduccion', 11),
                                                                                                (35, 'Ejercicio 3:', 26, 'traduccion', 11),
                                                                                                (36, 'Ejercicio 1:', 24, 'traduccion', 11),
                                                                                                (72, 'Ejercicio 3:', 4, 'traduccion', 25),
                                                                                                (73, 'Ejercicio 3:', 8, 'traduccion', 26),
                                                                                                (74, 'Ejercicio 3:', 10, 'traduccion', 27),
                                                                                                (75, 'Ejercicio 3:', 12, 'traduccion', 28),
                                                                                                (76, 'Ejercicio 3:', 16, 'traduccion', 29);


INSERT INTO EjercicioMatriz(id, puntos, letra, tipoEjercicio, leccion_id) VALUES
                                                                              (7, '100000', 'A', 'matriz', 2),
                                                                              (8, '101000', 'B', 'matriz', 2),
                                                                              (9, '110000', 'C', 'matriz', 2),
                                                                              (202, '110100', 'D', 'matriz', 13),
                                                                              (203, '100100', 'E', 'matriz', 13),
                                                                              (204, '111000', 'F', 'matriz', 13),
                                                                              (205, '111100', 'G', 'matriz', 14),
                                                                              (206, '101100', 'H', 'matriz', 14),
                                                                              (207, '011000', 'I', 'matriz', 14),
                                                                              (208, '011100', 'J', 'matriz', 15),
                                                                              (209, '100010', 'K', 'matriz', 15),
                                                                              (210, '101010', 'L', 'matriz', 15),
                                                                              (211, '110010', 'M', 'matriz', 16),
                                                                              (212, '110110', 'N', 'matriz', 16),
                                                                              (213, '100110', 'O', 'matriz', 16),
                                                                              (214, '111010', 'P', 'matriz', 17),
                                                                              (215, '111110', 'Q', 'matriz', 17),
                                                                              (216, '101110', 'R', 'matriz', 17),
                                                                              (217, '011010', 'S', 'matriz', 18),
                                                                              (218, '011110', 'T', 'matriz', 18),
                                                                              (219, '100011', 'U', 'matriz', 18),
                                                                              (220, '101011', 'V', 'matriz', 19),
                                                                              (221, '011101', 'W', 'matriz', 19),
                                                                              (222, '110011', 'X', 'matriz', 19),
                                                                              (223, '110111', 'Y', 'matriz', 20),
                                                                              (224, '100111', 'Z', 'matriz', 20),
                                                                              (226, '110011', 'X', 'matriz', 20),
                                                                              (227, '111000', 'F', 'matriz', 25),
                                                                              (228, '111010', 'P', 'matriz', 26),
                                                                              (229, '101010', 'L', 'matriz', 27),
                                                                              (230, '011010', 'S', 'matriz', 28),
                                                                              (231, '111110', 'Q', 'matriz', 29);


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
                                                                                                        (71, 'mar.png', 'MAR', 'A, R, Z, M, T, B, L', 'forma_palabras', 24),
                                                                                                        (81, 'casa.png', 'CASA', 'S, C, A, T, H, Z, A', 'forma_palabras',25),
                                                                                                        (82, 'luna.png', 'LUNA', 'A, N, U, L, R, Z, O', 'forma_palabras', 26),
                                                                                                        (83, 'sol.png', 'SOL', 'L, S, R, T, N, O, M', 'forma_palabras', 27),
                                                                                                        (84, 'ojo.png', 'OJO', 'O, M, J, L, T, N, O', 'forma_palabras', 28),
                                                                                                        (85, 'rey.png', 'REY', 'E, R, A, U, Y, M, N', 'forma_palabras', 29);

INSERT INTO EjercicioTraduccionSenia(id, consigna, opcionCorrecta_id, tipoEjercicio, leccion_id) VALUES
                                                                                                     (104, 'Ejercicio 1:', 1, 'senia', 12),
                                                                                                     (105, 'Ejercicio 2:', 2, 'senia', 12),
                                                                                                     (106, 'Ejercicio 3:', 3, 'senia', 12),
                                                                                                     (107, 'Ejercicio 1:', 4, 'senia', 30),
                                                                                                     (108, 'Ejercicio 2:', 5, 'senia', 30),
                                                                                                     (109, 'Ejercicio 3:', 6, 'senia', 30),
                                                                                                     (110, 'Ejercicio 1:', 7, 'senia', 31),
                                                                                                     (111, 'Ejercicio 2:', 8, 'senia', 31),
                                                                                                     (112, 'Ejercicio 3:', 9, 'senia', 31),
                                                                                                     (86, 'Ejercicio 1:', 10, 'senia', 32),
                                                                                                     (87, 'Ejercicio 2:', 11, 'senia', 32),
                                                                                                     (88, 'Ejercicio 3:', 12, 'senia', 32),
                                                                                                     (89, 'Ejercicio 1:', 13, 'senia', 33),
                                                                                                     (90, 'Ejercicio 2:', 14, 'senia', 33),
                                                                                                     (91, 'Ejercicio 3:', 15, 'senia', 33),
                                                                                                     (92, 'Ejercicio 1:', 16, 'senia', 34),
                                                                                                     (93, 'Ejercicio 2:', 17, 'senia', 34),
                                                                                                     (94, 'Ejercicio 3:', 18, 'senia', 34),
                                                                                                     (95, 'Ejercicio 1:', 19, 'senia', 35),
                                                                                                     (96, 'Ejercicio 2:', 20, 'senia', 35),
                                                                                                     (97, 'Ejercicio 3:', 21, 'senia', 35),
                                                                                                     (98, 'Ejercicio 1:', 22, 'senia', 36),
                                                                                                     (99, 'Ejercicio 2:', 23, 'senia', 36),
                                                                                                     (100, 'Ejercicio 3:', 24, 'senia', 36),
                                                                                                     (101, 'Ejercicio 2:', 25, 'senia', 37),
                                                                                                     (102, 'Ejercicio 3:', 26, 'senia', 37),
                                                                                                     (103, 'Ejercicio 1:', 24, 'senia', 37);

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
                                                                                           (36, 25),
                                                                                           (36, 26),


                                                                                           (72, 5),
                                                                                           (72, 6),

                                                                                           (73, 7),
                                                                                           (73, 9),

                                                                                           (74, 11),
                                                                                           (74, 12),

                                                                                           (75, 10),
                                                                                           (75, 11),

                                                                                           (76, 17),
                                                                                           (76, 18);


INSERT INTO EjercicioTraduccionSenia_Opcion(EjercicioTraduccionSenia_id, opcionesIncorrectas_id) VALUES
                                                                                                     (104, 2),
                                                                                                     (104, 3),
                                                                                                     (105, 1),
                                                                                                     (105, 3),
                                                                                                     (106, 1),
                                                                                                     (106, 2),

                                                                                                     (107, 5),
                                                                                                     (107, 6),
                                                                                                     (108, 4),
                                                                                                     (108, 6),
                                                                                                     (109, 4),
                                                                                                     (109, 5),

                                                                                                     (110, 8),
                                                                                                     (110, 9),
                                                                                                     (111, 7),
                                                                                                     (111, 9),
                                                                                                     (112, 7),
                                                                                                     (112, 8),

                                                                                                     (86, 11),
                                                                                                     (86, 12),
                                                                                                     (87, 10),
                                                                                                     (87, 12),
                                                                                                     (88, 10),
                                                                                                     (88, 11),

                                                                                                     (89, 14),
                                                                                                     (89, 15),
                                                                                                     (90, 13),
                                                                                                     (90, 15),
                                                                                                     (91, 13),
                                                                                                     (91, 14),

                                                                                                     (92, 17),
                                                                                                     (92, 18),
                                                                                                     (93, 16),
                                                                                                     (93, 18),
                                                                                                     (94, 16),
                                                                                                     (94, 17),

                                                                                                     (95, 20),
                                                                                                     (95, 21),
                                                                                                     (96, 19),
                                                                                                     (96, 21),
                                                                                                     (97, 19),
                                                                                                     (97, 20),

                                                                                                     (98, 23),
                                                                                                     (98, 24),
                                                                                                     (99, 22),
                                                                                                     (99, 24),
                                                                                                     (100, 22),
                                                                                                     (100, 23),

                                                                                                     (101, 26),
                                                                                                     (101, 23),
                                                                                                     (102, 25),
                                                                                                     (102, 24),
                                                                                                     (103, 25),
                                                                                                     (103, 26);


INSERT INTO Curso(id, nombre, descripcion, fecha, hora, tipo, nivel, capacidad) VALUES
                                                                                    (1, 'Curso básico de lengua de señas', 'Introducción a la lengua de señas para principiantes.', '2024-10-15', '10:00:00', 'SEÑAS', 'BÁSICO', 20),
                                                                                    (2, 'Curso intermedio de lengua de señas', 'Profundización en la lengua de señas para quienes ya tienen conocimientos básicos.', '2024-10-22', '11:00:00', 'SEÑAS', 'INTERMEDIO', 15),
                                                                                    (3, 'Curso avanzado de lengua de señas', 'Dominio avanzado de la lengua de señas y su uso en situaciones complejas.', '2024-11-01', '09:00:00', 'SEÑAS', 'AVANZADO', 10),
                                                                                    (4, 'Curso de braille para principiantes', 'Introducción al sistema Braille y su aplicación en la vida diaria.', '2024-11-05', '14:00:00', 'BRAILLE', 'BÁSICO', 25),
                                                                                    (5, 'Curso de braille avanzado', 'Profundización en el sistema Braille para usuarios con experiencia.', '2024-11-10', '16:00:00', 'BRAILLE', 'AVANZADO', 10);
