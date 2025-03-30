<%-- 
    Document   : listar
    Created on : 14 mar 2025, 19:03:25
    Author     : Juan
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Perfil" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Perfiles</title>
        <link rel="stylesheet" href="css/style.css"> <!-- Archivo de estilos centralizado -->
    </head>
    <body class="pagina">
        <div class="contenedor">
            <h2 class="titulo">Lista de Perfiles</h2>

            <% if (session.getAttribute("errorMensaje") != null) {%>
            <div style="color: red; font-weight: bold;"> <%= session.getAttribute("errorMensaje")%> </div>
            <% session.removeAttribute("errorMensaje"); %>
            <% } %>


            <!-- Barra de acciones -->
            <div class="barra-acciones">
                <a href="index.jsp" class="boton boton-primario">Regresar</a>
                <a href="PerfilController?action=nuevo" class="boton boton-terciario">Agregar Perfil</a>
                <a href="RolController?action=listar" class="boton boton-secundario">Ver Roles</a>
            </div>

            <!-- Contenedor de la tabla -->
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
                        <%
                            List<Perfil> perfiles = (List<Perfil>) request.getAttribute("perfiles");
                            if (perfiles != null && !perfiles.isEmpty()) {
                                for (Perfil perfil : perfiles) {
                        %>
                        <tr>
                            <td><%= perfil.getIdPerfil()%></td>
                            <td><%= perfil.getNombrePerfil()%></td>
                            <td><%= perfil.getDescripcion()%></td>
                            <td class="acciones">
                                <a href="PerfilController?action=editar&id=<%= perfil.getIdPerfil()%>" class="boton boton-editar" title="Editar Perfil">✏ Editar</a>
                                <a href="views/confirmDeletePerfil.jsp?id=<%= perfil.getIdPerfil()%>" class="boton boton-eliminar" title="Eliminar Perfil" onclick="return confirm('¿Estás seguro de eliminar este perfil?');">🗑 Eliminar</a>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="4" class="mensaje">No hay perfiles registrados.</td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
