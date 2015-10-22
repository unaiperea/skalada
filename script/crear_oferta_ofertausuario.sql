-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         5.6.12-log - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura de base de datos para eskalada



-- Volcando estructura para tabla eskalada.oferta
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.oferta: ~0 rows (aproximadamente)
DELETE FROM `oferta`;
/*!40000 ALTER TABLE `oferta` DISABLE KEYS */;
INSERT INTO `oferta` (`id`, `titulo`, `descripcion`, `precio`, `fecha_alta`, `fecha_baja`, `visible`, `zona_id`) VALUES
	(1, 'Noche de Hotel', 'Habitación doble', 100, '2015-10-20 11:27:21', '2015-11-20 11:27:22', 1, 1),
	(2, 'Clases de escalada', 'Clases particulares para principiantes', 250, '2015-10-20 11:27:50', '2015-12-20 11:27:51', 0, 3),
	(3, 'Materiales ', 'Materiales exclusivos', 50, '2015-10-20 11:28:41', '2015-11-20 11:28:42', 1, 1),
	(4, 'Transporte', 'Transporte a la zona de escalada', 20, '2015-10-20 11:29:32', '2015-12-20 11:29:34', 0, 3);
/*!40000 ALTER TABLE `oferta` ENABLE KEYS */;


-- Volcando estructura para tabla eskalada.ofertausuario
CREATE TABLE IF NOT EXISTS `ofertausuario` (
  `oferta_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `fecha_inscripcion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`oferta_id`,`usuario_id`),
  KEY `FK_ofertausuario_usuario` (`usuario_id`),
  CONSTRAINT `FK_ofertausuario_oferta` FOREIGN KEY (`oferta_id`) REFERENCES `oferta` (`id`),
  CONSTRAINT `FK_ofertausuario_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Volcando datos para la tabla eskalada.ofertausuario: ~0 rows (aproximadamente)
DELETE FROM `ofertausuario`;
/*!40000 ALTER TABLE `ofertausuario` DISABLE KEYS */;
INSERT INTO `ofertausuario` (`oferta_id`, `usuario_id`, `fecha_inscripcion`) VALUES
	(1, 1, '2015-10-20 11:30:07'),
	(1, 3, '2015-10-20 11:30:38'),
	(2, 1, '2015-10-20 11:31:45'),
	(3, 3, '2015-10-20 11:31:20');
/*!40000 ALTER TABLE `ofertausuario` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
