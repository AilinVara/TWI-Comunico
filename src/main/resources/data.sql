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