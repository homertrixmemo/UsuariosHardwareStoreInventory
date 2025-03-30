<%-- 
    Document   : listarRoles
    Created on : 14 mar 2025, 23:10:43
    Author     : Juan
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Roles</title>
        <link rel="stylesheet" href="css/style.css"> <!-- Archivo de estilos centralizado -->
    </head>
    <body class="pagina">
        <div class="contenedor">
            <h2>📋 Lista de Roles</h2>

            <!-- Barra de Acciones -->
            <div class="barra-acciones">
                <a href="PerfilController?action=listar" class="boton boton-secundario">Regresar</a>
                <a href="RolController?action=nuevo" class="boton boton-primario">➕ Agregar Nuevo Rol</a>
            </div>

            <!-- Tabla de Roles -->
            <c:if test="${not empty roles}">
                <div class="tabla-contenedor">
                    <table class="tabla">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Descripción</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="rol" items="${roles}">
                                <tr>
                                    <td>${rol.idRol}</td>
                                    <td>${rol.nombreRol}</td>
                                    <td>${rol.descripcion}</td>
                                    <td>
                                        <a href="RolController?action=editar&id=${rol.idRol}" class="boton boton-editar">✏ Editar</a>
                                        <a href="views/confirmDeleteRol.jsp?id=${rol.idRol}" class="boton boton-eliminar">🗑 Eliminar</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <!-- Mensaje si no hay Roles -->
            <c:if test="${empty roles}">
                <p class="perfil-alert-warning">⚠ No hay roles registrados.</p>
            </c:if>
        </div>
    </body>
</html>
