<%-- 
    Document   : add
    Created on : 14 mar 2025, 19:02:36
    Author     : Juan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Agregar Perfil</title>
        <link rel="stylesheet" href="css/style.css"> <!-- Archivo de estilos optimizado -->
    </head>
    <body>
        <div class="container">
            <!-- Contenedor Principal -->
            <div class="card-form">
                <div class="form-header">
                    <h3>➕ Agregar Nuevo Perfil</h3>
                </div>
                <div class="form-body">
                    <form action="${pageContext.request.contextPath}/PerfilController" method="post">
                        <input type="hidden" name="action" value="agregar">

                        <!-- Campo Nombre del Perfil -->
                        <div class="form-group">
                            <label for="nombre">Nombre del Perfil:</label>
                            <input type="text" id="nombre" name="nombre" class="input-field" maxlength="50" placeholder="Ejemplo: Administrador" required>
                        </div>

                        <!-- Campo Descripción -->
                        <div class="form-group">
                            <label for="descripcion">Descripción:</label>
                            <textarea id="descripcion" name="descripcion" class="textarea-field" rows="3" maxlength="255" placeholder="Escriba una breve descripción..." required></textarea>
                        </div>

                        <!-- Botones de Acción -->
                        <div class="button-group">
                            <button type="submit" class="btn-save">💾 Guardar</button>
                            <a href="${pageContext.request.contextPath}/PerfilController?action=listar" class="btn-cancel">❌ Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
