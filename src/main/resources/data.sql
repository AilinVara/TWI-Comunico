INSERT INTO Vida (cantidadDeVidasActuales, ultimaVezQueSeRegeneroLaVida)
VALUES (5, CURRENT_TIMESTAMP);
INSERT INTO Experiencia (cantidadExperiencia,nivel)
VALUES(0,0);

INSERT INTO Usuario (email, password, rol, activo, vida_id, comunicoPoints, experiencia_id, titulo)
VALUES ('test@unlam.edu.ar', 'test', 'ADMIN', true, 1, 0, 1, 'Principiante');


INSERT INTO Opcion(id, descripcion) VALUES
                                        (1, 'A'),
                                        (2, 'B'),
                                        (3, 'C');

INSERT INTO Leccion(id, titulo) VALUES
                                    (1, 'lección 1:'),
                                    (2, 'lección 2:'),
                                    (3, 'lección 3:'),
                                    (4, 'lección 4:');

INSERT INTO Ejercicio(id, tipoEjercicio, leccion_id) VALUES
                                                         (1, 'traduccion', 1),
                                                         (2, 'traduccion', 1),
                                                         (3, 'traduccion', 1),
                                                         (4, 'traduccionSenia', 4),
                                                         (5, 'traduccionSenia', 4),
                                                         (6, 'traduccionSenia', 4),
                                                         (7, 'matriz', 2),
                                                         (8, 'matriz', 2),
                                                         (9, 'matriz', 2),
                                                         (10, 'forma_palabra', 3),
                                                         (11, 'forma_palabra', 3),
                                                         (12, 'forma_palabra', 3);

INSERT INTO EjercicioTraduccion(id, consigna, opcionCorrecta_id) VALUES
                                                                     (1, 'Ejercicio 1:', 1),
                                                                     (2, 'Ejercicio 2:', 2),
                                                                     (3, 'Ejercicio 3:', 3);

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
                                                                                           (3, 2);

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
