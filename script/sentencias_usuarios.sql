-- GETONE
SELECT u.`id`, u.`email`, u.`nombre`, u.`password`, u.`validado`, u.`id_rol`, r.`nombre` AS `nombre_rol`
FROM `usuario` AS u
INNER JOIN `rol` as r
ON (u.`id_rol` = r.`id`) 
WHERE u.`id`= ?;

-- GETALL
SELECT u.`id`, u.`email`, u.`nombre`, u.`password`, u.`validado`, u.`id_rol`, r.`nombre` AS `nombre_rol`
FROM `usuario` AS u
INNER JOIN `rol` as r
ON (u.`id_rol` = r.`id`); 

-- DELETE
DELETE FROM `usuario` WHERE `id`=?;

-- UPDATE
UPDATE `usuario` SET `email`=?, `nombre`=?, `password`=?, `validado`=? WHERE `id`= ?;

-- INSERT
INSERT INTO `usuario` (`email`, `nombre`, `password`, `id_rol`) VALUES (?, ?, ?, ?);