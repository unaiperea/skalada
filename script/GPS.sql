ALTER TABLE `sector`
	ADD COLUMN `longitud` DOUBLE NULL DEFAULT '0.0' AFTER `id_usuario`;
ALTER TABLE `sector`
	ADD COLUMN `latitud` DOUBLE NULL DEFAULT '0.0' AFTER `longitud`;

ALTER TABLE `zona`
	ADD COLUMN `longitud` DOUBLE NULL DEFAULT '0.0' AFTER `fecha_modificado`;
ALTER TABLE `zona`
	ADD COLUMN `latitud` DOUBLE NULL DEFAULT '0.0' AFTER `longitud`;
	
