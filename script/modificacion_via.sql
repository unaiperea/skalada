ALTER TABLE `via`
ADD COLUMN `id_usuario` INT(11) NOT NULL AFTER `id_sector`;
	
ALTER TABLE `via`
ADD COLUMN `validado` BIT(1) NOT NULL DEFAULT b'0' COMMENT '0: Sin validar || 1: Validado' AFTER `id_usuario`;

UPDATE via SET id_usuario = 1;

ALTER TABLE `via`
ADD CONSTRAINT `fk_via_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`);