<%-- 
    Document   : usuariosAsociados
    Created on : 27 mar 2025, 0:59:08
    Author     : Juan
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Rol" %>
<%@ page import="Model.Usuario" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Usuarios Asociados</title>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
    </head>
    <body>

        <div class="contenedor">
            <div class="card-form">
                <h2 style="color: #E67E22; text-align: center;">⚠ No se puede eliminar este usuario</h2>
                <p class="perfil-alert">
                    El usuario tiene roles asociados. Debe reasignarlos o eliminarlos antes de continuar.
                </p>

                <%
                    List<Rol> rolesAsociados = (List<Rol>) request.getAttribute("rolesAsociados");
                %>

                <!-- Mostrar roles asociados -->
                <% if (rolesAsociados != null && !rolesAsociados.isEmpty()) { %>
                <h3 class="perfil-title">Roles Asociados</h3>
                <div class="tabla-contenedor">
                    <table class="tabla">
                        <thead>
                            <tr>
                                <th>ID Rol</th>
                                <th>Nombre Rol</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Rol rol : rolesAsociados) {%>
                            <tr>
                                <td><%= rol.getIdRol()%></td>
                                <td><%= rol.getNombreRol()%></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                <% } %>

                <!-- Mensaje si no hay asociaciones -->
                <% if (rolesAsociados == null || rolesAsociados.isEmpty()) { %>
                <p class="perfil-alert-warning" style="color: red; text-align: center;">No hay roles asociados a este usuario.</p>
                <% }%>

                <!-- Botones de acción -->
                <div class="button-group">
                    <a href="${pageContext.request.contextPath}/RolController?action=listar&idUsuario=${param.idUsuario}" 
                       class="boton boton-primario">Gestionar Roles</a>
                    <a href="${pageContext.request.contextPath}/UsuarioController?action=listar" 
                       class="boton boton-secundario">Volver a Usuarios</a>
                </div>

            </div>
        </div>

    </body>
</html>
