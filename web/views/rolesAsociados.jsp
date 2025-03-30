<%-- 
    Document   : usuariosAsociadosRol
    Created on : 20 mar 2025, 20:30:57
    Author     : Juan
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Usuario" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Roles Asociados</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>

        <div class="contenedor">
            <div class="card-form">
                <h2 style="color: #E67E22; text-align: center;">⚠ No se puede eliminar este rol</h2>
                <p class="rol-alert">
                    Hay usuarios asociados a este rol. Debe reasignarlos o eliminarlos antes de continuar.
                </p>

                <% List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("listaUsuarios"); %>

                <% if (listaUsuarios != null && !listaUsuarios.isEmpty()) { %>
                <h3 class="rol-title">Usuarios Asociados</h3>
                <div class="tabla-contenedor">
                    <table class="tabla">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Correo</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Usuario usuario : listaUsuarios) {%>
                            <tr>
                                <td><%= usuario.getIdUsuario()%></td>
                                <td><%= usuario.getNombreUsuario()%></td>
                                <td><%= usuario.getEmail()%></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
                <% } else { %>
                <p class="rol-alert-warning" style="color: red; text-align: center;">No hay usuarios asociados a este rol.</p>
                <% }%>

                <div class="button-group">
                    <a href="${pageContext.request.contextPath}/UsuarioController?action=listar&idRol=${idRol}" 
                       class="boton boton-primario">Gestionar Usuarios</a>
                    <a href="${pageContext.request.contextPath}/RolController?action=listar" 
                       class="boton boton-terciario">Volver a Roles</a>
                </div>
            </div>
        </div>

    </body>
</html>
