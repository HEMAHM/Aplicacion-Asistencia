-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 27-12-2024 a las 20:46:19
-- Versión del servidor: 8.0.30
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `asistencia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reg_asistencia`
--

CREATE TABLE `reg_asistencia` (
  `id` int NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellidop` varchar(255) NOT NULL,
  `apellidom` varchar(255) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `mensaje` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `reg_asistencia`
--

INSERT INTO `reg_asistencia` (`id`, `nombre`, `apellidop`, `apellidom`, `fecha`, `hora`, `mensaje`) VALUES
(1, 'Jose Luis', 'Hernandez', 'Martinez', '2024-12-27', '12:57:02', 'Sí asistió'),
(2, 'Monse', 'Hernandez', 'Martinez', '2024-12-27', '12:59:04', 'Sí asistió'),
(3, 'Jose Luis', 'Hernandez', 'Martinez', '2024-12-27', '13:48:22', 'Sí asistió');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellidop` varchar(255) NOT NULL,
  `apellidom` varchar(255) NOT NULL,
  `usuario` varchar(255) NOT NULL,
  `contrasena` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `apellidop`, `apellidom`, `usuario`, `contrasena`) VALUES
(1, 'Jose Luis', 'Hernandez', 'Martinez', 'hema', '123'),
(4, 'Monse', 'Hernandez', 'Martinez', 'moon', '123456789'),
(5, 'Artoria', 'Claymore', 'Claymore', 'art', '321');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `reg_asistencia`
--
ALTER TABLE `reg_asistencia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `reg_asistencia`
--
ALTER TABLE `reg_asistencia`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
