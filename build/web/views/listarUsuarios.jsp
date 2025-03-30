<%-- 
    Document   : listarUsuarios
    Created on : 15 mar 2025, 18:12:14
    Author     : Juan
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="Model.Usuario" %>

<%
    List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("usuarios");
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Lista de Usuarios</title>
        <link rel="stylesheet" href="css/style.css"> <!-- Archivo de estilos centralizado -->
    </head>
    <body>
        <div class="contenedor">
            <h2 class="titulo">Lista de Usuarios</h2>

            <!-- Barra de acciones -->
            <div class="barra-acciones">
                <a href="index.jsp" class="boton boton-secundario">&#8592; Regresar</a>
                <a href="<%= request.getContextPath()%>/UsuarioController?action=nuevo" class="boton boton-primario">➕ Agregar Usuario</a>
            </div>

            <!-- Contenedor de la tabla -->
            <div class="tabla-contenedor">
                <table class="tabla">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Perfil</th>
                            <th>Rol</th> <!-- Nueva columna -->
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (listaUsuarios != null && !listaUsuarios.isEmpty()) { %>
                        <% for (Usuario usuario : listaUsuarios) {%>
                        <tr>
                            <td><%= usuario.getIdUsuario()%></td>
                            <td><%= usuario.getNombreUsuario()%></td>
                            <td><%= usuario.getEmail()%></td>
                            <td><%= usuario.getNombrePerfil()%></td>
                            <td><%= usuario.getNombreRol() != null ? usuario.getNombreRol() : "Sin rol"%></td> <!-- Manejo de rol -->
                            <td class="acciones">
                                <a href="UsuarioController?action=editar&id=<%= usuario.getIdUsuario()%>"class="boton boton-editar" title="Editar Usuario">✏ Editar</a>
                                <a href="views/confirmDeleteUsuario.jsp?id=<%= usuario.getIdUsuario()%>" 
                                   class="boton boton-eliminar" title="Eliminar Usuario" onclick="return confirm('¿Estás seguro de eliminar este usuario?');">🗑 Eliminar</a>   
                            </td>
                        </tr>
                        <% } %>
                        <% } else { %>
                        <tr>
                            <td colspan="6" class="mensaje">No hay usuarios registrados.</td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
