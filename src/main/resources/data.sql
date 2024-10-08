INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

INSERT INTO Opcion(id, descripcion) VALUES (1, 'A'), (2, 'B'), (3, 'C');

INSERT INTO Ejercicio(id, consigna, opcionCorrecta_id) VALUES (1, 'Ejercicio de traduccion: ', 1);

INSERT INTO Ejercicio_Opcion(Ejercicio_id, opcionesIncorrectas_id) VALUES (1,2), (1,3);

INSERT INTO Letra(id, nombre, imagenSenias, imagenBraille) VALUES
(1, "A", 'senias-a.png', 'braille-a.png'),
(2, "B", 'senias-b.png', 'braille-b.png'),
(3, "C", 'senias-c.png', 'braille-c.png'),
(4, "D", 'senias-d.png', 'braille-d.png'),
(5, "E", 'senias-e.png', 'braille-e.png'),
(6, "F", 'senias-f.png', 'braille-f.png'),
(7, "G", 'senias-g.png', 'braille-g.png'),
(8, "H", 'senias-h.png', 'braille-h.png'),
(9, "I", 'senias-i.png', 'braille-i.png'),
(10, "J", 'senias-j.png', 'braille-j.png'),
(11, "K", 'senias-k.png', 'braille-k.png'),
(12, "L", 'senias-l.png', 'braille-l.png'),
(13, "M", 'senias-m.png', 'braille-m.png'),
(14, "N", 'senias-n.png', 'braille-n.png'),
(15, "Ñ", 'senias-ñ.png', 'braille-ñ.png'),
(16, "O", 'senias-o.png', 'braille-o.png'),
(17, "P", 'senias-p.png', 'braille-p.png'),
(18, "Q", 'senias-q.png', 'braille-q.png'),
(19, "R", 'senias-r.png', 'braille-r.png'),
(20, "S", 'senias-s.png', 'braille-s.png'),
(21, "T", 'senias-t.png', 'braille-t.png'),
(22, "U", 'senias-u.png', 'braille-u.png'),
(23, "V", 'senias-v.png', 'braille-v.png'),
(24, "W", 'senias-w.png', 'braille-w.png'),
(25, "X", 'senias-x.png', 'braille-x.png'),
(26, "Y", 'senias-y.png', 'braille-y.png'),
(27, "Z", 'senias-z.png', 'braille-z.png');

INSERT INTO Curso(id, nombre, descripcion, fecha, hora, tipo, nivel, capacidad, inscriptos) VALUES
(1, 'Curso Básico de Lengua de Señas', 'Introducción a la lengua de señas para principiantes.', '2024-10-15', '10:00:00', 'senias', 'basico', 20, 0),
(2, 'Curso Intermedio de Lengua de Señas', 'Profundización en la lengua de señas para quienes ya tienen conocimientos básicos.', '2024-10-22', '11:00:00', 'senias', 'intermedio', 15, 0),
(3, 'Curso Avanzado de Lengua de Señas', 'Dominio avanzado de la lengua de señas y su uso en situaciones complejas.', '2024-11-01', '09:00:00', 'senias', 'avanzado', 10, 0),
(4, 'Curso de Braille para Principiantes', 'Introducción al sistema Braille y su aplicación en la vida diaria.', '2024-11-05', '14:00:00', 'braille', 'basico', 25, 0),
(5, 'Curso de Braille Avanzado', 'Profundización en el sistema Braille para usuarios con experiencia.', '2024-11-10', '16:00:00', 'braille', 'avanzado', 10, 0);