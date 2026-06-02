DROP DATABASE IF EXISTS bdtareas;
CREATE DATABASE bdtareas;
USE bdtareas;

CREATE TABLE `categorias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `icono`varchar(45) NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
);

INSERT INTO `categorias` 
VALUES (1,'Jardinería','plant.png','Mantenimiento del jardín; cortar el césped, regar...'),(2,'Deporte','deporte.png','Actividad física y el deporte; ir al gym, jugar un partido de futbol'),(3,'Tareas del Hogar','hogar.png','Hogar; hacer la camas, lavar la ropa'),(4,'Escuela','escuela.png','Exàmenes, entrega de actividades, proyectos...'),(5,'Ocio','ocio.png','Quedar con amigos, comidas, cenas, fiestas');

CREATE TABLE `tareas` (
  `codigo` int NOT NULL AUTO_INCREMENT,
  `usuario` varchar(30) NOT NULL,
  `descripcion` varchar(40) NOT NULL,  
  `fecha_vencimiento` datetime NOT NULL,
  `prioridad` enum('ALTA','MEDIA','BAJA') NOT NULL DEFAULT 'MEDIA',
  `realizada` tinyint(1) NOT NULL DEFAULT '0',
  `categoria_id` int DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`codigo`),
  KEY `categoria_id_idx` (`categoria_id`),
  CONSTRAINT `categoria_id` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`)
);

INSERT INTO `tareas`(codigo, usuario, descripcion, fecha_vencimiento, prioridad, realizada, categoria_id) 
VALUES (1,'Juan','Partido de baloncesto','2026-05-12 20:00:00','ALTA',0,2),(2,'Juan','Estudiar Programación', '2026-05-18 20:00:00','BAJA',0,4),(3,'Batoi','Hacer la comida','2026-05-12 20:00:00','ALTA',1,3),(4,'Elena','Podar los setos del jardín','2026-05-10 07:52:37','MEDIA',0,1);

