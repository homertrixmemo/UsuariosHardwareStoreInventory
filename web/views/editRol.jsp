<%-- 
    Document   : editRol
    Created on : 14 mar 2025, 23:20:13
    Author     : Juan
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.Rol" %>
<%
    Rol rol = (Rol) request.getAttribute("rol");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Rol</title>
        <link rel="stylesheet" href="css/style.css"> <!-- Archivo de estilos centralizado -->
    </head>
    <body>
        <div class="contenedor">
            <div class="card-form">
                <h2 class="text-center">✏ Editar Rol</h2>
                <form action="RolController" method="post">
                    <input type="hidden" name="action" value="actualizar">
                    <input type="hidden" name="id_rol" value="<%= rol.getIdRol()%>">

                    <!-- Campo Nombre -->
                    <label for="nombre_rol">Nombre del Rol:</label>
                    <input type="text" id="nombre_rol" name="nombre_rol" class="input-field" 
                           value="<%= rol.getNombreRol()%>" maxlength="50" required>

                    <!-- Campo Descripción -->
                    <label for="descripcion">Descripción:</label>
                    <textarea id="descripcion" name="descripcion" class="textarea-field" rows="3" 
                              maxlength="255" required><%= rol.getDescripcion()%></textarea>

                    <!-- Botones de Acción -->
                    <div class="button-group">
                        <button type="submit" class="btn-save">💾 Actualizar</button>
                        <a href="RolController?action=listar" class="btn-cancel">❌ Cancelar</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
