-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.6.17 - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para eskalada
CREATE DATABASE IF NOT EXISTS `eskalada` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `eskalada`;


-- Volcando estructura para tabla eskalada.grado
CREATE TABLE IF NOT EXISTS `grado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(10) NOT NULL COMMENT 'Grado de dificultad de la via de escalada, por ejemplo: ',
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.grado: ~41 rows (aproximadamente)
/*!40000 ALTER TABLE `grado` DISABLE KEYS */;
INSERT IGNORE INTO `grado` (`id`, `nombre`, `descripcion`) VALUES
	(1, 'I', 'Trepada sencilla'),
	(2, 'II', 'Trepada fácil'),
	(3, 'II+', 'Trepada'),
	(4, 'III', 'Trepada comprometida'),
	(5, 'III+', 'Trepada complicada'),
	(6, 'IV', 'Escalada sencilla'),
	(7, 'IV+', 'Escalada sencilla'),
	(8, 'V', 'Escalada fácil'),
	(9, 'V+', 'Escalada fácil'),
	(10, '6a', 'Escalada complicada'),
	(11, '6a+', 'Escalada complicada'),
	(12, '6b', 'Escalada difícil'),
	(13, '6b+', 'Escalada difícil'),
	(14, '6c', 'Escalada difícil'),
	(15, '6c+', 'Escalada difícil'),
	(16, '7a', 'Escalada muy difícil'),
	(17, '7a+', 'Escalada muy difícil'),
	(18, '7b', 'Escalada muy difícil'),
	(19, '7b+', 'Escalada muy difícil'),
	(20, '7c', 'Escalada muy difícil'),
	(21, '7c+', 'Escalada muy difícil'),
	(22, '8a', 'Escalada extrema'),
	(23, '8a+', 'Escalada extrema'),
	(24, '8b', 'Escalada extrema'),
	(25, '8b+', 'Escalada extrema'),
	(26, '8c', 'Escalada extrema'),
	(27, '8c+', 'Escalada extrema'),
	(28, '9a', 'Escalada extrema'),
	(29, '9a+', 'Escalada extrema'),
	(30, '9b', 'Escalada extrema'),
	(31, '9b+', 'Escalada extrema'),
	(32, 'Ab', 'Artificial con buriles. El largo de artificial está equipado con buriles, rivets o chinchetas.'),
	(33, 'Ae', 'Artificial equipado. El largo está equipado con clavos, spits, parabolts o material abandonado (fisureros, plomos, etc.)'),
	(34, 'A0', 'Nos agarramos al seguro y nos superamos para poder progresar. No utilzamos estribos.'),
	(35, 'A1 o C1', 'Artificial fácil. Los emplazamientos suelen ser sencillos y sólidos. Si el escalador se cae el seguro anterior debiera de pararle sin problemas. Imaginémonos el mayor techo del mundo. Si está asegurado mediante parabolts, clavos, fisureros o sistemas de expansión (friends) a prueba de bombas, lo graduaremos con ésta dificultad. Nuestros riñones se quejaran por estar colgados en el vacio, pero las posibilidades de hacerse daño si nos caemos son minimas. El largo nos puede llevar una o dos horas el finalizarlo.'),
	(36, 'A2 o C2', 'Artificial moderado. Los emplazamientos suelen ser sólidos, pero pueden resultar delicados y arduos de colocar. Normalmente hay uno o dos emplazamientos que solo aguantan el peso de nuestro cuerpo entre otros que si aguantarian una buena caida. De 5 a 10 metros de caida potencial pero sin peligro. El largo no puede llevar entre una y tres horas el finalizarlo.'),
	(37, 'A3 o C3', 'Artificial duro. Necesitamos comprobar metodicamente los seguros mediante la utilización del probador. Normalmente se tratará de un largo compuesto de varios seguros débiles, cinco o seis aproximadamente, y los cuales solo aguantan el peso de nuestro cuerpo y no una caida. No obstante en el largo contaremos con buenos seguros que si que aguantarian una posible caida. Unos 20 metros de caida potencial pero sin peligro. El largo nos puede llevar de dos a tres horas el finalizarlo.'),
	(38, 'A4 o C4', 'Artificial serio. Muchos emplazamientos seguidos que solo aguantan el peso de nuestro cuerpo (de seis a ocho). De 20 a 30 metros de caida potencial con peligro de chocar contra repisas y salientes. Finalizar el largo nos puede llevar mas de tres horas'),
	(39, 'A5 o C5', 'Artificial extremo. Mas de diez emplazamientos seguidos que solo aguantan el peso de nuestro cuerpo y no una caida. Practicamente podriamos descoser todo el largo en caso de caida. Es preciso comprobar cada seguro con mucha precisión. Finalizar un largo nos puede llevar mas de cuatro horas.'),
	(40, 'A6', 'Artificial extremo. Igual que el de A5 pero con la posibilidad de que la reunión no aguante el impacto de una caida. El riesgo de caida mortal es real.'),
	(41, 'gradoMock', 'Lorem impsun ');
/*!40000 ALTER TABLE `grado` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.rol
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `descripcion` varchar(250) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.rol: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT IGNORE INTO `rol` (`id`, `nombre`, `descripcion`) VALUES
	(1, 'Administrador', 'Administrador del sistema'),
	(2, 'Usuario', 'Usuario estandar');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.sector
CREATE TABLE IF NOT EXISTS `sector` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `id_zona` int(11) NOT NULL,
  `imagen` varchar(250) NOT NULL DEFAULT 'default_sector.jpg',
  `validado` bit(1) NOT NULL DEFAULT b'0',
  `id_usuario` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`,`id_zona`,`id_usuario`),
  KEY `fk_sector_zona1_idx` (`id_zona`),
  KEY `fk_sector_usuario` (`id_usuario`),
  CONSTRAINT `fk_sector_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sector_zona1` FOREIGN KEY (`id_zona`) REFERENCES `zona` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

<<<<<<< HEAD
-- Volcando datos para la tabla eskalada.sector: ~11 rows (aproximadamente)
=======
-- Volcando datos para la tabla eskalada.sector: ~10 rows (aproximadamente)
DELETE FROM `sector`;
>>>>>>> refs/remotes/origin/master-frontoffice-raul2
/*!40000 ALTER TABLE `sector` DISABLE KEYS */;
<<<<<<< HEAD
INSERT IGNORE INTO `sector` (`id`, `nombre`, `id_zona`, `imagen`, `validado`, `id_usuario`) VALUES
	(1, 'Alluitz24', 1, 'default_sector.jpg', 0, 4),
	(2, 'Tercer espolón', 1, 'default_sector.jpg', 1, 1),
	(3, 'Alluitz2', 1, 'default_sector.jpg', 0, 4),
	(4, 'Labargorri', 1, 'default_sector.jpg', 1, 1),
	(5, 'Urrestei', 1, 'default_sector.jpg', 1, 1),
	(6, 'Elosuko Harrobia', 2, 'default_sector.jpg', 1, 1),
	(7, 'Lauretazpe', 2, 'default_sector.jpg', 1, 1),
	(8, 'Ogoño', 3, 'default_sector.jpg', 1, 1),
	(9, 'Cara Sur2', 4, 'default_sector.jpg', 1, 1),
	(10, 'Cara Oeste', 4, 'default_sector.jpg', 1, 1),
	(12, 'La Ruta del Cares', 7, 'default_sector.jpg', 0, 4);
=======
INSERT INTO `sector` (`id`, `nombre`, `id_zona`, `imagen`, `validado`, `id_usuario`) VALUES
	(1, 'Primer espolón', 1, 'default_sector.jpg', b'0', 1),
	(2, 'Tercer espolón', 1, 'default_sector.jpg', b'0', 1),
	(3, 'Alluitz', 1, 'default_sector.jpg', b'0', 1),
	(4, 'Labargorri', 1, 'default_sector.jpg', b'0', 1),
	(5, 'Urrestei', 1, 'default_sector.jpg', b'0', 1),
	(6, 'Elosuko Harrobia', 2, 'default_sector.jpg', b'0', 1),
	(7, 'Lauretazpe', 2, 'default_sector.jpg', b'0', 1),
	(8, 'Ogoño', 3, 'default_sector.jpg', b'0', 1),
	(9, 'Cara Sur', 4, 'default_sector.jpg', b'0', 1),
	(10, 'Cara Oeste', 4, 'default_sector.jpg', b'0', 1);
>>>>>>> refs/remotes/origin/master-frontoffice-raul2
/*!40000 ALTER TABLE `sector` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.tipo_escalada
CREATE TABLE IF NOT EXISTS `tipo_escalada` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.tipo_escalada: ~11 rows (aproximadamente)
/*!40000 ALTER TABLE `tipo_escalada` DISABLE KEYS */;
INSERT IGNORE INTO `tipo_escalada` (`id`, `nombre`, `descripcion`) VALUES
	(1, 'Libre, solo integral o natural', 'En este tipo de escalada sólo se utilizan los pies y las manos para progresar, sin fijar ningún medio de seguridad para evitar accidentes. Se suelen usar pies de gato para una mayor fijación.'),
	(2, 'Clásica', 'Aquí el escalador va metiendo sus propios seguros en la pared para hacer una escalada más segura. Se pueden introducir los seguro en anclajes naturales (árboles, puentes de roca, puntas de roca, etc.) o en anclajes artificiales recuperables (fisureros, friends, clavos, nudos empotrados, etc.).'),
	(3, 'Alpina', 'Cuando la escalada clásica se realiza en una montaña de gran altura, conllevando riesgos de nevadas, avalanchas, falta de oxígeno, etc, y con el fin de alcanzar la cima.'),
	(4, 'Artificial', 'Se trata de una variante de la escalada clásica en la que se emplean los mismos elementos de seguridad pero en la que se emplean tanto para asegurarse como para progresar en la ascensión.'),
	(5, 'Dry-tooling', 'Se trata de una variedad de escalada alpina en la que, mediante los piolets y los crampones, se asciende una pared de roca sin nada de nieve.'),
	(6, 'Big wall, grandes paredes o tapias', 'Es aquella escalada en la que la ascensión, debido a su longitud, precisa de varios días, debiendo de dormir y comer en la pared. Se puede realizar tanto en clásica como en artificial.'),
	(7, 'En solitario', 'En esta escalada se asciende autoasegurado con una cuerda pero sin compañero.'),
	(8, 'Deportiva', 'Este tipo de escalada se caracteriza por que en la pared podemos encontrar anclajes fijos colocados estratégicamente para asegurar nuestros pasos. Podemos encontrar anclajes basados en sistemas mecánicos -de expansión- o químicos -resinas-. Por lo general, estas vías al equiparse, se limpian de maleza y de piedras sueltas o susceptibles de romperse, para ganar en la seguridad del escalador deportivo.'),
	(9, 'Bloque o búlder', 'Es una escalada de solo integral en la que el escalador nunca sube suficientemente lejos como para que una caída pueda suponerle una caida grave, estando ésta asegurada con una colchoneta (crash pad) que evite golpes o un compañero atento a la caída.'),
	(10, 'Psicobloc', 'Es como la escalada en bloque, pero cuando la pared es un acantilado y la caída se hace directamente en el agua. No se utiliza cuerda.'),
	(11, 'Urbana', 'Se realiza en cualquier estructura que se encuentre en la ciudad. Mucha afición en Holanda.');
/*!40000 ALTER TABLE `tipo_escalada` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL DEFAULT '0',
  `nombre` varchar(100) NOT NULL DEFAULT '0',
  `password` varchar(30) NOT NULL DEFAULT '0',
  `id_rol` int(11) NOT NULL DEFAULT '0',
  `validado` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 no validado, 1 validado',
  `token` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`,`nombre`),
  KEY `fk_usuario_rol` (`id_rol`),
  CONSTRAINT `fk_usuario_rol` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

<<<<<<< HEAD
-- Volcando datos para la tabla eskalada.usuario: ~5 rows (aproximadamente)
=======
-- Volcando datos para la tabla eskalada.usuario: ~10 rows (aproximadamente)
DELETE FROM `usuario`;
>>>>>>> refs/remotes/origin/master-frontoffice-raul2
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT IGNORE INTO `usuario` (`id`, `email`, `nombre`, `password`, `id_rol`, `validado`, `token`) VALUES
	(1, 'admin@admin.com', 'admin', 'admin', 1, 1, '16kluv6gn3h9mptj18f58ieis6'),
	(2, 'degar00@gmail.com', 'Degar', '456456', 2, 1, '827gb5c31hbg2rt08fkfvj4mai'),
<<<<<<< HEAD
	(3, 'juan@juan.juan', 'Juan', '123456', 2, 0, 'dgmtcq96sjjcevunh4c349j2kj'),
	(4, 'pepe@pepe.com', 'pepe', 'pepe', 2, 1, 'iv60sat58rcs1nbundsdfkgq63');
=======
	(5, 'ieltxuorue@gmail.com', 'ieltxu', '123123', 2, 1, 'l7757dpbejjrna663ef4l7f3li'),
	(6, 'javi70@gmail.com', 'Javi', '000000', 2, 1, 'v2n6m07as8j6g1fh3agmcmuf4l'),
	(7, 'jaguar.wolf@yahoo.es', 'Aritz', '140686', 2, 1, 'r0iqrhstgiri73sk03n75saurf'),
	(8, 'ander.rabadan.bilbao@gmail.com', 'ander', '123456', 2, 1, 'g3tg77kocbjef52f8and2sq8rq'),
	(9, 'unaiperea@gmail.com', 'Unai', 'aaaaaa', 2, 1, 'btagdd4pdu6ledhv0gr5q58ibq'),
	(10, 'laragonzalez.bm@gmail.com', 'Lara', '123456', 2, 1, 'n45di033k5221f4mkimuobral2'),
	(11, 'mikelalonsorojo@gmail.com', 'mikel', '1234561', 2, 1, 'j384vkl2tbhe7nq1l7vu3a4nhk'),
	(12, 'raulgf1992@gmail.com', 'Raul', '123123', 2, 1, '6p3qb9ea1edee22u7smtgqt30e');
>>>>>>> refs/remotes/origin/master-frontoffice-raul2
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.via
CREATE TABLE IF NOT EXISTS `via` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `longitud` int(11) NOT NULL DEFAULT '0' COMMENT 'Longitud de la via de escalada en metros',
  `descripcion` text,
  `id_grado` int(11) NOT NULL,
  `id_tipo_escalada` int(11) NOT NULL,
  `id_sector` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `validado` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`,`id_grado`,`id_tipo_escalada`,`id_sector`),
  KEY `fk_via_grado_idx` (`id_grado`),
  KEY `fk_via_tipo_escalada1_idx` (`id_tipo_escalada`),
  KEY `fk_via_sector1_idx` (`id_sector`),
  KEY `FK_via_usuario` (`id_usuario`),
  CONSTRAINT `fk_via_grado` FOREIGN KEY (`id_grado`) REFERENCES `grado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_via_sector1` FOREIGN KEY (`id_sector`) REFERENCES `sector` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_via_tipo_escalada1` FOREIGN KEY (`id_tipo_escalada`) REFERENCES `tipo_escalada` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_via_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.via: ~14 rows (aproximadamente)
/*!40000 ALTER TABLE `via` DISABLE KEYS */;
INSERT IGNORE INTO `via` (`id`, `nombre`, `longitud`, `descripcion`, `id_grado`, `id_tipo_escalada`, `id_sector`, `id_usuario`, `validado`) VALUES
	(1, 'Irentxo', 30, 'Mantenida', 10, 8, 1, 1, b'0'),
	(2, 'Bosque de los Inurios\r\n', 18, 'Vía con 5 movimientos muy duros', 12, 8, 1, 1, b'0'),
	(3, 'Normal', 120, 'Larga y bonita vía con pasos de todo tipo. Reequipada con químicos en su totalidad', 8, 8, 2, 1, b'0'),
	(4, 'La Cabra de Judas\r\n', 400, 'A1 de artificial. Roca buena en general. Material: Fisureros, cordinos, estribos', 11, 4, 3, 1, b'0'),
	(5, 'María chimenea', 140, 'Último largo bastante lavado, técnica y de coco', 9, 8, 4, 1, b'0'),
	(6, 'Arista de Urrestei', 300, 'Grado asequible para utilizar tu chatarra', 9, 2, 5, 1, b'0'),
	(7, 'Oroimen\r\n', 45, 'Equipada por Carmen Urkiola, Ricardo Garaigordobil y Manu Marcos\r\n', 10, 8, 6, 1, b'0'),
	(8, 'Amaituezina', 50, 'Reunión común con la 7', 10, 8, 6, 1, b'0'),
	(9, 'Abiadura handiko trena KK\r\n', 30, 'Excelente roca\r\n', 13, 8, 6, 1, b'0'),
	(10, 'Ezinezkoa\r\n', 30, 'Buena roca caliza\r\n', 19, 8, 6, 1, b'0'),
	(11, 'Gaviotas', 1300, 'Escalar a partir de Septiembre por nidificación de aves\r\n', 9, 8, 8, 1, b'0'),
	(12, 'Directa de los Martínez\r\n', 160, 'Material necesario: 8 cintas exprés, juego completo de friends desde el 0,3 de los camalot hasta el n2 (nº3 y empotradores opcionales) casco imprescindible\r\n', 8, 2, 9, 1, b'0'),
	(13, 'Orbayu\r\n', 500, 'La vía clásica más dificil del mundo\r\n', 28, 3, 10, 1, b'0'),
	(14, 'Murciana 78\r\n', 600, 'Dominar el 6b+ a vista. Pasos difíciles: A1\r\n', 21, 8, 10, 1, b'0');
/*!40000 ALTER TABLE `via` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.zona
CREATE TABLE IF NOT EXISTS `zona` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `id_usuario` int(11) NOT NULL DEFAULT '1',
  `validado` bit(1) NOT NULL DEFAULT b'0',
  `fecha_creado` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_modificado` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `FK_zona_usuario` (`id_usuario`),
  CONSTRAINT `FK_zona_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.zona: ~8 rows (aproximadamente)
/*!40000 ALTER TABLE `zona` DISABLE KEYS */;
<<<<<<< HEAD
INSERT IGNORE INTO `zona` (`id`, `nombre`, `id_usuario`, `validado`, `fecha_creado`, `fecha_modificado`) VALUES
	(1, 'Atxarte', 1, b'1', '2015-10-14 12:00:24', '2015-10-14 12:00:24'),
	(2, 'Untzillaitz Sur', 1, b'1', '2015-10-14 12:00:24', '2015-10-14 12:00:24'),
	(3, 'Cabo Ogoño', 1, b'1', '2015-10-14 12:00:24', '2015-10-14 12:00:24'),
	(4, 'Naranjo de Bulnes', 1, b'1', '2015-10-14 12:00:24', '2015-10-14 12:00:24'),
	(6, 'vacia', 1, b'1', '2015-10-14 12:00:24', '2015-10-14 12:00:24'),
	(7, 'Nueva', 1, b'1', '2015-10-14 12:00:24', '2015-10-14 12:00:24'),
	(9, 'gradoMock', 1, b'1', '2015-10-14 12:00:24', '2015-10-14 12:00:24');
=======
INSERT INTO `zona` (`id`, `nombre`, `id_usuario`, `validado`, `fecha_creado`, `fecha_modificado`) VALUES
	(1, 'Atxarte', 1, b'1', '2015-10-07 09:47:00', '2015-10-07 09:47:19'),
	(2, 'Untzillaitz Sur', 1, b'1', '2015-10-07 09:47:19', '2015-10-07 09:47:19'),
	(3, 'Cabo Ogoño', 1, b'1', '2015-10-07 09:47:19', '2015-10-07 09:47:19'),
	(4, 'Naranjo de Bulnes', 1, b'1', '2015-10-07 09:47:19', '2015-10-07 09:47:19'),
	(6, 'vacia', 1, b'0', '2015-10-07 09:47:19', '2015-10-07 09:47:19'),
	(7, 'Nueva', 1, b'1', '2015-10-07 09:47:19', '2015-10-07 09:47:19'),
	(9, 'gradoMock', 1, b'1', '2015-10-07 09:47:19', '2015-10-07 09:47:19');
>>>>>>> refs/remotes/origin/master-frontoffice-raul2
/*!40000 ALTER TABLE `zona` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
