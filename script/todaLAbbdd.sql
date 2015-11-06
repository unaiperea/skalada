-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.6.17 - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para eskalada
DROP DATABASE IF EXISTS `eskalada`;
CREATE DATABASE IF NOT EXISTS `eskalada` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `eskalada`;


-- Volcando estructura para tabla eskalada.grado
DROP TABLE IF EXISTS `grado`;
CREATE TABLE IF NOT EXISTS `grado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(10) NOT NULL COMMENT 'Grado de dificultad de la via de escalada, por ejemplo: ',
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.grado: ~40 rows (aproximadamente)
DELETE FROM `grado`;
/*!40000 ALTER TABLE `grado` DISABLE KEYS */;
INSERT INTO `grado` (`id`, `nombre`, `descripcion`) VALUES
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
	(40, 'A6', 'Artificial extremo. Igual que el de A5 pero con la posibilidad de que la reunión no aguante el impacto de una caida. El riesgo de caida mortal es real.');
/*!40000 ALTER TABLE `grado` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.oferta
DROP TABLE IF EXISTS `oferta`;
CREATE TABLE IF NOT EXISTS `oferta` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'identificador de las ofertas',
  `titulo` varchar(255) NOT NULL COMMENT 'titulo de las ofertas',
  `descripcion` varchar(255) DEFAULT NULL COMMENT 'descripcion de las ofertas',
  `precio` float NOT NULL COMMENT 'precio de las ofertas',
  `fecha_alta` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha de inicio de la oferta',
  `fecha_baja` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'fecha de fin de oferta',
  `visible` tinyint(4) DEFAULT NULL COMMENT 'si la oferta es visible o no',
  `zona_id` int(11) DEFAULT NULL COMMENT 'clave foranea de la zona',
  PRIMARY KEY (`id`),
  KEY `FK_oferta_zona` (`zona_id`),
  CONSTRAINT `FK_oferta_zona` FOREIGN KEY (`zona_id`) REFERENCES `zona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.oferta: ~6 rows (aproximadamente)
DELETE FROM `oferta`;
/*!40000 ALTER TABLE `oferta` DISABLE KEYS */;
INSERT INTO `oferta` (`id`, `titulo`, `descripcion`, `precio`, `fecha_alta`, `fecha_baja`, `visible`, `zona_id`) VALUES
	(1, 'Noche de Hotel', 'Habitación doble', 100, '2015-10-23 00:00:00', '2015-10-31 00:00:00', 0, 1),
	(2, 'Clases de escalada', 'Clases particulares para principiantes', 250, '2015-10-20 11:27:50', '2015-12-20 11:27:51', 0, 3),
	(3, 'Materiales ', 'Materiales exclusivos', 50, '2015-10-20 11:28:41', '2015-11-20 11:28:42', 1, 1),
	(4, 'Transporte', 'Transporte a la zona de escalada', 20, '2015-10-20 11:29:32', '2015-12-20 11:29:34', 0, 3),
	(6, 'Fin de semana', 'Fin de semana en Asturias escalando sin parar', 100, '2015-11-01 00:00:00', '2015-12-31 00:00:00', 0, 4),
	(8, 'Prueba', 'null', 0, NULL, NULL, 0, 1);
/*!40000 ALTER TABLE `oferta` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.ofertausuario
DROP TABLE IF EXISTS `ofertausuario`;
CREATE TABLE IF NOT EXISTS `ofertausuario` (
  `oferta_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `fecha_inscripcion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`oferta_id`,`usuario_id`),
  KEY `FK_ofertausuario_usuario` (`usuario_id`),
  CONSTRAINT `FK_ofertausuario_oferta` FOREIGN KEY (`oferta_id`) REFERENCES `oferta` (`id`),
  CONSTRAINT `FK_ofertausuario_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.ofertausuario: ~4 rows (aproximadamente)
DELETE FROM `ofertausuario`;
/*!40000 ALTER TABLE `ofertausuario` DISABLE KEYS */;
INSERT INTO `ofertausuario` (`oferta_id`, `usuario_id`, `fecha_inscripcion`) VALUES
	(1, 3, '2015-10-20 11:30:38'),
	(2, 1, '2015-10-20 11:31:45'),
	(3, 6, '2015-10-22 11:55:40'),
	(3, 7, '2015-10-23 11:23:32');
/*!40000 ALTER TABLE `ofertausuario` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.rol
DROP TABLE IF EXISTS `rol`;
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT '0',
  `descripcion` varchar(250) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.rol: ~3 rows (aproximadamente)
DELETE FROM `rol`;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` (`id`, `nombre`, `descripcion`) VALUES
	(1, 'Administrador', 'Administrador del sistema'),
	(2, 'Usuario', 'Usuario estandar'),
	(4, 'Premium', 'Usuario de pago');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.sector
DROP TABLE IF EXISTS `sector`;
CREATE TABLE IF NOT EXISTS `sector` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `id_zona` int(11) NOT NULL,
  `imagen` varchar(250) NOT NULL DEFAULT 'default_sector.jpg',
  `validado` tinyint(1) NOT NULL DEFAULT '0',
  `id_usuario` int(11) NOT NULL DEFAULT '1',
  `longitud` double DEFAULT '0',
  `latitud` double DEFAULT '0',
  PRIMARY KEY (`id`,`id_zona`),
  KEY `fk_sector_zona1_idx` (`id_zona`),
  KEY `FK_sector_usuario` (`id_usuario`),
  CONSTRAINT `FK_sector_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `fk_sector_zona` FOREIGN KEY (`id_zona`) REFERENCES `zona` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.sector: ~12 rows (aproximadamente)
DELETE FROM `sector`;
/*!40000 ALTER TABLE `sector` DISABLE KEYS */;
INSERT INTO `sector` (`id`, `nombre`, `id_zona`, `imagen`, `validado`, `id_usuario`, `longitud`, `latitud`) VALUES
	(1, 'Primer espolón', 1, 'primer_espolon.jpg', 1, 1, -2.6350978437744743, 43.126893292104526),
	(2, 'Tercer espolón', 1, 'tercer_espolon.jpg', 1, 1, -2.6366427961670524, 43.12714386277939),
	(3, 'Alluitz', 1, 'alluitz_atxarte.jpg', 1, 2, -2.6450542036377556, 43.1276450010498),
	(4, 'Labargorri', 1, 'labalgorri.jpg', 1, 1, -2.635784489282287, 43.12482604487296),
	(5, 'Urrestei', 1, 'urrestei.jpg', 1, 1, -2.642650944360412, 43.1223828444408),
	(6, 'Elosuko Harrobia', 2, 'elosuko-harrobia.jpg', 1, 1, -2.5904564720644885, 43.085402970778496),
	(7, 'Lauretazpe', 2, 'laureztape.jpg', 1, 1, -2.595262990619176, 43.086092504866095),
	(8, 'Ogoño', 3, 'ogono.jpg', 1, 1, -2.646170002587951, 43.41543119750951),
	(9, 'Cara Sur', 4, 'cara_sur.jpg', 1, 1, -4.816747321730759, 43.199576520897466),
	(10, 'Cara Oeste', 4, 'cara_oeste.jpg', 1, 1, -4.819322242385056, 43.20157867407659),
	(12, 'PruebaDegar', 1, 'perejil.jpg', 1, 2, 0, 0),
	(16, 'Guggenheim', 7, 'guggenheim.jpg', 1, 6, -2.9340997759186394, 43.26887245682882);
/*!40000 ALTER TABLE `sector` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.tipo_escalada
DROP TABLE IF EXISTS `tipo_escalada`;
CREATE TABLE IF NOT EXISTS `tipo_escalada` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.tipo_escalada: ~11 rows (aproximadamente)
DELETE FROM `tipo_escalada`;
/*!40000 ALTER TABLE `tipo_escalada` DISABLE KEYS */;
INSERT INTO `tipo_escalada` (`id`, `nombre`, `descripcion`) VALUES
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
DROP TABLE IF EXISTS `usuario`;
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.usuario: ~7 rows (aproximadamente)
DELETE FROM `usuario`;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `email`, `nombre`, `password`, `id_rol`, `validado`, `token`) VALUES
	(1, 'admin@admin.com', 'admin', 'admin', 1, 1, 'rmd461ebm6p2f4ks0680n7ob3l'),
	(2, 'degar00@gmail.com', 'Degar', '456456', 2, 1, '827gb5c31hbg2rt08fkfvj4mai'),
	(3, 'juan@juan.juan', 'Juan', '123456', 2, 1, '1l7ah3bb9bc5glqq4eba1umjmn'),
	(4, 'user@user.com', 'user', 'user', 2, 1, ''),
	(6, 'ieltxuorue@gmail.com', 'ieltxu', '123456', 2, 1, '8d6lsnmi5sqreq7fhuu1pd01h7'),
	(7, 'javi70@gmail.com', 'Javi', '000000', 2, 1, 'jt9dm6q1e971v5006dualdfd6m'),
	(8, 'jaguar.wolf@yahoo.es', 'Aritz', 'aritz', 1, 1, 'gfo9m2kmilcj0l6s44ev6aopp1');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.via
DROP TABLE IF EXISTS `via`;
CREATE TABLE IF NOT EXISTS `via` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `longitud` int(11) NOT NULL DEFAULT '0' COMMENT 'Longitud de la via de escalada en metros',
  `descripcion` text,
  `id_grado` int(11) NOT NULL,
  `id_tipo_escalada` int(11) NOT NULL,
  `id_sector` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `validado` bit(1) NOT NULL DEFAULT b'0' COMMENT '0: Sin validar || 1: Validado',
  PRIMARY KEY (`id`,`id_grado`,`id_tipo_escalada`,`id_sector`),
  KEY `fk_via_grado_idx` (`id_grado`),
  KEY `fk_via_tipo_escalada1_idx` (`id_tipo_escalada`),
  KEY `fk_via_sector1_idx` (`id_sector`),
  KEY `fk_via_usuario` (`id_usuario`),
  CONSTRAINT `fk_via_grado` FOREIGN KEY (`id_grado`) REFERENCES `grado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_via_sector1` FOREIGN KEY (`id_sector`) REFERENCES `sector` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_via_tipo_escalada1` FOREIGN KEY (`id_tipo_escalada`) REFERENCES `tipo_escalada` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_via_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.via: ~15 rows (aproximadamente)
DELETE FROM `via`;
/*!40000 ALTER TABLE `via` DISABLE KEYS */;
INSERT INTO `via` (`id`, `nombre`, `longitud`, `descripcion`, `id_grado`, `id_tipo_escalada`, `id_sector`, `id_usuario`, `validado`) VALUES
	(1, 'Irentxo', 30, 'Mantenida', 10, 8, 1, 1, b'0'),
	(2, 'Bosque de los Inurios\r\n', 18, 'Vía con 5 movimientos muy duros', 12, 8, 1, 1, b'0'),
	(3, 'Normal', 120, 'Larga y bonita vía con pasos de todo tipo. Reequipada con químicos en su totalidad', 8, 8, 2, 1, b'0'),
	(4, 'La Cabra de Judas\r\n', 400, 'A1 de artificial. Roca buena en general. Material: Fisureros, cordinos, estribos', 11, 4, 3, 1, b'0'),
	(5, 'María chimenea', 140, 'Último largo bastante lavado, técnica y de coco', 9, 8, 4, 3, b'0'),
	(6, 'Arista de Urrestei', 300, 'Grado asequible para utilizar tu chatarra', 9, 2, 5, 1, b'0'),
	(7, 'Oroimen\r\n', 45, 'Equipada por Carmen Urkiola, Ricardo Garaigordobil y Manu Marcos\r\n', 10, 8, 6, 1, b'0'),
	(8, 'Amaituezina', 50, 'Reunión común con la 7', 10, 8, 6, 2, b'1'),
	(9, 'Abiadura handiko trena KK\r\n', 30, 'Excelente roca\r\n', 13, 8, 6, 1, b'0'),
	(10, 'Ezinezkoa\r\n', 30, 'Buena roca caliza\r\n', 19, 8, 6, 1, b'0'),
	(11, 'Gaviotas', 1300, 'Escalar a partir de Septiembre por nidificación de aves\r\n', 9, 8, 8, 4, b'0'),
	(12, 'Directa de los Martínez\r\n', 160, 'Material necesario: 8 cintas exprés, juego completo de friends desde el 0,3 de los camalot hasta el n2 (nº3 y empotradores opcionales) casco imprescindible\r\n', 8, 2, 9, 1, b'0'),
	(13, 'Orbayu\r\n', 500, 'La vía clásica más dificil del mundo\r\n', 28, 3, 10, 1, b'0'),
	(14, 'Murciana 78\r\n', 600, 'Dominar el 6b+ a vista. Pasos difíciles: A1\r\n', 21, 8, 10, 1, b'0'),
	(18, 'Puppy', 2000, 'Subida al perro', 31, 11, 16, 6, b'1');
/*!40000 ALTER TABLE `via` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.zona
DROP TABLE IF EXISTS `zona`;
CREATE TABLE IF NOT EXISTS `zona` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `id_usuario` int(11) NOT NULL DEFAULT '1',
  `validado` bit(1) NOT NULL DEFAULT b'0',
  `fecha_creado` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_modificado` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `longitud` double DEFAULT '0',
  `latitud` double DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `FK_zona_usuario` (`id_usuario`),
  CONSTRAINT `FK_zona_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.zona: ~5 rows (aproximadamente)
DELETE FROM `zona`;
/*!40000 ALTER TABLE `zona` DISABLE KEYS */;
INSERT INTO `zona` (`id`, `nombre`, `id_usuario`, `validado`, `fecha_creado`, `fecha_modificado`, `longitud`, `latitud`) VALUES
	(1, 'Atxarte', 1, b'1', '2015-10-07 09:47:00', '2015-10-20 13:28:31', -2.6411918226563103, 43.127707643044936),
	(2, 'Untzillaitz Sur', 1, b'1', '2015-10-07 09:47:19', '2015-10-23 08:39:43', -2.5914864403262072, 43.087471549756664),
	(3, 'Cabo Ogoño', 1, b'1', '2015-10-07 09:47:19', '2015-10-20 13:24:49', -2.643165928491271, 43.41131618744928),
	(4, 'Naranjo de Bulnes', 1, b'1', '2015-10-07 09:47:19', '2015-10-23 08:41:25', -4.815288200026657, 43.20264229122966),
	(7, 'Bilbo', 6, b'1', '2015-10-21 12:00:33', '2015-10-22 13:05:40', -2.9358479367251675, 43.263801835611346);
/*!40000 ALTER TABLE `zona` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
