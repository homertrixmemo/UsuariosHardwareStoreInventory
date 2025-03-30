<%-- 
    Document   : editUsuario
    Created on : 15 mar 2025, 18:14:02
    Author     : Juan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Usuario</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
            <!-- Contenedor Principal -->
            <div class="card-form">
                <div class="form-header">
                    <h3>✏️ Editar Usuario</h3>
                </div>
                <div class="form-body">
                    <form action="${pageContext.request.contextPath}/UsuarioController?action=actualizar" method="post">
                        <input type="hidden" name="id" value="${usuario.idUsuario}">

                        <!-- Campo Nombre -->
                        <div class="form-group">
                            <label for="nombre">Nombre:</label>
                            <input type="text" id="nombre" name="nombre" class="input-field"
                                   value="${usuario.nombreUsuario}" maxlength="50"
                                   placeholder="Ingrese el nombre" required>
                        </div>

                        <!-- Campo Email -->
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" class="input-field"
                                   value="${usuario.email}" placeholder="Ingrese el email" required>
                        </div>

                        <!-- Selector de Perfiles -->
                        <div class="form-group">
                            <label for="perfil_id">Perfil:</label>
                            <select id="perfil_id" name="perfil_id" class="select-field" required>
                                <c:forEach var="perfil" items="${listaPerfiles}">
                                    <option value="${perfil.idPerfil}" ${perfil.idPerfil == usuario.idPerfil ? 'selected' : ''}>
                                        ${perfil.nombrePerfil}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Selector de Roles -->
                        <div class="form-group">
                            <label for="id_rol">Rol:</label>
                            <select id="id_rol" name="id_rol" class="select-field" required>
                                <c:forEach var="rol" items="${listaRoles}">
                                    <option value="${rol.idRol}" ${rol.idRol == usuario.idRol ? 'selected' : ''}>
                                        ${rol.nombreRol}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Botones de Acción -->
                        <div class="button-group">
                            <button type="submit" class="btn-save" aria-label="Actualizar usuario">💾 Guardar Cambios</button>
                            <a href="${pageContext.request.contextPath}/UsuarioController?action=listar"
                               class="btn-cancel" aria-label="Cancelar">❌ Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
