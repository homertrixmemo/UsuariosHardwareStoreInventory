CREATE DATABASE IF NOT EXISTS hardware_store;
USE hardware_store;

--
-- Base de datos: `hardware_store`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `tipo_documento` varchar(45) NOT NULL,
  `numero_documento` varchar(15) NOT NULL,
  `nombres` varchar(50) NOT NULL,
  `primer_apellido` varchar(50) NOT NULL,
  `segundo_apellido` varchar(15) DEFAULT NULL,
  `telefono_movil` varchar(10) NOT NULL,
  `direccion_residencia` varchar(45) NOT NULL,
  `contacto_emergencia` varchar(45) DEFAULT NULL,
  `telefono_contacto` varchar(10) NOT NULL,
  `perfil_id_perfil` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `tipo_documento`, `numero_documento`, `nombres`, `primer_apellido`, `segundo_apellido`, `telefono_movil`, `direccion_residencia`, `contacto_emergencia`, `telefono_contacto`, `perfil_id_perfil`) VALUES
(1, 'CC', '123456789', 'Pedro', 'Gómez', 'López', '3001234567', 'Calle 1 #10-20', 'Juan Pérez', '3109876543', 2),
(2, 'TI', '987654321', 'Ana', 'Martínez', 'Ruiz', '3012345678', 'Carrera 5 #15-30', 'María Torres', '3208765432', 3),
(3, 'CC', '456789123', 'Luis', 'Fernández', NULL, '3023456789', 'Avenida 3 #20-40', 'Carlos Mendoza', '3307654321', 4),
(4, 'CE', '321654987', 'María', 'López', 'González', '3034567890', 'Calle 7 #25-50', 'Andrés Castro', '3406543210', 5),
(5, 'CC', '789123456', 'Jorge', 'Ramírez', NULL, '3045678901', 'Carrera 9 #30-60', 'Sofía Duarte', '3505432109', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil`
--

CREATE TABLE `perfil` (
  `id_perfil` int(11) NOT NULL,
  `nombre_perfil` varchar(50) NOT NULL,
  `descripcion` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `perfil`
--

INSERT INTO `perfil` (`id_perfil`, `nombre_perfil`, `descripcion`) VALUES
(1, 'Administrador', 'Acceso total al sistema'),
(2, 'Vendedor', 'Acceso a ventas y gestión de clientes'),
(3, 'Almacén', 'Gestión de inventario y productos'),
(4, 'Contador', 'Acceso a reportes financieros'),
(5, 'Soporte', 'Acceso a soporte técnico');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id_rol` int(11) NOT NULL,
  `nombre_rol` varchar(50) NOT NULL,
  `descripcion` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id_rol`, `nombre_rol`, `descripcion`) VALUES
(1, 'Gerente', 'Supervisa toda la operación'),
(2, 'Cajero', 'Manejo de caja y pagos'),
(3, 'Supervisor', 'Supervisión de empleados'),
(4, 'Técnico', 'Soporte y mantenimiento'),
(5, 'Recepcionista', 'Atención a clientes');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `perfil_id_perfil` int(11) DEFAULT NULL,
  `rol_id_rol` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombre_usuario`, `email`, `password`, `perfil_id_perfil`, `rol_id_rol`) VALUES
(1, 'admin', 'admin@example.com', 'hashed_password1', 1, 1),
(2, 'juanp', 'juan@example.com', 'hashed_password2', 2, 2),
(3, 'mariag', 'maria@example.com', 'hashed_password3', 3, 3),
(4, 'luisr', 'luis@example.com', 'hashed_password4', 4, 4),
(5, 'anaq', 'ana@example.com', 'hashed_password5', 5, 5);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD KEY `perfil_id_perfil` (`perfil_id_perfil`);

--
-- Indices de la tabla `perfil`
--
ALTER TABLE `perfil`
  ADD PRIMARY KEY (`id_perfil`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `perfil_id_perfil` (`perfil_id_perfil`),
  ADD KEY `rol_id_rol` (`rol_id_rol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `perfil`
--
ALTER TABLE `perfil`
  MODIFY `id_perfil` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id_rol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`perfil_id_perfil`) REFERENCES `perfil` (`id_perfil`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`perfil_id_perfil`) REFERENCES `perfil` (`id_perfil`),
  ADD CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`rol_id_rol`) REFERENCES `rol` (`id_rol`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
